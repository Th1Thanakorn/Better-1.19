package com.thana.better119.common.entity.models;

import com.thana.better119.common.entity.MoobloomEntity;
import com.thana.better119.core.Better1_19;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MoobloomModel extends AnimatedGeoModel<MoobloomEntity> {

	@Override
	public ResourceLocation getModelResource(MoobloomEntity object) {
		return new ResourceLocation(Better1_19.MODID, "geo/moobloom.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MoobloomEntity object) {
		return new ResourceLocation(Better1_19.MODID, "textures/entities/moobloom.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MoobloomEntity animatable) {
		return new ResourceLocation(Better1_19.MODID, "animations/animation.moobloom.json");
	}
}
