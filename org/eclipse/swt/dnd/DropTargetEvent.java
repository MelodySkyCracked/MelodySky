/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.DNDEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Widget;

public class DropTargetEvent
extends TypedEvent {
    public int x;
    public int y;
    public int detail;
    public int operations;
    public int feedback;
    public Widget item;
    public TransferData currentDataType;
    public TransferData[] dataTypes;
    static final long serialVersionUID = 3256727264573338678L;

    public DropTargetEvent(DNDEvent dNDEvent) {
        super(dNDEvent);
        this.data = dNDEvent.data;
        this.x = dNDEvent.x;
        this.y = dNDEvent.y;
        this.detail = dNDEvent.detail;
        this.currentDataType = dNDEvent.dataType;
        this.dataTypes = dNDEvent.dataTypes;
        this.operations = dNDEvent.operations;
        this.feedback = dNDEvent.feedback;
        this.item = dNDEvent.item;
    }

    void updateEvent(DNDEvent dNDEvent) {
        dNDEvent.widget = this.widget;
        dNDEvent.time = this.time;
        dNDEvent.data = this.data;
        dNDEvent.x = this.x;
        dNDEvent.y = this.y;
        dNDEvent.detail = this.detail;
        dNDEvent.dataType = this.currentDataType;
        dNDEvent.dataTypes = this.dataTypes;
        dNDEvent.operations = this.operations;
        dNDEvent.feedback = this.feedback;
        dNDEvent.item = this.item;
    }

    @Override
    public String toString() {
        String string = super.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string.substring(0, string.length() - 1));
        stringBuilder.append(" x=");
        stringBuilder.append(this.x);
        stringBuilder.append(" y=");
        stringBuilder.append(this.y);
        stringBuilder.append(" item=");
        stringBuilder.append(this.item);
        stringBuilder.append(" operations=");
        stringBuilder.append(this.operations);
        stringBuilder.append(" operation=");
        stringBuilder.append(this.detail);
        stringBuilder.append(" feedback=");
        stringBuilder.append(this.feedback);
        stringBuilder.append(" dataTypes={ ");
        if (this.dataTypes != null) {
            for (TransferData transferData : this.dataTypes) {
                stringBuilder.append(transferData.type);
                stringBuilder.append(' ');
            }
        }
        stringBuilder.append('}');
        stringBuilder.append(" currentDataType=");
        stringBuilder.append(this.currentDataType != null ? this.currentDataType.type : 48);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

