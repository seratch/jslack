package com.slack.api.methods.response.rtm;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.*;
import lombok.Data;

import java.util.List;

/**
 * @see <a href="https://api.slack.com/methods/rtm.start">rtm.start</a>
 */
@Data
public class RTMStartResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String url;
    private User self;
    private Team team;
    private List<User> users;
    private Prefs prefs;
    private List<Channel> channels;
    private List<Group> groups;
    private List<Im> ims;

    @Data
    public static class Prefs {
        // TODO
    }
}
