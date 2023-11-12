/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.ImmutableSet
 *  org.apache.logging.log4j.LogManager
 */
package org.spongepowered.asm.mixin.gen;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.AccessorGenerator;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.gen.InvokerInfo;
import org.spongepowered.asm.mixin.gen.l;
import org.spongepowered.asm.mixin.gen.lI;
import org.spongepowered.asm.mixin.gen.ll;
import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.IReferenceMapperContext;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.ASMHelper;

public class AccessorInfo
extends SpecialMethodInfo {
    protected static final Pattern PATTERN_ACCESSOR = Pattern.compile("^(get|set|is|invoke|call)(([A-Z])(.*))$");
    protected final Type[] argTypes;
    protected final Type returnType;
    protected final AccessorType type;
    private final Type targetFieldType;
    protected final MemberInfo target;
    protected FieldNode targetField;
    protected MethodNode targetMethod;

    public AccessorInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        this(mixinTargetContext, methodNode, Accessor.class);
    }

    protected AccessorInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode, Class clazz) {
        super(mixinTargetContext, methodNode, ASMHelper.getVisibleAnnotation(methodNode, clazz));
        this.argTypes = Type.getArgumentTypes(methodNode.desc);
        this.returnType = Type.getReturnType(methodNode.desc);
        this.type = this.initType();
        this.targetFieldType = this.initTargetFieldType();
        this.target = this.initTarget();
    }

    protected AccessorType initType() {
        if (this.returnType.equals(Type.VOID_TYPE)) {
            return AccessorType.FIELD_SETTER;
        }
        return AccessorType.FIELD_GETTER;
    }

    protected Type initTargetFieldType() {
        switch (this.type) {
            case FIELD_GETTER: {
                if (this.argTypes.length > 0) {
                    throw new InvalidAccessorException((IReferenceMapperContext)this.mixin, this + " must take exactly 0 arguments, found " + this.argTypes.length);
                }
                return this.returnType;
            }
            case FIELD_SETTER: {
                if (this.argTypes.length != 1) {
                    throw new InvalidAccessorException((IReferenceMapperContext)this.mixin, this + " must take exactly 1 argument, found " + this.argTypes.length);
                }
                return this.argTypes[0];
            }
        }
        throw new InvalidAccessorException((IReferenceMapperContext)this.mixin, "Computed unsupported accessor type " + (Object)((Object)this.type) + " for " + this);
    }

    protected MemberInfo initTarget() {
        MemberInfo memberInfo = new MemberInfo(this.getTargetName(), null, this.targetFieldType.getDescriptor());
        this.annotation.visit("target", memberInfo.toString());
        return memberInfo;
    }

    protected String getTargetName() {
        String string = (String)ASMHelper.getAnnotationValue(this.annotation);
        if (Strings.isNullOrEmpty((String)string)) {
            String string2 = this.inflectTarget();
            if (string2 == null) {
                throw new InvalidAccessorException((IReferenceMapperContext)this.mixin, "Failed to inflect target name for " + this + ", supported prefixes: [get, set, is]");
            }
            return string2;
        }
        return MemberInfo.parse((String)string, (IReferenceMapperContext)this.mixin).name;
    }

    protected String inflectTarget() {
        return AccessorInfo.inflectTarget(this.method.name, this.type, this.toString(), this.mixin, this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE));
    }

    public static String inflectTarget(String string, AccessorType accessorType, String string2, IReferenceMapperContext iReferenceMapperContext, boolean bl) {
        Matcher matcher = PATTERN_ACCESSOR.matcher(string);
        if (matcher.matches()) {
            String string3 = matcher.group(1);
            String string4 = matcher.group(3);
            String string5 = matcher.group(4);
            String string6 = String.format("%s%s", AccessorInfo.toLowerCase(string4, !AccessorInfo.isUpperCase(string5)), string5);
            if (!accessorType.isExpectedPrefix(string3) && bl) {
                LogManager.getLogger((String)"mixin").warn("Unexpected prefix for {}, found [{}] expecting {}", new Object[]{string2, string3, accessorType.getExpectedPrefixes()});
            }
            return MemberInfo.parse((String)string6, (IReferenceMapperContext)iReferenceMapperContext).name;
        }
        return null;
    }

    public final MemberInfo getTarget() {
        return this.target;
    }

    public final Type getTargetFieldType() {
        return this.targetFieldType;
    }

    public final FieldNode getTargetField() {
        return this.targetField;
    }

    public final MethodNode getTargetMethod() {
        return this.targetMethod;
    }

    public final Type getReturnType() {
        return this.returnType;
    }

    public final Type[] getArgTypes() {
        return this.argTypes;
    }

    public String toString() {
        return String.format("%s->@%s[%s]::%s%s", this.mixin.toString(), ASMHelper.getSimpleName(this.annotation), this.type.toString(), this.method.name, this.method.desc);
    }

    public void locate() {
        this.targetField = this.findTargetField();
    }

    public MethodNode generate() {
        return this.type.getGenerator(this).generate();
    }

    private FieldNode findTargetField() {
        return (FieldNode)this.findTarget(this.classNode.fields);
    }

    protected Object findTarget(List list) {
        Object var2_2 = null;
        ArrayList arrayList = new ArrayList();
        for (Object e : list) {
            String string;
            String string2 = AccessorInfo.getNodeDesc(e);
            if (string2 == null || !string2.equals(this.target.desc) || (string = AccessorInfo.getNodeName(e)) == null) continue;
            if (string.equals(this.target.name)) {
                var2_2 = e;
            }
            if (!string.equalsIgnoreCase(this.target.name)) continue;
            arrayList.add(e);
        }
        if (var2_2 != null) {
            if (arrayList.size() > 1) {
                LogManager.getLogger((String)"mixin").debug("{} found an exact match for {} but other candidates were found!", new Object[]{this, this.target});
            }
            return var2_2;
        }
        if (arrayList.size() == 1) {
            return arrayList.get(0);
        }
        throw new InvalidAccessorException(this, "Multiple candidates were found matching " + this.target + " in " + this.classNode.name + " for " + this);
    }

    private static String getNodeDesc(Object object) {
        return object instanceof MethodNode ? ((MethodNode)object).desc : (object instanceof FieldNode ? ((FieldNode)object).desc : null);
    }

    private static String getNodeName(Object object) {
        return object instanceof MethodNode ? ((MethodNode)object).name : (object instanceof FieldNode ? ((FieldNode)object).name : null);
    }

    public static AccessorInfo of(MixinTargetContext mixinTargetContext, MethodNode methodNode, Class clazz) {
        if (clazz == Accessor.class) {
            return new AccessorInfo(mixinTargetContext, methodNode);
        }
        if (clazz == Invoker.class) {
            return new InvokerInfo(mixinTargetContext, methodNode);
        }
        throw new InvalidAccessorException((IReferenceMapperContext)mixinTargetContext, "Could not parse accessor for unknown type " + clazz.getName());
    }

    private static String toLowerCase(String string, boolean bl) {
        return bl ? string.toLowerCase() : string;
    }

    private static boolean isUpperCase(String string) {
        return string.toUpperCase().equals(string);
    }

    public static abstract class AccessorType
    extends Enum {
        public static final /* enum */ AccessorType FIELD_GETTER = new lI((Set)ImmutableSet.of((Object)"get", (Object)"is"));
        public static final /* enum */ AccessorType FIELD_SETTER = new l((Set)ImmutableSet.of((Object)"set"));
        public static final /* enum */ AccessorType METHOD_PROXY = new ll((Set)ImmutableSet.of((Object)"call", (Object)"invoke"));
        private final Set expectedPrefixes;
        private static final AccessorType[] $VALUES = new AccessorType[]{FIELD_GETTER, FIELD_SETTER, METHOD_PROXY};

        public static AccessorType[] values() {
            return (AccessorType[])$VALUES.clone();
        }

        public static AccessorType valueOf(String string) {
            return Enum.valueOf(AccessorType.class, string);
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        private AccessorType() {
            void var3_1;
            void var2_-1;
            void var1_-1;
            this.expectedPrefixes = var3_1;
        }

        public boolean isExpectedPrefix(String string) {
            return this.expectedPrefixes.contains(string);
        }

        public String getExpectedPrefixes() {
            return this.expectedPrefixes.toString();
        }

        abstract AccessorGenerator getGenerator(AccessorInfo var1);

        /*
         * WARNING - void declaration
         */
        AccessorType() {
            this((String)var1_-1, (int)i, (Set)var3_2);
            void var3_2;
            void var1_-1;
        }
    }
}

