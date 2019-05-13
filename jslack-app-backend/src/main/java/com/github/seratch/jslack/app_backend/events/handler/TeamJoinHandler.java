package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.TeamJoinEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.TeamJoinPayload;

public abstract class TeamJoinHandler extends EventHandler<TeamJoinPayload> {

    @Override
    public String getEventType() {
        return TeamJoinEvent.TYPE_NAME;
    }
}
