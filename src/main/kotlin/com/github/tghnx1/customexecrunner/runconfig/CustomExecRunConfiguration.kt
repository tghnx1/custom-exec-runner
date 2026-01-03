package com.github.tghnx1.customexecrunner.runconfig

import com.github.tghnx1.customexecrunner.ui.CustomExecSettingsEditor
import com.intellij.execution.Executor
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.configurations.RuntimeConfigurationError
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializer
import org.jdom.Element

// simple implementation for task:
// stores executable path and arguments entered in Run Configuration UI

enum class ExecMode { RUSTC, CARGO, CUSTOM }

class CustomExecRunConfiguration(
    project: Project,
    factory: CustomExecConfigurationFactory,
    name: String
) : RunConfigurationBase<RunProfileState>(project, factory, name) {

    var mode: ExecMode = ExecMode.RUSTC
    var executablePath: String = ""
    var arguments: String = ""

    fun resolveExecutable(): String {
        return when (mode) {
            ExecMode.RUSTC -> "rustc"
            ExecMode.CARGO -> "cargo"
            ExecMode.CUSTOM -> executablePath
        }
    }

    override fun getConfigurationEditor(): SettingsEditor<out CustomExecRunConfiguration> =
        CustomExecSettingsEditor()

    override fun checkConfiguration() {
        when (mode) {
            ExecMode.CUSTOM -> {
                if (executablePath.isBlank()) {
                    throw RuntimeConfigurationError("Executable is not specified")
                }
            }
            ExecMode.RUSTC, ExecMode.CARGO -> {
                // OK: executable resolved via PATH
            }
        }
    }

    // creates execution state that actually runs the process
    override fun getState(
        executor: Executor,
        environment: ExecutionEnvironment
    ): RunProfileState {
        return CustomExecRunProfileState(environment, this)
    }

    override fun writeExternal(element: Element) {
        super.writeExternal(element)
        XmlSerializer.serializeInto(this, element)
    }

    override fun readExternal(element: Element) {
        super.readExternal(element)
        XmlSerializer.deserializeInto(this, element)
    }
}
