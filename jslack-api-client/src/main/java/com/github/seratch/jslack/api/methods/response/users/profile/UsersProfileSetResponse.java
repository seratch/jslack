package com.github.seratch.jslack.api.methods.response.users.profile;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.User;
import lombok.Data;

@Data
public class UsersProfileSetResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private User.Profile profile;
    private String username;
}