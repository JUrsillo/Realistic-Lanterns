package com.jack.lanternmod.worldgen;

import com.jack.lanternmod.block.RealisticLanternBlock;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

import static com.jack.lanternmod.block.custom.RealisticLantern.*;

public class RealisticLanternGen extends Feature<NoFeatureConfig> {

    public static final Feature<NoFeatureConfig> LANTERN_FEATURE = new RealisticLanternGen(NoFeatureConfig.CODEC);
    public static final ConfiguredFeature<?, ?> LANTERN_CONFIG = LANTERN_FEATURE.configured(IFeatureConfig.NONE);

    public RealisticLanternGen(Codec<NoFeatureConfig> p_i231953_1_) {
        super(p_i231953_1_);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator gen, Random random, BlockPos pos, NoFeatureConfig config) {
        int initialX = pos.getX();
        int initialZ = pos.getZ();
        BlockPos.Mutable replacePos = new BlockPos.Mutable();

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                for (int z = 0; z < 16; z++) {
                    replacePos.set(initialX, 0, initialZ).move(x, y, z);
                    if (world.getBlockState(replacePos).getBlock() == Blocks.LANTERN) {
                        world.setBlock(replacePos, RealisticLanternBlock.REALISTIC_LANTERN.get().defaultBlockState().setValue(STATE, ON).setValue(TICKTIME, INITIAL), 2);
                    } else if (world.getBlockState(replacePos).getBlock() == Blocks.SOUL_LANTERN) {
                        world.setBlock(replacePos, RealisticLanternBlock.REALISTIC_LANTERN.get().defaultBlockState().setValue(STATE, ON).setValue(TICKTIME, INITIAL), 2);
                    }
                }
            }
        }
        return true;
    }
}
