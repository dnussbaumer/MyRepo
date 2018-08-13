package reed.leavepro.poll

def findChanges(List configList, file) {
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

def findDbChanges(List configList, file) {
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

def addChanges(String changeLines, String optionLine) {
    changeLines += ".\\AutoCS.ps1 -ConfigFile .\\ChangeScript.config -Environment AT -Schema ${optionLine} -SkipBuildDb -SkipCompare -SkipTFSChangeScript\n"
    changeLines += ".\\AutoCS.ps1 -ConfigFile .\\ChangeScript.config -Environment AT -Schema ${optionLine} -SkipCompare -SkipTFSChangeScript -SkipCreateFiles\n"
    changeLines += ".\\AutoCS.ps1 -ConfigFile .\\ChangeScript.config -Environment AT -Schema ${optionLine} -SkipCreateFiles -SkipBuildDb \n"
    return changeLines
}

