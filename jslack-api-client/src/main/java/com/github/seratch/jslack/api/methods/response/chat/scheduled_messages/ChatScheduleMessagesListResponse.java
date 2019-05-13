package com.github.seratch.jslack.api.methods.response.chat.scheduled_messages;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class ChatScheduleMessagesListResponse implements SlackApiResponse {

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
        private Integer postAt;
        private Integer dateCreated;
    }

}