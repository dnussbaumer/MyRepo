def call(nuspecLocations, version) {
    def batCommand = ""
    def endingIndex = nuspecLocations.size() - 1
    for (i in 0..endingIndex) {
        batCommand += "\"C:\\Program Files (x86)\\Nuget\\nuget.exe\" pack \"${nuspecLocations[i]}\" -Version ${version} -OutputDirectory artifacts/ -NoPackageAnalysis\n"
//        bat "\"C:\\Program Files (x86)\\Nuget\\nuget.exe\" pack \"${nuspecLocations[i]}\" -Version ${version} -OutputDirectory artifacts/ -NoPackageAnalysis"
    }
    bat batCommand
//    return batCommand
}

