package dev.upcraft.soulbound;

import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import dev.upcraft.soulbound.api.inventory.SoulboundContainer;
import dev.upcraft.soulbound.compat.SoulboundCompat;
import dev.upcraft.soulbound.compat.trinkets.TrinketsIntegration;
import dev.upcraft.soulbound.compat.universalgraves.UniversalGravesCompat;
import dev.upcraft.soulbound.init.SoulboundContainerProviders;
import dev.upcraft.soulbound.init.SoulboundEnchantments;
import dev.upcraft.soulbound.init.SoulboundRegistries;
import dev.upcraft.sparkweave.api.registry.RegistryService;
import dev.upcraft.sparkweave.api.util.logging.SparkweaveLoggerFactory;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.Map;


public class Soulbound implements ModInitializer {

	public static final String MODID = "soulbound";
	public static final Logger LOGGER = SparkweaveLoggerFactory.getLogger();
	public static final Configurator CONFIGURATOR = new Configurator();

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MODID, path);
	}

	@Override
	public void onInitialize() {
		CONFIGURATOR.registerConfig(SoulboundConfig.class);

		// this must run before the registry handlers
		SoulboundCompat.TRINKETS.ifEnabled(() -> TrinketsIntegration::load);
		SoulboundCompat.UNIVERSAL_GRAVES.ifEnabled(() -> UniversalGravesCompat::load);

		var service = RegistryService.get();
		SoulboundEnchantments.ENCHANTMENTS.accept(service);
		SoulboundContainerProviders.CONTAINER_PROVIDERS.accept(service);


		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, stillAlive) -> {
			if (!stillAlive) {
				SoulboundPersistentState persistentState = SoulboundPersistentState.get(newPlayer);
				Map<ResourceLocation, CompoundTag> saveData = persistentState.restorePlayer(oldPlayer);
				if (saveData != null) {
					saveData.forEach((id, data) -> SoulboundRegistries.CONTAINER_PROVIDERS_REGISTRY.getOptional(id).ifPresentOrElse(provider -> {
						@Nullable SoulboundContainer container = provider.getContainer(newPlayer);
						if (container != null) {
							container.restoreFromNbt(data, SoulboundHooks.createItemProcessor(container));
						} else {
							Soulbound.LOGGER.warn("tried to deserialize null container for provider {}", id);
						}
					}, () -> Soulbound.LOGGER.error("tried to deserialize unknown provider {} for player {}", id, newPlayer.getGameProfile().getName())));
				}
			}
		});
	}
}
