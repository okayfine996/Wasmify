package com.github.okayfine996.wasmify.run.wasmbuild.configuration;

import com.github.okayfine996.wasmify.run.deploy.configuration.WasmRunDeployConfigurationOption;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessHandlerFactory;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.util.execution.ParametersListUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class WasmBuildConfiguration extends RunConfigurationBase<WasmBuildConfiguration> {


    protected WasmBuildConfiguration(@NotNull Project project, @Nullable ConfigurationFactory factory, @Nullable String name) {
        super(project, factory, name);
    }


    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        return new CommandLineState(environment) {
            @Override
            protected @NotNull ProcessHandler startProcess() throws ExecutionException {
                String cmd = "cargo wasm --lib";

                GeneralCommandLine commandLine = new GeneralCommandLine(ParametersListUtil.parse(cmd));
                commandLine.setWorkDirectory(environment.getProject().getBasePath());
                OSProcessHandler processHandler = ProcessHandlerFactory.getInstance().createColoredProcessHandler(commandLine);
                ProcessTerminatedListener.attach(processHandler);

                return processHandler;
            }
        };
    }

    @Override
    public @NotNull SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new SettingsEditor<RunConfiguration>() {
            @Override
            protected void resetEditorFrom(@NotNull RunConfiguration s) {

            }

            @Override
            protected void applyEditorTo(@NotNull RunConfiguration s) throws ConfigurationException {

            }

            @Override
            protected @NotNull JComponent createEditor() {
                return new JPanel();
            }
        };
    }

    @Override
    protected @NotNull WasmRunDeployConfigurationOption getOptions() {
        return (WasmRunDeployConfigurationOption)super.getOptions();
    }
}