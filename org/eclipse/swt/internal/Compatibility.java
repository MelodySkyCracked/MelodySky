/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.swt.SWT;

public final class Compatibility {
    private static ResourceBundle msgs = null;

    public static int ceil(int n, int n2) {
        return (int)Math.ceil((float)n / (float)n2);
    }

    public static int round(int n, int n2) {
        return Math.round((float)n / (float)n2);
    }

    public static int pow2(int n) {
        if (n >= 1 && n <= 30) {
            return 2 << n - 1;
        }
        if (n != 0) {
            SWT.error(6);
        }
        return 1;
    }

    public static void exec(String[] stringArray, String[] stringArray2, String string) throws IOException {
        Runtime.getRuntime().exec(stringArray, null, string != null ? new File(string) : null);
    }

    public static String getMessage(String string) {
        String string2 = string;
        if (string == null) {
            SWT.error(4);
        }
        if (msgs == null) {
            try {
                msgs = ResourceBundle.getBundle("org.eclipse.swt.internal.SWTMessages");
            }
            catch (MissingResourceException missingResourceException) {
                string2 = string + " (no resource bundle)";
            }
        }
        if (msgs != null) {
            try {
                string2 = msgs.getString(string);
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
        return string2;
    }

    public static String getMessage(String string, Object[] objectArray) {
        String string2 = string;
        if (string == null || objectArray == null) {
            SWT.error(4);
        }
        if (msgs == null) {
            try {
                msgs = ResourceBundle.getBundle("org.eclipse.swt.internal.SWTMessages");
            }
            catch (MissingResourceException missingResourceException) {
                string2 = string + " (no resource bundle)";
            }
        }
        if (msgs != null) {
            try {
                MessageFormat messageFormat = new MessageFormat("");
                messageFormat.applyPattern(msgs.getString(string));
                string2 = messageFormat.format(objectArray);
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
        return string2;
    }
}

