/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

public interface JWebBrowserWindow {
    public void setTitle(String var1);

    public String getTitle();

    public void setIconImage(Image var1);

    public void setSize(Dimension var1);

    public Dimension getSize();

    public void setLocation(Point var1);

    public void dispose();

    public void setVisible(boolean var1);

    public JWebBrowser getWebBrowser();

    public void setBarsVisible(boolean var1);

    public void setStatusBarVisible(boolean var1);

    public boolean isStatusBarVisisble();

    public void setMenuBarVisible(boolean var1);

    public boolean isMenuBarVisisble();

    public void setButtonBarVisible(boolean var1);

    public boolean isButtonBarVisisble();

    public void setLocationBarVisible(boolean var1);

    public boolean isLocationBarVisisble();
}

