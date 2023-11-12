/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserListener;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowOpeningEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;

public abstract class WebBrowserAdapter
implements WebBrowserListener {
    @Override
    public void windowWillOpen(WebBrowserWindowWillOpenEvent webBrowserWindowWillOpenEvent) {
    }

    @Override
    public void windowOpening(WebBrowserWindowOpeningEvent webBrowserWindowOpeningEvent) {
    }

    @Override
    public void windowClosing(WebBrowserEvent webBrowserEvent) {
    }

    @Override
    public void locationChanging(WebBrowserNavigationEvent webBrowserNavigationEvent) {
    }

    @Override
    public void locationChanged(WebBrowserNavigationEvent webBrowserNavigationEvent) {
    }

    @Override
    public void locationChangeCanceled(WebBrowserNavigationEvent webBrowserNavigationEvent) {
    }

    @Override
    public void loadingProgressChanged(WebBrowserEvent webBrowserEvent) {
    }

    @Override
    public void titleChanged(WebBrowserEvent webBrowserEvent) {
    }

    @Override
    public void statusChanged(WebBrowserEvent webBrowserEvent) {
    }

    @Override
    public void commandReceived(WebBrowserCommandEvent webBrowserCommandEvent) {
    }
}

