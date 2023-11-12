/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

public abstract class WebBrowserFunction {
    private String name;

    public WebBrowserFunction(String string) {
        this.name = string;
    }

    public String getName() {
        return this.name;
    }

    public abstract Object invoke(JWebBrowser var1, Object ... var2);
}

