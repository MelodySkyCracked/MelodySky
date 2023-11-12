/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.internal;

import chrriis.dj.nativeswing.swtimpl.ApplicationMessageHandler;
import chrriis.dj.nativeswing.swtimpl.Message;
import chrriis.dj.nativeswing.swtimpl.NativeInterfaceConfiguration;
import chrriis.dj.nativeswing.swtimpl.NativeInterfaceListener;
import java.io.PrintStream;
import java.io.PrintWriter;

public interface ISWTNativeInterface {
    public boolean isOpen_();

    public void close_();

    public NativeInterfaceConfiguration getConfiguration_();

    public boolean isInitialized_();

    public boolean isInProcess_();

    public void initialize_();

    public void printStackTraces_();

    public void printStackTraces_(PrintStream var1);

    public void printStackTraces_(PrintWriter var1);

    public void open_();

    public Object syncSend_(boolean var1, Message var2);

    public void asyncSend_(boolean var1, Message var2);

    public boolean isOutProcessNativeSide_();

    public boolean isUIThread_(boolean var1);

    public void runEventPump_();

    public boolean isEventPumpRunning_();

    public void addNativeInterfaceListener_(NativeInterfaceListener var1);

    public void removeNativeInterfaceListener_(NativeInterfaceListener var1);

    public NativeInterfaceListener[] getNativeInterfaceListeners_();

    public void setApplicationMessageHandler_(ApplicationMessageHandler var1);

    public void main_(String[] var1) throws Exception;
}

