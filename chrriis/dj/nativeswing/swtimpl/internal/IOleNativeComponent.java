/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.internal;

public interface IOleNativeComponent {
    public void invokeOleFunction(String var1, Object ... var2);

    public void invokeOleFunction(String[] var1, Object ... var2);

    public Object invokeOleFunctionWithResult(String var1, Object ... var2);

    public Object invokeOleFunctionWithResult(String[] var1, Object ... var2);

    public void setOleProperty(String var1, Object ... var2);

    public void setOleProperty(String[] var1, Object ... var2);

    public Object getOleProperty(String var1, Object ... var2);

    public Object getOleProperty(String[] var1, Object ... var2);

    public void dumpOleInterfaceDefinitions();
}

