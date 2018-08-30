def call (workspacePath) {
//    def configText = readFile(configFilePath)
    def batScript = ""
    if (!workspacePath.endsWith("\\")) {
        workspacePath += "\\"
    }
    def configText = """PlatDir\\etc\\src\\Benefits\\Services\\Reed.Benefits.Api\\obj\\Release\\Reed.Benefits.Api.dll,SaaSDir\\Source Code\\Common\\Reed.Platform
PlatDir\\etc\\src\\Benefits\\Services\\Reed.Benefits.Api\\obj\\Release\\Reed.Benefits.Api.pdb,SaaSDir\\Source Code\\Common\\Reed.Platform
PlatDir\\etc\\src\\Benefits\\Web\\Reed.Benefits.Web.Models\\obj\\Release\\Reed.Benefits.Web.Models.dll,SaaSDir\\Source Code\\Common\\Reed.Platform
PlatDir\\etc\\src\\Benefits\\Web\\Reed.Benefits.Web.Models\\obj\\Release\\Reed.Benefits.Web.Models.pdb,SaaSDir\\Source Code\\Common\\Reed.Platform
PlatDir\\etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Communication.Hig\\obj\\Release\\Reed.Communication.Hig.dll,SaaSDir\\Source Code\\Common\\Reed.Platform
PlatDir\\etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Communication.Hig\\obj\\Release\\Reed.Communication.Hig.pdb,SaaSDir\\Source Code\\Common\\Reed.Platform
PlatDir\\etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Sdk.Communication\\obj\\Release\\Reed.Sdk.Communication.dll,SaaSDir\\Source Code\\Common\\Reed.Platform
PlatDir\\etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Sdk.Communication\\obj\\Release\\Reed.Sdk.Communication.pdb,SaaSDir\\Source Code\\Common\\Reed.Platform"""
    def configLines = configText.split('\n')
    def lineIndex = configLines.size() - 1
    bat "echo lineIndex = ${lineIndex}"
    for(i in 0..lineIndex) {
        def configLine = configLines[i].toString()
        def configValues = configLine.split(',')
        def source = workspacePath + configValues[0]
        def target = workspacePath + configValues[1]
        batScript += "Xcopy \"${source}\" \"${target}\" /q /y /r\n"
    }
    bat batScript
}



//pipeline {
//    agent { label 'archive01' }
//    stages {
//        stage('Publish Artifact') {
//            steps {
//                script {
//        //            def configText = readFile(configFilePath)
//                    def batScript = ""
//                    def workspacePath = pwd() + "\\"
//                    def configText = """PlatDir\\etc\\src\\Benefits\\Services\\Reed.Benefits.Api\\obj\\Release\\Reed.Benefits.Api.dll,SaaSDir\\Source Code\\Common\\Reed.Platform
//PlatDir\\etc\\src\\Benefits\\Services\\Reed.Benefits.Api\\obj\\Release\\Reed.Benefits.Api.pdb,SaaSDir\\Source Code\\Common\\Reed.Platform
//PlatDir\\etc\\src\\Benefits\\Web\\Reed.Benefits.Web.Models\\obj\\Release\\Reed.Benefits.Web.Models.dll,SaaSDir\\Source Code\\Common\\Reed.Platform
//PlatDir\\etc\\src\\Benefits\\Web\\Reed.Benefits.Web.Models\\obj\\Release\\Reed.Benefits.Web.Models.pdb,SaaSDir\\Source Code\\Common\\Reed.Platform
//PlatDir\\etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Communication.Hig\\obj\\Release\\Reed.Communication.Hig.dll,SaaSDir\\Source Code\\Common\\Reed.Platform
//PlatDir\\etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Communication.Hig\\obj\\Release\\Reed.Communication.Hig.pdb,SaaSDir\\Source Code\\Common\\Reed.Platform
//PlatDir\\etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Sdk.Communication\\obj\\Release\\Reed.Sdk.Communication.dll,SaaSDir\\Source Code\\Common\\Reed.Platform
//PlatDir\\etc\\src\\Services\\Reed.Communication\\Delivery\\Reed.Sdk.Communication\\obj\\Release\\Reed.Sdk.Communication.pdb,SaaSDir\\Source Code\\Common\\Reed.Platform"""
//                    def configLines = configText.split('\n')
//                    def lineIndex = configLines.size() - 1
//                    bat "echo lineIndex = ${lineIndex}"
//                    for(i in 0..lineIndex) {
//                        def configLine = configLines[i].toString()
//                        def configValues = configLine.split(',')
//                        def source = workspacePath + configValues[0]
//                        def target = workspacePath + configValues[1]
//                        batScript += "Xcopy \"${source}\" \"${target}\" /q /y /r\n"
//                    }
//                    bat batScript
//                }
//            }
//        }        
//    }
//}
