package dev.upcraft.soulbound;

import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.DoubleRange;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

@Config(Soulbound.MODID)
public final class SoulboundConfig {

	@DoubleRange(min = 0.0F, max = 1.0F)
	@ConfigEntry(id = "soulbound_reduction_rate", type = EntryType.DOUBLE, translation = "config.soulbound.soulbound_reduction_rate")
	public static double levelReductionRate = 0.0F;

	@DoubleRange(min = 0.0F, max = 1.0F)
	@ConfigEntry(id = "soulbound_success_chance", type = EntryType.DOUBLE, translation = "config.soulbound.soulbound_success_chance")
	public static double soulboundSuccessChance = 1.0F;

	@ConfigEntry(id = "soulbound_is_treasure_enchantment", type = EntryType.BOOLEAN, translation = "config.soulbound.soulbound_is_treasure_enchantment")
	public static boolean soulboundIsTreasureEnchantment = false;
}
