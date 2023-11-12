/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.win32;

import chrriis.dj.nativeswing.swtimpl.components.win32.JWMediaPlayer;
import chrriis.dj.nativeswing.swtimpl.internal.IOleNativeComponent;

public class WMPMedia {
    private IOleNativeComponent nativeComponent;

    WMPMedia(JWMediaPlayer jWMediaPlayer) {
        this.nativeComponent = (IOleNativeComponent)((Object)jWMediaPlayer.getNativeComponent());
    }

    public int getDuration() {
        try {
            return (int)Math.round((Double)this.nativeComponent.getOleProperty(new String[]{"currentMedia", "duration"}, new Object[0]) * 1000.0);
        }
        catch (IllegalStateException illegalStateException) {
            throw illegalStateException;
        }
        catch (Exception exception) {
            return -1;
        }
    }
}

