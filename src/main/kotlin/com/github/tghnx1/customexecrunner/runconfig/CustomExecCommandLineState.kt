package com.github.tghnx1.customexecrunner.runconfig

import com.intellij.execution.DefaultExecutionResult
import com.intellij.execution.ExecutionResult
import com.intellij.execution.Executor
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.runners.ProgramRunner
import com.intellij.util.execution.ParametersListUtil
import java.nio.file.Files
import java.nio.file.Path

class CustomExecCommandLineState(
    environment: ExecutionEnvironment,
    private val config: CustomExecRunConfiguration
) : CommandLineState(environment) {

    override fun startProcess(): ProcessHandler {
        val exe = resolveExecutable()
        val args = ParametersListUtil.parse(config.arguments)

        val cmd = GeneralCommandLine(exe)
            .withParameters(args)
            .withWorkDirectory(environment.project.basePath)

        return OSProcessHandler(cmd)
    }

    override fun execute(executor: Executor, runner: ProgramRunner<*>): ExecutionResult {
        val handler = startProcess()

        val console = TextConsoleBuilderFactory.getInstance()
            .createBuilder(environment.project)
            .console

        console.attachToProcess(handler)
        return DefaultExecutionResult(console, handler)
    }

    private fun resolveExecutable(): String {
        return when (config.mode) {
            ExecMode.RUSTC -> "rustc"
            ExecMode.CARGO -> "cargo"
            ExecMode.CUSTOM -> {
                val p = config.executablePath.trim()
                val path = Path.of(p)
                if (!Files.exists(path)) error("Executable does not exist: $p")
                p
            }
        }
    }
}
