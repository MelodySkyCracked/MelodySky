/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.internal.INativeDirectoryDialog;
import chrriis.dj.nativeswing.swtimpl.internal.NativeCoreObjectFactory;
import java.awt.Component;

public class JDirectoryDialog {
    private INativeDirectoryDialog nativeDirectoryDialog = (INativeDirectoryDialog)NativeCoreObjectFactory.create(INativeDirectoryDialog.class, "chrriis.dj.nativeswing.swtimpl.components.core.NativeDirectoryDialog", new Class[0], new Object[0]);

    public void show(Component component) {
        this.nativeDirectoryDialog.show(component);
    }

    public String getSelectedDirectory() {
        return this.nativeDirectoryDialog.getSelectedDirectory();
    }

    public void setSelectedDirectory(String string) {
        this.nativeDirectoryDialog.setSelectedDirectory(string);
    }

    public void setTitle(String string) {
        this.nativeDirectoryDialog.setTitle(string);
    }

    public String getTitle() {
        return this.nativeDirectoryDialog.getTitle();
    }

    public void setMessage(String string) {
        this.nativeDirectoryDialog.setMessage(string);
    }

    public String getMessage() {
        return this.nativeDirectoryDialog.getMessage();
    }
}

