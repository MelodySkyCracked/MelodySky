/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumChatFormatting
 */
package xyz.Melody.GUI.CustomUI.Functions;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.util.EnumChatFormatting;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.GUI.CustomUI.HUDApi;
import xyz.Melody.GUI.CustomUI.HUDScreen;
import xyz.Melody.GUI.Font.CFontRenderer;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.System.Managers.Skyblock.Area.SkyblockArea;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.game.PlayerListUtils;
import xyz.Melody.Utils.render.RenderUtil;

public final class MiningOverlay
extends HUDApi {
    private ArrayList clicks = new ArrayList();
    private String mithrilPowder = null;
    private String gemstonePowder = null;
    private int tickTimer = 0;

    public MiningOverlay() {
        super("MiningOverlay", 50, 50);
        this.setEnabled(true);
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        SkyblockArea skyblockArea = Client.instance.sbArea;
        if (this.tickTimer <= 20) {
            ++this.tickTimer;
            return;
        }
        if (skyblockArea.getCurrentArea() != Areas.Dwarven_Mines && skyblockArea.getCurrentArea() != Areas.Crystal_Hollows) {
            this.mithrilPowder = "Failed to Fetch Mithril Powder Info From The Player List.";
            this.gemstonePowder = "Failed to Fetch Gemstone Powder Info From The Player List.";
            return;
        }
        if (PlayerListUtils.tabContains("Mithril Powder:")) {
            this.mithrilPowder = PlayerListUtils.copyContainsLine("Mithril Powder:");
            this.mithrilPowder.replaceFirst(" ", "");
        } else {
            this.mithrilPowder = "Failed to Fetch Mithril Powder Info From The Player List.";
        }
        if (PlayerListUtils.tabContains("Gemstone Powder:")) {
            this.gemstonePowder = PlayerListUtils.copyContainsLine("Gemstone Powder:");
            this.gemstonePowder.replaceFirst(" ", "");
        } else {
            this.gemstonePowder = "Failed to Fetch Gemstone Powder Info From The Player List.";
        }
        this.tickTimer = 0;
    }

    @EventHandler
    public void onRender(EventRender2D eventRender2D) {
        if (this.mc.field_71462_r instanceof HUDScreen) {
            return;
        }
        SkyblockArea skyblockArea = Client.instance.sbArea;
        if (skyblockArea.getCurrentArea() == Areas.Dwarven_Mines || skyblockArea.getCurrentArea() == Areas.Crystal_Hollows) {
            this.render();
        }
    }

    @Override
    public void InScreenRender() {
        this.render();
    }

    private void render() {
        int n = new Color(30, 30, 30, 170).getRGB();
        CFontRenderer cFontRenderer = FontLoaders.NMSL20;
        int n2 = Math.max(cFontRenderer.getStringWidth(this.mithrilPowder), cFontRenderer.getStringWidth(" Pickaxe CD Ready: " + Client.pickaxeAbilityReady));
        int n3 = Math.max(n2, cFontRenderer.getStringWidth(this.gemstonePowder));
        RenderUtil.drawFastRoundedRect(this.x, this.y, this.x + n3 + 10, this.y + 43, 1.0f, n);
        RenderUtil.drawBorderedRect(this.x, this.y, this.x + n3 + 10, this.y + 43, 1.5f, Client.pickaxeAbilityReady ? Colors.GREEN.c : Colors.RED.c);
        FontLoaders.NMSL20.drawString(this.mithrilPowder, this.x + 2, this.y + 5, -1);
        FontLoaders.NMSL20.drawString(this.gemstonePowder, this.x + 2, this.y + 18, -1);
        FontLoaders.NMSL20.drawString(" Pickaxe CD Ready: " + (Client.pickaxeAbilityReady ? EnumChatFormatting.GREEN + "true" : EnumChatFormatting.RED + "false"), this.x + 2, this.y + 31, -1);
    }

    public int getRightCPS() {
        long l2 = System.currentTimeMillis();
        this.clicks.removeIf(arg_0 -> MiningOverlay.lambda$getRightCPS$0(l2, arg_0));
        return this.clicks.size();
    }

    private static boolean lambda$getRightCPS$0(long l2, Long l3) {
        return l3 + 1000L < l2;
    }
}

