/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;
import javax.imageio.ImageIO;
import xyz.Melody.Client;
import xyz.Melody.Utils.I;

public class WindowsNotification {
    private static Image image;
    private static TrayIcon trayIcon;
    private static SystemTray tray;
    private static int num;

    public static void init() {
        new Thread(WindowsNotification::lambda$init$0, "Icon Controller").start();
    }

    private static void animate() throws Exception {
        Thread.sleep(60L);
        if (num == 16) {
            num = 0;
        }
        if (num == 1) {
            Thread.sleep(2000L);
        }
        image = ImageIO.read(Client.class.getResource("/assets/minecraft/Melody/wi/ic (" + num + ").png"));
        tray.getTrayIcons()[0].setImage(image);
        ++num;
    }

    public static void show(String string, String string2) {
        WindowsNotification.displayTray(string, string2, TrayIcon.MessageType.INFO);
    }

    public static void show(String string, String string2, TrayIcon.MessageType messageType) {
        if (SystemTray.isSupported()) {
            WindowsNotification.displayTray(string, string2, messageType);
        } else {
            Client.instance.logger.error("System tray not supported!");
        }
    }

    private static void displayTray(String string, String string2, TrayIcon.MessageType messageType) {
        try {
            image = ImageIO.read(Client.class.getResource("/assets/minecraft/Melody/wi/ic (0).png"));
            tray.getTrayIcons()[0].setImage(image);
            trayIcon.displayMessage(string, string2, messageType);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public static void stop() {
        if (trayIcon != null && tray != null) {
            tray.remove(trayIcon);
        }
    }

    private static void lambda$init$0() {
        try {
            image = ImageIO.read(Client.class.getResource("/assets/minecraft/Melody/wi/ic (0).png"));
            trayIcon = new TrayIcon(image);
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("MelodySky");
            trayIcon.addMouseListener(new I());
            tray.add(trayIcon);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            while (true) {
                WindowsNotification.animate();
            }
        }
        catch (Exception exception) {
            return;
        }
    }

    static {
        tray = SystemTray.getSystemTray();
        num = 0;
    }
}

