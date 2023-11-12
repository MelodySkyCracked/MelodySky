/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.win32.internal;

import chrriis.dj.nativeswing.swtimpl.components.win32.ShellExplorerListener;
import chrriis.dj.nativeswing.swtimpl.internal.IOleNativeComponent;
import java.awt.Component;
import java.util.Map;

public interface INativeWShellExplorer
extends IOleNativeComponent {
    public Component createEmbeddableComponent(Map var1);

    public void addShellExplorerListener(ShellExplorerListener var1);

    public void removeShellExplorerListener(ShellExplorerListener var1);
}

