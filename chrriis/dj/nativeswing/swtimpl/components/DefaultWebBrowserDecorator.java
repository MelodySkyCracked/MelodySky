/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowserWindow;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserDecorator;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.internal.INativeWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.lIIIIl;
import chrriis.dj.nativeswing.swtimpl.components.lIIIl;
import chrriis.dj.nativeswing.swtimpl.components.lIIIllI;
import chrriis.dj.nativeswing.swtimpl.components.lIIlII;
import chrriis.dj.nativeswing.swtimpl.components.lIIlIl;
import chrriis.dj.nativeswing.swtimpl.components.lIIll;
import chrriis.dj.nativeswing.swtimpl.components.lIIllI;
import chrriis.dj.nativeswing.swtimpl.components.lIIllII;
import chrriis.dj.nativeswing.swtimpl.components.lIIlll;
import chrriis.dj.nativeswing.swtimpl.components.lIIlllI;
import chrriis.dj.nativeswing.swtimpl.components.lIlIII;
import chrriis.dj.nativeswing.swtimpl.components.lIlIIII;
import chrriis.dj.nativeswing.swtimpl.components.lIlIIIl;
import chrriis.dj.nativeswing.swtimpl.components.lIlIlI;
import chrriis.dj.nativeswing.swtimpl.components.lIllI;
import chrriis.dj.nativeswing.swtimpl.components.lIllII;
import chrriis.dj.nativeswing.swtimpl.components.lIlllI;
import chrriis.dj.nativeswing.swtimpl.components.llIIII;
import chrriis.dj.nativeswing.swtimpl.components.llIIlI;
import chrriis.dj.nativeswing.swtimpl.components.llIlII;
import chrriis.dj.nativeswing.swtimpl.components.lllIll;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.Border;

