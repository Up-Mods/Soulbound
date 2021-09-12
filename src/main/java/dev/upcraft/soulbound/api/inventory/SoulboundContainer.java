package dev.upcraft.soulbound.api.inventory;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.function.UnaryOperator;

public interface SoulboundContainer {

    SoulboundContainerProvider<? extends SoulboundContainer> getProvider();

    LivingEntity getEntity();

    void storeToNbt(NbtCompound nbt);

    void restoreFromNbt(NbtCompound nbt, UnaryOperator<ItemStack> itemProcessor);
}
