package com.github.seratch.jslack.api.model;

import lombok.Data;

import java.util.List;

@Data
public class ErrorMetadata {

    private List<String> messages;

}
