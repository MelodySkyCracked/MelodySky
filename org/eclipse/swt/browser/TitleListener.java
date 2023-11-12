/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface TitleListener
extends SWTEventListener {
    public void changed(TitleEvent var1);
}

