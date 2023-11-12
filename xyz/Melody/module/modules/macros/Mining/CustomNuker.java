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
 *  net.minecraft.util.Vec3
 *  net.minecraft.util.Vec3i
 *  net.minecraft.world.World
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.macros.Mining;

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
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.Player.EventPreUpdate;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Event.value.TextValue;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class CustomNuker
extends Module {
    private BlockPos blockPos;
    private BlockPos lastBlockPos = null;
    public TextValue blockName = new TextValue("BlockName", (Object)"stone");
    private Numbers protectRange = new Numbers("ProtectRange", 10.0, 5.0, 100.0, 0.5);
    private Numbers range = new Numbers("Range", 5.0, 1.0, 6.0, 0.1);
    private Option rot = new Option("Rotation", false);
    private Option protect = new Option("MacroProtect", true);
    private float currentDamage = 0.0f;
    private int blockHitDelay = 0;
    private int ticks = 0;

    public CustomNuker() {
        super("CustomNuker", new String[]{"cosn"}, ModuleType.Macros);
        this.addValues(this.blockName, this.protectRange, this.range, this.rot, this.protect);
        this.setModInfo("'.cusn help'");
        this.setEnabled(false);
    }

    @EventHandler
    private void tickPlayer(EventTick eventTick) {
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
            float f = this.getRotations(this.blockPos, this.getClosestEnum(this.blockPos))[0];
            float f2 = this.getRotations(this.blockPos, this.getClosestEnum(this.blockPos))[1];
            this.mc.field_71439_g.field_70177_z = this.smoothRotation(this.mc.field_71439_g.field_70177_z, f, 70.0f);
            eventPreUpdate.setYaw(this.mc.field_71439_g.field_70177_z);
            this.mc.field_71439_g.field_70125_A = this.smoothRotation(this.mc.field_71439_g.field_70125_A, f2, 70.0f);
            eventPreUpdate.setPitch(this.mc.field_71439_g.field_70125_A);
        }
    }

    @Override
    public void onEnable() {
        if (this.blockName.getValue() == null || ((String)this.blockName.getValue()).equals("")) {
            this.blockName.setValue("stone");
            Helper.sendMessage("[CustomNuker] An Error Occured, Set to Default Block(stone).");
        }
        this.setModInfo("'.cusn help' | cur: " + this.blockName);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.currentDamage = 0.0f;
        super.onDisable();
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

    private EnumFacing getClosestEnum(BlockPos blockPos) {
        EnumFacing enumFacing = EnumFacing.UP;
        float f = MathHelper.func_76142_g((float)this.getRotations(blockPos, EnumFacing.UP)[0]);
        if (f >= 45.0f && f <= 135.0f) {
            enumFacing = EnumFacing.EAST;
        } else if (f >= 135.0f && f <= 180.0f || f <= -135.0f && f >= -180.0f) {
            enumFacing = EnumFacing.SOUTH;
        } else if (f <= -45.0f && f >= -135.0f) {
            enumFacing = EnumFacing.WEST;
        } else if (f >= -45.0f && f <= 0.0f || f <= 45.0f && f >= 0.0f) {
            enumFacing = EnumFacing.NORTH;
        }
        if (MathHelper.func_76142_g((float)this.getRotations(blockPos, EnumFacing.UP)[1]) > 75.0f || MathHelper.func_76142_g((float)this.getRotations(blockPos, EnumFacing.UP)[1]) < -75.0f) {
            enumFacing = EnumFacing.UP;
        }
        return enumFacing;
    }

    private BlockPos getBlock() {
        int n = ((Double)this.range.getValue()).intValue();
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return null;
        }
        BlockPos blockPos = this.mc.field_71439_g.func_180425_c();
        blockPos = blockPos.func_177982_a(0, 1, 0);
        Vec3i vec3i = new Vec3i(n, n, n);
        ArrayList<Vec3> arrayList = new ArrayList<Vec3>();
        if (blockPos != null) {
            for (BlockPos blockPos2 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i))) {
                IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos2);
                if (this != iBlockState) continue;
                arrayList.add(new Vec3((double)blockPos2.func_177958_n() + 0.5, (double)blockPos2.func_177956_o(), (double)blockPos2.func_177952_p() + 0.5));
            }
        }
        arrayList.sort(Comparator.comparingDouble(this::lambda$getBlock$0));
        if (!arrayList.isEmpty()) {
            return new BlockPos(((Vec3)arrayList.get((int)0)).field_72450_a, ((Vec3)arrayList.get((int)0)).field_72448_b, ((Vec3)arrayList.get((int)0)).field_72449_c);
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

    private double lambda$getBlock$0(Vec3 vec3) {
        return this.mc.field_71439_g.func_70011_f(vec3.field_72450_a, vec3.field_72448_b, vec3.field_72449_c);
    }
}

