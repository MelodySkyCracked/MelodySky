/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiLanguage
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiMultiplayer
 *  net.minecraft.client.gui.GuiOptions
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiSelectWorld
 *  net.minecraft.client.gui.GuiYesNoCallback
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.realms.RealmsBridge
 *  net.minecraft.util.ResourceLocation
 */
package xyz.Melody.GUI.Menu;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Method;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import xyz.Melody.Client;
import xyz.Melody.GUI.Animate.NonlinearOpacity;
import xyz.Melody.GUI.Animate.Opacity;
import xyz.Melody.GUI.ClickGui.DickGui;
import xyz.Melody.GUI.ClientButton;
import xyz.Melody.GUI.Menu.GuiChangeLog;
import xyz.Melody.GUI.Menu.GuiHideMods;
import xyz.Melody.GUI.Menu.GuiWelcome;
import xyz.Melody.GUI.Particles.Physic.PhysicalParticle;
import xyz.Melody.GUI.Particles.Winter.ParticleEngine;
import xyz.Melody.GUI.ShaderBG.Shaders.BackgroundShader;
import xyz.Melody.System.Melody.Account.GuiAltManager;
import xyz.Melody.Utils.Browser;
import xyz.Melody.Utils.shader.GaussianBlur;
import xyz.Melody.Utils.timer.TimerUtil;

