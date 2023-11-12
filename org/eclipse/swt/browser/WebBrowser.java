/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.browser.AuthenticationListener;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.browser.VisibilityWindowListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

abstract class WebBrowser {
    Browser browser;
    Map functions = new HashMap();
    AuthenticationListener[] authenticationListeners = new AuthenticationListener[0];
    CloseWindowListener[] closeWindowListeners = new CloseWindowListener[0];
    LocationListener[] locationListeners = new LocationListener[0];
    OpenWindowListener[] openWindowListeners = new OpenWindowListener[0];
    ProgressListener[] progressListeners = new ProgressListener[0];
    StatusTextListener[] statusTextListeners = new StatusTextListener[0];
    TitleListener[] titleListeners = new TitleListener[0];
    VisibilityWindowListener[] visibilityWindowListeners = new VisibilityWindowListener[0];
    boolean jsEnabledOnNextPage = true;
    boolean jsEnabled = true;
    int nextFunctionIndex = 1;
    Object evaluateResult;
    static final String ERROR_ID = "org.eclipse.swt.browser.error";
    static final String EXECUTE_ID = "SWTExecuteTemporaryFunction";
    static List NativePendingCookies = new ArrayList();
    static String CookieName;
    static String CookieValue;
    static String CookieUrl;
    static boolean CookieResult;
    static Runnable NativeClearSessions;
    static Runnable NativeGetCookie;
    static Runnable NativeSetCookie;
    static final int[][] KeyTable;

    WebBrowser() {
    }

    public void addAuthenticationListener(AuthenticationListener authenticationListener) {
        AuthenticationListener[] authenticationListenerArray = new AuthenticationListener[this.authenticationListeners.length + 1];
        System.arraycopy(this.authenticationListeners, 0, authenticationListenerArray, 0, this.authenticationListeners.length);
        this.authenticationListeners = authenticationListenerArray;
        authenticationListenerArray[this.authenticationListeners.length - 1] = authenticationListener;
    }

    public void addCloseWindowListener(CloseWindowListener closeWindowListener) {
        CloseWindowListener[] closeWindowListenerArray = new CloseWindowListener[this.closeWindowListeners.length + 1];
        System.arraycopy(this.closeWindowListeners, 0, closeWindowListenerArray, 0, this.closeWindowListeners.length);
        this.closeWindowListeners = closeWindowListenerArray;
        closeWindowListenerArray[this.closeWindowListeners.length - 1] = closeWindowListener;
    }

    public void addLocationListener(LocationListener locationListener) {
        LocationListener[] locationListenerArray = new LocationListener[this.locationListeners.length + 1];
        System.arraycopy(this.locationListeners, 0, locationListenerArray, 0, this.locationListeners.length);
        this.locationListeners = locationListenerArray;
        locationListenerArray[this.locationListeners.length - 1] = locationListener;
    }

    public void addOpenWindowListener(OpenWindowListener openWindowListener) {
        OpenWindowListener[] openWindowListenerArray = new OpenWindowListener[this.openWindowListeners.length + 1];
        System.arraycopy(this.openWindowListeners, 0, openWindowListenerArray, 0, this.openWindowListeners.length);
        this.openWindowListeners = openWindowListenerArray;
        openWindowListenerArray[this.openWindowListeners.length - 1] = openWindowListener;
    }

    public void addProgressListener(ProgressListener progressListener) {
        ProgressListener[] progressListenerArray = new ProgressListener[this.progressListeners.length + 1];
        System.arraycopy(this.progressListeners, 0, progressListenerArray, 0, this.progressListeners.length);
        this.progressListeners = progressListenerArray;
        progressListenerArray[this.progressListeners.length - 1] = progressListener;
    }

    public void addStatusTextListener(StatusTextListener statusTextListener) {
        StatusTextListener[] statusTextListenerArray = new StatusTextListener[this.statusTextListeners.length + 1];
        System.arraycopy(this.statusTextListeners, 0, statusTextListenerArray, 0, this.statusTextListeners.length);
        this.statusTextListeners = statusTextListenerArray;
        statusTextListenerArray[this.statusTextListeners.length - 1] = statusTextListener;
    }

