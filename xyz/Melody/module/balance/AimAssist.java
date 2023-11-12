/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.entity.monster.EntityEnderman
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 */
package xyz.Melody.module.balance;

import java.util.List;
import java.util.Random;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.Player.EventPreUpdate;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class AimAssist
extends Module {
    private Random rand = new Random();
    private Numbers h = new Numbers("Horizontal", 4.2, 0.0, 10.0, 0.1);
    private Numbers v = new Numbers("Vertical", 2.4, 0.0, 10.0, 0.1);
    private Numbers s = new Numbers("Speed", 0.2, 0.0, 1.5, 0.01);
    private Numbers r = new Numbers("AimRange", 4.2, 1.0, 8.1, 0.1);
    private Numbers amin = new Numbers("MinAngle", 0.0, 0.0, 1.0, 1.0);
    private Numbers amax = new Numbers("MaxAngle", 100.0, 20.0, 360.0, 1.0);
    private Option ca = new Option("ClickAim", false);
    private Option str = new Option("MoveStrafing", false);
    private Option team = new Option("Team", true);

    public AimAssist() {
        super("AimAssist", new String[]{"AimAssist"}, ModuleType.Balance);
        this.addValues(this.h, this.v, this.s, this.r, this.amin, this.amax, this.ca, this.str, this.team);
    }

    /*
     * Exception decompiling
     */
    @EventHandler
    public void onUpdate(EventPreUpdate var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl36 : ALOAD - null : trying to set 1 previously set to 0
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

    protected float getRotation(float f, float f2, float f3) {
        float f4 = MathHelper.func_76142_g((float)(f2 - f));
        if (f4 > f3) {
            f4 = f3;
        }
        if (f4 < -f3) {
            f4 = -f3;
        }
        return f + f4 / 2.0f;
    }

    private void faceTarget(Entity entity, float f, float f2) {
        EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
        float f3 = this.getAngles(entity)[1];
        float f4 = this.getAngles(entity)[0];
        entityPlayerSP.field_70177_z = this.getRotation(entityPlayerSP.field_70177_z, f3, f);
        entityPlayerSP.field_70125_A = this.getRotation(entityPlayerSP.field_70125_A, f4, f2);
    }

    public float[] getAngles(Entity entity) {
        double d = entity.field_70165_t - this.mc.field_71439_g.field_70165_t;
        double d2 = entity.field_70161_v - this.mc.field_71439_g.field_70161_v;
        double d3 = entity instanceof EntityEnderman ? entity.field_70163_u - this.mc.field_71439_g.field_70163_u : entity.field_70163_u + ((double)entity.func_70047_e() - 1.9) - this.mc.field_71439_g.field_70163_u + ((double)this.mc.field_71439_g.func_70047_e() - 1.9);
        double d4 = MathHelper.func_76133_a((double)(d * d + d2 * d2));
        float f = (float)Math.toDegrees(-Math.atan(d / d2));
        float f2 = (float)(-Math.toDegrees(Math.atan(d3 / d4)));
        if (d2 < 0.0 && d < 0.0) {
            f = (float)(90.0 + Math.toDegrees(Math.atan(d2 / d)));
        } else if (d2 < 0.0 && d > 0.0) {
            f = (float)(-90.0 + Math.toDegrees(Math.atan(d2 / d)));
        }
        return new float[]{f2, f};
    }

    public double getDistanceBetweenAngles(float f, float f2) {
        float f3 = Math.abs(f - f2) % 360.0f;
        if (f3 > 180.0f) {
            f3 = 360.0f - f3;
        }
        return f3;
    }

    public Object[] getEntity(double d, double d2, float f) {
        Entity entity = this.mc.func_175606_aa();
        Entity entity2 = null;
        if (entity == null || this.mc.field_71441_e == null) {
            return null;
        }
        this.mc.field_71424_I.func_76320_a("pick");
        double d3 = d;
        double d4 = d;
        Vec3 vec3 = entity.func_174824_e(0.0f);
        Vec3 vec32 = entity.func_70676_i(0.0f);
        Vec3 vec33 = vec3.func_72441_c(vec32.field_72450_a * d3, vec32.field_72448_b * d3, vec32.field_72449_c * d3);
        Vec3 vec34 = null;
        float f2 = 1.0f;
        List list = this.mc.field_71441_e.func_72839_b(entity, entity.func_174813_aQ().func_72321_a(vec32.field_72450_a * d3, vec32.field_72448_b * d3, vec32.field_72449_c * d3).func_72314_b((double)f2, (double)f2, (double)f2));
        double d5 = d4;
        for (int i = 0; i < list.size(); ++i) {
            double d6;
            Entity entity3 = (Entity)list.get(i);
            if (!entity3.func_70067_L()) continue;
            float f3 = entity3.func_70111_Y();
            AxisAlignedBB axisAlignedBB = entity3.func_174813_aQ().func_72314_b((double)f3, (double)f3, (double)f3);
            axisAlignedBB = axisAlignedBB.func_72314_b(d2, d2, d2);
            MovingObjectPosition movingObjectPosition = axisAlignedBB.func_72327_a(vec3, vec33);
            if (axisAlignedBB.func_72318_a(vec3)) {
                if (!(0.0 < d5) && d5 != 0.0) continue;
                entity2 = entity3;
                vec34 = movingObjectPosition == null ? vec3 : movingObjectPosition.field_72307_f;
                d5 = 0.0;
                continue;
            }
            if (movingObjectPosition == null || !((d6 = vec3.func_72438_d(movingObjectPosition.field_72307_f)) < d5) && d5 != 0.0) continue;
            entity2 = entity3;
            vec34 = movingObjectPosition.field_72307_f;
            d5 = d6;
        }
        if (d5 < d4 && !(entity2 instanceof EntityLivingBase) && !(entity2 instanceof EntityItemFrame)) {
            entity2 = null;
        }
        this.mc.field_71424_I.func_76319_b();
        if (entity2 == null || vec34 == null) {
            return null;
        }
        return new Object[]{entity2, vec34};
    }

    public Entity getEntity(double d) {
        if (this.getEntity(d, 0.0, 0.0f) == null) {
            return null;
        }
        return (Entity)this.getEntity(d, 0.0, 0.0f)[0];
    }
}

