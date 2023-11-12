/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockStainedGlass
 *  net.minecraft.block.BlockStainedGlassPane
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiMultiplayer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.EnumDyeColor
 *  net.minecraft.item.ItemPickaxe
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.StringUtils
 *  net.minecraft.util.Vec3i
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.macros.Mining;

import java.awt.Color;
import java.awt.TrayIcon;
import java.util.ArrayList;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.Player.EventPreUpdate;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.GUI.Notification.NotificationPublisher;
import xyz.Melody.GUI.Notification.NotificationType;
import xyz.Melody.System.Managers.Client.FriendManager;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.Item.ItemUtils;
import xyz.Melody.Utils.Vec3d;
import xyz.Melody.Utils.WindowsNotification;
import xyz.Melody.Utils.game.PlayerListUtils;
import xyz.Melody.Utils.math.Rotation;
import xyz.Melody.Utils.math.RotationUtil;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.macros.Mining.AutoRuby;

public class GemstoneNuker
extends Module {
    public ArrayList gemstones = new ArrayList();
    private BlockPos blockPos;
    private BlockPos lastBlockPos = null;
    public Mode mode = new Mode("Mode", Gemstone.values(), Gemstone.JADE);
    public Numbers range = new Numbers("Range", 5.0, 1.0, 6.0, 0.1);
    public Option admin = new Option("AntiAdmin", false);
    public Option blueEgg = new Option("BlueEggDrill", false);
    public Numbers blueSlot = new Numbers("EggDrill Slot", 5.0, 1.0, 8.0, 1.0);
    private Option all = new Option("AllGems", false);
    public Option sort = new Option("Sort", false);
    public Option under = new Option("Under", false);
    private Option rot = new Option("Rotation", true);
    private Option pickaxeCheck = new Option("Pickaxe", true);
    private Option pane = new Option("Pane", false);
    public Option protect = new Option("MacroProtect(10)", true);
    private float currentDamage = 0.0f;
    private int blockHitDelay = 0;
    private boolean tempDisable = false;
    private boolean holdingBlueEgg = false;
    private TimerUtil timer = new TimerUtil();
    private int lastSlot = 0;

    public GemstoneNuker() {
        super("GemstoneNuker", new String[]{"gm"}, ModuleType.Macros);
        this.addValues(this.mode, this.range, this.admin, this.blueEgg, this.blueSlot, this.all, this.sort, this.under, this.pickaxeCheck, this.protect, this.pane, this.rot);
        this.setModInfo("Auto Mine Gemstones Around You.");
    }

    @EventHandler
    private void destoryBlock(EventPreUpdate eventPreUpdate) {
        Object object;
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return;
        }
        if (this.tempDisable) {
            return;
        }
        AutoRuby autoRuby = (AutoRuby)Client.instance.getModuleManager().getModuleByClass(AutoRuby.class);
        if (!autoRuby.started && autoRuby.isEnabled()) {
            return;
        }
        if (this.blockPos == null) {
            this.blockPos = this.getBlock();
            return;
        }
        if (this.mc.field_71439_g.func_70011_f((double)this.blockPos.func_177958_n(), (double)this.blockPos.func_177956_o(), (double)this.blockPos.func_177952_p()) > 6.0) {
            this.gemstones.remove(this.blockPos);
            this.blockPos = null;
            return;
        }
        if (((Boolean)this.pickaxeCheck.getValue()).booleanValue()) {
            if (this.mc.field_71439_g.func_70694_bm() != null) {
                object = ItemUtils.getSkyBlockID(this.mc.field_71439_g.func_70694_bm());
                if (this.mc.field_71439_g.func_70694_bm().func_77973_b() != Items.field_179562_cC && !((String)object).contains("GEMSTONE_GAUNTLET") && !(this.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemPickaxe)) {
                    return;
                }
            } else {
                return;
            }
        }
        if (!Client.pickaxeAbilityReady || this.mc.field_71442_b == null) {
            this.timer.reset();
        }
        if (Client.pickaxeAbilityReady && this.mc.field_71442_b != null) {
            if (((Boolean)this.blueEgg.getValue()).booleanValue()) {
                if (!this.holdingBlueEgg) {
                    this.lastSlot = this.mc.field_71439_g.field_71071_by.field_70461_c;
                    this.mc.field_71439_g.field_71071_by.field_70461_c = ((Double)this.blueSlot.getValue()).intValue() - 1;
                    System.out.println(this.mc.field_71439_g.field_71071_by.field_70461_c);
                }
                if (this.timer.hasReached(250.0) && this.mc.field_71439_g.field_71071_by.func_70301_a(this.mc.field_71439_g.field_71071_by.field_70461_c) != null && this.mc.field_71439_g.field_71071_by.field_70461_c == ((Double)this.blueSlot.getValue()).intValue() - 1) {
                    this.mc.field_71442_b.func_78769_a((EntityPlayer)this.mc.field_71439_g, (World)this.mc.field_71441_e, this.mc.field_71439_g.field_71071_by.func_70301_a(this.mc.field_71439_g.field_71071_by.field_70461_c));
                    Client.pickaxeAbilityReady = false;
                    if (this.timer.hasReached(450.0)) {
                        this.mc.field_71439_g.field_71071_by.field_70461_c = this.lastSlot;
                        this.holdingBlueEgg = false;
                        this.timer.reset();
                    }
                }
            } else if (this.mc.field_71439_g.field_71071_by.func_70301_a(this.mc.field_71439_g.field_71071_by.field_70461_c) != null) {
                this.mc.field_71442_b.func_78769_a((EntityPlayer)this.mc.field_71439_g, (World)this.mc.field_71441_e, this.mc.field_71439_g.field_71071_by.func_70301_a(this.mc.field_71439_g.field_71071_by.field_70461_c));
                Client.pickaxeAbilityReady = false;
            }
        }
        if (this.currentDamage > 40.0f) {
            this.currentDamage = 0.0f;
        }
        if (this.blockPos != null && this.mc.field_71441_e != null && ((object = this.mc.field_71441_e.func_180495_p(this.blockPos)).func_177230_c() == Blocks.field_150357_h || object.func_177230_c() == Blocks.field_150350_a)) {
            this.currentDamage = 0.0f;
        }
        if (this.currentDamage == 0.0f) {
            this.lastBlockPos = this.blockPos;
            this.blockPos = this.getBlock();
        }
        if (this.blockPos != null) {
            if (this.blockHitDelay > 0) {
                --this.blockHitDelay;
                return;
            }
            if (this.currentDamage == 0.0f) {
                this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.blockPos, EnumFacing.DOWN));
            }
            this.mc.field_71439_g.func_71038_i();
            this.currentDamage += 1.0f;
        }
        if (((Boolean)this.rot.getValue()).booleanValue()) {
            object = this.getRotations(this.blockPos);
            if (object == null) {
                return;
            }
            float f = ((Rotation)object).getYaw();
            float f2 = ((Rotation)object).getPitch();
            this.mc.field_71439_g.field_70177_z = this.smoothRotation(this.mc.field_71439_g.field_70177_z, f, 70.0f);
            this.mc.field_71439_g.field_70125_A = this.smoothRotation(this.mc.field_71439_g.field_70125_A, f2, 70.0f);
        }
    }

    @EventHandler
    private void onAdminDetected(EventTick eventTick) {
        if (((Boolean)this.admin.getValue()).booleanValue() && (PlayerListUtils.tabContains("[ADMIN]") || PlayerListUtils.tabContains("[GM]"))) {
            Helper.sendMessage("[GemstoneNuker] Admin Detected, Warping to Private Island.");
            this.mc.field_71439_g.func_71165_d("/l");
            this.setEnabled(false);
        }
    }

    @SubscribeEvent(receiveCanceled=true)
    public void onAdminChat(ClientChatReceivedEvent clientChatReceivedEvent) {
        String string = StringUtils.func_76338_a((String)clientChatReceivedEvent.message.func_150260_c());
        if (!((Boolean)this.admin.getValue()).booleanValue()) {
            return;
        }
        for (String string2 : FriendManager.getFriends().keySet()) {
            if (!string.contains(string2)) continue;
            return;
        }
        if (string.startsWith("From [ADMIN]") || string.startsWith("From [GM]") || string.startsWith("From [YOUTUBE]")) {
            NotificationPublisher.queue("Admin Detected", "An Admin msged you, Quitting Server.", NotificationType.WARN, 10000);
            WindowsNotification.show("MelodySky", "Admin Detected.", TrayIcon.MessageType.ERROR);
            Helper.sendMessage("[GemstoneNuker] Admin Detected, Quitting Server.");
            boolean bl = this.mc.func_71387_A();
            this.mc.field_71441_e.func_72882_A();
            this.mc.func_71403_a((WorldClient)null);
            if (bl) {
                this.mc.func_147108_a((GuiScreen)new GuiMainMenu());
            } else {
                this.mc.func_147108_a((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()));
            }
        }
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (!((Boolean)this.protect.getValue()).booleanValue()) {
            return;
        }
        if (this != false && !this.tempDisable) {
            this.tempDisable = true;
        }
        if (this != false && this.tempDisable) {
            this.tempDisable = false;
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.currentDamage = 0.0f;
        this.gemstones.clear();
        this.tempDisable = false;
        super.onDisable();
    }

    @EventHandler
    public void onTick(EventRender3D eventRender3D) {
        if (this.getBlock() != null) {
            RenderUtil.drawSolidBlockESP(this.getBlock(), new Color(198, 139, 255, 190).getRGB(), eventRender3D.getPartialTicks());
        }
        BlockPos blockPos = new BlockPos(this.mc.field_71439_g.func_174791_d()).func_177977_b();
        RenderUtil.drawSolidBlockESP(blockPos, new Color(0, 139, 255, 190).getRGB(), eventRender3D.getPartialTicks());
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        Helper.sendMessage("[MacroProtection] Auto Disabled " + EnumChatFormatting.GREEN + this.getName() + EnumChatFormatting.GRAY + " due to World Change.");
        this.setEnabled(false);
    }

    public Rotation getRotations(BlockPos blockPos) {
        return RotationUtil.vec3ToRotation(Vec3d.ofCenter((Vec3i)blockPos));
    }

    /*
     * Exception decompiling
     */
    private BlockPos getBlock() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl72 : ALOAD - null : trying to set 1 previously set to 0
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

    private Gemstone getGemstone(IBlockState iBlockState) {
        if (iBlockState.func_177230_c() != Blocks.field_150399_cn && iBlockState.func_177230_c() != Blocks.field_150397_co) {
            return null;
        }
        if (!((Boolean)this.pane.getValue()).booleanValue() && iBlockState.func_177230_c() == Blocks.field_150397_co) {
            return null;
        }
        EnumDyeColor enumDyeColor = (EnumDyeColor)this.firstNotNull((EnumDyeColor)iBlockState.func_177229_b((IProperty)BlockStainedGlass.field_176547_a), (EnumDyeColor)iBlockState.func_177229_b((IProperty)BlockStainedGlassPane.field_176245_a));
        if (enumDyeColor == Gemstone.RUBY.dyeColor) {
            return Gemstone.RUBY;
        }
        if (enumDyeColor == Gemstone.AMETHYST.dyeColor) {
            return Gemstone.AMETHYST;
        }
        if (enumDyeColor == Gemstone.JADE.dyeColor) {
            return Gemstone.JADE;
        }
        if (enumDyeColor == Gemstone.SAPPHIRE.dyeColor) {
            return Gemstone.SAPPHIRE;
        }
        if (enumDyeColor == Gemstone.AMBER.dyeColor) {
            return Gemstone.AMBER;
        }
        if (enumDyeColor == Gemstone.TOPAZ.dyeColor) {
            return Gemstone.TOPAZ;
        }
        if (enumDyeColor == Gemstone.JASPER.dyeColor) {
            return Gemstone.JASPER;
        }
        if (enumDyeColor == Gemstone.OPAL.dyeColor) {
            return Gemstone.OPAL;
        }
        return null;
    }

    public Object firstNotNull(Object ... objectArray) {
        for (Object object : objectArray) {
            if (object == null) continue;
            return object;
        }
        return null;
    }

    private float smoothRotation(float f, float f2, float f3) {
        float f4 = MathHelper.func_76142_g((float)(f2 - f));
        if (f4 > f3) {
            f4 = f3;
        }
        if (f4 < -f3) {
            f4 = -f3;
        }
        return f + f4 / 2.0f;
    }

    private double lambda$getBlock$0(BlockPos blockPos) {
        return this.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p());
    }

    static enum Gemstone {
        RUBY(new Color(188, 3, 29), EnumDyeColor.RED),
        AMETHYST(new Color(137, 0, 201), EnumDyeColor.PURPLE),
        JADE(new Color(157, 249, 32), EnumDyeColor.LIME),
        SAPPHIRE(new Color(60, 121, 224), EnumDyeColor.LIGHT_BLUE),
        AMBER(new Color(237, 139, 35), EnumDyeColor.ORANGE),
        TOPAZ(new Color(249, 215, 36), EnumDyeColor.YELLOW),
        JASPER(new Color(214, 15, 150), EnumDyeColor.MAGENTA),
        OPAL(new Color(245, 245, 240), EnumDyeColor.WHITE);

        public Color color;
        public EnumDyeColor dyeColor;

        /*
         * WARNING - void declaration
         */
        private Gemstone() {
            void var4_2;
            void var3_1;
            this.color = var3_1;
            this.dyeColor = var4_2;
        }
    }
}

