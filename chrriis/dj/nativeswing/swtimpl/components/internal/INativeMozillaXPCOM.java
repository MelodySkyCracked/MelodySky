/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.internal;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

public interface INativeMozillaXPCOM {
    public Object getWebBrowser(JWebBrowser var1);

    public boolean initialize();

    public Object pack(Object var1, boolean var2);

    public Object unpack(Object var1);
}

