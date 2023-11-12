/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface CloseWindowListener
extends SWTEventListener {
    public void close(WindowEvent var1);
}

