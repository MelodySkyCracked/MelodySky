/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.System.Melody.Authentication;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.Security;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import xyz.Melody.Client;
import xyz.Melody.Utils.JsonUtils;

public class MSAC {
    private static Base64.Decoder base64Decoder;
    public static int pt;
    public static String idk;
    private static final String CIPHER_ALGORITHM;

    static {
        CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
        idk = null;
        pt = 0;
        base64Decoder = Base64.getDecoder();
        Security.setProperty("crypto.policy", "unlimited");
    }

    public static void syncFromIDK() {
        String string = JsonUtils.get("http://melody.nigger.cool/ClientConfig/host.txt").replaceAll("\n", "").replaceAll("\r", "");
        if (string == null || string.equals("") || string.contains(" ")) {
            Client.instance.logger.warn("Url1 Failed, trying 2.");
            string = JsonUtils.get("https://lbd.maid.ink/host.txt").replaceAll("\n", "").replaceAll("\r", "");
        }
        Client.instance.logger.info("Crypted: " + string);
        String[] stringArray = MSAC.decode(string).split(":");
        idk = stringArray[0];
        pt = Integer.parseInt(stringArray[1]);
        if (idk != null) {
            Client.instance.logger.info("Decode Successfully.");
        }
    }

    private static String decode(String string) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec("vQPvHDkCWmZuajKSelMdGxLFdfErljIK".getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, (Key)secretKeySpec, new IvParameterSpec("c558Gq0YQK2QUlMc".getBytes()));
            byte[] byArray = base64Decoder.decode(string);
            byte[] byArray2 = cipher.doFinal(byArray);
            return new String(byArray2, StandardCharsets.UTF_8);
        }
        catch (Exception exception) {
            Client.instance.logger.error("Error decoing content.");
            return null;
        }
    }
}

