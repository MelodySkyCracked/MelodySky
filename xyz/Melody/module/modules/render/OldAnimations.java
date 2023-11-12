/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockAir
 *  net.minecraft.potion.Potion
 */
package xyz.Melody.module.modules.render;

import net.minecraft.block.BlockAir;
import net.minecraft.potion.Potion;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class OldAnimations
extends Module {
    public Option oldRod = new Option("Rod", true);
    public Option oldBow = new Option("Bow", true);
    public Option oldEating = new Option("Eating", true);
    public Option oldModel = new Option("Model", true);
    public Option punching = new Option("Punching", true);
    public Option oldBlockhitting = new Option("BlockHit", true);
    public Numbers handX = new Numbers("HandX", 0.0, -1.0, 1.0, 0.1);
    public Numbers handY = new Numbers("HandY", 0.0, -1.0, 1.0, 0.1);
    public Numbers handZ = new Numbers("HandZ", 0.0, -1.0, 1.0, 0.1);
    private static OldAnimations INSTANCE;

    public OldAnimations() {
        super("OldAnimations", new String[]{"alc", "asc", "lobby"}, ModuleType.Render);
        this.addValues(this.oldBlockhitting, this.punching, this.oldModel, this.oldEating, this.oldBow, this.oldRod, this.handX, this.handY, this.handZ);
        this.setModInfo("1.7 Animations");
    }

    public static OldAnimations getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (OldAnimations)Client.instance.getModuleManager().getModuleByClass(OldAnimations.class);
        }
        return INSTANCE;
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (this.mc.field_71476_x == null) {
            return;
        }
        if (this.mc.field_71476_x.func_178782_a() == null) {
            return;
        }
        if ((this.mc.field_71439_g.func_70632_aY() || this.mc.field_71439_g.func_71039_bw()) && !(this.mc.field_71441_e.func_180495_p(this.mc.field_71476_x.func_178782_a()).func_177230_c() instanceof BlockAir)) {
            int n;
            int n2 = this.mc.field_71439_g.func_70644_a(Potion.field_76422_e) ? 6 - (1 + this.mc.field_71439_g.func_70660_b(Potion.field_76422_e).func_76458_c()) * 1 : (n = this.mc.field_71439_g.func_70644_a(Potion.field_76419_f) ? 6 + (1 + this.mc.field_71439_g.func_70660_b(Potion.field_76419_f).func_76458_c()) * 2 : 6);
            if (this.mc.field_71474_y.field_74312_F.func_151470_d() && (!this.mc.field_71439_g.field_82175_bq || this.mc.field_71439_g.field_110158_av >= n / 2 || this.mc.field_71439_g.field_110158_av < 0)) {
                this.mc.field_71439_g.field_110158_av = -1;
                this.mc.field_71439_g.field_82175_bq = true;
            }
        }
    }
}

