/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.core;

import chrriis.dj.nativeswing.swtimpl.components.JFileDialog;
import chrriis.dj.nativeswing.swtimpl.components.core.llll;
import chrriis.dj.nativeswing.swtimpl.components.internal.INativeFileDialog;
import chrriis.dj.nativeswing.swtimpl.core.ControlCommandMessage;
import java.awt.Component;
import java.io.Serializable;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;

class NativeFileDialog
implements INativeFileDialog {
    private Data data = new Data(null);

    NativeFileDialog() {
    }

    @Override
    public void show(Component component) {
        new llll(this).showModalDialog(component, new CMN_openFileDialog(null), this.data);
    }

    @Override
    public String getSelectedFileName() {
        String[] stringArray = this.getSelectedFileNames();
        return stringArray.length >= 1 ? stringArray[0] : null;
    }

    @Override
    public String[] getSelectedFileNames() {
        if (this.data.selectedFileNames == null) {
            return new String[0];
        }
        String[] stringArray = new String[this.data.selectedFileNames.length];
        System.arraycopy(this.data.selectedFileNames, 0, stringArray, 0, stringArray.length);
        return stringArray;
    }

    @Override
    public void setSelectedFileName(String string) {
        this.data.selectedFileNames = new String[]{string};
    }

    @Override
    public JFileDialog.SelectionMode getSelectionMode() {
        return this.data.isMulti ? JFileDialog.SelectionMode.MULTIPLE_SELECTION : JFileDialog.SelectionMode.SINGLE_SELECTION;
    }

    @Override
    public void setSelectionMode(JFileDialog.SelectionMode selectionMode) {
        this.data.isMulti = selectionMode == JFileDialog.SelectionMode.MULTIPLE_SELECTION;
    }

    @Override
    public JFileDialog.DialogType getDialogType() {
        return this.data.isSave ? JFileDialog.DialogType.SAVE_DIALOG_TYPE : JFileDialog.DialogType.OPEN_DIALOG_TYPE;
    }

    @Override
    public void setDialogType(JFileDialog.DialogType dialogType) {
        this.data.isSave = dialogType == JFileDialog.DialogType.SAVE_DIALOG_TYPE;
    }

    @Override
    public void setConfirmedOverwrite(boolean bl) {
        this.data.isConfirmedOverwrite = bl;
    }

    @Override
    public boolean isConfirmedOverwrite() {
        return this.data.isConfirmedOverwrite;
    }

    @Override
    public String getParentDirectory() {
        return this.data.parentDirectory;
    }

    @Override
    public void setParentDirectory(String string) {
        this.data.parentDirectory = string;
    }

    @Override
    public void setExtensionFilters(String[] stringArray, String[] stringArray2, int n) {
        if (stringArray2 != null && (stringArray == null || stringArray.length != stringArray2.length)) {
            throw new IllegalArgumentException("Filter descriptions can only be defined when filter extensions are defined, and the two arrays must have the same size!");
        }
        this.data.extensionFilters = stringArray;
        this.data.extensionFiltersNames = stringArray2;
        this.data.selectedExtensionFilterIndex = n;
    }

    @Override
    public String[] getExtensionFilters() {
        return this.data.extensionFilters;
    }

    @Override
    public String[] getExtensionFiltersNames() {
        return this.data.extensionFiltersNames;
    }

    @Override
    public int getSelectedExtensionFilterIndex() {
        return this.data.selectedExtensionFilterIndex;
    }

    @Override
    public void setTitle(String string) {
        this.data.title = string;
    }

    @Override
    public String getTitle() {
        return this.data.title;
    }

    static Data access$200(NativeFileDialog nativeFileDialog) {
        return nativeFileDialog.data;
    }

    static Data access$202(NativeFileDialog nativeFileDialog, Data data) {
        nativeFileDialog.data = data;
        return nativeFileDialog.data;
    }

    private static class Data
    implements Serializable {
        public String title;
        public boolean isSave;
        public boolean isMulti;
        public boolean isConfirmedOverwrite;
        public String[] selectedFileNames;
        public String[] extensionFiltersNames;
        public String[] extensionFilters;
        public int selectedExtensionFilterIndex;
        public String parentDirectory;

        private Data() {
        }

        Data(llll llll2) {
            this();
        }
    }

    private static class CMN_openFileDialog
    extends ControlCommandMessage {
        private CMN_openFileDialog() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Data data = (Data)objectArray[0];
            Control control = this.getControl();
            if (control.isDisposed()) {
                return null;
            }
            int n = 0;
            if (data.isSave) {
                n |= 0x2000;
            }
            if (data.isMulti) {
                n |= 2;
            }
            FileDialog fileDialog = new FileDialog(control.getShell(), n);
            if (data.title != null) {
                fileDialog.setText(data.title);
            }
            fileDialog.setOverwrite(data.isConfirmedOverwrite);
            if (data.parentDirectory != null) {
                fileDialog.setFilterPath(data.parentDirectory);
            }
            if (data.selectedFileNames != null && data.selectedFileNames.length == 1) {
                fileDialog.setFileName(data.selectedFileNames[0]);
            }
            if (data.extensionFilters != null) {
                fileDialog.setFilterExtensions(data.extensionFilters);
                fileDialog.setFilterNames(data.extensionFiltersNames);
                fileDialog.setFilterIndex(data.selectedExtensionFilterIndex);
            }
            fileDialog.open();
            data.selectedFileNames = fileDialog.getFileNames();
            data.selectedExtensionFilterIndex = fileDialog.getFilterIndex();
            data.parentDirectory = fileDialog.getFilterPath();
            if (data.parentDirectory.length() == 0) {
                data.parentDirectory = null;
            }
            return data;
        }

        CMN_openFileDialog(llll llll2) {
            this();
        }
    }
}

