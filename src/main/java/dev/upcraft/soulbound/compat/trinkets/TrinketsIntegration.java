package dev.upcraft.soulbound.compat.trinkets;

import dev.emi.trinkets.api.TrinketEnums;
import dev.emi.trinkets.api.event.TrinketDropCallback;
import dev.upcraft.soulbound.api.inventory.SoulboundContainer;
import dev.upcraft.soulbound.api.inventory.SoulboundContainerProvider;
import dev.upcraft.soulbound.SoulboundHooks;
import dev.upcraft.soulbound.init.SoulboundContainerProviders;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import org.jetbrains.annotations.Nullable;

public class TrinketsIntegration {

	public static RegistrySupplier<SoulboundContainerProvider<SoulboundContainer>> DUMMY_PROVIDER;

	public static void load() {
		DUMMY_PROVIDER = SoulboundContainerProviders.CONTAINER_PROVIDERS.register("trinkets_integration", DummyTrinketsContainerProvider::new);

		TrinketDropCallback.EVENT.register((rule, stack, ref, entity) -> {
			if (SoulboundHooks.shouldKeepStack(stack, entity.getRandom())) {
				@Nullable SoulboundContainer container = DUMMY_PROVIDER.get().getContainer(entity);
				if (container != null && !SoulboundHooks.createItemProcessor(container).apply(stack).isEmpty()) {
					return TrinketEnums.DropRule.KEEP;
				}
			}
			return TrinketEnums.DropRule.DEFAULT;
		});
	}

}
