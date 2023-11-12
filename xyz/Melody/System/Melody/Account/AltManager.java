/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.Session
 *  net.minecraft.util.StringUtils
 */
package xyz.Melody.System.Melody.Account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraft.util.StringUtils;
import xyz.Melody.System.Managers.Client.FileManager;
import xyz.Melody.System.Melody.Account.AccountEnum;
import xyz.Melody.System.Melody.Account.Alt;
import xyz.Melody.System.Melody.Account.altimpl.MicrosoftAlt;
import xyz.Melody.System.Melody.Account.altimpl.OfflineAlt;
import xyz.Melody.System.Melody.Account.altimpl.OriginalAlt;
import xyz.Melody.System.Melody.Account.altimpl.XBLTokenAlt;
import xyz.Melody.System.Melody.Account.l;
import xyz.Melody.Utils.hodgepodge.io.IOUtils;
import xyz.Melody.injection.mixins.client.MCA;

public final class AltManager {
    private final File ALT_FILE = new File(FileManager.getClientDir(), "Accounts.json");
    private final ArrayList altList = new ArrayList();
    private final JsonParser parser = new JsonParser();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void addAlt(Alt alt) {
        this.altList.add(alt);
    }

    public ArrayList getAltList() {
        return this.altList;
    }

    public void readAlt() {
        this.altList.clear();
        if (this.ALT_FILE.exists()) {
            try {
                String string = IOUtils.inputStreamToString((InputStream)new FileInputStream(this.ALT_FILE), StandardCharsets.UTF_8);
                for (JsonElement jsonElement : this.parser.parse(string).getAsJsonArray()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    AccountEnum accountEnum = AccountEnum.parse(jsonObject.get("AltType").getAsString());
                    String string2 = jsonObject.get("UserName").getAsString();
                    if (accountEnum == null) continue;
                    switch (accountEnum) {
                        case OFFLINE: {
                            this.addAlt(new OfflineAlt(string2));
                            break;
                        }
                        case MICROSOFT: {
                            this.addAlt(new MicrosoftAlt(string2, jsonObject.get("RefreshToken").getAsString(), jsonObject.get("MSAToken").getAsString(), jsonObject.get("XBLToken").getAsString(), jsonObject.get("XSTS_1").getAsString(), jsonObject.get("UHS").getAsString(), jsonObject.get("AccessToken").getAsString(), jsonObject.get("UUID").getAsString()));
                            break;
                        }
                        case ORIGINAL: {
                            this.addAlt(new OriginalAlt(string2, jsonObject.get("AccessToken").getAsString(), jsonObject.get("UUID").getAsString(), jsonObject.get("Type").getAsString()));
                            break;
                        }
                        case XBLTOKEN: {
                            this.addAlt(new XBLTokenAlt(string2, jsonObject.get("XBLToken").getAsString(), jsonObject.get("XSTS_1").getAsString(), jsonObject.get("UHS").getAsString(), jsonObject.get("AccessToken").getAsString(), jsonObject.get("UUID").getAsString(), jsonObject.get("Type").getAsString()));
                        }
                    }
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void saveAlt() {
        JsonArray jsonArray = new JsonArray();
        this.altList.sort(Comparator.comparingDouble(AltManager::lambda$saveAlt$0));
        for (Alt alt : this.altList) {
            Alt alt2;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("AltType", alt.getAccountType().getWriteName());
            jsonObject.addProperty("UserName", alt.getUserName());
            if (alt instanceof MicrosoftAlt) {
                alt2 = (MicrosoftAlt)alt;
                jsonObject.addProperty("RefreshToken", ((MicrosoftAlt)alt2).getRefreshToken());
                jsonObject.addProperty("MSAToken", ((MicrosoftAlt)alt2).getMsToken());
                jsonObject.addProperty("XBLToken", ((MicrosoftAlt)alt2).getXblToken());
                jsonObject.addProperty("XSTS_1", ((MicrosoftAlt)alt2).getXstsToken_f());
                jsonObject.addProperty("UHS", ((MicrosoftAlt)alt2).getXstsToken_s());
                jsonObject.addProperty("AccessToken", ((MicrosoftAlt)alt2).getAccessToken());
                jsonObject.addProperty("UUID", ((MicrosoftAlt)alt2).getUUID());
            } else if (alt instanceof OriginalAlt) {
                alt2 = (OriginalAlt)alt;
                jsonObject.addProperty("Type", ((OriginalAlt)alt2).getType());
                jsonObject.addProperty("AccessToken", ((OriginalAlt)alt2).getAccessToken());
                jsonObject.addProperty("UUID", ((OriginalAlt)alt2).getUUID());
            } else if (alt instanceof XBLTokenAlt) {
                alt2 = (XBLTokenAlt)alt;
                jsonObject.addProperty("Type", ((XBLTokenAlt)alt2).getType());
                jsonObject.addProperty("XBLToken", ((XBLTokenAlt)alt2).getXblToken());
                jsonObject.addProperty("XSTS_1", ((XBLTokenAlt)alt2).getXstsToken_f());
                jsonObject.addProperty("UHS", ((XBLTokenAlt)alt2).getXstsToken_s());
                jsonObject.addProperty("AccessToken", ((XBLTokenAlt)alt2).getAccessToken());
                jsonObject.addProperty("UUID", ((XBLTokenAlt)alt2).getUUID());
            }
            jsonArray.add((JsonElement)jsonObject);
        }
        try {
            String string = this.gson.toJson((JsonElement)jsonArray);
            this.writeStringToFile(string, this.ALT_FILE);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void writeStringToFile(String string, File file) throws IOException {
        block5: {
            FileOutputStream fileOutputStream;
            block6: {
                fileOutputStream = new FileOutputStream(file);
                Throwable throwable = null;
                try {
                    fileOutputStream.write(string.getBytes(StandardCharsets.UTF_8));
                    if (fileOutputStream == null) break block5;
                    if (throwable == null) break block6;
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                try {
                    fileOutputStream.close();
                }
                catch (Throwable throwable3) {
                    throwable.addSuppressed(throwable3);
                }
                break block5;
            }
            fileOutputStream.close();
        }
    }

    public static LoginStatus loginAlt(String string, String string2) throws AuthenticationException {
        if (StringUtils.func_151246_b((String)string2)) {
            ((MCA)Minecraft.func_71410_x()).setSession(new Session(string, "", "", "mojang"));
            return LoginStatus.SUCCESS;
        }
        YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication)yggdrasilAuthenticationService.createUserAuthentication(Agent.MINECRAFT);
        yggdrasilUserAuthentication.setUsername(string);
        yggdrasilUserAuthentication.setPassword(string2);
        yggdrasilUserAuthentication.logIn();
        ((MCA)Minecraft.func_71410_x()).setSession(new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang"));
        return LoginStatus.SUCCESS;
    }

    private static double lambda$saveAlt$0(Alt alt) {
        return -alt.getAccountType().getWriteName().length();
    }

    public static class LoginStatus
    extends Enum {
        public static final /* enum */ LoginStatus FAILED = new LoginStatus("FAILED", 0);
        public static final /* enum */ LoginStatus SUCCESS = new LoginStatus("SUCCESS", 1);
        public static final /* enum */ LoginStatus EXCEPTION = new l();
        private static final LoginStatus[] $VALUES = new LoginStatus[]{FAILED, SUCCESS, EXCEPTION};

        public static LoginStatus[] values() {
            return (LoginStatus[])$VALUES.clone();
        }

        public static LoginStatus valueOf(String string) {
            return Enum.valueOf(LoginStatus.class, string);
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        private LoginStatus() {
            void var2_-1;
            void var1_-1;
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        LoginStatus() {
            this((String)var1_-1, (int)var2_1);
            void var2_1;
            void var1_-1;
        }
    }
}

