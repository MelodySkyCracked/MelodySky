/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.play.server.S14PacketEntity
 *  net.minecraft.world.World
 */
package xyz.Melody.module.balance;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.world.World;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.Player.EventPreUpdate;
import xyz.Melody.Event.events.world.EventPacketRecieve;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Event.value.Value;
import xyz.Melody.Utils.game.PlayerListUtils;
import xyz.Melody.Utils.timer.TimeHelper;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class AntiBot
extends Module {
    private TimeHelper timer = new TimeHelper();
    private List invalid = new ArrayList();
    private List whitelist = new ArrayList();
    private List hurtTimeCheck = new ArrayList();
    private List playerList = new ArrayList();
    public Value remove = new Option("AutoRemove", true);
    public Value hurttime = new Numbers("HurtTimeCheck", 10000.0, 5000.0, 20000.0, 100.0);
    public Value touchedGround = new Option("GroundCheck", true);
    public Value toolCheck = new Option("ToolCheck", true);
    private static List removed = new ArrayList();
    public TimeHelper timer2 = new TimeHelper();
    public TimeHelper timer3 = new TimeHelper();
    public List onAirInvalid = new ArrayList();

    public AntiBot() {
        super("AntiBot", ModuleType.Balance);
        this.addValues(this.remove, this.hurttime, this.touchedGround, this.toolCheck);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate eventPreUpdate) {
        if (!this.hurtTimeCheck.isEmpty() && this.timer3.isDelayComplete(((Double)this.hurttime.getValue()).longValue())) {
            this.hurtTimeCheck.clear();
            this.timer3.reset();
        }
        if (!this.whitelist.isEmpty() && this.timer2.isDelayComplete(3000L)) {
            this.whitelist.clear();
            this.timer2.reset();
        }
        if (!this.invalid.isEmpty() && this.timer.isDelayComplete(1000L)) {
            this.invalid.clear();
            this.timer.reset();
        }
        for (Object e : this.mc.field_71441_e.func_72910_y()) {
            EntityPlayer entityPlayer;
            if (!(e instanceof EntityPlayer) || (entityPlayer = (EntityPlayer)e) == this.mc.field_71439_g || this.invalid.contains(entityPlayer)) continue;
            String string = entityPlayer.func_145748_c_().func_150254_d();
            String string2 = entityPlayer.func_95999_t();
            String string3 = entityPlayer.func_70005_c_();
            if (!string.startsWith("\ufffd\ufffd") && string.endsWith("\ufffd\ufffdr")) {
                this.invalid.add(entityPlayer);
            }
            if (!PlayerListUtils.isInTablist(entityPlayer)) {
                this.invalid.add(entityPlayer);
            }
            if (entityPlayer.field_70737_aN > 0) {
                this.hurtTimeCheck.add(entityPlayer);
            }
            if (this.hurtTimeCheck.contains(entityPlayer) && !this.whitelist.contains(entityPlayer)) {
                this.whitelist.add(entityPlayer);
            }
            if (entityPlayer.func_70694_bm() != null) {
                this.whitelist.add(entityPlayer);
            }
            if (entityPlayer.func_70694_bm() == null && !this.whitelist.contains(entityPlayer) && ((Boolean)this.toolCheck.getValue()).booleanValue()) {
                this.invalid.add(entityPlayer);
            }
            if (entityPlayer.func_82150_aj() && !string2.equalsIgnoreCase("") && string2.toLowerCase().contains("\ufffd\ufffdc\ufffd\ufffdc") && string3.contains("\ufffd\ufffdc")) {
                this.invalid.add(entityPlayer);
            }
            if (!string2.equalsIgnoreCase("") && string2.toLowerCase().contains("\ufffd\ufffdc") && string2.toLowerCase().contains("\ufffd\ufffdr")) {
                this.invalid.add(entityPlayer);
            }
            if (string.contains("\ufffd\ufffd8[NPC]")) {
                this.invalid.add(entityPlayer);
            }
            if (string.contains("\ufffd\ufffdc") || string2.equalsIgnoreCase("")) continue;
            this.invalid.add(entityPlayer);
        }
    }

    @Override
    public void onEnable() {
        this.onAirInvalid.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.onAirInvalid.clear();
        super.onDisable();
    }

    @EventHandler
    public void onReceivePacket(EventPacketRecieve eventPacketRecieve) {
        S14PacketEntity s14PacketEntity;
        Entity entity;
        if (this.mc.field_71441_e == null || this.mc.field_71439_g == null) {
            return;
        }
        if (eventPacketRecieve.getPacket() instanceof S14PacketEntity && (entity = (s14PacketEntity = (S14PacketEntity)eventPacketRecieve.getPacket()).func_149065_a((World)this.mc.field_71441_e)) instanceof EntityPlayer && !s14PacketEntity.func_179742_g() && !this.onAirInvalid.contains(entity.func_145782_y())) {
            this.onAirInvalid.add(entity.func_145782_y());
        }
    }

    public boolean isEntityBot(Entity entity) {
        if (!this.isEnabled()) {
            return false;
        }
        if (!(entity instanceof EntityPlayer) || !this.isEnabled()) {
            return false;
        }
        EntityPlayer entityPlayer = (EntityPlayer)entity;
        return this.invalid.contains(entityPlayer) || !this.onAirInvalid.contains(entityPlayer.func_145782_y());
    }
}

