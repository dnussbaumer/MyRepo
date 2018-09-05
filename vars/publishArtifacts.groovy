def call (workspacePath) {
    bat 'if exist PlatCommon rmdir PlatCommon /s /q'
    dir('PlatCommon/Reed.Platform') {
        //            def configText = readFile(configFilePath)
        //                        checkout([$class: 'TeamFoundationServerScm', credentialsConfigurer: [$class: 'AutomaticCredentialsConfigurer'], projectPath: '$/Support/DevOps/Pipeline/AT/Common/Reed.Platform', serverUrl: 'http://coautfssp001/tfs/iAM', useOverwrite: true, useUpdate: true, workspaceName: 'Hudson-${JOB_NAME}-${NODE_NAME}-test'])
        checkout([$class: 'TeamFoundationServerScm', password: hudson.util.Secret.fromString('23YNJFnvY!5J'), projectPath: '$/Support/DevOps/Pipeline/AT/Common/Reed.Platform', serverUrl: 'http://coautfssp001/tfs/iAM', useOverwrite: true, useUpdate: true, userName: 'tfsbuild', workspaceName: 'Hudson-${JOB_NAME}-${NODE_NAME}-test'])
        def batScript = ""
//        def workspacePath = "D:\\workspace\\Pipeline.Tester\\PlatDir\\"
        def configText = """etc\\src\\Benefits\\Services\\Reed.Benefits.Api\\obj\\Release\\Reed.Benefits.Api.dll
        etc\\src\\Benefits\\Services\\Reed.Benefits.Api\\obj\\Release\\Reed.Benefits.Api.pdb
        etc\\src\\Benefits\\Web\\Reed.Benefits.Web.Models\\obj\\Release\\Reed.Benefits.Web.Models.dll
        etc\\src\\Benefits\\Web\\Reed.Benefits.Web.Models\\obj\\Release\\Reed.Benefits.Web.Models.pdb
        etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Communication.Hig\\obj\\Release\\Reed.Communication.Hig.dll
        etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Communication.Hig\\obj\\Release\\Reed.Communication.Hig.pdb
        etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Sdk.Communication\\obj\\Release\\Reed.Sdk.Communication.dll
        etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Sdk.Communication\\obj\\Release\\Reed.Sdk.Communication.pdb
        etc\\src\\Services\\Reed.Communication\\Reed.Api.Communication\\obj\\Release\\Reed.Api.Communication.dll
        etc\\src\\Services\\Reed.Communication\\Reed.Api.Communication\\obj\\Release\\Reed.Api.Communication.pdb"""
        def configLines = configText.split('\n')
        def lineIndex = configLines.size() - 1
        for(i in 0..lineIndex) {
            def source = workspacePath + configLines[i]
            echo "workspacePath = ${workspacePath}"
            echo "configLines[${i}] =  ${configLines[i]}"
            echo "batScript = Xcopy \"${source}\" . /q /y /r\n"
            batScript += "Xcopy \"${source}\" . /q /y /r\n"
        }
        bat batScript
        powershell 'Start-ScheduledTask "TF Checkin"'
    }
}



//pipeline {
//    agent { label 'archive01' }
//    stages {
//        stage('Publish Artifact') {
//            steps {
//                script {
//                    bat 'if exist PlatCommon rmdir PlatCommon /s /q'
//                    dir('PlatCommon/Reed.Platform') {
//            //            def configText = readFile(configFilePath)
////                        checkout([$class: 'TeamFoundationServerScm', credentialsConfigurer: [$class: 'AutomaticCredentialsConfigurer'], projectPath: '$/Support/DevOps/Pipeline/AT/Common/Reed.Platform', serverUrl: 'http://coautfssp001/tfs/iAM', useOverwrite: true, useUpdate: true, workspaceName: 'Hudson-${JOB_NAME}-${NODE_NAME}-test'])
//                        checkout([$class: 'TeamFoundationServerScm', password: hudson.util.Secret.fromString('23YNJFnvY!5J'), projectPath: '$/Support/DevOps/Pipeline/AT/Common/Reed.Platform', serverUrl: 'http://coautfssp001/tfs/iAM', useOverwrite: true, useUpdate: true, userName: 'tfsbuild', workspaceName: 'Hudson-${JOB_NAME}-${NODE_NAME}-test'])
//                        def batScript = ""
//                        def workspacePath = "D:\\workspace\\Pipeline.Tester\\PlatDir\\"
//                        def configText = """etc\\src\\Benefits\\Services\\Reed.Benefits.Api\\obj\\Release\\Reed.Benefits.Api.dll
//etc\\src\\Benefits\\Services\\Reed.Benefits.Api\\obj\\Release\\Reed.Benefits.Api.pdb
//etc\\src\\Benefits\\Web\\Reed.Benefits.Web.Models\\obj\\Release\\Reed.Benefits.Web.Models.dll
//etc\\src\\Benefits\\Web\\Reed.Benefits.Web.Models\\obj\\Release\\Reed.Benefits.Web.Models.pdb
//etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Communication.Hig\\obj\\Release\\Reed.Communication.Hig.dll
//etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Communication.Hig\\obj\\Release\\Reed.Communication.Hig.pdb
//etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Sdk.Communication\\obj\\Release\\Reed.Sdk.Communication.dll
//etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Sdk.Communication\\obj\\Release\\Reed.Sdk.Communication.pdb\n\
//etc\\src\\Services\\Reed.Communication\\Reed.Api.Communication\\obj\\Release\\Reed.Api.Communication.dll
//etc\\src\\Services\\Reed.Communication\\Reed.Api.Communication\\obj\\Release\\Reed.Api.Communication.pdb"""
//                        def configLines = configText.split('\n')
//                        def lineIndex = configLines.size() - 1
//                        for(i in 0..lineIndex) {
//                            def source = workspacePath + configLines[i]
//                            batScript += "Xcopy \"${source}\" . /q /y /r\n"
//                        }
//                        bat batScript
//                        powershell 'Start-ScheduledTask "TF Checkin"'
//                    }
//                }
//            }
//        }        
//    }
//}