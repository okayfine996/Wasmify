package com.github.okayfine996.wasmify.ui.contract;

import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.components.JBLabel;

import javax.swing.*;

public class Network {
    private LabeledComponent name;
    private LabeledComponent chainId;
    private LabeledComponent restRUL;
    private LabeledComponent explorerURL;
    private LabeledComponent denom;
    private JPanel rootPanel;

    private String nameStr;
    private String chainIdStr;
    private String restUrlStr;
    private String explorerURLStr;
    private String denomStr;

    public Network(String name, String chainId, String restRUL, String explorerURL, String denom) {
        this.nameStr = name;
        this.chainIdStr = chainId;
        this.restUrlStr = restRUL;
        this.explorerURLStr = explorerURL;
        this.denomStr = denom;
    }

    private void createUIComponents() {
        this.name = new LabeledComponent<>();
        name.setComponent(new JBLabel(nameStr));
        this.chainId = new LabeledComponent<>();
        chainId.setComponent(new JBLabel(chainIdStr));
        this.restRUL = new LabeledComponent();
        restRUL.setComponent(new JBLabel(restUrlStr));
        this.explorerURL = new LabeledComponent<>();
        explorerURL.setComponent(new JBLabel(explorerURLStr));
        this.denom = new LabeledComponent<>();
        denom.setComponent(new JBLabel(denomStr));
    }

    public JComponent getContent() {
        return this.rootPanel;
    }
}
