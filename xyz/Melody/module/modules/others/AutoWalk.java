/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.others;

import java.util.ArrayList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.misc.EventKey;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.Event.value.Option;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.pathfinding.PathPerformer;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class AutoWalk
extends Module {
    private KeyBinding[] CONTROLS;
    private boolean releasedControl = true;
    public int curIndex = 0;
    public ArrayList waypoints = new ArrayList();
    public Option lockDirection = new Option("LockDirection", false);
    public Mode direction = new Mode("Direction", Directions.values(), Directions.NORTH);
    private Option autoDisable = new Option("AutoDisable", true);
    private Option spawn = new Option("AutoWarpGarden", false);
    private Option sneak = new Option("Sneak", false);
    private boolean started = false;
    public boolean activeWorldChange = false;
    public BlockPos currentGoal = null;
    private TimerUtil timer = new TimerUtil();
    private stage curStage = stage.NONE;
    private static AutoWalk INSTANCE;

    public AutoWalk() {
        super("AutoWalk", ModuleType.Macros);
        this.addValues(this.lockDirection, this.direction, this.autoDisable, this.spawn, this.sneak);
        this.setModInfo(".aw help / press alt to add waypoints.");
    }

    public static AutoWalk getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (AutoWalk)Client.instance.getModuleManager().getModuleByClass(AutoWalk.class);
        }
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        this.CONTROLS = new KeyBinding[]{this.mc.field_71474_y.field_74351_w, this.mc.field_71474_y.field_74368_y, this.mc.field_71474_y.field_74366_z, this.mc.field_71474_y.field_74370_x, this.mc.field_71474_y.field_74314_A, this.mc.field_71474_y.field_74311_E};
        super.onEnable();
    }

    public void reset() {
        this.curStage = stage.NONE;
        this.activeWorldChange = false;
        if (!this.waypoints.isEmpty()) {
            this.currentGoal = (BlockPos)this.waypoints.get(0);
        }
        this.timer.reset();
    }

    @EventHandler
    private void onKey(EventKey eventKey) {
        BlockPos blockPos;
        BlockPos blockPos2;
        MovingObjectPosition movingObjectPosition;
        if (!this.started && eventKey.getKey() == 56 && (movingObjectPosition = this.mc.field_71476_x) != null && movingObjectPosition.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && !this.waypoints.contains(blockPos2 = new BlockPos((blockPos = movingObjectPosition.func_178782_a()).func_177958_n(), blockPos.func_177956_o() + 1, blockPos.func_177952_p()))) {
            this.waypoints.add(blockPos2);
            Helper.sendMessage(EnumChatFormatting.GRAY + "Added " + EnumChatFormatting.LIGHT_PURPLE + blockPos2 + EnumChatFormatting.GRAY + " as waypoint " + EnumChatFormatting.LIGHT_PURPLE + "#" + this.waypoints.size());
        }
    }

    @EventHandler
    private void tick(EventTick eventTick) {
        if (this.currentGoal == null && !this.waypoints.isEmpty()) {
            this.currentGoal = (BlockPos)this.waypoints.get(0);
        } else if (this.waypoints.isEmpty()) {
            this.currentGoal = null;
        }
        if (this.started && !this.waypoints.isEmpty()) {
            if (((Boolean)this.sneak.getValue()).booleanValue()) {
                KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74311_E.func_151463_i(), (boolean)true);
            }
            if (this.curIndex >= this.waypoints.size()) {
                this.curIndex = 0;
            }
            if (this.curIndex < 0) {
                this.curIndex = 0;
            }
            if (PathPerformer.instance.done && PathPerformer.instance.getCurrentGoal() != this.waypoints.get(this.waypoints.size() - 1) && this.curIndex + 1 <= this.waypoints.size()) {
                this.releaseKeys();
                ++this.curIndex;
                this.start((BlockPos)this.waypoints.get(this.curIndex));
                this.currentGoal = (BlockPos)this.waypoints.get(this.curIndex);
            } else if (PathPerformer.instance.done && this.curIndex >= this.waypoints.size() - 1) {
                if (((Boolean)this.spawn.getValue()).booleanValue()) {
                    if (this.curStage == stage.NONE) {
                        this.activeWorldChange = true;
                        PathPerformer.instance.suicide();
                        this.mc.field_71439_g.func_71165_d("/is");
                        this.curStage = stage.Island;
                        this.timer.reset();
                    } else if (this.curStage == stage.Island) {
                        if (Client.instance.sbArea.getCurrentArea() == Areas.Private_Island) {
                            if (this.timer.hasReached(1000.0)) {
                                this.mc.field_71439_g.func_71165_d("/warp garden");
                                this.curStage = stage.Garden;
                                this.timer.reset();
                            }
                        } else if (this.timer.hasReached(3000.0)) {
                            this.mc.field_71439_g.func_71165_d("/is");
                            this.timer.reset();
                        }
                    }
                    if (this.curStage == stage.Garden) {
                        if (Client.instance.sbArea.getCurrentArea() == Areas.Garden) {
                            if (this.timer.hasReached(1000.0)) {
                                this.curStage = stage.NONE;
                                this.curIndex = 0;
                                PathPerformer.instance.start((BlockPos)this.waypoints.get(this.curIndex));
                                this.currentGoal = (BlockPos)this.waypoints.get(this.curIndex);
                                KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74311_E.func_151463_i(), (boolean)true);
                                new Thread(this::lambda$tick$0).start();
                                this.timer.reset();
                            }
                        } else if (this.timer.hasReached(3000.0)) {
                            this.mc.field_71439_g.func_71165_d("/warp garden");
                            this.timer.reset();
                        }
                    }
                } else {
                    this.curIndex = 0;
                    this.start((BlockPos)this.waypoints.get(this.curIndex));
                    this.currentGoal = (BlockPos)this.waypoints.get(this.curIndex);
                    this.releaseKeys();
                }
            }
        }
    }

    @Override
    public void onDisable() {
        if (PathPerformer.instance == null) {
            return;
        }
        PathPerformer.instance.suicide();
        this.releaseKeys();
        super.onDisable();
    }

    @EventHandler
    private void tick3D(EventRender3D eventRender3D) {
        if (PathPerformer.instance.getTransferPos() != null) {
            BlockPos blockPos = PathPerformer.instance.getTransferPos();
            Object object = EnumChatFormatting.LIGHT_PURPLE + "# " + EnumChatFormatting.DARK_AQUA + "\u00a7lTransfer ";
            RenderUtil.renderTag(blockPos.func_177981_b(2), (String)object);
        }
        for (Object object : this.waypoints) {
            int n = this.waypoints.indexOf(object) + 1;
            String string = EnumChatFormatting.LIGHT_PURPLE + "#" + n + EnumChatFormatting.WHITE + ": " + object.func_177958_n() + " " + object.func_177956_o() + " " + object.func_177952_p();
            RenderUtil.renderTag(object, string);
            RenderUtil.drawSolidBlockESP(object, object == this.currentGoal ? Colors.MAGENTA.c : Colors.BLUE.c, eventRender3D.getPartialTicks());
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load load) {
        if (!this.started || this.activeWorldChange) {
            return;
        }
        Helper.sendMessage(EnumChatFormatting.YELLOW + "[MacroProtection]" + EnumChatFormatting.GRAY + " Stopped " + EnumChatFormatting.LIGHT_PURPLE + this.getName() + EnumChatFormatting.GRAY + " due to World Change.");
        this.stop();
    }

    private void releaseKeys() {
        if (this.releasedControl) {
            return;
        }
        for (KeyBinding keyBinding : this.CONTROLS) {
            KeyBinding.func_74510_a((int)keyBinding.func_151463_i(), (boolean)false);
        }
        this.releasedControl = true;
    }

    public void start(BlockPos blockPos) {
        PathPerformer.instance.start(blockPos);
        this.started = true;
        this.releasedControl = false;
    }

    public void stop() {
        PathPerformer.instance.suicide();
        this.started = false;
    }

    private void lambda$tick$0() {
        try {
            Thread.sleep(2000L);
            this.activeWorldChange = false;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    static enum Directions {
        NORTH,
        SOUTH,
        EAST,
        WEST;

    }

    static enum stage {
        Garden,
        Island,
        NONE;

    }
}

