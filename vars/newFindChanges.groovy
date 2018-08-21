def call(project, configFilePath, file) {
    def configText = readFile(configFilePath)
    def configLines = configText.split('\n')
    def changeFound = 'false'
    
    def configEndingIndex = configLines.size() - 1
    for (i in 0..configEndingIndex) {
        def configValues = configLines[i].split(',')
        if (configValues[0] == project && file.path.contains(configValues[1])) {
            changeFound = 'true'
        }
    }
    return changeFound
}
