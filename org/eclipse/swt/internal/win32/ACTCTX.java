/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class ACTCTX {
    public int cbSize;
    public int dwFlags;
    public long lpSource;
    public short wProcessorArchitecture;
    public short wLangId;
    public long lpAssemblyDirectory;
    public long lpResourceName;
    public long lpApplicationName;
    public long hModule;
    public static final int sizeof = OS.ACTCTX_sizeof();
}

