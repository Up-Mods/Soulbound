package dev.upcraft.soulbound.init;

import dev.upcraft.soulbound.Soulbound;
import dev.upcraft.soulbound.enchantment.SoulboundEnchantment;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;

public class SoulboundEnchantments {

	public static final RegistryHandler<Enchantment> ENCHANTMENTS = RegistryHandler.create(Registries.ENCHANTMENT, Soulbound.MODID);

	public static final RegistrySupplier<SoulboundEnchantment> SOULBOUND = ENCHANTMENTS.register("soulbound", SoulboundEnchantment::new);
}
