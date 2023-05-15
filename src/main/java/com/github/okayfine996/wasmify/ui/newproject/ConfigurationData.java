package com.github.okayfine996.wasmify.ui.newproject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationData {
    private String name;
    private String crate;

    private String author;


}
