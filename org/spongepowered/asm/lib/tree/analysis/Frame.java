/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree.analysis;

import java.util.ArrayList;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.IincInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MultiANewArrayInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
import org.spongepowered.asm.lib.tree.analysis.Interpreter;
import org.spongepowered.asm.lib.tree.analysis.Value;

public class Frame {
    private Value returnValue;
    private Value[] values;
    private int locals;
    private int top;

    public Frame(int n, int n2) {
        this.values = new Value[n + n2];
        this.locals = n;
    }

    public Frame(Frame frame) {
        this(frame.locals, frame.values.length - frame.locals);
        this.init(frame);
    }

    public Frame init(Frame frame) {
        this.returnValue = frame.returnValue;
        System.arraycopy(frame.values, 0, this.values, 0, this.values.length);
        this.top = frame.top;
        return this;
    }

    public void setReturn(Value value) {
        this.returnValue = value;
    }

    public int getLocals() {
        return this.locals;
    }

    public int getMaxStackSize() {
        return this.values.length - this.locals;
    }

    public Value getLocal(int n) throws IndexOutOfBoundsException {
        if (n >= this.locals) {
            throw new IndexOutOfBoundsException("Trying to access an inexistant local variable");
        }
        return this.values[n];
    }

    public void setLocal(int n, Value value) throws IndexOutOfBoundsException {
        if (n >= this.locals) {
            throw new IndexOutOfBoundsException("Trying to access an inexistant local variable " + n);
        }
        this.values[n] = value;
    }

    public int getStackSize() {
        return this.top;
    }

    public Value getStack(int n) throws IndexOutOfBoundsException {
        return this.values[n + this.locals];
    }

    public void clearStack() {
        this.top = 0;
    }

    public Value pop() throws IndexOutOfBoundsException {
        if (this.top == 0) {
            throw new IndexOutOfBoundsException("Cannot pop operand off an empty stack.");
        }
        return this.values[--this.top + this.locals];
    }

