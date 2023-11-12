/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.ImageLoaderEvent;
import org.eclipse.swt.internal.image.LEDataInputStream;
import org.eclipse.swt.internal.image.LEDataOutputStream;
import org.eclipse.swt.internal.image.LZWNode;

final class LZWCodec {
    int bitsPerPixel;
    int blockSize;
    int blockIndex;
    int currentByte;
    int bitsLeft;
    int codeSize;
    int clearCode;
    int endCode;
    int newCodes;
    int topSlot;
    int currentSlot;
    int imageWidth;
    int imageHeight;
    int imageX;
    int imageY;
    int pass;
    int line;
    int codeMask;
    byte[] block;
    byte[] lineArray;
    int[] stack;
    int[] suffix;
    int[] prefix;
    LZWNode[] nodeStack;
    LEDataInputStream inputStream;
    LEDataOutputStream outputStream;
    ImageData image;
    ImageLoader loader;
    boolean interlaced;
    static final int[] MASK_TABLE = new int[]{1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095};

    LZWCodec() {
    }

    void decode() {
        int n;
        int n2 = 0;
        int n3 = 0;
        byte[] byArray = new byte[this.imageWidth];
        int n4 = 0;
        int n5 = 0;
        while ((n = this.nextCode()) != this.endCode) {
            if (n == this.clearCode) {
                this.codeSize = this.bitsPerPixel + 1;
                this.codeMask = MASK_TABLE[this.bitsPerPixel];
                this.currentSlot = this.newCodes;
                this.topSlot = 1 << this.codeSize;
                while ((n = this.nextCode()) == this.clearCode) {
                }
                if (n == this.endCode) continue;
                n3 = n2 = n;
                byArray[n5] = (byte)n;
                if (++n5 != this.imageWidth) continue;
                this.nextPutPixels(byArray);
                n5 = 0;
                continue;
            }
            int n6 = n;
            if (n6 >= this.currentSlot) {
                n6 = n2;
                this.stack[n4] = n3;
                ++n4;
            }
            while (n6 >= this.newCodes) {
                this.stack[n4] = this.suffix[n6];
                ++n4;
                n6 = this.prefix[n6];
            }
            this.stack[n4] = n6;
            ++n4;
            if (this.currentSlot < this.topSlot) {
                this.suffix[this.currentSlot] = n3 = n6;
                this.prefix[this.currentSlot] = n2;
                ++this.currentSlot;
                n2 = n;
            }
            if (this.currentSlot >= this.topSlot && this.codeSize < 12) {
                this.codeMask = MASK_TABLE[this.codeSize];
                ++this.codeSize;
                this.topSlot += this.topSlot;
            }
            while (n4 > 0) {
                byArray[n5] = (byte)this.stack[--n4];
                if (++n5 != this.imageWidth) continue;
                this.nextPutPixels(byArray);
                n5 = 0;
            }
        }
        if (n5 != 0 && this.line < this.imageHeight) {
            this.nextPutPixels(byArray);
        }
    }

    public void decode(LEDataInputStream lEDataInputStream, ImageLoader imageLoader, ImageData imageData, boolean bl, int n) {
        this.inputStream = lEDataInputStream;
        this.loader = imageLoader;
        this.image = imageData;
        this.interlaced = bl;
        this.bitsPerPixel = n;
        this.initializeForDecoding();
        this.decode();
    }

    void encode() {
        this.nextPutCode(this.clearCode);
        int n = this.encodeLoop();
        this.nextPutCode(n);
        this.nextPutCode(this.endCode);
        this.block[0] = this.bitsLeft == 8 ? (byte)(this.blockIndex - 1) : (byte)this.blockIndex;
        this.writeBlock();
        if (this.block[0] != 0) {
            this.block[0] = 0;
            this.writeBlock();
        }
    }

    public void encode(LEDataOutputStream lEDataOutputStream, ImageData imageData) {
        this.outputStream = lEDataOutputStream;
        this.image = imageData;
        this.initializeForEncoding();
        this.encode();
    }

