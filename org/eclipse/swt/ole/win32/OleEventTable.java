/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.ole.win32;

import org.eclipse.swt.ole.win32.OleEvent;
import org.eclipse.swt.ole.win32.OleListener;

class OleEventTable {
    int[] types;
    OleListener[] handlers;

    OleEventTable() {
    }

    void hook(int n, OleListener oleListener) {
        int n2;
        if (this.types == null) {
            this.types = new int[4];
        }
        if (this.handlers == null) {
            this.handlers = new OleListener[4];
        }
        for (n2 = 0; n2 < this.types.length; ++n2) {
            if (this.types[n2] != 0) continue;
            this.types[n2] = n;
            this.handlers[n2] = oleListener;
            return;
        }
        n2 = this.types.length;
        int[] nArray = new int[n2 + 4];
        OleListener[] oleListenerArray = new OleListener[n2 + 4];
        System.arraycopy(this.types, 0, nArray, 0, n2);
        System.arraycopy(this.handlers, 0, oleListenerArray, 0, n2);
        this.types = nArray;
        this.handlers = oleListenerArray;
        this.types[n2] = n;
        this.handlers[n2] = oleListener;
    }

    boolean hooks(int n) {
        if (this.handlers == null) {
            return false;
        }
        for (int n2 : this.types) {
            if (n2 != n) continue;
            return true;
        }
        return false;
    }

    void sendEvent(OleEvent oleEvent) {
        if (this.handlers == null) {
            return;
        }
        for (int i = 0; i < this.types.length; ++i) {
            OleListener oleListener;
            if (this.types[i] != oleEvent.type || (oleListener = this.handlers[i]) == null) continue;
            oleListener.handleEvent(oleEvent);
        }
    }

    void unhook(int n, OleListener oleListener) {
        if (this.handlers == null) {
            return;
        }
        for (int i = 0; i < this.types.length; ++i) {
            if (this.types[i] != n || this.handlers[i] != oleListener) continue;
            this.types[i] = 0;
            this.handlers[i] = null;
            return;
        }
    }

    boolean hasEntries() {
        for (int n : this.types) {
            if (n == 0) continue;
            return true;
        }
        return false;
    }
}

