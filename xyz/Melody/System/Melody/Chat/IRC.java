/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.IChatComponent
 */
package xyz.Melody.System.Melody.Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import xyz.Melody.Client;
import xyz.Melody.System.Melody.Authentication.MSAC;
import xyz.Melody.System.Melody.Chat.IRCThread;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.timer.TimerUtil;

public final class IRC {
    public Thread ircDaemon;
    public boolean shouldThreadStop = false;
    private TimerUtil timer = new TimerUtil();
    private boolean connected = false;
    public BufferedReader reader;
    public static Socket socket;
    private static Minecraft mc;
    static InputStream in;
    public static PrintWriter pw;

    private String readLine() {
        try {
            Method method = mc.func_110432_I().getClass().getDeclaredMethod("getToken", new Class[0]);
            method.setAccessible(true);
            return (String)method.invoke(mc.func_110432_I(), new Object[0]);
        }
        catch (Exception exception) {
            try {
                Method method = mc.func_110432_I().getClass().getDeclaredMethod("func_148254_d", new Class[0]);
                method.setAccessible(true);
                return (String)method.invoke(mc.func_110432_I(), new Object[0]);
            }
            catch (Exception exception2) {
                return exception2.getMessage();
            }
        }
    }

    public void connect() {
        this.connect(25545);
    }

    static {
        mc = Minecraft.func_71410_x();
    }

    public void start() {
        this.shouldThreadStop = false;
        Client.instance.ircThread = new IRCThread();
        Client.instance.ircThread.setDaemon(true);
        Client.instance.ircThread.setName("Melody -> IRC Main Thread");
        Client.instance.ircThread.start();
    }

    public void sendPrefixMsg(String string, boolean bl) {
        String string2 = "";
        int n = 0;
        if (n < string.length()) {
            string2 = string2 + string.charAt(n);
            ++n;
            return;
        }
        if (!Client.instance.authManager.verified) {
            if (IRC.mc.field_71441_e != null && IRC.mc.field_71439_g != null) {
                Helper.sendMessage("[IRC] Failed to Verify Client User.");
                Helper.sendMessage("[AUTHENTICATION] Blocked Your Connection to the IRC Server.");
            }
            Client.instance.logger.error("[MelodySky] [IRC] Failed to Verify Client User.");
            Client.instance.logger.error("[MelodySky] [AUTHENTICATION] Blocked Your Connection to the IRC Server.");
            return;
        }
        if (pw == null) {
            Client.instance.logger.error("[MelodySky] [IRC] Unexpected Error.");
            Client.instance.ircExeption = true;
            return;
        }
        String string3 = "";
        String string4 = mc.func_110432_I().func_148256_e().getId().toString();
        String string5 = IRC.mc.field_71439_g.func_70005_c_();
        string3 = string4.contains("6ceb8943-c0cf-49e8-b416-ac7d8b60261e") ? "\u00a7d[MelodySky]\u00a7b" + string5 : (string4.contains("293e94c6-53b6-4876-bd82-771611b549a9") ? "\u00a76[\u7eb8]\u00a7b" + string5 : (string4.contains("222f316f-5ec8-4298-b98e-5ffe2f98a228") ? "\u00a7e[SMCP]\u00a7b" + string5 : (string4.contains("3d92223f-319a-4cbb-8eae-559a809d7598") ? "\u00a7b[\u742a\u4e9a\u5a1c]\u00a7b" + string5 : (string4.contains("ea1e1b9f-b3fb-4585-a623-abc147411b58") ? "\u00a72[\u72d7]\u00a7b" + string5 : (string4.contains("35bec0ee-5b7b-4e0e-8190-cda5eaed6456") ? "\u00a7z[\u6211\u4e0d\u662f\u866b\u795e]\u00a7b" + string5 : (string4.contains("d90df5f2-1d55-40e2-b765-28afd2c5fb0c") ? "\u00a73[user]\u00a7z" + string5 : "\u00a73[user]\u00a7b" + IRC.mc.field_71439_g.func_70005_c_()))))));
        if (Client.customRank != null) {
            string3 = "\u00a7b" + Client.customRank + "\u00a7b" + string5;
        }
        String string6 = "\u00a77Melody > " + string3 + "\u00a7r: " + string2;
        pw.println(string6);
    }

