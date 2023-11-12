/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityEndermite
 *  net.minecraft.entity.monster.EntityGolem
 *  net.minecraft.entity.monster.EntityMagmaCube
 *  net.minecraft.entity.monster.EntitySlime
 *  net.minecraft.entity.monster.EntityZombie
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.module.modules.render;

import java.util.ArrayList;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.Utils.math.MathUtil;
import xyz.Melody.Utils.render.gl.GLUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class CHMobESP
extends Module {
    private ArrayList entities = new ArrayList();
    private int ticks = 0;

    public CHMobESP() {
        super("CHMobESP", new String[]{"chme"}, ModuleType.Render);
        this.setModInfo("RenderMob ESP & Tags in Crystall Hollows.");
    }

    @EventHandler
    private void on3D(EventRender3D eventRender3D) {
        if (this.entities.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.entities.size(); ++i) {
            Entity entity = (Entity)this.entities.get(i);
            if (entity instanceof EntityGolem) {
                this.renderTag(entity, "Automation", eventRender3D.getPartialTicks());
            }
            if (entity instanceof EntitySlime && !(entity instanceof EntityMagmaCube)) {
                this.renderTag(entity, "Sludge", eventRender3D.getPartialTicks());
            }
            if (entity instanceof EntityMagmaCube) {
                this.renderTag(entity, "Yog", eventRender3D.getPartialTicks());
            }
            if (entity instanceof EntityPlayer) {
                String string = entity.func_70005_c_().toLowerCase();
                if (string.contains("team treasurite")) {
                    this.renderTag(entity, "Team Treasurite", eventRender3D.getPartialTicks());
                }
                if (string.contains("goblin") || string.contains("weaklin")) {
                    this.renderTag(entity, "Goblin", eventRender3D.getPartialTicks());
                }
            }
            if (entity instanceof EntityZombie) {
                this.renderTag(entity, "Zombie", eventRender3D.getPartialTicks());
            }
            if (!(entity instanceof EntityEndermite)) continue;
            this.renderTag(entity, "Endermite", eventRender3D.getPartialTicks());
        }
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (!Client.inSkyblock || Client.instance.sbArea.getCurrentArea() != Areas.Crystal_Hollows) {
            this.entities.clear();
            return;
        }
        if (this.ticks < 20) {
            ++this.ticks;
            return;
        }
        this.ticks = 0;
        ArrayList<Entity> arrayList = new ArrayList<Entity>();
        for (Entity entity : this.mc.field_71441_e.field_72996_f) {
            if (entity == null || !entity.func_70089_S() || !(entity instanceof EntityLivingBase) || entity.func_70005_c_() == null || entity.func_82150_aj()) continue;
            arrayList.add(entity);
        }
        this.entities.clear();
        this.entities.addAll(arrayList);
    }

    @Override
    public void onDisable() {
        this.entities.clear();
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        this.entities.clear();
    }

    private void renderTag(Entity entity, String string, float f) {
        float f2 = MathUtil.distanceToEntity((Entity)this.mc.field_71439_g, entity) / 8.0f;
        if (f2 < 1.1f) {
            f2 = 1.1f;
        }
        float f3 = f2 * 1.8f;
        f3 /= 100.0f;
        double d = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)f - this.mc.func_175598_ae().field_78730_l;
        double d2 = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)f - this.mc.func_175598_ae().field_78731_m;
        double d3 = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)f - this.mc.func_175598_ae().field_78728_n;
        GL11.glPushMatrix();
        GlStateManager.func_179117_G();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glTranslated((double)d, (double)d2, (double)d3);
        GL11.glRotatef((float)(-this.mc.func_175598_ae().field_78735_i), (float)0.0f, (float)2.0f, (float)0.0f);
        GL11.glRotatef((float)this.mc.func_175598_ae().field_78732_j, (float)2.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)(-f3), (float)(-f3), (float)f3);
        GLUtil.setGLCap(2929, false);
        string = string + "[" + (int)MathUtil.distanceToEntity((Entity)this.mc.field_71439_g, entity) + "m]";
        float f4 = (float)(-this.mc.field_71466_p.func_78256_a(string) / 2) - 4.6f;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.field_71466_p.func_78276_b(string, (int)f4 + 4, -20, -1);
        GLUtil.revertAllCaps();
        GL11.glPopMatrix();
        GlStateManager.func_179117_G();
    }
}

