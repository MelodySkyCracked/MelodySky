/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.internal;

import java.awt.Component;

public interface INativeDirectoryDialog {
    public void show(Component var1);

    public String getSelectedDirectory();

    public void setSelectedDirectory(String var1);

    public void setTitle(String var1);

    public String getTitle();

    public void setMessage(String var1);

    public String getMessage();
}

