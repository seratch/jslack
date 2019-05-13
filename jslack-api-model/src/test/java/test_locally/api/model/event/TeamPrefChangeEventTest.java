package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.TeamPrefChangeEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TeamPrefChangeEventTest {

    @Test
    public void typeName() {
        assertThat(TeamPrefChangeEvent.TYPE_NAME, is("team_pref_change"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"team_pref_change\",\n" +
                "    \"name\": \"slackbot_responses_only_admins\",\n" +
                "    \"value\": true\n" +
                "}";
        TeamPrefChangeEvent event = GsonFactory.createSnakeCase().fromJson(json, TeamPrefChangeEvent.class);
        assertThat(event.getType(), is("team_pref_change"));
        assertThat(event.getName(), is("slackbot_responses_only_admins"));
        assertThat(event.getValue(), is("true"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TeamPrefChangeEvent event = new TeamPrefChangeEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"team_pref_change\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}