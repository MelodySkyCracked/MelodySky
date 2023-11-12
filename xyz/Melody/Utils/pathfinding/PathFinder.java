/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockSoulSand
 *  net.minecraft.client.Minecraft
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.Vec3i
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.Utils.pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import org.lwjgl.opengl.GL11;
import xyz.Melody.Utils.Vec3d;
import xyz.Melody.Utils.game.BlockUtil;
import xyz.Melody.Utils.math.MathUtil;
import xyz.Melody.Utils.pathfinding.Lib.PathPos;
import xyz.Melody.Utils.pathfinding.Lib.PathQueue;
import xyz.Melody.Utils.pathfinding.PathProcessor;
import xyz.Melody.Utils.render.RenderUtil;

public class PathFinder {
    private Minecraft mc = Minecraft.func_71410_x();
    private PathPos start;
    protected PathPos current;
    private BlockPos goal;
    public BlockPos failedPos;
    private HashMap costMap = new HashMap();
    protected HashMap prevPosMap = new HashMap();
    private PathQueue queue = new PathQueue();
    protected int thinkSpeed = 1024;
    protected int thinkTime = 10;
    private int iterations;
    protected boolean done;
    protected boolean failed;
    private ArrayList path = new ArrayList();

    public PathFinder(BlockPos blockPos) {
        this.start = this.mc.field_71439_g.field_70122_E ? new PathPos(MathUtil.ofFloored(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u + 0.5, this.mc.field_71439_g.field_70161_v)) : new PathPos(MathUtil.ofFloored(this.mc.field_71439_g.func_180425_c()));
        this.goal = blockPos;
        this.costMap.put(this.start, Float.valueOf(0.0f));
        this.queue.add(this.start, this.getHeuristic(this.start));
        this.done = false;
    }

    public PathFinder(BlockPos blockPos, BlockPos blockPos2) {
        this.start = this.mc.field_71439_g.field_70122_E ? new PathPos(MathUtil.ofFloored(blockPos.func_177958_n(), (double)blockPos.func_177956_o() + 0.5, blockPos.func_177952_p())) : new PathPos(MathUtil.ofFloored(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p()));
        this.goal = blockPos2;
        this.costMap.put(this.start, Float.valueOf(0.0f));
        this.queue.add(this.start, this.getHeuristic(this.start));
        this.done = false;
    }

    public PathFinder(PathFinder pathFinder) {
        this(pathFinder.goal);
        this.thinkSpeed = pathFinder.thinkSpeed;
        this.thinkTime = pathFinder.thinkTime;
    }

    public void think() {
        int n;
        if (this.done) {
            throw new IllegalStateException("Path was already found!");
        }
        for (n = 0; n < this.thinkSpeed && this == false; ++n) {
            this.current = this.queue.poll();
            if (this.checkDone()) {
                return;
            }
            for (PathPos pathPos : this.getNeighbors(this.current)) {
                float f = ((Float)this.costMap.get((Object)this.current)).floatValue() + this.getCost(this.current, pathPos);
                if (this.costMap.containsKey((Object)pathPos) && ((Float)this.costMap.get((Object)pathPos)).floatValue() <= f) continue;
                this.costMap.put(pathPos, Float.valueOf(f));
                this.prevPosMap.put(pathPos, this.current);
                this.queue.add(pathPos, f + this.getHeuristic(pathPos));
            }
        }
        if (this.failed && !this.prevPosMap.isEmpty()) {
            ArrayList arrayList = this.formatPath();
            this.failedPos = (BlockPos)arrayList.get(this.path.size() - 1);
            this.path.clear();
        }
        this.iterations += n;
    }

    protected boolean checkDone() {
        this.done = this.goal.equals((Object)this.current);
        return this.done;
    }

    /*
     * Exception decompiling
     */
    private ArrayList getNeighbors(PathPos var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl221 : ALOAD_2 - null : trying to set 30 previously set to 31
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

    protected boolean isMineable(BlockPos blockPos) {
        return false;
    }

    private boolean canFlyAt(BlockPos blockPos) {
        return false;
    }

    private float getCost(BlockPos blockPos, BlockPos blockPos2) {
        float[] fArray = new float[]{0.5f, 0.5f};
        BlockPos[] blockPosArray = new BlockPos[]{blockPos, blockPos2};
        for (int i = 0; i < blockPosArray.length; ++i) {
            BlockPos blockPos3 = blockPosArray[i];
            Block block = BlockUtil.getBlock(blockPos3);
            if (block == Blocks.field_150353_l) {
                int n = i;
                fArray[n] = fArray[n] * 4.5395155f;
            }
            if (!this.canFlyAt(blockPos3) && BlockUtil.getBlock(blockPos3.func_177977_b()) instanceof BlockSoulSand) {
                int n = i;
                fArray[n] = fArray[n] * 2.5f;
            }
            if (this.isMineable(blockPos3)) {
                int n = i;
                fArray[n] = fArray[n] * 2.0f;
            }
            if (!this.isMineable(blockPos3.func_177984_a())) continue;
            int n = i;
            fArray[n] = fArray[n] * 2.0f;
        }
        float f = fArray[0] + fArray[1];
        if (blockPos.func_177958_n() != blockPos2.func_177958_n() && blockPos.func_177952_p() != blockPos2.func_177952_p()) {
            f *= 1.4142135f;
        }
        return f;
    }

    private float getHeuristic(BlockPos blockPos) {
        float f = Math.abs(blockPos.func_177958_n() - this.goal.func_177958_n());
        float f2 = Math.abs(blockPos.func_177956_o() - this.goal.func_177956_o());
        float f3 = Math.abs(blockPos.func_177952_p() - this.goal.func_177952_p());
        return 1.001f * (f + f2 + f3 - 0.58578646f * Math.min(f, f3));
    }

    public void setPath(ArrayList arrayList) {
        this.path = arrayList;
    }

    public PathPos getCurrentPos() {
        return this.current;
    }

    public BlockPos getGoal() {
        return this.goal;
    }

    public int countProcessedBlocks() {
        return this.prevPosMap.size();
    }

    public int getQueueSize() {
        return this.queue.size();
    }

    public float getCost(BlockPos blockPos) {
        return ((Float)this.costMap.get(blockPos)).floatValue();
    }

    public boolean isDone() {
        return this.done;
    }

    public boolean isFailed() {
        return this.failed;
    }

    /*
     * Exception decompiling
     */
    public ArrayList formatPath() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl35 : ALOAD_2 - null : trying to set 1 previously set to 0
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

    public void renderPath(ArrayList arrayList, float f) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2884);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glPushMatrix();
        ArrayList<Vec3d> arrayList2 = new ArrayList<Vec3d>();
        for (PathPos pathPos : arrayList) {
            arrayList2.add(Vec3d.ofCenter((Vec3i)pathPos));
        }
        RenderUtil.drawLines(arrayList2, 2.0f, f);
        GL11.glPopMatrix();
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
        GL11.glDepthMask((boolean)true);
    }

    /*
     * Exception decompiling
     */
    public boolean isPathStillValid(int var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl38 : ICONST_1 - null : trying to set 2 previously set to 3
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

    public PathProcessor getProcessor() {
        return new PathProcessor(this.path);
    }

    public void setThinkSpeed(int n) {
        this.thinkSpeed = n;
    }

    public void setThinkTime(int n) {
        this.thinkTime = n;
    }

    public List getPath() {
        return Collections.unmodifiableList(this.path);
    }
}

