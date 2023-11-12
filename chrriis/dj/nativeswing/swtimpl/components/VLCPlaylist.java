/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.NSSystemPropertySWT;
import chrriis.dj.nativeswing.swtimpl.WebBrowserObject;
import chrriis.dj.nativeswing.swtimpl.components.JVLCPlayer;
import chrriis.dj.nativeswing.swtimpl.components.lIIIIII;
import java.io.File;

public class VLCPlaylist {
    private JVLCPlayer vlcPlayer;
    private WebBrowserObject webBrowserObject;
    private volatile Thread playlistFixThread;

    VLCPlaylist(JVLCPlayer jVLCPlayer) {
        this.vlcPlayer = jVLCPlayer;
        this.webBrowserObject = jVLCPlayer.getWebBrowserObject();
    }

    public int getItemCount() {
        Object object = this.webBrowserObject.getObjectProperty("playlist.items.count");
        return object == null ? -1 : ((Number)object).intValue();
    }

    public boolean isPlaying() {
        return Boolean.TRUE.equals(this.webBrowserObject.getObjectProperty("playlist.isPlaying"));
    }

    public void addItem(Class clazz, String string) {
        this.addItem(clazz, string, null);
    }

    public void addItem(Class clazz, String string, String string2) {
        this.vlcPlayer.addReferenceClassLoader(clazz.getClassLoader());
        this.addItem(WebServer.getDefaultWebServer().getClassPathResourceURL(clazz.getName(), string), string2);
    }

    public void addItem(String string) {
        this.addItem(string, null);
    }

    public void addItem(String string, String string2) {
        File file;
        if (!this.webBrowserObject.hasContent()) {
            this.vlcPlayer.load();
            this.clear();
        }
        if ((file = Utils.getLocalFile(string)) != null) {
            string = this.webBrowserObject.getLocalFileURL(file);
        }
        this.webBrowserObject.invokeObjectFunction("playlist.add", string, string, string2);
    }

    public void play() {
        this.setPlaylistFixActive(false);
        this.webBrowserObject.invokeObjectFunction("playlist.play", new Object[0]);
        this.setPlaylistFixActive(true);
    }

    public void playItem(int n) {
        this.setPlaylistFixActive(false);
        this.webBrowserObject.invokeObjectFunction("playlist.playItem", n);
        this.setPlaylistFixActive(true);
    }

    public void togglePause() {
        this.webBrowserObject.invokeObjectFunction("playlist.togglePause", new Object[0]);
    }

    public void stop() {
        this.setPlaylistFixActive(false);
        this.webBrowserObject.invokeObjectFunction("playlist.stop", new Object[0]);
    }

    public void goNext() {
        this.setPlaylistFixActive(false);
        this.webBrowserObject.invokeObjectFunction("playlist.next", new Object[0]);
        this.setPlaylistFixActive(true);
    }

    public void goPrevious() {
        this.setPlaylistFixActive(false);
        this.webBrowserObject.invokeObjectFunction("playlist.prev", new Object[0]);
        this.setPlaylistFixActive(true);
    }

    public void clear() {
        this.setPlaylistFixActive(false);
        this.webBrowserObject.invokeObjectFunction("playlist.items.clear", new Object[0]);
    }

    public void removeItem(int n) {
        this.webBrowserObject.invokeObjectFunction("playlist.items.removeItem", n);
    }

    private void setPlaylistFixActive(boolean bl) {
        if (this.playlistFixThread != null == bl) {
            return;
        }
        if (bl) {
            if (!Boolean.parseBoolean(NSSystemPropertySWT.VLCPLAYER_FIXPLAYLISTAUTOPLAYNEXT.get("true"))) {
                return;
            }
            this.playlistFixThread = new lIIIIII(this, "NativeSwing - VLC Player playlist fix");
            this.playlistFixThread.setDaemon(true);
            this.playlistFixThread.start();
        } else {
            this.playlistFixThread = null;
        }
    }

    static Thread access$000(VLCPlaylist vLCPlaylist) {
        return vLCPlaylist.playlistFixThread;
    }

    static JVLCPlayer access$100(VLCPlaylist vLCPlaylist) {
        return vLCPlaylist.vlcPlayer;
    }

    static void access$200(VLCPlaylist vLCPlaylist, boolean bl) {
        vLCPlaylist.setPlaylistFixActive(bl);
    }
}

