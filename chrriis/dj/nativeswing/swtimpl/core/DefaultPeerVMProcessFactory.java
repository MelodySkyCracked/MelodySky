/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.core;

import chrriis.dj.nativeswing.common.SystemProperty;
import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.swtimpl.NSSystemPropertySWT;
import chrriis.dj.nativeswing.swtimpl.PeerVMProcessFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class DefaultPeerVMProcessFactory
implements PeerVMProcessFactory {
    /*
     * WARNING - void declaration
     */
    @Override
    public Process createProcess(String[] stringArray, Map map, String[] stringArray2, String string, String[] stringArray3) {
        int n;
        int n2;
        Object object;
        String string2 = SystemProperty.PATH_SEPARATOR.get();
        String[] stringArray4 = new String[]{new File(SystemProperty.JAVA_HOME.get(), "bin/java").getAbsolutePath(), new File("/usr/lib/java").getAbsolutePath(), "java"};
        boolean bl = true;
        for (String object22 : stringArray2) {
            if (!object22.startsWith("-Xbootclasspath/a:")) continue;
            bl = false;
            break;
        }
        String string3 = SystemProperty.JAVA_VERSION.get();
        String string4 = null;
        if (bl && string3 != null && string3.compareTo("1.6.0_10") >= 0 && "Sun Microsystems Inc.".equals(SystemProperty.JAVA_VENDOR.get())) {
            String string5 = SystemProperty.JAVA_HOME.get();
            File[] fileArray = new File[]{new File(string5, "lib/deploy.jar"), new File(string5, "lib/plugin.jar"), new File(string5, "lib/javaws.jar")};
            object = new StringBuilder();
            for (n2 = 0; n2 < fileArray.length; ++n2) {
                File file;
                if (n2 != 0) {
                    ((StringBuilder)object).append(string2);
                }
                if (!(file = fileArray[n2]).exists()) continue;
                ((StringBuilder)object).append(file.getAbsolutePath());
            }
            string4 = ((StringBuilder)object).indexOf(" ") != -1 ? "\"-Xbootclasspath/a:" + object + "\"" : "-Xbootclasspath/a:" + object;
        } else {
            bl = false;
        }
        int n3 = n = bl ? 1 : 0;
        while (n >= 0) {
            ArrayList<String> arrayList = new ArrayList<String>();
            int n4 = 0;
            object = stringArray4;
            n2 = ((String[])object).length;
            if (n4 < n2) {
                void var18_29;
                String string5 = object[n4];
                arrayList.add(string5);
                if (n == 1) {
                    arrayList.add(string4);
                }
                for (String string6 : stringArray2) {
                    arrayList.add(string6);
                }
                for (Map.Entry entry : map.entrySet()) {
                    String string7 = (String)entry.getValue();
                    if (Utils.IS_WINDOWS) {
                        string7 = string7.replace("\\\"", "\"").replace("\"", "\\\"");
                    }
                    arrayList.add("-D" + (String)entry.getKey() + "=" + string7);
                }
                arrayList.add("-classpath");
                StringBuilder stringBuilder = new StringBuilder();
                boolean bl2 = false;
                while (var18_29 < stringArray.length) {
                    if (var18_29 > 0) {
                        stringBuilder.append(string2);
                    }
                    stringBuilder.append(stringArray[var18_29]);
                    ++var18_29;
                }
                arrayList.add(stringBuilder.toString());
                arrayList.add(string);
                for (String string8 : stringArray3) {
                    arrayList.add(string8);
                }
                if (Boolean.parseBoolean(NSSystemPropertySWT.PEERVM_DEBUG_PRINTCOMMANDLINE.get())) {
                    System.err.println("Native Command: " + Arrays.toString(arrayList.toArray()));
                }
                try {
                    return new ProcessBuilder(arrayList).start();
                }
                catch (IOException iOException) {
                    throw new IllegalStateException(iOException);
                }
            }
            --n;
        }
        return null;
    }
}

