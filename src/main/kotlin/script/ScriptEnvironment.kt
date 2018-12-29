package script

import java.io.File
import kotlin.script.experimental.annotations.KotlinScript


@KotlinScript(
        compilationConfiguration = ScriptEnvironmentConfiguration::class
)
open class ScriptEnvironment(val directory: File) {
    override fun toString() = "ScriptEnvironment(directory = $directory) is a ${this::class.qualifiedName}"
}