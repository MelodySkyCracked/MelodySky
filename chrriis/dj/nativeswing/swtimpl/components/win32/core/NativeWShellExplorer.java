/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.win32.core;

import chrriis.dj.nativeswing.swtimpl.components.win32.JWShellExplorer;
import chrriis.dj.nativeswing.swtimpl.components.win32.ShellExplorerDocumentCompleteEvent;
import chrriis.dj.nativeswing.swtimpl.components.win32.ShellExplorerListener;
import chrriis.dj.nativeswing.swtimpl.components.win32.core.I;
import chrriis.dj.nativeswing.swtimpl.components.win32.core.l;
import chrriis.dj.nativeswing.swtimpl.components.win32.internal.INativeWShellExplorer;
import chrriis.dj.nativeswing.swtimpl.core.ControlCommandMessage;
import chrriis.dj.nativeswing.swtimpl.core.SWTOleNativeComponent;
import java.awt.Component;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import javax.swing.event.EventListenerList;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleControlSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.OleListener;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

class NativeWShellExplorer
extends SWTOleNativeComponent
implements INativeWShellExplorer {
    private static String IID_DWebBrowserEvents2 = "{34A715A0-6587-11D0-924A-0020AFC7AC4D}";
    private static int DocumentComplete = 259;
    private Reference shellExplorer;

    protected static Control createControl(Composite composite, Object[] objectArray) {
        OleControlSite oleControlSite;
        OleFrame oleFrame = new OleFrame(composite, 0);
        try {
            oleControlSite = new OleControlSite((Composite)oleFrame, 0, "Shell.Explorer");
            NativeWShellExplorer.configureOleFrame(oleControlSite, oleFrame);
            OleAutomation oleAutomation = new OleAutomation(oleControlSite);
            int[] nArray = oleAutomation.getIDsOfNames(new String[]{"Application"});
            Variant variant = oleAutomation.getProperty(nArray[0]);
            OleAutomation oleAutomation2 = variant.getAutomation();
            oleFrame.addDisposeListener(new I(oleAutomation2));
            variant.dispose();
            oleAutomation.dispose();
            l l2 = new l(oleFrame);
            oleControlSite.addEventListener(oleAutomation2, IID_DWebBrowserEvents2, DocumentComplete, (OleListener)l2);
        }
        catch (SWTException sWTException) {
            sWTException.printStackTrace();
            oleFrame.dispose();
            return null;
        }
        oleControlSite.doVerb(-5);
        return oleFrame;
    }

    public NativeWShellExplorer(JWShellExplorer jWShellExplorer) {
        this.shellExplorer = new WeakReference<JWShellExplorer>(jWShellExplorer);
    }

    @Override
    public void addShellExplorerListener(ShellExplorerListener shellExplorerListener) {
        this.listenerList.add(ShellExplorerListener.class, shellExplorerListener);
    }

    @Override
    public void removeShellExplorerListener(ShellExplorerListener shellExplorerListener) {
        this.listenerList.remove(ShellExplorerListener.class, shellExplorerListener);
    }

    @Override
    public Component createEmbeddableComponent(Map map) {
        return super.createEmbeddableComponent(map);
    }

    @Override
    protected void disposeNativePeer() {
        super.disposeNativePeer();
    }

    static Reference access$000(NativeWShellExplorer nativeWShellExplorer) {
        return nativeWShellExplorer.shellExplorer;
    }

    static EventListenerList access$100(NativeWShellExplorer nativeWShellExplorer) {
        return nativeWShellExplorer.listenerList;
    }

    private static class CMJ_sendDocumentCompleteEvent
    extends ControlCommandMessage {
        private CMJ_sendDocumentCompleteEvent() {
        }

        @Override
        public Object run(Object[] objectArray) {
            JWShellExplorer jWShellExplorer;
            NativeWShellExplorer nativeWShellExplorer = (NativeWShellExplorer)this.getNativeComponent();
            JWShellExplorer jWShellExplorer2 = jWShellExplorer = nativeWShellExplorer == null ? null : (JWShellExplorer)NativeWShellExplorer.access$000(nativeWShellExplorer).get();
            if (jWShellExplorer == null) {
                return null;
            }
            Object[] objectArray2 = NativeWShellExplorer.access$100(nativeWShellExplorer).getListenerList();
            ShellExplorerDocumentCompleteEvent shellExplorerDocumentCompleteEvent = null;
            for (int i = objectArray2.length - 2; i >= 0; i -= 2) {
                if (objectArray2[i] != ShellExplorerListener.class) continue;
                if (shellExplorerDocumentCompleteEvent == null) {
                    shellExplorerDocumentCompleteEvent = new ShellExplorerDocumentCompleteEvent(jWShellExplorer, (String)objectArray[0]);
                }
                ((ShellExplorerListener)objectArray2[i + 1]).documentComplete(shellExplorerDocumentCompleteEvent);
            }
            return null;
        }

        CMJ_sendDocumentCompleteEvent(I i) {
            this();
        }
    }
}

