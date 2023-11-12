/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.Vec3i
 */
package xyz.Melody.Utils.pathfinding.Lib;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;

public class PathPos
extends BlockPos {
    private final boolean jumping;

    public PathPos(BlockPos blockPos) {
        this(blockPos, false);
    }

    public PathPos(BlockPos blockPos, boolean bl) {
        super((Vec3i)blockPos);
        this.jumping = bl;
    }

    public boolean isJumping() {
        return this.jumping;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PathPos)) {
            return false;
        }
        PathPos pathPos = (PathPos)((Object)object);
        return this.func_177958_n() == pathPos.func_177958_n() && this.func_177956_o() == pathPos.func_177956_o() && this.func_177952_p() == pathPos.func_177952_p() && this.isJumping() == pathPos.isJumping();
    }

    public int hashCode() {
        return super.hashCode() * 2 + (this.isJumping() ? 1 : 0);
    }

    public int compareTo(Object object) {
        return super.compareTo((Vec3i)object);
    }
}

