package com.jack.lanternmod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class RealisticLanternConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Integer> state_change_time;
    public static final ForgeConfigSpec.ConfigValue<Integer> change_to_flickering;

    static {
        BUILDER.push("Config");
        state_change_time = BUILDER.comment("Total tick time that the lantern will emit light for. Default value is 150." +
                        "The value must be an integer greater than 0")
                .define("State_Change_Time", 10);
        change_to_flickering = BUILDER.comment("Time between the lantern changing from on to flickering. Default value is 75. " +
                        "The value must be an integer greater than 0")
                .define("Change_To_Flickering", 5);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
