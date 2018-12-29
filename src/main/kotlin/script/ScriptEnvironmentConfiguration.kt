package script

import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.compilerOptions
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

class ScriptEnvironmentConfiguration: ScriptCompilationConfiguration({
    jvm {
        dependenciesFromCurrentContext()
    }

    compilerOptions.append("jvm-target", "1.8")
})