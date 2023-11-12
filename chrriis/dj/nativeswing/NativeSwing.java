/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing;

import chrriis.dj.nativeswing.NSSystemProperty;
import chrriis.dj.nativeswing.NativeComponentProxy;
import chrriis.dj.nativeswing.NativeComponentWrapper;
import chrriis.dj.nativeswing.common.SystemProperty;
import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.l;
import chrriis.dj.nativeswing.lIIlI;
import chrriis.dj.nativeswing.llII;
import chrriis.dj.nativeswing.lllI;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.AWTEventListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.swing.SwingUtilities;

public class NativeSwing {
    private static volatile List nativeComponentWrapperList;
    private static List windowList;
    private static volatile boolean isInitialized;
    private static volatile boolean isHeavyWeightForcerEnabled;

    static NativeComponentWrapper[] getNativeComponentWrappers() {
        if (nativeComponentWrapperList == null) {
            return new NativeComponentWrapper[0];
        }
        return nativeComponentWrapperList.toArray(new NativeComponentWrapper[0]);
    }

    static void addNativeComponentWrapper(NativeComponentWrapper nativeComponentWrapper) {
        NativeSwing.checkInitialized();
        if (nativeComponentWrapperList == null) {
            nativeComponentWrapperList = new ArrayList();
        }
        nativeComponentWrapperList.add(nativeComponentWrapper);
        if (!isHeavyWeightForcerEnabled) {
            HeavyweightForcer.activate(nativeComponentWrapper.getNativeComponent());
        }
    }

    static boolean removeNativeComponentWrapper(NativeComponentWrapper nativeComponentWrapper) {
        if (nativeComponentWrapperList == null) {
            return false;
        }
        return nativeComponentWrapperList.remove(nativeComponentWrapper);
    }

    static Window[] getWindows() {
        return windowList == null ? new Window[]{} : windowList.toArray(new Window[0]);
    }

    private static boolean isInitialized() {
        return isInitialized;
    }

    private static void checkInitialized() {
        if (!NativeSwing.isInitialized()) {
            throw new IllegalStateException("The Native Swing framework is not initialized! Please refer to the instructions to set it up properly.");
        }
    }

