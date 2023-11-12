/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.model.IBakedModel
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.EnumAction
 *  net.minecraft.item.ItemStack
 */
package xyz.Melody.injection.mixins.items;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Melody.System.Animations.AnimationHandler;
import xyz.Melody.module.modules.render.OldAnimations;

@Mixin(value={RenderItem.class})
public class MixinRenderItem {
    @Unique
    private EntityLivingBase lastEntityToRenderFor = null;

    @Inject(method="renderItemModelForEntity", at={@At(value="HEAD")})
    public void renderItemModelForEntity(ItemStack itemStack, EntityLivingBase entityLivingBase, ItemCameraTransforms.TransformType transformType, CallbackInfo callbackInfo) {
        this.lastEntityToRenderFor = entityLivingBase;
    }

    @Inject(method="renderItemModelTransform", at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V")})
    public void renderItemModelForEntity_renderItem(ItemStack itemStack, IBakedModel iBakedModel, ItemCameraTransforms.TransformType transformType, CallbackInfo callbackInfo) {
        EntityPlayer entityPlayer;
        ItemStack itemStack2;
        boolean bl = OldAnimations.getINSTANCE().isEnabled();
        if (bl && transformType == ItemCameraTransforms.TransformType.THIRD_PERSON && this.lastEntityToRenderFor instanceof EntityPlayer && (itemStack2 = (entityPlayer = (EntityPlayer)this.lastEntityToRenderFor).func_70694_bm()) != null && entityPlayer.func_71052_bv() > 0 && itemStack2.func_77975_n() == EnumAction.BLOCK) {
            AnimationHandler.getInstance().doSwordBlock3rdPersonTransform();
        }
    }
}

