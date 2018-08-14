@Library('LeavePro.AT.Pipeline')_

pipeline {
    agent any
    environment {
        packCCF = 'false'
        buildPlatform = 'false'
        buildPlatformDb = 'false'
        buildSaaS = 'false'
        deploySaaSSSRS = 'false'
        buildSaaSDb = 'false'
        buildCurly = 'false'
        deployCurlySSRS = 'false'
        buildCurlyDb = 'false'
        buildLarrySS = 'false'
        buildNxt = 'false'
        buildStgPlatform = 'false'
        buildStgBenefits = 'false'
        buildStgTools = 'false'
        buildStgSaaS = 'false'
        buildStgCurly = 'false'
        changeLines = ''
        optionLine = ''
        runAutoChangePS = 'false'
    }
    stages {
        stage('checkout') {
            steps {
                dir('ccfDir') {
                    checkout([$class: 'TeamFoundationServerScm', credentialsConfigurer: [$class: 'AutomaticCredentialsConfigurer'], projectPath: '$/Support/Central Configuration Files', serverUrl: 'http://coautfssp001/tfs/iAM', useOverwrite: true, useUpdate: true, workspaceName: 'Hudson-${JOB_NAME}-${NODE_NAME}-ccf'])
                }
                dir('PlatDir') {
                    checkout([$class: 'TeamFoundationServerScm', credentialsConfigurer: [$class: 'AutomaticCredentialsConfigurer'], projectPath: '$/Gold/Platform/AT', serverUrl: 'http://coautfssp001/tfs/iAM', useOverwrite: true, useUpdate: true, workspaceName: 'Hudson-${JOB_NAME}-${NODE_NAME}-plat'])
                }
                dir('SaaSDir') {
                    checkout([$class: 'TeamFoundationServerScm', credentialsConfigurer: [$class: 'AutomaticCredentialsConfigurer'], projectPath: '$/SaaS/AT', serverUrl: 'http://coautfssp001/tfs/iAM', useOverwrite: true, useUpdate: true, workspaceName: 'Hudson-${JOB_NAME}-${NODE_NAME}-saas'])
                }
                dir('CurlyDir') {
                    checkout([$class: 'TeamFoundationServerScm', credentialsConfigurer: [$class: 'AutomaticCredentialsConfigurer'], projectPath: '$/Curly/AT', serverUrl: 'http://coautfssp001/tfs/iAM', useOverwrite: true, useUpdate: true, workspaceName: 'Hudson-${JOB_NAME}-${NODE_NAME}-curly'])
                }
                dir('LarryDir') {
                    checkout([$class: 'TeamFoundationServerScm', credentialsConfigurer: [$class: 'AutomaticCredentialsConfigurer'], projectPath: '$/Larry/AT', serverUrl: 'http://coautfssp001/tfs/iAM', useOverwrite: true, useUpdate: true, workspaceName: 'Hudson-${JOB_NAME}-${NODE_NAME}-larry'])
                }
                script {
                    build = currentBuild
                    List configList = []
                    List dbConfigList = []
                    List dbResultList = []
                    changeLines = ''
                    //while(build != null && build.result != 'SUCCESS') {
                    while(build != null) {
                        for (changeLog in build.changeSets) {
                            echo 'Changes Found...'
                            for(entry in changeLog.items) {
                                echo "${entry.commitId} by ${entry.author} on ${entry.date}: ${entry.msg}"
                                echo 'Files Affected...'
                                for(file in entry.affectedFiles) {
                                    echo file.path.toString()
                                    configList = [
                                        ["CCF",packCCF,["\$/Support/Central Configuration Files/BAT","\$/Support/Central Configuration Files/SIT","\$/Support/Central Configuration Files/Stage/Reed Services","\$/Support/Central Configuration Files/Stage/SaaS","\$/Support/Central Configuration Files/Stage/Canada","\$/Support/Central Configuration Files/UAT/Curly"]]
                                    ]
                                    packCCF = findChanges configList, file
                                    configList = [
                                        [["Platform",buildPlatform,["\$/Gold/Platform/AT/etc/src/Services"]],
                                        ["Benefits",buildPlatform,["\$/Gold/Platform/AT/etc/src/Benefits"]],
                                        ["Tools",buildPlatform,["\$/Gold/Platform/AT/etc/src/Tools"]]],
                                        [["PlatformDB",buildPlatformDb,["\$/Gold/Platform/AT/db-branch/Platform"]],
                                        ["BenefitsDB",buildPlatformDb,["\$/Gold/Platform/AT/db-branch/Beneftis"]],
                                        ["ToolsDB",buildPlatformDb,["\$/Gold/Platform/AT/db-branch/Tools"]]]
                                    ]
                                    buildPlatform = findChanges configList[0], file
                                    buildPlatformDb = findChanges configList[1], file
                                    configList = [
                                        [["SaaS",buildSaaS,["\$/SaaS/AT/Source Code","\$/SaaS/AT/Configuration/CustomScripts"]]],
                                        [["SaaSSSRS",deploySaaSSSRS,["\$/SaaS/AT/Source Code/iAM.Reporting","\$/SaaS/AT/Source Code/Reed.Reporting"]]],
                                        [["SaaSDB",buildSaaSDb,["\$/SaaS/AT/Database/CoreDatabases-branch"]]]
                                    ]
                                    buildSaaS = findChanges configList[0], file
                                    deploySaaSSSRS = findChanges configList[1], file
                                    buildSaaSDb = findChanges configList[2], file
                                    configList = [
                                        [["Curly",buildCurly,["\$/Curly/AT/Source Code","\$/Curly/AT/Configuration/CustomScripts"]]],
                                        [["CurlySSRS",deployCurlySSRS,["\$/Curly/AT/Source Code/iAM.Reporting","\$/Curly/AT/Source Code/Reed.Reporting"]]],
                                        [["CurlyDB",buildCurlyDb,["\$/Curly/AT/Database/CoreDatabases-branch"]]]
                                    ]
                                    buildCurly = findChanges configList[0], file
                                    deployCurlySSRS = findChanges configList[1], file
                                    buildCurlyDb = findChanges configList[2], file
                                    configList = [
                                        ["Larry",buildLarrySS,["\$/Larry/AT/Source Code/Reed.Web.SelfService"]]
                                    ]
                                    buildLarrySS = findChanges configList, file
                                    
                                    dbConfigList = [
                                        ["Platform",buildStgPlatform,"-BuildHub ",["\$/Gold/Platform/AT/db/Platform/LeaveHubDb/Schema Objects/Schemas"]],
                                        ["Platform",buildStgPlatform,"-BuildClient ",["\$/Gold/Platform/AT/db/Platform/LeaveClientDb/Schema Objects/Schemas"]],
                                        ["Platform",buildStgPlatform,"-BuildReport ",["\$/Gold/Platform/AT/db/Platform/ReportDb/Schema Objects/Schemas"]]
                                    ]
                                    dbResultList = findDbChanges dbConfigList, file
                                    buildStgPlatform = dbResultList[0]
                                    optionLine = dbResultList[1]
                                    if (buildStgPlatform == 'true' && !(changeLines.contains("-Schema Platform"))){
                                        changeLines = addChanges changeLines, optionLines
                                    }
                                    
                                    dbConfigList = [    
                                        ["Benefits",buildStgBenefits,"-BuildHub ",["\$/Gold/Platform/AT/db/Benefits/BenefitsHubDb/Schema Objects/Schemas"]],
                                        ["Benefits",buildStgBenefits,"-BuildClient ",["\$/Gold/Platform/AT/db/Benefits/BenefitsClientDb/Schema Objects/Schemas"]]
                                    ]
                                    dbResultList = findDbChanges dbConfigList, file
                                    buildStgBenefits = dbResultList[0]
                                    optionLine = dbResultList[1]
                                    if (buildStgBenefits == 'true' && !(changeLines.contains("-Schema Benefits"))){
                                        changeLines = addChanges changeLines, optionLines
                                    }
                                    dbConfigList = [    
                                        ["Tools",buildStgTools,"Tools",["\$/Gold/Platform/AT/db/Tools/Schema Objects"]]
                                    ]
                                    dbResultList = findDbChanges dbConfigList, file
                                    buildStgTools = dbResultList[0]
                                    optionLine = dbResultList[1]
                                    if (buildStgTools == 'true' && !(changeLines.contains("-Schema Tools"))){
                                        changeLines = addChanges changeLines, optionLines
                                    }
                                    dbConfigList = [    
                                        ["SaaS",buildStgSaaS,"-BuildHub ",["\$/SaaS/AT/Database/CoreDatabases/LeaveHubDb_SaaS/Schema Objects/Schemas"]],
                                        ["SaaS",buildStgSaaS,"-BuildClient ",["\$/SaaS/AT/Database/CoreDatabases/LeaveClientDb_SaaS/Schema Objects/Schemas"]],
                                        ["SaaS",buildStgSaaS,"-BuildReport ",["\$/SaaS/AT/Database/CoreDatabases/ReportDb_SaaS/Schema Objects/Schemas"]]
                                    ]
                                    dbResultList = findDbChanges dbConfigList, file
                                    buildStgSaaS = dbResultList[0]
                                    optionLine = dbResultList[1]
                                    if (buildStgSaaS == 'true' && !(changeLines.contains("-Schema SaaS"))){
                                        changeLines = addChanges changeLines, optionLines
                                    }
                                    dbConfigList = [    
                                        ["Curly",buildStgCurly,"-BuildHub ",["\$/Curly/AT/Database/CoreDatabases/LeaveHubDb_Curly/Schema Objects/Schemas"]],
                                        ["Curly",buildStgCurly,"-BuildClient ",["\$/Curly/AT/Database/CoreDatabases/LeaveClientDb_Curly/Schema Objects/Schemas"]],
                                        ["Curly",buildStgCurly,"-BuildReport ",["\$/Curly/AT/Database/CoreDatabases/ReportDb_Curly/Schema Objects/Schemas"]]
                                    ]
                                    dbResultList = findDbChanges dbConfigList, file
                                    buildStgCurly = dbResultList[0]
                                    optionLine = dbResultList[1]
                                    if (buildStgCurly == 'true' && !(changeLines.contains("-Schema Curly"))){
                                        changeLines = addChanges changeLines, optionLines
                                    }
                                }
                            }
                        }
                        build = build.previousBuild
                    }
                }
                echo "packCCF = ${packCCF}"
                echo "buildPlatform = ${buildPlatform}"
                echo "buildPlatformDb = ${buildPlatformDb}"
                echo "buildSaaS = ${buildSaaS}"
                echo "deploySaaSSSRS = ${deploySaaSSSRS}"
                echo "buildSaaSDb = ${buildSaaSDb}"
                echo "buildCurly = ${buildCurly}"
                echo "deployCurlySSRS = ${deployCurlySSRS}"
                echo "buildCurlyDb = ${buildCurlyDb}"
                echo "buildLarrySS = ${buildLarrySS}"
                echo "buildStgPlatform = ${buildStgPlatform}"
                echo "buildStgBenefits = ${buildStgBenefits}"
                echo "buildStgTools = ${buildStgTools}"
                echo "buildStgSaaS = ${buildStgSaaS}"
                echo "buildStgCurly = ${buildStgCurly}"
                echo "changeLines = ${changeLines}"
            }
        }
    }
}

