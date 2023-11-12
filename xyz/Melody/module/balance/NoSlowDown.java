/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.EnumAction
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemBucketMilk
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C03PacketPlayer
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
 *  net.minecraft.network.play.client.C09PacketHeldItemChange
 *  net.minecraft.network.play.client.C0APacketAnimation
 *  net.minecraft.network.play.client.C0BPacketEntityAction
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 */
package xyz.Melody.module.balance;

import java.util.LinkedList;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.Player.EventPostUpdate;
import xyz.Melody.Event.events.Player.EventPreUpdate;
import xyz.Melody.Event.events.Player.EventUpdate;
import xyz.Melody.Event.events.world.EventPacketSend;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.System.PacketHandler;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class NoSlowDown
extends Module {
    private Numbers delay = new Numbers("PacketDelay", 13.0, 1.0, 20.0, 1.0);
    public TimerUtil timer = new TimerUtil();
    public LinkedList packets = new LinkedList();
    public boolean C09;
    public boolean f1052;
    public boolean f1053;
    public boolean f1054;
    public boolean f1055;
    public boolean f1056;
    private static NoSlowDown INSTANCE;

    public NoSlowDown() {
        super("NoSlowDown", new String[]{"noslow", "nsd", "noslowdown"}, ModuleType.Balance);
        this.addValues(this.delay);
        this.setModInfo("No Slow Down When Using Item.");
    }

    public static NoSlowDown getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (NoSlowDown)Client.instance.getModuleManager().getModuleByClass(NoSlowDown.class);
        }
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        this.f1055 = false;
        this.f1054 = false;
        this.timer.reset();
        this.packets.clear();
        this.f1052 = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.f1054 = false;
        this.f1055 = false;
        this.timer.reset();
        this.packets.clear();
        this.f1052 = false;
        super.onDisable();
    }

    @EventHandler
    public void onPacketSend(EventPacketSend eventPacketSend) {
        Packet packet = eventPacketSend.getPacket();
        if (this.f1052) {
            if ((packet instanceof C07PacketPlayerDigging || packet instanceof C08PacketPlayerBlockPlacement) && this == false) {
                eventPacketSend.setCancelled(true);
            } else if (packet instanceof C03PacketPlayer || packet instanceof C0BPacketEntityAction || packet instanceof C07PacketPlayerDigging || packet instanceof C08PacketPlayerBlockPlacement) {
                this.packets.add(packet);
                eventPacketSend.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void m2061(EventUpdate eventUpdate) {
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return;
        }
        if (this.f1053 || this == false) {
            if (this.timer.hasReached(10.0 * (Double)this.delay.getValue()) && this.f1052) {
                this.f1052 = false;
                PacketHandler.sendPacketNoEvent((Packet)new C09PacketHeldItemChange(this.mc.field_71439_g.field_71071_by.field_70461_c % 8 + 1));
                PacketHandler.sendPacketNoEvent((Packet)new C09PacketHeldItemChange(this.mc.field_71439_g.field_71071_by.field_70461_c));
                if (!this.packets.isEmpty()) {
                    boolean bl = false;
                    for (Packet packet : this.packets) {
                        if (packet instanceof C03PacketPlayer) {
                            bl = true;
                        }
                        if ((packet instanceof C02PacketUseEntity || packet instanceof C0APacketAnimation) && !bl) continue;
                        PacketHandler.sendPacketNoEvent(packet);
                    }
                    this.packets.clear();
                }
            }
            if (!this.f1052) {
                this.f1053 = this.isBlockingWithSword();
                if (this == false) {
                    return;
                }
                this.f1052 = true;
                PacketHandler.sendPacketNoEvent((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, this.mc.field_71439_g.field_71071_by.func_70448_g(), Float.intBitsToFloat(0), Float.intBitsToFloat(0), Float.intBitsToFloat(0)));
                this.timer.reset();
            }
        }
    }

    @EventHandler
    public void m2715(EventPreUpdate eventPreUpdate) {
        if (this == false) {
            PacketHandler.sendPacket((Packet)new C09PacketHeldItemChange(this.mc.field_71439_g.field_71071_by.field_70461_c % 8 + 1));
            PacketHandler.sendPacket((Packet)new C09PacketHeldItemChange(this.mc.field_71439_g.field_71071_by.field_70461_c));
        }
    }

    @EventHandler
    public void m2062(EventPostUpdate eventPostUpdate) {
        if (this == false) {
            this.C09 = false;
        } else if (!this.C09) {
            PacketHandler.sendPacket((Packet)new C09PacketHeldItemChange(this.mc.field_71439_g.field_71071_by.field_70461_c % 8 + 1));
            PacketHandler.sendPacket((Packet)new C09PacketHeldItemChange(this.mc.field_71439_g.field_71071_by.field_70461_c));
            this.C09 = true;
        }
    }

    private boolean isFood() {
        return this.mc.field_71439_g.func_71039_bw() && (this.mc.field_71439_g.func_71011_bu().func_77973_b().func_77661_b(this.mc.field_71439_g.func_71011_bu()) == EnumAction.EAT || this.mc.field_71439_g.func_71011_bu().func_77973_b().func_77661_b(this.mc.field_71439_g.func_71011_bu()) == EnumAction.DRINK);
    }

    public boolean checkItem(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        if (itemStack.func_77973_b() instanceof ItemSword) {
            return false;
        }
        if (itemStack.func_77973_b() instanceof ItemBow) {
            return false;
        }
        return itemStack.func_77973_b() instanceof ItemFood || itemStack.func_77973_b() instanceof ItemPotion || itemStack.func_77973_b() instanceof ItemBucketMilk;
    }

    public Vec3 getVectorForRotation(float f, float f2) {
        float f3 = MathHelper.func_76134_b((float)((float)(Math.toRadians(-f2) - 3.1415927410125732)));
        float f4 = MathHelper.func_76126_a((float)((float)(Math.toRadians(-f2) - 3.1415927410125732)));
        float f5 = -MathHelper.func_76134_b((float)((float)Math.toRadians(-f)));
        return new Vec3((double)(f4 * f5), (double)MathHelper.func_76126_a((float)((float)Math.toRadians(-f))), (double)(f3 * f5));
    }

    public Vec3 getPlayerVec() {
        return new Vec3(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u + (double)this.mc.field_71439_g.func_70047_e(), this.mc.field_71439_g.field_70161_v);
    }

    public MovingObjectPosition getCrosshair() {
        Vec3 vec3 = this.getPlayerVec();
        Vec3 vec32 = this.getVectorForRotation(90.0f, this.mc.field_71439_g.field_70177_z);
        return this.mc.field_71439_g.field_70170_p.func_147447_a(vec3, vec3.func_72441_c(vec32.field_72450_a * 4.5, vec32.field_72448_b * 4.5, vec32.field_72449_c * 4.5), false, false, false);
    }

    public boolean shouldSlow() {
        return this.mc.field_71439_g.func_70694_bm() == null || !(this.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword);
    }
}

