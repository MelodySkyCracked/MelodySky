/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.ole.win32;

import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IDispatch;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.ole.win32.VARIANT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;

public final class Variant {
    public static final int sizeof = VARIANT.sizeof;
    private short type;
    private boolean booleanData;
    private byte byteData;
    private short shortData;
    private char charData;
    private int intData;
    private long longData;
    private float floatData;
    private double doubleData;
    private String stringData;
    private long byRefPtr;
    private IDispatch dispatchData;
    private IUnknown unknownData;
    public static final Variant NULL = new Variant();

    public static void win32_copy(long l2, Variant variant) {
        variant.getData(l2);
    }

    public static Variant win32_new(long l2) {
        Variant variant = new Variant();
        variant.setData(l2);
        return variant;
    }

    public Variant() {
        this.type = 0;
    }

    public Variant(float f) {
        this.type = (short)4;
        this.floatData = f;
    }

    public Variant(double d) {
        this.type = (short)5;
        this.doubleData = d;
    }

    public Variant(int n) {
        this.type = (short)3;
        this.intData = n;
    }

    public Variant(long l2, short s) {
        this.type = s;
        this.byRefPtr = l2;
    }

    public Variant(OleAutomation oleAutomation) {
        this.type = (short)9;
        this.dispatchData = new IDispatch(oleAutomation.getAddress());
    }

    public Variant(IDispatch iDispatch) {
        this.type = (short)9;
        this.dispatchData = iDispatch;
    }

    public Variant(IUnknown iUnknown) {
        this.type = (short)13;
        this.unknownData = iUnknown;
    }

    public Variant(long l2) {
        this.type = (short)20;
        this.longData = l2;
    }

    public Variant(String string) {
        this.type = (short)8;
        this.stringData = string;
    }

    public Variant(short s) {
        this.type = (short)2;
        this.shortData = s;
    }

    public Variant(boolean bl) {
        this.type = (short)11;
        this.booleanData = bl;
    }

    public void dispose() {
        if ((this.type & 0x4000) == 16384) {
            return;
        }
        switch (this.type) {
            case 9: {
                this.dispatchData.Release();
                break;
            }
            case 13: {
                this.unknownData.Release();
            }
        }
    }

    public OleAutomation getAutomation() {
        if (this.type == 0) {
            OLE.error(1010, -1);
        }
        if (this.type == 9) {
            return new OleAutomation(this.dispatchData);
        }
        long l2 = OS.GlobalAlloc(64, sizeof);
        long l3 = OS.GlobalAlloc(64, sizeof);
        this.getData(l2);
        int n = COM.VariantChangeType(l3, l2, (short)0, (short)9);
        if (n != 0) {
            OLE.error(1010, n);
        }
        Variant variant = new Variant();
        variant.setData(l3);
        OleAutomation oleAutomation = variant.getAutomation();
        COM.VariantClear(l2);
        OS.GlobalFree(l2);
        COM.VariantClear(l3);
        OS.GlobalFree(l3);
        return oleAutomation;
    }

    public IDispatch getDispatch() {
        if (this.type == 0) {
            OLE.error(1010, -1);
        }
        if (this.type == 9) {
            return this.dispatchData;
        }
        long l2 = OS.GlobalAlloc(64, sizeof);
        long l3 = OS.GlobalAlloc(64, sizeof);
        this.getData(l2);
        int n = COM.VariantChangeType(l3, l2, (short)0, (short)9);
        if (n != 0) {
            OLE.error(1010, n);
        }
        Variant variant = new Variant();
        variant.setData(l3);
        IDispatch iDispatch = variant.getDispatch();
        COM.VariantClear(l2);
        OS.GlobalFree(l2);
        COM.VariantClear(l3);
        OS.GlobalFree(l3);
        return iDispatch;
    }

