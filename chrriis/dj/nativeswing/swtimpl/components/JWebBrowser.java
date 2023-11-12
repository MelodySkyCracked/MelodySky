/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.NSOption;
import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.swtimpl.EventDispatchUtils;
import chrriis.dj.nativeswing.swtimpl.NSPanelComponent;
import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.components.DefaultWebBrowserDecorator;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowserWindow;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAuthenticationHandler;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserDecorator;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserFunction;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserListener;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationParameters;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowOpeningEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;
import chrriis.dj.nativeswing.swtimpl.components.internal.INativeWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.internal.INativeWebBrowserStatic;
import chrriis.dj.nativeswing.swtimpl.components.lIIIlll;
import chrriis.dj.nativeswing.swtimpl.internal.NativeCoreObjectFactory;
import java.awt.Component;
import java.awt.Window;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingUtilities;

public class JWebBrowser
extends NSPanelComponent {
    public static final String COMMAND_FUNCTION = "sendNSCommand";
    public static final String COMMAND_LOCATION_PREFIX = "command://";
    public static final String COMMAND_STATUS_PREFIX = "scommand://";
    private static final String USE_XULRUNNER_RUNTIME_OPTION_KEY = "XULRunner Runtime";
    private static final NSOption XUL_RUNNER_RUNTIME_OPTION = new NSOption("XULRunner Runtime");
    private static final String USE_WEBKIT_RUNTIME_OPTION_KEY = "Webkit Runtime";
    private static final NSOption WEBKIT_RUNTIME_OPTION = new NSOption("Webkit Runtime");
    private static final String USE_EDGE_RUNTIME_OPTION_KEY = "Edge Runtime";
    private static final NSOption EDGE_RUNTIME_OPTION = new NSOption("Edge Runtime");
    private static WebBrowserDecoratorFactory webBrowserDecoratorFactory;
    private WebBrowserDecorator webBrowserDecorator;
    private static INativeWebBrowserStatic webBrowserStatic;
    private INativeWebBrowser nativeWebBrowser;
    private Map webBrowserListenerToNativeWebBrowserListenerMap = new HashMap();

    public static NSOption useXULRunnerRuntime() {
        return XUL_RUNNER_RUNTIME_OPTION;
    }

    public static NSOption useWebkitRuntime() {
        return WEBKIT_RUNTIME_OPTION;
    }

    public static NSOption useEdgeRuntime() {
        return EDGE_RUNTIME_OPTION;
    }

    public static void setWebBrowserDecoratorFactory(WebBrowserDecoratorFactory webBrowserDecoratorFactory) {
        JWebBrowser.webBrowserDecoratorFactory = webBrowserDecoratorFactory;
    }

    WebBrowserDecorator getWebBrowserDecorator() {
        return this.webBrowserDecorator;
    }

    protected WebBrowserDecorator createWebBrowserDecorator(Component component) {
        WebBrowserDecorator webBrowserDecorator;
        if (webBrowserDecoratorFactory != null && (webBrowserDecorator = webBrowserDecoratorFactory.createWebBrowserDecorator(this, component)) != null) {
            return webBrowserDecorator;
        }
        return new DefaultWebBrowserDecorator(this, component);
    }

    public static void clearSessionCookies() {
        webBrowserStatic.clearSessionCookies();
    }

    public static String getCookie(String string, String string2) {
        return webBrowserStatic.getCookie(string, string2);
    }

    public static void setCookie(String string, String string2) {
        webBrowserStatic.setCookie(string, string2);
    }

    public static void copyAppearance(JWebBrowser jWebBrowser, JWebBrowser jWebBrowser2) {
        jWebBrowser2.setLocationBarVisible(jWebBrowser.isLocationBarVisible());
        jWebBrowser2.setButtonBarVisible(jWebBrowser.isButtonBarVisible());
        jWebBrowser2.setMenuBarVisible(jWebBrowser.isMenuBarVisible());
        jWebBrowser2.setStatusBarVisible(jWebBrowser.isStatusBarVisible());
    }

    public static void copyContent(JWebBrowser jWebBrowser, JWebBrowser jWebBrowser2) {
        String string = jWebBrowser.getResourceLocation();
        if ("about:blank".equals(string)) {
            jWebBrowser2.setHTMLContent(jWebBrowser.getHTMLContent());
        } else {
            jWebBrowser2.navigate(string);
        }
    }

    public JWebBrowser(NSOption ... nSOptionArray) {
        Map map = NSOption.createOptionMap(nSOptionArray);
        INativeWebBrowser.WebBrowserRuntime webBrowserRuntime = INativeWebBrowser.WebBrowserRuntime.DEFAULT;
        if (map.get(USE_XULRUNNER_RUNTIME_OPTION_KEY) != null) {
            webBrowserRuntime = INativeWebBrowser.WebBrowserRuntime.XULRUNNER;
        }
        if (map.get(USE_WEBKIT_RUNTIME_OPTION_KEY) != null) {
            if (webBrowserRuntime != INativeWebBrowser.WebBrowserRuntime.DEFAULT) {
                throw new IllegalStateException("Only one web browser runtime can be specified!");
            }
            webBrowserRuntime = INativeWebBrowser.WebBrowserRuntime.WEBKIT;
        }
        if (map.get(USE_EDGE_RUNTIME_OPTION_KEY) != null) {
            if (webBrowserRuntime != INativeWebBrowser.WebBrowserRuntime.DEFAULT) {
                throw new IllegalStateException("Only one web browser runtime can be specified!");
            }
            webBrowserRuntime = INativeWebBrowser.WebBrowserRuntime.EDGE;
        }
        this.nativeWebBrowser = (INativeWebBrowser)NativeCoreObjectFactory.create(INativeWebBrowser.class, "chrriis.dj.nativeswing.swtimpl.components.core.NativeWebBrowser", new Class[]{JWebBrowser.class, INativeWebBrowser.WebBrowserRuntime.class}, new Object[]{this, webBrowserRuntime});
        this.initialize((NativeComponent)((Object)this.nativeWebBrowser));
        this.webBrowserDecorator = this.createWebBrowserDecorator(this.nativeWebBrowser.createEmbeddableComponent(map));
        this.add((Component)this.webBrowserDecorator, "Center");
    }

    public void setStatusBarVisible(boolean bl) {
        this.webBrowserDecorator.setStatusBarVisible(bl);
    }

    public boolean isStatusBarVisible() {
        return this.webBrowserDecorator.isStatusBarVisible();
    }

    public void setMenuBarVisible(boolean bl) {
        this.webBrowserDecorator.setMenuBarVisible(bl);
    }

    public boolean isMenuBarVisible() {
        return this.webBrowserDecorator.isMenuBarVisible();
    }

    public void setButtonBarVisible(boolean bl) {
        this.webBrowserDecorator.setButtonBarVisible(bl);
    }

    public boolean isButtonBarVisible() {
        return this.webBrowserDecorator.isButtonBarVisible();
    }

    public void setLocationBarVisible(boolean bl) {
        this.webBrowserDecorator.setLocationBarVisible(bl);
    }

    public boolean isLocationBarVisible() {
        return this.webBrowserDecorator.isLocationBarVisible();
    }

    public String getPageTitle() {
        return this.nativeWebBrowser.getPageTitle();
    }

    public String getStatusText() {
        return this.nativeWebBrowser.getStatusText();
    }

    public String getHTMLContent() {
        return this.nativeWebBrowser.getHTMLContent();
    }

    public boolean setHTMLContent(String string) {
        return this.nativeWebBrowser.setHTMLContent(string);
    }

    public String getResourceLocation() {
        return this.nativeWebBrowser.getResourceLocation();
    }

    public boolean navigate(String string) {
        return this.navigate(string, null);
    }

    public boolean navigate(String string, WebBrowserNavigationParameters webBrowserNavigationParameters) {
        return this.nativeWebBrowser.navigate(string, webBrowserNavigationParameters);
    }

    public boolean isBackNavigationEnabled() {
        return this.nativeWebBrowser.isBackNavigationEnabled();
    }

    public void navigateBack() {
        this.nativeWebBrowser.navigateBack();
    }

    public boolean isForwardNavigationEnabled() {
        return this.nativeWebBrowser.isForwardNavigationEnabled();
    }

    public void navigateForward() {
        this.nativeWebBrowser.navigateForward();
    }

    public void reloadPage() {
        this.nativeWebBrowser.reloadPage();
    }

    public void stopLoading() {
        this.nativeWebBrowser.stopLoading();
    }

    public boolean isJavascriptEnabled() {
        return this.nativeWebBrowser.isJavascriptEnabled();
    }

    public void setJavascriptEnabled(boolean bl) {
        this.nativeWebBrowser.setJavascriptEnabled(bl);
    }

    public void executeJavascript(String string) {
        this.nativeWebBrowser.executeJavascript(string);
    }

    public Object executeJavascriptWithResult(String string) {
        Object[] objectArray;
        if (!string.endsWith(";")) {
            string = string + ";";
        }
        if ((objectArray = this.executeJavascriptWithCommandResult("[[getScriptResult]]", "try {  sendNSCommand('[[getScriptResult]]', (function() {" + string + "})());} catch(exxxxx) {  " + COMMAND_FUNCTION + "('[[getScriptResult]]');}")) == null) {
            return null;
        }
        return objectArray.length == 0 ? null : objectArray[0];
    }

    public static String createJavascriptFunctionCall(String string, Object ... objectArray) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string).append('(');
        for (int i = 0; i < objectArray.length; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(JWebBrowser.convertJavaObjectToJavascript(objectArray[i]));
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static String convertJavaObjectToJavascript(Object object) {
        String string;
        if (object == null) {
            return "null";
        }
        if (object instanceof Boolean || object instanceof Number) {
            return object.toString();
        }
        if (object.getClass().isArray()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');
            int n = Array.getLength(object);
            for (int i = 0; i < n; ++i) {
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(JWebBrowser.convertJavaObjectToJavascript(Array.get(object, i)));
            }
            stringBuilder.append(']');
            return stringBuilder.toString();
        }
        if ((object = object.toString()).equals(string = Utils.encodeURL((String)object))) {
            return '\'' + (String)object + '\'';
        }
        return "decodeURIComponent('" + string + "')";
    }

    private Object[] executeJavascriptWithCommandResult(String string, String string2) {
        if (!((NativeComponent)((Object)this.nativeWebBrowser)).isNativePeerInitialized()) {
            return null;
        }
        AtomicReference atomicReference = new AtomicReference();
        NCommandListener nCommandListener = new NCommandListener(string, atomicReference, null);
        this.nativeWebBrowser.addWebBrowserListener(nCommandListener);
        if (this.nativeWebBrowser.executeJavascriptAndWait(string2)) {
            for (int i = 0; i < 20; ++i) {
                EventDispatchUtils.sleepWithEventDispatch(new lIIIlll(this, atomicReference), 50);
            }
        }
        this.nativeWebBrowser.removeWebBrowserListener(nCommandListener);
        return (Object[])atomicReference.get();
    }

    public int getLoadingProgress() {
        return this.nativeWebBrowser.getLoadingProgress();
    }

    public void setAuthenticationHandler(WebBrowserAuthenticationHandler webBrowserAuthenticationHandler) {
        this.nativeWebBrowser.setAuthenticationHandler(webBrowserAuthenticationHandler);
    }

    public WebBrowserAuthenticationHandler getAuthenticationHandler() {
        return this.nativeWebBrowser.getAuthenticationHandler();
    }

    public void addWebBrowserListener(WebBrowserListener webBrowserListener) {
        this.listenerList.add(WebBrowserListener.class, webBrowserListener);
        NativeWebBrowserListener nativeWebBrowserListener = new NativeWebBrowserListener(webBrowserListener);
        this.webBrowserListenerToNativeWebBrowserListenerMap.put(webBrowserListener, nativeWebBrowserListener);
        this.nativeWebBrowser.addWebBrowserListener(nativeWebBrowserListener);
    }

    public void removeWebBrowserListener(WebBrowserListener webBrowserListener) {
        this.listenerList.remove(WebBrowserListener.class, webBrowserListener);
        NativeWebBrowserListener nativeWebBrowserListener = (NativeWebBrowserListener)this.webBrowserListenerToNativeWebBrowserListenerMap.remove(webBrowserListener);
        if (nativeWebBrowserListener != null) {
            this.nativeWebBrowser.removeWebBrowserListener(nativeWebBrowserListener);
        }
    }

    public WebBrowserListener[] getWebBrowserListeners() {
        return (WebBrowserListener[])this.listenerList.getListeners(WebBrowserListener.class);
    }

    public void setBarsVisible(boolean bl) {
        this.setMenuBarVisible(bl);
        this.setButtonBarVisible(bl);
        this.setLocationBarVisible(bl);
        this.setStatusBarVisible(bl);
    }

    public JWebBrowserWindow getWebBrowserWindow() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof JWebBrowserWindow) {
            return (JWebBrowserWindow)((Object)window);
        }
        return null;
    }

    public void setDefaultPopupMenuRegistered(boolean bl) {
        this.nativeWebBrowser.setDefaultPopupMenuRegistered(bl);
    }

    public void registerFunction(WebBrowserFunction webBrowserFunction) {
        this.nativeWebBrowser.registerFunction(webBrowserFunction);
    }

    public void unregisterFunction(WebBrowserFunction webBrowserFunction) {
        this.nativeWebBrowser.unregisterFunction(webBrowserFunction);
    }

    public String getBrowserType() {
        return this.nativeWebBrowser.getBrowserType();
    }

    public String getBrowserVersion() {
        return this.nativeWebBrowser.getBrowserVersion();
    }

    public boolean disposeNativePeer(boolean bl) {
        if (bl) {
            return this.nativeWebBrowser.unloadAndDispose();
        }
        this.disposeNativePeer();
        return true;
    }

    public boolean print(boolean bl) {
        return this.nativeWebBrowser.print(bl);
    }

    static {
        webBrowserStatic = (INativeWebBrowserStatic)NativeCoreObjectFactory.create(INativeWebBrowserStatic.class, "chrriis.dj.nativeswing.swtimpl.components.core.NativeWebBrowserStatic", new Class[0], new Object[0]);
    }

    private static class NativeWebBrowserListener
    implements WebBrowserListener {
        private Reference webBrowserListener;

        public NativeWebBrowserListener(WebBrowserListener webBrowserListener) {
            this.webBrowserListener = new WeakReference<WebBrowserListener>(webBrowserListener);
        }

        @Override
        public void commandReceived(WebBrowserCommandEvent webBrowserCommandEvent) {
            boolean bl;
            WebBrowserListener webBrowserListener = (WebBrowserListener)this.webBrowserListener.get();
            if (webBrowserListener != null && (!(bl = webBrowserCommandEvent.getCommand().startsWith("[Chrriis]")) || webBrowserListener.getClass().getName().startsWith("chrriis."))) {
                webBrowserListener.commandReceived(webBrowserCommandEvent);
            }
        }

        @Override
        public void loadingProgressChanged(WebBrowserEvent webBrowserEvent) {
            WebBrowserListener webBrowserListener = (WebBrowserListener)this.webBrowserListener.get();
            if (webBrowserListener != null) {
                webBrowserListener.loadingProgressChanged(webBrowserEvent);
            }
        }

        @Override
        public void locationChangeCanceled(WebBrowserNavigationEvent webBrowserNavigationEvent) {
            WebBrowserListener webBrowserListener = (WebBrowserListener)this.webBrowserListener.get();
            if (webBrowserListener != null) {
                webBrowserListener.locationChangeCanceled(webBrowserNavigationEvent);
            }
        }

        @Override
        public void locationChanged(WebBrowserNavigationEvent webBrowserNavigationEvent) {
            WebBrowserListener webBrowserListener = (WebBrowserListener)this.webBrowserListener.get();
            if (webBrowserListener != null) {
                webBrowserListener.locationChanged(webBrowserNavigationEvent);
            }
        }

        @Override
        public void locationChanging(WebBrowserNavigationEvent webBrowserNavigationEvent) {
            WebBrowserListener webBrowserListener = (WebBrowserListener)this.webBrowserListener.get();
            if (webBrowserListener != null) {
                webBrowserListener.locationChanging(webBrowserNavigationEvent);
            }
        }

        @Override
        public void statusChanged(WebBrowserEvent webBrowserEvent) {
            WebBrowserListener webBrowserListener = (WebBrowserListener)this.webBrowserListener.get();
            if (webBrowserListener != null) {
                webBrowserListener.statusChanged(webBrowserEvent);
            }
        }

        @Override
        public void titleChanged(WebBrowserEvent webBrowserEvent) {
            WebBrowserListener webBrowserListener = (WebBrowserListener)this.webBrowserListener.get();
            if (webBrowserListener != null) {
                webBrowserListener.titleChanged(webBrowserEvent);
            }
        }

        @Override
        public void windowClosing(WebBrowserEvent webBrowserEvent) {
            WebBrowserListener webBrowserListener = (WebBrowserListener)this.webBrowserListener.get();
            if (webBrowserListener != null) {
                webBrowserListener.windowClosing(webBrowserEvent);
            }
        }

        @Override
        public void windowOpening(WebBrowserWindowOpeningEvent webBrowserWindowOpeningEvent) {
            WebBrowserListener webBrowserListener = (WebBrowserListener)this.webBrowserListener.get();
            if (webBrowserListener != null) {
                webBrowserListener.windowOpening(webBrowserWindowOpeningEvent);
            }
        }

        @Override
        public void windowWillOpen(WebBrowserWindowWillOpenEvent webBrowserWindowWillOpenEvent) {
            WebBrowserListener webBrowserListener = (WebBrowserListener)this.webBrowserListener.get();
            if (webBrowserListener != null) {
                webBrowserListener.windowWillOpen(webBrowserWindowWillOpenEvent);
            }
        }
    }

    private static class NCommandListener
    extends WebBrowserAdapter {
        private String command;
        private AtomicReference result;

        private NCommandListener(String string, AtomicReference atomicReference) {
            this.command = string;
            this.result = atomicReference;
        }

        @Override
        public void commandReceived(WebBrowserCommandEvent webBrowserCommandEvent) {
            if (this.command.equals(webBrowserCommandEvent.getCommand())) {
                this.result.set(webBrowserCommandEvent.getParameters());
                ((INativeWebBrowser)((Object)webBrowserCommandEvent.getWebBrowser().getNativeComponent())).removeWebBrowserListener(this);
            }
        }

        NCommandListener(String string, AtomicReference atomicReference, lIIIlll lIIIlll2) {
            this(string, atomicReference);
        }
    }

    public static interface WebBrowserDecoratorFactory {
        public WebBrowserDecorator createWebBrowserDecorator(JWebBrowser var1, Component var2);
    }
}

