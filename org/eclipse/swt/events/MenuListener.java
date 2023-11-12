/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import java.util.function.Consumer;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.lIIII;
import org.eclipse.swt.events.llll;
import org.eclipse.swt.internal.SWTEventListener;

public interface MenuListener
extends SWTEventListener {
    public void menuHidden(MenuEvent var1);

    public void menuShown(MenuEvent var1);

    default public MenuListener menuHiddenAdapter(Consumer consumer) {
        return new llll(this, consumer);
    }

    default public MenuListener menuShownAdapter(Consumer consumer) {
        return new lIIII(this, consumer);
    }
}

