/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.NSOption;
import chrriis.dj.nativeswing.NSSystemProperty;
import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.NSPanelComponent;
import chrriis.dj.nativeswing.swtimpl.WebBrowserObject;
import chrriis.dj.nativeswing.swtimpl.components.DefaultFlashPlayerDecorator;
import chrriis.dj.nativeswing.swtimpl.components.FlashPlayerDecorator;
import chrriis.dj.nativeswing.swtimpl.components.FlashPlayerListener;
import chrriis.dj.nativeswing.swtimpl.components.FlashPluginOptions;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.lIllIl;
import chrriis.dj.nativeswing.swtimpl.components.llIIl;
import chrriis.dj.nativeswing.swtimpl.components.lllIl;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JFlashPlayer
extends NSPanelComponent {
    private static final String SET_CUSTOM_JAVASCRIPT_DEFINITIONS_OPTION_KEY = "Flash Player Custom Javascript definitions";
    private static FlashPlayerDecoratorFactory flashPlayerDecoratorFactory;
    private FlashPlayerDecorator flashPlayerDecorator;
    private JWebBrowser webBrowser;
    private WebBrowserObject webBrowserObject;
    private volatile String customJavascriptDefinitions;
    private volatile FlashPluginOptions options;
    private List referenceClassLoaderList = new ArrayList(1);

    public static NSOption setCustomJavascriptDefinitions(String string) {
        return new llIIl(SET_CUSTOM_JAVASCRIPT_DEFINITIONS_OPTION_KEY, string);
    }

    public static void setFlashPlayerDecoratorFactory(FlashPlayerDecoratorFactory flashPlayerDecoratorFactory) {
        JFlashPlayer.flashPlayerDecoratorFactory = flashPlayerDecoratorFactory;
    }

    FlashPlayerDecorator getFlashPlayerDecorator() {
        return this.flashPlayerDecorator;
    }

    protected FlashPlayerDecorator createFlashPlayerDecorator(Component component) {
        FlashPlayerDecorator flashPlayerDecorator;
        if (flashPlayerDecoratorFactory != null && (flashPlayerDecorator = flashPlayerDecoratorFactory.createFlashPlayerDecorator(this, component)) != null) {
            return flashPlayerDecorator;
        }
        return new DefaultFlashPlayerDecorator(this, component);
    }

    public JFlashPlayer(NSOption ... nSOptionArray) {
        Map map = NSOption.createOptionMap(nSOptionArray);
        this.customJavascriptDefinitions = (String)map.get(SET_CUSTOM_JAVASCRIPT_DEFINITIONS_OPTION_KEY);
        this.webBrowser = new JWebBrowser(nSOptionArray);
        this.initialize(this.webBrowser.getNativeComponent());
        this.webBrowserObject = new NWebBrowserObject(this);
        this.webBrowser.addWebBrowserListener(new lllIl(this));
        this.flashPlayerDecorator = this.createFlashPlayerDecorator(this.webBrowser);
        this.add((Component)this.flashPlayerDecorator, "Center");
    }

    public void load(Class clazz, String string) {
        this.load(clazz, string, null);
    }

    public void load(Class clazz, String string, FlashPluginOptions flashPluginOptions) {
        this.addReferenceClassLoader(clazz.getClassLoader());
        this.load(WebServer.getDefaultWebServer().getClassPathResourceURL(clazz.getName(), string), flashPluginOptions);
    }

    public void load(String string) {
        this.load(string, null);
    }

    public void load(String string, FlashPluginOptions flashPluginOptions) {
        if ("".equals(string)) {
            string = null;
        }
        if (flashPluginOptions == null) {
            flashPluginOptions = new FlashPluginOptions();
        }
        this.options = flashPluginOptions;
        this.webBrowserObject.load(string);
    }

    public void play() {
        if (!this.webBrowserObject.hasContent()) {
            return;
        }
        this.webBrowserObject.invokeObjectFunction("Play", new Object[0]);
    }

    public void pause() {
        if (!this.webBrowserObject.hasContent()) {
            return;
        }
        this.webBrowserObject.invokeObjectFunction("StopPlay", new Object[0]);
    }

    public void stop() {
        if (!this.webBrowserObject.hasContent()) {
            return;
        }
        this.webBrowserObject.invokeObjectFunction("Rewind", new Object[0]);
    }

    public void setVariable(String string, String string2) {
        if (!this.webBrowserObject.hasContent()) {
            return;
        }
        this.webBrowserObject.invokeObjectFunction("SetVariable", string, string2);
    }

    public Object getVariable(String string) {
        if (!this.webBrowserObject.hasContent()) {
            return null;
        }
        return this.webBrowserObject.invokeObjectFunctionWithResult("GetVariable", string);
    }

    public void invokeFlashFunction(String string, Object ... objectArray) {
        this.webBrowserObject.invokeObjectFunction(string, objectArray);
    }

    public Object invokeFlashFunctionWithResult(String string, Object ... objectArray) {
        return this.webBrowserObject.invokeObjectFunctionWithResult(string, objectArray);
    }

    public JWebBrowser getWebBrowser() {
        return this.webBrowser;
    }

    public boolean isControlBarVisible() {
        return this.flashPlayerDecorator.isControlBarVisible();
    }

    public void setControlBarVisible(boolean bl) {
        this.flashPlayerDecorator.setControlBarVisible(bl);
    }

    public void addFlashPlayerListener(FlashPlayerListener flashPlayerListener) {
        this.listenerList.add(FlashPlayerListener.class, flashPlayerListener);
    }

    public void removeFlashPlayerListener(FlashPlayerListener flashPlayerListener) {
        this.listenerList.remove(FlashPlayerListener.class, flashPlayerListener);
    }

    public FlashPlayerListener[] getFlashPlayerListeners() {
        return (FlashPlayerListener[])this.listenerList.getListeners(FlashPlayerListener.class);
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

    @Override
    public void removeNotify() {
        super.removeNotify();
        this.cleanup();
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

    static JWebBrowser access$000(JFlashPlayer jFlashPlayer) {
        return jFlashPlayer.webBrowser;
    }

    static FlashPluginOptions access$100(JFlashPlayer jFlashPlayer) {
        return jFlashPlayer.options;
    }

    static WebBrowserObject access$200(JFlashPlayer jFlashPlayer) {
        return jFlashPlayer.webBrowserObject;
    }

    static String access$300(JFlashPlayer jFlashPlayer) {
        return jFlashPlayer.customJavascriptDefinitions;
    }

    static {
        WebServer.getDefaultWebServer().addContentProvider(new lIllIl());
    }

    private static class NWebBrowserObject
    extends WebBrowserObject {
        private final JFlashPlayer flashPlayer;
        private final String LS = Utils.LINE_SEPARATOR;

        NWebBrowserObject(JFlashPlayer jFlashPlayer) {
            super(JFlashPlayer.access$000(jFlashPlayer));
            this.flashPlayer = jFlashPlayer;
        }

        @Override
        protected WebBrowserObject.ObjectHTMLConfiguration getObjectHtmlConfiguration() {
            WebBrowserObject.ObjectHTMLConfiguration objectHTMLConfiguration = new WebBrowserObject.ObjectHTMLConfiguration();
            if (JFlashPlayer.access$100(this.flashPlayer) != null) {
                String string;
                Map map = JFlashPlayer.access$100(this.flashPlayer).getHTMLParameters();
                if (!map.containsKey("base") && (string = JFlashPlayer.access$200(this.flashPlayer).getLoadedResource()) != null) {
                    int n = string.lastIndexOf(47);
                    map.put("base", string.substring(0, n + 1));
                }
                objectHTMLConfiguration.setHTMLParameters(map);
            }
            objectHTMLConfiguration.setWindowsClassID("D27CDB6E-AE6D-11cf-96B8-444553540000");
            objectHTMLConfiguration.setWindowsInstallationURL("http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0");
            objectHTMLConfiguration.setMimeType("application/x-shockwave-flash");
            objectHTMLConfiguration.setInstallationURL("http://www.adobe.com/go/getflashplayer");
            objectHTMLConfiguration.setWindowsParamName("movie");
            objectHTMLConfiguration.setParamName("src");
            return objectHTMLConfiguration;
        }

        @Override
        protected String getJavascriptDefinitions() {
            String string = JFlashPlayer.access$300(this.flashPlayer);
            return "      function " + NWebBrowserObject.getEmbeddedObjectJavascriptName() + "_DoFSCommand(command, args) {" + this.LS + "        sendCommand(command, args);" + this.LS + "      }" + (string == null ? "" : this.LS + string);
        }

        @Override
        protected String getAdditionalHeadDefinitions() {
            return "    <script language=\"VBScript\">" + this.LS + "    <!-- " + this.LS + "    Sub " + NWebBrowserObject.getEmbeddedObjectJavascriptName() + "_FSCommand(ByVal command, ByVal args)" + this.LS + "      call " + NWebBrowserObject.getEmbeddedObjectJavascriptName() + "_DoFSCommand(command, args)" + this.LS + "    end sub" + this.LS + "    //-->" + this.LS + "    </script>";
        }

        @Override
        public String getLocalFileURL(File file) {
            if (Boolean.parseBoolean(NSSystemProperty.WEBSERVER_ACTIVATEOLDRESOURCEMETHOD.get())) {
                return WebServer.getDefaultWebServer().getResourcePathURL(this.encodeSpecialCharacters(file.getParent()), this.encodeSpecialCharacters(file.getName()));
            }
            return WebServer.getDefaultWebServer().getResourcePathURL(file.getParent(), file.getName());
        }

        private String encodeSpecialCharacters(String string) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < string.length(); ++i) {
                int n = string.charAt(i);
                boolean bl = false;
                if (!(n >= 97 && n <= 122 || n >= 65 && n <= 90 || n >= 48 && n <= 57)) {
                    switch (n) {
                        case 37: 
                        case 42: 
                        case 43: 
                        case 45: 
                        case 46: 
                        case 47: 
                        case 58: 
                        case 95: {
                            break;
                        }
                        case 92: {
                            if (!Utils.IS_WINDOWS) break;
                            n = 47;
                            break;
                        }
                        default: {
                            bl = true;
                        }
                    }
                }
                if (bl) {
                    stringBuilder.append(Utils.encodeURL(String.valueOf((char)n)));
                    continue;
                }
                stringBuilder.append((char)n);
            }
            return stringBuilder.toString();
        }
    }

    public static interface FlashPlayerDecoratorFactory {
        public FlashPlayerDecorator createFlashPlayerDecorator(JFlashPlayer var1, Component var2);
    }
}

