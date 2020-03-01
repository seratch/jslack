package test_locally;

import com.slack.api.bolt.context.SayUtility;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.junit.Test;

import java.io.IOException;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SayUtilityTest {

    @Test
    public void test() throws IOException, SlackApiException {
        MethodsClient client = mock(MethodsClient.class);
        ChatPostMessageResponse response = new ChatPostMessageResponse();
        response.setOk(true);
        response.setTs("123.123");
        when(client.chatPostMessage(any(ChatPostMessageRequest.class))).thenReturn(response);
        SayUtility util = new SayUtility() {
            @Override
            public String getChannelId() {
                return "C123";
            }

            @Override
            public MethodsClient client() {
                return client;
            }
        };
        {
            ChatPostMessageResponse result = util.say("hello!");
            assertTrue(result.isOk());
            assertEquals("123.123", result.getTs());
        }
        {
            ChatPostMessageResponse result = util.say(asBlocks(
                    section(section -> section.text(markdownText("*Please select a restaurant:*"))),
                    divider(),
                    actions(actions -> actions
                            .elements(asElements(
                                    button(b -> b.text(plainText(pt -> pt.emoji(true).text("Farmhouse"))).value("v1")),
                                    button(b -> b.text(plainText(pt -> pt.emoji(true).text("Kin Khao"))).value("v2"))
                            ))
                    )
            ));
            assertTrue(result.isOk());
            assertEquals("123.123", result.getTs());
        }
    }
}
