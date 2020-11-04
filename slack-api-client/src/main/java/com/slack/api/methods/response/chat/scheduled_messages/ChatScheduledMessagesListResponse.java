package com.slack.api.methods.response.chat.scheduled_messages;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class ChatScheduledMessagesListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<ScheduledMessage> scheduledMessages;
    private ResponseMetadata responseMetadata;

    @Data
    public static class ScheduledMessage {
        private String id;
        private String channelId;
        private String text;
        private Integer postAt;
        private Integer dateCreated;
    }

}