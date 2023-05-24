package com.github.okayfine996.wasmify.ui.signer;

import com.github.okayfine996.wasmify.cmwasm.core.Signer;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.components.IconLabelButton;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPasswordField;
import icons.SdkIcons;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class SignerCell implements ClipboardOwner {
    private LabeledComponent name;
    private LabeledComponent<JBPasswordField> value;
    private JPanel rootPanel;
    private LabeledComponent<JBLabel> addressLabeledComponent;
    private IconLabelButton addressCopy;
    private IconLabelButton valueCopy;
    private IconLabelButton deleteButton;

    private OnRemoveListener onRemoveListener;

    public SignerCell(String name, String value) {
        this.name.setComponent(new JBLabel(name));
        JBPasswordField jbPasswordField = new JBPasswordField();
        jbPasswordField.setText(value);
        jbPasswordField.setEditable(false);
        this.value.setComponent(jbPasswordField);
        this.addressLabeledComponent.setComponent(new JBLabel(new Signer(value).getHexAddress()));
    }


    public JComponent getContent() {
        return this.rootPanel;
    }

    private void createUIComponents() {

        deleteButton = new IconLabelButton(SdkIcons.Sdk_Remove_icon, (jComponent) -> {
            if (onRemoveListener != null) {
                onRemoveListener.onRemove(this.name.getText());
            }
            return null;
        });

        addressCopy = new IconLabelButton(AllIcons.Actions.Copy, jComponent -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection stringSelection = new StringSelection(addressLabeledComponent.getComponent().getText());
            clipboard.setContents(stringSelection, SignerCell.this);
            return null;
        });


        valueCopy = new IconLabelButton(AllIcons.Actions.Copy, jComponent -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection stringSelection = new StringSelection(value.getComponent().getText());
            clipboard.setContents(stringSelection, SignerCell.this);
            return null;
        });
    }


    public void setOnRemoveListener(OnRemoveListener onRemoveListener) {
        this.onRemoveListener = onRemoveListener;
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }


    public interface OnRemoveListener {
        void onRemove(String name);
    }
}
