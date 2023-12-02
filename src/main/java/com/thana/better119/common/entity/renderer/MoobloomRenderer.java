package com.thana.better119.common.entity.renderer;

import com.thana.better119.common.entity.MoobloomEntity;
import com.thana.better119.common.entity.models.MoobloomModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MoobloomRenderer extends GeoEntityRenderer<MoobloomEntity> {

	public MoobloomRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new MoobloomModel());
		this.shadowRadius = 0.4F;
	}
}
