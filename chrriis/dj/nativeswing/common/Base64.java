/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

class Base64 {
    private Base64() {
    }

    private static char toChar(int n, boolean bl) {
        char c;
        switch (n) {
            case 0: {
                c = 'A';
                break;
            }
            case 1: {
                c = 'B';
                break;
            }
            case 2: {
                c = 'C';
                break;
            }
            case 3: {
                c = 'D';
                break;
            }
            case 4: {
                c = 'E';
                break;
            }
            case 5: {
                c = 'F';
                break;
            }
            case 6: {
                c = 'G';
                break;
            }
            case 7: {
                c = 'H';
                break;
            }
            case 8: {
                c = 'I';
                break;
            }
            case 9: {
                c = 'J';
                break;
            }
            case 10: {
                c = 'K';
                break;
            }
            case 11: {
                c = 'L';
                break;
            }
            case 12: {
                c = 'M';
                break;
            }
            case 13: {
                c = 'N';
                break;
            }
            case 14: {
                c = 'O';
                break;
            }
            case 15: {
                c = 'P';
                break;
            }
            case 16: {
                c = 'Q';
                break;
            }
            case 17: {
                c = 'R';
                break;
            }
            case 18: {
                c = 'S';
                break;
            }
            case 19: {
                c = 'T';
                break;
            }
            case 20: {
                c = 'U';
                break;
            }
            case 21: {
                c = 'V';
                break;
            }
            case 22: {
                c = 'W';
                break;
            }
            case 23: {
                c = 'X';
                break;
            }
            case 24: {
                c = 'Y';
                break;
            }
            case 25: {
                c = 'Z';
                break;
            }
            case 26: {
                c = 'a';
                break;
            }
            case 27: {
                c = 'b';
                break;
            }
            case 28: {
                c = 'c';
                break;
            }
            case 29: {
                c = 'd';
                break;
            }
            case 30: {
                c = 'e';
                break;
            }
            case 31: {
                c = 'f';
                break;
            }
            case 32: {
                c = 'g';
                break;
            }
            case 33: {
                c = 'h';
                break;
            }
            case 34: {
                c = 'i';
                break;
            }
            case 35: {
                c = 'j';
                break;
            }
            case 36: {
                c = 'k';
                break;
            }
            case 37: {
                c = 'l';
                break;
            }
            case 38: {
                c = 'm';
                break;
            }
            case 39: {
                c = 'n';
                break;
            }
            case 40: {
                c = 'o';
                break;
            }
            case 41: {
                c = 'p';
                break;
            }
            case 42: {
                c = 'q';
                break;
            }
            case 43: {
                c = 'r';
                break;
            }
            case 44: {
                c = 's';
                break;
            }
            case 45: {
                c = 't';
                break;
            }
            case 46: {
                c = 'u';
                break;
            }
            case 47: {
                c = 'v';
                break;
            }
            case 48: {
                c = 'w';
                break;
            }
            case 49: {
                c = 'x';
                break;
            }
            case 50: {
                c = 'y';
                break;
            }
            case 51: {
                c = 'z';
                break;
            }
            case 52: {
                c = '0';
                break;
            }
            case 53: {
                c = '1';
                break;
            }
            case 54: {
                c = '2';
                break;
            }
            case 55: {
                c = '3';
                break;
            }
            case 56: {
                c = '4';
                break;
            }
            case 57: {
                c = '5';
                break;
            }
            case 58: {
                c = '6';
                break;
            }
            case 59: {
                c = '7';
                break;
            }
            case 60: {
                c = '8';
                break;
            }
            case 61: {
                c = '9';
                break;
            }
            case 62: {
                c = bl ? (char)'-' : '+';
                break;
            }
            case 63: {
                c = bl ? (char)'_' : '/';
                break;
            }
            default: {
                throw new RuntimeException("Cannot happen.");
            }
        }
        return c;
    }

