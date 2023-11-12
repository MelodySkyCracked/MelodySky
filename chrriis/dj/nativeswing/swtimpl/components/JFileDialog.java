/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.internal.INativeFileDialog;
import chrriis.dj.nativeswing.swtimpl.internal.NativeCoreObjectFactory;
import java.awt.Component;

public class JFileDialog {
    private INativeFileDialog nativeFileDialog = (INativeFileDialog)NativeCoreObjectFactory.create(INativeFileDialog.class, "chrriis.dj.nativeswing.swtimpl.components.core.NativeFileDialog", new Class[0], new Object[0]);

    public void show(Component component) {
        this.nativeFileDialog.show(component);
    }

    public String getSelectedFileName() {
        return this.nativeFileDialog.getSelectedFileName();
    }

    public String[] getSelectedFileNames() {
        return this.nativeFileDialog.getSelectedFileNames();
    }

    public void setSelectedFileName(String string) {
        this.nativeFileDialog.setSelectedFileName(string);
    }

    public SelectionMode getSelectionMode() {
        return this.nativeFileDialog.getSelectionMode();
    }

    public void setSelectionMode(SelectionMode selectionMode) {
        this.nativeFileDialog.setSelectionMode(selectionMode);
    }

    public DialogType getDialogType() {
        return this.nativeFileDialog.getDialogType();
    }

    public void setDialogType(DialogType dialogType) {
        this.nativeFileDialog.setDialogType(dialogType);
    }

    public void setConfirmedOverwrite(boolean bl) {
        this.nativeFileDialog.setConfirmedOverwrite(bl);
    }

    public boolean isConfirmedOverwrite() {
        return this.nativeFileDialog.isConfirmedOverwrite();
    }

    public String getParentDirectory() {
        return this.nativeFileDialog.getParentDirectory();
    }

    public void setParentDirectory(String string) {
        this.nativeFileDialog.setParentDirectory(string);
    }

    public void setExtensionFilters(String[] stringArray, String[] stringArray2, int n) {
        this.nativeFileDialog.setExtensionFilters(stringArray, stringArray2, n);
    }

    public String[] getExtensionFilters() {
        return this.nativeFileDialog.getExtensionFilters();
    }

    public String[] getExtensionFiltersNames() {
        return this.nativeFileDialog.getExtensionFiltersNames();
    }

    public int getSelectedExtensionFilterIndex() {
        return this.nativeFileDialog.getSelectedExtensionFilterIndex();
    }

    public void setTitle(String string) {
        this.nativeFileDialog.setTitle(string);
    }

    public String getTitle() {
        return this.nativeFileDialog.getTitle();
    }

    public static enum DialogType {
        OPEN_DIALOG_TYPE,
        SAVE_DIALOG_TYPE;

    }

    public static enum SelectionMode {
        SINGLE_SELECTION,
        MULTIPLE_SELECTION;

    }
}

