/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl;

import chrriis.dj.nativeswing.common.ObjectRegistry;
import chrriis.dj.nativeswing.swtimpl.CommandMessage;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

public abstract class NativeComponent
extends Canvas {
    private static ObjectRegistry nativeComponentRegistry;
    private static ObjectRegistry controlRegistry;

    public abstract void runInSequence(Runnable var1);

    public abstract Object runSync(CommandMessage var1, Object ... var2);

    public abstract void runAsync(CommandMessage var1, Object ... var2);

    public static NativeComponent[] getNativeComponents() {
        ArrayList<NativeComponent> arrayList = new ArrayList<NativeComponent>();
        for (int n : nativeComponentRegistry.getInstanceIDs()) {
            NativeComponent nativeComponent = (NativeComponent)nativeComponentRegistry.get(n);
            if (nativeComponent == null) continue;
            arrayList.add(nativeComponent);
        }
        return arrayList.toArray(new NativeComponent[0]);
    }

    protected static ObjectRegistry getNativeComponentRegistry() {
        return nativeComponentRegistry;
    }

    protected static ObjectRegistry getControlRegistry() {
        return controlRegistry;
    }

    protected abstract int getComponentID();

    public abstract void initializeNativePeer();

    protected abstract Object[] getNativePeerCreationParameters();

    protected abstract void disposeNativePeer();

    public abstract boolean isNativePeerDisposed();

    public abstract boolean isNativePeerInitialized();

    public abstract boolean isNativePeerValid();

    protected abstract Component createEmbeddableComponent(Map var1);

    public abstract void paintComponent(BufferedImage var1);

    public abstract void paintComponent(BufferedImage var1, Rectangle[] var2);

    public abstract void createBackBuffer();

    public abstract boolean hasBackBuffer();

    public abstract void updateBackBufferOnVisibleTranslucentAreas();

    public abstract void updateBackBuffer(Rectangle[] var1);

    public abstract void destroyBackBuffer();

    static {
        if (NativeInterface.isInProcess()) {
            nativeComponentRegistry = new ObjectRegistry();
            controlRegistry = new ObjectRegistry();
        } else if (NativeInterface.isOutProcessNativeSide()) {
            controlRegistry = new ObjectRegistry();
        } else {
            nativeComponentRegistry = new ObjectRegistry();
        }
    }
}

