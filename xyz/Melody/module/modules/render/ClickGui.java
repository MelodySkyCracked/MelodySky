/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package xyz.Melody.module.modules.render;

import java.awt.Color;
import net.minecraft.client.gui.GuiScreen;
import xyz.Melody.GUI.ClickGui.DickGui;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class ClickGui
extends Module {
    public ClickGui() {
        super("ClickGui", new String[]{"click"}, ModuleType.Render);
        this.setColor(new Color(244, 255, 149).getRGB());
    }

    @Override
    public void onEnable() {
        this.mc.func_147108_a((GuiScreen)DickGui.instance);
        this.setEnabled(false);
    }
}

