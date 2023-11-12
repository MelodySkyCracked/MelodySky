/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.utilities.internal;

import javax.swing.ImageIcon;

public interface INativeFileTypeLauncher {
    public String[] getRegisteredExtensions();

    public String getName();

    public ImageIcon getIcon();

    public void launch(String var1);
}

