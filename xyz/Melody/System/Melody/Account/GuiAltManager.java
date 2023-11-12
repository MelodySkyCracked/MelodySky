/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.IImageBuffer
 *  net.minecraft.client.renderer.ImageBufferDownload
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.ThreadDownloadImageData
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.texture.ITextureObject
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.client.resources.DefaultPlayerSkin
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Session
 *  org.apache.commons.io.IOUtils
 */
package xyz.Melody.System.Melody.Account;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.apache.commons.io.IOUtils;
import xyz.Melody.Client;
import xyz.Melody.GUI.Animate.Opacity;
import xyz.Melody.GUI.ClientButton;
import xyz.Melody.GUI.Menu.MainMenu;
import xyz.Melody.GUI.Notification.NotificationPublisher;
import xyz.Melody.GUI.Notification.NotificationType;
import xyz.Melody.GUI.ShaderBG.Shaders.BackgroundShader;
import xyz.Melody.System.Melody.Account.Alt;
import xyz.Melody.System.Melody.Account.SlidingCalculation;
import xyz.Melody.System.Melody.Account.altimpl.MicrosoftAlt;
import xyz.Melody.System.Melody.Account.altimpl.OriginalAlt;
import xyz.Melody.System.Melody.Account.altimpl.XBLTokenAlt;
import xyz.Melody.System.Melody.Account.gui.AccessToken.AddTokenAuthGui;
import xyz.Melody.System.Melody.Account.gui.AccessToken.TokenAuthGui;
import xyz.Melody.System.Melody.Account.gui.AddOfflineGui;
import xyz.Melody.System.Melody.Account.gui.RefreshToken.AddRefreshAuth;
import xyz.Melody.System.Melody.Account.gui.RefreshToken.RefreshTokenAuth;
import xyz.Melody.System.Melody.Account.gui.XBoxLive.AddXBLTokenAuth;
import xyz.Melody.System.Melody.Account.gui.XBoxLive.XBLTokenAuth;
import xyz.Melody.System.Melody.Account.ll;
import xyz.Melody.System.Melody.Account.microsoft.GuiAddMicrosoftAlt;
import xyz.Melody.System.Melody.Account.microsoft.GuiMicrosoftLogin;
import xyz.Melody.System.Melody.Account.microsoft.MicrosoftLogin;
import xyz.Melody.Utils.render.DrawEntity;
import xyz.Melody.Utils.shader.GaussianBlur;
import xyz.Melody.injection.mixins.client.MCA;

