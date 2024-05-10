package me.hannsi.melyclient.util.system.auth;

import me.hannsi.melyclient.MelyClient;
import net.minecraft.util.Session;

public class AccountData {
    public LoginMode loginMode;
    public Session session;
    public String email;
    public String password;

    public AccountData(LoginMode loginMode, String email, String password) {
        this.loginMode = loginMode;
        this.session = MelyClient.altManager.getUserMinecraftSession(email, password);
        this.email = email;
        this.password = password;
    }

    public AccountData(LoginMode loginMode, Session session) {
        this.loginMode = loginMode;
        this.session = session;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginMode getLoginMode() {
        return loginMode;
    }

    public void setLoginMode(LoginMode loginMode) {
        this.loginMode = loginMode;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
