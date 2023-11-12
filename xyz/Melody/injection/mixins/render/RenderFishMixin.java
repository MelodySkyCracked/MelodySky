/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.RenderFish
 *  net.minecraft.util.Vec3
 */
package xyz.Melody.injection.mixins.render;

import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.Melody.System.Animations.AnimationHandler;
import xyz.Melody.module.modules.render.OldAnimations;

@Mixin(value={RenderFish.class})
public class RenderFishMixin {
    @Redirect(method="doRender", at=@At(value="NEW", target="net/minecraft/util/Vec3", ordinal=1))
    private Vec3 oldMelodyFishingLine(double d, double d2, double d3) {
        if (OldAnimations.getINSTANCE().isEnabled()) {
            return (Boolean)OldAnimations.getINSTANCE().oldRod.getValue() == false ? new Vec3(d, d2, d3) : AnimationHandler.getInstance().getOffset();
        }
        return new Vec3(d, d2, d3);
    }
}

