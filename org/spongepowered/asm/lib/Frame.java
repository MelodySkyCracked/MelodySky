/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.Item;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;

final class Frame {
    static final int DIM = -268435456;
    static final int ARRAY_OF = 0x10000000;
    static final int ELEMENT_OF = -268435456;
    static final int KIND = 0xF000000;
    static final int TOP_IF_LONG_OR_DOUBLE = 0x800000;
    static final int VALUE = 0x7FFFFF;
    static final int BASE_KIND = 0xFF00000;
    static final int BASE_VALUE = 1048575;
    static final int BASE = 0x1000000;
    static final int OBJECT = 0x1700000;
    static final int UNINITIALIZED = 0x1800000;
    private static final int LOCAL = 0x2000000;
    private static final int STACK = 0x3000000;
    static final int TOP = 0x1000000;
    static final int BOOLEAN = 0x1000009;
    static final int BYTE = 0x100000A;
    static final int CHAR = 0x100000B;
    static final int SHORT = 0x100000C;
    static final int INTEGER = 0x1000001;
    static final int FLOAT = 0x1000002;
    static final int DOUBLE = 0x1000003;
    static final int LONG = 0x1000004;
    static final int NULL = 0x1000005;
    static final int UNINITIALIZED_THIS = 0x1000006;
    static final int[] SIZE;
    Label owner;
    int[] inputLocals;
    int[] inputStack;
    private int[] outputLocals;
    private int[] outputStack;
    private int outputStackTop;
    private int initializationCount;
    private int[] initializations;

    Frame() {
    }

    private int get(int n) {
        if (this.outputLocals == null || n >= this.outputLocals.length) {
            return 0x2000000 | n;
        }
        int n2 = this.outputLocals[n];
        if (n2 == 0) {
            n2 = this.outputLocals[n] = 0x2000000 | n;
        }
        return n2;
    }

    private void set(int n, int n2) {
        int n3;
        if (this.outputLocals == null) {
            this.outputLocals = new int[10];
        }
        if (n >= (n3 = this.outputLocals.length)) {
            int[] nArray = new int[Math.max(n + 1, 2 * n3)];
            System.arraycopy(this.outputLocals, 0, nArray, 0, n3);
            this.outputLocals = nArray;
        }
        this.outputLocals[n] = n2;
    }

    private void push(int n) {
        int n2;
        if (this.outputStack == null) {
            this.outputStack = new int[10];
        }
        if (this.outputStackTop >= (n2 = this.outputStack.length)) {
            int[] nArray = new int[Math.max(this.outputStackTop + 1, 2 * n2)];
            System.arraycopy(this.outputStack, 0, nArray, 0, n2);
            this.outputStack = nArray;
        }
        this.outputStack[this.outputStackTop++] = n;
        int n3 = this.owner.inputStackTop + this.outputStackTop;
        if (n3 > this.owner.outputStackMax) {
            this.owner.outputStackMax = n3;
        }
    }

    private void push(ClassWriter classWriter, String string) {
        int n = Frame.type(classWriter, string);
        if (n != 0) {
            this.push(n);
            if (n == 0x1000004 || n == 0x1000003) {
                this.push(0x1000000);
            }
        }
    }

    private static int type(ClassWriter classWriter, String string) {
        int n;
        int n2 = string.charAt(0) == '(' ? string.indexOf(41) + 1 : 0;
        switch (string.charAt(n2)) {
            case 'V': {
                return 0;
            }
            case 'B': 
            case 'C': 
            case 'I': 
            case 'S': 
            case 'Z': {
                return 0x1000001;
            }
            case 'F': {
                return 0x1000002;
            }
            case 'J': {
                return 0x1000004;
            }
            case 'D': {
                return 0x1000003;
            }
            case 'L': {
                String string2 = string.substring(n2 + 1, string.length() - 1);
                return 0x1700000 | classWriter.addType(string2);
            }
        }
        int n3 = n2 + 1;
        while (string.charAt(n3) == '[') {
            ++n3;
        }
        switch (string.charAt(n3)) {
            case 'Z': {
                n = 0x1000009;
                break;
            }
            case 'C': {
                n = 0x100000B;
                break;
            }
            case 'B': {
                n = 0x100000A;
                break;
            }
            case 'S': {
                n = 0x100000C;
                break;
            }
            case 'I': {
                n = 0x1000001;
                break;
            }
            case 'F': {
                n = 0x1000002;
                break;
            }
            case 'J': {
                n = 0x1000004;
                break;
            }
            case 'D': {
                n = 0x1000003;
                break;
            }
            default: {
                String string3 = string.substring(n3 + 1, string.length() - 1);
                n = 0x1700000 | classWriter.addType(string3);
            }
        }
        return n3 - n2 << 28 | n;
    }

