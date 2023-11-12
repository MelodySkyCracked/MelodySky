/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;

public interface nsIDOMProcessingInstruction
extends nsIDOMNode {
    public static final String NS_IDOMPROCESSINGINSTRUCTION_IID = "{a6cf907f-15b3-11d2-932e-00805f8add32}";

    public String getTarget();

    public String getData();

    public void setData(String var1);
}

