/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIProfileStartup;
import org.mozilla.interfaces.nsISupports;

public interface nsIProfileMigrator
extends nsISupports {
    public static final String NS_IPROFILEMIGRATOR_IID = "{24ce8b9d-b7ff-4279-aef4-26e158f03e34}";

    public void _import();

    public void migrate(nsIProfileStartup var1);
}

