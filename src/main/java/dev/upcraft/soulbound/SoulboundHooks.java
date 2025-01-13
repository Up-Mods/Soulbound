package dev.upcraft.soulbound;

import dev.upcraft.soulbound.api.event.SoulboundItemCallback;
import dev.upcraft.soulbound.api.inventory.SoulboundContainer;
import dev.upcraft.soulbound.init.SoulboundEnchantments;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.function.UnaryOperator;

public class SoulboundHooks {

	public static UnaryOperator<ItemStack> createItemProcessor(SoulboundContainer container) {
		RandomSource random = container.getEntity().getRandom();
		return stack -> {
			SoulboundItemCallback.Context ctx = new SoulboundItemCallback.Context(container, stack.copy(), SoulboundConfig.levelReductionRate);
			if (SoulboundItemCallback.EVENT.invoker().apply(ctx) != TriState.FALSE) {
				ItemStack itemStack = ctx.getStack();

				if (random.nextDouble() < ctx.getLevelReductionChance()) {
					var ench = SoulboundEnchantments.SOULBOUND.get();
					var map = EnchantmentHelper.getEnchantments(itemStack);
					int newLevel = map.getOrDefault(ench, 0) - 1;
					if (newLevel > 0) {
						map.put(ench, newLevel);
					} else {
						map.remove(ench);
					}
					EnchantmentHelper.setEnchantments(map, itemStack);
				}

				return itemStack;
			}

			return ItemStack.EMPTY;
		};
	}

	public static ListTag getFilteredItemList(NonNullList<ItemStack> items, RandomSource random) {
		ListTag list = new ListTag();
		for (int i = 0; i < items.size(); i++) {
			ItemStack stack = items.get(i);
			if (shouldKeepStack(stack, random)) {
				CompoundTag tag = new CompoundTag();
				tag.put("item", stack.save(new CompoundTag()));
				tag.putInt("slot", i);
				list.add(tag);
				items.set(i, ItemStack.EMPTY);
			}
		}
		return list;
	}

	public static boolean shouldKeepStack(ItemStack stack, RandomSource random) {
		return EnchantmentHelper.getItemEnchantmentLevel(SoulboundEnchantments.SOULBOUND.get(), stack) > 0 && random.nextDouble() < SoulboundConfig.soulboundSuccessChance;
	}

	public static Int2ObjectMap<ItemStack> readItemList(ListTag list) {
		Int2ObjectMap<ItemStack> value = new Int2ObjectOpenHashMap<>();
		for (int i = 0; i < list.size(); i++) {
			CompoundTag tag = list.getCompound(i);
			ItemStack stack = ItemStack.of(tag.getCompound("item"));
			value.put(tag.getInt("slot"), stack);
		}
		return value;
	}

	public static void processPlayerDrops(Player player, NonNullList<ItemStack> targetInv, Int2ObjectMap<ItemStack> items, UnaryOperator<ItemStack> itemProcessor) {
		items.int2ObjectEntrySet().forEach(e -> {
			ItemStack stack = itemProcessor.apply(e.getValue().copy());
			if (stack.isEmpty()) {
				ItemStack drop = e.getValue();
				drop.setCount(1);
				ItemEntity itemEntity = player.drop(drop, false);
				if (itemEntity != null) {
					itemEntity.makeFakeItem();
				}
			} else {
				int slot = e.getIntKey();
				if (slot > 0 && slot < targetInv.size() && targetInv.get(slot).isEmpty()) {
					targetInv.set(slot, stack);
				} else {
					addItemToPlayer(player, stack);
				}
			}
		});
	}

	public static void addItemToPlayer(Player player, ItemStack stack) {
		boolean inserted = player.getInventory().add(stack);
		ItemEntity itemEntity;
		if (inserted && stack.isEmpty()) {
			stack.setCount(1);
			itemEntity = player.drop(stack, false);
			if (itemEntity != null) {
				itemEntity.makeFakeItem();
			}

			player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
			player.containerMenu.broadcastChanges();
		} else {
			itemEntity = player.drop(stack, false);
			if (itemEntity != null) {
				itemEntity.setNoPickUpDelay();
				itemEntity.setThrower(player.getUUID());
			}
		}
	}

}
