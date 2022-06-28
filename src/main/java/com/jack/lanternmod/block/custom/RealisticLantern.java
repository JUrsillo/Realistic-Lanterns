package com.jack.lanternmod.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.ToIntFunction;

public class RealisticLantern extends net.minecraft.block.LanternBlock {

    public static final BooleanProperty ON = BooleanProperty.create("on");
    public static final int INITIAL = 50;
    public static final IntegerProperty TICKTIME = IntegerProperty.create("ticktime",0, INITIAL);

    public RealisticLantern(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ON, true).setValue(TICKTIME, INITIAL));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder);
        stateBuilder.add(ON);
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
        if (state.getValue(ON)) {
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
        if(!world.isClientSide && state.getValue(ON)) {
            changeState(state, world, pos);
        }
    }

    private void changeState(BlockState state, World world, BlockPos pos) {

        if(state.getValue(ON)) {
            world.setBlock(pos, state.setValue(ON, Boolean.FALSE).setValue(TICKTIME, 0),  2);
        } else {
            world.setBlock(pos, state.setValue(ON, Boolean.TRUE).setValue(TICKTIME, INITIAL),  2);
        }

    }

    public static ToIntFunction<BlockState> litBlockEmission(int pLightValue) {
        return (state) -> {

            if (state.getValue(ON)) {
                return pLightValue;
            } else {
                return 0;
            }

        };
    }
}