/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$MouseInputEvent
 *  org.lwjgl.input.Mouse
 */
package xyz.Melody.GUI.CustomUI.Functions;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.GUI.CustomUI.HUDApi;
import xyz.Melody.GUI.CustomUI.HUDScreen;
import xyz.Melody.GUI.Font.CFontRenderer;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.Utils.render.RenderUtil;

public final class RCPS
extends HUDApi {
    private ArrayList clicks = new ArrayList();

    public RCPS() {
        super("RCPS", 5, 30);
        this.setEnabled(true);
    }

    @SubscribeEvent
    public void onClick(InputEvent.MouseInputEvent mouseInputEvent) {
        if (Mouse.getEventButtonState() && this.mc.field_71474_y.field_74313_G.func_151470_d() && Mouse.getEventButton() == this.mc.field_71474_y.field_74313_G.func_151463_i() + 100) {
            this.clicks.add(System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onRender(EventRender2D eventRender2D) {
        if (this.mc.field_71462_r instanceof HUDScreen) {
            return;
        }
        this.cpsRender();
    }

    @Override
    public void InScreenRender() {
        this.cpsRender();
    }

    private void cpsRender() {
        int n = new Color(30, 30, 30, 100).getRGB();
        CFontRenderer cFontRenderer = FontLoaders.NMSL20;
        RenderUtil.drawFastRoundedRect(this.x, this.y, this.x + cFontRenderer.getStringWidth("RCPS: " + this.getRightCPS()) + 8, this.y + cFontRenderer.getStringHeight("RCPS: " + this.getRightCPS()) + 6, 1.0f, n);
        FontLoaders.NMSL20.drawString("RCPS: " + this.getRightCPS(), this.x + 4, this.y + 4, -1);
    }

    public int getRightCPS() {
        long l2 = System.currentTimeMillis();
        this.clicks.removeIf(arg_0 -> RCPS.lambda$getRightCPS$0(l2, arg_0));
        return this.clicks.size();
    }

    private static boolean lambda$getRightCPS$0(long l2, Long l3) {
        return l3 + 1000L < l2;
    }
}

