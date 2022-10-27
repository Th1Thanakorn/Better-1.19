package com.thana.better119.common.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thana.better119.common.entity.CopperGolemEntity;
import com.thana.better119.common.entity.models.CopperGolemModel;
import com.thana.better119.core.Better1_19;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CopperGolemRenderer extends GeoEntityRenderer<CopperGolemEntity> {

    public CopperGolemRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CopperGolemModel());
        this.shadowRadius = 0.4F;
    }

    @Override
    public ResourceLocation getTextureLocation(CopperGolemEntity entity) {
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
    public RenderType getRenderType(CopperGolemEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        stack.scale(0.8F, 0.8F, 0.8F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
