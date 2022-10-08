package com.thana.better119.common.item;

import com.thana.better119.common.block.BlockInit;
import com.thana.better119.core.Better1_19;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("all")
public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Better1_19.MODID);

    // Block Item
    public static final RegistryObject<Item> COPPER_BUTTON = ITEMS.register("copper_button", () -> new BlockItemBase(BlockInit.COPPER_BUTTON.get()));
    public static final RegistryObject<Item> WEATHERED_COPPER_BUTTON = ITEMS.register("weathered_copper_button", () -> new BlockItemBase(BlockInit.WEATHERED_COPPER_BUTTON.get()));
    public static final RegistryObject<Item> EXPOSED_COPPER_BUTTON = ITEMS.register("exposed_copper_button", () -> new BlockItemBase(BlockInit.EXPOSED_COPPER_BUTTON.get()));
    public static final RegistryObject<Item> OXIDIZED_COPPER_BUTTON = ITEMS.register("oxidized_copper_button", () -> new BlockItemBase(BlockInit.OXIDIZED_COPPER_BUTTON.get()));

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
