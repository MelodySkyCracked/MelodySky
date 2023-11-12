/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.win32;

import chrriis.dj.nativeswing.swtimpl.components.win32.JWMediaPlayer;
import chrriis.dj.nativeswing.swtimpl.internal.IOleNativeComponent;

public class WMPControls {
    private IOleNativeComponent nativeComponent;

    WMPControls(JWMediaPlayer jWMediaPlayer) {
        this.nativeComponent = (IOleNativeComponent)((Object)jWMediaPlayer.getNativeComponent());
    }

    public boolean isPlayEnabled() {
        return Boolean.TRUE.equals(this.nativeComponent.getOleProperty(new String[]{"controls", "isAvailable"}, "Play"));
    }

    public void play() {
        this.nativeComponent.invokeOleFunction(new String[]{"controls", "Play"}, new Object[0]);
    }

    public boolean isStopEnabled() {
        return Boolean.TRUE.equals(this.nativeComponent.getOleProperty(new String[]{"controls", "isAvailable"}, "Stop"));
    }

    public void stop() {
        this.nativeComponent.invokeOleFunction(new String[]{"controls", "Stop"}, new Object[0]);
    }

    public boolean isPauseEnabled() {
        return Boolean.TRUE.equals(this.nativeComponent.getOleProperty(new String[]{"controls", "isAvailable"}, "Pause"));
    }

    public void pause() {
        this.nativeComponent.invokeOleFunction(new String[]{"controls", "Pause"}, new Object[0]);
    }

    public void setAbsolutePosition(int n) {
        this.nativeComponent.setOleProperty(new String[]{"controls", "currentPosition"}, (double)n / 1000.0);
    }

    public int getAbsolutePosition() {
        try {
            return (int)Math.round((Double)this.nativeComponent.getOleProperty(new String[]{"controls", "currentPosition"}, new Object[0]) * 1000.0);
        }
        catch (IllegalStateException illegalStateException) {
            throw illegalStateException;
        }
        catch (Exception exception) {
            return -1;
        }
    }
}

