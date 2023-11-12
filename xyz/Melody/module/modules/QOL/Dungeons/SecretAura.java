/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C0DPacketCloseWindow
 *  net.minecraft.network.play.server.S02PacketChat
 *  net.minecraft.network.play.server.S2DPacketOpenWindow
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.Vec3
 *  net.minecraft.util.Vec3i
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.QOL.Dungeons;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.Player.EventPostUpdate;
import xyz.Melody.Event.events.Player.EventPreUpdate;
import xyz.Melody.Event.events.world.EventPacketRecieve;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Event.value.TextValue;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.Vec3d;
import xyz.Melody.Utils.math.Rotation;
import xyz.Melody.Utils.math.RotationUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class SecretAura
extends Module {
    private final String witherSkin = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRkYjRhZGZhOWJmNDhmZjVkNDE3MDdhZTM0ZWE3OGJkMjM3MTY1OWZjZDhjZDg5MzQ3NDlhZjRjY2U5YiJ9fX0=";
    private Thread calculationThread;
    private BlockPos currentBlock;
    private Numbers reach = new Numbers("Range", 5.0, 2.0, 6.0, 0.5);
    private TextValue item = new TextValue("Item", (Object)"");
    private Option cancelChest = new Option("Cancel Chests", false);
    private Option clickedCheck = new Option("Clicked Check", false);
    private Option rotation = new Option("Remove", true);
    public static ArrayList clicked = new ArrayList();
    public static boolean inBoss;

    public SecretAura() {
        super("SecretAura", new String[0], ModuleType.Dungeons);
        this.addValues(this.reach, this.item, this.cancelChest, this.clickedCheck, this.rotation);
        this.setModInfo("Auto Get Secrets.");
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (this.calculationThread == null || !this.calculationThread.isAlive()) {
            this.calculationThread = new Thread(this::lambda$onTick$0, "SecretAura");
            this.calculationThread.start();
        }
    }

    @EventHandler
    public void onPreUpdate(EventPreUpdate eventPreUpdate) {
        if (this.currentBlock != null && this.mc.field_71439_g != null && Client.inDungeons && ((Boolean)this.rotation.getValue()).booleanValue()) {
            Rotation rotation = RotationUtil.vec3ToRotation(new Vec3d((double)this.currentBlock.func_177958_n() + 0.5, (double)this.currentBlock.func_177956_o() + 0.5, (double)this.currentBlock.func_177952_p() + 0.5));
            eventPreUpdate.setYaw(rotation.getYaw());
            eventPreUpdate.setPitch(rotation.getPitch());
        }
    }

    @EventHandler
    public void onPostUpdate(EventPostUpdate eventPostUpdate) {
        if (this.currentBlock != null && this.mc.field_71439_g != null && Client.inDungeons) {
            this.interactWithBlock(this.currentBlock);
            this.currentBlock = null;
        }
    }

    private void interactWithBlock(BlockPos blockPos) {
        for (int i = 0; i < 9; ++i) {
            if (this.mc.field_71439_g.field_71071_by.func_70301_a(i) == null || !this.mc.field_71439_g.field_71071_by.func_70301_a(i).func_82833_r().toLowerCase().contains(((String)this.item.getValue()).toLowerCase())) continue;
            int n = this.mc.field_71439_g.field_71071_by.field_70461_c;
            this.mc.field_71439_g.field_71071_by.field_70461_c = i;
            if (this.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150442_at && !inBoss) {
                this.mc.field_71442_b.func_178890_a(this.mc.field_71439_g, this.mc.field_71441_e, this.mc.field_71439_g.field_71071_by.func_70448_g(), blockPos, EnumFacing.func_176733_a((double)this.mc.field_71439_g.field_70177_z), new Vec3(0.0, 0.0, 0.0));
            }
            this.mc.field_71442_b.func_178890_a(this.mc.field_71439_g, this.mc.field_71441_e, this.mc.field_71439_g.field_71071_by.func_70448_g(), blockPos, EnumFacing.func_176733_a((double)this.mc.field_71439_g.field_70177_z), new Vec3(0.0, 0.0, 0.0));
            this.mc.field_71439_g.field_71071_by.field_70461_c = n;
            clicked.add(blockPos);
            return;
        }
        Helper.sendMessage("Missing required item: \"" + (String)this.item.getValue() + "\" in your hotbar!");
    }

    @EventHandler
    public void onPacket(EventPacketRecieve eventPacketRecieve) {
        if (!Client.inDungeons) {
            return;
        }
        if (eventPacketRecieve.getPacket() instanceof S2DPacketOpenWindow && ((Boolean)this.cancelChest.getValue()).booleanValue()) {
            S2DPacketOpenWindow s2DPacketOpenWindow = (S2DPacketOpenWindow)eventPacketRecieve.getPacket();
            if (ChatFormatting.stripFormatting((String)s2DPacketOpenWindow.func_179840_c().func_150254_d()).equals("Chest")) {
                eventPacketRecieve.setCancelled(true);
                this.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0DPacketCloseWindow(s2DPacketOpenWindow.func_148901_c()));
            }
        } else if (eventPacketRecieve.getPacket() instanceof S02PacketChat && ChatFormatting.stripFormatting((String)((S02PacketChat)eventPacketRecieve.getPacket()).func_148915_c().func_150254_d()).startsWith("[BOSS] Necron")) {
            inBoss = true;
        }
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        inBoss = false;
        clicked.clear();
    }

    private void lambda$onTick$0() {
        while (this.isEnabled()) {
            try {
                Thread.sleep(25L);
                if (this.currentBlock != null) continue;
                Vec3i vec3i = new Vec3i(6, 6, 6);
                BlockPos blockPos = this.mc.field_71439_g.func_180425_c();
                for (BlockPos blockPos2 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i))) {
                    if (this != blockPos2 || !(this.mc.field_71439_g.func_70011_f((double)blockPos2.func_177958_n(), (double)((float)blockPos2.func_177956_o() - this.mc.field_71439_g.func_70047_e()), (double)blockPos2.func_177952_p()) < (Double)this.reach.getValue())) continue;
                    this.currentBlock = blockPos2;
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}

