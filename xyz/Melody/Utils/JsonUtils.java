/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package xyz.Melody.Utils;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import xyz.Melody.Utils.lI;

public class JsonUtils {
    private JsonObject object;

    public JsonUtils(JsonObject jsonObject) {
        this.object = jsonObject;
    }

    public int getInt(String string) {
        JsonElement jsonElement = this.object.get(string);
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return 0;
        }
        return jsonElement.getAsInt();
    }

    public double getDouble(String string) {
        JsonElement jsonElement = this.object.get(string);
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return 0.0;
        }
        return jsonElement.getAsDouble();
    }

    public long getLong(String string) {
        JsonElement jsonElement = this.object.get(string);
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return 0L;
        }
        return jsonElement.getAsLong();
    }

    public String getString(String string) {
        JsonElement jsonElement = this.object.get(string);
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        return jsonElement.getAsString();
    }

    public JsonObject getJsonObject(String string) {
        JsonElement jsonElement = this.object.get(string);
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        return jsonElement.getAsJsonObject();
    }

    public List getList(String string) {
        ArrayList arrayList = Lists.newArrayList();
        JsonElement jsonElement = this.object.get(string);
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return arrayList;
        }
        for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
            arrayList.add(jsonElement2.getAsString());
        }
        return arrayList;
    }

    public static String get(String string) {
        String string2 = "";
        try {
            String string3 = string;
            URL uRL = new URL(string3);
            HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String string4 = bufferedReader.readLine();
            while (string4 != null) {
                string2 = String.valueOf(String.valueOf(string2)) + string4 + "\n";
                string4 = bufferedReader.readLine();
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

    public static JsonObject readFromUrl(String string) {
        try {
            return JsonUtils.readFromUrl(string, "User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static JsonObject readFromUrl(String string, String string2, String string3) throws Exception {
        BufferedReader bufferedReader;
        StringBuilder stringBuilder;
        block7: {
            block8: {
                stringBuilder = new StringBuilder();
                URL uRL = new URL(string);
                TrustManager[] trustManagerArray = new TrustManager[]{new lI()};
                SSLContext sSLContext = SSLContext.getInstance("SSL");
                sSLContext.init(null, trustManagerArray, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
                HostnameVerifier hostnameVerifier = JsonUtils::lambda$readFromUrl$0;
                HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection)uRL.openConnection();
                if (string2 != null && string3 != null) {
                    httpsURLConnection.setRequestProperty(string2, string3);
                }
                httpsURLConnection.setRequestMethod("GET");
                bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                Throwable throwable = null;
                try {
                    String string4;
                    while ((string4 = bufferedReader.readLine()) != null) {
                        stringBuilder.append(string4);
                    }
                    if (bufferedReader == null) break block7;
                    if (throwable == null) break block8;
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                try {
                    bufferedReader.close();
                }
                catch (Throwable throwable3) {
                    throwable.addSuppressed(throwable3);
                }
                break block7;
            }
            bufferedReader.close();
        }
        bufferedReader = new JsonParser();
        return (JsonObject)bufferedReader.parse(stringBuilder.toString());
    }

    private static boolean lambda$readFromUrl$0(String string, SSLSession sSLSession) {
        return true;
    }
}

