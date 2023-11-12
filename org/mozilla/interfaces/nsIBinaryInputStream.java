/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;

public interface nsIBinaryInputStream
extends nsIInputStream {
    public static final String NS_IBINARYINPUTSTREAM_IID = "{7b456cb0-8772-11d3-90cf-0040056a906e}";

    public void setInputStream(nsIInputStream var1);

    public boolean readBoolean();

    public short read8();

    public int read16();

    public long read32();

    public double read64();

    public float readFloat();

    public double readDouble();

    public String readCString();

    public String readString();

    public String readBytes(long var1);

    public short[] readByteArray(long var1);
}

