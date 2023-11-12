/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.util.EnumChatFormatting
 *  org.lwjgl.input.Keyboard
 */
package xyz.Melody.System.Melody.Account.gui.RefreshToken;

import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import xyz.Melody.Client;
import xyz.Melody.GUI.ShaderBG.Shaders.BackgroundShader;
import xyz.Melody.System.Melody.Account.altimpl.MicrosoftAlt;
import xyz.Melody.System.Melody.Account.microsoft.MicrosoftLogin;
import xyz.Melody.Utils.shader.GaussianBlur;

public final class AddRefreshAuth
extends GuiScreen {
    private GuiScreen previousScreen;
    public String status = "Refresh Token:";
    private GuiTextField xbltField;
    private ScaledResolution sr;
    private GaussianBlur gb = new GaussianBlur();

    public AddRefreshAuth(GuiScreen guiScreen) {
        this.previousScreen = guiScreen;
    }

    public void func_73866_w_() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.sr = new ScaledResolution(this.field_146297_k);
        this.xbltField = new GuiTextField(1, this.field_146297_k.field_71466_p, this.sr.func_78326_a() / 2 - 100, this.sr.func_78328_b() / 2 - 30, 200, 20);
        this.xbltField.func_146203_f(Short.MAX_VALUE);
        this.xbltField.func_146195_b(true);
        this.field_146292_n.add(new GuiButton(997, this.sr.func_78326_a() / 2 - 100, this.sr.func_78328_b() / 2, 200, 20, "Add"));
        this.field_146292_n.add(new GuiButton(999, this.sr.func_78326_a() / 2 - 100, this.sr.func_78328_b() / 2 + 30, 200, 20, "Back"));
        super.func_73866_w_();
    }

    public void func_146281_b() {
        Keyboard.enableRepeatEvents((boolean)false);
        super.func_146281_b();
    }

    public void func_73863_a(int n, int n2, float f) {
        this.func_146276_q_();
        this.field_146297_k.field_71466_p.func_78276_b(this.status, this.sr.func_78326_a() / 2 - this.field_146297_k.field_71466_p.func_78256_a(this.status) / 2, this.sr.func_78328_b() / 2 - 60, Color.WHITE.getRGB());
        this.xbltField.func_146194_f();
        super.func_73863_a(n, n2, f);
    }

    protected void func_146284_a(GuiButton guiButton) throws IOException {
        if (guiButton.field_146127_k == 997) {
            new Thread(this::lambda$actionPerformed$0, "RefreshToken Authenticator").start();
        }
        if (guiButton.field_146127_k == 999) {
            try {
                this.field_146297_k.func_147108_a(this.previousScreen);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        super.func_146284_a(guiButton);
    }

    protected void func_73869_a(char c, int n) throws IOException {
        this.xbltField.func_146201_a(c, n);
        if (1 == n) {
            this.field_146297_k.func_147108_a(this.previousScreen);
        } else {
            super.func_73869_a(c, n);
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
        this.gb.renderBlur(90.0f);
    }

    private void lambda$actionPerformed$0() {
        this.status = EnumChatFormatting.YELLOW + "Loggingin...";
        try {
            MicrosoftLogin microsoftLogin = new MicrosoftLogin(this.xbltField.func_146179_b(), this);
            if (microsoftLogin.logged) {
                Client.instance.getAccountManager().addAlt(new MicrosoftAlt(microsoftLogin.userName, microsoftLogin.refreshToken, microsoftLogin.msToken, microsoftLogin.xblToken, microsoftLogin.xsts1, microsoftLogin.xsts2, microsoftLogin.accessToken, microsoftLogin.uuid));
                this.status = EnumChatFormatting.GREEN + "Success! " + this.field_146297_k.func_110432_I().func_111285_a();
            }
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            this.status = EnumChatFormatting.RED + "Failed. " + iOException.getClass().getName() + ": " + iOException.getMessage();
        }
    }
}

