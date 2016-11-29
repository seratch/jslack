package com.github.seratch.jslack.api.methods.request.usergroups;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsergroupsListRequest implements SlackApiRequest {

    private String token;
    private Integer includeDisabled;
    private Integer includeCount;
    private Integer includeUsers;
}