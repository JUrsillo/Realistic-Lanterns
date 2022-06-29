package com.jack.lanternmod.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;

public class RealisticLanternCondition implements ILootCondition {

    private static final RealisticLanternCondition CONDITION = new RealisticLanternCondition();

    @Override
    public LootConditionType getType() {
        return RealisticLanternConditions.DROP_REALISTIC_LANTERN;
    }

    private RealisticLanternCondition() {
    }

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param lootContext the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */

    @Override
    public boolean test(LootContext lootContext) {
        return true;
    }

    public static class Serializer implements ILootSerializer<RealisticLanternCondition> {
        @Override
        public void serialize(JsonObject jsonObject, RealisticLanternCondition lanternCondition,
                              JsonSerializationContext jsonSerializationContext) {
        }

        @Override
        public RealisticLanternCondition deserialize(JsonObject object, JsonDeserializationContext context) {
            return RealisticLanternCondition.CONDITION;
        }
    }
}
