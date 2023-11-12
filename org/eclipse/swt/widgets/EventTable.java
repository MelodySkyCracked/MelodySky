/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.internal.ExceptionStash;
import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TypedListener;

class EventTable {
    int[] types;
    Listener[] listeners;
    int level;
    static final int GROW_SIZE = 4;

    EventTable() {
    }

    public Listener[] getListeners(int n) {
        if (this.types == null) {
            return new Listener[0];
        }
        int n2 = 0;
        int[] objectArray = this.types;
        int n3 = objectArray.length;
        for (int i = 0; i < n3; ++i) {
            int n4 = objectArray[i];
            if (n4 != n) continue;
            ++n2;
        }
        if (n2 == 0) {
            return new Listener[0];
        }
        Listener[] listenerArray = new Listener[n2];
        n2 = 0;
        for (n3 = 0; n3 < this.types.length; ++n3) {
            if (this.types[n3] != n) continue;
            listenerArray[n2++] = this.listeners[n3];
        }
        return listenerArray;
    }

    public void hook(int n, Listener listener) {
        int n2;
        if (this.types == null) {
            this.types = new int[4];
        }
        if (this.listeners == null) {
            this.listeners = new Listener[4];
        }
        int n3 = this.types.length;
        for (n2 = n3 - 1; n2 >= 0 && this.types[n2] == 0; --n2) {
        }
        if (++n2 == n3) {
            int[] nArray = new int[n3 + 4];
            System.arraycopy(this.types, 0, nArray, 0, n3);
            this.types = nArray;
            Listener[] listenerArray = new Listener[n3 + 4];
            System.arraycopy(this.listeners, 0, listenerArray, 0, n3);
            this.listeners = listenerArray;
        }
        this.types[n2] = n;
        this.listeners[n2] = listener;
    }

    public boolean hooks(int n) {
        if (this.types == null) {
            return false;
        }
        for (int n2 : this.types) {
            if (n2 != n) continue;
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendEvent(Event var1_1) {
        block26: {
            block23: {
                block24: {
                    block25: {
                        if (this.types == null) {
                            return;
                        }
                        this.level += this.level >= 0 ? 1 : -1;
                        var2_2 = new ExceptionStash();
                        var3_4 = null;
                        try {
                            var4_6 = 0;
lbl8:
                            // 2 sources

                            while (var4_6 < this.types.length) {
                                if (var1_1.type == 0) {
                                    var2_2.close();
                                    if (var2_2 == null) break block23;
                                    if (var3_4 == null) break block24;
                                    break block25;
                                }
                                ** GOTO lbl-1000
                            }
                            break block26;
                        }
                        catch (Throwable var4_8) {
                            var3_4 = var4_8;
                            throw var4_8;
                        }
                    }
                    try {
                        var2_2.close();
                    }
                    catch (Throwable var5_10) {
                        var3_4.addSuppressed(var5_10);
                    }
                    break block23;
                }
                var2_2.close();
            }
            var5_11 = this.level < 0;
            this.level -= this.level >= 0 ? 1 : -1;
            if (var5_11 && this.level == 0) {
                var6_13 = 0;
                for (var7_14 = 0; var7_14 < this.types.length; ++var7_14) {
                    if (this.types[var7_14] == 0) continue;
                    this.types[var6_13] = this.types[var7_14];
                    this.listeners[var6_13] = this.listeners[var7_14];
                    ++var6_13;
                }
                for (var7_14 = var6_13; var7_14 < this.types.length; ++var7_14) {
                    this.types[var7_14] = 0;
                    this.listeners[var7_14] = null;
                }
            }
            return;
lbl-1000:
            // 1 sources

            {
                if (this.types[var4_6] == var1_1.type && (var5_9 = this.listeners[var4_6]) != null) {
                    try {
                        var5_9.handleEvent(var1_1);
                    }
                    catch (Error | RuntimeException var6_12) {
                        var2_2.stash(var6_12);
                    }
                }
                ++var4_6;
                ** GOTO lbl8
            }
        }
        if (var2_2 != null) {
            if (var3_4 != null) {
                try {
                    var2_2.close();
                }
                catch (Throwable var4_7) {
                    var3_4.addSuppressed(var4_7);
                }
            } else {
                var2_2.close();
            }
        }
        var2_3 = this.level < 0;
        this.level -= this.level >= 0 ? 1 : -1;
        if (var2_3 && this.level == 0) {
            var3_5 = 0;
            for (var4_6 = 0; var4_6 < this.types.length; ++var4_6) {
                if (this.types[var4_6] == 0) continue;
                this.types[var3_5] = this.types[var4_6];
                this.listeners[var3_5] = this.listeners[var4_6];
                ++var3_5;
            }
            for (var4_6 = var3_5; var4_6 < this.types.length; ++var4_6) {
                this.types[var4_6] = 0;
                this.listeners[var4_6] = null;
            }
        }
    }

    public int size() {
        if (this.types == null) {
            return 0;
        }
        int n = 0;
        for (int n2 : this.types) {
            if (n2 == 0) continue;
            ++n;
        }
        return n;
    }

    void remove(int n) {
        if (this.level == 0) {
            int n2 = this.types.length - 1;
            System.arraycopy(this.types, n + 1, this.types, n, n2 - n);
            System.arraycopy(this.listeners, n + 1, this.listeners, n, n2 - n);
            n = n2;
        } else if (this.level > 0) {
            this.level = -this.level;
        }
        this.types[n] = 0;
        this.listeners[n] = null;
    }

    public void unhook(int n, Listener listener) {
        if (this.types == null) {
            return;
        }
        for (int i = 0; i < this.types.length; ++i) {
            if (this.types[i] != n || this.listeners[i] != listener) continue;
            this.remove(i);
            return;
        }
    }

    public void unhook(int n, SWTEventListener sWTEventListener) {
        if (this.types == null) {
            return;
        }
        for (int i = 0; i < this.types.length; ++i) {
            TypedListener typedListener;
            if (this.types[i] != n || !(this.listeners[i] instanceof TypedListener) || (typedListener = (TypedListener)this.listeners[i]).getEventListener() != sWTEventListener) continue;
            this.remove(i);
            return;
        }
    }
}

