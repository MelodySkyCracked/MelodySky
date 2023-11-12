/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.newdawn.slick.Input;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.Control;
import org.newdawn.slick.command.ControllerButtonControl;
import org.newdawn.slick.command.ControllerDirectionControl;
import org.newdawn.slick.command.I;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;
import org.newdawn.slick.util.InputAdapter;

public class InputProvider {
    private HashMap commands;
    private ArrayList listeners = new ArrayList();
    private Input input;
    private HashMap commandState = new HashMap();
    private boolean active = true;

    public InputProvider(Input input) {
        this.input = input;
        input.addListener(new InputListenerImpl(this, null));
        this.commands = new HashMap();
    }

    public List getUniqueCommands() {
        ArrayList<Command> arrayList = new ArrayList<Command>();
        for (Command command : this.commands.values()) {
            if (arrayList.contains(command)) continue;
            arrayList.add(command);
        }
        return arrayList;
    }

    public List getControlsFor(Command command) {
        ArrayList<Control> arrayList = new ArrayList<Control>();
        for (Map.Entry entry : this.commands.entrySet()) {
            Control control = (Control)entry.getKey();
            Command command2 = (Command)entry.getValue();
            if (command2 != command) continue;
            arrayList.add(control);
        }
        return arrayList;
    }

    public void setActive(boolean bl) {
        this.active = bl;
    }

    public boolean isActive() {
        return this.active;
    }

    public void addListener(InputProviderListener inputProviderListener) {
        this.listeners.add(inputProviderListener);
    }

    public void removeListener(InputProviderListener inputProviderListener) {
        this.listeners.remove(inputProviderListener);
    }

    public void bindCommand(Control control, Command command) {
        this.commands.put(control, command);
        if (this.commandState.get(command) == null) {
            this.commandState.put(command, new CommandState(this, null));
        }
    }

    public void clearCommand(Command command) {
        List list = this.getControlsFor(command);
        for (int i = 0; i < list.size(); ++i) {
            this.unbindCommand((Control)list.get(i));
        }
    }

    public void unbindCommand(Control control) {
        Command command = (Command)this.commands.remove(control);
        if (command != null && !this.commands.keySet().contains(command)) {
            this.commandState.remove(command);
        }
    }

    private CommandState getState(Command command) {
        return (CommandState)this.commandState.get(command);
    }

    public boolean isCommandControlDown(Command command) {
        return this.getState(command).isDown();
    }

    public boolean isCommandControlPressed(Command command) {
        return this.getState(command).isPressed();
    }

    protected void firePressed(Command command) {
        CommandState.access$202(this.getState(command), true);
        CommandState.access$302(this.getState(command), true);
        if (!this.isActive()) {
            return;
        }
        for (int i = 0; i < this.listeners.size(); ++i) {
            ((InputProviderListener)this.listeners.get(i)).controlPressed(command);
        }
    }

    protected void fireReleased(Command command) {
        CommandState.access$202(this.getState(command), false);
        if (!this.isActive()) {
            return;
        }
        for (int i = 0; i < this.listeners.size(); ++i) {
            ((InputProviderListener)this.listeners.get(i)).controlReleased(command);
        }
    }

    static HashMap access$400(InputProvider inputProvider) {
        return inputProvider.commands;
    }

