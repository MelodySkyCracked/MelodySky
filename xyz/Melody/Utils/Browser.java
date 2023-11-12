/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils;

import chrriis.dj.nativeswing.NSOption;
import chrriis.dj.nativeswing.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import xyz.Melody.Utils.lIl;

public final class Browser
extends JPanel {
    private JPanel webBrowserPanel;
    public boolean closed = false;
    private JWebBrowser webBrowser;
    private JFrame frame;

    public Browser(String string, String string2, boolean bl, boolean bl2, boolean bl3, boolean bl4, NSOption ... nSOptionArray) {
        this.closed = false;
        new Thread(() -> this.lambda$new$0(string, string2, bl, bl2, bl3, bl4, nSOptionArray), "Thread-Call").start();
    }

    public Browser(String string, String string2, boolean bl, boolean bl2, boolean bl3, boolean bl4, int n, int n2, NSOption ... nSOptionArray) {
        this.closed = false;
        new Thread(() -> this.lambda$new$1(string, string2, bl, bl2, bl3, bl4, n, n2, nSOptionArray), "Thread-Call").start();
    }

    private Browser(String string, boolean bl, NSOption ... nSOptionArray) {
        super(new BorderLayout());
        this.webBrowserPanel = new JPanel(new BorderLayout());
        this.webBrowser = new JWebBrowser(nSOptionArray);
        this.webBrowser.navigate(string);
        this.webBrowser.setJavascriptEnabled(true);
        this.webBrowser.setBarsVisible(bl);
        this.webBrowser.setStatusBarVisible(false);
        this.webBrowserPanel.add((Component)this.webBrowser, "Center");
        this.add((Component)this.webBrowserPanel, "Center");
    }

    public void openForm(String string, String string2, boolean bl, boolean bl2, boolean bl3, boolean bl4, int n, int n2, NSOption ... nSOptionArray) {
        UIUtils.setPreferredLookAndFeel();
        NativeInterface.open();
        SwingUtilities.invokeLater(new lIl(this, string2, string, bl4, nSOptionArray, bl, bl3, bl2, n, n2));
    }

    public void close() {
        try {
            this.closed = true;
            this.frame.dispose();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void lambda$new$1(String string, String string2, boolean bl, boolean bl2, boolean bl3, boolean bl4, int n, int n2, NSOption[] nSOptionArray) {
        this.openForm(string, string2, bl, bl2, bl3, bl4, n, n2, nSOptionArray);
    }

    private void lambda$new$0(String string, String string2, boolean bl, boolean bl2, boolean bl3, boolean bl4, NSOption[] nSOptionArray) {
        this.openForm(string, string2, bl, bl2, bl3, bl4, 700, 800, nSOptionArray);
    }

    static JFrame access$002(Browser browser, JFrame jFrame) {
        browser.frame = jFrame;
        return browser.frame;
    }

    static JFrame access$000(Browser browser) {
        return browser.frame;
    }

    Browser(String string, boolean bl, NSOption[] nSOptionArray, lIl lIl2) {
        this(string, bl, nSOptionArray);
    }
}

