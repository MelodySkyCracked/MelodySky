/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import java.util.function.Consumer;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.lIlI;
import org.eclipse.swt.events.lIlIl;
import org.eclipse.swt.internal.SWTEventListener;

public interface ExpandListener
extends SWTEventListener {
    public void itemCollapsed(ExpandEvent var1);

    public void itemExpanded(ExpandEvent var1);

    default public ExpandListener itemCollapsedAdapter(Consumer consumer) {
        return new lIlI(this, consumer);
    }

    default public ExpandListener itemExpandedAdapter(Consumer consumer) {
        return new lIlIl(this, consumer);
    }
}

