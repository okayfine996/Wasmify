package com.github.okayfine996.wasmify.ui.newproject;

import javax.swing.*;

public class NewProject {
    private JTextField nameTextField;
    private JPanel createUI;
    private JTextField crateTextField;
    private JList templateList;
    private JTextField authorTextField;


    public JPanel getCreateUI() {
        return createUI;
    }

    public ConfigurationData getData() {
        return new ConfigurationData();
//        return new ConfigurationData(nameTextField.getText(),crateTextField.getText(),authorTextField.getText());
    }
}
