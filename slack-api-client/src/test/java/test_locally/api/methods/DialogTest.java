package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.model.dialog.Dialog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class DialogTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void test() throws Exception {
        assertThat(slack.methods(ValidToken).dialogOpen(r -> r.triggerId("xxx").dialogAsString("{}"))
                .isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).dialogOpen(r -> r.triggerId("xxx").dialogAsString("{}"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).dialogOpen(r -> r.triggerId("xxx").dialog(Dialog.builder().build()))
                .get().isOk(), is(true));
    }

}
