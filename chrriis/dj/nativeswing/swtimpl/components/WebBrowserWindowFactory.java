/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowserWindow;
import chrriis.dj.nativeswing.swtimpl.components.lllIIl;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.RootPaneContainer;

public class WebBrowserWindowFactory {
    public static JWebBrowserWindow create(JWebBrowser jWebBrowser) {
        return WebBrowserWindowFactory.create(null, jWebBrowser);
    }

    public static JWebBrowserWindow create(Window window, JWebBrowser jWebBrowser) {
        WebBrowserWindowStrategy webBrowserWindowStrategy = new WebBrowserWindowStrategy(jWebBrowser);
        JWebBrowserWindow jWebBrowserWindow = WebBrowserWindowFactory.createWindow(webBrowserWindowStrategy, window, window != null);
        jWebBrowser.getWebBrowserDecorator().configureForWebBrowserWindow(jWebBrowserWindow);
        ((RootPaneContainer)((Object)jWebBrowserWindow)).getContentPane().add((Component)jWebBrowser, "Center");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        dimension.width = dimension.width * 80 / 100;
        dimension.height = dimension.height * 80 / 100;
        Window window2 = (Window)((Object)jWebBrowserWindow);
        window2.setSize(dimension);
        window2.setLocationByPlatform(true);
        window2.addWindowListener(new lllIIl(webBrowserWindowStrategy));
        return jWebBrowserWindow;
    }

    private static JWebBrowserWindow createWindow(WebBrowserWindowStrategy webBrowserWindowStrategy, Window window, boolean bl) {
        Window window2 = bl ? (window instanceof Frame ? new WebBrowserDialog(webBrowserWindowStrategy, (Frame)window) : new WebBrowserDialog(webBrowserWindowStrategy, (Dialog)window)) : new WebBrowserFrame(webBrowserWindowStrategy);
        return window2;
    }

    private static class WebBrowserWindowStrategy {
        private JWebBrowser webBrowser;

        public WebBrowserWindowStrategy(JWebBrowser jWebBrowser) {
            this.webBrowser = jWebBrowser;
        }

        public JWebBrowser getWebBrowser() {
            return this.webBrowser;
        }

        public void setBarsVisible(boolean bl) {
            this.webBrowser.setBarsVisible(bl);
        }

        public void setStatusBarVisible(boolean bl) {
            this.webBrowser.setStatusBarVisible(bl);
        }

        public boolean isStatusBarVisisble() {
            return this.webBrowser.isStatusBarVisible();
        }

        public void setMenuBarVisible(boolean bl) {
            this.webBrowser.setMenuBarVisible(bl);
        }

        public boolean isMenuBarVisisble() {
            return this.webBrowser.isMenuBarVisible();
        }

        public void setButtonBarVisible(boolean bl) {
            this.webBrowser.setButtonBarVisible(bl);
        }

        public boolean isButtonBarVisisble() {
            return this.webBrowser.isButtonBarVisible();
        }

        public void setLocationBarVisible(boolean bl) {
            this.webBrowser.setLocationBarVisible(bl);
        }

        public boolean isLocationBarVisisble() {
            return this.webBrowser.isLocationBarVisible();
        }

        private static void adjustInScreen(Window window) {
            GraphicsConfiguration graphicsConfiguration = window.getGraphicsConfiguration();
            Rectangle rectangle = graphicsConfiguration.getBounds();
            Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(graphicsConfiguration);
            rectangle.x += insets.left;
            rectangle.width -= insets.left + insets.right;
            rectangle.y += insets.top;
            rectangle.height -= insets.top + insets.bottom;
            Rectangle rectangle2 = window.getBounds();
            if (rectangle.x + rectangle.width < rectangle2.x + rectangle2.width) {
                rectangle2.x = rectangle.x + rectangle.width - rectangle2.width;
            }
            if (rectangle2.x < rectangle.x) {
                rectangle2.x = rectangle.x;
            }
            if (rectangle.y + rectangle.height < rectangle2.y + rectangle2.height) {
                rectangle2.y = rectangle.y + rectangle.height - rectangle2.height;
            }
            if (rectangle2.y < rectangle.y) {
                rectangle2.y = rectangle.y;
            }
            if (!window.getBounds().equals(rectangle2)) {
                window.setBounds(rectangle2);
            }
        }

        static void access$000(Window window) {
            WebBrowserWindowStrategy.adjustInScreen(window);
        }
    }

