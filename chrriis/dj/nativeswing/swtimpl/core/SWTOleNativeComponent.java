/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.core;

import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.swtimpl.core.ControlCommandMessage;
import chrriis.dj.nativeswing.swtimpl.core.SWTNativeComponent;
import chrriis.dj.nativeswing.swtimpl.core.lIIll;
import chrriis.dj.nativeswing.swtimpl.core.lIlIIl;
import chrriis.dj.nativeswing.swtimpl.core.llIlII;
import chrriis.dj.nativeswing.swtimpl.internal.IOleNativeComponent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.OleFunctionDescription;
import org.eclipse.swt.ole.win32.OleParameterDescription;
import org.eclipse.swt.ole.win32.Variant;

public abstract class SWTOleNativeComponent
extends SWTNativeComponent
implements IOleNativeComponent {
    protected static void configureOleFrame(OleClientSite oleClientSite, OleFrame oleFrame) {
        oleFrame.setData("NS_site", oleClientSite);
    }

    protected static OleClientSite getSite(OleFrame oleFrame) {
        OleClientSite oleClientSite = (OleClientSite)oleFrame.getData("NS_site");
        if (oleClientSite == null) {
            throw new IllegalStateException("The OleNativeComponent is not properly initialized! You need to call configureOleFrame() after the site creation.");
        }
        return oleClientSite;
    }

    @Override
    public void invokeOleFunction(String string, Object ... objectArray) {
        this.invokeOleFunction(new String[]{string}, objectArray);
    }

    @Override
    public void invokeOleFunction(String[] stringArray, Object ... objectArray) {
        this.runAsync(new CMN_invokeOleFunction(null), false, stringArray, objectArray);
    }

    @Override
    public Object invokeOleFunctionWithResult(String string, Object ... objectArray) {
        return this.invokeOleFunctionWithResult(new String[]{string}, objectArray);
    }

    @Override
    public Object invokeOleFunctionWithResult(String[] stringArray, Object ... objectArray) {
        return this.runSync(new CMN_invokeOleFunction(null), true, stringArray, objectArray);
    }

    @Override
    public void setOleProperty(String string, Object ... objectArray) {
        this.setOleProperty(new String[]{string}, objectArray);
    }

    @Override
    public void setOleProperty(String[] stringArray, Object ... objectArray) {
        this.runAsync(new CMN_setOleProperty(null), stringArray, objectArray);
    }

    @Override
    public Object getOleProperty(String string, Object ... objectArray) {
        return this.getOleProperty(new String[]{string}, objectArray);
    }

    @Override
    public Object getOleProperty(String[] stringArray, Object ... objectArray) {
        return this.runSync(new CMN_getOleProperty(null), stringArray, objectArray);
    }

    protected static Variant createVariant(Object object) {
        if (object instanceof Boolean) {
            return new Variant((Boolean)object);
        }
        if (object instanceof Short) {
            return new Variant((Short)object);
        }
        if (object instanceof Integer) {
            return new Variant((Integer)object);
        }
        if (object instanceof Long) {
            return new Variant((Long)object);
        }
        if (object instanceof Float) {
            return new Variant(((Float)object).floatValue());
        }
        if (object instanceof Double) {
            return new Variant((Double)object);
        }
        if (object instanceof String || object == null) {
            return new Variant((String)object);
        }
        throw new IllegalArgumentException("The value could not be converted to a Variant: " + object);
    }

    protected static Object getVariantValue(Variant variant) {
        if (variant == null) {
            return null;
        }
        switch (variant.getType()) {
            case 11: {
                return variant.getBoolean();
            }
            case 2: {
                return variant.getShort();
            }
            case 3: {
                return variant.getInt();
            }
            case 20: {
                return variant.getLong();
            }
            case 4: {
                return Float.valueOf(variant.getFloat());
            }
            case 5: {
                return variant.getDouble();
            }
            case 8: {
                return variant.getString();
            }
        }
        throw new IllegalArgumentException("The value could not be converted from a Variant: " + variant);
    }

    private static void dispose(Variant variant) {
        if (variant == null) {
            return;
        }
        variant.dispose();
    }

    @Override
    public void dumpOleInterfaceDefinitions() {
        this.runSync(new CMN_dumpOleInterfaceDefinitions(null), new Object[0]);
    }

    static void access$000(Variant variant) {
        SWTOleNativeComponent.dispose(variant);
    }

    private static class CMN_dumpOleInterfaceDefinitions
    extends ControlCommandMessage {
        private Map oleTypeToDescriptionMap;

        private CMN_dumpOleInterfaceDefinitions() {
        }

        private void dumpOleInterfaceDefinitions(StringBuilder stringBuilder, OleAutomation oleAutomation, int n) {
            String string;
            Object object;
            OleFunctionDescription oleFunctionDescription2;
            ArrayList<OleFunctionDescription> arrayList = new ArrayList<OleFunctionDescription>();
            int n2 = 0;
            while ((oleFunctionDescription2 = oleAutomation.getFunctionDescription(n2)) != null) {
                arrayList.add(oleFunctionDescription2);
                ++n2;
            }
            Collections.sort(arrayList, new lIIll(this));
            for (OleFunctionDescription oleFunctionDescription2 : arrayList) {
                int n3;
                for (n3 = 0; n3 < n; ++n3) {
                    stringBuilder.append("  ");
                }
                stringBuilder.append(oleFunctionDescription2.name).append("(");
                for (n3 = 0; n3 < oleFunctionDescription2.args.length; ++n3) {
                    object = oleFunctionDescription2.args[n3];
                    if (n3 > 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(this.getTypeDescription(((OleParameterDescription)object).type)).append(' ').append(((OleParameterDescription)object).name == null ? "arg" + n3 : ((OleParameterDescription)object).name);
                }
                stringBuilder.append("): ").append(this.getTypeDescription(oleFunctionDescription2.returnType)).append(Utils.LINE_SEPARATOR);
            }
            ArrayList arrayList2 = new ArrayList();
            int n4 = 1;
            while ((string = oleAutomation.getName(n4)) != null) {
                arrayList2.add(string);
                ++n4;
            }
            Collections.sort(arrayList2, new llIlII(this));
            Iterator iterator = arrayList2.iterator();
            while (iterator.hasNext()) {
                string = (String)iterator.next();
                for (int i = 0; i < n; ++i) {
                    stringBuilder.append("  ");
                }
                stringBuilder.append(string).append(Utils.LINE_SEPARATOR);
                object = oleAutomation.getProperty(oleAutomation.getIDsOfNames(new String[]{string})[0]);
                if (object != null && ((Variant)object).getType() == 9) {
                    OleAutomation oleAutomation2 = ((Variant)object).getAutomation();
                    this.dumpOleInterfaceDefinitions(stringBuilder, oleAutomation2, n + 1);
                    oleAutomation2.dispose();
                }
                SWTOleNativeComponent.access$000((Variant)object);
            }
        }

        private String getTypeDescription(short s) {
            String string = (String)this.oleTypeToDescriptionMap.get(s);
            if (string == null) {
                string = '[' + String.valueOf(s) + ']';
            }
            return string;
        }

        @Override
        public Object run(Object[] objectArray) {
            this.oleTypeToDescriptionMap = new HashMap();
            for (Field field : OLE.class.getDeclaredFields()) {
                String string = field.getName();
                Short s = null;
                if (string.startsWith("VT_")) {
                    try {
                        s = (Short)field.get(null);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if (s == null) continue;
                String string2 = string.substring(3).toLowerCase(Locale.ENGLISH);
                this.oleTypeToDescriptionMap.put(s, string2);
            }
            OleAutomation oleAutomation = new OleAutomation(SWTOleNativeComponent.getSite((OleFrame)this.getControl()));
            StringBuilder stringBuilder = new StringBuilder();
            this.dumpOleInterfaceDefinitions(stringBuilder, oleAutomation, 0);
            oleAutomation.dispose();
            System.out.print(stringBuilder.toString());
            return null;
        }

        CMN_dumpOleInterfaceDefinitions(lIlIIl lIlIIl2) {
            this();
        }
    }

    private static class CMN_getOleProperty
    extends ControlCommandMessage {
        private CMN_getOleProperty() {
        }

        @Override
        public Object run(Object[] objectArray) {
            String[] stringArray = (String[])objectArray[0];
            Variant[] variantArray = new OleAutomation(SWTOleNativeComponent.getSite((OleFrame)this.getControl()));
            for (int i = 0; i < stringArray.length; ++i) {
                Variant[] variantArray2;
                Object object;
                int[] nArray = variantArray.getIDsOfNames(new String[]{stringArray[i]});
                if (nArray == null) {
                    variantArray.dispose();
                    return null;
                }
                if (i == stringArray.length - 1) {
                    object = (Object[])objectArray[1];
                    variantArray2 = new Variant[((Object[])object).length];
                    for (int j = 0; j < ((Object)object).length; ++j) {
                        variantArray2[j] = SWTOleNativeComponent.createVariant(object[j]);
                    }
                    Variant variant = variantArray.getProperty(nArray[0], variantArray2);
                    for (Variant variant2 : variantArray2) {
                        SWTOleNativeComponent.access$000(variant2);
                    }
                    Object object2 = SWTOleNativeComponent.getVariantValue(variant);
                    SWTOleNativeComponent.access$000(variant);
                    variantArray.dispose();
                    return object2;
                }
                object = variantArray.getProperty(nArray[0]);
                variantArray2 = ((Variant)object).getAutomation();
                ((Variant)object).dispose();
                variantArray.dispose();
                variantArray = variantArray2;
            }
            variantArray.dispose();
            return null;
        }

        CMN_getOleProperty(lIlIIl lIlIIl2) {
            this();
        }
    }

    private static class CMN_setOleProperty
    extends ControlCommandMessage {
        private CMN_setOleProperty() {
        }

        @Override
        public Object run(Object[] objectArray) {
            String[] stringArray = (String[])objectArray[0];
            Variant[] variantArray = new OleAutomation(SWTOleNativeComponent.getSite((OleFrame)this.getControl()));
            for (int i = 0; i < stringArray.length; ++i) {
                Variant[] variantArray2;
                Object object;
                int[] nArray = variantArray.getIDsOfNames(new String[]{stringArray[i]});
                if (nArray == null) {
                    variantArray.dispose();
                    return false;
                }
                if (i == stringArray.length - 1) {
                    boolean bl;
                    object = (Object[])objectArray[1];
                    variantArray2 = new Variant[((Object[])object).length];
                    for (bl = false; bl < ((Object)object).length; bl += 1) {
                        variantArray2[bl] = SWTOleNativeComponent.createVariant(object[bl]);
                    }
                    bl = variantArray.setProperty(nArray[0], variantArray2);
                    for (Variant variant : variantArray2) {
                        SWTOleNativeComponent.access$000(variant);
                    }
                    variantArray.dispose();
                    return bl;
                }
                object = variantArray.getProperty(nArray[0]);
                variantArray2 = ((Variant)object).getAutomation();
                ((Variant)object).dispose();
                variantArray.dispose();
                variantArray = variantArray2;
            }
            variantArray.dispose();
            return false;
        }

        CMN_setOleProperty(lIlIIl lIlIIl2) {
            this();
        }
    }

    private static class CMN_invokeOleFunction
    extends ControlCommandMessage {
        private CMN_invokeOleFunction() {
        }

        @Override
        public Object run(Object[] objectArray) {
            String[] stringArray = (String[])objectArray[1];
            Variant[] variantArray = new OleAutomation(SWTOleNativeComponent.getSite((OleFrame)this.getControl()));
            for (int i = 0; i < stringArray.length; ++i) {
                Variant[] variantArray2;
                Object object;
                int[] nArray = variantArray.getIDsOfNames(new String[]{stringArray[i]});
                if (nArray == null) {
                    variantArray.dispose();
                    return null;
                }
                if (i == stringArray.length - 1) {
                    Object object2;
                    object = (Object[])objectArray[2];
                    variantArray2 = new Variant[((Object[])object).length];
                    for (int j = 0; j < ((Object)object).length; ++j) {
                        variantArray2[j] = SWTOleNativeComponent.createVariant(object[j]);
                    }
                    if (((Boolean)objectArray[0]).booleanValue()) {
                        Variant[] variantArray3 = variantArray.invoke(nArray[0], variantArray2);
                        object2 = SWTOleNativeComponent.getVariantValue((Variant)variantArray3);
                        SWTOleNativeComponent.access$000((Variant)variantArray3);
                    } else {
                        object2 = null;
                        variantArray.invokeNoReply(nArray[0], variantArray2);
                    }
                    for (Variant variant : variantArray2) {
                        SWTOleNativeComponent.access$000(variant);
                    }
                    variantArray.dispose();
                    return object2;
                }
                object = variantArray.getProperty(nArray[0]);
                variantArray2 = ((Variant)object).getAutomation();
                ((Variant)object).dispose();
                variantArray.dispose();
                variantArray = variantArray2;
            }
            variantArray.dispose();
            return null;
        }

        CMN_invokeOleFunction(lIlIIl lIlIIl2) {
            this();
        }
    }
}

