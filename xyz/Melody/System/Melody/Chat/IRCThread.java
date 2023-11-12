/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.System.Melody.Chat;

import xyz.Melody.Client;

public final class IRCThread
extends Thread {
    @Override
    public void run() {
        Client.instance.irc.connect(25566);
        try {
            if (!(Client.instance.ircExeption || Client.stopping || Client.instance.irc.shouldThreadStop)) {
                Client.instance.irc.handleInput();
                return;
            }
        }
        catch (Exception exception) {
            return;
        }
    }
}

