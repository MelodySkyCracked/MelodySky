/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import java.io.File;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.ICustomDestinationList;
import org.eclipse.swt.internal.ole.win32.IObjectArray;
import org.eclipse.swt.internal.ole.win32.IObjectCollection;
import org.eclipse.swt.internal.ole.win32.IPropertyStore;
import org.eclipse.swt.internal.ole.win32.IShellLink;
import org.eclipse.swt.internal.ole.win32.ITaskbarList3;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PROPERTYKEY;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TaskItem;
import org.eclipse.swt.widgets.Widget;

public class TaskBar
extends Widget {
    int itemCount;
    TaskItem[] items = new TaskItem[4];
    ITaskbarList3 mTaskbarList3;
    String iconsDir;
    static final char[] EXE_PATH;
    static final PROPERTYKEY PKEY_Title;
    static final PROPERTYKEY PKEY_AppUserModel_IsDestListSeparator;
    static final String EXE_PATH_KEY = "org.eclipse.swt.win32.taskbar.executable";
    static final String EXE_ARGS_KEY = "org.eclipse.swt.win32.taskbar.arguments";
    static final String ICON_KEY = "org.eclipse.swt.win32.taskbar.icon";
    static final String ICON_INDEX_KEY = "org.eclipse.swt.win32.taskbar.icon.index";

    TaskBar(Display display, int n) {
        this.display = display;
        this.createHandle();
        this.reskinWidget();
    }

    void createHandle() {
        long[] lArray = new long[]{0L};
        int n = COM.CoCreateInstance(COM.CLSID_TaskbarList, 0L, 1, COM.IID_ITaskbarList3, lArray);
        if (n == -2147221164) {
            this.error(20);
        }
        if (n != 0) {
            this.error(2);
        }
        this.mTaskbarList3 = new ITaskbarList3(lArray[0]);
    }

    void createItem(TaskItem taskItem, int n) {
        if (n == -1) {
            n = this.itemCount;
        }
        if (0 > n || n > this.itemCount) {
            this.error(6);
        }
        if (this.itemCount == this.items.length) {
            TaskItem[] taskItemArray = new TaskItem[this.items.length + 4];
            System.arraycopy(this.items, 0, taskItemArray, 0, this.items.length);
            this.items = taskItemArray;
        }
        System.arraycopy(this.items, n, this.items, n + 1, this.itemCount++ - n);
        this.items[n] = taskItem;
    }

    void createItems() {
        for (Shell shell : this.display.getShells()) {
            this.getItem(shell);
        }
        this.getItem(null);
    }

    IShellLink createShellLink(MenuItem menuItem) {
        Object object;
        PROPERTYKEY pROPERTYKEY;
        int n = menuItem.getStyle();
        if ((n & 0x40) != 0) {
            return null;
        }
        long[] lArray = new long[]{0L};
        int n2 = COM.CoCreateInstance(COM.CLSID_ShellLink, 0L, 1, COM.IID_IShellLinkW, lArray);
        if (n2 != 0) {
            this.error(2);
        }
        IShellLink iShellLink = new IShellLink(lArray[0]);
        long l2 = OS.GetProcessHeap();
        long l3 = OS.HeapAlloc(l2, 8, OS.PROPVARIANT_sizeof());
        long l4 = 0L;
        if ((n & 2) != 0) {
            OS.MoveMemory(l3, new short[]{11}, 2);
            OS.MoveMemory(l3 + 8L, new short[]{-1}, 2);
            pROPERTYKEY = PKEY_AppUserModel_IsDestListSeparator;
        } else {
            object = menuItem.getText();
            int n3 = ((String)object).length();
            char[] cArray = new char[n3 + 1];
            ((String)object).getChars(0, n3, cArray, 0);
            l4 = OS.HeapAlloc(l2, 8, cArray.length * 2);
            OS.MoveMemory(l4, cArray, cArray.length * 2);
            OS.MoveMemory(l3, new short[]{31}, 2);
            OS.MoveMemory(l3 + 8L, new long[]{l4}, C.PTR_SIZEOF);
            pROPERTYKEY = PKEY_Title;
            String string = (String)menuItem.getData(EXE_PATH_KEY);
            if (string != null) {
                n3 = string.length();
                cArray = new char[n3 + 1];
                string.getChars(0, n3, cArray, 0);
            } else {
                cArray = EXE_PATH;
            }
            n2 = iShellLink.SetPath(cArray);
            if (n2 != 0) {
                this.error(5);
            }
            if ((object = (String)menuItem.getData(EXE_ARGS_KEY)) == null) {
                object = "--launcher.openFile /SWTINTERNAL_ID" + menuItem.id;
            }
            n3 = ((String)object).length();
            cArray = new char[n3 + 1];
            ((String)object).getChars(0, n3, cArray, 0);
            n2 = iShellLink.SetArguments(cArray);
            if (n2 != 0) {
                this.error(5);
            }
            String string2 = (String)menuItem.getData(ICON_KEY);
            int n4 = 0;
            if (string2 != null) {
                object = (String)menuItem.getData(ICON_INDEX_KEY);
                if (object != null) {
                    n4 = Integer.parseInt((String)object);
                }
            } else {
                String string3 = null;
                Image image = menuItem.getImage();
                if (image != null) {
                    string3 = this.getIconsDir();
                }
                if (string3 != null) {
                    ImageData imageData;
                    Object object2;
                    string2 = string3 + "\\menu" + menuItem.id + ".ico";
                    if (menuItem.hBitmap != 0L) {
                        object2 = Image.win32_new(this.display, 0, menuItem.hBitmap);
                        imageData = ((Image)object2).getImageData(DPIUtil.getDeviceZoom());
                        ((Image)object2).handle = 0L;
                    } else {
                        imageData = image.getImageData(DPIUtil.getDeviceZoom());
                    }
                    object2 = new ImageLoader();
                    ((ImageLoader)object2).data = new ImageData[]{imageData};
                    ((ImageLoader)object2).save(string2, 3);
                }
            }
            if (string2 != null) {
                n3 = string2.length();
                cArray = new char[n3 + 1];
                string2.getChars(0, n3, cArray, 0);
                n2 = iShellLink.SetIconLocation(cArray, n4);
                if (n2 != 0) {
                    this.error(5);
                }
            }
        }
        n2 = iShellLink.QueryInterface(COM.IID_IPropertyStore, lArray);
        if (n2 != 0) {
            this.error(2);
        }
        if ((n2 = ((IPropertyStore)(object = new IPropertyStore(lArray[0]))).SetValue(pROPERTYKEY, l3)) != 0) {
            this.error(5);
        }
        ((IPropertyStore)object).Commit();
        ((IUnknown)object).Release();
        OS.HeapFree(l2, 0, l3);
        if (l4 != 0L) {
            OS.HeapFree(l2, 0, l4);
        }
        return iShellLink;
    }

    IObjectArray createShellLinkArray(MenuItem[] menuItemArray) {
        if (menuItemArray == null) {
            return null;
        }
        if (menuItemArray.length == 0) {
            return null;
        }
        long[] lArray = new long[]{0L};
        int n = COM.CoCreateInstance(COM.CLSID_EnumerableObjectCollection, 0L, 1, COM.IID_IObjectCollection, lArray);
        if (n != 0) {
            this.error(2);
        }
        IObjectCollection iObjectCollection = new IObjectCollection(lArray[0]);
        for (MenuItem menuItem : menuItemArray) {
            IShellLink iShellLink = this.createShellLink(menuItem);
            if (iShellLink == null) continue;
            iObjectCollection.AddObject(iShellLink);
            if (n != 0) {
                this.error(5);
            }
            iShellLink.Release();
        }
        n = iObjectCollection.QueryInterface(COM.IID_IObjectArray, lArray);
        if (n != 0) {
            this.error(2);
        }
        IObjectArray iObjectArray = new IObjectArray(lArray[0]);
        iObjectCollection.Release();
        return iObjectArray;
    }

    void destroyItem(TaskItem taskItem) {
        int n;
        for (n = 0; n < this.itemCount && this.items[n] != taskItem; ++n) {
        }
        if (n == this.itemCount) {
            return;
        }
        System.arraycopy(this.items, n + 1, this.items, n, --this.itemCount - n);
        this.items[this.itemCount] = null;
    }

    String getIconsDir() {
        if (this.iconsDir != null) {
            return this.iconsDir;
        }
        File file = new File(this.display.appLocalDir + "\\ico_dir");
        if (file.exists()) {
            for (File file2 : file.listFiles()) {
                file2.delete();
            }
        } else if (!file.mkdirs()) {
            return null;
        }
        this.iconsDir = file.getPath();
        return this.iconsDir;
    }

    public TaskItem getItem(int n) {
        this.checkWidget();
        this.createItems();
        if (0 > n || n >= this.itemCount) {
            this.error(6);
        }
        return this.items[n];
    }

    public TaskItem getItem(Shell shell) {
        this.checkWidget();
        for (TaskItem taskItem : this.items) {
            if (taskItem == null || taskItem.shell != shell) continue;
            return taskItem;
        }
        TaskItem taskItem = new TaskItem(this, 0);
        if (shell != null) {
            taskItem.setShell(shell);
        }
        return taskItem;
    }

    public int getItemCount() {
        this.checkWidget();
        this.createItems();
        return this.itemCount;
    }

    public TaskItem[] getItems() {
        this.checkWidget();
        this.createItems();
        TaskItem[] taskItemArray = new TaskItem[this.itemCount];
        System.arraycopy(this.items, 0, taskItemArray, 0, taskItemArray.length);
        return taskItemArray;
    }

    @Override
    void releaseChildren(boolean bl) {
        if (this.items != null) {
            for (TaskItem taskItem : this.items) {
                if (taskItem == null || taskItem.isDisposed()) continue;
                taskItem.release(false);
            }
            this.items = null;
        }
        super.releaseChildren(bl);
    }

    @Override
    void releaseParent() {
        super.releaseParent();
        if (this.display.taskBar == this) {
            this.display.taskBar = null;
        }
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.mTaskbarList3.Release();
        this.mTaskbarList3 = null;
    }

    @Override
    void reskinChildren(int n) {
        if (this.items != null) {
            for (TaskItem taskItem : this.items) {
                if (taskItem == null) continue;
                taskItem.reskin(n);
            }
        }
        super.reskinChildren(n);
    }

    void setMenu(Menu menu) {
        long[] lArray = new long[]{0L};
        int n = COM.CoCreateInstance(COM.CLSID_DestinationList, 0L, 1, COM.IID_ICustomDestinationList, lArray);
        if (n != 0) {
            this.error(2);
        }
        ICustomDestinationList iCustomDestinationList = new ICustomDestinationList(lArray[0]);
        String string = Display.APP_NAME;
        char[] cArray = new char[]{'S', 'W', 'T', '\u0000'};
        if (string != null && string.length() > 0) {
            int n2 = string.length();
            cArray = new char[n2 + 1];
            string.getChars(0, n2, cArray, 0);
        }
        MenuItem[] menuItemArray = null;
        if (menu != null && (menuItemArray = menu.getItems()).length != 0) {
            IObjectArray iObjectArray = this.createShellLinkArray(menuItemArray);
            if (iObjectArray != null) {
                n = iCustomDestinationList.SetAppID(cArray);
                if (n != 0) {
                    this.error(5);
                }
                int[] nArray = new int[]{0};
                iCustomDestinationList.BeginList(nArray, COM.IID_IObjectArray, lArray);
                if (n != 0) {
                    this.error(5);
                }
                IObjectArray iObjectArray2 = new IObjectArray(lArray[0]);
                int[] nArray2 = new int[]{0};
                iObjectArray.GetCount(nArray2);
                if (nArray2[0] != 0 && (n = iCustomDestinationList.AddUserTasks(iObjectArray)) != 0) {
                    this.error(5);
                }
                for (MenuItem menuItem : menuItemArray) {
                    MenuItem[] menuItemArray2;
                    IObjectArray iObjectArray3;
                    Menu menu2;
                    if ((menuItem.getStyle() & 0x40) == 0 || (menu2 = menuItem.getMenu()) == null || (iObjectArray3 = this.createShellLinkArray(menuItemArray2 = menu2.getItems())) == null) continue;
                    iObjectArray3.GetCount(nArray2);
                    if (nArray2[0] != 0) {
                        String string2 = menuItem.getText();
                        int n3 = string2.length();
                        char[] cArray2 = new char[n3 + 1];
                        string2.getChars(0, n3, cArray2, 0);
                        n = iCustomDestinationList.AppendCategory(cArray2, iObjectArray3);
                        if (n != 0) {
                            this.error(5);
                        }
                    }
                    iObjectArray3.Release();
                }
                iObjectArray.Release();
                n = iCustomDestinationList.CommitList();
                if (n != 0) {
                    this.error(5);
                }
                iObjectArray2.Release();
            }
        } else {
            n = iCustomDestinationList.DeleteList(cArray);
            if (n != 0) {
                this.error(5);
            }
        }
        iCustomDestinationList.Release();
    }

    static {
        PKEY_Title = new PROPERTYKEY();
        PKEY_AppUserModel_IsDestListSeparator = new PROPERTYKEY();
        OS.PSPropertyKeyFromString("{F29F85E0-4FF9-1068-AB91-08002B27B3D9} 2\u0000".toCharArray(), PKEY_Title);
        OS.PSPropertyKeyFromString("{9F4C2855-9F79-4B39-A8D0-E1D42DE1D5F3}, 6\u0000".toCharArray(), PKEY_AppUserModel_IsDestListSeparator);
        char[] cArray = new char[260];
        while (OS.GetModuleFileName(0L, cArray, cArray.length) == cArray.length) {
            cArray = new char[cArray.length + 260];
        }
        EXE_PATH = cArray;
    }
}

