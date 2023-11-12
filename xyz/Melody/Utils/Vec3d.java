/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.vecmath.Vector3f
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3
 *  net.minecraft.util.Vec3i
 */
package xyz.Melody.Utils;

import java.util.Random;
import javax.vecmath.Vector3f;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import xyz.Melody.Utils.math.MathUtil;
import xyz.Melody.Utils.math.Vec2f;

public class Vec3d {
    public static final Vec3d ZERO = new Vec3d(0.0, 0.0, 0.0);
    public final double x;
    public final double y;
    public final double z;

    public static Vec3d unpackRgb(int n) {
        double d = (double)(n >> 16 & 0xFF) / 255.0;
        double d2 = (double)(n >> 8 & 0xFF) / 255.0;
        double d3 = (double)(n & 0xFF) / 255.0;
        return new Vec3d(d, d2, d3);
    }

    public static Vec3d of(Vec3i vec3i) {
        return new Vec3d(vec3i.func_177958_n(), vec3i.func_177956_o(), vec3i.func_177952_p());
    }

    public static Vec3d of(Vec3 vec3) {
        return new Vec3d(vec3.field_72450_a, vec3.field_72448_b, vec3.field_72449_c);
    }

    public static Vec3d add(Vec3i vec3i, double d, double d2, double d3) {
        return new Vec3d((double)vec3i.func_177958_n() + d, (double)vec3i.func_177956_o() + d2, (double)vec3i.func_177952_p() + d3);
    }

    public static Vec3d add(Vec3 vec3, double d, double d2, double d3) {
        return new Vec3d(vec3.field_72450_a + d, vec3.field_72448_b + d2, vec3.field_72449_c + d3);
    }

    public static Vec3d ofCenter(Vec3i vec3i) {
        return Vec3d.add(vec3i, 0.5, 0.5, 0.5);
    }

    public static Vec3d ofCenter(Vec3 vec3) {
        return Vec3d.add(vec3, 0.5, 0.5, 0.5);
    }

    public static Vec3d ofBottomCenter(Vec3i vec3i) {
        return Vec3d.add(vec3i, 0.5, 0.0, 0.5);
    }

    public static Vec3d ofCenter(Vec3i vec3i, double d) {
        return Vec3d.add(vec3i, 0.5, d, 0.5);
    }

    public Vec3d(BlockPos blockPos) {
        this.x = blockPos.func_177958_n();
        this.y = blockPos.func_177956_o();
        this.z = blockPos.func_177952_p();
    }

    public Vec3d(double d, double d2, double d3) {
        this.x = d;
        this.y = d2;
        this.z = d3;
    }

    public Vec3d(Vector3f vector3f) {
        this(vector3f.getX(), vector3f.getY(), vector3f.getZ());
    }

    public Vec3d relativize(Vec3d vec3d) {
        return new Vec3d(vec3d.x - this.x, vec3d.y - this.y, vec3d.z - this.z);
    }

    public Vec3d normalize() {
        double d = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        if (d < 1.0E-4) {
            return ZERO;
        }
        return new Vec3d(this.x / d, this.y / d, this.z / d);
    }

    public double dotProduct(Vec3d vec3d) {
        return this.x * vec3d.x + this.y * vec3d.y + this.z * vec3d.z;
    }

    public Vec3d crossProduct(Vec3d vec3d) {
        return new Vec3d(this.y * vec3d.z - this.z * vec3d.y, this.z * vec3d.x - this.x * vec3d.z, this.x * vec3d.y - this.y * vec3d.x);
    }

    public Vec3d subtract(Vec3d vec3d) {
        return this.subtract(vec3d.x, vec3d.y, vec3d.z);
    }

    public Vec3d subtract(double d, double d2, double d3) {
        return this.add(-d, -d2, -d3);
    }

    public Vec3d add(Vec3d vec3d) {
        return this.add(vec3d.x, vec3d.y, vec3d.z);
    }

    public Vec3d add(double d, double d2, double d3) {
        return new Vec3d(this.x + d, this.y + d2, this.z + d3);
    }

    public double distanceTo(Vec3d vec3d) {
        double d = vec3d.x - this.x;
        double d2 = vec3d.y - this.y;
        double d3 = vec3d.z - this.z;
        return Math.sqrt(d * d + d2 * d2 + d3 * d3);
    }

    public double squaredDistanceTo(Vec3d vec3d) {
        double d = vec3d.x - this.x;
        double d2 = vec3d.y - this.y;
        double d3 = vec3d.z - this.z;
        return d * d + d2 * d2 + d3 * d3;
    }

