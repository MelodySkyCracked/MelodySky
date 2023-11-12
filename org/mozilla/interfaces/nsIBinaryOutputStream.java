/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIOutputStream;

public interface nsIBinaryOutputStream
extends nsIOutputStream {
    public static final String NS_IBINARYOUTPUTSTREAM_IID = "{204ee610-8765-11d3-90cf-0040056a906e}";

    public void setOutputStream(nsIOutputStream var1);

    public void writeBoolean(boolean var1);

    public void write8(short var1);

    public void write16(int var1);

    public void write32(long var1);

    public void write64(double var1);

    public void writeFloat(float var1);

    public void writeDouble(double var1);

    public void writeStringZ(String var1);

    public void writeWStringZ(String var1);

    public void writeUtf8Z(String var1);

    public void writeBytes(String var1, long var2);

    public void writeByteArray(short[] var1, long var2);
}