    public void addTitleListener(TitleListener titleListener) {
        TitleListener[] titleListenerArray = new TitleListener[this.titleListeners.length + 1];
        System.arraycopy(this.titleListeners, 0, titleListenerArray, 0, this.titleListeners.length);
        this.titleListeners = titleListenerArray;
        titleListenerArray[this.titleListeners.length - 1] = titleListener;
    }

    public void addVisibilityWindowListener(VisibilityWindowListener visibilityWindowListener) {
        VisibilityWindowListener[] visibilityWindowListenerArray = new VisibilityWindowListener[this.visibilityWindowListeners.length + 1];
        System.arraycopy(this.visibilityWindowListeners, 0, visibilityWindowListenerArray, 0, this.visibilityWindowListeners.length);
        this.visibilityWindowListeners = visibilityWindowListenerArray;
        visibilityWindowListenerArray[this.visibilityWindowListeners.length - 1] = visibilityWindowListener;
    }

    public abstract boolean back();

    public static void clearSessions() {
        if (NativeClearSessions != null) {
            NativeClearSessions.run();
        }
    }

    public static String GetCookie(String string, String string2) {
        CookieName = string;
        CookieUrl = string2;
        CookieValue = null;
        if (NativeGetCookie != null) {
            NativeGetCookie.run();
        }
        String string3 = CookieValue;
        CookieUrl = null;
        CookieValue = null;
        CookieName = null;
        return string3;
    }

    public static boolean SetCookie(String string, String string2, boolean bl) {
        CookieValue = string;
        CookieUrl = string2;
        CookieResult = false;
        if (NativeSetCookie != null) {
            NativeSetCookie.run();
        } else if (bl && NativePendingCookies != null) {
            NativePendingCookies.add(new String[]{string, string2});
        }
        CookieUrl = null;
        CookieValue = null;
        return CookieResult;
    }

    static void SetPendingCookies(List list) {
        for (String[] stringArray : list) {
            WebBrowser.SetCookie(stringArray[0], stringArray[1], false);
        }
    }

    public abstract void create(Composite var1, int var2);

    static String CreateErrorString(String string) {
        return ERROR_ID + string;
    }

    static String ExtractError(String string) {
        return string.substring(29);
    }

    public boolean close() {
        return true;
    }

    public void createFunction(BrowserFunction browserFunction) {
        Object object2;
        for (Object object2 : this.functions.values()) {
            if (!((BrowserFunction)object2).name.equals(browserFunction.name)) continue;
            this.deregisterFunction((BrowserFunction)object2);
            break;
        }
        browserFunction.index = this.getNextFunctionIndex();
        this.registerFunction(browserFunction);
        StringBuilder stringBuilder = new StringBuilder(browserFunction.name);
        stringBuilder.append(" = function ");
        stringBuilder.append(browserFunction.name);
        stringBuilder.append("() {var result = callJava(");
        stringBuilder.append(browserFunction.index);
        stringBuilder.append(",'");
        stringBuilder.append(browserFunction.token);
        stringBuilder.append("',Array.prototype.slice.call(arguments)); if (typeof result == 'string' && result.indexOf('");
        stringBuilder.append(ERROR_ID);
        stringBuilder.append("') == 0) {var error = new Error(result.substring(");
        stringBuilder.append(29);
        stringBuilder.append(")); throw error;} return result;};");
        object2 = this.getJavaCallDeclaration();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append((String)object2);
        if (browserFunction.top) {
            stringBuilder2.append(stringBuilder.toString());
        }
        stringBuilder2.append("var frameIds = null;");
        if (browserFunction.frameNames != null) {
            stringBuilder2.append("frameIds = {");
            for (String string : browserFunction.frameNames) {
                stringBuilder2.append('\'');
                stringBuilder2.append(string);
                stringBuilder2.append("':1,");
            }
            if (browserFunction.frameNames.length > 0) {
                stringBuilder2.deleteCharAt(stringBuilder2.length() - 1);
            }
            stringBuilder2.append("};");
        }
        stringBuilder2.append("for (var i = 0; i < frames.length; i++) {try {if (!frameIds || (frames[i].name && frameIds[frames[i].name])) {");
        stringBuilder2.append("if (!frames[i].callJava) {frames[i].callJava = window.callJava;} frames[i].");
        stringBuilder2.append(stringBuilder.toString());
        stringBuilder2.append("}} catch(e) {}};");
        browserFunction.functionString = stringBuilder2.toString();
        this.nonBlockingExecute(browserFunction.functionString);
    }

