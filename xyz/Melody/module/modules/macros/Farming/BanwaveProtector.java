/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 */
package xyz.Melody.module.modules.macros.Farming;

import java.util.ArrayList;
import net.minecraft.client.gui.ScaledResolution;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.GUI.CustomUI.Functions.BanwaveChecker;
import xyz.Melody.GUI.CustomUI.HUDManager;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.WindowsNotification;
import xyz.Melody.Utils.pathfinding.PathPerformer;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.macros.Farming.CropNuker;
import xyz.Melody.module.modules.macros.Farming.LegitCropNuker;
import xyz.Melody.module.modules.others.AutoWalk;

public class BanwaveProtector
extends Module {
    public boolean banwave = false;
    private ArrayList mods = new ArrayList();
    private Numbers max = new Numbers("MaxBan", 30.0, 15.0, 100.0, 1.0);
    private Numbers min = new Numbers("MinBan", 4.0, 0.0, 15.0, 1.0);

    public BanwaveProtector() {
        super("BanwaveProtector", ModuleType.Macros);
        this.addValues(this.max, this.min);
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        boolean bl;
        if (!Client.instance.sbArea.isIn(Areas.Garden)) {
            return;
        }
        BanwaveChecker banwaveChecker = (BanwaveChecker)HUDManager.getApiByName("banwave");
        int n = 0;
        if (!banwaveChecker.getStaffBans().equals("non") && banwaveChecker.getStaffBans() != null) {
            n = Integer.parseInt(banwaveChecker.getStaffBans());
        }
        boolean bl2 = bl = (double)n < (Double)this.min.getValue() || (double)n > (Double)this.max.getValue();
        if (!this.banwave && bl) {
            this.banwave = true;
            this.mods.clear();
            if (this.getModule(AutoWalk.class).isEnabled()) {
                this.mods.add(this.getModule(AutoWalk.class));
            }
            if (this.getModule(CropNuker.class).isEnabled()) {
                this.mods.add(this.getModule(CropNuker.class));
            }
            if (this.getModule(LegitCropNuker.class).isEnabled()) {
                this.mods.add(this.getModule(LegitCropNuker.class));
            }
            for (Module module : this.mods) {
                module.setEnabled(false);
            }
            WindowsNotification.show("Banwave Protection", "Disabled All Nukers and AutoWalk.");
        } else if (this.banwave && !bl) {
            this.banwave = false;
            for (Module module : this.mods) {
                module.setEnabled(true);
                if (!(module instanceof AutoWalk)) continue;
                PathPerformer.instance.revive();
            }
            WindowsNotification.show("Banwave Protection", "Re-enabled Nukers and AutoWalk.");
            this.mods.clear();
        }
    }

    @EventHandler
    private void on2D(EventRender2D eventRender2D) {
        if (!this.banwave) {
            return;
        }
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        FontLoaders.CNMD35.drawCenteredString("BANWAVE", scaledResolution.func_78326_a() / 2, scaledResolution.func_78328_b() / 2 - 50, Colors.RED.c);
    }

    private Module getModule(Class clazz) {
        return Client.instance.getModuleManager().getModuleByClass(clazz);
    }
}

