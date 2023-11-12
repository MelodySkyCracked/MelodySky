/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.win32;

import chrriis.dj.nativeswing.swtimpl.components.win32.JWShellExplorer;

public class ShellExplorerDocumentCompleteEvent {
    private JWShellExplorer shellExplorer;
    private String location;

    public ShellExplorerDocumentCompleteEvent(JWShellExplorer jWShellExplorer, String string) {
        this.shellExplorer = jWShellExplorer;
        this.location = string;
    }

    public JWShellExplorer getShellExplorer() {
        return this.shellExplorer;
    }

    public String getLocation() {
        return this.location;
    }
}

