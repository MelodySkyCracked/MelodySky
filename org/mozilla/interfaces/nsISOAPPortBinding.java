/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIWSDLSOAPBinding;

public interface nsISOAPPortBinding
extends nsIWSDLSOAPBinding {
    public static final String NS_ISOAPPORTBINDING_IID = "{0458dac7-65de-11d5-9b42-00104bdf5339}";
    public static final int SOAP_VERSION_1_1 = 0;
    public static final int SOAP_VERSION_1_2 = 1;
    public static final int SOAP_VERSION_UNKNOWN = Short.MAX_VALUE;

    public String getName();

    public String getAddress();

    public int getStyle();

    public String getTransport();

    public int getSoapVersion();
}

