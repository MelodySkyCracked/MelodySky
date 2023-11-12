/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.GUI.ClickGui.Utils.Elements;

import xyz.Melody.Event.value.TextValue;
import xyz.Melody.GUI.ClickGui.Utils.Elements.Element;
import xyz.Melody.GUI.ClickGui.Utils.TextField;
import xyz.Melody.GUI.Font.FontLoaders;

public class TextEle
implements Element {
    private TextValue text;
    private String name;
    private TextField txtField;

    public TextEle(TextValue textValue) {
        this.txtField = new TextField(0, (String)textValue.getValue(), 0, 0, 100, 20);
        this.text = textValue;
        this.name = textValue.getName();
        this.syncText();
    }

    public TextValue getText() {
        return this.text;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void reset() {
        this.syncText();
    }

    @Override
    public void draw(float f, float f2, float f3, int n, float f4) {
        float f5 = f2 + 75.0f + (float)n + f4;
        this.txtField.func_146189_e(true);
        FontLoaders.NMSL24.drawString(this.name, f + 20.0f, f5, -1);
        this.txtField.drawTextBox((int)f3 - 135, (int)f5 - 6);
        if (this.txtField.func_146179_b() != this.text.getValue()) {
            this.setText(this.getTextField().func_146179_b());
        }
    }

    @Override
    public void handleMouseActions(float f, float f2) {
        this.txtField.func_146192_a((int)f, (int)f2, 0);
    }

    public void handleKeyTypes(char c, int n) {
        this.txtField.func_146201_a(c, n);
    }

    public TextField getTextField() {
        return this.txtField;
    }

    public void setText(String string) {
        this.text.setValue(string);
    }

    public void syncText() {
        this.setFieldText((String)this.text.getValue());
    }

    public void setFieldText(String string) {
        this.txtField.func_146180_a(string);
    }
}

