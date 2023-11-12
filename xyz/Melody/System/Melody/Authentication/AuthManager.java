/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package xyz.Melody.System.Melody.Authentication;

import net.minecraft.client.Minecraft;
import xyz.Melody.Client;
import xyz.Melody.System.Managers.Manager;
import xyz.Melody.System.Melody.Authentication.AuthUtils;
import xyz.Melody.System.Melody.Authentication.UserObj;

public final class AuthManager
implements Manager {
    public boolean verified;
    private Minecraft mc = Minecraft.func_71410_x();
    private UserObj user;

    public UserObj getUser() {
        return this.user;
    }

    public boolean authMe(String string, String string2) {
        Client.instance.authenticatingUser = true;
        boolean bl = false;
        try {
            AuthUtils.init();
            if (AuthUtils.t.isAlive()) {
                Thread.sleep(100L);
                return false;
            }
            Client.instance.authDaemon();
            bl = this.verified;
        }
        catch (Exception exception) {
            Throwable throwable = new Throwable(exception.getMessage());
            StackTraceElement[] stackTraceElementArray = new StackTraceElement[]{};
            throwable.setStackTrace(stackTraceElementArray);
            throwable.printStackTrace();
        }
        Client.instance.logger.info("m1Verified: " + bl);
        if (bl) {
            this.verified = true;
            Client.instance.authenticatingUser = false;
            return true;
        }
        Client.instance.authenticatingUser = false;
        return false;
    }

    @Override
    public void init() {
        this.authMe(this.user.getUuid(), this.user.getName());
    }

    public void setUser(UserObj userObj) {
        this.user = userObj;
    }
}

