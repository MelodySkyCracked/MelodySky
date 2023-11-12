/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.ISound
 *  net.minecraft.client.audio.PositionedSoundRecord
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.common.MinecraftForge
 */
package xyz.Melody.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import xyz.Melody.Client;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.Event.value.Value;
import xyz.Melody.GUI.ClickGui.DickGui;
import xyz.Melody.GUI.Notification.NotificationPublisher;
import xyz.Melody.GUI.Notification.NotificationType;
import xyz.Melody.System.Managers.Client.ModuleManager;
import xyz.Melody.Utils.Helper;
import xyz.Melody.module.I;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.macros.Mining.AutoRuby;
import xyz.Melody.module.modules.macros.Mining.GemstoneNuker;
import xyz.Melody.module.modules.render.ClickGui;

public class Module {
    public String name;
    private String suffix;
    private int color;
    private String[] alias;
    private String modInfo;
    private boolean enabled;
    private int key;
    public List values;
    public ModuleType type;
    private boolean removed;
    public Minecraft mc = Minecraft.func_71410_x();
    public ScaledResolution mainWindow = new ScaledResolution(this.mc);
    public static Random random = new Random();
    public boolean shouldPlaySound = true;

    public Module(String string, String[] stringArray, ModuleType moduleType) {
        this.name = string;
        this.alias = stringArray;
        this.type = moduleType;
        this.suffix = "";
        this.key = 0;
        this.removed = false;
        this.enabled = false;
        this.values = new ArrayList();
        this.modInfo = "";
        this.shouldPlaySound = true;
    }

    public Module(String string, ModuleType moduleType) {
        this.name = string;
        this.alias = new String[0];
        this.type = moduleType;
        this.suffix = "";
        this.key = 0;
        this.removed = false;
        this.enabled = false;
        this.values = new ArrayList();
        this.modInfo = "";
        this.shouldPlaySound = true;
    }

    public String getName() {
        return this.name;
    }

    public String[] getAlias() {
        return this.alias;
    }

