/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;

public class WebBrowserWindowWillOpenEvent
extends WebBrowserEvent {
    private JWebBrowser newWebBrowser;
    private boolean isDialogWindow;

    public WebBrowserWindowWillOpenEvent(JWebBrowser jWebBrowser, JWebBrowser jWebBrowser2) {
        super(jWebBrowser);
        this.newWebBrowser = jWebBrowser2;
    }

    public JWebBrowser getNewWebBrowser() {
        return this.newWebBrowser;
    }

    public void setNewWebBrowser(JWebBrowser jWebBrowser) {
        if (jWebBrowser == null) {
            throw new IllegalArgumentException("The new web browser cannot be null. To prevent the window to open, use the consume() method.");
        }
        this.newWebBrowser = jWebBrowser;
    }

    public void setDialogWindow(boolean bl) {
        this.isDialogWindow = bl;
    }

    public boolean isDialogWindow() {
        return this.isDialogWindow;
    }

    public void consume() {
        this.newWebBrowser = null;
    }

    public boolean isConsumed() {
        return this.newWebBrowser == null;
    }
}

