package com.jack.lanternmod.block.custom;

import com.jack.lanternmod.block.RealisticLanternBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

import java.util.function.ToIntFunction;

public class RealisticLantern extends net.minecraft.block.LanternBlock {

    public static final int ON = 1;
    public static final int OFF = 0;
    protected static final IntegerProperty CURRENTSTATE = IntegerProperty.create("currentstate",0,1);


    public RealisticLantern(int currentState) {
        super(Block.Properties.of(Material.HEAVY_METAL).strength(1000).noOcclusion()
                .sound(SoundType.LANTERN).lightLevel(getCurrentLightValue()));
        registerDefaultState(this.defaultBlockState().setValue(CURRENTSTATE, currentState));
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity pEntity, Hand pHand, BlockRayTraceResult blockRayTraceResult) {

        if(pEntity.isOnFire()) {
            changeToOff(world, pos, blockState);

            return ActionResultType.SUCCESS;
        }

        return super.use(blockState, world, pos, pEntity, pHand, blockRayTraceResult);
    }

    @Override
    public void stepOn(World world, BlockPos pos, Entity entity) {
        BlockState state = null;
        state.getBlockState();
        changeToOn(world, pos, state);
        super.stepOn(world, pos, entity);
    }

    public void changeToOff(World world, BlockPos pos, BlockState state) {
        world.setBlock(pos, RealisticLanternBlock.REALISTIC_LANTERN.get().defaultBlockState()
                .setValue(CURRENTSTATE, 0), 1);
    }

    public void changeToOn(World world, BlockPos pos, BlockState state) {
        world.setBlock(pos, RealisticLanternBlock.REALISTIC_LANTERN.get().defaultBlockState()
                .setValue(CURRENTSTATE, 1), 1);
    }


    private static ToIntFunction<BlockState> getCurrentLightValue() {
        return (state) -> {
            if (state.getValue(CURRENTSTATE) == ON) {
                return 14;
            }

            return 0;
        };
    }
}