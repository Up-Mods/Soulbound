package dev.upcraft.soulbound.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.upcraft.soulbound.api.SlottedItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SoulboundPersistentState extends PersistentState {

    private static final String PERSISTENT_ID = "soulbound_persisted_items";
    private final Map<UUID, List<SlottedItem>> persistedItems = Maps.newHashMap();

    public static SoulboundPersistentState get(MinecraftServer server) {
        return server.getOverworld().getPersistentStateManager().getOrCreate(SoulboundPersistentState::fromNbt, SoulboundPersistentState::new, PERSISTENT_ID);
    }

    private SoulboundPersistentState() {
        super();
    }

    public void storePlayer(PlayerEntity player, List<SlottedItem> items) {
        this.persistedItems.put(player.getGameProfile().getId(), items);
        markDirty();
    }

    public List<SlottedItem> restorePlayer(PlayerEntity player) {
        List<SlottedItem> items = persistedItems.getOrDefault(player.getGameProfile().getId(), Collections.emptyList());
        persistedItems.remove(player.getGameProfile().getId());
        markDirty();
        return items;
    }

    private static SoulboundPersistentState fromNbt(NbtCompound tag) {
        SoulboundPersistentState value = new SoulboundPersistentState();
        NbtList playerTags = tag.getList("playerTags", NbtElement.COMPOUND_TYPE);
        for (int j = 0; j < playerTags.size(); j++) {
            NbtCompound playerTag = playerTags.getCompound(j);
            UUID uuid = playerTag.getUuid("uuid");
            NbtList items = playerTag.getList("items", NbtElement.COMPOUND_TYPE);
            List<SlottedItem> slotted = Lists.newArrayList();
            for (int i = 0; i < items.size(); i++) {
                NbtCompound item = items.getCompound(i);
                Identifier id = new Identifier(item.getString("id"));
                ItemStack stack = ItemStack.fromNbt(item.getCompound("stack"));
                int slot = item.getInt("slotId");
                slotted.add(new SlottedItem(id, stack, slot));
            }
            value.persistedItems.put(uuid, slotted);
        }
        return value;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        NbtList playerTags = new NbtList();
        persistedItems.forEach((uuid, items) -> {
            NbtCompound playerTag = new NbtCompound();
            playerTag.putUuid("uuid", uuid);
            NbtList itemsList = new NbtList();
            items.forEach(slotted -> {
                NbtCompound itemTag = new NbtCompound();
                itemTag.putString("id", slotted.containerId().toString());
                itemTag.put("stack", slotted.stack().writeNbt(new NbtCompound()));
                itemTag.putInt("slotId", slotted.slotId());
                itemsList.add(itemTag);
            });
            playerTag.put("items", itemsList);

            playerTags.add(playerTag);
        });
        tag.put("playerTags", playerTags);
        return tag;
    }
}