    String getJavaCallDeclaration() {
        return "if (!window.callJava) {\n\t\twindow.callJava = function callJava(index, token, args) {\n\t\t\treturn external.callJava(index,token,args);\n\t\t}\n};\n";
    }

    void deregisterFunction(BrowserFunction browserFunction) {
        this.functions.remove(browserFunction.index);
    }

    public void destroyFunction(BrowserFunction browserFunction) {
        String string = this.getDeleteFunctionString(browserFunction.name);
        StringBuilder stringBuilder = new StringBuilder("for (var i = 0; i < frames.length; i++) {try {frames[i].eval(\"");
        stringBuilder.append(string);
        stringBuilder.append("\");} catch (e) {}}");
        this.nonBlockingExecute(stringBuilder.toString());
        this.nonBlockingExecute(string);
        this.deregisterFunction(browserFunction);
    }

    void nonBlockingExecute(String string) {
        this.execute(string);
    }

    public abstract boolean execute(String var1);

    public Object evaluate(String string, boolean bl) throws SWTException {
        return this.evaluate(string);
    }

    public Object evaluate(String string) throws SWTException {
        int n;
        EvaluateFunction evaluateFunction = new EvaluateFunction(this, this.browser, "");
        evaluateFunction.index = n = this.getNextFunctionIndex();
        evaluateFunction.isEvaluate = true;
        this.registerFunction(evaluateFunction);
        String string2 = EXECUTE_ID + n;
        StringBuilder stringBuilder = new StringBuilder("window.");
        stringBuilder.append(string2);
        stringBuilder.append(" = function ");
        stringBuilder.append(string2);
        stringBuilder.append("() {\n");
        stringBuilder.append(string);
        stringBuilder.append("\n};");
        this.nonBlockingExecute(stringBuilder.toString());
        stringBuilder = new StringBuilder("if (window.");
        stringBuilder.append(string2);
        stringBuilder.append(" == undefined) {window.external.callJava(");
        stringBuilder.append(n);
        stringBuilder.append(",'");
        stringBuilder.append(evaluateFunction.token);
        stringBuilder.append("', ['");
        stringBuilder.append(ERROR_ID);
        stringBuilder.append("']);} else {try {var result = ");
        stringBuilder.append(string2);
        stringBuilder.append("(); window.external.callJava(");
        stringBuilder.append(n);
        stringBuilder.append(",'");
        stringBuilder.append(evaluateFunction.token);
        stringBuilder.append("', [result]);} catch (e) {window.external.callJava(");
        stringBuilder.append(n);
        stringBuilder.append(",'");
        stringBuilder.append(evaluateFunction.token);
        stringBuilder.append("', ['");
        stringBuilder.append(ERROR_ID);
        stringBuilder.append("' + e.message]);}}");
        this.nonBlockingExecute(stringBuilder.toString());
        this.nonBlockingExecute(this.getDeleteFunctionString(string2));
        this.deregisterFunction(evaluateFunction);
        Object object = this.evaluateResult;
        this.evaluateResult = null;
        if (object instanceof SWTException) {
            throw (SWTException)object;
        }
        return object;
    }

    public abstract boolean forward();

    public abstract String getBrowserType();

    String getDeleteFunctionString(String string) {
        return "delete window." + string;
    }

    int getNextFunctionIndex() {
        return this.nextFunctionIndex++;
    }

    public abstract String getText();

    public abstract String getUrl();

    public Object getWebBrowser() {
        return null;
    }

    public abstract boolean isBackEnabled();

    public boolean isFocusControl() {
        return false;
    }

    public abstract boolean isForwardEnabled();

    public abstract void refresh();

    void registerFunction(BrowserFunction browserFunction) {
        this.functions.put(browserFunction.index, browserFunction);
    }

