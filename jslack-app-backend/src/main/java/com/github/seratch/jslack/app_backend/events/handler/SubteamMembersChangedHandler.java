package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.SubteamMembersChangedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.SubteamMembersChangedPayload;

public abstract class SubteamMembersChangedHandler extends EventHandler<SubteamMembersChangedPayload> {

    @Override
    public String getEventType() {
        return SubteamMembersChangedEvent.TYPE_NAME;
    }
}
