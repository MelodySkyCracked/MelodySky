/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 */
package xyz.Melody.GUI.Notification;

import net.minecraft.util.ResourceLocation;
import xyz.Melody.Utils.Colors;

public enum NotificationType {
    SUCCESS(Colors.GREEN.c, new ResourceLocation("Melody/noti/success.png")),
    INFO(Colors.GRAY.c, new ResourceLocation("Melody/noti/info.png")),
    WARN(Colors.YELLOW.c, new ResourceLocation("Melody/noti/warning.png")),
    ERROR(Colors.RED.c, new ResourceLocation("Melody/noti/error.png"));

    private final int color;
    private final ResourceLocation icon;

    public ResourceLocation getIcon() {
        return this.icon;
    }

    /*
     * WARNING - void declaration
     */
    private NotificationType() {
        void var4_2;
        void var3_1;
        this.color = var3_1;
        this.icon = var4_2;
    }

    public final int getColor() {
        return this.color;
    }
}

