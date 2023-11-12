/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.utilities.internal;

import chrriis.dj.nativeswing.swtimpl.utilities.internal.INativeFileTypeLauncher;
import java.awt.Dimension;
import javax.swing.ImageIcon;

public interface INativeFileTypeLauncherStatic {
    public void load();

    public String[] getAllRegisteredExtensions();

    public INativeFileTypeLauncher getLauncher(String var1);

    public INativeFileTypeLauncher[] getLaunchers();

    public ImageIcon getDefaultIcon();

    public Dimension getIconSize();
}

