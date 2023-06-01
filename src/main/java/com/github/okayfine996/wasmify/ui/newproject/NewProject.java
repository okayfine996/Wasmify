package com.github.okayfine996.wasmify.ui.newproject;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.ui.components.ActionLink;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;

public class NewProject {
    private JPanel createUI;
    private JList templateList;
    private ActionLink installLink;


    public NewProject() {
    }

    public JPanel getCreateUI() {
        return createUI;
    }

    public ConfigurationData getData() {
        return new ConfigurationData();
//        return new ConfigurationData(nameTextField.getText(),crateTextField.getText(),authorTextField.getText());
    }

    private void createUIComponents() {
        installLink = new ActionLink("Install cargo-generate using Cargo", actionEvent -> {
            Task.Modal modal = new Task.Modal(null, "Installing cargo-generate", true) {
                @Override
                public void run(@NotNull ProgressIndicator indicator) {
                    indicator.setIndeterminate(true);
//                    RsMacToolchainFlavor

//                    GeneralCommandLine commandLine = new GeneralCommandLine("cargo", "install", "cargo-generate");

//                    commandLine.setExePath("~/.cargo/bin/cargo");
                    try {


                        Process process =  Runtime.getRuntime().exec(System.getProperty("user.home")+"/.cargo/bin/cargo",new String[]{"install", "cargo-generate"});
                        int code = process.waitFor();
                        if (code == 0) {
                            installLink.setVisible(false);
                        }else  {
                            throw new RuntimeException("install failed " + code);
                        }
                    }  catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            modal.queue();
        });
        if (checkInstallCargoGenerate()) {
            installLink.setVisible(false);
        }
    }

    private boolean checkInstallCargoGenerate() {
        int code = -1;
        try {
            code = Runtime.getRuntime().exec(new String[]{System.getProperty("user.home")+"/.cargo/bin/cargo", "generate", "-V"}).waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return code == 0;
    }
}
