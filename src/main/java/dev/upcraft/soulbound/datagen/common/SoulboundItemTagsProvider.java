package dev.upcraft.soulbound.datagen.common;

import dev.upcraft.soulbound.data.SoulboundItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class SoulboundItemTagsProvider extends FabricTagProvider.ItemTagProvider {

	public SoulboundItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		getOrCreateRawBuilder(SoulboundItemTags.SOULBOUND_CANNOT_APPLY_TO);
	}
}
