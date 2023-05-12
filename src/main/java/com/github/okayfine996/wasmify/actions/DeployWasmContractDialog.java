package com.github.okayfine996.wasmify.actions;

import com.github.okayfine996.wasmify.listener.WasmServiceListener;
import com.github.okayfine996.wasmify.service.WasmService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.components.fields.ExpandableTextField;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.*;

public class DeployWasmContractDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private LabeledComponent<ComboBox<String>> Network;
    private LabeledComponent<TextFieldWithBrowseButton> wasmFile;
    private LabeledComponent<ExpandableTextField> initMsg;
    private LabeledComponent<ComboBox<String>> signer;

    private TextFieldWithBrowseButton wasmFileBrowseButton;

    private Project project;
    private String wasmFilePath;

    public DeployWasmContractDialog(Project project, String wasmFilePath) {
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
        String network = this.Network.getComponent().getItem();
        String wasmFile = this.wasmFileBrowseButton.getText();
        String initMsg = this.initMsg.getComponent().getText();
        String signer = this.signer.getComponent().getItem();
        ProgressManager.getInstance().run(new DeployWasmContractTask(project,network,wasmFile,initMsg, signer));
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        Network = new LabeledComponent<>();
        WasmService wasmService = ApplicationManager.getApplication().getService(WasmService.class);
        String[] comboxItems = wasmService.getNetworkList().stream().map(com.github.okayfine996.wasmify.model.Network::getChainId).toArray(String[]::new);
        ComboBox<String> networkCombox = new ComboBox<>(comboxItems);

        Network.setComponent(networkCombox);

        wasmFile = new LabeledComponent<>();
        wasmFileBrowseButton = new TextFieldWithBrowseButton();
        wasmFileBrowseButton.addBrowseFolderListener(new TextBrowseFolderListener(new FileChooserDescriptor(true, false, false, false, false, false)));
        wasmFileBrowseButton.setEditable(true);
        wasmFileBrowseButton.setText(this.wasmFilePath);

        wasmFile.setComponent(wasmFileBrowseButton);

        initMsg = new LabeledComponent<>();
        ExpandableTextField initMsgTextField = new ExpandableTextField();
        initMsgTextField.setEnabled(true);
        initMsg.setComponent(initMsgTextField);

        signer = new LabeledComponent<>();
        String[] signerArray= wasmService.getSignerList().stream().map(WasmService.Signer::getName).toArray(String[]::new);
        ComboBox<String> signerCombox = new ComboBox<>(signerArray);
        signer.setComponent(signerCombox);
    }


    class DeployWasmContractTask extends Task.Backgroundable {
        private static final String TASK_NAME = "deploy wasm";
        private Project project;
        private String network;
        private String wasmFile;
        private String initMsg;
        private String signer;

        public DeployWasmContractTask(@Nullable Project project, String network, String wasmFile, String initMsg, String signer) {
            super(project, TASK_NAME);
            this.project = project;
            this.network = network;
            this.wasmFile = wasmFile;
            this.initMsg = initMsg;
            this.signer = signer;

        }


        @Override
        public void run(@NotNull ProgressIndicator indicator) {
            WasmService wasmService = ApplicationManager.getApplication().getService(WasmService.class);
            var contractAddress = wasmService.deployWasmContract(network, wasmFile, signer, initMsg);

            if (contractAddress != null) {
                MessageBus messageBus = this.project.getMessageBus();
                WasmServiceListener publisher = messageBus.syncPublisher(WasmServiceListener.TOPIC);
                publisher.deployWasmEvent(signer, contractAddress, network);
            }
        }
    }
}