public final class GuiAltManager
extends GuiScreen {
    private GuiScreen parentScreen;
    private SlidingCalculation slidingCalculation = new SlidingCalculation(44.0, 44.0);
    private GuiButton buttonLogin;
    private GuiButton buttonRemove;
    public volatile String status = EnumChatFormatting.YELLOW + "Waitting...";
    private WorldClient world = null;
    private EntityPlayerSP player = null;
    private Opacity opacity = new Opacity(20);
    private boolean shabi = false;
    private GaussianBlur gb = new GaussianBlur();
    private DrawEntity entityDrawer = new DrawEntity();
    private volatile MicrosoftLogin microsoftLogin;
    private static Alt selectAlt;
    private Map avatars = new HashMap();
    private boolean tStarted = false;

    public GuiAltManager(GuiScreen guiScreen) {
        this.parentScreen = guiScreen;
        this.shabi = false;
        Client.instance.rotationPitchHead = 0.0f;
        Client.instance.prevRotationPitchHead = 0.0f;
    }

    public GuiAltManager(GuiScreen guiScreen, boolean bl) {
        this.parentScreen = guiScreen;
        this.shabi = bl;
        Client.instance.rotationPitchHead = 0.0f;
        Client.instance.prevRotationPitchHead = 0.0f;
    }

    public void func_73866_w_() {
        this.opacity = new Opacity(20);
        Client.instance.getAccountManager().getAltList().sort(Comparator.comparingDouble(GuiAltManager::lambda$initGui$0));
        int n = 0;
        this.field_146292_n.add(new ClientButton(0, (int)((float)this.field_146294_l / 1.43f) + n, this.field_146295_m - 44, (int)((float)this.field_146294_l / 9.6f), 20, "Back", new Color(10, 10, 10, 110)));
        this.field_146292_n.add(new ClientButton(1, (int)((float)this.field_146294_l / 1.43f) + n, this.field_146295_m - 66, (int)((float)this.field_146294_l / 9.6f), 20, "Add Offline", new Color(10, 10, 10, 110)));
        this.buttonLogin = new ClientButton(2, (int)((float)this.field_146294_l / 6.66f) + n, this.field_146295_m - 66, (int)((float)this.field_146294_l / 9.6f), 20, "Login", new Color(10, 10, 10, 110));
        this.field_146292_n.add(this.buttonLogin);
        this.buttonRemove = new ClientButton(3, (int)((float)this.field_146294_l / 6.66f) + n, this.field_146295_m - 44, (int)((float)this.field_146294_l / 9.6f), 20, "Remove", new Color(10, 10, 10, 110));
        this.field_146292_n.add(this.buttonRemove);
        this.field_146292_n.add(new ClientButton(5, (int)((float)this.field_146294_l / 2.09f) + n, this.field_146295_m - 66, (int)((float)this.field_146294_l / 9.6f), 20, "Microsoft", new Color(10, 10, 10, 110)));
        this.field_146292_n.add(new ClientButton(6, (int)((float)this.field_146294_l / 2.09f) + n, this.field_146295_m - 44, (int)((float)this.field_146294_l / 9.6f), 20, "Add Microsoft", new Color(10, 10, 10, 110)));
        this.field_146292_n.add(new ClientButton(7, (int)((float)this.field_146294_l / 1.7f) + n, this.field_146295_m - 66, (int)((float)this.field_146294_l / 9.6f), 20, "TokenAuth", new Color(10, 10, 10, 110)));
        this.field_146292_n.add(new ClientButton(8, (int)((float)this.field_146294_l / 1.7f) + n, this.field_146295_m - 44, (int)((float)this.field_146294_l / 9.6f), 20, "Add TokenAuth", new Color(10, 10, 10, 110)));
        this.field_146292_n.add(new ClientButton(11, (int)((float)this.field_146294_l / 3.85f) + n, this.field_146295_m - 66, (int)((float)this.field_146294_l / 9.6f), 20, "XBLToken Auth", new Color(10, 10, 10, 110)));
        this.field_146292_n.add(new ClientButton(12, (int)((float)this.field_146294_l / 3.85f) + n, this.field_146295_m - 44, (int)((float)this.field_146294_l / 9.6f), 20, "Add XBLT Auth", new Color(10, 10, 10, 110)));
        this.field_146292_n.add(new ClientButton(15, (int)((float)this.field_146294_l / 2.7f) + n, this.field_146295_m - 66, (int)((float)this.field_146294_l / 9.6f), 20, "RefreshTkn Auth", new Color(10, 10, 10, 110)));
        this.field_146292_n.add(new ClientButton(16, (int)((float)this.field_146294_l / 2.7f) + n, this.field_146295_m - 44, (int)((float)this.field_146294_l / 9.6f), 20, "Add RefTkn Auth", new Color(10, 10, 10, 110)));
        this.field_146292_n.add(new ClientButton(17, (int)((float)this.field_146294_l / 5.2f + (float)this.field_146294_l / 9.6f + 4.0f) + n, this.field_146295_m - 130, (int)((float)this.field_146294_l / 9.6f), 20, "Cookie Login", new Color(10, 10, 10, 110)));
        this.field_146292_n.add(new ClientButton(18, (int)((float)this.field_146294_l / 5.2f + (float)this.field_146294_l / 9.6f + 4.0f) + n, this.field_146295_m - 153, (int)((float)this.field_146294_l / 9.6f), 20, "Copy UUID:ID", new Color(10, 10, 10, 110)));
        this.field_146292_n.add(new ClientButton(9, (int)((float)((int)((float)this.field_146294_l / 13.0f)) + (float)this.field_146294_l / 9.6f + 4.0f), this.field_146295_m - 130, (int)((float)this.field_146294_l / 9.6f), 20, "Reset Session", new Color(10, 10, 10, 110)));
        this.field_146292_n.add(new ClientButton(10, (int)((float)this.field_146294_l / 14.0f), this.field_146295_m - 130, (int)((float)this.field_146294_l / 9.6f), 20, "Melody Auth", new Color(10, 10, 10, 110)));
        super.func_73866_w_();
    }

    /*
     * Exception decompiling
     */
    public void func_73863_a(int var1, int var2, float var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl485 : ALOAD - null : trying to set 6 previously set to 0
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
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

    public void func_146281_b() {
        Client.instance.getAccountManager().saveAlt();
        super.func_146281_b();
    }

    protected void func_146284_a(GuiButton guiButton) throws IOException {
        if (guiButton.field_146127_k == 0) {
            Client.instance.getAccountManager().saveAlt();
            this.field_146297_k.func_147108_a((GuiScreen)new MainMenu((int)this.opacity.getOpacity()));
        } else if (guiButton.field_146127_k == 1) {
            this.field_146297_k.func_147108_a((GuiScreen)new AddOfflineGui(this));
        } else if (guiButton.field_146127_k == 2) {
            if (selectAlt != null) {
                Thread thread = new Thread(this::lambda$actionPerformed$1, "Authentication Thread");
                thread.start();
            }
        } else if (guiButton.field_146127_k == 3) {
            if (selectAlt != null) {
                Client.instance.getAccountManager().getAltList().remove(selectAlt);
                selectAlt = null;
            }
        } else if (guiButton.field_146127_k == 5) {
            this.field_146297_k.func_147108_a((GuiScreen)new GuiMicrosoftLogin(this));
        } else if (guiButton.field_146127_k == 6) {
            this.field_146297_k.func_147108_a((GuiScreen)new GuiAddMicrosoftAlt(this));
        } else if (guiButton.field_146127_k == 7) {
            this.field_146297_k.func_147108_a((GuiScreen)new TokenAuthGui(this));
        } else if (guiButton.field_146127_k == 8) {
            this.field_146297_k.func_147108_a((GuiScreen)new AddTokenAuthGui(this));
        } else if (guiButton.field_146127_k == 9) {
            try {
                ((MCA)this.field_146297_k).setSession(Client.originalSession);
                this.player = null;
            }
            catch (Exception exception) {
                this.status = "\u00a7cError: Couldn't Restore Session (check mc logs).";
                exception.printStackTrace();
            }
        } else if (guiButton.field_146127_k == 10) {
            new Thread(GuiAltManager::lambda$actionPerformed$2, "MSA").start();
        } else if (guiButton.field_146127_k == 11) {
            this.field_146297_k.func_147108_a((GuiScreen)new XBLTokenAuth(this));
        } else if (guiButton.field_146127_k == 12) {
            this.field_146297_k.func_147108_a((GuiScreen)new AddXBLTokenAuth(this));
        } else if (guiButton.field_146127_k == 15) {
            this.field_146297_k.func_147108_a((GuiScreen)new RefreshTokenAuth(this));
        } else if (guiButton.field_146127_k == 16) {
            this.field_146297_k.func_147108_a((GuiScreen)new AddRefreshAuth(this));
        } else if (guiButton.field_146127_k == 17) {
            this.status = "Waitting For Actions From Browser...";
            new ll(this, "Cookie Login Thread").start();
        } else if (guiButton.field_146127_k == 18) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection stringSelection = new StringSelection(this.field_146297_k.func_110432_I().func_148256_e().getId().toString() + ":" + this.field_146297_k.func_110432_I().func_111285_a());
            clipboard.setContents(stringSelection, null);
            this.status = EnumChatFormatting.GREEN + "Copied UUID:ID To Your Clipboard.";
        }
        super.func_146284_a(guiButton);
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

    private void drawPlayerAvatar(String string, String string2, float f, float f2, float f3) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179124_c((float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179152_a((float)f3, (float)f3, (float)0.0f);
        GlStateManager.func_179109_b((float)(-f3), (float)(-f3), (float)0.0f);
        try {
            ThreadDownloadImageData threadDownloadImageData;
            if (this.avatars.containsKey(string2)) {
                threadDownloadImageData = (ThreadDownloadImageData)this.avatars.get(string2);
            } else {
                threadDownloadImageData = this.getDownloadImageHead(AbstractClientPlayer.func_110311_f((String)string2), string2);
                this.avatars.put(string2, threadDownloadImageData);
            }
            threadDownloadImageData.func_110551_a(Minecraft.func_71410_x().func_110442_L());
            this.field_146297_k.func_110434_K().func_110577_a(AbstractClientPlayer.func_110311_f((String)string2));
            Gui.func_146110_a((int)((int)(f + 18.0f)), (int)((int)(f2 + 18.0f)), (float)37.0f, (float)37.0f, (int)37, (int)37, (float)297.0f, (float)297.0f);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        GlStateManager.func_179121_F();
    }

    public ThreadDownloadImageData getDownloadImageHead(ResourceLocation resourceLocation, String string) {
        TextureManager textureManager = Minecraft.func_71410_x().func_110434_K();
        ThreadDownloadImageData threadDownloadImageData = new ThreadDownloadImageData(null, "https://crafatar.com/skins/" + string, DefaultPlayerSkin.func_177334_a((UUID)AbstractClientPlayer.func_175147_b((String)string)), (IImageBuffer)new ImageBufferDownload());
        textureManager.func_110579_a(resourceLocation, (ITextureObject)threadDownloadImageData);
        return threadDownloadImageData;
    }

    private String getAccessToken(String string, String string2) throws IOException {
        this.status = EnumChatFormatting.YELLOW + "Getting Access Token...";
        Client.instance.logger.info("Getting access token");
        URL uRL = new URL("https://api.minecraftservices.com/authentication/login_with_xbox");
        HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("identityToken", "XBL3.0 x=" + string2 + ";" + string);
        this.write(new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream())), new Gson().toJson((JsonElement)jsonObject));
        JsonObject jsonObject2 = (JsonObject)new JsonParser().parse(this.read(httpURLConnection.getInputStream()));
        return jsonObject2.get("access_token").getAsString();
    }

    public String[] getXSTSTokenAndUserHash(String string) throws IOException {
        this.status = EnumChatFormatting.YELLOW + "Getting XSTS Token and User Info...";
        Client.instance.logger.info("Getting xsts token and user hash");
        URL uRL = new URL("https://xsts.auth.xboxlive.com/xsts/authorize");
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(string);
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("SandboxId", "RETAIL");
        jsonObject2.add("UserTokens", new JsonParser().parse(new Gson().toJson(arrayList)));
        jsonObject.add("Properties", (JsonElement)jsonObject2);
        jsonObject.addProperty("RelyingParty", "rp://api.minecraftservices.com/");
        jsonObject.addProperty("TokenType", "JWT");
        String string2 = new Gson().toJson((JsonElement)jsonObject);
        HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        this.write(new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream())), string2);
        JsonObject jsonObject3 = (JsonObject)new JsonParser().parse(this.read(httpURLConnection.getInputStream()));
        String string3 = jsonObject3.get("Token").getAsString();
        String string4 = jsonObject3.getAsJsonObject("DisplayClaims").getAsJsonArray("xui").get(0).getAsJsonObject().get("uhs").getAsString();
        return new String[]{string3, string4};
    }

    public String getXBoxLiveToken(String string) throws IOException {
        this.status = EnumChatFormatting.YELLOW + "Getting XboxLive Token...";
        Client.instance.logger.info("Getting xbox live token");
        URL uRL = new URL("https://user.auth.xboxlive.com/user/authenticate");
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("AuthMethod", "RPS");
        jsonObject2.addProperty("SiteName", "user.auth.xboxlive.com");
        jsonObject2.addProperty("RpsTicket", "d=" + string);
        jsonObject.add("Properties", (JsonElement)jsonObject2);
        jsonObject.addProperty("RelyingParty", "http://auth.xboxlive.com");
        jsonObject.addProperty("TokenType", "JWT");
        String string2 = new Gson().toJson((JsonElement)jsonObject);
        HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        this.write(new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream())), string2);
        JsonObject jsonObject3 = (JsonObject)new JsonParser().parse(this.read(httpURLConnection.getInputStream()));
        return jsonObject3.get("Token").getAsString();
    }

    private void write(BufferedWriter bufferedWriter, String string) throws IOException {
        bufferedWriter.write(string);
        bufferedWriter.close();
    }

    private String read(InputStream inputStream) throws IOException {
        String string;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        while ((string = bufferedReader.readLine()) != null) {
            stringBuilder.append(string);
        }
        inputStream.close();
        bufferedReader.close();
        return stringBuilder.toString();
    }

    private static void lambda$actionPerformed$2() {
        Client.instance.auth();
    }

    private void lambda$actionPerformed$1() {
        this.status = EnumChatFormatting.YELLOW + "Loggingin...";
        switch (selectAlt.getAccountType()) {
            case OFFLINE: {
                ((MCA)this.field_146297_k).setSession(new Session(selectAlt.getUserName(), "", "", "mojang"));
                this.status = EnumChatFormatting.GREEN + "Success! " + this.field_146297_k.func_110432_I().func_111285_a();
                NotificationPublisher.queue("Account Loggedin!", "Logged in as: " + this.field_146297_k.func_110432_I().func_111285_a(), NotificationType.SUCCESS, 3000);
                break;
            }
            case MICROSOFT: {
                try {
                    MicrosoftLogin microsoftLogin = new MicrosoftLogin(((MicrosoftAlt)selectAlt).getRefreshToken(), this);
                    if (!microsoftLogin.logged) break;
                    ((MCA)this.field_146297_k).setSession(new Session(microsoftLogin.userName, microsoftLogin.uuid, microsoftLogin.accessToken, "mojang"));
                    Client.instance.getAccountManager().getAltList().remove(selectAlt);
                    Client.instance.getAccountManager().addAlt(new MicrosoftAlt(microsoftLogin.userName, microsoftLogin.refreshToken, microsoftLogin.msToken, microsoftLogin.xblToken, microsoftLogin.xsts1, microsoftLogin.xsts2, microsoftLogin.accessToken, microsoftLogin.uuid));
                    this.status = EnumChatFormatting.GREEN + "Success! " + this.field_146297_k.func_110432_I().func_111285_a();
                    NotificationPublisher.queue("Account Loggedin!", "Logged in as: " + this.field_146297_k.func_110432_I().func_111285_a(), NotificationType.SUCCESS, 3000);
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                    this.status = EnumChatFormatting.RED + "Failed. " + iOException.getClass().getName() + ": " + iOException.getMessage();
                }
                break;
            }
            case ORIGINAL: {
                OriginalAlt originalAlt = (OriginalAlt)selectAlt;
                ((MCA)this.field_146297_k).setSession(new Session(originalAlt.getUserName(), originalAlt.getUUID(), originalAlt.getAccessToken(), originalAlt.getType()));
                this.status = EnumChatFormatting.GREEN + "Success! " + this.field_146297_k.func_110432_I().func_111285_a();
                NotificationPublisher.queue("Account Loggedin!", "Logged in as: " + this.field_146297_k.func_110432_I().func_111285_a(), NotificationType.SUCCESS, 3000);
                break;
            }
            case XBLTOKEN: {
                XBLTokenAlt xBLTokenAlt = (XBLTokenAlt)selectAlt;
                try {
                    String string = xBLTokenAlt.getXblToken();
                    String[] stringArray = this.getXSTSTokenAndUserHash(string);
                    String string2 = this.getAccessToken(stringArray[0], stringArray[1]);
                    this.status = "Logging in from AccessToken...";
                    HttpURLConnection httpURLConnection = (HttpURLConnection)new URL("https://api.minecraftservices.com/minecraft/profile/").openConnection();
                    httpURLConnection.setRequestProperty("Content-type", "application/json");
                    httpURLConnection.setRequestProperty("Authorization", "Bearer " + string2);
                    httpURLConnection.setDoOutput(true);
                    JsonObject jsonObject = new JsonParser().parse(IOUtils.toString((InputStream)httpURLConnection.getInputStream())).getAsJsonObject();
                    String string3 = jsonObject.get("name").getAsString();
                    String string4 = stringArray[0];
                    String string5 = stringArray[1];
                    String string6 = jsonObject.get("id").getAsString();
                    String string7 = string2;
                    ((MCA)this.field_146297_k).setSession(new Session(string3, string6, string7, "mojang"));
                    this.status = EnumChatFormatting.GREEN + "Success! " + string3;
                    Client.instance.getAccountManager().getAltList().remove(xBLTokenAlt);
                    xBLTokenAlt = new XBLTokenAlt(string3, string, string4, string5, string7, string6, "mojang");
                    Client.instance.getAccountManager().addAlt(xBLTokenAlt);
                    this.status = EnumChatFormatting.GREEN + "Success! " + this.field_146297_k.func_110432_I().func_111285_a();
                    NotificationPublisher.queue("Account Loggedin!", "Logged in as: " + this.field_146297_k.func_110432_I().func_111285_a(), NotificationType.SUCCESS, 3000);
                }
                catch (Exception exception) {
                    this.status = EnumChatFormatting.RED + "Failed: " + exception.getMessage();
                    exception.printStackTrace();
                }
                break;
            }
        }
        this.tStarted = true;
    }

    private static double lambda$initGui$0(Alt alt) {
        return -alt.getAccountType().toString().length();
    }

    static MicrosoftLogin access$002(GuiAltManager guiAltManager, MicrosoftLogin microsoftLogin) {
        guiAltManager.microsoftLogin = microsoftLogin;
        return guiAltManager.microsoftLogin;
    }

    static MicrosoftLogin access$000(GuiAltManager guiAltManager) {
        return guiAltManager.microsoftLogin;
    }
}

