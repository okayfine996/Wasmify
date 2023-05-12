package com.github.okayfine996.wasmify.ui.signer;

import com.github.okayfine996.wasmify.notify.Notifier;
import com.github.okayfine996.wasmify.service.WasmService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.ui.components.JBTextField;
import kotlinx.serialization.StringFormat;

import javax.swing.*;
import java.awt.event.*;

public class AddSignerDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private LabeledComponent value;
    private LabeledComponent name;
    private JBTextField nameTextField = new JBTextField();
    private JBPasswordField valueFiled = new JBPasswordField();

    public AddSignerDialog() {

        name.setComponent(nameTextField);
        value.setComponent(valueFiled);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        WasmService wasmService = ApplicationManager.getApplication().getService(WasmService.class);
        wasmService.addSigner(new WasmService.Signer(nameTextField.getText(), valueFiled.getText()));
        Notifier.notifyInfo(null, String.format("add %s success", nameTextField.getText()));
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
