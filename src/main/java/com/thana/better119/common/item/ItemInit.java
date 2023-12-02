package com.thana.better119.common.item;

import com.thana.better119.common.block.BlockInit;
import com.thana.better119.common.entity.EntityTypeInit;
import com.thana.better119.core.Better1_19;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("all")
public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Better1_19.MODID);

    public static final RegistryObject<Item> COPPER_GOLEM_SPAWN_EGG = ITEMS.register("copper_golem_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeInit.COPPER_GOLEM::get, 0xCA8536, 0xBD7103, new Item.Properties().tab(Better1_19.TAB)));
    public static final RegistryObject<Item> MOOBLOOM_SPAWN_EGG = ITEMS.register("moobloom_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeInit.MOOBLOOM::get, 0xFFDC15, 0xFFF5C8, new Item.Properties().tab(Better1_19.TAB)));

    // Block Item
    public static final RegistryObject<Item> COPPER_BUTTON = ITEMS.register("copper_button", () -> new BlockItemBase(BlockInit.COPPER_BUTTON.get()));
    public static final RegistryObject<Item> WEATHERED_COPPER_BUTTON = ITEMS.register("weathered_copper_button", () -> new BlockItemBase(BlockInit.WEATHERED_COPPER_BUTTON.get()));
    public static final RegistryObject<Item> EXPOSED_COPPER_BUTTON = ITEMS.register("exposed_copper_button", () -> new BlockItemBase(BlockInit.EXPOSED_COPPER_BUTTON.get()));
    public static final RegistryObject<Item> OXIDIZED_COPPER_BUTTON = ITEMS.register("oxidized_copper_button", () -> new BlockItemBase(BlockInit.OXIDIZED_COPPER_BUTTON.get()));

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
