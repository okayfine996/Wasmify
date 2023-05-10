package com.github.okayfine996.wasmify.ui.contract;

import com.github.okayfine996.wasmify.notify.Notifier;
import com.github.okayfine996.wasmify.service.WasmService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.fields.ExpandableTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WasmContract {
    private JPanel rootPanel;
    private LabeledComponent contractLabeledComponent;
    private JButton executeButton;
    private JButton queryButton;
    private ExpandableTextField queryMsgTextField;
    private ExpandableTextField excuteMsgTextField;

    private String signer;
    private String contractAddress;
    private String chainName;

    private WasmContractActionListener wasmContractActionListener = null;

    public WasmContract(String contractAddress, String chainName, String signer) {
        this.contractAddress = contractAddress;
        this.chainName = chainName;
        this.signer = signer;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void createUIComponents() {
        contractLabeledComponent = new LabeledComponent<>();
        contractLabeledComponent.setComponent(new JBLabel(contractAddress));


        executeButton = new JButton("EXECUTE");
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wasmContractActionListener != null) {
                    System.out.println("=================================================");
                    String r = wasmContractActionListener.execute(signer, contractAddress, excuteMsgTextField.getText(), chainName);
                    Notifier.notifyInfo(null, r);
                    System.out.println(r);
                }
            }
        });

        queryButton = new JButton("QUERY");
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wasmContractActionListener != null) {
                    System.out.println("==================================================");
                    String r = wasmContractActionListener.query(contractAddress, queryMsgTextField.getText(), chainName);
                    Notifier.notifyInfo(null, r);
                    System.out.println(r);
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
        String execute(String signer, String contractAddress, String executeMsg, String chain);

        String query(String contractAddress, String queryMsg, String chain);
    }
}
