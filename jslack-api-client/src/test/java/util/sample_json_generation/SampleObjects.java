package util.sample_json_generation;

import com.github.seratch.jslack.api.model.*;
import com.github.seratch.jslack.api.model.block.*;
import com.github.seratch.jslack.api.model.block.composition.ConfirmationDialogObject;
import com.github.seratch.jslack.api.model.block.composition.MarkdownTextObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import com.github.seratch.jslack.api.model.block.composition.TextObject;
import com.github.seratch.jslack.api.model.block.element.*;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.JsonElement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    .actions(Arrays.asList(initProperties(Action.builder().build())))
                    .mrkdwnIn(Arrays.asList(""))
                    .build())
    );

    public static TextObject TextObject = initProperties(PlainTextObject.builder().build());

    public static ConfirmationDialogObject Confirm = ConfirmationDialogObject.builder().text(TextObject).build();

    public static List<BlockElement> BlockElements = Arrays.asList(
            initProperties(ButtonElement.builder().confirm(Confirm).build()),
            initProperties(ChannelsSelectElement.builder().confirm(Confirm).build()),
            initProperties(ConversationsSelectElement.builder().confirm(Confirm).build()),
            initProperties(DatePickerElement.builder().confirm(Confirm).build()),
            initProperties(ExternalSelectElement.builder().confirm(Confirm).build()),
            initProperties(ImageElement.builder().build()),
            initProperties(OverflowMenuElement.builder().confirm(Confirm).build()),
            initProperties(StaticSelectElement.builder().confirm(Confirm).build()),
            initProperties(UsersSelectElement.builder().confirm(Confirm).build())
    );
    public static List<ContextBlockElement> ContextBlockElements = Arrays.asList(
            (ContextBlockElement) initProperties(ImageElement.builder().build())
    );
    public static List<TextObject> SectionBlockFieldElements = Arrays.asList(
            initProperties(PlainTextObject.builder().build()),
            initProperties(MarkdownTextObject.builder().build())
    );
    public static List<LayoutBlock> Blocks = Arrays.asList(
            initProperties(ActionsBlock.builder().elements(BlockElements).build()),
            initProperties(ContextBlock.builder().elements(ContextBlockElements).build()),
            initProperties(DividerBlock.builder().build()),
            initProperties(ImageBlock.builder().build()),
            initProperties(SectionBlock.builder()
                    .accessory(initProperties(ImageElement.builder().build()))
                    .text(TextObject)
                    .fields(SectionBlockFieldElements)
                    .build())
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
