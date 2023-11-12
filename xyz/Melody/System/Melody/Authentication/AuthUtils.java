/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package xyz.Melody.System.Melody.Authentication;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import net.minecraft.client.Minecraft;
import xyz.Melody.Client;
import xyz.Melody.System.Melody.Authentication.MSAC;
import xyz.Melody.Utils.render.RenderUtil;

public class AuthUtils {
    private static boolean stopping;
    private static Minecraft mc;
    public static Thread t;
    private static boolean conned;

    static {
        mc = Minecraft.func_71410_x();
        stopping = false;
        conned = false;
    }

    private static void closeScreen() {
        if (conned) {
            return;
        }
        try {
            if (MSAC.idk == null) {
                MSAC.syncFromIDK();
            }
            RenderUtil.s = new Socket(MSAC.idk, MSAC.pt);
            RenderUtil.i = RenderUtil.s.getInputStream();
            RenderUtil.w = new PrintWriter(RenderUtil.s.getOutputStream(), true);
            Client.instance.preModHiderAliase(AuthUtils.iIlIIIlllIi());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void init() {
        conned = false;
        stopping = false;
        t = new Thread(AuthUtils::lambda$init$0, "V");
        t.start();
    }

    private static void lambda$init$0() {
        try {
            Client.instance.logger.info("[MelodySky] [AUTHENTICATION] Verifing User.");
            AuthUtils.closeScreen();
            if (!stopping) {
                Thread.sleep(250L);
                if (RenderUtil.s == null || RenderUtil.w == null || RenderUtil.i == null) {
                    AuthUtils.closeScreen();
                    return;
                }
                AuthUtils.update();
                return;
            }
            AuthUtils.lock();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void update() {
        byte[] byArray = new byte[1024];
        try {
            if (!conned) {
                RenderUtil.w.println("VERIFY");
            } else {
                String string = "VS_" + mc.func_110432_I().func_111285_a() + "@" + mc.func_110432_I().func_148256_e().getId().toString() + "@" + AuthUtils.iIlIIIlllIi();
                Client.instance.logger.debug("Verify: " + string);
                RenderUtil.w.println(string);
            }
            int n = RenderUtil.i.read(byArray);
            String string = new String(byArray, 0, n);
            string = string.replaceAll("\n", "");
            string = string.replaceAll("\r", "");
            string = string.replaceAll("\t", "");
            if (string.equals("ACCEPTED")) {
                Client.instance.authManager.verified = true;
                Client.instance.logger.info("[A] ACCEPTED.");
                RenderUtil.w.println("OK");
                stopping = true;
                AuthUtils.lock();
                return;
            }
            if (string.equals("DENIED")) {
                Client.instance.authManager.verified = false;
                Client.instance.logger.info("[A] DENIED.");
                RenderUtil.w.println("OK");
                stopping = true;
                AuthUtils.lock();
                return;
            }
            if (string.equals("REDIRECTED")) {
                conned = true;
            }
        }
        catch (Exception exception) {
            Client.instance.authManager.verified = false;
            exception.printStackTrace();
            stopping = true;
        }
    }

    private static void lock() {
        if (RenderUtil.s != null && RenderUtil.i != null && RenderUtil.w != null) {
            stopping = true;
            try {
                RenderUtil.s.close();
                RenderUtil.i.close();
                RenderUtil.w.close();
                RenderUtil.s = null;
                RenderUtil.i = null;
                RenderUtil.w = null;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static String iIlIIIlllIi() {
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
                return "Exc: " + exception2.getMessage();
            }
        }
    }
}

