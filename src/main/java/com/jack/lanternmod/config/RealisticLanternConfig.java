package com.jack.lanternmod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class RealisticLanternConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Integer> state_change_time;

    static {
        BUILDER.push("Config");
        state_change_time = BUILDER.comment("Time between state changes. Default value is 6000")
                        .define("State_Change_Time", 6000);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
