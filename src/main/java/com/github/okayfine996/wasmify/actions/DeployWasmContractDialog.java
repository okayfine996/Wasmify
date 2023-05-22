package com.github.okayfine996.wasmify.actions;

import com.github.okayfine996.wasmify.service.WasmService;
import com.github.okayfine996.wasmify.tasks.DeployWasmContractTask;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.JBIntSpinner;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.fields.ExpandableTextField;

import javax.swing.*;
import java.awt.event.*;
import java.util.Collections;

public class DeployWasmContractDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private LabeledComponent<ComboBox<String>> Network;
    private LabeledComponent<TextFieldWithBrowseButton> wasmFile;
    private LabeledComponent<ExpandableTextField> initMsg;
    private LabeledComponent<ComboBox<String>> signer;
    private LabeledComponent<JBTextField> feeLabel;
    private LabeledComponent<JBIntSpinner> gasLabel;
    private JBLabel denomLabel;

    private TextFieldWithBrowseButton wasmFileBrowseButton;

    private Project project;
    private String wasmFilePath;

    public DeployWasmContractDialog(Project project, String wasmFilePath) {
        setTitle("Deploy Wasm Contract");
        this.project = project;
        this.wasmFilePath = wasmFilePath;
        this.wasmFileBrowseButton.setText(wasmFilePath);

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
        String fee = this.feeLabel.getComponent().getText();
        String gas = this.gasLabel.getComponent().getNumber() + "";
        ProgressManager.getInstance()
                .run(new DeployWasmContractTask(project, network, wasmFile, initMsg, signer,fee,gas, null));
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {

        WasmService wasmService = ApplicationManager.getApplication().getService(WasmService.class);

        Network = new LabeledComponent<>();
        String[] comboxItems = wasmService.getNetworkList().stream().map(com.github.okayfine996.wasmify.model.Network::getName).toArray(String[]::new);
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
        String[] signerArray = wasmService.getSignerList().stream().map(WasmService.Signer::getName).toArray(String[]::new);
        ComboBox<String> signerCombox = new ComboBox<>(signerArray);
        signer.setComponent(signerCombox);


        feeLabel = new LabeledComponent();
        feeLabel.setComponent(new JBTextField());

        gasLabel = new LabeledComponent();
        gasLabel.setComponent(new JBIntSpinner(200000, 21000,1000000000,10000));
        denomLabel = new JBLabel("OKB");

    }
}
