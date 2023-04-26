package com.github.okayfine996.wasmify.run.deploy.configuration;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class WasmRunDeploySettingsEditor extends SettingsEditor<WasmRunDeployConfiguration> {
    private JPanel myPanel;
    private LabeledComponent<TextFieldWithBrowseButton> myScriptName;

    @Override
    protected void resetEditorFrom(@NotNull WasmRunDeployConfiguration s) {
        myScriptName.getComponent().setText(s.getScriptName());
    }

    @Override
    protected void applyEditorTo(@NotNull WasmRunDeployConfiguration s) throws ConfigurationException {
        s.setScriptName(myScriptName.getComponent().getText());
    }

    @Override
    protected @NotNull JComponent createEditor() {
        return myPanel;
    }

    private void createUIComponents() {
        myScriptName = new LabeledComponent<>();
        TextFieldWithBrowseButton browseButton = new TextFieldWithBrowseButton();
        browseButton.setEnabled(true);

        myScriptName.setComponent(browseButton);

        browseButton.addBrowseFolderListener(new TextBrowseFolderListener(new FileChooserDescriptor(true,true,true,true,true,false)));
    }

}
