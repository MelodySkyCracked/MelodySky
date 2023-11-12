/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.opengl.GL20
 */
package xyz.Melody.GUI.ShaderBG.Shaders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL20;
import xyz.Melody.GUI.Animate.Opacity;
import xyz.Melody.GUI.ShaderBG.Shader;
import xyz.Melody.Utils.timer.TimerUtil;

public final class BackgroundShader
extends Shader {
    public static final BackgroundShader BACKGROUND_SHADER = new BackgroundShader();
    private TimerUtil opLimit = new TimerUtil();
    private Opacity op = new Opacity(0);

    public BackgroundShader() {
        super("pixelBG.frag");
    }

    @Override
    public void setupUniforms() {
        this.setupUniform("iResolution");
        this.setupUniform("time");
        this.setupUniform("iMouse");
    }

    @Override
    public void updateUniforms() {
        int n;
        int n2;
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
        int n3 = this.getUniform("iResolution");
        if (n3 > -1) {
            GL20.glUniform2f((int)n3, (float)((float)scaledResolution.func_78326_a() * 2.0f), (float)((float)scaledResolution.func_78328_b() * 2.0f));
        }
        if ((n2 = this.getUniform("iMouse")) > -1) {
            GL20.glUniform4f((int)n2, (float)(this.op.getOpacity() * 10.0f), (float)((float)new ScaledResolution(Minecraft.func_71410_x()).func_78328_b() * 1.2f), (float)0.0f, (float)0.0f);
        }
        if ((n = this.getUniform("time")) > -1) {
            GL20.glUniform1f((int)n, (float)this.op.getOpacity());
        }
        if (this.opLimit.hasReached(50.0)) {
            this.opLimit.reset();
            this.op.interp(this.op.getOpacity() + 0.05f, 0.01f);
        }
    }
}

