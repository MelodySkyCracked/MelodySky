/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowOpeningEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;
import java.util.EventListener;

public interface WebBrowserListener
extends EventListener {
    public void windowWillOpen(WebBrowserWindowWillOpenEvent var1);

    public void windowOpening(WebBrowserWindowOpeningEvent var1);

    public void windowClosing(WebBrowserEvent var1);

    public void locationChanging(WebBrowserNavigationEvent var1);

    public void locationChanged(WebBrowserNavigationEvent var1);

    public void locationChangeCanceled(WebBrowserNavigationEvent var1);

    public void loadingProgressChanged(WebBrowserEvent var1);

    public void titleChanged(WebBrowserEvent var1);

    public void statusChanged(WebBrowserEvent var1);

    public void commandReceived(WebBrowserCommandEvent var1);
}

