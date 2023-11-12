/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.ToIntFunction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.JSON;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.browser.VisibilityWindowListener;
import org.eclipse.swt.browser.WebBrowser;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.Library;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2Controller;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2Cookie;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2CookieList;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2CookieManager;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2DOMContentLoadedEventArgs;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2Deferral;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2Environment;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2Environment2;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2EnvironmentOptions;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2MoveFocusRequestedEventArgs;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2NavigationCompletedEventArgs;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2NavigationStartingEventArgs;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2NewWindowRequestedEventArgs;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2Settings;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2SwtCallback;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2SwtHost;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2WindowFeatures;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2_2;
import org.eclipse.swt.internal.ole.win32.IStream;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

class Edge
extends WebBrowser {
    static final String SDK_TARGET_VERSION = "89.0.721.0";
    static final String APPLOCAL_DIR_KEY = "org.eclipse.swt.internal.win32.appLocalDir";
    static final String BROWSER_DIR_PROP = "org.eclipse.swt.browser.EdgeDir";
    static final String BROWSER_ARGS_PROP = "org.eclipse.swt.browser.EdgeArgs";
    static final String DATA_DIR_PROP = "org.eclipse.swt.browser.EdgeDataDir";
    static final String LANGUAGE_PROP = "org.eclipse.swt.browser.EdgeLanguage";
    static final String VERSIONT_PROP = "org.eclipse.swt.browser.EdgeVersion";
    static String DataDir;
    static ICoreWebView2Environment Environment;
    static ArrayList Instances;
    ICoreWebView2 webView;
    ICoreWebView2_2 webView_2;
    ICoreWebView2Controller controller;
    ICoreWebView2Settings settings;
    ICoreWebView2Environment2 environment2;
    static boolean inCallback;
    boolean inNewWindow;
    HashMap navigations = new HashMap();

    Edge() {
    }

    static String wstrToString(long l2, boolean bl) {
        if (l2 == 0L) {
            return "";
        }
        int n = OS.wcslen(l2);
        char[] cArray = new char[n];
        OS.MoveMemory(cArray, l2, n * 2);
        if (bl) {
            OS.CoTaskMemFree(l2);
        }
        return String.valueOf(cArray);
    }

    static String bstrToString(long l2) {
        if (l2 == 0L) {
            return "";
        }
        int n = COM.SysStringLen(l2);
        char[] cArray = new char[n];
        OS.MoveMemory(cArray, l2, n * 2);
        return String.valueOf(cArray);
    }

    static char[] stringToWstr(String string) {
        return string != null ? string.toCharArray() : null;
    }

    static void error(int n, int n2) {
        SWT.error(n, null, String.format(" [0x%08x]", n2));
    }

    static IUnknown newCallback(ICoreWebView2SwtCallback iCoreWebView2SwtCallback) {
        long l2 = COM.CreateSwtWebView2Callback((arg_0, arg_1) -> Edge.lambda$newCallback$0(iCoreWebView2SwtCallback, arg_0, arg_1));
        if (l2 == 0L) {
            Edge.error(2, -2147024882);
        }
        return new IUnknown(l2);
    }

    IUnknown newHostObject(ICoreWebView2SwtHost iCoreWebView2SwtHost) {
        long l2 = COM.CreateSwtWebView2Host(iCoreWebView2SwtHost);
        if (l2 == 0L) {
            Edge.error(2, -2147024882);
        }
        return new IUnknown(l2);
    }

    static int callAndWait(long[] lArray, ToIntFunction toIntFunction) {
        int[] nArray = new int[]{0};
        IUnknown iUnknown = Edge.newCallback((arg_0, arg_1) -> Edge.lambda$callAndWait$1(nArray, lArray, arg_0, arg_1));
        lArray[0] = 0L;
        nArray[0] = toIntFunction.applyAsInt(iUnknown);
        iUnknown.Release();
        Display display = Display.getCurrent();
        while (nArray[0] == 0 && lArray[0] == 0L) {
            if (display.readAndDispatch()) continue;
            display.sleep();
        }
        return nArray[0];
    }

    static int callAndWait(String[] stringArray, ToIntFunction toIntFunction) {
        int[] nArray = new int[]{0};
        IUnknown iUnknown = Edge.newCallback((arg_0, arg_1) -> Edge.lambda$callAndWait$2(nArray, stringArray, arg_0, arg_1));
        stringArray[0] = null;
        nArray[0] = toIntFunction.applyAsInt(iUnknown);
        iUnknown.Release();
        Display display = Display.getCurrent();
        while (nArray[0] == 0 && stringArray[0] == null) {
            if (display.readAndDispatch()) continue;
            display.sleep();
        }
        return nArray[0];
    }

    static ICoreWebView2CookieManager getCookieManager() {
        long[] lArray;
        int n;
        if (Instances.isEmpty()) {
            SWT.error(20, null, " [WebView2: cookie access requires a Browser instance]");
        }
        Edge edge = (Edge)Instances.get(0);
        if (edge.webView_2 == null) {
            SWT.error(20, null, " [WebView2 version 88+ is required to access cookies]");
        }
        if ((n = edge.webView_2.get_CookieManager(lArray = new long[]{0L})) != 0) {
            Edge.error(2, n);
        }
        return new ICoreWebView2CookieManager(lArray[0]);
    }

    void checkDeadlock() {
        if (inCallback || this.inNewWindow) {
            SWT.error(50, null, " [WebView2: deadlock detected]");
        }
    }

    ICoreWebView2Environment createEnvironment() {
        char[] cArray;
        long l2;
        if (Environment != null) {
            return Environment;
        }
        Display display = Display.getCurrent();
        String string = System.getProperty(BROWSER_DIR_PROP);
        String string2 = System.getProperty(DATA_DIR_PROP);
        String string3 = System.getProperty(BROWSER_ARGS_PROP);
        String string4 = System.getProperty(LANGUAGE_PROP);
        if (string2 == null) {
            string2 = (String)display.getData(APPLOCAL_DIR_KEY);
        }
        if ((l2 = COM.CreateSwtWebView2Options()) == 0L) {
            Edge.error(2, -2147024882);
        }
        ICoreWebView2EnvironmentOptions iCoreWebView2EnvironmentOptions = new ICoreWebView2EnvironmentOptions(l2);
        char[] cArray2 = Edge.stringToWstr(SDK_TARGET_VERSION);
        iCoreWebView2EnvironmentOptions.put_TargetCompatibleBrowserVersion(cArray2);
        if (string3 != null) {
            cArray = Edge.stringToWstr(string3);
            iCoreWebView2EnvironmentOptions.put_AdditionalBrowserArguments(cArray);
        }
        if (string4 != null) {
            cArray = Edge.stringToWstr(string4);
            iCoreWebView2EnvironmentOptions.put_Language(cArray);
        }
        cArray = Edge.stringToWstr(string);
        char[] cArray3 = Edge.stringToWstr(string2);
        long[] lArray = new long[]{0L};
        int n = Edge.callAndWait(lArray, arg_0 -> Edge.lambda$createEnvironment$3(cArray, cArray3, iCoreWebView2EnvironmentOptions, arg_0));
        iCoreWebView2EnvironmentOptions.Release();
        if (n == OS.HRESULT_FROM_WIN32(2)) {
            SWT.error(20, null, " [WebView2 runtime not found]");
        }
        if (n != 0) {
            Edge.error(2, n);
        }
        Environment = new ICoreWebView2Environment(lArray[0]);
        DataDir = string2;
        long[] lArray2 = new long[]{0L};
        Environment.get_BrowserVersionString(lArray2);
        String string5 = Edge.wstrToString(lArray2[0], true);
        System.setProperty(VERSIONT_PROP, string5);
        display.disposeExec(Edge::lambda$createEnvironment$4);
        return Environment;
    }

    @Override
    public void create(Composite composite, int n) {
        this.checkDeadlock();
        ICoreWebView2Environment iCoreWebView2Environment = this.createEnvironment();
        long[] lArray = new long[]{0L};
        int n2 = iCoreWebView2Environment.QueryInterface(COM.IID_ICoreWebView2Environment2, lArray);
        if (n2 == 0) {
            this.environment2 = new ICoreWebView2Environment2(lArray[0]);
        }
        if ((n2 = Edge.callAndWait(lArray, arg_0 -> this.lambda$create$5(iCoreWebView2Environment, arg_0))) != 0) {
            Edge.error(2, n2);
        }
        this.controller = new ICoreWebView2Controller(lArray[0]);
        this.controller.get_CoreWebView2(lArray);
        this.webView = new ICoreWebView2(lArray[0]);
        this.webView.get_Settings(lArray);
        this.settings = new ICoreWebView2Settings(lArray[0]);
        n2 = this.webView.QueryInterface(COM.IID_ICoreWebView2_2, lArray);
        if (n2 == 0) {
            this.webView_2 = new ICoreWebView2_2(lArray[0]);
        }
        long[] lArray2 = new long[]{0L};
        IUnknown iUnknown = Edge.newCallback(this::handleCloseRequested);
        this.webView.add_WindowCloseRequested(iUnknown, lArray2);
        iUnknown.Release();
        iUnknown = Edge.newCallback(this::handleNavigationStarting);
        this.webView.add_NavigationStarting(iUnknown, lArray2);
        iUnknown.Release();
        iUnknown = Edge.newCallback(this::handleFrameNavigationStarting);
        this.webView.add_FrameNavigationStarting(iUnknown, lArray2);
        iUnknown.Release();
        iUnknown = Edge.newCallback(this::handleNavigationCompleted);
        this.webView.add_NavigationCompleted(iUnknown, lArray2);
        iUnknown.Release();
        iUnknown = Edge.newCallback(this::handleFrameNavigationCompleted);
        this.webView.add_FrameNavigationCompleted(iUnknown, lArray2);
        iUnknown.Release();
        iUnknown = Edge.newCallback(this::handleDocumentTitleChanged);
        this.webView.add_DocumentTitleChanged(iUnknown, lArray2);
        iUnknown.Release();
        iUnknown = Edge.newCallback(this::handleNewWindowRequested);
        this.webView.add_NewWindowRequested(iUnknown, lArray2);
        iUnknown.Release();
        iUnknown = Edge.newCallback(this::handleSourceChanged);
        this.webView.add_SourceChanged(iUnknown, lArray2);
        iUnknown.Release();
        iUnknown = Edge.newCallback(this::handleMoveFocusRequested);
        this.controller.add_MoveFocusRequested(iUnknown, lArray2);
        iUnknown.Release();
        if (this.webView_2 != null) {
            iUnknown = Edge.newCallback(this::handleDOMContentLoaded);
            this.webView_2.add_DOMContentLoaded(iUnknown, lArray2);
            iUnknown.Release();
        }
        IUnknown iUnknown2 = this.newHostObject(this::handleCallJava);
        long[] lArray3 = new long[]{9L, iUnknown2.getAddress(), 0L};
        this.webView.AddHostObjectToScript("swt\u0000".toCharArray(), lArray3);
        iUnknown2.Release();
        this.browser.addListener(12, this::browserDispose);
        this.browser.addListener(15, this::browserFocusIn);
        this.browser.addListener(11, this::browserResize);
        this.browser.addListener(10, this::browserMove);
        Instances.add(this);
    }

    void browserDispose(Event event) {
        Instances.remove(this);
        if (this.webView_2 != null) {
            this.webView_2.Release();
        }
        if (this.environment2 != null) {
            this.environment2.Release();
        }
        this.settings.Release();
        this.webView.Release();
        this.webView_2 = null;
        this.environment2 = null;
        this.settings = null;
        this.webView = null;
        if (inCallback) {
            ICoreWebView2Controller iCoreWebView2Controller = this.controller;
            this.controller.put_IsVisible(false);
            this.browser.getDisplay().asyncExec(() -> Edge.lambda$browserDispose$6(iCoreWebView2Controller));
        } else {
            this.controller.Close();
            this.controller.Release();
        }
        this.controller = null;
    }

    void browserFocusIn(Event event) {
        this.controller.MoveFocus(0);
    }

    void browserMove(Event event) {
        this.controller.NotifyParentWindowPositionChanged();
    }

    void browserResize(Event event) {
        RECT rECT = new RECT();
        OS.GetClientRect(this.browser.handle, rECT);
        this.controller.put_Bounds(rECT);
        this.controller.put_IsVisible(true);
    }

    @Override
    public Object evaluate(String string) throws SWTException {
        Object object;
        this.checkDeadlock();
        if (!this.jsEnabled) {
            return null;
        }
        String[] stringArray = new String[]{null};
        String string2 = "(function() {try { " + string + " } catch (e) { return 'org.eclipse.swt.browser.error' + e.message; } })();\u0000";
        int n = Edge.callAndWait(stringArray, arg_0 -> this.lambda$evaluate$7(string2, arg_0));
        if (n != 0) {
            Edge.error(50, n);
        }
        if ((object = JSON.parse(stringArray[0])) instanceof String && ((String)object).startsWith("org.eclipse.swt.browser.error")) {
            String string3 = ((String)object).substring(29);
            throw new SWTException(50, string3);
        }
        return object;
    }

    @Override
    public boolean execute(String string) {
        if (!this.jsEnabled) {
            return false;
        }
        IUnknown iUnknown = Edge.newCallback(Edge::lambda$execute$8);
        int n = this.webView.ExecuteScript(Edge.stringToWstr(string), iUnknown);
        iUnknown.Release();
        return n == 0;
    }

    @Override
    public String getBrowserType() {
        return "edge";
    }

    @Override
    String getJavaCallDeclaration() {
        return "if (!window.callJava) { window.callJava = function(index, token, args) {\nreturn JSON.parse(window.chrome.webview.hostObjects.sync.swt.CallJava(index, token, JSON.stringify(args)));\n}};\n";
    }

    @Override
    public String getText() {
        return (String)this.evaluate("return document.documentElement.outerHTML;");
    }

    @Override
    public String getUrl() {
        long[] lArray = new long[]{0L};
        this.webView.get_Source(lArray);
        return Edge.wstrToString(lArray[0], true);
    }

    int handleCloseRequested(long l2, long l3) {
        this.browser.getDisplay().asyncExec(this::lambda$handleCloseRequested$9);
        return 0;
    }

    int handleDocumentTitleChanged(long l2, long l3) {
        long[] lArray = new long[]{0L};
        this.webView.get_DocumentTitle(lArray);
        String string = Edge.wstrToString(lArray[0], true);
        this.browser.getDisplay().asyncExec(() -> this.lambda$handleDocumentTitleChanged$10(string));
        return 0;
    }

    long handleCallJava(int n, long l2, long l3) {
        String string;
        Object object = null;
        String string2 = Edge.bstrToString(l2);
        BrowserFunction browserFunction = (BrowserFunction)this.functions.get(n);
        if (browserFunction != null && string2.equals(browserFunction.token)) {
            try {
                string = Edge.bstrToString(l3);
                Object object2 = JSON.parse(string.toCharArray());
                object = browserFunction.function((Object[])object2);
            }
            catch (Throwable throwable) {
                object = WebBrowser.CreateErrorString(throwable.getLocalizedMessage());
            }
        }
        string = JSON.stringify(object);
        return COM.SysAllocStringLen(string.toCharArray(), string.length());
    }

    int handleFrameNavigationStarting(long l2, long l3) {
        return this.handleNavigationStarting(l2, l3, false);
    }

    int handleNavigationStarting(long l2, long l3) {
        return this.handleNavigationStarting(l2, l3, true);
    }

    int handleNavigationStarting(long l2, long l3, boolean bl) {
        ICoreWebView2NavigationStartingEventArgs iCoreWebView2NavigationStartingEventArgs = new ICoreWebView2NavigationStartingEventArgs(l3);
        long[] lArray = new long[]{0L};
        int n = iCoreWebView2NavigationStartingEventArgs.get_Uri(lArray);
        if (n != 0) {
            return n;
        }
        String string = Edge.wstrToString(lArray[0], true);
        long[] lArray2 = new long[]{0L};
        iCoreWebView2NavigationStartingEventArgs.get_NavigationId(lArray2);
        LocationEvent locationEvent = new LocationEvent(this.browser);
        locationEvent.display = this.browser.getDisplay();
        locationEvent.widget = this.browser;
        locationEvent.location = string;
        locationEvent.top = bl;
        locationEvent.doit = true;
        for (LocationListener locationListener : this.locationListeners) {
            locationListener.changing(locationEvent);
            if (!this.browser.isDisposed()) continue;
            return 0;
        }
        if (locationEvent.doit) {
            this.navigations.put(lArray2[0], locationEvent);
            this.jsEnabled = this.jsEnabledOnNextPage;
            this.settings.put_IsScriptEnabled(this.jsEnabled);
            if (!this.functions.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (BrowserFunction browserFunction : this.functions.values()) {
                    stringBuilder.append(browserFunction.functionString);
                }
                this.execute(stringBuilder.toString());
            }
        } else {
            iCoreWebView2NavigationStartingEventArgs.put_Cancel(true);
        }
        return 0;
    }

    int handleSourceChanged(long l2, long l3) {
        long[] lArray = new long[]{0L};
        int n = this.webView.get_Source(lArray);
        if (n != 0) {
            return n;
        }
        String string = Edge.wstrToString(lArray[0], true);
        this.browser.getDisplay().asyncExec(() -> this.lambda$handleSourceChanged$11(string));
        return 0;
    }

    void sendProgressCompleted() {
        this.browser.getDisplay().asyncExec(this::lambda$sendProgressCompleted$12);
    }

    int handleDOMContentLoaded(long l2, long l3) {
        ICoreWebView2DOMContentLoadedEventArgs iCoreWebView2DOMContentLoadedEventArgs = new ICoreWebView2DOMContentLoadedEventArgs(l3);
        long[] lArray = new long[]{0L};
        iCoreWebView2DOMContentLoadedEventArgs.get_NavigationId(lArray);
        LocationEvent locationEvent = (LocationEvent)this.navigations.get(lArray[0]);
        if (locationEvent != null && locationEvent.top) {
            this.sendProgressCompleted();
        }
        return 0;
    }

    int handleNavigationCompleted(long l2, long l3) {
        return this.handleNavigationCompleted(l2, l3, true);
    }

    int handleFrameNavigationCompleted(long l2, long l3) {
        return this.handleNavigationCompleted(l2, l3, false);
    }

    int handleNavigationCompleted(long l2, long l3, boolean bl) {
        ICoreWebView2NavigationCompletedEventArgs iCoreWebView2NavigationCompletedEventArgs = new ICoreWebView2NavigationCompletedEventArgs(l3);
        long[] lArray = new long[]{0L};
        iCoreWebView2NavigationCompletedEventArgs.get_NavigationId(lArray);
        LocationEvent locationEvent = (LocationEvent)this.navigations.remove(lArray[0]);
        if (this.webView_2 == null && locationEvent != null && locationEvent.top) {
            this.sendProgressCompleted();
        }
        return 0;
    }

    void updateWindowFeatures(ICoreWebView2NewWindowRequestedEventArgs iCoreWebView2NewWindowRequestedEventArgs, WindowEvent windowEvent) {
        long[] lArray = new long[]{0L};
        int n = iCoreWebView2NewWindowRequestedEventArgs.get_WindowFeatures(lArray);
        if (n != 0) {
            return;
        }
        ICoreWebView2WindowFeatures iCoreWebView2WindowFeatures = new ICoreWebView2WindowFeatures(lArray[0]);
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        iCoreWebView2WindowFeatures.get_HasPosition(nArray);
        if (nArray[0] != 0) {
            iCoreWebView2WindowFeatures.get_Left(nArray);
            iCoreWebView2WindowFeatures.get_Top(nArray2);
            windowEvent.location = new Point(nArray[0], nArray2[0]);
        }
        iCoreWebView2WindowFeatures.get_HasSize(nArray);
        if (nArray[0] != 0) {
            iCoreWebView2WindowFeatures.get_Width(nArray);
            iCoreWebView2WindowFeatures.get_Height(nArray2);
            windowEvent.size = new Point(nArray[0], nArray2[0]);
        }
        iCoreWebView2WindowFeatures.get_ShouldDisplayMenuBar(nArray);
        windowEvent.menuBar = nArray[0] != 0;
        iCoreWebView2WindowFeatures.get_ShouldDisplayStatus(nArray);
        windowEvent.statusBar = nArray[0] != 0;
        iCoreWebView2WindowFeatures.get_ShouldDisplayToolbar(nArray);
        windowEvent.toolBar = nArray[0] != 0;
    }

    int handleNewWindowRequested(long l2, long l3) {
        ICoreWebView2NewWindowRequestedEventArgs iCoreWebView2NewWindowRequestedEventArgs = new ICoreWebView2NewWindowRequestedEventArgs(l3);
        iCoreWebView2NewWindowRequestedEventArgs.AddRef();
        long[] lArray = new long[]{0L};
        iCoreWebView2NewWindowRequestedEventArgs.GetDeferral(lArray);
        ICoreWebView2Deferral iCoreWebView2Deferral = new ICoreWebView2Deferral(lArray[0]);
        this.inNewWindow = true;
        this.browser.getDisplay().asyncExec(() -> this.lambda$handleNewWindowRequested$13(iCoreWebView2NewWindowRequestedEventArgs, iCoreWebView2Deferral));
        return 0;
    }

    int handleMoveFocusRequested(long l2, long l3) {
        ICoreWebView2MoveFocusRequestedEventArgs iCoreWebView2MoveFocusRequestedEventArgs = new ICoreWebView2MoveFocusRequestedEventArgs(l3);
        int[] nArray = new int[]{0};
        iCoreWebView2MoveFocusRequestedEventArgs.get_Reason(nArray);
        iCoreWebView2MoveFocusRequestedEventArgs.put_Handled(true);
        switch (nArray[0]) {
            case 1: {
                this.browser.traverse(16);
                break;
            }
            case 2: {
                this.browser.traverse(8);
            }
        }
        return 0;
    }

    @Override
    public boolean back() {
        return this != false && this.webView.GoBack() == 0;
    }

    @Override
    public boolean forward() {
        return this != false && this.webView.GoForward() == 0;
    }

    @Override
    public void refresh() {
        this.webView.Reload();
    }

    @Override
    public void stop() {
        this.webView.Stop();
    }

    @Override
    public boolean setText(String string, boolean bl) {
        char[] cArray = new char[string.length() + 1];
        string.getChars(0, string.length(), cArray, 0);
        return this.webView.NavigateToString(cArray) == 0;
    }

    @Override
    public boolean setUrl(String string, String string2, String[] stringArray) {
        int n;
        if (!string.matches("[a-z][a-z0-9+.-]*:.*")) {
            string = "http://" + string;
        }
        char[] cArray = Edge.stringToWstr(string);
        if (string2 != null || stringArray != null) {
            Object object;
            if (this.environment2 == null || this.webView_2 == null) {
                SWT.error(20, null, " [WebView2 version 88+ is required to set postData and headers]");
            }
            long[] lArray = new long[]{0L};
            char[] cArray2 = null;
            char[] cArray3 = null;
            IUnknown iUnknown = null;
            if (string2 != null) {
                cArray2 = "POST\u0000".toCharArray();
                object = string2.getBytes(StandardCharsets.UTF_8);
                long l2 = COM.SHCreateMemStream(object, string2.length());
                if (l2 == 0L) {
                    Edge.error(2, -2147024882);
                }
                iUnknown = new IStream(l2);
            } else {
                cArray2 = "GET\u0000".toCharArray();
            }
            if (stringArray != null) {
                object = String.join((CharSequence)"\r\n", Arrays.asList(stringArray));
                cArray3 = new char[object.length() + 1];
                object.getChars(0, object.length(), cArray3, 0);
            }
            n = this.environment2.CreateWebResourceRequest(cArray, cArray2, (IStream)iUnknown, cArray3, lArray);
            if (iUnknown != null) {
                iUnknown.Release();
            }
            if (n != 0) {
                Edge.error(2, n);
            }
            object = new IUnknown(lArray[0]);
            n = this.webView_2.NavigateWithWebResourceRequest((IUnknown)object);
            object.Release();
        } else {
            n = this.webView.Navigate(cArray);
        }
        return n == 0;
    }

    private static void lambda$static$18() {
        ICoreWebView2CookieManager iCoreWebView2CookieManager;
        URL uRL;
        HttpCookie httpCookie = HttpCookie.parse(CookieValue).get(0);
        try {
            uRL = new URL(CookieUrl);
        }
        catch (MalformedURLException malformedURLException) {
            return;
        }
        if (httpCookie.getDomain() == null) {
            httpCookie.setDomain(uRL.getHost());
        }
        if (httpCookie.getPath() == null) {
            httpCookie.setPath(uRL.getPath());
        }
        if ((iCoreWebView2CookieManager = Edge.getCookieManager()) != null) {
            long[] lArray;
            char[] cArray;
            char[] cArray2;
            char[] cArray3;
            char[] cArray4 = Edge.stringToWstr(httpCookie.getName());
            int n = iCoreWebView2CookieManager.CreateCookie(cArray4, cArray3 = Edge.stringToWstr(httpCookie.getValue()), cArray2 = Edge.stringToWstr(httpCookie.getDomain()), cArray = Edge.stringToWstr(httpCookie.getPath()), lArray = new long[]{0L});
            if (n != 0) {
                iCoreWebView2CookieManager.Release();
            } else {
                ICoreWebView2Cookie iCoreWebView2Cookie = new ICoreWebView2Cookie(lArray[0]);
                if (httpCookie.getMaxAge() != -1L) {
                    iCoreWebView2Cookie.put_Expires(Instant.now().getEpochSecond() + httpCookie.getMaxAge());
                }
                iCoreWebView2Cookie.put_IsSecure(httpCookie.getSecure());
                iCoreWebView2Cookie.put_IsHttpOnly(httpCookie.isHttpOnly());
                int n2 = iCoreWebView2CookieManager.AddOrUpdateCookie(iCoreWebView2Cookie);
                iCoreWebView2Cookie.Release();
                iCoreWebView2CookieManager.Release();
                CookieResult = n2 >= 0;
            }
        }
    }

    private static void lambda$static$17() {
        ICoreWebView2CookieManager iCoreWebView2CookieManager = Edge.getCookieManager();
        if (iCoreWebView2CookieManager == null) {
            return;
        }
        char[] cArray = Edge.stringToWstr(CookieUrl);
        long[] lArray = new long[]{0L};
        int n = Edge.callAndWait(lArray, arg_0 -> Edge.lambda$null$16(iCoreWebView2CookieManager, cArray, arg_0));
        if (n != 0) {
            Edge.error(2, n);
        }
        ICoreWebView2CookieList iCoreWebView2CookieList = new ICoreWebView2CookieList(lArray[0]);
        int[] nArray = new int[]{0};
        iCoreWebView2CookieList.get_Count(nArray);
        for (int i = 0; i < nArray[0]; ++i) {
            int n2 = iCoreWebView2CookieList.GetValueAtIndex(i, lArray);
            if (n2 != 0) {
                Edge.error(2, n2);
            }
            ICoreWebView2Cookie iCoreWebView2Cookie = new ICoreWebView2Cookie(lArray[0]);
            iCoreWebView2Cookie.get_Name(lArray);
            String string = Edge.wstrToString(lArray[0], true);
            if (CookieName.equals(string)) {
                iCoreWebView2Cookie.get_Value(lArray);
                CookieValue = Edge.wstrToString(lArray[0], true);
            }
            iCoreWebView2Cookie.Release();
            if (CookieValue != null) break;
        }
        iCoreWebView2CookieList.Release();
        iCoreWebView2CookieManager.Release();
    }

    private static int lambda$null$16(ICoreWebView2CookieManager iCoreWebView2CookieManager, char[] cArray, IUnknown iUnknown) {
        return iCoreWebView2CookieManager.GetCookies(cArray, iUnknown);
    }

    private static void lambda$static$15() {
        ICoreWebView2CookieManager iCoreWebView2CookieManager = Edge.getCookieManager();
        if (iCoreWebView2CookieManager == null) {
            return;
        }
        long[] lArray = new long[]{0L};
        int n = Edge.callAndWait(lArray, arg_0 -> Edge.lambda$null$14(iCoreWebView2CookieManager, arg_0));
        if (n != 0) {
            Edge.error(2, n);
        }
        ICoreWebView2CookieList iCoreWebView2CookieList = new ICoreWebView2CookieList(lArray[0]);
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        iCoreWebView2CookieList.get_Count(nArray);
        for (int i = 0; i < nArray[0]; ++i) {
            int n2 = iCoreWebView2CookieList.GetValueAtIndex(i, lArray);
            if (n2 != 0) {
                Edge.error(2, n2);
            }
            ICoreWebView2Cookie iCoreWebView2Cookie = new ICoreWebView2Cookie(lArray[0]);
            iCoreWebView2Cookie.get_IsSession(nArray2);
            if (nArray2[0] != 0) {
                iCoreWebView2CookieManager.DeleteCookie(iCoreWebView2Cookie);
            }
            iCoreWebView2Cookie.Release();
        }
        iCoreWebView2CookieList.Release();
        iCoreWebView2CookieManager.Release();
        try {
            Thread.sleep(5L);
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    private static int lambda$null$14(ICoreWebView2CookieManager iCoreWebView2CookieManager, IUnknown iUnknown) {
        return iCoreWebView2CookieManager.GetCookies(null, iUnknown);
    }

    private void lambda$handleNewWindowRequested$13(ICoreWebView2NewWindowRequestedEventArgs iCoreWebView2NewWindowRequestedEventArgs, ICoreWebView2Deferral iCoreWebView2Deferral) {
        if (!this.browser.isDisposed()) {
            WindowEvent windowEvent = new WindowEvent(this.browser);
            windowEvent.display = this.browser.getDisplay();
            windowEvent.widget = this.browser;
            windowEvent.required = false;
            for (OpenWindowListener object : this.openWindowListeners) {
                object.open(windowEvent);
                if (!this.browser.isDisposed()) continue;
                iCoreWebView2Deferral.Complete();
                iCoreWebView2Deferral.Release();
                iCoreWebView2NewWindowRequestedEventArgs.Release();
                this.inNewWindow = false;
                return;
            }
            if (windowEvent.browser != null && !windowEvent.browser.isDisposed()) {
                WebBrowser webBrowser = windowEvent.browser.webBrowser;
                iCoreWebView2NewWindowRequestedEventArgs.put_Handled(true);
                if (webBrowser instanceof Edge) {
                    iCoreWebView2NewWindowRequestedEventArgs.put_NewWindow(((Edge)webBrowser).webView.getAddress());
                    WindowEvent windowEvent2 = new WindowEvent(webBrowser.browser);
                    windowEvent2.display = this.browser.getDisplay();
                    windowEvent2.widget = webBrowser.browser;
                    this.updateWindowFeatures(iCoreWebView2NewWindowRequestedEventArgs, windowEvent2);
                    for (VisibilityWindowListener visibilityWindowListener : webBrowser.visibilityWindowListeners) {
                        visibilityWindowListener.show(windowEvent2);
                        if (!webBrowser.browser.isDisposed()) continue;
                    }
                }
            } else if (windowEvent.required) {
                iCoreWebView2NewWindowRequestedEventArgs.put_Handled(true);
            }
        }
        iCoreWebView2Deferral.Complete();
        iCoreWebView2Deferral.Release();
        iCoreWebView2NewWindowRequestedEventArgs.Release();
        this.inNewWindow = false;
    }

    private void lambda$sendProgressCompleted$12() {
        if (!this.browser.isDisposed()) {
            ProgressEvent progressEvent = new ProgressEvent(this.browser);
            progressEvent.display = this.browser.getDisplay();
            progressEvent.widget = this.browser;
            for (ProgressListener progressListener : this.progressListeners) {
                progressListener.completed(progressEvent);
                if (!this.browser.isDisposed()) continue;
            }
        }
    }

    private void lambda$handleSourceChanged$11(String string) {
        if (this.browser.isDisposed()) {
            return;
        }
        LocationEvent locationEvent = new LocationEvent(this.browser);
        locationEvent.display = this.browser.getDisplay();
        locationEvent.widget = this.browser;
        locationEvent.location = string;
        locationEvent.top = true;
        for (LocationListener locationListener : this.locationListeners) {
            locationListener.changed(locationEvent);
            if (!this.browser.isDisposed()) continue;
        }
    }

    private void lambda$handleDocumentTitleChanged$10(String string) {
        if (this.browser.isDisposed()) {
            return;
        }
        TitleEvent titleEvent = new TitleEvent(this.browser);
        titleEvent.display = this.browser.getDisplay();
        titleEvent.widget = this.browser;
        titleEvent.title = string;
        for (TitleListener titleListener : this.titleListeners) {
            titleListener.changed(titleEvent);
            if (!this.browser.isDisposed()) continue;
        }
    }

    private void lambda$handleCloseRequested$9() {
        if (this.browser.isDisposed()) {
            return;
        }
        WindowEvent windowEvent = new WindowEvent(this.browser);
        windowEvent.display = this.browser.getDisplay();
        windowEvent.widget = this.browser;
        for (CloseWindowListener closeWindowListener : this.closeWindowListeners) {
            closeWindowListener.close(windowEvent);
            if (!this.browser.isDisposed()) continue;
            return;
        }
        this.browser.dispose();
    }

    private static int lambda$execute$8(long l2, long l3) {
        return 0;
    }

    private int lambda$evaluate$7(String string, IUnknown iUnknown) {
        return this.webView.ExecuteScript(string.toCharArray(), iUnknown);
    }

    private static void lambda$browserDispose$6(ICoreWebView2Controller iCoreWebView2Controller) {
        iCoreWebView2Controller.Close();
        iCoreWebView2Controller.Release();
    }

    private int lambda$create$5(ICoreWebView2Environment iCoreWebView2Environment, IUnknown iUnknown) {
        return iCoreWebView2Environment.CreateCoreWebView2Controller(this.browser.handle, iUnknown);
    }

    private static void lambda$createEnvironment$4() {
        Environment.Release();
        Environment = null;
    }

    private static int lambda$createEnvironment$3(char[] cArray, char[] cArray2, ICoreWebView2EnvironmentOptions iCoreWebView2EnvironmentOptions, IUnknown iUnknown) {
        return COM.CreateCoreWebView2EnvironmentWithOptions(cArray, cArray2, iCoreWebView2EnvironmentOptions.getAddress(), iUnknown.getAddress());
    }

    private static int lambda$callAndWait$2(int[] nArray, String[] stringArray, long l2, long l3) {
        nArray[0] = (int)l2;
        if ((int)l2 == 0) {
            stringArray[0] = Edge.wstrToString(l3, false);
        }
        return 0;
    }

    private static int lambda$callAndWait$1(int[] nArray, long[] lArray, long l2, long l3) {
        nArray[0] = (int)l2;
        if ((int)l2 == 0) {
            lArray[0] = l3;
            new IUnknown(l3).AddRef();
        }
        return 0;
    }

    private static int lambda$newCallback$0(ICoreWebView2SwtCallback iCoreWebView2SwtCallback, long l2, long l3) {
        inCallback = true;
        int n = iCoreWebView2SwtCallback.Invoke(l2, l3);
        inCallback = false;
        return n;
    }

    static {
        Library.loadLibrary("WebView2Loader");
        Instances = new ArrayList();
        NativeClearSessions = Edge::lambda$static$15;
        NativeGetCookie = Edge::lambda$static$17;
        NativeSetCookie = Edge::lambda$static$18;
    }
}

