/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import java.util.function.Consumer;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.lII;
import org.eclipse.swt.events.lIII;
import org.eclipse.swt.events.lIIlI;
import org.eclipse.swt.events.lIIll;
import org.eclipse.swt.events.lIl;
import org.eclipse.swt.internal.SWTEventListener;

public interface ShellListener
extends SWTEventListener {
    public void shellActivated(ShellEvent var1);

    public void shellClosed(ShellEvent var1);

    public void shellDeactivated(ShellEvent var1);

    public void shellDeiconified(ShellEvent var1);

    public void shellIconified(ShellEvent var1);

    default public ShellListener shellActivatedAdapter(Consumer consumer) {
        return new lIIll(this, consumer);
    }

    default public ShellListener shellClosedAdapter(Consumer consumer) {
        return new lIIlI(this, consumer);
    }

    default public ShellListener shellDeactivatedAdapter(Consumer consumer) {
        return new lII(this, consumer);
    }

    default public ShellListener shellDeiconifiedAdapter(Consumer consumer) {
        return new lIl(this, consumer);
    }

    default public ShellListener shellIconifiedAdapter(Consumer consumer) {
        return new lIII(this, consumer);
    }
}

