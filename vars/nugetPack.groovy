def call(nuspecLocations, version) {
    echo 'Inside method'
    def endingIndex = nuspecLocations.size() - 1
    for (i in 0..endingIndex) {
        echo "nuspecLocations = ${nuspecLocations[i]}"
        echo "version = ${version}"
//        bat "\"C:\\Program Files (x86)\\Nuget\\nuget.exe\" pack \"${nuspecLocations[i]}\" -Version ${version} -OutputDirectory ccfDir/artifacts/ -NoPackageAnalysis"
    }
}

