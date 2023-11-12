/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.common;

import chrriis.dj.nativeswing.NSSystemProperty;
import chrriis.dj.nativeswing.common.lll;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ObjectRegistry {
    private static Thread cleanUpThread;
    private static Set registrySet;
    private static int nextThreadNumber;
    private int nextInstanceID = 1;
    private Map instanceIDToObjectReferenceMap = new HashMap();
    private static ObjectRegistry registry;

    private static void startThread(ObjectRegistry objectRegistry) {
        Set set = registrySet;
        synchronized (set) {
            registrySet.add(objectRegistry);
            if (cleanUpThread != null) {
                return;
            }
            cleanUpThread = new lll("Registry cleanup thread-" + nextThreadNumber++);
            boolean bl = "applet".equals(NSSystemProperty.DEPLOYMENT_TYPE.get());
            cleanUpThread.setDaemon(!bl);
            cleanUpThread.start();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int add(Object object) {
        int n;
        boolean bl = false;
        ObjectRegistry objectRegistry = this;
        synchronized (objectRegistry) {
            do {
                ++this.nextInstanceID;
            } while (this.instanceIDToObjectReferenceMap.containsKey(n));
            if (object != null) {
                this.instanceIDToObjectReferenceMap.put(n, new WeakReference<Object>(object));
                bl = true;
            }
        }
        if (!bl) return n;
        ObjectRegistry.startThread(this);
        return n;
    }

    public void add(Object object, int n) {
        ObjectRegistry objectRegistry = this;
        synchronized (objectRegistry) {
            Object object2 = this.get(n);
            if (object2 != null && object2 != object) {
                throw new IllegalStateException("An object is already registered with the id \"" + n + "\" for object: " + object);
            }
            this.instanceIDToObjectReferenceMap.put(n, new WeakReference<Object>(object));
        }
        ObjectRegistry.startThread(this);
    }

    public synchronized Object get(int n) {
        WeakReference weakReference = (WeakReference)this.instanceIDToObjectReferenceMap.get(n);
        if (weakReference == null) {
            return null;
        }
        Object t = weakReference.get();
        if (t == null) {
            this.instanceIDToObjectReferenceMap.remove(n);
        }
        return t;
    }

    public synchronized void remove(int n) {
        this.instanceIDToObjectReferenceMap.remove(n);
    }

    public synchronized int[] getInstanceIDs() {
        Object[] objectArray = this.instanceIDToObjectReferenceMap.keySet().toArray();
        int[] nArray = new int[objectArray.length];
        for (int i = 0; i < objectArray.length; ++i) {
            nArray[i] = (Integer)objectArray[i];
        }
        return nArray;
    }

    public static ObjectRegistry getInstance() {
        return registry;
    }

    static Set access$000() {
        return registrySet;
    }

    static Map access$100(ObjectRegistry objectRegistry) {
        return objectRegistry.instanceIDToObjectReferenceMap;
    }

    static Thread access$202(Thread thread) {
        cleanUpThread = thread;
        return cleanUpThread;
    }

    static {
        registrySet = new HashSet();
        registry = new ObjectRegistry();
    }
}

