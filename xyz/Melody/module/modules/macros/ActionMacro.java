/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.macros;

import java.awt.TrayIcon;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.Player.EventPreUpdate;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.WindowsNotification;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class ActionMacro
extends Module {
    private BlockPos blockPos;
    private BlockPos lastBlockPos = null;
    private float currentDamage = 0.0f;
    private Option forward = new Option("W", false);
    private Option backward = new Option("S", false);
    private Option left = new Option("A", false);
    private Option right = new Option("D", false);
    private Option space = new Option("Jump", false);
    private Option sneak = new Option("Sneak", false);
    private Option lmb = new Option("Lmb", false);
    private Option rmb = new Option("Rmb", false);
    private Option rotationCheck = new Option("RotationCheck", true);
    private Numbers rotRange = new Numbers("RotationDiff", 0.01, 0.001, 180.0, 0.001);
    private Option yCheck = new Option("YCheck", true);
    private Numbers yRange = new Numbers("YRange", 5.0, 1.0, 100.0, 1.0);
    private Option disable = new Option("AutoDisable", true);
    private int ticks = 0;
    private float lastYaw = 0.0f;
    private float lastPitch = 0.0f;
    private double lastY = 0.0;

    public ActionMacro() {
        super("ActionMacro", new String[]{""}, ModuleType.Macros);
        this.addValues(this.forward, this.backward, this.left, this.right, this.space, this.lmb, this.rotationCheck, this.rotRange, this.yCheck, this.yRange, this.disable);
        this.setModInfo("Press Keyboard or Mouse Buttons.");
    }

    @EventHandler
    private void checks(EventTick eventTick) {
        if (this.ticks < 10) {
            ++this.ticks;
            return;
        }
        if (((Boolean)this.rotationCheck.getValue()).booleanValue() && ((double)Math.abs(this.mc.field_71439_g.field_70177_z - this.lastYaw) > 0.01 || (double)Math.abs(this.mc.field_71439_g.field_70125_A - this.lastPitch) > (Double)this.rotRange.getValue())) {
            WindowsNotification.show("MelodySky ActionMacro", "Detected Rotation Change.", TrayIcon.MessageType.WARNING);
            if (((Boolean)this.disable.getValue()).booleanValue()) {
                this.setEnabled(false);
            }
        }
        if (((Boolean)this.yCheck.getValue()).booleanValue() && Math.abs(this.mc.field_71439_g.field_70163_u - this.lastY) > (Double)this.yRange.getValue()) {
            WindowsNotification.show("MelodySky ActionMacro", "Detected Position Y Change.", TrayIcon.MessageType.WARNING);
            if (((Boolean)this.disable.getValue()).booleanValue()) {
                this.setEnabled(false);
            }
        }
        this.ticks = 0;
    }

    @EventHandler
    private void idk(EventPreUpdate eventPreUpdate) {
        if (((Boolean)this.forward.getValue()).booleanValue()) {
            KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74351_w.func_151463_i(), (boolean)true);
        }
        if (((Boolean)this.backward.getValue()).booleanValue()) {
            KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74368_y.func_151463_i(), (boolean)true);
        }
        if (((Boolean)this.left.getValue()).booleanValue()) {
            KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74370_x.func_151463_i(), (boolean)true);
        }
        if (((Boolean)this.right.getValue()).booleanValue()) {
            KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74366_z.func_151463_i(), (boolean)true);
        }
        if (((Boolean)this.space.getValue()).booleanValue()) {
            KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74314_A.func_151463_i(), (boolean)true);
        }
        if (((Boolean)this.sneak.getValue()).booleanValue()) {
            KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74311_E.func_151463_i(), (boolean)true);
        }
        if (((Boolean)this.lmb.getValue()).booleanValue()) {
            KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74312_F.func_151463_i(), (boolean)true);
        }
        if (((Boolean)this.rmb.getValue()).booleanValue()) {
            KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74313_G.func_151463_i(), (boolean)true);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        KeyBinding.func_74506_a();
        super.onDisable();
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        Helper.sendMessage("[MacroProtection] Auto Disabled " + EnumChatFormatting.GREEN + this.getName() + EnumChatFormatting.GRAY + " due to World Change.");
        KeyBinding.func_74506_a();
        this.setEnabled(false);
    }
}

