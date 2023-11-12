/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAuthPrompt;
import org.mozilla.interfaces.nsIPrompt;

public interface nsIAuthPromptWrapper
extends nsIAuthPrompt {
    public static final String NS_IAUTHPROMPTWRAPPER_IID = "{6228d644-17fe-11d4-8cee-0060b0fc14a3}";

    public void setPromptDialogs(nsIPrompt var1);
}

