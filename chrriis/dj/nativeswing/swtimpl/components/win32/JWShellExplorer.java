/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.win32;

import chrriis.dj.nativeswing.NSOption;
import chrriis.dj.nativeswing.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.NSPanelComponent;
import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.components.win32.ShellExplorerListener;
import chrriis.dj.nativeswing.swtimpl.components.win32.internal.INativeWShellExplorer;
import chrriis.dj.nativeswing.swtimpl.internal.NativeCoreObjectFactory;
import java.util.ArrayList;
import java.util.List;

public class JWShellExplorer
extends NSPanelComponent {
    private INativeWShellExplorer nativeComponent;
    private List referenceClassLoaderList = new ArrayList(1);

    public JWShellExplorer(NSOption ... nSOptionArray) {
        this.nativeComponent = (INativeWShellExplorer)NativeCoreObjectFactory.create(INativeWShellExplorer.class, "chrriis.dj.nativeswing.swtimpl.components.win32.core.NativeWShellExplorer", new Class[]{JWShellExplorer.class}, new Object[]{this});
        this.initialize((NativeComponent)((Object)this.nativeComponent));
        this.add(this.nativeComponent.createEmbeddableComponent(NSOption.createOptionMap(nSOptionArray)), "Center");
    }

    public void load(String string) {
        this.nativeComponent.invokeOleFunction("Navigate", string == null ? "" : string);
    }

    public void load(Class clazz, String string) {
        this.addReferenceClassLoader(clazz.getClassLoader());
        this.load(WebServer.getDefaultWebServer().getClassPathResourceURL(clazz.getName(), string));
    }

    public void addShellExplorerListener(ShellExplorerListener shellExplorerListener) {
        this.nativeComponent.addShellExplorerListener(shellExplorerListener);
    }

    public void removeShellExplorerListener(ShellExplorerListener shellExplorerListener) {
        this.nativeComponent.removeShellExplorerListener(shellExplorerListener);
    }

    private void addReferenceClassLoader(ClassLoader classLoader) {
        if (classLoader == null || classLoader == this.getClass().getClassLoader() || this.referenceClassLoaderList.contains(classLoader)) {
            return;
        }
        this.referenceClassLoaderList.add(classLoader);
        WebServer.getDefaultWebServer().addReferenceClassLoader(classLoader);
    }

    protected void finalize() throws Throwable {
        for (ClassLoader classLoader : this.referenceClassLoaderList) {
            WebServer.getDefaultWebServer().removeReferenceClassLoader(classLoader);
        }
        this.referenceClassLoaderList.clear();
        super.finalize();
    }
}

