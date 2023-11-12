/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Widget;

public class WindowEvent
extends TypedEvent {
    public boolean required;
    public Browser browser;
    public Point location;
    public Point size;
    public boolean addressBar;
    public boolean menuBar;
    public boolean statusBar;
    public boolean toolBar;
    static final long serialVersionUID = 3617851997387174969L;

    public WindowEvent(Widget widget) {
        super(widget);
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " required=" + this.required + " browser=" + this.browser + " location=" + this.location + " size=" + this.size + " addressBar=" + this.addressBar + " menuBar=" + this.menuBar + " statusBar=" + this.statusBar + " toolBar=" + this.toolBar;
    }
}

