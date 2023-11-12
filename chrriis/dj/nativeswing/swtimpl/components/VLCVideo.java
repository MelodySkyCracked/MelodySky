/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.WebBrowserObject;
import chrriis.dj.nativeswing.swtimpl.components.JVLCPlayer;

public class VLCVideo {
    private WebBrowserObject webBrowserObject;

    VLCVideo(JVLCPlayer jVLCPlayer) {
        this.webBrowserObject = jVLCPlayer.getWebBrowserObject();
    }

    public int getWidth() {
        Object object = this.webBrowserObject.getObjectProperty("video.width");
        return object == null ? -1 : ((Number)object).intValue();
    }

    public int getHeight() {
        Object object = this.webBrowserObject.getObjectProperty("video.height");
        return object == null ? -1 : ((Number)object).intValue();
    }

    public void setFullScreen(boolean bl) {
        this.webBrowserObject.setObjectProperty("video.fullscreen", bl);
    }

    public boolean isFullScreen() {
        return Boolean.TRUE.equals(this.webBrowserObject.getObjectProperty("video.fullscreen"));
    }

    public void setAspectRatio(VLCAspectRatio vLCAspectRatio) {
        String string;
        switch (vLCAspectRatio) {
            case _1x1: {
                string = "1:1";
                break;
            }
            case _4x3: {
                string = "4:3";
                break;
            }
            case _16x9: {
                string = "16:9";
                break;
            }
            case _16x10: {
                string = "16:10";
                break;
            }
            case _221x100: {
                string = "221:100";
                break;
            }
            case _5x4: {
                string = "5:4";
                break;
            }
            default: {
                throw new IllegalArgumentException("The aspect ratio value is invalid!");
            }
        }
        this.webBrowserObject.setObjectProperty("video.aspectRatio", string);
    }

    public VLCAspectRatio getAspectRatio() {
        String string = (String)this.webBrowserObject.getObjectProperty("video.aspectRatio");
        if ("1:1".equals(string)) {
            return VLCAspectRatio._1x1;
        }
        if ("4:3".equals(string)) {
            return VLCAspectRatio._4x3;
        }
        if ("16:9".equals(string)) {
            return VLCAspectRatio._16x9;
        }
        if ("16:10".equals(string)) {
            return VLCAspectRatio._16x10;
        }
        if ("221:100".equals(string)) {
            return VLCAspectRatio._221x100;
        }
        if ("5:4".equals(string)) {
            return VLCAspectRatio._5x4;
        }
        return null;
    }

    public void setSubtitleTrack(int n) {
        this.webBrowserObject.setObjectProperty("video.subtitle", n);
    }

    public int getSubtitleTrack() {
        Object object = this.webBrowserObject.getObjectProperty("video.subtitle");
        return object == null ? -1 : ((Number)object).intValue();
    }

    public void toggleFullScreen() {
        this.webBrowserObject.invokeObjectFunction("video.toggleFullscreen", new Object[0]);
    }

    public static enum VLCAspectRatio {
        _1x1,
        _4x3,
        _16x9,
        _16x10,
        _221x100,
        _5x4;

    }
}

