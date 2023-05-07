package com.github.okayfine996.wasmify.run.deploy.configuration;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.components.JBTextField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class WasmRunDeploySettingsEditor extends SettingsEditor<WasmRunDeployConfiguration> {
    private JPanel myPanel;
    private LabeledComponent<JBTextField> privateKey;
    private LabeledComponent<JBTextField> restApi;
    private LabeledComponent<JBTextField> denom;
    private LabeledComponent instantiateMsg;

    @Override
    protected void resetEditorFrom(@NotNull WasmRunDeployConfiguration s) {

    }

    @Override
    protected void applyEditorTo(@NotNull WasmRunDeployConfiguration s) throws ConfigurationException {
    }

    @Override
    protected @NotNull JComponent createEditor() {
        return myPanel;
    }

    private void createUIComponents() {
        privateKey = new LabeledComponent<>();
        privateKey.setComponent(new JBTextField());
        restApi = new LabeledComponent<>();
        restApi.setComponent(new JBTextField());
        denom = new LabeledComponent<>();
        denom.setComponent(new JBTextField());
        instantiateMsg = new LabeledComponent<>();
        instantiateMsg.setComponent(new JBTextField());

    }

}
