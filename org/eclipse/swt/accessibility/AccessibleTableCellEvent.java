/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import java.util.Arrays;
import java.util.EventObject;
import org.eclipse.swt.accessibility.Accessible;

public class AccessibleTableCellEvent
extends EventObject {
    public Accessible accessible;
    public Accessible[] accessibles;
    public boolean isSelected;
    public int count;
    public int index;
    static final long serialVersionUID = 7231059449172889781L;

    public AccessibleTableCellEvent(Object object) {
        super(object);
    }

    @Override
    public String toString() {
        return "AccessibleTableCellEvent { accessibles=" + Arrays.toString(this.accessibles) + " isSelected=" + this.isSelected + " count=" + this.count + " index=" + this.index;
    }
}

