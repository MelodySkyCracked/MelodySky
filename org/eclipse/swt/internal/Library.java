/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.jar.Attributes;
import org.eclipse.swt.internal.Platform;

public class Library {
    static int MAJOR_VERSION = 4;
    static int MINOR_VERSION = 952;
    static int REVISION = 11;
    public static final int JAVA_VERSION;
    public static final int SWT_VERSION;
    public static final String USER_HOME;
    static final String SEPARATOR;
    static final String DELIMITER;
    static final String JAVA_LIB_PATH = "java.library.path";
    static final String SWT_LIB_PATH = "swt.library.path";
    static final String SUFFIX_64 = "-64";
    static final String SWT_LIB_DIR;

    static String arch() {
        String string = System.getProperty("os.arch");
        if (string.equals("amd64")) {
            return "x86_64";
        }
        return string;
    }

    static String os() {
        String string = System.getProperty("os.name");
        if (string.equals("Linux")) {
            return "linux";
        }
        if (string.equals("Mac OS X")) {
            return "macosx";
        }
        if (string.startsWith("Win")) {
            return "win32";
        }
        return string;
    }

    static void chmod(String string, String string2) {
        if (Library.os().equals("win32")) {
            return;
        }
        try {
            Runtime.getRuntime().exec(new String[]{"chmod", string, string2}).waitFor();
        }
        catch (Throwable throwable) {
            try {
                new File(string2).setExecutable(true);
            }
            catch (Throwable throwable2) {
                // empty catch block
            }
        }
    }

    static long longConst() {
        return 0x1FFFFFFFFL;
    }

    static int parseVersion(String string) {
        int n;
        if (string == null) {
            return 0;
        }
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = string.length();
        int n6 = 0;
        for (n = 0; n < n5 && Character.isDigit(string.charAt(n)); ++n) {
        }
        try {
            if (n6 < n5) {
                n2 = Integer.parseInt(string.substring(n6, n));
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        n6 = ++n;
        while (n < n5 && Character.isDigit(string.charAt(n))) {
            ++n;
        }
        try {
            if (n6 < n5) {
                n3 = Integer.parseInt(string.substring(n6, n));
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        n6 = ++n;
        while (n < n5 && Character.isDigit(string.charAt(n))) {
            ++n;
        }
        try {
            if (n6 < n5) {
                n4 = Integer.parseInt(string.substring(n6, n));
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return Library.JAVA_VERSION(n2, n3, n4);
    }

    public static int JAVA_VERSION(int n, int n2, int n3) {
        return (n << 16) + (n2 << 8) + n3;
    }

    public static int SWT_VERSION(int n, int n2) {
        return n * 1000 + n2;
    }

    static boolean isLoadable() {
        Object object;
        Object object2;
        URL uRL = Platform.class.getClassLoader().getResource("org/eclipse/swt/internal/Library.class");
        if (!uRL.getProtocol().equals("jar")) {
            return true;
        }
        Attributes attributes = null;
        try {
            object2 = uRL.openConnection();
            if (!(object2 instanceof JarURLConnection)) {
                return false;
            }
            object = (JarURLConnection)object2;
            attributes = ((JarURLConnection)object).getMainAttributes();
        }
        catch (IOException iOException) {
            return false;
        }
        object2 = Library.os();
        object = Library.arch();
        String string = attributes.getValue("SWT-OS");
        String string2 = attributes.getValue("SWT-Arch");
        return ((String)object).equals(string2) && ((String)object2).equals(string);
    }

    public static void loadLibrary(String string) {
        Library.loadLibrary(string, true);
    }

    /*
     * Exception decompiling
     */
    public static void loadLibrary(String var0, boolean var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl107 : ILOAD - null : trying to set 1 previously set to 0
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

    static String mapLibraryName(String string) {
        return Library.mapLibraryName(string, true);
    }

    static String mapLibraryName(String string, boolean bl) {
        string = System.mapLibraryName(string);
        String string2 = ".dylib";
        if (string.endsWith(".dylib") && bl) {
            string = string.substring(0, string.length() - 6) + ".jnilib";
        }
        return string;
    }

    public static String getVersionString() {
        String string = System.getProperty("swt.version");
        if (string == null) {
            string = "" + MAJOR_VERSION;
            if (MINOR_VERSION < 10) {
                string = string + "00";
            } else if (MINOR_VERSION < 100) {
                // empty if block
            }
            string = string + MINOR_VERSION;
            if (REVISION > 0) {
                string = string + "r" + REVISION;
            }
        }
        return string;
    }

    static {
        DELIMITER = System.lineSeparator();
        SEPARATOR = File.separator;
        USER_HOME = System.getProperty("user.home");
        SWT_LIB_DIR = ".swt" + SEPARATOR + "lib" + SEPARATOR + Library.os() + SEPARATOR + Library.arch();
        JAVA_VERSION = Library.parseVersion(System.getProperty("java.version"));
        SWT_VERSION = Library.SWT_VERSION(MAJOR_VERSION, MINOR_VERSION);
    }
}

