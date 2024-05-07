package me.hannsi.melyclient.manager;

import com.mojang.authlib.Agent;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.mixin.net.minecraft.client.IMinecraft;
import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.system.auth.AccountData;
import me.hannsi.melyclient.util.system.auth.LoginMode;
import me.hannsi.melyclient.util.system.auth.MicrosoftWebView;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AltManager implements InterfaceMinecraft {
    private final UserAuthentication userAuthentication;
    public AccountData nowLoginAccountData;
    public List<AccountData> accountDataList;

    public AltManager() {
        UUID notSureWhyINeedThis = UUID.randomUUID();
        AuthenticationService authService = new YggdrasilAuthenticationService(Minecraft.getMinecraft().getProxy(), notSureWhyINeedThis.toString());
        userAuthentication = authService.createUserAuthentication(Agent.MINECRAFT);
        authService.createMinecraftSessionService();
        accountDataList = new ArrayList<>();
    }

    public void unLoad() {
        MelyClient.altManager = null;
    }

    public boolean setUserMinecraft(String email, String password) {
        if (!Minecraft.getMinecraft().getSession().getUsername().equals(email) || Minecraft.getMinecraft().getSession().getToken().equals("0")) {

            this.userAuthentication.logOut();
            this.userAuthentication.setUsername(email);
            this.userAuthentication.setPassword(password);
            try {
                this.userAuthentication.logIn();
                Session session = new Session(this.userAuthentication.getSelectedProfile().getName(), UUIDTypeAdapter.fromUUID(userAuthentication.getSelectedProfile().getId()), this.userAuthentication.getAuthenticatedToken(), this.userAuthentication.getUserType().getName());
                setSession(session);
                nowLoginAccountData = new AccountData(email, password, session, LoginMode.MINECRAFT);
            } catch (Exception ignore) {
                return false;
            }
        }
        return true;
    }

    public boolean setUserMicrosoft(String email, String password) {
        try {
            MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
            MicrosoftAuthResult acc = authenticator.loginWithCredentials(email, password);
            Session session = new Session(acc.getProfile().getName(), acc.getProfile().getId(), acc.getAccessToken(), "legacy");
            setSession(session);
            nowLoginAccountData = new AccountData(email, password, session, LoginMode.MICROSOFT);
        } catch (MicrosoftAuthenticationException e) {
            new DebugLog(e, DebugLevel.WARNING);
            return false;
        }
        return true;
    }

    public Session getUserMicrosoftSession(String email, String password) {
        Session session;
        try {
            MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
            MicrosoftAuthResult acc = authenticator.loginWithCredentials(email, password);
            session = new Session(acc.getProfile().getName(), acc.getProfile().getId(), acc.getAccessToken(), "legacy");
        } catch (MicrosoftAuthenticationException e) {
            new DebugLog(e, DebugLevel.WARNING);
            return null;
        }

        return session;
    }

    public Session getUserMinecraftSession(String email, String password) {
        Session session = null;

        if (!Minecraft.getMinecraft().getSession().getUsername().equals(email) || Minecraft.getMinecraft().getSession().getToken().equals("0")) {

            this.userAuthentication.logOut();
            this.userAuthentication.setUsername(email);
            this.userAuthentication.setPassword(password);
            try {
                this.userAuthentication.logIn();
                session = new Session(this.userAuthentication.getSelectedProfile().getName(), UUIDTypeAdapter.fromUUID(userAuthentication.getSelectedProfile().getId()), this.userAuthentication.getAuthenticatedToken(), this.userAuthentication.getUserType().getName());
            } catch (Exception e) {
                new DebugLog(e, DebugLevel.WARNING);
                return null;
            }
        }
        return session;
    }

    public Session getSession(AccountData accountData) {
        if (accountData.getLoginMode() == LoginMode.MICROSOFT) {
            return getUserMicrosoftSession(accountData.getEmail(), accountData.getPassword());
        } else {
            return getUserMinecraftSession(accountData.getEmail(), accountData.getPassword());
        }
    }

    public Session getUserOfflineSession(String username) {
        this.userAuthentication.logOut();
        return new Session(username, username, "0", "legacy");
    }

    private void setSession(Session session) {
        ((IMinecraft) mc).setSession(session);
        new DebugLog("Setting user: " + session.getUsername(), DebugLevel.INFO);
    }

    public void setUserOffline(String username) {
        this.userAuthentication.logOut();
        Session session = new Session(username, username, "0", "legacy");
        setSession(session);
    }

    public boolean loginWithWebView() {
        Session session;
        try {
            MicrosoftAuthResult acc = MicrosoftWebView.loginWithWebview();
            session = new Session(acc.getProfile().getName(), acc.getProfile().getId(), acc.getAccessToken(), "legacy");
            setSession(session);
            return true;
        } catch (Exception e) {
            new DebugLog(e, DebugLevel.WARNING);
            return false;
        }
    }

    public boolean login(AccountData accountData) {
        if (accountData.getLoginMode() == LoginMode.MICROSOFT) {
            return setUserMicrosoft(accountData.getEmail(), accountData.getPassword());
        } else if (accountData.getLoginMode() == LoginMode.MINECRAFT) {
            return setUserMinecraft(accountData.getEmail(), accountData.getPassword());
        }
        return false;
    }

    public AccountData getAccountData(String email, String password, LoginMode loginMode) {
        AccountData accountData = null;

        if (loginMode == LoginMode.MICROSOFT) {
            accountData = new AccountData(email, password, getUserMicrosoftSession(email, password), loginMode);
        } else if (loginMode == LoginMode.MINECRAFT) {
            accountData = new AccountData(email, password, getUserMinecraftSession(email, password), loginMode);
        }

        return accountData;
    }

    public void addAccountData(AccountData accountData) {
        for (AccountData tempAccountData : accountDataList) {
            if (tempAccountData == accountData) {
                return;
            }
        }

        accountDataList.add(accountData);
    }
}
