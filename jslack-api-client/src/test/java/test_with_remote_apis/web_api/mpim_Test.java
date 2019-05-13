package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.mpim.*;
import com.github.seratch.jslack.api.methods.request.users.UsersListRequest;
import com.github.seratch.jslack.api.methods.response.mpim.*;
import com.github.seratch.jslack.api.methods.response.users.UsersListResponse;
import com.github.seratch.jslack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class mpim_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void operations() throws IOException, SlackApiException {
        MpimListResponse listResponse = slack.methods().mpimList(MpimListRequest.builder().token(token).build());
        assertThat(listResponse.isOk(), is(true));

        UsersListResponse usersListResponse = slack.methods().usersList(UsersListRequest.builder().token(token).presence(true).build());
        List<User> users = usersListResponse.getMembers();
        List<String> userIds = new ArrayList<>();
        for (User u : users) {
            if (u.isDeleted() == false && u.isRestricted() == false) {
                if (userIds.size() < 3) {
                    userIds.add(u.getId());
                }
            }
        }

        MpimOpenResponse openResponse = slack.methods().mpimOpen(MpimOpenRequest.builder().token(token).users(userIds).build());
        assertThat(openResponse.getError(), is(nullValue()));
        assertThat(openResponse.isOk(), is(true));

        String channelId = openResponse.getGroup().getId();

        MpimMarkResponse markResponse = slack.methods().mpimMark(MpimMarkRequest.builder().token(token).channel(channelId).build());
        assertThat(markResponse.getError(), is(nullValue()));
        assertThat(markResponse.isOk(), is(true));

        MpimHistoryResponse historyResponse = slack.methods().mpimHistory(MpimHistoryRequest.builder().token(token).channel(channelId).count(10).build());
        assertThat(historyResponse.getError(), is(nullValue()));
        assertThat(historyResponse.isOk(), is(true));

        MpimCloseResponse closeResponse = slack.methods().mpimClose(MpimCloseRequest.builder().token(token).channel(channelId).build());
        assertThat(closeResponse.getError(), is(nullValue()));
        assertThat(closeResponse.isOk(), is(true));
    }

}