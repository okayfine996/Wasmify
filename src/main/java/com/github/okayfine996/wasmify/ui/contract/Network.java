package com.github.okayfine996.wasmify.ui.contract;

import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.components.IconLabelButton;
import com.intellij.ui.components.JBLabel;
import icons.SdkIcons;

import javax.swing.*;

public class Network {
    private LabeledComponent name;
    private LabeledComponent chainId;
    private LabeledComponent restRUL;
    private LabeledComponent explorerURL;
    private LabeledComponent denom;
    private JPanel rootPanel;
    private IconLabelButton deleteButton;

    private String nameStr;
    private String chainIdStr;
    private String restUrlStr;
    private String explorerURLStr;
    private String denomStr;

    private OnRemoveNetworkListener onRemoveNetworkListener;

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
        this.deleteButton = new IconLabelButton(SdkIcons.Sdk_Remove_icon,jComponent -> {
            if (onRemoveNetworkListener!=null) {
                onRemoveNetworkListener.OnRemove(nameStr);
            }
            return null;
        });
    }

    public JComponent getContent() {
        return this.rootPanel;
    }



    public void setOnRemoveNetworkListener(OnRemoveNetworkListener listener) {
        this.onRemoveNetworkListener = listener;
    }

    public interface OnRemoveNetworkListener {
        void OnRemove(String network);
    }
}
