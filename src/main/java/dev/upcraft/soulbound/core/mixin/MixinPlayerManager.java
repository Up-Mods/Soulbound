package dev.upcraft.soulbound.core.mixin;

import dev.upcraft.soulbound.Soulbound;
import dev.upcraft.soulbound.api.SoulboundApi;
import dev.upcraft.soulbound.api.inventory.SoulboundContainer;
import dev.upcraft.soulbound.core.SoulboundHooks;
import dev.upcraft.soulbound.core.SoulboundPersistentState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;
import java.util.Optional;

@Mixin(PlayerManager.class)
public class MixinPlayerManager {

    @Shadow
    @Final
    private MinecraftServer server;

    @Inject(method = "respawnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;setMainArm(Lnet/minecraft/util/Arm;)V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void soulbound$respawnPlayer(ServerPlayerEntity oldPlayer, boolean dimensionChange, CallbackInfoReturnable<ServerPlayerEntity> cir, BlockPos blockPos, float spawnAngle, boolean forcedSpawn, ServerWorld oldWorld, Optional<Vec3d> spawnPosition, ServerPlayerInteractionManager interactionManager, ServerWorld newWorld, ServerPlayerEntity newPlayer) {
        if (dimensionChange) {
            return;
        }
        SoulboundPersistentState persistentState = SoulboundPersistentState.get(server);
        Map<Identifier, NbtCompound> saveData = persistentState.restorePlayer(oldPlayer);
        if (saveData != null) {
            saveData.forEach((id, data) -> SoulboundApi.CONTAINERS.getOrEmpty(id).ifPresentOrElse(provider -> {
                @Nullable SoulboundContainer container = provider.getContainer(newPlayer);
                if(container != null) {
                    container.restoreFromNbt(data, SoulboundHooks.createItemProcessor(container));
                }
                else {
                    Soulbound.LOGGER.warn("tried to deserialize null container for provider {}", id);
                }
            }, () -> Soulbound.LOGGER.error("tried to deserialize unknown provider {} for player {}", id, newPlayer.getEntityName())));
        }
    }
}
