/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 */
package xyz.Melody.System.Managers.Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import xyz.Melody.Client;
import xyz.Melody.System.Managers.Client.FriendCommand;
import xyz.Melody.System.Managers.Manager;

public final class FriendManager
implements Manager {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static HashMap friends;

    @Override
    public void init() {
        friends = new HashMap();
        Client.instance.getCommandManager().add(new FriendCommand("f", new String[]{"friend", "fren", "fr"}));
    }

    public static boolean isFriend(String string) {
        return friends.containsKey(string);
    }

    public static String getAlias(Object object) {
        return (String)friends.get(object);
    }

    public static HashMap getFriends() {
        return friends;
    }
}

