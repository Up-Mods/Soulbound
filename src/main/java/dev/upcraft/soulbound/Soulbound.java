package dev.upcraft.soulbound;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Lifecycle;
import dev.upcraft.soulbound.api.SoulboundApi;
import dev.upcraft.soulbound.api.inventory.SoulboundContainer;
import dev.upcraft.soulbound.api.inventory.SoulboundContainerProvider;
import dev.upcraft.soulbound.core.SoulboundConfig;
import dev.upcraft.soulbound.core.SoulboundHooks;
import dev.upcraft.soulbound.core.SoulboundPersistentState;
import dev.upcraft.soulbound.core.inventory.PlayerInventoryContainer;
import dev.upcraft.soulbound.core.inventory.PlayerInventoryContainerProvider;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.impl.registry.sync.FabricRegistry;
import net.fabricmc.fabric.mixin.registry.sync.AccessorRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public class Soulbound implements ModInitializer {

    public static final String MODID = "soulbound";
    public static final Logger LOGGER = LogManager.getLogger("Soulbound");
    public static final Supplier<SoulboundConfig> CONFIG = Util.make(() -> {
        AutoConfig.register(SoulboundConfig.class, JanksonConfigSerializer::new);
        return AutoConfig.getConfigHolder(SoulboundConfig.class);
    });
    public static final RegistryKey<? extends Registry<SoulboundContainerProvider<?>>> CONTAINERS_KEY = RegistryKey.ofRegistry(new Identifier(MODID, "containers"));
    public static final MutableRegistry<SoulboundContainerProvider<?>> CONTAINERS = Util.make(new SimpleRegistry<>(CONTAINERS_KEY, Lifecycle.stable()), it -> ((FabricRegistry) it).build(ImmutableSet.of(RegistryAttribute.PERSISTED)));
    public static final SoulboundEnchantment ENCHANT_SOULBOUND = new SoulboundEnchantment();
    public static final SoulboundContainerProvider<PlayerInventoryContainer> PLAYER_CONTAINER_PROVIDER = new PlayerInventoryContainerProvider();

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void onInitialize() {
        AccessorRegistry.getROOT().add(((AccessorRegistry) CONTAINERS).getRegistryKey(), CONTAINERS, Lifecycle.stable());
        Registry.register(Registry.ENCHANTMENT, new Identifier(MODID, "soulbound"), ENCHANT_SOULBOUND);
        Registry.register(CONTAINERS, new Identifier(MODID, "player_inventory"), PLAYER_CONTAINER_PROVIDER);
        SoulboundHooks.loadCompat("trinkets", "dev.upcraft.soulbound.compat.TrinketsIntegration");
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (!alive) {
                SoulboundPersistentState persistentState = SoulboundPersistentState.get(newPlayer.getServer());
                Map<Identifier, NbtCompound> saveData = persistentState.restorePlayer(oldPlayer);
                if (saveData != null) {
                    saveData.forEach((id, data) -> SoulboundApi.CONTAINERS.getOrEmpty(id).ifPresentOrElse(provider -> {
                        @Nullable SoulboundContainer container = provider.getContainer(newPlayer);
                        if (container != null) {
                            container.restoreFromNbt(data, SoulboundHooks.createItemProcessor(container));
                        } else {
                            Soulbound.LOGGER.warn("tried to deserialize null container for provider {}", id);
                        }
                    }, () -> Soulbound.LOGGER.error("tried to deserialize unknown provider {} for player {}", id, newPlayer.getEntityName())));
                }
            }
        });
    }

}
