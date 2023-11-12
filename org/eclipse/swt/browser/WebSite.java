/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.browser.AuthenticationEvent;
import org.eclipse.swt.browser.AuthenticationListener;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.IE;
import org.eclipse.swt.browser.WebBrowser;
import org.eclipse.swt.browser.lII;
import org.eclipse.swt.browser.lIIl;
import org.eclipse.swt.browser.lIlI;
import org.eclipse.swt.browser.lIll;
import org.eclipse.swt.browser.ll;
import org.eclipse.swt.browser.llI;
import org.eclipse.swt.browser.llII;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.DISPPARAMS;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IDispatch;
import org.eclipse.swt.internal.ole.win32.IDispatchEx;
import org.eclipse.swt.internal.ole.win32.TYPEATTR;
import org.eclipse.swt.internal.ole.win32.VARIANT;
import org.eclipse.swt.internal.win32.DOCHOSTUIINFO;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleControlSite;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

class WebSite
extends OleControlSite {
    COMObject iDocHostUIHandler;
    COMObject iDocHostShowUI;
    COMObject iServiceProvider;
    COMObject iInternetSecurityManager;
    COMObject iOleCommandTarget;
    COMObject iAuthenticate;
    COMObject iDispatch;
    boolean ignoreNextMessage;
    boolean ignoreAllMessages;
    boolean isForceTrusted;
    Boolean canExecuteApplets;
    static final int OLECMDID_SHOWSCRIPTERROR = 40;
    static final short[] ACCENTS = new short[]{126, 96, 39, 94, 34};
    static final String CONSUME_KEY = "org.eclipse.swt.OleFrame.ConsumeKey";

    public WebSite(Composite composite, int n, String string) {
        super(composite, n, string);
    }

    @Override
    protected void createCOMInterfaces() {
        super.createCOMInterfaces();
        this.iDocHostUIHandler = new lIll(this, new int[]{2, 0, 0, 4, 1, 5, 0, 0, 1, 1, 1, 3, 3, 2, 2, 1, 3, 2});
        this.iDocHostShowUI = new lII(this, new int[]{2, 0, 0, 7, 6});
        this.iServiceProvider = new llII(this, new int[]{2, 0, 0, 3});
        this.iInternetSecurityManager = new llI(this, new int[]{2, 0, 0, 1, 1, 3, 4, 8, 7, 3, 3});
        this.iOleCommandTarget = new lIIl(this, new int[]{2, 0, 0, 4, 5});
        this.iAuthenticate = new ll(this, new int[]{2, 0, 0, 3});
        this.iDispatch = new lIlI(this, new int[]{2, 0, 0, 1, 3, 5, 8});
    }

    @Override
    protected void disposeCOMInterfaces() {
        super.disposeCOMInterfaces();
        if (this.iDocHostUIHandler != null) {
            this.iDocHostUIHandler.dispose();
            this.iDocHostUIHandler = null;
        }
        if (this.iDocHostShowUI != null) {
            this.iDocHostShowUI.dispose();
            this.iDocHostShowUI = null;
        }
        if (this.iServiceProvider != null) {
            this.iServiceProvider.dispose();
            this.iServiceProvider = null;
        }
        if (this.iInternetSecurityManager != null) {
            this.iInternetSecurityManager.dispose();
            this.iInternetSecurityManager = null;
        }
        if (this.iOleCommandTarget != null) {
            this.iOleCommandTarget.dispose();
            this.iOleCommandTarget = null;
        }
        if (this.iAuthenticate != null) {
            this.iAuthenticate.dispose();
            this.iAuthenticate = null;
        }
        if (this.iDispatch != null) {
            this.iDispatch.dispose();
            this.iDispatch = null;
        }
    }

    @Override
    protected int AddRef() {
        return super.AddRef();
    }

    @Override
    protected int QueryInterface(long l2, long l3) {
        int n = super.QueryInterface(l2, l3);
        if (n == 0) {
            return n;
        }
        if (l2 == 0L || l3 == 0L) {
            return -2147024809;
        }
        GUID gUID = new GUID();
        COM.MoveMemory(gUID, l2, GUID.sizeof);
        if (COM.IsEqualGUID(gUID, COM.IIDIDocHostUIHandler)) {
            OS.MoveMemory(l3, new long[]{this.iDocHostUIHandler.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIDocHostShowUI)) {
            OS.MoveMemory(l3, new long[]{this.iDocHostShowUI.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIServiceProvider)) {
            OS.MoveMemory(l3, new long[]{this.iServiceProvider.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIInternetSecurityManager)) {
            OS.MoveMemory(l3, new long[]{this.iInternetSecurityManager.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIOleCommandTarget)) {
            OS.MoveMemory(l3, new long[]{this.iOleCommandTarget.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        OS.MoveMemory(l3, new long[]{0L}, C.PTR_SIZEOF);
        return -2147467262;
    }

    int EnableModeless(int n) {
        return -2147467263;
    }

    int FilterDataObject(long l2, long l3) {
        return -2147467263;
    }

    int GetDropTarget(long l2, long l3) {
        return -2147467263;
    }

    int GetExternal(long l2) {
        OS.MoveMemory(l2, new long[]{this.iDispatch.getAddress()}, C.PTR_SIZEOF);
        this.AddRef();
        return 0;
    }

    int GetHostInfo(long l2) {
        int n = 0x44040000;
        IE iE = (IE)((Browser)this.getParent().getParent()).webBrowser;
        if ((iE.style & 0x800) == 0) {
            n |= 0x200000;
        }
        DOCHOSTUIINFO dOCHOSTUIINFO = new DOCHOSTUIINFO();
        OS.MoveMemory(dOCHOSTUIINFO, l2, DOCHOSTUIINFO.sizeof);
        dOCHOSTUIINFO.dwFlags = n;
        OS.MoveMemory(l2, dOCHOSTUIINFO, DOCHOSTUIINFO.sizeof);
        return 0;
    }

    int GetOptionKeyPath(long l2, int n) {
        return -2147467263;
    }

    int HideUI() {
        return -2147467263;
    }

    int OnDocWindowActivate(int n) {
        return -2147467263;
    }

    int OnFrameWindowActivate(int n) {
        return -2147467263;
    }

    @Override
    protected int Release() {
        return super.Release();
    }

    int ResizeBorder(long l2, long l3, int n) {
        return -2147467263;
    }

    int ShowContextMenu(int n, long l2, long l3, long l4) {
        Browser browser = (Browser)this.getParent().getParent();
        Event event = new Event();
        POINT pOINT = new POINT();
        OS.MoveMemory(pOINT, l2, POINT.sizeof);
        pOINT.x = DPIUtil.autoScaleDown(pOINT.x);
        pOINT.y = DPIUtil.autoScaleDown(pOINT.y);
        event.x = pOINT.x;
        event.y = pOINT.y;
        browser.notifyListeners(35, event);
        if (!event.doit) {
            return 0;
        }
        Menu menu = browser.getMenu();
        if (menu != null && !menu.isDisposed()) {
            if (pOINT.x != event.x || pOINT.y != event.y) {
                menu.setLocation(event.x, event.y);
            }
            menu.setVisible(true);
            return 0;
        }
        return 1;
    }

    int ShowUI(int n, long l2, long l3, long l4, long l5) {
        return 1;
    }

    int TranslateAccelerator(long l2, long l3, int n) {
        Object object;
        Menu menu = this.getShell().getMenuBar();
        if (menu != null && !menu.isDisposed() && menu.isEnabled()) {
            Shell shell = menu.getShell();
            long l4 = shell.handle;
            long l5 = OS.SendMessage(l4, 32769, 0L, 0L);
            if (l5 != 0L) {
                object = new MSG();
                OS.MoveMemory((MSG)object, l2, MSG.sizeof);
                if (OS.TranslateAccelerator(l4, l5, (MSG)object) != 0) {
                    return 0;
                }
            }
        }
        int n2 = 1;
        MSG mSG = new MSG();
        OS.MoveMemory(mSG, l2, MSG.sizeof);
        if (mSG.message == 256) {
            switch ((int)mSG.wParam) {
                case 116: {
                    OleAutomation oleAutomation = new OleAutomation(this);
                    int[] nArray = oleAutomation.getIDsOfNames(new String[]{"LocationURL"});
                    Variant variant = oleAutomation.getProperty(nArray[0]);
                    oleAutomation.dispose();
                    if (variant == null) break;
                    if (variant.getType() == 8 && ((String)(object = variant.getString())).equals("about:blank")) {
                        n2 = 0;
                    }
                    variant.dispose();
                    break;
                }
                case 8: 
                case 9: 
                case 13: 
                case 32: 
                case 33: 
                case 34: 
                case 35: 
                case 36: 
                case 37: 
                case 38: 
                case 39: 
                case 40: {
                    break;
                }
                case 76: 
                case 78: 
                case 79: {
                    if (OS.GetKeyState(17) < 0 && OS.GetKeyState(18) >= 0 && OS.GetKeyState(16) >= 0 && (mSG.wParam == 78L || IE.IEVersion >= 8)) {
                        this.frame.setData(CONSUME_KEY, "false");
                        n2 = 0;
                        break;
                    }
                }
                default: {
                    OS.TranslateMessage(mSG);
                    this.frame.setData(CONSUME_KEY, "true");
                }
            }
        }
        switch (mSG.message) {
            case 256: 
            case 257: {
                boolean bl = false;
                block8 : switch ((int)mSG.wParam) {
                    case 16: 
                    case 17: 
                    case 18: 
                    case 20: 
                    case 144: 
                    case 145: {
                        break;
                    }
                    default: {
                        int n3 = OS.MapVirtualKey((int)mSG.wParam, 2);
                        if (n3 == 0) break;
                        boolean bl2 = bl = (n3 & Integer.MIN_VALUE) != 0;
                        if (bl) break;
                        for (Object object2 : (Variant)ACCENTS) {
                            short s = OS.VkKeyScan((short)object2);
                            if (s == -1 || (long)(s & 0xFF) != mSG.wParam) continue;
                            int n4 = s >> 8;
                            if (OS.GetKeyState(16) < 0 != ((n4 & 1) != 0) || OS.GetKeyState(17) < 0 != ((n4 & 2) != 0) || OS.GetKeyState(18) < 0 != ((n4 & 4) != 0)) continue;
                            if ((n4 & 7) == 0) break block8;
                            bl = true;
                            break block8;
                        }
                    }
                }
                if (!bl) break;
                n2 = 0;
                break;
            }
        }
        return n2;
    }

    int TranslateUrl(int n, long l2, long l3) {
        return -2147467263;
    }

    int UpdateUI() {
        return -2147467263;
    }

    int ShowMessage(long l2, long l3, long l4, int n, long l5, int n2, long l6) {
        boolean bl = this.ignoreNextMessage || this.ignoreAllMessages;
        this.ignoreNextMessage = false;
        return bl ? 0 : 1;
    }

    int ShowHelp(long l2, long l3, int n, int n2, long l4, long l5) {
        Browser browser = (Browser)this.getParent().getParent();
        Event event = new Event();
        event.type = 28;
        event.display = this.getDisplay();
        event.widget = browser;
        Shell shell = browser.getShell();
        Composite composite = browser;
        while (!composite.isListening(28)) {
            if (composite == shell) {
                return 0;
            }
            composite = composite.getParent();
        }
        composite.notifyListeners(28, event);
        return 0;
    }

    int QueryService(long l2, long l3, long l4) {
        if (l3 == 0L || l4 == 0L) {
            return -2147024809;
        }
        GUID gUID = new GUID();
        COM.MoveMemory(gUID, l3, GUID.sizeof);
        if (COM.IsEqualGUID(gUID, COM.IIDIInternetSecurityManager)) {
            OS.MoveMemory(l4, new long[]{this.iInternetSecurityManager.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAuthenticate)) {
            OS.MoveMemory(l4, new long[]{this.iAuthenticate.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        OS.MoveMemory(l4, new long[]{0L}, C.PTR_SIZEOF);
        return -2147467262;
    }

    int SetSecuritySite(long l2) {
        return -2146697199;
    }

    int GetSecuritySite(long l2) {
        return -2146697199;
    }

    int MapUrlToZone(long l2, long l3, int n) {
        if (this.isForceTrusted) {
            OS.MoveMemory(l3, new int[]{1}, 4);
            return 0;
        }
        return -2146697199;
    }

    int GetSecurityId(long l2, long l3, long l4, long l5) {
        return -2146697199;
    }

    /*
     * Exception decompiling
     */
    int ProcessUrlAction(long var1, int var3, long var4, int var6, long var7, int var9, int var10, int var11) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl48 : IF_ICMPGE - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
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

    int QueryCustomPolicy(long l2, long l3, long l4, long l5, long l6, int n, int n2) {
        return -2146697199;
    }

    int SetZoneMapping(int n, long l2, int n2) {
        return -2146697199;
    }

    int GetZoneMappings(int n, long l2, int n2) {
        return -2147467263;
    }

    int QueryStatus(long l2, int n, long l3, long l4) {
        return -2147221248;
    }

    int Exec(long l2, int n, int n2, long l3, long l4) {
        if (l2 != 0L) {
            GUID gUID = new GUID();
            COM.MoveMemory(gUID, l2, GUID.sizeof);
            if (COM.IsEqualGUID(gUID, COM.CGID_DocHostCommandHandler) && n == 40) {
                return 0;
            }
            if (n == 1 && COM.IsEqualGUID(gUID, COM.CGID_Explorer) && (n2 & 0xFFFF) == 10) {
                IE iE = (IE)((Browser)this.getParent().getParent()).webBrowser;
                iE.toolBar = (n2 & 0xFFFF0000) != 0;
            }
        }
        return -2147221248;
    }

    int Authenticate(long l2, long l3, long l4) {
        IE iE = (IE)((Browser)this.getParent().getParent()).webBrowser;
        for (AuthenticationListener authenticationListener : iE.authenticationListeners) {
            AuthenticationEvent authenticationEvent = new AuthenticationEvent(iE.browser);
            authenticationEvent.location = iE.lastNavigateURL;
            authenticationListener.authenticate(authenticationEvent);
            if (!authenticationEvent.doit) {
                return -2147024891;
            }
            if (authenticationEvent.user == null || authenticationEvent.password == null) continue;
            TCHAR tCHAR = new TCHAR(0, authenticationEvent.user, true);
            int n = tCHAR.length() * 2;
            long l5 = OS.CoTaskMemAlloc(n);
            OS.MoveMemory(l5, tCHAR, n);
            TCHAR tCHAR2 = new TCHAR(0, authenticationEvent.password, true);
            n = tCHAR2.length() * 2;
            long l6 = OS.CoTaskMemAlloc(n);
            OS.MoveMemory(l6, tCHAR2, n);
            C.memmove(l2, new long[]{0L}, (long)C.PTR_SIZEOF);
            C.memmove(l3, new long[]{l5}, (long)C.PTR_SIZEOF);
            C.memmove(l4, new long[]{l6}, (long)C.PTR_SIZEOF);
            return 0;
        }
        C.memmove(l2, new long[]{this.getShell().handle}, (long)C.PTR_SIZEOF);
        return 0;
    }

    int GetTypeInfoCount(long l2) {
        C.memmove(l2, new int[]{0}, 4L);
        return 0;
    }

    int GetTypeInfo(int n, int n2, long l2) {
        return 0;
    }

    int GetIDsOfNames(int n, long l2, int n2, int n3, long l3) {
        long[] lArray = new long[]{0L};
        OS.MoveMemory(lArray, l2, C.PTR_SIZEOF);
        int n4 = OS.wcslen(lArray[0]);
        char[] cArray = new char[n4];
        OS.MoveMemory(cArray, lArray[0], n4 * 2);
        String string = String.valueOf(cArray);
        int n5 = 0;
        int[] nArray = new int[n2];
        if (string.equals("callJava")) {
            for (int i = 0; i < n2; ++i) {
                nArray[i] = i + 1;
            }
        } else {
            n5 = -2147352570;
            for (int i = 0; i < n2; ++i) {
                nArray[i] = -1;
            }
        }
        OS.MoveMemory(l3, nArray, n2 * 4);
        return n5;
    }

    int Invoke(int n, long l2, int n2, int n3, long l3, long l4, long l5, long l6) {
        IE iE = (IE)((Browser)this.getParent().getParent()).webBrowser;
        Map map = iE.functions;
        if (map == null) {
            if (l4 != 0L) {
                OS.MoveMemory(l4, new long[]{0L}, C.PTR_SIZEOF);
            }
            return 0;
        }
        DISPPARAMS dISPPARAMS = new DISPPARAMS();
        COM.MoveMemory(dISPPARAMS, l3, DISPPARAMS.sizeof);
        if (dISPPARAMS.cArgs != 3) {
            if (l4 != 0L) {
                OS.MoveMemory(l4, new long[]{0L}, C.PTR_SIZEOF);
            }
            return 0;
        }
        long l7 = dISPPARAMS.rgvarg + (long)(2 * Variant.sizeof);
        Variant variant = Variant.win32_new(l7);
        if (variant.getType() != 3) {
            variant.dispose();
            if (l4 != 0L) {
                OS.MoveMemory(l4, new long[]{0L}, C.PTR_SIZEOF);
            }
            return 0;
        }
        int n4 = variant.getInt();
        variant.dispose();
        if (n4 <= 0) {
            if (l4 != 0L) {
                OS.MoveMemory(l4, new long[]{0L}, C.PTR_SIZEOF);
            }
            return 0;
        }
        l7 = dISPPARAMS.rgvarg + (long)Variant.sizeof;
        variant = Variant.win32_new(l7);
        short s = variant.getType();
        if (s != 8) {
            variant.dispose();
            if (l4 != 0L) {
                OS.MoveMemory(l4, new long[]{0L}, C.PTR_SIZEOF);
            }
            return 0;
        }
        String string = variant.getString();
        variant.dispose();
        variant = Variant.win32_new(dISPPARAMS.rgvarg);
        BrowserFunction browserFunction = (BrowserFunction)map.get(n4);
        Object object = null;
        if (browserFunction != null && string.equals(browserFunction.token)) {
            try {
                Object object2 = this.convertToJava(variant);
                if (object2 instanceof Object[]) {
                    Object[] objectArray = (Object[])object2;
                    try {
                        object = browserFunction.function(objectArray);
                    }
                    catch (Exception exception) {
                        object = WebBrowser.CreateErrorString(exception.getLocalizedMessage());
                    }
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                if (browserFunction.isEvaluate) {
                    browserFunction.function(new String[]{WebBrowser.CreateErrorString(new SWTException(51).getLocalizedMessage())});
                }
                object = WebBrowser.CreateErrorString(illegalArgumentException.getLocalizedMessage());
            }
        }
        variant.dispose();
        if (l4 != 0L) {
            try {
                variant = this.convertToJS(object);
            }
            catch (SWTException sWTException) {
                variant = this.convertToJS(WebBrowser.CreateErrorString(sWTException.getLocalizedMessage()));
            }
            Variant.win32_copy(l4, variant);
            variant.dispose();
        }
        return 0;
    }

    Object convertToJava(Variant variant) {
        switch (variant.getType()) {
            case 0: 
            case 1: {
                return null;
            }
            case 8: {
                return variant.getString();
            }
            case 11: {
                return variant.getBoolean();
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 20: {
                return variant.getDouble();
            }
            case 9: {
                Object[] objectArray = null;
                OleAutomation oleAutomation = variant.getAutomation();
                TYPEATTR tYPEATTR = oleAutomation.getTypeInfoAttributes();
                if (tYPEATTR != null) {
                    GUID gUID = new GUID();
                    gUID.Data1 = tYPEATTR.guid_Data1;
                    gUID.Data2 = tYPEATTR.guid_Data2;
                    gUID.Data3 = tYPEATTR.guid_Data3;
                    gUID.Data4 = tYPEATTR.guid_Data4;
                    if (COM.IsEqualGUID(gUID, COM.IIDIJScriptTypeInfo)) {
                        int[] nArray = oleAutomation.getIDsOfNames(new String[]{"length"});
                        if (nArray != null) {
                            Variant variant2 = oleAutomation.getProperty(nArray[0]);
                            int n = variant2.getInt();
                            variant2.dispose();
                            objectArray = new Object[n];
                            for (int i = 0; i < n; ++i) {
                                nArray = oleAutomation.getIDsOfNames(new String[]{String.valueOf(i)});
                                if (nArray == null) continue;
                                Variant variant3 = oleAutomation.getProperty(nArray[0]);
                                try {
                                    objectArray[i] = this.convertToJava(variant3);
                                    variant3.dispose();
                                    continue;
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    variant3.dispose();
                                    oleAutomation.dispose();
                                    throw illegalArgumentException;
                                }
                            }
                        }
                    } else {
                        oleAutomation.dispose();
                        SWT.error(5);
                    }
                }
                oleAutomation.dispose();
                return objectArray;
            }
        }
        SWT.error(5);
        return null;
    }

    Variant convertToJS(Object object) {
        if (object == null) {
            return Variant.NULL;
        }
        if (object instanceof String) {
            return new Variant((String)object);
        }
        if (object instanceof Boolean) {
            return new Variant((Boolean)object);
        }
        if (object instanceof Number) {
            return new Variant(((Number)object).doubleValue());
        }
        if (!(object instanceof Object[])) {
            SWT.error(51);
            return null;
        }
        IE iE = (IE)((Browser)this.getParent().getParent()).webBrowser;
        OleAutomation oleAutomation = iE.auto;
        int[] nArray = oleAutomation.getIDsOfNames(new String[]{"Document"});
        if (nArray == null) {
            return new Variant();
        }
        Variant variant = oleAutomation.getProperty(nArray[0]);
        if (variant == null) {
            return new Variant();
        }
        if (variant.getType() == 0) {
            variant.dispose();
            return new Variant();
        }
        OleAutomation oleAutomation2 = variant.getAutomation();
        variant.dispose();
        nArray = oleAutomation2.getIDsOfNames(new String[]{"parentWindow"});
        if (nArray == null) {
            oleAutomation2.dispose();
            return new Variant();
        }
        variant = oleAutomation2.getProperty(nArray[0]);
        if (variant == null || variant.getType() == 0) {
            if (variant != null) {
                variant.dispose();
            }
            oleAutomation2.dispose();
            return new Variant();
        }
        OleAutomation oleAutomation3 = variant.getAutomation();
        variant.dispose();
        oleAutomation2.dispose();
        nArray = oleAutomation3.getIDsOfNames(new String[]{"Array"});
        if (nArray == null) {
            oleAutomation3.dispose();
            return new Variant();
        }
        Variant variant2 = oleAutomation3.getProperty(nArray[0]);
        oleAutomation3.dispose();
        IDispatch iDispatch = variant2.getDispatch();
        long[] lArray = new long[]{0L};
        int n = iDispatch.QueryInterface(COM.IIDIDispatchEx, lArray);
        variant2.dispose();
        if (n != 0) {
            return new Variant();
        }
        IDispatchEx iDispatchEx = new IDispatchEx(lArray[0]);
        lArray[0] = 0L;
        DISPPARAMS dISPPARAMS = new DISPPARAMS();
        long l2 = OS.GlobalAlloc(64, VARIANT.sizeof);
        n = iDispatchEx.InvokeEx(0, 2048, 16384, dISPPARAMS, l2, null, 0L);
        if (n != 0) {
            OS.GlobalFree(l2);
            return new Variant();
        }
        Variant variant3 = Variant.win32_new(l2);
        OS.GlobalFree(l2);
        Object[] objectArray = (Object[])object;
        oleAutomation = variant3.getAutomation();
        int[] nArray2 = oleAutomation.getIDsOfNames(new String[]{"push"});
        if (nArray2 != null) {
            for (Object object2 : objectArray) {
                try {
                    Variant variant4 = this.convertToJS(object2);
                    oleAutomation.invoke(nArray2[0], new Variant[]{variant4});
                    variant4.dispose();
                }
                catch (SWTException sWTException) {
                    oleAutomation.dispose();
                    variant3.dispose();
                    throw sWTException;
                }
            }
        }
        oleAutomation.dispose();
        return variant3;
    }
}

