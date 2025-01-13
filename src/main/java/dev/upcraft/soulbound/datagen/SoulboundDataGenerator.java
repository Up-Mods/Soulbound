package dev.upcraft.soulbound.datagen;

import dev.upcraft.soulbound.datagen.client.SoulboundChineseLanguageProvider;
import dev.upcraft.soulbound.datagen.client.SoulboundEnglishLanguageProvider;
import dev.upcraft.soulbound.datagen.common.SoulboundEnchantmentTagsProvider;
import dev.upcraft.soulbound.datagen.common.SoulboundItemTagsProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;

public class SoulboundDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		var pack = generator.createPack();

		pack.addProvider(SoulboundItemTagsProvider::new);
		pack.addProvider(SoulboundEnchantmentTagsProvider::new);

		pack.addProvider(SoulboundEnglishLanguageProvider::new);
		pack.addProvider(SoulboundChineseLanguageProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
	}
}
