/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.System.Managers.Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import xyz.Melody.Client;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.misc.EventChat;
import xyz.Melody.System.Commands.Command;
import xyz.Melody.System.Commands.commands.AuthMe;
import xyz.Melody.System.Commands.commands.AutoRubyCMD;
import xyz.Melody.System.Commands.commands.AutoWalkCMD;
import xyz.Melody.System.Commands.commands.Bind;
import xyz.Melody.System.Commands.commands.CustomLbinColor;
import xyz.Melody.System.Commands.commands.GaoNengCommands;
import xyz.Melody.System.Commands.commands.Help;
import xyz.Melody.System.Commands.commands.IRCCommands;
import xyz.Melody.System.Commands.commands.NickCommand;
import xyz.Melody.System.Commands.commands.RankCommand;
import xyz.Melody.System.Commands.commands.ShowItemSBID;
import xyz.Melody.System.Commands.commands.Toggle;
import xyz.Melody.System.Managers.Client.I;
import xyz.Melody.System.Managers.Manager;
import xyz.Melody.Utils.Helper;
import xyz.Melody.module.modules.others.ClientCommands;

public final class CommandManager
implements Manager {
    private List commands;

    @Override
    public void init() {
        this.commands = new ArrayList();
        this.commands.add(new I(this, "test", new String[]{"test"}, "", "testing"));
        this.commands.add(new AutoWalkCMD());
        this.commands.add(new AutoRubyCMD());
        this.commands.add(new RankCommand());
        this.commands.add(new GaoNengCommands());
        this.commands.add(new AuthMe());
        this.commands.add(new NickCommand());
        this.commands.add(new CustomLbinColor());
        this.commands.add(new ShowItemSBID());
        this.commands.add(new IRCCommands());
        this.commands.add(new Help());
        this.commands.add(new Toggle());
        this.commands.add(new Bind());
        EventBus.getInstance().register(this);
    }

    public List getCommands() {
        return this.commands;
    }

    public Optional getCommandByName(String string) {
        return this.commands.stream().filter(arg_0 -> CommandManager.lambda$getCommandByName$0(string, arg_0)).findFirst();
    }

    public void add(Command command) {
        this.commands.add(command);
    }

    @EventHandler
    private void onChat(EventChat eventChat) {
        String[] stringArray;
        ClientCommands clientCommands = ClientCommands.getINSTANCE();
        String string = ".";
        switch (((Enum)clientCommands.mode.getValue()).toString()) {
            case "dot": {
                string = ".";
                break;
            }
            case "bar": {
                string = "-";
                break;
            }
            case "wavy_line": {
                string = "~";
            }
        }
        if (!(!Client.clientChat || eventChat.getMessage().length() > 1 && eventChat.getMessage().startsWith(string) || eventChat.getMessage().startsWith("/"))) {
            stringArray = eventChat.getMessage().replace("&", "\u00a7");
            Client.instance.irc.sendPrefixMsg((String)stringArray, true);
            eventChat.setCancelled(true);
            return;
        }
        if (clientCommands.isEnabled() && eventChat.getMessage().length() > 1 && eventChat.getMessage().startsWith(string)) {
            eventChat.setCancelled(true);
            stringArray = eventChat.getMessage().trim().substring(1).split(" ");
            Optional optional = this.getCommandByName(stringArray[0]);
            if (optional.isPresent()) {
                String string2 = ((Command)optional.get()).execute(Arrays.copyOfRange(stringArray, 1, stringArray.length));
                if (string2 != null && !string2.isEmpty()) {
                    Helper.sendMessage(string2);
                }
            } else {
                Helper.sendMessage(String.format("Command not found Try '%shelp'", "."));
            }
        }
    }

    private static boolean lambda$getCommandByName$0(String string, Command command) {
        boolean bl = false;
        for (String string2 : command.getAlias()) {
            if (!string2.equalsIgnoreCase(string)) continue;
            bl = true;
            break;
        }
        return command.getName().equalsIgnoreCase(string) || bl;
    }

    static List access$000(CommandManager commandManager) {
        return commandManager.commands;
    }
}

