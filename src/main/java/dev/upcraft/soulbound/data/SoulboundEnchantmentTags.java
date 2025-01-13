package dev.upcraft.soulbound.data;

import dev.upcraft.soulbound.Soulbound;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class SoulboundEnchantmentTags {

	public static final TagKey<Enchantment> SOULBOUND_INCOMPATIBLE_WITH = TagKey.create(Registries.ENCHANTMENT, Soulbound.id("soulbound_incompatible_with"));
}
