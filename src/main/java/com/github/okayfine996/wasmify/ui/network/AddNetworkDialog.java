package com.github.okayfine996.wasmify.ui.network;

import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.components.JBTextField;

import javax.swing.*;
import java.awt.event.*;

public class AddNetworkDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private LabeledComponent<JBTextField> name;
    private LabeledComponent<JBTextField> chainId;
    private LabeledComponent<JBTextField> restURL;
    private LabeledComponent<JBTextField> explorerURL;
    private LabeledComponent<JBTextField> denom;

    public AddNetworkDialog() {
        setTitle("Add Network Config");

        initView();

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
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void initView() {
        name.setComponent(new JBTextField());
        chainId.setComponent(new JBTextField());
        restURL.setComponent(new JBTextField());
        explorerURL.setComponent(new JBTextField());
        denom.setComponent(new JBTextField());
    }
}
