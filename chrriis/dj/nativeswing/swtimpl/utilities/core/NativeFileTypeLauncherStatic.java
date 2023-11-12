/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.utilities.core;

import chrriis.dj.nativeswing.swtimpl.CommandMessage;
import chrriis.dj.nativeswing.swtimpl.core.SWTUtils;
import chrriis.dj.nativeswing.swtimpl.utilities.core.I;
import chrriis.dj.nativeswing.swtimpl.utilities.core.NativeFileTypeLauncher;
import chrriis.dj.nativeswing.swtimpl.utilities.core.lI;
import chrriis.dj.nativeswing.swtimpl.utilities.internal.INativeFileTypeLauncher;
import chrriis.dj.nativeswing.swtimpl.utilities.internal.INativeFileTypeLauncherStatic;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.program.Program;

class NativeFileTypeLauncherStatic
implements INativeFileTypeLauncherStatic {
    private static Map idToFileTypeLauncherInfoMap;
    private static Map programToFileTypeLauncherInfoMap;
    private static boolean isNativeInitialized;
    private static boolean hasInitializedLaunchers;
    private static boolean hasInitializedExtensions;
    private static Map idToFileTypeLauncherMap;
    private static boolean isDefaultIconLoaded;
    private static ImageIcon defaultIcon;

    NativeFileTypeLauncherStatic() {
    }

    private static boolean isProgramValid(Program program) {
        String string = program.getName();
        return string != null && string.length() > 0;
    }

    private static void initNative() {
        if (isNativeInitialized) {
            return;
        }
        isNativeInitialized = true;
        programToFileTypeLauncherInfoMap = new HashMap();
        idToFileTypeLauncherInfoMap = new HashMap();
    }

    @Override
    public void load() {
        this.initializeExtensions();
        NativeFileTypeLauncherStatic.initializeLaunchers();
    }

    private static void initializeLaunchers() {
        if (hasInitializedLaunchers) {
            return;
        }
        hasInitializedLaunchers = true;
        new CMN_initializeLaunchers(null).syncExec(true, new Object[0]);
    }

    void initializeExtensions() {
        if (hasInitializedExtensions) {
            return;
        }
        hasInitializedExtensions = true;
        new CMN_initializeExtensions(null).syncExec(true, new Object[0]);
    }

    @Override
    public String[] getAllRegisteredExtensions() {
        this.initializeExtensions();
        return (String[])new CMN_getAllRegisteredExtensions(null).syncExec(true, new Object[0]);
    }

    @Override
    public INativeFileTypeLauncher getLauncher(String string) {
        int n = string.lastIndexOf(46);
        if (n == -1) {
            return null;
        }
        String string2 = string.substring(n);
        Integer n2 = (Integer)new CMN_getLauncherID(null).syncExec(true, string2);
        if (n2 == null) {
            return null;
        }
        NativeFileTypeLauncher nativeFileTypeLauncher = (NativeFileTypeLauncher)idToFileTypeLauncherMap.get(n2);
        if (nativeFileTypeLauncher == null) {
            nativeFileTypeLauncher = new NativeFileTypeLauncher(this, n2);
            idToFileTypeLauncherMap.put(n2, nativeFileTypeLauncher);
        }
        return nativeFileTypeLauncher;
    }

    @Override
    public INativeFileTypeLauncher[] getLaunchers() {
        this.load();
        int[] nArray = (int[])new CMN_getLauncherIDs(null).syncExec(true, new Object[0]);
        if (idToFileTypeLauncherMap == null) {
            idToFileTypeLauncherMap = new HashMap();
        }
        INativeFileTypeLauncher[] iNativeFileTypeLauncherArray = new NativeFileTypeLauncher[nArray.length];
        for (int i = 0; i < nArray.length; ++i) {
            int n = nArray[i];
            NativeFileTypeLauncher nativeFileTypeLauncher = (NativeFileTypeLauncher)idToFileTypeLauncherMap.get(n);
            if (nativeFileTypeLauncher == null) {
                nativeFileTypeLauncher = new NativeFileTypeLauncher(this, n);
                idToFileTypeLauncherMap.put(n, nativeFileTypeLauncher);
            }
            iNativeFileTypeLauncherArray[i] = nativeFileTypeLauncher;
        }
        return iNativeFileTypeLauncherArray;
    }

    @Override
    public ImageIcon getDefaultIcon() {
        if (!isDefaultIconLoaded) {
            Icon icon;
            isDefaultIconLoaded = true;
            try {
                File file = File.createTempFile("~djn", "~.qwertyuiop");
                file.deleteOnExit();
                icon = FileSystemView.getFileSystemView().getSystemIcon(file);
                file.delete();
            }
            catch (Exception exception) {
                icon = UIManager.getIcon("FileView.fileIcon");
            }
            if (!(icon instanceof ImageIcon)) {
                int n = icon.getIconWidth();
                int n2 = icon.getIconHeight();
                BufferedImage bufferedImage = new BufferedImage(n, n2, 2);
                Graphics graphics = bufferedImage.getGraphics();
                icon.paintIcon(null, graphics, 0, 0);
                graphics.dispose();
                icon = new ImageIcon(bufferedImage);
            }
            defaultIcon = (ImageIcon)icon;
        }
        return defaultIcon;
    }

    @Override
    public Dimension getIconSize() {
        ImageIcon imageIcon = this.getDefaultIcon();
        return imageIcon == null ? new Dimension(16, 16) : new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
    }

    static FileTypeLauncherInfo getFileTypeLauncherInfo(Integer n) {
        return (FileTypeLauncherInfo)idToFileTypeLauncherInfoMap.get(n);
    }

    static Map access$000() {
        return idToFileTypeLauncherInfoMap;
    }

    static Map access$100() {
        return programToFileTypeLauncherInfoMap;
    }

    static boolean access$200(Program program) {
        return NativeFileTypeLauncherStatic.isProgramValid(program);
    }

    static void access$400() {
        NativeFileTypeLauncherStatic.initNative();
    }

    static boolean access$800() {
        return hasInitializedExtensions;
    }

    private static class CMN_getLauncherIDs
    extends CommandMessage {
        private CMN_getLauncherIDs() {
        }

        @Override
        public Object run(Object[] objectArray) {
            NativeFileTypeLauncherStatic.access$400();
            FileTypeLauncherInfo[] fileTypeLauncherInfoArray = NativeFileTypeLauncherStatic.access$100().values().toArray(new FileTypeLauncherInfo[0]);
            Arrays.sort(fileTypeLauncherInfoArray, new I(this));
            int[] nArray = new int[fileTypeLauncherInfoArray.length];
            for (int i = 0; i < fileTypeLauncherInfoArray.length; ++i) {
                nArray[i] = fileTypeLauncherInfoArray[i].getID();
            }
            return nArray;
        }

        CMN_getLauncherIDs(lI lI2) {
            this();
        }
    }

    private static class CMN_getLauncherID
    extends CommandMessage {
        private CMN_getLauncherID() {
        }

        @Override
        public Object run(Object[] objectArray) {
            String string = (String)objectArray[0];
            Program program = Program.findProgram(string);
            if (program == null) {
                return null;
            }
            NativeFileTypeLauncherStatic.access$400();
            FileTypeLauncherInfo fileTypeLauncherInfo = (FileTypeLauncherInfo)NativeFileTypeLauncherStatic.access$100().get(program);
            if (fileTypeLauncherInfo == null && NativeFileTypeLauncherStatic.access$200(program)) {
                fileTypeLauncherInfo = new FileTypeLauncherInfo(program);
                NativeFileTypeLauncherStatic.access$100().put(program, fileTypeLauncherInfo);
            }
            if (fileTypeLauncherInfo != null) {
                if (!NativeFileTypeLauncherStatic.access$800()) {
                    FileTypeLauncherInfo.access$500(fileTypeLauncherInfo, string);
                }
                return fileTypeLauncherInfo.getID();
            }
            return null;
        }

        CMN_getLauncherID(lI lI2) {
            this();
        }
    }

    private static class CMN_getAllRegisteredExtensions
    extends CommandMessage {
        private CMN_getAllRegisteredExtensions() {
        }

        @Override
        public Object run(Object[] objectArray) {
            ArrayList<String> arrayList = new ArrayList<String>();
            for (FileTypeLauncherInfo fileTypeLauncherInfo : NativeFileTypeLauncherStatic.access$100().values()) {
                for (String string : fileTypeLauncherInfo.getRegisteredExtensions()) {
                    arrayList.add(string);
                }
            }
            return arrayList.toArray(new String[0]);
        }

        CMN_getAllRegisteredExtensions(lI lI2) {
            this();
        }
    }

    private static class CMN_initializeExtensions
    extends CommandMessage {
        private CMN_initializeExtensions() {
        }

        @Override
        public Object run(Object[] objectArray) {
            for (String string : Program.getExtensions()) {
                Program program = Program.findProgram(string);
                if (program == null) continue;
                NativeFileTypeLauncherStatic.access$400();
                FileTypeLauncherInfo fileTypeLauncherInfo = (FileTypeLauncherInfo)NativeFileTypeLauncherStatic.access$100().get(program);
                if (fileTypeLauncherInfo == null && NativeFileTypeLauncherStatic.access$200(program)) {
                    fileTypeLauncherInfo = new FileTypeLauncherInfo(program);
                    NativeFileTypeLauncherStatic.access$100().put(program, fileTypeLauncherInfo);
                }
                if (fileTypeLauncherInfo == null) continue;
                FileTypeLauncherInfo.access$500(fileTypeLauncherInfo, string);
            }
            return null;
        }

        CMN_initializeExtensions(lI lI2) {
            this();
        }
    }

    private static class CMN_initializeLaunchers
    extends CommandMessage {
        private CMN_initializeLaunchers() {
        }

        @Override
        public Object run(Object[] objectArray) {
            for (Program program : Program.getPrograms()) {
                if (NativeFileTypeLauncherStatic.access$100().containsKey(program) || !NativeFileTypeLauncherStatic.access$200(program) || program.getImageData() == null) continue;
                NativeFileTypeLauncherStatic.access$100().put(program, new FileTypeLauncherInfo(program));
            }
            return null;
        }

        CMN_initializeLaunchers(lI lI2) {
            this();
        }
    }

    static class FileTypeLauncherInfo {
        public static int nextID = 1;
        private int id = nextID++;
        private Program program;
        private List registeredExtensionList;
        private boolean isIconInitialized;
        private ImageIcon icon;

        public FileTypeLauncherInfo(Program program) {
            this.program = program;
            NativeFileTypeLauncherStatic.access$000().put(this.getID(), this);
        }

        private void addExtension(String string) {
            if (this.registeredExtensionList == null) {
                this.registeredExtensionList = new ArrayList(1);
            }
            if (!this.registeredExtensionList.contains(string)) {
                this.registeredExtensionList.add(string);
            }
        }

        public int getID() {
            return this.id;
        }

        public String[] getRegisteredExtensions() {
            return this.registeredExtensionList == null ? new String[]{} : this.registeredExtensionList.toArray(new String[0]);
        }

        public Program getProgram() {
            return this.program;
        }

        public ImageIcon getIcon() {
            if (!this.isIconInitialized) {
                this.isIconInitialized = true;
                ImageData imageData = this.program.getImageData();
                this.icon = imageData == null ? null : new ImageIcon(SWTUtils.convertSWTImage(imageData));
            }
            return this.icon;
        }

        static void access$500(FileTypeLauncherInfo fileTypeLauncherInfo, String string) {
            fileTypeLauncherInfo.addExtension(string);
        }
    }
}

