def call (workspacePath, configFile) {
    bat 'if exist PlatCommon rmdir PlatCommon /s /q'
    dir('../PlatCommon/Reed.Platform') {
        checkout([$class: 'TeamFoundationServerScm', password: hudson.util.Secret.fromString('23YNJFnvY!5J'), projectPath: '$/Support/DevOps/Pipeline/AT/Common/Reed.Platform', serverUrl: 'http://coautfssp001/tfs/iAM', useOverwrite: true, useUpdate: true, userName: 'tfsbuild', workspaceName: 'Hudson-${JOB_NAME}-${NODE_NAME}-test'])
        def batScript = ""
        if (!workspacePath.endsWith("\\")) {
            workspacePath += "\\"
        }
        def configText = readFile(configFile)
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
        def currentDir = pwd()
        bat 'del /F C:\\tf_checkin.bat'
        writeFile file: 'C:\\tf_checkin.bat', text: """(\r
D:\r
cd ${currentDir}\r
\"C:\\Program Files (x86)\\Microsoft Visual Studio 14.0\\Common7\\IDE\\TF.exe\" workspaces /collection:\"http://coautfssp001/tfs/iam\"\r
\"C:\\Program Files (x86)\\Microsoft Visual Studio 14.0\\Common7\\IDE\\TF.exe\" add * /recursive /noignore\r
\"C:\\Program Files (x86)\\Microsoft Visual Studio 14.0\\Common7\\IDE\\TF.exe\" checkin /comment:\"Auto Checkin\" /force /noprompt /override:\"Auto Checkin\" /recursive\r
) >> C:\\output.txt 2>&1"""
        powershell 'Start-ScheduledTask "TF Checkin"'
    }
}