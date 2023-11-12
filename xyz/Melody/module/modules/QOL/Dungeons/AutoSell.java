/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C0EPacketClickWindow
 */
package xyz.Melody.module.modules.QOL.Dungeons;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.container.DrawSlotEvent;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Option;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class AutoSell
extends Module {
    private Option DJ = new Option("DungeonJunks", true);
    private Option salvable = new Option("SalvageAble", false);
    private Option boooom = new Option("SuperBoooom", true);
    private Option rs = new Option("ReviveStones", true);
    private Option runes = new Option("Runes", true);
    private boolean inTradeMenu = false;
    private int tickCount = 0;
    private static final String[] dungeonJunk = new String[]{"Training Weight", "Healing VIII Splash Potion", "Healing 8 Slash Potion", "Premium Flesh", "Mimic Fragment", "Enchanted Rotten Flesh", "Enchanted Bone", "Defuse Kit", "Enchanted Ice", "Optic Lense", "Tripwire Hook", "Button", "Carpet", "Lever", "Journal Entry", "Sign"};

    public AutoSell() {
        super("AutoSell", new String[]{"as"}, ModuleType.Dungeons);
        this.addValues(this.DJ, this.salvable, this.boooom, this.runes, this.rs);
        this.setModInfo("Auto Sell Useless Items.");
    }

    /*
     * Exception decompiling
     */
    @EventHandler
    public void onTick(EventTick var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl45 : ALOAD_3 - null : trying to set 1 previously set to 0
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

    @EventHandler
    public void onBackgroundRender(EventTick eventTick) {
        Container container;
        GuiScreen guiScreen = this.mc.field_71462_r;
        if (guiScreen instanceof GuiChest && (container = ((GuiChest)guiScreen).field_147002_h) instanceof ContainerChest) {
            String string = ((ContainerChest)container).func_85151_d().func_145748_c_().func_150260_c();
            this.inTradeMenu = string.equals("Trades");
        }
    }

    /*
     * Exception decompiling
     */
    @EventHandler
    public void onDrawSlot(DrawSlotEvent var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl34 : RETURN - null : trying to set 0 previously set to 1
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

    private void windowClick(int n, Slot slot, int n2, int n3) {
        short s = this.mc.field_71439_g.field_71070_bA.func_75136_a(this.mc.field_71439_g.field_71071_by);
        ItemStack itemStack = slot.func_75211_c();
        this.mc.func_147114_u().func_147297_a((Packet)new C0EPacketClickWindow(n, 45 + slot.field_75222_d, n2, n3, itemStack, s));
    }
}

