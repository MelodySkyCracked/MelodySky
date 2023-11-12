/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C02PacketUseEntity$Action
 *  org.lwjgl.input.Mouse
 */
package xyz.Melody.module.balance;

import java.awt.Color;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import org.lwjgl.input.Mouse;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class AutoClicker
extends Module {
    private TimerUtil timer = new TimerUtil();
    private Option left = new Option("LMB", true);
    private Option right = new Option("RMB", false);
    private Numbers lcps = new Numbers("LCps", 14.0, 1.0, 20.0, 0.1);
    private Numbers rcps = new Numbers("RCps", 14.0, 1.0, 20.0, 0.1);

    public AutoClicker() {
        super("AutoClicker", new String[]{"ac"}, ModuleType.Balance);
        this.addValues(this.left, this.right, this.lcps, this.rcps);
        this.setColor(new Color(244, 255, 149).getRGB());
    }

    @Override
    public void onEnable() {
        this.timer.reset();
    }

    @EventHandler
    private void onLMB(EventTick eventTick) {
        if (this.mc.field_71476_x == null) {
            return;
        }
        if (this.mc.field_71476_x.field_72308_g == null && this.mc.field_71441_e.func_180495_p(this.mc.field_71476_x.func_178782_a()).func_177230_c() != Blocks.field_150350_a.func_176223_P().func_177230_c() && this.mc.field_71441_e.func_180495_p(this.mc.field_71476_x.func_178782_a()).func_177230_c() != Blocks.field_150358_i.func_176223_P().func_177230_c() && this.mc.field_71441_e.func_180495_p(this.mc.field_71476_x.func_178782_a()).func_177230_c() != Blocks.field_150355_j.func_176223_P().func_177230_c() && this.mc.field_71441_e.func_180495_p(this.mc.field_71476_x.func_178782_a()).func_177230_c() != Blocks.field_150356_k.func_176223_P().func_177230_c() && this.mc.field_71441_e.func_180495_p(this.mc.field_71476_x.func_178782_a()).func_177230_c() != Blocks.field_150353_l.func_176223_P().func_177230_c()) {
            return;
        }
        float f = 1000.0f / ((Double)this.lcps.getValue()).floatValue();
        if (Mouse.isButtonDown((int)0) && this.timer.delay(f) && this.mc.field_71462_r == null && ((Boolean)this.left.getValue()).booleanValue()) {
            this.mc.field_71439_g.func_71038_i();
            if (this.mc.field_71476_x.field_72308_g != null) {
                this.mc.func_147114_u().func_147297_a((Packet)new C02PacketUseEntity(this.mc.field_71476_x.field_72308_g, C02PacketUseEntity.Action.ATTACK));
            }
            this.timer.reset();
        }
    }

    @EventHandler
    private void onRMB(EventTick eventTick) {
        float f = 1000.0f / ((Double)this.rcps.getValue()).floatValue();
        if (Mouse.isButtonDown((int)1) && this.timer.delay(f) && this.mc.field_71462_r == null && ((Boolean)this.right.getValue()).booleanValue()) {
            Client.rightClick();
            this.timer.reset();
        }
    }

    @Override
    public void onDisable() {
        this.timer.reset();
    }
}

