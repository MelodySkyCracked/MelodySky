/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3
 */
package xyz.Melody.Utils.math;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.Vec3d;
import xyz.Melody.Utils.math.Rotation;

public final class RotationUtil {
    private static Minecraft mc = Minecraft.func_71410_x();

    public static float pitch() {
        return Helper.mc.field_71439_g.field_70125_A;
    }

    public static void pitch(float f) {
        Helper.mc.field_71439_g.field_70125_A = f;
    }

    public static float yaw() {
        return Helper.mc.field_71439_g.field_70177_z;
    }

    public static void yaw(float f) {
        Helper.mc.field_71439_g.field_70177_z = f;
    }

    public static double isInFov(float f, float f2, double d, double d2, double d3) {
        Vec3 vec3 = new Vec3((double)f, (double)f2, 0.0);
        float[] fArray = RotationUtil.getAngleBetweenVecs(new Vec3(RotationUtil.mc.field_71439_g.field_70165_t, RotationUtil.mc.field_71439_g.field_70163_u, RotationUtil.mc.field_71439_g.field_70161_v), new Vec3(d, d2, d3));
        double d4 = MathHelper.func_76138_g((double)(vec3.field_72450_a - (double)fArray[0]));
        return Math.abs(d4) * 2.0;
    }

    public static float[] getAngleBetweenVecs(Vec3 vec3, Vec3 vec32) {
        double d = vec32.field_72450_a - vec3.field_72450_a;
        double d2 = vec32.field_72448_b - vec3.field_72448_b;
        double d3 = vec32.field_72449_c - vec3.field_72449_c;
        double d4 = Math.sqrt(d * d + d3 * d3);
        float f = (float)(Math.atan2(d3, d) * 180.0 / Math.PI) - 90.0f;
        float f2 = (float)(-(Math.atan2(d2, d4) * 180.0 / Math.PI));
        return new float[]{f, f2};
    }

    public static float[] getRotationFromPosition(double d, double d2, double d3) {
        double d4 = d - Minecraft.func_71410_x().field_71439_g.field_70165_t;
        double d5 = d2 - Minecraft.func_71410_x().field_71439_g.field_70161_v;
        double d6 = d3 - Minecraft.func_71410_x().field_71439_g.field_70163_u - 1.2;
        double d7 = MathHelper.func_76133_a((double)(d4 * d4 + d5 * d5));
        float f = (float)(Math.atan2(d5, d4) * 180.0 / Math.PI) - 90.0f;
        float f2 = (float)(-(Math.atan2(d6, d7) * 180.0 / Math.PI));
        return new float[]{f, f2};
    }

