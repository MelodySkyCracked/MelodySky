/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiMultiplayer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.server.S12PacketEntityVelocity
 *  net.minecraft.network.play.server.S29PacketSoundEffect
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.StringUtils
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.Fishing;

import java.awt.Color;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventPacketRecieve;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.MouseUtils;
import xyz.Melody.Utils.game.PlayerListUtils;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.others.PlayerList;

public class SlugFishing
extends Module {
    private int tickTimer1 = 0;
    private Vec3 soundVec = null;
    private Option useEmber = new Option("UseEmber", false);
    private Numbers emberSlot = new Numbers("Ember Slot", 5.0, 0.0, 20.0, 1.0);
    private Numbers trophySlot = new Numbers("Trophy Slot", 5.0, 0.0, 20.0, 1.0);
    private Numbers emberDelay = new Numbers("EmberSwapDelay", 750.0, 250.0, 1500.0, 10.0);
    private Numbers fishingTimer = new Numbers("FishingTimer", 25.0, 20.0, 30.0, 1.0);
    private Option plist = new Option("EnablePlayerList", true);
    private Option unGrab = new Option("UnGrabMouse", false);
    private Option lockRod = new Option("LockRod", false);
    private Option admin = new Option("AntiAdmin", false);
    private Option lockView = new Option("LockView", false);
    private Option dead = new Option("DeathCheck", true);
    private Option escape = new Option("Escape", false);
    private Numbers escapeRange = new Numbers("Escape Range", 5.0, 0.0, 20.0, 1.0);
    private Option showDebug = new Option("Show Debug", false);
    private Mode rotationMode = new Mode("RotationMode", rotations.values(), rotations.Yaw);
    private Option rotation = new Option("NoRotationAFK", true);
    private Mode moveMode = new Mode("MoveMode", moves.values(), moves.AD);
    private Option move = new Option("NoMovingAFK", true);
    private Option holdShift = new Option("Sneaking", false);
    private Numbers angle = new Numbers("RotationAngle", 1.0, 1.0, 5.0, 1.0);
    private Option soundBB = new Option("SoundBox", false);
    private Numbers soundRadius = new Numbers("SoundRadius", 0.5, 0.1, 5.0, 0.1);
    private Enum currentStage = stage.NONE;
    private boolean backRotaion = false;
    private boolean soundReady = false;
    private boolean soundCDReady = false;
    private boolean motionReady = false;
    private Vec3 lockedVec = new Vec3(0.0, 0.0, 0.0);
    private TimerUtil moveTimer = new TimerUtil();
    private boolean moveDone = false;
    private boolean moved = false;
    private boolean needToEscape = false;
    private TimerUtil escapeDelay = new TimerUtil();
    private boolean swapedToEmberArmor = false;
    private boolean swapedToTrophyArmor = false;
    private TimerUtil secondTimer = new TimerUtil();
    private TimerUtil finishedTimer = new TimerUtil();
    private TimerUtil throwFaileTimer = new TimerUtil();
    private TimerUtil wdFaileTimer = new TimerUtil();
    private TimerUtil emberTimer = new TimerUtil();
    private TimerUtil timerTimer = new TimerUtil();
    private int page = 0;
    private int slot = 0;
    private boolean shouldOpenWardrobe = false;
    private static SlugFishing INSTANCE;
    private boolean escaped = false;

    public SlugFishing() {
        super("SlugFishing", new String[]{"slug"}, ModuleType.Fishing);
        this.addValues(this.useEmber, this.emberSlot, this.trophySlot, this.emberDelay, this.fishingTimer, this.plist, this.unGrab, this.lockRod, this.lockView, this.dead, this.admin, this.escape, this.escapeRange, this.showDebug, this.rotationMode, this.rotation, this.moveMode, this.move, this.holdShift, this.angle, this.soundBB, this.soundRadius);
        this.setColor(new Color(191, 191, 191).getRGB());
        this.setModInfo("Auto Swap Ember/Trophy Armor and Fishing.");
    }

    public static SlugFishing getInstance() {
        if (INSTANCE == null) {
            INSTANCE = (SlugFishing)Client.instance.getModuleManager().getModuleByClass(SlugFishing.class);
        }
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        PlayerList playerList;
        if (((Boolean)this.unGrab.getValue()).booleanValue()) {
            MouseUtils.ungrabMouse();
        }
        if (this.mc.field_71476_x != null && this.mc.field_71476_x.field_72308_g == null) {
            this.lockedVec = this.mc.field_71476_x.field_72307_f;
        }
        if (!(playerList = (PlayerList)Client.instance.getModuleManager().getModuleByClass(PlayerList.class)).isEnabled() && ((Boolean)this.plist.getValue()).booleanValue()) {
            playerList.setEnabled(true);
        }
        this.emberTimer.reset();
        this.timerTimer.reset();
        this.throwFaileTimer.reset();
        this.finishedTimer.reset();
        this.secondTimer.reset();
        this.wdFaileTimer.reset();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        PlayerList playerList;
        if (((Boolean)this.holdShift.getValue()).booleanValue()) {
            KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74311_E.func_151463_i(), (boolean)false);
        }
        if (((Boolean)this.unGrab.getValue()).booleanValue()) {
            MouseUtils.regrabMouse();
        }
        if ((playerList = (PlayerList)Client.instance.getModuleManager().getModuleByClass(PlayerList.class)).isEnabled() && ((Boolean)this.plist.getValue()).booleanValue()) {
            playerList.setEnabled(false);
        }
        this.page = 0;
        this.slot = 0;
        this.shouldOpenWardrobe = false;
        this.emberTimer.reset();
        this.timerTimer.reset();
        this.throwFaileTimer.reset();
        this.finishedTimer.reset();
        this.secondTimer.reset();
        this.wdFaileTimer.reset();
        this.needToEscape = false;
        this.lockedVec = new Vec3(0.0, 0.0, 0.0);
        this.moveDone = false;
        this.escaped = false;
        this.tickTimer1 = 0;
        this.soundVec = null;
        this.currentStage = stage.NONE;
        this.backRotaion = false;
        this.soundReady = false;
        this.soundCDReady = false;
        this.motionReady = false;
        this.escapeDelay.reset();
        this.escaped = false;
        super.onDisable();
    }

    @EventHandler
    private void onPlayerDetected(EventTick eventTick) {
        if (this == false) {
            this.needToEscape = true;
        }
        if (((Boolean)this.escape.getValue()).booleanValue() && !this.escaped) {
            if (this.needToEscape && this.escapeDelay.hasReached(5000.0)) {
                Helper.sendMessage("[AutoFish] Player Detected, Warping to Private Island.");
                this.mc.field_71439_g.func_71165_d("/l");
                this.escaped = true;
                this.setEnabled(false);
                this.escapeDelay.reset();
            }
            if (!this.needToEscape) {
                this.escapeDelay.reset();
            }
        }
        if (((Boolean)this.admin.getValue()).booleanValue() && PlayerListUtils.tabContains("[ADMIN]")) {
            Helper.sendMessage("[AutoFish] Admin Detected, Warping to Private Island.");
            this.mc.field_71439_g.func_71165_d("/l");
        }
    }

    @SubscribeEvent(receiveCanceled=true)
    public void onChat(ClientChatReceivedEvent clientChatReceivedEvent) {
        String string = StringUtils.func_76338_a((String)clientChatReceivedEvent.message.func_150260_c());
        if (string.startsWith("From [ADMIN]") || string.startsWith("From [GM]") || string.startsWith("From [YOUTUBE]")) {
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
    private void onDead(EventTick eventTick) {
        if (!((Boolean)this.dead.getValue()).booleanValue()) {
            return;
        }
        if (!this.mc.field_71439_g.func_70089_S() || this.mc.field_71439_g.field_70128_L) {
            Helper.sendMessage("[AutoFish] Detected mc.thePlayer.isDead, Disabled AutoFish.");
            this.setEnabled(false);
            return;
        }
        if (this.mc.field_71439_g.field_70173_aa <= 1) {
            Helper.sendMessage("[AutoFish] Detected mc.thePlayer.tickExisted <= 1, Disabled AutoFish.");
            this.setEnabled(false);
            return;
        }
        if (this.mc.field_71439_g.func_110143_aJ() == 0.0f) {
            Helper.sendMessage("[AutoFish] Detected mc.thePlayer.getHealth() == 0, Disabled AutoFish.");
            this.setEnabled(false);
            return;
        }
    }

    @EventHandler
    private void onLockRod(EventTick eventTick) {
        if (!((Boolean)this.lockRod.getValue()).booleanValue()) {
            return;
        }
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = this.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (itemStack == null || itemStack.func_77973_b() == null || !(itemStack.func_77973_b() instanceof ItemFishingRod)) continue;
            this.mc.field_71439_g.field_71071_by.field_70461_c = i;
            break;
        }
    }

    @EventHandler
    private void lockView(EventRender2D eventRender2D) {
        if (!((Boolean)this.lockView.getValue()).booleanValue()) {
            return;
        }
        if (this.currentStage != stage.NONE) {
            Rotation rotation = this.vec3ToRotation(this.lockedVec);
            this.mc.field_71439_g.field_70177_z = this.smoothRotation(this.mc.field_71439_g.field_70177_z, rotation.yaw, 30.0f);
            this.mc.field_71439_g.field_70125_A = this.smoothRotation(this.mc.field_71439_g.field_70125_A, rotation.pitch, 30.0f);
        }
    }

    @EventHandler
    private void onDebugDraw(EventRender2D eventRender2D) {
        if (!((Boolean)this.showDebug.getValue()).booleanValue()) {
            return;
        }
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        this.mc.field_71466_p.func_78276_b("Current Stage: " + this.currentStage, scaledResolution.func_78326_a() / 2 + 6, scaledResolution.func_78328_b() / 2 + 6, -1);
        this.mc.field_71466_p.func_78276_b("Time: " + (this.secondTimer.getCurrentMS() - this.secondTimer.getLastMS()) / 1000L, scaledResolution.func_78326_a() / 2 + 6, scaledResolution.func_78328_b() / 2 + 18, -1);
        this.mc.field_71466_p.func_78276_b("SoundCDTimer: " + this.tickTimer1 + (this.tickTimer1 == 50 ? " (Ready)" : ""), scaledResolution.func_78326_a() / 2 + 6, scaledResolution.func_78328_b() / 2 + 30, -1);
        this.mc.field_71466_p.func_78276_b("SoundMonitor: " + this.soundReady, scaledResolution.func_78326_a() / 2 + 6, scaledResolution.func_78328_b() / 2 + 54, -1);
        this.mc.field_71466_p.func_78276_b("SoundReady: " + this.soundCDReady, scaledResolution.func_78326_a() / 2 + 6, scaledResolution.func_78328_b() / 2 + 66, -1);
        this.mc.field_71466_p.func_78276_b("MotionReady: " + this.motionReady, scaledResolution.func_78326_a() / 2 + 6, scaledResolution.func_78328_b() / 2 + 78, -1);
    }

    @EventHandler
    private void onSneak(EventTick eventTick) {
        if (((Boolean)this.holdShift.getValue()).booleanValue()) {
            KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74311_E.func_151463_i(), (boolean)true);
        }
    }

    private void swapEmber(int n) {
        Helper.sendMessage("Swap to Ember Armor.");
        this.openWD(1, n);
        this.swapedToEmberArmor = true;
    }

    private void swapTrophy(int n) {
        Helper.sendMessage("Swap to Trophy Hunter Armor.");
        this.openWD(1, n);
        this.swapedToTrophyArmor = true;
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (this.currentStage == stage.NONE) {
            if (this.mc.field_71439_g.func_70694_bm() == null || !(this.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemFishingRod)) {
                Helper.sendMessage("[Slug Fishing] Please Hold a Fishing rod to use This Feature.");
                this.setEnabled(false);
                return;
            }
            this.currentStage = stage.EMBER;
        }
        if (this.currentStage == stage.FINISH) {
            this.soundReady = false;
            this.soundCDReady = false;
            this.motionReady = false;
            this.tickTimer1 = 0;
            if (this.finishedTimer.hasReached(200.0)) {
                this.currentStage = stage.EMBER;
                this.finishedTimer.reset();
                this.emberTimer.reset();
            }
        }
        if (this.currentStage == stage.EMBER) {
            if (((Boolean)this.useEmber.getValue()).booleanValue()) {
                if (this.emberTimer.hasReached(200.0)) {
                    if (((Boolean)this.rotation.getValue()).booleanValue()) {
                        if (this.backRotaion) {
                            if (this.rotationMode.getValue() == rotations.Yaw) {
                                this.mc.field_71439_g.field_70177_z -= ((Double)this.angle.getValue()).floatValue();
                            } else {
                                this.mc.field_71439_g.field_70125_A -= ((Double)this.angle.getValue()).floatValue();
                            }
                            this.backRotaion = !this.backRotaion;
                        } else {
                            if (this.rotationMode.getValue() == rotations.Yaw) {
                                this.mc.field_71439_g.field_70177_z += ((Double)this.angle.getValue()).floatValue();
                            } else {
                                this.mc.field_71439_g.field_70125_A += ((Double)this.angle.getValue()).floatValue();
                            }
                            this.backRotaion = !this.backRotaion;
                        }
                    }
                    this.swapEmber(((Double)this.emberSlot.getValue()).intValue());
                    if (this.swapedToEmberArmor) {
                        this.currentStage = stage.TIMER;
                    }
                    this.emberTimer.reset();
                    this.timerTimer.reset();
                }
            } else {
                this.swapedToEmberArmor = true;
                this.currentStage = stage.TIMER;
                this.emberTimer.reset();
                this.timerTimer.reset();
            }
        }
        if (this.currentStage == stage.TIMER && this.timerTimer.hasReached(((Double)this.emberDelay.getValue()).longValue())) {
            if (this.swapedToEmberArmor) {
                if (this.mc.field_71439_g.field_71104_cf == null) {
                    Client.rightClick();
                }
                this.swapedToEmberArmor = false;
                this.secondTimer.reset();
            }
            if (this.secondTimer.hasReached(((Double)this.fishingTimer.getValue()).intValue() * 1000)) {
                this.currentStage = stage.TROPHY;
                this.secondTimer.reset();
            }
            this.timerTimer.reset();
        }
        if (this.currentStage == stage.TROPHY) {
            if (((Boolean)this.useEmber.getValue()).booleanValue()) {
                this.swapTrophy(((Double)this.trophySlot.getValue()).intValue());
                if (this.swapedToTrophyArmor) {
                    this.finishedTimer.reset();
                    this.throwFaileTimer.reset();
                    this.currentStage = stage.WAITING;
                }
            } else {
                this.finishedTimer.reset();
                this.throwFaileTimer.reset();
                this.currentStage = stage.WAITING;
            }
        }
        if (this.currentStage == stage.WAITING) {
            this.swapedToTrophyArmor = false;
            if (this.soundCDReady && this.motionReady) {
                if (this.mc.field_71439_g.field_71104_cf != null) {
                    Client.rightClick();
                }
                this.finishedTimer.reset();
                this.throwFaileTimer.reset();
                this.currentStage = stage.FINISH;
            }
        }
        if (this.mc.field_71439_g.func_70694_bm() == null || !(this.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemFishingRod)) {
            this.currentStage = stage.NONE;
        }
        if (this.currentStage == stage.NONE) {
            this.reset();
            this.tickTimer1 = 0;
            this.soundVec = null;
        }
        if (this.currentStage == stage.WAITING && this.mc.field_71439_g.field_71104_cf == null && this.throwFaileTimer.hasReached(500.0)) {
            this.currentStage = stage.NONE;
            this.throwFaileTimer.reset();
        }
    }

    @EventHandler
    public void onPacket(EventPacketRecieve eventPacketRecieve) {
        S29PacketSoundEffect s29PacketSoundEffect;
        if (this.currentStage != stage.WAITING) {
            return;
        }
        Packet packet = eventPacketRecieve.getPacket();
        if (packet instanceof S29PacketSoundEffect && ((s29PacketSoundEffect = (S29PacketSoundEffect)packet).func_149212_c().contains("game.player.swim.splash") || s29PacketSoundEffect.func_149212_c().contains("random.splash"))) {
            float f = ((Double)this.soundRadius.getValue()).floatValue();
            if (Math.abs(s29PacketSoundEffect.func_149207_d() - this.mc.field_71439_g.field_71104_cf.field_70165_t) <= (double)f && Math.abs(s29PacketSoundEffect.func_149210_f() - this.mc.field_71439_g.field_71104_cf.field_70161_v) <= (double)f) {
                this.soundReady = true;
                this.soundVec = new Vec3(s29PacketSoundEffect.func_149207_d(), s29PacketSoundEffect.func_149211_e(), s29PacketSoundEffect.func_149210_f());
            }
        }
        if (packet instanceof S12PacketEntityVelocity && (s29PacketSoundEffect = (S12PacketEntityVelocity)packet).func_149411_d() == 0 && s29PacketSoundEffect.func_149410_e() != 0 && s29PacketSoundEffect.func_149409_f() == 0) {
            this.motionReady = true;
        }
    }

    @EventHandler
    private void onMove(EventTick eventTick) {
        if (((Boolean)this.move.getValue()).booleanValue()) {
            int n;
            int n2 = this.moveMode.getValue() == moves.AD ? this.mc.field_71474_y.field_74370_x.func_151463_i() : this.mc.field_71474_y.field_74351_w.func_151463_i();
            int n3 = n = this.moveMode.getValue() == moves.AD ? this.mc.field_71474_y.field_74366_z.func_151463_i() : this.mc.field_71474_y.field_74368_y.func_151463_i();
            if (!this.moveDone) {
                if (this.currentStage == stage.FINISH && !this.moved) {
                    this.moveTimer.reset();
                    KeyBinding.func_74510_a((int)n2, (boolean)true);
                    this.moved = true;
                }
                if (this.moved && this.moveTimer.hasReached(50.0)) {
                    KeyBinding.func_74510_a((int)n2, (boolean)false);
                    if (this.moveTimer.hasReached(100.0)) {
                        KeyBinding.func_74510_a((int)n, (boolean)true);
                        if (this.moveTimer.hasReached(150.0)) {
                            KeyBinding.func_74510_a((int)n, (boolean)false);
                            this.moveDone = true;
                        }
                    }
                }
            } else if (this.currentStage != stage.FINISH) {
                this.moved = false;
                this.moveTimer.reset();
                this.moveDone = false;
                KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74370_x.func_151463_i(), (boolean)false);
                KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74366_z.func_151463_i(), (boolean)false);
            }
        }
    }

    @EventHandler
    private void onReady(EventTick eventTick) {
    }

    @EventHandler
    private void onCDReady(EventTick eventTick) {
        if (this.currentStage == stage.WAITING) {
            if (this.tickTimer1 < 50) {
                this.soundCDReady = false;
                this.soundReady = false;
                ++this.tickTimer1;
            } else {
                this.soundCDReady = this.soundReady;
            }
        }
    }

    @EventHandler
    private void onR3D(EventRender3D eventRender3D) {
        AxisAlignedBB axisAlignedBB;
        if (!((Boolean)this.soundBB.getValue()).booleanValue()) {
            return;
        }
        if (this.mc.field_71439_g.field_71104_cf != null) {
            axisAlignedBB = this.mc.field_71439_g.field_71104_cf.func_174813_aQ().func_72314_b(((Double)this.soundRadius.getValue()).doubleValue(), 0.0, ((Double)this.soundRadius.getValue()).doubleValue());
            RenderUtil.drawOutlinedBoundingBox(axisAlignedBB, Colors.AQUA.c, 2.0f, eventRender3D.getPartialTicks());
        }
        if (this.soundVec != null) {
            axisAlignedBB = new AxisAlignedBB(this.soundVec.field_72450_a + 0.05, this.soundVec.field_72448_b + 0.05, this.soundVec.field_72449_c + 0.05, this.soundVec.field_72450_a - 0.05, this.soundVec.field_72448_b - 0.05, this.soundVec.field_72449_c - 0.05);
            RenderUtil.drawOutlinedBoundingBox(axisAlignedBB, Colors.RED.c, 2.0f, eventRender3D.getPartialTicks());
        }
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        Helper.sendMessage("[MacroProtection] Auto Disabled " + EnumChatFormatting.GREEN + this.getName() + EnumChatFormatting.GRAY + " due to World Change.");
        this.setEnabled(false);
    }

    private void reset() {
        this.soundReady = false;
        this.soundCDReady = false;
        this.motionReady = false;
    }

    public void openWD(int n, int n2) {
        this.page = n;
        this.slot = n2;
        this.shouldOpenWardrobe = true;
        this.mc.field_71439_g.func_71165_d("/pets");
        this.wdFaileTimer.reset();
    }

    @EventHandler
    public void onDrawGuiBackground(EventTick eventTick) {
        String string;
        Container container;
        GuiScreen guiScreen = this.mc.field_71462_r;
        if (Client.inSkyblock && this.shouldOpenWardrobe && guiScreen instanceof GuiChest && (container = ((GuiChest)guiScreen).field_147002_h) instanceof ContainerChest && (string = this.getGuiName(guiScreen)).startsWith("Pets")) {
            this.clickSlot(48, 0);
            this.clickSlot(32, 1);
            if (this.page == 1) {
                if (this.slot > 0 && this.slot < 10) {
                    this.clickSlot(this.slot + 35, 2);
                    this.mc.field_71439_g.func_71053_j();
                }
            } else if (this.page == 2) {
                this.clickSlot(53, 2);
                if (this.slot > 0 && this.slot < 10) {
                    this.clickSlot(this.slot + 35, 3);
                    this.mc.field_71439_g.func_71053_j();
                }
            }
            this.shouldOpenWardrobe = false;
        }
    }

    public String getGuiName(GuiScreen guiScreen) {
        if (guiScreen instanceof GuiChest) {
            return ((ContainerChest)((GuiChest)guiScreen).field_147002_h).func_85151_d().func_145748_c_().func_150260_c();
        }
        return "";
    }

    private void clickSlot(int n, int n2) {
        this.mc.field_71442_b.func_78753_a(this.mc.field_71439_g.field_71070_bA.field_75152_c + n2, n, 0, 0, (EntityPlayer)this.mc.field_71439_g);
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

    public Rotation vec3ToRotation(Vec3 vec3) {
        double d = vec3.field_72450_a - this.mc.field_71439_g.field_70165_t;
        double d2 = vec3.field_72448_b - this.mc.field_71439_g.field_70163_u - (double)this.mc.field_71439_g.func_70047_e();
        double d3 = vec3.field_72449_c - this.mc.field_71439_g.field_70161_v;
        double d4 = Math.sqrt(d * d + d3 * d3);
        float f = (float)(-Math.atan2(d4, d2));
        float f2 = (float)Math.atan2(d3, d);
        f = (float)SlugFishing.wrapAngleTo180(((double)(f * 180.0f) / Math.PI + 90.0) * -1.0);
        f2 = (float)SlugFishing.wrapAngleTo180((double)(f2 * 180.0f) / Math.PI - 90.0);
        return new Rotation(f, f2);
    }

    private static double wrapAngleTo180(double d) {
        return d - Math.floor(d / 360.0 + 0.5) * 360.0;
    }

    private static class Rotation {
        public float pitch;
        public float yaw;

        public Rotation(float f, float f2) {
            this.pitch = f;
            this.yaw = f2;
        }
    }

    static enum stage {
        NONE,
        EMBER,
        TIMER,
        TROPHY,
        WAITING,
        FINISH;

    }

    static enum moves {
        WS,
        AD;

    }

    static enum rotations {
        Yaw,
        Pitch;

    }
}

