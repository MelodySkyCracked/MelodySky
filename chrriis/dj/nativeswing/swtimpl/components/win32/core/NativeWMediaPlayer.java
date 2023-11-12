/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.win32.core;

import chrriis.dj.nativeswing.swtimpl.components.win32.internal.INativeWMediaPlayer;
import chrriis.dj.nativeswing.swtimpl.core.SWTOleNativeComponent;
import java.awt.Component;
import java.util.Map;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

class NativeWMediaPlayer
extends SWTOleNativeComponent
implements INativeWMediaPlayer {
    NativeWMediaPlayer() {
    }

    protected static Control createControl(Composite composite, Object[] objectArray) {
        OleClientSite oleClientSite;
        OleFrame oleFrame = new OleFrame(composite, 0);
        try {
            oleClientSite = new OleClientSite((Composite)oleFrame, 0, "WMPlayer.OCX");
            NativeWMediaPlayer.configureOleFrame(oleClientSite, oleFrame);
        }
        catch (SWTException sWTException) {
            sWTException.printStackTrace();
            oleFrame.dispose();
            return null;
        }
        oleClientSite.doVerb(-5);
        return oleFrame;
    }

    @Override
    public Component createEmbeddableComponent(Map map) {
        return super.createEmbeddableComponent(map);
    }

    @Override
    protected void disposeNativePeer() {
        super.disposeNativePeer();
    }
}

