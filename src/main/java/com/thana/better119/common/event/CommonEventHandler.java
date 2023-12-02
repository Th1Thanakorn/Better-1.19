package com.thana.better119.common.event;

import com.thana.better119.common.entity.CopperGolemEntity;
import com.thana.better119.common.entity.EntityTypeInit;
import com.thana.better119.common.entity.MoobloomEntity;
import com.thana.better119.core.Better1_19;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Better1_19.MODID)
public class CommonEventHandler {

    @SubscribeEvent
    public static void mobAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityTypeInit.COPPER_GOLEM.get(), CopperGolemEntity.createAttributes().build());
        event.put(EntityTypeInit.MOOBLOOM.get(), MoobloomEntity.createAttributes().build());
    }

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player) {
            BlockState state = event.getPlacedBlock();
            Level level = event.getEntity().getLevel();
            BlockPos pos = event.getPos();
            if (state.is(Blocks.LIGHTNING_ROD)) {
                BlockState belowState = level.getBlockState(pos.below());
                if (belowState.is(Blocks.COPPER_BLOCK)) {
                    for (int i = 0; i <= 1; i++) {
                        BlockInWorld block = new BlockInWorld(level, pos.offset(0, -i, 0), true);
                        level.setBlock(block.getPos(), Blocks.AIR.defaultBlockState(), 2);
                        level.levelEvent(2001, pos.below(), Block.getId(block.getState()));
                    }
                    CopperGolemEntity copperGolem = EntityTypeInit.COPPER_GOLEM.get().create(level);
                    copperGolem.moveTo((double) pos.below().getX() + 0.5D, (double) pos.below().getY() + 0.05D, (double) pos.below().getZ() + 0.5D, 0.0F, 0.0F);
                    level.addFreshEntity(copperGolem);
                }
            }
        }
    }
}
