package dev.upcraft.soulbound;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class SoulboundEnchantment extends Enchantment {

    public SoulboundEnchantment() {
        // Anybody who directly checks the target field is bad
        super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, EquipmentSlot.values());
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinPower(int level) {
        return 15;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Enchantments.VANISHING_CURSE && other != Enchantments.BINDING_CURSE;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return true;
    }
}
