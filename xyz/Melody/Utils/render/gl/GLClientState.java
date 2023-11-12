/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.render.gl;

import xyz.Melody.Utils.render.gl.GLenum;

public enum GLClientState implements GLenum
{
    COLOR("GL_COLOR_ARRAY", 32886),
    EDGE("GL_EDGE_FLAG_ARRAY", 32889),
    FOG("GL_FOG_COORD_ARRAY", 33879),
    INDEX("GL_INDEX_ARRAY", 32887),
    NORMAL("GL_NORMAL_ARRAY", 32885),
    SECONDARY_COLOR("GL_SECONDARY_COLOR_ARRAY", 33886),
    TEXTURE("GL_TEXTURE_COORD_ARRAY", 32888),
    VERTEX("GL_VERTEX_ARRAY", 32884);

    private final String name;
    private final int cap;

    /*
     * WARNING - void declaration
     */
    private GLClientState() {
        void var4_2;
        void var3_1;
        this.name = var3_1;
        this.cap = var4_2;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getCap() {
        return this.cap;
    }
}

