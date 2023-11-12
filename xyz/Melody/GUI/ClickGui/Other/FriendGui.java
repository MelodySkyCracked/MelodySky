/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.input.Mouse
 */
package xyz.Melody.GUI.ClickGui.Other;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import xyz.Melody.Client;
import xyz.Melody.GUI.Animate.Opacity;
import xyz.Melody.GUI.ClickGui.DickGui;
import xyz.Melody.GUI.ClickGui.Utils.TextField;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.GUI.Notification.NotificationPublisher;
import xyz.Melody.GUI.Notification.NotificationType;
import xyz.Melody.System.Managers.Client.FriendManager;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.render.Scissor;

public class FriendGui {
    private Minecraft mc = Minecraft.func_71410_x();
    private int mouseWheel = 0;
    private float startX = 0.0f;
    private float startY = 0.0f;
    private float endX = 0.0f;
    private float endY = 0.0f;
    private Opacity speedTranslator = new Opacity(0);
    private Opacity verticalTranslator = new Opacity(0);
    private float vertical = 0.0f;
    private String selectedName = "";
    private TextField nameField = new TextField(0, "name", 0, 0, 130, 20);
    private DickGui dick;

    public FriendGui(DickGui dickGui) {
        Client.instance.config.miscConfig.read();
        this.dick = dickGui;
    }

    public void draw(int n, int n2, int n3, int n4) {
        this.startX = n += 100;
        this.startY = n2 += 45;
        this.endX = n3 -= 5;
        this.endY = n4;
        int n5 = (n3 + n) / 2;
        RenderUtil.drawFastRoundedRect(n, n2 + 5, n5, n4 - 15, 5.0f, new Color(20, 20, 20, 80).getRGB());
        RenderUtil.outlineRect(n, n2 + 5, n5, n4 - 15, Colors.AQUA.c, 5.0f, 3.0f);
        RenderUtil.drawFastRoundedRect(n5 + 15, n2 + 5, n3 - 20, n2 + 120, 5.0f, new Color(100, 100, 100, 80).getRGB());
        FontLoaders.NMSL20.drawString("Selected: " + this.selectedName, n5 + 30, n2 + 40, -1);
        this.nameField.drawTextBox(n5 + 30, n2 + 60);
        RenderUtil.drawFastRoundedRect(n5 + 30, n2 + 90, n5 + 90, n2 + 110, 5.0f, new Color(20, 20, 20, 80).getRGB());
        FontLoaders.NMSL20.drawString("Add", n5 + 50, n2 + 97, -1);
        RenderUtil.drawFastRoundedRect(n5 + 100, n2 + 90, n5 + 160, n2 + 110, 5.0f, new Color(20, 20, 20, 80).getRGB());
        FontLoaders.NMSL20.drawString("Remove", n5 + 113, n2 + 97, -1);
        RenderUtil.drawFastRoundedRect(n5 + 15, n2 + 5, n3 - 20, n2 + 25, 5.0f, new Color(20, 20, 20, 80).getRGB());
        FontLoaders.CNMD26.drawString("<- Back", n5 + 25, n2 + 9, -1);
        int n6 = new ScaledResolution(this.mc).func_78325_e();
        Scissor.start(n * n6, (n2 + 5) * n6, n5 * n6, (n4 - 15) * n6);
        if (FriendManager.getFriends().keySet().isEmpty()) {
            FontLoaders.NMSL20.drawCenteredString("Empty.", n + n5 / 4 - 5, (n2 + n4) / 2 - 20, -1);
        } else {
            int n7 = (int)this.verticalTranslator.getOpacity();
            for (String string : FriendManager.getFriends().keySet()) {
                if (string.equals(this.selectedName)) {
                    RenderUtil.outlineRect(n + 3, this.startY + 8.0f + (float)n7, n5 - 3, this.startY + 8.0f + (float)n7 + 20.0f, Colors.BLUE.c, 5.0f, 3.0f);
                }
                FontLoaders.NMSL20.drawString(string, n + 10, n2 + 15 + n7, -1);
                n7 += 20;
            }
        }
        Scissor.end();
    }

