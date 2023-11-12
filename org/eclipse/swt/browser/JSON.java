/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import java.util.ArrayList;
import org.eclipse.swt.SWT;

class JSON {
    JSON() {
    }

    public static Object parse(char[] cArray) {
        return new Reader(cArray, 0, cArray.length).readTop();
    }

    public static Object parse(String string) {
        return JSON.parse(string.toCharArray());
    }

    public static String stringify(Object object) {
        return new Writer(object).toString();
    }

    static class Writer {
        static final String[] ESCAPED = new String[96];
        StringBuilder sb = new StringBuilder();

        public Writer(Object object) {
            this.writeAny(object);
        }

        void writeAny(Object object) {
            if (object == null) {
                this.sb.append("null");
            } else if (object instanceof Boolean) {
                this.sb.append(object.toString());
            } else if (object instanceof Long) {
                this.sb.append((Long)object);
            } else if (object instanceof Integer) {
                this.sb.append((Integer)object);
            } else if (object instanceof Short) {
                this.sb.append(((Short)object).shortValue());
            } else if (object instanceof Byte) {
                this.sb.append(((Byte)object).byteValue());
            } else if (object instanceof Double) {
                this.sb.append((Double)object);
            } else if (object instanceof Float) {
                this.sb.append(((Float)object).floatValue());
            } else if (object instanceof String) {
                this.writeString(object.toString());
            } else if (object instanceof Object[]) {
                this.writeArray((Object[])object);
            } else {
                SWT.error(5, null, " [object not encodable: " + object.getClass());
            }
        }

        void writeString(String string) {
            this.sb.append('\"');
            int n = 0;
            int n2 = string.length();
            for (int i = 0; i < n2; ++i) {
                char c = string.charAt(i);
                if (c >= ESCAPED.length || ESCAPED[c] == null) continue;
                this.sb.append(string, n, i);
                this.sb.append(ESCAPED[c]);
                n = i + 1;
            }
            this.sb.append(string, n, string.length());
            this.sb.append('\"');
        }

        void writeArray(Object[] objectArray) {
            this.sb.append('[');
            boolean bl = true;
            for (Object object : objectArray) {
                if (!bl) {
                    this.sb.append(',');
                }
                this.writeAny(object);
                bl = false;
            }
            this.sb.append(']');
        }

        public String toString() {
            return this.sb.toString();
        }

        static {
            for (int i = 0; i < 32; ++i) {
                Writer.ESCAPED[i] = String.format("\\u%04x", i);
            }
            Writer.ESCAPED[8] = "\\b";
            Writer.ESCAPED[12] = "\\f";
            Writer.ESCAPED[10] = "\\n";
            Writer.ESCAPED[13] = "\\r";
            Writer.ESCAPED[9] = "\\t";
            Writer.ESCAPED[34] = "\\\"";
        }
    }

    static class Reader {
        char[] input;
        int pos;
        int end;
        StringBuilder sb;

        public Reader(char[] cArray, int n, int n2) {
            this.input = cArray;
            this.pos = n;
            this.end = n2;
        }

        char nextChar() {
            return this.pos < this.end ? this.input[this.pos++] : (char)'\u0000';
        }

        void error() {
            SWT.error(5, null, " [decoding error at " + (this.pos - 1));
        }

        void readLiteral(String string) {
            int n = string.length();
            for (int i = 0; i < n; ++i) {
                if (this.nextChar() == string.charAt(i)) continue;
                this.error();
            }
        }

        int nextHexDigit() {
            char c = this.nextChar();
            if ('0' <= c && c <= '9') {
                return c - 48;
            }
            if ('a' <= c && c <= 'f') {
                return c - 97 + 10;
            }
            if ('A' <= c && c <= 'F') {
                return c - 65 + 10;
            }
            this.error();
            return 0;
        }

