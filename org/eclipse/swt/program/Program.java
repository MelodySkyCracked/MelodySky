/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.program;

import java.util.LinkedHashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PROCESS_INFORMATION;
import org.eclipse.swt.internal.win32.SHELLEXECUTEINFO;
import org.eclipse.swt.internal.win32.SHFILEINFO;
import org.eclipse.swt.internal.win32.STARTUPINFO;
import org.eclipse.swt.internal.win32.TCHAR;

public final class Program {
    String name;
    String command;
    String iconName;
    String extension;
    static final String[] ARGUMENTS = new String[]{"%1", "%l", "%L"};

    Program() {
    }

    static String assocQueryString(int n, TCHAR tCHAR, boolean bl) {
        TCHAR tCHAR2 = new TCHAR(0, 1024);
        int n2 = 1056;
        int[] nArray = new int[]{tCHAR2.length()};
        int n3 = OS.AssocQueryString(n2, n, tCHAR, null, tCHAR2, nArray);
        if (n3 == -2147467261) {
            tCHAR2 = new TCHAR(0, nArray[0]);
            n3 = OS.AssocQueryString(n2, n, tCHAR, null, tCHAR2, nArray);
        }
        if (n3 == 0) {
            if (bl) {
                int n4 = OS.ExpandEnvironmentStrings(tCHAR2, null, 0);
                if (n4 != 0) {
                    TCHAR tCHAR3 = new TCHAR(0, n4);
                    OS.ExpandEnvironmentStrings(tCHAR2, tCHAR3, n4);
                    return tCHAR3.toString(0, Math.max(0, n4 - 1));
                }
                return "";
            }
            return tCHAR2.toString(0, Math.max(0, nArray[0] - 1));
        }
        return null;
    }

    public static Program findProgram(String string) {
        if (string == null) {
            SWT.error(4);
        }
        if (string.length() == 0) {
            return null;
        }
        if (string.charAt(0) != '.') {
            string = "." + string;
        }
        TCHAR tCHAR = new TCHAR(0, string, true);
        Program program = null;
        String string2 = Program.assocQueryString(1, tCHAR, true);
        if (string2 != null) {
            String string3;
            String string4 = null;
            if (string4 == null) {
                string4 = Program.assocQueryString(3, tCHAR, false);
            }
            if (string4 == null) {
                string4 = Program.assocQueryString(4, tCHAR, false);
            }
            if (string4 == null) {
                string4 = "";
            }
            if ((string3 = Program.assocQueryString(15, tCHAR, true)) == null) {
                string3 = "";
            }
            program = new Program();
            program.name = string4;
            program.command = string2;
            program.iconName = string3;
            program.extension = string;
        }
        return program;
    }

    public static String[] getExtensions() {
        Object object;
        String[] stringArray = new String[1024];
        char[] cArray = new char[1024];
        int[] nArray = new int[]{cArray.length};
        int n = 0;
        int n2 = 0;
        while (OS.RegEnumKeyEx(Integer.MIN_VALUE, n, cArray, nArray, null, null, null, 0L) != 259) {
            object = new String(cArray, 0, nArray[0]);
            nArray[0] = cArray.length;
            if (((String)object).length() > 0 && ((String)object).charAt(0) == '.') {
                if (n2 == stringArray.length) {
                    String[] stringArray2 = new String[stringArray.length + 1024];
                    System.arraycopy(stringArray, 0, stringArray2, 0, stringArray.length);
                    stringArray = stringArray2;
                }
                stringArray[n2++] = object;
            }
            ++n;
        }
        if (n2 != stringArray.length) {
            object = new String[n2];
            System.arraycopy(stringArray, 0, object, 0, n2);
            stringArray = object;
        }
        return stringArray;
    }

