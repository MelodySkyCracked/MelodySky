/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import java.util.function.Consumer;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.llI;
import org.eclipse.swt.events.lll;
import org.eclipse.swt.internal.SWTEventListener;

public interface TreeListener
extends SWTEventListener {
    public void treeCollapsed(TreeEvent var1);

    public void treeExpanded(TreeEvent var1);

    default public TreeListener treeCollapsedAdapter(Consumer consumer) {
        return new lll(this, consumer);
    }

    default public TreeListener treeExpandedAdapter(Consumer consumer) {
        return new llI(this, consumer);
    }
}

