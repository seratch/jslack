package test_locally.sample_json_generation;

import com.slack.api.app_backend.events.payload.*;
import com.slack.api.model.Message;
import com.slack.api.model.event.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.sample_json_generation.ObjectToJsonDumper;
import util.sample_json_generation.SampleObjects;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static util.ObjectInitializer.initProperties;

@Slf4j
public class EventsApiPayloadDumpTest {

    ObjectToJsonDumper dumper = new ObjectToJsonDumper("../json-logs/samples/events");

    @Test
    public void dumpAll() throws Exception {
        // See also: test_with_remote_apis.EventsApiTest in bolt-servlet
        // The disabled ones are generated by the tests.
        List<EventsApiPayload<?>> payloads = Arrays.asList(
                new AppHomeOpenedPayload(),
                buildAppMentionPayload(),
                new AppRequestedPayload(),
                new AppRateLimitedPayload(),
                new AppUninstalledPayload(),
                new CallRejectedPayload(),
                // new ChannelArchivePayload(),
                // new ChannelCreatedPayload(),
                new ChannelDeletedPayload(),
                new ChannelHistoryChangedPayload(),
                new ChannelIdChangedPayload(),
                // new ChannelLeftPayload(),
                // new ChannelRenamePayload(),
                new ChannelSharedPayload(),
                // new ChannelUnarchivePayload(),
                new ChannelUnsharedPayload(),
                // new DndUpdatedPayload(),
                // new DndUpdatedUserPayload(),
                new EmailDomainChangedPayload(),
                //new EmojiChangedPayload(),
                new FileChangePayload(),
                // new FileCreatedPayload(),
                // new FileDeletedPayload(),
                // new FilePublicPayload(),
                // new FileSharedPayload(),
                // new FileUnsharedPayload(),
                new GoodbyePayload(),
                new GridMigrationFinishedPayload(),
                new GridMigrationStartedPayload(),
                // new GroupArchivePayload(),
                new GroupClosePayload(),
                new GroupDeletedPayload(),
                new GroupHistoryChangedPayload(),
                // new GroupLeftPayload(),
                new GroupOpenPayload(),
                // new GroupRenamePayload(),
                // new GroupUnarchivePayload(),
                new ImClosePayload(),
                new ImCreatedPayload(),
                new ImHistoryChangedPayload(),
                new ImOpenPayload(),
                buildInviteRequestedPayload(),
                buildLinkSharedPayload(),
                // new MemberJoinedChannelPayload(),
                // new MemberLeftChannelPayload(),
                buildMessagePayload(),
                buildMessageBotPayload(),
                buildMessageChangedPayload(),
                buildMessageDeletedPayload(),
                buildMessageEkmAccessDeniedPayload(),
                buildMessageFileSharePayload(),
                buildMessageMePayload(),
                buildMessageRepliedPayload(),
                buildMessageThreadBroadcastPayload(),
                new MessageChannelJoinPayload(),
                new MessageChannelArchivePayload(),
                new MessageChannelUnarchivePayload(),
                new MessageChannelLeavePayload(),
                new MessageChannelNamePayload(),
                new MessageChannelPurposePayload(),
                // buildPinAddedPayload(),
                // buildPinRemovedPayload(),
                // new ReactionAddedPayload(),
                // new ReactionRemovedPayload(),
                buildResourcesAddedPayload(),
                buildResourcesRemovedPayload(),
                buildScopeDeniedPayload(),
                buildScopeGrantedPayload(),
                // buildStarAddedPayload(),
                // buildStarRemovedPayload(),
                // buildSubteamCreatedPayload(),
                // buildSubteamMembersChangedPayload(),
                // new SubteamSelfAddedPayload(),
                // new SubteamSelfRemovedPayload(),
                // buildSubteamUpdatedPayload(),
                new TeamDomainChangePayload(),
                new TeamJoinPayload(),
                new TeamRenamePayload(),
                buildTeamAccessGrantedPayload(),
                buildTeamAccessRevokedPayload(),
                buildTokensRevokedPayload(),
                new UserChangePayload(),
                buildUserResourceDeniedPayload(),
                buildUserResourceGrantedPayload(),
                new UserResourceRemovedPayload(),
                new MessageGroupTopicPayload(),
                new MessageChannelTopicPayload(),
                new MessageChannelPostingPermissionsPayload(),
                new WorkflowStepExecutePayload(),
                new WorkflowDeletedPayload(),
                new WorkflowPublishedPayload(),
                new WorkflowUnpublishedPayload(),
                new WorkflowStepDeletedPayload()
        );
        for (EventsApiPayload<?> payload : payloads) {
            try {
                Field authedUsers = payload.getClass().getDeclaredField("authedUsers");
                if (authedUsers != null) {
                    authedUsers.setAccessible(true);
                    authedUsers.set(payload, Arrays.asList(""));
                }
            } catch (Exception e) {
            }
            try {
                Field authedTeams = payload.getClass().getDeclaredField("authedTeams");
                if (authedTeams != null) {
                    authedTeams.setAccessible(true);
                    authedTeams.set(payload, Arrays.asList(""));
                }
            } catch (Exception e) {
            }
            initProperties(payload);
            dumper.dump(payload.getClass().getSimpleName(), payload);
        }
    }

