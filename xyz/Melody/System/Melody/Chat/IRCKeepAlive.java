/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumChatFormatting
 */
package xyz.Melody.System.Melody.Chat;

import net.minecraft.util.EnumChatFormatting;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.System.Melody.Chat.IRC;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.timer.TimerUtil;

public final class IRCKeepAlive {
    private TimerUtil timer = new TimerUtil();
    private boolean remnided = false;
    private int ticks = 0;

    @EventHandler
    public void chouShabi(EventTick eventTick) {
        if (this.ticks < 100) {
            ++this.ticks;
            return;
        }
        if (Client.instance.irc == null || Client.instance.ircThread == null) {
            return;
        }
        if (Client.instance.ircExeption && !this.remnided && Client.mc.field_71441_e != null && Client.mc.field_71439_g != null) {
            Helper.sendMessage(EnumChatFormatting.RED + "[IMPORTANT] IRC Connection Lost, type " + EnumChatFormatting.GREEN + ".irc reconnect" + EnumChatFormatting.RED + " to Reconnect.");
            this.remnided = true;
        }
        Client.instance.ircExeption = !Client.instance.ircThread.isAlive();
        if (!Client.instance.ircExeption) {
            this.remnided = false;
        }
        if (this.timer.hasReached(3000.0) && !Client.instance.ircExeption && IRC.pw != null) {
            String string = "KEEP_ALIVE";
            IRC.pw.println(string);
            this.timer.reset();
        }
        this.ticks = 0;
    }
}

