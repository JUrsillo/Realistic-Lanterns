package com.jack.lanternmod.item.custom;

import com.google.common.collect.ImmutableMap;
import com.jack.lanternmod.block.RealisticLanternBlock;
import com.jack.lanternmod.block.custom.RealisticLantern;
import com.jack.lanternmod.item.RealisticLanternItem;
import com.jack.lanternmod.item.util.RealisticLanternSoundEvents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

import static com.jack.lanternmod.block.custom.RealisticLantern.*;

public class KeroseneItem extends Item {

    public static final String NAME = "kerosene";

    public KeroseneItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {

        if (!context.getLevel().isClientSide()) {
            PlayerEntity playerEntity = context.getPlayer();
            World world = context.getLevel();
            BlockPos pos = context.getClickedPos();
            BlockState state = context.getLevel().getBlockState(pos);
            Block block = world.getBlockState(pos).getBlock();

            if (block == RealisticLanternBlock.REALISTIC_LANTERN.get()) {
                if (world.getBlockState(pos).getValue(STATE) == 0) {
                    world.setBlock(pos, state.setValue(STATE, ON).setValue(TICKTIME, INITIAL), 2);
                    world.playSound(null, pos, RealisticLanternSoundEvents.LANTERN_FILLING.get(), SoundCategory.BLOCKS, 0.35F, 1);
                    if (playerEntity != null) {
                        context.getItemInHand().hurtAndBreak(1, context.getPlayer(), player -> {
                            player.broadcastBreakEvent(context.getHand());
                        });
                    }

                }
                return ActionResultType.SUCCESS;
            }
        }
        return super.onItemUseFirst(stack, context);

    }
}
