package com.thana.better119.common.entity.goals;

import com.thana.better119.common.block.CopperButtonBlock;
import com.thana.better119.common.entity.CopperGolemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FindButtonGoal extends Goal {

    private final CopperGolemEntity mob;
    private float lastTick;

    public FindButtonGoal(CopperGolemEntity mob) {
        this.mob = mob;
    }

    @Override
    public void tick() {
        if (this.lastTick > 0.0F) {
            --this.lastTick;
        }
        if (this.lastTick <= 0.0F) {
            this.searchForButtons();
        }
    }

    private void searchForButtons() {
        List<BlockPos> buttons = this.getButtons();
        if (!buttons.isEmpty()) {
            BlockPos pos = this.getRandomNavigableBlockPos(buttons);
            if (pos != null) {
                this.mob.setTargetPos(pos);
                this.lastTick = 20.0F * 10.0F * Mth.clamp(this.mob.getRandom().nextFloat(), 0.5F, 1.0F);
            }
        }
    }

    @Nullable
    private BlockPos getRandomNavigableBlockPos(List<BlockPos> buttons) {
        while (!buttons.isEmpty()) {
            int index = this.mob.getRandom().nextInt(buttons.size());
            BlockPos pos = buttons.get(index);
            Path path = this.mob.getNavigation().createPath(pos, 1);
            if (path != null && path.canReach()) {
                return pos;
            }
            buttons.remove(index);
        }
        return null;
    }

    private List<BlockPos> getButtons() {
        ArrayList<BlockPos> blocks = new ArrayList<>();
        BlockPos pos = this.mob.blockPosition();
        BlockPos start = pos.offset(-10, -10, -10);
        BlockPos end = pos.offset(10, 10, 10);
        for (BlockPos p : BlockPos.betweenClosed(start, end)) {
            Block block = this.mob.level.getBlockState(p).getBlock();
            if (this.isValidBlock(block)) {
                blocks.add(p.immutable());
            }
        }
        return blocks;
    }

    private boolean isValidBlock(Block block) {
        return block instanceof CopperButtonBlock;
    }

    @Override
    public boolean canUse() {
        return true;
    }
}
