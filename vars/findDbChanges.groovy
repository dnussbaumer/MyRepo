def call(List configList, file) {
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