    public void disconnect() {
        if (socket != null && in != null && pw != null) {
            this.shouldThreadStop = true;
            try {
                this.sendMessage("CLOSE");
                socket.close();
                in.close();
                pw.close();
                socket = null;
                in = null;
                pw = null;
                if (IRC.mc.field_71441_e != null && IRC.mc.field_71439_g != null) {
                    Helper.sendMessage("Disconnected to IRC Server.");
                }
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        } else {
            Helper.sendMessage("Already Disconnected From IRC Server.");
        }
    }

    public void sendMessage(String string) {
        if (!Client.instance.authManager.verified) {
            if (IRC.mc.field_71441_e != null && IRC.mc.field_71439_g != null) {
                Helper.sendMessage("[IRC] Failed to Verify Client User.");
                Helper.sendMessage("[AUTHENTICATION] Blocked Your Connection to the IRC Server.");
            }
            Client.instance.logger.error("[MelodySky] [IRC] Failed to Verify Client User.");
            Client.instance.logger.error("[MelodySky] [AUTHENTICATION] Blocked Your Connection to the IRC Server.");
            return;
        }
        if (pw == null) {
            Client.instance.logger.error("[MelodySky] [IRC] Unexpected Error.");
            Client.instance.ircExeption = true;
            return;
        }
        pw.println(string);
    }

    public void handleInput() {
        byte[] byArray = new byte[1024];
        if (pw == null || in == null) {
            Client.instance.logger.error("[MelodySky] [IRC] Unexpected Error.");
            Client.instance.ircExeption = true;
            return;
        }
        try {
            if (!this.connected) {
                pw.println("CHAT");
            }
            int n = in.read(byArray);
            String string = new String(byArray, 0, n);
            string = string.replaceAll("\n", "");
            string = string.replaceAll("\r", "");
            if ((string = string.replaceAll("\t", "")).equals("REDIRECTED")) {
                Helper.sendMessage("IRC Connected.");
                this.connected = true;
                return;
            }
            if (!this.connected) {
                return;
            }
            if (string.startsWith("SREQ_USERINFO")) {
                Client.instance.preModHiderAliase(this.readLine());
                String string2 = "CINFO_" + Minecraft.func_71410_x().func_110432_I().func_111285_a() + "@" + mc.func_110432_I().func_148256_e().getId().toString() + "@" + this.readLine();
                pw.println(string2);
                return;
            }
            if (string.startsWith("RECEIVED_ALIVE")) {
                Client.instance.ircExeption = false;
                return;
            }
            if (string.startsWith("SREQ_KEEP_ALIVE")) {
                pw.println("KEEP_ALIVE");
                return;
            }
            if (!Client.instance.authManager.verified) {
                Client.instance.logger.error("[MelodySky] [IRC] DETECTED NON-VERIFIED USER, CONNECTION DENIED.");
                this.sendMessage("CLOSE");
                if (IRC.mc.field_71441_e != null && IRC.mc.field_71439_g != null) {
                    Helper.sendMessage("[IRC] Detected That You are a NONE-Verified User, so You Will not Connect to IRC Server.");
                }
                this.disconnect();
                return;
            }
            if (IRC.mc.field_71439_g != null && IRC.mc.field_71441_e != null) {
                IRC.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(string));
            }
            Client.instance.logger.info("[MelodySky] [IRC] " + string);
        }
        catch (IOException iOException) {
            Client.instance.ircExeption = true;
            Client.instance.logger.info("[MelodySky] [CONSOL] IRC Ran into an Exception.");
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            Client.instance.ircExeption = true;
            Client.instance.logger.info("[MelodySky] [CONSOL] IRC Ran into an Exception.");
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void connect(int n) {
        if (!Client.instance.authManager.verified) {
            if (IRC.mc.field_71441_e != null && IRC.mc.field_71439_g != null) {
                Helper.sendMessage("[IRC] Failed to Verify Client User.");
                Helper.sendMessage("[AUTHENTICATION] Blocked Your Connection to the IRC Server.");
            }
            Client.instance.logger.error("[MelodySky] [IRC] Failed to Verify Client User.");
            Client.instance.logger.error("[MelodySky] [AUTHENTICATION] Blocked Your Connection to the IRC Server.");
            this.disconnect();
            this.shouldThreadStop = true;
            return;
        }
        try {
            socket = new Socket(MSAC.idk, MSAC.pt);
            in = socket.getInputStream();
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            pw.println("CHAT");
            this.shouldThreadStop = false;
            if (IRC.mc.field_71439_g != null && IRC.mc.field_71441_e != null) {
                Helper.sendMessage("Connetion Established.");
            }
            Client.instance.logger.info("[MelodySky] [CONSOLE] Connetion Established!");
            Client.instance.ircExeption = false;
        }
        catch (IOException iOException) {
            if (IRC.mc.field_71439_g != null && IRC.mc.field_71441_e != null) {
                Helper.sendMessage("Failed to Connecting With IRC Server.");
            }
            Client.instance.ircExeption = true;
            iOException.printStackTrace();
        }
    }

    public void exit() {
        if (pw != null) {
            this.sendMessage("CLOSE");
        }
    }
}

