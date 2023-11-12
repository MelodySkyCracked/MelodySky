/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.StringUtils
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.client.event.RenderLivingEvent$Pre
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.QOL.Slayer;

import java.awt.Color;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.Utils.MethodReflectionHelper;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class BlazeDagger
extends Module {
    private long lastClickTime = 0L;
    private MethodReflectionHelper CONTROLLER = new MethodReflectionHelper(PlayerControllerMP.class, "func_78750_j", "syncCurrentPlayItem", new Class[0]);

    public BlazeDagger() {
        super("AutoBlazeDagger", new String[]{"cb"}, ModuleType.Nether);
        this.setColor(new Color(158, 205, 125).getRGB());
        this.setModInfo("Auto Swap Dagger Mode.");
    }

    @SubscribeEvent(priority=EventPriority.LOWEST, receiveCanceled=true)
    public void onRenderEntity(RenderLivingEvent.Pre pre) {
        if (pre.entity instanceof EntityArmorStand) {
            EntityArmorStand entityArmorStand = (EntityArmorStand)pre.entity;
            if (!entityArmorStand.func_145818_k_()) {
                return;
            }
            String string = StringUtils.func_76338_a((String)entityArmorStand.func_95999_t());
            double d = pre.entity.field_70165_t;
            double d2 = pre.entity.field_70163_u;
            double d3 = pre.entity.field_70161_v;
            if (Client.instance.sbArea.getCurrentArea() == Areas.Crimson_Island && this.mc.field_71462_r == null && this >= 0) {
                if (string.startsWith("CRYSTAL")) {
                    if (this.isFacingAABB(new AxisAlignedBB(d - 0.5, d2 - 3.0, d3 - 0.5, d + 0.5, d2 + 1.0, d3 + 0.5), 5.0f)) {
                        this.swapToCrystal();
                    }
                    return;
                }
                if (string.startsWith("ASHEN")) {
                    if (this.isFacingAABB(new AxisAlignedBB(d - 0.5, d2 - 3.0, d3 - 0.5, d + 0.5, d2 + 1.0, d3 + 0.5), 5.0f)) {
                        this.swapToAshen();
                    }
                    return;
                }
                if (string.startsWith("AURIC")) {
                    if (this.isFacingAABB(new AxisAlignedBB(d - 0.5, d2 - 3.0, d3 - 0.5, d + 0.5, d2 + 1.0, d3 + 0.5), 5.0f)) {
                        this.swapToAuric();
                    }
                    return;
                }
                if (string.startsWith("SPIRIT")) {
                    if (this.isFacingAABB(new AxisAlignedBB(d - 0.5, d2 - 3.0, d3 - 0.5, d + 0.5, d2 + 1.0, d3 + 0.5), 5.0f)) {
                        this.swapToSprit();
                    }
                    return;
                }
            }
        }
    }

    public void swapToCrystal() {
        for (int i = 0; i < 8; ++i) {
            String string;
            ItemStack itemStack = this.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (itemStack == null || !(string = itemStack.func_82833_r()).contains("Deathripper Dagger") && !string.contains("Mawdredge Dagger") && !string.contains("Twilight Dagger")) continue;
            this.mc.field_71439_g.field_71071_by.field_70461_c = i;
            if (itemStack.func_77973_b() != Items.field_151048_u) {
                this.CONTROLLER.invoke(this.mc.field_71442_b);
                Client.rightClick();
            }
            this.lastClickTime = System.currentTimeMillis();
            break;
        }
    }

    public void swapToSprit() {
        for (int i = 0; i < 8; ++i) {
            String string;
            ItemStack itemStack = this.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (itemStack == null || !(string = itemStack.func_82833_r()).contains("Deathripper Dagger") && !string.contains("Mawdredge Dagger") && !string.contains("Twilight Dagger")) continue;
            this.mc.field_71439_g.field_71071_by.field_70461_c = i;
            if (itemStack.func_77973_b() != Items.field_151040_l) {
                this.CONTROLLER.invoke(this.mc.field_71442_b);
                Client.rightClick();
            }
            this.lastClickTime = System.currentTimeMillis();
            break;
        }
    }

    public void swapToAshen() {
        for (int i = 0; i < 8; ++i) {
            String string;
            ItemStack itemStack = this.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (itemStack == null || !(string = itemStack.func_82833_r()).contains("Pyrochaos Dagger") && !string.contains("Kindlebane Dagger") && !string.contains("Firedust Dagger")) continue;
            this.mc.field_71439_g.field_71071_by.field_70461_c = i;
            if (itemStack.func_77973_b() != Items.field_151052_q) {
                this.CONTROLLER.invoke(this.mc.field_71442_b);
                Client.rightClick();
            }
            this.lastClickTime = System.currentTimeMillis();
            break;
        }
    }

    public void swapToAuric() {
        for (int i = 0; i < 8; ++i) {
            String string;
            ItemStack itemStack = this.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (itemStack == null || !(string = itemStack.func_82833_r()).contains("Pyrochaos Dagger") && !string.contains("Kindlebane Dagger") && !string.contains("Firedust Dagger")) continue;
            this.mc.field_71439_g.field_71071_by.field_70461_c = i;
            if (itemStack.func_77973_b() != Items.field_151010_B) {
                this.CONTROLLER.invoke(this.mc.field_71442_b);
                Client.rightClick();
            }
            this.lastClickTime = System.currentTimeMillis();
            break;
        }
    }

    public boolean isFacingAABB(AxisAlignedBB axisAlignedBB, float f) {
        return this.isInterceptable(axisAlignedBB, f);
    }

    public Vec3 getPositionEyes() {
        return new Vec3(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u + (double)this.fastEyeHeight(), this.mc.field_71439_g.field_70161_v);
    }

    public float fastEyeHeight() {
        return this.mc.field_71439_g.func_70093_af() ? 1.54f : 1.62f;
    }

    public boolean isInterceptable(AxisAlignedBB axisAlignedBB, float f) {
        Vec3 vec3 = this.getPositionEyes();
        Vec3 vec32 = this.getVectorForRotation();
        return this.isInterceptable(vec3, vec3.func_72441_c(vec32.field_72450_a * (double)f, vec32.field_72448_b * (double)f, vec32.field_72449_c * (double)f), axisAlignedBB);
    }

    private Vec3 getVectorForRotation() {
        float f = -MathHelper.func_76134_b((float)(-this.mc.field_71439_g.field_70125_A * ((float)Math.PI / 180)));
        return new Vec3((double)(MathHelper.func_76126_a((float)(-this.mc.field_71439_g.field_70177_z * ((float)Math.PI / 180) - (float)Math.PI)) * f), (double)MathHelper.func_76126_a((float)(-this.mc.field_71439_g.field_70125_A * ((float)Math.PI / 180))), (double)(MathHelper.func_76134_b((float)(-this.mc.field_71439_g.field_70177_z * ((float)Math.PI / 180) - (float)Math.PI)) * f));
    }

    /*
     * Exception decompiling
     */
    public boolean isInterceptable(Vec3 var1, Vec3 var2, AxisAlignedBB var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl49 : ICONST_1 - null : trying to set 10 previously set to 12
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
}

