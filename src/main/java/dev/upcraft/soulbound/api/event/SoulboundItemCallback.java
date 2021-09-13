package dev.upcraft.soulbound.api.event;

import dev.upcraft.soulbound.api.inventory.SoulboundContainer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.item.ItemStack;

public interface SoulboundItemCallback {

    Event<SoulboundItemCallback> EVENT = EventFactory.createArrayBacked(SoulboundItemCallback.class, callbacks -> ctx -> {
        for (SoulboundItemCallback callback : callbacks) {
            TriState value = callback.apply(ctx);
            if(value != TriState.DEFAULT) {
                return value;
            }
        }
        return TriState.DEFAULT;
    });

    TriState apply(Context ctx);

    class Context {

        final SoulboundContainer container;
        ItemStack stack;
        double levelPreservationChance;

        public Context(SoulboundContainer container, ItemStack stack, double levelPreservationChance) {
            this.container = container;
            this.stack = stack;
            this.levelPreservationChance = levelPreservationChance;
        }

        public SoulboundContainer getContainer() {
            return container;
        }

        public ItemStack getStack() {
            return stack;
        }

        public void setStack(ItemStack stack) {
            this.stack = stack;
        }

        public double getLevelPreservationChance() {
            return levelPreservationChance;
        }

        public void setLevelPreservationChance(double levelPreservationChance) {
            this.levelPreservationChance = levelPreservationChance;
        }
    }
}
