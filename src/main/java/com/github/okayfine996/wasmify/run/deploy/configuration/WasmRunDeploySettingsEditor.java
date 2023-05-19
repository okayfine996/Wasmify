package com.github.okayfine996.wasmify.run.deploy.configuration;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.components.JBTextField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class WasmRunDeploySettingsEditor extends SettingsEditor<WasmRunDeployConfiguration> {
    private JPanel myPanel;
    private LabeledComponent<JBTextField> extralParameter;

    @Override
    protected void resetEditorFrom(@NotNull WasmRunDeployConfiguration s) {
        s.getOptions().setExtralParameter("");

    }

    @Override
    protected void applyEditorTo(@NotNull WasmRunDeployConfiguration s) throws ConfigurationException {
        s.getOptions().setExtralParameter(extralParameter.getComponent().getText());
    }

    @Override
    protected @NotNull JComponent createEditor() {
        return myPanel;
    }

    private void createUIComponents() {
        extralParameter = new LabeledComponent<>();
        extralParameter.setComponent(new JBTextField());
    }
}
