/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.utilities;

import chrriis.dj.nativeswing.swtimpl.internal.NativeCoreObjectFactory;
import chrriis.dj.nativeswing.swtimpl.utilities.internal.INativeFileTypeLauncher;
import chrriis.dj.nativeswing.swtimpl.utilities.internal.INativeFileTypeLauncherStatic;
import java.awt.Dimension;
import javax.swing.ImageIcon;

public class FileTypeLauncher {
    private static INativeFileTypeLauncherStatic fileTypeLauncherStatic = (INativeFileTypeLauncherStatic)NativeCoreObjectFactory.create(INativeFileTypeLauncherStatic.class, "chrriis.dj.nativeswing.swtimpl.utilities.core.NativeFileTypeLauncherStatic", new Class[0], new Object[0]);
    private INativeFileTypeLauncher fileTypeLauncher;

    public FileTypeLauncher() {
        this((INativeFileTypeLauncher)NativeCoreObjectFactory.create(INativeFileTypeLauncher.class, "chrriis.dj.nativeswing.swtimpl.utilities.core.NativeFileTypeLauncher", new Class[0], new Object[0]));
    }

    FileTypeLauncher(INativeFileTypeLauncher iNativeFileTypeLauncher) {
        this.fileTypeLauncher = iNativeFileTypeLauncher;
    }

    public static void load() {
        fileTypeLauncherStatic.load();
    }

    public static String[] getAllRegisteredExtensions() {
        return fileTypeLauncherStatic.getAllRegisteredExtensions();
    }

    public String[] getRegisteredExtensions() {
        return this.fileTypeLauncher.getRegisteredExtensions();
    }

    public String getName() {
        return this.fileTypeLauncher.getName();
    }

    public ImageIcon getIcon() {
        return this.fileTypeLauncher.getIcon();
    }

    public boolean equals(Object object) {
        return this.fileTypeLauncher.equals(((FileTypeLauncher)object).fileTypeLauncher);
    }

    public int hashCode() {
        return this.fileTypeLauncher.hashCode();
    }

    public void launch(String string) {
        this.fileTypeLauncher.launch(string);
    }

    public static FileTypeLauncher getLauncher(String string) {
        INativeFileTypeLauncher iNativeFileTypeLauncher = fileTypeLauncherStatic.getLauncher(string);
        return iNativeFileTypeLauncher == null ? null : new FileTypeLauncher(iNativeFileTypeLauncher);
    }

    public static FileTypeLauncher[] getLaunchers() {
        INativeFileTypeLauncher[] iNativeFileTypeLauncherArray = fileTypeLauncherStatic.getLaunchers();
        FileTypeLauncher[] fileTypeLauncherArray = new FileTypeLauncher[iNativeFileTypeLauncherArray.length];
        for (int i = 0; i < fileTypeLauncherArray.length; ++i) {
            fileTypeLauncherArray[i] = new FileTypeLauncher(iNativeFileTypeLauncherArray[i]);
        }
        return fileTypeLauncherArray;
    }

    public static ImageIcon getDefaultIcon() {
        return fileTypeLauncherStatic.getDefaultIcon();
    }

    public static Dimension getIconSize() {
        return fileTypeLauncherStatic.getIconSize();
    }
}

