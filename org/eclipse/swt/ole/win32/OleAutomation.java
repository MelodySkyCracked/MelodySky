/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.ole.win32;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.DISPPARAMS;
import org.eclipse.swt.internal.ole.win32.EXCEPINFO;
import org.eclipse.swt.internal.ole.win32.FUNCDESC;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IDispatch;
import org.eclipse.swt.internal.ole.win32.ITypeInfo;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.ole.win32.TYPEATTR;
import org.eclipse.swt.internal.ole.win32.VARDESC;
import org.eclipse.swt.internal.ole.win32.VARIANT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFunctionDescription;
import org.eclipse.swt.ole.win32.OleParameterDescription;
import org.eclipse.swt.ole.win32.OlePropertyDescription;
import org.eclipse.swt.ole.win32.Variant;

public final class OleAutomation {
    private IUnknown objIUnknown;
    private IDispatch objIDispatch;
    private String exceptionDescription;
    private ITypeInfo objITypeInfo;

    OleAutomation(IDispatch iDispatch) {
        if (iDispatch == null) {
            OLE.error(1011);
        }
        this.objIDispatch = iDispatch;
        this.objIDispatch.AddRef();
        long[] lArray = new long[]{0L};
        int n = this.objIDispatch.GetTypeInfo(0, 2048, lArray);
        if (n == 0) {
            this.objITypeInfo = new ITypeInfo(lArray[0]);
        }
    }

    public OleAutomation(OleClientSite oleClientSite) {
        if (oleClientSite == null) {
            OLE.error(1011);
        }
        this.objIDispatch = oleClientSite.getAutomationObject();
        long[] lArray = new long[]{0L};
        int n = this.objIDispatch.GetTypeInfo(0, 2048, lArray);
        if (n == 0) {
            this.objITypeInfo = new ITypeInfo(lArray[0]);
        }
    }

    public OleAutomation(String string) {
        try {
            OS.OleInitialize(0L);
            GUID gUID = this.getClassID(string);
            if (gUID == null) {
                OS.OleUninitialize();
                OLE.error(1004);
            }
            int n = 5;
            long[] lArray = new long[]{0L};
            int n2 = COM.CoCreateInstance(gUID, 0L, 5, COM.IIDIUnknown, lArray);
            if (n2 != 0) {
                OS.OleUninitialize();
                OLE.error(1001, n2);
            }
            this.objIUnknown = new IUnknown(lArray[0]);
            lArray[0] = 0L;
            n2 = this.objIUnknown.QueryInterface(COM.IIDIDispatch, lArray);
            if (n2 != 0) {
                OLE.error(1003);
            }
            this.objIDispatch = new IDispatch(lArray[0]);
            lArray[0] = 0L;
            n2 = this.objIDispatch.GetTypeInfo(0, 2048, lArray);
            if (n2 == 0) {
                this.objITypeInfo = new ITypeInfo(lArray[0]);
            }
        }
        catch (SWTException sWTException) {
            this.dispose();
            throw sWTException;
        }
    }

    public void dispose() {
        if (this.objIDispatch != null) {
            this.objIDispatch.Release();
        }
        this.objIDispatch = null;
        if (this.objITypeInfo != null) {
            this.objITypeInfo.Release();
        }
        this.objITypeInfo = null;
        if (this.objIUnknown != null) {
            this.objIUnknown.Release();
            OS.OleUninitialize();
        }
        this.objIUnknown = null;
    }

    long getAddress() {
        return this.objIDispatch.getAddress();
    }

    GUID getClassID(String string) {
        int n;
        GUID gUID = new GUID();
        char[] cArray = null;
        if (string != null) {
            n = string.length();
            cArray = new char[n + 1];
            string.getChars(0, n, cArray, 0);
        }
        if (COM.CLSIDFromProgID(cArray, gUID) != 0 && (n = COM.CLSIDFromString(cArray, gUID)) != 0) {
            return null;
        }
        return gUID;
    }

    public String getHelpFile(int n) {
        if (this.objITypeInfo == null) {
            return null;
        }
        String[] stringArray = new String[]{null};
        int n2 = this.objITypeInfo.GetDocumentation(n, null, null, null, stringArray);
        if (n2 == 0) {
            return stringArray[0];
        }
        return null;
    }

    public String getDocumentation(int n) {
        if (this.objITypeInfo == null) {
            return null;
        }
        String[] stringArray = new String[]{null};
        int n2 = this.objITypeInfo.GetDocumentation(n, null, stringArray, null, null);
        if (n2 == 0) {
            return stringArray[0];
        }
        return null;
    }