/*
String findChanges(List configList, file) {
    def endingOuterIndex = configList.size() - 1
    def endingInnerIndex = 0
    def changeFound = 'false'
    def i = 0
    while (i <= endingOuterIndex && changeFound == 'false') {
        changeFound = configList[i][1]
        if (changeFound == 'false') {
            endingInnerIndex = configList[i][2].size() -1
            for (j in 0..endingInnerIndex) {
                if (file.path.contains(configList[i][2][j])) {
                    changeFound = 'true'
                }
            }
        }
        i++
    }
    return changeFound
}

List findDbChanges(List configList, file) {
    def endingOuterIndex = configList.size() - 1
    def endingInnerIndex = 0
    def changeFound = 'false'
    def optionLine = ''
    for (i in 0..endingOuterIndex) {
        if (changeFound == 'false') {
            changeFound = configList[i][1]
        }
        endingInnerIndex = configList[i][3].size() -1
        for (j in 0..endingInnerIndex) {
            if (file.path.contains(configList[i][3][j])) {
                changeFound = 'true'
                if (!optionLine.contains(configList[i][0])) {
                    optionLine = configList[i][0] + ' '
                }
                if (!optionLine.contains(configList[i][2])) {
                    optionLine += configList[i][2]
                }
            }
        }
    }
    List returnList = [changeFound,optionLine]    
    return returnList
}

String addChanges(String changeLines, String optionLine) {
    changeLines += ".\\AutoCS.ps1 -ConfigFile .\\ChangeScript.config -Environment AT -Schema ${optionLine} -SkipBuildDb -SkipCompare -SkipTFSChangeScript\n"
    changeLines += ".\\AutoCS.ps1 -ConfigFile .\\ChangeScript.config -Environment AT -Schema ${optionLine} -SkipCompare -SkipTFSChangeScript -SkipCreateFiles\n"
    changeLines += ".\\AutoCS.ps1 -ConfigFile .\\ChangeScript.config -Environment AT -Schema ${optionLine} -SkipCreateFiles -SkipBuildDb \n"
}
*/