    public boolean getBoolean() {
        if (this.type == 0) {
            OLE.error(1010, -1);
        }
        if (this.type == 11) {
            return this.booleanData;
        }
        long l2 = OS.GlobalAlloc(64, sizeof);
        long l3 = OS.GlobalAlloc(64, sizeof);
        this.getData(l2);
        int n = COM.VariantChangeType(l3, l2, (short)0, (short)11);
        if (n != 0) {
            OLE.error(1010, n);
        }
        Variant variant = new Variant();
        variant.setData(l3);
        boolean bl = variant.getBoolean();
        COM.VariantClear(l2);
        OS.GlobalFree(l2);
        COM.VariantClear(l3);
        OS.GlobalFree(l3);
        return bl;
    }

    public long getByRef() {
        if (this.type == 0) {
            OLE.error(1010, -1);
        }
        if ((this.type & 0x4000) == 16384) {
            return this.byRefPtr;
        }
        return 0L;
    }

    public byte getByte() {
        if (this.type == 0) {
            OLE.error(1010, -1);
        }
        if (this.type == 16) {
            return this.byteData;
        }
        long l2 = OS.GlobalAlloc(64, sizeof);
        long l3 = OS.GlobalAlloc(64, sizeof);
        this.getData(l2);
        int n = COM.VariantChangeType(l3, l2, (short)0, (short)16);
        if (n != 0) {
            OLE.error(1010, n);
        }
        Variant variant = new Variant();
        variant.setData(l3);
        byte by = variant.getByte();
        COM.VariantClear(l2);
        OS.GlobalFree(l2);
        COM.VariantClear(l3);
        OS.GlobalFree(l3);
        return by;
    }

    public char getChar() {
        if (this.type == 0) {
            OLE.error(1010, -1);
        }
        if (this.type == 18) {
            return this.charData;
        }
        long l2 = OS.GlobalAlloc(64, sizeof);
        long l3 = OS.GlobalAlloc(64, sizeof);
        this.getData(l2);
        int n = COM.VariantChangeType(l3, l2, (short)0, (short)18);
        if (n != 0) {
            OLE.error(1010, n);
        }
        Variant variant = new Variant();
        variant.setData(l3);
        char c = variant.getChar();
        COM.VariantClear(l2);
        OS.GlobalFree(l2);
        COM.VariantClear(l3);
        OS.GlobalFree(l3);
        return c;
    }

    void getData(long l2) {
        if (l2 == 0L) {
            OLE.error(1007);
        }
        COM.VariantInit(l2);
        if ((this.type & 0x4000) == 16384) {
            OS.MoveMemory(l2, new short[]{this.type}, 2);
            OS.MoveMemory(l2 + 8L, new long[]{this.byRefPtr}, C.PTR_SIZEOF);
            return;
        }
        switch (this.type) {
            case 0: 
            case 1: {
                OS.MoveMemory(l2, new short[]{this.type}, 2);
                break;
            }
            case 11: {
                OS.MoveMemory(l2, new short[]{this.type}, 2);
                OS.MoveMemory(l2 + 8L, new short[]{(short)(this.booleanData ? -1 : 0)}, 2);
                break;
            }
            case 16: {
                OS.MoveMemory(l2, new short[]{this.type}, 2);
                OS.MoveMemory(l2 + 8L, new byte[]{this.byteData}, 1);
                break;
            }
            case 2: {
                OS.MoveMemory(l2, new short[]{this.type}, 2);
                OS.MoveMemory(l2 + 8L, new short[]{this.shortData}, 2);
                break;
            }
            case 18: {
                OS.MoveMemory(l2, new short[]{this.type}, 2);
                OS.MoveMemory(l2 + 8L, new char[]{this.charData}, 2);
                break;
            }
            case 3: {
                OS.MoveMemory(l2, new short[]{this.type}, 2);
                OS.MoveMemory(l2 + 8L, new int[]{this.intData}, 4);
                break;
            }
            case 20: {
                OS.MoveMemory(l2, new short[]{this.type}, 2);
                OS.MoveMemory(l2 + 8L, new long[]{this.longData}, 8);
                break;
            }
            case 4: {
                OS.MoveMemory(l2, new short[]{this.type}, 2);
                OS.MoveMemory(l2 + 8L, new float[]{this.floatData}, 4);
                break;
            }
            case 5: {
                OS.MoveMemory(l2, new short[]{this.type}, 2);
                OS.MoveMemory(l2 + 8L, new double[]{this.doubleData}, 8);
                break;
            }
            case 9: {
                this.dispatchData.AddRef();
                OS.MoveMemory(l2, new short[]{this.type}, 2);
                OS.MoveMemory(l2 + 8L, new long[]{this.dispatchData.getAddress()}, C.PTR_SIZEOF);
                break;
            }
            case 13: {
                this.unknownData.AddRef();
                OS.MoveMemory(l2, new short[]{this.type}, 2);
                OS.MoveMemory(l2 + 8L, new long[]{this.unknownData.getAddress()}, C.PTR_SIZEOF);
                break;
            }
            case 8: {
                OS.MoveMemory(l2, new short[]{this.type}, 2);
                char[] cArray = this.stringData.toCharArray();
                long l3 = COM.SysAllocString(cArray);
                OS.MoveMemory(l2 + 8L, new long[]{l3}, C.PTR_SIZEOF);
                break;
            }
            default: {
                OLE.error(20);
            }
        }
    }