    private AppMentionPayload buildAppMentionPayload() {
        AppMentionPayload payload = new AppMentionPayload();
        AppMentionEvent event = new AppMentionEvent();
        event.setBlocks(SampleObjects.Blocks);
        payload.setEvent(event);
        return payload;
    }

    private TeamAccessGrantedPayload buildTeamAccessGrantedPayload() {
        TeamAccessGrantedPayload payload = new TeamAccessGrantedPayload();
        payload.setEvent(new TeamAccessGrantedEvent());
        payload.getEvent().setTeamIds(Arrays.asList(""));
        return payload;
    }

    private TeamAccessRevokedPayload buildTeamAccessRevokedPayload() {
        TeamAccessRevokedPayload payload = new TeamAccessRevokedPayload();
        payload.setEvent(new TeamAccessRevokedEvent());
        payload.getEvent().setTeamIds(Arrays.asList(""));
        return payload;
    }

    private UserResourceGrantedPayload buildUserResourceGrantedPayload() {
        UserResourceGrantedPayload payload = new UserResourceGrantedPayload();
        payload.setEvent(new UserResourceGrantedEvent());
        payload.getEvent().setScopes(Arrays.asList(""));
        return payload;
    }

    private UserResourceDeniedPayload buildUserResourceDeniedPayload() {
        UserResourceDeniedPayload payload = new UserResourceDeniedPayload();
        payload.setEvent(new UserResourceDeniedEvent());
        payload.getEvent().setScopes(Arrays.asList(""));
        return payload;
    }

    private TokensRevokedPayload buildTokensRevokedPayload() {
        TokensRevokedPayload payload = new TokensRevokedPayload();
        payload.setEvent(new TokensRevokedEvent());
        payload.getEvent().setTokens(new TokensRevokedEvent.Tokens());
        payload.getEvent().getTokens().setBot(Arrays.asList(""));
        payload.getEvent().getTokens().setOauth(Arrays.asList(""));
        return payload;
    }

    private ScopeGrantedPayload buildScopeGrantedPayload() {
        ScopeGrantedPayload payload = new ScopeGrantedPayload();
        payload.setEvent(new ScopeGrantedEvent());
        payload.getEvent().setScopes(Arrays.asList(""));
        return payload;
    }

    private ScopeDeniedPayload buildScopeDeniedPayload() {
        ScopeDeniedPayload payload = new ScopeDeniedPayload();
        payload.setEvent(new ScopeDeniedEvent());
        payload.getEvent().setScopes(Arrays.asList(""));
        return payload;
    }

    private ResourcesRemovedPayload buildResourcesRemovedPayload() {
        ResourcesRemovedPayload payload = new ResourcesRemovedPayload();
        payload.setEvent(new ResourcesRemovedEvent());
        ResourcesRemovedEvent.ResourceItem resource = new ResourcesRemovedEvent.ResourceItem();
        resource.setResource(new ResourcesRemovedEvent.Resource());
        resource.setScopes(Arrays.asList(""));
        payload.getEvent().setResources(Arrays.asList(initProperties(resource)));
        return payload;
    }

    private ResourcesAddedPayload buildResourcesAddedPayload() {
        ResourcesAddedPayload payload = new ResourcesAddedPayload();
        payload.setEvent(new ResourcesAddedEvent());
        ResourcesAddedEvent.ResourceItem resource = new ResourcesAddedEvent.ResourceItem();
        resource.setResource(new ResourcesAddedEvent.Resource());
        resource.setScopes(Arrays.asList(""));
        payload.getEvent().setResources(Arrays.asList(initProperties(resource)));
        return payload;
    }

