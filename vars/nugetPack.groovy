def call(configText, packageLocation, version) {
    def batCommand = ""
    def endingIndex = nuspecLocations.size() - 1
    
    echo configText
    bat 'if exist artifacts rmdir artifacts /s /q'
    bat 'mkdir artifacts'
    for (i in 0..endingIndex) {
        batCommand += "\"C:\\Program Files (x86)\\Nuget\\nuget.exe\" pack \"${nuspecLocations[i]}\" -Version ${version} -OutputDirectory artifacts/ -NoPackageAnalysis\n"
    }
    batCommand += "\"C:\\Program Files (x86)\\Nuget\\nuget.exe\" push -source ${packageLocation} artifacts\\*.${version}.nupkg"
    echo "batCommand = \n${batCommand}"
    bat batCommand
}

