package com.thana.better119.common.entity.models;

import com.thana.better119.common.entity.CopperGolemEntity;
import com.thana.better119.core.Better1_19;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CopperGolemModel extends AnimatedGeoModel<CopperGolemEntity> {

    @Override
    public ResourceLocation getModelResource(CopperGolemEntity object) {
        return new ResourceLocation(Better1_19.MODID, "geo/copper_golem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CopperGolemEntity entity) {
        if (entity.getOxidizationState() == CopperGolemEntity.OxidizationState.OXIDIZED) {
            return new ResourceLocation(Better1_19.MODID, "textures/entities/copper_golem_oxidized.png");
        }
        else if (entity.getOxidizationState() == CopperGolemEntity.OxidizationState.WEATHERED) {
            return new ResourceLocation(Better1_19.MODID, "textures/entities/copper_golem_weathered.png");
        }
        else if (entity.getOxidizationState() == CopperGolemEntity.OxidizationState.EXPOSED) {
            return new ResourceLocation(Better1_19.MODID, "textures/entities/copper_golem_exposed.png");
        }
        else {
            return new ResourceLocation(Better1_19.MODID, "textures/entities/copper_golem.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(CopperGolemEntity animatable) {
        return new ResourceLocation(Better1_19.MODID, "animations/animation.copper_golem.json");
    }
}
