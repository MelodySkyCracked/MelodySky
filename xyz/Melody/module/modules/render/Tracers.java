/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.player.EntityPlayer
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.module.modules.render;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.System.Managers.Client.FriendManager;
import xyz.Melody.Utils.math.MathUtil;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.balance.AntiBot;

public class Tracers
extends Module {
    public Tracers() {
        super("Tracers", new String[]{"lines", "tracer"}, ModuleType.Render);
        this.setModInfo("Crosshair ----line---- Players.");
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    private void on3DRender(EventRender3D eventRender3D) {
        if (Client.inDungeons) {
            return;
        }
        for (Object e : this.mc.field_71441_e.field_72996_f) {
            double[] dArray;
            double[] dArray2;
            Entity entity = (Entity)e;
            if (!entity.func_70089_S() || !(entity instanceof EntityPlayer) || entity == this.mc.field_71439_g || ((AntiBot)Client.instance.getModuleManager().getModuleByClass(AntiBot.class)).isEntityBot(entity) || entity instanceof EntityArmorStand) continue;
            double d = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)eventRender3D.getPartialTicks() - this.mc.func_175598_ae().field_78730_l;
            double d2 = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)eventRender3D.getPartialTicks() - this.mc.func_175598_ae().field_78731_m;
            double d3 = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)eventRender3D.getPartialTicks() - this.mc.func_175598_ae().field_78728_n;
            RenderUtil.startDrawing();
            float f = (float)Math.round(255.0 - this.mc.field_71439_g.func_70068_e(entity) * 255.0 / MathUtil.sq((double)this.mc.field_71474_y.field_151451_c * 2.5)) / 255.0f;
            if (FriendManager.isFriend(entity.func_145748_c_().func_150260_c())) {
                dArray2 = new double[3];
                dArray2[0] = 0.0;
                dArray2[1] = 1.0;
                dArray = dArray2;
                dArray2[2] = 1.0;
            } else {
                dArray2 = new double[3];
                dArray2[0] = f;
                dArray2[1] = 1.0f - f;
                dArray = dArray2;
                dArray2[2] = 0.0;
            }
            this.drawLine(entity, dArray, d, d2, d3);
            RenderUtil.stopDrawing();
        }
    }

    private void drawLine(Entity entity, double[] dArray, double d, double d2, double d3) {
        float f = this.mc.field_71439_g.func_70032_d(entity);
        float f2 = f / 48.0f;
        if (f2 >= 1.0f) {
            f2 = 1.0f;
        }
        GL11.glEnable((int)2848);
        if (dArray.length >= 4) {
            if (dArray[3] <= 0.1) {
                return;
            }
            GL11.glColor4d((double)dArray[0], (double)dArray[1], (double)dArray[2], (double)dArray[3]);
        } else {
            GL11.glColor3d((double)dArray[0], (double)dArray[1], (double)dArray[2]);
        }
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)0.0, (double)this.mc.field_71439_g.func_70047_e(), (double)0.0);
        GL11.glVertex3d((double)d, (double)d2, (double)d3);
        GL11.glEnd();
        GL11.glDisable((int)2848);
    }
}

