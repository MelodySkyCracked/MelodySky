/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.LWJGLException
 *  org.lwjgl.input.Controller
 *  org.lwjgl.input.Controllers
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package org.newdawn.slick;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

public class Input {
    public static final int ANY_CONTROLLER = -1;
    private static final int MAX_BUTTONS = 100;
    public static final int KEY_ESCAPE = 1;
    public static final int KEY_1 = 2;
    public static final int KEY_2 = 3;
    public static final int KEY_3 = 4;
    public static final int KEY_4 = 5;
    public static final int KEY_5 = 6;
    public static final int KEY_6 = 7;
    public static final int KEY_7 = 8;
    public static final int KEY_8 = 9;
    public static final int KEY_9 = 10;
    public static final int KEY_0 = 11;
    public static final int KEY_MINUS = 12;
    public static final int KEY_EQUALS = 13;
    public static final int KEY_BACK = 14;
    public static final int KEY_TAB = 15;
    public static final int KEY_Q = 16;
    public static final int KEY_W = 17;
    public static final int KEY_E = 18;
    public static final int KEY_R = 19;
    public static final int KEY_T = 20;
    public static final int KEY_Y = 21;
    public static final int KEY_U = 22;
    public static final int KEY_I = 23;
    public static final int KEY_O = 24;
    public static final int KEY_P = 25;
    public static final int KEY_LBRACKET = 26;
    public static final int KEY_RBRACKET = 27;
    public static final int KEY_RETURN = 28;
    public static final int KEY_ENTER = 28;
    public static final int KEY_LCONTROL = 29;
    public static final int KEY_A = 30;
    public static final int KEY_S = 31;
    public static final int KEY_D = 32;
    public static final int KEY_F = 33;
    public static final int KEY_G = 34;
    public static final int KEY_H = 35;
    public static final int KEY_J = 36;
    public static final int KEY_K = 37;
    public static final int KEY_L = 38;
    public static final int KEY_SEMICOLON = 39;
    public static final int KEY_APOSTROPHE = 40;
    public static final int KEY_GRAVE = 41;
    public static final int KEY_LSHIFT = 42;
    public static final int KEY_BACKSLASH = 43;
    public static final int KEY_Z = 44;
    public static final int KEY_X = 45;
    public static final int KEY_C = 46;
    public static final int KEY_V = 47;
    public static final int KEY_B = 48;
    public static final int KEY_N = 49;
    public static final int KEY_M = 50;
    public static final int KEY_COMMA = 51;
    public static final int KEY_PERIOD = 52;
    public static final int KEY_SLASH = 53;
    public static final int KEY_RSHIFT = 54;
    public static final int KEY_MULTIPLY = 55;
    public static final int KEY_LMENU = 56;
    public static final int KEY_SPACE = 57;
    public static final int KEY_CAPITAL = 58;
    public static final int KEY_F1 = 59;
    public static final int KEY_F2 = 60;
    public static final int KEY_F3 = 61;
    public static final int KEY_F4 = 62;
    public static final int KEY_F5 = 63;
    public static final int KEY_F6 = 64;
    public static final int KEY_F7 = 65;
    public static final int KEY_F8 = 66;
    public static final int KEY_F9 = 67;
    public static final int KEY_F10 = 68;
    public static final int KEY_NUMLOCK = 69;
    public static final int KEY_SCROLL = 70;
    public static final int KEY_NUMPAD7 = 71;
    public static final int KEY_NUMPAD8 = 72;
    public static final int KEY_NUMPAD9 = 73;
    public static final int KEY_SUBTRACT = 74;
    public static final int KEY_NUMPAD4 = 75;
    public static final int KEY_NUMPAD5 = 76;
    public static final int KEY_NUMPAD6 = 77;
    public static final int KEY_ADD = 78;
    public static final int KEY_NUMPAD1 = 79;
    public static final int KEY_NUMPAD2 = 80;
    public static final int KEY_NUMPAD3 = 81;
    public static final int KEY_NUMPAD0 = 82;
    public static final int KEY_DECIMAL = 83;
    public static final int KEY_F11 = 87;
    public static final int KEY_F12 = 88;
    public static final int KEY_F13 = 100;
    public static final int KEY_F14 = 101;
    public static final int KEY_F15 = 102;
    public static final int KEY_KANA = 112;
    public static final int KEY_CONVERT = 121;
    public static final int KEY_NOCONVERT = 123;
    public static final int KEY_YEN = 125;
    public static final int KEY_NUMPADEQUALS = 141;
    public static final int KEY_CIRCUMFLEX = 144;
    public static final int KEY_AT = 145;
    public static final int KEY_COLON = 146;
    public static final int KEY_UNDERLINE = 147;
    public static final int KEY_KANJI = 148;
    public static final int KEY_STOP = 149;
    public static final int KEY_AX = 150;
    public static final int KEY_UNLABELED = 151;
    public static final int KEY_NUMPADENTER = 156;
    public static final int KEY_RCONTROL = 157;
    public static final int KEY_NUMPADCOMMA = 179;
    public static final int KEY_DIVIDE = 181;
    public static final int KEY_SYSRQ = 183;
    public static final int KEY_RMENU = 184;
    public static final int KEY_PAUSE = 197;
    public static final int KEY_HOME = 199;
    public static final int KEY_UP = 200;
    public static final int KEY_PRIOR = 201;
    public static final int KEY_LEFT = 203;
    public static final int KEY_RIGHT = 205;
    public static final int KEY_END = 207;
    public static final int KEY_DOWN = 208;
    public static final int KEY_NEXT = 209;
    public static final int KEY_INSERT = 210;
    public static final int KEY_DELETE = 211;
    public static final int KEY_LWIN = 219;
    public static final int KEY_RWIN = 220;
    public static final int KEY_APPS = 221;
    public static final int KEY_POWER = 222;
    public static final int KEY_SLEEP = 223;
    public static final int KEY_LALT = 56;
    public static final int KEY_RALT = 184;
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;
    private static final int BUTTON1 = 4;
    private static final int BUTTON2 = 5;
    private static final int BUTTON3 = 6;
    private static final int BUTTON4 = 7;
    private static final int BUTTON5 = 8;
    private static final int BUTTON6 = 9;
    private static final int BUTTON7 = 10;
    private static final int BUTTON8 = 11;
    private static final int BUTTON9 = 12;
    private static final int BUTTON10 = 13;
    public static final int MOUSE_LEFT_BUTTON = 0;
    public static final int MOUSE_RIGHT_BUTTON = 1;
    public static final int MOUSE_MIDDLE_BUTTON = 2;
    private static boolean controllersInited = false;
    private static ArrayList controllers = new ArrayList();
    private int lastMouseX;
    private int lastMouseY;
    protected boolean[] mousePressed = new boolean[10];
    private boolean[][] controllerPressed = new boolean[100][100];
    protected char[] keys = new char[1024];
    protected boolean[] pressed = new boolean[1024];
    protected long[] nextRepeat = new long[1024];
    private boolean[][] controls = new boolean[10][110];
    protected boolean consumed = false;
    protected HashSet allListeners = new HashSet();
    protected ArrayList keyListeners = new ArrayList();
    protected ArrayList keyListenersToAdd = new ArrayList();
    protected ArrayList mouseListeners = new ArrayList();
    protected ArrayList mouseListenersToAdd = new ArrayList();
    protected ArrayList controllerListeners = new ArrayList();
    private int wheel;
    private int height;
    private boolean displayActive = true;
    private boolean keyRepeat;
    private int keyRepeatInitial;
    private int keyRepeatInterval;
    private boolean paused;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private float xoffset = 0.0f;
    private float yoffset = 0.0f;
    private int doubleClickDelay = 250;
    private long doubleClickTimeout = 0L;
    private int clickX;
    private int clickY;
    private int clickButton;
    private int pressedX = -1;
    private int pressedY = -1;
    private int mouseClickTolerance = 5;

