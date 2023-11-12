/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonSyntaxException
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.shader.Shader
 *  net.minecraft.client.shader.ShaderGroup
 *  net.minecraft.client.shader.ShaderUniform
 *  net.minecraft.util.ResourceLocation
 */
package xyz.Melody.module.modules.render;

import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.util.ResourceLocation;
import xyz.Melody.Client;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.injection.mixins.shader.IMixinShaderGroup;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class MotionBlur
extends Module {
    private ResourceLocation location = new ResourceLocation("minecraft:shaders/post/motion_blur.json");
    private static MotionBlur INSTANCE;
    private Minecraft mc = Minecraft.func_71410_x();
    private ShaderGroup shader;
    private float shaderBlur;
    public Numbers size = new Numbers("Blur Amount", 5.0, 1.0, 10.0, 0.5);

    public MotionBlur() {
        super("MotionBlur", ModuleType.Render);
        this.addValues(this.size);
    }

    public static MotionBlur getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (MotionBlur)Client.instance.getModuleManager().getModuleByClass(MotionBlur.class);
        }
        return INSTANCE;
    }

    public ShaderGroup getShader() {
        float f;
        float f2;
        if (this.shader == null) {
            this.shaderBlur = Float.NaN;
            try {
                this.shader = new ShaderGroup(this.mc.func_110434_K(), this.mc.func_110442_L(), this.mc.func_147110_a(), this.location);
                this.shader.func_148026_a(this.mc.field_71443_c, this.mc.field_71440_d);
            }
            catch (JsonSyntaxException | IOException throwable) {
                Client.instance.logger.error("Could not load motion blur shader", throwable);
                return null;
            }
        }
        if ((f2 = ((Double)this.size.getValue()).floatValue() / 10.0f) >= 0.95f) {
            f2 = 0.95f;
        }
        if (this.shaderBlur != (f = 1.0f - f2)) {
            ((IMixinShaderGroup)this.shader).getListShaders().forEach(arg_0 -> MotionBlur.lambda$getShader$0(f, arg_0));
            this.shaderBlur = f;
        }
        return this.shader;
    }

    private static void lambda$getShader$0(float f, Shader shader) {
        ShaderUniform shaderUniform = shader.func_148043_c().func_147991_a("Phosphor");
        if (shaderUniform != null) {
            shaderUniform.func_148095_a(f, 0.0f, 0.0f);
        }
    }
}

