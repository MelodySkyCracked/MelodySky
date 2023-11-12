/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class PROCESS_INFORMATION {
    public long hProcess;
    public long hThread;
    public int dwProcessId;
    public int dwThreadId;
    public static int sizeof = OS.PROCESS_INFORMATION_sizeof();
}

