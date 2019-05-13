package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.ImMarkedEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ImMarkedEventTest {

    @Test
    public void typeName() {
        assertThat(ImMarkedEvent.TYPE_NAME, is("im_marked"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"im_marked\",\n" +
                "    \"channel\": \"D024BE91L\",\n" +
                "    \"ts\": \"1401383885.000061\"\n" +
                "}";
        ImMarkedEvent event = GsonFactory.createSnakeCase().fromJson(json, ImMarkedEvent.class);
        assertThat(event.getType(), is("im_marked"));
        assertThat(event.getChannel(), is("D024BE91L"));
        assertThat(event.getTs(), is("1401383885.000061"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ImMarkedEvent event = new ImMarkedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"im_marked\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}