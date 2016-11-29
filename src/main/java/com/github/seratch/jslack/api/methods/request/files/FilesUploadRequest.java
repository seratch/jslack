package com.github.seratch.jslack.api.methods.request.files;

import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.List;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

@Data
@Builder
public class FilesUploadRequest implements SlackApiRequest {

    private String token;
    private File file;
    private String content;
    private String filetype;
    private String filename;
    private String title;
    private String initialComment;
    private List<String> channels;
}