    private class InputListenerImpl
    extends InputAdapter {
        final InputProvider this$0;

        private InputListenerImpl(InputProvider inputProvider) {
            this.this$0 = inputProvider;
        }

        @Override
        public boolean isAcceptingInput() {
            return true;
        }

        @Override
        public void keyPressed(int n, char c) {
            Command command = (Command)InputProvider.access$400(this.this$0).get(new KeyControl(n));
            if (command != null) {
                this.this$0.firePressed(command);
            }
        }

        @Override
        public void keyReleased(int n, char c) {
            Command command = (Command)InputProvider.access$400(this.this$0).get(new KeyControl(n));
            if (command != null) {
                this.this$0.fireReleased(command);
            }
        }

        @Override
        public void mousePressed(int n, int n2, int n3) {
            Command command = (Command)InputProvider.access$400(this.this$0).get(new MouseButtonControl(n));
            if (command != null) {
                this.this$0.firePressed(command);
            }
        }

        @Override
        public void mouseReleased(int n, int n2, int n3) {
            Command command = (Command)InputProvider.access$400(this.this$0).get(new MouseButtonControl(n));
            if (command != null) {
                this.this$0.fireReleased(command);
            }
        }

        @Override
        public void controllerLeftPressed(int n) {
            Command command = (Command)InputProvider.access$400(this.this$0).get(new ControllerDirectionControl(n, ControllerDirectionControl.LEFT));
            if (command != null) {
                this.this$0.firePressed(command);
            }
        }

        @Override
        public void controllerLeftReleased(int n) {
            Command command = (Command)InputProvider.access$400(this.this$0).get(new ControllerDirectionControl(n, ControllerDirectionControl.LEFT));
            if (command != null) {
                this.this$0.fireReleased(command);
            }
        }

        @Override
        public void controllerRightPressed(int n) {
            Command command = (Command)InputProvider.access$400(this.this$0).get(new ControllerDirectionControl(n, ControllerDirectionControl.RIGHT));
            if (command != null) {
                this.this$0.firePressed(command);
            }
        }

        @Override
        public void controllerRightReleased(int n) {
            Command command = (Command)InputProvider.access$400(this.this$0).get(new ControllerDirectionControl(n, ControllerDirectionControl.RIGHT));
            if (command != null) {
                this.this$0.fireReleased(command);
            }
        }

        @Override
        public void controllerUpPressed(int n) {
            Command command = (Command)InputProvider.access$400(this.this$0).get(new ControllerDirectionControl(n, ControllerDirectionControl.UP));
            if (command != null) {
                this.this$0.firePressed(command);
            }
        }

        @Override
        public void controllerUpReleased(int n) {
            Command command = (Command)InputProvider.access$400(this.this$0).get(new ControllerDirectionControl(n, ControllerDirectionControl.UP));
            if (command != null) {
                this.this$0.fireReleased(command);
            }
        }

        @Override
        public void controllerDownPressed(int n) {
            Command command = (Command)InputProvider.access$400(this.this$0).get(new ControllerDirectionControl(n, ControllerDirectionControl.DOWN));
            if (command != null) {
                this.this$0.firePressed(command);
            }
        }

        @Override
        public void controllerDownReleased(int n) {
            Command command = (Command)InputProvider.access$400(this.this$0).get(new ControllerDirectionControl(n, ControllerDirectionControl.DOWN));
            if (command != null) {
                this.this$0.fireReleased(command);
            }
        }

        @Override
        public void controllerButtonPressed(int n, int n2) {
            Command command = (Command)InputProvider.access$400(this.this$0).get(new ControllerButtonControl(n, n2));
            if (command != null) {
                this.this$0.firePressed(command);
            }
        }

        @Override
        public void controllerButtonReleased(int n, int n2) {
            Command command = (Command)InputProvider.access$400(this.this$0).get(new ControllerButtonControl(n, n2));
            if (command != null) {
                this.this$0.fireReleased(command);
            }
        }

        InputListenerImpl(InputProvider inputProvider, I i) {
            this(inputProvider);
        }
    }

    private class CommandState {
        private boolean down;
        private boolean pressed;
        final InputProvider this$0;

        private CommandState(InputProvider inputProvider) {
            this.this$0 = inputProvider;
        }

        public boolean isPressed() {
            if (this.pressed) {
                this.pressed = false;
                return true;
            }
            return false;
        }

        public boolean isDown() {
            return this.down;
        }

        CommandState(InputProvider inputProvider, I i) {
            this(inputProvider);
        }

        static boolean access$202(CommandState commandState, boolean bl) {
            commandState.down = bl;
            return commandState.down;
        }

        static boolean access$302(CommandState commandState, boolean bl) {
            commandState.pressed = bl;
            return commandState.pressed;
        }
    }
}

