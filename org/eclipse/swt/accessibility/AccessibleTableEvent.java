/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import java.util.Arrays;
import java.util.EventObject;
import org.eclipse.swt.accessibility.Accessible;

public class AccessibleTableEvent
extends EventObject {
    public Accessible accessible;
    public Accessible[] accessibles;
    public String result;
    public int column;
    public int row;
    public int count;
    public boolean isSelected;
    public int[] selected;
    static final long serialVersionUID = 1624586163666270447L;

    public AccessibleTableEvent(Object object) {
        super(object);
    }

    @Override
    public String toString() {
        return "AccessibleTableEvent {accessible=" + this.accessible + " accessibles=" + Arrays.toString(this.accessibles) + " string=" + this.result + " isSelected=" + this.isSelected + " column=" + this.column + " count=" + this.count + " row=" + this.row + " selected=" + Arrays.toString(this.selected);
    }
}

