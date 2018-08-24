def call(packageDir, filter) {
    def packageVersion = powershell returnStdout: true, script: '''$dir = "${packageDir}"\n\
                    Start-Sleep -s 15
                    $filter="${filter}"
                    $latest = Get-ChildItem -Path $dir -Filter $filter | Sort-Object -Descending { [regex]::Replace($_, '\\d+', { $args[0].Value.PadLeft(20) })} | Select-Object -First 1
                    $filterArray = $filter.Split(".")
                    $fileNameArray = $latest.name.Split(".")
                    $versionIndex = $filterArray.length + 2
                    $oldVersion = [convert]::ToInt32($fileNameArray[$versionIndex],10)
                    $newVersion = $oldVersion + 1
                    $newVersion'''
    packageVersion = packageVersion.trim()
    return packageVersion
}

//pipeline {
//    agent any
//    stages {
//        stage('Setting Versions') {
//            steps {
//                script {
//                    packageVersion = powershell returnStdout: true, script: '''$dir = "\\\\COAUTFSARH001\\Builds\\NugetPackages\\AT\\Platform"
//                    $filter="Reed.CommunicationIntegration.Service*"
//                    $latest = Get-ChildItem -Path $dir -Filter $filter | Sort-Object -Descending { [regex]::Replace($_, '\\d+', { $args[0].Value.PadLeft(20) })} | Select-Object -First 1
//                    $filterArray = $filter.Split(".")
//                    $fileNameArray = $latest.name.Split(".")
//                    $versionIndex = $filterArray.length + 2
//                    $oldVersion = [convert]::ToInt32($fileNameArray[$versionIndex],10)
//                    $newVersion = $oldVersion + 1
//                    $newVersion'''
//                    packageVersion = packageVersion.trim()
//                    bat "echo ${packageVersion}" 
//                }
//            }
//        }
//    }
//}