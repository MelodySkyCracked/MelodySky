/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.TextChangeListener;

public interface StyledTextContent {
    public void addTextChangeListener(TextChangeListener var1);

    public int getCharCount();

    public String getLine(int var1);

    public int getLineAtOffset(int var1);

    public int getLineCount();

    public String getLineDelimiter();

    public int getOffsetAtLine(int var1);

    public String getTextRange(int var1, int var2);

    public void removeTextChangeListener(TextChangeListener var1);

    public void replaceTextRange(int var1, int var2, String var3);

    public void setText(String var1);
}

