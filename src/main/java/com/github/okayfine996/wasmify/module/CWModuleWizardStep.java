package com.github.okayfine996.wasmify.module;

import com.github.okayfine996.wasmify.run.deploy.configuration.WasmRunDeploySettingsEditor;
import com.github.okayfine996.wasmify.ui.newproject.ConfigurationData;
import com.github.okayfine996.wasmify.ui.newproject.NewProject;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.ide.wizard.CommitStepException;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.panels.HorizontalLayout;
import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.uiDesigner.core.Spacer;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class CWModuleWizardStep extends ModuleWizardStep {

    private WizardContext context;
    private NewProject newProject = new NewProject();

    public CWModuleWizardStep(WizardContext context) {
        this.context = context;
    }

    @Override
    public JComponent getComponent() {
        return newProject.getCreateUI();
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
        return super.validate();
    }


    @Override
    public String getName() {
        return super.getName();
    }


}
