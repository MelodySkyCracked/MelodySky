/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface CTabFolderListener
extends SWTEventListener {
    public void itemClosed(CTabFolderEvent var1);
}

