/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt;

import org.eclipse.swt.SWT;

public class SWTError
extends Error {
    public int code;
    public Throwable throwable;
    static final long serialVersionUID = 3833467327105808433L;

    public SWTError() {
        this(1);
    }

    public SWTError(String string) {
        this(1, string);
    }

    public SWTError(int n) {
        this(n, SWT.findErrorText(n));
    }

    public SWTError(int n, String string) {
        super(string);
        this.code = n;
    }

    @Override
    public Throwable getCause() {
        return this.throwable;
    }

    @Override
    public String getMessage() {
        if (this.throwable == null) {
            return super.getMessage();
        }
        return super.getMessage() + " (" + this.throwable.toString();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}

