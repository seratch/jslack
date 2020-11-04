package com.slack.api.methods.response.admin.teams.settings;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Team;
import lombok.Data;

@Data
public class AdminTeamsSettingsInfoResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Team team;
}