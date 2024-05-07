package me.hannsi.melyclient.util.system.auth;

import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import me.hannsi.melyclient.util.system.math.time.TimeCalculator;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LoginFrame extends JFrame {
    private CompletableFuture<String> future;

    public LoginFrame() {
        this.setTitle("Microsoft Authentication");
        this.setSize(750, 750);
        this.setLocationRelativeTo(null);

        this.setContentPane(new JFXPanel());
    }

    public CompletableFuture<String> start(String url) {
        if (this.future != null) {
            return this.future;
        }

        this.future = new CompletableFuture<>();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                future.completeExceptionally(new MicrosoftAuthenticationException("User closed the authentication window"));
            }
        });

        Platform.runLater(() -> this.init(url));
        return this.future;
    }

    protected void init(String url) {
        WebView webView = new WebView();

        JFXPanel content = (JFXPanel) this.getContentPane();

        content.setScene(new Scene(webView, this.getWidth(), this.getHeight()));

        webView.getEngine().locationProperty().addListener((observable, oldValue, newValue) -> {
            new DebugLog(newValue + " accessed.", DebugLevel.DEBUG);
            if (newValue.contains("access_token")) {
                this.future.complete(newValue);
                deleteCookies(webView);
            }
        });
        webView.getEngine().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        webView.getEngine().load(url);

        this.setVisible(true);
    }

    protected void deleteCookies(WebView webView) {
        new DebugLog("Start delete cookies.", DebugLevel.DEBUG);
        long tookTime = TimeCalculator.calculate(() -> {
            WebEngine webEngine = webView.getEngine();
            CookieManager manager = new CookieManager();
            CookieHandler.setDefault(manager);
            CookieStore cookieStore = manager.getCookieStore();
            List<URI> uris = new ArrayList<>(cookieStore.getURIs());
            for (URI uri : uris) {
                List<HttpCookie> cookies = cookieStore.get(uri);
                for (HttpCookie cookie : cookies) {
                    cookieStore.remove(uri, cookie);
                    new DebugLog("URI : " + uri + "   |   CookieName : " + cookie.getName(), DebugLevel.DEBUG);
                }
            }
            webEngine.reload();
        });
        new DebugLog("Finish delete cookies took " + tookTime + "ms.", DebugLevel.DEBUG);
    }
}