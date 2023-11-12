/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal;

import org.eclipse.swt.internal.Library;

public class Platform {
    public static final String PLATFORM = "win32";

    public static boolean isLoadable() {
        return Library.isLoadable();
    }
}

