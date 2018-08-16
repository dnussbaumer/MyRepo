def call(nuspecLocations, version) {
    echo 'Inside method'
    def endingIndex = nuspecLocations.size() - 1
    for (i in 0..endingIndex) {
        echo "nuspecLocation ${i} = ${nuspecLocations[i]}"
        bat "\"C:\\Program Files (x86)\\Nuget\\nuget.exe\" pack \"${nuspecLocations[i]}\" -Version ${version} -OutputDirectory artifacts/ -NoPackageAnalysis"
    }
}

