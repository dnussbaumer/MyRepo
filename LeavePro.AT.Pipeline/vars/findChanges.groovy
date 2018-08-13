String call(List configList, file) {
    def endingOuterIndex = configList.size() - 1
    def endingInnerIndex = 0
    def changeFound = 'false'
    def i = 0
    while (i <= endingOuterIndex && changeFound == 'false') {
        changeFound = configList[i][1]
        if (changeFound == 'false') {
            endingInnerIndex = configList[i][2].size() -1
            for (j in 0..endingInnerIndex) {
                if (file.path.contains(configList[i][2][j])) {
                    changeFound = 'true'
                }
            }
        }
        i++
    }
    return changeFound
}