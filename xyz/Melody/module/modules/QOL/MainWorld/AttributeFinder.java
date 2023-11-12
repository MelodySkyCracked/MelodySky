/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 */
package xyz.Melody.module.modules.QOL.MainWorld;

import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.container.DrawSlotEvent;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class AttributeFinder
extends Module {
    private String targetAttribute0;
    private String targetAttribute1;
    public GuiTextField atFiled0;
    public GuiTextField atFiled1;
    private Numbers R = new Numbers("Red", 150.0, 0.0, 255.0, 1.0);
    private Numbers G = new Numbers("Green", 255.0, 0.0, 255.0, 1.0);
    private Numbers B = new Numbers("Blue", 200.0, 0.0, 255.0, 1.0);
    private Numbers A = new Numbers("Alpha", 255.0, 0.0, 255.0, 1.0);
    private static AttributeFinder INSTANCE;

    public AttributeFinder() {
        super("AttributeFinder", new String[]{"am"}, ModuleType.QOL);
        this.addValues(this.R, this.G, this.B, this.A);
        this.setModInfo("Show Matching Attributes as BG Color.");
    }

    public static AttributeFinder getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (AttributeFinder)Client.instance.getModuleManager().getModuleByClass(AttributeFinder.class);
        }
        return INSTANCE;
    }

    @Override
    public void onDisable() {
        this.targetAttribute0 = "";
        this.targetAttribute1 = "";
        this.atFiled0 = null;
        this.atFiled1 = null;
        super.onDisable();
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (this.mc.field_71462_r == null) {
            this.targetAttribute0 = "";
            this.targetAttribute1 = "";
            this.atFiled0 = null;
            this.atFiled1 = null;
        }
    }

    @EventHandler
    private void onTick(DrawSlotEvent drawSlotEvent) {
        if (this.atFiled0.func_146179_b().equals("") && this.atFiled1.func_146179_b().equals("")) {
            return;
        }
        ItemStack itemStack = drawSlotEvent.slot.func_75211_c();
        if (itemStack == null) {
            return;
        }
        NBTTagCompound nBTTagCompound = itemStack.func_77978_p();
        NBTTagCompound nBTTagCompound2 = nBTTagCompound.func_74775_l("ExtraAttributes").func_74775_l("attributes");
        String string = nBTTagCompound2.func_150296_c().toString();
        if (this.targetAttribute1 == null) {
            if (string.contains(this.targetAttribute0)) {
                int n = drawSlotEvent.slot.field_75223_e;
                int n2 = drawSlotEvent.slot.field_75221_f;
                Gui.func_73734_a((int)n, (int)n2, (int)(n + 16), (int)(n2 + 16), (int)new Color(((Double)this.R.getValue()).intValue(), ((Double)this.G.getValue()).intValue(), ((Double)this.B.getValue()).intValue(), ((Double)this.A.getValue()).intValue()).getRGB());
            }
        } else if (string.contains(this.targetAttribute0) && string.contains(this.targetAttribute1)) {
            int n = drawSlotEvent.slot.field_75223_e;
            int n3 = drawSlotEvent.slot.field_75221_f;
            Gui.func_73734_a((int)n, (int)n3, (int)(n + 16), (int)(n3 + 16), (int)new Color(((Double)this.R.getValue()).intValue(), ((Double)this.G.getValue()).intValue(), ((Double)this.B.getValue()).intValue(), ((Double)this.A.getValue()).intValue()).getRGB());
        }
    }

    public boolean onKeyboard(char c, int n) {
        if (!this.isEnabled() || this.atFiled0 == null || this.atFiled1 == null) {
            return false;
        }
        if (n == 1) {
            return false;
        }
        if (this.atFiled0.func_146206_l()) {
            this.atFiled0.func_146201_a(c, n);
            return true;
        }
        if (this.atFiled1.func_146206_l()) {
            this.atFiled1.func_146201_a(c, n);
            return true;
        }
        return false;
    }

    public void onMouseClick(int n, int n2, int n3) {
        if (!this.isEnabled() || this.atFiled0 == null || this.atFiled1 == null) {
            return;
        }
        this.atFiled0.func_146192_a(n, n2, n3);
        this.atFiled1.func_146192_a(n, n2, n3);
    }

    public void initInv() {
        if (!this.isEnabled()) {
            return;
        }
        if (this.atFiled0 != null || this.atFiled1 != null) {
            return;
        }
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        int n = scaledResolution.func_78326_a();
        int n2 = scaledResolution.func_78328_b();
        this.atFiled0 = new GuiTextField(1, this.mc.field_71466_p, n - 140, n2 / 2 - 11, 80, 18);
        this.atFiled0.func_146203_f(Short.MAX_VALUE);
        this.atFiled1 = new GuiTextField(1, this.mc.field_71466_p, n - 140, n2 / 2 + 11, 80, 18);
        this.atFiled1.func_146203_f(Short.MAX_VALUE);
    }

    public void drawInv() {
        if (!this.isEnabled()) {
            return;
        }
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        int n = scaledResolution.func_78326_a();
        int n2 = scaledResolution.func_78328_b();
        this.mc.field_71466_p.func_78276_b("At 1:", n - 165, n2 / 2 - 6, -1);
        this.mc.field_71466_p.func_78276_b("At 2:", n - 165, n2 / 2 + 16, -1);
        this.atFiled0.func_146194_f();
        this.targetAttribute0 = this.atFiled0.func_146179_b().toLowerCase().replaceAll(" ", "_");
        this.atFiled1.func_146194_f();
        this.targetAttribute1 = this.atFiled1.func_146179_b().toLowerCase().replaceAll(" ", "_");
    }
}

