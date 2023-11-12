/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import java.util.Arrays;
import java.util.EventObject;

public class AccessibleAttributeEvent
extends EventObject {
    public int topMargin;
    public int bottomMargin;
    public int leftMargin;
    public int rightMargin;
    public int[] tabStops;
    public boolean justify;
    public int alignment;
    public int indent;
    public int groupLevel;
    public int groupCount;
    public int groupIndex;
    public String[] attributes;
    static final long serialVersionUID = -2894665777259297851L;

    public AccessibleAttributeEvent(Object object) {
        super(object);
    }

    @Override
    public String toString() {
        return "AccessibleAttributeEvent { topMargin=" + this.topMargin + " bottomMargin=" + this.bottomMargin + " leftMargin=" + this.leftMargin + " rightMargin=" + this.rightMargin + " tabStops=" + Arrays.toString(this.tabStops) + " justify=" + this.justify + " alignment=" + this.alignment + " indent=" + this.indent + " groupLevel=" + this.groupLevel + " groupCount=" + this.groupCount + " groupIndex=" + this.groupIndex;
    }
}

