/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 */
package xyz.Melody.module.balance;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class AutoArmor
extends Module {
    public Numbers delay = new Numbers("Delay", 100.0, 50.0, 1000.0, 50.0);
    private TimerUtil timer = new TimerUtil();

    public AutoArmor() {
        super("AutoArmor", new String[]{"AutoArmor"}, ModuleType.Balance);
        super.addValues(this.delay);
    }

    @EventHandler
    public void onEvent(EventTick eventTick) {
        float f = ((Double)this.delay.getValue()).floatValue();
        if (!(this.mc.field_71462_r instanceof GuiInventory)) {
            return;
        }
        if ((this.mc.field_71462_r == null || this.mc.field_71462_r instanceof GuiInventory || this.mc.field_71462_r instanceof GuiChat) && this.timer.hasReached(f)) {
            this.getBestArmor();
        }
    }

    /*
     * Exception decompiling
     */
    public void getBestArmor() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl3 : ILOAD_1 - null : trying to set 1 previously set to 0
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void shiftClick(int n) {
        this.mc.field_71442_b.func_78753_a(this.mc.field_71439_g.field_71069_bz.field_75152_c, n, 0, 1, (EntityPlayer)this.mc.field_71439_g);
    }

    public void drop(int n) {
        this.mc.field_71442_b.func_78753_a(this.mc.field_71439_g.field_71069_bz.field_75152_c, n, 1, 4, (EntityPlayer)this.mc.field_71439_g);
    }

    public static float getProtection(ItemStack itemStack) {
        float f = 0.0f;
        if (itemStack.func_77973_b() instanceof ItemArmor) {
            ItemArmor itemArmor = (ItemArmor)itemStack.func_77973_b();
            f += (float)((double)itemArmor.field_77879_b + (double)((100 - itemArmor.field_77879_b) * EnchantmentHelper.func_77506_a((int)Enchantment.field_180310_c.field_77352_x, (ItemStack)itemStack)) * 0.0075);
            f += (float)((double)EnchantmentHelper.func_77506_a((int)Enchantment.field_77327_f.field_77352_x, (ItemStack)itemStack) / 100.0);
            f += (float)((double)EnchantmentHelper.func_77506_a((int)Enchantment.field_77329_d.field_77352_x, (ItemStack)itemStack) / 100.0);
            f += (float)((double)EnchantmentHelper.func_77506_a((int)Enchantment.field_92091_k.field_77352_x, (ItemStack)itemStack) / 100.0);
            f += (float)((double)EnchantmentHelper.func_77506_a((int)Enchantment.field_77347_r.field_77352_x, (ItemStack)itemStack) / 50.0);
            f += (float)((double)EnchantmentHelper.func_77506_a((int)Enchantment.field_180309_e.field_77352_x, (ItemStack)itemStack) / 100.0);
        }
        return f;
    }

    public static enum EMode {
        OpenInv;

    }
}

