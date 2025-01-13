package dev.upcraft.soulbound.datagen.common;

import dev.upcraft.soulbound.data.SoulboundEnchantmentTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class SoulboundEnchantmentTagsProvider extends FabricTagProvider.EnchantmentTagProvider {

	public SoulboundEnchantmentTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		getOrCreateRawBuilder(SoulboundEnchantmentTags.SOULBOUND_INCOMPATIBLE_WITH);
	}
}
