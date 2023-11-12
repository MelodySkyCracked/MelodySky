/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchemaComponent;

public interface nsISchemaFacet
extends nsISchemaComponent {
    public static final String NS_ISCHEMAFACET_IID = "{3c14a031-6f4e-11d5-9b46-000064657374}";
    public static final int FACET_TYPE_LENGTH = 1;
    public static final int FACET_TYPE_MINLENGTH = 2;
    public static final int FACET_TYPE_MAXLENGTH = 3;
    public static final int FACET_TYPE_PATTERN = 4;
    public static final int FACET_TYPE_ENUMERATION = 5;
    public static final int FACET_TYPE_WHITESPACE = 6;
    public static final int FACET_TYPE_MAXINCLUSIVE = 7;
    public static final int FACET_TYPE_MININCLUSIVE = 8;
    public static final int FACET_TYPE_MAXEXCLUSIVE = 9;
    public static final int FACET_TYPE_MINEXCLUSIVE = 10;
    public static final int FACET_TYPE_TOTALDIGITS = 11;
    public static final int FACET_TYPE_FRACTIONDIGITS = 12;
    public static final int WHITESPACE_PRESERVE = 1;
    public static final int WHITESPACE_REPLACE = 1;
    public static final int WHITESPACE_COLLAPSE = 1;

    public int getFacetType();

    public String getValue();

    public long getLengthValue();

    public long getDigitsValue();

    public int getWhitespaceValue();

    public boolean getIsfixed();
}

