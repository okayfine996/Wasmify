package com.github.okayfine996.wasmify.module;

import com.github.okayfine996.wasmify.notify.Notifier;
import com.github.okayfine996.wasmify.run.deploy.configuration.WasmRunDeploySettingsEditor;
import com.github.okayfine996.wasmify.ui.newproject.ConfigurationData;
import com.github.okayfine996.wasmify.ui.newproject.NewProject;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.ide.wizard.CommitStepException;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.components.ActionLink;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.panels.HorizontalLayout;
import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.commons.cli.CommandLine;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.rust.cargo.toolchain.tools.Cargo;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class CWModuleWizardStep extends ModuleWizardStep {

    private WizardContext context;
    private NewProject newProject = new NewProject();

    public CWModuleWizardStep(WizardContext context) {
        this.context = context;
    }

    @Override
    public JComponent getComponent() {
        var ui = newProject.getCreateUI();
        return ui;
    }

    @Override
    public void updateDataModel() {
        ConfigurationData data = newProject.getData();
        if (context.getProjectBuilder() instanceof CWModuleBuilder) {
            ((CWModuleBuilder) context.getProjectBuilder()).setConfigurationData(data);
        }
    }




    @Override
    public boolean validate() throws ConfigurationException {
        if (!checkInstallCargoGenerate()){
            Messages.showMessageDialog("Cargo Generate Ins't Installed", "", Messages.getErrorIcon());
           return false;
        }
        return true;
    }


    @Override
    public String getName() {
        return super.getName();
    }

    public boolean checkInstallCargoGenerate() {
        int code = -1;
        try {
            code = Runtime.getRuntime().exec(new String[]{System.getProperty("user.home")+"/.cargo/bin/cargo", "generate", "-V"}).waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return code == 0;
    }
}
