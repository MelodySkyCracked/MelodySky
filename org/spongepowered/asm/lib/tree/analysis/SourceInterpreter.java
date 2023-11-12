/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree.analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
import org.spongepowered.asm.lib.tree.analysis.Interpreter;
import org.spongepowered.asm.lib.tree.analysis.SmallSet;
import org.spongepowered.asm.lib.tree.analysis.SourceValue;
import org.spongepowered.asm.lib.tree.analysis.Value;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SourceInterpreter
extends Interpreter
implements Opcodes {
    public SourceInterpreter() {
        super(327680);
    }

    protected SourceInterpreter(int n) {
        super(n);
    }

    public SourceValue newValue(Type type) {
        if (type == Type.VOID_TYPE) {
            return null;
        }
        return new SourceValue(type == null ? 1 : type.getSize());
    }

    public SourceValue newOperation(AbstractInsnNode abstractInsnNode) {
        int n;
        switch (abstractInsnNode.getOpcode()) {
            case 9: 
            case 10: 
            case 14: 
            case 15: {
                n = 2;
                break;
            }
            case 18: {
                Object object = ((LdcInsnNode)abstractInsnNode).cst;
                n = object instanceof Long || object instanceof Double ? 2 : 1;
                break;
            }
            case 178: {
                n = Type.getType(((FieldInsnNode)abstractInsnNode).desc).getSize();
                break;
            }
            default: {
                n = 1;
            }
        }
        return new SourceValue(n, abstractInsnNode);
    }

    public SourceValue copyOperation(AbstractInsnNode abstractInsnNode, SourceValue sourceValue) {
        return new SourceValue(sourceValue.getSize(), abstractInsnNode);
    }

    public SourceValue unaryOperation(AbstractInsnNode abstractInsnNode, SourceValue sourceValue) {
        int n;
        switch (abstractInsnNode.getOpcode()) {
            case 117: 
            case 119: 
            case 133: 
            case 135: 
            case 138: 
            case 140: 
            case 141: 
            case 143: {
                n = 2;
                break;
            }
            case 180: {
                n = Type.getType(((FieldInsnNode)abstractInsnNode).desc).getSize();
                break;
            }
            default: {
                n = 1;
            }
        }
        return new SourceValue(n, abstractInsnNode);
    }

    public SourceValue binaryOperation(AbstractInsnNode abstractInsnNode, SourceValue sourceValue, SourceValue sourceValue2) {
        int n;
        switch (abstractInsnNode.getOpcode()) {
            case 47: 
            case 49: 
            case 97: 
            case 99: 
            case 101: 
            case 103: 
            case 105: 
            case 107: 
            case 109: 
            case 111: 
            case 113: 
            case 115: 
            case 121: 
            case 123: 
            case 125: 
            case 127: 
            case 129: 
            case 131: {
                n = 2;
                break;
            }
            default: {
                n = 1;
            }
        }
        return new SourceValue(n, abstractInsnNode);
    }

    public SourceValue ternaryOperation(AbstractInsnNode abstractInsnNode, SourceValue sourceValue, SourceValue sourceValue2, SourceValue sourceValue3) {
        return new SourceValue(1, abstractInsnNode);
    }

    public SourceValue naryOperation(AbstractInsnNode abstractInsnNode, List list) {
        int n;
        int n2 = abstractInsnNode.getOpcode();
        if (n2 == 197) {
            n = 1;
        } else {
            String string = n2 == 186 ? ((InvokeDynamicInsnNode)abstractInsnNode).desc : ((MethodInsnNode)abstractInsnNode).desc;
            n = Type.getReturnType(string).getSize();
        }
        return new SourceValue(n, abstractInsnNode);
    }

    public void returnOperation(AbstractInsnNode abstractInsnNode, SourceValue sourceValue, SourceValue sourceValue2) {
    }

    public SourceValue merge(SourceValue sourceValue, SourceValue sourceValue2) {
        if (sourceValue.insns instanceof SmallSet && sourceValue2.insns instanceof SmallSet) {
            Set set = ((SmallSet)sourceValue.insns).union((SmallSet)sourceValue2.insns);
            if (set == sourceValue.insns && sourceValue.size == sourceValue2.size) {
                return sourceValue;
            }
            return new SourceValue(Math.min(sourceValue.size, sourceValue2.size), set);
        }
        if (sourceValue.size != sourceValue2.size || !sourceValue.insns.containsAll(sourceValue2.insns)) {
            HashSet hashSet = new HashSet();
            hashSet.addAll(sourceValue.insns);
            hashSet.addAll(sourceValue2.insns);
            return new SourceValue(Math.min(sourceValue.size, sourceValue2.size), hashSet);
        }
        return sourceValue;
    }

    public Value merge(Value value, Value value2) {
        return this.merge((SourceValue)value, (SourceValue)value2);
    }

    public void returnOperation(AbstractInsnNode abstractInsnNode, Value value, Value value2) throws AnalyzerException {
        this.returnOperation(abstractInsnNode, (SourceValue)value, (SourceValue)value2);
    }

    public Value naryOperation(AbstractInsnNode abstractInsnNode, List list) throws AnalyzerException {
        return this.naryOperation(abstractInsnNode, list);
    }

    public Value ternaryOperation(AbstractInsnNode abstractInsnNode, Value value, Value value2, Value value3) throws AnalyzerException {
        return this.ternaryOperation(abstractInsnNode, (SourceValue)value, (SourceValue)value2, (SourceValue)value3);
    }

    public Value binaryOperation(AbstractInsnNode abstractInsnNode, Value value, Value value2) throws AnalyzerException {
        return this.binaryOperation(abstractInsnNode, (SourceValue)value, (SourceValue)value2);
    }

    public Value unaryOperation(AbstractInsnNode abstractInsnNode, Value value) throws AnalyzerException {
        return this.unaryOperation(abstractInsnNode, (SourceValue)value);
    }

    public Value copyOperation(AbstractInsnNode abstractInsnNode, Value value) throws AnalyzerException {
        return this.copyOperation(abstractInsnNode, (SourceValue)value);
    }

    public Value newOperation(AbstractInsnNode abstractInsnNode) throws AnalyzerException {
        return this.newOperation(abstractInsnNode);
    }

    public Value newValue(Type type) {
        return this.newValue(type);
    }
}

