/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

final class LZWNode {
    public LZWNode left;
    public LZWNode right;
    public LZWNode children;
    public int code;
    public int prefix;
    public int suffix;

    LZWNode() {
    }
}

