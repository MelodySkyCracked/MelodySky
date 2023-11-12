/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Event;

public class AALAPI {
    private static String username;

    public static String getUsername() {
        if (username == null) {
            username = AALAPI.getUsernameUncached();
        }
        return username;
    }

    private static String getUsernameUncached() {
        Class<?> clazz;
        try {
            clazz = Class.forName("net.aal.API");
        }
        catch (ClassNotFoundException classNotFoundException) {
            clazz = null;
        }
        if (clazz == null) {
            return "debug-mode";
        }
        try {
            return (String)clazz.getMethod("getUsername", new Class[0]).invoke(null, new Object[0]);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return "error-getting-username";
        }
    }
}