public class DefaultWebBrowserDecorator
extends WebBrowserDecorator {
    private final ResourceBundle RESOURCES;
    private boolean isViewMenuVisible;
    private static final Border STATUS_BAR_BORDER = new lIIIl();
    private WebBrowserMenuBar menuBar;
    private WebBrowserButtonBar buttonBar;
    private WebBrowserLocationBar locationBar;
    private WebBrowserStatusBar statusBar;
    private JWebBrowser webBrowser;
    private INativeWebBrowser nativeWebBrowser;
    private JPanel menuToolAndLocationBarPanel;
    private JPanel nativeWebBrowserBorderContainerPane;

    private void updateNavigationButtons() {
        if (!this.nativeWebBrowser.isNativePeerDisposed() && (this.isViewMenuVisible || this != null)) {
            boolean bl;
            boolean bl2;
            boolean bl3 = bl2 = this.nativeWebBrowser.isNativePeerInitialized() ? this.nativeWebBrowser.isBackNavigationEnabled() : false;
            if (this.buttonBar != null) {
                this.buttonBar.getBackButton().setEnabled(bl2);
            }
            WebBrowserMenuBar.access$600(this.menuBar).setEnabled(bl2);
            boolean bl4 = bl = this.nativeWebBrowser.isNativePeerInitialized() ? this.nativeWebBrowser.isForwardNavigationEnabled() : false;
            if (this.buttonBar != null) {
                this.buttonBar.getForwardButton().setEnabled(bl);
            }
            WebBrowserMenuBar.access$700(this.menuBar).setEnabled(bl);
        }
    }

    protected void addButtonBarComponents(WebBrowserButtonBar webBrowserButtonBar) {
        webBrowserButtonBar.add(webBrowserButtonBar.getBackButton());
        webBrowserButtonBar.add(webBrowserButtonBar.getForwardButton());
        webBrowserButtonBar.add(webBrowserButtonBar.getReloadButton());
        webBrowserButtonBar.add(webBrowserButtonBar.getStopButton());
    }

    protected void addLocationBarComponents(WebBrowserLocationBar webBrowserLocationBar) {
        JPanel jPanel = new JPanel(new GridBagLayout());
        jPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
        jPanel.setOpaque(false);
        jPanel.add((Component)webBrowserLocationBar.getLocationField(), new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, 10, 2, new Insets(0, 0, 0, 0), 0, 0));
        webBrowserLocationBar.add(jPanel);
        webBrowserLocationBar.add(webBrowserLocationBar.getGoButton());
    }

    protected void addMenuBarComponents(WebBrowserMenuBar webBrowserMenuBar) {
        webBrowserMenuBar.add(webBrowserMenuBar.getFileMenu());
        webBrowserMenuBar.add(webBrowserMenuBar.getViewMenu());
    }

    public DefaultWebBrowserDecorator(JWebBrowser jWebBrowser, Component component) {
        String string = JWebBrowser.class.getName();
        this.RESOURCES = ResourceBundle.getBundle(string.substring(0, string.lastIndexOf(46)).replace('.', '/') + "/resource/WebBrowser");
        this.webBrowser = jWebBrowser;
        this.nativeWebBrowser = (INativeWebBrowser)((Object)jWebBrowser.getNativeComponent());
        this.menuToolAndLocationBarPanel = new JPanel(new BorderLayout());
        this.menuBar = new WebBrowserMenuBar(this);
        this.menuToolAndLocationBarPanel.add((Component)this.menuBar, "North");
        this.add((Component)this.menuToolAndLocationBarPanel, "North");
        this.nativeWebBrowserBorderContainerPane = new JPanel(new BorderLayout());
        this.nativeWebBrowserBorderContainerPane.add(component, "Center");
        this.add((Component)this.nativeWebBrowserBorderContainerPane, "Center");
        this.nativeWebBrowser.addWebBrowserListener(new NWebBrowserListener(null));
        this.adjustBorder();
        this.setButtonBarVisible(true);
        this.setLocationBarVisible(true);
        this.setStatusBarVisible(true);
    }

    protected JWebBrowser getWebBrowser() {
        return this.webBrowser;
    }

    private void adjustBorder() {
        this.nativeWebBrowserBorderContainerPane.setBorder(this.getInnerAreaBorder());
    }

    protected Border getInnerAreaBorder() {
        Border border = this.isMenuBarVisible() || this == null || this == null || this != null ? BorderFactory.createBevelBorder(1) : null;
        return border;
    }

    @Override
    public void setStatusBarVisible(boolean bl) {
        boolean bl2 = bl;
        if (this != null) {
            return;
        }
        if (bl) {
            this.statusBar = new WebBrowserStatusBar(this);
            this.webBrowser.add((Component)this.statusBar, "South");
        } else {
            this.webBrowser.remove(this.statusBar);
            this.statusBar = null;
        }
        this.webBrowser.revalidate();
        this.webBrowser.repaint();
        WebBrowserMenuBar.access$1400(this.menuBar).setSelected(bl);
        this.adjustBorder();
    }

    @Override
    public void setMenuBarVisible(boolean bl) {
        if (bl == this.isMenuBarVisible()) {
            return;
        }
        this.menuBar.setVisible(bl);
        this.adjustBorder();
    }

    @Override
    public boolean isMenuBarVisible() {
        return this.menuBar.isVisible();
    }

    @Override
    public void setButtonBarVisible(boolean bl) {
        boolean bl2 = bl;
        if (this != null) {
            return;
        }
        if (bl) {
            this.buttonBar = new WebBrowserButtonBar(this);
            this.menuToolAndLocationBarPanel.add((Component)this.buttonBar, "West");
        } else {
            this.menuToolAndLocationBarPanel.remove(this.buttonBar);
            this.buttonBar = null;
        }
        this.menuToolAndLocationBarPanel.revalidate();
        this.menuToolAndLocationBarPanel.repaint();
        WebBrowserMenuBar.access$1500(this.menuBar).setSelected(bl);
        this.adjustBorder();
        if (bl && !this.isViewMenuVisible) {
            this.updateNavigationButtons();
        }
    }

    @Override
    public void setLocationBarVisible(boolean bl) {
        boolean bl2 = bl;
        if (this != null) {
            return;
        }
        if (bl) {
            this.locationBar = new WebBrowserLocationBar(this);
            this.menuToolAndLocationBarPanel.add((Component)this.locationBar, "Center");
        } else {
            this.menuToolAndLocationBarPanel.remove(this.locationBar);
            this.locationBar = null;
        }
        this.menuToolAndLocationBarPanel.revalidate();
        this.menuToolAndLocationBarPanel.repaint();
        WebBrowserMenuBar.access$1600(this.menuBar).setSelected(bl);
        this.adjustBorder();
    }

    @Override
    public void configureForWebBrowserWindow(JWebBrowserWindow jWebBrowserWindow) {
        JMenu jMenu = WebBrowserMenuBar.access$1700(this.menuBar);
        jMenu.addSeparator();
        JMenuItem jMenuItem = new JMenuItem();
        this.configureComponent(jMenuItem, WebBrowserDecoratorComponentType.FILE_CLOSE_MENU_ITEM);
        jMenuItem.addActionListener(new lIIll(this, jWebBrowserWindow));
        jMenu.add(jMenuItem);
        this.webBrowser.addWebBrowserListener(new lllIll(this, jWebBrowserWindow));
        this.setWebBrowserWindowIcon(jWebBrowserWindow);
    }

    protected void setWebBrowserWindowTitle(JWebBrowserWindow jWebBrowserWindow, String string) {
        jWebBrowserWindow.setTitle(new MessageFormat(this.RESOURCES.getString("BrowserTitle")).format(new Object[]{string}));
    }

    protected void setWebBrowserWindowIcon(JWebBrowserWindow jWebBrowserWindow) {
        String string = this.RESOURCES.getString("BrowserIcon");
        if (string.length() > 0) {
            jWebBrowserWindow.setIconImage(new ImageIcon(JWebBrowserWindow.class.getResource(string)).getImage());
        }
    }

    protected String askLocation() {
        return JOptionPane.showInputDialog(this.webBrowser, this.RESOURCES.getString("FileOpenLocationDialogMessage"), this.RESOURCES.getString("FileOpenLocationDialogTitle"), 3);
    }

    protected void configureComponent(JComponent jComponent, WebBrowserDecoratorComponentType webBrowserDecoratorComponentType) {
        switch (webBrowserDecoratorComponentType) {
            case FILE_MENU: {
                ((AbstractButton)jComponent).setText(this.RESOURCES.getString("FileMenu"));
                return;
            }
            case FILE_NEW_WINDOW_MENU_ITEM: {
                ((AbstractButton)jComponent).setText(this.RESOURCES.getString("FileNewWindowMenu"));
                return;
            }
            case FILE_OPEN_LOCATION_MENU_ITEM: {
                ((AbstractButton)jComponent).setText(this.RESOURCES.getString("FileOpenLocationMenu"));
                return;
            }
            case FILE_OPEN_FILE_MENU_ITEM: {
                ((AbstractButton)jComponent).setText(this.RESOURCES.getString("FileOpenFileMenu"));
                return;
            }
            case FILE_CLOSE_MENU_ITEM: {
                ((AbstractButton)jComponent).setText(this.RESOURCES.getString("FileCloseMenu"));
                return;
            }
            case VIEW_MENU: {
                ((AbstractButton)jComponent).setText(this.RESOURCES.getString("ViewMenu"));
                return;
            }
            case VIEW_TOOLBARS_MENU: {
                ((AbstractButton)jComponent).setText(this.RESOURCES.getString("ViewToolbarsMenu"));
                return;
            }
            case VIEW_TOOLBARS_BUTTON_BAR_CHECKBOX_MENU_ITEM: {
                ((AbstractButton)jComponent).setText(this.RESOURCES.getString("ViewToolbarsButtonBarMenu"));
                return;
            }
            case VIEW_TOOLBARS_LOCATION_BAR_CHECKBOX_MENU_ITEM: {
                ((AbstractButton)jComponent).setText(this.RESOURCES.getString("ViewToolbarsLocationBarMenu"));
                return;
            }
            case VIEW_STATUS_BAR_CHECKBOX_MENU_ITEM: {
                ((AbstractButton)jComponent).setText(this.RESOURCES.getString("ViewStatusBarMenu"));
                return;
            }
            case VIEW_BACK_MENU_ITEM: {
                ((AbstractButton)jComponent).setText(this.RESOURCES.getString("ViewMenuBack"));
                ((AbstractButton)jComponent).setIcon(this.createIcon("ViewMenuBackIcon"));
                return;
            }
            case VIEW_FORWARD_MENU_ITEM: {
                ((AbstractButton)jComponent).setText(this.RESOURCES.getString("ViewMenuForward"));
                ((AbstractButton)jComponent).setIcon(this.createIcon("ViewMenuForwardIcon"));
                return;
            }
            case VIEW_RELOAD_MENU_ITEM: {
                ((AbstractButton)jComponent).setText(this.RESOURCES.getString("ViewMenuReload"));
                ((AbstractButton)jComponent).setIcon(this.createIcon("ViewMenuReloadIcon"));
                return;
            }
            case VIEW_STOP_MENU_ITEM: {
                ((AbstractButton)jComponent).setText(this.RESOURCES.getString("ViewMenuStop"));
                ((AbstractButton)jComponent).setIcon(this.createIcon("ViewMenuStopIcon"));
                return;
            }
            case BACK_BUTTON: {
                ((AbstractButton)jComponent).setIcon(this.createIcon("BackIcon"));
                ((AbstractButton)jComponent).setToolTipText(this.RESOURCES.getString("BackText"));
                return;
            }
            case FORWARD_BUTTON: {
                ((AbstractButton)jComponent).setIcon(this.createIcon("ForwardIcon"));
                ((AbstractButton)jComponent).setToolTipText(this.RESOURCES.getString("ForwardText"));
                return;
            }
            case RELOAD_BUTTON: {
                ((AbstractButton)jComponent).setIcon(this.createIcon("ReloadIcon"));
                ((AbstractButton)jComponent).setToolTipText(this.RESOURCES.getString("ReloadText"));
                return;
            }
            case STOP_BUTTON: {
                ((AbstractButton)jComponent).setIcon(this.createIcon("StopIcon"));
                ((AbstractButton)jComponent).setToolTipText(this.RESOURCES.getString("StopText"));
                return;
            }
            case GO_BUTTON: {
                ((AbstractButton)jComponent).setIcon(this.createIcon("GoIcon"));
                ((AbstractButton)jComponent).setToolTipText(this.RESOURCES.getString("GoText"));
                return;
            }
            case STATUS_LABEL: {
                return;
            }
        }
        throw new IllegalStateException("Type not handled: " + (Object)((Object)webBrowserDecoratorComponentType));
    }

    private Icon createIcon(String string) {
        String string2 = this.RESOURCES.getString(string);
        return string2.length() == 0 ? null : new ImageIcon(JWebBrowser.class.getResource(string2));
    }

    static WebBrowserLocationBar access$000(DefaultWebBrowserDecorator defaultWebBrowserDecorator) {
        return defaultWebBrowserDecorator.locationBar;
    }

    static void access$100(DefaultWebBrowserDecorator defaultWebBrowserDecorator) {
        defaultWebBrowserDecorator.updateNavigationButtons();
    }

    static WebBrowserStatusBar access$200(DefaultWebBrowserDecorator defaultWebBrowserDecorator) {
        return defaultWebBrowserDecorator.statusBar;
    }

    static WebBrowserButtonBar access$300(DefaultWebBrowserDecorator defaultWebBrowserDecorator) {
        return defaultWebBrowserDecorator.buttonBar;
    }

    static WebBrowserMenuBar access$400(DefaultWebBrowserDecorator defaultWebBrowserDecorator) {
        return defaultWebBrowserDecorator.menuBar;
    }

    static JWebBrowser access$800(DefaultWebBrowserDecorator defaultWebBrowserDecorator) {
        return defaultWebBrowserDecorator.webBrowser;
    }

    static INativeWebBrowser access$900(DefaultWebBrowserDecorator defaultWebBrowserDecorator) {
        return defaultWebBrowserDecorator.nativeWebBrowser;
    }

    static boolean access$1002(DefaultWebBrowserDecorator defaultWebBrowserDecorator, boolean bl) {
        defaultWebBrowserDecorator.isViewMenuVisible = bl;
        return defaultWebBrowserDecorator.isViewMenuVisible;
    }

    static Border access$1200() {
        return STATUS_BAR_BORDER;
    }

    private class WebBrowserStatusBar
    extends JPanel {
        private JLabel statusLabel;
        private JProgressBar progressBar;
        final DefaultWebBrowserDecorator this$0;

        public WebBrowserStatusBar(DefaultWebBrowserDecorator defaultWebBrowserDecorator) {
            this.this$0 = defaultWebBrowserDecorator;
            super(new BorderLayout());
            this.setBorder(BorderFactory.createCompoundBorder(DefaultWebBrowserDecorator.access$1200(), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
            this.statusLabel = new JLabel();
            defaultWebBrowserDecorator.configureComponent(this.statusLabel, WebBrowserDecoratorComponentType.STATUS_LABEL);
            this.updateStatus();
            this.add((Component)this.statusLabel, "Center");
            this.progressBar = new llIIlI(this, defaultWebBrowserDecorator);
            this.updateProgressValue();
            this.add((Component)this.progressBar, "East");
        }

        public void updateProgressValue() {
            int n = DefaultWebBrowserDecorator.access$900(this.this$0).isNativePeerInitialized() ? DefaultWebBrowserDecorator.access$900(this.this$0).getLoadingProgress() : 100;
            this.progressBar.setValue(n);
            this.progressBar.setVisible(n < 100);
        }

        public void updateStatus() {
            String string = DefaultWebBrowserDecorator.access$900(this.this$0).isNativePeerInitialized() ? DefaultWebBrowserDecorator.access$900(this.this$0).getStatusText() : "";
            this.statusLabel.setText(string.length() == 0 ? " " : string);
        }
    }

    public class WebBrowserLocationBar
    extends JToolBar {
        private JTextField locationField;
        private JButton goButton;
        final DefaultWebBrowserDecorator this$0;

        WebBrowserLocationBar(DefaultWebBrowserDecorator defaultWebBrowserDecorator) {
            this.this$0 = defaultWebBrowserDecorator;
            this.setLayout(new BoxLayout(this, 2));
            this.setFloatable(false);
            this.locationField = new JTextField();
            this.locationField.addKeyListener(new lIIIllI(this, defaultWebBrowserDecorator));
            llIlII llIlII2 = new llIlII(this, defaultWebBrowserDecorator);
            this.locationField.addActionListener(llIlII2);
            this.updateLocation();
            this.goButton = new JButton();
            defaultWebBrowserDecorator.configureComponent(this.goButton, WebBrowserDecoratorComponentType.GO_BUTTON);
            this.goButton.addActionListener(llIlII2);
            defaultWebBrowserDecorator.addLocationBarComponents(this);
        }

        public JTextField getLocationField() {
            return this.locationField;
        }

        public JButton getGoButton() {
            return this.goButton;
        }

        void updateLocation(String string) {
            this.locationField.setText(string);
        }

        void updateLocation() {
            this.locationField.setText(DefaultWebBrowserDecorator.access$900(this.this$0).isNativePeerInitialized() && !DefaultWebBrowserDecorator.access$900(this.this$0).isNativePeerDisposed() ? DefaultWebBrowserDecorator.access$900(this.this$0).getResourceLocation() : "");
        }

        static JTextField access$1100(WebBrowserLocationBar webBrowserLocationBar) {
            return webBrowserLocationBar.locationField;
        }
    }

    public class WebBrowserButtonBar
    extends JToolBar {
        private JButton backButton;
        private JButton forwardButton;
        private JButton reloadButton;
        private JButton stopButton;
        final DefaultWebBrowserDecorator this$0;

        WebBrowserButtonBar(DefaultWebBrowserDecorator defaultWebBrowserDecorator) {
            this.this$0 = defaultWebBrowserDecorator;
            this.setFloatable(false);
            this.backButton = new JButton();
            defaultWebBrowserDecorator.configureComponent(this.backButton, WebBrowserDecoratorComponentType.BACK_BUTTON);
            this.backButton.setEnabled(WebBrowserMenuBar.access$600(DefaultWebBrowserDecorator.access$400(defaultWebBrowserDecorator)).isEnabled());
            this.backButton.addActionListener(new lIIllI(this, defaultWebBrowserDecorator));
            this.forwardButton = new JButton();
            defaultWebBrowserDecorator.configureComponent(this.forwardButton, WebBrowserDecoratorComponentType.FORWARD_BUTTON);
            this.forwardButton.setEnabled(WebBrowserMenuBar.access$700(DefaultWebBrowserDecorator.access$400(defaultWebBrowserDecorator)).isEnabled());
            this.forwardButton.addActionListener(new lIIllII(this, defaultWebBrowserDecorator));
            this.reloadButton = new JButton();
            defaultWebBrowserDecorator.configureComponent(this.reloadButton, WebBrowserDecoratorComponentType.RELOAD_BUTTON);
            this.reloadButton.addActionListener(new lIIlIl(this, defaultWebBrowserDecorator));
            this.stopButton = new JButton();
            defaultWebBrowserDecorator.configureComponent(this.stopButton, WebBrowserDecoratorComponentType.STOP_BUTTON);
            this.stopButton.setEnabled(WebBrowserMenuBar.access$500(DefaultWebBrowserDecorator.access$400(defaultWebBrowserDecorator)).isEnabled());
            this.stopButton.addActionListener(new lIlllI(this, defaultWebBrowserDecorator));
            defaultWebBrowserDecorator.addButtonBarComponents(this);
            this.add(Box.createHorizontalStrut(2));
        }

        public JButton getBackButton() {
            return this.backButton;
        }

        public JButton getForwardButton() {
            return this.forwardButton;
        }

        public JButton getReloadButton() {
            return this.reloadButton;
        }

        public JButton getStopButton() {
            return this.stopButton;
        }
    }

    public class WebBrowserMenuBar
    extends JMenuBar {
        private JMenu fileMenu;
        private JMenu viewMenu;
        private JCheckBoxMenuItem buttonBarCheckBoxMenuItem;
        private JCheckBoxMenuItem locationBarCheckBoxMenuItem;
        private JCheckBoxMenuItem statusBarCheckBoxMenuItem;
        private JMenuItem backMenuItem;
        private JMenuItem forwardMenuItem;
        private JMenuItem reloadMenuItem;
        private JMenuItem stopMenuItem;
        final DefaultWebBrowserDecorator this$0;

        WebBrowserMenuBar(DefaultWebBrowserDecorator defaultWebBrowserDecorator) {
            this.this$0 = defaultWebBrowserDecorator;
            this.fileMenu = new JMenu();
            defaultWebBrowserDecorator.configureComponent(this.fileMenu, WebBrowserDecoratorComponentType.FILE_MENU);
            JMenuItem jMenuItem = new JMenuItem();
            defaultWebBrowserDecorator.configureComponent(jMenuItem, WebBrowserDecoratorComponentType.FILE_NEW_WINDOW_MENU_ITEM);
            jMenuItem.addActionListener(new lIIIIl(this, defaultWebBrowserDecorator));
            this.fileMenu.add(jMenuItem);
            JMenuItem jMenuItem2 = new JMenuItem();
            defaultWebBrowserDecorator.configureComponent(jMenuItem2, WebBrowserDecoratorComponentType.FILE_OPEN_LOCATION_MENU_ITEM);
            jMenuItem2.addActionListener(new lIlIIII(this, defaultWebBrowserDecorator));
            this.fileMenu.add(jMenuItem2);
            JMenuItem jMenuItem3 = new JMenuItem();
            defaultWebBrowserDecorator.configureComponent(jMenuItem3, WebBrowserDecoratorComponentType.FILE_OPEN_FILE_MENU_ITEM);
            jMenuItem3.addActionListener(new lIllII(this, defaultWebBrowserDecorator));
            this.fileMenu.add(jMenuItem3);
            this.viewMenu = new JMenu();
            defaultWebBrowserDecorator.configureComponent(this.viewMenu, WebBrowserDecoratorComponentType.VIEW_MENU);
            JMenu jMenu = new JMenu();
            defaultWebBrowserDecorator.configureComponent(jMenu, WebBrowserDecoratorComponentType.VIEW_TOOLBARS_MENU);
            this.buttonBarCheckBoxMenuItem = new JCheckBoxMenuItem();
            defaultWebBrowserDecorator.configureComponent(this.buttonBarCheckBoxMenuItem, WebBrowserDecoratorComponentType.VIEW_TOOLBARS_BUTTON_BAR_CHECKBOX_MENU_ITEM);
            this.buttonBarCheckBoxMenuItem.setSelected(defaultWebBrowserDecorator.isButtonBarVisible());
            this.buttonBarCheckBoxMenuItem.addItemListener(new lIlIIIl(this, defaultWebBrowserDecorator));
            jMenu.add(this.buttonBarCheckBoxMenuItem);
            this.locationBarCheckBoxMenuItem = new JCheckBoxMenuItem();
            defaultWebBrowserDecorator.configureComponent(this.locationBarCheckBoxMenuItem, WebBrowserDecoratorComponentType.VIEW_TOOLBARS_LOCATION_BAR_CHECKBOX_MENU_ITEM);
            this.locationBarCheckBoxMenuItem.setSelected(defaultWebBrowserDecorator.isLocationBarVisible());
            this.locationBarCheckBoxMenuItem.addItemListener(new lIIlll(this, defaultWebBrowserDecorator));
            jMenu.add(this.locationBarCheckBoxMenuItem);
            this.viewMenu.add(jMenu);
            this.statusBarCheckBoxMenuItem = new JCheckBoxMenuItem();
            defaultWebBrowserDecorator.configureComponent(this.statusBarCheckBoxMenuItem, WebBrowserDecoratorComponentType.VIEW_STATUS_BAR_CHECKBOX_MENU_ITEM);
            this.statusBarCheckBoxMenuItem.setSelected(defaultWebBrowserDecorator.isStatusBarVisible());
            this.statusBarCheckBoxMenuItem.addItemListener(new lIIlllI(this, defaultWebBrowserDecorator));
            this.viewMenu.add(this.statusBarCheckBoxMenuItem);
            this.viewMenu.addSeparator();
            this.backMenuItem = new JMenuItem();
            defaultWebBrowserDecorator.configureComponent(this.backMenuItem, WebBrowserDecoratorComponentType.VIEW_BACK_MENU_ITEM);
            this.backMenuItem.addActionListener(new lIIlII(this, defaultWebBrowserDecorator));
            this.backMenuItem.setEnabled(false);
            this.viewMenu.add(this.backMenuItem);
            this.forwardMenuItem = new JMenuItem();
            defaultWebBrowserDecorator.configureComponent(this.forwardMenuItem, WebBrowserDecoratorComponentType.VIEW_FORWARD_MENU_ITEM);
            this.forwardMenuItem.addActionListener(new lIlIlI(this, defaultWebBrowserDecorator));
            this.forwardMenuItem.setEnabled(false);
            this.viewMenu.add(this.forwardMenuItem);
            this.reloadMenuItem = new JMenuItem();
            defaultWebBrowserDecorator.configureComponent(this.reloadMenuItem, WebBrowserDecoratorComponentType.VIEW_RELOAD_MENU_ITEM);
            this.reloadMenuItem.addActionListener(new lIlIII(this, defaultWebBrowserDecorator));
            this.viewMenu.add(this.reloadMenuItem);
            this.stopMenuItem = new JMenuItem();
            defaultWebBrowserDecorator.configureComponent(this.stopMenuItem, WebBrowserDecoratorComponentType.VIEW_STOP_MENU_ITEM);
            this.stopMenuItem.addActionListener(new lIllI(this, defaultWebBrowserDecorator));
            this.stopMenuItem.setEnabled(false);
            this.viewMenu.add(this.stopMenuItem);
            this.viewMenu.getPopupMenu().addPopupMenuListener(new llIIII(this, defaultWebBrowserDecorator));
            defaultWebBrowserDecorator.addMenuBarComponents(this);
        }

        public JMenu getFileMenu() {
            return this.fileMenu;
        }

        public JMenu getViewMenu() {
            return this.viewMenu;
        }

        static JMenuItem access$500(WebBrowserMenuBar webBrowserMenuBar) {
            return webBrowserMenuBar.stopMenuItem;
        }

        static JMenuItem access$600(WebBrowserMenuBar webBrowserMenuBar) {
            return webBrowserMenuBar.backMenuItem;
        }

        static JMenuItem access$700(WebBrowserMenuBar webBrowserMenuBar) {
            return webBrowserMenuBar.forwardMenuItem;
        }

        static JCheckBoxMenuItem access$1400(WebBrowserMenuBar webBrowserMenuBar) {
            return webBrowserMenuBar.statusBarCheckBoxMenuItem;
        }

        static JCheckBoxMenuItem access$1500(WebBrowserMenuBar webBrowserMenuBar) {
            return webBrowserMenuBar.buttonBarCheckBoxMenuItem;
        }

        static JCheckBoxMenuItem access$1600(WebBrowserMenuBar webBrowserMenuBar) {
            return webBrowserMenuBar.locationBarCheckBoxMenuItem;
        }

        static JMenu access$1700(WebBrowserMenuBar webBrowserMenuBar) {
            return webBrowserMenuBar.fileMenu;
        }
    }

    private static class NWebBrowserListener
    extends WebBrowserAdapter {
        private NWebBrowserListener() {
        }

        @Override
        public void locationChanged(WebBrowserNavigationEvent webBrowserNavigationEvent) {
            JWebBrowser jWebBrowser = webBrowserNavigationEvent.getWebBrowser();
            this.updateStopButton(jWebBrowser, false);
            DefaultWebBrowserDecorator defaultWebBrowserDecorator = (DefaultWebBrowserDecorator)jWebBrowser.getWebBrowserDecorator();
            if (webBrowserNavigationEvent.isTopFrame() && DefaultWebBrowserDecorator.access$000(defaultWebBrowserDecorator) != null) {
                DefaultWebBrowserDecorator.access$000(defaultWebBrowserDecorator).updateLocation();
            }
            DefaultWebBrowserDecorator.access$100(defaultWebBrowserDecorator);
        }

        @Override
        public void locationChanging(WebBrowserNavigationEvent webBrowserNavigationEvent) {
            JWebBrowser jWebBrowser = webBrowserNavigationEvent.getWebBrowser();
            DefaultWebBrowserDecorator defaultWebBrowserDecorator = (DefaultWebBrowserDecorator)jWebBrowser.getWebBrowserDecorator();
            if (webBrowserNavigationEvent.isTopFrame() && DefaultWebBrowserDecorator.access$000(defaultWebBrowserDecorator) != null) {
                DefaultWebBrowserDecorator.access$000(defaultWebBrowserDecorator).updateLocation(webBrowserNavigationEvent.getNewResourceLocation());
            }
            this.updateStopButton(jWebBrowser, true);
        }

        @Override
        public void locationChangeCanceled(WebBrowserNavigationEvent webBrowserNavigationEvent) {
            JWebBrowser jWebBrowser = webBrowserNavigationEvent.getWebBrowser();
            this.updateStopButton(jWebBrowser, false);
            DefaultWebBrowserDecorator defaultWebBrowserDecorator = (DefaultWebBrowserDecorator)jWebBrowser.getWebBrowserDecorator();
            if (webBrowserNavigationEvent.isTopFrame() && DefaultWebBrowserDecorator.access$000(defaultWebBrowserDecorator) != null) {
                DefaultWebBrowserDecorator.access$000(defaultWebBrowserDecorator).updateLocation();
            }
            DefaultWebBrowserDecorator.access$100(defaultWebBrowserDecorator);
        }

        @Override
        public void statusChanged(WebBrowserEvent webBrowserEvent) {
            JWebBrowser jWebBrowser = webBrowserEvent.getWebBrowser();
            DefaultWebBrowserDecorator defaultWebBrowserDecorator = (DefaultWebBrowserDecorator)jWebBrowser.getWebBrowserDecorator();
            if (DefaultWebBrowserDecorator.access$200(defaultWebBrowserDecorator) != null) {
                DefaultWebBrowserDecorator.access$200(defaultWebBrowserDecorator).updateStatus();
            }
        }

        @Override
        public void loadingProgressChanged(WebBrowserEvent webBrowserEvent) {
            JWebBrowser jWebBrowser = webBrowserEvent.getWebBrowser();
            DefaultWebBrowserDecorator defaultWebBrowserDecorator = (DefaultWebBrowserDecorator)jWebBrowser.getWebBrowserDecorator();
            if (DefaultWebBrowserDecorator.access$200(defaultWebBrowserDecorator) != null) {
                DefaultWebBrowserDecorator.access$200(defaultWebBrowserDecorator).updateProgressValue();
            }
            this.updateStopButton(jWebBrowser, false);
        }

        private void updateStopButton(JWebBrowser jWebBrowser, boolean bl) {
            boolean bl2 = bl || jWebBrowser.getLoadingProgress() != 100;
            DefaultWebBrowserDecorator defaultWebBrowserDecorator = (DefaultWebBrowserDecorator)jWebBrowser.getWebBrowserDecorator();
            if (DefaultWebBrowserDecorator.access$300(defaultWebBrowserDecorator) != null) {
                DefaultWebBrowserDecorator.access$300(defaultWebBrowserDecorator).getStopButton().setEnabled(bl2);
            }
            WebBrowserMenuBar.access$500(DefaultWebBrowserDecorator.access$400(defaultWebBrowserDecorator)).setEnabled(bl2);
        }

        NWebBrowserListener(lIIIl lIIIl2) {
            this();
        }
    }

    public static enum WebBrowserDecoratorComponentType {
        FILE_MENU,
        FILE_NEW_WINDOW_MENU_ITEM,
        FILE_OPEN_LOCATION_MENU_ITEM,
        FILE_OPEN_FILE_MENU_ITEM,
        FILE_CLOSE_MENU_ITEM,
        VIEW_MENU,
        VIEW_TOOLBARS_MENU,
        VIEW_TOOLBARS_BUTTON_BAR_CHECKBOX_MENU_ITEM,
        VIEW_TOOLBARS_LOCATION_BAR_CHECKBOX_MENU_ITEM,
        VIEW_STATUS_BAR_CHECKBOX_MENU_ITEM,
        VIEW_BACK_MENU_ITEM,
        VIEW_FORWARD_MENU_ITEM,
        VIEW_RELOAD_MENU_ITEM,
        VIEW_STOP_MENU_ITEM,
        BACK_BUTTON,
        FORWARD_BUTTON,
        RELOAD_BUTTON,
        STOP_BUTTON,
        GO_BUTTON,
        STATUS_LABEL;

    }
}

