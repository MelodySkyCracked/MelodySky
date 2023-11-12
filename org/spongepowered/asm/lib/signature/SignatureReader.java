/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.signature;

import org.spongepowered.asm.lib.signature.SignatureVisitor;

public class SignatureReader {
    private final String signature;

    public SignatureReader(String string) {
        this.signature = string;
    }

    public void accept(SignatureVisitor signatureVisitor) {
        int n;
        String string = this.signature;
        int n2 = string.length();
        if (string.charAt(0) == '<') {
            char c;
            n = 2;
            do {
                int n3 = string.indexOf(58, n);
                signatureVisitor.visitFormalTypeParameter(string.substring(n - 1, n3));
                n = n3 + 1;
                c = string.charAt(n);
                if (c == 'L' || c == '[' || c == 'T') {
                    n = SignatureReader.parseType(string, n, signatureVisitor.visitClassBound());
                }
                while ((c = string.charAt(n++)) == ':') {
                    n = SignatureReader.parseType(string, n, signatureVisitor.visitInterfaceBound());
                }
            } while (c != '>');
        } else {
            n = 0;
        }
        if (string.charAt(n) == '(') {
            ++n;
            while (string.charAt(n) != ')') {
                n = SignatureReader.parseType(string, n, signatureVisitor.visitParameterType());
            }
            n = SignatureReader.parseType(string, n + 1, signatureVisitor.visitReturnType());
            while (n < n2) {
                n = SignatureReader.parseType(string, n + 1, signatureVisitor.visitExceptionType());
            }
        } else {
            n = SignatureReader.parseType(string, n, signatureVisitor.visitSuperclass());
            while (n < n2) {
                n = SignatureReader.parseType(string, n, signatureVisitor.visitInterface());
            }
        }
    }

    public void acceptType(SignatureVisitor signatureVisitor) {
        SignatureReader.parseType(this.signature, 0, signatureVisitor);
    }

    private static int parseType(String string, int n, SignatureVisitor signatureVisitor) {
        char c = string.charAt(n++);
        switch (c) {
            case 'B': 
            case 'C': 
            case 'D': 
            case 'F': 
            case 'I': 
            case 'J': 
            case 'S': 
            case 'V': 
            case 'Z': {
                signatureVisitor.visitBaseType(c);
                return n;
            }
            case '[': {
                return SignatureReader.parseType(string, n, signatureVisitor.visitArrayType());
            }
            case 'T': {
                int n2 = string.indexOf(59, n);
                signatureVisitor.visitTypeVariable(string.substring(n, n2));
                return n2 + 1;
            }
        }
        int n3 = n;
        boolean bl = false;
        boolean bl2 = false;
        while (true) {
            c = string.charAt(n++);
            block5 : switch (c) {
                case '.': 
                case ';': {
                    String string2;
                    if (!bl) {
                        string2 = string.substring(n3, n - 1);
                        if (bl2) {
                            signatureVisitor.visitInnerClassType(string2);
                        } else {
                            signatureVisitor.visitClassType(string2);
                        }
                    }
                    if (c == ';') {
                        signatureVisitor.visitEnd();
                        return n;
                    }
                    n3 = n;
                    bl = false;
                    bl2 = true;
                    break;
                }
                case '<': {
                    String string2 = string.substring(n3, n - 1);
                    if (bl2) {
                        signatureVisitor.visitInnerClassType(string2);
                    } else {
                        signatureVisitor.visitClassType(string2);
                    }
                    bl = true;
                    block15: while (true) {
                        c = string.charAt(n);
                        switch (c) {
                            case '>': {
                                break block5;
                            }
                            case '*': {
                                ++n;
                                signatureVisitor.visitTypeArgument();
                                continue block15;
                            }
                            case '+': 
                            case '-': {
                                n = SignatureReader.parseType(string, n + 1, signatureVisitor.visitTypeArgument(c));
                                continue block15;
                            }
                        }
                        n = SignatureReader.parseType(string, n, signatureVisitor.visitTypeArgument('='));
                    }
                }
            }
        }
    }
}

