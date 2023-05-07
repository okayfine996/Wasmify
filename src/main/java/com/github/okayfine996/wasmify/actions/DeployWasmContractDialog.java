package com.github.okayfine996.wasmify.actions;

import com.github.okayfine996.wasmify.listener.WasmServiceListener;
import com.github.okayfine996.wasmify.service.WasmService;
import com.github.okayfine996.wasmify.services.MyProjectService;
import com.github.okayfine996.wasmify.toolWindow.WasmToolWindow;
import com.intellij.ide.FrameStateListener;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ComponentManager;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.fields.ExpandableTextField;
import com.intellij.ui.content.ContentManager;
import com.intellij.util.messages.MessageBus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeployWasmContractDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private LabeledComponent<ComboBox<String>> Network;
    private LabeledComponent<TextFieldWithBrowseButton> wasmFile;
    private LabeledComponent<ExpandableTextField> initMsg;
    private LabeledComponent<JBTextField> account;

    private TextFieldWithBrowseButton wasmFileBrowseButton;

    private Project project;
    private String wasmFilePath;

    public DeployWasmContractDialog(Project project,String wasmFilePath) {
        this.project = project;
        this.wasmFilePath = wasmFilePath;
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

        String network = this.Network.getComponent().getItem();
        String wasmFile = this.wasmFileBrowseButton.getText();
        String initMsg = this.initMsg.getComponent().getText();
        String account = this.account.getComponent().getText();
        var contractAddress = wasmService.deployWasmContract(network,wasmFile,account,initMsg);

        if (contractAddress != null) {
            MessageBus messageBus = this.project.getMessageBus();
            WasmServiceListener publisher = messageBus.syncPublisher(WasmServiceListener.TOPIC);
            publisher.deployWasmEvent(contractAddress);

            ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Wasmify");
            toolWindow.getContentManager().getContent(0);

        }



        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        Network = new LabeledComponent<>();
        ComboBox<String> networkCombox = new ComboBox<>(new String[]{"okb-local","OKC2"});
        Network.setComponent(networkCombox);

        wasmFile = new LabeledComponent<>();
        wasmFileBrowseButton = new TextFieldWithBrowseButton();
        wasmFileBrowseButton.addBrowseFolderListener(new TextBrowseFolderListener(new FileChooserDescriptor(true,false,false,false,false,false)));
        wasmFileBrowseButton.setEditable(true);
        wasmFileBrowseButton.setText(this.wasmFilePath);

        wasmFile.setComponent(wasmFileBrowseButton);

        initMsg = new LabeledComponent<>();
        ExpandableTextField initMsgTextField = new ExpandableTextField();
        initMsgTextField.setEnabled(true);
        initMsg.setComponent(initMsgTextField);

        account = new LabeledComponent<>();
        JBTextField accountTextField = new JBTextField();
        accountTextField.setEnabled(true);
        account.setComponent(accountTextField);
    }
}
