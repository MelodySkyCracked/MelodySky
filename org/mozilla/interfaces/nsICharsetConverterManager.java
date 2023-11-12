/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAtom;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUTF8StringEnumerator;

public interface nsICharsetConverterManager
extends nsISupports {
    public static final String NS_ICHARSETCONVERTERMANAGER_IID = "{f5323a76-c8f7-4c65-8d0c-1250e969c7d5}";

    public nsISupports getUnicodeDecoder(String var1);

    public nsISupports getUnicodeDecoderRaw(String var1);

    public nsISupports getUnicodeEncoder(String var1);

    public nsISupports getUnicodeEncoderRaw(String var1);

    public String getCharsetAlias(String var1);

    public nsIUTF8StringEnumerator getDecoderList();

    public nsIUTF8StringEnumerator getEncoderList();

    public nsIUTF8StringEnumerator getCharsetDetectorList();

    public String getCharsetTitle(String var1);

    public String getCharsetData(String var1, String var2);

    public nsIAtom getCharsetLangGroup(String var1);

    public nsIAtom getCharsetLangGroupRaw(String var1);
}

