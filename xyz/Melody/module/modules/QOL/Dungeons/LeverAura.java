/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockLever
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.Vec3
 *  net.minecraft.util.Vec3i
 */
package xyz.Melody.module.modules.QOL.Dungeons;

import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.block.BlockLever;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.math.RotationUtil;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class LeverAura
extends Module {
    public static BlockPos blockPos;
    private TimerUtil timer = new TimerUtil();
    private Thread calculationThread;
    private Option dungeon = new Option("DungeonOnly", true);
    private Option bossEntry = new Option("BossOnly", true);
    private Option clickedCheck = new Option("ClickedCheck", false);
    private Option poweredCheck = new Option("PoweredCheck", true);
    private Numbers delay = new Numbers("Delay", 50.0, 10.0, 200.0, 1.0);
    private Numbers range = new Numbers("Range", 4.0, 0.0, 4.5, 0.1);
    public static ArrayList allLevers;
    public static ArrayList clicked;

    public LeverAura() {
        super("LeverAura", new String[]{"la"}, ModuleType.Dungeons);
        this.addValues(this.dungeon, this.bossEntry, this.clickedCheck, this.poweredCheck, this.range, this.delay);
        this.setModInfo("Auto Pull Levers Around You.");
    }

    @EventHandler
    private void destoryBlock(EventTick eventTick) {
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return;
        }
        if (((Boolean)this.dungeon.getValue()).booleanValue() && !Client.inDungeons) {
            return;
        }
        if (((Boolean)this.bossEntry.getValue()).booleanValue() && !Client.instance.dungeonManager.inBoss()) {
            return;
        }
        if (this.calculationThread == null || !this.calculationThread.isAlive()) {
            this.calculationThread = new Thread(this::lambda$destoryBlock$0, "LeverAura");
            this.calculationThread.start();
        }
        if (blockPos != null && this.timer.hasReached((Double)this.delay.getValue())) {
            clicked.add(blockPos);
            this.mc.field_71439_g.func_71038_i();
            this.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(blockPos, RotationUtil.getClosestEnum(blockPos).func_176745_a(), this.mc.field_71439_g.func_71045_bC(), (float)blockPos.func_177958_n(), (float)blockPos.func_177956_o(), (float)blockPos.func_177952_p()));
            blockPos = null;
            this.timer.reset();
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        blockPos = null;
        clicked.clear();
        allLevers.clear();
        super.onDisable();
    }

    @EventHandler
    public void onTick(EventRender3D eventRender3D) {
        for (BlockPos blockPos : allLevers) {
            RenderUtil.drawSolidBlockESP(blockPos, Colors.RED.c, eventRender3D.getPartialTicks());
        }
    }

    @EventHandler
    private void onWorldLoad(EventTick eventTick) {
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            blockPos = null;
            clicked.clear();
            allLevers.clear();
        }
    }

    private BlockPos getLever() {
        float f = ((Double)this.range.getValue()).floatValue();
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return null;
        }
        BlockPos blockPos = this.mc.field_71439_g.func_180425_c();
        blockPos = blockPos.func_177982_a(0, 1, 0);
        Vec3i vec3i = new Vec3i((double)f, (double)f, (double)f);
        ArrayList<Vec3> arrayList = new ArrayList<Vec3>();
        if (blockPos != null) {
            allLevers.clear();
            for (BlockPos blockPos2 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i))) {
                IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos2);
                if (!(iBlockState.func_177230_c() instanceof BlockLever)) continue;
                if (clicked.contains(blockPos2) && ((Boolean)this.clickedCheck.getValue()).booleanValue()) continue;
                BlockLever blockLever = (BlockLever)iBlockState.func_177230_c();
                if (Boolean.valueOf((Boolean)iBlockState.func_177229_b((IProperty)BlockLever.field_176359_b)).booleanValue() && ((Boolean)this.poweredCheck.getValue()).booleanValue()) continue;
                allLevers.add(blockPos2);
                arrayList.add(new Vec3((double)blockPos2.func_177958_n() + 0.5, (double)blockPos2.func_177956_o(), (double)blockPos2.func_177952_p() + 0.5));
            }
        }
        arrayList.sort(Comparator.comparingDouble(this::lambda$getLever$1));
        if (!arrayList.isEmpty()) {
            return new BlockPos((Vec3)arrayList.get(0));
        }
        return null;
    }

    private double lambda$getLever$1(Vec3 vec3) {
        return this.mc.field_71439_g.func_70011_f(vec3.field_72450_a, vec3.field_72448_b, vec3.field_72449_c);
    }

    private void lambda$destoryBlock$0() {
        while (this.isEnabled()) {
            try {
                Thread.sleep(((Double)this.delay.getValue()).intValue());
                if (blockPos != null) continue;
                blockPos = this.getLever();
                this.timer.reset();
                return;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    static {
        allLevers = new ArrayList();
        clicked = new ArrayList();
    }
}