    private static void loadClipboardDebuggingProperties() {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            if (!clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new StringReader((String)clipboard.getData(DataFlavor.stringFlavor)));
            if ("[nativeswing debug]".equals(bufferedReader.readLine().trim().toLowerCase(Locale.ENGLISH))) {
                String string;
                while ((string = bufferedReader.readLine()) != null) {
                    if (string.length() == 0) continue;
                    int n = string.indexOf(61);
                    if (n <= 0) break;
                    String string2 = string.substring(0, n).trim();
                    String string3 = string.substring(n + 1).trim();
                    if (!string2.startsWith("nativeswing.")) continue;
                    System.setProperty(string2, string3);
                }
            }
            bufferedReader.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void initialize() {
        boolean bl;
        if (NativeSwing.isInitialized()) {
            return;
        }
        NativeSwing.loadClipboardDebuggingProperties();
        SystemProperty.SUN_AWT_NOERASEBACKGROUND.set("true");
        SystemProperty.SUN_AWT_XEMBEDSERVER.set("true");
        NSSystemProperty.JNA_FORCE_HW_POPUP.set("false");
        if (SystemProperty.JAVAWEBSTART_VERSION.get() != null && SystemProperty.JAVA_VERSION.get().compareTo("1.6.0_18") >= 0) {
            if (SystemProperty.SUN_AWT_DISABLEMIXING.get() == null) {
                System.err.println("Under WebStart on Java >= 1.6.0_18, the value of the \"" + SystemProperty.SUN_AWT_DISABLEMIXING.getName() + "\" system property needs to be defined in the JNLP descriptor with value \"true\" (or \"false\" if you really want the default behavior). When not set to \"true\", the content of the native components may not be displayed.");
                SystemProperty.SUN_AWT_DISABLEMIXING.set("false");
            }
        } else if (SystemProperty.SUN_AWT_DISABLEMIXING.get() == null) {
            SystemProperty.SUN_AWT_DISABLEMIXING.set("true");
        }
        isHeavyWeightForcerEnabled = bl = !"true".equals(SystemProperty.SUN_AWT_DISABLEMIXING.get()) && SystemProperty.JAVA_VERSION.get().compareTo("1.6.0_12") >= 0;
        NSSystemProperty.INTEGRATION_USEDEFAULTCLIPPING.set(String.valueOf(bl));
        long l2 = 65L;
        if (Utils.IS_JAVA_7_OR_GREATER) {
            l2 |= 0x14L;
        }
        Toolkit.getDefaultToolkit().addAWTEventListener(new NIAWTEventListener(null), l2);
        isInitialized = true;
    }

    private NativeSwing() {
    }

    static List access$200() {
        return nativeComponentWrapperList;
    }

    static List access$300() {
        return windowList;
    }

    static List access$302(List list) {
        windowList = list;
        return windowList;
    }

    private static class NIAWTEventListener
    implements AWTEventListener {
        private List dialogList = new ArrayList();
        private volatile Set blockedWindowSet = new HashSet();

        private NIAWTEventListener() {
        }

        /*
         * Exception decompiling
         */
        private void computeBlockedDialogs() {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl54 : ILOAD_3 - null : trying to set 1 previously set to 0
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }

        private void adjustNativeComponents() {
            if (NativeSwing.access$200() == null) {
                return;
            }
            for (int i = NativeSwing.access$200().size() - 1; i >= 0; --i) {
                Component component;
                NativeComponentWrapper nativeComponentWrapper = (NativeComponentWrapper)NativeSwing.access$200().get(i);
                Component component2 = component = nativeComponentWrapper.getNativeComponent();
                NativeComponentProxy nativeComponentProxy = nativeComponentWrapper.getNativeComponentProxy();
                if (nativeComponentProxy != null) {
                    component2 = nativeComponentProxy;
                }
                Window window = SwingUtilities.getWindowAncestor(component2);
                boolean bl = this.blockedWindowSet.contains(window);
                boolean bl2 = component2.isShowing();
                nativeComponentWrapper.setNativeComponentEnabled(!bl && bl2);
                if (Utils.IS_MAC || bl2 || !component.hasFocus()) continue;
                component.transferFocus();
            }
        }

        @Override
        public void eventDispatched(AWTEvent aWTEvent) {
            Window window;
            int n = aWTEvent.getID();
            if (Utils.IS_JAVA_7_OR_GREATER) {
                switch (n) {
                    case 501: 
                    case 1004: {
                        if (NativeSwing.access$200() == null) {
                            return;
                        }
                        for (int i = NativeSwing.access$200().size() - 1; i >= 0; --i) {
                            NativeComponentWrapper nativeComponentWrapper = (NativeComponentWrapper)NativeSwing.access$200().get(i);
                            if (!nativeComponentWrapper.isNativeComponentEnabled()) continue;
                            nativeComponentWrapper.setNativeComponentEnabled(false);
                            nativeComponentWrapper.setNativeComponentEnabled(true);
                        }
                        return;
                    }
                }
            }
            boolean bl = false;
            switch (n) {
                case 102: 
                case 103: {
                    bl = true;
                }
            }
            if (aWTEvent.getSource() instanceof Window) {
                if (NativeSwing.access$300() == null) {
                    NativeSwing.access$302(new ArrayList());
                }
                switch (n) {
                    case 102: 
                    case 200: {
                        window = (Window)aWTEvent.getSource();
                        NativeSwing.access$300().remove(window);
                        NativeSwing.access$300().add(window);
                        break;
                    }
                    case 103: 
                    case 202: {
                        NativeSwing.access$300().remove(aWTEvent.getSource());
                    }
                }
            }
            if (aWTEvent.getSource() instanceof Dialog) {
                switch (n) {
                    case 102: 
                    case 200: {
                        window = (Dialog)aWTEvent.getSource();
                        this.dialogList.remove(window);
                        this.dialogList.add(window);
                        break;
                    }
                    case 103: 
                    case 202: {
                        this.dialogList.remove(aWTEvent.getSource());
                    }
                }
                switch (n) {
                    case 102: 
                    case 103: 
                    case 200: 
                    case 202: {
                        this.computeBlockedDialogs();
                        bl = true;
                    }
                }
            }
            if (bl) {
                this.adjustNativeComponents();
            }
            switch (n) {
                case 208: {
                    if (!(aWTEvent.getSource() instanceof Dialog) || !(window = (Dialog)aWTEvent.getSource()).getFocusableWindowState()) break;
                    window.setFocusableWindowState(false);
                    lllI lllI2 = new lllI(this, "Dialog focus fixer", (Dialog)window);
                    lllI2.setDaemon(true);
                    lllI2.start();
                    break;
                }
            }
        }

        NIAWTEventListener(llII llII2) {
            this();
        }
    }

    private static class HeavyweightForcer
    implements HierarchyListener {
        private Component component;
        private HeavyweightForcerWindow forcer;

        private HeavyweightForcer(Component component) {
            this.component = component;
            if (component.isShowing()) {
                this.createForcer();
            }
        }

        public static void activate(Component component) {
            component.addHierarchyListener(new HeavyweightForcer(component));
        }

        private void destroyForcer() {
            if (!SwingUtilities.isEventDispatchThread()) {
                SwingUtilities.invokeLater(new l(this));
                return;
            }
            if (this.forcer == null) {
                return;
            }
            int n = this.forcer.getCount() - 1;
            this.forcer.setCount(n);
            if (n == 0) {
                this.forcer.dispose();
            }
            this.forcer = null;
        }

        private void createForcer() {
            if (!SwingUtilities.isEventDispatchThread()) {
                SwingUtilities.invokeLater(new lIIlI(this));
                return;
            }
            Window window = SwingUtilities.getWindowAncestor(this.component);
            if (window == null) {
                return;
            }
            for (Window window2 : window.getOwnedWindows()) {
                if (!(window2 instanceof HeavyweightForcerWindow)) continue;
                this.forcer = (HeavyweightForcerWindow)window2;
                break;
            }
            if (this.forcer == null) {
                this.forcer = new HeavyweightForcerWindow(window);
            }
            this.forcer.setCount(this.forcer.getCount() + 1);
        }

        @Override
        public void hierarchyChanged(HierarchyEvent hierarchyEvent) {
            long l2 = hierarchyEvent.getChangeFlags();
            if ((l2 & 2L) != 0L) {
                if (!this.component.isDisplayable()) {
                    this.component.removeHierarchyListener(this);
                    this.destroyForcer();
                }
            } else if ((l2 & 4L) != 0L) {
                if (this.component.isShowing()) {
                    this.createForcer();
                } else {
                    this.destroyForcer();
                }
            }
        }

        static void access$000(HeavyweightForcer heavyweightForcer) {
            heavyweightForcer.destroyForcer();
        }

        static void access$100(HeavyweightForcer heavyweightForcer) {
            heavyweightForcer.createForcer();
        }
    }

    private static class HeavyweightForcerWindow
    extends Window {
        private boolean isPacked;
        private int count;

        public HeavyweightForcerWindow(Window window) {
            super(window);
            this.pack();
            this.isPacked = true;
        }

        @Override
        public boolean isVisible() {
            return this.isPacked;
        }

        @Override
        public Rectangle getBounds() {
            Window window = this.getOwner();
            return window == null ? super.getBounds() : window.getBounds();
        }

        public void setCount(int n) {
            this.count = n;
        }

        public int getCount() {
            return this.count;
        }
    }
}

