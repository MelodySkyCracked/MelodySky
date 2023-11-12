/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.StringUtils
 *  org.lwjgl.input.Mouse
 */
package xyz.Melody.GUI.Menu;

import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import org.lwjgl.input.Mouse;
import xyz.Melody.GUI.Animate.Opacity;
import xyz.Melody.GUI.Font.CFontRenderer;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.GUI.Menu.MainMenu;
import xyz.Melody.GUI.Particles.ParticleUtils;
import xyz.Melody.GUI.ShaderBG.Shaders.BackgroundShader;
import xyz.Melody.Utils.render.FadeUtil;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.shader.GaussianBlur;

public final class GuiChangeLog
extends GuiScreen {
    public boolean firstLaunch = false;
    private int alpha = 0;
    private Opacity opacity = new Opacity(10);
    private boolean shouldMainMenu;
    private ParticleUtils particle = new ParticleUtils();
    private GaussianBlur gb = new GaussianBlur();
    private int mouseWheel = 0;
    private int logStart = 0;
    private String[] logs = new String[]{"[2.10.5] Fixed SlugFishing.", "[2.10.5] Added Friend Support in GoldNuker.", "[2.10.5] Added Blur Quality Value in HUD.", "[2.10.4] Fixed Color Issues in NameTags.", "[2.10.4] Fixed Vanilla Name issue while NameTags is on.", "[2.10.4] Added NickHider.", "[2.10.3] Added Child Option in Camera.", "[2.10.3] Recoded MotionBlur.", "[2.10.3] Added Fps Limitation Option in HUD.", "[2.10.3] Recoded GhostBlock.", "[2.10.2] [B2] Added Url Bar in InternetSurfing.", "[2.10.2] Fixed LeverAura.", "[2.10.2] Removed AutoValue in StonkLessStonk.", "[2.10.2] Added SecretAura Module.", "[2.10.1] Fixed Screen Flashes in GenshinSplashScreen.", "[2.10.1] Animation Optimizations.", "[2.10.0] Genshin Impact ON TOP.", "[2.10.0] Added MacroProtect in CustomNuker.", "[2.10.0] Added Range Value in AutoGemstones -> MacroProtect.", "[2.10.0] Added Friend Support in MithrilNuker -> MacroProtect", "[2.10.0] Fixed FPS Lags in Dungeons.", "[2.9.8] DungeonManager Optimizations.", "[2.9.8] Source Code Simplified.", "[2.9.8] Fixed ClickGui Issues.", "[2.9.7] Added AutoReturnRealm.", "[2.9.7] Fixed Config Loading Issues.", "[2.9.7] BoundingBox Renderer Optimizations.", "[2.9.7] Tried to fix AutoFish.", "[2.9.7] Fixed ChatGui Misalignment.", "[2.9.7] Added Chat RoundRadius Value in HUD.", "[2.9.6] Fixed GuiFPSLimit in HUD.", "[2.9.6] Changed the main color in ClickGui", "[2.9.6] Adjusted ClickGui.", "[2.9.6] Fixed Crashes caused by InternetSurfing.", "[2.9.6] Changed CST Link in Mainmenu.", "[2.9.5] ClickGui Optimizations.", "[2.9.5] Added OverwriteChat Option in HUD.", "[2.9.4] Added Friend Support in AutoGemstones.", "[2.9.4] Added Auto Adapt to Screen Size in ClickGui.", "[2.9.4] Fixed Reenabling AutoWalk while not in Skb.", "[2.9.3] [B2] Fixed Misc Configs.", "[2.9.3] [B2] Fixed Color Prefixes in TextReplacements.", "[2.9.3] Fixed MacroProtect in AutoGemstone.", "[2.9.3] Removed ChatTrigger Commands.", "[2.9.3] Removed TetReplacement Commands.", "[2.9.3] Added Strip Mode in ChatTriggers.", "[2.9.2] Added TextValue Support.", "[2.9.2] Recoded Config System.", "[2.9.2] Simplified Sourcecode.", "[2.9.2] ClickGui Optimizations.", "[2.9.2] Removed SecretChecker.", "[2.9.2] Recoded PlayerList.", "[2.9.2] Changed Rainbow Color.", "[2.9.1] Fixed AutoWalk.", "[2.9.1] Fixed Network Problem.", "[2.9.1] Added Friend Manager in ClickGui.", "[2.9.1] Added ChatTrigger Manager in ClickGui.", "[2.9.1] Added TextReplacement Manager in ClickGui.", "[2.9.0] Recoded ClickGui.", "[2.9.0] Added Chat Options in HUD.", "[2.9.0] Added Scoreboard Options in HUD.", "[2.9.0] Added Gui FPS Limit Value in HUD.", "[2.8.9] Simplified Sourcecode.", "[2.8.9] Fixed BlueEgg Drill Issue.", "[2.8.8] Added FuelWhenRefill Value in AutoGemstone.", "[2.8.8] Added Instant Option in PowderChestMacro.", "[2.8.7] Recoded Aura.", "[2.8.7] Recoded NoSlowDown.", "[2.8.7] Reocded AntiKB.", "[2.8.7] Fixed FPS Issue caused by AutoGemstone.", "[2.8.6] Fixed AutoRefill in AutoGemstone.", "[2.8.6] Recoded LockDirection in AutoWalk.", "[2.8.5] Fixed AutoRefill in AutoGemstone.", "[2.8.4] [B2] Fixed XRay.", "[2.8.4] Network Optimizations.", "[2.8.4] Added XRay (Optifine Required).", "[2.8.3] [B2] Adjusted AutoWalk.", "[2.8.3] ClickGUI Optimizations.", "[2.8.3] SackClicker Optimizations.", "[2.8.3] Fixed FPS Issue Caused by MithrilNuker.", "[2.8.3] AutoWalk Optimizaitons.", "[2.8.2] Added SackClicker.", "[2.8.2] RouteHelper Optimizations.", "[2.8.2] Added Range Value in RouteHelper.", "[2.8.2] Fixed PlayerChecks in AutoGemstone.", "[2.8.1] Added ChestSellValue.", "[2.8.1] Recode AntiJacobContest.", "[2.8.1] ClickGui Optimizations.", "[2.8.0] Added RouteHelper Module.", "[2.8.0] AutoRuby Optimizations.", "[2.8.0] Added Stack Support in FetchLbinData.", "[2.8.0] Camera -> SelfName Optimizations.", "[2.8.0] CropNuker Optimizations.", "[2.8.0] AutoGemstone Optimizations.", "[2.7.9] Fixed AntiJacobContest.", "[2.7.9] Added BanwaveProtector.", "[2.7.9] Adjusted LockDirection in AutoWalk.", "[2.7.9] Added Range Value in LegitCropNuker.", "[2.7.9] Fixed Authentication.", "[2.7.8] Added AntiJacobContest.", "[2.7.8] Recode IceNuker.", "[2.7.8] Recode PlotCleaner.", "[2.7.8] Added .chattrigger Command.", "[2.7.7] Adjusted LockDirection in AutoWalk.", "[2.7.6] [B3] Fixed AutoFish.", "[2.7.6] Migrated MSA/C Server.", "[2.7.6] UseEmber Option in Slugfishing.", "[2.7.5] Fixed AutoFish.", "[2.7.5] Fixed BanwaveChecker.", "[2.7.4] Fixed AutoStartMacro in AutoWalk and AntiLimbo.", "[2.7.4] PathFinder Optimizations.", "[2.7.3] Fixed MSA/C Server.", "[2.7.3] [/] Recoded AutoWalk", "[2.7.3] [+] RequireMouseDown in LegitCropNuker", "[2.7.2] Migrated IRC Server.", "[2.7.1] Suited New Chat Server.", "[2.7.1] Some Fixes.", "[2.7.0] [/] Fixed AutoWalk while AutoWarpGarden Disabled.", "[2.7.0] [WIP] SecretChecker.", "[2.7.0] [/] Fixed AutoWalk AutoWarpGarden.", "[2.7.0] [+] CropNuker Auto /is(inside)."};

    public GuiChangeLog() {
        this.opacity = new Opacity(10);
    }

    public void func_73866_w_() {
        this.alpha = 6;
        this.firstLaunch = true;
        super.func_73866_w_();
    }

    public void func_73863_a(int n, int n2, float f) {
        CFontRenderer cFontRenderer = FontLoaders.NMSL18;
        this.func_146276_q_();
        this.gb.renderBlur(this.opacity.getOpacity());
        this.opacity.interp(90.0f, 3.0f);
        RenderUtil.drawFastRoundedRect(0.0f, 59.0f, this.field_146294_l, 61.0f, 1.0f, new Color(160, 160, 160, 100).getRGB());
        FontLoaders.CNMD45.drawCenteredString("ChangeLogs", this.field_146294_l / 2, 20.0f, FadeUtil.fade(FadeUtil.PURPLE.getColor()).getRGB());
        this.field_146297_k.field_71466_p.func_78276_b("\u00a92019-2023 MelodyWorkGroup", 4, this.field_146295_m - 10, new Color(60, 60, 60, 180).getRGB());
        this.mouseWheel = Mouse.getDWheel();
        if (this.mouseWheel < 0 && this.logStart < this.logs.length / 5 + 1) {
            ++this.logStart;
        }
        if (this.mouseWheel > 0 && this.logStart > 0) {
            --this.logStart;
        }
        float f2 = this.logs.length * (this.logStart == 0 ? 1 : this.logStart) / (this.logs.length / 25) - 25;
        RenderUtil.drawFastRoundedRect(0.0f + f2, 59.0f, 20.0f + f2, 61.0f, 0.0f, FadeUtil.BLUE.getColor().getRGB());
        int n3 = 0;
        int n4 = 0;
        if (!this.shouldMainMenu && this.alpha < 210) {
            this.alpha += 10;
        }
        if (this.shouldMainMenu && this.alpha > 10) {
            this.alpha -= 12;
        }
        this.alpha = this.alpha <= 10 ? 6 : this.alpha;
        for (String string : this.logs) {
            if (n3 % 25 == 0 && n3 != 0) {
                ++n4;
            }
            if (string.contains("2.10.5") && (string = EnumChatFormatting.AQUA + string).contains("[B2]")) {
                string = EnumChatFormatting.GREEN + StringUtils.func_76338_a((String)string);
            }
            if (string.contains("[HEY]")) {
                string = EnumChatFormatting.RED + string;
            }
            cFontRenderer.drawString(string, (float)(6 + n4 * 340) - (float)this.logStart * 42.5f, n3 * 16 + 71 - n4 * 400, new Color(255, 255, 255, this.alpha).getRGB());
            ++n3;
        }
        if (this.shouldMainMenu) {
            this.field_146297_k.func_147108_a((GuiScreen)new MainMenu((int)this.opacity.getOpacity()));
        }
    }

    protected void func_73864_a(int n, int n2, int n3) throws IOException {
        this.shouldMainMenu = true;
    }

    public void func_146276_q_() {
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
        ParticleUtils.drawParticles();
    }

    public void func_146282_l() throws IOException {
        super.func_146282_l();
    }

    public void func_146281_b() {
        this.field_146297_k.field_71460_t.func_175071_c();
    }
}

