/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.module;

import xyz.Melody.Event.value.Mode;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Event.value.Value;
import xyz.Melody.System.Commands.Command;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.math.MathUtil;
import xyz.Melody.module.Module;

class I
extends Command {
    private final Module m;
    final Module this$0;

    I(Module module, String string, String[] stringArray, String string2, String string3) {
        super(string, stringArray, string2, string3);
        this.this$0 = module;
        this.m = module;
    }

    @Override
    public String execute(String[] stringArray) {
        if (stringArray.length >= 2) {
            Value value = null;
            Numbers numbers = null;
            Mode mode = null;
            for (Value value2 : this.m.values) {
                if (!(value2 instanceof Option) || !value2.getName().equalsIgnoreCase(stringArray[0])) continue;
                value = (Option)value2;
            }
            if (value != null) {
                value.setValue((Boolean)value.getValue() == false);
                Helper.sendMessage(String.format("> %s has been set to %s", value.getName(), value.getValue()));
            } else {
                for (Value value2 : this.m.values) {
                    if (!(value2 instanceof Numbers) || !value2.getName().equalsIgnoreCase(stringArray[0])) continue;
                    numbers = (Numbers)value2;
                }
                if (numbers != null) {
                    if (MathUtil.parsable(stringArray[1], (byte)4)) {
                        double d = MathUtil.round(Double.parseDouble(stringArray[1]), 1);
                        numbers.setValue(d);
                        Helper.sendMessage(String.format("> %s has been set to %s", numbers.getName(), numbers.getValue()));
                    } else {
                        Helper.sendMessage("> " + stringArray[1] + " is not a number");
                    }
                }
                for (Value value2 : this.m.values) {
                    if (!stringArray[0].equalsIgnoreCase(value2.getDisplayName()) || !(value2 instanceof Mode)) continue;
                    mode = (Mode)value2;
                }
                if (mode != null) {
                    if (mode.isValid(stringArray[1])) {
                        mode.setMode(stringArray[1]);
                        Helper.sendMessage(String.format("> %s set to %s", mode.getName(), mode.getModeAsString()));
                    } else {
                        Helper.sendMessage("> " + stringArray[1] + " is an invalid mode");
                    }
                }
            }
            if (numbers == null && value == null && mode == null) {
                this.syntaxError("Valid .<module> <setting> <mode if needed>");
            }
        } else if (stringArray.length >= 1) {
            Value value = null;
            for (Object object : this.m.values) {
                if (!(object instanceof Option) || !((Value)object).getName().equalsIgnoreCase(stringArray[0])) continue;
                value = (Option)object;
            }
            if (value != null) {
                Object object;
                value.setValue((Boolean)value.getValue() == false);
                object = value.getName().substring(1);
                String string = value.getName().substring(0, 1).toUpperCase();
                if (((Boolean)value.getValue()).booleanValue()) {
                    Helper.sendMessage(String.format("> %s has been set to \u00a7a%s", string + (String)object, value.getValue()));
                } else {
                    Helper.sendMessage(String.format("> %s has been set to \u00a7c%s", string + (String)object, value.getValue()));
                }
            } else {
                this.syntaxError("Valid .<module> <setting> <mode if needed>");
            }
        } else {
            Helper.sendMessage(String.format("%s Values: \n %s", this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1).toLowerCase(), this.getSyntax(), "false"));
        }
        return null;
    }
}

