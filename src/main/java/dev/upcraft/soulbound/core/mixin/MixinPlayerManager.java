package dev.upcraft.soulbound.core.mixin;

import dev.upcraft.soulbound.api.SlottedItem;
import dev.upcraft.soulbound.Soulbound;
import dev.upcraft.soulbound.api.SoulboundContainer;
import dev.upcraft.soulbound.core.SoulboundPersistentState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mixin(PlayerManager.class)
public class MixinPlayerManager {

    @Shadow
    @Final
    private MinecraftServer server;

    @Inject(method = "respawnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;setMainArm(Lnet/minecraft/util/Arm;)V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void soulbound$respawnPlayer(ServerPlayerEntity oldPlayer, boolean dimensionChange, CallbackInfoReturnable<ServerPlayerEntity> cir, BlockPos blockPos, float spawnAngle, boolean forcedSpawn, ServerWorld oldWorld, Optional<Vec3d> spawnPosition, ServerPlayerInteractionManager interactionManager, ServerWorld newWorld, ServerPlayerEntity newPlayer) {
        if (dimensionChange)
            return;

        SoulboundPersistentState persistentState = SoulboundPersistentState.get(server);

        List<SlottedItem> savedItems = persistentState.restorePlayer(oldPlayer);
        if (savedItems == null)
            return;

        SoulboundContainer.CONTAINERS.forEach((id, container) -> savedItems.stream().filter(item -> item.containerId().equals(id)).forEach(item -> {
            if (newPlayer.getRandom().nextFloat() < Soulbound.CONFIG.get().soulboundRemovalChance) {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(item.stack());
                enchantments.remove(Soulbound.ENCHANT_SOULBOUND);
                EnchantmentHelper.set(enchantments, item.stack());
            }

            container.replaceItem(newPlayer, item);
        }));

        savedItems.clear();
    }
}
