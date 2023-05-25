package com.github.okayfine996.wasmify.actions;

import com.alibaba.fastjson.JSON;
import com.github.okayfine996.wasmify.service.Signer;
import com.github.okayfine996.wasmify.service.WasmService;
import com.github.okayfine996.wasmify.tasks.DeployWasmContractTask;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.*;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.JBIntSpinner;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.fields.ExpandableTextField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.function.Supplier;

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

    private ExpandableTextField initMsgTextField;
    private JBTextField feeTextField;

    private Project project;
    private String wasmFilePath;

    public DeployWasmContractDialog(Project project, String wasmFilePath) {
        setTitle("Deploy Wasm Contract");
        this.project = project;
        this.wasmFilePath = wasmFilePath;
        this.wasmFileBrowseButton.setText(wasmFilePath);


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

        initValidator();
    }

    private void onOK() {
        // add your code here
        String network = this.Network.getComponent().getItem();
        String wasmFile = this.wasmFileBrowseButton.getText();
        String initMsg = this.initMsg.getComponent().getText();
        String signer = this.signer.getComponent().getItem();
        String fee = this.feeLabel.getComponent().getText();
        String gas = this.gasLabel.getComponent().getNumber() + "";
        if (StringUtil.isEmpty(network) || StringUtil.isEmpty(wasmFile) || StringUtil.isEmpty(initMsg) || StringUtil.isEmpty(signer) || StringUtil.isEmpty(fee)) {
            return;
        }

        ProgressManager.getInstance().run(new DeployWasmContractTask(project, network, wasmFile, initMsg, signer, fee, gas, null));
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        Network = new LabeledComponent<>();


        wasmFile = new LabeledComponent<>();
        wasmFileBrowseButton = new TextFieldWithBrowseButton();
        wasmFileBrowseButton.addBrowseFolderListener(new TextBrowseFolderListener(new FileChooserDescriptor(true, false, false, false, false, false)));
        wasmFileBrowseButton.setEditable(true);
        wasmFile.setComponent(wasmFileBrowseButton);

        initMsg = new LabeledComponent<>();
        initMsgTextField = new ExpandableTextField();
        initMsgTextField.setEnabled(true);
        initMsg.setComponent(initMsgTextField);

        signer = new LabeledComponent<>();


        feeLabel = new LabeledComponent();
        feeTextField = new JBTextField();
        feeTextField.setPreferredSize(new Dimension(100, 30));
        feeTextField.setMinimumSize(new Dimension(100, 30));
        feeLabel.setComponent(feeTextField);

        gasLabel = new LabeledComponent();
        JBIntSpinner gasSpinner = new JBIntSpinner(200000, 21000, 1000000000, 10000);
        gasSpinner.setMinimumSize(new Dimension(100, 30));
        gasSpinner.setPreferredSize(new Dimension(100, 30));
        gasLabel.setComponent(gasSpinner);
        denomLabel = new JBLabel("");

    }

    private void initView() {
        WasmService wasmService = project.getService(WasmService.class);

        String[] comboxItems = wasmService.getNetworkList().stream().map(com.github.okayfine996.wasmify.model.Network::getName).toArray(String[]::new);
        ComboBox<String> networkCombox = new ComboBox<>(comboxItems);
        networkCombox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                String denom = wasmService.getNetwork((String) e.getItem()).getDenom();
                denomLabel.setText(denom);
            }
        });
        Network.setComponent(networkCombox);
        if (comboxItems.length > 0) {
            networkCombox.setSelectedItem(comboxItems[0]);
        }

        wasmFileBrowseButton.setText(this.wasmFilePath);

        // signer
        String[] signerArray = wasmService.getSignerList().stream().map(Signer::getName).toArray(String[]::new);
        ComboBox<String> signerCombox = new ComboBox<>(signerArray);
        signer.setComponent(signerCombox);

    }

    private void initValidator() {

        new ComponentValidator(project).withValidator(new Supplier<ValidationInfo>() {
            @Override
            public ValidationInfo get() {
                String str = feeTextField.getText();
                if (StringUtil.isNotEmpty(str)) {
                    try {
                        Float.valueOf(str);
                    } catch (Exception e) {
                        return new ValidationInfo("invalid fee", feeTextField);
                    }
                }
                return null;
            }
        }).installOn(feeTextField);
        feeTextField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                ComponentValidator.getInstance(feeTextField).ifPresent(ComponentValidator::revalidate);
            }
        });

        new ComponentValidator(project).withValidator(() -> {
            String str = initMsgTextField.getText();
            if (StringUtil.isNotEmpty(str)) {
                if (!JSON.isValidObject(str)) {
                    return new ValidationInfo("invalid json", initMsgTextField);
                }
            }
            return null;
        }).installOn(initMsgTextField);
        initMsgTextField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                ComponentValidator.getInstance(initMsgTextField).ifPresent(ComponentValidator::revalidate);
            }
        });
    }
}
