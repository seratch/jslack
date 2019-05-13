package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.GoodbyeEvent;
import lombok.Data;

import java.util.List;

@Data
public class GoodbyePayload implements EventsApiPayload<GoodbyeEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private GoodbyeEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
