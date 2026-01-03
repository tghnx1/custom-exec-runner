package com.github.tghnx1.customexecrunner.runconfig

import com.intellij.execution.configurations.*

class CustomExecConfigurationFactory(
    type: ConfigurationType
) : ConfigurationFactory(type) {

    override fun createTemplateConfiguration(project: com.intellij.openapi.project.Project)
            : RunConfiguration {
        return CustomExecRunConfiguration(project, this, "Custom Executable")
    }
}