    public double squaredDistanceTo(double d, double d2, double d3) {
        double d4 = d - this.x;
        double d5 = d2 - this.y;
        double d6 = d3 - this.z;
        return d4 * d4 + d5 * d5 + d6 * d6;
    }

    public Vec3d multiply(double d) {
        return this.multiply(d, d, d);
    }

    public Vec3d negate() {
        return this.multiply(-1.0);
    }

    public Vec3d multiply(Vec3d vec3d) {
        return this.multiply(vec3d.x, vec3d.y, vec3d.z);
    }

    public Vec3d multiply(double d, double d2, double d3) {
        return new Vec3d(this.x * d, this.y * d2, this.z * d3);
    }

    public Vec3d addRandom(Random random, float f) {
        return this.add((random.nextFloat() - 0.5f) * f, (random.nextFloat() - 0.5f) * f, (random.nextFloat() - 0.5f) * f);
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double horizontalLength() {
        return Math.sqrt(this.x * this.x + this.z * this.z);
    }

    public double horizontalLengthSquared() {
        return this.x * this.x + this.z * this.z;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Vec3d)) {
            return false;
        }
        Vec3d vec3d = (Vec3d)object;
        return Double.compare(vec3d.x, this.x) == 0 && Double.compare(vec3d.y, this.y) == 0 && Double.compare(vec3d.z, this.z) == 0;
    }

    public int hashCode() {
        long l2 = Double.doubleToLongBits(this.x);
        int n = (int)(l2 ^ l2 >>> 32);
        l2 = Double.doubleToLongBits(this.y);
        n = 31 * n + (int)(l2 ^ l2 >>> 32);
        l2 = Double.doubleToLongBits(this.z);
        n = 31 * n + (int)(l2 ^ l2 >>> 32);
        return n;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z;
    }

    public Vec3d lerp(Vec3d vec3d, double d) {
        return new Vec3d(MathUtil.lerp(d, this.x, vec3d.x), MathUtil.lerp(d, this.y, vec3d.y), MathUtil.lerp(d, this.z, vec3d.z));
    }

    public Vec3d rotateX(float f) {
        float f2 = MathHelper.func_76134_b((float)f);
        float f3 = MathHelper.func_76126_a((float)f);
        double d = this.x;
        double d2 = this.y * (double)f2 + this.z * (double)f3;
        double d3 = this.z * (double)f2 - this.y * (double)f3;
        return new Vec3d(d, d2, d3);
    }

    public Vec3d rotateY(float f) {
        float f2 = MathHelper.func_76134_b((float)f);
        float f3 = MathHelper.func_76126_a((float)f);
        double d = this.x * (double)f2 + this.z * (double)f3;
        double d2 = this.y;
        double d3 = this.z * (double)f2 - this.x * (double)f3;
        return new Vec3d(d, d2, d3);
    }

    public Vec3d rotateZ(float f) {
        float f2 = MathHelper.func_76134_b((float)f);
        float f3 = MathHelper.func_76126_a((float)f);
        double d = this.x * (double)f2 + this.y * (double)f3;
        double d2 = this.y * (double)f2 - this.x * (double)f3;
        double d3 = this.z;
        return new Vec3d(d, d2, d3);
    }

    public static Vec3d fromPolar(Vec2f vec2f) {
        return Vec3d.fromPolar(vec2f.getX(), vec2f.getY());
    }

    public static Vec3d fromPolar(float f, float f2) {
        float f3 = MathHelper.func_76134_b((float)(-f2 * ((float)Math.PI / 180) - (float)Math.PI));
        float f4 = MathHelper.func_76126_a((float)(-f2 * ((float)Math.PI / 180) - (float)Math.PI));
        float f5 = -MathHelper.func_76134_b((float)(-f * ((float)Math.PI / 180)));
        float f6 = MathHelper.func_76126_a((float)(-f * ((float)Math.PI / 180)));
        return new Vec3d(f4 * f5, f6, f3 * f5);
    }

    public final double getX() {
        return this.x;
    }

    public final double getY() {
        return this.y;
    }

    public final double getZ() {
        return this.z;
    }

    public Vector3f toVector3f() {
        return new Vector3f((float)this.x, (float)this.y, (float)this.z);
    }

    public Vec3 toVec3() {
        return new Vec3(this.x, this.y, this.z);
    }
}

