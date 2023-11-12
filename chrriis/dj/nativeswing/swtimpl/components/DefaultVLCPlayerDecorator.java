/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.components.JVLCPlayer;
import chrriis.dj.nativeswing.swtimpl.components.VLCAudio;
import chrriis.dj.nativeswing.swtimpl.components.VLCInput;
import chrriis.dj.nativeswing.swtimpl.components.VLCPlayerDecorator;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.lIIIlIl;
import chrriis.dj.nativeswing.swtimpl.components.lIIl;
import chrriis.dj.nativeswing.swtimpl.components.lIlIll;
import chrriis.dj.nativeswing.swtimpl.components.lIllll;
import chrriis.dj.nativeswing.swtimpl.components.llIII;
import chrriis.dj.nativeswing.swtimpl.components.llIl;
import chrriis.dj.nativeswing.swtimpl.components.lllI;
import chrriis.dj.nativeswing.swtimpl.components.llllII;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ResourceBundle;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;

public class DefaultVLCPlayerDecorator
extends VLCPlayerDecorator {
    private final ResourceBundle RESOURCES;
    private int lastVolume;
    private JVLCPlayer vlcPlayer;
    private VLCPlayerControlBar controlBar;
    private JPanel nativeComponentBorderContainerPane;

    public DefaultVLCPlayerDecorator(JVLCPlayer jVLCPlayer, Component component) {
        String string = JVLCPlayer.class.getName();
        this.RESOURCES = ResourceBundle.getBundle(string.substring(0, string.lastIndexOf(46)).replace('.', '/') + "/resource/VLCPlayer");
        this.lastVolume = 50;
        this.vlcPlayer = jVLCPlayer;
        this.nativeComponentBorderContainerPane = new JPanel(new BorderLayout());
        this.nativeComponentBorderContainerPane.add(component, "Center");
        this.add((Component)this.nativeComponentBorderContainerPane, "Center");
        this.setControlBarVisible(false);
    }

    protected JVLCPlayer getFlashPlayer() {
        return this.vlcPlayer;
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
            this.controlBar = new VLCPlayerControlBar(this);
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

    protected String getTimeDisplay(int n, int n2) {
        boolean bl = n2 >= 3600000;
        return this.formatTime(n, bl) + " / " + this.formatTime(n2, bl);
    }

    private String formatTime(int n, boolean bl) {
        int n2 = n / 1000;
        int n3 = n2 / 3600;
        int n4 = n2 % 3600 / 60;
        n2 %= 60;
        StringBuilder stringBuilder = new StringBuilder();
        if (n3 != 0 || bl) {
            stringBuilder.append(n3).append(':');
        }
        stringBuilder.append(n4 < 10 ? "0" : "").append(n4).append(':');
        stringBuilder.append(n2 < 10 ? "0" : "").append(n2);
        return stringBuilder.toString();
    }

    protected void addControlBarComponents(VLCPlayerControlBar vLCPlayerControlBar, JComponent jComponent) {
        jComponent.add(vLCPlayerControlBar.getPlayButton());
        jComponent.add(vLCPlayerControlBar.getPauseButton());
        jComponent.add(vLCPlayerControlBar.getStopButton());
    }

    protected void configureComponent(JComponent jComponent, VLCDecoratorComponentType vLCDecoratorComponentType) {
        switch (vLCDecoratorComponentType) {
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
            case VOLUME_BUTTON_OFF: {
                ((AbstractButton)jComponent).setIcon(this.createIcon("VolumeOffIcon"));
                ((AbstractButton)jComponent).setToolTipText(this.RESOURCES.getString("VolumeOffText"));
                return;
            }
            case VOLUME_BUTTON_ON: {
                ((AbstractButton)jComponent).setIcon(this.createIcon("VolumeOnIcon"));
                ((AbstractButton)jComponent).setToolTipText(this.RESOURCES.getString("VolumeOnText"));
                return;
            }
        }
        throw new IllegalStateException("Type not handled: " + (Object)((Object)vLCDecoratorComponentType));
    }

    private Icon createIcon(String string) {
        String string2 = this.RESOURCES.getString(string);
        return string2.length() == 0 ? null : new ImageIcon(JVLCPlayer.class.getResource(string2));
    }

    static JVLCPlayer access$000(DefaultVLCPlayerDecorator defaultVLCPlayerDecorator) {
        return defaultVLCPlayerDecorator.vlcPlayer;
    }

    static int access$502(DefaultVLCPlayerDecorator defaultVLCPlayerDecorator, int n) {
        defaultVLCPlayerDecorator.lastVolume = n;
        return defaultVLCPlayerDecorator.lastVolume;
    }

    static int access$500(DefaultVLCPlayerDecorator defaultVLCPlayerDecorator) {
        return defaultVLCPlayerDecorator.lastVolume;
    }

    public class VLCPlayerControlBar
    extends JPanel {
        private JButton playButton;
        private JButton pauseButton;
        private JButton stopButton;
        private JSlider seekBarSlider;
        private volatile boolean isAdjustingSeekBar;
        private volatile Thread updateThread;
        private JLabel timeLabel;
        private JButton volumeButton;
        private JSlider volumeSlider;
        private boolean isAdjustingVolume;
        private WebBrowserAdapter webBrowserListener;
        private boolean isMute;
        private int volume;
        final DefaultVLCPlayerDecorator this$0;

        VLCPlayerControlBar(DefaultVLCPlayerDecorator defaultVLCPlayerDecorator) {
            this.this$0 = defaultVLCPlayerDecorator;
            super(new BorderLayout());
            this.volume = -2;
            JPanel jPanel = new JPanel(new FlowLayout(1, 4, 2));
            this.playButton = new JButton();
            defaultVLCPlayerDecorator.configureComponent(this.playButton, VLCDecoratorComponentType.PLAY_BUTTON);
            this.playButton.addActionListener(new lIllll(this, defaultVLCPlayerDecorator));
            this.pauseButton = new JButton();
            defaultVLCPlayerDecorator.configureComponent(this.pauseButton, VLCDecoratorComponentType.PAUSE_BUTTON);
            this.pauseButton.addActionListener(new lIIIlIl(this, defaultVLCPlayerDecorator));
            this.stopButton = new JButton();
            defaultVLCPlayerDecorator.configureComponent(this.stopButton, VLCDecoratorComponentType.STOP_BUTTON);
            this.stopButton.addActionListener(new lllI(this, defaultVLCPlayerDecorator));
            defaultVLCPlayerDecorator.addControlBarComponents(this, jPanel);
            this.seekBarSlider = new JSlider(0, 10000, 0);
            this.seekBarSlider.setVisible(false);
            this.seekBarSlider.addChangeListener(new llIl(this, defaultVLCPlayerDecorator));
            this.add((Component)this.seekBarSlider, "North");
            JPanel jPanel2 = new JPanel(new FlowLayout(2, 0, 2));
            this.volumeButton = new JButton();
            Insets insets = this.volumeButton.getMargin();
            insets.left = Math.min(2, insets.left);
            insets.right = Math.min(2, insets.left);
            this.volumeButton.setMargin(insets);
            this.volumeButton.addActionListener(new llIII(this, defaultVLCPlayerDecorator));
            jPanel2.add(this.volumeButton);
            this.volumeSlider = new JSlider();
            this.volumeSlider.addChangeListener(new lIlIll(this, defaultVLCPlayerDecorator));
            this.volumeSlider.setPreferredSize(new Dimension(60, this.volumeSlider.getPreferredSize().height));
            jPanel2.add(this.volumeSlider);
            this.volumeButton.setEnabled(false);
            this.volumeSlider.setEnabled(false);
            GridBagLayout gridBagLayout = new GridBagLayout();
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            JPanel jPanel3 = new JPanel(gridBagLayout);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.anchor = 17;
            gridBagConstraints.fill = 2;
            this.timeLabel = new JLabel(" ");
            this.timeLabel.setPreferredSize(new Dimension(0, this.timeLabel.getPreferredSize().height));
            gridBagLayout.setConstraints(this.timeLabel, gridBagConstraints);
            jPanel3.add(this.timeLabel);
            ++gridBagConstraints.gridx;
            gridBagConstraints.weightx = 0.0;
            gridBagConstraints.anchor = 10;
            gridBagConstraints.fill = 0;
            gridBagLayout.setConstraints(jPanel, gridBagConstraints);
            jPanel3.add(jPanel);
            jPanel3.setMinimumSize(jPanel3.getPreferredSize());
            ++gridBagConstraints.gridx;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.anchor = 13;
            gridBagConstraints.fill = 2;
            jPanel2.setPreferredSize(new Dimension(0, jPanel2.getPreferredSize().height));
            gridBagLayout.setConstraints(jPanel2, gridBagConstraints);
            jPanel3.add(jPanel2);
            this.add((Component)jPanel3, "Center");
            this.adjustButtonState();
            this.updateControlBar();
            this.webBrowserListener = new llllII(this, defaultVLCPlayerDecorator);
            DefaultVLCPlayerDecorator.access$000(defaultVLCPlayerDecorator).getWebBrowser().addWebBrowserListener(this.webBrowserListener);
        }

        void disconnect() {
            this.stopUpdateThread();
            DefaultVLCPlayerDecorator.access$000(this.this$0).getWebBrowser().removeWebBrowserListener(this.webBrowserListener);
        }

        void adjustButtonState() {
            String string = DefaultVLCPlayerDecorator.access$000(this.this$0).getWebBrowser().getResourceLocation();
            boolean bl = string != null && string.startsWith(WebServer.getDefaultWebServer().getURLPrefix());
            this.playButton.setEnabled(bl);
            this.pauseButton.setEnabled(bl);
            this.stopButton.setEnabled(bl);
            this.volumeButton.setEnabled(bl);
            this.volumeSlider.setEnabled(bl);
            if (bl) {
                this.adjustVolumePanel();
                this.startUpdateThread();
            }
        }

        void adjustVolumePanel() {
            VLCAudio vLCAudio = DefaultVLCPlayerDecorator.access$000(this.this$0).getVLCAudio();
            boolean bl = vLCAudio.isMute();
            int n = vLCAudio.getVolume();
            this.volumeButton.setEnabled(true);
            this.volumeSlider.setEnabled(!bl);
            if (bl == this.isMute && this.volume == n) {
                return;
            }
            if (bl) {
                this.this$0.configureComponent(this.volumeButton, VLCDecoratorComponentType.VOLUME_BUTTON_OFF);
            } else {
                this.this$0.configureComponent(this.volumeButton, VLCDecoratorComponentType.VOLUME_BUTTON_ON);
            }
            this.isAdjustingVolume = true;
            if (!bl) {
                this.volumeSlider.setValue(n);
                DefaultVLCPlayerDecorator.access$502(this.this$0, n);
            } else {
                this.volumeSlider.setValue(DefaultVLCPlayerDecorator.access$500(this.this$0));
            }
            this.isAdjustingVolume = false;
            this.isMute = bl;
            this.volume = n;
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

        @Override
        public void removeNotify() {
            this.stopUpdateThread();
            super.removeNotify();
        }

        @Override
        public void addNotify() {
            super.addNotify();
            this.adjustButtonState();
        }

        private void stopUpdateThread() {
            this.updateThread = null;
        }

        private void startUpdateThread() {
            if (this.updateThread != null) {
                return;
            }
            if (DefaultVLCPlayerDecorator.access$000(this.this$0).isNativePeerDisposed()) {
                return;
            }
            this.updateThread = new lIIl(this, "NativeSwing - VLC Player control bar update");
            this.updateThread.setDaemon(true);
            this.updateThread.start();
        }

        private void updateControlBar() {
            boolean bl;
            VLCInput vLCInput = DefaultVLCPlayerDecorator.access$000(this.this$0).getVLCInput();
            VLCInput.VLCMediaState vLCMediaState = vLCInput.getMediaState();
            boolean bl2 = bl = vLCMediaState == VLCInput.VLCMediaState.OPENING || vLCMediaState == VLCInput.VLCMediaState.BUFFERING || vLCMediaState == VLCInput.VLCMediaState.PLAYING || vLCMediaState == VLCInput.VLCMediaState.PAUSED || vLCMediaState == VLCInput.VLCMediaState.STOPPING;
            if (bl) {
                int n = vLCInput.getAbsolutePosition();
                int n2 = vLCInput.getDuration();
                boolean bl3 = bl = n >= 0 && n2 > 0;
                if (bl) {
                    this.isAdjustingSeekBar = true;
                    this.seekBarSlider.setValue(Math.round((float)n * 10000.0f / (float)n2));
                    this.isAdjustingSeekBar = false;
                    this.timeLabel.setText(this.this$0.getTimeDisplay(n, n2));
                }
            }
            if (!bl) {
                this.timeLabel.setText("");
            }
            this.seekBarSlider.setVisible(bl);
            this.adjustVolumePanel();
        }

        static boolean access$100(VLCPlayerControlBar vLCPlayerControlBar) {
            return vLCPlayerControlBar.isAdjustingSeekBar;
        }

        static JSlider access$200(VLCPlayerControlBar vLCPlayerControlBar) {
            return vLCPlayerControlBar.seekBarSlider;
        }

        static boolean access$300(VLCPlayerControlBar vLCPlayerControlBar) {
            return vLCPlayerControlBar.isAdjustingVolume;
        }

        static JSlider access$400(VLCPlayerControlBar vLCPlayerControlBar) {
            return vLCPlayerControlBar.volumeSlider;
        }

        static Thread access$600(VLCPlayerControlBar vLCPlayerControlBar) {
            return vLCPlayerControlBar.updateThread;
        }

        static void access$700(VLCPlayerControlBar vLCPlayerControlBar) {
            vLCPlayerControlBar.stopUpdateThread();
        }

        static void access$800(VLCPlayerControlBar vLCPlayerControlBar) {
            vLCPlayerControlBar.updateControlBar();
        }
    }

    public static enum VLCDecoratorComponentType {
        PLAY_BUTTON,
        PAUSE_BUTTON,
        STOP_BUTTON,
        VOLUME_BUTTON_ON,
        VOLUME_BUTTON_OFF;

    }
}

