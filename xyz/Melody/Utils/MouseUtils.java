/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.MouseHelper
 *  org.lwjgl.input.Mouse
 */
package xyz.Melody.Utils;

import net.minecraft.util.MouseHelper;
import org.lwjgl.input.Mouse;
import xyz.Melody.Client;
import xyz.Melody.Utils.ll;

public class MouseUtils {
    private static boolean isUngrabbed = false;
    private static MouseHelper oldMouseHelper;
    private static boolean doesGameWantUngrab;

    public static void ungrabMouse() {
        if (!Client.mc.field_71415_G || isUngrabbed) {
            return;
        }
        if (oldMouseHelper == null) {
            oldMouseHelper = Client.mc.field_71417_B;
        }
        Client.mc.field_71474_y.field_82881_y = false;
        doesGameWantUngrab = !Mouse.isGrabbed();
        oldMouseHelper.func_74373_b();
        Client.mc.field_71415_G = true;
        Client.mc.field_71417_B = new ll();
        isUngrabbed = true;
    }

    public static void regrabMouse() {
        if (!isUngrabbed) {
            return;
        }
        Client.mc.field_71417_B = oldMouseHelper;
        if (!doesGameWantUngrab) {
            Client.mc.field_71417_B.func_74372_a();
        }
        oldMouseHelper = null;
        isUngrabbed = false;
    }

    static boolean access$002(boolean bl) {
        doesGameWantUngrab = bl;
        return doesGameWantUngrab;
    }
}

