/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import java.util.function.Consumer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.l;
import org.eclipse.swt.events.lIllI;
import org.eclipse.swt.events.llIl;
import org.eclipse.swt.internal.SWTEventListener;

public interface MouseTrackListener
extends SWTEventListener {
    public void mouseEnter(MouseEvent var1);

    public void mouseExit(MouseEvent var1);

    public void mouseHover(MouseEvent var1);

    default public MouseTrackListener mouseEnterAdapter(Consumer consumer) {
        return new l(this, consumer);
    }

    default public MouseTrackListener mouseExitAdapter(Consumer consumer) {
        return new llIl(this, consumer);
    }

    default public MouseTrackListener mouseHoverAdapter(Consumer consumer) {
        return new lIllI(this, consumer);
    }
}

