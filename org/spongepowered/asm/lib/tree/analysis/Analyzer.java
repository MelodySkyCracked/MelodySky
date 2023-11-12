/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.IincInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LookupSwitchInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TableSwitchInsnNode;
import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.lib.tree.analysis.Interpreter;
import org.spongepowered.asm.lib.tree.analysis.Subroutine;

public class Analyzer
implements Opcodes {
    private final Interpreter interpreter;
    private int n;
    private InsnList insns;
    private List[] handlers;
    private Frame[] frames;
    private Subroutine[] subroutines;
    private boolean[] queued;
    private int[] queue;
    private int top;

    public Analyzer(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public Frame[] analyze(String string, MethodNode methodNode) throws AnalyzerException {
        int n;
        Type[] typeArray;
        Object object;
        if ((methodNode.access & 0x500) != 0) {
            this.frames = new Frame[0];
            return this.frames;
        }
        this.n = methodNode.instructions.size();
        this.insns = methodNode.instructions;
        this.handlers = new List[this.n];
        this.frames = new Frame[this.n];
        this.subroutines = new Subroutine[this.n];
        this.queued = new boolean[this.n];
        this.queue = new int[this.n];
        this.top = 0;
        for (int i = 0; i < methodNode.tryCatchBlocks.size(); ++i) {
            object = (TryCatchBlockNode)methodNode.tryCatchBlocks.get(i);
            int n2 = this.insns.indexOf(((TryCatchBlockNode)object).start);
            int n3 = this.insns.indexOf(((TryCatchBlockNode)object).end);
            for (int j = n2; j < n3; ++j) {
                typeArray = this.handlers[j];
                if (typeArray == null) {
                    this.handlers[j] = typeArray = new ArrayList();
                }
                typeArray.add(object);
            }
        }
        Subroutine subroutine = new Subroutine(null, methodNode.maxLocals, null);
        object = new ArrayList();
        HashMap<LabelNode, Subroutine> hashMap = new HashMap<LabelNode, Subroutine>();
        this.findSubroutine(0, subroutine, (List)object);
        while (!object.isEmpty()) {
            JumpInsnNode jumpInsnNode = (JumpInsnNode)object.remove(0);
            Subroutine subroutine2 = (Subroutine)hashMap.get(jumpInsnNode.label);
            if (subroutine2 == null) {
                subroutine2 = new Subroutine(jumpInsnNode.label, methodNode.maxLocals, jumpInsnNode);
                hashMap.put(jumpInsnNode.label, subroutine2);
                this.findSubroutine(this.insns.indexOf(jumpInsnNode.label), subroutine2, (List)object);
                continue;
            }
            subroutine2.callers.add(jumpInsnNode);
        }
        for (int i = 0; i < this.n; ++i) {
            if (this.subroutines[i] == null || this.subroutines[i].start != null) continue;
            this.subroutines[i] = null;
        }
        Frame frame = this.newFrame(methodNode.maxLocals, methodNode.maxStack);
        Frame frame2 = this.newFrame(methodNode.maxLocals, methodNode.maxStack);
        frame.setReturn(this.interpreter.newValue(Type.getReturnType(methodNode.desc)));
        typeArray = Type.getArgumentTypes(methodNode.desc);
        int n4 = 0;
        if ((methodNode.access & 8) == 0) {
            Type type = Type.getObjectType(string);
            frame.setLocal(n4++, this.interpreter.newValue(type));
        }
        for (n = 0; n < typeArray.length; ++n) {
            frame.setLocal(n4++, this.interpreter.newValue(typeArray[n]));
            if (typeArray[n].getSize() != 2) continue;
            frame.setLocal(n4++, this.interpreter.newValue(null));
        }
        while (n4 < methodNode.maxLocals) {
            frame.setLocal(n4++, this.interpreter.newValue(null));
        }
        this.merge(0, frame, null);
        this.init(string, methodNode);
        while (this.top > 0) {
            n = this.queue[--this.top];
            Frame frame3 = this.frames[n];
            Subroutine subroutine3 = this.subroutines[n];
            this.queued[n] = false;
            AbstractInsnNode abstractInsnNode = null;
            try {
                Object object2;
                int n5;
                Object object3;
                abstractInsnNode = methodNode.instructions.get(n);
                int n6 = abstractInsnNode.getOpcode();
                int n7 = abstractInsnNode.getType();
                if (n7 == 8 || n7 == 15 || n7 == 14) {
                    this.merge(n + 1, frame3, subroutine3);
                    this.newControlFlowEdge(n, n + 1);
                } else {
                    int n8;
                    frame.init(frame3).execute(abstractInsnNode, this.interpreter);
                    Subroutine subroutine4 = subroutine3 = subroutine3 == null ? null : subroutine3.copy();
                    if (abstractInsnNode instanceof JumpInsnNode) {
                        object3 = (JumpInsnNode)abstractInsnNode;
                        if (n6 != 167 && n6 != 168) {
                            this.merge(n + 1, frame, subroutine3);
                            this.newControlFlowEdge(n, n + 1);
                        }
                        n5 = this.insns.indexOf(((JumpInsnNode)object3).label);
                        if (n6 == 168) {
                            this.merge(n5, frame, new Subroutine(((JumpInsnNode)object3).label, methodNode.maxLocals, (JumpInsnNode)object3));
                        } else {
                            this.merge(n5, frame, subroutine3);
                        }
                        this.newControlFlowEdge(n, n5);
                    } else if (abstractInsnNode instanceof LookupSwitchInsnNode) {
                        object3 = (LookupSwitchInsnNode)abstractInsnNode;
                        n5 = this.insns.indexOf(((LookupSwitchInsnNode)object3).dflt);
                        this.merge(n5, frame, subroutine3);
                        this.newControlFlowEdge(n, n5);
                        for (n8 = 0; n8 < ((LookupSwitchInsnNode)object3).labels.size(); ++n8) {
                            object2 = (LabelNode)((LookupSwitchInsnNode)object3).labels.get(n8);
                            n5 = this.insns.indexOf((AbstractInsnNode)object2);
                            this.merge(n5, frame, subroutine3);
                            this.newControlFlowEdge(n, n5);
                        }
                    } else if (abstractInsnNode instanceof TableSwitchInsnNode) {
                        object3 = (TableSwitchInsnNode)abstractInsnNode;
                        n5 = this.insns.indexOf(((TableSwitchInsnNode)object3).dflt);
                        this.merge(n5, frame, subroutine3);
                        this.newControlFlowEdge(n, n5);
                        for (n8 = 0; n8 < ((TableSwitchInsnNode)object3).labels.size(); ++n8) {
                            object2 = (LabelNode)((TableSwitchInsnNode)object3).labels.get(n8);
                            n5 = this.insns.indexOf((AbstractInsnNode)object2);
                            this.merge(n5, frame, subroutine3);
                            this.newControlFlowEdge(n, n5);
                        }
                    } else if (n6 == 169) {
                        if (subroutine3 == null) {
                            throw new AnalyzerException(abstractInsnNode, "RET instruction outside of a sub routine");
                        }
                        for (int i = 0; i < subroutine3.callers.size(); ++i) {
                            JumpInsnNode jumpInsnNode = (JumpInsnNode)subroutine3.callers.get(i);
                            n8 = this.insns.indexOf(jumpInsnNode);
                            if (this.frames[n8] == null) continue;
                            this.merge(n8 + 1, this.frames[n8], frame, this.subroutines[n8], subroutine3.access);
                            this.newControlFlowEdge(n, n8 + 1);
                        }
                    } else if (n6 != 191 && (n6 < 172 || n6 > 177)) {
                        if (subroutine3 != null) {
                            if (abstractInsnNode instanceof VarInsnNode) {
                                int n9 = ((VarInsnNode)abstractInsnNode).var;
                                subroutine3.access[n9] = true;
                                if (n6 == 22 || n6 == 24 || n6 == 55 || n6 == 57) {
                                    subroutine3.access[n9 + 1] = true;
                                }
                            } else if (abstractInsnNode instanceof IincInsnNode) {
                                int n10 = ((IincInsnNode)abstractInsnNode).var;
                                subroutine3.access[n10] = true;
                            }
                        }
                        this.merge(n + 1, frame, subroutine3);
                        this.newControlFlowEdge(n, n + 1);
                    }
                }
                if ((object3 = this.handlers[n]) == null) continue;
                for (n5 = 0; n5 < object3.size(); ++n5) {
                    TryCatchBlockNode tryCatchBlockNode = (TryCatchBlockNode)object3.get(n5);
                    object2 = tryCatchBlockNode.type == null ? Type.getObjectType("java/lang/Throwable") : Type.getObjectType(tryCatchBlockNode.type);
                    int n11 = this.insns.indexOf(tryCatchBlockNode.handler);
                    if (!this.newControlFlowExceptionEdge(n, tryCatchBlockNode)) continue;
                    frame2.init(frame3);
                    frame2.clearStack();
                    frame2.push(this.interpreter.newValue((Type)object2));
                    this.merge(n11, frame2, subroutine3);
                }
            }
            catch (AnalyzerException analyzerException) {
                throw new AnalyzerException(analyzerException.node, "Error at instruction " + n + ": " + analyzerException.getMessage(), analyzerException);
            }
            catch (Exception exception) {
                throw new AnalyzerException(abstractInsnNode, "Error at instruction " + n + ": " + exception.getMessage(), exception);
            }
        }
        return this.frames;
    }

    private void findSubroutine(int n, Subroutine subroutine, List list) throws AnalyzerException {
        while (true) {
            Object object;
            int n2;
            Object object2;
            if (n < 0 || n >= this.n) {
                throw new AnalyzerException(null, "Execution can fall off end of the code");
            }
            if (this.subroutines[n] != null) {
                return;
            }
            this.subroutines[n] = subroutine.copy();
            AbstractInsnNode abstractInsnNode = this.insns.get(n);
            if (abstractInsnNode instanceof JumpInsnNode) {
                if (abstractInsnNode.getOpcode() == 168) {
                    list.add(abstractInsnNode);
                } else {
                    object2 = (JumpInsnNode)abstractInsnNode;
                    this.findSubroutine(this.insns.indexOf(((JumpInsnNode)object2).label), subroutine, list);
                }
            } else if (abstractInsnNode instanceof TableSwitchInsnNode) {
                object2 = (TableSwitchInsnNode)abstractInsnNode;
                this.findSubroutine(this.insns.indexOf(((TableSwitchInsnNode)object2).dflt), subroutine, list);
                for (n2 = ((TableSwitchInsnNode)object2).labels.size() - 1; n2 >= 0; --n2) {
                    object = (LabelNode)((TableSwitchInsnNode)object2).labels.get(n2);
                    this.findSubroutine(this.insns.indexOf((AbstractInsnNode)object), subroutine, list);
                }
            } else if (abstractInsnNode instanceof LookupSwitchInsnNode) {
                object2 = (LookupSwitchInsnNode)abstractInsnNode;
                this.findSubroutine(this.insns.indexOf(((LookupSwitchInsnNode)object2).dflt), subroutine, list);
                for (n2 = ((LookupSwitchInsnNode)object2).labels.size() - 1; n2 >= 0; --n2) {
                    object = (LabelNode)((LookupSwitchInsnNode)object2).labels.get(n2);
                    this.findSubroutine(this.insns.indexOf((AbstractInsnNode)object), subroutine, list);
                }
            }
            object2 = this.handlers[n];
            if (object2 != null) {
                for (n2 = 0; n2 < object2.size(); ++n2) {
                    object = (TryCatchBlockNode)object2.get(n2);
                    this.findSubroutine(this.insns.indexOf(((TryCatchBlockNode)object).handler), subroutine, list);
                }
            }
            switch (abstractInsnNode.getOpcode()) {
                case 167: 
                case 169: 
                case 170: 
                case 171: 
                case 172: 
                case 173: 
                case 174: 
                case 175: 
                case 176: 
                case 177: 
                case 191: {
                    return;
                }
            }
            ++n;
        }
    }

    public Frame[] getFrames() {
        return this.frames;
    }

    public List getHandlers(int n) {
        return this.handlers[n];
    }

    protected void init(String string, MethodNode methodNode) throws AnalyzerException {
    }

    protected Frame newFrame(int n, int n2) {
        return new Frame(n, n2);
    }

    protected Frame newFrame(Frame frame) {
        return new Frame(frame);
    }

    protected void newControlFlowEdge(int n, int n2) {
    }

    protected boolean newControlFlowExceptionEdge(int n, int n2) {
        return true;
    }

    protected boolean newControlFlowExceptionEdge(int n, TryCatchBlockNode tryCatchBlockNode) {
        return this.newControlFlowExceptionEdge(n, this.insns.indexOf(tryCatchBlockNode.handler));
    }

    private void merge(int n, Frame frame, Subroutine subroutine) throws AnalyzerException {
        boolean bl;
        Frame frame2 = this.frames[n];
        Subroutine subroutine2 = this.subroutines[n];
        if (frame2 == null) {
            this.frames[n] = this.newFrame(frame);
            bl = true;
        } else {
            bl = frame2.merge(frame, this.interpreter);
        }
        if (subroutine2 == null) {
            if (subroutine != null) {
                this.subroutines[n] = subroutine.copy();
                bl = true;
            }
        } else if (subroutine != null) {
            bl |= subroutine2.merge(subroutine);
        }
        if (bl && !this.queued[n]) {
            this.queued[n] = true;
            this.queue[this.top++] = n;
        }
    }

    private void merge(int n, Frame frame, Frame frame2, Subroutine subroutine, boolean[] blArray) throws AnalyzerException {
        boolean bl;
        Frame frame3 = this.frames[n];
        Subroutine subroutine2 = this.subroutines[n];
        frame2.merge(frame, blArray);
        if (frame3 == null) {
            this.frames[n] = this.newFrame(frame2);
            bl = true;
        } else {
            bl = frame3.merge(frame2, this.interpreter);
        }
        if (subroutine2 != null && subroutine != null) {
            bl |= subroutine2.merge(subroutine);
        }
        if (bl && !this.queued[n]) {
            this.queued[n] = true;
            this.queue[this.top++] = n;
        }
    }
}

