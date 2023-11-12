/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import java.util.function.Consumer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.lIIIl;
import org.eclipse.swt.events.ll;
import org.eclipse.swt.internal.SWTEventListener;

public interface KeyListener
extends SWTEventListener {
    public void keyPressed(KeyEvent var1);

    public void keyReleased(KeyEvent var1);

    default public KeyListener keyPressedAdapter(Consumer consumer) {
        return new ll(this, consumer);
    }

    default public KeyListener keyReleasedAdapter(Consumer consumer) {
        return new lIIIl(this, consumer);
    }
}

