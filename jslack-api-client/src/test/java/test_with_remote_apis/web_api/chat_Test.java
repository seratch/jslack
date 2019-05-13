package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.*;
import com.github.seratch.jslack.api.methods.request.chat.*;
import com.github.seratch.jslack.api.methods.request.chat.scheduled_messages.ChatScheduleMessagesListRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsHistoryRequest;
import com.github.seratch.jslack.api.methods.response.channels.*;
import com.github.seratch.jslack.api.methods.response.chat.*;
import com.github.seratch.jslack.api.methods.response.chat.scheduled_messages.ChatScheduleMessagesListResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsHistoryResponse;
import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.model.Channel;
import com.github.seratch.jslack.api.model.Message;
import com.github.seratch.jslack.api.model.block.ImageBlock;
import com.github.seratch.jslack.api.model.block.LayoutBlock;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@Slf4j
public class chat_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void channels_threading() throws IOException, SlackApiException {

        String channelId = null;
        ChannelsListResponse channelsListResponse = slack.methods().channelsList(ChannelsListRequest.builder()
                .token(token)
                .excludeArchived(true)
                .limit(100).build());
        assertThat(channelsListResponse.getError(), is(nullValue()));
        for (Channel channel : channelsListResponse.getChannels()) {
            if (channel.getName().equals("random")) {
                channelId = channel.getId();
                break;
            }
        }
        assertThat(channelId, is(notNullValue()));

