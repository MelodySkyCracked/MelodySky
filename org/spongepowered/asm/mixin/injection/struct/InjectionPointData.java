/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.common.base.Strings
 */
package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InvalidMemberDescriptorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionPointException;
import org.spongepowered.asm.mixin.refmap.IReferenceMapperContext;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

public class InjectionPointData {
    private static final Pattern AT_PATTERN = InjectionPointData.createPattern();
    private final Map args = new HashMap();
    private final MixinTargetContext mixin;
    private final MethodNode method;
    private final AnnotationNode parent;
    private final String at;
    private final String type;
    private final InjectionPoint.Selector selector;
    private final String target;
    private final String slice;
    private final int ordinal;
    private final int opcode;

    public InjectionPointData(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode, String string, List list, String string2, String string3, int n, int n2) {
        this.mixin = mixinTargetContext;
        this.method = methodNode;
        this.parent = annotationNode;
        this.at = string;
        this.target = string2;
        this.slice = Strings.nullToEmpty((String)string3);
        this.ordinal = Math.max(-1, n);
        this.opcode = n2;
        this.parseArgs(list);
        this.args.put("target", string2);
        this.args.put("ordinal", String.valueOf(n));
        this.args.put("opcode", String.valueOf(n2));
        Matcher matcher = AT_PATTERN.matcher(string);
        this.type = InjectionPointData.parseType(matcher, string);
        this.selector = InjectionPointData.parseSelector(matcher);
    }

    private void parseArgs(List list) {
        if (list == null) {
            return;
        }
        for (String string : list) {
            if (string == null) continue;
            int n = string.indexOf(61);
            if (n > -1) {
                this.args.put(string.substring(0, n), string.substring(n + 1));
                continue;
            }
            this.args.put(string, "");
        }
    }

    public String getAt() {
        return this.at;
    }

    public String getType() {
        return this.type;
    }

    public InjectionPoint.Selector getSelector() {
        return this.selector;
    }

    public MixinTargetContext getMixin() {
        return this.mixin;
    }

    public MethodNode getMethod() {
        return this.method;
    }

    public AnnotationNode getParent() {
        return this.parent;
    }

    public String getSlice() {
        return this.slice;
    }

    public Type getReturnType() {
        return Type.getReturnType(this.method.desc);
    }

    public LocalVariableDiscriminator getLocalVariableDiscriminator() {
        return LocalVariableDiscriminator.parse(this.parent);
    }

    public String get(String string, String string2) {
        String string3 = (String)this.args.get(string);
        return string3 != null ? string3 : string2;
    }

    public int get(String string, int n) {
        return this.parseInt(this.get(string, String.valueOf(n)), n);
    }

    public boolean get(String string, boolean bl) {
        return this.parseBoolean(this.get(string, String.valueOf(bl)), bl);
    }

    public MemberInfo get(String string) {
        try {
            return MemberInfo.parseAndValidate(this.get(string, ""), this.mixin);
        }
        catch (InvalidMemberDescriptorException invalidMemberDescriptorException) {
            throw new InvalidInjectionPointException((IReferenceMapperContext)this.mixin, "Failed parsing @At(\"%s\").%s descriptor \"%s\" on %s", this.at, string, this.target, InjectionInfo.describeInjector(this.mixin, this.parent, this.method));
        }
    }

    private int parseInt(String string, int n) {
        try {
            return Integer.parseInt(string);
        }
        catch (Exception exception) {
            return n;
        }
    }

    private boolean parseBoolean(String string, boolean bl) {
        try {
            return Boolean.parseBoolean(string);
        }
        catch (Exception exception) {
            return bl;
        }
    }

    public MemberInfo getTarget() {
        try {
            return MemberInfo.parseAndValidate(this.target, this.mixin);
        }
        catch (InvalidMemberDescriptorException invalidMemberDescriptorException) {
            throw new InvalidInjectionPointException((IReferenceMapperContext)this.mixin, "Failed parsing @At(\"%s\") descriptor \"%s\" on %s", this.at, this.target, InjectionInfo.describeInjector(this.mixin, this.parent, this.method));
        }
    }

    public int getOrdinal() {
        return this.ordinal;
    }

    public int getOpcode() {
        return this.opcode;
    }

    public int getOpcode(int n) {
        return this.opcode > 0 ? this.opcode : n;
    }

    public int getOpcode(int n, int ... nArray) {
        for (int n2 : nArray) {
            if (this.opcode != n2) continue;
            return this.opcode;
        }
        return n;
    }

    public String toString() {
        return this.type;
    }

    private static Pattern createPattern() {
        return Pattern.compile(String.format("^([^:]+):?(%s)?$", Joiner.on((char)'|').join((Object[])InjectionPoint.Selector.values())));
    }

    private static String parseType(Matcher matcher, String string) {
        return matcher.matches() ? matcher.group(1) : string;
    }

    private static InjectionPoint.Selector parseSelector(Matcher matcher) {
        return matcher.matches() && matcher.group(2) != null ? InjectionPoint.Selector.valueOf(matcher.group(2)) : InjectionPoint.Selector.DEFAULT;
    }

    public static String parseType(String string) {
        Matcher matcher = AT_PATTERN.matcher(string);
        return InjectionPointData.parseType(matcher, string);
    }
}