    static String getKeyValue(String string, boolean bl) {
        TCHAR tCHAR = new TCHAR(0, string, true);
        long[] lArray = new long[1];
        if (OS.RegOpenKeyEx(Integer.MIN_VALUE, tCHAR, 0, 131097, lArray) != 0) {
            return null;
        }
        String string2 = null;
        int[] nArray = new int[1];
        if (OS.RegQueryValueEx(lArray[0], (TCHAR)null, 0L, null, (TCHAR)null, nArray) == 0) {
            char[] cArray;
            string2 = "";
            int n = nArray[0] / 2;
            if (nArray[0] % 2 != 0) {
                ++n;
            }
            if (n != 0 && OS.RegQueryValueEx(lArray[0], null, 0L, null, cArray = new char[n], nArray) == 0) {
                if (bl) {
                    n = OS.ExpandEnvironmentStrings(cArray, null, 0);
                    if (n != 0) {
                        char[] cArray2 = new char[n];
                        OS.ExpandEnvironmentStrings(cArray, cArray2, n);
                        string2 = new String(cArray2, 0, n - 1);
                    }
                } else {
                    string2 = new String(cArray, 0, n - 1);
                }
            }
        }
        if (lArray[0] != 0L) {
            OS.RegCloseKey(lArray[0]);
        }
        return string2;
    }

    static Program getProgram(String string, String string2) {
        String string3 = Program.getKeyValue(string, false);
        if (string3 == null || string3.length() == 0) {
            string3 = string;
        }
        String string4 = "\\shell";
        String string5 = Program.getKeyValue(string + "\\shell", true);
        if (string5 == null || string5.length() == 0) {
            string5 = "open";
        }
        String string6 = "\\shell\\" + string5 + "\\command";
        String string7 = Program.getKeyValue(string + string6, true);
        if (string7 == null || string7.length() == 0) {
            return null;
        }
        String string8 = "\\DefaultIcon";
        String string9 = Program.getKeyValue(string + string8, true);
        if (string9 == null) {
            string9 = "";
        }
        Program program = new Program();
        program.name = string3;
        program.command = string7;
        program.iconName = string9;
        program.extension = string2;
        return program;
    }

    public static Program[] getPrograms() {
        Object object;
        char[] cArray = new char[1024];
        int[] nArray = new int[]{cArray.length};
        int n = 0;
        LinkedHashSet<Object> linkedHashSet = new LinkedHashSet<Object>();
        while (OS.RegEnumKeyEx(Integer.MIN_VALUE, n, cArray, nArray, null, null, null, 0L) != 259) {
            object = new String(cArray, 0, nArray[0]);
            nArray[0] = cArray.length;
            linkedHashSet.add(object);
            ++n;
        }
        object = new ConcurrentHashMap(linkedHashSet.size());
        ((Stream)linkedHashSet.stream().parallel()).forEach(arg_0 -> Program.lambda$getPrograms$0((ConcurrentHashMap)object, arg_0));
        LinkedHashSet linkedHashSet2 = linkedHashSet.stream().map(arg_0 -> Program.lambda$getPrograms$1((ConcurrentHashMap)object, arg_0)).filter(Program::lambda$getPrograms$2).collect(Collectors.toCollection(LinkedHashSet::new));
        return (Program[])linkedHashSet2.toArray((Object[])new Program[linkedHashSet2.size()]);
    }

    public static boolean launch(String string) {
        return Program.launch(string, null);
    }

    public static boolean launch(String string, String string2) {
        Object object;
        if (string == null) {
            SWT.error(4);
        }
        long l2 = OS.GetProcessHeap();
        TCHAR tCHAR = new TCHAR(0, string, true);
        int n = tCHAR.length() * 2;
        long l3 = OS.HeapAlloc(l2, 8, n);
        OS.MoveMemory(l3, tCHAR, n);
        long l4 = 0L;
        if (string2 != null && OS.PathIsExe(l3)) {
            object = new TCHAR(0, string2, true);
            n = ((TCHAR)object).length() * 2;
            l4 = OS.HeapAlloc(l2, 8, n);
            OS.MoveMemory(l4, (TCHAR)object, n);
        }
        object = new SHELLEXECUTEINFO();
        ((SHELLEXECUTEINFO)object).cbSize = SHELLEXECUTEINFO.sizeof;
        ((SHELLEXECUTEINFO)object).lpFile = l3;
        ((SHELLEXECUTEINFO)object).lpDirectory = l4;
        ((SHELLEXECUTEINFO)object).nShow = 5;
        boolean bl = OS.ShellExecuteEx((SHELLEXECUTEINFO)object);
        if (l3 != 0L) {
            OS.HeapFree(l2, 0, l3);
        }
        if (l4 != 0L) {
            OS.HeapFree(l2, 0, l4);
        }
        return bl;
    }

