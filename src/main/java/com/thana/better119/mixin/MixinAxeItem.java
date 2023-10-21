package com.thana.better119.mixin;

import com.thana.better119.common.block.BlockInit;
import com.thana.better119.common.block.CopperButtonBlock;
import com.thana.better119.common.block.IWeatheringCopper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public class MixinAxeItem {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> infoReturnable) {
        InteractionHand hand = context.getHand();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(pos);
        ItemStack itemStack = context.getItemInHand();

        // Wax Off
        if (state.getBlock() instanceof CopperButtonBlock && state.getValue(CopperButtonBlock.WAXED) && player.isShiftKeyDown()) {
            level.setBlock(pos, state.setValue(CopperButtonBlock.WAXED, false), Block.UPDATE_ALL_IMMEDIATE);

            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, pos, itemStack);
            }
            if (!player.isCreative()) {
                itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            }
            level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3004, pos, 0);
            infoReturnable.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
        }

        // Oxidize State
        if (state.getBlock() instanceof CopperButtonBlock && state.getBlock() != BlockInit.COPPER_BUTTON.get() && player.isShiftKeyDown()) {
            BlockState prevState = IWeatheringCopper.getPrevious(state).get();
            level.setBlock(pos, prevState, Block.UPDATE_ALL_IMMEDIATE);

            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, pos, itemStack);
            }
            if (!player.isCreative()) {
                itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            }
            level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3005, pos, 0);
            infoReturnable.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
        }
    }
}