    public void removeAuthenticationListener(AuthenticationListener authenticationListener) {
        if (this.authenticationListeners.length == 0) {
            return;
        }
        int n = -1;
        for (int i = 0; i < this.authenticationListeners.length; ++i) {
            if (authenticationListener != this.authenticationListeners[i]) continue;
            n = i;
            break;
        }
        if (n == -1) {
            return;
        }
        if (this.authenticationListeners.length == 1) {
            this.authenticationListeners = new AuthenticationListener[0];
            return;
        }
        AuthenticationListener[] authenticationListenerArray = new AuthenticationListener[this.authenticationListeners.length - 1];
        System.arraycopy(this.authenticationListeners, 0, authenticationListenerArray, 0, n);
        System.arraycopy(this.authenticationListeners, n + 1, authenticationListenerArray, n, this.authenticationListeners.length - n - 1);
        this.authenticationListeners = authenticationListenerArray;
    }

    public void removeCloseWindowListener(CloseWindowListener closeWindowListener) {
        if (this.closeWindowListeners.length == 0) {
            return;
        }
        int n = -1;
        for (int i = 0; i < this.closeWindowListeners.length; ++i) {
            if (closeWindowListener != this.closeWindowListeners[i]) continue;
            n = i;
            break;
        }
        if (n == -1) {
            return;
        }
        if (this.closeWindowListeners.length == 1) {
            this.closeWindowListeners = new CloseWindowListener[0];
            return;
        }
        CloseWindowListener[] closeWindowListenerArray = new CloseWindowListener[this.closeWindowListeners.length - 1];
        System.arraycopy(this.closeWindowListeners, 0, closeWindowListenerArray, 0, n);
        System.arraycopy(this.closeWindowListeners, n + 1, closeWindowListenerArray, n, this.closeWindowListeners.length - n - 1);
        this.closeWindowListeners = closeWindowListenerArray;
    }

    public void removeLocationListener(LocationListener locationListener) {
        if (this.locationListeners.length == 0) {
            return;
        }
        int n = -1;
        for (int i = 0; i < this.locationListeners.length; ++i) {
            if (locationListener != this.locationListeners[i]) continue;
            n = i;
            break;
        }
        if (n == -1) {
            return;
        }
        if (this.locationListeners.length == 1) {
            this.locationListeners = new LocationListener[0];
            return;
        }
        LocationListener[] locationListenerArray = new LocationListener[this.locationListeners.length - 1];
        System.arraycopy(this.locationListeners, 0, locationListenerArray, 0, n);
        System.arraycopy(this.locationListeners, n + 1, locationListenerArray, n, this.locationListeners.length - n - 1);
        this.locationListeners = locationListenerArray;
    }

    public void removeOpenWindowListener(OpenWindowListener openWindowListener) {
        if (this.openWindowListeners.length == 0) {
            return;
        }
        int n = -1;
        for (int i = 0; i < this.openWindowListeners.length; ++i) {
            if (openWindowListener != this.openWindowListeners[i]) continue;
            n = i;
            break;
        }
        if (n == -1) {
            return;
        }
        if (this.openWindowListeners.length == 1) {
            this.openWindowListeners = new OpenWindowListener[0];
            return;
        }
        OpenWindowListener[] openWindowListenerArray = new OpenWindowListener[this.openWindowListeners.length - 1];
        System.arraycopy(this.openWindowListeners, 0, openWindowListenerArray, 0, n);
        System.arraycopy(this.openWindowListeners, n + 1, openWindowListenerArray, n, this.openWindowListeners.length - n - 1);
        this.openWindowListeners = openWindowListenerArray;
    }

    public void removeProgressListener(ProgressListener progressListener) {
        if (this.progressListeners.length == 0) {
            return;
        }
        int n = -1;
        for (int i = 0; i < this.progressListeners.length; ++i) {
            if (progressListener != this.progressListeners[i]) continue;
            n = i;
            break;
        }
        if (n == -1) {
            return;
        }
        if (this.progressListeners.length == 1) {
            this.progressListeners = new ProgressListener[0];
            return;
        }
        ProgressListener[] progressListenerArray = new ProgressListener[this.progressListeners.length - 1];
        System.arraycopy(this.progressListeners, 0, progressListenerArray, 0, n);
        System.arraycopy(this.progressListeners, n + 1, progressListenerArray, n, this.progressListeners.length - n - 1);
        this.progressListeners = progressListenerArray;
    }

