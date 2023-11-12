/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIJVMConfig
extends nsISupports {
    public static final String NS_IJVMCONFIG_IID = "{3e333e20-b190-42d8-b993-d5fa435e46c4}";

    public String getVersion();

    public String getType();

    public String getOS();

    public String getArch();

    public nsIFile getPath();

    public nsIFile getMozillaPluginPath();

    public String getDescription();
}

