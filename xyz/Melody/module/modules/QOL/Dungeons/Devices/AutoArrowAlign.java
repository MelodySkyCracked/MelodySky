/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.EnumDyeColor
 *  net.minecraft.item.Item
 *  net.minecraft.util.BlockPos
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.QOL.Dungeons.Devices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class AutoArrowAlign
extends Module {
    private final Set clickedItemFrames = new HashSet();
    private static final Map requiredClicksForEntity = new HashMap();
    private int ticks = 1;
    private boolean foundPattern;

    public AutoArrowAlign() {
        super("AutoArrowAlign", new String[]{"aaa"}, ModuleType.Dungeons);
        this.setModInfo("Auto Do A.A. When CrossHair Hovered.");
    }

    @EventHandler
    public void onTick(EventTick eventTick) {
        BlockPos blockPos;
        EntityItemFrame entityItemFrame;
        if (!Client.inDungeons || this.mc.field_71462_r != null || this.mc.field_71476_x == null) {
            return;
        }
        if (this.foundPattern && this.mc.field_71476_x.field_72308_g instanceof EntityItemFrame && (entityItemFrame = (EntityItemFrame)this.mc.field_71476_x.field_72308_g).func_82335_i() != null && entityItemFrame.func_82335_i().func_77973_b() == Items.field_151032_g && !this.clickedItemFrames.contains(blockPos = new BlockPos(entityItemFrame.field_70165_t, entityItemFrame.field_70163_u, entityItemFrame.field_70161_v)) && requiredClicksForEntity.containsKey(blockPos)) {
            int n = (Integer)requiredClicksForEntity.get(blockPos);
            int n2 = entityItemFrame.func_82333_j();
            if (n2 != n) {
                int n3 = n2 < n ? n - n2 : n - n2 + 8;
                for (int i = 0; i < n3; ++i) {
                    Client.rightClick();
                }
            }
            this.clickedItemFrames.add(blockPos);
        }
        if (this.ticks % 70 == 0) {
            this.calculatePattern();
            this.ticks = 0;
        }
        ++this.ticks;
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load load) {
        this.foundPattern = false;
        this.clickedItemFrames.clear();
        requiredClicksForEntity.clear();
    }

    private void calculatePattern() {
        Object object;
        requiredClicksForEntity.clear();
        HashMap<BlockPos, Entity> hashMap = new HashMap<BlockPos, Entity>();
        ArrayList<Object> arrayList = new ArrayList<Object>();
        ArrayList<BlockPos> arrayList2 = new ArrayList<BlockPos>();
        HashSet<BlockPos> hashSet = new HashSet<BlockPos>();
        for (Entity object2 : this.mc.field_71441_e.field_72996_f) {
            if (!(object2 instanceof EntityItemFrame) || (object = ((EntityItemFrame)object2).func_82335_i()) == null) continue;
            Item item = object.func_77973_b();
            if (item == Items.field_151032_g) {
                hashMap.put(new BlockPos(object2.field_70165_t, object2.field_70163_u, object2.field_70161_v), object2);
                continue;
            }
            if (item != Item.func_150898_a((Block)Blocks.field_150325_L)) continue;
            if (EnumDyeColor.func_176764_b((int)object.func_77952_i()) == EnumDyeColor.LIME) {
                arrayList2.add(new BlockPos(object2.field_70165_t, object2.field_70163_u, object2.field_70161_v));
                continue;
            }
            hashSet.add(new BlockPos(object2.field_70165_t, object2.field_70163_u, object2.field_70161_v));
        }
        if (hashMap.size() >= 9 && arrayList2.size() != 0) {
            for (BlockPos blockPos : arrayList2) {
                object = blockPos.func_177984_a();
                if (hashMap.containsKey(object)) {
                    arrayList.add(object);
                }
                if (hashMap.containsKey(object = blockPos.func_177977_b())) {
                    arrayList.add(object);
                }
                if (hashMap.containsKey(object = blockPos.func_177968_d())) {
                    arrayList.add(object);
                }
                if (!hashMap.containsKey(object = blockPos.func_177978_c())) continue;
                arrayList.add(object);
            }
            for (int i = 0; i < 200; ++i) {
                if (arrayList.size() == 0) {
                    if (!this.foundPattern) {
                        this.foundPattern = true;
                    }
                    return;
                }
                ArrayList arrayList3 = new ArrayList(arrayList);
                arrayList.clear();
                for (Item item : arrayList3) {
                    BlockPos blockPos = item.func_177984_a();
                    if (hashSet.contains(blockPos)) {
                        requiredClicksForEntity.put(item, 7);
                        continue;
                    }
                    blockPos = item.func_177977_b();
                    if (hashSet.contains(blockPos)) {
                        requiredClicksForEntity.put(item, 3);
                        continue;
                    }
                    blockPos = item.func_177968_d();
                    if (hashSet.contains(blockPos)) {
                        requiredClicksForEntity.put(item, 5);
                        continue;
                    }
                    blockPos = item.func_177978_c();
                    if (hashSet.contains(blockPos)) {
                        requiredClicksForEntity.put(item, 1);
                        continue;
                    }
                    if (requiredClicksForEntity.containsKey(item)) continue;
                    blockPos = item.func_177984_a();
                    if (hashMap.containsKey(blockPos) && !requiredClicksForEntity.containsKey(blockPos)) {
                        arrayList.add(blockPos);
                        requiredClicksForEntity.put(item, 7);
                        continue;
                    }
                    blockPos = item.func_177977_b();
                    if (hashMap.containsKey(blockPos) && !requiredClicksForEntity.containsKey(blockPos)) {
                        arrayList.add(blockPos);
                        requiredClicksForEntity.put(item, 3);
                        continue;
                    }
                    blockPos = item.func_177968_d();
                    if (hashMap.containsKey(blockPos) && !requiredClicksForEntity.containsKey(blockPos)) {
                        arrayList.add(blockPos);
                        requiredClicksForEntity.put(item, 5);
                        continue;
                    }
                    blockPos = item.func_177978_c();
                    if (!hashMap.containsKey(blockPos) || requiredClicksForEntity.containsKey(blockPos)) continue;
                    arrayList.add(blockPos);
                    requiredClicksForEntity.put(item, 1);
                }
            }
            this.foundPattern = false;
        }
    }
}