    public void removeStatusTextListener(StatusTextListener statusTextListener) {
        if (this.statusTextListeners.length == 0) {
            return;
        }
        int n = -1;
        for (int i = 0; i < this.statusTextListeners.length; ++i) {
            if (statusTextListener != this.statusTextListeners[i]) continue;
            n = i;
            break;
        }
        if (n == -1) {
            return;
        }
        if (this.statusTextListeners.length == 1) {
            this.statusTextListeners = new StatusTextListener[0];
            return;
        }
        StatusTextListener[] statusTextListenerArray = new StatusTextListener[this.statusTextListeners.length - 1];
        System.arraycopy(this.statusTextListeners, 0, statusTextListenerArray, 0, n);
        System.arraycopy(this.statusTextListeners, n + 1, statusTextListenerArray, n, this.statusTextListeners.length - n - 1);
        this.statusTextListeners = statusTextListenerArray;
    }

    public void removeTitleListener(TitleListener titleListener) {
        if (this.titleListeners.length == 0) {
            return;
        }
        int n = -1;
        for (int i = 0; i < this.titleListeners.length; ++i) {
            if (titleListener != this.titleListeners[i]) continue;
            n = i;
            break;
        }
        if (n == -1) {
            return;
        }
        if (this.titleListeners.length == 1) {
            this.titleListeners = new TitleListener[0];
            return;
        }
        TitleListener[] titleListenerArray = new TitleListener[this.titleListeners.length - 1];
        System.arraycopy(this.titleListeners, 0, titleListenerArray, 0, n);
        System.arraycopy(this.titleListeners, n + 1, titleListenerArray, n, this.titleListeners.length - n - 1);
        this.titleListeners = titleListenerArray;
    }

    public void removeVisibilityWindowListener(VisibilityWindowListener visibilityWindowListener) {
        if (this.visibilityWindowListeners.length == 0) {
            return;
        }
        int n = -1;
        for (int i = 0; i < this.visibilityWindowListeners.length; ++i) {
            if (visibilityWindowListener != this.visibilityWindowListeners[i]) continue;
            n = i;
            break;
        }
        if (n == -1) {
            return;
        }
        if (this.visibilityWindowListeners.length == 1) {
            this.visibilityWindowListeners = new VisibilityWindowListener[0];
            return;
        }
        VisibilityWindowListener[] visibilityWindowListenerArray = new VisibilityWindowListener[this.visibilityWindowListeners.length - 1];
        System.arraycopy(this.visibilityWindowListeners, 0, visibilityWindowListenerArray, 0, n);
        System.arraycopy(this.visibilityWindowListeners, n + 1, visibilityWindowListenerArray, n, this.visibilityWindowListeners.length - n - 1);
        this.visibilityWindowListeners = visibilityWindowListenerArray;
    }

