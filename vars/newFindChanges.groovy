def call(project, configFilePath, file) {
    echo 'Entering method call'
    echo "project = ${project}"
    echo "file = ${file.path.toString()}"
    echo "Attempting to read file ${configFilePath}"
    def configText = readFile(configFilePath)
//    def configText = "CCF,\$/Support/Central Configuration Files/BAT\n" +
//                    "CCF,\$/Support/Central Configuration Files/SIT\n" +
//                    "CCF,\$/Support/Central Configuration Files/Stage/Reed Services\n" +
//                    "CCF,\$/Support/Central Configuration Files/Stage/SaaS\n" +
//                    "CCF,\$/Support/Central Configuration Files/Stage/Canada\n" +
//                    "CCF,\$/Support/Central Configuration Files/UAT/Curly"
//    echo 'file read'
//    def configLines = configText.split('\n')
//    echo 'lines split'
    def changeFound = 'false'
//    
//    def configEndingIndex = configLines.size() - 1
//    for (i in 0..configEndingIndex) {
//        def configValues = configLines[i].split(',')
//        echo 'values split'
//        if (configValues[0] == project && file.path.contains(configValues[1])) {
//            echo "found change in ${project}"
//            changeFound = 'true'
//        }
//    }
    echo 'returning'
    return changeFound
}
