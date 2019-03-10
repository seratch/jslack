package com.github.seratch.jslack.api.model.block;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * https://api.slack.com/reference/messaging/blocks#context
 */
@Data
@Builder
public class ContextBlock implements LayoutBlock {
    private final String type = "context";
    @Builder.Default
    private List<ContextBlockElement> elements = new ArrayList<>();
    private String blockId;
}
