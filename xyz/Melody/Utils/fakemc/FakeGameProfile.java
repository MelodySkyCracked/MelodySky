/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.properties.PropertyMap
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.builder.ToStringBuilder
 */
package xyz.Melody.Utils.fakemc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.PropertyMap;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class FakeGameProfile
extends GameProfile {
    private UUID id;
    private String name;
    private PropertyMap properties = new PropertyMap();
    private boolean legacy;

    public FakeGameProfile(UUID uUID, String string) {
        super(uUID, string);
        if (uUID == null && StringUtils.isBlank((CharSequence)string)) {
            throw new IllegalArgumentException("Name and ID cannot both be blank");
        }
        this.id = uUID;
        this.name = string;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public PropertyMap getProperties() {
        return this.properties;
    }

    public boolean isComplete() {
        return this.id != null && StringUtils.isNotBlank((CharSequence)this.getName());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || ((Object)((Object)this)).getClass() != object.getClass()) {
            return false;
        }
        GameProfile gameProfile = (GameProfile)object;
        if (this.id != null ? !this.id.equals(gameProfile.getId()) : gameProfile.getId() != null) {
            return false;
        }
        return !(this.name != null ? !this.name.equals(gameProfile.getName()) : gameProfile.getName() != null);
    }

    public int hashCode() {
        int n = this.id != null ? this.id.hashCode() : 0;
        n = 31 * n + (this.name != null ? this.name.hashCode() : 0);
        return n;
    }

    public String toString() {
        return new ToStringBuilder((Object)this).append("id", (Object)this.id).append("name", (Object)this.name).append("properties", (Object)this.properties).append("legacy", this.legacy).toString();
    }

    public boolean isLegacy() {
        return this.legacy;
    }
}