        char readEscape() {
            int n = this.nextChar();
            switch (n) {
                case 34: 
                case 47: 
                case 92: {
                    break;
                }
                case 98: {
                    n = 8;
                    break;
                }
                case 102: {
                    n = 12;
                    break;
                }
                case 110: {
                    n = 10;
                    break;
                }
                case 114: {
                    n = 13;
                    break;
                }
                case 116: {
                    n = 9;
                    break;
                }
                case 117: {
                    n = (char)(this.nextHexDigit() << 12 | this.nextHexDigit() << 8 | this.nextHexDigit() << 4 | this.nextHexDigit());
                    break;
                }
                default: {
                    this.error();
                }
            }
            return (char)n;
        }

        String readString() {
            char c;
            int n = this.pos;
            do {
                if ((c = this.nextChar()) < ' ') {
                    this.error();
                }
                if (c != '\\') continue;
                if (this.sb == null) {
                    this.sb = new StringBuilder();
                }
                this.sb.append(this.input, n, this.pos - n - 1);
                this.sb.append(this.readEscape());
                n = this.pos;
            } while (c != '\"');
            if (this.sb != null) {
                this.sb.append(this.input, n, this.pos - 1 - n);
                String string = this.sb.toString();
                this.sb.setLength(0);
                return string;
            }
            return String.valueOf(this.input, n, this.pos - n - 1);
        }

        double readNumber() {
            int n = this.pos - 1;
            block6: while (true) {
                char c = this.nextChar();
                switch (c) {
                    case '+': 
                    case '-': 
                    case '.': 
                    case '0': 
                    case '1': 
                    case '2': 
                    case '3': 
                    case '4': 
                    case '5': 
                    case '6': 
                    case '7': 
                    case '8': 
                    case '9': 
                    case 'E': 
                    case 'e': {
                        continue block6;
                    }
                    default: {
                        --this.pos;
                    }
                    case '\u0000': 
                }
                try {
                    return Double.parseDouble(String.valueOf(this.input, n, this.pos - n));
                }
                catch (NumberFormatException numberFormatException) {
                    this.error();
                    continue;
                }
                break;
            }
        }

        Object readAny() {
            block12: while (true) {
                char c = this.nextChar();
                switch (c) {
                    case '\t': 
                    case '\n': 
                    case '\r': 
                    case ' ': {
                        continue block12;
                    }
                    case '\u0000': {
                        return Control.END;
                    }
                    case '[': {
                        return this.readArray();
                    }
                    case ']': {
                        return Control.ARRAY_END;
                    }
                    case ',': {
                        return Control.COMMA;
                    }
                    case '\"': {
                        return this.readString();
                    }
                    case '-': 
                    case '0': 
                    case '1': 
                    case '2': 
                    case '3': 
                    case '4': 
                    case '5': 
                    case '6': 
                    case '7': 
                    case '8': 
                    case '9': {
                        return this.readNumber();
                    }
                    case 'n': {
                        this.readLiteral("ull");
                        return null;
                    }
                    case 't': {
                        this.readLiteral("rue");
                        return true;
                    }
                    case 'f': {
                        this.readLiteral("alse");
                        return false;
                    }
                }
                this.error();
            }
        }

        Object readArray() {
            Object object;
            Object object2 = this.readAny();
            if (object2 == Control.ARRAY_END) {
                return new Object[0];
            }
            if (object2 instanceof Control) {
                this.error();
            }
            ArrayList<Object> arrayList = new ArrayList<Object>();
            arrayList.add(object2);
            while ((object = this.readAny()) != Control.ARRAY_END) {
                if (object != Control.COMMA) {
                    this.error();
                }
                if ((object2 = this.readAny()) instanceof Control) {
                    this.error();
                }
                arrayList.add(object2);
            }
            return arrayList.toArray();
        }

        Object readTop() {
            Object object = this.readAny();
            if (object instanceof Control) {
                this.error();
            }
            if (this.readAny() != Control.END) {
                this.error();
            }
            return object;
        }

        static enum Control {
            END,
            ARRAY_END,
            COMMA;

        }
    }
}

