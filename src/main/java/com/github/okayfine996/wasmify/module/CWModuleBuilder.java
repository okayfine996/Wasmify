package com.github.okayfine996.wasmify.module;

import com.github.okayfine996.wasmify.ui.newproject.ConfigurationData;
import com.intellij.execution.configuration.EnvironmentVariablesData;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.ide.CliResult;
import com.intellij.ide.CommandLineProcessor;
import com.intellij.ide.CommandLineProcessorResult;
import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rust.cargo.toolchain.BacktraceMode;
import org.rust.cargo.toolchain.CargoCommandLine;
import org.rust.cargo.toolchain.RustChannel;

import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CWModuleBuilder extends ModuleBuilder {

    private ConfigurationData configurationData;

    @Override
    public ModuleType<?> getModuleType() {
        return CWModuleType.getInstance();
    }

    @Override
    public @Nullable ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        CWModuleWizardStep cwModuleWizardStep = new CWModuleWizardStep(context);
        return cwModuleWizardStep;
    }


    @Override
    public void setupRootModel(@NotNull ModifiableRootModel modifiableRootModel) throws ConfigurationException {
        VirtualFile root = doAddContentEntry(modifiableRootModel).getFile();
        String name = modifiableRootModel.getProject().getName();
        String[] command = new String[]{"cargo", "generate", "--git", "https://gitee.com/tainrandai/cw-template.git", "--name", name,"-d","minimal=false","--init"};
        GeneralCommandLine commandLine = new GeneralCommandLine(command);
        commandLine.setWorkDirectory(root.getPath());
        try {
            int code = commandLine.createProcess().waitFor();
            if (code != 0) {
                throw new ConfigurationException("generate from template failed");
            }
        } catch (InterruptedException e) {
            throw new ConfigurationException("generate from template failed");
        } catch (com.intellij.execution.ExecutionException e) {
            throw new ConfigurationException("generate from template failed");
        }
    }

    public void setConfigurationData(ConfigurationData configurationData) {
        this.configurationData = configurationData;
    }


    private void replaceInFile(String filePath, String old, String newStr) throws IOException {
        File fileToBeModified = new File(filePath);
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(fileToBeModified));
            String line = reader.readLine();
            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }
            String newContent = oldContent.replaceAll(old, newStr);
            writer = new FileWriter(fileToBeModified);
            writer.write(newContent);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
