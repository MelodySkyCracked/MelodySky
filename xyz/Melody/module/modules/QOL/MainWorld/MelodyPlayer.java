/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C0EPacketClickWindow
 */
package xyz.Melody.module.modules.QOL.MainWorld;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class MelodyPlayer
extends Module {
    private long lastInteractTime;
    public boolean click;
    private String harpTag;
    private Numbers delay = new Numbers("Delay", 100.0, 0.0, 300.0, 10.0);

    public MelodyPlayer() {
        super("AutoMelody", new String[]{"am"}, ModuleType.QOL);
        this.addValues(this.delay);
        this.setModInfo("Auto Play Melody.");
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (this.mc.field_71462_r != null && this.mc.field_71462_r instanceof GuiChest && Client.inSkyblock && this.getGuiName(this.mc.field_71462_r).startsWith("Harp -")) {
            int n;
            ContainerChest containerChest = (ContainerChest)this.mc.field_71439_g.field_71070_bA;
            IInventory iInventory = containerChest.func_85151_d();
            if (!this.click) {
                StringBuilder stringBuilder = new StringBuilder();
                for (n = 1; n <= 34; ++n) {
                    if (iInventory.func_70301_a(n) == null) continue;
                    stringBuilder.append(iInventory.func_70301_a(n).func_77973_b());
                }
                if (!stringBuilder.toString().equals(this.harpTag)) {
                    this.harpTag = stringBuilder.toString();
                    this.lastInteractTime = 0L;
                    this.click = true;
                }
            }
            if (this.click) {
                if (this.lastInteractTime == 0L) {
                    this.lastInteractTime = System.currentTimeMillis();
                    return;
                }
                if ((double)(System.currentTimeMillis() - this.lastInteractTime) >= (Double)this.delay.getValue()) {
                    int n2 = -1;
                    for (n = 28; n <= 34; ++n) {
                        if (iInventory.func_70301_a(n) == null || iInventory.func_70301_a(n).func_77973_b() != Item.func_150898_a((Block)Blocks.field_150325_L)) continue;
                        n2 = n + 9;
                        break;
                    }
                    if (n2 == -1) {
                        this.lastInteractTime = 0L;
                        this.click = false;
                        return;
                    }
                    this.mc.field_71442_b.func_78753_a(this.mc.field_71439_g.field_71070_bA.field_75152_c, n2, 2, 3, (EntityPlayer)this.mc.field_71439_g);
                    this.lastInteractTime = 0L;
                    this.click = false;
                }
            }
        }
    }

    private void windowClick(int n, Slot slot, int n2, int n3) {
        short s = this.mc.field_71439_g.field_71070_bA.func_75136_a(this.mc.field_71439_g.field_71071_by);
        ItemStack itemStack = slot.func_75211_c();
        this.mc.func_147114_u().func_147297_a((Packet)new C0EPacketClickWindow(n, slot.field_75222_d, n2, n3, itemStack, s));
    }

    public String getGuiName(GuiScreen guiScreen) {
        if (guiScreen instanceof GuiChest) {
            return ((ContainerChest)((GuiChest)guiScreen).field_147002_h).func_85151_d().func_145748_c_().func_150260_c();
        }
        return "";
    }

    public String getInventoryName() {
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return "null";
        }
        return ((Slot)this.mc.field_71439_g.field_71070_bA.field_75151_b.get((int)0)).field_75224_c.func_70005_c_();
    }
}

