package com.github.tghnx1.customexecrunner.runconfig

import com.intellij.execution.ExecutionResult
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.DefaultExecutionResult
import com.intellij.openapi.project.Project
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.filters.TextConsoleBuilderFactory

class CustomExecRunProfileState(
    private val executable: String,
    private val args: String
) : RunProfileState {

    override fun execute(
        executor: com.intellij.execution.Executor?,
        runner: com.intellij.execution.runners.ProgramRunner<*>
    ): ExecutionResult {

        val commandLine = GeneralCommandLine(executable)
            .withParameters(args.split(" ").filter { it.isNotBlank() })

        val handler: ProcessHandler = OSProcessHandler(commandLine)

        val console: ConsoleView =
            TextConsoleBuilderFactory.getInstance()
                .createBuilder(null as Project)
                .console

        console.attachToProcess(handler)
        handler.startNotify()

        return DefaultExecutionResult(console, handler)
    }
}
