package com.jack.lanternmod.item;

import com.jack.lanternmod.LanternMod;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RealisticLanternItem {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LanternMod.MOD_ID);


    public static void register(IEventBus eventBus) { ITEMS.register(eventBus); }
}
