/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.components.FlashPlayerDecorator;
import chrriis.dj.nativeswing.swtimpl.components.JFlashPlayer;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.l;
import chrriis.dj.nativeswing.swtimpl.components.lIIIll;
import chrriis.dj.nativeswing.swtimpl.components.lIIllll;
import chrriis.dj.nativeswing.swtimpl.components.lIlll;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ResourceBundle;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DefaultFlashPlayerDecorator
extends FlashPlayerDecorator {
    private final ResourceBundle RESOURCES;
    private JFlashPlayer flashPlayer;
    private FlashPlayerControlBar controlBar;
    private JPanel nativeComponentBorderContainerPane;

    public DefaultFlashPlayerDecorator(JFlashPlayer jFlashPlayer, Component component) {
        String string = JFlashPlayer.class.getName();
        this.RESOURCES = ResourceBundle.getBundle(string.substring(0, string.lastIndexOf(46)).replace('.', '/') + "/resource/FlashPlayer");
        this.flashPlayer = jFlashPlayer;
        this.nativeComponentBorderContainerPane = new JPanel(new BorderLayout());
        this.nativeComponentBorderContainerPane.add(component, "Center");
        this.add((Component)this.nativeComponentBorderContainerPane, "Center");
        this.setControlBarVisible(false);
    }

    protected JFlashPlayer getFlashPlayer() {
        return this.flashPlayer;
    }

    private void adjustBorder() {
        this.nativeComponentBorderContainerPane.setBorder(this.getInnerAreaBorder());
    }

    protected Border getInnerAreaBorder() {
        Border border = this != null ? BorderFactory.createBevelBorder(1) : null;
        return border;
    }

    @Override
    public void setControlBarVisible(boolean bl) {
        boolean bl2 = bl;
        if (this != null) {
            return;
        }
        if (bl) {
            this.controlBar = new FlashPlayerControlBar(this);
            this.add((Component)this.controlBar, "South");
        } else {
            this.remove(this.controlBar);
            this.controlBar.disconnect();
            this.controlBar = null;
        }
        this.revalidate();
        this.repaint();
        this.adjustBorder();
    }

    protected void addControlBarComponents(FlashPlayerControlBar flashPlayerControlBar, JComponent jComponent) {
        jComponent.add(flashPlayerControlBar.getPlayButton());
        jComponent.add(flashPlayerControlBar.getPauseButton());
        jComponent.add(flashPlayerControlBar.getStopButton());
    }

    protected void configureComponent(JComponent jComponent, FlashDecoratorComponentType flashDecoratorComponentType) {
        switch (flashDecoratorComponentType) {
            case PLAY_BUTTON: {
                ((AbstractButton)jComponent).setIcon(this.createIcon("PlayIcon"));
                ((AbstractButton)jComponent).setToolTipText(this.RESOURCES.getString("PlayText"));
                return;
            }
            case PAUSE_BUTTON: {
                ((AbstractButton)jComponent).setIcon(this.createIcon("PauseIcon"));
                ((AbstractButton)jComponent).setToolTipText(this.RESOURCES.getString("PauseText"));
                return;
            }
            case STOP_BUTTON: {
                ((AbstractButton)jComponent).setIcon(this.createIcon("StopIcon"));
                ((AbstractButton)jComponent).setToolTipText(this.RESOURCES.getString("StopText"));
                return;
            }
        }
        throw new IllegalStateException("Type not handled: " + (Object)((Object)flashDecoratorComponentType));
    }

    private Icon createIcon(String string) {
        String string2 = this.RESOURCES.getString(string);
        return string2.length() == 0 ? null : new ImageIcon(JFlashPlayer.class.getResource(string2));
    }

    static JFlashPlayer access$000(DefaultFlashPlayerDecorator defaultFlashPlayerDecorator) {
        return defaultFlashPlayerDecorator.flashPlayer;
    }

    public class FlashPlayerControlBar
    extends JPanel {
        private JButton playButton;
        private JButton pauseButton;
        private JButton stopButton;
        private WebBrowserAdapter webBrowserListener;
        final DefaultFlashPlayerDecorator this$0;

        FlashPlayerControlBar(DefaultFlashPlayerDecorator defaultFlashPlayerDecorator) {
            this.this$0 = defaultFlashPlayerDecorator;
            super(new FlowLayout(1, 4, 2));
            this.playButton = new JButton();
            defaultFlashPlayerDecorator.configureComponent(this.playButton, FlashDecoratorComponentType.PLAY_BUTTON);
            this.playButton.addActionListener(new lIIllll(this, defaultFlashPlayerDecorator));
            this.pauseButton = new JButton();
            defaultFlashPlayerDecorator.configureComponent(this.pauseButton, FlashDecoratorComponentType.PAUSE_BUTTON);
            this.pauseButton.addActionListener(new lIIIll(this, defaultFlashPlayerDecorator));
            this.stopButton = new JButton();
            defaultFlashPlayerDecorator.configureComponent(this.stopButton, FlashDecoratorComponentType.STOP_BUTTON);
            this.stopButton.addActionListener(new l(this, defaultFlashPlayerDecorator));
            this.adjustButtonState();
            this.webBrowserListener = new lIlll(this, defaultFlashPlayerDecorator);
            DefaultFlashPlayerDecorator.access$000(defaultFlashPlayerDecorator).getWebBrowser().addWebBrowserListener(this.webBrowserListener);
            defaultFlashPlayerDecorator.addControlBarComponents(this, this);
        }

        void disconnect() {
            DefaultFlashPlayerDecorator.access$000(this.this$0).getWebBrowser().removeWebBrowserListener(this.webBrowserListener);
        }

        void adjustButtonState() {
            String string = DefaultFlashPlayerDecorator.access$000(this.this$0).getWebBrowser().getResourceLocation();
            boolean bl = string != null && string.startsWith(WebServer.getDefaultWebServer().getURLPrefix());
            this.playButton.setEnabled(bl);
            this.pauseButton.setEnabled(bl);
            this.stopButton.setEnabled(bl);
        }

        public JButton getPlayButton() {
            return this.playButton;
        }

        public JButton getPauseButton() {
            return this.pauseButton;
        }

        public JButton getStopButton() {
            return this.stopButton;
        }
    }

    public static enum FlashDecoratorComponentType {
        PLAY_BUTTON,
        PAUSE_BUTTON,
        STOP_BUTTON;

    }
}

