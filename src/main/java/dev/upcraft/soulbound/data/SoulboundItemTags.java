package dev.upcraft.soulbound.data;

import dev.upcraft.soulbound.Soulbound;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SoulboundItemTags {

	public static final TagKey<Item> SOULBOUND_CANNOT_APPLY_TO = TagKey.create(Registries.ITEM, Soulbound.id("soulbound_cannot_apply_to"));
}
