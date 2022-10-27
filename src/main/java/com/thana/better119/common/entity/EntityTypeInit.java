package com.thana.better119.common.entity;

import com.thana.better119.core.Better1_19;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypeInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Better1_19.MODID);

    public static final RegistryObject<EntityType<CopperGolemEntity>> COPPER_GOLEM = ENTITIES.register("copper_golem", () -> EntityType.Builder.of(CopperGolemEntity::new, MobCategory.MISC).sized(0.75F, 1.2F).build(new ResourceLocation(Better1_19.MODID, "copper_golem").toString()));

    public static void init() {
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
