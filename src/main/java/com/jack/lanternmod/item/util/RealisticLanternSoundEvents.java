package com.jack.lanternmod.item.util;

import com.jack.lanternmod.LanternMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RealisticLanternSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LanternMod.MOD_ID);

    public static final RegistryObject<SoundEvent> LANTERN_BREAKING =
            registerSoundEvent("lantern_breaking");
    public static final RegistryObject<SoundEvent> LANTERN_FLICKERING =
            registerSoundEvent("lantern_flickering");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(LanternMod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) { SOUND_EVENTS.register(eventBus); }
}
