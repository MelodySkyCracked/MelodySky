/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Widget;

public class CTabFolderEvent
extends TypedEvent {
    public Widget item;
    public boolean doit;
    public int x;
    public int y;
    public int width;
    public int height;
    static final long serialVersionUID = 3760566386225066807L;

    CTabFolderEvent(Widget widget) {
        super(widget);
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " item=" + this.item + " doit=" + this.doit + " x=" + this.x + " y=" + this.y + " width=" + this.width + " height=" + this.height;
    }
}

