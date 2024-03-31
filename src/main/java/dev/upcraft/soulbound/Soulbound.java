package dev.upcraft.soulbound;

import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import dev.upcraft.soulbound.api.inventory.SoulboundContainer;
import dev.upcraft.soulbound.compat.SoulboundCompat;
import dev.upcraft.soulbound.compat.trinkets.TrinketsIntegration;
import dev.upcraft.soulbound.compat.universalgraves.UniversalGravesCompat;
import dev.upcraft.soulbound.core.SoulboundConfig;
import dev.upcraft.soulbound.core.SoulboundHooks;
import dev.upcraft.soulbound.core.SoulboundPersistentState;
import dev.upcraft.soulbound.init.SoulboundContainerProviders;
import dev.upcraft.soulbound.init.SoulboundEnchantments;
import dev.upcraft.sparkweave.api.platform.services.RegistryService;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.entity.event.api.ServerPlayerEntityCopyCallback;

import java.util.Map;


public class Soulbound implements ModInitializer {

	public static final String MODID = "soulbound";
	public static final Logger LOGGER = LogManager.getLogger("Soulbound");
	public static final Configurator CONFIGURATOR = new Configurator(MODID);

	@Override
	public void onInitialize(ModContainer mod) {
		CONFIGURATOR.register(SoulboundConfig.class);

		// this must run before the registry handlers
		SoulboundCompat.TRINKETS.ifEnabled(() -> TrinketsIntegration::load);
		SoulboundCompat.UNIVERSAL_GRAVES.ifEnabled(() -> UniversalGravesCompat::load);

		var service = RegistryService.get();
		SoulboundEnchantments.ENCHANTMENTS.accept(service);
		SoulboundContainerProviders.CONTAINER_PROVIDERS.accept(service);

		ServerPlayerEntityCopyCallback.EVENT.register((copy, original, wasDeath) -> {
			if (wasDeath) {
				SoulboundPersistentState persistentState = SoulboundPersistentState.get(copy);
				Map<ResourceLocation, CompoundTag> saveData = persistentState.restorePlayer(original);
				if (saveData != null) {
					saveData.forEach((id, data) -> SoulboundContainerProviders.REGISTRY.getOptional(id).ifPresentOrElse(provider -> {
						@Nullable SoulboundContainer container = provider.getContainer(copy);
						if (container != null) {
							container.restoreFromNbt(data, SoulboundHooks.createItemProcessor(container));
						} else {
							Soulbound.LOGGER.warn("tried to deserialize null container for provider {}", id);
						}
					}, () -> Soulbound.LOGGER.error("tried to deserialize unknown provider {} for player {}", id, copy.getGameProfile().getName())));
				}
			}
		});
	}
}
