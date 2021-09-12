package dev.upcraft.soulbound.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public record SlottedItem(Identifier containerId, ItemStack stack, int slotId) {
}
