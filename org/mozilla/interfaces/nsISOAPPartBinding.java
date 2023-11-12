/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIWSDLSOAPBinding;

public interface nsISOAPPartBinding
extends nsIWSDLSOAPBinding {
    public static final String NS_ISOAPPARTBINDING_IID = "{0458dac9-65de-11d5-9b42-00104bdf5339}";
    public static final int LOCATION_BODY = 1;
    public static final int LOCATION_HEADER = 2;
    public static final int LOCATION_FAULT = 3;
    public static final int USE_LITERAL = 1;
    public static final int USE_ENCODED = 2;

    public int getLocation();

    public int getUse();

    public String getEncodingStyle();

    public String getNamespace();
}

