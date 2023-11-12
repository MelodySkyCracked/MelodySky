/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree.analysis;

import java.util.List;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.IntInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MultiANewArrayInsnNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Interpreter;
import org.spongepowered.asm.lib.tree.analysis.Value;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BasicInterpreter
extends Interpreter
implements Opcodes {
    public BasicInterpreter() {
        super(327680);
    }

    protected BasicInterpreter(int n) {
        super(n);
    }

    public BasicValue newValue(Type type) {
        if (type == null) {
            return BasicValue.UNINITIALIZED_VALUE;
        }
        switch (type.getSort()) {
            case 0: {
                return null;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: {
                return BasicValue.INT_VALUE;
            }
            case 6: {
                return BasicValue.FLOAT_VALUE;
            }
            case 7: {
                return BasicValue.LONG_VALUE;
            }
            case 8: {
                return BasicValue.DOUBLE_VALUE;
            }
            case 9: 
            case 10: {
                return BasicValue.REFERENCE_VALUE;
            }
        }
        throw new Error("Internal error");
    }

    public BasicValue newOperation(AbstractInsnNode abstractInsnNode) throws AnalyzerException {
        switch (abstractInsnNode.getOpcode()) {
            case 1: {
                return this.newValue(Type.getObjectType("null"));
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: {
                return BasicValue.INT_VALUE;
            }
            case 9: 
            case 10: {
                return BasicValue.LONG_VALUE;
            }
            case 11: 
            case 12: 
            case 13: {
                return BasicValue.FLOAT_VALUE;
            }
            case 14: 
            case 15: {
                return BasicValue.DOUBLE_VALUE;
            }
            case 16: 
            case 17: {
                return BasicValue.INT_VALUE;
            }
            case 18: {
                Object object = ((LdcInsnNode)abstractInsnNode).cst;
                if (object instanceof Integer) {
                    return BasicValue.INT_VALUE;
                }
                if (object instanceof Float) {
                    return BasicValue.FLOAT_VALUE;
                }
                if (object instanceof Long) {
                    return BasicValue.LONG_VALUE;
                }
                if (object instanceof Double) {
                    return BasicValue.DOUBLE_VALUE;
                }
                if (object instanceof String) {
                    return this.newValue(Type.getObjectType("java/lang/String"));
                }
                if (object instanceof Type) {
                    int n = ((Type)object).getSort();
                    if (n == 10 || n == 9) {
                        return this.newValue(Type.getObjectType("java/lang/Class"));
                    }
                    if (n == 11) {
                        return this.newValue(Type.getObjectType("java/lang/invoke/MethodType"));
                    }
                    throw new IllegalArgumentException("Illegal LDC constant " + object);
                }
                if (object instanceof Handle) {
                    return this.newValue(Type.getObjectType("java/lang/invoke/MethodHandle"));
                }
                throw new IllegalArgumentException("Illegal LDC constant " + object);
            }
            case 168: {
                return BasicValue.RETURNADDRESS_VALUE;
            }
            case 178: {
                return this.newValue(Type.getType(((FieldInsnNode)abstractInsnNode).desc));
            }
            case 187: {
                return this.newValue(Type.getObjectType(((TypeInsnNode)abstractInsnNode).desc));
            }
        }
        throw new Error("Internal error.");
    }

    public BasicValue copyOperation(AbstractInsnNode abstractInsnNode, BasicValue basicValue) throws AnalyzerException {
        return basicValue;
    }

    public BasicValue unaryOperation(AbstractInsnNode abstractInsnNode, BasicValue basicValue) throws AnalyzerException {
        switch (abstractInsnNode.getOpcode()) {
            case 116: 
            case 132: 
            case 136: 
            case 139: 
            case 142: 
            case 145: 
            case 146: 
            case 147: {
                return BasicValue.INT_VALUE;
            }
            case 118: 
            case 134: 
            case 137: 
            case 144: {
                return BasicValue.FLOAT_VALUE;
            }
            case 117: 
            case 133: 
            case 140: 
            case 143: {
                return BasicValue.LONG_VALUE;
            }
            case 119: 
            case 135: 
            case 138: 
            case 141: {
                return BasicValue.DOUBLE_VALUE;
            }
            case 153: 
            case 154: 
            case 155: 
            case 156: 
            case 157: 
            case 158: 
            case 170: 
            case 171: 
            case 172: 
            case 173: 
            case 174: 
            case 175: 
            case 176: 
            case 179: {
                return null;
            }
            case 180: {
                return this.newValue(Type.getType(((FieldInsnNode)abstractInsnNode).desc));
            }
            case 188: {
                switch (((IntInsnNode)abstractInsnNode).operand) {
                    case 4: {
                        return this.newValue(Type.getType("[Z"));
                    }
                    case 5: {
                        return this.newValue(Type.getType("[C"));
                    }
                    case 8: {
                        return this.newValue(Type.getType("[B"));
                    }
                    case 9: {
                        return this.newValue(Type.getType("[S"));
                    }
                    case 10: {
                        return this.newValue(Type.getType("[I"));
                    }
                    case 6: {
                        return this.newValue(Type.getType("[F"));
                    }
                    case 7: {
                        return this.newValue(Type.getType("[D"));
                    }
                    case 11: {
                        return this.newValue(Type.getType("[J"));
                    }
                }
                throw new AnalyzerException(abstractInsnNode, "Invalid array type");
            }
            case 189: {
                String string = ((TypeInsnNode)abstractInsnNode).desc;
                return this.newValue(Type.getType("[" + Type.getObjectType(string)));
            }
            case 190: {
                return BasicValue.INT_VALUE;
            }
            case 191: {
                return null;
            }
            case 192: {
                String string = ((TypeInsnNode)abstractInsnNode).desc;
                return this.newValue(Type.getObjectType(string));
            }
            case 193: {
                return BasicValue.INT_VALUE;
            }
            case 194: 
            case 195: 
            case 198: 
            case 199: {
                return null;
            }
        }
        throw new Error("Internal error.");
    }

    public BasicValue binaryOperation(AbstractInsnNode abstractInsnNode, BasicValue basicValue, BasicValue basicValue2) throws AnalyzerException {
        switch (abstractInsnNode.getOpcode()) {
            case 46: 
            case 51: 
            case 52: 
            case 53: 
            case 96: 
            case 100: 
            case 104: 
            case 108: 
            case 112: 
            case 120: 
            case 122: 
            case 124: 
            case 126: 
            case 128: 
            case 130: {
                return BasicValue.INT_VALUE;
            }
            case 48: 
            case 98: 
            case 102: 
            case 106: 
            case 110: 
            case 114: {
                return BasicValue.FLOAT_VALUE;
            }
            case 47: 
            case 97: 
            case 101: 
            case 105: 
            case 109: 
            case 113: 
            case 121: 
            case 123: 
            case 125: 
            case 127: 
            case 129: 
            case 131: {
                return BasicValue.LONG_VALUE;
            }
            case 49: 
            case 99: 
            case 103: 
            case 107: 
            case 111: 
            case 115: {
                return BasicValue.DOUBLE_VALUE;
            }
            case 50: {
                return BasicValue.REFERENCE_VALUE;
            }
            case 148: 
            case 149: 
            case 150: 
            case 151: 
            case 152: {
                return BasicValue.INT_VALUE;
            }
            case 159: 
            case 160: 
            case 161: 
            case 162: 
            case 163: 
            case 164: 
            case 165: 
            case 166: 
            case 181: {
                return null;
            }
        }
        throw new Error("Internal error.");
    }

    public BasicValue ternaryOperation(AbstractInsnNode abstractInsnNode, BasicValue basicValue, BasicValue basicValue2, BasicValue basicValue3) throws AnalyzerException {
        return null;
    }

    public BasicValue naryOperation(AbstractInsnNode abstractInsnNode, List list) throws AnalyzerException {
        int n = abstractInsnNode.getOpcode();
        if (n == 197) {
            return this.newValue(Type.getType(((MultiANewArrayInsnNode)abstractInsnNode).desc));
        }
        if (n == 186) {
            return this.newValue(Type.getReturnType(((InvokeDynamicInsnNode)abstractInsnNode).desc));
        }
        return this.newValue(Type.getReturnType(((MethodInsnNode)abstractInsnNode).desc));
    }

    public void returnOperation(AbstractInsnNode abstractInsnNode, BasicValue basicValue, BasicValue basicValue2) throws AnalyzerException {
    }

    public BasicValue merge(BasicValue basicValue, BasicValue basicValue2) {
        if (!basicValue.equals(basicValue2)) {
            return BasicValue.UNINITIALIZED_VALUE;
        }
        return basicValue;
    }

    public Value merge(Value value, Value value2) {
        return this.merge((BasicValue)value, (BasicValue)value2);
    }

    public void returnOperation(AbstractInsnNode abstractInsnNode, Value value, Value value2) throws AnalyzerException {
        this.returnOperation(abstractInsnNode, (BasicValue)value, (BasicValue)value2);
    }

    public Value naryOperation(AbstractInsnNode abstractInsnNode, List list) throws AnalyzerException {
        return this.naryOperation(abstractInsnNode, list);
    }

    public Value ternaryOperation(AbstractInsnNode abstractInsnNode, Value value, Value value2, Value value3) throws AnalyzerException {
        return this.ternaryOperation(abstractInsnNode, (BasicValue)value, (BasicValue)value2, (BasicValue)value3);
    }

    public Value binaryOperation(AbstractInsnNode abstractInsnNode, Value value, Value value2) throws AnalyzerException {
        return this.binaryOperation(abstractInsnNode, (BasicValue)value, (BasicValue)value2);
    }

    public Value unaryOperation(AbstractInsnNode abstractInsnNode, Value value) throws AnalyzerException {
        return this.unaryOperation(abstractInsnNode, (BasicValue)value);
    }

    public Value copyOperation(AbstractInsnNode abstractInsnNode, Value value) throws AnalyzerException {
        return this.copyOperation(abstractInsnNode, (BasicValue)value);
    }

    public Value newOperation(AbstractInsnNode abstractInsnNode) throws AnalyzerException {
        return this.newOperation(abstractInsnNode);
    }

    public Value newValue(Type type) {
        return this.newValue(type);
    }
}

