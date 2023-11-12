/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.network.play.server.S2APacketParticles
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumParticleTypes
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  net.minecraft.util.Vec3i
 */
package xyz.Melody.module.modules.macros.Farming;

import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.BlockChangeEvent;
import xyz.Melody.Event.events.world.EventPacketRecieve;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class GlowingMushroomNuker
extends Module {
    private Numbers range = new Numbers("Range", 5.0, 1.0, 6.0, 0.1);
    private ArrayList mushrooms = new ArrayList();
    private BlockPos clothestMushroom;

    public GlowingMushroomNuker() {
        super("GlowingMushroomNuker", new String[]{"chest"}, ModuleType.Macros);
        this.addValues(this.range);
        this.setModInfo("Auto Mine Glowing Mushrooms.");
    }

    @EventHandler
    private void onRotation(EventTick eventTick) {
        this.updateClothest();
        if (this.clothestMushroom != null) {
            MovingObjectPosition movingObjectPosition = this.mc.field_71476_x;
            movingObjectPosition.field_72307_f = new Vec3((Vec3i)this.clothestMushroom);
            EnumFacing enumFacing = movingObjectPosition.field_178784_b;
            if (enumFacing != null && this.mc.field_71439_g != null) {
                this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.clothestMushroom, enumFacing));
            }
            this.mc.field_71439_g.func_71038_i();
            this.mushrooms.remove(this.clothestMushroom);
            this.clothestMushroom = null;
        }
    }

    @EventHandler
    private void onBlockDestory(BlockChangeEvent blockChangeEvent) {
        if (this.mushrooms.contains(blockChangeEvent.getPosition())) {
            this.mushrooms.remove(blockChangeEvent.getPosition());
        }
    }

    @EventHandler
    public void receivePacket(EventPacketRecieve eventPacketRecieve) {
        Vec3 vec3;
        BlockPos blockPos;
        S2APacketParticles s2APacketParticles;
        if (eventPacketRecieve.getPacket() instanceof S2APacketParticles && (s2APacketParticles = (S2APacketParticles)eventPacketRecieve.getPacket()).func_179749_a() == EnumParticleTypes.SPELL_MOB && (this.mc.field_71441_e.func_180495_p(blockPos = new BlockPos(vec3 = new Vec3(s2APacketParticles.func_149220_d(), s2APacketParticles.func_149226_e(), s2APacketParticles.func_149225_f()))).func_177230_c() == Blocks.field_150337_Q || this.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150338_P) && !this.mushrooms.contains(blockPos)) {
            this.mushrooms.add(blockPos);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.mushrooms.clear();
        super.onDisable();
    }

    @EventHandler
    public void onR3D(EventRender3D eventRender3D) {
        for (BlockPos blockPos : this.mushrooms) {
            RenderUtil.drawSolidBlockESP(blockPos, Colors.MAGENTA.c, eventRender3D.getPartialTicks());
        }
        if (this.clothestMushroom != null) {
            RenderUtil.drawSolidBlockESP(this.clothestMushroom, Colors.GREEN.c, eventRender3D.getPartialTicks());
        }
    }

    private void updateClothest() {
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null || this.mushrooms.isEmpty()) {
            return;
        }
        this.mushrooms.sort(Comparator.comparingDouble(this::lambda$updateClothest$0));
        BlockPos blockPos = (BlockPos)this.mushrooms.get(0);
        if (this.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p()) < (Double)this.range.getValue()) {
            this.clothestMushroom = blockPos;
            return;
        }
        this.clothestMushroom = null;
    }

    private double lambda$updateClothest$0(BlockPos blockPos) {
        return this.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p());
    }
}

