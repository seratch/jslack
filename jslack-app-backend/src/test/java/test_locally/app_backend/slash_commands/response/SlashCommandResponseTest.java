package test_locally.app_backend.slash_commands.response;

import com.github.seratch.jslack.app_backend.slash_commands.response.SlashCommandResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SlashCommandResponseTest {

    @Test
    public void test() {
        SlashCommandResponse response = new SlashCommandResponse();
        response.setResponseType("ephemeral");
        assertThat(response.getResponseType(), is("ephemeral"));
    }

}