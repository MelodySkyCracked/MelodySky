/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import java.awt.Dimension;
import java.awt.Point;

public class WebBrowserWindowOpeningEvent
extends WebBrowserEvent {
    private JWebBrowser newWebBrowser;
    private Point location;
    private Dimension size;

    public WebBrowserWindowOpeningEvent(JWebBrowser jWebBrowser, JWebBrowser jWebBrowser2, Point point, Dimension dimension) {
        super(jWebBrowser);
        this.newWebBrowser = jWebBrowser2;
        this.location = point;
        this.size = dimension;
    }

    public JWebBrowser getNewWebBrowser() {
        return this.newWebBrowser;
    }

    public Point getLocation() {
        return this.location;
    }

    public Dimension getSize() {
        return this.size;
    }
}

