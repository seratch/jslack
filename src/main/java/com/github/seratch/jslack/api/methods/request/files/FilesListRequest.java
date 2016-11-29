package com.github.seratch.jslack.api.methods.request.files;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FilesListRequest implements SlackApiRequest {

	private String token;
	private String user;
	private String channel;
	private String tsFrom;
	private String tsTo;
	private List<String> types;
	private Integer count;
	private Integer page;
}