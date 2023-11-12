/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISOAPBlock;

public interface nsISOAPHeaderBlock
extends nsISOAPBlock {
    public static final String NS_ISOAPHEADERBLOCK_IID = "{063d4a4e-1dd2-11b2-a365-cbaf1651f140}";

    public String getActorURI();

    public void setActorURI(String var1);

    public boolean getMustUnderstand();

    public void setMustUnderstand(boolean var1);
}

