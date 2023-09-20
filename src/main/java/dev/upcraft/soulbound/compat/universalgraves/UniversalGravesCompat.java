package dev.upcraft.soulbound.compat.universalgraves;

import dev.upcraft.soulbound.init.SoulboundEnchantments;
import eu.pb4.graves.GravesApi;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class UniversalGravesCompat {

	public static void load() {
		GravesApi.ADD_ITEM_EVENT.register((player, stack) -> EnchantmentHelper.getItemEnchantmentLevel(SoulboundEnchantments.SOULBOUND.get(), stack) > 0 ? InteractionResult.FAIL : InteractionResult.PASS);
	}
}
