package dev.upcraft.soulbound.api.event;

import dev.upcraft.soulbound.api.inventory.SoulboundContainer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.item.ItemStack;

public interface SoulboundItemCallback {

	Event<SoulboundItemCallback> EVENT = EventFactory.createArrayBacked(SoulboundItemCallback.class, callbacks -> ctx -> {
		for (SoulboundItemCallback callback : callbacks) {
			TriState value = callback.apply(ctx);
			if (value != TriState.DEFAULT) {
				return value;
			}
		}
		return TriState.DEFAULT;
	});

	TriState apply(Context ctx);

	class Context {

		private final SoulboundContainer container;
		private ItemStack stack;
		private double levelReductionChance;

		public Context(SoulboundContainer container, ItemStack stack, double levelReductionChance) {
			this.container = container;
			this.stack = stack;
			this.levelReductionChance = levelReductionChance;
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

		public double getLevelReductionChance() {
			return levelReductionChance;
		}

		public void setLevelReductionChance(double levelReductionChance) {
			this.levelReductionChance = levelReductionChance;
		}
	}
}
