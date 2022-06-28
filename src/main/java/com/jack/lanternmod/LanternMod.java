package com.jack.lanternmod;

import com.jack.lanternmod.block.RealisticLanternBlock;
import com.jack.lanternmod.config.RealisticLanternConfig;
import com.jack.lanternmod.item.RealisticLanternItem;
import com.jack.lanternmod.item.util.RealisticLanternSoundEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LanternMod.MOD_ID)
public class LanternMod {
    // Directly reference a log4j logger.
    public static final String MOD_ID = "lanternmod";
    private static final Logger LOGGER = LogManager.getLogger();


    public LanternMod() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::setupClient);

        RealisticLanternItem.register(eventBus);
        RealisticLanternBlock.register(eventBus);
        RealisticLanternSoundEvents.register(eventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RealisticLanternConfig.SPEC,
                "lanternmod-common.toml");

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


    private void setupClient(final FMLClientSetupEvent event) {

        RenderTypeLookup.setRenderLayer(RealisticLanternBlock.REALISTIC_LANTERN.get(), RenderType.cutout());

    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
