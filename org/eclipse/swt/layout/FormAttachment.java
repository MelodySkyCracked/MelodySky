/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

public final class FormAttachment {
    public int numerator;
    public int denominator = 100;
    public int offset;
    public Control control;
    public int alignment;

    public FormAttachment() {
    }

    public FormAttachment(int n) {
        this(n, 100, 0);
    }

    public FormAttachment(int n, int n2) {
        this(n, 100, n2);
    }

    public FormAttachment(int n, int n2, int n3) {
        if (n2 == 0) {
            SWT.error(7);
        }
        this.numerator = n;
        this.denominator = n2;
        this.offset = n3;
    }

    public FormAttachment(Control control) {
        this(control, 0, -1);
    }

    public FormAttachment(Control control, int n) {
        this(control, n, -1);
    }

    public FormAttachment(Control control, int n, int n2) {
        this.control = control;
        this.offset = n;
        this.alignment = n2;
    }

    FormAttachment divide(int n) {
        return new FormAttachment(this.numerator, this.denominator * n, this.offset / n);
    }

    int gcd(int n, int n2) {
        int n3;
        if ((n = Math.abs(n)) < (n2 = Math.abs(n2))) {
            n3 = n;
            n = n2;
            n2 = n3;
        }
        while (n2 != 0) {
            n3 = n;
            n = n2;
            n2 = n3 % n2;
        }
        return n;
    }

    FormAttachment minus(FormAttachment formAttachment) {
        FormAttachment formAttachment2 = new FormAttachment();
        formAttachment2.numerator = this.numerator * formAttachment.denominator - this.denominator * formAttachment.numerator;
        formAttachment2.denominator = this.denominator * formAttachment.denominator;
        int n = this.gcd(formAttachment2.denominator, formAttachment2.numerator);
        formAttachment2.numerator /= n;
        formAttachment2.denominator /= n;
        formAttachment2.offset = this.offset - formAttachment.offset;
        return formAttachment2;
    }

    FormAttachment minus(int n) {
        return new FormAttachment(this.numerator, this.denominator, this.offset - n);
    }

    FormAttachment plus(FormAttachment formAttachment) {
        FormAttachment formAttachment2 = new FormAttachment();
        formAttachment2.numerator = this.numerator * formAttachment.denominator + this.denominator * formAttachment.numerator;
        formAttachment2.denominator = this.denominator * formAttachment.denominator;
        int n = this.gcd(formAttachment2.denominator, formAttachment2.numerator);
        formAttachment2.numerator /= n;
        formAttachment2.denominator /= n;
        formAttachment2.offset = this.offset + formAttachment.offset;
        return formAttachment2;
    }

    FormAttachment plus(int n) {
        return new FormAttachment(this.numerator, this.denominator, this.offset + n);
    }

    int solveX(int n) {
        if (this.denominator == 0) {
            SWT.error(7);
        }
        return this.numerator * n / this.denominator + this.offset;
    }

    int solveY(int n) {
        if (this.numerator == 0) {
            SWT.error(7);
        }
        return (n - this.offset) * this.denominator / this.numerator;
    }

    public String toString() {
        String string = this.control != null ? this.control.toString() : this.numerator + "/" + this.denominator;
        return "{y = (" + string + (this.offset >= 0 ? ")x + " + this.offset : ")x - " + -this.offset) + "}";
    }
}

