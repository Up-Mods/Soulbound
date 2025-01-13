package dev.upcraft.soulbound.api.inventory;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public interface SoulboundContainerProvider<T extends SoulboundContainer> {

	@Nullable T getContainer(LivingEntity entity);
}
