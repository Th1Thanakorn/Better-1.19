package com.thana.better119.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.StoneButtonBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

public class CopperButtonBlock extends StoneButtonBlock implements IWeatheringCopper {

    private final WeatheringCopper.WeatherState weatherState;

    public CopperButtonBlock(WeatheringCopper.WeatherState weatherState, Properties properties) {
        super(properties);
        this.weatherState = weatherState;
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        this.onRandomTick(state, level, pos, randomSource);
    }

    public boolean isRandomlyTicking(BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    public WeatheringCopper.WeatherState getAge() {
        return this.weatherState;
    }

    @Override
    public int getPressDuration() {
        return 20 + this.weatherState.ordinal() * 7;
    }
}
