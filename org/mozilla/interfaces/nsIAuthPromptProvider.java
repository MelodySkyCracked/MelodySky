/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAuthPrompt;
import org.mozilla.interfaces.nsISupports;

public interface nsIAuthPromptProvider
extends nsISupports {
    public static final String NS_IAUTHPROMPTPROVIDER_IID = "{129d3bd5-8a26-4b0b-b8a0-19fdea029196}";
    public static final long PROMPT_NORMAL = 0L;
    public static final long PROMPT_PROXY = 1L;

    public nsIAuthPrompt getAuthPrompt(long var1);
}

