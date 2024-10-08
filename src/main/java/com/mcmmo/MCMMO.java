package com.mcmmo;

import com.mcmmo.registries.BlockExperienceRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCMMO implements ModInitializer {
	public static final String MOD_ID = "mc-mmo";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Commencing Initialization");
		BlockExperienceRegistry.init();
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> ProfessionCommand.register(dispatcher));
	}
}
