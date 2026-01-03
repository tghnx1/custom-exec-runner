package com.github.tghnx1.customexecrunner.runconfig

import com.intellij.execution.Executor
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializer
import org.jdom.Element

enum class ExecMode { RUSTC, CARGO, CUSTOM }

class CustomExecRunConfiguration(
    project: Project,
    factory: CustomExecConfigurationFactory,
    name: String
) : RunConfigurationBase<RunProfileState>(project, factory, name) {

    var mode: ExecMode = ExecMode.RUSTC
    var executablePath: String = ""   // используется только в CUSTOM
    var arguments: String = ""

    override fun getConfigurationEditor(): SettingsEditor<out CustomExecRunConfiguration> =
        CustomExecSettingsEditor()

    override fun checkConfiguration() {
        if (mode == ExecMode.CUSTOM && executablePath.isBlank()) {
            error("Executable path is empty (Custom executable mode).")
        }
    }

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
        return CustomExecCommandLineState(environment, this)
    }

    // В новых версиях безопаснее так:
    override fun writeExternal(element: Element) {
        super.writeExternal(element)
        XmlSerializer.serializeInto(this, element)
    }

    override fun readExternal(element: Element) {
        super.readExternal(element)
        XmlSerializer.deserializeInto(this, element)
    }
}
