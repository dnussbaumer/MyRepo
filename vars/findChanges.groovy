def call(project, configFileText, file) {
    def configLines = configFileText.split('\n')
    def changeFound = 'false'

    def configEndingIndex = configLines.size() - 1
    echo "Method project = ${project}"
    for (i in 0..configEndingIndex) {
        echo "configLine = ${configLines[i]}"
        def configValues = configLines[i].split(',')
        echo "configValue project = ${configValues[0]}"
        echo "file path = ${file.path.toString()}"
        echo "configValues path = ${configValues[1]}"
        if (configValues[0] == project) {
            echo 'Correct Project'
        }
        if (file.path.contains(configValues[1])) {
            echo 'Correct Path'
        }
        if ((configValues[0] == project) && (file.path.contains(configValues[1]))) {
            echo "found change in ${project}"
            changeFound = 'true'
        }
    }
    return changeFound
}