    private int pop() {
        if (this.outputStackTop > 0) {
            return this.outputStack[--this.outputStackTop];
        }
        return 0x3000000 | -(--this.owner.inputStackTop);
    }

    private void pop(int n) {
        if (this.outputStackTop >= n) {
            this.outputStackTop -= n;
        } else {
            this.owner.inputStackTop -= n - this.outputStackTop;
            this.outputStackTop = 0;
        }
    }

    private void pop(String string) {
        char c = string.charAt(0);
        if (c == '(') {
            this.pop((Type.getArgumentsAndReturnSizes(string) >> 2) - 1);
        } else if (c == 'J' || c == 'D') {
            this.pop(2);
        } else {
            this.pop(1);
        }
    }

    private void init(int n) {
        int n2;
        if (this.initializations == null) {
            this.initializations = new int[2];
        }
        if (this.initializationCount >= (n2 = this.initializations.length)) {
            int[] nArray = new int[Math.max(this.initializationCount + 1, 2 * n2)];
            System.arraycopy(this.initializations, 0, nArray, 0, n2);
            this.initializations = nArray;
        }
        this.initializations[this.initializationCount++] = n;
    }

    private int init(ClassWriter classWriter, int n) {
        int n2;
        if (n == 0x1000006) {
            n2 = 0x1700000 | classWriter.addType(classWriter.thisName);
        } else if ((n & 0xFFF00000) == 0x1800000) {
            String string = classWriter.typeTable[n & 0xFFFFF].strVal1;
            n2 = 0x1700000 | classWriter.addType(string);
        } else {
            return n;
        }
        for (int i = 0; i < this.initializationCount; ++i) {
            int n3 = this.initializations[i];
            int n4 = n3 & 0xF0000000;
            int n5 = n3 & 0xF000000;
            if (n5 == 0x2000000) {
                n3 = n4 + this.inputLocals[n3 & 0x7FFFFF];
            } else if (n5 == 0x3000000) {
                n3 = n4 + this.inputStack[this.inputStack.length - (n3 & 0x7FFFFF)];
            }
            if (n != n3) continue;
            return n2;
        }
        return n;
    }

    void initInputFrame(ClassWriter classWriter, int n, Type[] typeArray, int n2) {
        this.inputLocals = new int[n2];
        this.inputStack = new int[0];
        int n3 = 0;
        if ((n & 8) == 0) {
            this.inputLocals[n3++] = (n & 0x80000) == 0 ? 0x1700000 | classWriter.addType(classWriter.thisName) : 0x1000006;
        }
        for (int i = 0; i < typeArray.length; ++i) {
            int n4 = Frame.type(classWriter, typeArray[i].getDescriptor());
            this.inputLocals[n3++] = n4;
            if (n4 != 0x1000004 && n4 != 0x1000003) continue;
            this.inputLocals[n3++] = 0x1000000;
        }
        while (n3 < n2) {
            this.inputLocals[n3++] = 0x1000000;
        }
    }

