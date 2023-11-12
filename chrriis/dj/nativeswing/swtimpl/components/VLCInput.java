/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.WebBrowserObject;
import chrriis.dj.nativeswing.swtimpl.components.JVLCPlayer;

public class VLCInput {
    private WebBrowserObject webBrowserObject;

    VLCInput(JVLCPlayer jVLCPlayer) {
        this.webBrowserObject = jVLCPlayer.getWebBrowserObject();
    }

    public int getDuration() {
        Object object = this.webBrowserObject.getObjectProperty("input.length");
        return object == null ? -1 : ((Number)object).intValue();
    }

    public float getFrameRate() {
        Object object = this.webBrowserObject.getObjectProperty("input.fps");
        return object == null ? Float.NaN : ((Number)object).floatValue();
    }

    public boolean isVideoDisplayed() {
        return Boolean.TRUE.equals(this.webBrowserObject.getObjectProperty("input.isVout"));
    }

    public void setRelativePosition(float f) {
        if (f >= 0.0f && f <= 1.0f) {
            this.webBrowserObject.setObjectProperty("input.position", Float.valueOf(f));
            return;
        }
        throw new IllegalArgumentException("The position must be between 0 and 1");
    }

    public float getRelativePosition() {
        Object object = this.webBrowserObject.getObjectProperty("input.position");
        return object == null ? Float.NaN : ((Number)object).floatValue();
    }

    public void setAbsolutePosition(int n) {
        this.webBrowserObject.setObjectProperty("input.time", n);
    }

    public int getAbsolutePosition() {
        Object object = this.webBrowserObject.getObjectProperty("input.time");
        return object == null ? -1 : ((Number)object).intValue();
    }

    public VLCMediaState getMediaState() {
        Object object = this.webBrowserObject.getObjectProperty("input.state");
        if (object == null) {
            return null;
        }
        switch (((Number)object).intValue()) {
            case 0: {
                return VLCMediaState.IDLE_CLOSE;
            }
            case 1: {
                return VLCMediaState.OPENING;
            }
            case 2: {
                return VLCMediaState.BUFFERING;
            }
            case 3: {
                return VLCMediaState.PLAYING;
            }
            case 4: {
                return VLCMediaState.PAUSED;
            }
            case 5: {
                return VLCMediaState.STOPPING;
            }
            case 6: {
                return VLCMediaState.ERROR;
            }
        }
        return null;
    }

    public void setPlaySpeedFactor(float f) {
        this.webBrowserObject.setObjectProperty("input.rate", Float.valueOf(f));
    }

    public float getPlaySpeedFactor() {
        Object object = this.webBrowserObject.getObjectProperty("input.rate");
        return object == null ? Float.NaN : ((Number)object).floatValue();
    }

    public static enum VLCMediaState {
        IDLE_CLOSE,
        OPENING,
        BUFFERING,
        PLAYING,
        PAUSED,
        STOPPING,
        ERROR;

    }
}

