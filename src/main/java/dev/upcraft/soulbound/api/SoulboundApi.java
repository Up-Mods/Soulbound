package dev.upcraft.soulbound.api;

import dev.upcraft.soulbound.Soulbound;
import dev.upcraft.soulbound.api.inventory.SoulboundContainerProvider;
import net.minecraft.util.registry.Registry;

public interface SoulboundApi {

    Registry<SoulboundContainerProvider<?>> CONTAINERS = Soulbound.CONTAINERS;
}
