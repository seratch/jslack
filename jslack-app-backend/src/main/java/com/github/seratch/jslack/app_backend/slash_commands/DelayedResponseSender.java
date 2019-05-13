package com.github.seratch.jslack.app_backend.slash_commands;

import com.github.seratch.jslack.api.webhook.WebhookResponse;
import com.github.seratch.jslack.app_backend.slash_commands.payload.SlashCommandPayload;
import com.github.seratch.jslack.app_backend.slash_commands.response.SlashCommandResponse;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import okhttp3.Response;

import java.io.IOException;

public class DelayedResponseSender {

    private final SlackHttpClient httpClient;

    public DelayedResponseSender() {
        this(new SlackHttpClient());
    }

    public DelayedResponseSender(SlackHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public WebhookResponse send(SlashCommandPayload payload, SlashCommandResponse response) throws IOException {
        Response httpResponse = httpClient.postJsonPostRequest(payload.getResponseUrl(), response);
        String body = httpResponse.body().string();
        httpClient.runHttpResponseListeners(httpResponse, body);

        return WebhookResponse.builder()
                .code(httpResponse.code())
                .message(httpResponse.message())
                .body(body)
                .build();
    }
}
