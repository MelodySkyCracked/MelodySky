/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.core;

import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.swtimpl.CommandMessage;
import chrriis.dj.nativeswing.swtimpl.NSSystemPropertySWT;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.MozillaXPCOM;
import chrriis.dj.nativeswing.swtimpl.components.core.llII;
import chrriis.dj.nativeswing.swtimpl.components.internal.INativeMozillaXPCOM;
import chrriis.dj.nativeswing.swtimpl.core.ControlCommandMessage;
import java.io.File;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Shell;
import org.mozilla.xpcom.Mozilla;

class NativeMozillaXPCOM
implements INativeMozillaXPCOM {
    private static Map idToNativeInterfaceMap = new HashMap();
    private static Map idToProxyInterfaceReferenceMap = new HashMap();
    private static Map interfaceInfoToIDMap = new HashMap();
    private static int nextNativeSideID = 1;
    private static int nextSwingSideID = -1;

    NativeMozillaXPCOM() {
    }

    @Override
    public Object getWebBrowser(JWebBrowser jWebBrowser) {
        return this.unpack(jWebBrowser.getNativeComponent().runSync(new CMN_getWebBrowser(null), new Object[0]));
    }

    @Override
    public boolean initialize() {
        String string = NSSystemPropertySWT.WEBBROWSER_XULRUNNER_HOME.get();
        if (string == null) {
            string = NSSystemPropertySWT.ORG_ECLIPSE_SWT_BROWSER_XULRUNNERPATH.get();
        } else {
            NSSystemPropertySWT.ORG_ECLIPSE_SWT_BROWSER_XULRUNNERPATH.set(string);
        }
        if (Utils.IS_MAC) {
            if (string == null) {
                string = System.getenv("XULRUNNER_HOME");
            }
            if (string == null) {
                return false;
            }
            File file = new File(string);
            if (!file.exists()) {
                return false;
            }
            Mozilla.getInstance().initialize(file);
        } else {
            Shell shell = new Shell(0);
            new Browser(shell, 32768);
            shell.dispose();
        }
        return true;
    }

    @Override
    public Object pack(Object object, boolean bl) {
        return NativeMozillaXPCOM.pack_(object, bl);
    }

    static Object pack_(Object object, boolean bl) {
        if (object == null) {
            return null;
        }
        if (object instanceof Object[]) {
            Object[] objectArray = (Object[])object;
            Object[] objectArray2 = new Object[objectArray.length];
            for (int i = 0; i < objectArray.length; ++i) {
                objectArray2[i] = NativeMozillaXPCOM.pack_(objectArray[i], bl);
            }
            return new ArrayInfo(objectArray.getClass(), objectArray2);
        }
        Package package_ = object.getClass().getPackage();
        if (package_ != null && package_.getName().equals("java.lang")) {
            return object;
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo(object);
        Integer n = (Integer)interfaceInfoToIDMap.get(interfaceInfo);
        if (n == null) {
            ArrayList arrayList = new ArrayList();
            ClassLoader classLoader = MozillaXPCOM.class.getClassLoader();
            for (Class<?> clazz : object.getClass().getInterfaces()) {
                Class<?> clazz2 = null;
                try {
                    clazz2 = Class.forName(clazz.getName(), false, classLoader);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    // empty catch block
                }
                if (clazz2 != clazz) continue;
                arrayList.add(clazz);
            }
            arrayList.add(NativeSwingProxy.class);
            n = bl ? Integer.valueOf(nextNativeSideID++) : Integer.valueOf(nextSwingSideID--);
            idToNativeInterfaceMap.put(n, object);
            interfaceInfoToIDMap.put(interfaceInfo, n);
            return new InterfaceDefinition(n, arrayList.toArray(new Class[0]), true, !bl, null);
        }
        return new InterfaceDefinition(n, null, !(object instanceof NativeSwingProxy), !bl, null);
    }

    @Override
    public Object unpack(Object object) {
        return NativeMozillaXPCOM.unpack_(object);
    }

    static Object unpack_(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof ArrayInfo) {
            ArrayInfo arrayInfo = (ArrayInfo)object;
            Class clazz = arrayInfo.getArrayClass();
            Object[] objectArray = arrayInfo.getItems();
            Object[] objectArray2 = (Object[])Array.newInstance(clazz.getComponentType(), objectArray.length);
            for (int i = 0; i < objectArray.length; ++i) {
                objectArray2[i] = NativeMozillaXPCOM.unpack_(objectArray[i]);
            }
            return objectArray2;
        }
        if (object instanceof InterfaceDefinition) {
            InterfaceDefinition interfaceDefinition = (InterfaceDefinition)object;
            Integer n = interfaceDefinition.getID();
            if (interfaceDefinition.isProxy()) {
                NativeSwingProxy nativeSwingProxy;
                WeakReference weakReference = (WeakReference)idToProxyInterfaceReferenceMap.get(n);
                NativeSwingProxy nativeSwingProxy2 = nativeSwingProxy = weakReference == null ? null : (NativeSwingProxy)weakReference.get();
                if (nativeSwingProxy == null) {
                    Class[] classArray = interfaceDefinition.getInterfaces();
                    boolean bl = interfaceDefinition.isNativeSide();
                    nativeSwingProxy = (NativeSwingProxy)Proxy.newProxyInstance(MozillaXPCOM.class.getClassLoader(), classArray, (InvocationHandler)new llII(n, bl));
                    idToProxyInterfaceReferenceMap.put(n, new WeakReference<NativeSwingProxy>(nativeSwingProxy));
                    interfaceInfoToIDMap.put(new InterfaceInfo(nativeSwingProxy), n);
                }
                return nativeSwingProxy;
            }
            return idToNativeInterfaceMap.get(n);
        }
        return object;
    }

    static Map access$200() {
        return idToNativeInterfaceMap;
    }

    static Map access$300() {
        return interfaceInfoToIDMap;
    }

    static Map access$400() {
        return idToProxyInterfaceReferenceMap;
    }

    private static class CM_disposeResources
    extends CommandMessage {
        private CM_disposeResources() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Object v = NativeMozillaXPCOM.access$200().remove(objectArray[0]);
            NativeMozillaXPCOM.access$300().remove(new InterfaceInfo(v));
            return null;
        }

        CM_disposeResources(llII llII2) {
            this();
        }
    }

    private static class CM_runMethod
    extends CommandMessage {
        private CM_runMethod() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Integer n = (Integer)objectArray[0];
            String string = (String)objectArray[1];
            Class[] classArray = (Class[])objectArray[2];
            Object[] objectArray2 = (Object[])NativeMozillaXPCOM.unpack_(objectArray[3]);
            boolean bl = (Boolean)objectArray[4];
            Object v = NativeMozillaXPCOM.access$200().get(n);
            try {
                Method method = v.getClass().getMethod(string, classArray);
                method.setAccessible(true);
                Object object = method.invoke(v, objectArray2);
                ArrayList<Object> arrayList = null;
                if (objectArray2 != null) {
                    arrayList = new ArrayList<Object>();
                    for (Object object2 : objectArray2) {
                        if (!(object2 instanceof Object[])) continue;
                        arrayList.add(NativeMozillaXPCOM.pack_(object2, bl));
                    }
                }
                return new RunMethodResult(NativeMozillaXPCOM.pack_(object, bl), arrayList == null || arrayList.isEmpty() ? null : arrayList.toArray());
            }
            catch (Exception exception) {
                throw new IllegalStateException("The method " + string + " could not be invoked on interface " + v + "!", exception);
            }
        }

        CM_runMethod(llII llII2) {
            this();
        }
    }

    private static class RunMethodResult
    implements Serializable {
        private Object result;
        private Object[] outParams;

        public RunMethodResult(Object object, Object[] objectArray) {
            this.result = object;
            this.outParams = objectArray;
        }

        public Object getResult() {
            return this.result;
        }

        public Object[] getOutParams() {
            return this.outParams;
        }
    }

    private static class ArrayInfo
    implements Serializable {
        private Class arrayClass;
        private Object[] content;

        public ArrayInfo(Class clazz, Object[] objectArray) {
            ClassLoader classLoader = MozillaXPCOM.class.getClassLoader();
            Class<?> clazz2 = null;
            while (true) {
                try {
                    clazz2 = Class.forName(clazz.getName(), false, classLoader);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    // empty catch block
                }
                if (clazz2 == clazz) break;
                clazz2 = clazz2.getSuperclass();
            }
            this.arrayClass = clazz;
            this.content = objectArray;
        }

        public Class getArrayClass() {
            return this.arrayClass;
        }

        public Object[] getItems() {
            return this.content;
        }

        public String toString() {
            return Arrays.deepToString(this.content);
        }
    }

    private static class InterfaceInfo {
        private int id;

        public InterfaceInfo(Object object) {
            this.id = System.identityHashCode(object);
        }

        public int hashCode() {
            return this.id;
        }

        public boolean equals(Object object) {
            return ((InterfaceInfo)object).id == this.id;
        }
    }

    private static interface NativeSwingProxy {
        public void finalize();
    }

    private static class InterfaceDefinition
    implements Serializable {
        private int id;
        private Class[] interfaces;
        private boolean isProxy;
        private boolean isNativeSide;

        private InterfaceDefinition(int n, Class[] classArray, boolean bl, boolean bl2) {
            this.id = n;
            this.interfaces = classArray;
            this.isProxy = bl;
            this.isNativeSide = bl2;
        }

        public int getID() {
            return this.id;
        }

        public Class[] getInterfaces() {
            return this.interfaces;
        }

        public boolean isProxy() {
            return this.isProxy;
        }

        public boolean isNativeSide() {
            return this.isNativeSide;
        }

        InterfaceDefinition(int n, Class[] classArray, boolean bl, boolean bl2, llII llII2) {
            this(n, classArray, bl, bl2);
        }
    }

    private static class CMN_getWebBrowser
    extends ControlCommandMessage {
        private CMN_getWebBrowser() {
        }

        @Override
        public Object run(Object[] objectArray) {
            return NativeMozillaXPCOM.pack_(((Browser)this.getControl()).getWebBrowser(), true);
        }

        CMN_getWebBrowser(llII llII2) {
            this();
        }
    }
}

