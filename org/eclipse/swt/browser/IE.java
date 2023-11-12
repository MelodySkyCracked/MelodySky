/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.browser.VisibilityWindowListener;
import org.eclipse.swt.browser.WebBrowser;
import org.eclipse.swt.browser.WebSite;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IDispatch;
import org.eclipse.swt.internal.ole.win32.IPersistStreamInit;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.SAFEARRAY;
import org.eclipse.swt.internal.win32.SAFEARRAYBOUND;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleEvent;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.OleListener;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

class IE
extends WebBrowser {
    OleFrame frame;
    WebSite site;
    OleAutomation auto;
    OleListener domListener;
    OleAutomation[] documents = new OleAutomation[0];
    boolean back;
    boolean forward;
    boolean delaySetText;
    boolean ignoreDispose;
    boolean ignoreTraverse;
    boolean performingInitialNavigate;
    boolean installFunctionsOnDocumentComplete;
    boolean untrustedText;
    boolean isRefresh;
    boolean isAboutBlank;
    Point location;
    Point size;
    boolean addressBar = true;
    boolean menuBar = true;
    boolean statusBar = true;
    boolean toolBar = true;
    long globalDispatch;
    String html;
    String lastNavigateURL;
    String uncRedirect;
    Object[] pendingText;
    Object[] pendingUrl;
    int style;
    int lastKeyCode;
    int lastCharCode;
    int lastMouseMoveX;
    int lastMouseMoveY;
    static boolean Initialized;
    static int IEVersion;
    static int PDFCount;
    static String ProgId;
    static final int BeforeNavigate2 = 250;
    static final int CommandStateChange = 105;
    static final int DocumentComplete = 259;
    static final int DownloadComplete = 104;
    static final int NavigateComplete2 = 252;
    static final int NewWindow2 = 251;
    static final int OnMenuBar = 256;
    static final int OnStatusBar = 257;
    static final int OnToolBar = 255;
    static final int OnVisible = 254;
    static final int ProgressChange = 108;
    static final int RegisterAsBrowser = 552;
    static final int StatusTextChange = 102;
    static final int TitleChange = 113;
    static final int WindowClosing = 263;
    static final int WindowSetHeight = 267;
    static final int WindowSetLeft = 264;
    static final int WindowSetResizable = 262;
    static final int WindowSetTop = 265;
    static final int WindowSetWidth = 266;
    static final int NavigateError = 271;
    static final short CSC_NAVIGATEFORWARD = 1;
    static final short CSC_NAVIGATEBACK = 2;
    static final int INET_E_DEFAULT_ACTION = -2146697199;
    static final int INET_E_RESOURCE_NOT_FOUND = -2146697211;
    static final int READYSTATE_COMPLETE = 4;
    static final int URLPOLICY_ALLOW = 0;
    static final int URLPOLICY_DISALLOW = 3;
    static final int URLPOLICY_JAVA_PROHIBIT = 0;
    static final int URLPOLICY_JAVA_LOW = 196608;
    static final int URLZONE_LOCAL_MACHINE = 0;
    static final int URLZONE_INTRANET = 1;
    static final int URLACTION_ACTIVEX_MIN = 4608;
    static final int URLACTION_ACTIVEX_MAX = 5119;
    static final int URLACTION_ACTIVEX_RUN = 4608;
    static final int URLACTION_FEATURE_ZONE_ELEVATION = 8449;
    static final int URLACTION_JAVA_MIN = 7168;
    static final int URLACTION_JAVA_MAX = 7423;
    static final int URLACTION_SCRIPT_RUN = 5120;
    static final int DISPID_AMBIENT_DLCONTROL = -5512;
    static final int DLCTL_DLIMAGES = 16;
    static final int DLCTL_VIDEOS = 32;
    static final int DLCTL_BGSOUNDS = 64;
    static final int DLCTL_NO_SCRIPTS = 128;
    static final int DLCTL_NO_JAVA = 256;
    static final int DLCTL_NO_RUNACTIVEXCTLS = 512;
    static final int DLCTL_NO_DLACTIVEXCTLS = 1024;
    static final int DLCTL_DOWNLOADONLY = 2048;
    static final int DLCTL_NO_FRAMEDOWNLOAD = 4096;
    static final int DLCTL_RESYNCHRONIZE = 8192;
    static final int DLCTL_PRAGMA_NO_CACHE = 16384;
    static final int DLCTL_FORCEOFFLINE = 0x10000000;
    static final int DLCTL_NO_CLIENTPULL = 0x20000000;
    static final int DLCTL_SILENT = 0x40000000;
    static final int DOCHOSTUIFLAG_THEME = 262144;
    static final int DOCHOSTUIFLAG_NO3DBORDER = 4;
    static final int DOCHOSTUIFLAG_NO3DOUTERBORDER = 0x200000;
    static final int DOCHOSTUIFLAG_ENABLE_REDIRECT_NOTIFICATION = 0x4000000;
    static final int DOCHOSTUIFLAG_DPI_AWARE = 0x40000000;
    static final String ABOUT_BLANK = "about:blank";
    static final String CLSID_SHELLEXPLORER1 = "{EAB22AC3-30C1-11CF-A7EB-0000C05BAE0B}";
    static final int DEFAULT_IE_VERSION = 9999;
    static final String EXTENSION_PDF = ".pdf";
    static final String HTML_DOCUMENT = "HTML Document";
    static final int MAX_PDF = 20;
    static final char SEPARATOR_OS;
    static final String PROPERTY_IEVERSION = "org.eclipse.swt.browser.IEVersion";
    static final String VALUE_DEFAULT = "default";
    static final String EVENT_DOUBLECLICK = "dblclick";
    static final String EVENT_DRAGEND = "dragend";
    static final String EVENT_DRAGSTART = "dragstart";
    static final String EVENT_KEYDOWN = "keydown";
    static final String EVENT_KEYPRESS = "keypress";
    static final String EVENT_KEYUP = "keyup";
    static final String EVENT_MOUSEMOVE = "mousemove";
    static final String EVENT_MOUSEWHEEL = "mousewheel";
    static final String EVENT_MOUSEUP = "mouseup";
    static final String EVENT_MOUSEDOWN = "mousedown";
    static final String EVENT_MOUSEOUT = "mouseout";
    static final String EVENT_MOUSEOVER = "mouseover";
    static final String PROTOCOL_FILE = "file://";
    static final String PROPERTY_ALTKEY = "altKey";
    static final String PROPERTY_BUTTON = "button";
    static final String PROPERTY_CTRLKEY = "ctrlKey";
    static final String PROPERTY_DOCUMENT = "Document";
    static final String PROPERTY_FROMELEMENT = "fromElement";
    static final String PROPERTY_KEYCODE = "keyCode";
    static final String PROPERTY_REPEAT = "repeat";
    static final String PROPERTY_RETURNVALUE = "returnValue";
    static final String PROPERTY_SCREENX = "screenX";
    static final String PROPERTY_SCREENY = "screenY";
    static final String PROPERTY_SHIFTKEY = "shiftKey";
    static final String PROPERTY_TOELEMENT = "toElement";
    static final String PROPERTY_TYPE = "type";
    static final String PROPERTY_WHEELDELTA = "wheelDelta";

    IE() {
    }

    @Override
    public void create(Composite composite, int n) {
        Object object;
        Object object2;
        Object object3;
        this.style = n;
        this.frame = new OleFrame(this.browser, 0);
        try {
            this.site = new WebSite((Composite)this.frame, 0, ProgId);
        }
        catch (SWTException sWTException) {
            this.browser.dispose();
            SWT.error(2);
        }
        if (!Initialized) {
            Initialized = true;
            int n2 = 0;
            object3 = System.getProperty(PROPERTY_IEVERSION);
            if (object3 != null) {
                if (((String)object3).equalsIgnoreCase(VALUE_DEFAULT)) {
                    n2 = -1;
                } else {
                    try {
                        n2 = Integer.parseInt((String)object3);
                    }
                    catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
            }
            if (n2 == 0) {
                int n3 = IEVersion != 0 ? (IEVersion >= 10 ? IEVersion * 1000 + 1 : (IEVersion >= 8 ? IEVersion * 1111 : IEVersion * 1000)) : (n2 = 9999);
            }
            if (n2 != -1) {
                object2 = new TCHAR(0, "Software\\Microsoft\\Internet Explorer\\Main\\FeatureControl\\FEATURE_BROWSER_EMULATION", true);
                long[] lArray = new long[1];
                object = lArray;
                if (OS.RegCreateKeyEx(-2147483647L, (TCHAR)object2, 0, null, 1, 131079, 0L, lArray, null) == 0) {
                    TCHAR tCHAR = new TCHAR(0, 260);
                    OS.GetModuleFileName(0L, tCHAR, tCHAR.length());
                    String string = tCHAR.toString(0, tCHAR.strlen());
                    int n4 = string.lastIndexOf(SEPARATOR_OS);
                    String string2 = n4 != -1 ? string.substring(n4 + 1) : string;
                    TCHAR tCHAR2 = new TCHAR(0, string2, true);
                    int n5 = OS.RegQueryValueEx((long)object[0], tCHAR2, 0L, null, (int[])null, null);
                    if ((n5 == 0 || n5 == 2) && OS.RegSetValueEx((long)object[0], tCHAR2, 0, 4, new int[]{n2}, 4) == 0) {
                        composite.getDisplay().addListener(12, arg_0 -> IE.lambda$create$0((TCHAR)object2, tCHAR2, arg_0));
                    }
                    OS.RegCloseKey((long)object[0]);
                }
            }
        }
        this.site.doVerb(-5);
        this.auto = new OleAutomation(this.site);
        this.domListener = this::lambda$create$1;
        object = this::lambda$create$2;
        this.browser.addListener(12, (Listener)object);
        this.browser.addListener(15, (Listener)object);
        this.browser.addListener(11, (Listener)object);
        this.browser.addListener(31, (Listener)object);
        this.site.addListener(37, (Listener)object);
        this.site.addListener(31, (Listener)object);
        object2 = this::lambda$create$6;
        this.site.addEventListener(250, (OleListener)object2);
        this.site.addEventListener(105, (OleListener)object2);
        this.site.addEventListener(259, (OleListener)object2);
        this.site.addEventListener(104, (OleListener)object2);
        this.site.addEventListener(252, (OleListener)object2);
        this.site.addEventListener(271, (OleListener)object2);
        this.site.addEventListener(251, (OleListener)object2);
        this.site.addEventListener(256, (OleListener)object2);
        this.site.addEventListener(257, (OleListener)object2);
        this.site.addEventListener(255, (OleListener)object2);
        this.site.addEventListener(254, (OleListener)object2);
        this.site.addEventListener(108, (OleListener)object2);
        this.site.addEventListener(102, (OleListener)object2);
        this.site.addEventListener(113, (OleListener)object2);
        this.site.addEventListener(263, (OleListener)object2);
        this.site.addEventListener(267, (OleListener)object2);
        this.site.addEventListener(264, (OleListener)object2);
        this.site.addEventListener(265, (OleListener)object2);
        this.site.addEventListener(266, (OleListener)object2);
        Variant variant = new Variant(true);
        this.auto.setProperty(552, variant);
        variant.dispose();
        variant = new Variant(false);
        object3 = this.auto.getIDsOfNames(new String[]{"RegisterAsDropTarget"});
        if (object3 != null) {
            this.auto.setProperty((int)object3[0], variant);
        }
        variant.dispose();
    }

    @Override
    public boolean back() {
        if (!this.back) {
            return false;
        }
        int[] nArray = this.auto.getIDsOfNames(new String[]{"GoBack"});
        Variant variant = this.auto.invoke(nArray[0]);
        return variant != null && variant.getType() == 0;
    }

    @Override
    public boolean close() {
        boolean bl = true;
        int[] nArray = this.auto.getIDsOfNames(new String[]{PROPERTY_DOCUMENT});
        int n = nArray[0];
        Variant variant = this.auto.getProperty(n);
        if (variant == null || variant.getType() == 0) {
            if (variant != null) {
                variant.dispose();
            }
        } else {
            OleAutomation oleAutomation = variant.getAutomation();
            variant.dispose();
            nArray = oleAutomation.getIDsOfNames(new String[]{"parentWindow"});
            if (nArray != null) {
                n = nArray[0];
                variant = oleAutomation.getProperty(n);
                if (variant == null || variant.getType() == 0) {
                    if (variant != null) {
                        variant.dispose();
                    }
                } else {
                    OleAutomation oleAutomation2 = variant.getAutomation();
                    variant.dispose();
                    nArray = oleAutomation2.getIDsOfNames(new String[]{"location"});
                    n = nArray[0];
                    variant = oleAutomation2.getProperty(n);
                    if (variant == null || variant.getType() == 0) {
                        if (variant != null) {
                            variant.dispose();
                        }
                    } else {
                        OleAutomation oleAutomation3 = variant.getAutomation();
                        variant.dispose();
                        LocationListener[] locationListenerArray = this.locationListeners;
                        this.locationListeners = new LocationListener[0];
                        nArray = oleAutomation3.getIDsOfNames(new String[]{"replace"});
                        n = nArray[0];
                        Variant[] variantArray = new Variant[]{new Variant(ABOUT_BLANK)};
                        variant = oleAutomation3.invoke(n, variantArray);
                        if (variant == null) {
                            bl = false;
                        } else {
                            variant.dispose();
                        }
                        variantArray[0].dispose();
                        this.locationListeners = locationListenerArray;
                        oleAutomation3.dispose();
                    }
                    oleAutomation2.dispose();
                }
            }
            oleAutomation.dispose();
        }
        return bl;
    }

    static Variant createSafeArray(String string) {
        SAFEARRAYBOUND sAFEARRAYBOUND;
        byte[] byArray = string.getBytes();
        int n = byArray.length;
        long l2 = OS.GlobalAlloc(64, n);
        C.memmove(l2, byArray, (long)n);
        int n2 = n;
        long l3 = OS.GlobalAlloc(64, SAFEARRAY.sizeof);
        SAFEARRAY sAFEARRAY = new SAFEARRAY();
        sAFEARRAY.cDims = 1;
        sAFEARRAY.fFeatures = (short)16;
        sAFEARRAY.cbElements = 1;
        sAFEARRAY.pvData = l2;
        sAFEARRAY.rgsabound = sAFEARRAYBOUND = new SAFEARRAYBOUND();
        sAFEARRAYBOUND.cElements = n2;
        OS.MoveMemory(l3, sAFEARRAY, SAFEARRAY.sizeof);
        long l4 = OS.GlobalAlloc(64, Variant.sizeof);
        short s = 8209;
        OS.MoveMemory(l4, new short[]{s}, 2);
        OS.MoveMemory(l4 + 8L, new long[]{l3}, C.PTR_SIZEOF);
        return new Variant(l4, 16396);
    }

    @Override
    public boolean execute(String string) {
        int[] nArray;
        int n;
        Variant variant;
        if (!this.performingInitialNavigate && this._getUrl().length() == 0) {
            this.performingInitialNavigate = true;
            this.navigate(ABOUT_BLANK, null, null, true);
        }
        if ((variant = this.auto.getProperty(n = (nArray = this.auto.getIDsOfNames(new String[]{PROPERTY_DOCUMENT}))[0])) == null || variant.getType() == 0) {
            if (variant != null) {
                variant.dispose();
            }
            return false;
        }
        OleAutomation oleAutomation = variant.getAutomation();
        variant.dispose();
        nArray = oleAutomation.getIDsOfNames(new String[]{"parentWindow"});
        if (nArray == null) {
            oleAutomation.dispose();
            return false;
        }
        n = nArray[0];
        variant = oleAutomation.getProperty(n);
        if (variant == null || variant.getType() == 0) {
            if (variant != null) {
                variant.dispose();
            }
            oleAutomation.dispose();
            return false;
        }
        OleAutomation oleAutomation2 = variant.getAutomation();
        variant.dispose();
        oleAutomation.dispose();
        nArray = oleAutomation2.getIDsOfNames(new String[]{"execScript", "code"});
        if (nArray == null) {
            oleAutomation2.dispose();
            return false;
        }
        Variant[] variantArray = new Variant[]{new Variant(string)};
        int[] nArray2 = new int[]{nArray[1]};
        variant = oleAutomation2.invoke(nArray[0], variantArray, nArray2);
        variantArray[0].dispose();
        oleAutomation2.dispose();
        if (variant == null) {
            return false;
        }
        variant.dispose();
        return true;
    }

    @Override
    public boolean forward() {
        if (!this.forward) {
            return false;
        }
        int[] nArray = this.auto.getIDsOfNames(new String[]{"GoForward"});
        Variant variant = this.auto.invoke(nArray[0]);
        return variant != null && variant.getType() == 0;
    }

    @Override
    public String getBrowserType() {
        return "ie";
    }

    @Override
    String getDeleteFunctionString(String string) {
        return "window." + string + "=undefined";
    }

    @Override
    public String getText() {
        int[] nArray = this.auto.getIDsOfNames(new String[]{PROPERTY_DOCUMENT});
        Variant variant = this.auto.getProperty(nArray[0]);
        if (variant == null || variant.getType() == 0) {
            if (variant != null) {
                variant.dispose();
            }
            return "";
        }
        OleAutomation oleAutomation = variant.getAutomation();
        variant.dispose();
        nArray = oleAutomation.getIDsOfNames(new String[]{"documentElement"});
        if (nArray == null) {
            oleAutomation.dispose();
            return "";
        }
        variant = oleAutomation.getProperty(nArray[0]);
        oleAutomation.dispose();
        if (variant == null || variant.getType() == 0 || variant.getType() == 1) {
            if (variant != null) {
                variant.dispose();
            }
            return "";
        }
        OleAutomation oleAutomation2 = variant.getAutomation();
        variant.dispose();
        nArray = oleAutomation2.getIDsOfNames(new String[]{"outerHTML"});
        variant = oleAutomation2.getProperty(nArray[0]);
        oleAutomation2.dispose();
        if (variant == null || variant.getType() == 0) {
            if (variant != null) {
                variant.dispose();
            }
            return "";
        }
        String string = variant.getString();
        variant.dispose();
        return string;
    }

    @Override
    public String getUrl() {
        String string = this._getUrl();
        return string.length() != 0 ? string : ABOUT_BLANK;
    }

    String _getUrl() {
        int[] nArray = this.auto.getIDsOfNames(new String[]{"LocationURL"});
        Variant variant = this.auto.getProperty(nArray[0]);
        if (variant == null || variant.getType() != 8) {
            return "";
        }
        String string = variant.getString();
        variant.dispose();
        return string;
    }

    @Override
    public boolean isBackEnabled() {
        return this.back;
    }

    @Override
    public boolean isForwardEnabled() {
        return this.forward;
    }

    @Override
    public boolean isFocusControl() {
        return this.site.isFocusControl() || this.frame.isFocusControl();
    }

    boolean navigate(String string, String string2, String[] stringArray, boolean bl) {
        int n;
        int n2 = 1;
        if (string2 != null) {
            n2 += 1;
        }
        if (stringArray != null) {
            n2 += 1;
        }
        Variant[] variantArray = new Variant[n2];
        int[] nArray = new int[n2];
        int[] nArray2 = this.auto.getIDsOfNames(new String[]{"Navigate", "URL", "PostData", "Headers"});
        int n3 = 0;
        variantArray[n3] = new Variant(string);
        nArray[n3++] = nArray2[1];
        if (string2 != null) {
            variantArray[n3] = IE.createSafeArray(string2);
            nArray[n3++] = nArray2[2];
        }
        if (stringArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String string3 : stringArray) {
                int n4;
                if (string3 == null || (n4 = string3.indexOf(58)) == -1) continue;
                String string4 = string3.substring(0, n4).trim();
                String string5 = string3.substring(n4 + 1).trim();
                if (string4.length() <= 0 || string5.length() <= 0) continue;
                stringBuilder.append(string4);
                stringBuilder.append(':');
                stringBuilder.append(string5);
                stringBuilder.append("\r\n");
            }
            variantArray[n3] = new Variant(stringBuilder.toString());
            nArray[n3++] = nArray2[3];
        }
        boolean bl2 = false;
        if (bl && IEVersion >= 7) {
            int n5 = OS.CoInternetIsFeatureEnabled(21, 2);
            bl2 = n5 == 0;
            OS.CoInternetSetFeatureEnabled(21, 2, true);
        }
        Variant variant = this.auto.invoke(nArray2[0], variantArray, nArray);
        if (bl && IEVersion >= 7) {
            OS.CoInternetSetFeatureEnabled(21, 2, bl2);
        }
        for (n = 0; n < n2; n += 1) {
            variantArray[n].dispose();
        }
        if (variant == null) {
            return false;
        }
        n = variant.getType() == 0 ? 1 : 0;
        variant.dispose();
        return n != 0;
    }

    @Override
    public void refresh() {
        this.uncRedirect = null;
        String string = this._getUrl();
        int n = string.lastIndexOf(46);
        if (n != -1 && string.substring(n).equalsIgnoreCase(EXTENSION_PDF) && ++PDFCount > 20) {
            COM.FreeUnusedLibraries = false;
        }
        this.isRefresh = true;
        int[] nArray = this.auto.getIDsOfNames(new String[]{"Refresh"});
        this.auto.invoke(nArray[0]);
    }

    void setHTML(String string) {
        int n = string.length();
        char[] cArray = new char[n];
        string.getChars(0, n, cArray, 0);
        int n2 = OS.WideCharToMultiByte(65001, 0, cArray, n, null, 0, null, null);
        byte[] byArray = new byte[]{-17, -69, -65};
        long l2 = OS.GlobalAlloc(64, byArray.length + n2);
        if (l2 != 0L) {
            OS.MoveMemory(l2, byArray, byArray.length);
            OS.WideCharToMultiByte(65001, 0, cArray, n, l2 + (long)byArray.length, n2, null, null);
            long[] lArray = new long[1];
            if (OS.CreateStreamOnHGlobal(l2, true, lArray) == 0) {
                IUnknown iUnknown;
                long[] lArray2;
                int[] nArray = this.auto.getIDsOfNames(new String[]{PROPERTY_DOCUMENT});
                Variant variant = this.auto.getProperty(nArray[0]);
                IDispatch iDispatch = variant.getDispatch();
                int n3 = iDispatch.QueryInterface(COM.IIDIPersistStreamInit, lArray2 = new long[1]);
                if (n3 == 0) {
                    iUnknown = new IPersistStreamInit(lArray2[0]);
                    if (((IPersistStreamInit)iUnknown).InitNew() == 0) {
                        ((IPersistStreamInit)iUnknown).Load(lArray[0]);
                    }
                    iUnknown.Release();
                }
                variant.dispose();
                iUnknown = new IUnknown(lArray[0]);
                iUnknown.Release();
            } else {
                OS.GlobalFree(l2);
            }
        }
    }

    private void setAboutBlank(boolean bl) {
        this.isAboutBlank = bl;
        this.updateForceTrusted();
    }

    private void setUntrustedText(boolean bl) {
        this.untrustedText = bl;
        this.updateForceTrusted();
    }

    private void updateForceTrusted() {
        this.site.isForceTrusted = this.isAboutBlank && !this.untrustedText;
    }

    @Override
    public boolean setText(String string, boolean bl) {
        Variant[] variantArray;
        int[] nArray;
        if (this.performingInitialNavigate) {
            this.pendingText = new Object[]{string, bl};
            this.pendingUrl = null;
            return true;
        }
        boolean bl2 = this.html != null;
        this.html = string;
        this.setUntrustedText(!bl);
        if (bl2) {
            return true;
        }
        if (this._getUrl().length() != 0) {
            nArray = this.auto.getIDsOfNames(new String[]{"ReadyState"});
            variantArray = this.auto.getProperty(nArray[0]);
            if (variantArray == null) {
                return false;
            }
            this.delaySetText = variantArray.getInt() != 4;
            variantArray.dispose();
            nArray = this.auto.getIDsOfNames(new String[]{"Stop"});
            this.auto.invoke(nArray[0]);
        }
        nArray = this.auto.getIDsOfNames(new String[]{"Navigate", "URL"});
        variantArray = new Variant[]{new Variant(ABOUT_BLANK)};
        int[] nArray2 = new int[]{nArray[1]};
        boolean bl3 = false;
        if (IEVersion >= 7) {
            int n = OS.CoInternetIsFeatureEnabled(21, 2);
            bl3 = n == 0;
            OS.CoInternetSetFeatureEnabled(21, 2, true);
        }
        Variant variant = this.auto.invoke(nArray[0], variantArray, nArray2);
        if (IEVersion >= 7) {
            OS.CoInternetSetFeatureEnabled(21, 2, bl3);
        }
        variantArray[0].dispose();
        if (variant == null) {
            return false;
        }
        boolean bl4 = variant.getType() == 0;
        variant.dispose();
        return bl4;
    }

    @Override
    public boolean setUrl(String string, String string2, String[] stringArray) {
        this.uncRedirect = null;
        this.html = null;
        if (this._getUrl().length() == 0 && !ABOUT_BLANK.equalsIgnoreCase(string)) {
            this.pendingText = null;
            this.pendingUrl = new Object[]{string, string2, stringArray};
            this.performingInitialNavigate = true;
            this.navigate(ABOUT_BLANK, null, null, true);
            return true;
        }
        if (string.endsWith(".xml")) {
            int[] nArray = this.auto.getIDsOfNames(new String[]{"Stop"});
            this.auto.invoke(nArray[0]);
        }
        return this.navigate(string, string2, stringArray, false);
    }

    @Override
    public void stop() {
        if (this.performingInitialNavigate) {
            this.pendingUrl = null;
            this.pendingText = null;
            return;
        }
        if (this._getUrl().length() == 0) {
            return;
        }
        this.setAboutBlank(this.getUrl().startsWith(ABOUT_BLANK));
        this.uncRedirect = null;
        int[] nArray = this.auto.getIDsOfNames(new String[]{"Stop"});
        this.auto.invoke(nArray[0]);
    }

    @Override
    boolean translateMnemonics() {
        return false;
    }

    void handleDOMEvent(OleEvent oleEvent) {
        boolean bl;
        if (oleEvent.arguments == null || oleEvent.arguments.length == 0) {
            return;
        }
        Variant variant = oleEvent.arguments[0];
        OleAutomation oleAutomation = variant.getAutomation();
        int[] nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_TYPE});
        int n = nArray[0];
        Variant variant2 = oleAutomation.getProperty(n);
        String string = variant2.getString();
        variant2.dispose();
        if (string.equals(EVENT_KEYDOWN)) {
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_KEYCODE});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            this.lastKeyCode = this.translateKey(variant2.getInt());
            variant2.dispose();
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_RETURNVALUE});
            variant2 = oleAutomation.getProperty(nArray[0]);
            boolean bl2 = variant2 != null && variant2.getType() == 11 && !variant2.getBoolean();
            variant2.dispose();
            MSG mSG = new MSG();
            int n2 = 2 | (bl2 ? 1 : 0);
            if (OS.PeekMessage(mSG, this.frame.handle, 258, 258, n2)) {
                oleAutomation.dispose();
                return;
            }
            if (bl2) {
                oleAutomation.dispose();
                return;
            }
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_REPEAT});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            boolean bl3 = variant2.getBoolean();
            variant2.dispose();
            if (bl3) {
                oleAutomation.dispose();
                return;
            }
            int n3 = 0;
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_ALTKEY});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            if (variant2.getBoolean()) {
                n3 |= 0x10000;
            }
            variant2.dispose();
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_CTRLKEY});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            if (variant2.getBoolean()) {
                n3 |= 0x40000;
            }
            variant2.dispose();
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_SHIFTKEY});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            if (variant2.getBoolean()) {
                n3 |= 0x20000;
            }
            variant2.dispose();
            Event event = new Event();
            event.widget = this.browser;
            event.type = 1;
            event.keyCode = this.lastKeyCode;
            event.stateMask = n3;
            event.stateMask &= ~this.lastKeyCode;
            switch (this.lastKeyCode) {
                case 8: {
                    event.character = (char)8;
                    this.lastCharCode = 8;
                    break;
                }
                case 13: {
                    event.character = (char)13;
                    this.lastCharCode = 13;
                    break;
                }
                case 127: {
                    event.character = (char)127;
                    this.lastCharCode = 127;
                    break;
                }
                case 9: {
                    event.character = (char)9;
                    this.lastCharCode = 9;
                }
            }
            if (!this.sendKeyEvent(event)) {
                nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_RETURNVALUE});
                n = nArray[0];
                Variant variant3 = new Variant(false);
                oleAutomation.setProperty(n, variant3);
                variant3.dispose();
            }
            if (this.lastKeyCode == 0x100000E) {
                this.isRefresh = true;
            }
            oleAutomation.dispose();
            return;
        }
        if (string.equals(EVENT_KEYPRESS)) {
            int n4 = 0;
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_CTRLKEY});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            if (variant2.getBoolean()) {
                n4 |= 0x40000;
            }
            variant2.dispose();
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_SHIFTKEY});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            if (variant2.getBoolean()) {
                n4 |= 0x20000;
            }
            variant2.dispose();
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_ALTKEY});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            if (variant2.getBoolean()) {
                n4 |= 0x10000;
            }
            variant2.dispose();
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_KEYCODE});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            this.lastCharCode = variant2.getInt();
            variant2.dispose();
            if (this.lastCharCode == 13 || this.lastCharCode == 10) {
                oleAutomation.dispose();
                return;
            }
            Event event = new Event();
            event.widget = this.browser;
            event.type = 1;
            event.keyCode = this.lastKeyCode;
            event.character = (char)this.lastCharCode;
            event.stateMask = n4;
            if (!this.sendKeyEvent(event)) {
                nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_RETURNVALUE});
                n = nArray[0];
                Variant variant4 = new Variant(false);
                oleAutomation.setProperty(n, variant4);
                variant4.dispose();
            }
            oleAutomation.dispose();
            return;
        }
        if (string.equals(EVENT_KEYUP)) {
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_KEYCODE});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            int n5 = this.translateKey(variant2.getInt());
            variant2.dispose();
            if (n5 == 0) {
                this.lastCharCode = 0;
                this.lastKeyCode = 0;
                oleAutomation.dispose();
                return;
            }
            if (n5 != this.lastKeyCode) {
                this.lastKeyCode = n5;
                this.lastCharCode = 0;
            }
            int n6 = 0;
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_CTRLKEY});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            if (variant2.getBoolean()) {
                n6 |= 0x40000;
            }
            variant2.dispose();
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_ALTKEY});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            if (variant2.getBoolean()) {
                n6 |= 0x10000;
            }
            variant2.dispose();
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_SHIFTKEY});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            if (variant2.getBoolean()) {
                n6 |= 0x20000;
            }
            variant2.dispose();
            Event event = new Event();
            event.widget = this.browser;
            event.type = 2;
            event.keyCode = this.lastKeyCode;
            event.character = (char)this.lastCharCode;
            event.stateMask = n6;
            switch (this.lastKeyCode) {
                case 65536: 
                case 131072: 
                case 262144: 
                case 0x400000: {
                    event.stateMask |= this.lastKeyCode;
                }
            }
            this.browser.notifyListeners(event.type, event);
            if (!event.doit) {
                nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_RETURNVALUE});
                n = nArray[0];
                Variant variant5 = new Variant(false);
                oleAutomation.setProperty(n, variant5);
                variant5.dispose();
            }
            this.lastCharCode = 0;
            this.lastKeyCode = 0;
            oleAutomation.dispose();
            return;
        }
        if (string.equals(EVENT_MOUSEOVER)) {
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_FROMELEMENT});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            bl = variant2.getType() != 0;
            variant2.dispose();
            if (bl) {
                oleAutomation.dispose();
                return;
            }
        }
        if (string.equals(EVENT_MOUSEOUT)) {
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_TOELEMENT});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            bl = variant2.getType() != 0;
            variant2.dispose();
            if (bl) {
                oleAutomation.dispose();
                return;
            }
        }
        int n7 = 0;
        Event event = new Event();
        event.widget = this.browser;
        nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_SCREENX});
        n = nArray[0];
        variant2 = oleAutomation.getProperty(n);
        int n8 = variant2.getInt();
        variant2.dispose();
        nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_SCREENY});
        n = nArray[0];
        variant2 = oleAutomation.getProperty(n);
        int n9 = variant2.getInt();
        variant2.dispose();
        Point point = DPIUtil.autoScaleDown(new Point(n8, n9));
        point = this.browser.getDisplay().map(null, (Control)this.browser, point);
        event.x = point.x;
        event.y = point.y;
        nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_CTRLKEY});
        n = nArray[0];
        variant2 = oleAutomation.getProperty(n);
        if (variant2.getBoolean()) {
            n7 |= 0x40000;
        }
        variant2.dispose();
        nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_ALTKEY});
        n = nArray[0];
        variant2 = oleAutomation.getProperty(n);
        if (variant2.getBoolean()) {
            n7 |= 0x10000;
        }
        variant2.dispose();
        nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_SHIFTKEY});
        n = nArray[0];
        variant2 = oleAutomation.getProperty(n);
        if (variant2.getBoolean()) {
            n7 |= 0x20000;
        }
        variant2.dispose();
        event.stateMask = n7;
        nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_BUTTON});
        n = nArray[0];
        variant2 = oleAutomation.getProperty(n);
        int n10 = variant2.getInt();
        variant2.dispose();
        switch (n10) {
            case 1: {
                n10 = 1;
                break;
            }
            case 2: {
                n10 = 3;
                break;
            }
            case 4: {
                n10 = 2;
            }
        }
        if (string.equals(EVENT_MOUSEDOWN)) {
            event.type = 3;
            event.button = n10;
            event.count = 1;
        } else if (string.equals(EVENT_MOUSEUP) || string.equals(EVENT_DRAGEND)) {
            event.type = 4;
            event.button = n10 != 0 ? n10 : 1;
            event.count = 1;
            switch (event.button) {
                case 1: {
                    event.stateMask |= 0x80000;
                    break;
                }
                case 2: {
                    event.stateMask |= 0x100000;
                    break;
                }
                case 3: {
                    event.stateMask |= 0x200000;
                    break;
                }
                case 4: {
                    event.stateMask |= 0x800000;
                    break;
                }
                case 5: {
                    event.stateMask |= 0x2000000;
                }
            }
        } else if (string.equals(EVENT_MOUSEWHEEL)) {
            event.type = 37;
            nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_WHEELDELTA});
            n = nArray[0];
            variant2 = oleAutomation.getProperty(n);
            event.count = variant2.getInt() / 120 * 3;
            variant2.dispose();
        } else if (string.equals(EVENT_MOUSEMOVE)) {
            if (event.x == this.lastMouseMoveX && event.y == this.lastMouseMoveY) {
                oleAutomation.dispose();
                return;
            }
            event.type = 5;
            this.lastMouseMoveX = event.x;
            this.lastMouseMoveY = event.y;
        } else if (string.equals(EVENT_MOUSEOVER)) {
            event.type = 6;
        } else if (string.equals(EVENT_MOUSEOUT)) {
            event.type = 7;
        } else if (string.equals(EVENT_DRAGSTART)) {
            event.type = 29;
            event.button = 1;
            event.stateMask |= 0x80000;
        }
        oleAutomation.dispose();
        this.browser.notifyListeners(event.type, event);
        if (string.equals(EVENT_DOUBLECLICK)) {
            event = new Event();
            event.widget = this.browser;
            event.type = 8;
            event.x = point.x;
            event.y = point.y;
            event.stateMask = n7;
            event.type = 8;
            event.button = 1;
            event.count = 2;
            this.browser.notifyListeners(event.type, event);
        }
    }

    void hookDOMListeners(OleAutomation oleAutomation, boolean bl) {
        int[] nArray = oleAutomation.getIDsOfNames(new String[]{PROPERTY_DOCUMENT});
        int n = nArray[0];
        Variant variant = oleAutomation.getProperty(n);
        if (variant == null) {
            return;
        }
        if (variant.getType() == 0) {
            variant.dispose();
            return;
        }
        OleAutomation oleAutomation2 = variant.getAutomation();
        variant.dispose();
        this.unhookDOMListeners(new OleAutomation[]{oleAutomation2});
        this.site.addEventListener(oleAutomation2, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -602, this.domListener);
        this.site.addEventListener(oleAutomation2, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -603, this.domListener);
        this.site.addEventListener(oleAutomation2, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -604, this.domListener);
        this.site.addEventListener(oleAutomation2, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -605, this.domListener);
        this.site.addEventListener(oleAutomation2, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -607, this.domListener);
        this.site.addEventListener(oleAutomation2, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", 1033, this.domListener);
        this.site.addEventListener(oleAutomation2, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -601, this.domListener);
        this.site.addEventListener(oleAutomation2, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -606, this.domListener);
        this.site.addEventListener(oleAutomation2, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -2147418101, this.domListener);
        this.site.addEventListener(oleAutomation2, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -2147418091, this.domListener);
        if (bl) {
            this.site.addEventListener(oleAutomation2, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -2147418104, this.domListener);
            this.site.addEventListener(oleAutomation2, "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}", -2147418103, this.domListener);
        }
        OleAutomation[] oleAutomationArray = new OleAutomation[this.documents.length + 1];
        System.arraycopy(this.documents, 0, oleAutomationArray, 0, this.documents.length);
        oleAutomationArray[this.documents.length] = oleAutomation2;
        this.documents = oleAutomationArray;
    }

    void unhookDOMListeners(OleAutomation[] oleAutomationArray) {
        GUID gUID;
        char[] cArray = "{3050F613-98B5-11CF-BB82-00AA00BDCE0B}\u0000".toCharArray();
        if (COM.IIDFromString(cArray, gUID = new GUID()) == 0) {
            for (OleAutomation oleAutomation : oleAutomationArray) {
                this.site.removeEventListener(oleAutomation, gUID, -602, this.domListener);
                this.site.removeEventListener(oleAutomation, gUID, -603, this.domListener);
                this.site.removeEventListener(oleAutomation, gUID, -604, this.domListener);
                this.site.removeEventListener(oleAutomation, gUID, -605, this.domListener);
                this.site.removeEventListener(oleAutomation, gUID, -607, this.domListener);
                this.site.removeEventListener(oleAutomation, gUID, 1033, this.domListener);
                this.site.removeEventListener(oleAutomation, gUID, -601, this.domListener);
                this.site.removeEventListener(oleAutomation, gUID, -606, this.domListener);
                this.site.removeEventListener(oleAutomation, gUID, -2147418101, this.domListener);
                this.site.removeEventListener(oleAutomation, gUID, -2147418091, this.domListener);
                this.site.removeEventListener(oleAutomation, gUID, -2147418104, this.domListener);
                this.site.removeEventListener(oleAutomation, gUID, -2147418103, this.domListener);
            }
        }
    }

    private static void lambda$static$9() {
        TCHAR tCHAR = new TCHAR(0, CookieUrl, true);
        TCHAR tCHAR2 = new TCHAR(0, CookieValue, true);
        CookieResult = OS.InternetSetCookie(tCHAR, null, tCHAR2);
    }

    private static void lambda$static$8() {
        TCHAR tCHAR = new TCHAR(0, CookieUrl, true);
        TCHAR tCHAR2 = new TCHAR(0, 8192);
        int[] nArray = new int[]{tCHAR2.length()};
        if (!OS.InternetGetCookie(tCHAR, null, tCHAR2, nArray)) {
            nArray[0] = nArray[0] / 2;
            tCHAR2 = new TCHAR(0, nArray[0]);
            if (!OS.InternetGetCookie(tCHAR, null, tCHAR2, nArray)) {
                return;
            }
        }
        String string = tCHAR2.toString(0, nArray[0]);
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = stringTokenizer.nextToken();
            int n = string2.indexOf(61);
            if (n == -1 || !string2.substring(0, n).trim().equals(CookieName)) continue;
            CookieValue = string2.substring(n + 1).trim();
            return;
        }
    }

    private static void lambda$static$7() {
        OS.InternetSetOption(0L, 42, 0L, 0);
    }

    /*
     * WARNING - void declaration
     */
    private void lambda$create$6(OleEvent oleEvent) {
        if (this.auto != null) {
            switch (oleEvent.type) {
                case 250: {
                    void l4;
                    LocationListener[] locationListenerArray;
                    Object object2;
                    if (this.performingInitialNavigate) break;
                    Variant variant = oleEvent.arguments[1];
                    String string = variant.getString();
                    if (this.uncRedirect != null) {
                        if (this.uncRedirect.equals(string) || this.uncRedirect.startsWith(string) && this.uncRedirect.indexOf(92, 2) == string.length()) {
                            Variant variant2 = oleEvent.arguments[6];
                            if (variant2 != null) {
                                long l2 = variant2.getByRef();
                                OS.MoveMemory(l2, new short[]{0}, 2);
                            }
                            this.setAboutBlank(false);
                            break;
                        }
                        this.uncRedirect = null;
                    }
                    if (string.indexOf(":/") == -1 && string.indexOf(":\\") != -1) {
                        object2 = new TCHAR(0, string, true);
                        locationListenerArray = new TCHAR(0, 2084);
                        int[] nArray = new int[]{locationListenerArray.length()};
                        String string2 = string = OS.UrlCreateFromPath((TCHAR)object2, (TCHAR)locationListenerArray, nArray, 0) == 0 ? locationListenerArray.toString(0, nArray[0]) : PROTOCOL_FILE + string.replace('\\', '/');
                    }
                    if (string.startsWith(PROTOCOL_FILE) && this._getUrl().startsWith(ABOUT_BLANK) && this.untrustedText) {
                        object2 = oleEvent.arguments[6];
                        if (object2 == null) break;
                        long l3 = ((Variant)object2).getByRef();
                        OS.MoveMemory(l3, new short[]{-1}, 2);
                        break;
                    }
                    object2 = new LocationEvent(this.browser);
                    ((LocationEvent)object2).display = this.browser.getDisplay();
                    ((LocationEvent)object2).widget = this.browser;
                    ((LocationEvent)object2).location = string;
                    ((LocationEvent)object2).doit = true;
                    locationListenerArray = this.locationListeners;
                    int n = locationListenerArray.length;
                    boolean bl = false;
                    while (l4 < n) {
                        LocationListener locationListener = locationListenerArray[l4];
                        locationListener.changing((LocationEvent)object2);
                        ++l4;
                    }
                    boolean bl2 = ((LocationEvent)object2).doit && !this.browser.isDisposed();
                    Variant variant3 = oleEvent.arguments[6];
                    if (variant3 != null) {
                        long iDispatch = variant3.getByRef();
                        OS.MoveMemory(iDispatch, new short[]{bl2 ? (short)0 : -1}, 2);
                    }
                    if (!bl2) break;
                    variant = oleEvent.arguments[0];
                    IDispatch object = variant.getDispatch();
                    Variant variant2 = new Variant(this.auto);
                    IDispatch iDispatch = variant2.getDispatch();
                    if (iDispatch.getAddress() != object.getAddress()) break;
                    this.setAboutBlank(string.startsWith(ABOUT_BLANK));
                    break;
                }
                case 105: {
                    boolean bl = false;
                    Variant variant = oleEvent.arguments[0];
                    int n = variant.getInt();
                    variant = oleEvent.arguments[1];
                    bl = variant.getBoolean();
                    switch (n) {
                        case 2: {
                            this.back = bl;
                            break;
                        }
                        case 1: {
                            this.forward = bl;
                        }
                    }
                    break;
                }
                case 259: {
                    void var10_122;
                    Object object4;
                    Object object5;
                    if (this.performingInitialNavigate) {
                        this.performingInitialNavigate = false;
                        if (this.pendingText != null) {
                            this.setText((String)this.pendingText[0], (Boolean)this.pendingText[1]);
                        } else if (this.pendingUrl != null) {
                            this.setUrl((String)this.pendingUrl[0], (String)this.pendingUrl[1], (String[])this.pendingUrl[2]);
                        }
                        this.pendingUrl = null;
                        this.pendingText = null;
                        break;
                    }
                    Variant variant = oleEvent.arguments[0];
                    IDispatch iDispatch = variant.getDispatch();
                    variant = oleEvent.arguments[1];
                    String string = variant.getString();
                    if (string.indexOf(":/") == -1 && string.indexOf(":\\") != -1) {
                        object5 = new TCHAR(0, string, true);
                        object4 = new TCHAR(0, 2084);
                        int[] object = new int[]{((TCHAR)object4).length()};
                        String string3 = string = OS.UrlCreateFromPath((TCHAR)object5, (TCHAR)object4, object, 0) == 0 ? ((TCHAR)object4).toString(0, object[0]) : PROTOCOL_FILE + string.replace('\\', '/');
                    }
                    if (this.html != null && string.equals(ABOUT_BLANK)) {
                        if (this.delaySetText) {
                            this.delaySetText = false;
                            this.browser.getDisplay().asyncExec(this::lambda$null$3);
                            break;
                        }
                        this.setHTML(this.html);
                        this.html = null;
                        break;
                    }
                    object5 = new Variant(this.auto);
                    object4 = ((Variant)object5).getDispatch();
                    LocationEvent l5 = new LocationEvent(this.browser);
                    l5.display = this.browser.getDisplay();
                    l5.widget = this.browser;
                    l5.location = string;
                    l5.top = ((IUnknown)object4).getAddress() == iDispatch.getAddress();
                    LocationListener[] locationListenerArray = this.locationListeners;
                    int n = locationListenerArray.length;
                    boolean bl = false;
                    while (var10_122 < n) {
                        LocationListener locationListener = locationListenerArray[var10_122];
                        locationListener.changed(l5);
                        ++var10_122;
                    }
                    if (this.browser.isDisposed()) {
                        return;
                    }
                    int[] nArray = this.auto.getIDsOfNames(new String[]{"ReadyState"});
                    Variant variant3 = this.auto.getProperty(nArray[0]);
                    if (variant3 != null) {
                        int n2 = variant3.getInt();
                        variant3.dispose();
                        if (n2 != 4) break;
                    }
                    if (this.globalDispatch == 0L || iDispatch.getAddress() != this.globalDispatch) break;
                    this.globalDispatch = 0L;
                    IE iE = (IE)this.browser.webBrowser;
                    if (iE.installFunctionsOnDocumentComplete) {
                        iE.installFunctionsOnDocumentComplete = false;
                        for (ProgressListener[] progressListenerArray : this.functions.values()) {
                            this.execute(progressListenerArray.functionString);
                        }
                    }
                    ProgressEvent progressEvent = new ProgressEvent(this.browser);
                    progressEvent.display = this.browser.getDisplay();
                    progressEvent.widget = this.browser;
                    for (ProgressListener progressListener : this.progressListeners) {
                        progressListener.completed(progressEvent);
                    }
                    break;
                }
                case 104: {
                    for (ProgressListener[] progressListenerArray : this.functions.values()) {
                        this.execute(progressListenerArray.functionString);
                    }
                    if (!this.isRefresh) break;
                    this.isRefresh = false;
                    ProgressEvent progressEvent = new ProgressEvent(this.browser);
                    progressEvent.display = this.browser.getDisplay();
                    progressEvent.widget = this.browser;
                    for (ProgressListener progressListener : this.progressListeners) {
                        progressListener.completed(progressEvent);
                    }
                    break;
                }
                case 252: {
                    boolean bl;
                    String string;
                    int n;
                    Object object6;
                    this.jsEnabled = this.jsEnabledOnNextPage;
                    Variant variant = oleEvent.arguments[1];
                    String string4 = variant.getString();
                    if (!this.performingInitialNavigate) {
                        Variant variant5 = oleEvent.arguments[0];
                        object6 = variant5.getDispatch();
                        Variant variant6 = new Variant(this.auto);
                        IDispatch object = variant6.getDispatch();
                        if (object.getAddress() == ((IUnknown)object6).getAddress()) {
                            this.setAboutBlank(string4.startsWith(ABOUT_BLANK));
                            this.lastNavigateURL = string4;
                        }
                    }
                    boolean bl2 = false;
                    object6 = null;
                    try {
                        object6 = new URL(string4).getPath();
                    }
                    catch (MalformedURLException malformedURLException) {
                        // empty catch block
                    }
                    if (object6 != null && (n = ((String)object6).lastIndexOf(46)) != -1 && (string = ((String)object6).substring(n)).equalsIgnoreCase(EXTENSION_PDF)) {
                        bl2 = true;
                        if (++PDFCount > 20) {
                            COM.FreeUnusedLibraries = false;
                        }
                    }
                    if (this.uncRedirect != null) {
                        if (this.uncRedirect.equals(string4)) {
                            this.uncRedirect = null;
                            break;
                        }
                        if (this.uncRedirect.startsWith(string4)) {
                            this.navigate(this.uncRedirect, null, null, true);
                            break;
                        }
                        this.uncRedirect = null;
                    }
                    Variant variant7 = oleEvent.arguments[0];
                    IDispatch iDispatch = variant7.getDispatch();
                    if (this.globalDispatch == 0L) {
                        this.globalDispatch = iDispatch.getAddress();
                    }
                    OleAutomation oleAutomation = variant7.getAutomation();
                    Variant variant4 = new Variant(this.auto);
                    IDispatch iDispatch2 = variant4.getDispatch();
                    boolean bl3 = bl = iDispatch2.getAddress() == iDispatch.getAddress();
                    if (bl) {
                        this.unhookDOMListeners(this.documents);
                        for (OleAutomation oleAutomation2 : this.documents) {
                            oleAutomation2.dispose();
                        }
                        this.documents = new OleAutomation[0];
                        for (BrowserFunction browserFunction : this.functions.values()) {
                            this.execute(browserFunction.functionString);
                        }
                    }
                    if (!bl2) {
                        this.hookDOMListeners(oleAutomation, bl);
                    }
                    oleAutomation.dispose();
                    break;
                }
                case 271: {
                    int n;
                    if (this.uncRedirect != null) {
                        this.uncRedirect = null;
                        break;
                    }
                    Variant variant = oleEvent.arguments[1];
                    String string = variant.getString();
                    if (!string.startsWith("\\\\") || (variant = oleEvent.arguments[3]).getInt() != -2146697211 || (n = string.indexOf(92, 2)) == -1) break;
                    String string4 = string.substring(0, n);
                    Variant variant9 = oleEvent.arguments[4];
                    if (variant9 != null) {
                        long l2 = variant9.getByRef();
                        OS.MoveMemory(l2, new short[]{-1}, 2);
                    }
                    this.browser.getDisplay().asyncExec(() -> this.lambda$null$4(string, string4));
                    break;
                }
                case 251: {
                    void var7_91;
                    boolean bl;
                    Variant variant = oleEvent.arguments[1];
                    long l6 = variant.getByRef();
                    WindowEvent windowEvent = new WindowEvent(this.browser);
                    windowEvent.display = this.browser.getDisplay();
                    windowEvent.widget = this.browser;
                    windowEvent.required = false;
                    for (OpenWindowListener openWindowListener : this.openWindowListeners) {
                        openWindowListener.open(windowEvent);
                    }
                    Object var7_89 = null;
                    if (windowEvent.browser != null && windowEvent.browser.webBrowser instanceof IE) {
                        IE iE = (IE)windowEvent.browser.webBrowser;
                    }
                    boolean bl4 = bl = var7_91 != null && !var7_91.browser.isDisposed();
                    if (bl) {
                        var7_91.installFunctionsOnDocumentComplete = true;
                        Variant variant5 = new Variant(var7_91.auto);
                        IDispatch iDispatch = variant5.getDispatch();
                        Variant variant6 = oleEvent.arguments[0];
                        long l3 = variant6.getByRef();
                        if (l3 != 0L) {
                            OS.MoveMemory(l3, new long[]{iDispatch.getAddress()}, C.PTR_SIZEOF);
                        }
                    }
                    if (!windowEvent.required) break;
                    OS.MoveMemory(l6, new short[]{bl ? (short)0 : -1}, 2);
                    break;
                }
                case 256: {
                    Variant variant = oleEvent.arguments[0];
                    this.menuBar = variant.getBoolean();
                    break;
                }
                case 257: {
                    Variant variant = oleEvent.arguments[0];
                    this.statusBar = variant.getBoolean();
                    break;
                }
                case 255: {
                    Variant variant = oleEvent.arguments[0];
                    this.toolBar = variant.getBoolean();
                    if (this.toolBar) break;
                    this.addressBar = false;
                    this.menuBar = false;
                    break;
                }
                case 254: {
                    void var7_94;
                    Variant variant = oleEvent.arguments[0];
                    boolean bl = variant.getBoolean();
                    WindowEvent windowEvent = new WindowEvent(this.browser);
                    windowEvent.display = this.browser.getDisplay();
                    windowEvent.widget = this.browser;
                    if (bl) {
                        void var8_108;
                        Variant variant11;
                        if (this.addressBar && (variant11 = this.auto.getProperty(this.auto.getIDsOfNames(new String[]{"AddressBar"})[0])) != null) {
                            if (variant11.getType() == 11) {
                                this.addressBar = variant11.getBoolean();
                            }
                            variant11.dispose();
                        }
                        windowEvent.addressBar = this.addressBar;
                        windowEvent.menuBar = this.menuBar;
                        windowEvent.statusBar = this.statusBar;
                        windowEvent.toolBar = this.toolBar;
                        windowEvent.location = this.location;
                        windowEvent.size = this.size;
                        VisibilityWindowListener[] visibilityWindowListenerArray = this.visibilityWindowListeners;
                        int n = visibilityWindowListenerArray.length;
                        boolean bl5 = false;
                        while (var8_108 < n) {
                            VisibilityWindowListener visibilityWindowListener = visibilityWindowListenerArray[var8_108];
                            visibilityWindowListener.show(windowEvent);
                            ++var8_108;
                        }
                        this.location = null;
                        this.size = null;
                        break;
                    }
                    VisibilityWindowListener[] visibilityWindowListenerArray = this.visibilityWindowListeners;
                    int n = visibilityWindowListenerArray.length;
                    boolean bl6 = false;
                    while (var7_94 < n) {
                        VisibilityWindowListener visibilityWindowListener = visibilityWindowListenerArray[var7_94];
                        visibilityWindowListener.hide(windowEvent);
                        ++var7_94;
                    }
                    break;
                }
                case 108: {
                    if (this.performingInitialNavigate) break;
                    Variant variant = oleEvent.arguments[0];
                    int n = variant.getType() != 3 ? 0 : variant.getInt();
                    Variant variant12 = oleEvent.arguments[1];
                    int n2 = variant12.getType() != 3 ? 0 : variant12.getInt();
                    ProgressEvent progressEvent = new ProgressEvent(this.browser);
                    progressEvent.display = this.browser.getDisplay();
                    progressEvent.widget = this.browser;
                    progressEvent.current = n;
                    progressEvent.total = n2;
                    if (n == -1) break;
                    for (ProgressListener progressListener : this.progressListeners) {
                        progressListener.changed(progressEvent);
                    }
                    break;
                }
                case 102: {
                    void var7_97;
                    Variant variant;
                    if (this.performingInitialNavigate || (variant = oleEvent.arguments[0]).getType() != 8) break;
                    String string = variant.getString();
                    StatusTextEvent statusTextEvent = new StatusTextEvent(this.browser);
                    statusTextEvent.display = this.browser.getDisplay();
                    statusTextEvent.widget = this.browser;
                    statusTextEvent.text = string;
                    StatusTextListener[] statusTextListenerArray = this.statusTextListeners;
                    int n = statusTextListenerArray.length;
                    boolean bl = false;
                    while (var7_97 < n) {
                        StatusTextListener statusTextListener = statusTextListenerArray[var7_97];
                        statusTextListener.changed(statusTextEvent);
                        ++var7_97;
                    }
                    break;
                }
                case 113: {
                    void var7_99;
                    Variant variant;
                    if (this.performingInitialNavigate || (variant = oleEvent.arguments[0]).getType() != 8) break;
                    String string = variant.getString();
                    TitleEvent titleEvent = new TitleEvent(this.browser);
                    titleEvent.display = this.browser.getDisplay();
                    titleEvent.widget = this.browser;
                    titleEvent.title = string;
                    TitleListener[] titleListenerArray = this.titleListeners;
                    int n = titleListenerArray.length;
                    boolean bl = false;
                    while (var7_99 < n) {
                        TitleListener titleListener = titleListenerArray[var7_99];
                        titleListener.changed(titleEvent);
                        ++var7_99;
                    }
                    break;
                }
                case 263: {
                    this.browser.getDisplay().asyncExec(this::lambda$null$5);
                    Variant variant = oleEvent.arguments[1];
                    long l8 = variant.getByRef();
                    Variant variant13 = oleEvent.arguments[0];
                    boolean bl = variant13.getBoolean();
                    OS.MoveMemory(l8, new short[]{bl ? (short)0 : -1}, 2);
                    break;
                }
                case 267: {
                    if (this.size == null) {
                        this.size = new Point(0, 0);
                    }
                    Variant variant = oleEvent.arguments[0];
                    this.size.y = variant.getInt();
                    break;
                }
                case 264: {
                    if (this.location == null) {
                        this.location = new Point(0, 0);
                    }
                    Variant variant = oleEvent.arguments[0];
                    this.location.x = variant.getInt();
                    break;
                }
                case 265: {
                    if (this.location == null) {
                        this.location = new Point(0, 0);
                    }
                    Variant variant = oleEvent.arguments[0];
                    this.location.y = variant.getInt();
                    break;
                }
                case 266: {
                    if (this.size == null) {
                        this.size = new Point(0, 0);
                    }
                    Variant variant = oleEvent.arguments[0];
                    this.size.x = variant.getInt();
                    break;
                }
            }
        }
    }

    private void lambda$null$5() {
        if (this.browser.isDisposed()) {
            return;
        }
        WindowEvent windowEvent = new WindowEvent(this.browser);
        windowEvent.display = this.browser.getDisplay();
        windowEvent.widget = this.browser;
        for (CloseWindowListener closeWindowListener : this.closeWindowListeners) {
            closeWindowListener.close(windowEvent);
        }
        this.browser.dispose();
    }

    private void lambda$null$4(String string, String string2) {
        if (this.browser.isDisposed()) {
            return;
        }
        this.uncRedirect = string.endsWith("\\") ? string.substring(0, string.length() - 1) : string;
        this.navigate(string2, null, null, true);
    }

    private void lambda$null$3() {
        if (this.browser.isDisposed() || this.html == null) {
            return;
        }
        this.setHTML(this.html);
        this.html = null;
    }

    private void lambda$create$2(Event event) {
        switch (event.type) {
            case 12: {
                Object object;
                if (this.ignoreDispose) {
                    this.ignoreDispose = false;
                    break;
                }
                this.ignoreDispose = true;
                this.browser.notifyListeners(event.type, event);
                event.type = 0;
                if (!this.browser.isClosing) {
                    object = this.locationListeners;
                    this.locationListeners = new LocationListener[0];
                    this.site.ignoreAllMessages = true;
                    this.execute("window.location.href='about:blank'");
                    this.site.ignoreAllMessages = false;
                    this.locationListeners = object;
                }
                if (!this.frame.isDisposed()) {
                    this.unhookDOMListeners(this.documents);
                }
                for (OleAutomation oleAutomation : this.documents) {
                    oleAutomation.dispose();
                }
                this.documents = null;
                object = this.functions.values().iterator();
                while (object.hasNext()) {
                    ((BrowserFunction)object.next()).dispose(false);
                }
                this.functions = null;
                this.uncRedirect = null;
                this.lastNavigateURL = null;
                this.domListener = null;
                if (this.auto != null) {
                    this.auto.dispose();
                }
                this.auto = null;
                break;
            }
            case 11: {
                this.frame.setBounds(this.browser.getClientArea());
                break;
            }
            case 37: {
                event.doit = false;
                break;
            }
            case 15: {
                this.site.setFocus();
                break;
            }
            case 31: {
                if (event.detail == 8 && event.widget instanceof WebSite) {
                    this.browser.traverse(8, event);
                    event.doit = false;
                }
                if (event.detail != 4 || !event.doit || !(event.widget instanceof Browser)) break;
                event.type = 0;
                event.doit = false;
            }
        }
    }

    private void lambda$create$1(OleEvent oleEvent) {
        this.handleDOMEvent(oleEvent);
    }

    private static void lambda$create$0(TCHAR tCHAR, TCHAR tCHAR2, Event event) {
        long[] lArray = new long[1];
        if (OS.RegOpenKeyEx(-2147483647L, tCHAR, 0, 131078, lArray) == 0) {
            OS.RegDeleteValue(lArray[0], tCHAR2);
        }
    }

    static {
        Object object;
        int[] nArray;
        ProgId = "Shell.Explorer";
        SEPARATOR_OS = File.separatorChar;
        NativeClearSessions = IE::lambda$static$7;
        NativeGetCookie = IE::lambda$static$8;
        NativeSetCookie = IE::lambda$static$9;
        TCHAR tCHAR = new TCHAR(0, "Software\\Microsoft\\Internet Explorer", true);
        long[] lArray = new long[1];
        if (OS.RegOpenKeyEx(-2147483646L, tCHAR, 0, 131097, lArray) == 0) {
            int n;
            TCHAR tCHAR2;
            TCHAR tCHAR3 = new TCHAR(0, "svcVersion", true);
            nArray = new int[1];
            int n2 = OS.RegQueryValueEx(lArray[0], tCHAR3, 0L, null, (TCHAR)null, nArray);
            if (n2 != 0) {
                tCHAR3 = new TCHAR(0, "Version", true);
                n2 = OS.RegQueryValueEx(lArray[0], tCHAR3, 0L, null, (TCHAR)null, nArray);
            }
            if (n2 == 0 && (n2 = OS.RegQueryValueEx(lArray[0], tCHAR3, 0L, null, tCHAR2 = new TCHAR(0, nArray[0] / 2), nArray)) == 0 && (n = ((String)(object = tCHAR2.toString(0, tCHAR2.strlen()))).indexOf(".")) != -1) {
                String string = ((String)object).substring(0, n);
                try {
                    IEVersion = Integer.parseInt(string);
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            OS.RegCloseKey(lArray[0]);
        }
        if (OS.RegOpenKeyEx(Integer.MIN_VALUE, tCHAR = new TCHAR(0, "Shell.Explorer\\CLSID", true), 0, 131097, lArray = new long[1]) == 0) {
            TCHAR tCHAR4;
            nArray = new int[1];
            int n = OS.RegQueryValueEx(lArray[0], null, 0L, null, (TCHAR)null, nArray);
            if (n == 0 && (n = OS.RegQueryValueEx(lArray[0], null, 0L, null, tCHAR4 = new TCHAR(0, nArray[0] / 2), nArray)) == 0 && tCHAR4.toString(0, tCHAR4.strlen()).equals(CLSID_SHELLEXPLORER1)) {
                tCHAR = new TCHAR(0, "Shell.Explorer.2", true);
                long[] lArray2 = new long[1];
                object = lArray2;
                if (OS.RegOpenKeyEx(Integer.MIN_VALUE, tCHAR, 0, 131097, lArray2) == 0) {
                    OS.RegCloseKey((long)object[0]);
                    ProgId = "Shell.Explorer.2";
                }
            }
            OS.RegCloseKey(lArray[0]);
        }
        if (NativePendingCookies != null) {
            IE.SetPendingCookies(NativePendingCookies);
        }
        NativePendingCookies = null;
    }
}

