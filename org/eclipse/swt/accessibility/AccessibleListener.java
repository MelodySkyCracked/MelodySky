/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import java.util.function.Consumer;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.accessibility.lIIII;
import org.eclipse.swt.accessibility.ll;
import org.eclipse.swt.accessibility.llI;
import org.eclipse.swt.accessibility.lllI;
import org.eclipse.swt.internal.SWTEventListener;

public interface AccessibleListener
extends SWTEventListener {
    public void getName(AccessibleEvent var1);

    public void getHelp(AccessibleEvent var1);

    public void getKeyboardShortcut(AccessibleEvent var1);

    public void getDescription(AccessibleEvent var1);

    default public AccessibleListener getNameAdapter(Consumer consumer) {
        return new llI(this, consumer);
    }

    default public AccessibleListener getHelpAdapter(Consumer consumer) {
        return new lIIII(this, consumer);
    }

    default public AccessibleListener getKeyboardShortcutAdapter(Consumer consumer) {
        return new ll(this, consumer);
    }

    default public AccessibleListener getDescriptionAdapter(Consumer consumer) {
        return new lllI(this, consumer);
    }
}

