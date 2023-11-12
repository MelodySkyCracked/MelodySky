/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.NSOption;
import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.NSPanelComponent;
import chrriis.dj.nativeswing.swtimpl.WebBrowserObject;
import chrriis.dj.nativeswing.swtimpl.components.DefaultVLCPlayerDecorator;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.VLCAudio;
import chrriis.dj.nativeswing.swtimpl.components.VLCInput;
import chrriis.dj.nativeswing.swtimpl.components.VLCPlayerDecorator;
import chrriis.dj.nativeswing.swtimpl.components.VLCPlaylist;
import chrriis.dj.nativeswing.swtimpl.components.VLCPluginOptions;
import chrriis.dj.nativeswing.swtimpl.components.VLCVideo;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JVLCPlayer
extends NSPanelComponent {
    private static VLCPlayerDecoratorFactory vlcPlayerDecoratorFactory;
    private VLCPlayerDecorator vlcPlayerDecorator;
    private JWebBrowser webBrowser;
    private WebBrowserObject webBrowserObject;
    private volatile VLCPluginOptions options;
    private VLCAudio vlcAudio;
    private VLCInput vlcInput;
    private VLCPlaylist vlcPlaylist;
    private VLCVideo vlcVideo;
    private List referenceClassLoaderList = new ArrayList(1);

    public static void setVLCPlayerDecoratorFactory(VLCPlayerDecoratorFactory vLCPlayerDecoratorFactory) {
        vlcPlayerDecoratorFactory = vLCPlayerDecoratorFactory;
    }

    VLCPlayerDecorator getVLCPlayerDecorator() {
        return this.vlcPlayerDecorator;
    }

    protected VLCPlayerDecorator createVLCPlayerDecorator(Component component) {
        VLCPlayerDecorator vLCPlayerDecorator;
        if (vlcPlayerDecoratorFactory != null && (vLCPlayerDecorator = vlcPlayerDecoratorFactory.createVLCPlayerDecorator(this, component)) != null) {
            return vLCPlayerDecorator;
        }
        return new DefaultVLCPlayerDecorator(this, component);
    }

    WebBrowserObject getWebBrowserObject() {
        return this.webBrowserObject;
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        this.cleanup();
    }

    public JVLCPlayer(NSOption ... nSOptionArray) {
        this.webBrowser = new JWebBrowser(nSOptionArray);
        this.initialize(this.webBrowser.getNativeComponent());
        this.webBrowserObject = new NWebBrowserObject(this);
        this.vlcAudio = new VLCAudio(this);
        this.vlcInput = new VLCInput(this);
        this.vlcPlaylist = new VLCPlaylist(this);
        this.vlcVideo = new VLCVideo(this);
        this.vlcPlayerDecorator = this.createVLCPlayerDecorator(this.webBrowser);
        this.add((Component)this.vlcPlayerDecorator, "Center");
    }

    public JWebBrowser getWebBrowser() {
        return this.webBrowser;
    }

    public void load() {
        this.load((VLCPluginOptions)null);
    }

    public void load(String string) {
        this.load(string, null);
    }

    public void load(VLCPluginOptions vLCPluginOptions) {
        this.load_("", vLCPluginOptions);
    }

    public void load(Class clazz, String string) {
        this.load(clazz, string, null);
    }

    public void load(Class clazz, String string, VLCPluginOptions vLCPluginOptions) {
        this.addReferenceClassLoader(clazz.getClassLoader());
        this.load(WebServer.getDefaultWebServer().getClassPathResourceURL(clazz.getName(), string), vLCPluginOptions);
    }

    public void load(String string, VLCPluginOptions vLCPluginOptions) {
        if ("".equals(string)) {
            string = null;
        }
        this.load_(string, vLCPluginOptions);
    }

    private void load_(String string, VLCPluginOptions vLCPluginOptions) {
        if (vLCPluginOptions == null) {
            vLCPluginOptions = new VLCPluginOptions();
        }
        this.options = vLCPluginOptions;
        this.webBrowserObject.load(string);
        VLCPlaylist vLCPlaylist = this.getVLCPlaylist();
        if (string != null && !"".equals(string)) {
            vLCPlaylist.stop();
            vLCPlaylist.clear();
            vLCPlaylist.addItem(string);
            vLCPlaylist.play();
        }
    }

    public boolean isControlBarVisible() {
        return this.vlcPlayerDecorator.isControlBarVisible();
    }

    public void setControlBarVisible(boolean bl) {
        this.vlcPlayerDecorator.setControlBarVisible(bl);
    }

    public VLCAudio getVLCAudio() {
        return this.vlcAudio;
    }

    public VLCInput getVLCInput() {
        return this.vlcInput;
    }

    public VLCPlaylist getVLCPlaylist() {
        return this.vlcPlaylist;
    }

    public VLCVideo getVLCVideo() {
        return this.vlcVideo;
    }

    void addReferenceClassLoader(ClassLoader classLoader) {
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

    @Override
    public void disposeNativePeer() {
        super.disposeNativePeer();
        this.cleanup();
    }

    private void cleanup() {
        if (this.isNativePeerDisposed()) {
            this.webBrowserObject.load(null);
        }
    }

    static JWebBrowser access$000(JVLCPlayer jVLCPlayer) {
        return jVLCPlayer.webBrowser;
    }

    static VLCPluginOptions access$100(JVLCPlayer jVLCPlayer) {
        return jVLCPlayer.options;
    }

    private static class NWebBrowserObject
    extends WebBrowserObject {
        private final JVLCPlayer vlcPlayer;

        public NWebBrowserObject(JVLCPlayer jVLCPlayer) {
            super(JVLCPlayer.access$000(jVLCPlayer));
            this.vlcPlayer = jVLCPlayer;
        }

        @Override
        protected WebBrowserObject.ObjectHTMLConfiguration getObjectHtmlConfiguration() {
            WebBrowserObject.ObjectHTMLConfiguration objectHTMLConfiguration = new WebBrowserObject.ObjectHTMLConfiguration();
            if (JVLCPlayer.access$100(this.vlcPlayer) != null) {
                objectHTMLConfiguration.setHTMLParameters(JVLCPlayer.access$100(this.vlcPlayer).getParameters());
            }
            objectHTMLConfiguration.setWindowsClassID("9BE31822-FDAD-461B-AD51-BE1D1C159921");
            objectHTMLConfiguration.setWindowsInstallationURL("http://downloads.videolan.org/pub/videolan/vlc/latest/win32/axvlc.cab");
            objectHTMLConfiguration.setMimeType("application/x-vlc-plugin");
            objectHTMLConfiguration.setInstallationURL("http://www.videolan.org");
            objectHTMLConfiguration.setVersion("VideoLAN.VLCPlugin.2");
            return objectHTMLConfiguration;
        }

        @Override
        public String getLocalFileURL(File file) {
            String string;
            block3: {
                String string2 = file.getAbsolutePath();
                if (string2.startsWith("\\\\")) {
                    return string2;
                }
                try {
                    string = "file://" + file.toURI().toURL().toString().substring(5);
                }
                catch (Exception exception) {
                    string = "file:///" + string2;
                    if (!Utils.IS_WINDOWS) break block3;
                    string = string.replace('\\', '/');
                }
            }
            return this.encodeSpecialCharacters(string);
        }

        private String encodeSpecialCharacters(String string) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < string.length(); ++i) {
                char c = string.charAt(i);
                boolean bl = false;
                if (!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9')) {
                    switch (c) {
                        case '%': 
                        case '*': 
                        case '+': 
                        case '-': 
                        case '.': 
                        case '/': 
                        case ':': 
                        case '_': {
                            break;
                        }
                        default: {
                            bl = true;
                        }
                    }
                }
                if (bl) {
                    stringBuilder.append(Utils.encodeURL(String.valueOf(c)));
                    continue;
                }
                stringBuilder.append(c);
            }
            return stringBuilder.toString();
        }
    }

    public static interface VLCPlayerDecoratorFactory {
        public VLCPlayerDecorator createVLCPlayerDecorator(JVLCPlayer var1, Component var2);
    }
}

