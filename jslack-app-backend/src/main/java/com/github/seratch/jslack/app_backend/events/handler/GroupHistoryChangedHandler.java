package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.GroupHistoryChangedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.GroupHistoryChangedPayload;

public abstract class GroupHistoryChangedHandler extends EventHandler<GroupHistoryChangedPayload> {

    @Override
    public String getEventType() {
        return GroupHistoryChangedEvent.TYPE_NAME;
    }
}