    public OlePropertyDescription getPropertyDescription(int n) {
        if (this.objITypeInfo == null) {
            return null;
        }
        long[] lArray = new long[]{0L};
        int n2 = this.objITypeInfo.GetVarDesc(n, lArray);
        if (n2 != 0) {
            return null;
        }
        VARDESC vARDESC = new VARDESC();
        COM.MoveMemory(vARDESC, lArray[0], VARDESC.sizeof);
        OlePropertyDescription olePropertyDescription = new OlePropertyDescription();
        olePropertyDescription.id = vARDESC.memid;
        olePropertyDescription.name = this.getName(vARDESC.memid);
        olePropertyDescription.type = vARDESC.elemdescVar_tdesc_vt;
        if (olePropertyDescription.type == 26) {
            short[] sArray = new short[]{0};
            OS.MoveMemory(sArray, vARDESC.elemdescVar_tdesc_union + (long)C.PTR_SIZEOF, 2);
            olePropertyDescription.type = sArray[0];
        }
        olePropertyDescription.flags = vARDESC.wVarFlags;
        olePropertyDescription.kind = vARDESC.varkind;
        olePropertyDescription.description = this.getDocumentation(vARDESC.memid);
        olePropertyDescription.helpFile = this.getHelpFile(vARDESC.memid);
        this.objITypeInfo.ReleaseVarDesc(lArray[0]);
        return olePropertyDescription;
    }

    public OleFunctionDescription getFunctionDescription(int n) {
        if (this.objITypeInfo == null) {
            return null;
        }
        long[] lArray = new long[]{0L};
        int n2 = this.objITypeInfo.GetFuncDesc(n, lArray);
        if (n2 != 0) {
            return null;
        }
        FUNCDESC fUNCDESC = new FUNCDESC();
        COM.MoveMemory(fUNCDESC, lArray[0], FUNCDESC.sizeof);
        OleFunctionDescription oleFunctionDescription = new OleFunctionDescription();
        oleFunctionDescription.id = fUNCDESC.memid;
        oleFunctionDescription.optionalArgCount = fUNCDESC.cParamsOpt;
        oleFunctionDescription.invokeKind = fUNCDESC.invkind;
        oleFunctionDescription.funcKind = fUNCDESC.funckind;
        oleFunctionDescription.flags = fUNCDESC.wFuncFlags;
        oleFunctionDescription.callingConvention = fUNCDESC.callconv;
        oleFunctionDescription.documentation = this.getDocumentation(fUNCDESC.memid);
        oleFunctionDescription.helpFile = this.getHelpFile(fUNCDESC.memid);
        String[] stringArray = this.getNames(fUNCDESC.memid, fUNCDESC.cParams + 1);
        if (stringArray.length > 0) {
            oleFunctionDescription.name = stringArray[0];
        }
        oleFunctionDescription.args = new OleParameterDescription[fUNCDESC.cParams];
        for (int i = 0; i < oleFunctionDescription.args.length; ++i) {
            Object[] objectArray;
            oleFunctionDescription.args[i] = new OleParameterDescription();
            if (stringArray.length > i + 1) {
                oleFunctionDescription.args[i].name = stringArray[i + 1];
            }
            short[] sArray = new short[]{0};
            OS.MoveMemory(sArray, fUNCDESC.lprgelemdescParam + (long)(i * COM.ELEMDESC_sizeof()) + (long)C.PTR_SIZEOF, 2);
            if (sArray[0] == 26) {
                objectArray = new long[]{0L};
                OS.MoveMemory(objectArray, fUNCDESC.lprgelemdescParam + (long)(i * COM.ELEMDESC_sizeof()), C.PTR_SIZEOF);
                short[] sArray2 = new short[]{0};
                OS.MoveMemory(sArray2, objectArray[0] + (long)C.PTR_SIZEOF, 2);
                sArray[0] = (short)(sArray2[0] | 0x4000);
            }
            oleFunctionDescription.args[i].type = sArray[0];
            objectArray = new short[]{0};
            OS.MoveMemory((short[])objectArray, fUNCDESC.lprgelemdescParam + (long)(i * COM.ELEMDESC_sizeof()) + (long)COM.TYPEDESC_sizeof() + (long)C.PTR_SIZEOF, 2);
            oleFunctionDescription.args[i].flags = (short)objectArray[0];
        }
        oleFunctionDescription.returnType = fUNCDESC.elemdescFunc_tdesc_vt;
        if (oleFunctionDescription.returnType == 26) {
            short[] sArray = new short[]{0};
            OS.MoveMemory(sArray, fUNCDESC.elemdescFunc_tdesc_union + (long)C.PTR_SIZEOF, 2);
            oleFunctionDescription.returnType = sArray[0];
        }
        this.objITypeInfo.ReleaseFuncDesc(lArray[0]);
        return oleFunctionDescription;
    }

