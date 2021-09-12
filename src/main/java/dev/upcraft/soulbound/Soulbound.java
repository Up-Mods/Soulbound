package dev.upcraft.soulbound;

import dev.upcraft.soulbound.api.SoulboundContainer;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Range;

import java.util.function.Supplier;

public class Soulbound implements ModInitializer {

    public static final String MODID = "soulbound";
    public static final Logger LOGGER = LogManager.getLogger("Soulbound");
    public static final EnchantmentSoulbound ENCHANT_SOULBOUND = new EnchantmentSoulbound();
    public static final Supplier<SoulboundConfig> CONFIG = AutoConfig.getConfigHolder(SoulboundConfig.class)::getConfig;

    @Override
    public void onInitialize() {
        AutoConfig.register(SoulboundConfig.class, JanksonConfigSerializer::new);
        Registry.register(Registry.ENCHANTMENT, new Identifier(MODID, "soulbound"), ENCHANT_SOULBOUND);

        SoulboundContainer.CONTAINERS.put(new Identifier(MODID, "inv_main"), player -> player.getInventory().main);
        SoulboundContainer.CONTAINERS.put(new Identifier(MODID, "inv_off"), player -> player.getInventory().offHand);
        SoulboundContainer.CONTAINERS.put(new Identifier(MODID, "inv_armor"), player -> player.getInventory().armor);
        loadCompat("trinkets", "dev.upcraft.soulbound.compat.TrinketsIntegration");
    }

    private static void loadCompat(String modid, String clazz) {
        try {
            if (FabricLoader.getInstance().isModLoaded(modid))
                Class.forName(clazz);
        } catch (Exception e) {
            LOGGER.error("Failed to load {} compatibility from {}", modid, clazz);
        }
    }

    @me.shedaniel.autoconfig.annotation.Config(name = Soulbound.MODID)
    public static class SoulboundConfig implements ConfigData {

        public float soulboundRemovalChance = 0.0F;

        @Override
        public void validatePostLoad() throws ValidationException {
            soulboundRemovalChance = MathHelper.clamp(soulboundRemovalChance, 0.0F, 1.0F);
        }
    }
}
