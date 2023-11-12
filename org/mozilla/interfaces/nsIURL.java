/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIURI;

public interface nsIURL
extends nsIURI {
    public static final String NS_IURL_IID = "{d6116970-8034-11d3-9399-00104ba0fd40}";

    public String getFilePath();

    public void setFilePath(String var1);

    public String getParam();

    public void setParam(String var1);

    public String getQuery();

    public void setQuery(String var1);

    public String getRef();

    public void setRef(String var1);

    public String getDirectory();

    public void setDirectory(String var1);

    public String getFileName();

    public void setFileName(String var1);

    public String getFileBaseName();

    public void setFileBaseName(String var1);

    public String getFileExtension();

    public void setFileExtension(String var1);

    public String getCommonBaseSpec(nsIURI var1);

    public String getRelativeSpec(nsIURI var1);
}

