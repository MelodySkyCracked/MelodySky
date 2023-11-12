/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.util.Vec3
 *  net.minecraft.util.Vec3i
 *  net.minecraft.world.World
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Keyboard
 */
package xyz.Melody.module.modules.macros.Farming;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.misc.EventKey;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.game.InventoryUtils;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class ForagingMacro
extends Module {
    private Option useRod = new Option("Use Rod", false);
    private Numbers treeSlot = new Numbers("Sapling Slot", 0.0, 0.0, 8.0, 1.0);
    private Numbers bonemealSlot = new Numbers("Bonemeal Slot", 1.0, 0.0, 8.0, 1.0);
    private Numbers axeSlot = new Numbers("Axe Slot", 2.0, 0.0, 8.0, 1.0);
    private Numbers rodSlot = new Numbers("Rod Slot", 3.0, 0.0, 8.0, 1.0);
    private Numbers delay = new Numbers("PlaceDelay", 500.0, 0.0, 5000.0, 100.0);
    private Numbers timeBreak = new Numbers("TimeBeforeBreak", 500.0, 100.0, 1000.0, 10.0);
    private Numbers breakDelay = new Numbers("BreakDelay", 2000.0, 1000.0, 3000.0, 50.0);
    private ArrayList dirtPos = new ArrayList();
    private TimerUtil yepTimer = new TimerUtil();
    private TimerUtil failSafeTimer = new TimerUtil();
    private TimerUtil shabTimer = new TimerUtil();
    private ForagingState foragingState;
    private int currentTree = 1;
    private int treeWait;

    public ForagingMacro() {
        super("ForagingMacro", new String[]{"am"}, ModuleType.Macros);
        this.addValues(this.useRod, this.treeSlot, this.bonemealSlot, this.axeSlot, this.rodSlot, this.delay, this.timeBreak, this.breakDelay);
        this.setModInfo("Auto Place -> Grow -> Break Trees.");
    }

    @Override
    public void onEnable() {
        Helper.sendMessage("[ForagingMacro] Aim a Block And Press ALT to Set Dirt Position.");
        this.foragingState = ForagingState.TREE;
        this.currentTree = 1;
        this.yepTimer.reset();
        this.failSafeTimer.reset();
        this.shabTimer.reset();
        this.treeWait = ((Double)this.delay.getValue()).intValue();
        this.dirtPos.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    private void onKey(EventKey eventKey) {
        if (Keyboard.getKeyName((int)eventKey.getKey()).toLowerCase().contains("lmenu") && this.dirtPos.size() < 4) {
            this.dirtPos.add(this.mc.field_71476_x.func_178782_a());
        }
    }

    @EventHandler
    private void drawBlocks(EventRender3D eventRender3D) {
        for (BlockPos blockPos : this.dirtPos) {
            RenderUtil.drawFullBlockESP(blockPos, new Color(Colors.MAGENTA.c), eventRender3D.getPartialTicks());
        }
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (this.dirtPos.size() < 4) {
            return;
        }
        int n = InventoryUtils.getAmountInHotbar("Jungle Sapling");
        int n2 = InventoryUtils.getAmountInHotbar("Enchanted Bone Meal");
        if (n < 5 || n2 < 2) {
            return;
        }
        switch (this.foragingState) {
            case TREE: {
                if (!this.failSafeTimer.hasReached((Double)this.breakDelay.getValue()) || !this.yepTimer.hasReached(this.treeWait)) break;
                this.swapSlot(((Double)this.treeSlot.getValue()).intValue());
                BlockPos blockPos = (BlockPos)this.dirtPos.get(this.currentTree - 1);
                float[] fArray = this.getRotations(blockPos, EnumFacing.DOWN);
                this.mc.field_71439_g.field_70177_z = fArray[0];
                this.mc.field_71439_g.field_70125_A = fArray[1];
                this.yepTimer.reset();
                this.foragingState = ForagingState.LOOKING;
                break;
            }
            case BONEMEAL: {
                if (!this.yepTimer.hasReached(((Double)this.delay.getValue()).intValue())) break;
                this.swapSlot(((Double)this.bonemealSlot.getValue()).intValue());
                Client.rightClick();
                this.yepTimer.reset();
                this.shabTimer.reset();
                this.foragingState = ForagingState.RODSWAP;
                break;
            }
            case RODSWAP: {
                if (((Boolean)this.useRod.getValue()).booleanValue()) {
                    if (!this.yepTimer.hasReached(((Double)this.delay.getValue()).intValue())) break;
                    this.silentUse(((Double)this.axeSlot.getValue()).intValue(), ((Double)this.rodSlot.getValue()).intValue());
                    Client.rightClick();
                    this.yepTimer.reset();
                    this.failSafeTimer.reset();
                    this.shabTimer.reset();
                    this.foragingState = ForagingState.HARVEST;
                    this.swapSlot(((Double)this.axeSlot.getValue()).intValue());
                    break;
                }
                this.yepTimer.reset();
                this.failSafeTimer.reset();
                this.shabTimer.reset();
                this.foragingState = ForagingState.HARVEST;
                this.swapSlot(((Double)this.axeSlot.getValue()).intValue());
                break;
            }
            case HARVEST: {
                if (this.failSafeTimer.hasReached((Double)this.breakDelay.getValue())) {
                    this.foragingState = ForagingState.TREE;
                    this.currentTree = 1;
                }
                if (!this.shabTimer.hasReached((Double)this.timeBreak.getValue()) || this.mc.field_71476_x == null || this.mc.field_71476_x.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK || !this.yepTimer.hasReached(((Double)this.timeBreak.getValue()).intValue())) break;
                if (this.closestLog() != null) {
                    this.harvest();
                } else {
                    this.yepTimer.reset();
                    this.foragingState = ForagingState.TREE;
                    this.currentTree = 1;
                    this.treeWait = 500;
                }
                this.shabTimer.reset();
                break;
            }
        }
    }

    @EventHandler
    public void onTickWorld(EventTick eventTick) {
        if (this.foragingState == ForagingState.LOOKING && this.yepTimer.hasReached((Double)this.delay.getValue())) {
            Client.rightClick();
            this.yepTimer.reset();
            if (this.currentTree < 4) {
                ++this.currentTree;
                this.foragingState = ForagingState.TREE;
                this.treeWait = ((Double)this.delay.getValue()).intValue();
            } else {
                this.foragingState = ForagingState.BONEMEAL;
            }
        }
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        Helper.sendMessage("[MacroProtection] Auto Disabled " + EnumChatFormatting.GREEN + this.getName() + EnumChatFormatting.GRAY + " due to World Change.");
        this.setEnabled(false);
    }

    private void harvest() {
        MovingObjectPosition movingObjectPosition = this.mc.field_71476_x;
        BlockPos blockPos = this.closestLog();
        if (blockPos == null) {
            return;
        }
        movingObjectPosition.field_72307_f = new Vec3((Vec3i)blockPos);
        EnumFacing enumFacing = movingObjectPosition.field_178784_b;
        if (enumFacing != null && this.mc.field_71439_g != null) {
            this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, enumFacing));
        }
        this.mc.field_71439_g.func_71038_i();
    }

    private BlockPos closestLog() {
        if (this.mc.field_71441_e == null || this.mc.field_71439_g == null) {
            return null;
        }
        float f = 2.0f;
        BlockPos blockPos = this.mc.field_71439_g.func_180425_c();
        Vec3i vec3i = new Vec3i((double)f, (double)f, (double)f);
        ArrayList<Vec3> arrayList = new ArrayList<Vec3>();
        if (blockPos != null) {
            for (BlockPos blockPos2 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i))) {
                IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos2);
                if (iBlockState.func_177230_c() != Blocks.field_150364_r && iBlockState.func_177230_c() != Blocks.field_150395_bd && iBlockState.func_177230_c() != Blocks.field_150345_g) continue;
                arrayList.add(new Vec3((double)blockPos2.func_177958_n() + 0.5, (double)blockPos2.func_177956_o(), (double)blockPos2.func_177952_p() + 0.5));
            }
        }
        arrayList.sort(Comparator.comparingDouble(this::lambda$closestLog$0));
        if (!arrayList.isEmpty()) {
            return new BlockPos(((Vec3)arrayList.get((int)0)).field_72450_a, ((Vec3)arrayList.get((int)0)).field_72448_b, ((Vec3)arrayList.get((int)0)).field_72449_c);
        }
        return null;
    }

    public float[] getRotations(BlockPos blockPos, EnumFacing enumFacing) {
        double d = (double)blockPos.func_177958_n() + 0.5 - this.mc.field_71439_g.field_70165_t + (double)enumFacing.func_82601_c() / 2.0;
        double d2 = (double)blockPos.func_177952_p() + 0.5 - this.mc.field_71439_g.field_70161_v + (double)enumFacing.func_82599_e() / 2.0;
        double d3 = this.mc.field_71439_g.field_70163_u + (double)this.mc.field_71439_g.func_70047_e() - ((double)blockPos.func_177956_o() + 0.5);
        double d4 = MathHelper.func_76133_a((double)(d * d + d2 * d2));
        float f = (float)(Math.atan2(d2, d) * 180.0 / Math.PI) - 90.0f;
        float f2 = (float)(Math.atan2(d3, d4) * 180.0 / Math.PI);
        if (f < 0.0f) {
            f += 360.0f;
        }
        return new float[]{f, f2};
    }

    private void swapSlot(int n) {
        if (n > 0 && n <= 8) {
            this.mc.field_71439_g.field_71071_by.field_70461_c = n - 1;
        }
    }

    public void silentUse(int n, int n2) {
        int n3 = this.mc.field_71439_g.field_71071_by.field_70461_c;
        if (n2 > 0 && n2 <= 8) {
            this.mc.field_71439_g.field_71071_by.field_70461_c = n2 - 1;
            this.mc.field_71442_b.func_78769_a((EntityPlayer)this.mc.field_71439_g, (World)this.mc.field_71441_e, this.mc.field_71439_g.func_70694_bm());
        }
        if (n > 0 && n <= 8) {
            this.mc.field_71439_g.field_71071_by.field_70461_c = n - 1;
        } else if (n == 0) {
            this.mc.field_71439_g.field_71071_by.field_70461_c = n3;
        }
    }

    private double lambda$closestLog$0(Vec3 vec3) {
        return this.mc.field_71439_g.func_70011_f(vec3.field_72450_a, vec3.field_72448_b, vec3.field_72449_c);
    }

    static enum ForagingState {
        TREE,
        BONEMEAL,
        RODSWAP,
        HARVEST,
        LOOKING;

    }

    static enum dir {
        NORTH,
        EAST,
        SOUTH,
        WEST;

    }
}

