/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIPassword;

public interface nsIPasswordInternal
extends nsIPassword {
    public static final String NS_IPASSWORDINTERNAL_IID = "{2cc35c67-978f-42a9-a958-16e97ad2f4c8}";

    public String getUserFieldName();

    public String getPasswordFieldName();
}

