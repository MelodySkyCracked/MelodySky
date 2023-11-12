/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.internal;

import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAuthenticationHandler;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserFunction;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserListener;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationParameters;
import java.awt.Component;
import java.util.Map;

public interface INativeWebBrowser {
    public static final String BROWSER_FOCUS_FUNCTION = "nsBrowserFocus";
    public static final String CONSOLE_OUT_FUNCTION = "nsConsoleOut";
    public static final String CONSOLE_ERR_FUNCTION = "nsConsoleErr";
    public static final String COMMAND_FUNCTION = "sendNSCommand";
    public static final String COMMAND_LOCATION_PREFIX = "command://";
    public static final String COMMAND_STATUS_PREFIX = "scommand://";

    public WebBrowserRuntime getRuntime();

    public String getResourceLocation();

    public boolean navigate(String var1, WebBrowserNavigationParameters var2);

    public String getHTMLContent();

    public boolean setHTMLContent(String var1);

    public boolean isJavascriptEnabled();

    public void setJavascriptEnabled(boolean var1);

    public boolean executeJavascriptAndWait(String var1);

    public void executeJavascript(String var1);

    public Object executeJavascriptWithResult(String var1);

    public void stopLoading();

    public void reloadPage();

    public boolean isBackNavigationEnabled();

    public void navigateBack();

    public boolean isForwardNavigationEnabled();

    public void navigateForward();

    public void setDefaultPopupMenuRegistered(boolean var1);

    public String getStatusText();

    public String getPageTitle();

    public int getLoadingProgress();

    public void registerFunction(WebBrowserFunction var1);

    public void unregisterFunction(WebBrowserFunction var1);

    public void setAuthenticationHandler(WebBrowserAuthenticationHandler var1);

    public WebBrowserAuthenticationHandler getAuthenticationHandler();

    public String getBrowserType();

    public String getBrowserVersion();

    public void addWebBrowserListener(WebBrowserListener var1);

    public void removeWebBrowserListener(WebBrowserListener var1);

    public Component createEmbeddableComponent(Map var1);

    public boolean unloadAndDispose();

    public void requestFocus();

    public boolean isNativePeerDisposed();

    public boolean isNativePeerInitialized();

    public boolean print(boolean var1);

    public static enum WebBrowserRuntime {
        DEFAULT,
        XULRUNNER,
        WEBKIT,
        EDGE;

    }
}

