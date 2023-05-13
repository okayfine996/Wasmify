package com.github.okayfine996.wasmify.ui.signer;

import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPasswordField;

import javax.swing.*;

public class SignerCell {
    private LabeledComponent name;
    private LabeledComponent value;
    private JPanel rootPanel;

    public SignerCell(String name,String value) {
        this.name.setComponent(new JBLabel(name));
        JBPasswordField jbPasswordField = new JBPasswordField();
        jbPasswordField.setText(value);
        jbPasswordField.setEditable(false);
        this.value.setComponent(jbPasswordField);
    }


    public JComponent getContent() {
        return this.rootPanel;
    }
}