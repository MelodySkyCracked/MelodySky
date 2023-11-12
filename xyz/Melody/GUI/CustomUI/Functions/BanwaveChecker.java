/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.GUI.CustomUI.Functions;

import java.awt.Color;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.GUI.CustomUI.HUDApi;
import xyz.Melody.GUI.CustomUI.HUDScreen;
import xyz.Melody.GUI.Font.CFontRenderer;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.Utils.JsonUtils;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;

public final class BanwaveChecker
extends HUDApi {
    private boolean startUp = true;
    private TimerUtil timer = new TimerUtil();
    private String staffBans = "non";

    public BanwaveChecker() {
        super("banwave", 100, 150);
        this.setEnabled(true);
    }

    @EventHandler
    public void onRender(EventRender2D eventRender2D) {
        if (this.mc.field_71462_r instanceof HUDScreen) {
            return;
        }
        this.renderBans();
    }

    @Override
    public void InScreenRender() {
        this.renderBans();
    }

    @EventHandler
    public final void tick(EventTick eventTick) {
        if (this.timer.hasReached(60000.0) || this.startUp) {
            this.startUp = false;
            new Thread(this::lambda$tick$0, "BanChecker").start();
            this.timer.reset();
        }
    }

    public String getStaffBans() {
        return this.staffBans;
    }

    private void renderBans() {
        CFontRenderer cFontRenderer = FontLoaders.NMSL20;
        String string = "Fechting Data...";
        try {
            string = this.staffBans.equals("non") ? "Fetching Data..." : (Integer.parseInt(this.staffBans) >= 15 ? "\u00a7c\u00a7l" : "");
        }
        catch (Exception exception) {
            string = "Exception Thrown: " + exception.getMessage();
        }
        int n = string != "" ? 17 : 0;
        RenderUtil.drawFastRoundedRect(this.x, this.y, this.x + cFontRenderer.getStringWidth(string + this.getBanDisplay()) + 8 + n, this.y + cFontRenderer.getHeight() + 6, 1.0f, new Color(30, 30, 30, 140).getRGB());
        FontLoaders.NMSL20.drawString(string + this.getBanDisplay(), this.x + 4, this.y + 4, -1);
    }

    private void fetch() {
        try {
            this.staffBans = JsonUtils.readFromUrl("https://cst.msirp.cn/api/user/module/cheatstats/15min.json").get("staff_stats").getAsString();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public String getBanDisplay() {
        return this.staffBans != null ? "Staff bans in last 15 Minutes: " + this.staffBans : "\u00a7bFetching data...";
    }

    private void lambda$tick$0() {
        this.fetch();
    }
}

