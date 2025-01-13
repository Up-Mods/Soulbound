package dev.upcraft.soulbound.api;

import dev.upcraft.soulbound.api.inventory.SoulboundContainerProvider;
import dev.upcraft.soulbound.init.SoulboundRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface SoulboundApi {

	ResourceKey<Registry<SoulboundContainerProvider<?>>> CONTAINER_PROVIDER_REGISTRY = SoulboundRegistries.CONTAINER_PROVIDERS;
}
