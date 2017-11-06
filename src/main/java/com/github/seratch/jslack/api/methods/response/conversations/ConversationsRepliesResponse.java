package com.github.seratch.jslack.api.methods.response.conversations;

import java.util.List;

import com.github.seratch.jslack.api.model.Message;
import com.github.seratch.jslack.api.model.ResponseMetadata;

import lombok.Data;

@Data
public class ConversationsRepliesResponse {
    private boolean ok;
    private String warning;
    private String error;

    private List<Message> messages;
    private boolean hasMore;
    private ResponseMetadata responseMetadata;
}
