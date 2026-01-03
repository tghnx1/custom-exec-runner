package com.github.tghnx1.customexecrunner.runconfig

import com.intellij.execution.ExecutionResult
import com.intellij.execution.Executor
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.runners.ProgramRunner
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.DefaultExecutionResult
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.runners.ExecutionEnvironment

// responsible for starting OS process and attaching console output
class CustomExecRunProfileState(
    private val environment: ExecutionEnvironment,
    private val configuration: CustomExecRunConfiguration
) : RunProfileState {

    override fun execute(
        executor: Executor,
        runner: ProgramRunner<*>
    ): ExecutionResult {

        val cmd = GeneralCommandLine(configuration.resolveExecutable())
            .withParameters(
                configuration.arguments
                    .split(" ")
                    .filter { it.isNotBlank() }
            )
            .withWorkDirectory(environment.project.basePath)

        val handler = OSProcessHandler(cmd)

        val console: ConsoleView =
            TextConsoleBuilderFactory.getInstance()
                .createBuilder(environment.project)
                .console

        console.attachToProcess(handler)
        handler.startNotify()

        return DefaultExecutionResult(console, handler)
    }
}
