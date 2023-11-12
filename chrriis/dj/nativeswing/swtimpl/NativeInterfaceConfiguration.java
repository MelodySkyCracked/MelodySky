/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl;

import chrriis.dj.nativeswing.swtimpl.PeerVMProcessFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NativeInterfaceConfiguration {
    private boolean isNativeSideRespawnedOnError = true;
    private List nativeClassPathReferenceClassList = new ArrayList();
    private List nativeClassPathReferenceResourceList = new ArrayList();
    private String[] peerVMParams;
    private PeerVMProcessFactory peerVMProcessFactory;

    NativeInterfaceConfiguration() {
    }

    public void setPeerVMProcessFactory(PeerVMProcessFactory peerVMProcessFactory) {
        this.peerVMProcessFactory = peerVMProcessFactory;
    }

    public PeerVMProcessFactory getPeerVMProcessFactory() {
        return this.peerVMProcessFactory;
    }

    public void setNativeSideRespawnedOnError(boolean bl) {
        this.isNativeSideRespawnedOnError = bl;
    }

    public boolean isNativeSideRespawnedOnError() {
        return this.isNativeSideRespawnedOnError;
    }

    public void addNativeClassPathReferenceClasses(Class ... classArray) {
        this.nativeClassPathReferenceClassList.addAll(Arrays.asList(classArray));
    }

    Class[] getNativeClassPathReferenceClasses() {
        return this.nativeClassPathReferenceClassList.toArray(new Class[0]);
    }

    public void addNativeClassPathReferenceResources(String ... stringArray) {
        this.nativeClassPathReferenceResourceList.addAll(Arrays.asList(stringArray));
    }

    String[] getNativeClassPathReferenceResources() {
        return this.nativeClassPathReferenceResourceList.toArray(new String[0]);
    }

    public void setPeerVMParams(String ... stringArray) {
        this.peerVMParams = stringArray;
    }

    String[] getPeerVMParams() {
        return this.peerVMParams;
    }
}

