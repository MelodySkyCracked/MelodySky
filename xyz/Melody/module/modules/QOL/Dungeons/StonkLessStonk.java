/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockChest
 *  net.minecraft.block.BlockLever
 *  net.minecraft.block.BlockSkull
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.Vec3
 *  net.minecraft.util.Vec3i
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$Action
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.QOL.Dungeons;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockSkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.BlockChangeEvent;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.math.RayMarchUtils;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class StonkLessStonk
extends Module {
    private Numbers range = new Numbers("Range", 5.0, 0.0, 6.0, 0.5);
    private Numbers scanRange = new Numbers("ScanRange", 6.0, 0.0, 15.0, 1.0);
    private Option sneak = new Option("Sneak", false);
    private Option remove = new Option("Remove", true);
    private HashMap blockList = new HashMap();
    private BlockPos selectedBlock = null;
    private BlockPos currentBlock = null;
    private BlockPos lastCheckedPosition = null;
    private HashSet usedBlocks = new HashSet();
    private String witherEssenceSkin = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRkYjRhZGZhOWJmNDhmZjVkNDE3MDdhZTM0ZWE3OGJkMjM3MTY1OWZjZDhjZDg5MzQ3NDlhZjRjY2U5YiJ9fX0=";
    private int essenceSkinHash = this.witherEssenceSkin.hashCode();
    private TimerUtil timer = new TimerUtil();
    private TimerUtil autoTimer = new TimerUtil();

    public StonkLessStonk() {
        super("StonkLessStonk", new String[]{"sls"}, ModuleType.Dungeons);
        this.addValues(this.range, this.scanRange, this.sneak, this.remove);
        this.setModInfo("Click Secrets Thru walls.");
    }

    @EventHandler
    private void tickDungeon(EventTick eventTick) {
        if (this.mc.field_71439_g == null) {
            return;
        }
        if (!Client.inDungeons || Client.instance.dungeonManager.inBoss()) {
            return;
        }
        BlockPos blockPos = this.mc.field_71439_g.func_180425_c();
        if (this.lastCheckedPosition == null || !this.lastCheckedPosition.equals((Object)blockPos)) {
            this.lastCheckedPosition = blockPos;
            this.loadSecrets();
        }
    }

    /*
     * Exception decompiling
     */
    private void loadSecrets() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl39 : ALOAD - null : trying to set 2 previously set to 0
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

    @EventHandler
    private void onDraw(EventRender3D eventRender3D) {
        if (this.currentBlock != null) {
            RenderUtil.drawSolidBlockESP(this.currentBlock, Colors.MAGENTA.c, eventRender3D.getPartialTicks());
        }
        if (this != null) {
            for (Map.Entry entry : this.blockList.entrySet()) {
                if (this.usedBlocks.contains(entry.getKey())) continue;
                if (this.selectedBlock == null) {
                    if (RayMarchUtils.isFacingBlock((BlockPos)entry.getKey(), ((Double)this.range.getValue()).floatValue())) {
                        this.selectedBlock = (BlockPos)entry.getKey();
                    }
                } else if (!RayMarchUtils.isFacingBlock(this.selectedBlock, ((Double)this.range.getValue()).floatValue())) {
                    this.selectedBlock = null;
                }
                Color color = new Color(192, 192, 192, 75);
                if (entry.getValue() instanceof BlockSkull) {
                    color = new Color(51, 0, 118, 75);
                }
                if (entry.getValue() instanceof BlockLever) {
                    color = new Color(51, 208, 118, 75);
                }
                if (entry.getValue() instanceof BlockChest && ((BlockChest)entry.getValue()).field_149956_a == 1) {
                    color = new Color(211, 0, 118, 75);
                }
                if (((BlockPos)entry.getKey()).equals((Object)this.selectedBlock)) {
                    color = new Color(65, 105, 255, 100);
                }
                RenderUtil.drawSolidBlockESP((BlockPos)entry.getKey(), color.getRGB(), eventRender3D.getPartialTicks());
            }
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent playerInteractEvent) {
        if (this.timer.hasReached(150.0) && this.isEnabled() && this.selectedBlock != null && !this.usedBlocks.contains(this.selectedBlock)) {
            if (this.mc.field_71476_x != null && this.selectedBlock.equals((Object)this.mc.field_71476_x.func_178782_a())) {
                return;
            }
            if (playerInteractEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || playerInteractEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                if (((Boolean)this.remove.getValue()).booleanValue()) {
                    this.usedBlocks.add(this.selectedBlock);
                }
                if (this.mc.field_71442_b.func_178890_a(this.mc.field_71439_g, this.mc.field_71441_e, this.mc.field_71439_g.field_71071_by.func_70448_g(), this.selectedBlock, EnumFacing.func_176733_a((double)this.mc.field_71439_g.field_70177_z), new Vec3(Math.random(), Math.random(), Math.random()))) {
                    this.mc.field_71439_g.func_71038_i();
                    this.timer.reset();
                }
            }
        }
    }

    @EventHandler
    public void onBlockChange(BlockChangeEvent blockChangeEvent) {
        if (this.mc.field_71441_e == null || this.mc.field_71439_g == null) {
            return;
        }
        if (blockChangeEvent.getPosition().func_177951_i((Vec3i)this.mc.field_71439_g.func_180425_c()) > (Double)this.range.getValue()) {
            return;
        }
        if (this.usedBlocks.contains(blockChangeEvent.getPosition())) {
            return;
        }
        StonkLessStonk stonkLessStonk = this;
        Block block = blockChangeEvent.getNewBlock().func_177230_c();
        if (blockChangeEvent.getPosition() == false) {
            return;
        }
        this.blockList.put(blockChangeEvent.getPosition(), blockChangeEvent.getNewBlock().func_177230_c());
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load load) {
        this.blockList.clear();
        this.usedBlocks.clear();
        this.selectedBlock = null;
        this.lastCheckedPosition = null;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.blockList.clear();
        this.usedBlocks.clear();
        this.selectedBlock = null;
        this.currentBlock = null;
        this.lastCheckedPosition = null;
        super.onDisable();
    }
}