    public static void disableControllers() {
        controllersInited = true;
    }

    public Input(int n) {
        this.init(n);
    }

    public void setDoubleClickInterval(int n) {
        this.doubleClickDelay = n;
    }

    public void setMouseClickTolerance(int n) {
        this.mouseClickTolerance = n;
    }

    public void setScale(float f, float f2) {
        this.scaleX = f;
        this.scaleY = f2;
    }

    public void setOffset(float f, float f2) {
        this.xoffset = f;
        this.yoffset = f2;
    }

    public void resetInputTransform() {
        this.setOffset(0.0f, 0.0f);
        this.setScale(1.0f, 1.0f);
    }

    public void addListener(InputListener inputListener) {
        this.addKeyListener(inputListener);
        this.addMouseListener(inputListener);
        this.addControllerListener(inputListener);
    }

    public void addKeyListener(KeyListener keyListener) {
        this.keyListenersToAdd.add(keyListener);
    }

    private void addKeyListenerImpl(KeyListener keyListener) {
        if (this.keyListeners.contains(keyListener)) {
            return;
        }
        this.keyListeners.add(keyListener);
        this.allListeners.add(keyListener);
    }

    public void addMouseListener(MouseListener mouseListener) {
        this.mouseListenersToAdd.add(mouseListener);
    }

    private void addMouseListenerImpl(MouseListener mouseListener) {
        if (this.mouseListeners.contains(mouseListener)) {
            return;
        }
        this.mouseListeners.add(mouseListener);
        this.allListeners.add(mouseListener);
    }

