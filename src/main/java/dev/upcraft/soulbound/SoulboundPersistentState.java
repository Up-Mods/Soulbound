package dev.upcraft.soulbound;

import com.google.common.collect.Maps;
import dev.upcraft.soulbound.api.inventory.SoulboundContainer;
import dev.upcraft.soulbound.init.SoulboundRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SoulboundPersistentState extends SavedData {

	private static final String PERSISTENT_ID = "soulbound_persisted_items";
	private final Map<UUID, Map<ResourceLocation, CompoundTag>> persistedData = Maps.newHashMap();

	public static SoulboundPersistentState get(ServerPlayer player) {
		return player.server.overworld().getDataStorage().computeIfAbsent(SoulboundPersistentState::fromNbt, SoulboundPersistentState::new, PERSISTENT_ID);
	}

	private static SoulboundPersistentState fromNbt(CompoundTag tag) {
		SoulboundPersistentState value = new SoulboundPersistentState();
		ListTag playerTags = tag.getList("players", Tag.TAG_COMPOUND);
		for (int j = 0; j < playerTags.size(); j++) {
			CompoundTag playerTag = playerTags.getCompound(j);
			UUID uuid = playerTag.getUUID("uuid");
			Map<ResourceLocation, CompoundTag> map = new HashMap<>();
			ListTag inventories = playerTag.getList("inventories", Tag.TAG_COMPOUND);
			for (int i = 0; i < inventories.size(); i++) {
				CompoundTag inv = inventories.getCompound(i);
				ResourceLocation id = new ResourceLocation(inv.getString("id"));
				if (SoulboundRegistries.CONTAINER_PROVIDERS_REGISTRY.containsKey(id)) {
					map.put(id, inv.getCompound("data"));
				} else {
					Soulbound.LOGGER.error("unable to read data for unknown provider {} for player {}", id, uuid);
				}
			}
			value.persistedData.put(uuid, map);
		}
		return value;
	}

	private SoulboundPersistentState() {
		super();
	}

	public void storePlayer(ServerPlayer player) {
		Map<ResourceLocation, CompoundTag> data = new HashMap<>();
		SoulboundRegistries.CONTAINER_PROVIDERS_REGISTRY.entrySet().forEach(entry -> {
			var id = entry.getKey().location();
			var provider = entry.getValue();
			SoulboundContainer container = provider.getContainer(player);
			if (container != null) {
				CompoundTag containerData = new CompoundTag();
				container.storeToNbt(containerData);
				if (!containerData.isEmpty()) {
					data.put(id, containerData);
				}
			}
		});
		this.persistedData.put(player.getGameProfile().getId(), data);
		setDirty();
	}

	public Map<ResourceLocation, CompoundTag> restorePlayer(ServerPlayer player) {
		Map<ResourceLocation, CompoundTag> value = persistedData.remove(player.getGameProfile().getId());
		if (value != null) {
			setDirty();
		}
		return value;
	}

	@Override
	public CompoundTag save(CompoundTag tag) {
		ListTag playerTags = new ListTag();
		persistedData.forEach((uuid, map) -> {
			CompoundTag playerTag = new CompoundTag();
			playerTag.putUUID("uuid", uuid);
			ListTag inventories = new ListTag();
			map.forEach((identifier, data) -> {
				CompoundTag inv = new CompoundTag();
				inv.putString("id", identifier.toString());
				inv.put("data", data);
				inventories.add(inv);
			});
			playerTag.put("inventories", inventories);
			playerTags.add(playerTag);
		});
		tag.put("players", playerTags);
		return tag;
	}
}
