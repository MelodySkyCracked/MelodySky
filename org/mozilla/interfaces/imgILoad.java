/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.imgIContainer;
import org.mozilla.interfaces.nsISupports;

public interface imgILoad
extends nsISupports {
    public static final String IMGILOAD_IID = "{e6273acc-1dd1-11b2-a08b-824ad1b1628d}";

    public imgIContainer getImage();

    public void setImage(imgIContainer var1);

    public boolean getIsMultiPartChannel();
}

