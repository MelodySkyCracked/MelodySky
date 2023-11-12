/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.CommandMessage;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.internal.INativeMozillaXPCOM;
import chrriis.dj.nativeswing.swtimpl.components.lIlIIll;
import chrriis.dj.nativeswing.swtimpl.internal.NativeCoreObjectFactory;
import org.mozilla.interfaces.nsIComponentManager;
import org.mozilla.interfaces.nsIComponentRegistrar;
import org.mozilla.interfaces.nsIServiceManager;
import org.mozilla.interfaces.nsIWebBrowser;
import org.mozilla.xpcom.XPCOMInitializationException;

public class MozillaXPCOM {
    private static INativeMozillaXPCOM nativeMozillaXPCOM = (INativeMozillaXPCOM)NativeCoreObjectFactory.create(INativeMozillaXPCOM.class, "chrriis.dj.nativeswing.swtimpl.components.core.NativeMozillaXPCOM", new Class[0], new Object[0]);

    private MozillaXPCOM() {
    }

    public static nsIWebBrowser getWebBrowser(JWebBrowser jWebBrowser) {
        return (nsIWebBrowser)nativeMozillaXPCOM.getWebBrowser(jWebBrowser);
    }

    private static Object pack(Object object, boolean bl) {
        return nativeMozillaXPCOM.pack(object, bl);
    }

    private static Object unpack(Object object) {
        return nativeMozillaXPCOM.unpack(object);
    }

    static INativeMozillaXPCOM access$000() {
        return nativeMozillaXPCOM;
    }

    static Object access$100(Object object, boolean bl) {
        return MozillaXPCOM.pack(object, bl);
    }

    static Object access$400(Object object) {
        return MozillaXPCOM.unpack(object);
    }

    public static class Mozilla {
        private static boolean isInitialized;

        private static boolean initialize() {
            if (isInitialized) {
                return false;
            }
            isInitialized = true;
            return MozillaXPCOM.access$000().initialize();
        }

        public static nsIComponentRegistrar getComponentRegistrar() {
            return (nsIComponentRegistrar)MozillaXPCOM.access$400(new CMN_getComponentRegistrar(null).syncExec(true, new Object[0]));
        }

        public static nsIComponentManager getComponentManager() {
            return (nsIComponentManager)MozillaXPCOM.access$400(new CMN_getComponentManager(null).syncExec(true, new Object[0]));
        }

        public static nsIServiceManager getServiceManager() {
            return (nsIServiceManager)MozillaXPCOM.access$400(new CMN_getServiceManager(null).syncExec(true, new Object[0]));
        }

        static boolean access$200() {
            return Mozilla.initialize();
        }

        private static class CMN_getServiceManager
        extends CommandMessage {
            private CMN_getServiceManager() {
            }

            @Override
            public Object run(Object[] objectArray) {
                try {
                    return MozillaXPCOM.access$100(org.mozilla.xpcom.Mozilla.getInstance().getServiceManager(), true);
                }
                catch (XPCOMInitializationException xPCOMInitializationException) {
                    if (!Mozilla.access$200()) {
                        throw xPCOMInitializationException;
                    }
                    return MozillaXPCOM.access$100(org.mozilla.xpcom.Mozilla.getInstance().getServiceManager(), true);
                }
            }

            CMN_getServiceManager(lIlIIll lIlIIll2) {
                this();
            }
        }

        private static class CMN_getComponentManager
        extends CommandMessage {
            private CMN_getComponentManager() {
            }

            @Override
            public Object run(Object[] objectArray) {
                try {
                    return MozillaXPCOM.access$100(org.mozilla.xpcom.Mozilla.getInstance().getComponentManager(), true);
                }
                catch (XPCOMInitializationException xPCOMInitializationException) {
                    if (!Mozilla.access$200()) {
                        throw xPCOMInitializationException;
                    }
                    return MozillaXPCOM.access$100(org.mozilla.xpcom.Mozilla.getInstance().getComponentManager(), true);
                }
            }

            CMN_getComponentManager(lIlIIll lIlIIll2) {
                this();
            }
        }

        private static class CMN_getComponentRegistrar
        extends CommandMessage {
            private CMN_getComponentRegistrar() {
            }

            @Override
            public Object run(Object[] objectArray) {
                try {
                    return MozillaXPCOM.access$100(org.mozilla.xpcom.Mozilla.getInstance().getComponentRegistrar(), true);
                }
                catch (XPCOMInitializationException xPCOMInitializationException) {
                    if (!Mozilla.access$200()) {
                        throw xPCOMInitializationException;
                    }
                    return MozillaXPCOM.access$100(org.mozilla.xpcom.Mozilla.getInstance().getComponentRegistrar(), true);
                }
            }

            CMN_getComponentRegistrar(lIlIIll lIlIIll2) {
                this();
            }
        }
    }
}

