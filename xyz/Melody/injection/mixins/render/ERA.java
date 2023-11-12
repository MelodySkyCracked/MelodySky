/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.EntityRenderer
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.shader.ShaderGroup
 */
package xyz.Melody.injection.mixins.render;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.shader.ShaderGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={EntityRenderer.class})
public interface ERA {
    @Accessor(value="torchFlickerX")
    public float getTorchFlickerX();

    @Accessor(value="lightmapColors")
    public int[] getLightmapColors();

    @Accessor(value="lightmapTexture")
    public DynamicTexture getLightmapTexture();

    @Accessor(value="theShaderGroup")
    public void setShaderGroup(ShaderGroup var1);
}

