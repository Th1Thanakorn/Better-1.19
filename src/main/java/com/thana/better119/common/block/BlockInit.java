package com.thana.better119.common.block;

import com.thana.better119.core.Better1_19;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Better1_19.MODID);

    public static final RegistryObject<Block> COPPER_BUTTON = BLOCKS.register("copper_button", () -> new CopperButtonBlock(WeatheringCopper.WeatherState.UNAFFECTED, BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F)));
    public static final RegistryObject<Block> WEATHERED_COPPER_BUTTON = BLOCKS.register("weathered_copper_button", () -> new CopperButtonBlock(WeatheringCopper.WeatherState.WEATHERED, BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F)));
    public static final RegistryObject<Block> EXPOSED_COPPER_BUTTON = BLOCKS.register("exposed_copper_button", () -> new CopperButtonBlock(WeatheringCopper.WeatherState.EXPOSED, BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F)));
    public static final RegistryObject<Block> OXIDIZED_COPPER_BUTTON = BLOCKS.register("oxidized_copper_button", () -> new CopperButtonBlock(WeatheringCopper.WeatherState.OXIDIZED, BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F)));

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
