/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.boss.EntityDragon
 *  net.minecraft.entity.boss.EntityWither
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.monster.EntityCreeper
 *  net.minecraft.entity.monster.EntityEnderman
 *  net.minecraft.entity.passive.EntityBat
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityArrow
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.QOL;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.rendering.EventRenderEntityModel;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Option;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.game.ScoreboardUtils;
import xyz.Melody.Utils.render.FadeUtil;
import xyz.Melody.Utils.render.OutlineUtils;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class MobTracker
extends Module {
    private Option ghost = new Option("Ghost", true);
    private Option bat = new Option("Bat", true);
    private Option starMob = new Option("Starred Mob", true);
    private Option dragon = new Option("Ender Dragon", true);
    private Option arrows = new Option("Arrows", false);
    private Option necron = new Option("Wither", true);
    private Option wither = new Option("Wither(Crimson)", true);
    private Option farming = new Option("Endangered Animal", true);
    private Option modle = new Option("RenderModle", false);
    private Option removeArrows = new Option("RemoveArrows", false);
    private HashMap mobIDs = new HashMap();
    public HashMap checked = new HashMap();
    private ArrayList endangeredAnimals = new ArrayList();

    public MobTracker() {
        super("MobTracker", new String[]{"mt"}, ModuleType.Render);
        this.addValues(this.ghost, this.bat, this.starMob, this.dragon, this.necron, this.wither, this.arrows, this.farming, this.modle, this.removeArrows);
        this.setModInfo("Entity ESP.");
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
    private void tick(EventTick eventTick) {
        if (((Boolean)this.removeArrows.getValue()).booleanValue()) {
            for (Entity entity : this.mc.field_71441_e.field_72996_f) {
                if (!(entity instanceof EntityArrow)) continue;
                this.mc.field_71441_e.func_72900_e(entity);
            }
        }
    }

    @EventHandler
    public void onRenderEntityModel(EventRenderEntityModel eventRenderEntityModel) {
        if (!((Boolean)this.modle.getValue()).booleanValue()) {
            return;
        }
        if (((Boolean)this.starMob.getValue()).booleanValue()) {
            if (eventRenderEntityModel.getEntity().func_82150_aj() && !(eventRenderEntityModel.getEntity() instanceof EntityCreeper)) {
                return;
            }
            if (!this.mobIDs.isEmpty() && this.mobIDs.containsKey(eventRenderEntityModel.getEntity().func_145782_y()) && !this.checked.containsKey(eventRenderEntityModel.getEntity())) {
                this.checked.put(eventRenderEntityModel.getEntity(), this.mobIDs.get(eventRenderEntityModel.getEntity().func_145782_y()));
            }
            if (this.checked.containsKey(eventRenderEntityModel.getEntity())) {
                OutlineUtils.outlineEntity(eventRenderEntityModel, (Color)this.checked.get(eventRenderEntityModel.getEntity()), 4);
            }
        }
        if (eventRenderEntityModel.getEntity() == this.mc.field_71439_g) {
            OutlineUtils.outlineEntity(eventRenderEntityModel, Color.BLUE, 4);
        }
    }

    @EventHandler
    private void onRender3D(EventRender3D eventRender3D) {
        this.mobIDs.clear();
        for (Entity entity : this.mc.field_71441_e.func_72910_y()) {
            Object object;
            Object object2;
            if (entity instanceof EntityArrow && ((Boolean)this.arrows.getValue()).booleanValue()) {
                object2 = new Color(Colors.WHITE.c);
                object = new Color(((Color)object2).getRed(), ((Color)object2).getGreen(), ((Color)object2).getBlue());
                RenderUtil.drawFilledESP(entity, (Color)object, eventRender3D);
            }
            if (entity instanceof EntityWither && ((Boolean)this.wither.getValue()).booleanValue() && !entity.func_82150_aj()) {
                object2 = new Color(Colors.GREEN.c);
                object = new Color(((Color)object2).getRed(), ((Color)object2).getGreen(), ((Color)object2).getBlue());
                if (!this.checked.keySet().contains((EntityLivingBase)entity)) {
                    this.checked.put((EntityLivingBase)entity, object);
                    RenderUtil.drawFilledESP(entity, (Color)object, eventRender3D);
                }
                if (!((Boolean)this.modle.getValue()).booleanValue()) {
                    RenderUtil.drawFilledESP(entity, (Color)object, eventRender3D);
                }
            }
            if (entity instanceof EntityCreeper && ScoreboardUtils.scoreboardContains("The Mist") && ((Boolean)this.ghost.getValue()).booleanValue()) {
                object2 = new Color(Colors.RED.c);
                object = new Color(((Color)object2).getRed(), ((Color)object2).getGreen(), ((Color)object2).getBlue());
                if (!this.checked.keySet().contains((EntityLivingBase)entity)) {
                    this.checked.put((EntityLivingBase)entity, object);
                    RenderUtil.drawFilledESP(entity, (Color)object, eventRender3D);
                }
                if (!((Boolean)this.modle.getValue()).booleanValue()) {
                    RenderUtil.drawFilledESP(entity, (Color)object, eventRender3D);
                }
            }
            if (entity instanceof EntityBat && ((Boolean)this.bat.getValue()).booleanValue()) {
                object2 = new Color(Colors.BLUE.c);
                object = new Color(((Color)object2).getRed(), ((Color)object2).getGreen(), ((Color)object2).getBlue());
                if (!this.checked.keySet().contains((EntityLivingBase)entity)) {
                    this.checked.put((EntityLivingBase)entity, object);
                    RenderUtil.drawFilledESP(entity, (Color)object, eventRender3D);
                }
                if (!((Boolean)this.modle.getValue()).booleanValue()) {
                    RenderUtil.drawFilledESP(entity, (Color)object, eventRender3D);
                }
            }
            if (entity instanceof EntityDragon && ((Boolean)this.dragon.getValue()).booleanValue() && !Client.inDungeons) {
                object2 = new Color(Colors.YELLOW.c);
                object = new Color(((Color)object2).getRed(), ((Color)object2).getGreen(), ((Color)object2).getBlue());
                RenderUtil.drawFilledESP(entity, (Color)object, eventRender3D);
            }
            if (entity instanceof EntityWither && ((Boolean)this.necron.getValue()).booleanValue() && !entity.func_82150_aj() && Client.inDungeons) {
                object2 = FadeUtil.PURPLE.getColor();
                object = new Color(((Color)object2).getRed(), ((Color)object2).getGreen(), ((Color)object2).getBlue());
                RenderUtil.drawFilledESP(entity, (Color)object, eventRender3D);
            }
            if (((Boolean)this.farming.getValue()).booleanValue() && Client.instance.sbArea.getCurrentArea() == Areas.The_Farming_Island && entity.func_145818_k_() && this.isEndangeredAnimal((String)(object2 = entity.func_95999_t().toLowerCase())) != null && this.isEndangeredAnimal((String)object2) != EAnimalTypes.NONE && !(object = this.mc.field_71441_e.func_175674_a(entity, entity.func_174813_aQ().func_72314_b(0.0, 2.0, 0.0), this::lambda$onRender3D$0)).isEmpty() && object.get(0) instanceof EntityLivingBase && !this.checked.keySet().contains((EntityLivingBase)object.get(0))) {
                Entity entity2 = (Entity)object.get(0);
                switch (this.isEndangeredAnimal((String)object2).toString()) {
                    case "Trackable": {
                        Color color = FadeUtil.PURPLE.getColor();
                        RenderUtil.drawFilledESP(entity2, color, eventRender3D, 2.3f);
                        break;
                    }
                    case "Untrackable": {
                        Color color = FadeUtil.BLUE.getColor();
                        RenderUtil.drawFilledESP(entity2, color, eventRender3D, 2.3f);
                        break;
                    }
                    case "Undetected": {
                        Color color = FadeUtil.GREEN.getColor();
                        RenderUtil.drawFilledESP(entity2, color, eventRender3D, 2.3f);
                        break;
                    }
                    case "Endangered": {
                        Color color = FadeUtil.WHITE.getColor();
                        RenderUtil.drawFilledESP(entity2, color, eventRender3D, 2.3f);
                        break;
                    }
                    case "Elusive": {
                        Color color = FadeUtil.RED.getColor();
                        RenderUtil.drawFilledESP(entity2, color, eventRender3D, 2.3f);
                        break;
                    }
                }
            }
            if (!((Boolean)this.starMob.getValue()).booleanValue()) continue;
            object2 = entity;
            if (entity.func_145818_k_() && entity.func_95999_t().contains("\u272f") && !(object = this.mc.field_71441_e.func_175674_a((Entity)object2, object2.func_174813_aQ().func_72314_b(0.0, 3.0, 0.0), this::lambda$onRender3D$1)).isEmpty()) {
                boolean bl;
                boolean bl2 = bl = entity.func_70005_c_().toUpperCase().equals("SHADOW ASSASSIN") || entity.func_70005_c_().toUpperCase().equals("LOST ADVENTURER") || entity.func_70005_c_().toUpperCase().equals("DIAMOND GUY");
                if (entity != this.mc.field_71439_g && !bl) {
                    if (((Boolean)this.modle.getValue()).booleanValue()) {
                        this.mobIDs.put(((Entity)object.get(0)).func_145782_y(), new Color(135, 206, 250));
                    }
                    if (!((Boolean)this.modle.getValue()).booleanValue() && object.get(0) instanceof EntityLivingBase && !this.checked.keySet().contains((EntityLivingBase)object.get(0))) {
                        RenderUtil.drawFilledESP((Entity)object.get(0), new Color(135, 206, 250), eventRender3D);
                    }
                }
            }
            if (entity instanceof EntityEnderman && entity.func_82150_aj()) {
                entity.func_82142_c(false);
            }
            if (!(entity instanceof EntityPlayer)) continue;
            switch (entity.func_70005_c_().toUpperCase()) {
                case "SHADOW ASSASSIN": {
                    entity.func_82142_c(false);
                    this.mobIDs.put(entity.func_145782_y(), new Color(Colors.RED.c));
                    if (this.checked.keySet().contains((EntityLivingBase)entity)) break;
                    RenderUtil.drawFilledESP(entity, new Color(Colors.RED.c), eventRender3D);
                    break;
                }
                case "LOST ADVENTURER": {
                    this.mobIDs.put(entity.func_145782_y(), new Color(Colors.GREEN.c));
                    if (this.checked.keySet().contains((EntityLivingBase)entity)) break;
                    RenderUtil.drawFilledESP(entity, new Color(Colors.GREEN.c), eventRender3D);
                    break;
                }
                case "DIAMOND GUY": {
                    this.mobIDs.put(entity.func_145782_y(), new Color(Colors.BLUE.c));
                    if (this.checked.keySet().contains((EntityLivingBase)entity)) break;
                    RenderUtil.drawFilledESP(entity, new Color(Colors.BLUE.c), eventRender3D);
                }
            }
        }
    }

    private EAnimalTypes isEndangeredAnimal(String string) {
        if (string.contains("trackable")) {
            return EAnimalTypes.Trackable;
        }
        if (string.contains("untrackable")) {
            return EAnimalTypes.Untrackable;
        }
        if (string.contains("undetected")) {
            return EAnimalTypes.Undetected;
        }
        if (string.contains("endangered")) {
            return EAnimalTypes.Endangered;
        }
        if (string.contains("elusive")) {
            return EAnimalTypes.Elusive;
        }
        return EAnimalTypes.NONE;
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        this.checked.clear();
        this.mobIDs.clear();
        this.endangeredAnimals.clear();
    }

    private boolean lambda$onRender3D$1(Entity entity) {
        return !(entity instanceof EntityArmorStand) && entity != this.mc.field_71439_g;
    }

    private boolean lambda$onRender3D$0(Entity entity) {
        return !(entity instanceof EntityArmorStand) && entity != this.mc.field_71439_g;
    }

    static enum EAnimalTypes {
        NONE,
        Trackable,
        Untrackable,
        Undetected,
        Endangered,
        Elusive;

    }
}

