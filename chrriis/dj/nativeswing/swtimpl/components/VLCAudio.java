/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.WebBrowserObject;
import chrriis.dj.nativeswing.swtimpl.components.JVLCPlayer;

public class VLCAudio {
    private WebBrowserObject webBrowserObject;

    VLCAudio(JVLCPlayer jVLCPlayer) {
        this.webBrowserObject = jVLCPlayer.getWebBrowserObject();
    }

    public void setMute(boolean bl) {
        this.webBrowserObject.setObjectProperty("audio.mute", bl);
    }

    public boolean isMute() {
        return Boolean.TRUE.equals(this.webBrowserObject.getObjectProperty("audio.mute"));
    }

    public void setVolume(int n) {
        if (n < 0 || n > 100) {
            throw new IllegalArgumentException("The volume must be between 0 and 100");
        }
        this.webBrowserObject.setObjectProperty("audio.volume", Math.round((double)n * 1.99 + 1.0));
    }

    public int getVolume() {
        Object object = this.webBrowserObject.getObjectProperty("audio.volume");
        return object == null ? -1 : Math.max(0, (int)Math.round((double)(((Number)object).intValue() - 1) / 1.99));
    }

    public void setTrack(int n) {
        this.webBrowserObject.setObjectProperty("audio.track", n);
    }

    public int getTrack() {
        Object object = this.webBrowserObject.getObjectProperty("audio.track");
        return object == null ? -1 : ((Number)object).intValue();
    }

    public void setChannel(VLCChannel vLCChannel) {
        int n;
        switch (vLCChannel) {
            case STEREO: {
                n = 1;
                break;
            }
            case REVERSE_STEREO: {
                n = 2;
                break;
            }
            case LEFT: {
                n = 3;
                break;
            }
            case RIGHT: {
                n = 4;
                break;
            }
            case DOLBY: {
                n = 5;
                break;
            }
            default: {
                throw new IllegalArgumentException("The channel value is invalid!");
            }
        }
        this.webBrowserObject.setObjectProperty("audio.channel", n);
    }

    public VLCChannel getChannel() {
        Object object = this.webBrowserObject.getObjectProperty("audio.channel");
        if (object == null) {
            return null;
        }
        switch (((Number)object).intValue()) {
            case 1: {
                return VLCChannel.STEREO;
            }
            case 2: {
                return VLCChannel.REVERSE_STEREO;
            }
            case 3: {
                return VLCChannel.LEFT;
            }
            case 4: {
                return VLCChannel.RIGHT;
            }
            case 5: {
                return VLCChannel.DOLBY;
            }
        }
        return null;
    }

    public void toggleMute() {
        this.webBrowserObject.invokeObjectFunction("audio.toggleMute", new Object[0]);
    }

    public static enum VLCChannel {
        STEREO,
        REVERSE_STEREO,
        LEFT,
        RIGHT,
        DOLBY;

    }
}

