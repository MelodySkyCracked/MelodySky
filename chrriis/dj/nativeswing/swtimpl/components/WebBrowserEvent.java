/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.util.EventObject;

public class WebBrowserEvent
extends EventObject {
    private JWebBrowser webBrowser;

    public WebBrowserEvent(JWebBrowser jWebBrowser) {
        super(jWebBrowser);
        this.webBrowser = jWebBrowser;
    }

    public JWebBrowser getWebBrowser() {
        return this.webBrowser;
    }
}

