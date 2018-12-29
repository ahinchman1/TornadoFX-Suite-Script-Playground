package com.github.hd.tornadofxsuite.controller

import com.github.hd.tornadofxsuite.view.Dialog
import com.github.hd.tornadofxsuite.view.MainView
import de.swirtz.ktsrunner.objectloader.KtsObjectLoader
import javafx.util.Duration
import kastree.ast.Node
import kastree.ast.psi.Converter
import kastree.ast.psi.Parser
import script.ScriptEnvironment
import tornadofx.*
import java.io.BufferedReader
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.javaHome
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

class TestGenerator: Controller() {
    val kotlinFiles = ArrayList<File>()
    private val view: MainView by inject()
    private val scanner: Scanner by inject()

    var scriptConfiguration = createJvmCompilationConfigurationFromTemplate<ScriptEnvironment> {
        jvm {
            dependenciesFromCurrentContext(wholeClasspath = true)

            // you need to specify the jdk location, should usually be the same as the JAVA_HOME env variable anyway
            val JDK_HOME = System.getenv("JAVA_HOME")
                    ?: throw IllegalStateException("please set JAVA_HOME to the installed jdk")
            javaHome(File(JDK_HOME))
        }

        // once more for good measure
        compilerOptions.append("-jvm-target", "1.8")
    }

    fun walk(path: String) {
        Files.walk(Paths.get(path)).use { allFiles ->
            allFiles.filter { path -> path.toString().endsWith(".kt") }
                    .forEach {
                        fileOutputRead(it)
                    }
        }
        consoleLog()
    }

    private fun readFiles(file: File): String? {
        val fileText = file.bufferedReader().use(BufferedReader::readText)
        var result : String? = null
        if (filterFiles(fileText)) {
            view.console.items.add(view.consolePath + file.toString())
            view.console.items.add("READING FILES...")
            kotlinFiles.add(file)
            view.console.items.add(fileText)
            view.console.items.add("===================================================================")
            result = fileText
        }
        return result
    }

    // filter files for only Views and Controllers
    private fun filterFiles(fileText: String): Boolean =
            !fileText.contains("ApplicationTest()")
                    && !fileText.contains("src/test")
                    && !fileText.contains("@Test")

    fun askUserDialog() {
        view.overlay.fade(Duration.millis(2000.0), .5)
        find(Dialog::class).openModal()
    }

    fun SourceCode.Location.posToString() = "(${start.line}, ${start.col})"

    private fun fileOutputRead(path: Path) {
        val file = File(path.toUri())
        val evaluationConfig = ScriptEvaluationConfiguration {
            constructorArgs.append(file)
        }
        // parse text here
        val scriptSource = readFiles(file)?.toScriptSource()
        if (scriptSource != null) {
            val result = BasicJvmScriptingHost().eval(scriptSource, scriptConfiguration, evaluationConfig)
            val name = file.name
            for (report in result.reports) {
                println(report)
                val severityIndicator = when (report.severity) {
                    ScriptDiagnostic.Severity.FATAL -> "fatal"
                    ScriptDiagnostic.Severity.ERROR -> "e"
                    ScriptDiagnostic.Severity.WARNING -> "w"
                    ScriptDiagnostic.Severity.INFO -> "i"
                    ScriptDiagnostic.Severity.DEBUG -> "d"
                }
                println("$severityIndicator: $name: ${report.location?.posToString()}: ${report.message}")
                report.exception?.printStackTrace()
            }
            println(result)
        }
    }

    // class name, methods,
    private fun consoleLog() {
        // print and format classes
        //
    }
}