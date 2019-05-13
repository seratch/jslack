package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.GoodbyeEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.GoodbyePayload;

public abstract class GoodbyeHandler extends EventHandler<GoodbyePayload> {

    @Override
    public String getEventType() {
        return GoodbyeEvent.TYPE_NAME;
    }
}
