/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import java.util.function.Consumer;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.I;
import org.eclipse.swt.custom.lIl;
import org.eclipse.swt.custom.lIllI;
import org.eclipse.swt.custom.llIIl;
import org.eclipse.swt.custom.llIll;
import org.eclipse.swt.internal.SWTEventListener;

public interface CTabFolder2Listener
extends SWTEventListener {
    public void close(CTabFolderEvent var1);

    public void minimize(CTabFolderEvent var1);

    public void maximize(CTabFolderEvent var1);

    public void restore(CTabFolderEvent var1);

    public void showList(CTabFolderEvent var1);

    default public CTabFolder2Listener closeAdapter(Consumer consumer) {
        return new llIll(this, consumer);
    }

    default public CTabFolder2Listener minimizeAdapter(Consumer consumer) {
        return new llIIl(this, consumer);
    }

    default public CTabFolder2Listener maximizeAdapter(Consumer consumer) {
        return new lIllI(this, consumer);
    }

    default public CTabFolder2Listener restoreAdapter(Consumer consumer) {
        return new lIl(this, consumer);
    }

    default public CTabFolder2Listener showListAdapter(Consumer consumer) {
        return new I(this, consumer);
    }
}

