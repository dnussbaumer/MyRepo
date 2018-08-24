def call(packageDir, filter) {
    def psScript = """\$dir = '${packageDir}'
                    \$filter='${filter}'
                    \$latest = Get-ChildItem -Path \$dir -Filter \$filter | Sort-Object -Descending { [regex]::Replace(\$_, '\\d+', { \$args[0].Value.PadLeft(20) })} | Select-Object -First 1
                    \$filterArray = \$filter.Split('.')
                    \$fileNameArray = \$latest.name.Split('.')
                    \$versionIndex = \$filterArray.length + 2
                    \$oldVersion = [convert]::ToInt32(\$fileNameArray[\$versionIndex],10)
                    \$newVersion = \$oldVersion + 1
                    \$newVersion"""
    def packageVersion = powershell returnStdout: true, script: psScript
    packageVersion = packageVersion.trim()
    return packageVersion
}