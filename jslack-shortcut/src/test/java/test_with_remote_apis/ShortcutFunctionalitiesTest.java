package test_with_remote_apis;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.SlackShortcut;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsAddResponse;
import com.github.seratch.jslack.api.methods.response.search.SearchAllResponse;
import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.model.Message;
import com.github.seratch.jslack.api.model.block.DividerBlock;
import com.github.seratch.jslack.api.model.block.SectionBlock;
import com.github.seratch.jslack.api.model.block.composition.MarkdownTextObject;
import com.github.seratch.jslack.api.model.block.element.ImageElement;
import com.github.seratch.jslack.shortcut.Shortcut;
import com.github.seratch.jslack.shortcut.model.ApiToken;
import com.github.seratch.jslack.shortcut.model.ChannelId;
import com.github.seratch.jslack.shortcut.model.ChannelName;
import com.github.seratch.jslack.shortcut.model.ReactionName;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class ShortcutFunctionalitiesTest {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    ApiToken token = ApiToken.of(System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN));

    @Test
    public void chatOps() throws IOException, SlackApiException {
        Shortcut shortcut = new SlackShortcut(slack).getInstance(token);
        ChannelName channelName = ChannelName.of("random");

        Optional<ChannelId> channelId = shortcut.findChannelIdByName(channelName);
        assertThat(channelId.isPresent(), is(true));

        Optional<ChannelName> maybeChannelName = shortcut.findChannelNameById(channelId.get());
        assertThat(maybeChannelName.isPresent(), is(true));
        assertThat(maybeChannelName.get(), is(channelName));

        List<Message> messages = shortcut.findRecentMessagesByName(channelName, 10);
        assertThat(messages, is(notNullValue()));

        SearchAllResponse searchResult = shortcut.search("hello");
        assertThat(searchResult, is(notNullValue()));

        ReactionName reactionName = ReactionName.of("smile");
        ReactionsAddResponse addReaction = shortcut.addReaction(messages.get(0), reactionName);
        assertThat(addReaction.getError(), is(nullValue()));
        assertThat(addReaction.isOk(), is(true));
    }

    @Test
    public void postMessage() throws IOException, SlackApiException {
        Shortcut shortcut = new SlackShortcut(slack).getInstance(token);
        Attachment attachment = Attachment.builder().text("text").footer("footer").build();
        List<Attachment> attachments = Arrays.asList(attachment);

        {
            ChatPostMessageResponse response = shortcut.post(ChannelName.of("random"), "hello, hello!");
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            ChatPostMessageResponse response = shortcut.postAsBot(ChannelName.of("random"), "hello, hello!");
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            ChatPostMessageResponse response = shortcut.post(ChannelName.of("random"), "Hi, these are my attachments!", attachments);
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            ChatPostMessageResponse response = shortcut.postAsBot(ChannelName.of("random"), "Hi, these are my attachments!", attachments);
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }

    @Test
    public void postMessage_blocks() throws IOException, SlackApiException {
        Shortcut shortcut = new SlackShortcut(slack).getInstance(token);

        {
            MarkdownTextObject text = MarkdownTextObject.builder()
                    .text("*Ler Ros*\n:star::star::star::star: 2082 reviews\n I would really recommend the  Yum Koh Moo Yang - Spicy lime dressing and roasted quick marinated pork shoulder, basil leaves, chili & rice powder.")
                    .build();
            ImageElement accessory = ImageElement.builder()
                    .imageUrl("https://s3-media2.fl.yelpcdn.com/bphoto/DawwNigKJ2ckPeDeDM7jAg/o.jpg")
                    .altText("alt text for image")
                    .build();

            SectionBlock section = SectionBlock.builder()
                    .text(text)
                    .accessory(accessory)
                    .build();

            DividerBlock divider = new DividerBlock();

            ChatPostMessageResponse response = shortcut.post(ChannelName.of("random"), Arrays.asList(section, divider));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }
}