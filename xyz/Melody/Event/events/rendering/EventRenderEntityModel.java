/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.entity.EntityLivingBase
 */
package xyz.Melody.Event.events.rendering;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import xyz.Melody.Event.Event;

public class EventRenderEntityModel
extends Event {
    private EntityLivingBase entity;
    private float limbSwing;
    private float limbSwingAmount;
    private float ageInTicks;
    private float headYaw;
    private float headPitch;
    private float scaleFactor;
    private ModelBase model;
    private boolean outlines;

    public EventRenderEntityModel(EntityLivingBase entityLivingBase, float f, float f2, float f3, float f4, float f5, float f6, ModelBase modelBase, boolean bl) {
        this.entity = entityLivingBase;
        this.limbSwing = f;
        this.limbSwingAmount = f2;
        this.ageInTicks = f3;
        this.headYaw = f4;
        this.headPitch = f5;
        this.scaleFactor = f6;
        this.model = modelBase;
        this.outlines = bl;
    }

    public boolean isOutline() {
        return this.outlines;
    }

    public void setOutline(boolean bl) {
        this.outlines = bl;
    }

    public float getAgeInTicks() {
        return this.ageInTicks;
    }

    public EntityLivingBase getEntity() {
        return this.entity;
    }

    public float getHeadPitch() {
        return this.headPitch;
    }

    public float getHeadYaw() {
        return this.headYaw;
    }

    public float getLimbSwing() {
        return this.limbSwing;
    }

    public float getLimbSwingAmount() {
        return this.limbSwingAmount;
    }

    public ModelBase getModel() {
        return this.model;
    }

    public float getScaleFactor() {
        return this.scaleFactor;
    }
}

