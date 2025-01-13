package dev.upcraft.soulbound.api.inventory;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.UnaryOperator;

public interface SoulboundContainer {

	SoulboundContainerProvider<? extends SoulboundContainer> getProvider();

	LivingEntity getEntity();

	RandomSource getRandom();

	void storeToNbt(CompoundTag nbt);

	void restoreFromNbt(CompoundTag nbt, UnaryOperator<ItemStack> itemProcessor);
}
