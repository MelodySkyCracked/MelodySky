/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.ole.win32;

import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.CONTROLINFO;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IClassFactory2;
import org.eclipse.swt.internal.ole.win32.IOleControl;
import org.eclipse.swt.internal.ole.win32.IPersistStorage;
import org.eclipse.swt.internal.ole.win32.IProvideClassInfo;
import org.eclipse.swt.internal.ole.win32.IProvideClassInfo2;
import org.eclipse.swt.internal.ole.win32.ITypeInfo;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.ole.win32.LICINFO;
import org.eclipse.swt.internal.ole.win32.TYPEATTR;
import org.eclipse.swt.internal.win32.GUITHREADINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.ole.win32.I;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleEventSink;
import org.eclipse.swt.ole.win32.OleListener;
import org.eclipse.swt.ole.win32.OlePropertyChangeSink;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.ole.win32.l;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

public class OleControlSite
extends OleClientSite {
    private COMObject iOleControlSite;
    private COMObject iDispatch;
    private OlePropertyChangeSink olePropertyChangeSink;
    private OleEventSink[] oleEventSink = new OleEventSink[0];
    private GUID[] oleEventSinkGUID = new GUID[0];
    private long[] oleEventSinkIUnknown = new long[0];
    private CONTROLINFO currentControlInfo;
    private int[] sitePropertyIds = new int[0];
    private Variant[] sitePropertyValues = new Variant[0];
    private Font font;
    static int SWT_RESTORECARET;
    static final String SHELL_PROG_ID = "Shell.Explorer";

    public OleControlSite(Composite composite, int n, File file) {
        super(composite, n, file);
        this.setSiteProperty(-709, new Variant(true));
        this.setSiteProperty(-710, new Variant(false));
    }

    public OleControlSite(Composite composite, int n, String string) {
        super(composite, n);
        try {
            long l2;
            this.appClsid = this.getClassID(string);
            if (this.appClsid == null) {
                OLE.error(1004);
            }
            if ((l2 = this.getLicenseInfo(this.appClsid)) == 0L) {
                this.tempStorage = this.createTempStorage();
                long[] lArray = new long[]{0L};
                long l3 = this.isICAClient() ? 0L : this.iOleClientSite.getAddress();
                int n2 = COM.OleCreate(this.appClsid, COM.IIDIUnknown, 1, null, l3, this.tempStorage.getAddress(), lArray);
                if (n2 != 0) {
                    OLE.error(1001, n2);
                }
                this.objIUnknown = new IUnknown(lArray[0]);
            } else {
                long[] lArray = new long[]{0L};
                int n3 = COM.CoGetClassObject(this.appClsid, 3, 0L, COM.IIDIClassFactory2, lArray);
                if (n3 != 0) {
                    OLE.error(1005, n3);
                }
                IClassFactory2 iClassFactory2 = new IClassFactory2(lArray[0]);
                lArray = new long[]{0L};
                n3 = iClassFactory2.CreateInstanceLic(0L, 0L, COM.IIDIUnknown, l2, lArray);
                iClassFactory2.Release();
                if (n3 != 0) {
                    OLE.error(1006, n3);
                }
                COM.SysFreeString(l2);
                this.objIUnknown = new IUnknown(lArray[0]);
                lArray = new long[]{0L};
                if (this.objIUnknown.QueryInterface(COM.IIDIPersistStorage, lArray) == 0) {
                    IPersistStorage iPersistStorage = new IPersistStorage(lArray[0]);
                    this.tempStorage = this.createTempStorage();
                    iPersistStorage.InitNew(this.tempStorage.getAddress());
                    iPersistStorage.Release();
                }
            }
            this.addObjectReferences();
            this.setSiteProperty(-709, new Variant(true));
            this.setSiteProperty(-710, new Variant(false));
            if (COM.OleRun(this.objIUnknown.getAddress()) == 0) {
                this.state = 1;
            }
        }
        catch (SWTError sWTError) {
            this.dispose();
            this.disposeCOMInterfaces();
            throw sWTError;
        }
    }

    public OleControlSite(Composite composite, int n, String string, File file) {
        super(composite, n, string, file);
        this.setSiteProperty(-709, new Variant(true));
        this.setSiteProperty(-710, new Variant(false));
    }

    public void addEventListener(int n, OleListener oleListener) {
        GUID gUID;
        if (oleListener == null) {
            OLE.error(4);
        }
        if ((gUID = OleControlSite.getDefaultEventSinkGUID(this.objIUnknown)) != null) {
            this.addEventListener(this.objIUnknown.getAddress(), gUID, n, oleListener);
        }
    }

    static GUID getDefaultEventSinkGUID(IUnknown iUnknown) {
        Object object;
        IProvideClassInfo iProvideClassInfo;
        long[] lArray = new long[]{0L};
        if (iUnknown.QueryInterface(COM.IIDIProvideClassInfo2, lArray) == 0) {
            iProvideClassInfo = new IProvideClassInfo2(lArray[0]);
            object = new GUID();
            int n = ((IProvideClassInfo2)iProvideClassInfo).GetGUID(1, (GUID)object);
            iProvideClassInfo.Release();
            if (n == 0) {
                return object;
            }
        }
        if (iUnknown.QueryInterface(COM.IIDIProvideClassInfo, lArray) == 0) {
            iProvideClassInfo = new IProvideClassInfo(lArray[0]);
            object = new long[]{0L};
            long[] lArray2 = new long[]{0L};
            int n = iProvideClassInfo.GetClassInfo((long[])object);
            iProvideClassInfo.Release();
            if (n == 0 && object[0] != 0L) {
                Object object2;
                ITypeInfo iTypeInfo = new ITypeInfo((long)object[0]);
                long[] lArray3 = new long[]{0L};
                n = iTypeInfo.GetTypeAttr(lArray3);
                if (n == 0 && lArray3[0] != 0L) {
                    object2 = new TYPEATTR();
                    COM.MoveMemory((TYPEATTR)object2, lArray3[0], TYPEATTR.sizeof);
                    iTypeInfo.ReleaseTypeAttr(lArray3[0]);
                    int n2 = 7;
                    int n3 = 3;
                    for (int i = 0; i < ((TYPEATTR)object2).cImplTypes; ++i) {
                        int[] nArray;
                        int[] nArray2 = new int[]{0};
                        if (iTypeInfo.GetImplTypeFlags(i, nArray2) != 0 || (nArray2[0] & 7) != 3 || iTypeInfo.GetRefTypeOfImplType(i, nArray = new int[]{0}) != 0) continue;
                        iTypeInfo.GetRefTypeInfo(nArray[0], lArray2);
                    }
                }
                iTypeInfo.Release();
                if (lArray2[0] != 0L) {
                    object2 = new ITypeInfo(lArray2[0]);
                    lArray3 = new long[]{0L};
                    n = ((ITypeInfo)object2).GetTypeAttr(lArray3);
                    GUID gUID = null;
                    if (n == 0 && lArray3[0] != 0L) {
                        gUID = new GUID();
                        COM.MoveMemory(gUID, lArray3[0], GUID.sizeof);
                        ((ITypeInfo)object2).ReleaseTypeAttr(lArray3[0]);
                    }
                    ((IUnknown)object2).Release();
                    return gUID;
                }
            }
        }
        return null;
    }

    public void addEventListener(OleAutomation oleAutomation, int n, OleListener oleListener) {
        long l2;
        IUnknown iUnknown;
        GUID gUID;
        if (oleListener == null || oleAutomation == null) {
            OLE.error(4);
        }
        if ((gUID = OleControlSite.getDefaultEventSinkGUID(iUnknown = new IUnknown(l2 = oleAutomation.getAddress()))) != null) {
            this.addEventListener(l2, gUID, n, oleListener);
        }
    }

    public void addEventListener(OleAutomation oleAutomation, String string, int n, OleListener oleListener) {
        GUID gUID;
        long l2;
        if (oleListener == null || oleAutomation == null || string == null) {
            OLE.error(4);
        }
        if ((l2 = oleAutomation.getAddress()) == 0L) {
            return;
        }
        char[] cArray = string.toCharArray();
        if (COM.IIDFromString(cArray, gUID = new GUID()) != 0) {
            return;
        }
        this.addEventListener(l2, gUID, n, oleListener);
    }

    void addEventListener(long l2, GUID gUID, int n, OleListener oleListener) {
        int n2;
        if (oleListener == null || l2 == 0L || gUID == null) {
            OLE.error(4);
        }
        int n3 = -1;
        for (n2 = 0; n2 < this.oleEventSinkGUID.length; ++n2) {
            if (!COM.IsEqualGUID(this.oleEventSinkGUID[n2], gUID) || l2 != this.oleEventSinkIUnknown[n2]) continue;
            n3 = n2;
            break;
        }
        if (n3 != -1) {
            this.oleEventSink[n3].addListener(n, oleListener);
        } else {
            n2 = this.oleEventSink.length;
            OleEventSink[] oleEventSinkArray = new OleEventSink[n2 + 1];
            GUID[] gUIDArray = new GUID[n2 + 1];
            long[] lArray = new long[n2 + 1];
            System.arraycopy(this.oleEventSink, 0, oleEventSinkArray, 0, n2);
            System.arraycopy(this.oleEventSinkGUID, 0, gUIDArray, 0, n2);
            System.arraycopy(this.oleEventSinkIUnknown, 0, lArray, 0, n2);
            this.oleEventSink = oleEventSinkArray;
            this.oleEventSinkGUID = gUIDArray;
            this.oleEventSinkIUnknown = lArray;
            this.oleEventSink[n2] = new OleEventSink(this, l2, gUID);
            this.oleEventSinkGUID[n2] = gUID;
            this.oleEventSinkIUnknown[n2] = l2;
            this.oleEventSink[n2].AddRef();
            this.oleEventSink[n2].connect();
            this.oleEventSink[n2].addListener(n, oleListener);
        }
    }

    @Override
    protected void addObjectReferences() {
        super.addObjectReferences();
        this.connectPropertyChangeSink();
        long[] lArray = new long[]{0L};
        if (this.objIUnknown.QueryInterface(COM.IIDIOleControl, lArray) == 0) {
            IOleControl iOleControl = new IOleControl(lArray[0]);
            this.currentControlInfo = new CONTROLINFO();
            iOleControl.GetControlInfo(this.currentControlInfo);
            iOleControl.Release();
        }
    }

    public void addPropertyListener(int n, OleListener oleListener) {
        if (oleListener == null) {
            SWT.error(4);
        }
        this.olePropertyChangeSink.addListener(n, oleListener);
    }

    private void connectPropertyChangeSink() {
        this.olePropertyChangeSink = new OlePropertyChangeSink(this);
        this.olePropertyChangeSink.AddRef();
        this.olePropertyChangeSink.connect(this.objIUnknown);
    }

    @Override
    protected void createCOMInterfaces() {
        super.createCOMInterfaces();
        this.iOleControlSite = new l(this, new int[]{2, 0, 0, 0, 1, 1, 3, 2, 1, 0});
        this.iDispatch = new I(this, new int[]{2, 0, 0, 1, 3, 5, 8});
    }

    private void disconnectEventSinks() {
        for (OleEventSink oleEventSink : this.oleEventSink) {
            oleEventSink.disconnect();
            oleEventSink.Release();
        }
        this.oleEventSink = new OleEventSink[0];
        this.oleEventSinkGUID = new GUID[0];
        this.oleEventSinkIUnknown = new long[0];
    }

    private void disconnectPropertyChangeSink() {
        if (this.olePropertyChangeSink != null) {
            this.olePropertyChangeSink.disconnect(this.objIUnknown);
            this.olePropertyChangeSink.Release();
        }
        this.olePropertyChangeSink = null;
    }

    @Override
    protected void disposeCOMInterfaces() {
        super.disposeCOMInterfaces();
        if (this.iOleControlSite != null) {
            this.iOleControlSite.dispose();
        }
        this.iOleControlSite = null;
        if (this.iDispatch != null) {
            this.iDispatch.dispose();
        }
        this.iDispatch = null;
    }

    @Override
    public Color getBackground() {
        if (this.objIUnknown != null) {
            OleAutomation oleAutomation = new OleAutomation(this);
            Variant variant = oleAutomation.getProperty(-501);
            oleAutomation.dispose();
            if (variant != null) {
                int[] nArray = new int[]{0};
                if (COM.OleTranslateColor(variant.getInt(), 0L, nArray) == 0) {
                    return Color.win32_new(this.getDisplay(), nArray[0]);
                }
            }
        }
        return super.getBackground();
    }

    @Override
    public Font getFont() {
        if (this.font != null && !this.font.isDisposed()) {
            return this.font;
        }
        if (this.objIUnknown != null) {
            OleAutomation oleAutomation = new OleAutomation(this);
            Variant variant = oleAutomation.getProperty(-512);
            oleAutomation.dispose();
            if (variant != null) {
                OleAutomation oleAutomation2 = variant.getAutomation();
                Variant variant2 = oleAutomation2.getProperty(0);
                Variant variant3 = oleAutomation2.getProperty(2);
                Variant variant4 = oleAutomation2.getProperty(4);
                Variant variant5 = oleAutomation2.getProperty(3);
                oleAutomation2.dispose();
                if (variant2 != null && variant3 != null && variant4 != null && variant5 != null) {
                    int n = 3 * variant5.getInt() + 2 * variant4.getInt();
                    this.font = new Font((Device)this.getShell().getDisplay(), variant2.getString(), variant3.getInt(), n);
                    return this.font;
                }
            }
        }
        return super.getFont();
    }

    @Override
    public Color getForeground() {
        if (this.objIUnknown != null) {
            OleAutomation oleAutomation = new OleAutomation(this);
            Variant variant = oleAutomation.getProperty(-513);
            oleAutomation.dispose();
            if (variant != null) {
                int[] nArray = new int[]{0};
                if (COM.OleTranslateColor(variant.getInt(), 0L, nArray) == 0) {
                    return Color.win32_new(this.getDisplay(), nArray[0]);
                }
            }
        }
        return super.getForeground();
    }

    protected long getLicenseInfo(GUID gUID) {
        long[] lArray = new long[]{0L};
        if (COM.CoGetClassObject(gUID, 3, 0L, COM.IIDIClassFactory, lArray) != 0) {
            return 0L;
        }
        long l2 = 0L;
        IUnknown iUnknown = new IUnknown(lArray[0]);
        if (iUnknown.QueryInterface(COM.IIDIClassFactory2, lArray) == 0) {
            IClassFactory2 iClassFactory2 = new IClassFactory2(lArray[0]);
            LICINFO lICINFO = new LICINFO();
            if (iClassFactory2.GetLicInfo(lICINFO) == 0) {
                long[] lArray2 = new long[]{0L};
                if (lICINFO != null && lICINFO.fRuntimeKeyAvail && iClassFactory2.RequestLicKey(0, lArray2) == 0) {
                    l2 = lArray2[0];
                }
            }
            iClassFactory2.Release();
        }
        iUnknown.Release();
        return l2;
    }

    public Variant getSiteProperty(int n) {
        for (int i = 0; i < this.sitePropertyIds.length; ++i) {
            if (this.sitePropertyIds[i] != n) continue;
            return this.sitePropertyValues[i];
        }
        return null;
    }

    @Override
    protected int GetWindow(long l2) {
        if (l2 == 0L) {
            return -2147024809;
        }
        if (this.frame == null) {
            OS.MoveMemory(l2, new long[]{0L}, C.PTR_SIZEOF);
            return -2147467263;
        }
        OS.MoveMemory(l2, new long[]{this.handle}, C.PTR_SIZEOF);
        return 0;
    }

    private int Invoke(int n, long l2, int n2, int n3, long l3, long l4, long l5, long l6) {
        if (l4 == 0L || n3 != 2) {
            if (l5 != 0L) {
                OS.MoveMemory(l5, new long[]{0L}, C.PTR_SIZEOF);
            }
            if (l6 != 0L) {
                OS.MoveMemory(l6, new int[]{0}, 4);
            }
            return -2147352573;
        }
        Variant variant = this.getSiteProperty(n);
        if (variant != null) {
            if (l4 != 0L) {
                variant.getData(l4);
            }
            return 0;
        }
        switch (n) {
            case -714: 
            case -712: 
            case -711: {
                if (l4 != 0L) {
                    OS.MoveMemory(l4, new long[]{0L}, C.PTR_SIZEOF);
                }
                if (l5 != 0L) {
                    OS.MoveMemory(l5, new long[]{0L}, C.PTR_SIZEOF);
                }
                if (l6 != 0L) {
                    OS.MoveMemory(l6, new int[]{0}, 4);
                }
                return 1;
            }
            case -5502: 
            case -5501: 
            case -706: 
            case -705: 
            case -704: 
            case -703: 
            case -701: {
                if (l4 != 0L) {
                    OS.MoveMemory(l4, new long[]{0L}, C.PTR_SIZEOF);
                }
                if (l5 != 0L) {
                    OS.MoveMemory(l5, new long[]{0L}, C.PTR_SIZEOF);
                }
                if (l6 != 0L) {
                    OS.MoveMemory(l6, new int[]{0}, 4);
                }
                return -2147467263;
            }
        }
        if (l4 != 0L) {
            OS.MoveMemory(l4, new long[]{0L}, C.PTR_SIZEOF);
        }
        if (l5 != 0L) {
            OS.MoveMemory(l5, new long[]{0L}, C.PTR_SIZEOF);
        }
        if (l6 != 0L) {
            OS.MoveMemory(l6, new int[]{0}, 4);
        }
        return -2147352573;
    }

    private int OnControlInfoChanged() {
        long[] lArray = new long[]{0L};
        if (this.objIUnknown.QueryInterface(COM.IIDIOleControl, lArray) == 0) {
            IOleControl iOleControl = new IOleControl(lArray[0]);
            this.currentControlInfo = new CONTROLINFO();
            iOleControl.GetControlInfo(this.currentControlInfo);
            iOleControl.Release();
        }
        return 0;
    }

    @Override
    protected int OnUIDeactivate(int n) {
        return super.OnUIDeactivate(n);
    }

    @Override
    void onFocusIn(Event event) {
        String string = this.getProgramID();
        if (string == null) {
            return;
        }
        if (!string.startsWith(SHELL_PROG_ID)) {
            super.onFocusIn(event);
            return;
        }
        if (this.objIOleInPlaceObject == null) {
            return;
        }
        if (!this.isActivated) {
            this.doVerb(-4);
        }
        if (this.isFocusControl()) {
            return;
        }
        long[] lArray = new long[]{0L};
        this.objIOleInPlaceObject.GetWindow(lArray);
        if (lArray[0] == 0L) {
            return;
        }
        OS.SetFocus(lArray[0]);
    }

    @Override
    void onFocusOut(Event event) {
        if (this.objIOleInPlaceObject == null) {
            return;
        }
        String string = this.getProgramID();
        if (string == null) {
            return;
        }
        if (!string.startsWith(SHELL_PROG_ID)) {
            super.onFocusOut(event);
            return;
        }
        if (this.isFocusControl()) {
            return;
        }
        int n = OS.GetCurrentThreadId();
        GUITHREADINFO gUITHREADINFO = new GUITHREADINFO();
        gUITHREADINFO.cbSize = GUITHREADINFO.sizeof;
        OS.GetGUIThreadInfo(n, gUITHREADINFO);
        this.objIOleInPlaceObject.UIDeactivate();
        if (SWT_RESTORECARET == 0) {
            SWT_RESTORECARET = OS.RegisterWindowMessage(new TCHAR(0, "SWT_RESTORECARET", true));
        }
        if (gUITHREADINFO.hwndCaret != 0L) {
            GUITHREADINFO gUITHREADINFO2 = new GUITHREADINFO();
            gUITHREADINFO2.cbSize = GUITHREADINFO.sizeof;
            OS.GetGUIThreadInfo(n, gUITHREADINFO2);
            if (gUITHREADINFO2.hwndCaret == 0L && gUITHREADINFO.hwndCaret == OS.GetFocus() && OS.SendMessage(gUITHREADINFO.hwndCaret, SWT_RESTORECARET, 0L, 0L) == 0L) {
                int n2 = gUITHREADINFO.right - gUITHREADINFO.left;
                int n3 = gUITHREADINFO.bottom - gUITHREADINFO.top;
                OS.CreateCaret(gUITHREADINFO.hwndCaret, 0L, n2, n3);
                OS.SetCaretPos(gUITHREADINFO.left, gUITHREADINFO.top);
                OS.ShowCaret(gUITHREADINFO.hwndCaret);
            }
        } else if (gUITHREADINFO.hwndFocus != 0L && gUITHREADINFO.hwndFocus == OS.GetFocus()) {
            OS.SendMessage(gUITHREADINFO.hwndFocus, SWT_RESTORECARET, 0L, 0L);
        }
    }

    private int OnFocus(int n) {
        return 0;
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
        if (COM.IsEqualGUID(gUID, COM.IIDIOleControlSite)) {
            OS.MoveMemory(l3, new long[]{this.iOleControlSite.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIDispatch)) {
            OS.MoveMemory(l3, new long[]{this.iDispatch.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        OS.MoveMemory(l3, new long[]{0L}, C.PTR_SIZEOF);
        return -2147467262;
    }

    @Override
    protected int Release() {
        int n = super.Release();
        if (n == 0) {
            for (int i = 0; i < this.sitePropertyIds.length; ++i) {
                this.sitePropertyValues[i].dispose();
            }
            this.sitePropertyIds = new int[0];
            this.sitePropertyValues = new Variant[0];
        }
        return n;
    }

    @Override
    protected void releaseObjectInterfaces() {
        this.disconnectEventSinks();
        this.disconnectPropertyChangeSink();
        super.releaseObjectInterfaces();
    }

    public void removeEventListener(int n, OleListener oleListener) {
        GUID gUID;
        this.checkWidget();
        if (oleListener == null) {
            SWT.error(4);
        }
        if ((gUID = OleControlSite.getDefaultEventSinkGUID(this.objIUnknown)) != null) {
            this.removeEventListener(this.objIUnknown.getAddress(), gUID, n, oleListener);
        }
    }

    @Deprecated
    public void removeEventListener(OleAutomation oleAutomation, GUID gUID, int n, OleListener oleListener) {
        this.checkWidget();
        if (oleAutomation == null || oleListener == null || gUID == null) {
            SWT.error(4);
        }
        this.removeEventListener(oleAutomation.getAddress(), gUID, n, oleListener);
    }

    public void removeEventListener(OleAutomation oleAutomation, int n, OleListener oleListener) {
        long l2;
        IUnknown iUnknown;
        GUID gUID;
        this.checkWidget();
        if (oleAutomation == null || oleListener == null) {
            SWT.error(4);
        }
        if ((gUID = OleControlSite.getDefaultEventSinkGUID(iUnknown = new IUnknown(l2 = oleAutomation.getAddress()))) != null) {
            this.removeEventListener(l2, gUID, n, oleListener);
        }
    }

    void removeEventListener(long l2, GUID gUID, int n, OleListener oleListener) {
        if (oleListener == null || gUID == null) {
            SWT.error(4);
        }
        for (int i = 0; i < this.oleEventSink.length; ++i) {
            if (!COM.IsEqualGUID(this.oleEventSinkGUID[i], gUID) || l2 != this.oleEventSinkIUnknown[i]) continue;
            this.oleEventSink[i].removeListener(n, oleListener);
            if (!this.oleEventSink[i].hasListeners()) {
                this.oleEventSink[i].disconnect();
                this.oleEventSink[i].Release();
                int n2 = this.oleEventSink.length;
                if (n2 == 1) {
                    this.oleEventSink = new OleEventSink[0];
                    this.oleEventSinkGUID = new GUID[0];
                    this.oleEventSinkIUnknown = new long[0];
                } else {
                    OleEventSink[] oleEventSinkArray = new OleEventSink[n2 - 1];
                    System.arraycopy(this.oleEventSink, 0, oleEventSinkArray, 0, i);
                    System.arraycopy(this.oleEventSink, i + 1, oleEventSinkArray, i, n2 - i - 1);
                    this.oleEventSink = oleEventSinkArray;
                    GUID[] gUIDArray = new GUID[n2 - 1];
                    System.arraycopy(this.oleEventSinkGUID, 0, gUIDArray, 0, i);
                    System.arraycopy(this.oleEventSinkGUID, i + 1, gUIDArray, i, n2 - i - 1);
                    this.oleEventSinkGUID = gUIDArray;
                    long[] lArray = new long[n2 - 1];
                    System.arraycopy(this.oleEventSinkIUnknown, 0, lArray, 0, i);
                    System.arraycopy(this.oleEventSinkIUnknown, i + 1, lArray, i, n2 - i - 1);
                    this.oleEventSinkIUnknown = lArray;
                }
            }
            return;
        }
    }

    public void removePropertyListener(int n, OleListener oleListener) {
        if (oleListener == null) {
            SWT.error(4);
        }
        this.olePropertyChangeSink.removeListener(n, oleListener);
    }

    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        if (this.objIUnknown != null) {
            OleAutomation oleAutomation = new OleAutomation(this);
            oleAutomation.setProperty(-501, new Variant(color.handle));
            oleAutomation.dispose();
        }
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        if (this.objIUnknown != null) {
            OleAutomation oleAutomation = new OleAutomation(this);
            Variant variant = oleAutomation.getProperty(-512);
            oleAutomation.dispose();
            if (variant != null) {
                OleAutomation oleAutomation2 = variant.getAutomation();
                FontData[] fontDataArray = font.getFontData();
                oleAutomation2.setProperty(0, new Variant(fontDataArray[0].getName()));
                oleAutomation2.setProperty(2, new Variant(fontDataArray[0].getHeight()));
                oleAutomation2.setProperty(4, new Variant(fontDataArray[0].getStyle() & 2));
                oleAutomation2.setProperty(3, new Variant(fontDataArray[0].getStyle() & 1));
                oleAutomation2.dispose();
            }
        }
        this.font = font;
    }

    @Override
    public void setForeground(Color color) {
        super.setForeground(color);
        if (this.objIUnknown != null) {
            OleAutomation oleAutomation = new OleAutomation(this);
            oleAutomation.setProperty(-513, new Variant(color.handle));
            oleAutomation.dispose();
        }
    }

    public void setSiteProperty(int n, Variant variant) {
        int n2;
        for (n2 = 0; n2 < this.sitePropertyIds.length; ++n2) {
            if (this.sitePropertyIds[n2] != n) continue;
            if (this.sitePropertyValues[n2] != null) {
                this.sitePropertyValues[n2].dispose();
            }
            if (variant != null) {
                this.sitePropertyValues[n2] = variant;
            } else {
                int n3 = this.sitePropertyIds.length;
                int[] nArray = new int[n3 - 1];
                Variant[] variantArray = new Variant[n3 - 1];
                System.arraycopy(this.sitePropertyIds, 0, nArray, 0, n2);
                System.arraycopy(this.sitePropertyIds, n2 + 1, nArray, n2, n3 - n2 - 1);
                System.arraycopy(this.sitePropertyValues, 0, variantArray, 0, n2);
                System.arraycopy(this.sitePropertyValues, n2 + 1, variantArray, n2, n3 - n2 - 1);
                this.sitePropertyIds = nArray;
                this.sitePropertyValues = variantArray;
            }
            return;
        }
        n2 = this.sitePropertyIds.length;
        int[] nArray = new int[n2 + 1];
        Variant[] variantArray = new Variant[n2 + 1];
        System.arraycopy(this.sitePropertyIds, 0, nArray, 0, n2);
        System.arraycopy(this.sitePropertyValues, 0, variantArray, 0, n2);
        nArray[n2] = n;
        variantArray[n2] = variant;
        this.sitePropertyIds = nArray;
        this.sitePropertyValues = variantArray;
    }

    static int access$000(OleControlSite oleControlSite) {
        return oleControlSite.OnControlInfoChanged();
    }

    static int access$100(OleControlSite oleControlSite, int n) {
        return oleControlSite.OnFocus(n);
    }

    static int access$200(OleControlSite oleControlSite, int n, long l2, int n2, int n3, long l3, long l4, long l5, long l6) {
        return oleControlSite.Invoke(n, l2, n2, n3, l3, l4, l5, l6);
    }
}

