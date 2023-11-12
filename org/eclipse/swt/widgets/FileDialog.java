/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IFileDialog;
import org.eclipse.swt.internal.ole.win32.IShellItem;
import org.eclipse.swt.internal.ole.win32.IShellItemArray;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class FileDialog
extends Dialog {
    String[] filterNames = new String[0];
    String[] filterExtensions = new String[0];
    String[] fileNames = new String[0];
    String filterPath = "";
    String fileName = "";
    int filterIndex = 0;
    boolean overwrite = false;
    static final String DEFAULT_FILTER = "*.*";
    static final String LONG_PATH_PREFIX = "\\\\?\\";

    public FileDialog(Shell shell) {
        this(shell, 65536);
    }

    public FileDialog(Shell shell, int n) {
        super(shell, Dialog.checkStyle(shell, n));
        this.checkSubclass();
    }

    public String getFileName() {
        return this.fileName;
    }

    public String[] getFileNames() {
        return this.fileNames;
    }

    public String[] getFilterExtensions() {
        return this.filterExtensions;
    }

    public int getFilterIndex() {
        return this.filterIndex;
    }

    public String[] getFilterNames() {
        return this.filterNames;
    }

    public String getFilterPath() {
        return this.filterPath;
    }

    public boolean getOverwrite() {
        return this.overwrite;
    }

    static Path getItemPath(IShellItem iShellItem) {
        long[] lArray = new long[]{0L};
        if (iShellItem.GetDisplayName(-2147123200, lArray) == 0) {
            int n = OS.wcslen(lArray[0]);
            char[] cArray = new char[n];
            OS.MoveMemory(cArray, lArray[0], n * 2);
            OS.CoTaskMemFree(lArray[0]);
            String string = String.valueOf(cArray);
            if (string.startsWith(LONG_PATH_PREFIX)) {
                string = string.substring(4);
            }
            return Paths.get(string, new String[0]);
        }
        return null;
    }

    public String open() {
        char[] cArray;
        Object object;
        Object object2;
        int n;
        boolean bl;
        Object[] objectArray;
        long[] lArray = new long[]{0L};
        int n2 = (this.style & 0x2000) != 0 ? COM.CoCreateInstance(COM.CLSID_FileSaveDialog, 0L, 1, COM.IID_IFileSaveDialog, lArray) : COM.CoCreateInstance(COM.CLSID_FileOpenDialog, 0L, 1, COM.IID_IFileOpenDialog, lArray);
        if (n2 != 0) {
            SWT.error(2);
        }
        IFileDialog iFileDialog = new IFileDialog(lArray[0]);
        int[] nArray = new int[]{0};
        iFileDialog.GetOptions(nArray);
        int[] nArray2 = nArray;
        boolean bl2 = false;
        nArray2[0] = nArray2[0] | 0x48;
        int[] nArray3 = nArray;
        boolean bl3 = false;
        nArray3[0] = nArray3[0] & 0xFFFFEFFF;
        if ((this.style & 0x2000) != 0) {
            if (!this.overwrite) {
                objectArray = nArray;
                bl = false;
                objectArray[0] = objectArray[0] & 0xFFFFFFFD;
            }
        } else if ((this.style & 2) != 0) {
            objectArray = nArray;
            bl = false;
            objectArray[0] = objectArray[0] | 0x200;
        }
        iFileDialog.SetOptions(nArray[0]);
        if (!this.title.isEmpty()) {
            iFileDialog.SetTitle(this.title.toCharArray());
        }
        objectArray = this.filterExtensions;
        String[] stringArray = this.filterNames;
        if (objectArray == null || objectArray.length == 0) {
            String[] stringArray2 = new String[]{DEFAULT_FILTER};
            objectArray = stringArray2;
            stringArray = stringArray2;
        }
        long l2 = OS.GetProcessHeap();
        long[] lArray2 = new long[objectArray.length * 2];
        for (n = 0; n < objectArray.length; ++n) {
            int[] nArray4;
            object2 = objectArray[n];
            object = stringArray != null && n < stringArray.length ? (Object)stringArray[n] : object2;
            Object object3 = object;
            if (!object.contains("*.") && (nArray4 = OS.readRegistryDwords(-2147483647, "Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Advanced", "HideFileExt")) != null && nArray4[0] == 0) {
                object = object.replace(" (" + (String)object2, "");
            }
            long l3 = OS.HeapAlloc(l2, 8, (object.length() + 1) * 2);
            long l4 = OS.HeapAlloc(l2, 8, (((String)object2).length() + 1) * 2);
            OS.MoveMemory(l3, object.toCharArray(), object.length() * 2);
            OS.MoveMemory(l4, ((String)object2).toCharArray(), ((String)object2).length() * 2);
            lArray2[n * 2] = l3;
            lArray2[n * 2 + 1] = l4;
        }
        iFileDialog.SetFileTypes(objectArray.length, lArray2);
        for (n = 0; n < lArray2.length; ++n) {
            OS.HeapFree(l2, 0, lArray2[n]);
        }
        iFileDialog.SetDefaultExtension(new char[1]);
        iFileDialog.SetFileTypeIndex(this.filterIndex + 1);
        if (this.filterPath != null && COM.SHCreateItemFromParsingName(cArray = this.filterPath.replace('/', '\\').toCharArray(), 0L, COM.IID_IShellItem, lArray) == 0) {
            object2 = new IShellItem(lArray[0]);
            iFileDialog.SetDefaultFolder((IShellItem)object2);
            ((IUnknown)object2).Release();
        }
        if (this.fileName != null) {
            char[] cArray2 = this.fileName.replace('/', '\\').toCharArray();
            iFileDialog.SetFileName(cArray2);
        }
        Dialog dialog = null;
        object2 = this.parent.getDisplay();
        if ((this.style & 0x30000) != 0) {
            dialog = ((Display)object2).getModalDialog();
            ((Display)object2).setModalDialog(this);
        }
        ((Display)object2).externalEventLoop = true;
        ((Display)object2).sendPreExternalEventDispatchEvent();
        n2 = iFileDialog.Show(this.parent.handle);
        ((Display)object2).externalEventLoop = false;
        ((Display)object2).sendPostExternalEventDispatchEvent();
        if ((this.style & 0x30000) != 0) {
            ((Display)object2).setModalDialog(dialog);
        }
        object = null;
        this.fileNames = new String[0];
        if (n2 == 0) {
            int[] nArray5;
            if ((this.style & 0x2000) != 0) {
                if (iFileDialog.GetResult(lArray) == 0) {
                    IShellItem iShellItem = new IShellItem(lArray[0]);
                    Path path = FileDialog.getItemPath(iShellItem);
                    iShellItem.Release();
                    this.fileName = path.getFileName().toString();
                    this.filterPath = path.getParent().toString();
                    this.fileNames = new String[]{this.fileName};
                    object = path.toString();
                }
            } else if (iFileDialog.GetResults(lArray) == 0) {
                IShellItemArray iShellItemArray = new IShellItemArray(lArray[0]);
                int[] nArray6 = new int[]{0};
                iShellItemArray.GetCount(nArray6);
                this.fileNames = new String[nArray6[0]];
                Path path = null;
                for (int i = 0; i < nArray6[0]; ++i) {
                    iShellItemArray.GetItemAt(i, lArray);
                    IShellItem iShellItem = new IShellItem(lArray[0]);
                    Path path2 = FileDialog.getItemPath(iShellItem);
                    iShellItem.Release();
                    if (path == null) {
                        path = path2.getParent();
                        this.filterPath = path.toString();
                        object = path2.toString();
                    }
                    this.fileNames[i] = path2.getParent().equals(path) ? path2.getFileName().toString() : path2.toString();
                }
                this.fileName = this.fileNames[0];
                iShellItemArray.Release();
            }
            if (iFileDialog.GetFileTypeIndex(nArray5 = new int[]{0}) == 0) {
                this.filterIndex = nArray5[0] - 1;
            }
        }
        iFileDialog.Release();
        return object;
    }

    public void setFileName(String string) {
        this.fileName = string;
    }

    public void setFilterExtensions(String[] stringArray) {
        this.filterExtensions = stringArray;
    }

    public void setFilterIndex(int n) {
        this.filterIndex = n;
    }

    public void setFilterNames(String[] stringArray) {
        this.filterNames = stringArray;
    }

    public void setFilterPath(String string) {
        this.filterPath = string;
    }

    public void setOverwrite(boolean bl) {
        this.overwrite = bl;
    }
}

