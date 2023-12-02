package com.thana.better119.core;

import com.mojang.logging.LogUtils;
import com.thana.better119.common.block.BlockInit;
import com.thana.better119.common.entity.EntityTypeInit;
import com.thana.better119.common.entity.renderer.CopperGolemRenderer;
import com.thana.better119.common.entity.renderer.MoobloomRenderer;
import com.thana.better119.common.event.CommonEventHandler;
import com.thana.better119.common.item.ItemInit;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.server.commands.DebugPathCommand;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.resource.ResourceListener;

@Mod(Better1_19.MODID)
public class Better1_19 {

    public static final String MODID = "better119";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Better1_19() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());

        BlockInit.init();
        ItemInit.init();
        EntityTypeInit.init();

        // Register GeckoLib
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ResourceListener::registerReloadListener);
        GeckoLibNetwork.initialize();
        GeckoLib.hasInitialized = true;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        DebugPathCommand.register(event.getDispatcher());
    }

    public void onClientSetup(final FMLClientSetupEvent event) {
        EntityRenderers.register(EntityTypeInit.COPPER_GOLEM.get(), CopperGolemRenderer::new);
        EntityRenderers.register(EntityTypeInit.MOOBLOOM.get(), MoobloomRenderer::new);
    }

    public static final CreativeModeTab TAB = new CreativeModeTab("better119") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.OAK_LEAVES);
        }
    };
}