    public void addControllerListener(ControllerListener controllerListener) {
        if (this.controllerListeners.contains(controllerListener)) {
            return;
        }
        this.controllerListeners.add(controllerListener);
        this.allListeners.add(controllerListener);
    }

    public void removeAllListeners() {
        this.removeAllKeyListeners();
        this.removeAllMouseListeners();
        this.removeAllControllerListeners();
    }

    public void removeAllKeyListeners() {
        this.allListeners.removeAll(this.keyListeners);
        this.keyListeners.clear();
    }

    public void removeAllMouseListeners() {
        this.allListeners.removeAll(this.mouseListeners);
        this.mouseListeners.clear();
    }

    public void removeAllControllerListeners() {
        this.allListeners.removeAll(this.controllerListeners);
        this.controllerListeners.clear();
    }

    public void addPrimaryListener(InputListener inputListener) {
        this.removeListener(inputListener);
        this.keyListeners.add(0, inputListener);
        this.mouseListeners.add(0, inputListener);
        this.controllerListeners.add(0, inputListener);
        this.allListeners.add(inputListener);
    }

    public void removeListener(InputListener inputListener) {
        this.removeKeyListener(inputListener);
        this.removeMouseListener(inputListener);
        this.removeControllerListener(inputListener);
    }

    public void removeKeyListener(KeyListener keyListener) {
        this.keyListeners.remove(keyListener);
        if (!this.mouseListeners.contains(keyListener) && !this.controllerListeners.contains(keyListener)) {
            this.allListeners.remove(keyListener);
        }
    }

    public void removeControllerListener(ControllerListener controllerListener) {
        this.controllerListeners.remove(controllerListener);
        if (!this.mouseListeners.contains(controllerListener) && !this.keyListeners.contains(controllerListener)) {
            this.allListeners.remove(controllerListener);
        }
    }

    public void removeMouseListener(MouseListener mouseListener) {
        this.mouseListeners.remove(mouseListener);
        if (!this.controllerListeners.contains(mouseListener) && !this.keyListeners.contains(mouseListener)) {
            this.allListeners.remove(mouseListener);
        }
    }

    void init(int n) {
        this.height = n;
        this.lastMouseX = this.getMouseX();
        this.lastMouseY = this.getMouseY();
    }

    public static String getKeyName(int n) {
        return Keyboard.getKeyName((int)n);
    }

    public boolean isKeyPressed(int n) {
        if (this.pressed[n]) {
            this.pressed[n] = false;
            return true;
        }
        return false;
    }

    public boolean isMousePressed(int n) {
        if (this.mousePressed[n]) {
            this.mousePressed[n] = false;
            return true;
        }
        return false;
    }

    public boolean isControlPressed(int n) {
        return this.isControlPressed(n, 0);
    }

    public boolean isControlPressed(int n, int n2) {
        if (this.controllerPressed[n2][n]) {
            this.controllerPressed[n2][n] = false;
            return true;
        }
        return false;
    }

    public void clearControlPressedRecord() {
        for (int i = 0; i < controllers.size(); ++i) {
            Arrays.fill(this.controllerPressed[i], false);
        }
    }

    public void clearKeyPressedRecord() {
        Arrays.fill(this.pressed, false);
    }

    public void clearMousePressedRecord() {
        Arrays.fill(this.mousePressed, false);
    }

    public boolean isKeyDown(int n) {
        return Keyboard.isKeyDown((int)n);
    }

    public int getAbsoluteMouseX() {
        return Mouse.getX();
    }

    public int getAbsoluteMouseY() {
        return this.height - Mouse.getY();
    }

    public int getMouseX() {
        return (int)((float)Mouse.getX() * this.scaleX + this.xoffset);
    }

    public int getMouseY() {
        return (int)((float)(this.height - Mouse.getY()) * this.scaleY + this.yoffset);
    }

    public boolean isMouseButtonDown(int n) {
        return Mouse.isButtonDown((int)n);
    }

    public int getControllerCount() {
        try {
            this.initControllers();
        }
        catch (SlickException slickException) {
            throw new RuntimeException("Failed to initialise controllers");
        }
        return controllers.size();
    }

    public int getAxisCount(int n) {
        return ((Controller)controllers.get(n)).getAxisCount();
    }

    public float getAxisValue(int n, int n2) {
        return ((Controller)controllers.get(n)).getAxisValue(n2);
    }

    public String getAxisName(int n, int n2) {
        return ((Controller)controllers.get(n)).getAxisName(n2);
    }

    public boolean isButton1Pressed(int n) {
        return this.isButtonPressed(0, n);
    }

    public boolean isButton2Pressed(int n) {
        return this.isButtonPressed(1, n);
    }

    public boolean isButton3Pressed(int n) {
        return this.isButtonPressed(2, n);
    }

