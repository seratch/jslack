package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.request.team.TeamAccessLogsRequest;
import com.github.seratch.jslack.api.methods.request.team.TeamBillableInfoRequest;
import com.github.seratch.jslack.api.methods.request.team.TeamInfoRequest;
import com.github.seratch.jslack.api.methods.request.team.TeamIntegrationLogsRequest;
import com.github.seratch.jslack.api.methods.request.team.profile.TeamProfileGetRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersListRequest;
import com.github.seratch.jslack.api.methods.response.team.TeamAccessLogsResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamBillableInfoResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamInfoResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamIntegrationLogsResponse;
import com.github.seratch.jslack.api.methods.response.team.profile.TeamProfileGetResponse;
import com.github.seratch.jslack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class team_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void teamAccessLogs() throws Exception {
        TeamAccessLogsResponse response = slack.methods().teamAccessLogs(TeamAccessLogsRequest.builder()
                .token(token)
                .build());
        if (response.isOk()) {
            // when you pay for this team
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        } else {
            // when you don't pay for this team
            assertThat(response.isOk(), is(false));
            assertThat(response.getError(), is("paid_only"));
        }
    }

    @Test
    public void teamBillableInfo() throws Exception {
        List<User> users = slack.methods().usersList(UsersListRequest.builder().token(token).build()).getMembers();
        User user = null;
        for (User u : users) {
            if (!u.isBot() && !"USLACKBOT".equals(u.getId())) {
                user = u;
                break;
            }
        }
        String userId = user.getId();
        TeamBillableInfoResponse response = slack.methods().teamBillableInfo(TeamBillableInfoRequest.builder()
                .token(token)
                .user(userId)
                .build());
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamInfo() throws Exception {
        TeamInfoResponse response = slack.methods().teamInfo(TeamInfoRequest.builder()
                .token(token)
                .build());
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamIntegrationLogs() throws Exception {
        String user = slack.methods().usersList(UsersListRequest.builder().token(token).build()).getMembers().get(0).getId();
        TeamIntegrationLogsResponse response = slack.methods().teamIntegrationLogs(TeamIntegrationLogsRequest.builder()
                .token(token)
                .user(user)
                .build());
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamProfileGet() throws Exception {
        TeamProfileGetResponse response = slack.methods().teamProfileGet(TeamProfileGetRequest.builder().token(token).build());
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

}