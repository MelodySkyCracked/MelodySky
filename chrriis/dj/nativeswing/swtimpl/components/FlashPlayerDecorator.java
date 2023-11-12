/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public abstract class FlashPlayerDecorator
extends JPanel {
    public FlashPlayerDecorator() {
        super(new BorderLayout());
    }

    public abstract void setControlBarVisible(boolean var1);

    public abstract boolean isControlBarVisible();
}