    int encodeLoop() {
        int n = this.nextPixel();
        while (true) {
            int n2 = n;
            LZWNode lZWNode = this.nodeStack[n2];
            boolean bl = true;
            n = this.nextPixel();
            if (n < 0) {
                return n2;
            }
            while (bl && lZWNode.children != null) {
                lZWNode = lZWNode.children;
                while (bl && lZWNode.suffix != n) {
                    if (n < lZWNode.suffix) {
                        if (lZWNode.left == null) {
                            lZWNode.left = new LZWNode();
                            bl = false;
                        }
                        lZWNode = lZWNode.left;
                        continue;
                    }
                    if (lZWNode.right == null) {
                        lZWNode.right = new LZWNode();
                        bl = false;
                    }
                    lZWNode = lZWNode.right;
                }
                if (!bl) continue;
                n2 = lZWNode.code;
                n = this.nextPixel();
                if (n >= 0) continue;
                return n2;
            }
            if (bl) {
                lZWNode = lZWNode.children = new LZWNode();
            }
            lZWNode.children = null;
            lZWNode.left = null;
            lZWNode.right = null;
            lZWNode.code = this.currentSlot++;
            lZWNode.prefix = n2;
            lZWNode.suffix = n;
            this.nextPutCode(n2);
            if (this.currentSlot < 4096) {
                if (this.currentSlot <= this.topSlot) continue;
                ++this.codeSize;
                this.codeMask = MASK_TABLE[this.codeSize - 1];
                this.topSlot *= 2;
                continue;
            }
            this.nextPutCode(this.clearCode);
            for (LZWNode lZWNode2 : this.nodeStack) {
                lZWNode2.children = null;
            }
            this.codeSize = this.bitsPerPixel + 1;
            this.codeMask = MASK_TABLE[this.codeSize - 1];
            this.currentSlot = this.newCodes;
            this.topSlot = 1 << this.codeSize;
        }
    }

    void initializeForDecoding() {
        int n;
        this.pass = 1;
        this.line = 0;
        this.codeSize = this.bitsPerPixel + 1;
        this.topSlot = 1 << this.codeSize;
        this.clearCode = 1 << this.bitsPerPixel;
        this.endCode = this.clearCode + 1;
        this.currentSlot = n = this.endCode + 1;
        this.newCodes = n;
        this.currentByte = -1;
        boolean bl = false;
        this.bitsLeft = 0;
        this.blockSize = 0;
        this.blockIndex = 0;
        this.codeMask = MASK_TABLE[this.codeSize - 1];
        this.stack = new int[4096];
        this.suffix = new int[4096];
        this.prefix = new int[4096];
        this.block = new byte[256];
        this.imageWidth = this.image.width;
        this.imageHeight = this.image.height;
    }

    void initializeForEncoding() {
        int n;
        this.interlaced = false;
        this.bitsPerPixel = this.image.depth;
        this.codeSize = this.bitsPerPixel + 1;
        this.topSlot = 1 << this.codeSize;
        this.clearCode = 1 << this.bitsPerPixel;
        this.endCode = this.clearCode + 1;
        this.currentSlot = n = this.endCode + 1;
        this.newCodes = n;
        this.bitsLeft = 8;
        this.currentByte = 0;
        this.blockIndex = 1;
        this.blockSize = 255;
        byte[] byArray = new byte[this.blockSize];
        this.block = byArray;
        byArray[0] = (byte)(this.blockSize - 1);
        this.nodeStack = new LZWNode[1 << this.bitsPerPixel];
        for (int i = 0; i < this.nodeStack.length; ++i) {
            LZWNode lZWNode = new LZWNode();
            lZWNode.code = i + 1;
            lZWNode.prefix = -1;
            lZWNode.suffix = i + 1;
            this.nodeStack[i] = lZWNode;
        }
        this.imageWidth = this.image.width;
        this.imageHeight = this.image.height;
        this.imageY = -1;
        this.lineArray = new byte[this.imageWidth];
        this.imageX = this.imageWidth + 1;
    }

    int nextCode() {
        int n;
        if (this.bitsLeft == 0) {
            if (this.blockIndex >= this.blockSize) {
                this.blockSize = this.readBlock();
                this.blockIndex = 0;
                if (this.blockSize == 0) {
                    return this.endCode;
                }
            }
            ++this.blockIndex;
            this.currentByte = this.block[this.blockIndex] & 0xFF;
            this.bitsLeft = 8;
            n = this.currentByte;
        } else {
            int n2 = this.bitsLeft - 8;
            n = n2 < 0 ? this.currentByte >> 0 - n2 : this.currentByte << n2;
        }
        while (this.codeSize > this.bitsLeft) {
            if (this.blockIndex >= this.blockSize) {
                this.blockSize = this.readBlock();
                this.blockIndex = 0;
                if (this.blockSize == 0) {
                    return this.endCode;
                }
            }
            ++this.blockIndex;
            this.currentByte = this.block[this.blockIndex] & 0xFF;
            n += this.currentByte << this.bitsLeft;
            this.bitsLeft += 8;
        }
        this.bitsLeft -= this.codeSize;
        return n & this.codeMask;
    }

