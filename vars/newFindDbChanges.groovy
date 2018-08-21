def call(project, configFileText, file) {
    def configLines = configFileText.split("\n")
    def endingOuterIndex = configLines.size() - 1
    def endingInnerIndex = 0
    def changeFound = 'false'
    def optionLine = ''
    for (i in 0..endingOuterIndex) {
        def configValues = configLines[i].split(',')
            if (configValues[0] == project && file.path.contains(configValues[2])) {
                changeFound = 'true'
                optionLine += configValues[1]
            }
    }
    def returnList = [changeFound,optionLine]    
    return returnList
}