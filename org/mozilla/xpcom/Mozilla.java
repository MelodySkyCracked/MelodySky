/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.xpcom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import org.mozilla.interfaces.nsIComponentManager;
import org.mozilla.interfaces.nsIComponentRegistrar;
import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsIServiceManager;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.xpcom.GREVersionRange;
import org.mozilla.xpcom.IAppFileLocProvider;
import org.mozilla.xpcom.IGRE;
import org.mozilla.xpcom.IJavaXPCOMUtils;
import org.mozilla.xpcom.IMozilla;
import org.mozilla.xpcom.INIParser;
import org.mozilla.xpcom.IXPCOM;
import org.mozilla.xpcom.IXPCOMError;
import org.mozilla.xpcom.ProfileLock;
import org.mozilla.xpcom.XPCOMException;
import org.mozilla.xpcom.XPCOMInitializationException;

public class Mozilla
implements IMozilla,
IGRE,
IXPCOM,
IJavaXPCOMUtils,
IXPCOMError {
    private static Mozilla mozillaInstance = new Mozilla();
    private static final String JAVAXPCOM_JAR = "javaxpcom.jar";
    private IMozilla mozilla = null;
    private IGRE gre = null;
    private IXPCOM xpcom = null;
    private IJavaXPCOMUtils jxutils = null;

    public static Mozilla getInstance() {
        return mozillaInstance;
    }

    private Mozilla() {
    }

    public static File getGREPathWithProperties(GREVersionRange[] gREVersionRangeArray, Properties properties) throws FileNotFoundException {
        String string = System.getProperty("GRE_HOME");
        if (string != null) {
            File file;
            try {
                file = new File(string).getCanonicalFile();
            }
            catch (IOException iOException) {
                throw new FileNotFoundException("cannot access GRE_HOME");
            }
            if (!file.exists()) {
                throw new FileNotFoundException("GRE_HOME doesn't exist");
            }
            return file;
        }
        if (System.getProperty("USE_LOCAL_GRE") != null) {
            return null;
        }
        if (properties == null) {
            properties = new Properties();
        }
        properties.setProperty("javaxpcom", "1");
        String string2 = System.getProperty("os.name").toLowerCase();
        File file = string2.startsWith("mac os x") ? Mozilla.getGREPathMacOSX(gREVersionRangeArray) : (string2.startsWith("windows") ? Mozilla.getGREPathWindows(gREVersionRangeArray, properties) : Mozilla.getGREPathUnix(gREVersionRangeArray, properties));
        if (file == null) {
            throw new FileNotFoundException("GRE not found");
        }
        return file;
    }

    private static File getGREPathMacOSX(GREVersionRange[] gREVersionRangeArray) {
        File file;
        File file2 = Mozilla.findGREBundleFramework();
        if (file2 != null) {
            return file2;
        }
        String string = System.getProperty("user.home");
        if (string != null && (file = Mozilla.findGREFramework(string, gREVersionRangeArray)) != null) {
            return file;
        }
        return Mozilla.findGREFramework("", gREVersionRangeArray);
    }

    private static File findGREBundleFramework() {
        try {
            File file;
            File file2;
            File file3;
            String string;
            Class<?> clazz = Class.forName("com.apple.cocoa.foundation.NSBundle", true, new URLClassLoader(new URL[]{new File("/System/Library/Java/").toURL()}));
            Object object = clazz.getMethod("mainBundle", null).invoke(null, null);
            if (object != null && (string = (String)clazz.getMethod("privateFrameworksPath", null).invoke(object, null)).length() != 0 && (file3 = new File(string, "XUL.framework")).isDirectory() && (file2 = new File(file3, "libxpcom.dylib")).canRead() && new File(file = file2.getCanonicalFile().getParentFile(), JAVAXPCOM_JAR).canRead()) {
                return file;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    private static File findGREFramework(String string, GREVersionRange[] gREVersionRangeArray) {
        File file = new File(string + "/Library/Frameworks/XUL.framework/Versions");
        if (!file.exists()) {
            return null;
        }
        File[] fileArray = file.listFiles();
        for (int i = 0; i < fileArray.length; ++i) {
            if (fileArray[i].getName() >= gREVersionRangeArray) continue;
            File file2 = new File(fileArray[i], "libxpcom.dylib");
            File file3 = new File(fileArray[i], JAVAXPCOM_JAR);
            if (!file2.canRead() || !file3.canRead()) continue;
            return fileArray[i];
        }
        return null;
    }

    private static File getGREPathWindows(GREVersionRange[] gREVersionRangeArray, Properties properties) {
        File file = Mozilla.getGREPathFromRegKey("HKEY_CURRENT_USER\\Software\\mozilla.org\\GRE", gREVersionRangeArray, properties);
        if (file == null) {
            file = Mozilla.getGREPathFromRegKey("HKEY_LOCAL_MACHINE\\Software\\mozilla.org\\GRE", gREVersionRangeArray, properties);
        }
        return file;
    }

    private static File getGREPathFromRegKey(String string, GREVersionRange[] gREVersionRangeArray, Properties properties) {
        File file;
        try {
            file = File.createTempFile("jx_registry", null);
        }
        catch (IOException iOException) {
            return null;
        }
        try {
            Runtime.getRuntime().exec("regedit /e \"" + file.getPath() + "\" \"" + string + "\"").waitFor();
        }
        catch (Exception exception) {
            // empty catch block
        }
        File file2 = null;
        if (file.length() != 0L) {
            file2 = Mozilla.getGREPathFromRegistryFile(file.getPath(), string, gREVersionRangeArray, properties);
        }
        file.delete();
        return file2;
    }

    private static File getGREPathFromRegistryFile(String string, String string2, GREVersionRange[] gREVersionRangeArray, Properties properties) {
        INIParser iNIParser;
        try {
            iNIParser = new INIParser(string, Charset.forName("UTF-16"));
        }
        catch (Exception exception) {
            return null;
        }
        Iterator iterator = iNIParser.getSections();
        while (iterator.hasNext()) {
            String string3;
            Object object;
            String string4;
            String string5 = (String)iterator.next();
            int n = string2.length();
            if (string5.length() <= n || string5.substring(n + 1).indexOf(92) != -1 || (string4 = iNIParser.getString(string5, "\"Version\"")) == null || string4.substring(1, string4.length() - 1) < gREVersionRangeArray) continue;
            if (properties != null) {
                boolean bl = true;
                object = properties.propertyNames();
                while (bl && object.hasMoreElements()) {
                    String string6 = (String)object.nextElement();
                    String string7 = iNIParser.getString(string5, "\"" + string6 + "\"");
                    if (string7 == null) {
                        bl = false;
                        continue;
                    }
                    if (string7.equals("\"" + properties.getProperty(string6) + "\"")) continue;
                    bl = false;
                }
                if (!bl) continue;
            }
            if ((string3 = iNIParser.getString(string5, "\"GreHome\"")) == null || !((File)(object = new File(string3.substring(1, string3.length() - 1)))).exists() || !new File((File)object, "xpcom.dll").canRead()) continue;
            return object;
        }
        return null;
    }

    private static File getGREPathUnix(GREVersionRange[] gREVersionRangeArray, Properties properties) {
        File file;
        Object object;
        String string = System.getProperty("MOZ_GRE_CONF");
        if (string != null && (object = Mozilla.getPathFromConfigFile(string, gREVersionRangeArray, properties)) != null) {
            return object;
        }
        object = System.getProperty("user.home");
        if (object != null) {
            file = Mozilla.getPathFromConfigFile((String)object + File.separator + ".gre.config", gREVersionRangeArray, properties);
            if (file != null) {
                return file;
            }
            File file2 = Mozilla.getPathFromConfigDir((String)object + File.separator + ".gre.d", gREVersionRangeArray, properties);
            if (file2 != null) {
                return file2;
            }
        }
        if ((file = Mozilla.getPathFromConfigFile("/etc/gre.conf", gREVersionRangeArray, properties)) != null) {
            return file;
        }
        return Mozilla.getPathFromConfigDir("/etc/gre.d", gREVersionRangeArray, properties);
    }

    private static File getPathFromConfigFile(String string, GREVersionRange[] gREVersionRangeArray, Properties properties) {
        INIParser iNIParser;
        try {
            iNIParser = new INIParser(string);
        }
        catch (Exception exception) {
            return null;
        }
        Iterator iterator = iNIParser.getSections();
        while (iterator.hasNext()) {
            String string2;
            Object object;
            String string3 = (String)iterator.next();
            if (string3 < gREVersionRangeArray) continue;
            if (properties != null) {
                boolean bl = true;
                object = properties.propertyNames();
                while (bl && object.hasMoreElements()) {
                    String string4 = (String)object.nextElement();
                    String string5 = iNIParser.getString(string3, string4);
                    if (string5 == null) {
                        bl = false;
                        continue;
                    }
                    if (string5.equals(properties.getProperty(string4))) continue;
                    bl = false;
                }
                if (!bl) continue;
            }
            if ((string2 = iNIParser.getString(string3, "GRE_PATH")) == null || !((File)(object = new File(string2))).exists() || !new File((File)object, "libxpcom.so").canRead()) continue;
            return object;
        }
        return null;
    }

    private static File getPathFromConfigDir(String string, GREVersionRange[] gREVersionRangeArray, Properties properties) {
        File file = new File(string);
        if (!file.isDirectory()) {
            return null;
        }
        File file2 = null;
        File[] fileArray = file.listFiles();
        for (int i = 0; i < fileArray.length && file2 == null; ++i) {
            if (!fileArray[i].getName().endsWith(".conf")) continue;
            file2 = Mozilla.getPathFromConfigFile(fileArray[i].getPath(), gREVersionRangeArray, properties);
        }
        return file2;
    }

    @Override
    public void initialize(File file) throws XPCOMInitializationException {
        File file2 = new File(file, JAVAXPCOM_JAR);
        if (!file2.exists()) {
            throw new XPCOMInitializationException("Could not find javaxpcom.jar in " + file);
        }
        URL[] uRLArray = new URL[]{null};
        try {
            uRLArray[0] = file2.toURL();
        }
        catch (MalformedURLException malformedURLException) {
            throw new XPCOMInitializationException(malformedURLException);
        }
        URLClassLoader uRLClassLoader = new URLClassLoader(uRLArray, this.getClass().getClassLoader());
        try {
            this.mozilla = (IMozilla)Class.forName("org.mozilla.xpcom.internal.MozillaImpl", true, uRLClassLoader).newInstance();
            this.gre = (IGRE)Class.forName("org.mozilla.xpcom.internal.GREImpl", true, uRLClassLoader).newInstance();
            this.xpcom = (IXPCOM)Class.forName("org.mozilla.xpcom.internal.XPCOMImpl", true, uRLClassLoader).newInstance();
            this.jxutils = (IJavaXPCOMUtils)Class.forName("org.mozilla.xpcom.internal.JavaXPCOMMethods", true, uRLClassLoader).newInstance();
        }
        catch (Exception exception) {
            throw new XPCOMInitializationException("Could not load org.mozilla.xpcom.internal.* classes", exception);
        }
        this.mozilla.initialize(file);
    }

    @Override
    public void initEmbedding(File file, File file2, IAppFileLocProvider iAppFileLocProvider) throws XPCOMException {
        try {
            this.gre.initEmbedding(file, file2, iAppFileLocProvider);
        }
        catch (NullPointerException nullPointerException) {
            throw new XPCOMInitializationException("Must call Mozilla.getInstance().initialize() before using this method", nullPointerException);
        }
    }

    @Override
    public void termEmbedding() {
        try {
            this.gre.termEmbedding();
            this.mozilla = null;
            this.gre = null;
            this.xpcom = null;
        }
        catch (NullPointerException nullPointerException) {
            throw new XPCOMInitializationException("Must call Mozilla.getInstance().initialize() before using this method", nullPointerException);
        }
    }

    @Override
    public ProfileLock lockProfileDirectory(File file) throws XPCOMException {
        try {
            return this.gre.lockProfileDirectory(file);
        }
        catch (NullPointerException nullPointerException) {
            throw new XPCOMInitializationException("Must call Mozilla.getInstance().initialize() before using this method", nullPointerException);
        }
    }

    @Override
    public void notifyProfile() {
        try {
            this.gre.notifyProfile();
        }
        catch (NullPointerException nullPointerException) {
            throw new XPCOMInitializationException("Must call Mozilla.getInstance().initialize() before using this method", nullPointerException);
        }
    }

    @Override
    public nsIServiceManager initXPCOM(File file, IAppFileLocProvider iAppFileLocProvider) throws XPCOMException {
        try {
            return this.xpcom.initXPCOM(file, iAppFileLocProvider);
        }
        catch (NullPointerException nullPointerException) {
            throw new XPCOMInitializationException("Must call Mozilla.getInstance().initialize() before using this method", nullPointerException);
        }
    }

    @Override
    public void shutdownXPCOM(nsIServiceManager nsIServiceManager2) throws XPCOMException {
        try {
            this.xpcom.shutdownXPCOM(nsIServiceManager2);
            this.mozilla = null;
            this.gre = null;
            this.xpcom = null;
        }
        catch (NullPointerException nullPointerException) {
            throw new XPCOMInitializationException("Must call Mozilla.getInstance().initialize() before using this method", nullPointerException);
        }
    }

    @Override
    public nsIServiceManager getServiceManager() throws XPCOMException {
        try {
            return this.xpcom.getServiceManager();
        }
        catch (NullPointerException nullPointerException) {
            throw new XPCOMInitializationException("Must call Mozilla.getInstance().initialize() before using this method", nullPointerException);
        }
    }

    @Override
    public nsIComponentManager getComponentManager() throws XPCOMException {
        try {
            return this.xpcom.getComponentManager();
        }
        catch (NullPointerException nullPointerException) {
            throw new XPCOMInitializationException("Must call Mozilla.getInstance().initialize() before using this method", nullPointerException);
        }
    }

    @Override
    public nsIComponentRegistrar getComponentRegistrar() throws XPCOMException {
        try {
            return this.xpcom.getComponentRegistrar();
        }
        catch (NullPointerException nullPointerException) {
            throw new XPCOMInitializationException("Must call Mozilla.getInstance().initialize() before using this method", nullPointerException);
        }
    }

    @Override
    public nsILocalFile newLocalFile(String string, boolean bl) throws XPCOMException {
        try {
            return this.xpcom.newLocalFile(string, bl);
        }
        catch (NullPointerException nullPointerException) {
            throw new XPCOMInitializationException("Must call Mozilla.getInstance().initialize() before using this method", nullPointerException);
        }
    }

    public static nsISupports queryInterface(nsISupports nsISupports2, String string) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(nsISupports2.getClass());
        while (!arrayList.isEmpty()) {
            Object object;
            Class clazz = (Class)arrayList.remove(0);
            String string2 = clazz.getName();
            if (string2.startsWith("java.") || string2.startsWith("javax.")) continue;
            if (clazz.isInterface() && string2.startsWith("org.mozilla") && (object = Mozilla.getInterfaceIID(clazz)) != null && string.equals(object)) {
                return nsISupports2;
            }
            object = clazz.getInterfaces();
            for (int i = 0; i < ((Class<?>[])object).length; ++i) {
                arrayList.add(object[i]);
            }
            Class clazz2 = clazz.getSuperclass();
            if (clazz2 == null) continue;
            arrayList.add(clazz2);
        }
        return null;
    }

    public static String getInterfaceIID(Class clazz) {
        String string;
        String string2;
        StringBuffer stringBuffer = new StringBuffer();
        String string3 = clazz.getName();
        int n = string3.lastIndexOf(".");
        String string4 = string2 = n > 0 ? string3.substring(n + 1) : string3;
        if (string2.startsWith("ns")) {
            stringBuffer.append("NS_");
            stringBuffer.append(string2.substring(2).toUpperCase());
        } else {
            stringBuffer.append(string2.toUpperCase());
        }
        stringBuffer.append("_IID");
        try {
            string = (String)clazz.getDeclaredField(stringBuffer.toString()).get(null);
        }
        catch (NoSuchFieldException noSuchFieldException) {
            string = null;
        }
        catch (IllegalAccessException illegalAccessException) {
            System.err.println("ERROR: Could not get field " + stringBuffer.toString());
            string = null;
        }
        return string;
    }

    @Override
    public long getNativeHandleFromAWT(Object object) {
        try {
            return this.mozilla.getNativeHandleFromAWT(object);
        }
        catch (NullPointerException nullPointerException) {
            throw new XPCOMInitializationException("Must call Mozilla.getInstance().initialize() before using this method", nullPointerException);
        }
    }

    @Override
    public long wrapJavaObject(Object object, String string) {
        try {
            return this.jxutils.wrapJavaObject(object, string);
        }
        catch (NullPointerException nullPointerException) {
            throw new XPCOMInitializationException("Must call Mozilla.getInstance().initialize() before using this method", nullPointerException);
        }
    }

    @Override
    public Object wrapXPCOMObject(long l2, String string) {
        try {
            return this.jxutils.wrapXPCOMObject(l2, string);
        }
        catch (NullPointerException nullPointerException) {
            throw new XPCOMInitializationException("Must call Mozilla.getInstance().initialize() before using this method", nullPointerException);
        }
    }
}

