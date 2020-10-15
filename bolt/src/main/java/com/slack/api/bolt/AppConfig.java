package com.slack.api.bolt;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.bolt.meta.BoltLibraryVersion;
import com.slack.api.util.http.SlackHttpClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Configuration for a Slack App.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AppConfig {

    public static final class EnvVariableName {
        private EnvVariableName() {
        }

        public static final String SLACK_BOT_TOKEN = "SLACK_BOT_TOKEN";
        public static final String SLACK_SIGNING_SECRET = "SLACK_SIGNING_SECRET";
        public static final String SLACK_VERIFICATION_TOKEN = "SLACK_VERIFICATION_TOKEN";

        public static final String SLACK_CLIENT_ID = "SLACK_CLIENT_ID";
        public static final String SLACK_CLIENT_SECRET = "SLACK_CLIENT_SECRET";
        public static final String SLACK_REDIRECT_URI = "SLACK_REDIRECT_URI";
        public static final String SLACK_SCOPES = "SLACK_SCOPES";
        public static final String SLACK_USER_SCOPES = "SLACK_USER_SCOPES";
        public static final String SLACK_INSTALL_PATH = "SLACK_INSTALL_PATH";
        public static final String SLACK_REDIRECT_URI_PATH = "SLACK_REDIRECT_URI_PATH";
        public static final String SLACK_OAUTH_CANCELLATION_URL = "SLACK_OAUTH_CANCELLATION_URL";
        public static final String SLACK_OAUTH_COMPLETION_URL = "SLACK_OAUTH_COMPLETION_URL";

        @Deprecated // will be removed in v2.0
        public static final String SLACK_APP_CLIENT_ID = "SLACK_APP_CLIENT_ID";
        @Deprecated // will be removed in v2.0
        public static final String SLACK_APP_CLIENT_SECRET = "SLACK_APP_CLIENT_SECRET";
        @Deprecated // will be removed in v2.0
        public static final String SLACK_APP_REDIRECT_URI = "SLACK_APP_REDIRECT_URI";
        @Deprecated // will be removed in v2.0
        public static final String SLACK_APP_SCOPE = "SLACK_APP_SCOPE";
        @Deprecated // will be removed in v2.0
        public static final String SLACK_APP_USER_SCOPE = "SLACK_APP_USER_SCOPE";
        @Deprecated // will be removed in v2.0
        public static final String SLACK_APP_OAUTH_START_PATH = "SLACK_APP_OAUTH_START_PATH";
        @Deprecated // will be removed in v2.0
        public static final String SLACK_APP_OAUTH_CALLBACK_PATH = "SLACK_APP_OAUTH_CALLBACK_PATH";
        @Deprecated // will be removed in v2.0
        public static final String SLACK_APP_OAUTH_CANCELLATION_URL = "SLACK_APP_OAUTH_CANCELLATION_URL";
        @Deprecated // will be removed in v2.0
        public static final String SLACK_APP_OAUTH_COMPLETION_URL = "SLACK_APP_OAUTH_COMPLETION_URL";
    }

    @Builder.Default
    private Slack slack = Slack.getInstance(SlackConfig.DEFAULT, buildSlackHttpClient());

    private static SlackHttpClient buildSlackHttpClient() {
        Map<String, String> userAgentCustomInfo = new HashMap<>();
        userAgentCustomInfo.put("bolt", BoltLibraryVersion.get());
        SlackHttpClient client = new SlackHttpClient(userAgentCustomInfo);
        return client;
    }

    @Builder.Default
    private String singleTeamBotToken = System.getenv(EnvVariableName.SLACK_BOT_TOKEN);
    @Builder.Default
    private String signingSecret = System.getenv(EnvVariableName.SLACK_SIGNING_SECRET);
    @Deprecated
    @Builder.Default
    private String verificationToken = System.getenv(EnvVariableName.SLACK_VERIFICATION_TOKEN);

    // https://api.slack.com/docs/oauth

    public boolean isDistributedApp() {
        return clientId != null && clientSecret != null;
    }

    // --------------------------

    @Builder.Default
    private boolean oAuthInstallPathEnabled = false;

    public boolean isOAuthInstallPathEnabled() {
        return oAuthInstallPathEnabled || oAuthStartEnabled;
    }

    @Deprecated // will be removed in v2.0 - use oAuthInstallPathEnabled
    @Builder.Default
    private boolean oAuthStartEnabled = false;

    @Deprecated
    public boolean isOAuthStartEnabled() {
        return isOAuthInstallPathEnabled();
    }

    @Deprecated
    public void setOAuthStartEnabled(boolean enabled) {
        setOAuthInstallPathEnabled(enabled);
    }

    // --------------------------

    @Builder.Default
    private boolean oAuthRedirectUriPathEnabled = false;

    public boolean isOAuthRedirectUriPathEnabled() {
        return oAuthRedirectUriPathEnabled || oAuthCallbackEnabled;
    }

    @Deprecated // will be removed in v2.0 - use oAuthRedirectUriPathEnabled
    @Builder.Default
    private boolean oAuthCallbackEnabled = false;

    @Deprecated
    public boolean isOAuthCallbackEnabled() {
        return isOAuthRedirectUriPathEnabled();
    }

    @Deprecated
    public void setOAuthCallbackEnabled(boolean enabled) {
        setOAuthRedirectUriPathEnabled(enabled);
    }

    // --------------------------

    /**
     * Returns true if auth.test call result cache in MultiTeamsAuthorization middleware
     * is enabled. The default is false.
     */
    @Builder.Default
    private boolean authTestCacheEnabled = false;

    /**
     * Returns the millisecond value to keep cached auth.test response in cache.
     * Negative value indicates the cache is permanent. The default is 10 minutes.
     */
    @Builder.Default
    private long authTestCacheExpirationMillis = 600_000L;

    @Builder.Default
    // https://api.slack.com/authentication/migration
    private boolean classicAppPermissionsEnabled = false;

    /**
     * The thread pool size for the built-in handy executor service.
     */
    @Builder.Default
    private int threadPoolSize = 10;

    @Builder.Default
    private String clientId = Optional.ofNullable(System.getenv(EnvVariableName.SLACK_CLIENT_ID))
            .orElse(System.getenv(EnvVariableName.SLACK_APP_CLIENT_ID));
    @Builder.Default
    private String clientSecret = Optional.ofNullable(System.getenv(EnvVariableName.SLACK_CLIENT_SECRET))
            .orElse(System.getenv(EnvVariableName.SLACK_APP_CLIENT_SECRET));
    @Builder.Default
    private String redirectUri = Optional.ofNullable(System.getenv(EnvVariableName.SLACK_REDIRECT_URI))
            .orElse(System.getenv(EnvVariableName.SLACK_APP_REDIRECT_URI));
    @Builder.Default
    private String scope = Optional.ofNullable(System.getenv(EnvVariableName.SLACK_SCOPES))
            .orElse(System.getenv(EnvVariableName.SLACK_APP_SCOPE));
    @Builder.Default
    private String userScope = Optional.ofNullable(System.getenv(EnvVariableName.SLACK_USER_SCOPES))
            .orElse(System.getenv(EnvVariableName.SLACK_APP_USER_SCOPE));

    private String appPath;

    // --------------------------

    @Deprecated // will be removed in v2.0 - use getOauthInstallRequestURI instead
    public String getOauthStartRequestURI() {
        return getOauthInstallRequestURI();
    }

    public String getOauthInstallRequestURI() {
        if (appPath == null) {
            return getOauthInstallPath();
        } else {
            return appPath + getOauthInstallPath();
        }
    }

    // --------------------------

    @Deprecated // will be removed in v2.0 - use getOauthRedirectUriRequestURI instead
    public String getOauthCallbackRequestURI() {
        return getOauthRedirectUriRequestURI();
    }

    public String getOauthRedirectUriRequestURI() {
        if (appPath == null) {
            return getOauthRedirectUriPath();
        } else {
            return appPath + getOauthRedirectUriPath();
        }
    }

    // --------------------------

    // NOTE: We should not change this default value in v1.x as it's a breaking change to existing users.
    // We will be changing this in v2.0.
    private static final String DEFAULT_OAUTH_INSTALL_PATH = "start";

    @Deprecated // will be removed in v2.0 - use oauthInstallPath instead
    @Builder.Default
    private String oauthStartPath = Optional.ofNullable(System.getenv(EnvVariableName.SLACK_INSTALL_PATH))
            .orElse(Optional.ofNullable(System.getenv(EnvVariableName.SLACK_APP_OAUTH_START_PATH))
                    .orElse(DEFAULT_OAUTH_INSTALL_PATH));

    @Deprecated
    public String getOauthStartPath() {
        return getOauthInstallPath();
    }

    @Deprecated
    public void setOauthStartPath(String oauthStartPath) {
        setOauthInstallPath(oauthStartPath);
    }

    @Builder.Default
    private String oauthInstallPath = Optional.ofNullable(System.getenv(EnvVariableName.SLACK_INSTALL_PATH))
            .orElse(Optional.ofNullable(System.getenv(EnvVariableName.SLACK_APP_OAUTH_START_PATH))
                    .orElse(DEFAULT_OAUTH_INSTALL_PATH));

    public String getOauthInstallPath() {
        String path = this.oauthInstallPath;
        String legacyPath = this.oauthStartPath;
        if (path != null
                && path.equals(DEFAULT_OAUTH_INSTALL_PATH) == false
                && legacyPath.equals(DEFAULT_OAUTH_INSTALL_PATH)) {
            return path;
        } else {
            return legacyPath;
        }
    }

    public void setOauthInstallPath(String oauthInstallPath) {
        this.oauthInstallPath = oauthInstallPath;
        this.oAuthInstallPathEnabled = oauthInstallPath != null;
    }

    // --------------------------

    // NOTE: We should not change this default value in v1.x as it's a breaking change to existing users.
    // We will be changing this in v2.0.
    private static final String DEFAULT_OAUTH_REDIRECT_URI_PATH = "callback";

    @Deprecated // will be removed in v2.0 - use oauthRedirectUriPath instead
    @Builder.Default
    private String oauthCallbackPath = Optional.ofNullable(System.getenv(EnvVariableName.SLACK_REDIRECT_URI_PATH))
            .orElse(Optional.ofNullable(System.getenv(EnvVariableName.SLACK_APP_OAUTH_CALLBACK_PATH))
                    .orElse(DEFAULT_OAUTH_REDIRECT_URI_PATH));

    @Deprecated
    public String getOauthCallbackPath() {
        return getOauthRedirectUriPath();
    }

    @Deprecated
    public void setOauthCallbackPath(String oauthCallbackPath) {
        setOauthRedirectUriPath(oauthCallbackPath);
    }

    @Builder.Default
    private String oauthRedirectUriPath = Optional.ofNullable(System.getenv(EnvVariableName.SLACK_REDIRECT_URI_PATH))
            .orElse(Optional.ofNullable(System.getenv(EnvVariableName.SLACK_APP_OAUTH_CALLBACK_PATH))
                    .orElse(DEFAULT_OAUTH_REDIRECT_URI_PATH));

    public String getOauthRedirectUriPath() {
        String path = this.oauthRedirectUriPath;
        String legacyPath = this.oauthCallbackPath;
        if (path != null
                && path.equals(DEFAULT_OAUTH_REDIRECT_URI_PATH) == false
                && legacyPath.equals(DEFAULT_OAUTH_REDIRECT_URI_PATH)) {
            return path;
        } else {
            return legacyPath;
        }
    }

    public void setOauthRedirectUriPath(String oauthRedirectUriPath) {
        this.oauthRedirectUriPath = oauthRedirectUriPath;
        this.oAuthRedirectUriPathEnabled = oauthRedirectUriPath != null;
    }

    // --------------------------

    @Builder.Default
    private String oauthCancellationUrl = Optional.ofNullable(System.getenv(EnvVariableName.SLACK_OAUTH_CANCELLATION_URL))
            .orElse(System.getenv(EnvVariableName.SLACK_APP_OAUTH_CANCELLATION_URL));
    @Builder.Default
    private String oauthCompletionUrl = Optional.ofNullable(System.getenv(EnvVariableName.SLACK_OAUTH_COMPLETION_URL))
            .orElse(System.getenv(EnvVariableName.SLACK_APP_OAUTH_COMPLETION_URL));

    @Builder.Default
    private boolean alwaysRequestUserTokenNeeded = false;

    @Builder.Default
    private boolean appInitializersEnabled = true;

}