    public void initControllers() throws SlickException {
        if (controllersInited) {
            return;
        }
        controllersInited = true;
        try {
            int n;
            Controllers.create();
            int n2 = Controllers.getControllerCount();
            for (n = 0; n < n2; ++n) {
                Controller controller = Controllers.getController((int)n);
                if (controller.getButtonCount() < 3 || controller.getButtonCount() >= 100) continue;
                controllers.add(controller);
            }
            Log.info("Found " + controllers.size() + " controllers");
            for (n = 0; n < controllers.size(); ++n) {
                Log.info(n + " : " + ((Controller)controllers.get(n)).getName());
            }
        }
        catch (LWJGLException lWJGLException) {
            if (lWJGLException.getCause() instanceof ClassNotFoundException) {
                throw new SlickException("Unable to create controller - no jinput found - add jinput.jar to your classpath");
            }
            throw new SlickException("Unable to create controllers");
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            // empty catch block
        }
    }

    public void consumeEvent() {
        this.consumed = true;
    }

    private int resolveEventKey(int n, char c) {
        if (c == '=' || n == 0) {
            return 13;
        }
        return n;
    }

    public void considerDoubleClick(int n, int n2, int n3) {
        if (this.doubleClickTimeout == 0L) {
            this.clickX = n2;
            this.clickY = n3;
            this.clickButton = n;
            this.doubleClickTimeout = System.currentTimeMillis() + (long)this.doubleClickDelay;
            this.fireMouseClicked(n, n2, n3, 1);
        } else if (this.clickButton == n && System.currentTimeMillis() < this.doubleClickTimeout) {
            this.fireMouseClicked(n, n2, n3, 2);
            this.doubleClickTimeout = 0L;
        }
    }

    /*
     * Exception decompiling
     */
    public void poll(int var1, int var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl384 : IF_ICMPGE - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void enableKeyRepeat(int n, int n2) {
        Keyboard.enableRepeatEvents((boolean)true);
    }

    public void enableKeyRepeat() {
        Keyboard.enableRepeatEvents((boolean)true);
    }

    public void disableKeyRepeat() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    public boolean isKeyRepeatEnabled() {
        return Keyboard.areRepeatEventsEnabled();
    }

    private void fireControlPress(int n, int n2) {
        this.consumed = false;
        for (int i = 0; i < this.controllerListeners.size(); ++i) {
            ControllerListener controllerListener = (ControllerListener)this.controllerListeners.get(i);
            if (!controllerListener.isAcceptingInput()) continue;
            switch (n) {
                case 0: {
                    controllerListener.controllerLeftPressed(n2);
                    break;
                }
                case 1: {
                    controllerListener.controllerRightPressed(n2);
                    break;
                }
                case 2: {
                    controllerListener.controllerUpPressed(n2);
                    break;
                }
                case 3: {
                    controllerListener.controllerDownPressed(n2);
                    break;
                }
                default: {
                    controllerListener.controllerButtonPressed(n2, n - 4 + 1);
                }
            }
            if (this.consumed) break;
        }
    }

    private void fireControlRelease(int n, int n2) {
        this.consumed = false;
        for (int i = 0; i < this.controllerListeners.size(); ++i) {
            ControllerListener controllerListener = (ControllerListener)this.controllerListeners.get(i);
            if (!controllerListener.isAcceptingInput()) continue;
            switch (n) {
                case 0: {
                    controllerListener.controllerLeftReleased(n2);
                    break;
                }
                case 1: {
                    controllerListener.controllerRightReleased(n2);
                    break;
                }
                case 2: {
                    controllerListener.controllerUpReleased(n2);
                    break;
                }
                case 3: {
                    controllerListener.controllerDownReleased(n2);
                    break;
                }
                default: {
                    controllerListener.controllerButtonReleased(n2, n - 4 + 1);
                }
            }
            if (this.consumed) break;
        }
    }

    public void pause() {
        this.paused = true;
        this.clearKeyPressedRecord();
        this.clearMousePressedRecord();
        this.clearControlPressedRecord();
    }

    public void resume() {
        this.paused = false;
    }

    private void fireMouseClicked(int n, int n2, int n3, int n4) {
        this.consumed = false;
        for (int i = 0; i < this.mouseListeners.size(); ++i) {
            MouseListener mouseListener = (MouseListener)this.mouseListeners.get(i);
            if (!mouseListener.isAcceptingInput()) continue;
            mouseListener.mouseClicked(n, n2, n3, n4);
            if (this.consumed) break;
        }
    }

    private class NullOutputStream
    extends OutputStream {
        final Input this$0;

        private NullOutputStream(Input input) {
            this.this$0 = input;
        }

        @Override
        public void write(int n) throws IOException {
        }
    }
}

