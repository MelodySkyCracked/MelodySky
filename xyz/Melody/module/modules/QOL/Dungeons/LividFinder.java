/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.module.modules.QOL.Dungeons;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.System.Managers.Skyblock.Dungeons.DungeonFloors;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.other.TextUtils;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.module.FMLModules.Utils.HealthData;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.QOL.Dungeons.l;

public class LividFinder
extends Module {
    private String realLividName;
    private String prefix;
    private EntityOtherPlayerMP realLivid;
    private EntityArmorStand lividStand;
    private final Set knownLivids = new HashSet();
    private boolean isMasterMode = false;
    private Option cc = new Option("CustomColor", false);
    private Numbers r = new Numbers("Red", 0.0, 0.0, 255.0, 1.0);
    private Numbers g = new Numbers("Green", 0.0, 0.0, 255.0, 1.0);
    private Numbers b = new Numbers("Blue", 0.0, 0.0, 255.0, 1.0);
    private Numbers a = new Numbers("Alpha", 0.0, 0.0, 255.0, 1.0);
    private static final Map lividColorPrefix = new l();
    private Option tracer = new Option("Tracer", true);

    public LividFinder() {
        super("BoxLivid", new String[]{"la"}, ModuleType.Dungeons);
        this.addValues(this.tracer, this.cc, this.r, this.g, this.b, this.a);
        this.setModInfo("Create Correct Livid ESP(Tracer).");
    }

    @EventHandler
    private void tickDungeon(EventTick eventTick) {
        DungeonFloors dungeonFloors = Client.instance.dungeonManager.getCurrentFloor();
        if (!Client.inDungeons || dungeonFloors != DungeonFloors.F5 && dungeonFloors != DungeonFloors.M5) {
            this.knownLivids.clear();
            this.isMasterMode = false;
            this.lividStand = null;
            this.realLivid = null;
            this.realLividName = null;
            this.prefix = null;
            return;
        }
        this.isMasterMode = Client.instance.dungeonManager.isMMD;
    }

    @EventHandler
    private void onDraw(EventRender3D eventRender3D) {
        EntityOtherPlayerMP entityOtherPlayerMP;
        DungeonFloors dungeonFloors = Client.instance.dungeonManager.getCurrentFloor();
        if ((dungeonFloors == DungeonFloors.F5 || dungeonFloors == DungeonFloors.M5) && (entityOtherPlayerMP = this.realLivid) != null) {
            double d = entityOtherPlayerMP.field_70142_S + (entityOtherPlayerMP.field_70165_t - entityOtherPlayerMP.field_70142_S) * (double)eventRender3D.getPartialTicks() - this.mc.func_175598_ae().field_78730_l;
            double d2 = entityOtherPlayerMP.field_70137_T + (entityOtherPlayerMP.field_70163_u - entityOtherPlayerMP.field_70137_T) * (double)eventRender3D.getPartialTicks() - this.mc.func_175598_ae().field_78731_m;
            double d3 = entityOtherPlayerMP.field_70136_U + (entityOtherPlayerMP.field_70161_v - entityOtherPlayerMP.field_70136_U) * (double)eventRender3D.getPartialTicks() - this.mc.func_175598_ae().field_78728_n;
            if (((Boolean)this.cc.getValue()).booleanValue()) {
                RenderUtil.entityOutlineAXIS((Entity)entityOtherPlayerMP, new Color(((Double)this.r.getValue()).intValue(), ((Double)this.g.getValue()).intValue(), ((Double)this.b.getValue()).intValue(), ((Double)this.a.getValue()).intValue()).getRGB(), eventRender3D);
            } else {
                RenderUtil.entityOutlineAXIS((Entity)entityOtherPlayerMP, Colors.GREEN.c, eventRender3D);
            }
            RenderUtil.startDrawing();
            this.drawLine((Entity)entityOtherPlayerMP, new Color(Colors.WHITE.c), d, d2, d3);
            RenderUtil.stopDrawing();
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.knownLivids.clear();
        this.isMasterMode = false;
        this.lividStand = null;
        this.realLivid = null;
        this.realLividName = null;
        this.prefix = null;
        super.onDisable();
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent livingUpdateEvent) {
        DungeonFloors dungeonFloors = Client.instance.dungeonManager.getCurrentFloor();
        if (!Client.inDungeons || dungeonFloors != DungeonFloors.F5 && dungeonFloors != DungeonFloors.M5) {
            this.knownLivids.clear();
            this.isMasterMode = false;
            this.lividStand = null;
            this.realLivid = null;
            this.realLividName = null;
            this.prefix = null;
            return;
        }
        if (livingUpdateEvent.entityLiving.func_70005_c_().endsWith("Livid") && livingUpdateEvent.entityLiving instanceof EntityOtherPlayerMP) {
            if (!this.knownLivids.contains(livingUpdateEvent.entityLiving.func_70005_c_())) {
                this.knownLivids.add(livingUpdateEvent.entityLiving.func_70005_c_());
                this.realLividName = livingUpdateEvent.entityLiving.func_70005_c_();
                this.realLivid = (EntityOtherPlayerMP)livingUpdateEvent.entityLiving;
                this.prefix = (String)lividColorPrefix.get(this.realLividName.split(" ")[0]);
            } else if (this.realLividName != null && livingUpdateEvent.entityLiving != null && this.realLividName.equalsIgnoreCase(livingUpdateEvent.entityLiving.func_70005_c_())) {
                this.realLivid = (EntityOtherPlayerMP)livingUpdateEvent.entityLiving;
            }
        } else if ((livingUpdateEvent.entityLiving.func_70005_c_().startsWith(this.prefix + "\ufd3e ") || livingUpdateEvent.entityLiving.func_70005_c_().startsWith(this.prefix + "\ufd3e ")) && livingUpdateEvent.entityLiving instanceof EntityArmorStand) {
            this.lividStand = (EntityArmorStand)livingUpdateEvent.entityLiving;
        }
    }

    public List getHealths() {
        ArrayList<HealthData> arrayList = new ArrayList<HealthData>();
        long l2 = 0L;
        if (this.lividStand != null) {
            try {
                String string = TextUtils.stripColor(this.lividStand.func_70005_c_());
                String string2 = string.split(" ")[2];
                l2 = TextUtils.reverseFormat(string2.substring(0, string2.length() - 1));
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        arrayList.add(new HealthData(this.realLividName == null ? "unknown" : this.realLividName, (int)l2, this.isMasterMode ? 600000000 : 7000000, true));
        return arrayList;
    }

    public String getBossName() {
        return this.realLividName == null ? "Livid" : this.realLividName;
    }

    private void drawLine(Entity entity, Color color, double d, double d2, double d3) {
        float f = this.mc.field_71439_g.func_70032_d(entity);
        float f2 = f / 48.0f;
        if (f2 >= 1.0f) {
            f2 = 1.0f;
        }
        GlStateManager.func_179117_G();
        GL11.glEnable((int)2848);
        GL11.glColor4f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getAlpha());
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)0.0, (double)this.mc.field_71439_g.func_70047_e(), (double)0.0);
        GL11.glVertex3d((double)d, (double)d2, (double)d3);
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GlStateManager.func_179117_G();
    }
}

