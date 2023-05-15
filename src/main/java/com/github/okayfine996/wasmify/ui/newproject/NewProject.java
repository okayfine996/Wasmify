package com.github.okayfine996.wasmify.ui.newproject;

import javax.swing.*;

public class NewProject {
    private JPanel createUI;
    private JList templateList;


    public JPanel getCreateUI() {
        return createUI;
    }

    public ConfigurationData getData() {
        return new ConfigurationData();
//        return new ConfigurationData(nameTextField.getText(),crateTextField.getText(),authorTextField.getText());
    }
}