    int nextPixel() {
        ++this.imageX;
        if (this.imageX > this.imageWidth) {
            ++this.imageY;
            if (this.imageY >= this.imageHeight) {
                return -1;
            }
            this.nextPixels(this.lineArray, this.imageWidth);
            this.imageX = 1;
        }
        return this.lineArray[this.imageX - 1] & 0xFF;
    }

    void nextPixels(byte[] byArray, int n) {
        if (this.image.depth == 8) {
            System.arraycopy(this.image.data, this.imageY * this.image.bytesPerLine, byArray, 0, n);
        } else {
            this.image.getPixels(0, this.imageY, n, byArray, 0);
        }
    }

    void nextPutCode(int n) {
        int n2 = n;
        int n3 = this.codeSize;
        int n4 = n2 & MASK_TABLE[this.bitsLeft - 1];
        this.currentByte |= n4 << 8 - this.bitsLeft;
        this.block[this.blockIndex] = (byte)this.currentByte;
        if ((n3 -= this.bitsLeft) < 1) {
            this.bitsLeft -= this.codeSize;
            if (this.bitsLeft == 0) {
                this.bitsLeft = 8;
                ++this.blockIndex;
                if (this.blockIndex >= this.blockSize) {
                    this.writeBlock();
                    this.blockIndex = 1;
                }
                this.currentByte = 0;
            }
            return;
        }
        n2 >>= this.bitsLeft;
        ++this.blockIndex;
        if (this.blockIndex >= this.blockSize) {
            this.writeBlock();
            this.blockIndex = 1;
        }
        while (n3 >= 8) {
            this.currentByte = n2 & 0xFF;
            this.block[this.blockIndex] = (byte)this.currentByte;
            n2 >>= 8;
            n3 -= 8;
            ++this.blockIndex;
            if (this.blockIndex < this.blockSize) continue;
            this.writeBlock();
            this.blockIndex = 1;
        }
        this.bitsLeft = 8 - n3;
        this.currentByte = n2;
        this.block[this.blockIndex] = (byte)this.currentByte;
    }

    void nextPutPixels(byte[] byArray) {
        if (this.image.depth == 8) {
            int n = this.line * this.image.bytesPerLine;
            System.arraycopy(byArray, 0, this.image.data, n, this.imageWidth);
        } else {
            this.image.setPixels(0, this.line, this.imageWidth, byArray, 0);
        }
        if (this.interlaced) {
            if (this.pass == 1) {
                this.copyRow(byArray, 7);
                this.line += 8;
            } else if (this.pass == 2) {
                this.copyRow(byArray, 3);
                this.line += 8;
            } else if (this.pass == 3) {
                this.copyRow(byArray, 1);
                this.line += 4;
            } else if (this.pass == 4) {
                this.line += 2;
            } else if (this.pass == 5) {
                this.line += 0;
            }
            if (this.line >= this.imageHeight) {
                ++this.pass;
                if (this.pass == 2) {
                    this.line = 4;
                } else if (this.pass == 3) {
                    this.line = 2;
                } else if (this.pass == 4) {
                    this.line = 1;
                } else if (this.pass == 5) {
                    this.line = 0;
                }
                if (this.pass < 5 && this.loader.hasListeners()) {
                    ImageData imageData = (ImageData)this.image.clone();
                    this.loader.notifyListeners(new ImageLoaderEvent(this.loader, imageData, this.pass - 2, false));
                }
            }
            if (this.line >= this.imageHeight) {
                this.line = 0;
            }
        } else {
            ++this.line;
        }
    }

    void copyRow(byte[] byArray, int n) {
        for (int i = 1; i <= n; ++i) {
            if (this.line + i >= this.imageHeight) continue;
            this.image.setPixels(0, this.line + i, this.imageWidth, byArray, 0);
        }
    }

    int readBlock() {
        int n = -1;
        try {
            n = this.inputStream.read();
            if (n == -1) {
                SWT.error(40);
            }
            this.block[0] = (byte)n;
            if ((n = this.inputStream.read(this.block, 1, n)) == -1) {
                SWT.error(40);
            }
        }
        catch (Exception exception) {
            SWT.error(39, exception);
        }
        return n;
    }

    void writeBlock() {
        try {
            this.outputStream.write(this.block, 0, (this.block[0] & 0xFF) + 1);
        }
        catch (Exception exception) {
            SWT.error(39, exception);
        }
    }
}