    public void onMouseClick(double d, double d2, int n) {
        int n2 = (int)this.verticalTranslator.getOpacity();
        float f = (this.endX + this.startX) / 2.0f;
        if (d > (double)this.startX && d < (double)f) {
            for (String string : FriendManager.getFriends().keySet()) {
                if (d2 > (double)(this.startY + 8.0f + (float)n2) && d2 < (double)(this.startY + 8.0f + (float)n2 + 20.0f) && d2 > (double)(this.startY + 5.0f)) {
                    this.selectedName = string;
                }
                n2 += 20;
            }
        }
        if (n == 3) {
            return;
        }
        if (d > (double)(f + 15.0f) && d < (double)(this.endX - 20.0f) && d2 > (double)(this.startY + 5.0f) && d2 < (double)(this.startY + 25.0f)) {
            this.dick.curType = DickGui.clickType.ClickGui;
        }
        if (d2 > (double)(this.startY + 90.0f) && d2 < (double)(this.startY + 110.0f)) {
            if (d > (double)(f + 30.0f) && d < (double)(f + 90.0f)) {
                if (this.nameField.func_146179_b().contains(" ")) {
                    NotificationPublisher.queue("Warn", "Player name can not contains space.", NotificationType.ERROR, 5000);
                    this.nameField.func_146180_a("");
                    return;
                }
                if (this.nameField.func_146179_b().equals("")) {
                    NotificationPublisher.queue("Warn", "Player name should not be empty.", NotificationType.ERROR, 5000);
                    this.nameField.func_146180_a("");
                    return;
                }
                FriendManager.getFriends().put(this.nameField.func_146179_b(), this.nameField.func_146179_b().toLowerCase());
                NotificationPublisher.queue("Success", "Added " + this.nameField.func_146179_b() + " to your friend list.", NotificationType.SUCCESS, 5000);
                this.nameField.func_146180_a("");
                Client.instance.config.miscConfig.save();
            } else if (d > (double)(f + 100.0f) && d < (double)(f + 160.0f)) {
                if (this.selectedName == null || this.selectedName.equals("")) {
                    NotificationPublisher.queue("Warn", "Select a name first.", NotificationType.ERROR, 5000);
                    return;
                }
                FriendManager.getFriends().remove(this.selectedName);
                NotificationPublisher.queue("Success", "Removed " + this.selectedName + " successfully.", NotificationType.SUCCESS, 5000);
                this.selectedName = "";
                Client.instance.config.miscConfig.save();
            }
        }
        this.nameField.func_146192_a((int)d, (int)d2, n);
    }

    public void handleMouseScroll(float f, float f2) {
        this.speedTranslator.interp(0.0f, 0.0f);
        this.verticalTranslator.interp(this.vertical, this.speedTranslator.getOpacity());
        float f3 = (this.endX + this.startX) / 2.0f;
        if (!(f > this.startX && f < f3 && f2 > this.startY + 5.0f && f2 < this.endY - 15.0f)) {
            return;
        }
        this.mouseWheel = Mouse.getDWheel();
        if (this.mouseWheel < 0 && this.vertical < (float)(FriendManager.getFriends().keySet().size() - 1)) {
            this.vertical -= 10.0f;
            this.speedTranslator.setOpacity(this.speedTranslator.getOpacity() * 0.5f + 4.0f);
        }
        if (this.mouseWheel > 0 && this.vertical < 0.0f) {
            this.vertical += 10.0f;
            this.speedTranslator.setOpacity(this.speedTranslator.getOpacity() * 0.5f + 4.0f);
        }
    }

    public void onCharTyped(char c, int n) {
        this.nameField.func_146201_a(c, n);
    }
}

