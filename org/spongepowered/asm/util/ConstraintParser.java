/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.util.ASMHelper;
import org.spongepowered.asm.util.ITokenProvider;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;

public final class ConstraintParser {
    private ConstraintParser() {
    }

    public static Constraint parse(String string) {
        if (string == null || string.length() == 0) {
            return Constraint.NONE;
        }
        String[] stringArray = string.replaceAll("\\s", "").toUpperCase().split(";");
        Constraint constraint = null;
        for (String string2 : stringArray) {
            Constraint constraint2 = new Constraint(string2);
            if (constraint == null) {
                constraint = constraint2;
                continue;
            }
            constraint.append(constraint2);
        }
        return constraint != null ? constraint : Constraint.NONE;
    }

    public static Constraint parse(AnnotationNode annotationNode) {
        String string = (String)ASMHelper.getAnnotationValue(annotationNode, "constraints", "");
        return ConstraintParser.parse(string);
    }

    public static class Constraint {
        public static final Constraint NONE = new Constraint();
        private static final Pattern pattern = Pattern.compile("^([A-Z0-9\\-_\\.]+)\\((?:(<|<=|>|>=|=)?([0-9]+)(<|(-)([0-9]+)?|>|(\\+)([0-9]+)?)?)?\\)$");
        private final String expr;
        private String token;
        private String[] constraint;
        private int min = Integer.MIN_VALUE;
        private int max = Integer.MAX_VALUE;
        private Constraint next;

        Constraint(String string) {
            this.expr = string;
            Matcher matcher = pattern.matcher(string);
            if (!matcher.matches()) {
                throw new InvalidConstraintException("Constraint syntax was invalid parsing: " + this.expr);
            }
            this.token = matcher.group(1);
            this.constraint = new String[]{matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5), matcher.group(6), matcher.group(7), matcher.group(8)};
            this.parse();
        }

        private Constraint() {
            this.expr = null;
            this.token = "*";
            this.constraint = new String[0];
        }

        private void parse() {
            Constraint constraint;
            Constraint constraint2 = this;
            if (true != null) {
                return;
            }
            this.min = this.val(1);
            this.max = this.min++;
            boolean bl = this.has(0);
            Constraint constraint3 = this;
            if (4 != null) {
                if (bl) {
                    throw new InvalidConstraintException("Unexpected modifier '" + this.elem(0) + "' in " + this.expr + " parsing range");
                }
                this.max = this.val(4);
                if (this.max < this.min) {
                    throw new InvalidConstraintException("Invalid range specified '" + this.max + "' is less than " + this.min + " in " + this.expr);
                }
                return;
            }
            Constraint constraint4 = this;
            if (6 != null) {
                if (bl) {
                    throw new InvalidConstraintException("Unexpected modifier '" + this.elem(0) + "' in " + this.expr + " parsing range");
                }
                this.max = this.min + this.val(6);
                return;
            }
            if (bl) {
                constraint = this;
                if (3 != null) {
                    throw new InvalidConstraintException("Unexpected trailing modifier '" + this.elem(3) + "' in " + this.expr);
                }
                String string = this.elem(0);
                if (">".equals(string)) {
                    this.max = Integer.MAX_VALUE;
                } else if (">=".equals(string)) {
                    this.max = Integer.MAX_VALUE;
                } else if ("<".equals(string)) {
                    this.max = --this.min;
                    this.min = Integer.MIN_VALUE;
                } else if ("<=".equals(string)) {
                    this.max = this.min;
                    this.min = Integer.MIN_VALUE;
                }
            } else {
                constraint = this;
                if (2 != null) {
                    String string = this.elem(2);
                    if ("<".equals(string)) {
                        this.max = this.min;
                        this.min = Integer.MIN_VALUE;
                    } else {
                        this.max = Integer.MAX_VALUE;
                    }
                }
            }
        }

        private String elem(int n) {
            return this.constraint[n];
        }

        private int val(int n) {
            return this.constraint[n] != null ? Integer.parseInt(this.constraint[n]) : 0;
        }

        void append(Constraint constraint) {
            if (this.next != null) {
                this.next.append(constraint);
                return;
            }
            this.next = constraint;
        }

        public String getToken() {
            return this.token;
        }

        public int getMin() {
            return this.min;
        }

        public int getMax() {
            return this.max;
        }

        public void check(ITokenProvider iTokenProvider) throws ConstraintViolationException {
            if (this != NONE) {
                Integer n = iTokenProvider.getToken(this.token);
                if (n == null) {
                    throw new ConstraintViolationException("The token '" + this.token + "' could not be resolved in " + iTokenProvider, this);
                }
                if (n < this.min) {
                    throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + n + ") which is less than the minimum value " + this.min + " in " + iTokenProvider, this, (int)n);
                }
                if (n > this.max) {
                    throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + n + ") which is greater than the maximum value " + this.max + " in " + iTokenProvider, this, (int)n);
                }
            }
            if (this.next != null) {
                this.next.check(iTokenProvider);
            }
        }

        public String getRangeHumanReadable() {
            if (this.min == Integer.MIN_VALUE && this.max == Integer.MAX_VALUE) {
                return "ANY VALUE";
            }
            if (this.min == Integer.MIN_VALUE) {
                return String.format("less than or equal to %d", this.max);
            }
            if (this.max == Integer.MAX_VALUE) {
                return String.format("greater than or equal to %d", this.min);
            }
            if (this.min == this.max) {
                return String.format("%d", this.min);
            }
            return String.format("between %d and %d", this.min, this.max);
        }

        public String toString() {
            return String.format("Constraint(%s [%d-%d])", this.token, this.min, this.max);
        }
    }
}

