package dev.upcraft.soulbound.compat;

// import com.terraformersmc.modmenu.api.ConfigScreenFactory;
// import com.terraformersmc.modmenu.api.ModMenuApi;
//import dev.upcraft.soulbound.core.SoulboundConfig;
//import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModmenuIntegration {//implements ModMenuApi {

    // @Override
    // public ConfigScreenFactory<?> getModConfigScreenFactory() {
    //     return parent -> AutoConfig.getConfigScreen(SoulboundConfig.class, parent).get();
    // }
}
