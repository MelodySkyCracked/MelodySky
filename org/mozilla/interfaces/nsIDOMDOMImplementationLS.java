/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMLSInput;
import org.mozilla.interfaces.nsIDOMLSOutput;
import org.mozilla.interfaces.nsIDOMLSParser;
import org.mozilla.interfaces.nsIDOMLSSerializer;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMDOMImplementationLS
extends nsISupports {
    public static final String NS_IDOMDOMIMPLEMENTATIONLS_IID = "{e2c8b03c-a49a-4923-81b0-ba9a86da0e21}";
    public static final int MODE_SYNCHRONOUS = 1;
    public static final int MODE_ASYNCHRONOUS = 2;

    public nsIDOMLSParser createLSParser(int var1, String var2);

    public nsIDOMLSSerializer createLSSerializer();

    public nsIDOMLSInput createLSInput();

    public nsIDOMLSOutput createLSOutput();
}

