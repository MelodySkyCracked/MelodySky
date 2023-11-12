/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.util.StringUtils
 */
package xyz.Melody.module.modules.QOL;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.StringUtils;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventDrawText;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class DamageFormat
extends Module {
    private String[] colors = new String[]{"\u00a7f", "\u00a7e", "\u00a76", "\u00a7c", "\u00a7f", "\u00a7f", "\u00a7f", "\u00a7f"};
    private static final NavigableMap suffixes = new TreeMap();

    public DamageFormat() {
        super("DamageFormat", new String[]{"df"}, ModuleType.QOL);
        this.setModInfo("Change The Damage Numbers to xxxM or xxxk.");
    }

    @EventHandler
    private void onFR(EventDrawText eventDrawText) {
        String string = eventDrawText.getText();
        if (string != null && (string.contains("\u2727") || string.contains("\u2727") || string.contains("\u272f") || string.contains("\u272f")) && !string.contains("M") && !string.contains("b") && !string.contains("k")) {
            String string2 = string.replaceAll("\u2727", "").replaceAll("\u272f", "").replaceAll("\u272f", "").replaceAll("\u2764", "").replaceAll("\u2727", "").replaceAll(String.valueOf('\"'), "").replaceAll("'", "").replaceAll("text", "").replaceAll(":", "").replaceAll("}", "").replaceFirst("\\{", "");
            String string3 = StringUtils.func_76338_a((String)string2.replaceAll(",", ""));
            long l2 = 0L;
            try {
                l2 = Long.parseLong(string3);
            }
            catch (Exception exception) {
                // empty catch block
            }
            String string4 = this.format(l2);
            String string5 = "";
            for (int i = 0; i < string4.length(); ++i) {
                char c = string4.charAt(i);
                String string6 = this.colors[0];
                string6 = i >= this.colors.length ? this.colors[0] : this.colors[i];
                string5 = string5 + string6 + String.valueOf(c);
            }
            eventDrawText.setText("\u00a7r\u2727" + string5 + "\u00a7e\u2727\u00a7r");
        }
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (this.mc.field_71441_e == null) {
            return;
        }
        for (Entity entity : this.mc.field_71441_e.field_72996_f) {
            if (!(entity instanceof EntityArmorStand)) continue;
            EntityArmorStand entityArmorStand = (EntityArmorStand)entity;
            char c = '\"';
            String string = entityArmorStand.func_95999_t();
            if (!entityArmorStand.func_145818_k_() || !string.contains("\u2727") && !string.contains("\u2727") && !string.contains("\u272f") && !string.contains("\u272f") || string.contains("M") || string.contains("b") || string.contains("k")) continue;
            String string2 = StringUtils.func_76338_a((String)entityArmorStand.func_70005_c_().replaceAll(",", "").replaceAll("\u2727", "").replaceAll("\u272f", "").replaceAll("\u272f", "").replaceAll("\u2764", "").replaceAll("\u2727", "").replaceAll(String.valueOf(c), "").replaceAll("'", "").replaceAll("text", "").replaceAll(":", "").replaceAll("}", "").replaceFirst("\\{", ""));
            long l2 = Long.parseLong(string2);
            String string3 = this.format(l2);
            String string4 = "";
            for (int i = 0; i < string3.length(); ++i) {
                char c2 = string3.charAt(i);
                String string5 = this.colors[0];
                string5 = i >= this.colors.length ? this.colors[0] : this.colors[i];
                string4 = string4 + string5 + String.valueOf(c2);
            }
            entityArmorStand.func_96094_a("\u00a7r\u2727" + string4 + "\u00a7e\u2727\u00a7r");
        }
    }

    public String format(long l2) {
        if (l2 == Long.MIN_VALUE) {
            return this.format(-9223372036854775807L);
        }
        if (l2 < 0L) {
            return "-" + this.format(-l2);
        }
        if (l2 < 1000L) {
            return Long.toString(l2);
        }
        Map.Entry entry = suffixes.floorEntry(l2);
        Long l3 = entry.getKey();
        String string = (String)entry.getValue();
        long l4 = l2 / (l3 / 10L);
        boolean bl = l4 < 100L && (double)l4 / 10.0 != (double)(l4 / 10L);
        return bl ? (double)l4 / 10.0 + string : l4 / 10L + string;
    }

    static {
        suffixes.put(1000L, "k");
        suffixes.put(1000000L, "M");
        suffixes.put(1000000000L, "b");
    }
}

