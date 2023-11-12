/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

public class SyntaxHighlighterOptions {
    private boolean isGutterVisible = true;
    private boolean isControlAreaVisible = true;
    private boolean isBlockCollapsed;
    private int firstLineNumber = 1;
    private boolean isShowingColumns;

    public void setGutterVisible(boolean bl) {
        this.isGutterVisible = bl;
    }

    public boolean isGutterVisible() {
        return this.isGutterVisible;
    }

    public void setControlAreaVisible(boolean bl) {
        this.isControlAreaVisible = bl;
    }

    public boolean isControlAreaVisible() {
        return this.isControlAreaVisible;
    }

    public void setFirstLineNumber(int n) {
        this.firstLineNumber = n;
    }

    public int getFirstLineNumber() {
        return this.firstLineNumber;
    }

    String getOptionsString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!this.isGutterVisible) {
            stringBuilder.append(":nogutter");
        }
        if (!this.isControlAreaVisible) {
            stringBuilder.append(":nocontrols");
        }
        if (this.isBlockCollapsed) {
            stringBuilder.append(":collapse");
        }
        if (this.firstLineNumber != 1) {
            stringBuilder.append(":firstline[" + this.firstLineNumber + "]");
        }
        if (this.isShowingColumns) {
            stringBuilder.append(":showcolumns");
        }
        return stringBuilder.toString();
    }
}

