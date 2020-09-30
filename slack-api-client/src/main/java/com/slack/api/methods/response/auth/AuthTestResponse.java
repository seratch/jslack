package com.slack.api.methods.response.auth;

import com.slack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class AuthTestResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String url;
    private String team;
    private String user;
    private String botId; // only for bot tokens
    private String teamId;
    private String userId;
    private String enterpriseId;
    private String appId; // only for app-level tokens
    private String appName; // only for app-level tokens
}