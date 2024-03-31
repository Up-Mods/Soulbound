package dev.upcraft.soulbound.core;

import com.teamresourceful.resourcefulconfig.api.annotations.Config;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigOption;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;
import dev.upcraft.soulbound.Soulbound;

@Config(Soulbound.MODID)
@ConfigInfo(
	title = "Soulbound",
	titleTranslation = "config.soulbound.title",
	description = "An enchantment that will be retain items upon death",
	links = {
		@ConfigInfo.Link(
			value = "https://modrinth.com/mod/9QyjzbTh",
			icon = "modrinth",
			text = "Modrinth"
		),
		@ConfigInfo.Link(
			value = "https://www.curseforge.com/projects/618682",
			icon = "curseforge",
			text = "Curseforge"
		),
		@ConfigInfo.Link(
			value = "https://github.com/Up-Mods/Soulbound",
			icon = "github",
			text = "GitHub"
		)
	}
)
public final class SoulboundConfig {

	@ConfigOption.Range(min = 0.0F, max = 1.0F)
	@ConfigEntry(id = "soulboundPreservationRate", type = EntryType.DOUBLE, translation = "config.soulbound.soulboundPreservationRate")
	public static double soulboundPreservationRate = 1.0F;

	@ConfigOption.Range(min = 0.0F, max = 1.0F)
	@ConfigEntry(id = "soulboundDropChance", type = EntryType.DOUBLE, translation = "config.soulbound.soulboundDropChance")
	public static double soulboundDropChance = 0.0F;

	@ConfigEntry(id = "soulboundIsTreasureEnchantment", type = EntryType.BOOLEAN, translation = "config.soulbound.soulboundIsTreasureEnchantment")
	public static boolean soulboundIsTreasureEnchantment = false;
}
