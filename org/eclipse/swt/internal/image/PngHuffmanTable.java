/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.IOException;
import org.eclipse.swt.internal.image.PngDecodingDataStream;

public class PngHuffmanTable {
    CodeLengthInfo[] codeLengthInfo;
    int[] codeValues;
    static final int MAX_CODE_LENGTH = 15;
    static final int BAD_CODE = 0xFFFFFFF;
    static final int[] incs = new int[]{1391376, 463792, 198768, 86961, 33936, 13776, 4592, 1968, 861, 336, 112, 48, 21, 7, 3, 1};

    PngHuffmanTable(int[] nArray) {
        this.initialize(nArray);
        this.generateTable(nArray);
    }

    private void initialize(int[] nArray) {
        int n;
        this.codeValues = new int[nArray.length];
        for (n = 0; n < this.codeValues.length; ++n) {
            this.codeValues[n] = n;
        }
        this.codeLengthInfo = new CodeLengthInfo[15];
        for (n = 0; n < 15; ++n) {
            this.codeLengthInfo[n] = new CodeLengthInfo();
            this.codeLengthInfo[n].length = n;
            this.codeLengthInfo[n].baseIndex = 0;
            this.codeLengthInfo[n].min = 0xFFFFFFF;
            this.codeLengthInfo[n].max = -1;
        }
    }

    private void generateTable(int[] nArray) {
        int n;
        int n2;
        int n3;
        int n4;
        for (int i = 0; i < 16; ++i) {
            n3 = n4 = incs[i];
            while (n4 < nArray.length) {
                n2 = nArray[n4];
                n = this.codeValues[n4];
                for (int j = n4; j >= n3 && (nArray[j - n3] > n2 || nArray[j - n3] == n2 && this.codeValues[j - n3] > n); j -= n3) {
                    nArray[j] = nArray[j - n3];
                    this.codeValues[j] = this.codeValues[j - n3];
                }
                nArray[j] = n2;
                this.codeValues[j] = n;
                ++n4;
            }
        }
        int[] nArray2 = new int[nArray.length];
        n4 = 0;
        n3 = 0;
        for (n2 = 0; n2 < nArray.length; ++n2) {
            while (n4 != nArray[n2]) {
                ++n4;
                n3 <<= 1;
            }
            if (n4 == 0) continue;
            nArray2[n2] = n3++;
        }
        n2 = 0;
        for (n = 0; n < nArray.length; ++n) {
            if (n2 != nArray[n]) {
                n2 = nArray[n];
                this.codeLengthInfo[n2 - 1].baseIndex = n;
                this.codeLengthInfo[n2 - 1].min = nArray2[n];
            }
            if (n2 == 0) continue;
            this.codeLengthInfo[n2 - 1].max = nArray2[n];
        }
    }

    int getNextValue(PngDecodingDataStream pngDecodingDataStream) throws IOException {
        int n;
        int n2 = pngDecodingDataStream.getNextIdatBit();
        for (n = 0; n < 15 && n2 > this.codeLengthInfo[n].max; ++n) {
            n2 = n2 << 1 | pngDecodingDataStream.getNextIdatBit();
        }
        if (n >= 15) {
            pngDecodingDataStream.error();
        }
        int n3 = n2 - this.codeLengthInfo[n].min;
        int n4 = this.codeLengthInfo[n].baseIndex + n3;
        return this.codeValues[n4];
    }

    static class CodeLengthInfo {
        int length;
        int max;
        int min;
        int baseIndex;

        CodeLengthInfo() {
        }
    }
}

