package com.github.tghnx1.customexecrunner.runconfig

import com.intellij.execution.configurations.*
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon


// simple implementation for task: registers new Run/Debug configuration type
class CustomExecConfigurationType : ConfigurationType {

    override fun getDisplayName() = "Custom Executable"
    override fun getConfigurationTypeDescription() = "Run custom executable"
    override fun getId() = "CUSTOM_EXEC_RUN"
    override fun getConfigurationFactories(): Array<ConfigurationFactory> =
        arrayOf(CustomExecConfigurationFactory(this))
    override fun getIcon(): Icon =
        IconLoader.getIcon("/icons/plugin.svg", javaClass)

}
