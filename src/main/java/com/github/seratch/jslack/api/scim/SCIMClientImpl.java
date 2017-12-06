package com.github.seratch.jslack.api.scim;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.scim.request.UsersDeleteRequest;
import com.github.seratch.jslack.api.scim.response.UsersDeleteResponse;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class SCIMClientImpl implements SCIMClient {
    private String endpointUrlPrefix = "https://api.slack.com/scim/v1/Users";

    private final SlackHttpClient slackHttpClient;

    public SCIMClientImpl(SlackHttpClient slackHttpClient) {
        this.slackHttpClient = slackHttpClient;
    }

    @Override
    public void setEndpointUrlPrefix(String endpointUrlPrefix) {
        this.endpointUrlPrefix = endpointUrlPrefix;
    }

    @Override
    public UsersDeleteResponse delete(UsersDeleteRequest req) throws IOException, SlackApiException {
        Request.Builder request = new Request.Builder().url(endpointUrlPrefix + "/" + req.getId()).addHeader("Authorization", "Bearer " + req.getToken());
        Response response = slackHttpClient.delete(request.method("DELETE", null).build());
        return SlackHttpClient.buildJsonResponse(response, UsersDeleteResponse.class);
    }

}
