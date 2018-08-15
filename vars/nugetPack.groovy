def call(workSpace, version, packageLocation) {
    bat 'if exist artifacts rmdir artifacts /s /q'
    bat 'mkdir artifacts'
    bat "\"C:\\Program Files (x86)\\Nuget\\nuget.exe\" pack \"${workSpace}\\ccfDir\\UAT\\AT_CCF.nuspec\" -Version ${version} -OutputDirectory ccfDir/artifacts/ -NoPackageAnalysis"
    bat "\"C:\\Program Files (x86)\\Nuget\\nuget.exe\" push -source ${packageLocation}/Platform ccfDir/artifacts/AT_CCF.${version}.nupkg"
}