    private static class WebBrowserDialog
    extends JDialog
    implements JWebBrowserWindow {
        private WebBrowserWindowStrategy webBrowserWindowStrategy;

        public WebBrowserDialog(WebBrowserWindowStrategy webBrowserWindowStrategy, Frame frame) {
            super(frame);
            this.webBrowserWindowStrategy = webBrowserWindowStrategy;
            this.setDefaultCloseOperation(2);
        }

        public WebBrowserDialog(WebBrowserWindowStrategy webBrowserWindowStrategy, Dialog dialog) {
            super(dialog);
            this.webBrowserWindowStrategy = webBrowserWindowStrategy;
            this.setDefaultCloseOperation(2);
        }

        @Override
        public void show() {
            boolean bl = this.isLocationByPlatform();
            super.show();
            if (bl) {
                WebBrowserWindowStrategy.access$000(this);
            }
        }

        @Override
        public void setIconImage(Image image) {
            if (Utils.IS_JAVA_6_OR_GREATER) {
                super.setIconImage(image);
            }
        }

        @Override
        public JWebBrowser getWebBrowser() {
            return this.webBrowserWindowStrategy.getWebBrowser();
        }

        @Override
        public void setBarsVisible(boolean bl) {
            this.webBrowserWindowStrategy.setBarsVisible(bl);
        }

        @Override
        public void setStatusBarVisible(boolean bl) {
            this.webBrowserWindowStrategy.setStatusBarVisible(bl);
        }

        @Override
        public boolean isStatusBarVisisble() {
            return this.webBrowserWindowStrategy.isStatusBarVisisble();
        }

        @Override
        public void setMenuBarVisible(boolean bl) {
            this.webBrowserWindowStrategy.setMenuBarVisible(bl);
        }

        @Override
        public boolean isMenuBarVisisble() {
            return this.webBrowserWindowStrategy.isMenuBarVisisble();
        }

        @Override
        public void setButtonBarVisible(boolean bl) {
            this.webBrowserWindowStrategy.setButtonBarVisible(bl);
        }

        @Override
        public boolean isButtonBarVisisble() {
            return this.webBrowserWindowStrategy.isButtonBarVisisble();
        }

        @Override
        public void setLocationBarVisible(boolean bl) {
            this.webBrowserWindowStrategy.setLocationBarVisible(bl);
        }

        @Override
        public boolean isLocationBarVisisble() {
            return this.webBrowserWindowStrategy.isLocationBarVisisble();
        }
    }

    private static class WebBrowserFrame
    extends JFrame
    implements JWebBrowserWindow {
        private WebBrowserWindowStrategy webBrowserWindowStrategy;

        public WebBrowserFrame(WebBrowserWindowStrategy webBrowserWindowStrategy) {
            this.webBrowserWindowStrategy = webBrowserWindowStrategy;
            this.setDefaultCloseOperation(2);
        }

        @Override
        public void show() {
            boolean bl = this.isLocationByPlatform();
            super.show();
            if (bl) {
                WebBrowserWindowStrategy.access$000(this);
            }
        }

        @Override
        public JWebBrowser getWebBrowser() {
            return this.webBrowserWindowStrategy.getWebBrowser();
        }

        @Override
        public void setBarsVisible(boolean bl) {
            this.webBrowserWindowStrategy.setBarsVisible(bl);
        }

        @Override
        public void setStatusBarVisible(boolean bl) {
            this.webBrowserWindowStrategy.setStatusBarVisible(bl);
        }

        @Override
        public boolean isStatusBarVisisble() {
            return this.webBrowserWindowStrategy.isStatusBarVisisble();
        }

        @Override
        public void setMenuBarVisible(boolean bl) {
            this.webBrowserWindowStrategy.setMenuBarVisible(bl);
        }

        @Override
        public boolean isMenuBarVisisble() {
            return this.webBrowserWindowStrategy.isMenuBarVisisble();
        }

        @Override
        public void setButtonBarVisible(boolean bl) {
            this.webBrowserWindowStrategy.setButtonBarVisible(bl);
        }

        @Override
        public boolean isButtonBarVisisble() {
            return this.webBrowserWindowStrategy.isButtonBarVisisble();
        }

        @Override
        public void setLocationBarVisible(boolean bl) {
            this.webBrowserWindowStrategy.setLocationBarVisible(bl);
        }

        @Override
        public boolean isLocationBarVisisble() {
            return this.webBrowserWindowStrategy.isLocationBarVisisble();
        }
    }
}

