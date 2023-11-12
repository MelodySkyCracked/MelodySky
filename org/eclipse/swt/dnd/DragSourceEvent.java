/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.DNDEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.graphics.Image;

public class DragSourceEvent
extends TypedEvent {
    public int detail;
    public boolean doit;
    public int x;
    public int y;
    public TransferData dataType;
    public Image image;
    public int offsetX;
    public int offsetY;
    static final long serialVersionUID = 3257002142513770808L;

    public DragSourceEvent(DNDEvent dNDEvent) {
        super(dNDEvent);
        this.data = dNDEvent.data;
        this.detail = dNDEvent.detail;
        this.doit = dNDEvent.doit;
        this.dataType = dNDEvent.dataType;
        this.x = dNDEvent.x;
        this.y = dNDEvent.y;
        this.image = dNDEvent.image;
        this.offsetX = dNDEvent.offsetX;
        this.offsetY = dNDEvent.offsetY;
    }

    void updateEvent(DNDEvent dNDEvent) {
        dNDEvent.widget = this.widget;
        dNDEvent.time = this.time;
        dNDEvent.data = this.data;
        dNDEvent.detail = this.detail;
        dNDEvent.doit = this.doit;
        dNDEvent.dataType = this.dataType;
        dNDEvent.x = this.x;
        dNDEvent.y = this.y;
        dNDEvent.image = this.image;
        dNDEvent.offsetX = this.offsetX;
        dNDEvent.offsetY = this.offsetY;
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " operation=" + this.detail + " type=" + (this.dataType != null ? this.dataType.type : 0) + " doit=" + this.doit;
    }
}

