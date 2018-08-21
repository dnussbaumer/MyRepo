def call(project, configFileText, file) {
    def configLines = configFileText.split('\n')
    def changeFound = 'false'

    def configEndingIndex = configLines.size() - 1
    for (i in 0..configEndingIndex) {
        def configValues = configLines[i].split(',')
        if (configValues[0] == project && file.path.contains(configValues[1])) {
            echo "found change in ${project}"
            changeFound = 'true'
        }
    }
    return changeFound
}
