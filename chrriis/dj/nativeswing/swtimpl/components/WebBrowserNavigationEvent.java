/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;

public class WebBrowserNavigationEvent
extends WebBrowserEvent {
    private String newResourceLocation;
    private boolean isTopFrame;
    private boolean isConsumed;

    public WebBrowserNavigationEvent(JWebBrowser jWebBrowser, String string, boolean bl) {
        super(jWebBrowser);
        this.newResourceLocation = string;
        this.isTopFrame = bl;
    }

    public String getNewResourceLocation() {
        return this.newResourceLocation;
    }

    public boolean isTopFrame() {
        return this.isTopFrame;
    }

    public void consume() {
        this.isConsumed = true;
    }

    public boolean isConsumed() {
        return this.isConsumed;
    }
}

