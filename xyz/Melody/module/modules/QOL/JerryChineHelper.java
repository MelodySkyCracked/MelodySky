/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.S12PacketEntityVelocity
 */
package xyz.Melody.module.modules.QOL;

import java.awt.Color;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventPacketRecieve;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.injection.mixins.packets.S12Accessor;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class JerryChineHelper
extends Module {
    private TimerUtil timer = new TimerUtil();

    public JerryChineHelper() {
        super("JerryChineHelper", new String[]{"jch"}, ModuleType.QOL);
        this.setColor(new Color(158, 205, 125).getRGB());
        this.setModInfo("Help You Using JyrreChineGun.");
    }

    @EventHandler
    private void tick(EventTick eventTick) {
        if (this.mc.field_71474_y.field_74313_G.func_151470_d()) {
            this.timer.reset();
        }
    }

    @EventHandler
    private void OP(EventPacketRecieve eventPacketRecieve) {
        S12PacketEntityVelocity s12PacketEntityVelocity;
        if (this.timer.hasReached(450.0)) {
            return;
        }
        if (eventPacketRecieve.getPacket() instanceof S12PacketEntityVelocity && (s12PacketEntityVelocity = (S12PacketEntityVelocity)eventPacketRecieve.getPacket()).func_149412_c() == this.mc.field_71439_g.func_145782_y() && this != null) {
            S12PacketEntityVelocity s12PacketEntityVelocity2 = (S12PacketEntityVelocity)eventPacketRecieve.getPacket();
            ((S12Accessor)s12PacketEntityVelocity2).setMotionX(0);
            ((S12Accessor)s12PacketEntityVelocity2).setMotionZ(0);
        }
    }
}