    public TYPEATTR getTypeInfoAttributes() {
        if (this.objITypeInfo == null) {
            return null;
        }
        long[] lArray = new long[]{0L};
        int n = this.objITypeInfo.GetTypeAttr(lArray);
        if (n != 0) {
            return null;
        }
        TYPEATTR tYPEATTR = new TYPEATTR();
        COM.MoveMemory(tYPEATTR, lArray[0], TYPEATTR.sizeof);
        this.objITypeInfo.ReleaseTypeAttr(lArray[0]);
        return tYPEATTR;
    }

    public String getName(int n) {
        if (this.objITypeInfo == null) {
            return null;
        }
        String[] stringArray = new String[]{null};
        int n2 = this.objITypeInfo.GetDocumentation(n, stringArray, null, null, null);
        if (n2 == 0) {
            return stringArray[0];
        }
        return null;
    }

    public String[] getNames(int n, int n2) {
        if (this.objITypeInfo == null) {
            return new String[0];
        }
        String[] stringArray = new String[n2];
        int[] nArray = new int[]{0};
        int n3 = this.objITypeInfo.GetNames(n, stringArray, n2, nArray);
        if (n3 == 0) {
            String[] stringArray2 = new String[nArray[0]];
            System.arraycopy(stringArray, 0, stringArray2, 0, nArray[0]);
            return stringArray2;
        }
        return new String[0];
    }

    public int[] getIDsOfNames(String[] stringArray) {
        int[] nArray = new int[stringArray.length];
        int n = this.objIDispatch.GetIDsOfNames(new GUID(), stringArray, stringArray.length, 2048, nArray);
        if (n != 0) {
            return null;
        }
        return nArray;
    }

    public String getLastError() {
        return this.exceptionDescription;
    }

    public Variant getProperty(int n) {
        Variant variant = new Variant();
        int n2 = this.invoke(n, 2, null, null, variant);
        return n2 == 0 ? variant : null;
    }

    public Variant getProperty(int n, Variant[] variantArray) {
        Variant variant = new Variant();
        int n2 = this.invoke(n, 2, variantArray, null, variant);
        return n2 == 0 ? variant : null;
    }

    public Variant getProperty(int n, Variant[] variantArray, int[] nArray) {
        Variant variant = new Variant();
        int n2 = this.invoke(n, 2, variantArray, nArray, variant);
        return n2 == 0 ? variant : null;
    }

    public boolean equals(Object object) {
        long l2;
        if (object == this) {
            return true;
        }
        if (!(object instanceof OleAutomation)) {
            return false;
        }
        if (this.objIDispatch == null) {
            return false;
        }
        OleAutomation oleAutomation = (OleAutomation)object;
        if (oleAutomation.objIDispatch == null) {
            return false;
        }
        long l3 = this.objIDispatch.getAddress();
        return l3 == (l2 = oleAutomation.objIDispatch.getAddress());
    }

    public Variant invoke(int n) {
        Variant variant = new Variant();
        int n2 = this.invoke(n, 1, null, null, variant);
        return n2 == 0 ? variant : null;
    }

    public Variant invoke(int n, Variant[] variantArray) {
        Variant variant = new Variant();
        int n2 = this.invoke(n, 1, variantArray, null, variant);
        return n2 == 0 ? variant : null;
    }

    public Variant invoke(int n, Variant[] variantArray, int[] nArray) {
        Variant variant = new Variant();
        int n2 = this.invoke(n, 1, variantArray, nArray, variant);
        return n2 == 0 ? variant : null;
    }

