/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import java.util.EventObject;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.graphics.Rectangle;

public class AccessibleTextEvent
extends EventObject {
    public int childID;
    public int offset;
    public int length;
    public Accessible accessible;
    public String result;
    public int count;
    public int index;
    public int start;
    public int end;
    public int type;
    public int x;
    public int y;
    public int width;
    public int height;
    public int[] ranges;
    public Rectangle[] rectangles;
    static final long serialVersionUID = 3977019530868308275L;

    public AccessibleTextEvent(Object object) {
        super(object);
    }

    @Override
    public String toString() {
        return "AccessibleTextEvent {childID=" + this.childID + " offset=" + this.offset + " length=" + this.length;
    }
}

