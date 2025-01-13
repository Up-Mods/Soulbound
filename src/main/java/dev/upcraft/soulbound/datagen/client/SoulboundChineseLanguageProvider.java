package dev.upcraft.soulbound.datagen.client;

import dev.upcraft.soulbound.init.SoulboundEnchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class SoulboundChineseLanguageProvider extends FabricLanguageProvider {

	public SoulboundChineseLanguageProvider(FabricDataOutput dataOutput) {
		super(dataOutput, "zh_cn");
	}

	@Override
	public void generateTranslations(TranslationBuilder translationBuilder) {
		translationBuilder.add(SoulboundEnchantments.SOULBOUND.get(), "灵魂绑定");
	}
}
