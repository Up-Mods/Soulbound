package dev.upcraft.soulbound.init;

import dev.upcraft.soulbound.api.SoulboundApi;
import dev.upcraft.soulbound.api.inventory.SoulboundContainerProvider;
import dev.upcraft.soulbound.core.inventory.PlayerInventoryContainer;
import dev.upcraft.soulbound.core.inventory.PlayerInventoryContainerProvider;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.Registry;

public class SoulboundContainerProviders {

	public static final RegistryHandler<SoulboundContainerProvider<?>> CONTAINER_PROVIDERS = RegistryHandler.create(SoulboundApi.CONTAINER_PROVIDER_REGISTRY, "soulbound");
	public static final Registry<SoulboundContainerProvider<?>> REGISTRY = CONTAINER_PROVIDERS.createNewRegistry();

	public static final RegistrySupplier<SoulboundContainerProvider<PlayerInventoryContainer>> PLAYER_INVENTORY = CONTAINER_PROVIDERS.register("player_inventory", PlayerInventoryContainerProvider::new);
}
