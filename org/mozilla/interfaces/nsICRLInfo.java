/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsICRLInfo
extends nsISupports {
    public static final String NS_ICRLINFO_IID = "{c185d920-4a3e-11d5-ba27-00108303b117}";

    public String getOrganization();

    public String getOrganizationalUnit();

    public double getLastUpdate();

    public double getNextUpdate();

    public String getLastUpdateLocale();

    public String getNextUpdateLocale();

    public String getNameInDb();

    public String getLastFetchURL();
}

