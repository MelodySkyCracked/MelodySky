/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.win32;

import chrriis.dj.nativeswing.NSOption;
import chrriis.dj.nativeswing.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.NSPanelComponent;
import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.components.win32.WMPControls;
import chrriis.dj.nativeswing.swtimpl.components.win32.WMPMedia;
import chrriis.dj.nativeswing.swtimpl.components.win32.WMPSettings;
import chrriis.dj.nativeswing.swtimpl.components.win32.internal.INativeWMediaPlayer;
import chrriis.dj.nativeswing.swtimpl.internal.NativeCoreObjectFactory;
import java.util.ArrayList;
import java.util.List;

public class JWMediaPlayer
extends NSPanelComponent {
    private INativeWMediaPlayer nativeComponent;
    private WMPSettings wmpSettings;
    private WMPControls wmpControls;
    private WMPMedia wmpMedia;
    private List referenceClassLoaderList = new ArrayList(1);

    public JWMediaPlayer(NSOption ... nSOptionArray) {
        this.nativeComponent = (INativeWMediaPlayer)NativeCoreObjectFactory.create(INativeWMediaPlayer.class, "chrriis.dj.nativeswing.swtimpl.components.win32.core.NativeWMediaPlayer", new Class[0], new Object[0]);
        this.initialize((NativeComponent)((Object)this.nativeComponent));
        this.wmpSettings = new WMPSettings(this);
        this.wmpControls = new WMPControls(this);
        this.wmpMedia = new WMPMedia(this);
        this.add(this.nativeComponent.createEmbeddableComponent(NSOption.createOptionMap(nSOptionArray)), "Center");
        this.wmpSettings.setAutoStart(true);
        this.wmpSettings.setErrorDialogsEnabled(false);
        this.setControlBarVisible(true);
    }

    public WMPSettings getWMPSettings() {
        return this.wmpSettings;
    }

    public WMPControls getWMPControls() {
        return this.wmpControls;
    }

    public WMPMedia getWMPMedia() {
        return this.wmpMedia;
    }

    public void load(String string) {
        this.nativeComponent.setOleProperty("url", string == null ? "" : string);
    }

    public void load(Class clazz, String string) {
        this.addReferenceClassLoader(clazz.getClassLoader());
        this.load(WebServer.getDefaultWebServer().getClassPathResourceURL(clazz.getName(), string));
    }

    public void setControlBarVisible(boolean bl) {
        this.nativeComponent.setOleProperty("uiMode", bl ? "full" : "none");
    }

    public boolean isControlBarVisible() {
        return Boolean.TRUE.equals("full".equals(this.nativeComponent.getOleProperty("uiMode", new Object[0])));
    }

    public void setFullScreen(boolean bl) {
        this.nativeComponent.setOleProperty("fullScreen", bl);
    }

    public boolean isFullScreen() {
        return Boolean.TRUE.equals(this.nativeComponent.getOleProperty("fullScreen", new Object[0]));
    }

    public void setStretchToFit(boolean bl) {
        this.nativeComponent.setOleProperty("stretchToFit", bl);
    }

    public boolean isStretchToFit() {
        return Boolean.TRUE.equals(this.nativeComponent.getOleProperty("stretchToFit", new Object[0]));
    }

    public WMPMediaState getMediaState() {
        try {
            switch ((Integer)this.nativeComponent.getOleProperty("playState", new Object[0])) {
                case 1: {
                    return WMPMediaState.STOPPED;
                }
                case 2: {
                    return WMPMediaState.PAUSED;
                }
                case 3: {
                    return WMPMediaState.PLAYING;
                }
                case 4: {
                    return WMPMediaState.SCAN_FORWARD;
                }
                case 5: {
                    return WMPMediaState.SCAN_REVERSE;
                }
                case 6: {
                    return WMPMediaState.BUFFERING;
                }
                case 7: {
                    return WMPMediaState.WAITING;
                }
                case 8: {
                    return WMPMediaState.MEDIA_ENDED;
                }
                case 9: {
                    return WMPMediaState.TRANSITIONING;
                }
                case 10: {
                    return WMPMediaState.READY;
                }
                case 11: {
                    return WMPMediaState.RECONNECTING;
                }
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw illegalStateException;
        }
        catch (Exception exception) {
            // empty catch block
        }
        return WMPMediaState.UNDEFINED;
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

    public static enum WMPMediaState {
        UNDEFINED,
        STOPPED,
        PAUSED,
        PLAYING,
        SCAN_FORWARD,
        SCAN_REVERSE,
        BUFFERING,
        WAITING,
        MEDIA_ENDED,
        TRANSITIONING,
        READY,
        RECONNECTING;

    }
}

