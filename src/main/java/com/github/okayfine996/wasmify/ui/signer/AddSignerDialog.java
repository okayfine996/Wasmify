package com.github.okayfine996.wasmify.ui.signer;

import com.github.okayfine996.wasmify.notify.Notifier;
import com.github.okayfine996.wasmify.service.WasmService;
import com.github.okayfine996.wasmify.toolWindow.WasmToolWindowFactory;
import com.github.okayfine996.wasmify.toolWindow.WasmToolWindowSignerPanel;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.ComponentValidator;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.ui.components.JBTextField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddSignerDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private LabeledComponent value;
    private LabeledComponent name;
    private JBTextField nameTextField = new JBTextField();
    private JBPasswordField valueFiled = new JBPasswordField();

    private Project project;

    public AddSignerDialog(Project project) {
        setTitle("Add Signer");
        this.project = project;
        name.setComponent(nameTextField);
        value.setComponent(valueFiled);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        new ComponentValidator(ProjectManager.getInstance().getDefaultProject()).withValidator(v -> {
            String str = valueFiled.getText();
            if (StringUtil.isNotEmpty(str)) {
                if (!isVaildPrivateKeyOrMnemonic(str)){
                    v.updateInfo(new ValidationInfo("invalid privateKey or mnemonic", valueFiled));
                }
            } else {
                v.updateInfo(null);
            }
        }).installOn(valueFiled);
        valueFiled.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                ComponentValidator.getInstance(valueFiled).ifPresent(v -> v.revalidate());
            }
        });

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
        if (!vailidate()) {
            return;
        }
        WasmService wasmService = project.getService(WasmService.class);
        wasmService.addSigner(new com.github.okayfine996.wasmify.service.Signer(nameTextField.getText(), valueFiled.getText()));
        Notifier.notifyInfo(project, String.format("add %s success", nameTextField.getText()));
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Wasmify");
        WasmToolWindowSignerPanel signerPanel = (WasmToolWindowSignerPanel) toolWindow.getContentManager().findContent("Signer").getComponent();
        signerPanel.updateList();
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public boolean vailidate() {
        String value = valueFiled.getText();
        String name = nameTextField.getText();
        if (StringUtil.isNotEmpty(name)&&StringUtil.isNotEmpty(value)&&isVaildPrivateKeyOrMnemonic(value)) {
            return true;
        }
        return false;
    }

    public boolean isVaildPrivateKeyOrMnemonic(String str) {
        if (str.contains(" ")) {
            if (str.split(" ").length == 12) {
                return true;
            }else {
                return false;
            }
        }


        String pattern = "[a-fA-F0-9]{64}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }else {
            return false;
        }
    }
}
