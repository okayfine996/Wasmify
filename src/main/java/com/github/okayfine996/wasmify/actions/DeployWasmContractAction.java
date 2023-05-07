package com.github.okayfine996.wasmify.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DeployWasmContractAction extends AnAction {
    private static final String EXTENSION_NAME = "wasm";

    @Override
    public void update(@NotNull AnActionEvent e) {
        String extension = getFileExtension(e.getDataContext());
        e.getPresentation().setEnabledAndVisible(extension != null && extension.equals(EXTENSION_NAME));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
//        Project currentProject = event.getProject();
//        StringBuilder message =
//                new StringBuilder(event.getPresentation().getText() + " Selected!");
//        // If an element is selected in the editor, add info about it.
//        Navigatable selectedElement = event.getData(CommonDataKeys.NAVIGATABLE);
//        if (selectedElement != null) {
//            message.append("\nSelected Element: ").append(selectedElement);
//        }
//        String title = event.getPresentation().getDescription();
//        Messages.showMessageDialog(
//                currentProject,
//                message.toString(),
//                title,
//                Messages.getInformationIcon());
        String wasmFilePath = getSelectFile(event.getDataContext());
        DeployWasmContractDialog dialog = new DeployWasmContractDialog(event.getProject(),wasmFilePath);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        int width = 400;
        int height = 300;
        dialog.setBounds((dimension.width - width) / 2, (dimension.height - height) / 2, width, height);
        dialog.pack();
        dialog.show();

    }

    public String getFileExtension(DataContext dataContext) {
        VirtualFile virtualFile = PlatformDataKeys.VIRTUAL_FILE.getData(dataContext);
        return virtualFile == null ? null : virtualFile.getExtension();
    }

    public String getSelectFile(DataContext dataContext) {
        VirtualFile virtualFile = PlatformDataKeys.VIRTUAL_FILE.getData(dataContext);
        return virtualFile.getPath();
    }
}
