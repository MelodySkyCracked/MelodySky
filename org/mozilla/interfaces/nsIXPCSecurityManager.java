/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIXPCSecurityManager
extends nsISupports {
    public static final String NS_IXPCSECURITYMANAGER_IID = "{31431440-f1ce-11d2-985a-006008962422}";
    public static final long HOOK_CREATE_WRAPPER = 1L;
    public static final long HOOK_CREATE_INSTANCE = 2L;
    public static final long HOOK_GET_SERVICE = 4L;
    public static final long HOOK_CALL_METHOD = 8L;
    public static final long HOOK_GET_PROPERTY = 16L;
    public static final long HOOK_SET_PROPERTY = 32L;
    public static final long HOOK_ALL = 63L;
    public static final long ACCESS_CALL_METHOD = 0L;
    public static final long ACCESS_GET_PROPERTY = 1L;
    public static final long ACCESS_SET_PROPERTY = 2L;
}

