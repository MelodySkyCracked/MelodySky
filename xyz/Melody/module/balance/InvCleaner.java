/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemAxe
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemPickaxe
 *  net.minecraft.item.ItemSpade
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.item.ItemTool
 */
package xyz.Melody.module.balance;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.game.InventoryUtils;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.balance.AutoArmor;

public class InvCleaner
extends Module {
    private Numbers BlockCap = new Numbers("MaxBlocks", 128.0, 0.0, 256.0, 8.0);
    private Numbers Delay = new Numbers("Delay", 100.0, 50.0, 1000.0, 100.0);
    private Option UHC = new Option("UHC", false);
    private Numbers weaponSlot = new Numbers("WeaponSlot", 36.0, 36.0, 44.0, 1.0);
    private Numbers pickaxeSlot = new Numbers("PickaxeSlot", 37.0, 36.0, 44.0, 1.0);
    private Numbers axeSlot = new Numbers("AxeSlot", 38.0, 36.0, 44.0, 1.0);
    private Numbers shovelSlot = new Numbers("ShovelSlot", 39.0, 36.0, 44.0, 1.0);
    private TimerUtil timer = new TimerUtil();

    public InvCleaner() {
        super("InvManager", new String[]{"InvCleaner"}, ModuleType.Balance);
        this.addValues(this.BlockCap, this.Delay, this.weaponSlot, this.pickaxeSlot, this.axeSlot, this.shovelSlot, this.UHC);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    /*
     * Exception decompiling
     */
    @EventHandler
    public void onEvent(EventTick var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl205 : ILOAD - null : trying to set 2 previously set to 0
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

    public void swap(int n, int n2) {
        this.mc.field_71442_b.func_78753_a(this.mc.field_71439_g.field_71069_bz.field_75152_c, n, n2, 2, (EntityPlayer)this.mc.field_71439_g);
    }

    public void drop(int n) {
        this.mc.field_71442_b.func_78753_a(this.mc.field_71439_g.field_71069_bz.field_75152_c, n, 1, 4, (EntityPlayer)this.mc.field_71439_g);
    }

    public void getBestWeapon(int n) {
        for (int i = 9; i < 45; ++i) {
            ItemStack itemStack;
            if (!this.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d() || this >= (itemStack = this.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c()) || !(this.getDamage(itemStack) > 0.0f) || !(itemStack.func_77973_b() instanceof ItemSword)) continue;
            this.swap(i, n - 36);
            this.timer.reset();
            break;
        }
    }

    private float getDamage(ItemStack itemStack) {
        ItemTool itemTool;
        float f = 0.0f;
        Item item = itemStack.func_77973_b();
        if (item instanceof ItemTool) {
            itemTool = (ItemTool)item;
            f += (float)itemTool.func_77612_l();
        }
        if (item instanceof ItemSword) {
            itemTool = (ItemSword)item;
            f += itemTool.func_150931_i();
        }
        return f += (float)EnchantmentHelper.func_77506_a((int)Enchantment.field_180314_l.field_77352_x, (ItemStack)itemStack) * 1.25f + (float)EnchantmentHelper.func_77506_a((int)Enchantment.field_77334_n.field_77352_x, (ItemStack)itemStack) * 0.01f;
    }

    private int getBlockCount() {
        int n = 0;
        for (int i = 0; i < 45; ++i) {
            if (!this.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) continue;
            ItemStack itemStack = this.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
            Item item = itemStack.func_77973_b();
            if (!(itemStack.func_77973_b() instanceof ItemBlock) || InventoryUtils.BLOCK_BLACKLIST.contains(((ItemBlock)item).func_179223_d())) continue;
            n += itemStack.field_77994_a;
        }
        return n;
    }

    /*
     * Exception decompiling
     */
    private void getBestPickaxe(int var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl9 : ILOAD_3 - null : trying to set 1 previously set to 0
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

    /*
     * Exception decompiling
     */
    private void getBestShovel(int var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl9 : ILOAD_3 - null : trying to set 1 previously set to 0
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

    /*
     * Exception decompiling
     */
    private void getBestAxe(int var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl9 : ILOAD_3 - null : trying to set 1 previously set to 0
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

    private float getToolEffect(ItemStack itemStack) {
        Item item = itemStack.func_77973_b();
        if (!(item instanceof ItemTool)) {
            return 0.0f;
        }
        String string = item.func_77658_a();
        ItemTool itemTool = (ItemTool)item;
        float f = 1.0f;
        if (item instanceof ItemPickaxe) {
            f = itemTool.func_150893_a(itemStack, Blocks.field_150348_b);
            if (string.toLowerCase().contains("gold")) {
                f -= 5.0f;
            }
        } else if (item instanceof ItemSpade) {
            f = itemTool.func_150893_a(itemStack, Blocks.field_150346_d);
            if (string.toLowerCase().contains("gold")) {
                f -= 5.0f;
            }
        } else {
            if (!(item instanceof ItemAxe)) {
                return 1.0f;
            }
            f = itemTool.func_150893_a(itemStack, Blocks.field_150364_r);
            if (string.toLowerCase().contains("gold")) {
                f -= 5.0f;
            }
        }
        f += (float)((double)EnchantmentHelper.func_77506_a((int)Enchantment.field_77349_p.field_77352_x, (ItemStack)itemStack) * 0.0075);
        return f += (float)((double)EnchantmentHelper.func_77506_a((int)Enchantment.field_77347_r.field_77352_x, (ItemStack)itemStack) / 100.0);
    }

    public void getBestArmor() {
        AutoArmor autoArmor = (AutoArmor)Client.instance.getModuleManager().getModuleByClass(AutoArmor.class);
        for (int i = 1; i < 5; ++i) {
            if (this.mc.field_71439_g.field_71069_bz.func_75139_a(4 + i).func_75216_d()) {
                ItemStack itemStack = this.mc.field_71439_g.field_71069_bz.func_75139_a(4 + i).func_75211_c();
                if (autoArmor.isBestArmor(itemStack, i)) continue;
                this.drop(4 + i);
            }
            for (int j = 9; j < 45; ++j) {
                ItemStack itemStack;
                if (!this.mc.field_71439_g.field_71069_bz.func_75139_a(j).func_75216_d() || !autoArmor.isBestArmor(itemStack = this.mc.field_71439_g.field_71069_bz.func_75139_a(j).func_75211_c(), i) || !(AutoArmor.getProtection(itemStack) > 0.0f)) continue;
                this.shiftClick(j);
                this.timer.reset();
                if (((Double)this.Delay.getValue()).longValue() <= 0L) continue;
                return;
            }
        }
    }
}

