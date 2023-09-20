package dev.upcraft.soulbound.compat.trinkets;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import dev.upcraft.soulbound.api.inventory.SoulboundContainer;
import dev.upcraft.soulbound.api.inventory.SoulboundContainerProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

class DummyTrinketsContainerProvider implements SoulboundContainerProvider<SoulboundContainer> {

	@Override
	public @Nullable SoulboundContainer getContainer(LivingEntity entity) {
		return TrinketsApi.getTrinketComponent(entity).map(DummyContainer::new).orElse(null);
	}

	private record DummyContainer(TrinketComponent component) implements SoulboundContainer {

		@Override
		public SoulboundContainerProvider<? extends SoulboundContainer> getProvider() {
			return TrinketsIntegration.DUMMY_PROVIDER.get();
		}

		@Override
		public LivingEntity getEntity() {
			return component().getEntity();
		}

		@Override
		public void storeToNbt(CompoundTag nbt) {
			// NO-OP
		}

		@Override
		public void restoreFromNbt(CompoundTag nbt, UnaryOperator<ItemStack> itemProcessor) {
			// NO-OP
		}
	}
}
