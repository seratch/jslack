
package com.github.seratch.jslack;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.dialog.DialogOpenRequest;
import com.github.seratch.jslack.api.methods.response.dialog.DialogOpenResponse;
import com.github.seratch.jslack.api.model.dialog.Dialog;
import com.github.seratch.jslack.api.model.dialog.DialogTextElement;
import com.github.seratch.jslack.api.model.dialog.SubType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Slack_dialogs_Test {

    Slack slack = new Slack();
    String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");
    
    @Test
    public void open() throws IOException, SlackApiException {
        
        DialogTextElement quanityTextElement = DialogTextElement.builder()
        		.subtype(SubType.NUMBER)
        		.label("Quantity")
        		.name("quantity")
        		.hint("The number you need")
        		.maxLength(3)
        		.minLength(1)
        		.placeholder("Required quantity")
        		.value("1")
        		.build();

        Dialog dialog = Dialog.builder()
        		.title("Request pens")
        		.callbackId("pens-1122")
        		.elements(Arrays.asList(quanityTextElement))
        		.submitLabel("")
        		.build();
        
        DialogOpenResponse dialogOpenResponse = slack.methods().dialogOpen(DialogOpenRequest.builder()
        		.token(token)
        		.triggerId("FAKE_TRIGGER_ID")
        		.dialog(dialog)
        		.build());
        assertThat(dialogOpenResponse.isOk(), is(false));
        	assertThat(dialogOpenResponse.getError(), is("invalid_trigger"));
    }
}
