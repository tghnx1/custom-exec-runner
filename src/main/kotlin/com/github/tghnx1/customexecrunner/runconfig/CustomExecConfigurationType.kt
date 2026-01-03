package com.github.tghnx1.customexecrunner.runconfig

import com.intellij.execution.configurations.*

class CustomExecConfigurationType : ConfigurationType {

    override fun getDisplayName() = "Custom Executable"
    override fun getConfigurationTypeDescription() = "Run custom executable"
    override fun getIcon() = null
    override fun getId() = "CUSTOM_EXEC_RUN"

    override fun getConfigurationFactories(): Array<ConfigurationFactory> =
        arrayOf(CustomExecConfigurationFactory(this))
}
