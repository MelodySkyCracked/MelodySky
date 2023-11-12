/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.IChatComponent
 */
package xyz.Melody.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import xyz.Melody.Client;
import xyz.Melody.Utils.game.ChatUtils;

public final class Helper {
    public static Minecraft mc = Minecraft.func_71410_x();

    public static void sendMessageOLD(String string) {
        Object[] objectArray = new Object[2];
        Client.instance.getClass();
        objectArray[0] = EnumChatFormatting.BLUE + "Melody" + EnumChatFormatting.GRAY + ": ";
        objectArray[1] = string;
        Helper.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(String.format("%s%s", objectArray)));
    }

    public static void sendMessage(Object object) {
        new ChatUtils.ChatMessageBuilder(true, true).appendText(object + "").setColor(EnumChatFormatting.GRAY).build().displayClientSided();
    }

    public static void sendMessageWithoutPrefix(Object object) {
        new ChatUtils.ChatMessageBuilder(false, true).appendText(object + "").setColor(EnumChatFormatting.GRAY).build().displayClientSided();
    }

    public static boolean onServer(String string) {
        return !mc.func_71356_B() && Helper.mc.func_147104_D().field_78845_b.toLowerCase().contains(string);
    }
}

