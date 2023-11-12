/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.core;

import chrriis.dj.nativeswing.swtimpl.components.core.lIII;
import chrriis.dj.nativeswing.swtimpl.components.internal.INativeDirectoryDialog;
import chrriis.dj.nativeswing.swtimpl.core.ControlCommandMessage;
import java.awt.Component;
import java.io.Serializable;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;

class NativeDirectoryDialog
implements INativeDirectoryDialog {
    private Data data = new Data(null);

    NativeDirectoryDialog() {
    }

    @Override
    public void show(Component component) {
        new lIII(this).showModalDialog(component, new CMN_openDirectoryDialog(null), this.data);
    }

    @Override
    public String getSelectedDirectory() {
        return this.data.selectedDirectory;
    }

    @Override
    public void setSelectedDirectory(String string) {
        this.data.selectedDirectory = string;
    }

    @Override
    public void setTitle(String string) {
        this.data.title = string;
    }

    @Override
    public String getTitle() {
        return this.data.title;
    }

    @Override
    public void setMessage(String string) {
        this.data.message = string;
    }

    @Override
    public String getMessage() {
        return this.data.message;
    }

    static Data access$202(NativeDirectoryDialog nativeDirectoryDialog, Data data) {
        nativeDirectoryDialog.data = data;
        return nativeDirectoryDialog.data;
    }

    private static class CMN_openDirectoryDialog
    extends ControlCommandMessage {
        private CMN_openDirectoryDialog() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Data data = (Data)objectArray[0];
            Control control = this.getControl();
            if (control.isDisposed()) {
                return data;
            }
            DirectoryDialog directoryDialog = new DirectoryDialog(control.getShell(), 0);
            if (data.title != null) {
                directoryDialog.setText(data.title);
            }
            if (data.selectedDirectory != null) {
                directoryDialog.setFilterPath(data.selectedDirectory);
            }
            if (data.message != null) {
                directoryDialog.setMessage(data.message);
            }
            data.selectedDirectory = directoryDialog.open();
            return data;
        }

        CMN_openDirectoryDialog(lIII lIII2) {
            this();
        }
    }

    private static class Data
    implements Serializable {
        public String title;
        public String message;
        public String selectedDirectory;

        private Data() {
        }

        Data(lIII lIII2) {
            this();
        }
    }
}

