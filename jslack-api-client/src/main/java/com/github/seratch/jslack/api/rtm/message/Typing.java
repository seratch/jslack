package com.github.seratch.jslack.api.rtm.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/events/user_typing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Typing implements RTMMessage {

    public static final String TYPE_NAME = "typing";

    private Long id;
    private final String type = TYPE_NAME;
    private String channel;

    // For Android compatibility, avoided to use default implementation in RTMMessage
    @Override
    public String toJSONString() {
        return RTMMessageToJSONString.toJSON(this);
    }
}
