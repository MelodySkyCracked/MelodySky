/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.util.StringUtils
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.client.event.GuiOpenEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.QOL.MainWorld;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Utils.Helper;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class AutoJerryBox
extends Module {
    private boolean enabled = false;
    private Thread openThread = null;
    private boolean guiOpened = false;
    private boolean received = false;

    public AutoJerryBox() {
        super("AutoJerryBox", new String[]{"jerry"}, ModuleType.QOL);
        this.setModInfo("Auto Open Jerry Boxies.");
    }

    @Override
    public void onEnable() {
        if (this == null) {
            Helper.sendMessage("Holding a Jerry Box to Continue!");
        }
        this.enabled = true;
        super.onEnable();
    }

    @EventHandler
    public void onTick(EventTick eventTick) {
        if (this.openThread != null && this.openThread.isAlive()) {
            return;
        }
        if (!this.enabled || this == null) {
            return;
        }
        this.enabled = false;
        this.openThread = new Thread(this::lambda$onTick$0);
        this.openThread.start();
    }

    @SubscribeEvent
    public void onOpen(GuiOpenEvent guiOpenEvent) {
        try {
            if (guiOpenEvent.gui instanceof GuiChest && this.openThread != null && this.openThread.isAlive() && ((ContainerChest)((GuiChest)guiOpenEvent.gui).field_147002_h).func_85151_d().func_70005_c_().equals("Open a Jerry Box")) {
                this.guiOpened = true;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onReceiveOpenMessage(ClientChatReceivedEvent clientChatReceivedEvent) {
        if (clientChatReceivedEvent.type == 0 && StringUtils.func_76338_a((String)clientChatReceivedEvent.message.func_150260_c()).matches(" \u263a ")) {
            this.received = true;
        }
    }

    private void stopMovement() {
        KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74370_x.func_151463_i(), (boolean)false);
        KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74366_z.func_151463_i(), (boolean)false);
        KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74351_w.func_151463_i(), (boolean)false);
        KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74368_y.func_151463_i(), (boolean)false);
        KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74314_A.func_151463_i(), (boolean)false);
    }

    public int getHeldItemIndex() {
        if (this.mc.field_71439_g == null) {
            return -1;
        }
        InventoryPlayer inventoryPlayer = this.mc.field_71439_g.field_71071_by;
        if (inventoryPlayer == null) {
            return -1;
        }
        return inventoryPlayer.field_70461_c;
    }

    public String getGuiName(GuiScreen guiScreen) {
        if (guiScreen instanceof GuiChest) {
            return ((ContainerChest)((GuiChest)guiScreen).field_147002_h).func_85151_d().func_145748_c_().func_150260_c();
        }
        return "";
    }

    /*
     * Exception decompiling
     */
    private void lambda$onTick$0() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl8 : ALOAD_0 - null : trying to set 1 previously set to 0
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
}

