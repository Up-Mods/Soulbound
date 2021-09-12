package dev.upcraft.soulbound;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Lifecycle;
import dev.upcraft.soulbound.api.inventory.SoulboundContainerProvider;
import dev.upcraft.soulbound.core.SoulboundConfig;
import dev.upcraft.soulbound.core.SoulboundHooks;
import dev.upcraft.soulbound.core.inventory.PlayerInventoryContainer;
import dev.upcraft.soulbound.core.inventory.PlayerInventoryContainerProvider;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.impl.registry.sync.FabricRegistry;
import net.fabricmc.fabric.mixin.registry.sync.AccessorRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class Soulbound implements ModInitializer {

    public static final String MODID = "soulbound";
    public static final Logger LOGGER = LogManager.getLogger("Soulbound");
    public static final Supplier<SoulboundConfig> CONFIG = Util.make(() -> {
        AutoConfig.register(SoulboundConfig.class, JanksonConfigSerializer::new);
        return () -> AutoConfig.getConfigHolder(SoulboundConfig.class).getConfig();
    });
    public static final RegistryKey<? extends Registry<SoulboundContainerProvider<?>>> CONTAINERS_KEY = RegistryKey.ofRegistry(new Identifier(MODID, "containers"));
    public static final MutableRegistry<SoulboundContainerProvider<?>> CONTAINERS = Util.make(new SimpleRegistry<>(CONTAINERS_KEY, Lifecycle.stable()), it -> ((FabricRegistry) it).build(ImmutableSet.of(RegistryAttribute.PERSISTED)));
    public static final EnchantmentSoulbound ENCHANT_SOULBOUND = new EnchantmentSoulbound();
    public static final SoulboundContainerProvider<PlayerInventoryContainer> PLAYER_CONTAINER_PROVIDER = new PlayerInventoryContainerProvider();

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void onInitialize() {
        AccessorRegistry.getROOT().add(((AccessorRegistry) CONTAINERS).getRegistryKey(), CONTAINERS, Lifecycle.stable());
        Registry.register(Registry.ENCHANTMENT, new Identifier(MODID, "soulbound"), ENCHANT_SOULBOUND);
        Registry.register(CONTAINERS, new Identifier(MODID, "player_inventory"), PLAYER_CONTAINER_PROVIDER);
        SoulboundHooks.loadCompat("trinkets", "dev.upcraft.soulbound.compat.TrinketsIntegration");
    }

}
