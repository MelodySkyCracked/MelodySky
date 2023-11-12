/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.common;

import chrriis.dj.nativeswing.common.lIl;
import chrriis.dj.nativeswing.common.ll;
import chrriis.dj.nativeswing.common.llI;
import java.security.AccessController;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class SystemProperty
extends Enum {
    public static final /* enum */ SystemProperty COM_IBM_VM_BITMODE = new SystemProperty("COM_IBM_VM_BITMODE", 0, "com.ibm.vm.bitmode");
    public static final /* enum */ SystemProperty FILE_ENCODING = new SystemProperty("FILE_ENCODING", 1, "file.encoding");
    public static final /* enum */ SystemProperty FILE_ENCODING_PKG = new SystemProperty("FILE_ENCODING_PKG", 2, "file.encoding.pkg");
    public static final /* enum */ SystemProperty FILE_SEPARATOR = new SystemProperty("FILE_SEPARATOR", 3, "file.separator");
    public static final /* enum */ SystemProperty JAVA_AWT_GRAPHICSENV = new SystemProperty("JAVA_AWT_GRAPHICSENV", 4, "java.awt.graphicsenv");
    public static final /* enum */ SystemProperty JAVA_AWT_PRINTERJOB = new SystemProperty("JAVA_AWT_PRINTERJOB", 5, "java.awt.printerjob");
    public static final /* enum */ SystemProperty JAVA_AWT_SMARTINVALIDATE = new SystemProperty("JAVA_AWT_SMARTINVALIDATE", 6, "java.awt.smartInvalidate");
    public static final /* enum */ SystemProperty JAVA_CLASS_PATH = new SystemProperty("JAVA_CLASS_PATH", 7, "java.class.path");
    public static final /* enum */ SystemProperty JAVA_CLASS_VERSION = new SystemProperty("JAVA_CLASS_VERSION", 8, "java.class.version");
    public static final /* enum */ SystemProperty JAVA_ENDORSED_DIRS = new SystemProperty("JAVA_ENDORSED_DIRS", 9, "java.endorsed.dirs");
    public static final /* enum */ SystemProperty JAVA_EXT_DIRS = new SystemProperty("JAVA_EXT_DIRS", 10, "java.ext.dirs");
    public static final /* enum */ SystemProperty JAVA_HOME = new SystemProperty("JAVA_HOME", 11, "java.home");
    public static final /* enum */ SystemProperty JAVA_IO_TMPDIR = new ll("java.io.tmpdir", Type.READ_WRITE);
    public static final /* enum */ SystemProperty JAVA_LIBRARY_PATH = new SystemProperty("JAVA_LIBRARY_PATH", 13, "java.library.path");
    public static final /* enum */ SystemProperty JAVA_RUNTIME_NAME = new SystemProperty("JAVA_RUNTIME_NAME", 14, "java.runtime.name");
    public static final /* enum */ SystemProperty JAVA_RUNTIME_VERSION = new SystemProperty("JAVA_RUNTIME_VERSION", 15, "java.runtime.version");
    public static final /* enum */ SystemProperty JAVA_SPECIFICATION_NAME = new SystemProperty("JAVA_SPECIFICATION_NAME", 16, "java.specification.name");
    public static final /* enum */ SystemProperty JAVA_SPECIFICATION_VENDOR = new SystemProperty("JAVA_SPECIFICATION_VENDOR", 17, "java.specification.vendor");
    public static final /* enum */ SystemProperty JAVA_SPECIFICATION_VERSION = new SystemProperty("JAVA_SPECIFICATION_VERSION", 18, "java.specification.version");
    public static final /* enum */ SystemProperty JAVA_VERSION = new SystemProperty("JAVA_VERSION", 19, "java.version");
    public static final /* enum */ SystemProperty JAVA_VENDOR = new SystemProperty("JAVA_VENDOR", 20, "java.vendor");
    public static final /* enum */ SystemProperty JAVA_VENDOR_URL = new SystemProperty("JAVA_VENDOR_URL", 21, "java.vendor.url");
    public static final /* enum */ SystemProperty JAVA_VENDOR_URL_BUG = new SystemProperty("JAVA_VENDOR_URL_BUG", 22, "java.vendor.url.bug");
    public static final /* enum */ SystemProperty JAVAWEBSTART_VERSION = new SystemProperty("JAVAWEBSTART_VERSION", 23, "javawebstart.version");
    public static final /* enum */ SystemProperty JAVA_VM_INFO = new SystemProperty("JAVA_VM_INFO", 24, "java.vm.info");
    public static final /* enum */ SystemProperty JAVA_VM_NAME = new SystemProperty("JAVA_VM_NAME", 25, "java.vm.name");
    public static final /* enum */ SystemProperty JAVA_VM_SPECIFICATION_NAME = new SystemProperty("JAVA_VM_SPECIFICATION_NAME", 26, "java.vm.specification.name");
    public static final /* enum */ SystemProperty JAVA_VM_SPECIFICATION_VENDOR = new SystemProperty("JAVA_VM_SPECIFICATION_VENDOR", 27, "java.vm.specification.vendor");
    public static final /* enum */ SystemProperty JAVA_VM_SPECIFICATION_VERSION = new SystemProperty("JAVA_VM_SPECIFICATION_VERSION", 28, "java.vm.specification.version");
    public static final /* enum */ SystemProperty JAVA_VM_VERSION = new SystemProperty("JAVA_VM_VERSION", 29, "java.vm.version");
    public static final /* enum */ SystemProperty JAVA_VM_VENDOR = new SystemProperty("JAVA_VM_VENDOR", 30, "java.vm.vendor");
    public static final /* enum */ SystemProperty LINE_SEPARATOR = new SystemProperty("LINE_SEPARATOR", 31, "line.separator");
    public static final /* enum */ SystemProperty OS_NAME = new SystemProperty("OS_NAME", 32, "os.name");
    public static final /* enum */ SystemProperty OS_ARCH = new SystemProperty("OS_ARCH", 33, "os.arch");
    public static final /* enum */ SystemProperty OS_VERSION = new SystemProperty("OS_VERSION", 34, "os.version");
    public static final /* enum */ SystemProperty PATH_SEPARATOR = new SystemProperty("PATH_SEPARATOR", 35, "path.separator");
    public static final /* enum */ SystemProperty SUN_ARCH_DATA_MODEL = new SystemProperty("SUN_ARCH_DATA_MODEL", 36, "sun.arch.data.model");
    public static final /* enum */ SystemProperty SUN_BOOT_CLASS_PATH = new SystemProperty("SUN_BOOT_CLASS_PATH", 37, "sun.boot.class.path");
    public static final /* enum */ SystemProperty SUN_BOOT_LIBRARY_PATH = new SystemProperty("SUN_BOOT_LIBRARY_PATH", 38, "sun.boot.library.path");
    public static final /* enum */ SystemProperty SUN_CPU_ENDIAN = new SystemProperty("SUN_CPU_ENDIAN", 39, "sun.cpu.endian");
    public static final /* enum */ SystemProperty SUN_CPU_ISALIST = new SystemProperty("SUN_CPU_ISALIST", 40, "sun.cpu.isalist");
    public static final /* enum */ SystemProperty SUN_IO_UNICODE_ENCODING = new SystemProperty("SUN_IO_UNICODE_ENCODING", 41, "sun.io.unicode.encoding");
    public static final /* enum */ SystemProperty SUN_JAVA_LAUNCHER = new SystemProperty("SUN_JAVA_LAUNCHER", 42, "sun.java.launcher");
    public static final /* enum */ SystemProperty SUN_JNU_ENCODING = new SystemProperty("SUN_JNU_ENCODING", 43, "sun.jnu.encoding");
    public static final /* enum */ SystemProperty SUN_MANAGEMENT_COMPILER = new SystemProperty("SUN_MANAGEMENT_COMPILER", 44, "sun.management.compiler");
    public static final /* enum */ SystemProperty SUN_OS_PATCH_LEVEL = new SystemProperty("SUN_OS_PATCH_LEVEL", 45, "sun.os.patch.level");
    public static final /* enum */ SystemProperty USER_COUNTRY = new SystemProperty("USER_COUNTRY", 46, "user.country");
    public static final /* enum */ SystemProperty USER_DIR = new SystemProperty("USER_DIR", 47, "user.dir");
    public static final /* enum */ SystemProperty USER_HOME = new SystemProperty("USER_HOME", 48, "user.home");
    public static final /* enum */ SystemProperty USER_LANGUAGE = new SystemProperty("USER_LANGUAGE", 49, "user.language");
    public static final /* enum */ SystemProperty USER_NAME = new SystemProperty("USER_NAME", 50, "user.name");
    public static final /* enum */ SystemProperty USER_TIMEZONE = new SystemProperty("USER_TIMEZONE", 51, "user.timezone");
    public static final /* enum */ SystemProperty SUN_AWT_DISABLEMIXING = new SystemProperty("SUN_AWT_DISABLEMIXING", 52, "sun.awt.disableMixing", Type.READ_WRITE);
    public static final /* enum */ SystemProperty SUN_AWT_NOERASEBACKGROUND = new SystemProperty("SUN_AWT_NOERASEBACKGROUND", 53, "sun.awt.noerasebackground", Type.READ_WRITE);
    public static final /* enum */ SystemProperty SUN_AWT_XEMBEDSERVER = new SystemProperty("SUN_AWT_XEMBEDSERVER", 54, "sun.awt.xembedserver", Type.READ_WRITE);
    public static final /* enum */ SystemProperty SUN_DESKTOP = new SystemProperty("SUN_DESKTOP", 55, "sun.desktop");
    public static final /* enum */ SystemProperty AWT_NATIVE_DOUBLE_BUFFERING = new SystemProperty("AWT_NATIVE_DOUBLE_BUFFERING", 56, "awt.nativeDoubleBuffering");
    public static final /* enum */ SystemProperty AWT_TOOLKIT = new SystemProperty("AWT_TOOLKIT", 57, "awt.toolkit");
    public static final /* enum */ SystemProperty FTP_NON_PROXY_HOSTS = new SystemProperty("FTP_NON_PROXY_HOSTS", 58, "ftp.nonProxyHosts");
    public static final /* enum */ SystemProperty GOPHER_PROXY_SET = new SystemProperty("GOPHER_PROXY_SET", 59, "gopherProxySet");
    public static final /* enum */ SystemProperty HTTP_NON_PROXY_HOSTS = new SystemProperty("HTTP_NON_PROXY_HOSTS", 60, "http.nonProxyHosts");
    public static final /* enum */ SystemProperty MRJ_VERSION = new SystemProperty("MRJ_VERSION", 61, "mrj.version");
    public static final /* enum */ SystemProperty SOCKS_NON_PROXY_HOSTS = new SystemProperty("SOCKS_NON_PROXY_HOSTS", 62, "socksNonProxyHosts");
    private final String _name;
    private final boolean _readOnly;
    private static final SystemProperty[] $VALUES = new SystemProperty[]{COM_IBM_VM_BITMODE, FILE_ENCODING, FILE_ENCODING_PKG, FILE_SEPARATOR, JAVA_AWT_GRAPHICSENV, JAVA_AWT_PRINTERJOB, JAVA_AWT_SMARTINVALIDATE, JAVA_CLASS_PATH, JAVA_CLASS_VERSION, JAVA_ENDORSED_DIRS, JAVA_EXT_DIRS, JAVA_HOME, JAVA_IO_TMPDIR, JAVA_LIBRARY_PATH, JAVA_RUNTIME_NAME, JAVA_RUNTIME_VERSION, JAVA_SPECIFICATION_NAME, JAVA_SPECIFICATION_VENDOR, JAVA_SPECIFICATION_VERSION, JAVA_VERSION, JAVA_VENDOR, JAVA_VENDOR_URL, JAVA_VENDOR_URL_BUG, JAVAWEBSTART_VERSION, JAVA_VM_INFO, JAVA_VM_NAME, JAVA_VM_SPECIFICATION_NAME, JAVA_VM_SPECIFICATION_VENDOR, JAVA_VM_SPECIFICATION_VERSION, JAVA_VM_VERSION, JAVA_VM_VENDOR, LINE_SEPARATOR, OS_NAME, OS_ARCH, OS_VERSION, PATH_SEPARATOR, SUN_ARCH_DATA_MODEL, SUN_BOOT_CLASS_PATH, SUN_BOOT_LIBRARY_PATH, SUN_CPU_ENDIAN, SUN_CPU_ISALIST, SUN_IO_UNICODE_ENCODING, SUN_JAVA_LAUNCHER, SUN_JNU_ENCODING, SUN_MANAGEMENT_COMPILER, SUN_OS_PATCH_LEVEL, USER_COUNTRY, USER_DIR, USER_HOME, USER_LANGUAGE, USER_NAME, USER_TIMEZONE, SUN_AWT_DISABLEMIXING, SUN_AWT_NOERASEBACKGROUND, SUN_AWT_XEMBEDSERVER, SUN_DESKTOP, AWT_NATIVE_DOUBLE_BUFFERING, AWT_TOOLKIT, FTP_NON_PROXY_HOSTS, GOPHER_PROXY_SET, HTTP_NON_PROXY_HOSTS, MRJ_VERSION, SOCKS_NON_PROXY_HOSTS};

    public static SystemProperty[] values() {
        return (SystemProperty[])$VALUES.clone();
    }

    public static SystemProperty valueOf(String string) {
        return Enum.valueOf(SystemProperty.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private SystemProperty() {
        this((String)var1_-1, (int)var2_-1, (String)var3_1, Type.READ_ONLY);
        void var3_1;
        void var2_-1;
        void var1_-1;
    }

    /*
     * WARNING - void declaration
     */
    private SystemProperty() {
        void var4_2;
        String string3;
        void var2_-1;
        void var1_-1;
        if (string3 == null) {
            throw new NullPointerException("name");
        }
        if ("".equals(string3 = string3.trim())) {
            throw new IllegalArgumentException();
        }
        this._name = string3;
        this._readOnly = var4_2 == Type.READ_ONLY;
    }

    public String get() {
        return this.get(null);
    }

    public String get(String string) {
        return (String)AccessController.doPrivileged(new lIl(this, string));
    }

    public String set(String string) {
        if (this.isReadOnly()) {
            throw new UnsupportedOperationException(this.getName() + " is a read-only property");
        }
        return (String)AccessController.doPrivileged(new llI(this, string));
    }

    public String getName() {
        return this._name;
    }

    public boolean isReadOnly() {
        return this._readOnly;
    }

    public String toString() {
        return this.get();
    }

    public String toDebugString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name()).append(": ");
        stringBuilder.append(this.getName()).append("=");
        stringBuilder.append(this.get());
        if (this.isReadOnly()) {
            stringBuilder.append(" (read-only)");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] stringArray) {
        TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
        TreeSet<SystemProperty> treeSet = new TreeSet<SystemProperty>();
        treeMap.putAll(System.getProperties());
        for (SystemProperty systemProperty : SystemProperty.values()) {
            System.out.println(systemProperty.toDebugString());
            if (!treeMap.containsKey(systemProperty.getName())) {
                treeSet.add(systemProperty);
            } else {
                treeMap.remove(systemProperty.getName());
            }
            SystemProperty.checkNaming(systemProperty);
        }
        if (treeSet.size() > 0) {
            System.out.println("\n\n### UNKNOWN");
            for (SystemProperty systemProperty : treeSet) {
                System.out.println(systemProperty.toDebugString());
            }
        }
        if (treeMap.size() > 0) {
            System.out.println("\n\n### MISSING");
            for (Map.Entry entry : treeMap.entrySet()) {
                System.out.println(entry);
            }
            System.out.println("\n\n### PLEASE POST THIS AT http://j.mp/props0 or http://j.mp/props1");
            for (Map.Entry entry : treeMap.entrySet()) {
                System.out.println(String.format("\t/**\n\t * %s only: known values: %s\n\t */\n\t%s(\"%s\"),", new Object[]{OS_NAME, entry.getValue(), SystemProperty.toEnumName((String)entry.getKey()), entry.getKey()}));
            }
        }
    }

    private static void checkNaming(SystemProperty systemProperty) {
        String string = SystemProperty.toEnumName(systemProperty.getName());
        if (!systemProperty.name().equals(string)) {
            System.err.println("name missmatch: " + systemProperty.toDebugString() + " (expected " + string + ")");
        }
    }

    private static String toEnumName(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (Character.isUpperCase(c)) {
                stringBuilder.append('_').append(c);
                continue;
            }
            if (c == '.') {
                stringBuilder.append('_');
                continue;
            }
            stringBuilder.append(Character.toUpperCase(c));
        }
        return stringBuilder.toString();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    SystemProperty(ll ll2) {
        this((String)var1_-1, (int)type, (String)((Object)ll2), (Type)var4_3);
        void var4_3;
        void var1_-1;
    }

    private static enum Type {
        READ_WRITE,
        READ_ONLY;

    }
}

