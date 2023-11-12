/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 */
package org.spongepowered.asm.util;

import com.google.common.base.Strings;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;

public class SignaturePrinter {
    private final String name;
    private final Type returnType;
    private final Type[] argTypes;
    private final String[] argNames;
    private String modifiers = "private void";
    private boolean fullyQualified;

    public SignaturePrinter(MethodNode methodNode) {
        this(methodNode.name, Type.VOID_TYPE, Type.getArgumentTypes(methodNode.desc));
        this.setModifiers(methodNode);
    }

    public SignaturePrinter(MethodNode methodNode, String[] stringArray) {
        this(methodNode.name, Type.VOID_TYPE, Type.getArgumentTypes(methodNode.desc), stringArray);
        this.setModifiers(methodNode);
    }

    public SignaturePrinter(MemberInfo memberInfo) {
        this(memberInfo.name, memberInfo.desc);
    }

    public SignaturePrinter(String string, String string2) {
        this(string, Type.getReturnType(string2), Type.getArgumentTypes(string2));
    }

    public SignaturePrinter(String string, Type type, Type[] typeArray) {
        this.name = string;
        this.returnType = type;
        this.argTypes = new Type[typeArray.length];
        this.argNames = new String[typeArray.length];
        int n = 0;
        for (int i = 0; i < typeArray.length; ++i) {
            if (typeArray[i] == null) continue;
            this.argTypes[i] = typeArray[i];
            this.argNames[i] = "var" + n++;
        }
    }

    public SignaturePrinter(String string, Type type, LocalVariableNode[] localVariableNodeArray) {
        this.name = string;
        this.returnType = type;
        this.argTypes = new Type[localVariableNodeArray.length];
        this.argNames = new String[localVariableNodeArray.length];
        for (int i = 0; i < localVariableNodeArray.length; ++i) {
            if (localVariableNodeArray[i] == null) continue;
            this.argTypes[i] = Type.getType(localVariableNodeArray[i].desc);
            this.argNames[i] = localVariableNodeArray[i].name;
        }
    }

    public SignaturePrinter(String string, Type type, Type[] typeArray, String[] stringArray) {
        this.name = string;
        this.returnType = type;
        this.argTypes = typeArray;
        this.argNames = stringArray;
        if (this.argTypes.length > this.argNames.length) {
            throw new IllegalArgumentException(String.format("Types array length must not exceed names array length! (names=%d, types=%d)", this.argNames.length, this.argTypes.length));
        }
    }

    public String getFormattedArgs() {
        return this.appendArgs(new StringBuilder(), true).toString();
    }

    public String getReturnType() {
        return SignaturePrinter.getTypeName(this.returnType, false, this.fullyQualified);
    }

    public void setModifiers(MethodNode methodNode) {
        String string = SignaturePrinter.getTypeName(Type.getReturnType(methodNode.desc), false, this.fullyQualified);
        if ((methodNode.access & 1) != 0) {
            this.setModifiers("public " + string);
        } else if ((methodNode.access & 4) != 0) {
            this.setModifiers("protected " + string);
        } else if ((methodNode.access & 2) != 0) {
            this.setModifiers("private " + string);
        } else {
            this.setModifiers(string);
        }
    }

    public SignaturePrinter setModifiers(String string) {
        this.modifiers = string.replace("${returnType}", this.getReturnType());
        return this;
    }

    public SignaturePrinter setFullyQualified(boolean bl) {
        this.fullyQualified = bl;
        return this;
    }

    public boolean isFullyQualified() {
        return this.fullyQualified;
    }

    public String toString() {
        return this.appendArgs(new StringBuilder().append(this.modifiers).append(" ").append(this.name), false).toString();
    }

    public String toDescriptor() {
        StringBuilder stringBuilder = this.appendArgs(new StringBuilder(), true);
        return stringBuilder.append(SignaturePrinter.getTypeName(this.returnType, false, this.fullyQualified)).toString();
    }

    private StringBuilder appendArgs(StringBuilder stringBuilder, boolean bl) {
        stringBuilder.append('(');
        for (int i = 0; i < this.argTypes.length; ++i) {
            if (this.argTypes[i] == null) continue;
            if (i > 0) {
                stringBuilder.append(',');
                if (!bl) {
                    stringBuilder.append(' ');
                }
            }
            try {
                String string = bl ? null : (Strings.isNullOrEmpty((String)this.argNames[i]) ? "unnamed" + i : this.argNames[i]);
                this.appendType(stringBuilder, this.argTypes[i], string);
                continue;
            }
            catch (Exception exception) {
                System.err.printf("\n\n>>> argTypes=%d, argNames=%d\n\n", this.argTypes.length, this.argNames.length);
                throw new RuntimeException(exception);
            }
        }
        return stringBuilder.append(")");
    }

    private StringBuilder appendType(StringBuilder stringBuilder, Type type, String string) {
        switch (type.getSort()) {
            case 9: {
                return this.appendType(stringBuilder, type.getElementType(), string).append("[]");
            }
            case 10: {
                return this.appendType(stringBuilder, type.getClassName(), string);
            }
        }
        stringBuilder.append(SignaturePrinter.getTypeName(type, false, this.fullyQualified));
        if (string != null) {
            stringBuilder.append(' ').append(string);
        }
        return stringBuilder;
    }

    private StringBuilder appendType(StringBuilder stringBuilder, String string, String string2) {
        if (!this.fullyQualified) {
            string = string.substring(string.lastIndexOf(46) + 1);
        }
        stringBuilder.append(string);
        if (string.endsWith("CallbackInfoReturnable")) {
            stringBuilder.append('<').append(SignaturePrinter.getTypeName(this.returnType, true, this.fullyQualified)).append('>');
        }
        if (string2 != null) {
            stringBuilder.append(' ').append(string2);
        }
        return stringBuilder;
    }

    public static String getTypeName(Type type, boolean bl) {
        return SignaturePrinter.getTypeName(type, bl, false);
    }

    public static String getTypeName(Type type, boolean bl, boolean bl2) {
        switch (type.getSort()) {
            case 0: {
                return bl ? "Void" : "void";
            }
            case 1: {
                return bl ? "Boolean" : "boolean";
            }
            case 2: {
                return bl ? "Character" : "char";
            }
            case 3: {
                return bl ? "Byte" : "byte";
            }
            case 4: {
                return bl ? "Short" : "short";
            }
            case 5: {
                return bl ? "Integer" : "int";
            }
            case 6: {
                return bl ? "Float" : "float";
            }
            case 7: {
                return bl ? "Long" : "long";
            }
            case 8: {
                return bl ? "Double" : "double";
            }
            case 9: {
                return SignaturePrinter.getTypeName(type.getElementType(), bl, bl2) + "[]";
            }
            case 10: {
                String string = type.getClassName();
                if (!bl2) {
                    string = string.substring(string.lastIndexOf(46) + 1);
                }
                return string;
            }
        }
        return "Object";
    }
}

