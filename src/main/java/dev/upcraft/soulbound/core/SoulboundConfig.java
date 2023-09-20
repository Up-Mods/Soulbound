package dev.upcraft.soulbound.core;

import eu.midnightdust.lib.config.MidnightConfig;

public class SoulboundConfig extends MidnightConfig {

	@Entry(min = 0.0F, max = 1.0F)
    public static double soulboundPreservationRate = 1.0F;

	@Entry(min = 0.0F, max = 1.0F)
    public static double soulboundDropChance = 0.0F;

	@Entry
    public static boolean soulboundIsTreasureEnchantment = false;
}
