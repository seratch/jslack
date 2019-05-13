package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.GroupRenameEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GroupRenameEventTest {

    @Test
    public void typeName() {
        assertThat(GroupRenameEvent.TYPE_NAME, is("group_rename"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"group_rename\",\n" +
                "    \"channel\": {\n" +
                "        \"id\":\"G02ELGNBH\",\n" +
                "        \"name\":\"new_name\",\n" +
                "        \"created\":1360782804\n" +
                "    }\n" +
                "}";
        GroupRenameEvent event = GsonFactory.createSnakeCase().fromJson(json, GroupRenameEvent.class);
        assertThat(event.getType(), is("group_rename"));
        assertThat(event.getChannel().getId(), is("G02ELGNBH"));
        assertThat(event.getChannel().getName(), is("new_name"));
        assertThat(event.getChannel().getCreated(), is(1360782804));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        GroupRenameEvent event = new GroupRenameEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"group_rename\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}