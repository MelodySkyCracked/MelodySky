/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.core;

import chrriis.dj.nativeswing.swtimpl.components.core.NativeWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.internal.INativeWebBrowserStatic;

class NativeWebBrowserStatic
implements INativeWebBrowserStatic {
    NativeWebBrowserStatic() {
    }

    @Override
    public void clearSessionCookies() {
        NativeWebBrowser.clearSessionCookies();
    }

    @Override
    public String getCookie(String string, String string2) {
        return NativeWebBrowser.getCookie(string, string2);
    }

    @Override
    public void setCookie(String string, String string2) {
        NativeWebBrowser.setCookie(string, string2);
    }
}