        ChatPostMessageResponse firstMessageCreation = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .channel(channelId)
                .token(token)
                .text("[thread] This is a test message posted by unit tests for jslack library")
                .replyBroadcast(false)
                .build());
        assertThat(firstMessageCreation.getError(), is(nullValue()));
        assertThat(firstMessageCreation.isOk(), is(true));

        ChatPostMessageResponse reply1 = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .channel(channelId)
                .token(token)
                .asUser(false)
                .text("replied")
                .iconEmoji(":smile:")
                .threadTs(firstMessageCreation.getTs())
                //.replyBroadcast(true)
                .build());
        assertThat(reply1.getError(), is(nullValue()));
        assertThat(reply1.isOk(), is(true));

        ChatGetPermalinkResponse permalink = slack.methods().chatGetPermalink(ChatGetPermalinkRequest.builder()
                .token(token)
                .channel(channelId)
                .messageTs(reply1.getTs())
                .build());
        assertThat(permalink.getError(), is(nullValue()));
        assertThat(permalink.isOk(), is(true));
        assertThat(permalink.getPermalink(), is(notNullValue()));

        ChatPostMessageResponse reply2 = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .channel(channelId)
                .token(token)
                .asUser(true)
                .text("replied to " + permalink.getPermalink())
                .threadTs(reply1.getTs())
                .unfurlLinks(true)
                .replyBroadcast(true)
                .build());
        assertThat(reply2.isOk(), is(true));

        // Ideally, this message must contain an attachment which shows the preview for reply1
        // however, in this timing, Slack API doesn't return any attachments.
        assertThat(reply2.getMessage().getAttachments(), is(nullValue()));

        // via channels.history
        {
            ChannelsHistoryResponse history = slack.methods().channelsHistory(ChannelsHistoryRequest.builder()
                    .token(token)
                    .channel(channelId)
                    .count(20)
                    .build());
            assertThat(history.isOk(), is(true));

            Message firstReply = history.getMessages().get(1);
            assertThat(firstReply.getType(), is("message"));
            assertThat(firstReply.getSubtype(), is("bot_message"));
            assertThat(firstReply.getAttachments(), is(nullValue()));
            assertThat(firstReply.getRoot(), is(nullValue()));

            Message latestMessage = history.getMessages().get(0);
            assertThat(latestMessage.getType(), is("message"));
            assertThat(latestMessage.getSubtype(), is("thread_broadcast"));

            // NOTE: the following assertions can fail due to Slack API's unstable response
            // this message must contain an attachment which shows the preview for reply1
            // TODO: as of 2018/05, these assertions fail.
//            assertThat(latestMessage.getAttachments(), is(notNullValue()));
//            assertThat(latestMessage.getAttachments().size(), is(1));
//            assertThat(latestMessage.getRoot(), is(notNullValue()));
//            assertThat(latestMessage.getRoot().getReplies().size(), is(2));
//            assertThat(latestMessage.getRoot().getReplyCount(), is(2));
        }

        // via conversations.history
        {
            ConversationsHistoryResponse history = slack.methods().conversationsHistory(ConversationsHistoryRequest.builder()
                    .token(token)
                    .channel(channelId)
                    .limit(20)
                    .build());
            assertThat(history.isOk(), is(true));

            Message firstReply = history.getMessages().get(1);
            assertThat(firstReply.getType(), is("message"));
            assertThat(firstReply.getSubtype(), is("bot_message"));
            assertThat(firstReply.getAttachments(), is(nullValue()));
            assertThat(firstReply.getRoot(), is(nullValue()));

            Message latestMessage = history.getMessages().get(0);
            assertThat(latestMessage.getType(), is("message"));
            assertThat(latestMessage.getSubtype(), is("thread_broadcast"));

            // NOTE: the following assertions can fail due to Slack API's unstable response
            // this message must contain an attachment which shows the preview for reply1
            // TODO: as of August 2018, these assertions fail.
//            assertThat(latestMessage.getAttachments(), is(notNullValue()));
//            assertThat(latestMessage.getAttachments().size(), is(1));
//            assertThat(latestMessage.getRoot(), is(notNullValue()));
//            assertThat(latestMessage.getRoot().getReplies().size(), is(2));
//            assertThat(latestMessage.getRoot().getReplyCount(), is(2));
        }
    }

    @Test
    public void chat_getPermalink() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        ChannelsListResponse channels = slack.methods().channelsList(ChannelsListRequest.builder()
                .token(token)
                .excludeArchived(true)
                .build());
        assertThat(channels.getError(), is(nullValue()));
        assertThat(channels.isOk(), is(true));

        String channelId = channels.getChannels().get(0).getId();

        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .channel(channelId)
                .token(token)
                .text("Hi, this is a test message from jSlack library's unit tests")
                .linkNames(true)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.isOk(), is(true));

        ChatGetPermalinkResponse permalink = slack.methods().chatGetPermalink(ChatGetPermalinkRequest.builder()
                .token(token)
                .channel(channelId)
                .messageTs(postResponse.getTs())
                .build());
        assertThat(permalink.getError(), is(nullValue()));
        assertThat(permalink.isOk(), is(true));
        assertThat(permalink.getPermalink(), is(notNullValue()));
    }

    @Test
    public void channels_chat() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

        {
            ChannelsListResponse response = slack.methods().channelsList(
                    ChannelsListRequest.builder().token(token).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getChannels(), is(notNullValue()));
        }

        ChannelsCreateResponse creationResponse = slack.methods().channelsCreate(
                ChannelsCreateRequest.builder().token(token).name("test" + System.currentTimeMillis()).build());
        assertThat(creationResponse.getError(), is(nullValue()));
        assertThat(creationResponse.isOk(), is(true));
        assertThat(creationResponse.getChannel(), is(notNullValue()));

        Channel channel = creationResponse.getChannel();

        try {
            {
                ChannelsInfoResponse response = slack.methods().channelsInfo(
                        ChannelsInfoRequest.builder().token(token).channel(channel.getId()).build());
                assertThat(response.isOk(), is(true));
                Channel fetchedChannel = response.getChannel();
                assertThat(fetchedChannel.isMember(), is(true));
                assertThat(fetchedChannel.isGeneral(), is(false));
                assertThat(fetchedChannel.isArchived(), is(false));
            }

            {
                ChannelsSetPurposeResponse response = slack.methods().channelsSetPurpose(
                        ChannelsSetPurposeRequest.builder().token(token).channel(channel.getId()).purpose("purpose").build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsSetTopicResponse response = slack.methods().channelsSetTopic(
                        ChannelsSetTopicRequest.builder().token(token).channel(channel.getId()).topic("topic").build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsHistoryResponse response = slack.methods().channelsHistory(
                        ChannelsHistoryRequest.builder().token(token).channel(channel.getId()).count(10).build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsHistoryResponse history = slack.methods().channelsHistory(
                        ChannelsHistoryRequest.builder().token(token).channel(channel.getId()).count(1).build());
                String threadTs = history.getMessages().get(0).getTs();
                ChannelsRepliesResponse response = slack.methods().channelsReplies(
                        ChannelsRepliesRequest.builder().token(token).channel(channel.getId()).threadTs(threadTs).build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChatUnfurlResponse unfurlResponse = slack.methods().chatUnfurl(ChatUnfurlRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .unfurls("http://www.example.com/")
                        .build());
                // TODO: valid test
                assertThat(unfurlResponse.isOk(), is(false));
                assertThat(unfurlResponse.getError(), is("missing_ts"));
            }

            {
                ChannelsKickResponse response = slack.methods().channelsKick(ChannelsKickRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .user(channel.getMembers().get(0))
                        .build());
                // TODO: valid test
                assertThat(response.isOk(), is(false));
                assertThat(response.getError(), is("cant_kick_self"));
            }

            {
                ChannelsInviteResponse response = slack.methods().channelsInvite(ChannelsInviteRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .user(channel.getMembers().get(0))
                        .build());
                // TODO: valid test
                assertThat(response.isOk(), is(false));
                assertThat(response.getError(), is("cant_invite_self"));
            }

            {
                ChatMeMessageResponse response = slack.methods().chatMeMessage(ChatMeMessageRequest.builder()
                        .channel(channel.getId())
                        .token(token)
                        .text("Hello World! via chat.meMessage API")
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            //--------------Edited test---------
            Message lastMessage = slack.methods().conversationsHistory(
                    ConversationsHistoryRequest.builder()
                            .token(token)
                            .channel(channel.getId())
                            .limit(1)
                            .build()
            ).getMessages().get(0);

            ChatUpdateResponse updateMessage = slack.methods().chatUpdate(ChatUpdateRequest.builder()
                    .channel(channel.getId())
                    .token(token)
                    .ts(lastMessage.getTs())
                    .text("Updated text" + lastMessage.getText())
                    .build());
            assertThat(updateMessage.getError(), is(nullValue()));
            assertThat(updateMessage.isOk(), is(true));

            ConversationsHistoryResponse conversationsHistoryResponse = slack.methods().conversationsHistory(
                    ConversationsHistoryRequest.builder()
                            .token(token)
                            .channel(channel.getId())
                            .limit(1)
                            .build()
            );
            assertFalse(conversationsHistoryResponse.getMessages().isEmpty());
            assertNotNull(conversationsHistoryResponse.getMessages().get(0).getEdited());
            assertNotEquals(conversationsHistoryResponse.getMessages().get(0).getEdited().getTs(), lastMessage.getTs());
            //--------------------

            {
                ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                        .channel(channel.getId())
                        .token(token)
                        .text("@seratch Hello World! via chat.postMessage API")
                        .linkNames(true)
                        .build());
                assertThat(postResponse.getError(), is(nullValue()));
                assertThat(postResponse.isOk(), is(true));

                ChatPostMessageResponse replyResponse1 = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                        .channel(channel.getId())
                        .token(token)
                        .text("@seratch Replied via chat.postMessage API")
                        .linkNames(true)
                        .threadTs(postResponse.getTs())
                        //.replyBroadcast(false)
                        .build());
                assertThat(replyResponse1.getError(), is(nullValue()));
                assertThat(replyResponse1.isOk(), is(true));

                ChatPostMessageResponse replyResponse2 = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                        .channel(channel.getId())
                        .token(token)
                        .text("@seratch Replied via chat.postMessage API")
                        .linkNames(true)
                        .threadTs(postResponse.getTs())
                        .replyBroadcast(true)
                        .build());
                assertThat(replyResponse2.getError(), is(nullValue()));
                assertThat(replyResponse2.isOk(), is(true));

                ChatUpdateResponse updateResponse = slack.methods().chatUpdate(ChatUpdateRequest.builder()
                        .channel(channel.getId())
                        .token(token)
                        .ts(postResponse.getTs())
                        .text("Updated text")
                        .linkNames(false)
                        .build());
                assertThat(updateResponse.getError(), is(nullValue()));
                assertThat(updateResponse.isOk(), is(true));

                ChannelsMarkResponse markResponse = slack.methods().channelsMark(ChannelsMarkRequest.builder()
                        //.token(token)
                        .channel(channel.getId())
                        .ts(updateMessage.getTs())
                        .build());
                assertThat(markResponse.getError(), is("invalid_auth"));
                assertThat(markResponse.isOk(), is(false));

                markResponse = slack.methods().channelsMark(ChannelsMarkRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .ts(updateMessage.getTs())
                        .build());
                assertThat(markResponse.getError(), is(nullValue()));
                assertThat(markResponse.isOk(), is(true));

                ChatDeleteResponse deleteResponse = slack.methods().chatDelete(ChatDeleteRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .ts(postResponse.getMessage().getTs())
                        .build());
                assertThat(deleteResponse.getError(), is(nullValue()));
                assertThat(deleteResponse.isOk(), is(true));
            }

            // scheduled messages
            {
                ChatScheduleMessagesListResponse listResponse = slack.methods().chatScheduleMessagesListMessage(
                        ChatScheduleMessagesListRequest.builder()
                                .token(token)
                                .limit(10)
                                .channel(channel.getId())
                                .build());
                assertThat(listResponse.getError(), is(nullValue()));
                int initialScheduledMessageCount = listResponse.getScheduledMessages().size();

                int postAt = (int) ((new Date().getTime() / 1000) + 180);

                ChatScheduleMessageResponse postResponse = slack.methods().chatScheduleMessage(
                        ChatScheduleMessageRequest.builder()
                                .token(token)
                                .channel(channel.getId())
                                .text("Something is happening!")
                                .postAt(postAt) // will be posted in 3 minutes
                                .build());
                assertThat(postResponse.getError(), is(nullValue()));

                assertNumOfScheduledMessages(token, channel, initialScheduledMessageCount + 1);
                deleteScheduledMessage(token, channel, postResponse);

                postResponse = slack.methods().chatScheduleMessage(
                        ChatScheduleMessageRequest.builder()
                                .token(token)
                                .channel(channel.getId())
                                .attachments(Arrays.asList(Attachment.builder().text("something is happening!").build()))
                                .postAt(postAt) // will be posted in 3 minutes
                                .build());
                assertThat(postResponse.getError(), is(nullValue()));

                assertNumOfScheduledMessages(token, channel, initialScheduledMessageCount + 1);
                deleteScheduledMessage(token, channel, postResponse);

                postResponse = slack.methods().chatScheduleMessage(
                        ChatScheduleMessageRequest.builder()
                                .token(token)
                                .channel(channel.getId())
                                .blocks(Arrays.asList((LayoutBlock) ImageBlock.builder()
                                        .blockId("123")
                                        .altText("alt")
                                        .title(PlainTextObject.builder().text("title").build())
                                        .imageUrl("https://a.slack-edge.com/4a5c4/marketing/img/meta/slack_hash_256.png")
                                        .build()))
                                .postAt(postAt) // will be posted in 3 minutes
                                .build());
                assertThat(postResponse.getError(), is(nullValue()));

                deleteScheduledMessage(token, channel, postResponse);
                assertNumOfScheduledMessages(token, channel, initialScheduledMessageCount);
            }

            {
                ChannelsLeaveResponse response = slack.methods().channelsLeave(ChannelsLeaveRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }
            {
                ChannelsJoinResponse response = slack.methods().channelsJoin(ChannelsJoinRequest.builder()
                        .token(token)
                        .name(channel.getName())
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsRenameResponse response = slack.methods().channelsRename(ChannelsRenameRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .name(channel.getName() + "-1")
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

        } finally {
            {
                ChannelsArchiveResponse response = slack.methods().channelsArchive(ChannelsArchiveRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsUnarchiveResponse response = slack.methods().channelsUnarchive(ChannelsUnarchiveRequest.builder()
                        .token(token)
                        //.channel(channel.getId())
                        .build());
                assertThat(response.getError(), is(notNullValue()));

                response = slack.methods().channelsUnarchive(ChannelsUnarchiveRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsArchiveResponse response = slack.methods().channelsArchive(ChannelsArchiveRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsInfoResponse response = slack.methods().channelsInfo(
                        ChannelsInfoRequest.builder().token(token).channel(channel.getId()).build());
                assertThat(response.isOk(), is(true));
                Channel fetchedChannel = response.getChannel();
                assertThat(fetchedChannel.isMember(), is(false));
                assertThat(fetchedChannel.isGeneral(), is(false));
                assertThat(fetchedChannel.isArchived(), is(true));
            }
        }
    }

    private void assertNumOfScheduledMessages(String token, Channel channel, int i) throws IOException, SlackApiException {
        ChatScheduleMessagesListResponse listResponse;
        listResponse = slack.methods().chatScheduleMessagesListMessage(
                ChatScheduleMessagesListRequest.builder()
                        .token(token)
                        .limit(10)
                        .channel(channel.getId())
                        .build());
        assertThat(listResponse.getError(), is(nullValue()));
        assertThat(listResponse.getScheduledMessages().size(), is(i));
    }

    private ChatDeleteScheduledMessageResponse deleteScheduledMessage(String token, Channel channel, ChatScheduleMessageResponse postResponse) throws IOException, SlackApiException {
        ChatDeleteScheduledMessageResponse deleteResponse = slack.methods().chatDeleteScheduledMessage(
                ChatDeleteScheduledMessageRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .scheduledMessageId(postResponse.getScheduledMessageId())
                        .build());
        assertThat(deleteResponse.getError(), is(nullValue()));
        return deleteResponse;
    }

    // It's also possible to post a message by giving the name of a channel.
    //
    // https://api.slack.com/methods/chat.postMessage#channels
    // You can either pass the channel's name (#general) or encoded ID (C024BE91L),
    // and the message will be posted to that channel.
    // The channel's ID can be retrieved through the channels.list API method.
    //
    // https://github.com/slackapi/python-slackclient/blob/master/README.md#sending-a-message-to-slack
    //  In our examples, we specify the channel name, however it is recommended to use the channel_id where possible.
    @Test
    public void postingWithChannelName() throws IOException, SlackApiException {
        makeSureIfGivingChannelNameWorks("random");
        makeSureIfGivingChannelNameWorks("#random");
    }

    private void makeSureIfGivingChannelNameWorks(String channelName) throws IOException, SlackApiException {
        ChatPostMessageResponse response = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(token)
                .channel(channelName)
                .text("Hello!")
                .build());

        assertThat(response.getError(), is(nullValue()));
        assertThat(response.getChannel(), is(startsWith("C")));
        assertThat(response.getMessage().getText(), is(startsWith("Hello!")));
    }

}