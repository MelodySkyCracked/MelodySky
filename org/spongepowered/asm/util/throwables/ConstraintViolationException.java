/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.util.ConstraintParser;

public class ConstraintViolationException
extends Exception {
    private static final String MISSING_VALUE = "UNRESOLVED";
    private static final long serialVersionUID = 1L;
    private final ConstraintParser.Constraint constraint;
    private final String badValue;

    public ConstraintViolationException(ConstraintParser.Constraint constraint) {
        this.constraint = constraint;
        this.badValue = MISSING_VALUE;
    }

    public ConstraintViolationException(ConstraintParser.Constraint constraint, int n) {
        this.constraint = constraint;
        this.badValue = String.valueOf(n);
    }

    public ConstraintViolationException(String string, ConstraintParser.Constraint constraint) {
        super(string);
        this.constraint = constraint;
        this.badValue = MISSING_VALUE;
    }

    public ConstraintViolationException(String string, ConstraintParser.Constraint constraint, int n) {
        super(string);
        this.constraint = constraint;
        this.badValue = String.valueOf(n);
    }

    public ConstraintViolationException(Throwable throwable, ConstraintParser.Constraint constraint) {
        super(throwable);
        this.constraint = constraint;
        this.badValue = MISSING_VALUE;
    }

    public ConstraintViolationException(Throwable throwable, ConstraintParser.Constraint constraint, int n) {
        super(throwable);
        this.constraint = constraint;
        this.badValue = String.valueOf(n);
    }

    public ConstraintViolationException(String string, Throwable throwable, ConstraintParser.Constraint constraint) {
        super(string, throwable);
        this.constraint = constraint;
        this.badValue = MISSING_VALUE;
    }

    public ConstraintViolationException(String string, Throwable throwable, ConstraintParser.Constraint constraint, int n) {
        super(string, throwable);
        this.constraint = constraint;
        this.badValue = String.valueOf(n);
    }

    public ConstraintParser.Constraint getConstraint() {
        return this.constraint;
    }

    public String getBadValue() {
        return this.badValue;
    }
}

