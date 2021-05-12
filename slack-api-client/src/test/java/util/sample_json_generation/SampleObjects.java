package util.sample_json_generation;

import com.google.gson.JsonElement;
import com.slack.api.model.*;
import com.slack.api.model.block.*;
import com.slack.api.model.block.composition.*;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.ImageElement;
import com.slack.api.model.block.element.PlainTextInputElement;
import com.slack.api.model.block.element.RadioButtonsElement;
import com.slack.api.util.json.GsonFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;
import static util.ObjectInitializer.initProperties;

public class SampleObjects {

    private SampleObjects() {
    }

    public static class Json {
        private Json() {
        }

        public static List<JsonElement> Attachments = Arrays.asList(
                GsonFactory.createSnakeCase().toJsonTree(SampleObjects.Attachments.get(0))
        );

        public static List<JsonElement> Blocks = Arrays.asList(
                GsonFactory.createSnakeCase().toJsonTree(initProperties(
                        ActionsBlock.builder().elements(BlockElements).build())),
                GsonFactory.createSnakeCase().toJsonTree(initProperties(
                        ContextBlock.builder().elements(ContextBlockElements).build())),
                GsonFactory.createSnakeCase().toJsonTree(initProperties(
                        DividerBlock.builder().build())),
                GsonFactory.createSnakeCase().toJsonTree(initProperties(
                        ImageBlock.builder().build())),
                GsonFactory.createSnakeCase().toJsonTree(initProperties(
                        SectionBlock.builder()
                                .accessory(initProperties(ImageElement.builder().build()))
                                .text(TextObject)
                                .fields(SectionBlockFieldElements)
                                .build()))
        );
    }

    public static List<Attachment> Attachments = Arrays.asList(
            initProperties(Attachment.builder()
                    .fields(Arrays.asList(initProperties(Field.builder().build())))
                    .actions(Arrays.asList(initProperties(Action.builder()
                            .optionGroups(Arrays.asList(initProperties(Action.OptionGroup.builder().build())))
                            .options(Arrays.asList(initProperties(Action.Option.builder().build())))
                            .selectedOptions(Arrays.asList(initProperties(Action.Option.builder().build())))
                            .build())))
                    .mrkdwnIn(Arrays.asList(""))
                    .build())
    );

    public static TextObject TextObject = initProperties(PlainTextObject.builder().build());

    public static OptionObject Option = initProperties(OptionObject.builder()
            .text(TextObject)
            .description(PlainTextObject.builder().build())
            .build());

    public static ConfirmationDialogObject Confirm = ConfirmationDialogObject.builder().text(TextObject).build();

    public static List<BlockElement> BlockElements = asElements(
            initProperties(button(b -> b.confirm(Confirm))),
            initProperties(channelsSelect(c -> c.confirm(Confirm))),
            initProperties(conversationsSelect(c -> c.confirm(Confirm))),
            initProperties(datePicker(d -> d.confirm(Confirm))),
            initProperties(timePicker(d -> d.confirm(Confirm))),
            initProperties(externalSelect(e -> e.initialOption(Option).confirm(Confirm))),
            initProperties(com.slack.api.model.block.element.BlockElements.image(i -> i)),
            initProperties(overflowMenu(o -> o.confirm(Confirm))),
            initProperties(staticSelect(s -> s.initialOption(Option).confirm(Confirm))),
            initProperties(usersSelect(u -> u.confirm(Confirm)))
    );
    public static List<ContextBlockElement> ContextBlockElements = asContextElements(
            initProperties(ImageElement.builder().build())
    );
    public static List<TextObject> SectionBlockFieldElements = asSectionFields(
            initProperties(plainText(pt -> pt)),
            initProperties(markdownText(m -> m))
    );
    public static List<LayoutBlock> Blocks = asBlocks(
            initProperties(actions(a -> a.elements(BlockElements))),
            initProperties(context(c -> c.elements(ContextBlockElements))),
            initProperties(divider()),
            initProperties(com.slack.api.model.block.Blocks.image(i -> i)),
            initProperties(section(s -> s
                    .accessory(initProperties(ImageElement.builder().build()))
                    .text(TextObject)
                    .fields(SectionBlockFieldElements)))
    );

    public static PlainTextInputElement plainTextInputElement = initProperties(PlainTextInputElement.builder()
            .placeholder(initProperties(PlainTextObject.builder().build()))
            .dispatchActionConfig(initProperties(DispatchActionConfig.builder().triggerActionsOn(Arrays.asList("")).build()))
            .build());
    public static RadioButtonsElement radioButtonsElement = initProperties(RadioButtonsElement.builder()
            .confirm(initProperties(ConfirmationDialogObject.builder()
                    .text(initProperties(PlainTextObject.builder().build()))
                    .build()))
            .options(Arrays.asList(
                    initProperties(OptionObject.builder().text(initProperties(PlainTextObject.builder().build())).build()),
                    initProperties(OptionObject.builder().text(initProperties(MarkdownTextObject.builder().build())).build())
            ))
            .initialOption(initProperties(OptionObject.builder().text(initProperties(PlainTextObject.builder().build())).build()))
            .build());

    public static List<LayoutBlock> ModalBlocks = asBlocks(
            initProperties(input(i -> i.element(plainTextInputElement))),
            initProperties(input(i -> i.element(radioButtonsElement))),
            initProperties(input(i -> i.element(initProperties(button(b -> b.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(channelsSelect(c -> c.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(conversationsSelect(c -> c.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(datePicker(d -> d.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(timePicker(d -> d.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(externalSelect(e -> e.initialOption(Option).confirm(Confirm)))))),
            initProperties(input(ip -> ip.element(initProperties(com.slack.api.model.block.element.BlockElements.image(i -> i))))),
            initProperties(input(i -> i.element(initProperties(overflowMenu(o -> o.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(staticSelect(s -> s.initialOption(Option).confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(usersSelect(u -> u.confirm(Confirm)))))),
            initProperties(actions(a -> a.elements(BlockElements))),
            initProperties(context(c -> c.elements(ContextBlockElements))),
            initProperties(divider()),
            initProperties(com.slack.api.model.block.Blocks.image(i -> i)),
            initProperties(section(s -> s
                    .accessory(initProperties(ImageElement.builder().build()))
                    .text(TextObject)
                    .fields(SectionBlockFieldElements)))
    );

    public static Message Message = new Message();

    static {
        Message.setAttachments(Attachments);
        Message.setBlocks(Blocks);
        File.Shares shares = new File.Shares();
        Map<String, List<File.ShareDetail>> channels = new HashMap<>();
        File.ShareDetail shareDetail = initProperties(new File.ShareDetail());
        shareDetail.setReplyUsers(Arrays.asList(""));
        channels.put("C03E94MKU", Arrays.asList(shareDetail));
        channels.put("C03E94MKU_", Arrays.asList(shareDetail));
        shares.setPrivateChannels(channels);
        shares.setPublicChannels(channels);
        File file = initProperties(File.builder().shares(shares).build());
        Message.setFile(file);
        Message.setFiles(Arrays.asList(file));
        Message.setPinnedTo(Arrays.asList(""));
        Message.setReactions(Arrays.asList(initProperties(new Reaction())));
        Message.setReplyUsers(Arrays.asList(""));
    }

}
