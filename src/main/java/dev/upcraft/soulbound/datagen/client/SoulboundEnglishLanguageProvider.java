package dev.upcraft.soulbound.datagen.client;

import dev.upcraft.soulbound.data.SoulboundEnchantmentTags;
import dev.upcraft.soulbound.data.SoulboundItemTags;
import dev.upcraft.soulbound.init.SoulboundEnchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.Util;
import net.minecraft.locale.Language;
import net.minecraft.tags.TagKey;

public class SoulboundEnglishLanguageProvider extends FabricLanguageProvider {

	public SoulboundEnglishLanguageProvider(FabricDataOutput dataOutput) {
		super(dataOutput, Language.DEFAULT);
	}

	@Override
	public void generateTranslations(TranslationBuilder translationBuilder) {

		translationBuilder.add(SoulboundEnchantments.SOULBOUND.get(), "Soulbound");
		translationBuilder.add(SoulboundEnchantments.SOULBOUND.get().getDescriptionId() + ".desc", "Retains items upon death");

		tag(translationBuilder, SoulboundItemTags.SOULBOUND_CANNOT_APPLY_TO, "Cannot apply Soulbound enchantment");
		tag(translationBuilder, SoulboundEnchantmentTags.SOULBOUND_INCOMPATIBLE_WITH, "Incompatible with Soulbound");

		translationBuilder.add("config.soulbound.soulbound_reduction_rate", "Chance to reduce Soulbound enchantment Level by 1");
		translationBuilder.add("config.soulbound.soulbound_success_chance", "Chance to apply Soulbound enchantment effect");
		translationBuilder.add("config.soulbound.soulbound_is_treasure_enchantment", "Soulbound is a Treasure-Enchantment");
	}

	public static void tag(TranslationBuilder builder, TagKey<?> tag, String translation) {
		var registryName = tag.registry().location().toShortLanguageKey().replace('/', '.');
		var tagName = Util.makeDescriptionId("tag." + registryName, tag.location());
		builder.add(tagName, translation);
	}
}
