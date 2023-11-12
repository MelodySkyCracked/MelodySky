/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.S29PacketSoundEffect
 *  net.minecraft.network.play.server.S2APacketParticles
 *  net.minecraft.util.EnumParticleTypes
 */
package xyz.Melody.module.modules.render;

import java.awt.Color;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.EnumParticleTypes;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventPacketRecieve;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class HideImplosionParticle
extends Module {
    public HideImplosionParticle() {
        super("HideImplosions", new String[]{"implosionparticle"}, ModuleType.Render);
        this.setModInfo("Hide Implosion(Wither Impact) Particles.");
        this.setColor(new Color(244, 255, 149).getRGB());
    }

    @EventHandler
    private void onPacketRCV(EventPacketRecieve eventPacketRecieve) {
        S29PacketSoundEffect s29PacketSoundEffect;
        if (eventPacketRecieve.getPacket() instanceof S29PacketSoundEffect && (s29PacketSoundEffect = (S29PacketSoundEffect)eventPacketRecieve.getPacket()).func_149212_c().contains("random.explode") && s29PacketSoundEffect.func_149209_h() == 1.0f && s29PacketSoundEffect.func_149208_g() == 1.0f) {
            eventPacketRecieve.setCancelled(true);
        }
        if (eventPacketRecieve.getPacket() instanceof S2APacketParticles) {
            s29PacketSoundEffect = (S2APacketParticles)eventPacketRecieve.getPacket();
            boolean bl = s29PacketSoundEffect.func_179750_b();
            float f = s29PacketSoundEffect.func_149227_j();
            int n = s29PacketSoundEffect.func_149222_k();
            float f2 = s29PacketSoundEffect.func_149221_g();
            float f3 = s29PacketSoundEffect.func_149224_h();
            float f4 = s29PacketSoundEffect.func_149223_i();
            EnumParticleTypes enumParticleTypes = s29PacketSoundEffect.func_179749_a();
            if (enumParticleTypes == EnumParticleTypes.EXPLOSION_LARGE && bl && f == 8.0f && n == 8 && f2 == 0.0f && f3 == 0.0f && f4 == 0.0f) {
                eventPacketRecieve.setCancelled(true);
            }
        }
    }
}

