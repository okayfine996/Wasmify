package com.github.okayfine996.wasmify.ui.contract;

import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.components.JBLabel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WasmContract {
    private JPanel rootPanel;
    private LabeledComponent contractLabeledComponent;
    private JButton executeButton;
    private JButton queryButton;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void createUIComponents() {
        contractLabeledComponent = new LabeledComponent<>();
        contractLabeledComponent.setComponent(new JBLabel("0x00000000000000000000"));
        queryButton = new JButton("QUERY");
        queryButton.setEnabled(true);;
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e);
            }
        });
    }
}
