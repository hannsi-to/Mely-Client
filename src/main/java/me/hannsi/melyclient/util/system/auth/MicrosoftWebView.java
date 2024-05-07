package me.hannsi.melyclient.util.system.auth;

import fr.litarvan.openauth.microsoft.AuthTokens;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fr.litarvan.openauth.microsoft.MicrosoftAuthenticator.*;

public class MicrosoftWebView {
    public static MicrosoftAuthResult loginWithWebview() throws MicrosoftAuthenticationException {
        try {
            return loginWithAsyncWebview().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new MicrosoftAuthenticationException(e);
        }
    }

    public static CompletableFuture<MicrosoftAuthResult> loginWithAsyncWebview() {
        HttpClient http = new HttpClient();

        String url = String.format("%s?%s", MICROSOFT_AUTHORIZATION_ENDPOINT, http.buildParams(getLoginParams()));
        LoginFrame frame = new LoginFrame();

        return frame.start(url).thenApplyAsync(result -> {
            MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();

            try {
                return authenticator.loginWithTokens(extractTokens(result));
            } catch (MicrosoftAuthenticationException e) {
                throw new CompletionException(e);
            }
        });
    }

    public static AuthTokens extractTokens(String url) throws MicrosoftAuthenticationException {
        return new AuthTokens(extractValue(url, "access_token"), extractValue(url, "refresh_token"));
    }

    public static String extractValue(String url, String key) throws MicrosoftAuthenticationException {
        String matched = match(key + "=([^&]*)", url);
        if (matched == null) {
            throw new MicrosoftAuthenticationException("Invalid credentials or tokens");
        }

        try {
            return URLDecoder.decode(matched, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new MicrosoftAuthenticationException(e);
        }
    }

    public static String match(String regex, String content) {
        Matcher matcher = Pattern.compile(regex).matcher(content);
        if (!matcher.find()) {
            return null;
        }

        return matcher.group(1);
    }

    public static Map<String, String> getLoginParams() {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", XBOX_LIVE_CLIENT_ID);
        params.put("redirect_uri", MICROSOFT_REDIRECTION_ENDPOINT);
        params.put("scope", XBOX_LIVE_SERVICE_SCOPE);
        params.put("response_type", "token");

        return params;
    }
}