public final class MainMenu
extends GuiScreen
implements GuiYesNoCallback {
    private TimerUtil restTimer = new TimerUtil();
    private String title = "";
    private boolean m = false;
    private boolean e = false;
    private boolean l = false;
    private boolean o = false;
    private boolean d = false;
    private boolean y = false;
    private TimerUtil timer = new TimerUtil();
    private TimerUtil dick = new TimerUtil();
    private TimerUtil saveTimer = new TimerUtil();
    private int letterDrawn = 0;
    private boolean titleDone = false;
    private ParticleEngine winterParticles = new ParticleEngine();
    private Opacity opacity;
    private boolean CO = false;
    private GaussianBlur gblur = new GaussianBlur();
    private NonlinearOpacity wtffb = new NonlinearOpacity(0);

    public MainMenu(int n) {
        this.opacity = new Opacity(n);
        this.CO = true;
    }

    public MainMenu() {
        this.CO = false;
    }

    public void func_73866_w_() {
        if (Client.firstMenu && Client.instance.authManager.verified) {
            this.field_146297_k.func_147108_a((GuiScreen)new GuiWelcome());
        }
        int n = (int)(this.CO ? this.opacity.getOpacity() : 140.0f);
        this.opacity = new Opacity(n);
        this.CO = false;
        this.m = false;
        this.e = false;
        this.l = false;
        this.o = false;
        this.d = false;
        this.y = false;
        this.winterParticles.particles.clear();
        this.restTimer.reset();
        this.timer.reset();
        this.letterDrawn = 0;
        this.title = "";
        this.titleDone = false;
        this.field_146292_n.add(new ClientButton(0, this.field_146294_l / 2 - 80, this.field_146295_m / 2 - 60, 160, 20, "Single Player", null, new Color(20, 20, 20, 80)));
        this.field_146292_n.add(new ClientButton(1, this.field_146294_l / 2 - 80, this.field_146295_m / 2 - 36, 160, 20, "Multi Player", null, new Color(20, 20, 20, 80)));
        this.field_146292_n.add(new ClientButton(2, this.field_146294_l / 2 - 80, this.field_146295_m / 2 - 12, 160, 20, "Config Manager", null, new Color(20, 20, 20, 80)));
        this.field_146292_n.add(new ClientButton(3, this.field_146294_l / 2 - 80, this.field_146295_m / 2 + 12, 160, 20, "Settings", null, new Color(20, 20, 20, 80)));
        this.field_146292_n.add(new ClientButton(5, this.field_146294_l / 2 + 2, this.field_146295_m / 2 + 36, 78, 18, "ChangeLogs", null, new Color(20, 20, 20, 80)));
        this.field_146292_n.add(new ClientButton(15, this.field_146294_l / 2 - 80, this.field_146295_m / 2 + 36, 78, 18, "Languages", null, new Color(20, 20, 20, 80)));
        this.field_146292_n.add(new ClientButton(50, this.field_146294_l - 5, this.field_146295_m - 5, this.field_146294_l + 8, this.field_146295_m + 8, "", null, new Color(0, 0, 0, 0)));
        this.field_146292_n.add(new ClientButton(16, this.field_146294_l / 2 - 102, this.field_146295_m / 2 + 36, 18, 18, "", new ResourceLocation("Melody/icon/realms.png"), new ResourceLocation("Melody/icon/realms_hovered.png"), -3.0f, -3.0f, 12.0f, new Color(20, 20, 20, 80)));
        this.field_146292_n.add(new ClientButton(10, this.field_146294_l - 43, this.field_146295_m - 40, 32, 32, "", new ResourceLocation("Melody/icon/discord.png"), new Color(20, 20, 20, 0)));
        this.field_146292_n.add(new ClientButton(11, this.field_146294_l - 78, this.field_146295_m - 40, 32, 32, "", new ResourceLocation("Melody/icon/github.png"), new Color(20, 20, 20, 0)));
        this.field_146292_n.add(new ClientButton(12, this.field_146294_l - 113, this.field_146295_m - 40, 32, 32, "", new ResourceLocation("Melody/icon/cnsbtool.png"), -4.0f, -4.0f, 20.0f, new Color(20, 20, 20, 0)));
        this.field_146292_n.add(new ClientButton(13, this.field_146294_l - 148, this.field_146295_m - 40, 32, 32, "", new ResourceLocation("Melody/icon/youtube.png"), new Color(20, 20, 20, 0)));
        this.field_146292_n.add(new ClientButton(4, this.field_146294_l - 100, 10, 60, 24, "Vanilla", null, new Color(20, 20, 20, 80)));
        this.field_146292_n.add(new ClientButton(19198, this.field_146294_l - 165, 10, 60, 24, "Hide Mods", null, new Color(20, 20, 20, 80)));
        this.field_146292_n.add(new ClientButton(14, this.field_146294_l - 10 - 24, 10, 25, 24, "", new ResourceLocation("Melody/icon/exit.png"), new Color(20, 20, 20, 60)));
        PhysicalParticle.phycle.reset(this.field_146294_l, this.field_146295_m);
        super.func_73866_w_();
    }

    protected void func_146284_a(GuiButton guiButton) {
        switch (guiButton.field_146127_k) {
            case 0: {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiSelectWorld((GuiScreen)this));
                break;
            }
            case 1: {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiMultiplayer((GuiScreen)this));
                break;
            }
            case 2: {
                this.field_146297_k.func_147108_a((GuiScreen)DickGui.instance);
                break;
            }
            case 3: {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiOptions((GuiScreen)this, this.field_146297_k.field_71474_y));
                break;
            }
            case 4: {
                Client.vanillaMenu = true;
                this.field_146297_k.func_147108_a((GuiScreen)new GuiMainMenu());
                Client.instance.saveClientSettings();
                break;
            }
            case 5: {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiChangeLog());
                break;
            }
            case 10: {
                try {
                    this.open("https://discord.gg/VnNCJfEyhU");
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case 11: {
                try {
                    this.open("https://github.com/NMSLAndy");
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case 12: {
                try {
                    new Browser("https://cst.msirp.cn/#/Main", "China Skyblock Tool", true, true, false, false, 800, 550, JWebBrowser.useEdgeRuntime());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case 13: {
                try {
                    this.open("https://www.youtube.com/channel/UCM8A_7JEGLyqlUq7I7BwUVQ");
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case 14: {
                this.field_146297_k.func_71400_g();
                break;
            }
            case 15: {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiLanguage((GuiScreen)this, this.field_146297_k.field_71474_y, this.field_146297_k.func_135016_M()));
                break;
            }
            case 16: {
                this.switchToRealms();
                break;
            }
            case 50: {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiAltManager(new MainMenu(), true));
                break;
            }
            case 19198: {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiHideMods(this));
            }
        }
    }

    /*
     * Exception decompiling
     */
    public void func_73863_a(int var1, int var2, float var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl35 : IF_ICMPGE - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    protected void func_73864_a(int n, int n2, int n3) throws IOException {
        this.restTimer.reset();
        super.func_73864_a(n, n2, n3);
    }

    public void func_146281_b() {
        PhysicalParticle.phycle.paused = true;
        super.func_146281_b();
    }

    private void open(String string) throws Exception {
        String string2 = System.getProperty("os.name", "");
        if (string2.startsWith("Windows")) {
            Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + string);
        } else if (string2.startsWith("Mac OS")) {
            Class<?> clazz = Class.forName("com.apple.eio.FileManager");
            Method method = clazz.getDeclaredMethod("openURL", String.class);
            method.invoke(null, string);
        } else {
            String[] stringArray = new String[]{"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape"};
            String string3 = null;
            for (int i = 0; i < stringArray.length && string3 == null; ++i) {
                if (Runtime.getRuntime().exec(new String[]{"which", stringArray[i]}).waitFor() != 0) continue;
                string3 = stringArray[i];
            }
            if (string3 == null) {
                throw new RuntimeException("No Browser Was Found.");
            }
            Runtime.getRuntime().exec(new String[]{string3, string});
        }
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
    }

    /*
     * Exception decompiling
     */
    private void drawMelodySkyLogo() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl185 : IF_ICMPGT - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private void switchToRealms() {
        RealmsBridge realmsBridge = new RealmsBridge();
        realmsBridge.switchToRealms((GuiScreen)this);
    }
}

