/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.internal;

import chrriis.dj.nativeswing.swtimpl.components.JFileDialog;
import java.awt.Component;

public interface INativeFileDialog {
    public void show(Component var1);

    public String getSelectedFileName();

    public String[] getSelectedFileNames();

    public void setSelectedFileName(String var1);

    public JFileDialog.SelectionMode getSelectionMode();

    public void setSelectionMode(JFileDialog.SelectionMode var1);

    public JFileDialog.DialogType getDialogType();

    public void setDialogType(JFileDialog.DialogType var1);

    public void setConfirmedOverwrite(boolean var1);

    public boolean isConfirmedOverwrite();

    public String getParentDirectory();

    public void setParentDirectory(String var1);

    public void setExtensionFilters(String[] var1, String[] var2, int var3);

    public String[] getExtensionFilters();

    public String[] getExtensionFiltersNames();

    public int getSelectedExtensionFilterIndex();

    public void setTitle(String var1);

    public String getTitle();
}

