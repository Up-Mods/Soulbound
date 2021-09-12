package dev.upcraft.soulbound.compat;

import dev.emi.trinkets.api.TrinketsApi;
import dev.upcraft.soulbound.api.SlottedItem;
import dev.upcraft.soulbound.api.SoulboundContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class TrinketsIntegration implements SoulboundContainer {

    static {
        SoulboundContainer.CONTAINERS.put(new Identifier("trinkets", "trinkets"), new TrinketsIntegration());
    }

    @Override
    public List<ItemStack> getContainerStacks(PlayerEntity player) {
        TrinketsApi.getTrinketComponent(player).orElseThrow(() -> new IllegalStateException("player without trinkets component: " + player.getEntityName()));
        Inventory inventory = TrinketsApi.getTrinketsInventory(player);
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++)
            stacks.add(inventory.getStack(i));
        return stacks;
    }

    @Override
    public void replaceItem(PlayerEntity player, SlottedItem item) {
        TrinketsApi.getTrinketsInventory(player).setStack(item.slotId(), item.stack());
    }

    @Override
    public void removeStoredItem(PlayerEntity player, int slot) {
        TrinketsApi.getTrinketsInventory(player).setStack(slot, ItemStack.EMPTY);
    }
}
