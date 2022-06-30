package com.jack.lanternmod.item;

import com.jack.lanternmod.LanternMod;
import com.jack.lanternmod.item.custom.KeroseneItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RealisticLanternItem {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LanternMod.MOD_ID);

    public static final RegistryObject<Item> KEROSENE = ITEMS.register("kerosene",
            () -> new KeroseneItem(new Item.Properties().tab(ItemGroup.TAB_MISC).durability(10)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
