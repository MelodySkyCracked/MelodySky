/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import java.util.function.Consumer;
import org.eclipse.swt.events.I;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.lIlII;
import org.eclipse.swt.events.llII;
import org.eclipse.swt.internal.SWTEventListener;

public interface MouseListener
extends SWTEventListener {
    public void mouseDoubleClick(MouseEvent var1);

    public void mouseDown(MouseEvent var1);

    public void mouseUp(MouseEvent var1);

    default public MouseListener mouseDoubleClickAdapter(Consumer consumer) {
        return new llII(this, consumer);
    }

    default public MouseListener mouseDownAdapter(Consumer consumer) {
        return new lIlII(this, consumer);
    }

    default public MouseListener mouseUpAdapter(Consumer consumer) {
        return new I(this, consumer);
    }
}

