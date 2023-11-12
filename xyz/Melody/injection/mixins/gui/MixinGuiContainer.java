/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 */
package xyz.Melody.injection.mixins.gui;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.events.container.DrawSlotEvent;
import xyz.Melody.GUI.Animate.Opacity;
import xyz.Melody.Utils.animate.Translate;
import xyz.Melody.injection.mixins.gui.MixinGuiScreen;
import xyz.Melody.module.modules.QOL.AutoEnchantTable;
import xyz.Melody.module.modules.QOL.ChestSellValue;
import xyz.Melody.module.modules.QOL.Dungeons.DungeonChestProfit;
import xyz.Melody.module.modules.QOL.MainWorld.AttributeFinder;
import xyz.Melody.module.modules.render.HUD;

@Mixin(value={GuiContainer.class})
public abstract class MixinGuiContainer
extends MixinGuiScreen {
    @Shadow
    public Container field_147002_h;
    @Shadow
    private Slot field_147006_u;
    public Translate translate = new Translate(0.0f, 0.0f);
    public Opacity opacity = new Opacity(1);
    private static final String TARGET_GETSTACK = "Lnet/minecraft/inventory/Slot;getStack()Lnet/minecraft/item/ItemStack;";

    @Inject(method="initGui", at={@At(value="HEAD")})
    private void init(CallbackInfo callbackInfo) {
        this.opacity = new Opacity(1);
        AttributeFinder.getINSTANCE().initInv();
        super.func_73866_w_();
    }

    @Inject(method="drawSlot", at={@At(value="HEAD")}, cancellable=true)
    public void drawSlot(Slot slot, CallbackInfo callbackInfo) {
        if (slot == null) {
            return;
        }
        ItemStack itemStack = slot.func_75211_c();
        if (itemStack != null && AutoEnchantTable.getINSTANCE().onStackRender(itemStack, slot.field_75224_c, slot.getSlotIndex(), slot.field_75223_e, slot.field_75221_f)) {
            callbackInfo.cancel();
            return;
        }
        RenderHelper.func_74520_c();
    }

    @Redirect(method="drawSlot", at=@At(value="INVOKE", target="Lnet/minecraft/inventory/Slot;getStack()Lnet/minecraft/item/ItemStack;"))
    public ItemStack drawSlot_getStack(Slot slot) {
        ItemStack itemStack;
        ItemStack itemStack2 = slot.func_75211_c();
        if (itemStack2 != null && (itemStack = AutoEnchantTable.getINSTANCE().overrideStack(slot.field_75224_c, slot.getSlotIndex(), itemStack2)) != null) {
            itemStack2 = itemStack;
        }
        return itemStack2;
    }

    @Redirect(method="drawScreen", at=@At(value="INVOKE", target="Lnet/minecraft/inventory/Slot;getStack()Lnet/minecraft/item/ItemStack;"))
    public ItemStack drawScreen_getStack(Slot slot) {
        ItemStack itemStack;
        if (this.field_147006_u != null && this.field_147006_u == slot && this.field_147006_u.func_75211_c() != null && (itemStack = AutoEnchantTable.getINSTANCE().overrideStack(this.field_147006_u.field_75224_c, this.field_147006_u.getSlotIndex(), this.field_147006_u.func_75211_c())) != null) {
            return itemStack;
        }
        return slot.func_75211_c();
    }

    @Inject(method="drawScreen", at={@At(value="HEAD")})
    private void drawScreen(int n, int n2, float f, CallbackInfo callbackInfo) {
        this.dick(true);
        HUD.getInstance().handleContainer(this.translate, this.opacity, this.field_146294_l, this.field_146295_m);
        DungeonChestProfit.getINSTANCE().onRender();
        ChestSellValue.getINSTANCE().onRender();
        AttributeFinder.getINSTANCE().drawInv();
    }

    @Inject(method="keyTyped", at={@At(value="HEAD")}, cancellable=true)
    protected void keyTyped(char c, int n, CallbackInfo callbackInfo) throws IOException {
        if (AttributeFinder.getINSTANCE().onKeyboard(c, n)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method="drawSlot", at={@At(value="HEAD")}, cancellable=true)
    private void beforeDrawSlot(Slot slot, CallbackInfo callbackInfo) {
        DrawSlotEvent drawSlotEvent = new DrawSlotEvent(this.field_147002_h, slot);
        EventBus.getInstance().call(drawSlotEvent);
        if (drawSlotEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method="mouseClicked", at={@At(value="HEAD")}, cancellable=true)
    protected void mouseClicked(int n, int n2, int n3, CallbackInfo callbackInfo) {
        AttributeFinder.getINSTANCE().onMouseClick(n, n2, n3);
    }

    @Inject(method="handleMouseClick", at={@At(value="HEAD")}, cancellable=true)
    public void handleMouseClick(Slot slot, int n, int n2, int n3, CallbackInfo callbackInfo) {
        GuiContainer guiContainer = (GuiContainer)this;
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        if (atomicBoolean.get()) {
            return;
        }
        if (slot != null && slot.func_75211_c() != null && AutoEnchantTable.getINSTANCE().onStackClick(slot.func_75211_c(), guiContainer.field_147002_h.field_75152_c, n, n2, n3)) {
            callbackInfo.cancel();
        }
    }

    @Override
    protected void func_146276_q_() {
    }

    private void dick(boolean bl) {
        if (!bl) {
            return;
        }
        if (!HUD.getInstance().isEnabled() || !((Boolean)HUD.getInstance().blur.getValue()).booleanValue()) {
            this.func_146270_b(0);
        }
    }
}

