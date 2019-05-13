package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.StarAddedEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StarAddedEventTest {

    @Test
    public void typeName() {
        assertThat(StarAddedEvent.TYPE_NAME, is("star_added"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"star_added\",\n" +
                "    \"user\": \"U024BE7LH\",\n" +
                "    \"item\": {\n" +
                "    },\n" +
                "    \"event_ts\": \"1360782804.083113\"\n" +
                "}";
        StarAddedEvent event = GsonFactory.createSnakeCase().fromJson(json, StarAddedEvent.class);
        assertThat(event.getType(), is("star_added"));
        assertThat(event.getUser(), is("U024BE7LH"));
        assertThat(event.getEventTs(), is("1360782804.083113"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        StarAddedEvent event = new StarAddedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"star_added\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}