    private static int fromChar(char c) {
        int n;
        switch (c) {
            case 'A': {
                n = 0;
                break;
            }
            case 'B': {
                n = 1;
                break;
            }
            case 'C': {
                n = 2;
                break;
            }
            case 'D': {
                n = 3;
                break;
            }
            case 'E': {
                n = 4;
                break;
            }
            case 'F': {
                n = 5;
                break;
            }
            case 'G': {
                n = 6;
                break;
            }
            case 'H': {
                n = 7;
                break;
            }
            case 'I': {
                n = 8;
                break;
            }
            case 'J': {
                n = 9;
                break;
            }
            case 'K': {
                n = 10;
                break;
            }
            case 'L': {
                n = 11;
                break;
            }
            case 'M': {
                n = 12;
                break;
            }
            case 'N': {
                n = 13;
                break;
            }
            case 'O': {
                n = 14;
                break;
            }
            case 'P': {
                n = 15;
                break;
            }
            case 'Q': {
                n = 16;
                break;
            }
            case 'R': {
                n = 17;
                break;
            }
            case 'S': {
                n = 18;
                break;
            }
            case 'T': {
                n = 19;
                break;
            }
            case 'U': {
                n = 20;
                break;
            }
            case 'V': {
                n = 21;
                break;
            }
            case 'W': {
                n = 22;
                break;
            }
            case 'X': {
                n = 23;
                break;
            }
            case 'Y': {
                n = 24;
                break;
            }
            case 'Z': {
                n = 25;
                break;
            }
            case 'a': {
                n = 26;
                break;
            }
            case 'b': {
                n = 27;
                break;
            }
            case 'c': {
                n = 28;
                break;
            }
            case 'd': {
                n = 29;
                break;
            }
            case 'e': {
                n = 30;
                break;
            }
            case 'f': {
                n = 31;
                break;
            }
            case 'g': {
                n = 32;
                break;
            }
            case 'h': {
                n = 33;
                break;
            }
            case 'i': {
                n = 34;
                break;
            }
            case 'j': {
                n = 35;
                break;
            }
            case 'k': {
                n = 36;
                break;
            }
            case 'l': {
                n = 37;
                break;
            }
            case 'm': {
                n = 38;
                break;
            }
            case 'n': {
                n = 39;
                break;
            }
            case 'o': {
                n = 40;
                break;
            }
            case 'p': {
                n = 41;
                break;
            }
            case 'q': {
                n = 42;
                break;
            }
            case 'r': {
                n = 43;
                break;
            }
            case 's': {
                n = 44;
                break;
            }
            case 't': {
                n = 45;
                break;
            }
            case 'u': {
                n = 46;
                break;
            }
            case 'v': {
                n = 47;
                break;
            }
            case 'w': {
                n = 48;
                break;
            }
            case 'x': {
                n = 49;
                break;
            }
            case 'y': {
                n = 50;
                break;
            }
            case 'z': {
                n = 51;
                break;
            }
            case '0': {
                n = 52;
                break;
            }
            case '1': {
                n = 53;
                break;
            }
            case '2': {
                n = 54;
                break;
            }
            case '3': {
                n = 55;
                break;
            }
            case '4': {
                n = 56;
                break;
            }
            case '5': {
                n = 57;
                break;
            }
            case '6': {
                n = 58;
                break;
            }
            case '7': {
                n = 59;
                break;
            }
            case '8': {
                n = 60;
                break;
            }
            case '9': {
                n = 61;
                break;
            }
            case '+': 
            case '-': {
                n = 62;
                break;
            }
            case '/': 
            case '_': {
                n = 63;
                break;
            }
            default: {
                throw new RuntimeException("Cannot happen.");
            }
        }
        return n;
    }

    public static String encode(String string, boolean bl) {
        byte[] byArray;
        try {
            byArray = string.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
        return Base64.encode(byArray, bl);
    }

    public static String encode(byte[] byArray, boolean bl) {
        int n;
        int n2 = (byArray.length + 2) / 3 * 4 - (2 - (byArray.length + 2) % 3);
        int n3 = (byArray.length + 2) / 3 * 4 - n2;
        char[] cArray = new char[n2 + n3];
        for (n = 0; n < n3; ++n) {
            cArray[cArray.length - 1 - n] = 61;
        }
        for (n = 0; n < n2; ++n) {
            int n4;
            switch (n % 4) {
                case 0: {
                    int n5 = n / 4 * 3;
                    n4 = (byArray[n5] & 0xFF) >> 2;
                    break;
                }
                case 1: {
                    int n5 = (n - 1) / 4 * 3;
                    if (n5 + 1 < byArray.length) {
                        n4 = (byArray[n5] & 3) << 4 | (byArray[n5 + 1] & 0xFF) >> 4;
                        break;
                    }
                    n4 = (byArray[n5] & 3) << 4;
                    break;
                }
                case 2: {
                    int n5 = (n - 2) / 4 * 3 + 1;
                    if (n5 + 1 < byArray.length) {
                        n4 = (byArray[n5] & 0xF) << 2 | (byArray[n5 + 1] & 0xFF) >> 6;
                        break;
                    }
                    n4 = (byArray[n5] & 0xF) << 2;
                    break;
                }
                case 3: {
                    int n5 = (n - 3) / 4 * 3 + 2;
                    n4 = byArray[n5] & 0x3F;
                    break;
                }
                default: {
                    throw new RuntimeException("Cannot happen.");
                }
            }
            cArray[n] = Base64.toChar(n4, bl);
        }
        return new String(cArray);
    }

    public static String decode(String string) {
        try {
            int n = string.length();
            int n2 = (n + 2) / 4 * 3 - (2 - (n + 2) % 4);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(n2);
            Base64.decode(string, byteArrayOutputStream);
            return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void decode(String string, OutputStream outputStream) throws IOException {
        int n = string.length();
        if (n > 1 && string.charAt(n - 1) == '=') {
            --n;
        }
        if (n > 1 && string.charAt(n - 1) == '=') {
            --n;
        }
        int n2 = (n + 2) / 4 * 3 - (2 - (n + 2) % 4);
        block5: for (int i = 0; i < n2; ++i) {
            switch (i % 3) {
                case 0: {
                    int n3 = i / 3 * 4;
                    byte by = (byte)(Base64.fromChar(string.charAt(n3)) << 2 | Base64.fromChar(string.charAt(n3 + 1)) >> 4);
                    outputStream.write(by);
                    continue block5;
                }
                case 1: {
                    int n3 = i / 3 * 4 + 1;
                    byte by = (byte)((Base64.fromChar(string.charAt(n3)) & 0xF) << 4 | Base64.fromChar(string.charAt(n3 + 1)) >> 2);
                    outputStream.write(by);
                    continue block5;
                }
                case 2: {
                    int n3 = i / 3 * 4 + 2;
                    byte by = (byte)((Base64.fromChar(string.charAt(n3)) & 3) << 6 | Base64.fromChar(string.charAt(n3 + 1)));
                    outputStream.write(by);
                    continue block5;
                }
            }
        }
    }
}

