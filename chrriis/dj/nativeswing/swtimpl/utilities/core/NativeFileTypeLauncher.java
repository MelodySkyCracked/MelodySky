/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.utilities.core;

import chrriis.dj.nativeswing.swtimpl.CommandMessage;
import chrriis.dj.nativeswing.swtimpl.utilities.core.NativeFileTypeLauncherStatic;
import chrriis.dj.nativeswing.swtimpl.utilities.core.l;
import chrriis.dj.nativeswing.swtimpl.utilities.internal.INativeFileTypeLauncher;
import javax.swing.ImageIcon;

class NativeFileTypeLauncher
implements INativeFileTypeLauncher {
    private NativeFileTypeLauncherStatic fileTypeLauncherStatic;
    private int id;
    private String[] registeredExtensions;
    private String name;
    private ImageIcon icon;
    private boolean isIconInitialized;
    private Integer hashCode;

    NativeFileTypeLauncher(NativeFileTypeLauncherStatic nativeFileTypeLauncherStatic, int n) {
        this.fileTypeLauncherStatic = nativeFileTypeLauncherStatic;
        this.id = n;
    }

    @Override
    public String[] getRegisteredExtensions() {
        if (this.registeredExtensions == null) {
            this.fileTypeLauncherStatic.initializeExtensions();
            this.registeredExtensions = (String[])new CMN_getRegisteredExtensions(null).syncExec(true, this.id);
        }
        return this.registeredExtensions;
    }

    @Override
    public String getName() {
        if (this.name == null) {
            this.name = (String)new CMN_getName(null).syncExec(true, this.id);
        }
        return this.name;
    }

    @Override
    public ImageIcon getIcon() {
        if (!this.isIconInitialized) {
            this.isIconInitialized = true;
            this.icon = (ImageIcon)new CMN_getIcon(null).syncExec(true, this.id);
        }
        return this.icon == null ? this.fileTypeLauncherStatic.getDefaultIcon() : this.icon;
    }

    public boolean equals(Object object) {
        return this == object;
    }

    public int hashCode() {
        if (this.hashCode == null) {
            this.hashCode = (Integer)new CMN_hashCode(null).syncExec(true, this.id);
        }
        return this.hashCode;
    }

    @Override
    public void launch(String string) {
        new CMN_launch(null).asyncExec(true, this.id, string);
    }

    private static class CMN_launch
    extends CommandMessage {
        private CMN_launch() {
        }

        @Override
        public Object run(Object[] objectArray) {
            NativeFileTypeLauncherStatic.getFileTypeLauncherInfo((Integer)objectArray[0]).getProgram().execute((String)objectArray[1]);
            return null;
        }

        CMN_launch(l l2) {
            this();
        }
    }

    private static class CMN_hashCode
    extends CommandMessage {
        private CMN_hashCode() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return NativeFileTypeLauncherStatic.getFileTypeLauncherInfo((Integer)objectArray[0]).getProgram().hashCode();
        }

        CMN_hashCode(l l2) {
            this();
        }
    }

    private static class CMN_getIcon
    extends CommandMessage {
        private CMN_getIcon() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return NativeFileTypeLauncherStatic.getFileTypeLauncherInfo((Integer)objectArray[0]).getIcon();
        }

        CMN_getIcon(l l2) {
            this();
        }
    }

    private static class CMN_getName
    extends CommandMessage {
        private CMN_getName() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return NativeFileTypeLauncherStatic.getFileTypeLauncherInfo((Integer)objectArray[0]).getProgram().getName();
        }

        CMN_getName(l l2) {
            this();
        }
    }

    private static class CMN_getRegisteredExtensions
    extends CommandMessage {
        private CMN_getRegisteredExtensions() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return NativeFileTypeLauncherStatic.getFileTypeLauncherInfo((Integer)objectArray[0]).getRegisteredExtensions();
        }

        CMN_getRegisteredExtensions(l l2) {
            this();
        }
    }
}

