/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumChatFormatting
 */
package xyz.Melody.module.modules.QOL;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Option;
import xyz.Melody.GUI.Notification.NotificationPublisher;
import xyz.Melody.GUI.Notification.NotificationType;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class SapphireMiningPit
extends Module {
    private Option debug = new Option("Debug", false);
    private ArrayList blockPoss = new ArrayList();
    private Thread thread;
    private Thread scanThread0;
    private Thread scanThread1;
    private Thread scanThread2;
    private Thread scanThread3;
    private int ticks = 0;

    public SapphireMiningPit() {
        super("SapphirePitESP", new String[]{"spe"}, ModuleType.QOL);
        this.setEnabled(false);
        this.addValues(this.debug);
        this.setModInfo("Sapphire Mining Pit ESP.");
    }

    @Override
    public void onDisable() {
        Helper.sendMessage("[SapphirePitESP] Scanning Abort.");
        this.thread = null;
        this.blockPoss.clear();
        super.onDisable();
    }

    @EventHandler
    private void onUpdate(EventTick eventTick) {
        if (this.ticks < 10) {
            ++this.ticks;
            return;
        }
        if (Client.instance.sbArea.getCurrentArea() != Areas.Crystal_Hollows) {
            this.thread = null;
            this.blockPoss.clear();
            return;
        }
        if (this.mc.field_71441_e != null && this.mc.field_71439_g != null && this.thread == null) {
            this.thread = new Thread(this::lambda$onUpdate$4, "MelodySky-SapphireFinder Call");
            this.thread.start();
        }
        this.ticks = 0;
    }

    @EventHandler
    private void on3D(EventRender3D eventRender3D) {
        if (Client.instance.sbArea.getCurrentArea() != Areas.Crystal_Hollows) {
            return;
        }
        for (int i = 0; i < this.blockPoss.size(); ++i) {
            BlockPos blockPos = (BlockPos)this.blockPoss.get(i);
            Color color = new Color(Colors.BLUE.c);
            int n = new Color(color.getRed(), color.getGreen(), color.getBlue(), 200).getRGB();
            RenderUtil.drawSolidBlockESP(blockPos, n, eventRender3D.getPartialTicks());
            RenderUtil.trace(blockPos, Colors.BLUE.c);
        }
    }

    private void updateBlocks0() {
        if (Client.instance.sbArea.getCurrentArea() != Areas.Crystal_Hollows) {
            return;
        }
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return;
        }
        int n = 0;
        for (BlockPos blockPos : BlockPos.func_177980_a((BlockPos)new BlockPos(473, 31, 473), (BlockPos)new BlockPos(648, 188, 648))) {
            if (this.mc.field_71441_e == null || !this.isEnabled()) break;
            IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos);
            if (!this.isEnabled()) {
                this.blockPoss.clear();
                return;
            }
            ++n;
            try {
                if (!this.mc.field_71441_e.func_175667_e(blockPos) || iBlockState.func_177230_c() != Blocks.field_150334_T) continue;
                IBlockState iBlockState2 = this.mc.field_71441_e.func_180495_p(blockPos.func_177981_b(6));
                IBlockState iBlockState3 = this.mc.field_71441_e.func_180495_p(blockPos.func_177981_b(2));
                IBlockState iBlockState4 = this.mc.field_71441_e.func_180495_p(blockPos.func_177981_b(1));
                IBlockState iBlockState5 = this.mc.field_71441_e.func_180495_p(blockPos.func_177979_c(1));
                IBlockState iBlockState6 = this.mc.field_71441_e.func_180495_p(blockPos.func_177979_c(2));
                if (iBlockState2.func_177230_c() == Blocks.field_150333_U && iBlockState3.func_177230_c() == Blocks.field_150334_T && iBlockState4.func_177230_c() == Blocks.field_150333_U && iBlockState6.func_177230_c() == Blocks.field_150390_bg && iBlockState5.func_177230_c() == Blocks.field_150334_T) {
                    if (((Boolean)this.debug.getValue()).booleanValue()) {
                        Helper.sendMessage(blockPos + " " + EnumChatFormatting.GREEN + "TRUE");
                    }
                    if (this.blockPoss.contains(blockPos)) continue;
                    Helper.sendMessage("[SapphirePitESP] Sapphire Pit Found.");
                    Helper.sendMessage("[SapphirePitESP] Coords: " + EnumChatFormatting.GREEN + blockPos.func_177958_n() + ", " + blockPos.func_177956_o() + ", " + blockPos.func_177952_p());
                    NotificationPublisher.queue("Sapphire Pit ESP", "Coords: " + EnumChatFormatting.GREEN + blockPos.func_177958_n() + ", " + blockPos.func_177956_o() + ", " + blockPos.func_177952_p(), NotificationType.INFO, 7500);
                    this.blockPoss.add(blockPos);
                    continue;
                }
                if (!((Boolean)this.debug.getValue()).booleanValue()) continue;
                Helper.sendMessage(blockPos + " " + EnumChatFormatting.RED + "FALSE");
                Helper.sendMessage(iBlockState6.func_177230_c().getRegistryName());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (this.blockPoss.isEmpty() && ((Boolean)this.debug.getValue()).booleanValue()) {
            Helper.sendMessage("[SapphirePitESP] Scanned " + n + " Blocks.");
            Helper.sendMessage("[SapphirePitESP] " + this.blockPoss.size() + " Coords Possible.");
        }
    }

    private void updateBlocks1() {
        if (Client.instance.sbArea.getCurrentArea() != Areas.Crystal_Hollows) {
            return;
        }
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return;
        }
        int n = 0;
        for (BlockPos blockPos : BlockPos.func_177980_a((BlockPos)new BlockPos(648, 31, 648), (BlockPos)new BlockPos(823, 188, 823))) {
            if (this.mc.field_71441_e == null || !this.isEnabled()) break;
            IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos);
            if (!this.isEnabled()) {
                this.blockPoss.clear();
                return;
            }
            ++n;
            try {
                if (!this.mc.field_71441_e.func_175667_e(blockPos) || iBlockState.func_177230_c() != Blocks.field_150334_T) continue;
                IBlockState iBlockState2 = this.mc.field_71441_e.func_180495_p(blockPos.func_177981_b(6));
                IBlockState iBlockState3 = this.mc.field_71441_e.func_180495_p(blockPos.func_177981_b(2));
                IBlockState iBlockState4 = this.mc.field_71441_e.func_180495_p(blockPos.func_177981_b(1));
                IBlockState iBlockState5 = this.mc.field_71441_e.func_180495_p(blockPos.func_177979_c(1));
                IBlockState iBlockState6 = this.mc.field_71441_e.func_180495_p(blockPos.func_177979_c(2));
                if (iBlockState2.func_177230_c() == Blocks.field_150333_U && iBlockState3.func_177230_c() == Blocks.field_150334_T && iBlockState4.func_177230_c() == Blocks.field_150333_U && iBlockState6.func_177230_c() == Blocks.field_150390_bg && iBlockState5.func_177230_c() == Blocks.field_150334_T) {
                    if (((Boolean)this.debug.getValue()).booleanValue()) {
                        Helper.sendMessage(blockPos + " " + EnumChatFormatting.GREEN + "TRUE");
                    }
                    if (this.blockPoss.contains(blockPos)) continue;
                    Helper.sendMessage("[SapphirePitESP] Sapphire Pit Found.");
                    Helper.sendMessage("[SapphirePitESP] Coords: " + EnumChatFormatting.GREEN + blockPos.func_177958_n() + ", " + blockPos.func_177956_o() + ", " + blockPos.func_177952_p());
                    NotificationPublisher.queue("Sapphire Pit ESP", "Coords: " + EnumChatFormatting.GREEN + blockPos.func_177958_n() + ", " + blockPos.func_177956_o() + ", " + blockPos.func_177952_p(), NotificationType.INFO, 7500);
                    this.blockPoss.add(blockPos);
                    continue;
                }
                if (!((Boolean)this.debug.getValue()).booleanValue()) continue;
                Helper.sendMessage(blockPos + " " + EnumChatFormatting.RED + "FALSE");
                Helper.sendMessage(iBlockState6.func_177230_c().getRegistryName());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (this.blockPoss.isEmpty() && ((Boolean)this.debug.getValue()).booleanValue()) {
            Helper.sendMessage("[SapphirePitESP] Scanned " + n + " Blocks.");
            Helper.sendMessage("[SapphirePitESP] " + this.blockPoss.size() + " Coords Possible.");
        }
    }

    private void updateBlocks2() {
        if (Client.instance.sbArea.getCurrentArea() != Areas.Crystal_Hollows) {
            return;
        }
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return;
        }
        int n = 0;
        for (BlockPos blockPos : BlockPos.func_177980_a((BlockPos)new BlockPos(473, 31, 823), (BlockPos)new BlockPos(648, 188, 648))) {
            if (this.mc.field_71441_e == null || !this.isEnabled()) break;
            IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos);
            if (!this.isEnabled()) {
                this.blockPoss.clear();
                return;
            }
            ++n;
            try {
                if (!this.mc.field_71441_e.func_175667_e(blockPos) || iBlockState.func_177230_c() != Blocks.field_150334_T) continue;
                IBlockState iBlockState2 = this.mc.field_71441_e.func_180495_p(blockPos.func_177981_b(6));
                IBlockState iBlockState3 = this.mc.field_71441_e.func_180495_p(blockPos.func_177981_b(2));
                IBlockState iBlockState4 = this.mc.field_71441_e.func_180495_p(blockPos.func_177981_b(1));
                IBlockState iBlockState5 = this.mc.field_71441_e.func_180495_p(blockPos.func_177979_c(1));
                IBlockState iBlockState6 = this.mc.field_71441_e.func_180495_p(blockPos.func_177979_c(2));
                if (iBlockState2.func_177230_c() == Blocks.field_150333_U && iBlockState3.func_177230_c() == Blocks.field_150334_T && iBlockState4.func_177230_c() == Blocks.field_150333_U && iBlockState6.func_177230_c() == Blocks.field_150390_bg && iBlockState5.func_177230_c() == Blocks.field_150334_T) {
                    if (((Boolean)this.debug.getValue()).booleanValue()) {
                        Helper.sendMessage(blockPos + " " + EnumChatFormatting.GREEN + "TRUE");
                    }
                    if (this.blockPoss.contains(blockPos)) continue;
                    Helper.sendMessage("[SapphirePitESP] Sapphire Pit Found.");
                    Helper.sendMessage("[SapphirePitESP] Coords: " + EnumChatFormatting.GREEN + blockPos.func_177958_n() + ", " + blockPos.func_177956_o() + ", " + blockPos.func_177952_p());
                    NotificationPublisher.queue("Sapphire Pit ESP", "Coords: " + EnumChatFormatting.GREEN + blockPos.func_177958_n() + ", " + blockPos.func_177956_o() + ", " + blockPos.func_177952_p(), NotificationType.INFO, 7500);
                    this.blockPoss.add(blockPos);
                    continue;
                }
                if (!((Boolean)this.debug.getValue()).booleanValue()) continue;
                Helper.sendMessage(blockPos + " " + EnumChatFormatting.RED + "FALSE");
                Helper.sendMessage(iBlockState6.func_177230_c().getRegistryName());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (this.blockPoss.isEmpty() && ((Boolean)this.debug.getValue()).booleanValue()) {
            Helper.sendMessage("[SapphirePitESP] Scanned " + n + " Blocks.");
            Helper.sendMessage("[SapphirePitESP] " + this.blockPoss.size() + " Coords Possible.");
        }
    }

    private void updateBlocks3() {
        if (Client.instance.sbArea.getCurrentArea() != Areas.Crystal_Hollows) {
            return;
        }
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return;
        }
        int n = 0;
        for (BlockPos blockPos : BlockPos.func_177980_a((BlockPos)new BlockPos(648, 31, 648), (BlockPos)new BlockPos(823, 188, 473))) {
            if (this.mc.field_71441_e == null || !this.isEnabled()) break;
            IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos);
            if (!this.isEnabled()) {
                this.blockPoss.clear();
                return;
            }
            ++n;
            try {
                if (!this.mc.field_71441_e.func_175667_e(blockPos) || iBlockState.func_177230_c() != Blocks.field_150334_T) continue;
                IBlockState iBlockState2 = this.mc.field_71441_e.func_180495_p(blockPos.func_177981_b(6));
                IBlockState iBlockState3 = this.mc.field_71441_e.func_180495_p(blockPos.func_177981_b(2));
                IBlockState iBlockState4 = this.mc.field_71441_e.func_180495_p(blockPos.func_177981_b(1));
                IBlockState iBlockState5 = this.mc.field_71441_e.func_180495_p(blockPos.func_177979_c(1));
                IBlockState iBlockState6 = this.mc.field_71441_e.func_180495_p(blockPos.func_177979_c(2));
                if (iBlockState2.func_177230_c() == Blocks.field_150333_U && iBlockState3.func_177230_c() == Blocks.field_150334_T && iBlockState4.func_177230_c() == Blocks.field_150333_U && iBlockState6.func_177230_c() == Blocks.field_150390_bg && iBlockState5.func_177230_c() == Blocks.field_150334_T) {
                    if (((Boolean)this.debug.getValue()).booleanValue()) {
                        Helper.sendMessage(blockPos + " " + EnumChatFormatting.GREEN + "TRUE");
                    }
                    if (this.blockPoss.contains(blockPos)) continue;
                    Helper.sendMessage("[SapphirePitESP] Sapphire Pit Found.");
                    Helper.sendMessage("[SapphirePitESP] Coords: " + EnumChatFormatting.GREEN + blockPos.func_177958_n() + ", " + blockPos.func_177956_o() + ", " + blockPos.func_177952_p());
                    NotificationPublisher.queue("Sapphire Pit ESP", "Coords: " + EnumChatFormatting.GREEN + blockPos.func_177958_n() + ", " + blockPos.func_177956_o() + ", " + blockPos.func_177952_p(), NotificationType.INFO, 7500);
                    this.blockPoss.add(blockPos);
                    continue;
                }
                if (!((Boolean)this.debug.getValue()).booleanValue()) continue;
                Helper.sendMessage(blockPos + " " + EnumChatFormatting.RED + "FALSE");
                Helper.sendMessage(iBlockState6.func_177230_c().getRegistryName());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (this.blockPoss.isEmpty() && ((Boolean)this.debug.getValue()).booleanValue()) {
            Helper.sendMessage("[SapphirePitESP] Scanned " + n + " Blocks.");
            Helper.sendMessage("[SapphirePitESP] " + this.blockPoss.size() + " Coords Possible.");
        }
    }

    private void lambda$onUpdate$4() {
        Helper.sendMessage("[SapphirePitESP] This May Take Some Time.");
        if (!this.isEnabled() || Client.instance.sbArea.getCurrentArea() != Areas.Crystal_Hollows) {
            return;
        }
        if (this.mc.field_71441_e == null) {
            return;
        }
        this.scanThread0 = new Thread(this::lambda$null$0, "MS-SE-Scan 0");
        this.scanThread0.start();
        this.scanThread1 = new Thread(this::lambda$null$1, "MS-SE-Scan 1");
        this.scanThread1.start();
        this.scanThread2 = new Thread(this::lambda$null$2, "MS-SE-Scan 2");
        this.scanThread2.start();
        this.scanThread3 = new Thread(this::lambda$null$3, "MS-SE-Scan 3");
        this.scanThread3.start();
    }

    private void lambda$null$3() {
        while (this.mc.field_71441_e != null && this.isEnabled() && Client.instance.sbArea.getCurrentArea() == Areas.Crystal_Hollows) {
            this.updateBlocks3();
        }
    }

    private void lambda$null$2() {
        while (this.mc.field_71441_e != null && this.isEnabled() && Client.instance.sbArea.getCurrentArea() == Areas.Crystal_Hollows) {
            this.updateBlocks2();
        }
    }

    private void lambda$null$1() {
        while (this.mc.field_71441_e != null && this.isEnabled() && Client.instance.sbArea.getCurrentArea() == Areas.Crystal_Hollows) {
            this.updateBlocks1();
        }
    }

    private void lambda$null$0() {
        while (this.mc.field_71441_e != null && this.isEnabled() && Client.instance.sbArea.getCurrentArea() == Areas.Crystal_Hollows) {
            this.updateBlocks0();
        }
    }
}

