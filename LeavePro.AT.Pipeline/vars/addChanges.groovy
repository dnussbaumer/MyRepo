def call(String changeLines, String optionLine) {
    changeLines += ".\\AutoCS.ps1 -ConfigFile .\\ChangeScript.config -Environment AT -Schema ${optionLine} -SkipBuildDb -SkipCompare -SkipTFSChangeScript\n"
    changeLines += ".\\AutoCS.ps1 -ConfigFile .\\ChangeScript.config -Environment AT -Schema ${optionLine} -SkipCompare -SkipTFSChangeScript -SkipCreateFiles\n"
    changeLines += ".\\AutoCS.ps1 -ConfigFile .\\ChangeScript.config -Environment AT -Schema ${optionLine} -SkipCreateFiles -SkipBuildDb \n"
}