    public double getDouble() {
        if (this.type == 0) {
            OLE.error(1010, -1);
        }
        if (this.type == 5) {
            return this.doubleData;
        }
        long l2 = OS.GlobalAlloc(64, sizeof);
        long l3 = OS.GlobalAlloc(64, sizeof);
        this.getData(l2);
        int n = COM.VariantChangeType(l3, l2, (short)0, (short)5);
        if (n != 0) {
            OLE.error(1010, n);
        }
        Variant variant = new Variant();
        variant.setData(l3);
        double d = variant.getDouble();
        COM.VariantClear(l2);
        OS.GlobalFree(l2);
        COM.VariantClear(l3);
        OS.GlobalFree(l3);
        return d;
    }

    public float getFloat() {
        if (this.type == 0) {
            OLE.error(1010, -1);
        }
        if (this.type == 4) {
            return this.floatData;
        }
        long l2 = OS.GlobalAlloc(64, sizeof);
        long l3 = OS.GlobalAlloc(64, sizeof);
        this.getData(l2);
        int n = COM.VariantChangeType(l3, l2, (short)0, (short)4);
        if (n != 0) {
            OLE.error(1010, n);
        }
        Variant variant = new Variant();
        variant.setData(l3);
        float f = variant.getFloat();
        COM.VariantClear(l2);
        OS.GlobalFree(l2);
        COM.VariantClear(l3);
        OS.GlobalFree(l3);
        return f;
    }

    public int getInt() {
        if (this.type == 0) {
            OLE.error(1010, -1);
        }
        if (this.type == 3) {
            return this.intData;
        }
        long l2 = OS.GlobalAlloc(64, sizeof);
        long l3 = OS.GlobalAlloc(64, sizeof);
        this.getData(l2);
        int n = COM.VariantChangeType(l3, l2, (short)0, (short)3);
        if (n != 0) {
            OLE.error(1010, n);
        }
        Variant variant = new Variant();
        variant.setData(l3);
        int n2 = variant.getInt();
        COM.VariantClear(l2);
        OS.GlobalFree(l2);
        COM.VariantClear(l3);
        OS.GlobalFree(l3);
        return n2;
    }

    public long getLong() {
        if (this.type == 0) {
            OLE.error(1010, -1);
        }
        if (this.type == 20) {
            return this.longData;
        }
        long l2 = OS.GlobalAlloc(64, sizeof);
        long l3 = OS.GlobalAlloc(64, sizeof);
        this.getData(l2);
        int n = COM.VariantChangeType(l3, l2, (short)0, (short)20);
        if (n != 0) {
            OLE.error(1010, n);
        }
        Variant variant = new Variant();
        variant.setData(l3);
        long l4 = variant.getLong();
        COM.VariantClear(l2);
        OS.GlobalFree(l2);
        COM.VariantClear(l3);
        OS.GlobalFree(l3);
        return l4;
    }