    void execute(int n, int n2, ClassWriter classWriter, Item item) {
        block0 : switch (n) {
            case 0: 
            case 116: 
            case 117: 
            case 118: 
            case 119: 
            case 145: 
            case 146: 
            case 147: 
            case 167: 
            case 177: {
                break;
            }
            case 1: {
                this.push(0x1000005);
                break;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 16: 
            case 17: 
            case 21: {
                this.push(0x1000001);
                break;
            }
            case 9: 
            case 10: 
            case 22: {
                this.push(0x1000004);
                this.push(0x1000000);
                break;
            }
            case 11: 
            case 12: 
            case 13: 
            case 23: {
                this.push(0x1000002);
                break;
            }
            case 14: 
            case 15: 
            case 24: {
                this.push(0x1000003);
                this.push(0x1000000);
                break;
            }
            case 18: {
                switch (item.type) {
                    case 3: {
                        this.push(0x1000001);
                        break block0;
                    }
                    case 5: {
                        this.push(0x1000004);
                        this.push(0x1000000);
                        break block0;
                    }
                    case 4: {
                        this.push(0x1000002);
                        break block0;
                    }
                    case 6: {
                        this.push(0x1000003);
                        this.push(0x1000000);
                        break block0;
                    }
                    case 7: {
                        this.push(0x1700000 | classWriter.addType("java/lang/Class"));
                        break block0;
                    }
                    case 8: {
                        this.push(0x1700000 | classWriter.addType("java/lang/String"));
                        break block0;
                    }
                    case 16: {
                        this.push(0x1700000 | classWriter.addType("java/lang/invoke/MethodType"));
                        break block0;
                    }
                }
                this.push(0x1700000 | classWriter.addType("java/lang/invoke/MethodHandle"));
                break;
            }
            case 25: {
                this.push(this.get(n2));
                break;
            }
            case 46: 
            case 51: 
            case 52: 
            case 53: {
                this.pop(2);
                this.push(0x1000001);
                break;
            }
            case 47: 
            case 143: {
                this.pop(2);
                this.push(0x1000004);
                this.push(0x1000000);
                break;
            }
            case 48: {
                this.pop(2);
                this.push(0x1000002);
                break;
            }
            case 49: 
            case 138: {
                this.pop(2);
                this.push(0x1000003);
                this.push(0x1000000);
                break;
            }
            case 50: {
                this.pop(1);
                int n3 = this.pop();
                this.push(-268435456 + n3);
                break;
            }
            case 54: 
            case 56: 
            case 58: {
                int n4 = this.pop();
                this.set(n2, n4);
                if (n2 <= 0) break;
                int n5 = this.get(n2 - 1);
                if (n5 == 0x1000004 || n5 == 0x1000003) {
                    this.set(n2 - 1, 0x1000000);
                    break;
                }
                if ((n5 & 0xF000000) == 0x1000000) break;
                this.set(n2 - 1, n5 | 0x800000);
                break;
            }
            case 55: 
            case 57: {
                this.pop(1);
                int n6 = this.pop();
                this.set(n2, n6);
                this.set(n2 + 1, 0x1000000);
                if (n2 <= 0) break;
                int n7 = this.get(n2 - 1);
                if (n7 == 0x1000004 || n7 == 0x1000003) {
                    this.set(n2 - 1, 0x1000000);
                    break;
                }
                if ((n7 & 0xF000000) == 0x1000000) break;
                this.set(n2 - 1, n7 | 0x800000);
                break;
            }
            case 79: 
            case 81: 
            case 83: 
            case 84: 
            case 85: 
            case 86: {
                this.pop(3);
                break;
            }
            case 80: 
            case 82: {
                this.pop(4);
                break;
            }
            case 87: 
            case 153: 
            case 154: 
            case 155: 
            case 156: 
            case 157: 
            case 158: 
            case 170: 
            case 171: 
            case 172: 
            case 174: 
            case 176: 
            case 191: 
            case 194: 
            case 195: 
            case 198: 
            case 199: {
                this.pop(1);
                break;
            }
            case 88: 
            case 159: 
            case 160: 
            case 161: 
            case 162: 
            case 163: 
            case 164: 
            case 165: 
            case 166: 
            case 173: 
            case 175: {
                this.pop(2);
                break;
            }
            case 89: {
                int n8 = this.pop();
                this.push(n8);
                this.push(n8);
                break;
            }
            case 90: {
                int n9 = this.pop();
                int n10 = this.pop();
                this.push(n9);
                this.push(n10);
                this.push(n9);
                break;
            }
            case 91: {
                int n11 = this.pop();
                int n12 = this.pop();
                int n13 = this.pop();
                this.push(n11);
                this.push(n13);
                this.push(n12);
                this.push(n11);
                break;
            }
            case 92: {
                int n14 = this.pop();
                int n15 = this.pop();
                this.push(n15);
                this.push(n14);
                this.push(n15);
                this.push(n14);
                break;
            }
            case 93: {
                int n16 = this.pop();
                int n17 = this.pop();
                int n18 = this.pop();
                this.push(n17);
                this.push(n16);
                this.push(n18);
                this.push(n17);
                this.push(n16);
                break;
            }
            case 94: {
                int n19 = this.pop();
                int n20 = this.pop();
                int n21 = this.pop();
                int n22 = this.pop();
                this.push(n20);
                this.push(n19);
                this.push(n22);
                this.push(n21);
                this.push(n20);
                this.push(n19);
                break;
            }
            case 95: {
                int n23 = this.pop();
                int n24 = this.pop();
                this.push(n23);
                this.push(n24);
                break;
            }
            case 96: 
            case 100: 
            case 104: 
            case 108: 
            case 112: 
            case 120: 
            case 122: 
            case 124: 
            case 126: 
            case 128: 
            case 130: 
            case 136: 
            case 142: 
            case 149: 
            case 150: {
                this.pop(2);
                this.push(0x1000001);
                break;
            }
            case 97: 
            case 101: 
            case 105: 
            case 109: 
            case 113: 
            case 127: 
            case 129: 
            case 131: {
                this.pop(4);
                this.push(0x1000004);
                this.push(0x1000000);
                break;
            }
            case 98: 
            case 102: 
            case 106: 
            case 110: 
            case 114: 
            case 137: 
            case 144: {
                this.pop(2);
                this.push(0x1000002);
                break;
            }
            case 99: 
            case 103: 
            case 107: 
            case 111: 
            case 115: {
                this.pop(4);
                this.push(0x1000003);
                this.push(0x1000000);
                break;
            }
            case 121: 
            case 123: 
            case 125: {
                this.pop(3);
                this.push(0x1000004);
                this.push(0x1000000);
                break;
            }
            case 132: {
                this.set(n2, 0x1000001);
                break;
            }
            case 133: 
            case 140: {
                this.pop(1);
                this.push(0x1000004);
                this.push(0x1000000);
                break;
            }
            case 134: {
                this.pop(1);
                this.push(0x1000002);
                break;
            }
            case 135: 
            case 141: {
                this.pop(1);
                this.push(0x1000003);
                this.push(0x1000000);
                break;
            }
            case 139: 
            case 190: 
            case 193: {
                this.pop(1);
                this.push(0x1000001);
                break;
            }
            case 148: 
            case 151: 
            case 152: {
                this.pop(4);
                this.push(0x1000001);
                break;
            }
            case 168: 
            case 169: {
                throw new RuntimeException("JSR/RET are not supported with computeFrames option");
            }
            case 178: {
                this.push(classWriter, item.strVal3);
                break;
            }
            case 179: {
                this.pop(item.strVal3);
                break;
            }
            case 180: {
                this.pop(1);
                this.push(classWriter, item.strVal3);
                break;
            }
            case 181: {
                this.pop(item.strVal3);
                this.pop();
                break;
            }
            case 182: 
            case 183: 
            case 184: 
            case 185: {
                this.pop(item.strVal3);
                if (n != 184) {
                    int n25 = this.pop();
                    if (n == 183 && item.strVal2.charAt(0) == '<') {
                        this.init(n25);
                    }
                }
                this.push(classWriter, item.strVal3);
                break;
            }
            case 186: {
                this.pop(item.strVal2);
                this.push(classWriter, item.strVal2);
                break;
            }
            case 187: {
                this.push(0x1800000 | classWriter.addUninitializedType(item.strVal1, n2));
                break;
            }
            case 188: {
                this.pop();
                switch (n2) {
                    case 4: {
                        this.push(0x11000009);
                        break block0;
                    }
                    case 5: {
                        this.push(0x1100000B);
                        break block0;
                    }
                    case 8: {
                        this.push(0x1100000A);
                        break block0;
                    }
                    case 9: {
                        this.push(0x1100000C);
                        break block0;
                    }
                    case 10: {
                        this.push(0x11000001);
                        break block0;
                    }
                    case 6: {
                        this.push(0x11000002);
                        break block0;
                    }
                    case 7: {
                        this.push(0x11000003);
                        break block0;
                    }
                }
                this.push(0x11000004);
                break;
            }
            case 189: {
                String string = item.strVal1;
                this.pop();
                if (string.charAt(0) == '[') {
                    this.push(classWriter, '[' + string);
                    break;
                }
                this.push(0x11700000 | classWriter.addType(string));
                break;
            }
            case 192: {
                String string = item.strVal1;
                this.pop();
                if (string.charAt(0) == '[') {
                    this.push(classWriter, string);
                    break;
                }
                this.push(0x1700000 | classWriter.addType(string));
                break;
            }
            default: {
                this.pop(n2);
                this.push(classWriter, item.strVal1);
            }
        }
    }

    boolean merge(ClassWriter classWriter, Frame frame, int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        boolean bl = false;
        int n7 = this.inputLocals.length;
        int n8 = this.inputStack.length;
        if (frame.inputLocals == null) {
            frame.inputLocals = new int[n7];
            bl = true;
        }
        for (n6 = 0; n6 < n7; ++n6) {
            if (this.outputLocals != null && n6 < this.outputLocals.length) {
                n5 = this.outputLocals[n6];
                if (n5 == 0) {
                    n4 = this.inputLocals[n6];
                } else {
                    n3 = n5 & 0xF0000000;
                    n2 = n5 & 0xF000000;
                    if (n2 == 0x1000000) {
                        n4 = n5;
                    } else {
                        n4 = n2 == 0x2000000 ? n3 + this.inputLocals[n5 & 0x7FFFFF] : n3 + this.inputStack[n8 - (n5 & 0x7FFFFF)];
                        if ((n5 & 0x800000) != 0 && (n4 == 0x1000004 || n4 == 0x1000003)) {
                            n4 = 0x1000000;
                        }
                    }
                }
            } else {
                n4 = this.inputLocals[n6];
            }
            if (this.initializations != null) {
                n4 = this.init(classWriter, n4);
            }
            bl |= Frame.merge(classWriter, n4, frame.inputLocals, n6);
        }
        if (n > 0) {
            for (n6 = 0; n6 < n7; ++n6) {
                n4 = this.inputLocals[n6];
                bl |= Frame.merge(classWriter, n4, frame.inputLocals, n6);
            }
            if (frame.inputStack == null) {
                frame.inputStack = new int[1];
                bl = true;
            }
            return bl |= Frame.merge(classWriter, n, frame.inputStack, 0);
        }
        int n9 = this.inputStack.length + this.owner.inputStackTop;
        if (frame.inputStack == null) {
            frame.inputStack = new int[n9 + this.outputStackTop];
            bl = true;
        }
        for (n6 = 0; n6 < n9; ++n6) {
            n4 = this.inputStack[n6];
            if (this.initializations != null) {
                n4 = this.init(classWriter, n4);
            }
            bl |= Frame.merge(classWriter, n4, frame.inputStack, n6);
        }
        for (n6 = 0; n6 < this.outputStackTop; ++n6) {
            n5 = this.outputStack[n6];
            n3 = n5 & 0xF0000000;
            n2 = n5 & 0xF000000;
            if (n2 == 0x1000000) {
                n4 = n5;
            } else {
                n4 = n2 == 0x2000000 ? n3 + this.inputLocals[n5 & 0x7FFFFF] : n3 + this.inputStack[n8 - (n5 & 0x7FFFFF)];
                if ((n5 & 0x800000) != 0 && (n4 == 0x1000004 || n4 == 0x1000003)) {
                    n4 = 0x1000000;
                }
            }
            if (this.initializations != null) {
                n4 = this.init(classWriter, n4);
            }
            bl |= Frame.merge(classWriter, n4, frame.inputStack, n9 + n6);
        }
        return bl;
    }

    private static boolean merge(ClassWriter classWriter, int n, int[] nArray, int n2) {
        int n3;
        int n4 = nArray[n2];
        if (n4 == n) {
            return false;
        }
        if ((n & 0xFFFFFFF) == 0x1000005) {
            if (n4 == 0x1000005) {
                return false;
            }
            n = 0x1000005;
        }
        if (n4 == 0) {
            nArray[n2] = n;
            return true;
        }
        if ((n4 & 0xFF00000) == 0x1700000 || (n4 & 0xF0000000) != 0) {
            if (n == 0x1000005) {
                return false;
            }
            if ((n & 0xFFF00000) == (n4 & 0xFFF00000)) {
                if ((n4 & 0xFF00000) == 0x1700000) {
                    n3 = n & 0xF0000000 | 0x1700000 | classWriter.getMergedType(n & 0xFFFFF, n4 & 0xFFFFF);
                } else {
                    int n5 = -268435456 + (n4 & 0xF0000000);
                    n3 = n5 | 0x1700000 | classWriter.addType("java/lang/Object");
                }
            } else if ((n & 0xFF00000) == 0x1700000 || (n & 0xF0000000) != 0) {
                int n6 = ((n & 0xF0000000) == 0 || (n & 0xFF00000) == 0x1700000 ? 0 : -268435456) + (n & 0xF0000000);
                int n7 = ((n4 & 0xF0000000) == 0 || (n4 & 0xFF00000) == 0x1700000 ? 0 : -268435456) + (n4 & 0xF0000000);
                n3 = Math.min(n6, n7) | 0x1700000 | classWriter.addType("java/lang/Object");
            } else {
                n3 = 0x1000000;
            }
        } else {
            n3 = n4 == 0x1000005 ? ((n & 0xFF00000) == 0x1700000 || (n & 0xF0000000) != 0 ? n : 0x1000000) : 0x1000000;
        }
        if (n4 != n3) {
            nArray[n2] = n3;
            return true;
        }
        return false;
    }

    static {
        int[] nArray = new int[202];
        String string = "EFFFFFFFFGGFFFGGFFFEEFGFGFEEEEEEEEEEEEEEEEEEEEDEDEDDDDDCDCDEEEEEEEEEEEEEEEEEEEEBABABBBBDCFFFGGGEDCDCDCDCDCDCDCDCDCDCEEEEDDDDDDDCDCDCEFEFDDEEFFDEDEEEBDDBBDDDDDDCCCCCCCCEFEDDDCDCDEEEEEEEEEEFEEEEEEDDEEDDEE";
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = string.charAt(i) - 69;
        }
        SIZE = nArray;
    }
}

