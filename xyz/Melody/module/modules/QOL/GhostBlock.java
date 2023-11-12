/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.play.server.S08PacketPlayerPosLook
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.QOL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.BlockChangeEvent;
import xyz.Melody.Event.events.world.EventPacketRecieve;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Event.value.TextValue;
import xyz.Melody.System.Managers.Skyblock.Dungeons.DungeonFloors;
import xyz.Melody.Utils.math.MathUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class GhostBlock
extends Module {
    private final String skull = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRkYjRhZGZhOWJmNDhmZjVkNDE3MDdhZTM0ZWE3OGJkMjM3MTY1OWZjZDhjZDg5MzQ3NDlhZjRjY2U5YiJ9fX0=";
    private TextValue item = new TextValue("Item Name", (Object)"pickaxe");
    public Numbers range = new Numbers("Range", 10.0, 1.0, 100.0, 1.0);
    public Numbers delay = new Numbers("Delay", 40.0, 10.0, 100.0, 10.0);
    public Option F7PGB = new Option("F7 PreGB", true);
    public Option itemCheck = new Option("ItemCheck", true);
    private TimerUtil timer = new TimerUtil();
    private static ArrayList ghostBlocks = new ArrayList();
    private static HashMap eventQueue = new HashMap();
    private boolean hasSent;
    private static final int[][] cords = new int[][]{{275, 220, 231}, {275, 220, 232}, {299, 168, 243}, {299, 168, 244}, {299, 168, 246}, {299, 168, 247}, {299, 168, 247}, {300, 168, 247}, {300, 168, 246}, {300, 168, 244}, {300, 168, 243}, {298, 168, 247}, {298, 168, 246}, {298, 168, 244}, {298, 168, 243}, {287, 167, 240}, {288, 167, 240}, {289, 167, 240}, {290, 167, 240}, {291, 167, 240}, {292, 167, 240}, {293, 167, 240}, {294, 167, 240}, {295, 167, 240}, {290, 167, 239}, {291, 167, 239}, {292, 167, 239}, {293, 167, 239}, {294, 167, 239}, {295, 167, 239}, {290, 166, 239}, {291, 166, 239}, {292, 166, 239}, {293, 166, 239}, {294, 166, 239}, {295, 166, 239}, {290, 166, 240}, {291, 166, 240}, {292, 166, 240}, {293, 166, 240}, {294, 166, 240}, {295, 166, 240}};

    public GhostBlock() {
        super("GhostBlock", new String[]{"gb"}, ModuleType.QOL);
        this.setModInfo("Set Block to Air.");
        this.addValues(this.item, this.range, this.delay, this.F7PGB, this.itemCheck);
    }

    @Override
    public void onEnable() {
        this.doi();
        super.onEnable();
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (this.mc.field_71462_r != null || this.mc.field_71441_e == null) {
            return;
        }
        if (((Boolean)this.F7PGB.getValue()).booleanValue() && Client.instance.dungeonManager.inBoss() && (Client.instance.dungeonManager.isIn(DungeonFloors.F7) || Client.instance.dungeonManager.isIn(DungeonFloors.M7))) {
            for (Object object : cords) {
                this.mc.field_71441_e.func_175698_g(new BlockPos(object[0], object[1], object[2]));
            }
        }
        if (this.mc.field_71474_y.field_74313_G.func_151470_d() && (!((Boolean)this.itemCheck.getValue()).booleanValue() || this.mc.field_71439_g.func_70694_bm() != null && this.mc.field_71439_g.func_70694_bm().func_82833_r().toLowerCase().contains(((String)this.item.getValue()).toLowerCase()))) {
            this.doi();
            return;
        }
        if (this.mc.field_71474_y.field_74313_G.func_151470_d() && this.mc.field_71439_g.func_70694_bm() == null && ((Boolean)this.itemCheck.getValue()).booleanValue()) {
            Object object;
            Vec3 vec3 = this.mc.field_71439_g.func_174824_e(0.0f);
            Vec3 vec32 = this.mc.field_71439_g.func_70676_i(0.0f);
            Vec3 vec33 = vec3.func_72441_c(vec32.field_72450_a * (Double)this.range.getValue(), vec32.field_72448_b * (Double)this.range.getValue(), vec32.field_72449_c * (Double)this.range.getValue());
            object = this.mc.field_71441_e.func_147447_a(vec3, vec33, true, false, true).func_178782_a();
            for (BlockPos blockPos : ghostBlocks) {
                if (!(MathUtil.distanceToPos(blockPos, (BlockPos)object) <= 1.4f)) continue;
                ghostBlocks.remove(blockPos);
                break;
            }
        }
    }

    private void doi() {
        if (this.mc.field_71462_r != null || this.mc.field_71441_e == null) {
            return;
        }
        this.hasSent = true;
        eventQueue.entrySet().removeIf(this::lambda$doi$0);
        this.hasSent = false;
        if (this.timer.hasReached((Double)this.delay.getValue())) {
            this.timer.reset();
            Vec3 vec3 = this.mc.field_71439_g.func_174824_e(0.0f);
            Vec3 vec32 = this.mc.field_71439_g.func_70676_i(0.0f);
            Vec3 vec33 = vec3.func_72441_c(vec32.field_72450_a * (Double)this.range.getValue(), vec32.field_72448_b * (Double)this.range.getValue(), vec32.field_72449_c * (Double)this.range.getValue());
            BlockPos blockPos = this.mc.field_71441_e.func_147447_a(vec3, vec33, true, false, true).func_178782_a();
            if (this == blockPos) {
                return;
            }
            this.mc.field_71441_e.func_175698_g(blockPos);
            ghostBlocks.add(blockPos);
        }
    }

    @EventHandler
    private void handlePackets(EventPacketRecieve eventPacketRecieve) {
        if (eventPacketRecieve.getPacket() instanceof S08PacketPlayerPosLook) {
            eventQueue.clear();
        }
    }

    @EventHandler
    private void blockUpdates(BlockChangeEvent blockChangeEvent) {
        if (blockChangeEvent.getNewBlock() != null && ghostBlocks.contains(blockChangeEvent.getPosition()) && !this.hasSent && blockChangeEvent.getNewBlock().func_177230_c() != Blocks.field_150350_a) {
            blockChangeEvent.setCancelled(true);
            eventQueue.put(System.currentTimeMillis(), blockChangeEvent);
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load load) {
        eventQueue.clear();
        ghostBlocks.clear();
    }

    @Override
    public void onDisable() {
        this.setEnabled(true);
        super.onDisable();
    }

    private boolean lambda$doi$0(Map.Entry entry) {
        if (System.currentTimeMillis() - (Long)entry.getKey() > 250L) {
            this.mc.field_71441_e.func_175656_a(((BlockChangeEvent)entry.getValue()).getPosition(), ((BlockChangeEvent)entry.getValue()).getNewBlock());
            ghostBlocks.remove(((BlockChangeEvent)entry.getValue()).getPosition());
            return true;
        }
        return false;
    }
}

