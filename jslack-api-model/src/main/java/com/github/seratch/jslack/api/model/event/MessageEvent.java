package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.model.Message;
import com.github.seratch.jslack.api.model.Reaction;
import com.github.seratch.jslack.api.model.block.LayoutBlock;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * A message is delivered from several sources:
 * <p>
 * - They are sent via the Real Time Messaging API when a message is sent to a channel to which you subscribe.
 * This message should immediately be displayed in the client.
 * - They are returned via calls to channels.history, im.history or groups.history
 * - They are returned as latest property on channel, group and im objects.
 * <p>
 * https://api.slack.com/events/message
 */
@Data
public class MessageEvent implements Event {

    public static final String TYPE_NAME = "message";

    private final String type = TYPE_NAME;
    private String channel;
    private String user;

    private String text;
    private List<LayoutBlock> blocks;
    private List<Attachment> attachments;

    private String ts;
    private String eventTs;
    private String channelType;

    private Edited edited;

    @Data
    public static class Edited {
        private String user;
        private String ts;
    }

    private String subtype;
    private boolean hidden;
    private String deletedTs;

    @SerializedName("is_starred")
    private boolean starred;
    private List<String> pinnedTo;
    private List<Reaction> reactions;

    // bot_message
    private String botId;
    private String username;
    private Message.Icons icons;
}
