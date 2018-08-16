def call(project, configFilePath, packageLocation, version) {
    echo "Entering Method"
    def batCommand = ""

    def configText = readFile(configFilePath)
    echo configText
    def configLines = configText.split('\n')
    def nuspecLocations = []
    def nuspecLocationIndex = 0
    def configEndingIndex = configLines.size() - 1
    for (i in 0..configEndingIndex) {
        def configValues = configLines[i].split(',')
        if (configValues[0] == project) {
            nuspecLocations[nuspecLocationIndex] = configValues[1]
            nuspecLocationIndex++
        }
    }
    def endingIndex = nuspecLocations.size() - 1
    bat 'if exist artifacts rmdir artifacts /s /q'
    bat 'mkdir artifacts'
    for (i in 0..endingIndex) {
        batCommand += "\"C:\\Program Files (x86)\\Nuget\\nuget.exe\" pack \"${nuspecLocations[i]}\" -Version ${version} -OutputDirectory artifacts/ -NoPackageAnalysis\n"
    }
    batCommand += "\"C:\\Program Files (x86)\\Nuget\\nuget.exe\" push -source ${packageLocation} artifacts\\*.${version}.nupkg"
    bat batCommand
}

