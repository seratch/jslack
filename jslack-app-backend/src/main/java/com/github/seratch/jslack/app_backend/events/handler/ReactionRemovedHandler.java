package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.ReactionRemovedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ReactionRemovedPayload;

public abstract class ReactionRemovedHandler extends EventHandler<ReactionRemovedPayload> {

    @Override
    public String getEventType() {
        return ReactionRemovedEvent.TYPE_NAME;
    }
}
