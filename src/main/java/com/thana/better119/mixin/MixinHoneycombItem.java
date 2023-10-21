package com.thana.better119.mixin;

import com.thana.better119.common.block.CopperButtonBlock;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoneycombItem.class)
public class MixinHoneycombItem {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> infoReturnable) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        ItemStack itemStack = context.getItemInHand();

        if (state.getBlock() instanceof CopperButtonBlock && !state.getValue(CopperButtonBlock.WAXED) && player.isShiftKeyDown()) {
            level.setBlock(pos, state.setValue(CopperButtonBlock.WAXED, true), Block.UPDATE_ALL_IMMEDIATE);

            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, itemStack);
            }
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
            level.levelEvent(player, 3003, pos, 0);
            infoReturnable.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
        }
    }
}
