/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.ChatStyle
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.IChatComponent
 */
package xyz.Melody.Utils.game;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public final class ChatUtils {
    private final ChatComponentText message;

    private ChatUtils(ChatComponentText chatComponentText) {
        this.message = chatComponentText;
    }

    public static String addFormat(String string, String string2) {
        return string.replaceAll("(?i)" + string2 + "([0-9a-fklmnor])", "\u00a7$1");
    }

    public void displayClientSided() {
        if (Minecraft.func_71410_x().field_71441_e != null && Minecraft.func_71410_x().field_71439_g != null) {
            Minecraft.func_71410_x().field_71439_g.func_145747_a((IChatComponent)this.message);
        }
    }

    private ChatComponentText getChatComponent() {
        return this.message;
    }

    ChatUtils(ChatComponentText chatComponentText, ChatUtils chatUtils) {
        this(chatComponentText);
    }

    static ChatComponentText access$000(ChatUtils chatUtils) {
        return chatUtils.getChatComponent();
    }

    public static class ChatMessageBuilder {
        private static final EnumChatFormatting defaultMessageColor = EnumChatFormatting.WHITE;
        private ChatComponentText theMessage = new ChatComponentText("");
        private boolean useDefaultMessageColor = false;
        private ChatStyle workingStyle = new ChatStyle();
        private ChatComponentText workerMessage = new ChatComponentText("");

        public ChatMessageBuilder(boolean bl, boolean bl2) {
            if (bl) {
                this.theMessage.func_150257_a((IChatComponent)ChatUtils.access$000(new ChatMessageBuilder(false, false).appendText(((StringBuilder)((Object)(EnumChatFormatting.AQUA + "Melody > "))).toString()).setColor(EnumChatFormatting.RED).build()));
            }
            this.useDefaultMessageColor = bl2;
        }

        public ChatMessageBuilder() {
        }

        public ChatMessageBuilder appendText(String string) {
            this.appendSibling();
            this.workerMessage = new ChatComponentText(string);
            this.workingStyle = new ChatStyle();
            if (this.useDefaultMessageColor) {
                this.setColor(defaultMessageColor);
            }
            return this;
        }

        public ChatMessageBuilder setColor(EnumChatFormatting enumChatFormatting) {
            this.workingStyle.func_150238_a(enumChatFormatting);
            return this;
        }

        public ChatMessageBuilder bold() {
            this.workingStyle.func_150227_a(Boolean.valueOf(true));
            return this;
        }

        public ChatMessageBuilder italic() {
            this.workingStyle.func_150217_b(Boolean.valueOf(true));
            return this;
        }

        public ChatMessageBuilder strikethrough() {
            this.workingStyle.func_150225_c(Boolean.valueOf(true));
            return this;
        }

        public ChatMessageBuilder underline() {
            this.workingStyle.func_150228_d(Boolean.valueOf(true));
            return this;
        }

        public ChatUtils build() {
            this.appendSibling();
            return new ChatUtils(this.theMessage, null);
        }

        private void appendSibling() {
            this.theMessage.func_150257_a(this.workerMessage.func_150255_a(this.workingStyle));
        }
    }
}

