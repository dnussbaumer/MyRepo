def call(nuspecLocations, version) {
    def endingIndex = nuspecLocations.size() - 1
    for (i in 0..endingIndex) {
        echo "bat \"\"C:\\Program Files (x86)\\Nuget\\nuget.exe\" pack \"${nuspecLocations[i]}\" -Version ${version} -OutputDirectory artifacts/ -NoPackageAnalysis\""
        bat "\"C:\\Program Files (x86)\\Nuget\\nuget.exe\" pack \"${nuspecLocations[i]}\" -Version ${version} -OutputDirectory artifacts/ -NoPackageAnalysis"
    }
}

