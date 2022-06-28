package com.jack.lanternmod.block.custom;

import com.jack.lanternmod.item.util.RealisticLanternSoundEvents;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.lwjgl.system.CallbackI;

import java.util.Random;
import java.util.function.ToIntFunction;

public class RealisticLantern extends net.minecraft.block.LanternBlock {

    public static final int INITIAL = 50;
    public static final int ON = 3;
    public static final int BRIGHT = 2;
    public static final int DIM = 1;
    public static final int OFF = 0;

    public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 3);
    public static final IntegerProperty TICKTIME = IntegerProperty.create("ticktime",0, INITIAL);

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
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand pHand, BlockRayTraceResult blockRayTraceResult) {

        if(!world.isClientSide) {

            changeState(state, world, pos);

        }

        return ActionResultType.sidedSuccess(world.isClientSide);
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.getValue(STATE) > 0) {
            for (Direction direction : Direction.values()) {
                BlockPos blockpos = pos.relative(direction);
                if (!world.getBlockState(blockpos).isSolidRender(world, blockpos)) {
                    Direction.Axis direction$axis = direction.getAxis();
                    double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double) direction.getStepX() : (double) random.nextFloat();
                    double d2 = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double) direction.getStepY() : (double) random.nextFloat();
                    double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double) direction.getStepZ() : (double) random.nextFloat();
                    world.addParticle(ParticleTypes.FLAME, (double) pos.getX() + d1, (double) pos.getY() + d2, (double) pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean condition) {
        if (!condition && state.getBlock() != newState.getBlock()) {
            defaultBlockState().updateNeighbourShapes(world, pos, 3);
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
            if (newTime < 30) {
                if (state.getValue(STATE) == ON && newTime > 0) {
                    world.setBlock(pos, state.setValue(STATE, BRIGHT).setValue(TICKTIME, newTime), 2);
                    world.playSound(null, pos, RealisticLanternSoundEvents.LANTERN_FLICKERING.get(), SoundCategory.BLOCKS, 1, 1);
                } else if (state.getValue(STATE) == BRIGHT && newTime > 0) {
                    world.setBlock(pos, state.setValue(STATE, DIM).setValue(TICKTIME, newTime), 2);
                    world.playSound(null, pos, RealisticLanternSoundEvents.LANTERN_FLICKERING.get(), SoundCategory.BLOCKS, 1, 1);
                } else if (state.getValue(STATE) == DIM && newTime > 0){
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

    private void changeState(BlockState state, World world, BlockPos pos) {
        if(state.getValue(STATE) > 0) {
            world.setBlock(pos, state.setValue(STATE, 0).setValue(TICKTIME, 0),  2);
        } else {
            world.setBlock(pos, state.setValue(STATE, ON).setValue(TICKTIME, INITIAL),  2);
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