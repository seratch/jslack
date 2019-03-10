package com.github.seratch.jslack.api.model.block.element;

import com.github.seratch.jslack.api.model.block.composition.ConfirmationDialogObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/messaging/block-elements#datepicker
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatePickerElement extends BlockElement {
	private final String type = "datepicker";
	private String actionId;
	private PlainTextObject placeholder;
	private String initialDate;
	private ConfirmationDialogObject confirm;
}
