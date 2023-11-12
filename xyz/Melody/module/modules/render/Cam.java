/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 */
package xyz.Melody.module.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class Cam
extends Module {
    private static Cam INSTANCE;
    public Option chilePlayers = new Option("ChildPlayers", false);
    public Option name = new Option("Show Self Name", true);
    public Option showRank = new Option("Show Custom Rank", true);
    public Option colorHurtCam = new Option("ColorHurtCam", true);
    public Option bht = new Option("NoHurtCam", true);
    public Option noFire = new Option("NoFireRender", false);
    public Option noBlindness = new Option("NoBlindness", true);
    public Option noClip = new Option("NoClip", false);
    private float eyeHeight = 0.0f;

    public Cam() {
        super("Camera", ModuleType.Render);
        this.addValues(this.chilePlayers, this.name, this.showRank, this.bht, this.colorHurtCam, this.noFire, this.noBlindness, this.noClip);
        this.setModInfo("Better Camera Render.");
        this.setEnabled(true);
    }

    public static Cam getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (Cam)Client.instance.getModuleManager().getModuleByClass(Cam.class);
        }
        return INSTANCE;
    }

    @EventHandler
    private void onHurt(EventRender2D eventRender2D) {
        if (((Boolean)this.chilePlayers.getValue()).booleanValue() && this.mc.field_71474_y.field_74320_O != 0) {
            if (this.eyeHeight == 0.0f) {
                this.eyeHeight = this.mc.field_71439_g.eyeHeight;
            }
            this.mc.field_71439_g.eyeHeight = 0.9f;
        } else {
            if (this.eyeHeight != 0.0f) {
                this.mc.field_71439_g.eyeHeight = this.eyeHeight;
            }
            this.eyeHeight = 0.0f;
        }
        if (!((Boolean)this.bht.getValue()).booleanValue()) {
            return;
        }
        if (!((Boolean)this.colorHurtCam.getValue()).booleanValue()) {
            return;
        }
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
        if (this.mc.field_71439_g.field_70737_aN > 0) {
            RenderUtil.drawBorderedRect(0.0f, 0.0f, scaledResolution.func_78326_a(), scaledResolution.func_78328_b(), 10.0f, new Color(25 * this.mc.field_71439_g.field_70737_aN, 20, 20, 20 * this.mc.field_71439_g.field_70737_aN).getRGB());
        }
    }

    @Override
    public void onDisable() {
        this.setEnabled(true);
        super.onDisable();
    }
}

