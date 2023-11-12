/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3i
 */
package xyz.Melody.Utils.pathfinding;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;
import xyz.Melody.Client;
import xyz.Melody.Utils.Vec3d;
import xyz.Melody.Utils.math.MathUtil;
import xyz.Melody.Utils.math.Rotation;
import xyz.Melody.Utils.math.RotationUtil;
import xyz.Melody.Utils.pathfinding.Lib.PathPos;
import xyz.Melody.module.modules.others.AutoWalk;

public class PathProcessor {
    private static final Minecraft mc = Minecraft.func_71410_x();
    private static final KeyBinding[] CONTROLS = new KeyBinding[]{PathProcessor.mc.field_71474_y.field_74351_w, PathProcessor.mc.field_71474_y.field_74368_y, PathProcessor.mc.field_71474_y.field_74366_z, PathProcessor.mc.field_71474_y.field_74370_x, PathProcessor.mc.field_71474_y.field_74314_A, PathProcessor.mc.field_71474_y.field_74311_E};
    protected ArrayList path;
    protected int index;
    protected boolean done;
    protected int ticksOffPath;

    public PathProcessor(ArrayList arrayList) {
        if (arrayList.isEmpty()) {
            Client.instance.logger.error("There is no path!");
        } else {
            this.path = arrayList;
        }
    }

    public void process() {
        PathPos pathPos;
        BlockPos blockPos = PathProcessor.mc.field_71439_g.field_70122_E ? MathUtil.ofFloored(PathProcessor.mc.field_71439_g.field_70165_t, PathProcessor.mc.field_71439_g.field_70163_u + 0.5, PathProcessor.mc.field_71439_g.field_70161_v) : MathUtil.ofFloored(PathProcessor.mc.field_71439_g.func_180425_c());
        try {
            pathPos = (PathPos)((Object)this.path.get(this.index));
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            this.done = true;
            return;
        }
        int n = this.path.indexOf(blockPos);
        this.ticksOffPath = n == -1 ? ++this.ticksOffPath : 0;
        if (blockPos.func_177958_n() == pathPos.func_177958_n() && Math.abs(blockPos.func_177956_o() - pathPos.func_177956_o()) < 1 && blockPos.func_177952_p() == pathPos.func_177952_p()) {
            ++this.index;
            if (this.index >= this.path.size()) {
                this.done = true;
            }
            return;
        }
        if (n > this.index) {
            this.index = n + 1;
            if (this.index >= this.path.size()) {
                this.done = true;
            }
            return;
        }
        PathProcessor.lockControls();
        PathProcessor.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
        this.facePosition(pathPos);
        AutoWalk autoWalk = (AutoWalk)Client.instance.getModuleManager().getModuleByClass(AutoWalk.class);
        if (MathHelper.func_76142_g((float)Math.abs(RotationUtil.getHorizontalAngleToLookVec(Vec3d.ofBottomCenter((Vec3i)pathPos)))) > 90.0f && !((Boolean)autoWalk.lockDirection.getValue()).booleanValue()) {
            return;
        }
        if (blockPos.func_177958_n() != pathPos.func_177958_n() || blockPos.func_177952_p() != pathPos.func_177952_p()) {
            if (((Boolean)autoWalk.lockDirection.getValue()).booleanValue()) {
                PathProcessor.mc.field_71439_g.field_70177_z = RotationUtil.fixRadius(PathProcessor.mc.field_71439_g.field_70177_z);
                ArrayList<KeyBinding> arrayList = new ArrayList<KeyBinding>();
                String string = ((Enum)autoWalk.direction.getValue()).name().toLowerCase();
                float f = RotationUtil.fixRadius(RotationUtil.vec3ToRotation(Vec3d.ofBottomCenter((Vec3i)pathPos)).getYaw());
                float f2 = 0.0f;
                switch (string) {
                    case "north": {
                        f2 = 180.0f;
                        break;
                    }
                    case "south": {
                        f2 = 0.0f;
                        break;
                    }
                    case "east": {
                        f2 = 270.0f;
                        break;
                    }
                    case "west": {
                        f2 = 90.0f;
                    }
                }
                float f3 = f2;
                float f4 = RotationUtil.fixRadius(f2 - 90.0f);
                float f5 = RotationUtil.fixRadius(f2 + 90.0f);
                float f6 = RotationUtil.fixRadius(f2 - 180.0f);
                if (Math.abs(f - f3) < 45.0f || string.equals("south") && Math.abs(f - f3) > 315.0f) {
                    arrayList.add(PathProcessor.mc.field_71474_y.field_74351_w);
                }
                if (Math.abs(f - f4) < 45.0f || string.equals("west") && Math.abs(f - f4) > 315.0f) {
                    arrayList.add(PathProcessor.mc.field_71474_y.field_74370_x);
                }
                if (Math.abs(f - f5) < 45.0f || string.equals("east") && Math.abs(f - f5) > 315.0f) {
                    arrayList.add(PathProcessor.mc.field_71474_y.field_74366_z);
                }
                if (Math.abs(f - f6) < 45.0f || string.equals("north") && Math.abs(f - f6) > 315.0f) {
                    arrayList.add(PathProcessor.mc.field_71474_y.field_74368_y);
                }
                for (KeyBinding keyBinding : arrayList) {
                    if (keyBinding == null) continue;
                    KeyBinding.func_74510_a((int)keyBinding.func_151463_i(), (boolean)true);
                }
            } else {
                KeyBinding.func_74510_a((int)PathProcessor.mc.field_71474_y.field_74351_w.func_151463_i(), (boolean)true);
            }
            if (this.index > 0 && ((PathPos)((Object)this.path.get(this.index - 1))).isJumping() || blockPos.func_177956_o() < pathPos.func_177956_o()) {
                KeyBinding.func_74510_a((int)PathProcessor.mc.field_71474_y.field_74314_A.func_151463_i(), (boolean)true);
            }
        } else if (blockPos.func_177956_o() != pathPos.func_177956_o()) {
            if (blockPos.func_177956_o() < pathPos.func_177956_o()) {
                if (this.index < this.path.size() - 1 && !pathPos.func_177984_a().equals(this.path.get(this.index + 1))) {
                    ++this.index;
                }
                KeyBinding.func_74510_a((int)PathProcessor.mc.field_71474_y.field_74314_A.func_151463_i(), (boolean)true);
                KeyBinding.func_74510_a((int)PathProcessor.mc.field_71474_y.field_74351_w.func_151463_i(), (boolean)true);
            } else {
                while (this.index < this.path.size() - 1 && ((PathPos)((Object)this.path.get(this.index))).func_177977_b().equals(this.path.get(this.index + 1))) {
                    ++this.index;
                }
                if (PathProcessor.mc.field_71439_g.field_70122_E) {
                    KeyBinding.func_74510_a((int)PathProcessor.mc.field_71474_y.field_74351_w.func_151463_i(), (boolean)true);
                }
            }
        }
    }

