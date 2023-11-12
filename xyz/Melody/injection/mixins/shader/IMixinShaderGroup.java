/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.shader.ShaderGroup
 */
package xyz.Melody.injection.mixins.shader;

import java.util.List;
import net.minecraft.client.shader.ShaderGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ShaderGroup.class})
public interface IMixinShaderGroup {
    @Accessor(value="listShaders")
    public List getListShaders();
}