    public short getShort() {
        if (this.type == 0) {
            OLE.error(1010, -1);
        }
        if (this.type == 2) {
            return this.shortData;
        }
        long l2 = OS.GlobalAlloc(64, sizeof);
        long l3 = OS.GlobalAlloc(64, sizeof);
        this.getData(l2);
        int n = COM.VariantChangeType(l3, l2, (short)0, (short)2);
        if (n != 0) {
            OLE.error(1010, n);
        }
        Variant variant = new Variant();
        variant.setData(l3);
        short s = variant.getShort();
        COM.VariantClear(l2);
        OS.GlobalFree(l2);
        COM.VariantClear(l3);
        OS.GlobalFree(l3);
        return s;
    }

    public String getString() {
        if (this.type == 0) {
            OLE.error(1010, -1);
        }
        if (this.type == 8) {
            return this.stringData;
        }
        long l2 = OS.GlobalAlloc(64, sizeof);
        long l3 = OS.GlobalAlloc(64, sizeof);
        this.getData(l2);
        int n = COM.VariantChangeType(l3, l2, (short)0, (short)8);
        if (n != 0) {
            OLE.error(1010, n);
        }
        Variant variant = new Variant();
        variant.setData(l3);
        String string = variant.getString();
        COM.VariantClear(l2);
        OS.GlobalFree(l2);
        COM.VariantClear(l3);
        OS.GlobalFree(l3);
        return string;
    }

    public short getType() {
        return this.type;
    }

    public IUnknown getUnknown() {
        if (this.type == 0) {
            OLE.error(1010, -1);
        }
        if (this.type == 13) {
            return this.unknownData;
        }
        long l2 = OS.GlobalAlloc(64, sizeof);
        long l3 = OS.GlobalAlloc(64, sizeof);
        this.getData(l2);
        int n = COM.VariantChangeType(l3, l2, (short)0, (short)13);
        if (n != 0) {
            OLE.error(1010, n);
        }
        Variant variant = new Variant();
        variant.setData(l3);
        IUnknown iUnknown = variant.getUnknown();
        COM.VariantClear(l2);
        OS.GlobalFree(l2);
        COM.VariantClear(l3);
        OS.GlobalFree(l3);
        return iUnknown;
    }

    public void setByRef(boolean bl) {
        if ((this.type & 0x4000) == 0 || (this.type & 0xB) == 0) {
            OLE.error(1010);
        }
        OS.MoveMemory(this.byRefPtr, new short[]{(short)(bl ? -1 : 0)}, 2);
    }

    public void setByRef(float f) {
        if ((this.type & 0x4000) == 0 || (this.type & 4) == 0) {
            OLE.error(1010);
        }
        OS.MoveMemory(this.byRefPtr, new float[]{f}, 4);
    }

    public void setByRef(long l2) {
        if ((this.type & 0x4000) == 0 || (this.type & 0x14) == 0) {
            OLE.error(1010);
        }
        OS.MoveMemory(this.byRefPtr, new long[]{l2}, C.PTR_SIZEOF);
    }

    public void setByRef(short s) {
        if ((this.type & 0x4000) == 0 || (this.type & 2) == 0) {
            OLE.error(1010);
        }
        OS.MoveMemory(this.byRefPtr, new short[]{s}, 2);
    }

