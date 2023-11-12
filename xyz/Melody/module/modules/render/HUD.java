/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  net.minecraft.client.gui.ChatLine
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer$EnumChatVisibility
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.scoreboard.Score
 *  net.minecraft.scoreboard.ScoreObjective
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.scoreboard.Scoreboard
 *  net.minecraft.scoreboard.Team
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.MathHelper
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.module.modules.render;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.events.rendering.EventRenderScoreboard;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.GUI.Animate.Opacity;
import xyz.Melody.Utils.animate.Translate;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.render.Stencil;
import xyz.Melody.Utils.shader.GaussianBlur;
import xyz.Melody.injection.mixins.client.MCA;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.Fishing.SlugFishing;
import xyz.Melody.module.modules.QOL.AutoEnchantTable;
import xyz.Melody.module.modules.QOL.Dungeons.AutoTerminals;

public class HUD
extends Module {
    public Option qualityBoom = new Option("DO NOT ENABLE", false);
    public Numbers blurQuality = new Numbers("Blur Quality", 24.0, 8.0, 64.0, 1.0);
    public Option fpsLimit = new Option("Fps Limitation", true);
    public Numbers guiFpsLim = new Numbers("Gui FPS Limit", 90.0, 30.0, 200.0, 10.0);
    public Option overwriteChat = new Option("OverwriteChat", true);
    public Option chatBlur = new Option("Chat Blur", false);
    public Option chatTextShadow = new Option("Chat TextShadow", false);
    public Numbers chatRad = new Numbers("Chat RoundRadius", 5.0, 0.0, 50.0, 1.0);
    public Numbers chatAlpha = new Numbers("Chat Alpha", 100.0, 0.0, 255.0, 5.0);
    public Numbers chatBlurStr = new Numbers("Chat BlurStrength", 15.0, 0.0, 50.0, 5.0);
    public Option scoreBoard = new Option("ScoreBoard", true);
    public Option scoreboardBlur = new Option("ScoreBoard Blur", false);
    public Option scoreboardOutline = new Option("ScoreBlur Outline", false);
    public Numbers scoreBlurStr = new Numbers("ScoreBoard BlurStr", 15.0, 0.0, 50.0, 5.0);
    public Option blur = new Option("Gui Blur", true);
    public Option cgblur = new Option("ClickGui Blur", true);
    public Option replaceText = new Option("TextReplacement", true);
    public Numbers bMax = new Numbers("Blur Value", 25.0, 10.0, 100.0, 5.0);
    public Numbers bSpeed = new Numbers("Blur Speed", 5.0, 1.0, 10.0, 1.0);
    public Option container = new Option("Gui Animations", true);
    private Option coords = new Option("Coords", true);
    private Option pots = new Option("Effects", true);
    private static HUD INSTANCE;
    private GaussianBlur gblur = new GaussianBlur();
    private ScoreObjective objective;
    private ScaledResolution scaledRes;
    private GaussianBlur gblur2 = new GaussianBlur();
    private GaussianBlur gblur1 = new GaussianBlur();

    public HUD() {
        super("HUD", new String[]{"gui"}, ModuleType.Render);
        this.addValues(this.blurQuality, this.fpsLimit, this.guiFpsLim, this.overwriteChat, this.chatBlur, this.chatTextShadow, this.chatRad, this.chatAlpha, this.chatBlurStr, this.scoreboardBlur, this.scoreboardOutline, this.scoreBoard, this.scoreBlurStr, this.blur, this.cgblur, this.replaceText, this.bMax, this.bSpeed, this.container, this.coords, this.pots);
        this.setEnabled(true);
    }

    public static HUD getInstance() {
        if (INSTANCE == null) {
            INSTANCE = (HUD)Client.instance.getModuleManager().getModuleByClass(HUD.class);
        }
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.setEnabled(true);
        super.onDisable();
    }

    @EventHandler
    private void onRenderInfo(EventRender2D eventRender2D) {
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        float f = this.mc.field_71462_r != null && this.mc.field_71462_r instanceof GuiChat ? -15.0f : -2.0f;
        RenderUtil.drawFastRoundedRect(0.0f, 0.0f, 1.0f, 1.0f, 1.0f, new Color(10, 10, 10, 10).getRGB());
        if (((Boolean)this.coords.getValue()).booleanValue()) {
            this.mc.field_71466_p.func_78276_b("X: " + (int)this.mc.field_71439_g.field_70165_t + "  Y: " + (int)this.mc.field_71439_g.field_70163_u + "  Z: " + (int)this.mc.field_71439_g.field_70161_v, 3, (int)((float)(scaledResolution.func_78328_b() - 10) + f), -1);
        }
        if (((Boolean)this.pots.getValue()).booleanValue()) {
            this.drawPotionStatus(scaledResolution);
        }
    }

    private void drawPotionStatus(ScaledResolution scaledResolution) {
        ArrayList<PotionEffect> arrayList = new ArrayList<PotionEffect>();
        for (Object object : this.mc.field_71439_g.func_70651_bq()) {
            arrayList.add((PotionEffect)object);
        }
        arrayList.sort(Comparator.comparingDouble(this::lambda$drawPotionStatus$0));
        float f = this.mc.field_71462_r != null && this.mc.field_71462_r instanceof GuiChat ? -15.0f : -2.0f;
        for (PotionEffect potionEffect : arrayList) {
            Potion potion = Potion.field_76425_a[potionEffect.func_76456_a()];
            String string = I18n.func_135052_a((String)potion.func_76393_a(), (Object[])new Object[0]);
            String string2 = "";
            if (potionEffect.func_76458_c() == 1) {
                string = string + " II";
            } else if (potionEffect.func_76458_c() == 2) {
                string = string + " III";
            } else if (potionEffect.func_76458_c() == 3) {
                string = string + " IV";
            }
            if (potionEffect.func_76459_b() < 600 && potionEffect.func_76459_b() > 300) {
                string2 = string2 + "\u00a76 " + Potion.func_76389_a((PotionEffect)potionEffect);
            } else if (potionEffect.func_76459_b() < 300) {
                string2 = string2 + "\u00a7c " + Potion.func_76389_a((PotionEffect)potionEffect);
            } else if (potionEffect.func_76459_b() > 600) {
                string2 = string2 + "\u00a77 " + Potion.func_76389_a((PotionEffect)potionEffect);
            }
            this.mc.field_71466_p.func_175063_a(string, (float)(scaledResolution.func_78326_a() - this.mc.field_71466_p.func_78256_a(string + string2)), (float)(scaledResolution.func_78328_b() - 9) + f, potion.func_76401_j());
            this.mc.field_71466_p.func_175063_a(string2, (float)(scaledResolution.func_78326_a() - this.mc.field_71466_p.func_78256_a(string2)), (float)(scaledResolution.func_78328_b() - 9) + f, -1);
            f -= 9.0f;
        }
    }

    public void handleContainer(Translate translate, Opacity opacity, float f, float f2) {
        if (((Boolean)this.blur.getValue()).booleanValue()) {
            this.gblur.renderBlur(opacity.getOpacity());
            opacity.interp(((Double)this.bMax.getValue()).floatValue() / 3.0f, ((Double)this.bSpeed.getValue()).intValue());
        }
        if (this.isEnabled() && ((Boolean)this.container.getValue()).booleanValue()) {
            AutoTerminals autoTerminals = AutoTerminals.getInstance();
            AutoEnchantTable autoEnchantTable = AutoEnchantTable.getINSTANCE();
            SlugFishing slugFishing = SlugFishing.getInstance();
            if (!(slugFishing.isEnabled() || autoTerminals.currentTerminal != null && autoTerminals.currentTerminal != AutoTerminals.TerminalType.NONE || autoEnchantTable.currentType != null && autoEnchantTable.currentType != AutoEnchantTable.expType.NONE)) {
                translate.interpolate(f, f2, 12.0);
                double d = f / 2.0f - translate.getX() / 2.0f;
                double d2 = f2 / 2.0f - translate.getY() / 2.0f;
                GlStateManager.func_179137_b((double)d, (double)d2, (double)0.0);
                GlStateManager.func_179152_a((float)(translate.getX() / f), (float)(translate.getY() / f2), (float)1.0f);
            }
        }
    }

    @EventHandler
    public void onRenderScoreboard(EventRenderScoreboard eventRenderScoreboard) {
        if (!((Boolean)this.scoreBoard.getValue()).booleanValue()) {
            this.objective = null;
            this.scaledRes = null;
            return;
        }
        this.objective = eventRenderScoreboard.getObjective();
        this.scaledRes = eventRenderScoreboard.getScaledRes();
    }

    @EventHandler
    private void scoreBoard(EventRender2D eventRender2D) {
        if (!((Boolean)this.scoreBoard.getValue()).booleanValue()) {
            return;
        }
        if (this.mc.field_71441_e == null) {
            return;
        }
        if (this.objective == null || this.scaledRes == null) {
            return;
        }
        if (this.objective.func_96682_a().func_96528_e().size() <= 1) {
            return;
        }
        Scoreboard scoreboard = this.objective.func_96682_a();
        ArrayList arrayList = scoreboard.func_96534_i(this.objective);
        ArrayList arrayList2 = arrayList.stream().filter(HUD::lambda$scoreBoard$1).collect(Collectors.toList());
        arrayList = arrayList2.size() > 15 ? Lists.newArrayList((Iterable)Iterables.skip(arrayList2, (int)(arrayList.size() - 15))) : arrayList2;
        float f = this.mc.field_71466_p.func_78256_a(this.objective.func_96678_d());
        int n = this.mc.field_71466_p.field_78288_b;
        for (Score score : arrayList) {
            ScorePlayerTeam scorePlayerTeam = scoreboard.func_96509_i(score.func_96653_e());
            String string = ScorePlayerTeam.func_96667_a((Team)scorePlayerTeam, (String)score.func_96653_e()) + ": " + EnumChatFormatting.RED + score.func_96652_c();
            f = Math.max(f, (float)this.mc.field_71466_p.func_78256_a(string));
        }
        float f2 = arrayList.size() * n;
        float f3 = (float)this.scaledRes.func_78328_b() / 2.0f + f2 / 3.0f;
        float f4 = 3.0f;
        float f5 = (float)this.scaledRes.func_78326_a() - f - f4;
        float f6 = (float)this.scaledRes.func_78326_a() - f4 + 2.0f;
        int n2 = ((Double)this.scoreBlurStr.getValue()).intValue() / 3;
        Stencil.initStencil();
        Stencil.bindWriteStencilBuffer();
        RenderUtil.drawFastRoundedRect((float)this.scaledRes.func_78326_a() - (f6 - (f5 - 4.0f)), f3 + 3.0f - (float)(n * (arrayList.size() + 1) + 7), this.scaledRes.func_78326_a(), f3 + 2.0f, 5.0f, new Color(21, 21, 21, 50).getRGB());
        Stencil.bindReadStencilBuffer(1);
        if (((Boolean)this.scoreboardBlur.getValue()).booleanValue()) {
            this.gblur2.renderBlur(n2);
        }
        Stencil.uninitStencil();
        RenderUtil.startTop();
        RenderUtil.drawFastRoundedRect((float)this.scaledRes.func_78326_a() - (f6 - (f5 - 4.0f)), f3 + 3.0f - (float)(n * (arrayList.size() + 1) + 7), this.scaledRes.func_78326_a() - 1, f3 + 2.0f, 5.0f, new Color(21, 21, 21, 50).getRGB());
        RenderUtil.endTop();
        float f7 = (float)this.scaledRes.func_78326_a() - (f6 - (f5 - 5.0f));
        float f8 = f3 + 3.0f - (float)(n * (arrayList.size() + 1) + 8);
        float f9 = this.scaledRes.func_78326_a();
        float f10 = f3 + 3.0f;
        Stencil.initStencil();
        Stencil.bindWriteStencilBuffer();
        RenderUtil.drawFastRoundedRect(f7, f8, f9, f10, 5.0f, new Color(0, 0, 0, 255).getRGB());
        Stencil.bindReadStencilBuffer(1);
        if (((Boolean)this.scoreboardOutline.getValue()).booleanValue()) {
            RenderUtil.outlineRainbow(f7, f8, f9, f10, 5.0f, 15.0f);
        }
        Stencil.uninitStencil();
        int n3 = 0;
        for (Score score : arrayList) {
            ScorePlayerTeam scorePlayerTeam = scoreboard.func_96509_i(score.func_96653_e());
            String string = ScorePlayerTeam.func_96667_a((Team)scorePlayerTeam, (String)score.func_96653_e());
            float f11 = f3 - (float)(++n3 * n);
            RenderUtil.startTop();
            this.mc.field_71466_p.func_78276_b(string, (int)f5, (int)f11, 0x20FFFFFF);
            if (n3 == arrayList.size()) {
                String string2 = this.objective.func_96678_d();
                this.mc.field_71466_p.func_78276_b(string2, (int)(f5 + f / 2.0f - (float)this.mc.field_71466_p.func_78256_a(string2) / 2.0f), (int)(f11 - (float)n), Color.white.getRGB());
            }
            RenderUtil.endTop();
        }
        GlStateManager.func_179124_c((float)1.0f, (float)1.0f, (float)1.0f);
    }

    public void handleChat(int n, int n2, List list, boolean bl, float f, int n3, float f2, boolean bl2) {
        if (this.mc.field_71474_y.field_74343_n != EntityPlayer.EnumChatVisibility.HIDDEN) {
            ScaledResolution scaledResolution = new ScaledResolution(this.mc);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GlStateManager.func_179109_b((float)0.0f, (float)(scaledResolution.func_78328_b() - 60), (float)0.0f);
            int n4 = n2;
            boolean bl3 = false;
            int n5 = 0;
            int n6 = list.size();
            int n7 = this.mc.field_71466_p.field_78288_b;
            if (n6 > 0) {
                int n8;
                int n9;
                int n10;
                if (bl) {
                    bl3 = true;
                }
                float f3 = f2;
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b((float)2.0f, (float)20.0f, (float)0.0f);
                GlStateManager.func_179152_a((float)f3, (float)f3, (float)1.0f);
                int n11 = MathHelper.func_76123_f((float)(f / f3));
                float f4 = 0.0f;
                float f5 = 0.0f;
                boolean bl4 = false;
                for (n10 = 0; n10 + n3 < list.size() && n10 < n4; ++n10) {
                    ChatLine chatLine = (ChatLine)list.get(n10 + n3);
                    if (chatLine == null || n - chatLine.func_74540_b() >= 200 && !bl3) continue;
                    bl4 = true;
                    if (!bl3 && n - chatLine.func_74540_b() > 190) {
                        float f6 = 1.0f - ((float)(n - chatLine.func_74540_b()) + ((MCA)this.mc).getTimer().field_74281_c - 195.0f) / 1.75f;
                        f6 = MathHelper.func_76131_a((float)f6, (float)0.0f, (float)1.0f);
                        f5 -= (float)n7 * f6 - 0.5f;
                        continue;
                    }
                    f5 -= (float)n7;
                }
                if (bl4) {
                    n10 = ((Double)this.chatBlurStr.getValue()).intValue() / 3;
                    if (n10 > 0) {
                        for (float f7 = 0.5f; f7 < 3.0f; f7 += 0.5f) {
                            RenderUtil.drawFastRoundedRect(f4 + f7 - 2.0f, f5 + f7, f4 + (float)n11 + 4.0f + f7, 1.0f + f7, ((Double)this.chatRad.getValue()).floatValue(), new Color(20, 20, 20, 5).getRGB());
                        }
                    }
                    Stencil.initStencil();
                    Stencil.bindWriteStencilBuffer();
                    RenderUtil.drawFastRoundedRect(f4 - 2.0f, f5, f4 + (float)n11 + 4.0f, 1.0f, ((Double)this.chatRad.getValue()).floatValue(), Color.white.getRGB());
                    GL11.glPopMatrix();
                    GL11.glPopMatrix();
                    Stencil.bindReadStencilBuffer(1);
                    if (((Boolean)this.chatBlur.getValue()).booleanValue()) {
                        this.gblur1.renderBlur(n10);
                    }
                    GL11.glPushMatrix();
                    GlStateManager.func_179109_b((float)0.0f, (float)(scaledResolution.func_78328_b() - 60), (float)0.0f);
                    GL11.glPushMatrix();
                    GlStateManager.func_179109_b((float)2.0f, (float)20.0f, (float)0.0f);
                    GlStateManager.func_179152_a((float)f3, (float)f3, (float)1.0f);
                    RenderUtil.drawFastRoundedRect(f4 - 2.0f, f5, f4 + (float)n11 + 4.0f, 1.0f, ((Double)this.chatRad.getValue()).floatValue(), new Color(20, 20, 20, ((Double)this.chatAlpha.getValue()).intValue()).getRGB());
                }
                for (n10 = 0; n10 + n3 < list.size() && n10 < n4; ++n10) {
                    int n12;
                    ChatLine chatLine = (ChatLine)list.get(n10 + n3);
                    if (chatLine == null || (n12 = n - chatLine.func_74540_b()) >= 200 && !bl3) continue;
                    ++n5;
                    n9 = 0;
                    n8 = -n10 * n7;
                    String string = chatLine.func_151461_a().func_150254_d();
                    GlStateManager.func_179147_l();
                    if (((Boolean)this.chatTextShadow.getValue()).booleanValue()) {
                        this.mc.field_71466_p.func_175065_a(string, (float)n9, (float)n8 - ((float)n7 - 1.5f), 0xFFFFFF, true);
                    } else {
                        this.mc.field_71466_p.func_175065_a(string, (float)n9, (float)n8 - ((float)n7 - 1.5f), 0xFFFFFF, false);
                    }
                    GlStateManager.func_179118_c();
                    GlStateManager.func_179084_k();
                }
                if (bl4) {
                    Stencil.uninitStencil();
                }
                if (bl3) {
                    GlStateManager.func_179109_b((float)-3.0f, (float)0.0f, (float)0.0f);
                    n7 = this.mc.field_71466_p.field_78288_b;
                    n10 = n6 * n7 + n6;
                    int n13 = n5 * n7 + n5;
                    int n14 = n3 * n13 / n6;
                    n9 = n13 * n13 / n10;
                    if (n10 != n13) {
                        n8 = n14 > 0 ? 170 : 96;
                        int n15 = bl2 ? 0xCC3333 : 0x3333AA;
                        RenderUtil.drawFastRoundedRect(0.0f, -n14, 2.0f, -n14 - n9, 0.0f, n15 + (n8 << 24));
                        RenderUtil.drawFastRoundedRect(2.0f, -n14, 1.0f, -n14 - n9, 0.0f, 0xCCCCCC + (n8 << 24));
                    }
                }
                GlStateManager.func_179121_F();
            }
        }
    }

    private static boolean lambda$scoreBoard$1(Score score) {
        return score.func_96653_e() != null && !score.func_96653_e().startsWith("#");
    }

    private double lambda$drawPotionStatus$0(PotionEffect potionEffect) {
        return -this.mc.field_71466_p.func_78256_a(I18n.func_135052_a((String)Potion.field_76425_a[potionEffect.func_76456_a()].func_76393_a(), (Object[])new Object[0]));
    }
}

