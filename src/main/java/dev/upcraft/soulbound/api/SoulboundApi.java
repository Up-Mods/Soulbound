package dev.upcraft.soulbound.api;

import dev.upcraft.soulbound.Soulbound;
import dev.upcraft.soulbound.api.inventory.SoulboundContainerProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public interface SoulboundApi {

	ResourceKey<Registry<SoulboundContainerProvider<?>>> CONTAINER_PROVIDER_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(Soulbound.MODID, "container_provider"));
}
