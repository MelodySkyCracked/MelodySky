/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIMarkupDocumentViewer
extends nsISupports {
    public static final String NS_IMARKUPDOCUMENTVIEWER_IID = "{18cbdb18-3917-42fd-9c4a-0b2112d41a6d}";

    public void scrollToNode(nsIDOMNode var1);

    public float getTextZoom();

    public void setTextZoom(float var1);

    public boolean getAuthorStyleDisabled();

    public void setAuthorStyleDisabled(boolean var1);

    public String getDefaultCharacterSet();

    public void setDefaultCharacterSet(String var1);

    public String getForceCharacterSet();

    public void setForceCharacterSet(String var1);

    public String getHintCharacterSet();

    public void setHintCharacterSet(String var1);

    public int getHintCharacterSetSource();

    public void setHintCharacterSetSource(int var1);

    public String getPrevDocCharacterSet();

    public void setPrevDocCharacterSet(String var1);

    public void sizeToContent();

    public byte getBidiTextDirection();

    public void setBidiTextDirection(byte var1);

    public byte getBidiTextType();

    public void setBidiTextType(byte var1);

    public byte getBidiControlsTextMode();

    public void setBidiControlsTextMode(byte var1);

    public byte getBidiNumeral();

    public void setBidiNumeral(byte var1);

    public byte getBidiSupport();

    public void setBidiSupport(byte var1);

    public byte getBidiCharacterSet();

    public void setBidiCharacterSet(byte var1);

    public long getBidiOptions();

    public void setBidiOptions(long var1);
}

