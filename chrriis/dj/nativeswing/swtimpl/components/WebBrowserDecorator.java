/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowserWindow;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public abstract class WebBrowserDecorator
extends JPanel {
    public WebBrowserDecorator() {
        super(new BorderLayout());
    }

    public abstract void setStatusBarVisible(boolean var1);

    public abstract boolean isStatusBarVisible();

    public abstract void setMenuBarVisible(boolean var1);

    public abstract boolean isMenuBarVisible();

    public abstract void setButtonBarVisible(boolean var1);

    public abstract boolean isButtonBarVisible();

    public abstract void setLocationBarVisible(boolean var1);

    public abstract boolean isLocationBarVisible();

    public abstract void configureForWebBrowserWindow(JWebBrowserWindow var1);
}

