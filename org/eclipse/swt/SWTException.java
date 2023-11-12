/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt;

import org.eclipse.swt.SWT;

public class SWTException
extends RuntimeException {
    public int code;
    public Throwable throwable;
    static final long serialVersionUID = 3257282552304842547L;

    public SWTException() {
        this(1);
    }

    public SWTException(String string) {
        this(1, string);
    }

    public SWTException(int n) {
        this(n, SWT.findErrorText(n));
    }

    public SWTException(int n, String string) {
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

