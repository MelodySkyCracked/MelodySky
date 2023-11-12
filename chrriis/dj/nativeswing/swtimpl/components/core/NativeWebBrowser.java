/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.core;

import chrriis.dj.nativeswing.common.ObjectRegistry;
import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.swtimpl.CommandMessage;
import chrriis.dj.nativeswing.swtimpl.NSSystemPropertySWT;
import chrriis.dj.nativeswing.swtimpl.components.Credentials;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowserWindow;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAuthenticationHandler;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserFunction;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserListener;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationParameters;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowFactory;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowOpeningEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;
import chrriis.dj.nativeswing.swtimpl.components.core.I;
import chrriis.dj.nativeswing.swtimpl.components.core.NativeJSBrowserDetection;
import chrriis.dj.nativeswing.swtimpl.components.core.l;
import chrriis.dj.nativeswing.swtimpl.components.core.lI;
import chrriis.dj.nativeswing.swtimpl.components.core.lII;
import chrriis.dj.nativeswing.swtimpl.components.core.lIIIl;
import chrriis.dj.nativeswing.swtimpl.components.core.lIIl;
import chrriis.dj.nativeswing.swtimpl.components.core.lIl;
import chrriis.dj.nativeswing.swtimpl.components.core.lIlI;
import chrriis.dj.nativeswing.swtimpl.components.core.lIlIl;
import chrriis.dj.nativeswing.swtimpl.components.core.lIll;
import chrriis.dj.nativeswing.swtimpl.components.core.lIllI;
import chrriis.dj.nativeswing.swtimpl.components.core.llI;
import chrriis.dj.nativeswing.swtimpl.components.core.llIl;
import chrriis.dj.nativeswing.swtimpl.components.core.lll;
import chrriis.dj.nativeswing.swtimpl.components.core.lllI;
import chrriis.dj.nativeswing.swtimpl.components.internal.INativeWebBrowser;
import chrriis.dj.nativeswing.swtimpl.core.ControlCommandMessage;
import chrriis.dj.nativeswing.swtimpl.core.SWTNativeComponent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
import org.eclipse.swt.browser.AuthenticationListener;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

