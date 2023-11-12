/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.player.EntityPlayerMP
 */
package xyz.Melody.System.Managers.GaoNeng;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import xyz.Melody.Client;

public final class GaoNengManager {
    public static volatile Map gaoNengs = new HashMap();
    public static Thread scanTimerThread;
    public static volatile Map loading_gaoNengs;

    static {
        loading_gaoNengs = new HashMap();
    }

    public static void loadGaoNengs() throws IOException {
        String string = GaoNengManager.get("http://melody.nigger.cool/ClientConfig/Rank.json");
        if (string == null) {
            Client.instance.logger.error("Result cannot be 'null'.");
            return;
        }
        JsonObject jsonObject = (JsonObject)new JsonParser().parse(string);
        if (jsonObject == null) {
            return;
        }
        JsonObject jsonObject2 = jsonObject.getAsJsonObject();
        Iterator iterator = jsonObject2.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            String string2 = (String)entry.getKey();
            GaoNeng gaoNeng = (GaoNeng)loading_gaoNengs.get(string2);
            boolean bl = gaoNeng == null;
            JsonObject jsonObject3 = ((JsonElement)entry.getValue()).getAsJsonObject();
            String string3 = jsonObject3.get("Name").getAsString();
            String string4 = jsonObject3.get("UUID").getAsString();
            String string5 = jsonObject3.get("Reason").getAsString();
            String string6 = jsonObject3.get("Checker").getAsString();
            String string7 = jsonObject3.get("Rank").getAsString();
            String string8 = jsonObject3.get("Time").getAsString();
            String string9 = jsonObject3.get("Bilibili").getAsString();
            String string10 = jsonObject3.get("QQ").getAsString();
            String string11 = jsonObject3.get("Phone").getAsString();
            boolean bl2 = jsonObject3.get("Tag").getAsBoolean();
            if (bl) {
                gaoNeng = new GaoNeng(string3, string4, string5, string6, string7, string8, string9, string10, string11, bl2);
                loading_gaoNengs.put(entry.getKey(), gaoNeng);
            }
            return;
        }
    }

    private static String get(String string) throws IOException {
        String string2 = "";
        try {
            URL uRL = new URL(string);
            HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String string3 = bufferedReader.readLine();
            if (string3 != null) {
                string2 = String.valueOf(String.valueOf(string2)) + string3 + "\n";
                string3 = bufferedReader.readLine();
                return null;
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        }
        catch (Exception exception) {
            Throwable throwable = new Throwable(exception.getMessage());
            StackTraceElement[] stackTraceElementArray = new StackTraceElement[]{};
            throwable.setStackTrace(stackTraceElementArray);
            throwable.printStackTrace();
        }
        return string2;
    }

    public static void registerTimer() {
        scanTimerThread = new Thread(GaoNengManager::lambda$registerTimer$0);
        scanTimerThread.setDaemon(true);
        scanTimerThread.setName("Melody -> GaoNeng Thread");
        scanTimerThread.start();
    }

    private static String getPlayerUUID(EntityOtherPlayerMP entityOtherPlayerMP) {
        if (entityOtherPlayerMP == null) {
            return "none";
        }
        String string = EntityPlayerMP.func_146094_a((GameProfile)entityOtherPlayerMP.func_146103_bH()).toString();
        String string2 = string.replaceAll("-", "");
        return string2;
    }

    public static GaoNeng getIfIsGaoNeng(EntityPlayerSP entityPlayerSP) {
        if (gaoNengs.containsKey(EntityPlayerSP.func_146094_a((GameProfile)entityPlayerSP.func_146103_bH()).toString().replaceAll("-", ""))) {
            return (GaoNeng)gaoNengs.get(EntityPlayerSP.func_146094_a((GameProfile)entityPlayerSP.func_146103_bH()).toString().replaceAll("-", ""));
        }
        return null;
    }

    public static GaoNeng getIfIsGaoNeng(EntityOtherPlayerMP entityOtherPlayerMP) {
        if (gaoNengs.containsKey(GaoNengManager.getPlayerUUID(entityOtherPlayerMP))) {
            return (GaoNeng)gaoNengs.get(GaoNengManager.getPlayerUUID(entityOtherPlayerMP));
        }
        return null;
    }

    public static void load() {
        try {
            GaoNengManager.loadGaoNengs();
            gaoNengs = loading_gaoNengs;
            loading_gaoNengs = new HashMap();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void lambda$registerTimer$0() {
        int n = 3600000;
        GaoNengManager.load();
        try {
            Thread.sleep(n);
            GaoNengManager.load();
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public static GaoNeng getIfIsGaoNeng(String string) {
        if (gaoNengs.containsKey(string)) {
            return (GaoNeng)gaoNengs.get(string);
        }
        return null;
    }

    public static class GaoNeng {
        private String reason;
        private String checker;
        private boolean realBlackList;
        private String name;
        private String uuid;
        private String phone;
        private String bilibili;
        private String time;
        private String rank;
        private String qq;

        public String getBilibili() {
            return this.bilibili;
        }

        public String getUuid() {
            return this.uuid;
        }

        public String getRank() {
            return this.rank;
        }

        public String getQQ() {
            return this.qq;
        }

        public String getName() {
            return this.name;
        }

        public String getReason() {
            return this.reason;
        }

        public String getChecker() {
            return this.checker;
        }

        public boolean isRealBlackList() {
            return this.realBlackList;
        }

        public GaoNeng(String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, boolean bl) {
            this.name = string;
            this.uuid = string2;
            this.reason = string3;
            this.checker = string4;
            this.rank = string5;
            this.time = string6;
            this.bilibili = string7;
            this.qq = string8;
            this.phone = string9;
            this.realBlackList = bl;
        }

        public String getPhone() {
            return this.phone;
        }

        public String getTime() {
            return this.time;
        }
    }
}

