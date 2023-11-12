/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.monster.EntityZombie
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.System.Managers.Skyblock.Dungeons;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.module.modules.QOL.Dungeons.SayMimicKilled;

public final class MimicListener {
    private List possibleMimic = new ArrayList();
    private Minecraft mc = Minecraft.func_71410_x();

    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        EventBus.getInstance().register(this);
    }

    @EventHandler
    public void onEntityUpdate(EventTick eventTick) {
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return;
        }
        if (!Client.inDungeons) {
            return;
        }
        if (Client.instance.dungeonManager.dungeonListener.foundMimic) {
            return;
        }
        for (Entity entity : this.mc.field_71441_e.field_72996_f) {
            if (!entity.func_145818_k_() || !entity.func_95999_t().contains("Mimic")) continue;
            this.possibleMimic = this.mc.field_71441_e.func_175674_a(entity, entity.func_174813_aQ().func_72314_b(0.5, 1.5, 0.5), MimicListener::lambda$onEntityUpdate$0);
        }
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent livingDeathEvent) {
        if (Client.inDungeons && !Client.instance.dungeonManager.dungeonListener.foundMimic && !this.possibleMimic.isEmpty() && livingDeathEvent.entity == this.possibleMimic.get(0)) {
            SayMimicKilled sayMimicKilled = SayMimicKilled.getINSTANCE();
            if (sayMimicKilled.isEnabled()) {
                if (sayMimicKilled.mode.getValue() == SayMimicKilled.Chats.All) {
                    this.mc.field_71439_g.func_71165_d("/ac Mimic Killed.");
                }
                if (sayMimicKilled.mode.getValue() == SayMimicKilled.Chats.Party) {
                    this.mc.field_71439_g.func_71165_d("/pc Mimic Killed.");
                }
            }
            Client.instance.dungeonManager.dungeonListener.foundMimic = true;
        }
    }

    private static boolean lambda$onEntityUpdate$0(Entity entity) {
        return !(entity instanceof EntityArmorStand) && entity instanceof EntityZombie && ((EntityZombie)entity).func_70631_g_();
    }
}

