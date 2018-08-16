def call(nuspecLocations, version) {
    echo "In Method"
    def endingIndex = nuspecLocations.size() - 1
    for (i in 0..endingIndex) {
        echo "In Loop"
        bat "cd"
        echo "bat \"\"C:\\Program Files (x86)\\Nuget\\nuget.exe\" pack \"${nuspecLocations[i]}\" -Version ${version} -OutputDirectory artifacts/ -NoPackageAnalysis\""
//        bat "\"C:\\Program Files (x86)\\Nuget\\nuget.exe\" pack \"${nuspecLocations[i]}\" -Version ${version} -OutputDirectory artifacts/ -NoPackageAnalysis"
    }
}

