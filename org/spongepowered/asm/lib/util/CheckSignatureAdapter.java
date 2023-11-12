/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.signature.SignatureVisitor;
import org.spongepowered.asm.lib.util.CheckMethodAdapter;

public class CheckSignatureAdapter
extends SignatureVisitor {
    public static final int CLASS_SIGNATURE = 0;
    public static final int METHOD_SIGNATURE = 1;
    public static final int TYPE_SIGNATURE = 2;
    private static final int EMPTY = 1;
    private static final int FORMAL = 2;
    private static final int BOUND = 4;
    private static final int SUPER = 8;
    private static final int PARAM = 16;
    private static final int RETURN = 32;
    private static final int SIMPLE_TYPE = 64;
    private static final int CLASS_TYPE = 128;
    private static final int END = 256;
    private final int type;
    private int state;
    private boolean canBeVoid;
    private final SignatureVisitor sv;

    public CheckSignatureAdapter(int n, SignatureVisitor signatureVisitor) {
        this(327680, n, signatureVisitor);
    }

    protected CheckSignatureAdapter(int n, int n2, SignatureVisitor signatureVisitor) {
        super(n);
        this.type = n2;
        this.state = 1;
        this.sv = signatureVisitor;
    }

    public void visitFormalTypeParameter(String string) {
        if (this.type == 2 || this.state != 1 && this.state != 2 && this.state != 4) {
            throw new IllegalStateException();
        }
        CheckMethodAdapter.checkIdentifier(string, "formal type parameter");
        this.state = 2;
        if (this.sv != null) {
            this.sv.visitFormalTypeParameter(string);
        }
    }

    public SignatureVisitor visitClassBound() {
        if (this.state != 2) {
            throw new IllegalStateException();
        }
        this.state = 4;
        SignatureVisitor signatureVisitor = this.sv == null ? null : this.sv.visitClassBound();
        return new CheckSignatureAdapter(2, signatureVisitor);
    }

    public SignatureVisitor visitInterfaceBound() {
        if (this.state != 2 && this.state != 4) {
            throw new IllegalArgumentException();
        }
        SignatureVisitor signatureVisitor = this.sv == null ? null : this.sv.visitInterfaceBound();
        return new CheckSignatureAdapter(2, signatureVisitor);
    }

    public SignatureVisitor visitSuperclass() {
        if (this.type != 0 || (this.state & 7) == 0) {
            throw new IllegalArgumentException();
        }
        this.state = 8;
        SignatureVisitor signatureVisitor = this.sv == null ? null : this.sv.visitSuperclass();
        return new CheckSignatureAdapter(2, signatureVisitor);
    }

    public SignatureVisitor visitInterface() {
        if (this.state != 8) {
            throw new IllegalStateException();
        }
        SignatureVisitor signatureVisitor = this.sv == null ? null : this.sv.visitInterface();
        return new CheckSignatureAdapter(2, signatureVisitor);
    }

    public SignatureVisitor visitParameterType() {
        if (this.type != 1 || (this.state & 0x17) == 0) {
            throw new IllegalArgumentException();
        }
        this.state = 16;
        SignatureVisitor signatureVisitor = this.sv == null ? null : this.sv.visitParameterType();
        return new CheckSignatureAdapter(2, signatureVisitor);
    }

    public SignatureVisitor visitReturnType() {
        if (this.type != 1 || (this.state & 0x17) == 0) {
            throw new IllegalArgumentException();
        }
        this.state = 32;
        SignatureVisitor signatureVisitor = this.sv == null ? null : this.sv.visitReturnType();
        CheckSignatureAdapter checkSignatureAdapter = new CheckSignatureAdapter(2, signatureVisitor);
        checkSignatureAdapter.canBeVoid = true;
        return checkSignatureAdapter;
    }

    public SignatureVisitor visitExceptionType() {
        if (this.state != 32) {
            throw new IllegalStateException();
        }
        SignatureVisitor signatureVisitor = this.sv == null ? null : this.sv.visitExceptionType();
        return new CheckSignatureAdapter(2, signatureVisitor);
    }

    public void visitBaseType(char c) {
        if (this.type != 2 || this.state != 1) {
            throw new IllegalStateException();
        }
        if (c == 'V' ? !this.canBeVoid : "ZCBSIFJD".indexOf(c) == -1) {
            throw new IllegalArgumentException();
        }
        this.state = 64;
        if (this.sv != null) {
            this.sv.visitBaseType(c);
        }
    }

    public void visitTypeVariable(String string) {
        if (this.type != 2 || this.state != 1) {
            throw new IllegalStateException();
        }
        CheckMethodAdapter.checkIdentifier(string, "type variable");
        this.state = 64;
        if (this.sv != null) {
            this.sv.visitTypeVariable(string);
        }
    }

    public SignatureVisitor visitArrayType() {
        if (this.type != 2 || this.state != 1) {
            throw new IllegalStateException();
        }
        this.state = 64;
        SignatureVisitor signatureVisitor = this.sv == null ? null : this.sv.visitArrayType();
        return new CheckSignatureAdapter(2, signatureVisitor);
    }

    public void visitClassType(String string) {
        if (this.type != 2 || this.state != 1) {
            throw new IllegalStateException();
        }
        CheckMethodAdapter.checkInternalName(string, "class name");
        this.state = 128;
        if (this.sv != null) {
            this.sv.visitClassType(string);
        }
    }

    public void visitInnerClassType(String string) {
        if (this.state != 128) {
            throw new IllegalStateException();
        }
        CheckMethodAdapter.checkIdentifier(string, "inner class name");
        if (this.sv != null) {
            this.sv.visitInnerClassType(string);
        }
    }

    public void visitTypeArgument() {
        if (this.state != 128) {
            throw new IllegalStateException();
        }
        if (this.sv != null) {
            this.sv.visitTypeArgument();
        }
    }

    public SignatureVisitor visitTypeArgument(char c) {
        if (this.state != 128) {
            throw new IllegalStateException();
        }
        if ("+-=".indexOf(c) == -1) {
            throw new IllegalArgumentException();
        }
        SignatureVisitor signatureVisitor = this.sv == null ? null : this.sv.visitTypeArgument(c);
        return new CheckSignatureAdapter(2, signatureVisitor);
    }

    public void visitEnd() {
        if (this.state != 128) {
            throw new IllegalStateException();
        }
        this.state = 256;
        if (this.sv != null) {
            this.sv.visitEnd();
        }
    }
}

