/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.EnumChatFormatting
 */
package xyz.Melody.GUI.CustomUI.Functions;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.GUI.CustomUI.HUDApi;
import xyz.Melody.GUI.CustomUI.HUDScreen;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.game.PlayerListUtils;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.shader.GaussianBlur;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.modules.others.PlayerList;

public final class NPlayerList
extends HUDApi {
    private TimerUtil timer = new TimerUtil();
    private Map playerList = new HashMap();
    private GaussianBlur blur = new GaussianBlur();
    private int length = 0;
    private float range = 0.0f;
    private int oof = 0;

    public NPlayerList() {
        super("PlayerList", 300, 300);
        this.setEnabled(true);
    }

    @Override
    public void InScreenRender() {
        this.render();
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        PlayerList playerList = PlayerList.getINSTANCE();
        if (!playerList.isEnabled()) {
            this.playerList.clear();
            return;
        }
        if (this.timer.hasReached((Double)playerList.scanDelay.getValue())) {
            this.range = ((Double)playerList.range.getValue()).floatValue();
            this.playerList = this.getPlayersIn(this.range);
            this.timer.reset();
        }
    }

    @EventHandler
    private void onR2D(EventRender2D eventRender2D) {
        if (this.playerList.isEmpty()) {
            return;
        }
        if (this.mc.field_71462_r instanceof HUDScreen) {
            return;
        }
        this.render();
    }

    private void render() {
        float f = 5.0f;
        float f2 = 6.0f;
        RenderUtil.drawFastRoundedRect(this.x, this.y, this.x + this.length + 20, this.y + this.oof * 14 + 22, 5.0f, new Color(20, 20, 20, 50).getRGB());
        RenderUtil.outlineRect(this.x, this.y + 1, this.x + this.length + 20, this.y + this.oof * 14 + 22, new Color(90, 90, 90, 80).getRGB(), 5.0f, 3.0f);
        FontLoaders.CNMD22.drawString("Players Within " + this.range + " Blocks", (float)this.x + f, (float)this.y + f2, Colors.ORANGE.c);
        this.oof = 0;
        this.length = FontLoaders.CNMD22.getStringWidth("Players Within " + this.range + " Blocks");
        for (String string : this.playerList.keySet()) {
            float f3 = ((Float)this.playerList.get(string)).floatValue();
            EnumChatFormatting enumChatFormatting = f3 <= 15.0f ? EnumChatFormatting.RED : EnumChatFormatting.AQUA;
            String string2 = " -" + string + " " + enumChatFormatting + String.format("%.2f", Float.valueOf(f3)) + "m";
            FontLoaders.CNMD20.drawString(string2, (float)this.x + f + 6.0f, (float)(this.y + this.oof * 14 + 16) + f2, -1);
            if (this.length < FontLoaders.CNMD20.getStringWidth(string2)) {
                this.length = FontLoaders.CNMD20.getStringWidth(string2);
            }
            ++this.oof;
        }
    }

    private Map getPlayersIn(float f) {
        HashMap<String, Float> hashMap = new HashMap<String, Float>();
        for (EntityPlayer entityPlayer : this.mc.field_71441_e.field_73010_i) {
            float f2 = this.mc.field_71439_g.func_70032_d((Entity)entityPlayer);
            if (!(f2 <= f) || entityPlayer == this.mc.field_71439_g || entityPlayer.func_70005_c_() == null || !PlayerListUtils.isInTablist(entityPlayer)) continue;
            hashMap.put(entityPlayer.func_145748_c_().func_150260_c(), Float.valueOf(f2));
        }
        return hashMap;
    }
}

