/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 */
package xyz.Melody.Utils.pathfinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.util.BlockPos;
import xyz.Melody.Client;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.math.MathUtil;
import xyz.Melody.Utils.pathfinding.PathFinder;
import xyz.Melody.Utils.pathfinding.PathProcessor;
import xyz.Melody.Utils.timer.TimerUtil;

public class PathPerformer {
    private PathFinder pathFinder;
    private PathProcessor processor;
    private BlockPos endPos;
    private BlockPos transferPos;
    private BlockPos lastTransfer;
    private TimerUtil transferCalTimer = new TimerUtil();
    private PathFinder pathFinderTransfer;
    public static PathPerformer instance;
    public boolean isAlive = false;
    public boolean done = false;

    public static void init() {
        instance = new PathPerformer();
    }

    public void start(BlockPos blockPos) {
        this.transferPos = null;
        this.endPos = blockPos;
        this.pathFinder = new PathFinder(this.endPos);
        this.revive();
    }

    public BlockPos getCurrentGoal() {
        return this.pathFinder.getGoal();
    }

    @EventHandler
    public void onUpdate(EventRender2D eventRender2D) {
        if (!this.pathFinder.isDone()) {
            PathProcessor.lockControls();
            this.pathFinder.think();
            if (!this.pathFinder.isDone()) {
                if (this.pathFinder.isFailed()) {
                    if (this.pathFinder.failedPos != null) {
                        this.transferPos = this.pathFinder.failedPos;
                        this.pathFinder = new PathFinder(this.transferPos);
                    } else {
                        Helper.sendMessage("Could not find a path.");
                        this.suicide();
                    }
                }
                return;
            }
            this.pathFinder.formatPath();
            this.processor = this.pathFinder.getProcessor();
        }
        if (this.processor != null && !this.pathFinder.isPathStillValid(this.processor.getIndex())) {
            this.pathFinder = new PathFinder(this.pathFinder.getGoal());
            return;
        }
        this.processor.process();
        this.pathFinder.setPath(this.processor.getPath());
        if (this.transferPos != null) {
            boolean bl = false;
            if (MathUtil.distanceToPos(Client.mc.field_71439_g.func_180425_c(), this.transferPos) < 24.0f) {
                if (this != null) {
                    this.pathFinderTransfer = new PathFinder(this.lastTransfer, this.transferPos);
                    Client.instance.logger.info("Transfer: " + this.lastTransfer);
                    Client.instance.logger.info("Target: " + this.transferPos);
                    this.calculateTransferToEnd();
                    this.lastTransfer = null;
                    bl = true;
                }
                if (!this.pathFinderTransfer.isDone()) {
                    return;
                }
                if (this.pathFinderTransfer.countProcessedBlocks() > 0 && this.getCurrentGoal() != this.endPos && bl && this.transferCalTimer.hasReached(1000.0)) {
                    this.transferCalTimer.reset();
                    ArrayList arrayList = new ArrayList();
                    arrayList.addAll(this.processor.getPath());
                    arrayList.addAll(this.pathFinderTransfer.formatPath());
                    Client.instance.logger.info("Combinding Path...");
                    List list = arrayList.stream().distinct().collect(Collectors.toList());
                    arrayList.clear();
                    arrayList.addAll(list);
                    this.processor = new PathProcessor(arrayList);
                    this.pathFinder.setPath(this.processor.getPath());
                    this.lastTransfer = null;
                    this.pathFinderTransfer = null;
                }
            }
        }
        if (this.processor.isDone()) {
            if (this.transferPos != null) {
                this.pathFinder = new PathFinder(this.endPos);
                this.pathFinderTransfer = null;
                this.transferPos = null;
                return;
            }
            this.done = true;
            this.suicide();
        }
    }

    @EventHandler
    public void onRender(EventRender3D eventRender3D) {
        if (this.processor != null && this.processor.getPath() != null) {
            this.pathFinder.renderPath(this.processor.getPath(), eventRender3D.getPartialTicks());
        }
    }

    public BlockPos getTransferPos() {
        return this.transferPos;
    }

    public void revive() {
        if (this.pathFinder == null) {
            Helper.sendMessage("Set a goal first.");
            return;
        }
        this.done = false;
        EventBus.getInstance().register(instance);
        this.isAlive = true;
    }

    public void suicide() {
        EventBus.getInstance().unregister(instance);
        this.isAlive = false;
        PathProcessor.releaseControls();
    }
}