class NativeWebBrowser
extends SWTNativeComponent
implements INativeWebBrowser {
    private INativeWebBrowser.WebBrowserRuntime runtime;
    private String xulRunnerHome;
    private Reference webBrowser;
    private static Pattern JAVASCRIPT_LINE_COMMENT_PATTERN = Pattern.compile("^\\s*//.*$", 8);
    private static volatile Boolean isFixedJS;
    private String status;
    private String title;
    private int loadingProgress = 100;
    private Map nameToFunctionMap;
    private WebBrowserAuthenticationHandler authenticationHandler;
    private Component embeddableComponent;

    @Override
    public INativeWebBrowser.WebBrowserRuntime getRuntime() {
        return this.runtime;
    }

    @Override
    protected Object[] getNativePeerCreationParameters() {
        return new Object[]{this.runtime, this.xulRunnerHome};
    }

    protected static Control createControl(Composite composite, Object[] objectArray) {
        String string = (String)objectArray[1];
        if (string != null) {
            NSSystemPropertySWT.ORG_ECLIPSE_SWT_BROWSER_XULRUNNERPATH.set(string);
        } else {
            string = NSSystemPropertySWT.ORG_ECLIPSE_SWT_BROWSER_XULRUNNERPATH.get();
            if (string == null && (string = System.getenv("XULRUNNER_HOME")) != null) {
                NSSystemPropertySWT.ORG_ECLIPSE_SWT_BROWSER_XULRUNNERPATH.set(string);
            }
        }
        int n = 0;
        INativeWebBrowser.WebBrowserRuntime webBrowserRuntime = (INativeWebBrowser.WebBrowserRuntime)((Object)objectArray[0]);
        switch (webBrowserRuntime) {
            case XULRUNNER: {
                n |= 0x8000;
                break;
            }
            case WEBKIT: {
                n |= 0x10000;
                break;
            }
            case EDGE: {
                n |= 0x40000;
            }
        }
        Browser browser = new Browser(composite, n);
        NativeWebBrowser.configureBrowserFunctions(browser);
        browser.addCloseWindowListener(new lIlI(browser));
        browser.addOpenWindowListener(new lIllI(browser));
        browser.addLocationListener(new l(browser));
        browser.addTitleListener(new lIIIl(browser));
        browser.addStatusTextListener(new lIll(browser));
        browser.addProgressListener(new lIlIl(browser));
        NativeWebBrowser.registerDefaultPopupMenu(browser);
        return browser;
    }

    private static void configureBrowserFunctions(Browser browser) {
        if ("edge".equals(browser.getBrowserType())) {
            new NSBrowserFocusBrowserFunction(browser);
        }
        new NSCommandBrowserFunction(browser);
        new NSConsolePrintingBrowserFunction(browser, false);
        new NSConsolePrintingBrowserFunction(browser, true);
    }

    public NativeWebBrowser(JWebBrowser jWebBrowser, INativeWebBrowser.WebBrowserRuntime webBrowserRuntime) {
        this.webBrowser = new WeakReference<JWebBrowser>(jWebBrowser);
        if (webBrowserRuntime == INativeWebBrowser.WebBrowserRuntime.DEFAULT) {
            String string = NSSystemPropertySWT.WEBBROWSER_RUNTIME.get();
            if ("xulrunner".equals(string)) {
                webBrowserRuntime = INativeWebBrowser.WebBrowserRuntime.XULRUNNER;
            } else if ("webkit".equals(string)) {
                webBrowserRuntime = INativeWebBrowser.WebBrowserRuntime.WEBKIT;
            } else if ("edge".equals(string)) {
                webBrowserRuntime = INativeWebBrowser.WebBrowserRuntime.EDGE;
            }
        }
        this.runtime = webBrowserRuntime;
        if (webBrowserRuntime == INativeWebBrowser.WebBrowserRuntime.XULRUNNER) {
            this.xulRunnerHome = NSSystemPropertySWT.WEBBROWSER_XULRUNNER_HOME.get();
        }
    }

    public static void clearSessionCookies() {
        new CMN_clearSessionCookies(null).asyncExec(true, new Object[0]);
    }

    public static String getCookie(String string, String string2) {
        return (String)new CMN_getCookie(null).syncExec(true, string, string2);
    }

    public static void setCookie(String string, String string2) {
        new CMN_setCookie(null).asyncExec(true, string, string2);
    }

    @Override
    public String getResourceLocation() {
        return (String)this.runSync(new CMN_getResourceLocation(null), new Object[0]);
    }

    @Override
    public boolean navigate(String string, WebBrowserNavigationParameters webBrowserNavigationParameters) {
        return Boolean.TRUE.equals(this.runSync(new CMN_navigate(null), string, webBrowserNavigationParameters == null ? null : webBrowserNavigationParameters.getPostData(), webBrowserNavigationParameters == null ? null : webBrowserNavigationParameters.getHeaders()));
    }

    @Override
    public String getHTMLContent() {
        return (String)this.runSync(new CMN_getHTMLContent(null), new Object[0]);
    }

    @Override
    public boolean setHTMLContent(String string) {
        return Boolean.TRUE.equals(this.runSync(new CMN_setHTMLContent(null), string));
    }

    @Override
    public boolean isJavascriptEnabled() {
        return Boolean.TRUE.equals(this.runSync(new CMN_isJavascriptEnabled(null), new Object[0]));
    }

    @Override
    public void setJavascriptEnabled(boolean bl) {
        this.runAsync(new CMN_setJavascriptEnabled(null), bl);
    }

    private static String fixJavascript(Browser browser, String string) {
        if ("mozilla".equals(browser.getBrowserType())) {
            if (isFixedJS == null) {
                isFixedJS = "%25".equals(browser.evaluate("return '%25'"));
            }
            if (!isFixedJS.booleanValue()) {
                string = JAVASCRIPT_LINE_COMMENT_PATTERN.matcher(string).replaceAll("");
                string = Utils.encodeURL(string);
            }
        }
        return string;
    }

    @Override
    public boolean executeJavascriptAndWait(String string) {
        return Boolean.TRUE.equals(this.runSync(new CMN_executeJavascript(null), string));
    }

    @Override
    public void executeJavascript(String string) {
        this.runAsync(new CMN_executeJavascript(null), string);
    }

    @Override
    public Object executeJavascriptWithResult(String string) {
        return this.runSync(new CMN_executeJavascriptWithResult(null), string);
    }

    @Override
    public void stopLoading() {
        this.runAsync(new CMN_stopLoading(null), new Object[0]);
    }

    @Override
    public void reloadPage() {
        this.runAsync(new CMN_reloadPage(null), new Object[0]);
    }

    @Override
    public boolean isBackNavigationEnabled() {
        return Boolean.TRUE.equals(this.runSync(new CMN_isBackNavigationEnabled(null), new Object[0]));
    }

    @Override
    public void navigateBack() {
        this.runAsync(new CMN_navigateBack(null), new Object[0]);
    }

    @Override
    public boolean isForwardNavigationEnabled() {
        return Boolean.TRUE.equals(this.runSync(new CMN_isForwardNavigationEnabled(null), new Object[0]));
    }

    @Override
    public void navigateForward() {
        this.runAsync(new CMN_navigateForward(null), new Object[0]);
    }

    private static void registerDefaultPopupMenu(Browser browser) {
        Menu menu = browser.getMenu();
        if (menu != null) {
            menu.dispose();
        }
        if (!"mozilla".equals(browser.getBrowserType())) {
            browser.setMenu(null);
            return;
        }
        Menu menu2 = new Menu(browser.getShell(), 8);
        String string = JWebBrowser.class.getName();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(string.substring(0, string.lastIndexOf(46)).replace('.', '/') + "/resource/WebBrowser");
        MenuItem menuItem = new MenuItem(menu2, 8);
        menuItem.setText(resourceBundle.getString("SystemMenuBack"));
        menuItem.setImage(new Image((Device)browser.getDisplay(), JWebBrowser.class.getResourceAsStream(resourceBundle.getString("SystemMenuBackIcon"))));
        menuItem.addSelectionListener(new lIl(browser));
        MenuItem menuItem2 = new MenuItem(menu2, 8);
        menuItem2.setText(resourceBundle.getString("SystemMenuForward"));
        menuItem2.setImage(new Image((Device)browser.getDisplay(), JWebBrowser.class.getResourceAsStream(resourceBundle.getString("SystemMenuForwardIcon"))));
        menuItem2.addSelectionListener(new I(browser));
        MenuItem menuItem3 = new MenuItem(menu2, 8);
        menuItem3.setText(resourceBundle.getString("SystemMenuReload"));
        menuItem3.setImage(new Image((Device)browser.getDisplay(), JWebBrowser.class.getResourceAsStream(resourceBundle.getString("SystemMenuReloadIcon"))));
        menuItem3.addSelectionListener(new llI(browser));
        MenuItem menuItem4 = new MenuItem(menu2, 8);
        menuItem4.setText(resourceBundle.getString("SystemMenuStop"));
        menuItem4.setImage(new Image((Device)browser.getDisplay(), JWebBrowser.class.getResourceAsStream(resourceBundle.getString("SystemMenuStopIcon"))));
        menuItem4.addSelectionListener(new lI(browser));
        menu2.addMenuListener(new lll(menuItem, browser, menuItem2, menuItem4));
        browser.setMenu(menu2);
    }

    @Override
    public void setDefaultPopupMenuRegistered(boolean bl) {
        this.runAsync(new CMN_setDefaultPopupMenuRegistered(null), bl);
    }

    @Override
    public String getStatusText() {
        return this.status == null ? "" : this.status;
    }

    @Override
    public String getPageTitle() {
        return this.title == null ? "" : this.title;
    }

    @Override
    public int getLoadingProgress() {
        return this.loadingProgress;
    }

    @Override
    public void registerFunction(WebBrowserFunction webBrowserFunction) {
        String string = webBrowserFunction.getName();
        if (this.nameToFunctionMap == null) {
            this.nameToFunctionMap = new HashMap();
        } else {
            WebBrowserFunction webBrowserFunction2 = (WebBrowserFunction)this.nameToFunctionMap.get(string);
            if (webBrowserFunction2 == webBrowserFunction) {
                return;
            }
            if (webBrowserFunction2 != null) {
                this.unregisterFunction(webBrowserFunction2);
            }
        }
        this.nameToFunctionMap.put(string, webBrowserFunction);
        this.runAsync(new CMN_registerFunction(null), string);
    }

    @Override
    public void unregisterFunction(WebBrowserFunction webBrowserFunction) {
        if (this.nameToFunctionMap == null) {
            return;
        }
        String string = webBrowserFunction.getName();
        WebBrowserFunction webBrowserFunction2 = (WebBrowserFunction)this.nameToFunctionMap.get(string);
        if (webBrowserFunction2 != webBrowserFunction) {
            return;
        }
        this.nameToFunctionMap.remove(webBrowserFunction);
        if (this.nameToFunctionMap.isEmpty()) {
            this.nameToFunctionMap = null;
        }
        this.runAsync(new CMN_unregisterFunction(null), string);
    }

    @Override
    public void setAuthenticationHandler(WebBrowserAuthenticationHandler webBrowserAuthenticationHandler) {
        if (this.authenticationHandler == webBrowserAuthenticationHandler) {
            return;
        }
        boolean bl = this.authenticationHandler == null;
        boolean bl2 = webBrowserAuthenticationHandler == null;
        this.authenticationHandler = webBrowserAuthenticationHandler;
        if (bl || bl2) {
            this.runAsync(new CMN_setAuthenticationHandler(null), bl);
        }
    }

    @Override
    public WebBrowserAuthenticationHandler getAuthenticationHandler() {
        return this.authenticationHandler;
    }

    @Override
    public String getBrowserType() {
        return (String)this.runSync(new CMN_getBrowserType(null), new Object[0]);
    }

    @Override
    public String getBrowserVersion() {
        return (String)this.runSync(new CMN_getBrowserVersion(null), new Object[0]);
    }

    @Override
    public void addWebBrowserListener(WebBrowserListener webBrowserListener) {
        this.listenerList.add(WebBrowserListener.class, webBrowserListener);
    }

    @Override
    public void removeWebBrowserListener(WebBrowserListener webBrowserListener) {
        this.listenerList.remove(WebBrowserListener.class, webBrowserListener);
    }

    @Override
    public Component createEmbeddableComponent(Map map) {
        this.embeddableComponent = super.createEmbeddableComponent(map);
        return this.embeddableComponent;
    }

    @Override
    protected void disposeNativePeer() {
        super.disposeNativePeer();
    }

    @Override
    public boolean unloadAndDispose() {
        if (this.isNativePeerInitialized() && Boolean.TRUE.equals(this.runSync(new CMN_unloadAndDispose(null), new Object[0]))) {
            return false;
        }
        this.disposeNativePeer();
        return true;
    }

    @Override
    public boolean print(boolean bl) {
        return Boolean.TRUE.equals(this.runSync(new CMN_print(null), bl));
    }

    static Reference access$000(NativeWebBrowser nativeWebBrowser) {
        return nativeWebBrowser.webBrowser;
    }

    static EventListenerList access$100(NativeWebBrowser nativeWebBrowser) {
        return nativeWebBrowser.listenerList;
    }

    static EventListenerList access$200(NativeWebBrowser nativeWebBrowser) {
        return nativeWebBrowser.listenerList;
    }

    static int access$300(NativeWebBrowser nativeWebBrowser) {
        return nativeWebBrowser.getComponentID();
    }

    static ObjectRegistry access$400() {
        return NativeWebBrowser.getNativeComponentRegistry();
    }

    static Component access$500(NativeWebBrowser nativeWebBrowser) {
        return nativeWebBrowser.embeddableComponent;
    }

    static EventListenerList access$600(NativeWebBrowser nativeWebBrowser) {
        return nativeWebBrowser.listenerList;
    }

    static EventListenerList access$700(NativeWebBrowser nativeWebBrowser) {
        return nativeWebBrowser.listenerList;
    }

    static EventListenerList access$800(NativeWebBrowser nativeWebBrowser) {
        return nativeWebBrowser.listenerList;
    }

    static EventListenerList access$900(NativeWebBrowser nativeWebBrowser) {
        return nativeWebBrowser.listenerList;
    }

    static EventListenerList access$1000(NativeWebBrowser nativeWebBrowser) {
        return nativeWebBrowser.listenerList;
    }

    static String access$1102(NativeWebBrowser nativeWebBrowser, String string) {
        nativeWebBrowser.title = string;
        return nativeWebBrowser.title;
    }

    static EventListenerList access$1200(NativeWebBrowser nativeWebBrowser) {
        return nativeWebBrowser.listenerList;
    }

    static String access$1302(NativeWebBrowser nativeWebBrowser, String string) {
        nativeWebBrowser.status = string;
        return nativeWebBrowser.status;
    }

    static EventListenerList access$1400(NativeWebBrowser nativeWebBrowser) {
        return nativeWebBrowser.listenerList;
    }

    static int access$1502(NativeWebBrowser nativeWebBrowser, int n) {
        nativeWebBrowser.loadingProgress = n;
        return nativeWebBrowser.loadingProgress;
    }

    static EventListenerList access$1600(NativeWebBrowser nativeWebBrowser) {
        return nativeWebBrowser.listenerList;
    }

    static void access$1900(Browser browser) {
        NativeWebBrowser.configureBrowserFunctions(browser);
    }

    static ObjectRegistry access$2000() {
        return NativeWebBrowser.getControlRegistry();
    }

    static String access$2700(Browser browser, String string) {
        return NativeWebBrowser.fixJavascript(browser, string);
    }

    static void access$4900(Browser browser) {
        NativeWebBrowser.registerDefaultPopupMenu(browser);
    }

    static Map access$5100(NativeWebBrowser nativeWebBrowser) {
        return nativeWebBrowser.nameToFunctionMap;
    }

    private static class CMN_print
    extends ControlCommandMessage {
        private CMN_print() {
        }

        @Override
        public Object run(Object[] objectArray) {
            boolean bl = (Boolean)objectArray[0];
            Browser browser = (Browser)this.getControl();
            if (Utils.IS_WINDOWS && "ie".equals(browser.getBrowserType())) {
                try {
                    Class<?> clazz = Class.forName("org.eclipse.swt.browser.IE");
                    Field field = Browser.class.getDeclaredField("webBrowser");
                    field.setAccessible(true);
                    Object object = field.get(browser);
                    if (clazz.isInstance(object)) {
                        Field field2 = clazz.getDeclaredField("auto");
                        field2.setAccessible(true);
                        OleAutomation oleAutomation = (OleAutomation)field2.get(object);
                        int[] nArray = oleAutomation.getIDsOfNames(new String[]{"ExecWB", "cmdID", "cmdexecopt"});
                        Variant[] variantArray = new Variant[]{new Variant(6), new Variant(bl ? 1 : 2)};
                        int[] nArray2 = new int[]{nArray[1], nArray[2]};
                        oleAutomation.invoke(nArray[0], variantArray, nArray2);
                        return true;
                    }
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            if (!bl) {
                return false;
            }
            return browser.execute("print();");
        }

        CMN_print(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_unloadAndDispose
    extends ControlCommandMessage {
        private CMN_unloadAndDispose() {
        }

        @Override
        public Object run(Object[] objectArray) {
            boolean bl = true;
            Browser browser = (Browser)this.getControl();
            if (browser != null && !browser.isDisposed()) {
                Shell shell = browser.getShell();
                if (browser.close()) {
                    bl = false;
                    if (shell != null) {
                        shell.dispose();
                    }
                }
            }
            return bl;
        }

        CMN_unloadAndDispose(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_getBrowserVersion
    extends ControlCommandMessage {
        private CMN_getBrowserVersion() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return new NativeJSBrowserDetection((Browser)((Browser)this.getControl())).browserVersion;
        }

        CMN_getBrowserVersion(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_getBrowserType
    extends ControlCommandMessage {
        private CMN_getBrowserType() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return ((Browser)this.getControl()).getBrowserType();
        }

        CMN_getBrowserType(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_setAuthenticationHandler
    extends ControlCommandMessage {
        private CMN_setAuthenticationHandler() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Browser browser = (Browser)this.getControl();
            boolean bl = (Boolean)objectArray[0];
            if (bl) {
                lIIl lIIl2 = new lIIl(this, browser);
                browser.setData("Browser.authenticationListener", lIIl2);
                browser.addAuthenticationListener(lIIl2);
            } else {
                browser.removeAuthenticationListener((AuthenticationListener)browser.getData("Browser.authenticationListener"));
                browser.setData("Browser.authenticationListener", null);
            }
            return null;
        }

        CMN_setAuthenticationHandler(lIlI lIlI2) {
            this();
        }
    }

    private static class CMJ_getCredentials
    extends ControlCommandMessage {
        private CMJ_getCredentials() {
        }

        @Override
        public Object run(Object[] objectArray) {
            JWebBrowser jWebBrowser;
            NativeWebBrowser nativeWebBrowser = (NativeWebBrowser)this.getNativeComponent();
            JWebBrowser jWebBrowser2 = jWebBrowser = nativeWebBrowser == null ? null : (JWebBrowser)NativeWebBrowser.access$000(nativeWebBrowser).get();
            if (jWebBrowser == null) {
                return null;
            }
            WebBrowserAuthenticationHandler webBrowserAuthenticationHandler = nativeWebBrowser.getAuthenticationHandler();
            if (webBrowserAuthenticationHandler == null) {
                return new Object[]{true, null, null};
            }
            String string = (String)objectArray[0];
            Credentials credentials = webBrowserAuthenticationHandler.getCredentials(jWebBrowser, string);
            if (credentials == null) {
                return new Object[]{false, null, null};
            }
            return new Object[]{true, credentials.getUserName(), credentials.getPassword()};
        }

        CMJ_getCredentials(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_unregisterFunction
    extends ControlCommandMessage {
        private CMN_unregisterFunction() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Browser browser = (Browser)this.getControl();
            String string = "nsFunction_" + (String)objectArray[0];
            BrowserFunction browserFunction = (BrowserFunction)browser.getData(string);
            browser.setData(string, null);
            browserFunction.dispose();
            return null;
        }

        CMN_unregisterFunction(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_registerFunction
    extends ControlCommandMessage {
        private CMN_registerFunction() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Browser browser = (Browser)this.getControl();
            String string = (String)objectArray[0];
            lllI lllI2 = new lllI(this, browser, string);
            browser.setData("nsFunction_" + string, lllI2);
            return null;
        }

        CMN_registerFunction(lIlI lIlI2) {
            this();
        }
    }

    private static class CMJ_invokeFunction
    extends ControlCommandMessage {
        private CMJ_invokeFunction() {
        }

        @Override
        public Object run(Object[] objectArray) {
            WebBrowserFunction webBrowserFunction;
            JWebBrowser jWebBrowser;
            NativeWebBrowser nativeWebBrowser = (NativeWebBrowser)this.getNativeComponent();
            JWebBrowser jWebBrowser2 = jWebBrowser = nativeWebBrowser == null ? null : (JWebBrowser)NativeWebBrowser.access$000(nativeWebBrowser).get();
            if (jWebBrowser == null) {
                return null;
            }
            if (NativeWebBrowser.access$5100(nativeWebBrowser) != null && (webBrowserFunction = (WebBrowserFunction)NativeWebBrowser.access$5100(nativeWebBrowser).get(objectArray[0])) != null) {
                return webBrowserFunction.invoke(jWebBrowser, (Object[])objectArray[1]);
            }
            return null;
        }

        CMJ_invokeFunction(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_setDefaultPopupMenuRegistered
    extends ControlCommandMessage {
        private CMN_setDefaultPopupMenuRegistered() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Browser browser = (Browser)this.getControl();
            boolean bl = (Boolean)objectArray[0];
            if (bl) {
                NativeWebBrowser.access$4900(browser);
            } else {
                Menu menu = browser.getMenu();
                if (menu != null) {
                    menu.dispose();
                }
                Menu menu2 = new Menu(browser.getShell(), 8);
                menu2.addMenuListener(new lII(this, menu2));
                browser.setMenu(menu2);
            }
            return null;
        }

        CMN_setDefaultPopupMenuRegistered(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_navigateForward
    extends ControlCommandMessage {
        private CMN_navigateForward() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return ((Browser)this.getControl()).forward();
        }

        CMN_navigateForward(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_isForwardNavigationEnabled
    extends ControlCommandMessage {
        private CMN_isForwardNavigationEnabled() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return ((Browser)this.getControl()).isForwardEnabled();
        }

        CMN_isForwardNavigationEnabled(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_navigateBack
    extends ControlCommandMessage {
        private CMN_navigateBack() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return ((Browser)this.getControl()).back();
        }

        CMN_navigateBack(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_isBackNavigationEnabled
    extends ControlCommandMessage {
        private CMN_isBackNavigationEnabled() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return ((Browser)this.getControl()).isBackEnabled();
        }

        CMN_isBackNavigationEnabled(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_reloadPage
    extends ControlCommandMessage {
        private CMN_reloadPage() {
        }

        @Override
        public Object run(Object[] objectArray) {
            ((Browser)this.getControl()).refresh();
            return null;
        }

        CMN_reloadPage(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_stopLoading
    extends ControlCommandMessage {
        private CMN_stopLoading() {
        }

        @Override
        public Object run(Object[] objectArray) {
            ((Browser)this.getControl()).stop();
            return null;
        }

        CMN_stopLoading(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_executeJavascriptWithResult
    extends ControlCommandMessage {
        private CMN_executeJavascriptWithResult() {
        }

        @Override
        public Object run(Object[] objectArray) {
            String string = (String)objectArray[0];
            Browser browser = (Browser)this.getControl();
            return browser.evaluate(NativeWebBrowser.access$2700(browser, string));
        }

        CMN_executeJavascriptWithResult(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_executeJavascript
    extends ControlCommandMessage {
        private CMN_executeJavascript() {
        }

        @Override
        public Object run(Object[] objectArray) {
            String string = (String)objectArray[0];
            Browser browser = (Browser)this.getControl();
            return browser.execute(NativeWebBrowser.access$2700(browser, string));
        }

        CMN_executeJavascript(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_setJavascriptEnabled
    extends ControlCommandMessage {
        private CMN_setJavascriptEnabled() {
        }

        @Override
        public Object run(Object[] objectArray) {
            ((Browser)this.getControl()).setJavascriptEnabled((Boolean)objectArray[0]);
            return null;
        }

        CMN_setJavascriptEnabled(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_isJavascriptEnabled
    extends ControlCommandMessage {
        private CMN_isJavascriptEnabled() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return ((Browser)this.getControl()).getJavascriptEnabled();
        }

        CMN_isJavascriptEnabled(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_setHTMLContent
    extends ControlCommandMessage {
        private CMN_setHTMLContent() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return ((Browser)this.getControl()).setText((String)objectArray[0]);
        }

        CMN_setHTMLContent(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_getHTMLContent
    extends ControlCommandMessage {
        private CMN_getHTMLContent() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return ((Browser)this.getControl()).getText();
        }

        CMN_getHTMLContent(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_navigate
    extends ControlCommandMessage {
        private CMN_navigate() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return ((Browser)this.getControl()).setUrl((String)objectArray[0], (String)objectArray[1], (String[])objectArray[2]);
        }

        CMN_navigate(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_getResourceLocation
    extends ControlCommandMessage {
        private CMN_getResourceLocation() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return ((Browser)this.getControl()).getUrl();
        }

        CMN_getResourceLocation(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_setCookie
    extends CommandMessage {
        private CMN_setCookie() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Browser.setCookie((String)objectArray[1], (String)objectArray[0]);
            return null;
        }

        CMN_setCookie(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_getCookie
    extends CommandMessage {
        private CMN_getCookie() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return Browser.getCookie((String)objectArray[1], (String)objectArray[0]);
        }

        CMN_getCookie(lIlI lIlI2) {
            this();
        }
    }

    private static class CMN_clearSessionCookies
    extends CommandMessage {
        private CMN_clearSessionCookies() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Browser.clearSessions();
            return null;
        }

        CMN_clearSessionCookies(lIlI lIlI2) {
            this();
        }
    }

    private static class NSConsolePrintingBrowserFunction
    extends BrowserFunction {
        public NSConsolePrintingBrowserFunction(Browser browser, boolean bl) {
            super(browser, bl ? "nsConsoleErr" : "nsConsoleOut");
        }

        @Override
        public Object function(Object[] objectArray) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < objectArray.length; ++i) {
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(objectArray[i]);
            }
            new CMJ_consolePrinting(null).asyncExec(this.getBrowser(), stringBuilder.toString());
            return null;
        }
    }

    private static class CMJ_consolePrinting
    extends ControlCommandMessage {
        private CMJ_consolePrinting() {
        }

        @Override
        public Object run(Object[] objectArray) {
            JWebBrowser jWebBrowser;
            NativeWebBrowser nativeWebBrowser = (NativeWebBrowser)this.getNativeComponent();
            JWebBrowser jWebBrowser2 = jWebBrowser = nativeWebBrowser == null ? null : (JWebBrowser)NativeWebBrowser.access$000(nativeWebBrowser).get();
            if (jWebBrowser == null) {
                return null;
            }
            System.out.println(objectArray[0]);
            return null;
        }

        CMJ_consolePrinting(lIlI lIlI2) {
            this();
        }
    }

    private static class NSBrowserFocusBrowserFunction
    extends BrowserFunction {
        public NSBrowserFocusBrowserFunction(Browser browser) {
            super(browser, "nsBrowserFocus");
        }

        @Override
        public Object function(Object[] objectArray) {
            new CMJ_browserFocus(null).asyncExec(this.getBrowser(), new Object[0]);
            return null;
        }
    }

    private static class CMJ_browserFocus
    extends ControlCommandMessage {
        private CMJ_browserFocus() {
        }

        @Override
        public Object run(Object[] objectArray) {
            JWebBrowser jWebBrowser;
            NativeWebBrowser nativeWebBrowser = (NativeWebBrowser)this.getNativeComponent();
            JWebBrowser jWebBrowser2 = jWebBrowser = nativeWebBrowser == null ? null : (JWebBrowser)NativeWebBrowser.access$000(nativeWebBrowser).get();
            if (jWebBrowser == null) {
                return null;
            }
            Window window = SwingUtilities.getWindowAncestor(nativeWebBrowser);
            Component component = window.getFocusOwner();
            if (component != null && component != nativeWebBrowser) {
                nativeWebBrowser.requestFocus();
            }
            MenuSelectionManager.defaultManager().clearSelectedPath();
            return null;
        }

        CMJ_browserFocus(lIlI lIlI2) {
            this();
        }
    }

    private static class NSCommandBrowserFunction
    extends BrowserFunction {
        public NSCommandBrowserFunction(Browser browser) {
            super(browser, "sendNSCommand");
        }

        @Override
        public Object function(Object[] objectArray) {
            Object[] objectArray2;
            String string;
            String string2 = objectArray.length >= 1 ? (objectArray[0] instanceof String ? (String)objectArray[0] : "") : (string = "");
            if (objectArray.length > 1) {
                objectArray2 = new Object[objectArray.length - 1];
                System.arraycopy(objectArray, 1, objectArray2, 0, objectArray2.length);
                objectArray = objectArray2;
            } else {
                objectArray2 = new Object[]{};
            }
            new CMJ_commandReceived(null).asyncExec(this.getBrowser(), string, objectArray2);
            return null;
        }
    }

    private static class CMJ_updateLoadingProgress
    extends ControlCommandMessage {
        private CMJ_updateLoadingProgress() {
        }

        @Override
        public Object run(Object[] objectArray) {
            JWebBrowser jWebBrowser;
            NativeWebBrowser nativeWebBrowser = (NativeWebBrowser)this.getNativeComponent();
            JWebBrowser jWebBrowser2 = jWebBrowser = nativeWebBrowser == null ? null : (JWebBrowser)NativeWebBrowser.access$000(nativeWebBrowser).get();
            if (jWebBrowser == null) {
                return null;
            }
            NativeWebBrowser.access$1502(nativeWebBrowser, (Integer)objectArray[0]);
            Object[] objectArray2 = NativeWebBrowser.access$1600(nativeWebBrowser).getListenerList();
            WebBrowserEvent webBrowserEvent = null;
            for (int i = objectArray2.length - 2; i >= 0; i -= 2) {
                if (objectArray2[i] != WebBrowserListener.class) continue;
                if (webBrowserEvent == null) {
                    webBrowserEvent = new WebBrowserEvent(jWebBrowser);
                }
                ((WebBrowserListener)objectArray2[i + 1]).loadingProgressChanged(webBrowserEvent);
            }
            return null;
        }

        CMJ_updateLoadingProgress(lIlI lIlI2) {
            this();
        }
    }

    private static class CMJ_updateStatus
    extends ControlCommandMessage {
        private CMJ_updateStatus() {
        }

        @Override
        public Object run(Object[] objectArray) {
            JWebBrowser jWebBrowser;
            NativeWebBrowser nativeWebBrowser = (NativeWebBrowser)this.getNativeComponent();
            JWebBrowser jWebBrowser2 = jWebBrowser = nativeWebBrowser == null ? null : (JWebBrowser)NativeWebBrowser.access$000(nativeWebBrowser).get();
            if (jWebBrowser == null) {
                return null;
            }
            NativeWebBrowser.access$1302(nativeWebBrowser, (String)objectArray[0]);
            Object[] objectArray2 = NativeWebBrowser.access$1400(nativeWebBrowser).getListenerList();
            WebBrowserEvent webBrowserEvent = null;
            for (int i = objectArray2.length - 2; i >= 0; i -= 2) {
                if (objectArray2[i] != WebBrowserListener.class) continue;
                if (webBrowserEvent == null) {
                    webBrowserEvent = new WebBrowserEvent(jWebBrowser);
                }
                ((WebBrowserListener)objectArray2[i + 1]).statusChanged(webBrowserEvent);
            }
            return null;
        }

        CMJ_updateStatus(lIlI lIlI2) {
            this();
        }
    }

    private static class CMJ_updateTitle
    extends ControlCommandMessage {
        private CMJ_updateTitle() {
        }

        @Override
        public Object run(Object[] objectArray) {
            JWebBrowser jWebBrowser;
            NativeWebBrowser nativeWebBrowser = (NativeWebBrowser)this.getNativeComponent();
            JWebBrowser jWebBrowser2 = jWebBrowser = nativeWebBrowser == null ? null : (JWebBrowser)NativeWebBrowser.access$000(nativeWebBrowser).get();
            if (jWebBrowser == null) {
                return null;
            }
            NativeWebBrowser.access$1102(nativeWebBrowser, (String)objectArray[0]);
            Object[] objectArray2 = NativeWebBrowser.access$1200(nativeWebBrowser).getListenerList();
            WebBrowserEvent webBrowserEvent = null;
            for (int i = objectArray2.length - 2; i >= 0; i -= 2) {
                if (objectArray2[i] != WebBrowserListener.class) continue;
                if (webBrowserEvent == null) {
                    webBrowserEvent = new WebBrowserEvent(jWebBrowser);
                }
                ((WebBrowserListener)objectArray2[i + 1]).titleChanged(webBrowserEvent);
            }
            return null;
        }

        CMJ_updateTitle(lIlI lIlI2) {
            this();
        }
    }

    private static class CMJ_locationChangeCanceled
    extends ControlCommandMessage {
        private CMJ_locationChangeCanceled() {
        }

        @Override
        public Object run(Object[] objectArray) {
            JWebBrowser jWebBrowser;
            NativeWebBrowser nativeWebBrowser = (NativeWebBrowser)this.getNativeComponent();
            JWebBrowser jWebBrowser2 = jWebBrowser = nativeWebBrowser == null ? null : (JWebBrowser)NativeWebBrowser.access$000(nativeWebBrowser).get();
            if (jWebBrowser == null) {
                return null;
            }
            Object[] objectArray2 = NativeWebBrowser.access$1000(nativeWebBrowser).getListenerList();
            String string = (String)objectArray[0];
            boolean bl = (Boolean)objectArray[1];
            WebBrowserNavigationEvent webBrowserNavigationEvent = null;
            for (int i = objectArray2.length - 2; i >= 0; i -= 2) {
                if (objectArray2[i] != WebBrowserListener.class) continue;
                if (webBrowserNavigationEvent == null) {
                    webBrowserNavigationEvent = new WebBrowserNavigationEvent(jWebBrowser, string, bl);
                }
                ((WebBrowserListener)objectArray2[i + 1]).locationChangeCanceled(webBrowserNavigationEvent);
            }
            return null;
        }

        CMJ_locationChangeCanceled(lIlI lIlI2) {
            this();
        }
    }

    private static class CMJ_locationChanging
    extends ControlCommandMessage {
        private CMJ_locationChanging() {
        }

        @Override
        public Object run(Object[] objectArray) {
            JWebBrowser jWebBrowser;
            NativeWebBrowser nativeWebBrowser = (NativeWebBrowser)this.getNativeComponent();
            JWebBrowser jWebBrowser2 = jWebBrowser = nativeWebBrowser == null ? null : (JWebBrowser)NativeWebBrowser.access$000(nativeWebBrowser).get();
            if (jWebBrowser == null) {
                return false;
            }
            Object[] objectArray2 = NativeWebBrowser.access$900(nativeWebBrowser).getListenerList();
            String string = (String)objectArray[0];
            boolean bl = (Boolean)objectArray[1];
            boolean bl2 = true;
            WebBrowserNavigationEvent webBrowserNavigationEvent = null;
            for (int i = objectArray2.length - 2; i >= 0; i -= 2) {
                if (objectArray2[i] != WebBrowserListener.class) continue;
                if (webBrowserNavigationEvent == null) {
                    webBrowserNavigationEvent = new WebBrowserNavigationEvent(jWebBrowser, string, bl);
                }
                ((WebBrowserListener)objectArray2[i + 1]).locationChanging(webBrowserNavigationEvent);
                bl2 &= !webBrowserNavigationEvent.isConsumed();
            }
            return bl2;
        }

        CMJ_locationChanging(lIlI lIlI2) {
            this();
        }
    }

    private static class CMJ_commandReceived
    extends ControlCommandMessage {
        private CMJ_commandReceived() {
        }

        @Override
        public Object run(Object[] objectArray) {
            JWebBrowser jWebBrowser;
            NativeWebBrowser nativeWebBrowser = (NativeWebBrowser)this.getNativeComponent();
            JWebBrowser jWebBrowser2 = jWebBrowser = nativeWebBrowser == null ? null : (JWebBrowser)NativeWebBrowser.access$000(nativeWebBrowser).get();
            if (jWebBrowser == null) {
                return null;
            }
            Object[] objectArray2 = NativeWebBrowser.access$800(nativeWebBrowser).getListenerList();
            WebBrowserCommandEvent webBrowserCommandEvent = null;
            String string = (String)objectArray[0];
            Object[] objectArray3 = (Object[])objectArray[1];
            boolean bl = string.startsWith("[Chrriis]");
            for (int i = objectArray2.length - 2; i >= 0; i -= 2) {
                if (objectArray2[i] != WebBrowserListener.class) continue;
                if (webBrowserCommandEvent == null) {
                    webBrowserCommandEvent = new WebBrowserCommandEvent(jWebBrowser, string, objectArray3);
                }
                WebBrowserListener webBrowserListener = (WebBrowserListener)objectArray2[i + 1];
                if (bl && !webBrowserListener.getClass().getName().startsWith("chrriis.")) continue;
                webBrowserListener.commandReceived(webBrowserCommandEvent);
            }
            return null;
        }

        CMJ_commandReceived(lIlI lIlI2) {
            this();
        }
    }

    private static class CMJ_locationChanged
    extends ControlCommandMessage {
        private CMJ_locationChanged() {
        }

        @Override
        public Object run(Object[] objectArray) {
            JWebBrowser jWebBrowser;
            NativeWebBrowser nativeWebBrowser = (NativeWebBrowser)this.getNativeComponent();
            JWebBrowser jWebBrowser2 = jWebBrowser = nativeWebBrowser == null ? null : (JWebBrowser)NativeWebBrowser.access$000(nativeWebBrowser).get();
            if (jWebBrowser == null) {
                return null;
            }
            Object[] objectArray2 = NativeWebBrowser.access$700(nativeWebBrowser).getListenerList();
            String string = (String)objectArray[0];
            boolean bl = (Boolean)objectArray[1];
            WebBrowserNavigationEvent webBrowserNavigationEvent = null;
            for (int i = objectArray2.length - 2; i >= 0; i -= 2) {
                if (objectArray2[i] != WebBrowserListener.class) continue;
                if (webBrowserNavigationEvent == null) {
                    webBrowserNavigationEvent = new WebBrowserNavigationEvent(jWebBrowser, string, bl);
                }
                ((WebBrowserListener)objectArray2[i + 1]).locationChanged(webBrowserNavigationEvent);
            }
            return null;
        }

        CMJ_locationChanged(lIlI lIlI2) {
            this();
        }
    }

    private static class CMJ_showWindow
    extends ControlCommandMessage {
        private CMJ_showWindow() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Serializable serializable;
            Object object;
            JWebBrowser jWebBrowser;
            NativeWebBrowser nativeWebBrowser = (NativeWebBrowser)this.getNativeComponent();
            JWebBrowser jWebBrowser2 = jWebBrowser = nativeWebBrowser == null ? null : (JWebBrowser)NativeWebBrowser.access$000(nativeWebBrowser).get();
            if (jWebBrowser == null) {
                return null;
            }
            int n = (Integer)objectArray[0];
            JWebBrowser jWebBrowser3 = (JWebBrowser)NativeWebBrowser.access$000((NativeWebBrowser)NativeWebBrowser.access$400().get(n)).get();
            jWebBrowser3.setMenuBarVisible((Boolean)objectArray[1]);
            jWebBrowser3.setButtonBarVisible((Boolean)objectArray[2]);
            jWebBrowser3.setLocationBarVisible((Boolean)objectArray[3]);
            jWebBrowser3.setStatusBarVisible((Boolean)objectArray[4]);
            Point point = (Point)objectArray[5];
            Dimension dimension = (Dimension)objectArray[6];
            JWebBrowserWindow jWebBrowserWindow = jWebBrowser3.getWebBrowserWindow();
            if (jWebBrowserWindow != null) {
                if (dimension != null) {
                    ((Window)((Object)jWebBrowserWindow)).validate();
                    object = jWebBrowserWindow.getSize();
                    serializable = NativeWebBrowser.access$500((NativeWebBrowser)jWebBrowserWindow.getWebBrowser().getNativeComponent()).getSize();
                    if (dimension.width > 0) {
                        object.width -= serializable.width;
                        object.width += dimension.width;
                    }
                    if (dimension.height > 0) {
                        object.height -= serializable.height;
                        object.height += dimension.height;
                    }
                    jWebBrowserWindow.setSize((Dimension)object);
                }
                if (point != null) {
                    jWebBrowserWindow.setLocation(point);
                }
            }
            object = NativeWebBrowser.access$600(nativeWebBrowser).getListenerList();
            serializable = null;
            for (int i = ((Object[])object).length - 2; i >= 0 && jWebBrowser3 != null; i -= 2) {
                if (object[i] != WebBrowserListener.class) continue;
                if (serializable == null) {
                    serializable = new WebBrowserWindowOpeningEvent(jWebBrowser, jWebBrowser3, point, dimension);
                }
                ((WebBrowserListener)object[i + 1]).windowOpening((WebBrowserWindowOpeningEvent)serializable);
            }
            new llIl(this, jWebBrowser3).start();
            return null;
        }

        CMJ_showWindow(lIlI lIlI2) {
            this();
        }
    }

    private static class CMJ_createWindow
    extends ControlCommandMessage {
        private CMJ_createWindow() {
        }

        @Override
        public Object run(Object[] objectArray) {
            JWebBrowser jWebBrowser;
            JWebBrowser jWebBrowser2;
            NativeWebBrowser nativeWebBrowser = (NativeWebBrowser)this.getNativeComponent();
            JWebBrowser jWebBrowser3 = jWebBrowser2 = nativeWebBrowser == null ? null : (JWebBrowser)NativeWebBrowser.access$000(nativeWebBrowser).get();
            if (jWebBrowser2 == null) {
                return null;
            }
            switch (nativeWebBrowser.getRuntime()) {
                case WEBKIT: {
                    jWebBrowser = new JWebBrowser(JWebBrowser.useWebkitRuntime());
                    break;
                }
                case XULRUNNER: {
                    jWebBrowser = new JWebBrowser(JWebBrowser.useXULRunnerRuntime());
                    break;
                }
                case EDGE: {
                    jWebBrowser = new JWebBrowser(JWebBrowser.useEdgeRuntime());
                    break;
                }
                default: {
                    jWebBrowser = new JWebBrowser(JWebBrowser.useEdgeRuntime());
                }
            }
            Object[] objectArray2 = NativeWebBrowser.access$200(nativeWebBrowser).getListenerList();
            WebBrowserWindowWillOpenEvent webBrowserWindowWillOpenEvent = null;
            for (int i = objectArray2.length - 2; i >= 0 && jWebBrowser != null; i -= 2) {
                if (objectArray2[i] != WebBrowserListener.class) continue;
                if (webBrowserWindowWillOpenEvent == null) {
                    webBrowserWindowWillOpenEvent = new WebBrowserWindowWillOpenEvent(jWebBrowser2, jWebBrowser);
                }
                ((WebBrowserListener)objectArray2[i + 1]).windowWillOpen(webBrowserWindowWillOpenEvent);
                jWebBrowser = webBrowserWindowWillOpenEvent.isConsumed() ? null : webBrowserWindowWillOpenEvent.getNewWebBrowser();
            }
            if (jWebBrowser == null) {
                return null;
            }
            if (!jWebBrowser.isNativePeerInitialized()) {
                Window window = SwingUtilities.getWindowAncestor(jWebBrowser);
                if (window == null) {
                    Window window2 = webBrowserWindowWillOpenEvent.isDialogWindow() ? SwingUtilities.getWindowAncestor(jWebBrowser2) : null;
                    window = (Window)((Object)WebBrowserWindowFactory.create(window2, jWebBrowser));
                }
                jWebBrowser.getNativeComponent().initializeNativePeer();
            }
            return NativeWebBrowser.access$300((NativeWebBrowser)jWebBrowser.getNativeComponent());
        }

        CMJ_createWindow(lIlI lIlI2) {
            this();
        }
    }

    private static class CMJ_closeWindow
    extends ControlCommandMessage {
        private CMJ_closeWindow() {
        }

        @Override
        public Object run(Object[] objectArray) {
            JWebBrowser jWebBrowser;
            NativeWebBrowser nativeWebBrowser = (NativeWebBrowser)this.getNativeComponent();
            JWebBrowser jWebBrowser2 = jWebBrowser = nativeWebBrowser == null ? null : (JWebBrowser)NativeWebBrowser.access$000(nativeWebBrowser).get();
            if (jWebBrowser == null) {
                return null;
            }
            Object[] objectArray2 = NativeWebBrowser.access$100(nativeWebBrowser).getListenerList();
            WebBrowserEvent webBrowserEvent = null;
            for (int i = objectArray2.length - 2; i >= 0; i -= 2) {
                if (objectArray2[i] != WebBrowserListener.class) continue;
                if (webBrowserEvent == null) {
                    webBrowserEvent = new WebBrowserEvent(jWebBrowser);
                }
                ((WebBrowserListener)objectArray2[i + 1]).windowClosing(webBrowserEvent);
            }
            JWebBrowserWindow jWebBrowserWindow = jWebBrowser.getWebBrowserWindow();
            if (jWebBrowserWindow != null) {
                jWebBrowserWindow.dispose();
            }
            jWebBrowser.disposeNativePeer();
            return null;
        }

        CMJ_closeWindow(lIlI lIlI2) {
            this();
        }
    }
}

