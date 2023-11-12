/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelBiped
 */
package xyz.Melody.injection.mixins.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import xyz.Melody.Client;
import xyz.Melody.module.modules.render.OldAnimations;

@Mixin(value={ModelBiped.class})
public class ModelBipedMixin
extends ModelBase {
    @ModifyConstant(method="setRotationAngles", constant=@Constant(floatValue=-0.5235988f))
    private float cancelRotation(float f) {
        OldAnimations oldAnimations = (OldAnimations)Client.instance.getModuleManager().getModuleByClass(OldAnimations.class);
        if (oldAnimations.isEnabled()) {
            return (Boolean)oldAnimations.oldBlockhitting.getValue() != false ? 0.0f : f;
        }
        return f;
    }
}

