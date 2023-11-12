/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C0EPacketClickWindow
 */
package xyz.Melody.module.modules.macros.Mining;

import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.container.DrawSlotEvent;
import xyz.Melody.Event.events.misc.EventClickSlot;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.GUI.Notification.NotificationPublisher;
import xyz.Melody.GUI.Notification.NotificationType;
import xyz.Melody.Utils.render.ColorUtils;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class SackClicker
extends Module {
    private boolean started = false;
    private int slotId = -1;
    private TimerUtil clickTimer = new TimerUtil();

    public SackClicker() {
        super("SackClicker", ModuleType.Macros);
        this.setModInfo("Helps you clicking in sacks.");
    }

    @EventHandler
    private void onClickSlot(EventClickSlot eventClickSlot) {
        if (this != false) {
            return;
        }
        if (eventClickSlot.getSlotNumber() != this.slotId) {
            this.started = false;
            this.slotId = eventClickSlot.getSlotNumber();
            NotificationPublisher.queue("SackClicker", "Click again to start clicking.", NotificationType.INFO, 1500);
        } else {
            this.started = !this.started;
            this.clickTimer.reset();
        }
    }

    @EventHandler
    private void drawSlot(DrawSlotEvent drawSlotEvent) {
        if (drawSlotEvent.slot.field_75222_d != this.slotId) {
            return;
        }
        int n = this.started ? ColorUtils.addAlpha(Color.RED, 190).getRGB() : ColorUtils.addAlpha(Color.GREEN, 190).getRGB();
        int n2 = drawSlotEvent.slot.field_75223_e;
        int n3 = drawSlotEvent.slot.field_75221_f;
        Gui.func_73734_a((int)n2, (int)n3, (int)(n2 + 16), (int)(n3 + 16), (int)n);
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (this != false) {
            this.started = false;
            return;
        }
        if (!this.started || this.slotId == -1) {
            return;
        }
        if (this.clickTimer.hasReached(250.0) && this.slotId >= 0) {
            Slot slot = this.mc.field_71439_g.field_71070_bA.func_75139_a(this.slotId);
            this.leftClickSlot(slot);
            this.clickTimer.reset();
        }
    }

    private void leftClickSlot(Slot slot) {
        short s = this.mc.field_71439_g.field_71070_bA.func_75136_a(this.mc.field_71439_g.field_71071_by);
        ItemStack itemStack = slot.func_75211_c();
        int n = this.mc.field_71439_g.field_71070_bA.field_75152_c;
        this.mc.func_147114_u().func_147297_a((Packet)new C0EPacketClickWindow(n, slot.field_75222_d, 0, 0, itemStack, s));
    }
}

