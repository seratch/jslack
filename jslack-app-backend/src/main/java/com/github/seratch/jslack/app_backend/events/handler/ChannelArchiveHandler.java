package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.ChannelArchiveEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ChannelArchivePayload;

public abstract class ChannelArchiveHandler extends EventHandler<ChannelArchivePayload> {

    @Override
    public String getEventType() {
        return ChannelArchiveEvent.TYPE_NAME;
    }
}