    boolean sendKeyEvent(Event event) {
        int n = 0;
        boolean bl = true;
        switch (event.keyCode) {
            case 27: {
                n = 2;
                bl = true;
                break;
            }
            case 13: {
                n = 4;
                bl = false;
                break;
            }
            case 0x1000002: 
            case 0x1000004: {
                n = 64;
                bl = false;
                break;
            }
            case 0x1000001: 
            case 0x1000003: {
                n = 32;
                bl = false;
                break;
            }
            case 9: {
                n = (event.stateMask & 0x20000) != 0 ? 8 : 16;
                bl = (event.stateMask & 0x40000) != 0;
                break;
            }
            case 0x1000006: {
                if ((event.stateMask & 0x40000) == 0) break;
                n = 512;
                bl = true;
                break;
            }
            case 0x1000005: {
                if ((event.stateMask & 0x40000) == 0) break;
                n = 256;
                bl = true;
                break;
            }
            default: {
                if (!this.translateMnemonics() || event.character == '\u0000' || (event.stateMask & 0x50000) != 65536) break;
                n = 128;
                bl = true;
            }
        }
        boolean bl2 = true;
        if (n != 0) {
            boolean bl3 = event.doit;
            event.doit = bl;
            bl2 = !this.browser.traverse(n, event);
            event.doit = bl3;
        }
        if (bl2) {
            this.browser.notifyListeners(event.type, event);
            bl2 = event.doit;
        }
        return bl2;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public abstract boolean setText(String var1, boolean var2);

    public abstract boolean setUrl(String var1, String var2, String[] var3);

    public abstract void stop();

    int translateKey(int n) {
        for (int[] nArray : KeyTable) {
            if (nArray[0] != n) continue;
            return nArray[1];
        }
        return 0;
    }

    boolean translateMnemonics() {
        return true;
    }

    static {
        KeyTable = new int[][]{{18, 65536}, {16, 131072}, {17, 262144}, {224, 0x400000}, {65, 97}, {66, 98}, {67, 99}, {68, 100}, {69, 101}, {70, 102}, {71, 103}, {72, 104}, {73, 105}, {74, 106}, {75, 107}, {76, 108}, {77, 109}, {78, 110}, {79, 111}, {80, 112}, {81, 113}, {82, 114}, {83, 115}, {84, 116}, {85, 117}, {86, 118}, {87, 119}, {88, 120}, {89, 121}, {90, 122}, {48, 48}, {49, 49}, {50, 50}, {51, 51}, {52, 52}, {53, 53}, {54, 54}, {55, 55}, {56, 56}, {57, 57}, {32, 32}, {59, 59}, {61, 61}, {188, 44}, {190, 46}, {191, 47}, {219, 91}, {221, 93}, {222, 39}, {192, 96}, {220, 92}, {108, 124}, {226, 60}, {37, 0x1000003}, {39, 0x1000004}, {38, 0x1000001}, {40, 0x1000002}, {45, 0x1000009}, {36, 0x1000007}, {35, 0x1000008}, {46, 127}, {33, 0x1000005}, {34, 0x1000006}, {8, 8}, {13, 13}, {9, 9}, {27, 27}, {12, 127}, {112, 0x100000A}, {113, 0x100000B}, {114, 0x100000C}, {115, 0x100000D}, {116, 0x100000E}, {117, 0x100000F}, {118, 0x1000010}, {119, 0x1000011}, {120, 0x1000012}, {121, 0x1000013}, {122, 0x1000014}, {123, 0x1000015}, {124, 0x1000016}, {125, 0x1000017}, {126, 0x1000018}, {127, 0}, {128, 0}, {129, 0}, {130, 0}, {131, 0}, {132, 0}, {133, 0}, {134, 0}, {135, 0}, {96, 0x1000030}, {97, 0x1000031}, {98, 16777266}, {99, 0x1000033}, {100, 16777268}, {101, 16777269}, {102, 16777270}, {103, 16777271}, {104, 16777272}, {105, 16777273}, {14, 0x1000050}, {107, 16777259}, {109, 16777261}, {106, 16777258}, {111, 16777263}, {110, 16777262}, {20, 16777298}, {144, 16777299}, {145, 16777300}, {44, 16777303}, {6, 0x1000051}, {19, 0x1000055}, {3, 16777302}, {186, 59}, {187, 61}, {189, 45}};
    }

    public class EvaluateFunction
    extends BrowserFunction {
        final WebBrowser this$0;

        public EvaluateFunction(WebBrowser webBrowser, Browser browser, String string) {
            this.this$0 = webBrowser;
            super(browser, string, true, new String[0], false);
        }

        @Override
        public Object function(Object[] objectArray) {
            String string;
            if (objectArray[0] instanceof String && (string = (String)objectArray[0]).startsWith(WebBrowser.ERROR_ID)) {
                String string2 = WebBrowser.ExtractError(string);
                this.this$0.evaluateResult = string2.length() > 0 ? new SWTException(50, string2) : new SWTException(50);
                return null;
            }
            this.this$0.evaluateResult = objectArray[0];
            return null;
        }
    }
}

