package dev.upcraft.soulbound.enchantment;


import dev.upcraft.soulbound.SoulboundConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class SoulboundEnchantment extends Enchantment {

	public SoulboundEnchantment() {
		// Anybody who directly checks the target field is bad
		super(Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.values());
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public int getMinCost(int level) {
		return 15;
	}

	@Override
	public int getMaxCost(int level) {
		return super.getMaxCost(level) + 50;
	}

	@Override
	protected boolean checkCompatibility(Enchantment other) {
		return super.checkCompatibility(other) && other != Enchantments.VANISHING_CURSE && other != Enchantments.BINDING_CURSE;
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		return true;
	}

	@Override
	public boolean isTreasureOnly() {
		return SoulboundConfig.soulboundIsTreasureEnchantment;
	}
}
