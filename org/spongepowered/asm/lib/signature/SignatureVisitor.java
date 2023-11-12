/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.signature;

public abstract class SignatureVisitor {
    public static final char EXTENDS = '+';
    public static final char SUPER = '-';
    public static final char INSTANCEOF = '=';
    protected final int api;

    public SignatureVisitor(int n) {
        if (n != 262144 && n != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = n;
    }

    public void visitFormalTypeParameter(String string) {
    }

    public SignatureVisitor visitClassBound() {
        return this;
    }

    public SignatureVisitor visitInterfaceBound() {
        return this;
    }

    public SignatureVisitor visitSuperclass() {
        return this;
    }

    public SignatureVisitor visitInterface() {
        return this;
    }

    public SignatureVisitor visitParameterType() {
        return this;
    }

    public SignatureVisitor visitReturnType() {
        return this;
    }

    public SignatureVisitor visitExceptionType() {
        return this;
    }

    public void visitBaseType(char c) {
    }

    public void visitTypeVariable(String string) {
    }

    public SignatureVisitor visitArrayType() {
        return this;
    }

    public void visitClassType(String string) {
    }

    public void visitInnerClassType(String string) {
    }

    public void visitTypeArgument() {
    }

    public SignatureVisitor visitTypeArgument(char c) {
        return this;
    }

    public void visitEnd() {
    }
}

