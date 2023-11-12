/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import org.eclipse.swt.accessibility.AccessibleHyperlinkEvent;
import org.eclipse.swt.internal.SWTEventListener;

public interface AccessibleHyperlinkListener
extends SWTEventListener {
    public void getAnchor(AccessibleHyperlinkEvent var1);

    public void getAnchorTarget(AccessibleHyperlinkEvent var1);

    public void getStartIndex(AccessibleHyperlinkEvent var1);

    public void getEndIndex(AccessibleHyperlinkEvent var1);
}

