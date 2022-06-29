package com.jack.lanternmod.conditions;

import com.jack.lanternmod.LanternMod;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class RealisticLanternConditions {

    public static final LootConditionType DROP_REALISTIC_LANTERN = register("drop_realistic_lantern", new RealisticLanternCondition.Serializer());

    public static void init() {

    }

    public static LootConditionType register(String resource_name, ILootSerializer<? extends ILootCondition> loot_serializer) {
        return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(LanternMod.MOD_ID + ":" + resource_name), new LootConditionType(loot_serializer));
    }

}
