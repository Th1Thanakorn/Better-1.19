package com.thana.better119.common.entity.goals;

import com.thana.better119.common.entity.MoobloomEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class MoobloomGrowCropGoal extends Goal {

	private final MoobloomEntity moobloom;

	public MoobloomGrowCropGoal(MoobloomEntity moobloom) {
		this.moobloom = moobloom;
	}

	public boolean canUse() {
		return this.moobloom.getRandom().nextFloat() >= 0.3F;
	}

	@Override
	public void tick() {
		if (this.moobloom.getRandom().nextInt(this.adjustedTickDelay(30)) == 0) {
			for (int i = -1; i <= 1; i++) {
				BlockPos blockpos = this.moobloom.blockPosition().below(i);
				BlockState blockstate = this.moobloom.level.getBlockState(blockpos);
				Block block = blockstate.getBlock();
				boolean flag = false;
				IntegerProperty integerproperty = null;
				if (blockstate.is(BlockTags.BEE_GROWABLES)) {
					if (block instanceof CropBlock cropblock) {
						if (!cropblock.isMaxAge(blockstate)) {
							flag = true;
							integerproperty = cropblock.getAgeProperty();
						}
					} else if (block instanceof StemBlock) {
						int j = blockstate.getValue(StemBlock.AGE);
						if (j < 7) {
							flag = true;
							integerproperty = StemBlock.AGE;
						}
					} else if (blockstate.is(Blocks.SWEET_BERRY_BUSH)) {
						int k = blockstate.getValue(SweetBerryBushBlock.AGE);
						if (k < 3) {
							flag = true;
							integerproperty = SweetBerryBushBlock.AGE;
						}
					} else if (blockstate.is(Blocks.CAVE_VINES) || blockstate.is(Blocks.CAVE_VINES_PLANT)) {
						((BonemealableBlock) blockstate.getBlock()).performBonemeal((ServerLevel) this.moobloom.level, this.moobloom.getRandom(), blockpos, blockstate);
					}

					if (flag) {
						this.moobloom.level.levelEvent(2005, blockpos, 0);
						this.moobloom.level.setBlockAndUpdate(blockpos, blockstate.setValue(integerproperty, blockstate.getValue(integerproperty) + 1));
					}
				}
			}
		}
	}
}
