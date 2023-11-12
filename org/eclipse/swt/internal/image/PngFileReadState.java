/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

class PngFileReadState {
    boolean readIHDR;
    boolean readPLTE;
    boolean readIDAT;
    boolean readIEND;
    boolean readTRNS;
    boolean readPixelData;

    PngFileReadState() {
    }
}

