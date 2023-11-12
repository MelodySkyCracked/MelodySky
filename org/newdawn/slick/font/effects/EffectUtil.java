/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.font.effects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;
import org.newdawn.slick.font.effects.ConfigurableEffect;
import org.newdawn.slick.font.effects.I;
import org.newdawn.slick.font.effects.l;
import org.newdawn.slick.font.effects.lII;
import org.newdawn.slick.font.effects.lIII;
import org.newdawn.slick.font.effects.lIIl;
import org.newdawn.slick.font.effects.lIl;
import org.newdawn.slick.font.effects.ll;
import org.newdawn.slick.font.effects.lll;

public class EffectUtil {
    private static BufferedImage scratchImage = new BufferedImage(256, 256, 2);

    public static BufferedImage getScratchImage() {
        Graphics2D graphics2D = (Graphics2D)scratchImage.getGraphics();
        graphics2D.setComposite(AlphaComposite.Clear);
        graphics2D.fillRect(0, 0, 256, 256);
        graphics2D.setComposite(AlphaComposite.SrcOver);
        graphics2D.setColor(Color.white);
        return scratchImage;
    }

    public static ConfigurableEffect.Value colorValue(String string, Color color) {
        return new lIIl(string, EffectUtil.toString(color));
    }

    public static ConfigurableEffect.Value intValue(String string, int n, String string2) {
        return new ll(string, String.valueOf(n), n, string2);
    }

    public static ConfigurableEffect.Value floatValue(String string, float f, float f2, float f3, String string2) {
        return new lll(string, String.valueOf(f), f, f2, f3, string2);
    }

    public static ConfigurableEffect.Value booleanValue(String string, boolean bl, String string2) {
        return new l(string, String.valueOf(bl), bl, string2);
    }

    public static ConfigurableEffect.Value optionValue(String string, String string2, String[][] stringArray, String string3) {
        return new lIII(string, string2.toString(), stringArray, string2, string3);
    }

    public static String toString(Color color) {
        String string;
        String string2;
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null.");
        }
        String string3 = Integer.toHexString(color.getRed());
        if (string3.length() == 1) {
            string3 = "0" + string3;
        }
        if ((string2 = Integer.toHexString(color.getGreen())).length() == 1) {
            string2 = "0" + string2;
        }
        if ((string = Integer.toHexString(color.getBlue())).length() == 1) {
            string = "0" + string;
        }
        return string3 + string2 + string;
    }

    public static Color fromString(String string) {
        if (string == null || string.length() != 6) {
            return Color.white;
        }
        return new Color(Integer.parseInt(string.substring(0, 2), 16), Integer.parseInt(string.substring(2, 4), 16), Integer.parseInt(string.substring(4, 6), 16));
    }

    private static class ValueDialog
    extends JDialog {
        public boolean okPressed = false;

        public ValueDialog(JComponent jComponent, String string, String string2) {
            this.setDefaultCloseOperation(2);
            this.setLayout(new GridBagLayout());
            this.setModal(true);
            if (jComponent instanceof JSpinner) {
                ((JSpinner.DefaultEditor)((JSpinner)jComponent).getEditor()).getTextField().setColumns(4);
            }
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new GridBagLayout());
            this.getContentPane().add((Component)jPanel, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
            jPanel.setBackground(Color.white);
            jPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
            JComponent jComponent2 = new JTextArea(string2);
            jPanel.add((Component)jComponent2, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, 10, 1, new Insets(5, 5, 5, 5), 0, 0));
            ((JTextArea)jComponent2).setWrapStyleWord(true);
            ((JTextArea)jComponent2).setLineWrap(true);
            jComponent2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            ((JTextComponent)jComponent2).setEditable(false);
            jComponent2 = new JPanel();
            this.getContentPane().add((Component)jComponent2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, 10, 0, new Insets(5, 5, 0, 5), 0, 0));
            jComponent2.add(new JLabel(string + ":"));
            jComponent2.add(jComponent);
            JPanel jPanel2 = new JPanel();
            this.getContentPane().add((Component)jPanel2, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
            JButton jButton = new JButton("OK");
            jPanel2.add(jButton);
            jButton.addActionListener(new I(this));
            jButton = new JButton("Cancel");
            jPanel2.add(jButton);
            jButton.addActionListener(new lIl(this));
            this.setSize(new Dimension(320, 175));
        }
    }

    private static abstract class DefaultValue
    implements ConfigurableEffect.Value {
        String value;
        String name;

        public DefaultValue(String string, String string2) {
            this.value = string2;
            this.name = string;
        }

        @Override
        public void setString(String string) {
            this.value = string;
        }

        @Override
        public String getString() {
            return this.value;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public String toString() {
            if (this.value == null) {
                return "";
            }
            return this.value.toString();
        }

        public boolean showValueDialog(JComponent jComponent, String string) {
            ValueDialog valueDialog = new ValueDialog(jComponent, this.name, string);
            valueDialog.setTitle(this.name);
            valueDialog.setLocationRelativeTo(null);
            EventQueue.invokeLater(new lII(this, jComponent));
            valueDialog.setVisible(true);
            return valueDialog.okPressed;
        }
    }
}

