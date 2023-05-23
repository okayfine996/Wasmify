package com.github.okayfine996.wasmify.ui.contract;

import com.github.okayfine996.wasmify.cmwasm.results.MigrateResult;
import com.github.okayfine996.wasmify.cmwasm.wasm.Fund;
import com.github.okayfine996.wasmify.cmwasm.wasm.msg.MigrateMsg;
import com.github.okayfine996.wasmify.notify.Notifier;
import com.github.okayfine996.wasmify.service.WasmService;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.JBIntSpinner;
import com.intellij.ui.components.IconLabelButton;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.fields.ExpandableTextField;
import icons.SdkIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WasmContract {
    private JPanel rootPanel;
    private LabeledComponent contractLabeledComponent;
    private JButton executeButton;
    private JButton queryButton;
    private ExpandableTextField excuteMsgTextField;
    private JButton migrateButton;
    private LabeledComponent<JBIntSpinner> gasLimitLabel;
    private LabeledComponent signerLabel;
    private LabeledComponent chainName;
    private LabeledComponent<JBTextField> feeLabel;
    private LabeledComponent<TextFieldWithBrowseButton> migrateLabel;
    private IconLabelButton deleteButton;
    private LabeledComponent<JBIntSpinner> fundsLabel;

    private String signer;
    private String contractAddress;
    private String chainNameStr;

    private WasmContractActionListener wasmContractActionListener;

    public WasmContract(String contractAddress, String chainName, String signer) {
        this.contractAddress = contractAddress;
        this.chainNameStr = chainName;
        this.signer = signer;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void createUIComponents() {
        var wasmService = ApplicationManager.getApplication().getService(WasmService.class);
        chainName = new LabeledComponent<>();
        chainName.setComponent(new JBLabel(this.chainNameStr));


        deleteButton = new IconLabelButton(SdkIcons.Sdk_Remove_icon, (jComponent) -> {
            System.out.println();
            return null;
        });

        contractLabeledComponent = new LabeledComponent<>();
        contractLabeledComponent.setComponent(new JBLabel(contractAddress));
        signerLabel = new LabeledComponent<>();
        String[] signerArray = wasmService.getSignerList().stream().map(WasmService.Signer::getName).toArray(String[]::new);
        ComboBox<String> signerCombox = new ComboBox<>(signerArray);
        signerLabel.setComponent(signerCombox);

        excuteMsgTextField = new ExpandableTextField();

        gasLimitLabel = new LabeledComponent<JBIntSpinner>();
        gasLimitLabel.setComponent(new JBIntSpinner(3000000, 21000, 100000000, 1000));
        feeLabel = new LabeledComponent<>();
        feeLabel.setComponent(new JBTextField("0.03"));
        fundsLabel = new LabeledComponent<>();
        fundsLabel.setComponent(new JBIntSpinner(0, 0, Integer.MAX_VALUE, 1));

        migrateLabel = new LabeledComponent<TextFieldWithBrowseButton>();
        TextFieldWithBrowseButton wasmFileBrowseButton = new TextFieldWithBrowseButton();
        wasmFileBrowseButton.addBrowseFolderListener(new TextBrowseFolderListener(new FileChooserDescriptor(true, false, false, false, false, false)));
        wasmFileBrowseButton.setEditable(true);
        migrateLabel.setComponent(wasmFileBrowseButton);

        executeButton = new JButton("EXECUTE");
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wasmContractActionListener != null) {
                    ProgressManager progressManager = ProgressManager.getInstance();
                    Project project = ProjectManager.getInstance().getDefaultProject();
                    progressManager.run(new Task.Backgroundable(project, "execute wasm") {
                        @Override
                        public void run(@NotNull ProgressIndicator indicator) {
                            String fee = feeLabel.getComponent().getText();
                            String gas = gasLimitLabel.getComponent().getNumber() + "";
                            String r = wasmContractActionListener.execute(signer, contractAddress, excuteMsgTextField.getText(), chainNameStr, fee, gas, Collections.emptyList());
                            Notifier.notifyInfo(null, r);
                        }
                    });
                }
            }
        });

        queryButton = new JButton("QUERY");
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wasmContractActionListener != null) {
                    ProgressManager progressManager = ProgressManager.getInstance();
                    Project project = ProjectManager.getInstance().getDefaultProject();
                    progressManager.run(new Task.Backgroundable(project, "query wasm") {
                        @Override
                        public void run(@NotNull ProgressIndicator indicator) {
                            String r = wasmContractActionListener.query(contractAddress, excuteMsgTextField.getText(), chainNameStr);
                            Notifier.notifyInfo(null, r);
                        }
                    });
                }
            }
        });

        migrateButton = new JButton("Migrate");
        migrateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wasmContractActionListener != null) {
                    ProgressManager progressManager = ProgressManager.getInstance();
                    Project project = ProjectManager.getInstance().getDefaultProject();
                    progressManager.run(new Task.Backgroundable(project, "migrate contract") {
                        @Override
                        public void run(@NotNull ProgressIndicator indicator) {
                            String migrateFile = migrateLabel.getComponent().getText();
                            String migrateMsg = excuteMsgTextField.getText();
                            String feeAmout = feeLabel.getComponent().getText();
                            String gas = gasLimitLabel.getComponent().getNumber() + "";
                            int fund = fundsLabel.getComponent().getNumber();
                            MigrateResult result = wasmContractActionListener.migrate(signer, contractAddress, migrateMsg, chainNameStr,migrateFile, feeAmout, gas, fund);
                            if (result == null || !result.isSuccess()) {
                                Notifier.notifyError(null, "Migrate failed");
                            } else {
                                Notifier.notifyInfo(null, result.txHash());
                            }
                        }
                    });
                }
            }
        });
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setWasmContractActionListener(WasmContractActionListener actionListener) {
        if (actionListener != null) {
            this.wasmContractActionListener = actionListener;
        }
    }


    public interface WasmContractActionListener {
        String execute(String signer, String contractAddress, String executeMsg, String chain, String fee, String gas, List<Fund> funds);

        String query(String contractAddress, String queryMsg, String chain);

        MigrateResult migrate(String signer, String contractAddress, String migrateMsg, String chain, String wasmFile, String fee, String gas, int fund);
    }
}
