/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiMultiplayer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
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
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.GUI.Notification.NotificationPublisher;
import xyz.Melody.GUI.Notification.NotificationType;
import xyz.Melody.System.Managers.Client.FriendManager;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.Vec3d;
import xyz.Melody.Utils.WindowsNotification;
import xyz.Melody.Utils.game.PlayerListUtils;
import xyz.Melody.Utils.math.Rotation;
import xyz.Melody.Utils.math.RotationUtil;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class MithrilNuker
extends Module {
    private ArrayList shabs = new ArrayList();
    public ArrayList blockPoses = new ArrayList();
    private float yaw = 0.0f;
    private float pitch = 0.0f;
    private BlockPos blockPos;
    private BlockPos lastBlockPos = null;
    private Numbers protectRange = new Numbers("ProtectRange", 10.0, 5.0, 100.0, 0.5);
    private Numbers range = new Numbers("Range", 5.0, 1.0, 6.0, 0.1);
    private Option admin = new Option("AntiAdmin", true);
    private Option wool = new Option("WoolOnly", false);
    private Option facingOnly = new Option("FacingOnly", false);
    private Option rot = new Option("Rotation", true);
    private Option sneak = new Option("Sneak", true);
    private Option protect = new Option("MacroProtect", true);
    private float currentDamage = 0.0f;
    private int blockHitDelay = 0;
    private boolean tempDisable = false;
    private boolean sneaked = false;
    private IBlockState blockState;
    private int ticks = 0;

    public MithrilNuker() {
        super("MithrilNuker", new String[]{"am"}, ModuleType.Macros);
        this.addValues(this.protectRange, this.range, this.wool, this.admin, this.facingOnly, this.sneak, this.protect, this.rot);
        this.setModInfo("Auto Mine Mithril Around You.");
    }

    @EventHandler
    private void tickPlayer(EventTick eventTick) {
        if (((Boolean)this.sneak.getValue()).booleanValue()) {
            KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74311_E.func_151463_i(), (boolean)true);
        }
        if (this.ticks <= 20) {
            ++this.ticks;
            return;
        }
        this.ticks = 0;
        if (this != false && ((Boolean)this.protect.getValue()).booleanValue()) {
            this.currentDamage = 0.0f;
            Helper.sendMessage("[Mithril Fucker] Player Detected in 20 Blocks, Auto Disabled.");
            this.setEnabled(false);
        }
    }

    @EventHandler
    private void destoryBlock(EventPreUpdate eventPreUpdate) {
        IBlockState iBlockState;
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return;
        }
        if (Client.pickaxeAbilityReady && this.mc.field_71442_b != null && this.mc.field_71439_g.field_71071_by.func_70301_a(this.mc.field_71439_g.field_71071_by.field_70461_c) != null) {
            this.mc.field_71442_b.func_78769_a((EntityPlayer)this.mc.field_71439_g, (World)this.mc.field_71441_e, this.mc.field_71439_g.field_71071_by.func_70301_a(this.mc.field_71439_g.field_71071_by.field_70461_c));
            Client.pickaxeAbilityReady = false;
        }
        if (this.currentDamage > 100.0f) {
            this.currentDamage = 0.0f;
        }
        if (this.blockPos != null && this.mc.field_71441_e != null && ((iBlockState = this.mc.field_71441_e.func_180495_p(this.blockPos)).func_177230_c() == Blocks.field_150357_h || iBlockState.func_177230_c() == Blocks.field_150350_a)) {
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
            float f = this.getRotations(this.blockPos).getYaw();
            float f2 = this.getRotations(this.blockPos).getPitch();
            this.mc.field_71439_g.field_70177_z = this.smoothRotation(this.mc.field_71439_g.field_70177_z, f, 70.0f);
            eventPreUpdate.setYaw(this.mc.field_71439_g.field_70177_z);
            this.mc.field_71439_g.field_70125_A = this.smoothRotation(this.mc.field_71439_g.field_70125_A, f2, 70.0f);
            eventPreUpdate.setPitch(this.mc.field_71439_g.field_70125_A);
        }
    }

    public Rotation getRotations(BlockPos blockPos) {
        return RotationUtil.vec3ToRotation(Vec3d.ofCenter((Vec3i)blockPos));
    }

    @Override
    public void onEnable() {
        this.yaw = this.mc.field_71439_g.field_70177_z;
        this.pitch = this.mc.field_71439_g.field_70125_A;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.currentDamage = 0.0f;
        this.shabs.clear();
        this.sneaked = false;
        this.tempDisable = false;
        if (((Boolean)this.sneak.getValue()).booleanValue()) {
            KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74311_E.func_151463_i(), (boolean)false);
        }
        super.onDisable();
    }

    @EventHandler
    private void onAdminDetected(EventTick eventTick) {
        if (((Boolean)this.admin.getValue()).booleanValue() && PlayerListUtils.tabContains("[ADMIN]")) {
            Helper.sendMessage("[AutoGemstone] Admin Detected, Warping to Main Lobby.");
            NotificationPublisher.queue("Admin Detected", "An Admin Joined Your Server.", NotificationType.WARN, 10000);
            WindowsNotification.show("MelodySky", "Admin Detected.", TrayIcon.MessageType.ERROR);
            this.mc.field_71439_g.func_71165_d("/l");
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
            Helper.sendMessage("[AutoFish] Admin Detected, Quitting Server.");
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
    public void onTick(EventRender3D eventRender3D) {
        if (this.getBlock() != null) {
            RenderUtil.drawSolidBlockESP(this.getBlock(), new Color(198, 139, 255, 190).getRGB(), eventRender3D.getPartialTicks());
        }
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        Helper.sendMessage("[MacroProtection] Auto Disabled " + EnumChatFormatting.GREEN + this.getName() + EnumChatFormatting.GRAY + " due to World Change.");
        this.setEnabled(false);
    }

    /*
     * Exception decompiling
     */
    private BlockPos getBlock() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl55 : ALOAD - null : trying to set 1 previously set to 0
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
}