    public ModuleType getType() {
        return this.type;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean wasRemoved() {
        return this.removed;
    }

    public void setRemoved(boolean bl) {
        this.removed = bl;
    }

    public String getModInfo() {
        return this.modInfo;
    }

    public void setModInfo(String string) {
        this.modInfo = string;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(Object object) {
        String string = object.toString();
        this.suffix = string.isEmpty() ? string : String.format("\u00a77- \u00a7f%s\u00a77", EnumChatFormatting.GRAY + string);
    }

    public void preEnable(boolean bl) {
        ModuleManager.waittingHook.put(this, bl);
        this.setEnabled(bl, false);
    }

    public void setEnabled(boolean bl) {
        ModuleManager.waittingHook.remove(this);
        try {
            this.setEnabled(bl, true);
        }
        catch (Exception exception) {
            NotificationPublisher.queue("Exception Thrown!", exception.toString() + EnumChatFormatting.AQUA + " Check log for details.", NotificationType.ERROR, 5000);
            exception.printStackTrace();
        }
    }

    private void setEnabled(boolean bl, boolean bl2) {
        this.enabled = bl;
        if (bl) {
            if (this.name == "FreeCam" && this.mc.field_71439_g == null) {
                this.enabled = false;
                return;
            }
            if (this.name == "FreeCam" && !this.mc.field_71439_g.field_70122_E) {
                Helper.sendMessage("[WARNING] FreeCam can only be used on Ground.");
                this.enabled = false;
                return;
            }
            if (bl2) {
                this.onEnable();
            }
            EventBus.getInstance().register(this);
            this.regFML(this);
            if (DickGui.instance != null) {
                if (this.mc.field_71462_r instanceof DickGui && !(this instanceof ClickGui) && this.shouldPlaySound) {
                    this.mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a((ResourceLocation)new ResourceLocation("gui.button.press"), (float)1.0f));
                } else if (ModuleManager.loaded && this.getType() != ModuleType.QOL && this.getType() != ModuleType.Swapping && this.getName() != "ClickGui") {
                    if (this.getType() == ModuleType.Macros && !AutoRuby.getINSTANCE().isEnabled() && !(this instanceof GemstoneNuker)) {
                        Helper.sendMessage("[Macro] " + EnumChatFormatting.DARK_AQUA + this.getName() + EnumChatFormatting.GRAY + " Now" + EnumChatFormatting.GREEN + " Enabled" + EnumChatFormatting.GRAY + ".");
                    }
                    this.mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a((ResourceLocation)new ResourceLocation("gui.button.press"), (float)1.0f));
                    NotificationPublisher.queue("Module", this.getName() + " Enabled", NotificationType.INFO, 1000);
                }
            }
        } else {
            EventBus.getInstance().unregister(this);
            this.unregFML(this);
            if (DickGui.instance != null) {
                if (this.mc.field_71462_r instanceof DickGui && !(this instanceof ClickGui) && this.shouldPlaySound) {
                    this.mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a((ResourceLocation)new ResourceLocation("gui.button.press"), (float)0.8f));
                } else if (ModuleManager.loaded && this.getType() != ModuleType.QOL && this.getType() != ModuleType.Swapping && this.getName() != "ClickGui") {
                    if (this.getType() == ModuleType.Macros && !AutoRuby.getINSTANCE().isEnabled() && !(this instanceof GemstoneNuker)) {
                        Helper.sendMessage("[Macro] " + EnumChatFormatting.DARK_AQUA + this.getName() + EnumChatFormatting.GRAY + " Now" + EnumChatFormatting.RED + " Disabled" + EnumChatFormatting.GRAY + ".");
                    }
                    this.mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a((ResourceLocation)new ResourceLocation("gui.button.press"), (float)0.8f));
                    NotificationPublisher.queue("Module", this.getName() + " Disabled", NotificationType.INFO, 1000);
                }
            }
            if (bl2) {
                this.onDisable();
            }
        }
    }

    private void regFML(Object object) {
        MinecraftForge.EVENT_BUS.register(object);
    }

    private void unregFML(Object object) {
        MinecraftForge.EVENT_BUS.unregister(object);
    }

    public void setColor(int n) {
        this.color = n;
    }

    public int getColor() {
        return this.color;
    }

    protected void addValues(Value ... valueArray) {
        Value[] valueArray2 = valueArray;
        int n = valueArray.length;
        for (int i = 0; i < n; ++i) {
            Value value = valueArray2[i];
            this.values.add(value);
        }
    }

    public List getValues() {
        return this.values;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int n) {
        this.key = n;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    /*
     * Unable to fully structure code
     */
    public void makeCommand() {
        if (this.values.size() > 0) {
            var1_1 = "";
            var2_2 = "";
            for (Value var4_4 : this.values) {
                if (var4_4 instanceof Mode) continue;
                if (var1_1.isEmpty()) {
                    var1_1 = String.valueOf(var1_1) + var4_4.getName();
                    continue;
                }
                var1_1 = String.valueOf(var1_1) + String.format(", %s", new Object[]{var4_4.getName()});
            }
            var3_3 = this.values.iterator();
            block1: while (true) {
                if (!var3_3.hasNext()) {
                    Client.instance.getCommandManager().add(new I(this, this.name, this.alias, String.format("%s%s", new Object[]{var1_1.isEmpty() != false ? "" : String.format("%s,", new Object[]{var1_1}), var2_2.isEmpty() != false ? "" : String.format("%s", new Object[]{var2_2})}), "Setup this module"));
                    return;
                }
                var4_4 = (Value)var3_3.next();
                if (!(var4_4 instanceof Mode)) continue;
                var5_5 = (Mode)var4_4;
                var6_6 = var5_5.getModes();
                var7_7 = var6_6.length;
                var8_8 = 0;
                while (true) {
                    if (var8_8 < var7_7) ** break;
                    continue block1;
                    var9_9 = var6_6[var8_8];
                    var2_2 = var2_2.isEmpty() != false ? String.valueOf(var2_2) + var9_9.name().toLowerCase() : String.valueOf(var2_2) + String.format(", %s", new Object[]{var9_9.name().toLowerCase()});
                    ++var8_8;
                }
                break;
            }
        }
    }
}