    public ArrayList getPath() {
        return this.path;
    }

    public void setPath(ArrayList arrayList) {
        this.path = arrayList;
    }

    public final int getIndex() {
        return this.index;
    }

    public final boolean isDone() {
        return this.done;
    }

    public final int getTicksOffPath() {
        return this.ticksOffPath;
    }

    protected final void facePosition(BlockPos blockPos) {
        AutoWalk autoWalk = (AutoWalk)Client.instance.getModuleManager().getModuleByClass(AutoWalk.class);
        if (((Boolean)autoWalk.lockDirection.getValue()).booleanValue()) {
            String string = ((Enum)autoWalk.direction.getValue()).name().toLowerCase();
            float f = 0.0f;
            switch (string) {
                case "north": {
                    f = 180.0f;
                    break;
                }
                case "south": {
                    f = 0.0f;
                    break;
                }
                case "east": {
                    f = 270.0f;
                    break;
                }
                case "west": {
                    f = 90.0f;
                }
            }
            PathProcessor.mc.field_71439_g.field_70177_z = RotationUtil.smoothRotation(PathProcessor.mc.field_71439_g.field_70177_z, f);
        } else {
            Rotation rotation = RotationUtil.vec3ToRotation(Vec3d.ofCenter((Vec3i)blockPos));
            PathProcessor.mc.field_71439_g.field_70177_z = RotationUtil.smoothRotation(PathProcessor.mc.field_71439_g.field_70177_z, rotation.getYaw());
        }
    }

    public static final void lockControls() {
        for (KeyBinding keyBinding : CONTROLS) {
            KeyBinding.func_74510_a((int)keyBinding.func_151463_i(), (boolean)false);
        }
    }

    public static final void releaseControls() {
        for (KeyBinding keyBinding : CONTROLS) {
            KeyBinding.func_74510_a((int)keyBinding.func_151463_i(), (boolean)false);
        }
    }
}

