package dev.upcraft.soulbound.init;

import dev.upcraft.soulbound.Soulbound;
import dev.upcraft.soulbound.api.inventory.SoulboundContainerProvider;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class SoulboundRegistries {

	public static final ResourceKey<Registry<SoulboundContainerProvider<?>>> CONTAINER_PROVIDERS = ResourceKey.createRegistryKey(Soulbound.id("container_provider"));
	public static final Registry<SoulboundContainerProvider<?>> CONTAINER_PROVIDERS_REGISTRY = FabricRegistryBuilder.createSimple(CONTAINER_PROVIDERS).attribute(RegistryAttribute.PERSISTED).buildAndRegister();
}