    private int invoke(int n, int n2, Variant[] variantArray, int[] nArray, Variant variant) {
        int n3;
        int n4;
        if (this.objIDispatch == null) {
            return -2147467259;
        }
        DISPPARAMS dISPPARAMS = new DISPPARAMS();
        if (variantArray != null && variantArray.length > 0) {
            dISPPARAMS.cArgs = variantArray.length;
            dISPPARAMS.rgvarg = OS.GlobalAlloc(64, VARIANT.sizeof * variantArray.length);
            n4 = 0;
            for (n3 = variantArray.length - 1; n3 >= 0; --n3) {
                variantArray[n3].getData(dISPPARAMS.rgvarg + (long)n4);
                n4 += VARIANT.sizeof;
            }
        }
        if (nArray != null && nArray.length > 0) {
            dISPPARAMS.cNamedArgs = nArray.length;
            dISPPARAMS.rgdispidNamedArgs = OS.GlobalAlloc(64, 4 * nArray.length);
            n4 = 0;
            for (n3 = nArray.length; n3 > 0; --n3) {
                OS.MoveMemory(dISPPARAMS.rgdispidNamedArgs + (long)n4, new int[]{nArray[n3 - 1]}, 4);
                n4 += 4;
            }
        }
        EXCEPINFO eXCEPINFO = new EXCEPINFO();
        int[] nArray2 = new int[]{0};
        long l2 = 0L;
        if (variant != null) {
            l2 = OS.GlobalAlloc(64, VARIANT.sizeof);
        }
        int n5 = this.objIDispatch.Invoke(n, new GUID(), 2048, n2, dISPPARAMS, l2, eXCEPINFO, nArray2);
        if (l2 != 0L) {
            variant.setData(l2);
            COM.VariantClear(l2);
            OS.GlobalFree(l2);
        }
        if (dISPPARAMS.rgdispidNamedArgs != 0L) {
            OS.GlobalFree(dISPPARAMS.rgdispidNamedArgs);
        }
        if (dISPPARAMS.rgvarg != 0L) {
            int n6 = 0;
            int n7 = variantArray.length;
            for (int i = 0; i < n7; ++i) {
                COM.VariantClear(dISPPARAMS.rgvarg + (long)n6);
                n6 += VARIANT.sizeof;
            }
            OS.GlobalFree(dISPPARAMS.rgvarg);
        }
        this.manageExcepinfo(n5, eXCEPINFO);
        return n5;
    }

    public void invokeNoReply(int n) {
        int n2 = this.invoke(n, 1, null, null, null);
        if (n2 != 0) {
            OLE.error(1014, n2);
        }
    }

    public void invokeNoReply(int n, Variant[] variantArray) {
        int n2 = this.invoke(n, 1, variantArray, null, null);
        if (n2 != 0) {
            OLE.error(1014, n2);
        }
    }

    public void invokeNoReply(int n, Variant[] variantArray, int[] nArray) {
        int n2 = this.invoke(n, 1, variantArray, nArray, null);
        if (n2 != 0) {
            OLE.error(1014, n2);
        }
    }

    private void manageExcepinfo(int n, EXCEPINFO eXCEPINFO) {
        if (n == 0) {
            this.exceptionDescription = "No Error";
            return;
        }
        if (n == -2147352567) {
            if (eXCEPINFO.bstrDescription != 0L) {
                int n2 = COM.SysStringByteLen(eXCEPINFO.bstrDescription);
                char[] cArray = new char[(n2 + 1) / 2];
                OS.MoveMemory(cArray, eXCEPINFO.bstrDescription, n2);
                this.exceptionDescription = new String(cArray);
            } else {
                this.exceptionDescription = "OLE Automation Error Exception ";
                if (eXCEPINFO.wCode != 0) {
                    this.exceptionDescription = this.exceptionDescription + "code = " + eXCEPINFO.wCode;
                } else if (eXCEPINFO.scode != 0) {
                    this.exceptionDescription = this.exceptionDescription + "code = " + eXCEPINFO.scode;
                }
            }
        } else {
            this.exceptionDescription = "OLE Automation Error HResult : " + n;
        }
        if (eXCEPINFO.bstrDescription != 0L) {
            COM.SysFreeString(eXCEPINFO.bstrDescription);
        }
        if (eXCEPINFO.bstrHelpFile != 0L) {
            COM.SysFreeString(eXCEPINFO.bstrHelpFile);
        }
        if (eXCEPINFO.bstrSource != 0L) {
            COM.SysFreeString(eXCEPINFO.bstrSource);
        }
    }

    public boolean setProperty(int n, Variant variant) {
        Variant variant2;
        int n2;
        Variant[] variantArray = new Variant[]{variant};
        int[] nArray = new int[]{-3};
        int n3 = 4;
        if ((variant.getType() & 0x4000) == 16384) {
            n3 = 8;
        }
        return (n2 = this.invoke(n, n3, variantArray, nArray, variant2 = new Variant())) == 0;
    }

    public boolean setProperty(int n, Variant[] variantArray) {
        int[] nArray = new int[]{-3};
        int n2 = 4;
        for (Variant variant : variantArray) {
            if ((variant.getType() & 0x4000) != 16384) continue;
            n2 = 8;
        }
        Variant variant = new Variant();
        int n3 = this.invoke(n, n2, variantArray, nArray, variant);
        return n3 == 0;
    }
}

