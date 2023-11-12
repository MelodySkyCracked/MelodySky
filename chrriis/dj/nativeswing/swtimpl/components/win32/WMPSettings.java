/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.win32;

import chrriis.dj.nativeswing.swtimpl.components.win32.JWMediaPlayer;
import chrriis.dj.nativeswing.swtimpl.internal.IOleNativeComponent;

public class WMPSettings {
    private IOleNativeComponent nativeComponent;

    WMPSettings(JWMediaPlayer jWMediaPlayer) {
        this.nativeComponent = (IOleNativeComponent)((Object)jWMediaPlayer.getNativeComponent());
    }

    void setErrorDialogsEnabled(boolean bl) {
        this.nativeComponent.setOleProperty(new String[]{"settings", "enableErrorDialogs"}, bl);
    }

    public void setVolume(int n) {
        if (n < 0 || n > 100) {
            throw new IllegalArgumentException("The volume must be between 0 and 100");
        }
        this.nativeComponent.setOleProperty(new String[]{"settings", "volume"}, n);
    }

    public int getVolume() {
        try {
            return (Integer)this.nativeComponent.getOleProperty(new String[]{"settings", "volume"}, new Object[0]);
        }
        catch (IllegalStateException illegalStateException) {
            throw illegalStateException;
        }
        catch (Exception exception) {
            return -1;
        }
    }

    public void setPlayCount(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("The play count must be strictly greater than 0");
        }
        this.nativeComponent.setOleProperty(new String[]{"settings", "playCount"}, n);
    }

    public int getPlayCount() {
        try {
            return (Integer)this.nativeComponent.getOleProperty(new String[]{"settings", "playCount"}, new Object[0]);
        }
        catch (IllegalStateException illegalStateException) {
            throw illegalStateException;
        }
        catch (Exception exception) {
            return -1;
        }
    }

    public void setPlaySpeedFactor(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("The rate must be strictly greater than 0!");
        }
        this.nativeComponent.setOleProperty(new String[]{"settings", "rate"}, (double)f);
    }

    public float getPlaySpeedFactor() {
        try {
            return ((Double)this.nativeComponent.getOleProperty(new String[]{"settings", "rate"}, new Object[0])).floatValue();
        }
        catch (IllegalStateException illegalStateException) {
            throw illegalStateException;
        }
        catch (Exception exception) {
            return Float.NaN;
        }
    }

    public void setStereoBalance(int n) {
        if (n < 100 || n > 100) {
            throw new IllegalArgumentException("The stereo balance must be between -100 and 100");
        }
        this.nativeComponent.setOleProperty(new String[]{"settings", "balance"}, n);
    }

    public int getStereoBalance() {
        try {
            return (Integer)this.nativeComponent.getOleProperty(new String[]{"settings", "balance"}, new Object[0]);
        }
        catch (IllegalStateException illegalStateException) {
            throw illegalStateException;
        }
        catch (Exception exception) {
            return -1;
        }
    }

    public void setAutoStart(boolean bl) {
        this.nativeComponent.setOleProperty(new String[]{"settings", "autoStart"}, bl);
    }

    public boolean isAutoStart() {
        return Boolean.TRUE.equals(this.nativeComponent.getOleProperty(new String[]{"settings", "autoStart"}, new Object[0]));
    }

    public void setMute(boolean bl) {
        this.nativeComponent.setOleProperty(new String[]{"settings", "mute"}, bl);
    }

    public boolean isMute() {
        return Boolean.TRUE.equals(this.nativeComponent.getOleProperty(new String[]{"settings", "mute"}, new Object[0]));
    }
}

