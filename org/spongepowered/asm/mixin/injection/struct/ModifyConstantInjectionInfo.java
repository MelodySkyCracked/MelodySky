/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyConstantInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.ASMHelper;

public class ModifyConstantInjectionInfo
extends InjectionInfo {
    public ModifyConstantInjectionInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode) {
        super(mixinTargetContext, methodNode, annotationNode);
    }

    @Override
    protected List readInjectionPoints(String string) {
        AnnotationNode annotationNode = (AnnotationNode)ASMHelper.getAnnotationValue(this.annotation, "constant");
        ArrayList<AnnotationNode> arrayList = new ArrayList<AnnotationNode>();
        arrayList.add(annotationNode);
        return arrayList;
    }

    @Override
    protected void parseInjectionPoints(List list) {
        Type type = Type.getReturnType(this.method.desc);
        for (AnnotationNode annotationNode : list) {
            this.injectionPoints.add(new BeforeConstant(this, annotationNode, type.getDescriptor()));
        }
    }

    @Override
    protected Injector parseInjector(AnnotationNode annotationNode) {
        return new ModifyConstantInjector(this);
    }

    @Override
    protected String getDescription() {
        return "Constant modifier method";
    }

    static class BeforeConstant
    extends InjectionPoint {
        private static final Logger logger = LogManager.getLogger((String)"mixin");
        private final int ordinal;
        private final boolean nullValue;
        private final Integer intValue;
        private final Float floatValue;
        private final Long longValue;
        private final Double doubleValue;
        private final String stringValue;
        private final Type typeValue;
        private final String matchByType;
        private final boolean log;

        public BeforeConstant(InjectionInfo injectionInfo, AnnotationNode annotationNode, String string) {
            Boolean bl = (Boolean)ASMHelper.getAnnotationValue(annotationNode, "nullValue", (Object)null);
            this.ordinal = (Integer)ASMHelper.getAnnotationValue(annotationNode, "ordinal", -1);
            this.nullValue = bl != null ? bl : false;
            this.intValue = (Integer)ASMHelper.getAnnotationValue(annotationNode, "intValue", (Object)null);
            this.floatValue = (Float)ASMHelper.getAnnotationValue(annotationNode, "floatValue", (Object)null);
            this.longValue = (Long)ASMHelper.getAnnotationValue(annotationNode, "longValue", (Object)null);
            this.doubleValue = (Double)ASMHelper.getAnnotationValue(annotationNode, "doubleValue", (Object)null);
            this.stringValue = (String)ASMHelper.getAnnotationValue(annotationNode, "stringValue", (Object)null);
            this.typeValue = (Type)ASMHelper.getAnnotationValue(annotationNode, "classValue", (Object)null);
            int n = BeforeConstant.count(bl, this.intValue, this.floatValue, this.longValue, this.doubleValue, this.stringValue, this.typeValue);
            if (n == 1) {
                string = null;
            } else if (n > 1) {
                throw new InvalidInjectionException(injectionInfo, "Conflicting constant discriminators specified on @Constant annotation for " + injectionInfo);
            }
            this.matchByType = string;
            this.log = (Boolean)ASMHelper.getAnnotationValue(annotationNode, "log", Boolean.FALSE);
        }

        @Override
        public boolean find(String string, InsnList insnList, Collection collection) {
            boolean bl = false;
            if (this.log) {
                logger.info("BeforeConstant is searching for an constants in method with descriptor {}", new Object[]{string});
            }
            ListIterator listIterator = insnList.iterator();
            int n = 0;
            while (listIterator.hasNext()) {
                AbstractInsnNode abstractInsnNode = (AbstractInsnNode)listIterator.next();
                boolean bl2 = this.matchesInsn(abstractInsnNode);
                if (!bl2) continue;
                if (this.log) {
                    String string2 = this.matchByType != null ? " TYPE" : " value";
                    logger.info("    BeforeConstant found a matching constant{} at ordinal {}", new Object[]{string2, n});
                }
                if (this.ordinal == -1 || this.ordinal == n) {
                    if (this.log) {
                        logger.info("      BeforeConstant found {}", new Object[]{ASMHelper.getNodeDescriptionForDebug(abstractInsnNode).trim()});
                    }
                    collection.add(abstractInsnNode);
                    bl = true;
                }
                ++n;
            }
            return bl;
        }

        private boolean matchesInsn(AbstractInsnNode abstractInsnNode) {
            if (!ASMHelper.isConstant(abstractInsnNode)) {
                return false;
            }
            Object object = ASMHelper.getConstant(abstractInsnNode);
            if (object == null) {
                if (this.log) {
                    logger.info("  BeforeConstant found NULL constant: nullValue = {}", new Object[]{this.nullValue});
                }
                return this.nullValue || "Ljava/lang/Object;".equals(this.matchByType);
            }
            if (object instanceof Integer) {
                if (this.log) {
                    logger.info("  BeforeConstant found INTEGER constant: value = {}, intValue = {}", new Object[]{object, this.intValue});
                }
                return object.equals(this.intValue) || "I".equals(this.matchByType);
            }
            if (object instanceof Float) {
                if (this.log) {
                    logger.info("  BeforeConstant found FLOAT constant: value = {}, floatValue = {}", new Object[]{object, this.floatValue});
                }
                return object.equals(this.floatValue) || "F".equals(this.matchByType);
            }
            if (object instanceof Long) {
                if (this.log) {
                    logger.info("  BeforeConstant found LONG constant: value = {}, longValue = {}", new Object[]{object, this.longValue});
                }
                return object.equals(this.longValue) || "J".equals(this.matchByType);
            }
            if (object instanceof Double) {
                if (this.log) {
                    logger.info("  BeforeConstant found DOUBLE constant: value = {}, doubleValue = {}", new Object[]{object, this.doubleValue});
                }
                return object.equals(this.doubleValue) || "D".equals(this.matchByType);
            }
            if (object instanceof String) {
                if (this.log) {
                    logger.info("  BeforeConstant found STRING constant: value = {}, stringValue = {}", new Object[]{object, this.stringValue});
                }
                return object.equals(this.stringValue) || "Ljava/lang/String;".equals(this.matchByType);
            }
            if (object instanceof Type) {
                if (this.log) {
                    logger.info("  BeforeConstant found CLASS constant: value = {}, typeValue = {}", new Object[]{object, this.typeValue});
                }
                return object.equals(this.typeValue) || "Ljava/lang/Class;".equals(this.matchByType);
            }
            return false;
        }

        private static int count(Object ... objectArray) {
            int n = 0;
            for (Object object : objectArray) {
                if (object == null) continue;
                ++n;
            }
            return n;
        }
    }
}

