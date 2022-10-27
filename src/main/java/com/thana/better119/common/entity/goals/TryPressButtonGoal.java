package com.thana.better119.common.entity.goals;

import com.thana.better119.common.block.CopperButtonBlock;
import com.thana.better119.common.entity.CopperGolemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TryPressButtonGoal extends Goal {

    private final CopperGolemEntity mob;
    private final Level level;
    private float lastPressedTick;

    public TryPressButtonGoal(CopperGolemEntity mob) {
        this.mob = mob;
        this.level = mob.level;
    }

    @Override
    public void start() {
        BlockPos targetPos = this.mob.getTargetPos();
        this.mob.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 0.4F);
    }

    @Override
    public boolean canUse() {
        return this.mob.getTargetPos() != null && this.mob.getRandom().nextInt(100) > 75;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.canUse();
    }

    @Override
    public void tick() {
        if (this.lastPressedTick > 0.0F) {
            this.lastPressedTick--;
        }
        BlockPos pos = this.mob.getTargetPos();
        if (pos != null && this.canClickTargetButton(pos) && this.lastPressedTick <= 20.0F) {
            BlockState state = this.level.getBlockState(pos);
            if (!(state.getBlock() instanceof CopperButtonBlock)) {
                return;
            }
            CopperButtonBlock block = (CopperButtonBlock) state.getBlock();
            block.use(state, this.level, pos, null, InteractionHand.MAIN_HAND, null);
            this.lastPressedTick = 100.0F;
            this.mob.resetTargetPos();
        }
    }

    private boolean canClickTargetButton(BlockPos pos) {
        int distance = this.mob.blockPosition().distManhattan(pos);
        return distance <= 1;
    }
}