    public static float[] faceTarget(Entity entity, float f, float f2, boolean bl) {
        double d;
        double d2 = entity.field_70165_t - Helper.mc.field_71439_g.field_70165_t;
        double d3 = entity.field_70161_v - Helper.mc.field_71439_g.field_70161_v;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            d = entityLivingBase.field_70163_u + (double)entityLivingBase.func_70047_e() - (Helper.mc.field_71439_g.field_70163_u + (double)Helper.mc.field_71439_g.func_70047_e());
        } else {
            d = (entity.func_174813_aQ().field_72338_b + entity.func_174813_aQ().field_72337_e) / 2.0 - (Helper.mc.field_71439_g.field_70163_u + (double)Helper.mc.field_71439_g.func_70047_e());
        }
        double d4 = MathHelper.func_76133_a((double)(d2 * d2 + d3 * d3));
        float f3 = (float)(Math.atan2(d3, d2) * 180.0 / Math.PI) - 90.0f;
        float f4 = (float)(-Math.atan2(d - (entity instanceof EntityPlayer ? 0.25 : 0.0), d4) * 180.0 / Math.PI);
        float f5 = RotationUtil.changeRotation(Helper.mc.field_71439_g.field_70125_A, f4, f2);
        float f6 = RotationUtil.changeRotation(Helper.mc.field_71439_g.field_70177_z, f3, f);
        return new float[]{f6, f5};
    }

    public static float changeRotation(float f, float f2, float f3) {
        float f4 = MathHelper.func_76142_g((float)(f2 - f));
        if (f4 > f3) {
            f4 = f3;
        }
        if (f4 < -f3) {
            f4 = -f3;
        }
        return f + f4;
    }

    public static float[] getRotationToEntity(Entity entity) {
        double d = Helper.mc.field_71439_g.field_70165_t;
        double d2 = Helper.mc.field_71439_g.field_70163_u + (double)Helper.mc.field_71439_g.func_70047_e();
        double d3 = Helper.mc.field_71439_g.field_70161_v;
        double d4 = entity.field_70165_t;
        double d5 = entity.field_70163_u + (double)(entity.field_70131_O / 2.0f);
        double d6 = entity.field_70161_v;
        double d7 = d - d4;
        double d8 = d2 - d5;
        double d9 = d3 - d6;
        double d10 = Math.sqrt(Math.pow(d7, 2.0) + Math.pow(d9, 2.0));
        double d11 = Math.toDegrees(Math.atan2(d9, d7)) + 90.0;
        double d12 = Math.toDegrees(Math.atan2(d10, d8));
        return new float[]{(float)d11, (float)(90.0 - d12)};
    }

    public static float[] getRotations(Entity entity) {
        double d;
        if (entity == null) {
            return null;
        }
        double d2 = entity.field_70165_t - Helper.mc.field_71439_g.field_70165_t;
        double d3 = entity.field_70161_v - Helper.mc.field_71439_g.field_70161_v;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            d = entityLivingBase.field_70163_u + ((double)entityLivingBase.func_70047_e() - 0.4) - (Helper.mc.field_71439_g.field_70163_u + (double)Helper.mc.field_71439_g.func_70047_e());
        } else {
            d = (entity.func_174813_aQ().field_72338_b + entity.func_174813_aQ().field_72337_e) / 2.0 - (Helper.mc.field_71439_g.field_70163_u + (double)Helper.mc.field_71439_g.func_70047_e());
        }
        double d4 = MathHelper.func_76133_a((double)(d2 * d2 + d3 * d3));
        float f = (float)(Math.atan2(d3, d2) * 180.0 / Math.PI) - 90.0f;
        float f2 = (float)(-Math.atan2(d, d4) * 180.0 / Math.PI);
        return new float[]{f, f2};
    }

    public static float getDistanceBetweenAngles(float f, float f2) {
        float f3 = Math.abs(f - f2) % 360.0f;
        if (f3 > 180.0f) {
            f3 = 0.0f;
        }
        return f3;
    }

    public static int wrapAngleToDirection(float f, int n) {
        int n2 = (int)((double)(f + (float)(360 / (2 * n))) + 0.5) % 360;
        if (n2 < 0) {
            n2 += 360;
        }
        return n2 / (360 / n);
    }

    public static Rotation vec3ToRotation(Vec3d vec3d) {
        double d = vec3d.getX() - RotationUtil.mc.field_71439_g.field_70165_t;
        double d2 = vec3d.getY() - RotationUtil.mc.field_71439_g.field_70163_u - (double)RotationUtil.mc.field_71439_g.func_70047_e();
        double d3 = vec3d.getZ() - RotationUtil.mc.field_71439_g.field_70161_v;
        double d4 = Math.sqrt(d * d + d3 * d3);
        float f = (float)(-Math.atan2(d4, d2));
        float f2 = (float)Math.atan2(d3, d);
        f = (float)RotationUtil.wrapAngleTo180(((double)(f * 180.0f) / Math.PI + 90.0) * -1.0);
        f2 = (float)RotationUtil.wrapAngleTo180((double)(f2 * 180.0f) / Math.PI - 90.0);
        return new Rotation(f2, f);
    }

    private static double wrapAngleTo180(double d) {
        return d - Math.floor(d / 360.0 + 0.5) * 360.0;
    }

    public static float[] getPredictedRotations(EntityLivingBase entityLivingBase) {
        double d = entityLivingBase.field_70165_t + (entityLivingBase.field_70165_t - entityLivingBase.field_70142_S);
        double d2 = entityLivingBase.field_70161_v + (entityLivingBase.field_70161_v - entityLivingBase.field_70136_U);
        double d3 = entityLivingBase.field_70163_u + (double)(entityLivingBase.func_70047_e() / 2.0f);
        return RotationUtil.getRotationFromPosition(d, d2, d3);
    }

    public static float[] getRotations(BlockPos blockPos, EnumFacing enumFacing) {
        double d = (double)blockPos.func_177958_n() + 0.5 - RotationUtil.mc.field_71439_g.field_70165_t + (double)enumFacing.func_82601_c() / 2.0;
        double d2 = (double)blockPos.func_177952_p() + 0.5 - RotationUtil.mc.field_71439_g.field_70161_v + (double)enumFacing.func_82599_e() / 2.0;
        double d3 = RotationUtil.mc.field_71439_g.field_70163_u + (double)RotationUtil.mc.field_71439_g.func_70047_e() - ((double)blockPos.func_177956_o() + 0.5);
        double d4 = MathHelper.func_76133_a((double)(d * d + d2 * d2));
        float f = (float)(Math.atan2(d2, d) * 180.0 / Math.PI) - 90.0f;
        float f2 = (float)(Math.atan2(d3, d4) * 180.0 / Math.PI);
        if (f < 0.0f) {
            f += 360.0f;
        }
        return new float[]{f, f2};
    }

    public static Rotation getNeededChange(Rotation rotation) {
        return RotationUtil.getNeededChange(new Rotation(RotationUtil.mc.field_71439_g.field_70125_A, RotationUtil.mc.field_71439_g.field_70177_z), rotation);
    }

    public static Rotation getNeededChange(Rotation rotation, Rotation rotation2) {
        float f = MathHelper.func_76142_g((float)rotation2.getYaw()) - MathHelper.func_76142_g((float)rotation.getYaw());
        if (f <= -180.0f) {
            f += 360.0f;
        } else if (f > 180.0f) {
            f -= 360.0f;
        }
        return new Rotation(rotation2.getPitch() - rotation.getPitch(), f);
    }

    public static Rotation getRotation(Vec3 vec3, Vec3 vec32) {
        double d = vec32.field_72450_a - vec3.field_72450_a;
        double d2 = vec32.field_72448_b - vec3.field_72448_b;
        double d3 = vec32.field_72449_c - vec3.field_72449_c;
        double d4 = Math.sqrt(d * d + d3 * d3);
        float f = (float)(-Math.atan2(d4, d2));
        float f2 = (float)Math.atan2(d3, d);
        f = (float)MathHelper.func_76138_g((double)(((double)(f * 180.0f) / Math.PI + 90.0) * -1.0));
        f2 = (float)MathHelper.func_76138_g((double)((double)(f2 * 180.0f) / Math.PI - 90.0));
        return new Rotation(f, f2);
    }

    public static float[] getRotationsWithFacing(BlockPos blockPos) {
        EnumFacing enumFacing = RotationUtil.getClosestEnum(blockPos);
        double d = (double)blockPos.func_177958_n() + 0.5 - RotationUtil.mc.field_71439_g.field_70165_t + (double)enumFacing.func_82601_c() / 2.0;
        double d2 = (double)blockPos.func_177952_p() + 0.5 - RotationUtil.mc.field_71439_g.field_70161_v + (double)enumFacing.func_82599_e() / 2.0;
        double d3 = RotationUtil.mc.field_71439_g.field_70163_u + (double)RotationUtil.mc.field_71439_g.func_70047_e() - ((double)blockPos.func_177956_o() + 0.5);
        double d4 = MathHelper.func_76133_a((double)(d * d + d2 * d2));
        float f = (float)(Math.atan2(d2, d) * 180.0 / Math.PI) - 90.0f;
        float f2 = (float)(Math.atan2(d3, d4) * 180.0 / Math.PI);
        if (f < 0.0f) {
            f += 360.0f;
        }
        return new float[]{f, f2};
    }

    public static EnumFacing getClosestEnum(BlockPos blockPos) {
        EnumFacing enumFacing = EnumFacing.UP;
        float f = MathHelper.func_76142_g((float)RotationUtil.getRotations(blockPos, EnumFacing.UP)[0]);
        if (f >= 45.0f && f <= 135.0f) {
            enumFacing = EnumFacing.EAST;
        } else if (f >= 135.0f && f <= 180.0f || f <= -135.0f && f >= -180.0f) {
            enumFacing = EnumFacing.SOUTH;
        } else if (f <= -45.0f && f >= -135.0f) {
            enumFacing = EnumFacing.WEST;
        } else if (f >= -45.0f && f <= 0.0f || f <= 45.0f && f >= 0.0f) {
            enumFacing = EnumFacing.NORTH;
        }
        if (MathHelper.func_76142_g((float)RotationUtil.getRotations(blockPos, EnumFacing.UP)[1]) > 75.0f || MathHelper.func_76142_g((float)RotationUtil.getRotations(blockPos, EnumFacing.UP)[1]) < -75.0f) {
            enumFacing = EnumFacing.UP;
        }
        return enumFacing;
    }

    public static Rotation getRotation(Vec3 vec3) {
        return RotationUtil.getRotation(new Vec3(RotationUtil.mc.field_71439_g.field_70165_t, RotationUtil.mc.field_71439_g.field_70163_u + (double)RotationUtil.mc.field_71439_g.func_70047_e(), RotationUtil.mc.field_71439_g.field_70161_v), vec3);
    }

    public static Vec3 getLook(Vec3 vec3) {
        double d = vec3.field_72450_a - RotationUtil.mc.field_71439_g.field_70165_t;
        double d2 = vec3.field_72448_b - (RotationUtil.mc.field_71439_g.field_70163_u + (double)RotationUtil.mc.field_71439_g.func_70047_e());
        double d3 = vec3.field_72449_c - RotationUtil.mc.field_71439_g.field_70161_v;
        double d4 = MathHelper.func_76133_a((double)(d * d + d3 * d3));
        return RotationUtil.getVectorForRotation((float)(-(MathHelper.func_181159_b((double)d2, (double)d4) * 180.0 / Math.PI)), (float)(MathHelper.func_181159_b((double)d3, (double)d) * 180.0 / Math.PI - 90.0));
    }

    public static Vec3 getVectorForRotation(float f, float f2) {
        float f3 = -MathHelper.func_76134_b((float)(-f * ((float)Math.PI / 180)));
        return new Vec3((double)(MathHelper.func_76126_a((float)(-f2 * ((float)Math.PI / 180) - (float)Math.PI)) * f3), (double)MathHelper.func_76126_a((float)(-f * ((float)Math.PI / 180))), (double)(MathHelper.func_76134_b((float)(-f2 * ((float)Math.PI / 180) - (float)Math.PI)) * f3));
    }

    public static float smoothRotation(float f, float f2) {
        return RotationUtil.smoothRotation(f, f2, (float)Minecraft.func_175610_ah() / 1.5f < 60.0f ? 60.0f : (float)Minecraft.func_175610_ah() / 1.5f);
    }

    public static float smoothRotation(float f, float f2, float f3) {
        float f4 = MathHelper.func_76142_g((float)(f2 - f));
        if (f4 > f3) {
            f4 = f3;
        }
        if (f4 < -f3) {
            f4 = -f3;
        }
        return RotationUtil.fixRadius(f + f4 / 2.0f);
    }

    public static float fixRadius(float f) {
        if (f < 0.0f) {
            return 360.0f + f;
        }
        if (f > 360.0f) {
            return f % 360.0f;
        }
        if (f == 360.0f) {
            return 0.0f;
        }
        return f;
    }

    public static float getHorizontalAngleToLookVec(Vec3d vec3d) {
        Rotation rotation = RotationUtil.vec3ToRotation(vec3d);
        return MathHelper.func_76142_g((float)RotationUtil.mc.field_71439_g.field_70177_z) - rotation.getYaw();
    }
}