    private LinkSharedPayload buildLinkSharedPayload() {
        LinkSharedPayload payload = new LinkSharedPayload();
        payload.setEvent(new LinkSharedEvent());
        payload.getEvent().setLinks(Arrays.asList(initProperties(new LinkSharedEvent.Link())));
        return payload;
    }

    private MessagePayload buildMessagePayload() {
        MessagePayload payload = new MessagePayload();
        payload.setEvent(new MessageEvent());
        payload.getEvent().setAttachments(SampleObjects.Attachments);
        payload.getEvent().setBlocks(SampleObjects.Blocks);
        return payload;
    }

    private MessageBotPayload buildMessageBotPayload() {
        MessageBotPayload payload = new MessageBotPayload();
        payload.setEvent(new MessageBotEvent());
        payload.getEvent().setAttachments(SampleObjects.Attachments);
        payload.getEvent().setBlocks(SampleObjects.Blocks);
        return payload;
    }

    private MessageChangedPayload buildMessageChangedPayload() {
        MessageChangedPayload payload = new MessageChangedPayload();
        MessageChangedEvent event = new MessageChangedEvent();
        MessageChangedEvent.Message message = new MessageChangedEvent.Message();
        message.setAttachments(SampleObjects.Attachments);
        message.setBlocks(SampleObjects.Blocks);
        event.setMessage(message);
        event.setPreviousMessage(message);
        payload.setEvent(event);
        return payload;
    }

    private MessageFileSharePayload buildMessageFileSharePayload() {
        MessageFileSharePayload payload = new MessageFileSharePayload();
        MessageFileShareEvent event = new MessageFileShareEvent();
        event.setAttachments(SampleObjects.Attachments);
        event.setBlocks(SampleObjects.Blocks);
        event.setFiles(SampleObjects.Files);
        payload.setEvent(event);
        return payload;
    }

    private MessageDeletedPayload buildMessageDeletedPayload() {
        MessageDeletedPayload payload = new MessageDeletedPayload();
        MessageDeletedEvent event = new MessageDeletedEvent();
        MessageDeletedEvent.Message message = new MessageDeletedEvent.Message();
        message.setAttachments(SampleObjects.Attachments);
        message.setBlocks(SampleObjects.Blocks);
        event.setPreviousMessage(message);
        payload.setEvent(event);
        return payload;
    }

    private MessageEkmAccessDeniedPayload buildMessageEkmAccessDeniedPayload() {
        MessageEkmAccessDeniedPayload payload = new MessageEkmAccessDeniedPayload();
        MessageEkmAccessDeniedEvent event = new MessageEkmAccessDeniedEvent();
        event.setAttachments(SampleObjects.Attachments);
        event.setBlocks(SampleObjects.Blocks);
        payload.setEvent(event);
        return payload;
    }

    private MessageMePayload buildMessageMePayload() {
        MessageMePayload payload = new MessageMePayload();
        MessageMeEvent event = new MessageMeEvent();
        payload.setEvent(event);
        return payload;
    }

    private MessageThreadBroadcastPayload buildMessageThreadBroadcastPayload() {
        MessageThreadBroadcastPayload payload = new MessageThreadBroadcastPayload();
        MessageThreadBroadcastEvent event = new MessageThreadBroadcastEvent();
        event.setRoot(new Message.MessageRoot());
        event.setAttachments(SampleObjects.Attachments);
        event.setBlocks(SampleObjects.Blocks);
        payload.setEvent(event);
        return payload;
    }

    private InviteRequestedPayload buildInviteRequestedPayload() {
        InviteRequestedPayload payload = new InviteRequestedPayload();
        InviteRequestedEvent event = new InviteRequestedEvent();
        InviteRequestedEvent.InviteRequest inviteRequest = new InviteRequestedEvent.InviteRequest();
        inviteRequest.setChannelIds(Arrays.asList(""));
        inviteRequest.setRequesterIds(Arrays.asList(""));
        inviteRequest.setTeam(new InviteRequestedEvent.Team());
        event.setInviteRequest(inviteRequest);
        payload.setEvent(event);
        return payload;
    }

    private MessageRepliedPayload buildMessageRepliedPayload() {
        MessageRepliedPayload payload = new MessageRepliedPayload();
        MessageRepliedEvent event = new MessageRepliedEvent();
        MessageRepliedEvent.Message message = initProperties(new MessageRepliedEvent.Message());
        message.setPinnedTo(Arrays.asList(""));
        message.setAttachments(SampleObjects.Attachments);
        message.setBlocks(SampleObjects.Blocks);
        event.setMessage(message);
        payload.setEvent(event);
        return payload;
    }

}