    public void push(Value value) throws IndexOutOfBoundsException {
        if (this.top + this.locals >= this.values.length) {
            throw new IndexOutOfBoundsException("Insufficient maximum stack size.");
        }
        this.values[this.top++ + this.locals] = value;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void execute(AbstractInsnNode abstractInsnNode, Interpreter interpreter) throws AnalyzerException {
        switch (abstractInsnNode.getOpcode()) {
            case 0: {
                return;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: {
                this.push(interpreter.newOperation(abstractInsnNode));
                return;
            }
            case 21: 
            case 22: 
            case 23: 
            case 24: 
            case 25: {
                this.push(interpreter.copyOperation(abstractInsnNode, this.getLocal(((VarInsnNode)abstractInsnNode).var)));
                return;
            }
            case 46: 
            case 47: 
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: {
                Value value = this.pop();
                Value value2 = this.pop();
                this.push(interpreter.binaryOperation(abstractInsnNode, value2, value));
                return;
            }
            case 54: 
            case 55: 
            case 56: 
            case 57: 
            case 58: {
                Value value;
                Value value3 = interpreter.copyOperation(abstractInsnNode, this.pop());
                int n = ((VarInsnNode)abstractInsnNode).var;
                this.setLocal(n, value3);
                if (value3.getSize() == 2) {
                    this.setLocal(n + 1, interpreter.newValue(null));
                }
                if (n <= 0 || (value = this.getLocal(n - 1)) == null || value.getSize() != 2) return;
                this.setLocal(n - 1, interpreter.newValue(null));
                return;
            }
            case 79: 
            case 80: 
            case 81: 
            case 82: 
            case 83: 
            case 84: 
            case 85: 
            case 86: {
                Value value = this.pop();
                Value value4 = this.pop();
                Value value5 = this.pop();
                interpreter.ternaryOperation(abstractInsnNode, value5, value4, value);
                return;
            }
            case 87: {
                if (this.pop().getSize() != 2) return;
                throw new AnalyzerException(abstractInsnNode, "Illegal use of POP");
            }
            case 88: {
                if (this.pop().getSize() != 1 || this.pop().getSize() == 1) return;
                throw new AnalyzerException(abstractInsnNode, "Illegal use of POP2");
            }
            case 89: {
                Value value = this.pop();
                if (value.getSize() != 1) {
                    throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP");
                }
                this.push(value);
                this.push(interpreter.copyOperation(abstractInsnNode, value));
                return;
            }
            case 90: {
                Value value = this.pop();
                Value value6 = this.pop();
                if (value.getSize() != 1 || value6.getSize() != 1) {
                    throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP_X1");
                }
                this.push(interpreter.copyOperation(abstractInsnNode, value));
                this.push(value6);
                this.push(value);
                return;
            }
            case 91: {
                Value value = this.pop();
                if (value.getSize() != 1) throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP_X2");
                Value value7 = this.pop();
                if (value7.getSize() == 1) {
                    Value value8 = this.pop();
                    if (value8.getSize() != 1) throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP_X2");
                    this.push(interpreter.copyOperation(abstractInsnNode, value));
                    this.push(value8);
                    this.push(value7);
                    this.push(value);
                    return;
                }
                this.push(interpreter.copyOperation(abstractInsnNode, value));
                this.push(value7);
                this.push(value);
                return;
            }
            case 92: {
                Value value = this.pop();
                if (value.getSize() == 1) {
                    Value value9 = this.pop();
                    if (value9.getSize() != 1) throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP2");
                    this.push(value9);
                    this.push(value);
                    this.push(interpreter.copyOperation(abstractInsnNode, value9));
                    this.push(interpreter.copyOperation(abstractInsnNode, value));
                    return;
                }
                this.push(value);
                this.push(interpreter.copyOperation(abstractInsnNode, value));
                return;
            }
            case 93: {
                Value value = this.pop();
                if (value.getSize() == 1) {
                    Value value10;
                    Value value11 = this.pop();
                    if (value11.getSize() != 1 || (value10 = this.pop()).getSize() != 1) throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP2_X1");
                    this.push(interpreter.copyOperation(abstractInsnNode, value11));
                    this.push(interpreter.copyOperation(abstractInsnNode, value));
                    this.push(value10);
                    this.push(value11);
                    this.push(value);
                    return;
                }
                Value value12 = this.pop();
                if (value12.getSize() != 1) throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP2_X1");
                this.push(interpreter.copyOperation(abstractInsnNode, value));
                this.push(value12);
                this.push(value);
                return;
            }
            case 94: {
                Value value = this.pop();
                if (value.getSize() == 1) {
                    Value value13 = this.pop();
                    if (value13.getSize() != 1) throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP2_X2");
                    Value value14 = this.pop();
                    if (value14.getSize() == 1) {
                        Value value15 = this.pop();
                        if (value15.getSize() != 1) throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP2_X2");
                        this.push(interpreter.copyOperation(abstractInsnNode, value13));
                        this.push(interpreter.copyOperation(abstractInsnNode, value));
                        this.push(value15);
                        this.push(value14);
                        this.push(value13);
                        this.push(value);
                        return;
                    }
                    this.push(interpreter.copyOperation(abstractInsnNode, value13));
                    this.push(interpreter.copyOperation(abstractInsnNode, value));
                    this.push(value14);
                    this.push(value13);
                    this.push(value);
                    return;
                }
                Value value16 = this.pop();
                if (value16.getSize() == 1) {
                    Value value17 = this.pop();
                    if (value17.getSize() != 1) throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP2_X2");
                    this.push(interpreter.copyOperation(abstractInsnNode, value));
                    this.push(value17);
                    this.push(value16);
                    this.push(value);
                    return;
                }
                this.push(interpreter.copyOperation(abstractInsnNode, value));
                this.push(value16);
                this.push(value);
                return;
            }
            case 95: {
                Value value = this.pop();
                Value value18 = this.pop();
                if (value18.getSize() != 1 || value.getSize() != 1) {
                    throw new AnalyzerException(abstractInsnNode, "Illegal use of SWAP");
                }
                this.push(interpreter.copyOperation(abstractInsnNode, value));
                this.push(interpreter.copyOperation(abstractInsnNode, value18));
                return;
            }
            case 96: 
            case 97: 
            case 98: 
            case 99: 
            case 100: 
            case 101: 
            case 102: 
            case 103: 
            case 104: 
            case 105: 
            case 106: 
            case 107: 
            case 108: 
            case 109: 
            case 110: 
            case 111: 
            case 112: 
            case 113: 
            case 114: 
            case 115: {
                Value value = this.pop();
                Value value19 = this.pop();
                this.push(interpreter.binaryOperation(abstractInsnNode, value19, value));
                return;
            }
            case 116: 
            case 117: 
            case 118: 
            case 119: {
                this.push(interpreter.unaryOperation(abstractInsnNode, this.pop()));
                return;
            }
            case 120: 
            case 121: 
            case 122: 
            case 123: 
            case 124: 
            case 125: 
            case 126: 
            case 127: 
            case 128: 
            case 129: 
            case 130: 
            case 131: {
                Value value = this.pop();
                Value value20 = this.pop();
                this.push(interpreter.binaryOperation(abstractInsnNode, value20, value));
                return;
            }
            case 132: {
                int n = ((IincInsnNode)abstractInsnNode).var;
                this.setLocal(n, interpreter.unaryOperation(abstractInsnNode, this.getLocal(n)));
                return;
            }
            case 133: 
            case 134: 
            case 135: 
            case 136: 
            case 137: 
            case 138: 
            case 139: 
            case 140: 
            case 141: 
            case 142: 
            case 143: 
            case 144: 
            case 145: 
            case 146: 
            case 147: {
                this.push(interpreter.unaryOperation(abstractInsnNode, this.pop()));
                return;
            }
            case 148: 
            case 149: 
            case 150: 
            case 151: 
            case 152: {
                Value value = this.pop();
                Value value21 = this.pop();
                this.push(interpreter.binaryOperation(abstractInsnNode, value21, value));
                return;
            }
            case 153: 
            case 154: 
            case 155: 
            case 156: 
            case 157: 
            case 158: {
                interpreter.unaryOperation(abstractInsnNode, this.pop());
                return;
            }
            case 159: 
            case 160: 
            case 161: 
            case 162: 
            case 163: 
            case 164: 
            case 165: 
            case 166: {
                Value value = this.pop();
                Value value22 = this.pop();
                interpreter.binaryOperation(abstractInsnNode, value22, value);
                return;
            }
            case 167: {
                return;
            }
            case 168: {
                this.push(interpreter.newOperation(abstractInsnNode));
                return;
            }
            case 169: {
                return;
            }
            case 170: 
            case 171: {
                interpreter.unaryOperation(abstractInsnNode, this.pop());
                return;
            }
            case 172: 
            case 173: 
            case 174: 
            case 175: 
            case 176: {
                Value value = this.pop();
                interpreter.unaryOperation(abstractInsnNode, value);
                interpreter.returnOperation(abstractInsnNode, value, this.returnValue);
                return;
            }
            case 177: {
                if (this.returnValue == null) return;
                throw new AnalyzerException(abstractInsnNode, "Incompatible return type");
            }
            case 178: {
                this.push(interpreter.newOperation(abstractInsnNode));
                return;
            }
            case 179: {
                interpreter.unaryOperation(abstractInsnNode, this.pop());
                return;
            }
            case 180: {
                this.push(interpreter.unaryOperation(abstractInsnNode, this.pop()));
                return;
            }
            case 181: {
                Value value = this.pop();
                Value value23 = this.pop();
                interpreter.binaryOperation(abstractInsnNode, value23, value);
                return;
            }
            case 182: 
            case 183: 
            case 184: 
            case 185: {
                ArrayList<Value> arrayList = new ArrayList<Value>();
                String string = ((MethodInsnNode)abstractInsnNode).desc;
                for (int i = Type.getArgumentTypes(string).length; i > 0; --i) {
                    arrayList.add(0, this.pop());
                }
                if (abstractInsnNode.getOpcode() != 184) {
                    arrayList.add(0, this.pop());
                }
                if (Type.getReturnType(string) == Type.VOID_TYPE) {
                    interpreter.naryOperation(abstractInsnNode, arrayList);
                    return;
                }
                this.push(interpreter.naryOperation(abstractInsnNode, arrayList));
                return;
            }
            case 186: {
                ArrayList<Value> arrayList = new ArrayList<Value>();
                String string = ((InvokeDynamicInsnNode)abstractInsnNode).desc;
                for (int i = Type.getArgumentTypes(string).length; i > 0; --i) {
                    arrayList.add(0, this.pop());
                }
                if (Type.getReturnType(string) == Type.VOID_TYPE) {
                    interpreter.naryOperation(abstractInsnNode, arrayList);
                    return;
                }
                this.push(interpreter.naryOperation(abstractInsnNode, arrayList));
                return;
            }
            case 187: {
                this.push(interpreter.newOperation(abstractInsnNode));
                return;
            }
            case 188: 
            case 189: 
            case 190: {
                this.push(interpreter.unaryOperation(abstractInsnNode, this.pop()));
                return;
            }
            case 191: {
                interpreter.unaryOperation(abstractInsnNode, this.pop());
                return;
            }
            case 192: 
            case 193: {
                this.push(interpreter.unaryOperation(abstractInsnNode, this.pop()));
                return;
            }
            case 194: 
            case 195: {
                interpreter.unaryOperation(abstractInsnNode, this.pop());
                return;
            }
            case 197: {
                ArrayList<Value> arrayList = new ArrayList<Value>();
                for (int i = ((MultiANewArrayInsnNode)abstractInsnNode).dims; i > 0; --i) {
                    arrayList.add(0, this.pop());
                }
                this.push(interpreter.naryOperation(abstractInsnNode, arrayList));
                return;
            }
            case 198: 
            case 199: {
                interpreter.unaryOperation(abstractInsnNode, this.pop());
                return;
            }
            default: {
                throw new RuntimeException("Illegal opcode " + abstractInsnNode.getOpcode());
            }
        }
    }

    public boolean merge(Frame frame, Interpreter interpreter) throws AnalyzerException {
        if (this.top != frame.top) {
            throw new AnalyzerException(null, "Incompatible stack heights");
        }
        boolean bl = false;
        for (int i = 0; i < this.locals + this.top; ++i) {
            Value value = interpreter.merge(this.values[i], frame.values[i]);
            if (value.equals(this.values[i])) continue;
            this.values[i] = value;
            bl = true;
        }
        return bl;
    }

    public boolean merge(Frame frame, boolean[] blArray) {
        boolean bl = false;
        for (int i = 0; i < this.locals; ++i) {
            if (blArray[i] || this.values[i].equals(frame.values[i])) continue;
            this.values[i] = frame.values[i];
            bl = true;
        }
        return bl;
    }

    public String toString() {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        for (n = 0; n < this.getLocals(); ++n) {
            stringBuilder.append(this.getLocal(n));
        }
        stringBuilder.append(' ');
        for (n = 0; n < this.getStackSize(); ++n) {
            stringBuilder.append(this.getStack(n).toString());
        }
        return stringBuilder.toString();
    }
}

