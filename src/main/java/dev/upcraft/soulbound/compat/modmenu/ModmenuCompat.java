package dev.upcraft.soulbound.compat.modmenu;

import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.upcraft.soulbound.Soulbound;
import dev.upcraft.soulbound.SoulboundConfig;
import org.jetbrains.annotations.Nullable;

public class ModmenuCompat implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			@Nullable var config = Soulbound.CONFIGURATOR.getConfig(SoulboundConfig.class);

			return config != null ? new ConfigScreen(null, config) : null;
		};
	}
}