    public boolean execute(String string) {
        if (string == null) {
            SWT.error(4);
        }
        boolean bl = true;
        String string2 = this.command;
        String string3 = "";
        for (int i = 0; i < ARGUMENTS.length; ++i) {
            int n = this.command.indexOf(ARGUMENTS[i]);
            if (n == -1) continue;
            bl = false;
            string2 = this.command.substring(0, n);
            string3 = this.command.substring(n + ARGUMENTS[i].length(), this.command.length());
            break;
        }
        if (bl) {
            string = " \"" + string + "\"";
        }
        String string4 = string2 + string + string3;
        long l2 = OS.GetProcessHeap();
        TCHAR tCHAR = new TCHAR(0, string4, true);
        int n = tCHAR.length() * 2;
        long l3 = OS.HeapAlloc(l2, 8, n);
        OS.MoveMemory(l3, tCHAR, n);
        STARTUPINFO sTARTUPINFO = new STARTUPINFO();
        sTARTUPINFO.cb = STARTUPINFO.sizeof;
        PROCESS_INFORMATION pROCESS_INFORMATION = new PROCESS_INFORMATION();
        boolean bl2 = OS.CreateProcess(0L, l3, 0L, 0L, false, 0, 0L, 0L, sTARTUPINFO, pROCESS_INFORMATION);
        if (l3 != 0L) {
            OS.HeapFree(l2, 0, l3);
        }
        if (pROCESS_INFORMATION.hProcess != 0L) {
            OS.CloseHandle(pROCESS_INFORMATION.hProcess);
        }
        if (pROCESS_INFORMATION.hThread != 0L) {
            OS.CloseHandle(pROCESS_INFORMATION.hThread);
        }
        return bl2;
    }

    public ImageData getImageData() {
        int n;
        Object object;
        if (this.extension != null) {
            SHFILEINFO sHFILEINFO = new SHFILEINFO();
            int n2 = 273;
            TCHAR tCHAR = new TCHAR(0, this.extension, true);
            OS.SHGetFileInfo(tCHAR.chars, 128, sHFILEINFO, SHFILEINFO.sizeof, n2);
            if (sHFILEINFO.hIcon != 0L) {
                Image image = Image.win32_new(null, 1, sHFILEINFO.hIcon);
                ImageData imageData = image.getImageData();
                image.dispose();
                return imageData;
            }
        }
        int n3 = 0;
        String string = this.iconName;
        int n4 = this.iconName.indexOf(44);
        if (n4 != -1) {
            string = this.iconName.substring(0, n4);
            object = this.iconName.substring(n4 + 1, this.iconName.length()).trim();
            try {
                n3 = Integer.parseInt((String)object);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        if ((n = string.length()) > 1 && string.charAt(0) == '\"' && string.charAt(n - 1) == '\"') {
            string = string.substring(1, n - 1);
        }
        object = new TCHAR(0, string, true);
        long[] lArray = new long[1];
        long[] lArray2 = null;
        OS.ExtractIconEx((TCHAR)object, n3, lArray2, lArray, 1);
        if (lArray[0] == 0L) {
            return null;
        }
        Image image = Image.win32_new(null, 1, lArray[0]);
        ImageData imageData = image.getImageData();
        image.dispose();
        return imageData;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof Program) {
            Program program = (Program)object;
            return this.name.equals(program.name) && this.command.equals(program.command) && this.iconName.equals(program.iconName);
        }
        return false;
    }

    public int hashCode() {
        return this.name.hashCode() ^ this.command.hashCode() ^ this.iconName.hashCode();
    }

    public String toString() {
        return "Program {" + this.name + "}";
    }

    private static boolean lambda$getPrograms$2(Object object) {
        return object != null;
    }

    private static Object lambda$getPrograms$1(ConcurrentHashMap concurrentHashMap, Object object) {
        return (Program)concurrentHashMap.get(object);
    }

    private static void lambda$getPrograms$0(ConcurrentHashMap concurrentHashMap, Object object) {
        Program program = Program.getProgram((String)object, null);
        if (program != null) {
            concurrentHashMap.put(object, program);
        }
    }
}

