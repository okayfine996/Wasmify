package com.github.okayfine996.wasmify.run.deploy.configuration;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.jshell.LaunchJShellConsoleAction;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessHandlerFactory;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsActions;
import com.intellij.openapi.util.io.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class WasmRunDeployConfiguration extends LocatableConfigurationBase<WasmRunDeployConfigurationOption> {
    protected WasmRunDeployConfiguration(@NotNull Project project, @Nullable ConfigurationFactory factory, @Nullable String name) {
        super(project, factory, name);
    }

    @Override
    public @NotNull SettingsEditor<? extends com.intellij.execution.configurations.RunConfiguration> getConfigurationEditor() {
        return new WasmRunDeploySettingsEditor();
    }

    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        return new CommandLineState(environment) {
            @NotNull
            @Override
            protected ProcessHandler startProcess() throws ExecutionException {
                String optimizeCmd = "docker run --rm -v" + environment.getProject().getBasePath() + ":/code"+ " --mount"+ " type=volume,source=registry_cache,target=/usr/local/cargo/registry"+" cosmwasm/rust-optimizer:0.12.13";
                GeneralCommandLine commandLine = new GeneralCommandLine("bash","/tmp/wasmbuild.sh");
                File file = new File("/tmp/wasmbuild.sh");
                try {
                    FileUtil.writeToFile(file,"#/bin/bash\ncargo wasm\n"+optimizeCmd);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                commandLine.withInput(file);


                if (!StringUtils.isEmpty(getOptions().getExtralParameter())) {
                    commandLine.addParameter(getOptions().getExtralParameter());
                }
                commandLine.setWorkDirectory(environment.getProject().getBasePath());
                OSProcessHandler processHandler = ProcessHandlerFactory.getInstance().createColoredProcessHandler(commandLine);
                ProcessTerminatedListener.attach(processHandler);
                return processHandler;
            }

            @Override
            protected AnAction @NotNull [] createActions(ConsoleView console, ProcessHandler processHandler) {
                return super.createActions(console, processHandler);
            }
        };
    }


    @Override
    protected @NotNull WasmRunDeployConfigurationOption getOptions() {
        return (WasmRunDeployConfigurationOption) super.getOptions();
    }
}
