/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.server.S29PacketSoundEffect
 */
package xyz.Melody.module.modules.QOL;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventPacketRecieve;
import xyz.Melody.Event.value.Option;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class SoundsHider
extends Module {
    private Option emanHurt = new Option("Enderman Hurt", false);
    private Option emanDie = new Option("Enderman Die", true);
    private Option emanAnger = new Option("Enderman Anger", true);
    private Option abcd = new Option("Ability Cooldown", true);
    private Option jerryChine = new Option("Jerry-ChineGun", false);
    private Option explosions = new Option("Explosions", false);
    private Option harvest = new Option("Harvest", true);

    public SoundsHider() {
        super("SoundsHider", new String[]{"nbt"}, ModuleType.QOL);
        this.addValues(this.emanHurt, this.emanAnger, this.emanDie, this.abcd, this.jerryChine, this.explosions, this.harvest);
        this.setModInfo("Hide Fuckin Sounds.");
    }

    @EventHandler
    private void onPacketRCV(EventPacketRecieve eventPacketRecieve) {
        Packet packet = eventPacketRecieve.getPacket();
        if (packet instanceof S29PacketSoundEffect) {
            S29PacketSoundEffect s29PacketSoundEffect = (S29PacketSoundEffect)packet;
            if (((Boolean)this.emanHurt.getValue()).booleanValue() && s29PacketSoundEffect.func_149212_c().contains("dig.grass")) {
                eventPacketRecieve.setCancelled(true);
            }
            if (((Boolean)this.emanHurt.getValue()).booleanValue() && s29PacketSoundEffect.func_149212_c().contains("mob.endermen.hit")) {
                eventPacketRecieve.setCancelled(true);
            }
            if (((Boolean)this.emanDie.getValue()).booleanValue() && s29PacketSoundEffect.func_149212_c().contains("mob.endermen.death")) {
                eventPacketRecieve.setCancelled(true);
            }
            if (((Boolean)this.emanAnger.getValue()).booleanValue() && (s29PacketSoundEffect.func_149212_c().contains("mob.endermen.scream") || s29PacketSoundEffect.func_149212_c().contains("mob.endermen.stare"))) {
                eventPacketRecieve.setCancelled(true);
            }
            if (((Boolean)this.explosions.getValue()).booleanValue() && s29PacketSoundEffect.func_149212_c().contains("random.explode")) {
                eventPacketRecieve.setCancelled(true);
            }
            if (((Boolean)this.jerryChine.getValue()).booleanValue()) {
                if (s29PacketSoundEffect.func_149212_c().contains("mob.villager.yes") && s29PacketSoundEffect.func_149208_g() == 0.35f) {
                    eventPacketRecieve.setCancelled(true);
                }
                if (s29PacketSoundEffect.func_149212_c().contains("mob.villager.haggle") && s29PacketSoundEffect.func_149208_g() == 0.5f) {
                    eventPacketRecieve.setCancelled(true);
                }
            }
            if (((Boolean)this.abcd.getValue()).booleanValue() && s29PacketSoundEffect.func_149212_c().contains("mob.endermen.portal") && s29PacketSoundEffect.func_149209_h() == 0.0f && s29PacketSoundEffect.func_149208_g() == 8.0f) {
                eventPacketRecieve.setCancelled(true);
            }
        }
    }
}