    void setData(long l2) {
        if (l2 == 0L) {
            OLE.error(5);
        }
        short[] sArray = new short[]{0};
        OS.MoveMemory(sArray, l2, 2);
        this.type = sArray[0];
        if ((this.type & 0x4000) == 16384) {
            long[] lArray = new long[]{0L};
            OS.MoveMemory(lArray, l2 + 8L, C.PTR_SIZEOF);
            this.byRefPtr = lArray[0];
            return;
        }
        switch (this.type) {
            case 0: 
            case 1: {
                break;
            }
            case 11: {
                short[] sArray2 = new short[]{0};
                OS.MoveMemory(sArray2, l2 + 8L, 2);
                this.booleanData = sArray2[0] != 0;
                break;
            }
            case 16: {
                byte[] byArray = new byte[]{0};
                OS.MoveMemory(byArray, l2 + 8L, 1);
                this.byteData = byArray[0];
                break;
            }
            case 2: {
                short[] sArray3 = new short[]{0};
                OS.MoveMemory(sArray3, l2 + 8L, 2);
                this.shortData = sArray3[0];
                break;
            }
            case 18: {
                char[] cArray = new char[]{'\u0000'};
                OS.MoveMemory(cArray, l2 + 8L, 2);
                this.charData = cArray[0];
                break;
            }
            case 3: {
                int[] nArray = new int[]{0};
                OS.MoveMemory(nArray, l2 + 8L, 4);
                this.intData = nArray[0];
                break;
            }
            case 20: {
                long[] lArray = new long[]{0L};
                OS.MoveMemory(lArray, l2 + 8L, 8);
                this.longData = lArray[0];
                break;
            }
            case 4: {
                float[] fArray = new float[]{0.0f};
                OS.MoveMemory(fArray, l2 + 8L, 4);
                this.floatData = fArray[0];
                break;
            }
            case 5: {
                double[] dArray = new double[]{0.0};
                OS.MoveMemory(dArray, l2 + 8L, 8);
                this.doubleData = dArray[0];
                break;
            }
            case 9: {
                long[] lArray = new long[]{0L};
                OS.MoveMemory(lArray, l2 + 8L, C.PTR_SIZEOF);
                if (lArray[0] == 0L) {
                    this.type = 0;
                    break;
                }
                this.dispatchData = new IDispatch(lArray[0]);
                this.dispatchData.AddRef();
                break;
            }
            case 13: {
                long[] lArray = new long[]{0L};
                OS.MoveMemory(lArray, l2 + 8L, C.PTR_SIZEOF);
                if (lArray[0] == 0L) {
                    this.type = 0;
                    break;
                }
                this.unknownData = new IUnknown(lArray[0]);
                this.unknownData.AddRef();
                break;
            }
            case 8: {
                long[] lArray = new long[]{0L};
                OS.MoveMemory(lArray, l2 + 8L, C.PTR_SIZEOF);
                if (lArray[0] == 0L) {
                    this.type = 0;
                    break;
                }
                int n = COM.SysStringByteLen(lArray[0]);
                if (n > 0) {
                    char[] cArray = new char[(n + 1) / 2];
                    OS.MoveMemory(cArray, lArray[0], n);
                    this.stringData = new String(cArray);
                    break;
                }
                this.stringData = "";
                break;
            }
            default: {
                long l3 = OS.GlobalAlloc(64, sizeof);
                if (COM.VariantChangeType(l3, l2, (short)0, (short)5) == 0) {
                    this.setData(l3);
                } else if (COM.VariantChangeType(l3, l2, (short)0, (short)20) == 0) {
                    this.setData(l3);
                } else if (COM.VariantChangeType(l3, l2, (short)0, (short)8) == 0) {
                    this.setData(l3);
                }
                COM.VariantClear(l3);
                OS.GlobalFree(l3);
                break;
            }
        }
    }

    public String toString() {
        switch (this.type) {
            case 11: {
                return "VT_BOOL{" + this.booleanData;
            }
            case 16: {
                return "VT_I1{" + this.byteData;
            }
            case 2: {
                return "VT_I2{" + this.shortData;
            }
            case 18: {
                return "VT_UI2{" + this.charData;
            }
            case 3: {
                return "VT_I4{" + this.intData;
            }
            case 20: {
                return "VT_I8{" + this.longData;
            }
            case 4: {
                return "VT_R4{" + this.floatData;
            }
            case 5: {
                return "VT_R8{" + this.doubleData;
            }
            case 8: {
                return "VT_BSTR{" + this.stringData;
            }
            case 9: {
                return "VT_DISPATCH{" + (this.dispatchData == null ? 0L : this.dispatchData.getAddress());
            }
            case 13: {
                return "VT_UNKNOWN{" + (this.unknownData == null ? 0L : this.unknownData.getAddress());
            }
            case 0: {
                return "VT_EMPTY";
            }
            case 1: {
                return "VT_NULL";
            }
        }
        if ((this.type & 0x4000) != 0) {
            return "VT_BYREF|" + (this.type & 0xFFFFBFFF) + "{" + this.byRefPtr;
        }
        return "Unsupported Type " + this.type;
    }

    static {
        Variant.NULL.type = 1;
    }
}

