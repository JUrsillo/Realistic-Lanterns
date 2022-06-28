package com.jack.lanternmod.block.custom;

import com.jack.lanternmod.config.RealisticLanternConfig;
import com.jack.lanternmod.item.util.RealisticLanternSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.ToIntFunction;

public class RealisticLantern extends net.minecraft.block.LanternBlock {

    public static final int INITIAL = RealisticLanternConfig.state_change_time.get();
    public static final int FLICKERING = RealisticLanternConfig.change_to_flickering.get();
    public static final int ON = 3;
    public static final int BRIGHT = 2;
    public static final int DIM = 1;
    public static final int OFF = 0;

    public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 3);
    public static final IntegerProperty TICKTIME = IntegerProperty.create("ticktime", 0, INITIAL);

    public RealisticLantern(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STATE, ON).setValue(TICKTIME, INITIAL));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder);
        stateBuilder.add(STATE);
        stateBuilder.add(TICKTIME);
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        double val = Math.random() * 10;
        if (state.getValue(STATE) > 0 && val < 2) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + 0.58D;
            double d2 = (double) pos.getZ() + 0.5D;
            world.addParticle(ParticleTypes.WHITE_ASH, d0, d1, d2, 0.1D, 0.1D, 0.1D);
        }
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean condition) {
        if (!condition && state.getBlock() != newState.getBlock()) {
            defaultBlockState().updateNeighbourShapes(world, pos, 3);
            world.playSound(null, pos, RealisticLanternSoundEvents.LANTERN_BREAKING.get(), SoundCategory.BLOCKS, 1, 1);
        }
        super.onRemove(state, world, pos, newState, condition);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClientSide && state.getValue(STATE) > 0) {
            int newTime = state.getValue(TICKTIME) - 1;
            if (newTime < 0) {
                newTime = 0;
            }
            if (newTime < FLICKERING) {
                if (state.getValue(STATE) == ON && newTime > 0) {
                    world.setBlock(pos, state.setValue(STATE, BRIGHT).setValue(TICKTIME, newTime), 2);
                    world.playSound(null, pos, RealisticLanternSoundEvents.LANTERN_FLICKERING.get(), SoundCategory.BLOCKS, 1, 1);
                } else if (state.getValue(STATE) == BRIGHT && newTime > 0) {
                    world.setBlock(pos, state.setValue(STATE, DIM).setValue(TICKTIME, newTime), 2);
                    world.playSound(null, pos, RealisticLanternSoundEvents.LANTERN_FLICKERING.get(), SoundCategory.BLOCKS, 1, 1);
                } else if (state.getValue(STATE) == DIM && newTime > 0) {
                    world.setBlock(pos, state.setValue(STATE, BRIGHT).setValue(TICKTIME, newTime), 2);
                    world.playSound(null, pos, RealisticLanternSoundEvents.LANTERN_FLICKERING.get(), SoundCategory.BLOCKS, 1, 1);
                } else {
                    world.setBlock(pos, state.setValue(STATE, OFF).setValue(TICKTIME, newTime), 2);
                    world.playSound(null, pos, RealisticLanternSoundEvents.LANTERN_BREAKING.get(), SoundCategory.BLOCKS, 1, 1);
                }
            } else {
                world.setBlock(pos, state.setValue(STATE, state.getValue(STATE)).setValue(TICKTIME, newTime), 2);
            }
        }
    }

    public static ToIntFunction<BlockState> litBlockEmission(int pLightValue) {
        return (state) -> {
            if (state.getValue(STATE) > 0) {
                if (state.getValue(STATE) == ON) {
                    return 15;
                } else if (state.getValue(STATE) == BRIGHT) {
                    return 12;
                } else {
                    return 8;
                }
            } else {
                return 0;
            }
        };
    }
}