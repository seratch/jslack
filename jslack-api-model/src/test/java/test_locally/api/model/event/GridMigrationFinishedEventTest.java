package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.GridMigrationFinishedEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GridMigrationFinishedEventTest {

    @Test
    public void typeName() {
        assertThat(GridMigrationFinishedEvent.TYPE_NAME, is("grid_migration_finished"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "        \"type\": \"grid_migration_finished\",\n" +
                "        \"enterprise_id\": \"EXXXXXXXX\"\n" +
                "}";
        GridMigrationFinishedEvent event = GsonFactory.createSnakeCase().fromJson(json, GridMigrationFinishedEvent.class);
        assertThat(event.getType(), is("grid_migration_finished"));
        assertThat(event.getEnterpriseId(), is("EXXXXXXXX"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        GridMigrationFinishedEvent event = new GridMigrationFinishedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"grid_migration_finished\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}