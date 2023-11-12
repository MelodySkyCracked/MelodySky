/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.GUI.ClickGui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xyz.Melody.Client;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Event.value.TextValue;
import xyz.Melody.Event.value.Value;
import xyz.Melody.GUI.Animate.NonlinearOpacity;
import xyz.Melody.GUI.ClickGui.ModBlock;
import xyz.Melody.GUI.ClickGui.Other.ChatTriggersGui;
import xyz.Melody.GUI.ClickGui.Other.FriendGui;
import xyz.Melody.GUI.ClickGui.Other.TextReplacementsGui;
import xyz.Melody.GUI.ClickGui.Utils.CGRender;
import xyz.Melody.GUI.ClickGui.Utils.Elements.Element;
import xyz.Melody.GUI.ClickGui.Utils.Elements.ModeEle;
import xyz.Melody.GUI.ClickGui.Utils.Elements.NumbersEle;
import xyz.Melody.GUI.ClickGui.Utils.Elements.OptionEle;
import xyz.Melody.GUI.ClickGui.Utils.Elements.TextEle;
import xyz.Melody.GUI.ClickGui.Utils.TextField;
import xyz.Melody.GUI.ClickGui.ValueElement;
import xyz.Melody.GUI.ClientButton;
import xyz.Melody.GUI.CustomUI.HUDScreen;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.GUI.Menu.MainMenu;
import xyz.Melody.GUI.Notification.NotificationPublisher;
import xyz.Melody.GUI.Notification.NotificationType;
import xyz.Melody.GUI.ShaderBG.Shaders.BackgroundShader;
import xyz.Melody.System.Managers.Client.ModuleManager;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.animate.Translate;
import xyz.Melody.Utils.render.ColorUtils;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.render.Scissor;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class DickGui
extends GuiScreen {
    public static DickGui instance;
    private double lastScreenWidth = 0.0;
    private double lastScreenHeight = 0.0;
    private TextField searchField;
    private String searchText = "";
    private int halfX = 0;
    private int halfY = 0;
    private int xStart = 0;
    private int yStart = 0;
    private int xEnd = 0;
    private int yEnd = 0;
    private boolean dragging = false;
    private int dxStart = 0;
    private int dyStart = 0;
    private int dxEnd = 0;
    private int dyEnd = 0;
    private int dragX = 0;
    private int dragY = 0;
    private Translate translate = new Translate(0.0f, 0.0f);
    private ModuleType currentType = ModuleType.values()[0];
    private Module currentModule = null;
    private Module bindingModule = null;
    private HashMap moduleMap = new HashMap();
    private ArrayList modBlocks = new ArrayList();
    public int mouseWheel = 0;
    private int mouseTypeScrollY = 0;
    private int mouseModuleScrollY = 0;
    private int mouseValueScrollY = 0;
    private boolean shouldValueOpen = false;
    private float valueStartX = 0.0f;
    private NonlinearOpacity valueTranslator = new NonlinearOpacity(0);
    private NonlinearOpacity typeApYTranslator = new NonlinearOpacity(0);
    private NonlinearOpacity ModApYTranslator = new NonlinearOpacity(0);
    private NonlinearOpacity ValApYTranslator = new NonlinearOpacity(0);
    private float typeApY = 0.0f;
    private float ModApY = 0.0f;
    private float ValApY = 0.0f;
    public clickType curType = clickType.ClickGui;
    private FriendGui friendGui = new FriendGui(this);
    private ChatTriggersGui chatTriggersGui = new ChatTriggersGui(this);
    private TextReplacementsGui textReplacementsGui = new TextReplacementsGui(this);

    public void init() {
        instance = this;
    }

    public DickGui() {
        this.moduleMap.clear();
        this.modBlocks.clear();
        for (Module module : ModuleManager.getModules()) {
            ValueElement valueElement = new ValueElement(module);
            for (Value value : module.getValues()) {
                Element element = null;
                if (value instanceof Option) {
                    element = new OptionEle((Option)value);
                } else if (value instanceof Numbers) {
                    element = new NumbersEle((Numbers)value);
                } else if (value instanceof Mode) {
                    element = new ModeEle((Mode)value);
                } else if (value instanceof TextValue) {
                    element = new TextEle((TextValue)value);
                }
                if (element == null) continue;
                valueElement.addValueElement(value, element);
            }
            this.moduleMap.put(module, valueElement);
            this.modBlocks.add(new ModBlock(module));
        }
    }

    public void func_73866_w_() {
        if (this.lastScreenHeight != (double)this.field_146295_m || this.lastScreenWidth != (double)this.field_146294_l) {
            this.halfX = (int)((float)this.field_146294_l / 3.1f);
            this.halfY = (int)((float)this.field_146295_m / 2.72f);
            this.halfX = this.halfX < 270 ? 270 : this.halfX;
            this.halfY = this.halfY < 140 ? 140 : this.halfY;
            this.xStart = this.field_146294_l / 2 - this.halfX;
            this.yStart = this.field_146295_m / 2 - this.halfY;
            this.xEnd = this.field_146294_l / 2 + this.halfX;
            this.yEnd = this.field_146295_m / 2 + this.halfY;
            this.lastScreenHeight = this.field_146295_m;
            this.lastScreenWidth = this.field_146294_l;
        }
        this.searchText = "";
        this.searchField = new TextField(1, "Search...", 0, 0, 130, 20);
        this.dragY = 0;
        this.dragX = 0;
        this.translate = new Translate(0.0f, 0.0f);
        this.field_146292_n.add(new ClientButton(0, 20, this.field_146295_m - 40, 100, 20, "EditHUD", new ResourceLocation("Melody/icon/edithud.png"), 0.0f, -3.0f, 14.0f, new Color(30, 164, 226, 80)));
        this.field_146292_n.add(new ClientButton(1, 20, this.field_146295_m - 65, 100, 20, "Reset Location", new Color(30, 164, 226, 80)));
        if (this.field_146297_k.field_71441_e == null) {
            this.field_146292_n.add(new ClientButton(2, 20, this.field_146295_m - 90, 100, 20, "Save & Exit", new Color(240, 90, 117, 80)));
        }
        super.func_73866_w_();
    }

    protected void func_146284_a(GuiButton guiButton) throws IOException {
        switch (guiButton.field_146127_k) {
            case 0: {
                if (this.field_146297_k.field_71441_e != null && this.field_146297_k.field_71439_g != null) {
                    this.field_146297_k.func_147108_a((GuiScreen)new HUDScreen());
                    this.func_146281_b();
                    break;
                }
                NotificationPublisher.queue("WARNING", "HUD editing is disabled while world is not loaded.", NotificationType.WARN, 3000);
                break;
            }
            case 1: {
                this.halfX = (int)((float)this.field_146294_l / 3.1f);
                this.halfY = (int)((float)this.field_146295_m / 2.72f);
                this.halfX = this.halfX < 270 ? 270 : this.halfX;
                this.halfY = this.halfY < 140 ? 140 : this.halfY;
                this.xStart = this.field_146294_l / 2 - this.halfX;
                this.yStart = this.field_146295_m / 2 - this.halfY;
                this.xEnd = this.field_146294_l / 2 + this.halfX;
                this.yEnd = this.field_146295_m / 2 + this.halfY;
                break;
            }
            case 2: {
                this.field_146297_k.func_147108_a((GuiScreen)new MainMenu(15));
                this.func_146281_b();
            }
        }
        super.func_146284_a(guiButton);
    }

    /*
     * WARNING - void declaration
     */
    public void func_73863_a(int n, int n2, float f) {
        int n3;
        this.func_146276_q_();
        if (this.dragX == 0 && this.dragY == 0) {
            this.dxStart = this.xStart;
            this.dxEnd = this.xEnd;
            this.dyStart = this.yStart;
            this.dyEnd = this.yEnd;
        } else if (this.field_146297_k.field_71439_g == null) {
            RenderUtil.outlineRect(this.dxStart - 1, this.dyStart - 2, this.dxEnd + 1, this.dyEnd, Colors.MAGENTA.c, 10.0f, 3.0f);
        } else {
            RenderUtil.outlineRainbow(this.dxStart - 1, this.dyStart - 2, this.dxEnd + 1, this.dyEnd, 10.0f, 3.0f);
        }
        RenderUtil.drawFastRoundedRect(0.0f, 0.0f, this.field_146294_l, this.field_146295_m, 0.0f, new Color(20, 20, 20, 40).getRGB());
        GL11.glPushMatrix();
        this.translate.interpolate(this.field_146294_l, this.field_146295_m, 8.0);
        double d = (float)(this.field_146294_l / 2) - this.translate.getX() / 2.0f;
        double d2 = (float)(this.field_146295_m / 2) - this.translate.getY() / 2.0f;
        GlStateManager.func_179137_b((double)d, (double)d2, (double)0.0);
        GlStateManager.func_179152_a((float)(this.translate.getX() / (float)this.field_146294_l), (float)(this.translate.getY() / (float)this.field_146295_m), (float)1.0f);
        ScaledResolution scaledResolution = new ScaledResolution(this.field_146297_k);
        int n4 = scaledResolution.func_78325_e();
        CGRender.drawBlurRect(this.xStart, this.yStart, this.xEnd, this.yEnd, new Color(30, 30, 30, 160).getRGB(), 50, 6);
        RenderUtil.outlineRect(this.xStart, this.yStart, this.xEnd, this.yEnd, Colors.GRAY.c, 10.0f, 2.0f);
        FontLoaders.NMSL35.drawString("\u00a7lMelodySky", this.xStart + 15, (float)this.yStart + 11.5f, -1);
        Color color = ColorUtils.addAlpha(ColorUtils.lighter(new Color(Colors.GRAY.c), 1.0), 130);
        RenderUtil.glColor(color);
        RenderUtil.drawLine(this.xStart + 90, this.yStart + 40, this.xStart + 90, this.yEnd, 3.0f);
        RenderUtil.drawLine(this.xStart + 5, this.yStart + 40, this.xEnd - 5, this.yStart + 40, 3.0f);
        if (this.curType == clickType.ClickGui) {
            String string = this.currentModule == null ? "" : this.currentModule.name;
            FontLoaders.CNMD30.drawString("\u00a7l" + (this.searchText.equals("") ? this.currentType.name() : "Search") + "\u00a7r > " + string, this.xStart + 130, (float)this.yStart + 13.5f, -1);
        } else if (this.curType == clickType.Friends) {
            FontLoaders.CNMD30.drawString("\u00a7lFriends", this.xStart + 130, (float)this.yStart + 13.5f, -1);
        } else if (this.curType == clickType.ChatTriggers) {
            FontLoaders.CNMD30.drawString("\u00a7lChatTriggers", this.xStart + 130, (float)this.yStart + 13.5f, -1);
        } else if (this.curType == clickType.TextReplacements) {
            FontLoaders.CNMD30.drawString("\u00a7lTextReplacements", this.xStart + 130, (float)this.yStart + 13.5f, -1);
        }
        if (!this.shouldValueOpen) {
            this.ValApY = 0.0f;
            this.mouseValueScrollY = (int)0.0f;
            this.ValApYTranslator = new NonlinearOpacity(0);
        }
        int n5 = this.xEnd - 140;
        int n6 = this.yStart + 10;
        this.searchField.drawTextBox(n5, n6);
        if (this.searchText != this.searchField.func_146179_b()) {
            this.ModApY = 0.0f;
            this.mouseModuleScrollY = (int)0.0f;
            this.ModApYTranslator = new NonlinearOpacity(0);
            this.searchText = this.searchField.func_146179_b();
        }
        int n7 = 0;
        for (ModuleType moduleTypeArray : ModuleType.values()) {
            FontLoaders.CNMD25.drawString(moduleTypeArray.name(), this.xStart + 10, (float)this.yStart + 48.5f + (float)n7, moduleTypeArray == this.currentType ? -1 : new Color(230, 230, 230, 160).getRGB());
            n7 += 18;
        }
        int n8 = new Color(130, 160, 228, 100).getRGB();
        int n9 = new Color(70, 100, 255, 100).getRGB();
        RenderUtil.drawFastRoundedRect(this.xStart + 5, (float)this.yStart + 52.5f + (float)n7, this.xStart + 80, (float)this.yStart + 68.5f + (float)n7, 3.0f, this.curType == clickType.Friends ? n9 : n8);
        FontLoaders.NMSL19.drawString("Friends", this.xStart + 25, (float)this.yStart + 57.5f + (float)n7, -1);
        RenderUtil.drawFastRoundedRect(this.xStart + 5, (float)this.yStart + 72.5f + (float)n7, this.xStart + 80, (float)this.yStart + 88.5f + (float)n7, 3.0f, this.curType == clickType.ChatTriggers ? n9 : n8);
        FontLoaders.NMSL19.drawString("ChatTriggers", this.xStart + 15, (float)this.yStart + 77.0f + (float)n7, -1);
        RenderUtil.drawFastRoundedRect(this.xStart + 5, (float)this.yStart + 92.5f + (float)n7, this.xStart + 80, (float)this.yStart + 108.5f + (float)n7, 3.0f, this.curType == clickType.TextReplacements ? n9 : n8);
        FontLoaders.NMSL19.drawString("TextReplaces", this.xStart + 13, (float)this.yStart + 97.0f + (float)n7, -1);
        if (this.curType == clickType.ClickGui) {
            float f2 = this.currentModule != null ? this.valueStartX : 0.0f;
            Scissor.start(this.xStart * n4, (this.yStart + 41) * n4, ((float)this.xEnd - f2) * (float)n4, (float)(((double)(this.yEnd - 5) - d2) * (double)n4));
            if (this.searchText.equals("")) {
                for (Object object : ModuleType.values()) {
                    if (object != this.currentType) continue;
                    int n10 = 0;
                    for (Module module : Client.instance.getModuleManager().getModulesInType((ModuleType)((Object)object))) {
                        for (ModBlock modBlock : this.modBlocks) {
                            if (modBlock.getModule() != module) continue;
                            modBlock.draw(this.xStart, this.yStart, this.xEnd, n10, this.ModApY, module == this.bindingModule, this.field_146297_k);
                        }
                        n10 += 40;
                    }
                }
            } else if (!this.searchText.equals("")) {
                boolean valueElement = false;
                for (Module module : ModuleManager.getModules()) {
                    Object object;
                    object = "";
                    for (Object object2 : module.getValues()) {
                        object = (String)object + ((Value)object2).getName().toLowerCase();
                    }
                    if (!module.getName().toLowerCase().contains(this.searchText.toLowerCase()) && !((String)object).contains(this.searchText.toLowerCase()) && !module.getModInfo().toLowerCase().contains(this.searchText.toLowerCase())) continue;
                    for (Object object2 : this.modBlocks) {
                        void n13;
                        if (((ModBlock)object2).getModule() != module) continue;
                        ((ModBlock)object2).draw(this.xStart, this.yStart, this.xEnd, (int)n13, this.ModApY, module == this.bindingModule, this.field_146297_k);
                    }
                    n13 += 40;
                }
            }
            Scissor.end();
            if (this.currentModule != null) {
                ValueElement valueElement = (ValueElement)this.moduleMap.get(this.currentModule);
                n3 = 0;
                Scissor.start(((float)this.xEnd - f2) * (float)n4, (this.yStart + 41) * n4, this.xEnd * n4, (this.yEnd - 5) * n4);
                RenderUtil.drawFastRoundedRect((float)this.xEnd - f2, this.yStart + 41, this.xEnd, this.yEnd - 5, 4.0f, new Color(120, 120, 120, 90).getRGB());
                RenderUtil.drawFastRoundedRect((float)this.xEnd - f2, this.yStart + 41, this.xEnd, this.yStart + 67, 4.0f, new Color(120, 120, 120, 120).getRGB());
                FontLoaders.NMSL24.drawString("<- Back ", (float)(this.xEnd + 10) - f2, this.yStart + 49, -1);
                Scissor.start(((float)this.xEnd - f2) * (float)n4, (this.yStart + 67) * n4, this.xEnd * n4, (this.yEnd - 8) * n4);
                if (this.shouldValueOpen && Mouse.isButtonDown((int)0)) {
                    ValueElement valueElement2 = (ValueElement)this.moduleMap.get(this.currentModule);
                    for (Value value : this.currentModule.getValues()) {
                        Object object2;
                        if (!(value instanceof Numbers)) continue;
                        object2 = valueElement2.getValueElement(value);
                        object2.handleMouseActions(n, n2);
                    }
                }
                if (this.currentModule.getValues().isEmpty()) {
                    FontLoaders.NMSL20.drawString("No Values Available.", (float)(this.xEnd + 108) - f2, this.yStart + 190, -1);
                } else {
                    for (Object object : this.currentModule.getValues()) {
                        Element element = valueElement.getValueElement((Value)object);
                        element.draw((float)this.xEnd - this.valueStartX, this.yStart, this.xEnd, n3, this.ValApY);
                        n3 += 25;
                    }
                }
                Scissor.end();
                Scissor.end();
            }
            this.handleValueAnimation();
            this.handleMouseScroll(n, n2);
            this.handleYScrollRender();
        } else if (this.curType == clickType.Friends) {
            this.friendGui.draw(this.xStart, this.yStart, this.xEnd, this.yEnd);
            this.friendGui.handleMouseScroll(n, n2);
        } else if (this.curType == clickType.ChatTriggers) {
            this.chatTriggersGui.draw(this.xStart, this.yStart, this.xEnd, this.yEnd);
            this.chatTriggersGui.handleMouseScroll(n, n2);
        } else if (this.curType == clickType.TextReplacements) {
            this.textReplacementsGui.draw(this.xStart, this.yStart, this.xEnd, this.yEnd);
            this.textReplacementsGui.handleMouseScroll(n, n2);
        }
        GL11.glPopMatrix();
        if (Mouse.isButtonDown((int)0)) {
            if (n > this.xStart && n < this.xEnd - 160 && n2 > this.yStart && n2 < this.yStart + 40 && !this.dragging) {
                this.dragging = true;
            }
        } else {
            this.dragging = false;
        }
        if (this.dragging) {
            if ((float)this.dragX == 0.0f && (float)this.dragY == 0.0f) {
                this.dragX = n - this.xStart;
                this.dragY = n2 - this.yStart;
            } else {
                int n12 = n - this.dragX;
                int n11 = n - this.dragX + 2 * this.halfX;
                n3 = n2 - this.dragY;
                int valueElement2 = n2 - this.dragY + 2 * this.halfY;
                if (Math.abs(n12 - this.dxStart) < 7 && Math.abs(n11 - this.dxEnd) < 7 && Math.abs(n3 - this.dyStart) < 7 && Math.abs(valueElement2 - this.dyEnd) < 7) {
                    this.xStart = this.dxStart;
                    this.xEnd = this.dxEnd;
                    this.yStart = this.dyStart;
                    this.yEnd = this.dyEnd;
                } else {
                    this.xStart = n12;
                    this.xEnd = n11;
                    this.yStart = n3;
                    this.yEnd = valueElement2;
                }
            }
        } else {
            this.dragX = 0;
            this.dragY = 0;
        }
        super.func_73863_a(n, n2, f);
    }

    /*
     * WARNING - void declaration
     */
    protected void func_73864_a(int n, int n2, int n3) throws IOException {
        int n4 = 0;
        for (ModuleType object2 : ModuleType.values()) {
            if (n > this.xStart + 10 && n < this.xStart + 10 + FontLoaders.CNMD25.getStringWidth(object2.name()) + 10 && n2 > this.yStart + n4 + 45 && n2 < this.yStart + n4 + 45 + 18) {
                this.curType = clickType.ClickGui;
                this.currentType = object2;
                this.mouseModuleScrollY = 0;
                this.ModApY = 0;
                this.ModApYTranslator = new NonlinearOpacity(0);
                this.ValApY = 0.0f;
                this.mouseValueScrollY = (int)0.0f;
                this.ValApYTranslator = new NonlinearOpacity(0);
                this.searchField.func_146180_a("");
            }
            n4 += 18;
        }
        if (n > this.xStart + 5 && n < this.xStart + 80) {
            if ((float)n2 > (float)this.yStart + 52.5f + (float)n4 && (float)n2 < (float)this.yStart + 68.5f + (float)n4) {
                this.friendGui = new FriendGui(this);
                this.curType = clickType.Friends;
            } else if ((float)n2 > (float)this.yStart + 72.5f + (float)n4 && (float)n2 < (float)this.yStart + 88.5f + (float)n4) {
                this.chatTriggersGui = new ChatTriggersGui(this);
                this.curType = clickType.ChatTriggers;
            } else if ((float)n2 > (float)this.yStart + 92.5f + (float)n4 && (float)n2 < (float)this.yStart + 108.5f + (float)n4) {
                this.textReplacementsGui = new TextReplacementsGui(this);
                this.curType = clickType.TextReplacements;
            }
        }
        if (this.curType == clickType.Friends) {
            this.friendGui.onMouseClick(n, n2, n3);
        } else if (this.curType == clickType.ChatTriggers) {
            this.chatTriggersGui.onMouseClick(n, n2, n3);
        } else if (this.curType == clickType.TextReplacements) {
            this.textReplacementsGui.onMouseClick(n, n2, n3);
        } else if (this.curType == clickType.ClickGui) {
            this.searchField.func_146192_a(n, n2, n3);
            n4 = 0;
            for (ModuleType moduleType : ModuleType.values()) {
                if (n > this.xStart + 10 && n < this.xStart + 10 + FontLoaders.CNMD25.getStringWidth(moduleType.name()) + 10 && n2 > this.yStart + n4 + 45 && n2 < this.yStart + n4 + 45 + 18) {
                    this.currentType = moduleType;
                    this.mouseModuleScrollY = 0;
                    this.ModApY = 0;
                    this.ModApYTranslator = new NonlinearOpacity(0);
                    this.ValApY = 0.0f;
                    this.mouseValueScrollY = (int)0.0f;
                    this.ValApYTranslator = new NonlinearOpacity(0);
                    this.searchField.func_146180_a("");
                }
                n4 += 18;
            }
            if (!this.shouldValueOpen) {
                for (ModBlock modBlock : this.modBlocks) {
                    if (this.searchText.equals("")) {
                        if (modBlock.getModule().getType() != this.currentType) {
                            continue;
                        }
                    } else {
                        void var8_19;
                        Module module = modBlock.getModule();
                        String string = "";
                        for (Value value : module.getValues()) {
                            String string2 = (String)var8_19 + value.getName().toLowerCase();
                        }
                        if (!module.getName().toLowerCase().contains(this.searchText.toLowerCase()) && !var8_19.contains(this.searchText.toLowerCase()) && !module.getModInfo().toLowerCase().contains(this.searchText.toLowerCase())) continue;
                    }
                    if (n2 < this.yStart + 41 || n2 > this.yStart + 367) continue;
                    if ((float)n > modBlock.getStartX() && (float)n < modBlock.getEndX() && (float)n2 > modBlock.getStartY() && (float)n2 < modBlock.getEndY() && n3 == 0) {
                        modBlock.onMouseClick();
                    } else if ((float)n > modBlock.getStartX() && (float)n < modBlock.getEndX() && (float)n2 > modBlock.getStartY() && (float)n2 < modBlock.getEndY() && n3 == 1) {
                        this.currentModule = modBlock.getModule();
                        for (Map.Entry entry : ((ValueElement)this.moduleMap.get(this.currentModule)).getValueMap().entrySet()) {
                            ((Element)entry.getValue()).reset();
                        }
                        this.shouldValueOpen = true;
                    }
                    if (!((float)n > modBlock.getvStartX()) || !((float)n < modBlock.getvEndX()) || !((float)n2 > modBlock.getvStartY()) || !((float)n2 < modBlock.getvEndY())) continue;
                    if (n3 == 0) {
                        this.bindingModule = modBlock.getModule();
                    }
                    if (n3 != 1) continue;
                    modBlock.getModule().setKey(0);
                    this.bindingModule = null;
                }
            } else {
                float f;
                ValueElement valueElement = (ValueElement)this.moduleMap.get(this.currentModule);
                for (Value value : this.currentModule.getValues()) {
                    Element element = valueElement.getValueElement(value);
                    element.handleMouseActions(n, n2);
                    if (!(element instanceof NumbersEle)) continue;
                    NumbersEle numbersEle = (NumbersEle)element;
                    numbersEle.onPlusMinus(n, n2);
                }
                float f2 = f = this.currentModule != null ? 310.0f : 0.0f;
                if (!((float)n > (float)this.xEnd - f) || n >= this.xEnd || n2 <= this.yStart + 67 || n2 >= this.yStart + 367) {
                    this.shouldValueOpen = false;
                }
            }
        }
        super.func_73864_a(n, n2, n3);
    }

    protected void func_146273_a(int n, int n2, int n3, long l2) {
        int n4 = 0;
        for (ModuleType moduleType : ModuleType.values()) {
            if (n > this.xStart + 10 && n < this.xStart + 10 + FontLoaders.CNMD25.getStringWidth(moduleType.name()) + 10 && n2 > this.yStart + n4 + 45 && n2 < this.yStart + n4 + 45 + 18) {
                this.curType = clickType.ClickGui;
                this.currentType = moduleType;
                this.mouseModuleScrollY = 0;
                this.ModApY = 0;
                this.ModApYTranslator = new NonlinearOpacity(0);
                this.ValApY = 0.0f;
                this.mouseValueScrollY = (int)0.0f;
                this.ValApYTranslator = new NonlinearOpacity(0);
                this.searchField.func_146180_a("");
            }
            n4 += 18;
        }
        if (n > this.xStart + 5 && n < this.xStart + 80) {
            if ((float)n2 > (float)this.yStart + 52.5f + (float)n4 && (float)n2 < (float)this.yStart + 68.5f + (float)n4) {
                this.friendGui = new FriendGui(this);
                this.curType = clickType.Friends;
            } else if ((float)n2 > (float)this.yStart + 72.5f + (float)n4 && (float)n2 < (float)this.yStart + 88.5f + (float)n4) {
                this.chatTriggersGui = new ChatTriggersGui(this);
                this.curType = clickType.ChatTriggers;
            } else if ((float)n2 > (float)this.yStart + 92.5f + (float)n4 && (float)n2 < (float)this.yStart + 108.5f + (float)n4) {
                this.textReplacementsGui = new TextReplacementsGui(this);
                this.curType = clickType.TextReplacements;
            }
        }
        if (this.curType == clickType.Friends) {
            this.friendGui.onMouseClick(n, n2, 3);
        } else if (this.curType == clickType.ChatTriggers) {
            this.chatTriggersGui.onMouseClick(n, n2, 3);
        } else if (this.curType == clickType.TextReplacements) {
            this.textReplacementsGui.onMouseClick(n, n2, 3);
        }
        super.func_146273_a(n, n2, n3, l2);
    }

    public void func_146281_b() {
        Client.instance.saveConfig();
        super.func_146281_b();
    }

    protected void func_73869_a(char c, int n) throws IOException {
        if (this.curType == clickType.Friends) {
            this.friendGui.onCharTyped(c, n);
        } else if (this.curType == clickType.ChatTriggers) {
            this.chatTriggersGui.onCharTyped(c, n);
        } else if (this.curType == clickType.TextReplacements) {
            this.textReplacementsGui.onCharTyped(c, n);
        } else if (this.curType == clickType.ClickGui) {
            this.searchField.func_146201_a(c, n);
            if (this.currentModule != null) {
                ValueElement valueElement = (ValueElement)this.moduleMap.get(this.currentModule);
                for (Value value : this.currentModule.getValues()) {
                    Element element = valueElement.getValueElement(value);
                    if (!(element instanceof TextEle)) continue;
                    ((TextEle)element).handleKeyTypes(c, n);
                }
            }
            if (this.bindingModule != null) {
                this.bindingModule.setKey(n);
                this.bindingModule = null;
            }
        }
        super.func_73869_a(c, n);
    }

    public boolean func_73868_f() {
        return false;
    }

    public void func_146276_q_() {
        if (this.field_146297_k.field_71441_e == null) {
            BackgroundShader.BACKGROUND_SHADER.startShader();
            Tessellator tessellator = Tessellator.func_178181_a();
            WorldRenderer worldRenderer = tessellator.func_178180_c();
            worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
            worldRenderer.func_181662_b(0.0, (double)this.field_146295_m, 0.0).func_181675_d();
            worldRenderer.func_181662_b((double)this.field_146294_l, (double)this.field_146295_m, 0.0).func_181675_d();
            worldRenderer.func_181662_b((double)this.field_146294_l, 0.0, 0.0).func_181675_d();
            worldRenderer.func_181662_b(0.0, 0.0, 0.0).func_181675_d();
            tessellator.func_78381_a();
            BackgroundShader.BACKGROUND_SHADER.stopShader();
            CGRender.bgBlur.renderBlur(15.0f);
        }
    }

    private void handleValueAnimation() {
        if (this.shouldValueOpen) {
            this.valueTranslator.interp(310.0f, 28.0f);
        } else {
            this.valueTranslator.interp(0.0f, 33.0f);
        }
        this.valueStartX = this.valueTranslator.getOpacity();
        if (this.valueStartX == 0.0f && !this.shouldValueOpen) {
            this.currentModule = null;
        }
    }

    private void handleYScrollRender() {
        this.typeApYTranslator.interp(this.mouseTypeScrollY, 6.0f);
        this.typeApY = this.typeApYTranslator.getOpacity();
        if (this.currentModule == null && this.searchText.equals("")) {
            List list = Client.instance.getModuleManager().getModulesInType(this.currentType);
            for (ModBlock modBlock : this.modBlocks) {
                if (modBlock.getModule() == list.get(0) && modBlock.getStartY() > (float)(this.yStart + 45)) {
                    this.ModApY = 0.0f;
                    this.mouseModuleScrollY = (int)0.0f;
                    this.ModApYTranslator = new NonlinearOpacity(0);
                }
                if (modBlock.getModule() != list.get(list.size() - 1) || !(modBlock.getEndY() < (float)(this.yEnd - 5))) continue;
                this.ModApYTranslator.setOpacity(this.ModApY + 0.1f);
                this.mouseModuleScrollY = (int)this.ModApYTranslator.getOpacity();
            }
        }
        float f = this.mouseModuleScrollY / 4 < 8 ? 8.0f : (float)(this.mouseModuleScrollY / 4);
        this.ModApYTranslator.interp(this.mouseModuleScrollY, f);
        this.ModApY = this.ModApYTranslator.getOpacity();
        this.ValApYTranslator.interp(this.mouseValueScrollY, 8.0f);
        this.ValApY = this.ValApYTranslator.getOpacity();
    }

    private void handleMouseScroll(float f, float f2) {
        this.mouseWheel = Mouse.getDWheel();
        if (this.currentModule == null) {
            if (f > (float)(this.xStart + 120) && f < (float)this.xEnd && f2 > (float)(this.yStart + 40) && f2 < (float)(this.yStart + 370)) {
                if (this.mouseWheel < 0) {
                    this.mouseModuleScrollY -= 40;
                } else if (this.mouseWheel > 0 && this.mouseModuleScrollY < 0) {
                    this.mouseModuleScrollY += 40;
                }
            }
        } else if (this.currentModule != null && f > (float)(this.xStart + 120) && f < (float)(this.xStart + 610) && f2 > (float)(this.yStart + 40) && f2 < (float)(this.yStart + 370)) {
            int n = this.currentModule.getValues().size();
            if (this.mouseWheel < 0 && this.mouseValueScrollY < n - 1) {
                this.mouseValueScrollY -= 40;
            } else if (this.mouseWheel > 0 && this.mouseValueScrollY < 0) {
                this.mouseValueScrollY += 40;
            }
        }
    }

    public static enum clickType {
        ClickGui,
        Friends,
        ChatTriggers,
        TextReplacements;

    }
}

