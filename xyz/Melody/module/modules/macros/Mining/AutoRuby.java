/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiMultiplayer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityMagmaCube
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C02PacketUseEntity$Action
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.StringUtils
 *  net.minecraft.util.Vec3i
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.module.modules.macros.Mining;

import java.awt.TrayIcon;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3i;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
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
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.Item.ItemUtils;
import xyz.Melody.Utils.MouseUtils;
import xyz.Melody.Utils.WindowsNotification;
import xyz.Melody.Utils.game.PlayerListUtils;
import xyz.Melody.Utils.math.MathUtil;
import xyz.Melody.Utils.math.Rotation;
import xyz.Melody.Utils.math.RotationUtil;
import xyz.Melody.Utils.render.ColorUtils;
import xyz.Melody.Utils.render.FadeUtil;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.macros.Mining.GemstoneNuker;
import xyz.Melody.module.modules.macros.Mining.MithrilNuker;

public class AutoRuby
extends Module {
    private TimerUtil timer = new TimerUtil();
    private TimerUtil ewTimer = new TimerUtil();
    public ArrayList wps = new ArrayList();
    private Mode mode = new Mode("Mode", GemstoneNuker.Gemstone.values(), GemstoneNuker.Gemstone.RUBY);
    private Option hideCoords = new Option("HideCoords", false);
    private Option waiting = new Option("Waiting", false);
    private Numbers waitTime = new Numbers("WaitTime(sec)", 60.0, 10.0, 300.0, 1.0);
    private Option mithril = new Option("Mithril", false);
    private Option admin = new Option("AntiAdmin", false);
    private Option ugm = new Option("UnGrabMouse", false);
    private Option killYogs = new Option("KillYogs", true);
    private Option rcKill = new Option("RCKillYogs", false);
    private Option aim = new Option("RCAimYogs", false);
    private Option faceDown = new Option("RCFaceDown", false);
    private Numbers yogRange = new Numbers("YogRange", 3.5, 1.0, 4.5, 0.5);
    private Numbers weaponSlot = new Numbers("WeaponSlot", 3.0, 1.0, 8.0, 1.0);
    private Option routeHelper = new Option("RouteHelper", true);
    private Option protect = new Option("MacroProtect", true);
    private Numbers protectRange = new Numbers("ProtectRange", 20.0, 10.0, 100.0, 5.0);
    private Option refill = new Option("AutoRefill", true);
    private Numbers fuelLeft = new Numbers("FuelWhenRefill", 50.0, 0.0, 1000.0, 10.0);
    private Option restart = new Option("AutoRestart", true);
    private boolean etherWarped = false;
    private int curIndex = 0;
    private BlockPos curBP;
    private BlockPos nextBP;
    public boolean started = false;
    private boolean shouldWait = false;
    private TimerUtil waitTimer = new TimerUtil();
    private TimerUtil restartTimer = new TimerUtil();
    private boolean playerDetected = false;
    private int lastSlot = 0;
    private boolean restore = false;
    private boolean shouldSwitchToAbi = false;
    private boolean shouldCallSB = false;
    private boolean shouldPreClickFuel = true;
    private boolean shouldPostClickFuel = false;
    private boolean shouldClickDrill = false;
    private boolean shouldCombind = false;
    private int drillSlot = 0;
    private int fuelSlot = 0;
    private static AutoRuby INSTANCE;
    private ArrayList yogs = new ArrayList();
    private TimerUtil attackTimer = new TimerUtil();
    private boolean killingYogs = false;
    private int ticks = 0;
    private int idkTicks = 0;
    private TimerUtil fallSafeTimer = new TimerUtil();

    public AutoRuby() {
        super("AutoGemstone", new String[]{""}, ModuleType.Macros);
        this.addValues(this.mode, this.hideCoords, this.waiting, this.waitTime, this.mithril, this.admin, this.ugm, this.routeHelper, this.protect, this.protectRange, this.refill, this.fuelLeft, this.restart, this.killYogs, this.yogRange, this.weaponSlot, this.rcKill, this.aim, this.faceDown);
        this.setModInfo("Auto Mine Gemstone.");
    }

    public static AutoRuby getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (AutoRuby)Client.instance.getModuleManager().getModuleByClass(AutoRuby.class);
        }
        return INSTANCE;
    }

    @EventHandler
    private void onAdminDetected(EventTick eventTick) {
        if (((Boolean)this.admin.getValue()).booleanValue() && PlayerListUtils.tabContains("[ADMIN]")) {
            Helper.sendMessage("[AutoGemstone] Admin Detected, Warping to Main Lobby.");
            NotificationPublisher.queue("Admin Detected", "An Admin Joined Your Server.", NotificationType.WARN, 10000);
            WindowsNotification.show("MelodySky", "Admin Detected.", TrayIcon.MessageType.ERROR);
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
            Helper.sendMessage("[AutoGemstone] Admin Detected, Quitting Server.");
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
    public void onChat(EventTick eventTick) {
        if (!((Boolean)this.refill.getValue()).booleanValue()) {
            this.shouldSwitchToAbi = false;
            return;
        }
        if (this.started && !this.restore) {
            String[] stringArray;
            ItemStack itemStack = this.mc.field_71439_g.func_70694_bm();
            if (itemStack == null) {
                return;
            }
            String string = ItemUtils.getSkyBlockID(itemStack);
            if (string == null || !string.contains("_DRILL_") && !string.equals("DIVAN_DRILL")) {
                return;
            }
            for (String string2 : stringArray = ItemUtils.getLoreFromNBT(itemStack.func_77978_p())) {
                try {
                    String[] stringArray2;
                    if (!string2.startsWith("\u00a77Fuel: ") || (stringArray2 = StringUtils.func_76338_a((String)string2).split("/")).length != 2) continue;
                    String string3 = stringArray2[0].split(" ")[1];
                    int n = Integer.parseInt(string3.replace(",", "").trim());
                    if (n > ((Double)this.fuelLeft.getValue()).intValue()) {
                        return;
                    }
                    NotificationPublisher.queue("AutoRuby Drill Empty.", "Trying To Refill Your Drill.", NotificationType.WARN, 10000);
                    this.started = false;
                    this.restore = true;
                    this.shouldSwitchToAbi = true;
                    break;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }

    @SubscribeEvent(receiveCanceled=true)
    public void onRefillMSG(ClientChatReceivedEvent clientChatReceivedEvent) {
        if (!((Boolean)this.refill.getValue()).booleanValue()) {
            this.shouldSwitchToAbi = false;
            return;
        }
        String string = StringUtils.func_76338_a((String)clientChatReceivedEvent.message.func_150260_c());
        if (string.contains("is empty! Refuel it by talking to a Drill Mechanic!") && this.started && !this.restore) {
            NotificationPublisher.queue("AutoRuby Drill Empty.", "Trying To Refill Your Drill.", NotificationType.WARN, 10000);
            this.started = false;
            this.restore = true;
            this.shouldSwitchToAbi = true;
        }
    }

    @EventHandler
    private void onKillYog(EventPreUpdate eventPreUpdate) {
        if (((Boolean)this.killYogs.getValue()).booleanValue()) {
            this.loadYogs();
        } else if (!this.yogs.isEmpty()) {
            this.yogs.clear();
        }
        if (!this.yogs.isEmpty()) {
            EntityMagmaCube entityMagmaCube = (EntityMagmaCube)this.yogs.get(0);
            if (this.started) {
                NotificationPublisher.queue("AutoRuby", "Yog Detected, Trying to FUCK it.", NotificationType.WARN, 3000);
                this.started = false;
                this.killingYogs = true;
                this.attackTimer.reset();
            }
            if (entityMagmaCube != null && entityMagmaCube.func_70089_S() && this.killingYogs) {
                this.mc.field_71439_g.field_71071_by.field_70461_c = ((Double)this.weaponSlot.getValue()).intValue() - 1;
                if (((Boolean)this.rcKill.getValue()).booleanValue()) {
                    if (((Boolean)this.faceDown.getValue()).booleanValue()) {
                        eventPreUpdate.setPitch(90.0f);
                        if (this.attackTimer.hasReached(180.0)) {
                            Client.rightClick();
                            this.attackTimer.reset();
                        }
                    } else {
                        if (((Boolean)this.aim.getValue()).booleanValue()) {
                            float[] fArray = RotationUtil.getPredictedRotations((EntityLivingBase)entityMagmaCube);
                            eventPreUpdate.setYaw(fArray[0]);
                            eventPreUpdate.setPitch(fArray[1]);
                        }
                        if (this.attackTimer.hasReached(180.0)) {
                            Client.rightClick();
                            this.attackTimer.reset();
                        }
                    }
                } else {
                    float[] fArray = RotationUtil.getPredictedRotations((EntityLivingBase)entityMagmaCube);
                    eventPreUpdate.setYaw(fArray[0]);
                    eventPreUpdate.setPitch(fArray[1]);
                    if (this.attackTimer.hasReached(180.0)) {
                        this.mc.field_71439_g.func_71038_i();
                        this.mc.func_147114_u().func_147297_a((Packet)new C02PacketUseEntity((Entity)entityMagmaCube, C02PacketUseEntity.Action.ATTACK));
                        this.attackTimer.reset();
                    }
                }
            }
        } else if (this.killingYogs) {
            NotificationPublisher.queue("AutoRuby", "OKAY, Continued Mining..", NotificationType.SUCCESS, 3000);
            this.started = true;
            this.killingYogs = false;
            this.attackTimer.reset();
        }
    }

    @EventHandler
    public void onDrawGuiBackground(EventTick eventTick) {
        ItemStack itemStack;
        if (this.ticks < 40) {
            ++this.ticks;
            return;
        }
        this.ticks = 0;
        if (this.shouldSwitchToAbi) {
            for (int i = 0; i < 9; ++i) {
                itemStack = this.mc.field_71439_g.field_71071_by.field_70462_a[i];
                if (itemStack != null && itemStack.func_77973_b() != null && ItemUtils.getSkyBlockID(itemStack).startsWith("ABIPHONE")) {
                    this.mc.field_71439_g.field_71071_by.field_70461_c = i;
                    break;
                }
                if (i != 8) continue;
                NotificationPublisher.queue("AutoRuby Auto Refill.", "No Abiphone Found in Hotbar.", NotificationType.ERROR, 10000);
            }
            Client.rightClick();
            this.shouldCallSB = true;
            this.shouldSwitchToAbi = false;
        }
        GuiScreen guiScreen = this.mc.field_71462_r;
        if (Client.inSkyblock && guiScreen instanceof GuiChest && (itemStack = ((GuiChest)guiScreen).field_147002_h) instanceof ContainerChest) {
            String string = this.getGuiName(guiScreen);
            if (string.startsWith("Abiphone") && this.shouldCallSB) {
                List list = itemStack.field_75151_b;
                for (Slot slot : list) {
                    ItemStack itemStack2 = slot.func_75211_c();
                    if (itemStack2 == null || !itemStack2.func_82837_s() || !StringUtils.func_76338_a((String)itemStack2.func_82833_r()).equals("Jotraeline Greatforge")) continue;
                    this.clickSlot(slot.field_75222_d, 0, 0, 0);
                    this.shouldCallSB = false;
                    this.shouldCombind = false;
                    break;
                }
            }
            if (string.equals("Drill Anvil")) {
                if (!this.shouldCombind) {
                    for (int i = 0; i < 9; ++i) {
                        ItemStack itemStack3 = this.mc.field_71439_g.field_71071_by.field_70462_a[i];
                        if (itemStack3 == null || itemStack3.func_77973_b() == null) continue;
                        if (this.shouldPreClickFuel && ItemUtils.getSkyBlockID(itemStack3).contains("DRILL") && !((Slot)itemStack.field_75151_b.get(29)).func_75216_d() && Block.func_149634_a((Item)((Slot)itemStack.field_75151_b.get(13)).func_75211_c().func_77973_b()) == Blocks.field_180401_cv) {
                            this.shouldPreClickFuel = false;
                            int n = i + 1;
                            this.clickSlot(80 + n, 0, 0, 1);
                            this.drillSlot = 80 + n;
                            return;
                        }
                        if (!ItemUtils.getSkyBlockID(itemStack3).contains("OIL_BARREL") || ((Slot)itemStack.field_75151_b.get(33)).func_75216_d() || !((Slot)itemStack.field_75151_b.get(29)).func_75216_d()) continue;
                        int n = i + 1;
                        this.clickSlot(80 + n, 0, 0, 1);
                        this.fuelSlot = 80 + n;
                        return;
                    }
                }
                if (((Slot)itemStack.field_75151_b.get(29)).func_75216_d() && ((Slot)itemStack.field_75151_b.get(33)).func_75216_d()) {
                    this.clickSlot(22, 0, 0, 0);
                    this.shouldCombind = true;
                }
                if (this.shouldCombind && !((Slot)itemStack.field_75151_b.get(29)).func_75216_d()) {
                    if (!this.shouldClickDrill && Block.func_149634_a((Item)((Slot)itemStack.field_75151_b.get(13)).func_75211_c().func_77973_b()) != Blocks.field_180401_cv) {
                        this.clickSlot(13, 0, 0, 0);
                        this.shouldClickDrill = true;
                        return;
                    }
                    if (!this.shouldPostClickFuel) {
                        this.clickSlot(this.drillSlot, 0, 0, 0);
                        this.clickSlot(33, 0, 0, 0);
                        this.clickSlot(this.fuelSlot, 0, 0, 0);
                        this.shouldPostClickFuel = true;
                        return;
                    }
                    this.mc.field_71439_g.func_71053_j();
                    this.reset();
                    if (this.restore) {
                        this.started = true;
                        this.restore = false;
                    }
                }
            }
        }
    }

    private void reset() {
        this.shouldSwitchToAbi = false;
        this.shouldCallSB = false;
        this.shouldPreClickFuel = true;
        this.shouldPostClickFuel = false;
        this.shouldClickDrill = false;
        this.shouldCombind = false;
    }

    @EventHandler
    private void tickWayPoints(EventTick eventTick) {
        if (this.started && !this.playerDetected) {
            KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74311_E.func_151463_i(), (boolean)true);
        } else if (this.playerDetected && this.mc.field_71474_y.field_74311_E.func_151468_f()) {
            KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74311_E.func_151463_i(), (boolean)false);
        }
        if (((Boolean)this.restart.getValue()).booleanValue()) {
            if (this.playerDetected) {
                if (this.restartTimer.hasReached(10000.0)) {
                    this.started = true;
                    this.playerDetected = false;
                    this.restartTimer.reset();
                }
            } else {
                this.restartTimer.reset();
            }
        } else {
            this.playerDetected = false;
        }
        if (this.started && ((Boolean)this.protect.getValue()).booleanValue()) {
            if (this.idkTicks < 10) {
                ++this.idkTicks;
                return;
            }
            boolean bl = false;
            String string = null;
            for (EntityPlayer entityPlayer : this.mc.field_71441_e.field_73010_i) {
                if (!PlayerListUtils.isInTablist(entityPlayer) || FriendManager.isFriend(entityPlayer.func_70005_c_())) continue;
                if (entityPlayer != this.mc.field_71439_g && this.mc.field_71439_g.func_70685_l((Entity)entityPlayer) && (double)MathUtil.distanceToEntity((Entity)this.mc.field_71439_g, (Entity)entityPlayer) < (Double)this.protectRange.getValue()) {
                    bl = true;
                    string = entityPlayer.func_70005_c_();
                    break;
                }
                bl = false;
            }
            if (bl) {
                this.started = false;
                this.playerDetected = true;
                WindowsNotification.show("AutoRuby Player Detected", "Player Name: " + string + ".", TrayIcon.MessageType.WARNING);
                NotificationPublisher.queue("AutoRuby", "Player Detected, Auto Disabled.", NotificationType.WARN, 10000);
                Helper.sendMessage("Player Name: " + string + ".");
            }
        }
        if (Keyboard.isKeyDown((int)56) && this.mc.field_71462_r == null && !this.started && this.mc.field_71476_x != null && this.mc.field_71476_x.func_178782_a() != null) {
            if (this.mc.field_71441_e.func_180495_p(this.mc.field_71476_x.func_178782_a()).func_177230_c() == Blocks.field_150350_a) {
                return;
            }
            if (!this.wps.contains(this.mc.field_71476_x.func_178782_a())) {
                this.wps.add(this.mc.field_71476_x.func_178782_a());
            }
        }
    }

    @EventHandler
    private void idk(EventTick eventTick) {
        if (!((Boolean)this.mithril.getValue()).booleanValue()) {
            GemstoneNuker gemstoneNuker = (GemstoneNuker)Client.instance.getModuleManager().getModuleByClass(GemstoneNuker.class);
            if (this.started) {
                Object object;
                BlockPos blockPos;
                BlockPos blockPos2;
                for (int i = 0; i < this.wps.size(); ++i) {
                    blockPos2 = (BlockPos)this.wps.get(i);
                    blockPos = new BlockPos(this.mc.field_71439_g.func_174791_d()).func_177977_b();
                    object = new BlockPos(new Vec3i(blockPos2.func_177958_n(), blockPos2.func_177956_o(), blockPos2.func_177952_p()));
                    BlockPos blockPos3 = new BlockPos(new Vec3i(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p()));
                    if (blockPos3.func_177958_n() != object.func_177958_n() || blockPos3.func_177956_o() != object.func_177956_o() || blockPos3.func_177952_p() != object.func_177952_p()) continue;
                    this.curIndex = i;
                    this.curBP = blockPos3;
                }
                if (this.curBP == null) {
                    return;
                }
                BlockPos blockPos4 = new BlockPos(this.mc.field_71439_g.func_174791_d()).func_177977_b();
                blockPos2 = new BlockPos(new Vec3i(this.curBP.func_177958_n(), this.curBP.func_177956_o(), this.curBP.func_177952_p()));
                blockPos = new BlockPos(new Vec3i(blockPos4.func_177958_n(), blockPos4.func_177956_o(), blockPos4.func_177952_p()));
                if ((float)Math.abs(blockPos2.func_177958_n() - blockPos.func_177958_n()) > 0.2f || (float)Math.abs(blockPos2.func_177956_o() - blockPos.func_177956_o()) > 0.2f || (float)Math.abs(blockPos2.func_177952_p() - blockPos.func_177952_p()) > 0.2f) {
                    this.nextBP = blockPos2;
                }
                if (this.curBP != new BlockPos(this.mc.field_71439_g.func_174791_d()).func_177977_b() && this.fallSafeTimer.hasReached(5000.0)) {
                    object = (ArrayList)this.wps.clone();
                    ((ArrayList)object).sort(Comparator.comparingDouble(this::lambda$idk$0));
                }
                this.fallSafeTimer.reset();
                if (this.curBP == null) {
                    this.curBP = (BlockPos)this.wps.get(this.curIndex);
                    if (new BlockPos(this.mc.field_71439_g.func_174791_d()).func_177977_b() != this.curBP) {
                        Helper.sendMessage("[AutoRuby] Something went wrong, Please enter '.ar start' again.");
                        this.started = false;
                        return;
                    }
                }
                if (!gemstoneNuker.gemstones.isEmpty()) {
                    this.timer.reset();
                    this.ewTimer.reset();
                    this.etherWarped = false;
                    this.nextBP = null;
                    this.mc.field_71439_g.field_71071_by.field_70461_c = 0;
                } else {
                    if (this.nextBP == null) {
                        if (this.curIndex + 1 < this.wps.size()) {
                            this.nextBP = (BlockPos)this.wps.get(this.curIndex + 1);
                        }
                        if (this.curIndex + 1 >= this.wps.size()) {
                            this.curIndex = -1;
                            this.nextBP = (BlockPos)this.wps.get(this.curIndex + 1);
                            if (((Boolean)this.waiting.getValue()).booleanValue()) {
                                this.shouldWait = true;
                            }
                        }
                        this.ewTimer.reset();
                        this.timer.reset();
                        this.etherWarped = false;
                    }
                    if (this.nextBP != null) {
                        object = gemstoneNuker.getRotations(this.nextBP);
                        this.mc.field_71439_g.field_70177_z = this.smoothRotation(this.mc.field_71439_g.field_70177_z, ((Rotation)object).getYaw(), 120.0f);
                        this.mc.field_71439_g.field_70125_A = this.smoothRotation(this.mc.field_71439_g.field_70125_A, ((Rotation)object).getPitch(), 120.0f);
                        if (this.timer.hasReached(75.0)) {
                            if (this.timer.hasReached(150.0)) {
                                if (this.nextBP != null) {
                                    this.etherWarp(this.nextBP);
                                }
                                if (gemstoneNuker.isEnabled()) {
                                    gemstoneNuker.setEnabled(false);
                                }
                                if (this.timer.hasReached(650.0)) {
                                    ++this.curIndex;
                                    this.curBP = this.nextBP;
                                    gemstoneNuker.setEnabled(true);
                                    this.nextBP = null;
                                    this.timer.reset();
                                    if (this.shouldWait) {
                                        this.started = false;
                                    }
                                }
                            } else {
                                this.ewTimer.reset();
                            }
                        }
                    }
                }
            }
        } else {
            MithrilNuker mithrilNuker = (MithrilNuker)Client.instance.getModuleManager().getModuleByClass(MithrilNuker.class);
            if (this.started) {
                Object object;
                BlockPos blockPos;
                BlockPos blockPos5;
                for (int i = 0; i < this.wps.size(); ++i) {
                    blockPos5 = (BlockPos)this.wps.get(i);
                    blockPos = new BlockPos(this.mc.field_71439_g.func_174791_d()).func_177977_b();
                    object = new BlockPos(new Vec3i(blockPos5.func_177958_n(), blockPos5.func_177956_o(), blockPos5.func_177952_p()));
                    BlockPos blockPos6 = new BlockPos(new Vec3i(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p()));
                    if (blockPos6.func_177958_n() != object.func_177958_n() || blockPos6.func_177956_o() != object.func_177956_o() || blockPos6.func_177952_p() != object.func_177952_p()) continue;
                    this.curIndex = i;
                    this.curBP = blockPos6;
                }
                if (this.curBP == null) {
                    return;
                }
                BlockPos blockPos7 = new BlockPos(this.mc.field_71439_g.func_174791_d()).func_177977_b();
                blockPos5 = new BlockPos(new Vec3i(this.curBP.func_177958_n(), this.curBP.func_177956_o(), this.curBP.func_177952_p()));
                blockPos = new BlockPos(new Vec3i(blockPos7.func_177958_n(), blockPos7.func_177956_o(), blockPos7.func_177952_p()));
                if (blockPos5.func_177958_n() != blockPos.func_177958_n() || blockPos5.func_177956_o() != blockPos.func_177956_o() || blockPos5.func_177952_p() != blockPos.func_177952_p()) {
                    this.nextBP = blockPos5;
                }
                if (this.curBP != new BlockPos(this.mc.field_71439_g.func_174791_d()).func_177977_b() && this.fallSafeTimer.hasReached(5000.0)) {
                    object = (ArrayList)this.wps.clone();
                    ((ArrayList)object).sort(Comparator.comparingDouble(this::lambda$idk$1));
                }
                this.fallSafeTimer.reset();
                if (this.curBP == null) {
                    this.curBP = (BlockPos)this.wps.get(this.curIndex);
                    if (new BlockPos(this.mc.field_71439_g.func_174791_d()).func_177977_b() != this.curBP) {
                        Helper.sendMessage("[AutoRuby] Something went wrong, Please enter '.ar start' again.");
                        this.started = false;
                        return;
                    }
                }
                if (!mithrilNuker.blockPoses.isEmpty()) {
                    this.timer.reset();
                    this.ewTimer.reset();
                    this.etherWarped = false;
                    this.nextBP = null;
                    this.mc.field_71439_g.field_71071_by.field_70461_c = 0;
                } else {
                    if (this.nextBP == null) {
                        if (this.curIndex + 1 < this.wps.size()) {
                            this.nextBP = (BlockPos)this.wps.get(this.curIndex + 1);
                        }
                        if (this.curIndex + 1 >= this.wps.size()) {
                            this.curIndex = -1;
                            this.nextBP = (BlockPos)this.wps.get(this.curIndex + 1);
                            if (((Boolean)this.waiting.getValue()).booleanValue()) {
                                this.shouldWait = true;
                            }
                        }
                        this.ewTimer.reset();
                        this.timer.reset();
                        this.etherWarped = false;
                    }
                    if (this.nextBP != null) {
                        object = mithrilNuker.getRotations(this.nextBP);
                        this.mc.field_71439_g.field_70177_z = this.smoothRotation(this.mc.field_71439_g.field_70177_z, ((Rotation)object).getYaw(), 120.0f);
                        this.mc.field_71439_g.field_70125_A = this.smoothRotation(this.mc.field_71439_g.field_70125_A, ((Rotation)object).getPitch(), 120.0f);
                        if (this.timer.hasReached(75.0)) {
                            if (this.timer.hasReached(150.0)) {
                                if (this.nextBP != null) {
                                    this.etherWarp(this.nextBP);
                                }
                                if (mithrilNuker.isEnabled()) {
                                    mithrilNuker.setEnabled(false);
                                }
                                if (this.timer.hasReached(650.0)) {
                                    ++this.curIndex;
                                    this.curBP = this.nextBP;
                                    mithrilNuker.setEnabled(true);
                                    this.nextBP = null;
                                    if (this.shouldWait) {
                                        this.started = false;
                                    }
                                    this.timer.reset();
                                }
                            } else {
                                this.ewTimer.reset();
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    private void tickWaiting(EventTick eventTick) {
        if (!((Boolean)this.waiting.getValue()).booleanValue() && this.shouldWait) {
            if (!this.started) {
                this.started = true;
            }
            this.shouldWait = false;
            this.waitTimer.reset();
        }
        if (this.shouldWait && !this.started) {
            if (this.waitTimer.hasReached(((Double)this.waitTime.getValue()).intValue() * 1000)) {
                Helper.sendMessage("Continued.");
                this.started = true;
                this.shouldWait = false;
                this.waitTimer.reset();
            }
        } else {
            this.waitTimer.reset();
        }
    }

    @EventHandler
    private void R3D(EventRender3D eventRender3D) {
        for (int i = 0; i < this.wps.size(); ++i) {
            BlockPos blockPos = (BlockPos)this.wps.get(i);
            BlockPos blockPos2 = null;
            blockPos2 = i + 1 == this.wps.size() ? (BlockPos)this.wps.get(0) : (BlockPos)this.wps.get(i + 1);
            if (blockPos == this.nextBP) {
                RenderUtil.drawSolidBlockESP(blockPos, ColorUtils.addAlpha(FadeUtil.PURPLE.getColor(), 190).getRGB(), 2.0f, eventRender3D.getPartialTicks());
            } else if (blockPos == this.curBP) {
                RenderUtil.drawSolidBlockESP(blockPos, ColorUtils.addAlpha(FadeUtil.GREEN.getColor(), 190).getRGB(), 2.0f, eventRender3D.getPartialTicks());
            } else {
                RenderUtil.drawSolidBlockESP(blockPos, ColorUtils.addAlpha(FadeUtil.BLUE.getColor(), 190).getRGB(), 2.0f, eventRender3D.getPartialTicks());
            }
            String string = (Boolean)this.hideCoords.getValue() != false ? EnumChatFormatting.LIGHT_PURPLE + "#" + (i + 1) : EnumChatFormatting.LIGHT_PURPLE + "#" + (i + 1) + EnumChatFormatting.WHITE + ": " + blockPos.func_177958_n() + " " + blockPos.func_177956_o() + " " + blockPos.func_177952_p();
            RenderUtil.renderTag(blockPos, string);
            if (!((Boolean)this.routeHelper.getValue()).booleanValue()) continue;
            double d = (double)blockPos2.func_177958_n() - this.mc.func_175598_ae().field_78730_l + 0.5;
            double d2 = (double)blockPos2.func_177956_o() - this.mc.func_175598_ae().field_78731_m + 0.5;
            double d3 = (double)blockPos2.func_177952_p() - this.mc.func_175598_ae().field_78728_n + 0.5;
            double d4 = (double)blockPos.func_177958_n() - this.mc.func_175598_ae().field_78730_l + 0.5;
            double d5 = (double)blockPos.func_177956_o() - this.mc.func_175598_ae().field_78731_m + 1.0 + (double)this.mc.field_71439_g.func_70047_e();
            double d6 = (double)blockPos.func_177952_p() - this.mc.func_175598_ae().field_78728_n + 0.5;
            double d7 = (double)blockPos.func_177958_n() - this.mc.func_175598_ae().field_78730_l + 0.5;
            double d8 = (double)blockPos.func_177956_o() - this.mc.func_175598_ae().field_78731_m + 0.5;
            double d9 = (double)blockPos.func_177952_p() - this.mc.func_175598_ae().field_78728_n + 0.5;
            RenderUtil.startDrawing();
            GL11.glEnable((int)2848);
            RenderUtil.setColor(Colors.MAGENTA.c);
            GL11.glLineWidth((float)3.0f);
            GL11.glBegin((int)1);
            GL11.glVertex3d((double)d, (double)d2, (double)d3);
            GL11.glVertex3d((double)d4, (double)d5, (double)d6);
            GL11.glEnd();
            RenderUtil.setColor(FadeUtil.BLUE.getColor().getRGB());
            GL11.glLineWidth((float)3.0f);
            GL11.glBegin((int)1);
            GL11.glVertex3d((double)d4, (double)d5, (double)d6);
            GL11.glVertex3d((double)d7, (double)d8, (double)d9);
            GL11.glEnd();
            GL11.glDisable((int)2848);
            RenderUtil.stopDrawing();
        }
    }

    @Override
    public void onEnable() {
        if (((Boolean)this.ugm.getValue()).booleanValue()) {
            MouseUtils.ungrabMouse();
        }
        if (!((Boolean)this.mithril.getValue()).booleanValue()) {
            GemstoneNuker gemstoneNuker = (GemstoneNuker)Client.instance.getModuleManager().getModuleByClass(GemstoneNuker.class);
            gemstoneNuker.mode.setValue(this.mode.getValue());
            gemstoneNuker.protect.setValue(false);
            if (!gemstoneNuker.isEnabled()) {
                gemstoneNuker.setEnabled(true);
            }
        } else {
            MithrilNuker mithrilNuker = (MithrilNuker)Client.instance.getModuleManager().getModuleByClass(MithrilNuker.class);
            if (!mithrilNuker.isEnabled()) {
                mithrilNuker.setEnabled(true);
            }
        }
        this.timer.reset();
        this.ewTimer.reset();
        this.etherWarped = false;
        Helper.sendMessage("[AutoRuby] Press ALT to add waypoints.");
        Helper.sendMessage("[AutoRuby] Use '.ar start' to start mining.");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (((Boolean)this.ugm.getValue()).booleanValue()) {
            MouseUtils.regrabMouse();
        }
        if (!((Boolean)this.mithril.getValue()).booleanValue()) {
            GemstoneNuker gemstoneNuker = (GemstoneNuker)Client.instance.getModuleManager().getModuleByClass(GemstoneNuker.class);
            if (gemstoneNuker.isEnabled()) {
                gemstoneNuker.setEnabled(false);
            }
        } else {
            MithrilNuker mithrilNuker = (MithrilNuker)Client.instance.getModuleManager().getModuleByClass(MithrilNuker.class);
            if (mithrilNuker.isEnabled()) {
                mithrilNuker.setEnabled(false);
            }
        }
        KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74311_E.func_151463_i(), (boolean)false);
        Helper.sendMessage("Remember to save the waypoints~");
        this.timer.reset();
        this.ewTimer.reset();
        this.etherWarped = false;
        this.curBP = null;
        this.nextBP = null;
        this.curIndex = 0;
        super.onDisable();
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        Helper.sendMessage("[MacroProtection] Auto Disabled " + EnumChatFormatting.GREEN + this.getName() + EnumChatFormatting.GRAY + " due to World Change.");
        this.setEnabled(false);
    }

    private void etherWarp(BlockPos blockPos) {
        if (this.etherWarped) {
            return;
        }
        if (this.mc.field_71439_g.func_70694_bm() != null && !ItemUtils.getSkyBlockID(this.mc.field_71439_g.func_70694_bm()).equals("ASPECT_OF_THE_VOID") || this.mc.field_71439_g.func_70694_bm() == null) {
            this.lastSlot = this.mc.field_71439_g.field_71071_by.field_70461_c;
            for (int i = 0; i < 9; ++i) {
                ItemStack itemStack = this.mc.field_71439_g.field_71071_by.field_70462_a[i];
                if (itemStack == null || itemStack.func_77973_b() == null || !ItemUtils.getSkyBlockID(itemStack).equals("ASPECT_OF_THE_VOID")) continue;
                this.mc.field_71439_g.field_71071_by.field_70461_c = i;
                break;
            }
        }
        if (this.ewTimer.hasReached(250.0)) {
            Client.rightClick();
            this.ewTimer.reset();
            this.etherWarped = true;
            this.mc.field_71439_g.field_71071_by.field_70461_c = 0;
        }
    }

    private void loadYogs() {
        this.yogs.clear();
        for (Entity entity : this.mc.field_71441_e.field_72996_f) {
            if (entity.field_70128_L || !entity.func_70089_S() || !(entity instanceof EntityMagmaCube) || !((double)this.mc.field_71439_g.func_70032_d(entity) < (Double)this.yogRange.getValue())) continue;
            this.yogs.add((EntityMagmaCube)entity);
        }
        this.yogs.sort(Comparator.comparingDouble(this::lambda$loadYogs$2));
    }

    private void clickSlot(int n, int n2, int n3, int n4) {
        this.mc.field_71442_b.func_78753_a(this.mc.field_71439_g.field_71070_bA.field_75152_c + n2, n, n3, n4, (EntityPlayer)this.mc.field_71439_g);
    }

    public String getGuiName(GuiScreen guiScreen) {
        if (guiScreen instanceof GuiChest) {
            return ((ContainerChest)((GuiChest)guiScreen).field_147002_h).func_85151_d().func_145748_c_().func_150260_c();
        }
        return "";
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

    private double lambda$loadYogs$2(EntityMagmaCube entityMagmaCube) {
        return this.mc.field_71439_g.func_70032_d((Entity)entityMagmaCube);
    }

    private double lambda$idk$1(BlockPos blockPos) {
        return this.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p());
    }

    private double lambda$idk$0(BlockPos blockPos) {
        return this.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p());
    }
}

