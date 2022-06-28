package com.jack.lanternmod.block;

import com.jack.lanternmod.LanternMod;
import com.jack.lanternmod.block.custom.RealisticLantern;
import com.jack.lanternmod.item.RealisticLanternItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class RealisticLanternBlock {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, LanternMod.MOD_ID);

    public static final RegistryObject<Block> REALISTIC_LANTERN = registerBlock("realistic_lantern",
            () -> new RealisticLantern(AbstractBlock.Properties.of(Material.METAL).strength(0.3F).sound(SoundType.LANTERN)
                    .lightLevel(RealisticLantern.litBlockEmission(15)).noOcclusion().randomTicks()
                    .requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, Supplier<T> block) {
        RealisticLanternItem.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(ItemGroup.TAB_MISC)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
