/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 */
package xyz.Melody.module.modules.QOL.Dungeons;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.Player.EventPreUpdate;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class CrystalGetter
extends Module {
    private TimerUtil timer = new TimerUtil();
    private EntityEnderCrystal crystal;
    private ArrayList crystalList = new ArrayList();
    private Numbers range = new Numbers("Range", 6.0, 1.0, 6.0, 1.0);

    public CrystalGetter() {
        super("CrystalGetter", new String[]{"cr"}, ModuleType.Dungeons);
        this.addValues(this.range);
        this.setModInfo("Auto Get Crystal in Range in F7/M7.");
    }

    @EventHandler
    public void onTick(EventTick eventTick) {
        this.crystal = this.getClosestCrystal();
    }

    @EventHandler
    public void onRightClick(EventPreUpdate eventPreUpdate) {
        List list;
        if (this.crystal == null) {
            return;
        }
        if ((double)this.mc.field_71439_g.func_70032_d((Entity)this.crystal) > (Double)this.range.getValue()) {
            return;
        }
        if (this.crystal != null && this.timer.hasReached(200.0) && !(list = this.mc.field_71441_e.func_175674_a((Entity)this.crystal, this.crystal.func_174813_aQ(), CrystalGetter::lambda$onRightClick$0)).isEmpty() && list.get(0) != null) {
            this.mc.field_71442_b.func_78768_b((EntityPlayer)this.mc.field_71439_g, (Entity)list.get(0));
            this.timer.reset();
        }
    }

    private EntityEnderCrystal getClosestCrystal() {
        this.crystalList.clear();
        for (Entity entity : this.mc.field_71441_e.field_72996_f) {
            if (!(entity instanceof EntityEnderCrystal)) continue;
            this.crystalList.add((EntityEnderCrystal)entity);
        }
        this.crystalList.sort(this::lambda$getClosestCrystal$1);
        if (this.crystalList.size() > 0) {
            return (EntityEnderCrystal)this.crystalList.get(0);
        }
        return null;
    }

    private int lambda$getClosestCrystal$1(EntityEnderCrystal entityEnderCrystal, EntityEnderCrystal entityEnderCrystal2) {
        return (int)(entityEnderCrystal.func_70032_d((Entity)this.mc.field_71439_g) - entityEnderCrystal2.func_70032_d((Entity)this.mc.field_71439_g));
    }

    private static boolean lambda$onRightClick$0(Entity entity) {
        return entity instanceof EntityArmorStand && entity.func_95999_t().contains("CLICK HERE");
    }
}

