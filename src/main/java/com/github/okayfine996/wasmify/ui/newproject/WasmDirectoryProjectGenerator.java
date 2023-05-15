package com.github.okayfine996.wasmify.ui.newproject;

import com.intellij.icons.AllIcons;
import com.intellij.ide.util.projectWizard.AbstractNewProjectStep;
import com.intellij.ide.util.projectWizard.CustomStepProjectGenerator;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.impl.welcomeScreen.AbstractActionWithPanel;
import com.intellij.platform.DirectoryProjectGenerator;
import com.intellij.platform.DirectoryProjectGeneratorBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class WasmDirectoryProjectGenerator extends DirectoryProjectGeneratorBase<ConfigurationData> implements CustomStepProjectGenerator<ConfigurationData> {
    @Override
    public @NotNull @NlsContexts.Label String getName() {
        return "Wasm";
    }

    @Override
    public @Nullable Icon getLogo() {
        return AllIcons.Toolbar.Locale;
    }

    @Override
    public void generateProject(@NotNull Project project, @NotNull VirtualFile baseDir, @NotNull ConfigurationData settings, @NotNull Module module) {

    }

    @Override
    public AbstractActionWithPanel createStep(DirectoryProjectGenerator<ConfigurationData> projectGenerator, AbstractNewProjectStep.AbstractCallback<ConfigurationData> callback) {
        return null;
    }
}
