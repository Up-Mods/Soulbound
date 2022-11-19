package dev.upcraft.soulbound.core.mixin;

import dev.upcraft.soulbound.Soulbound;
import dev.upcraft.soulbound.api.inventory.SoulboundContainer;
import dev.upcraft.soulbound.api.inventory.SoulboundContainerProvider;
import dev.upcraft.soulbound.core.SoulboundHooks;
import dev.upcraft.soulbound.core.SoulboundPersistentState;
import dev.upcraft.soulbound.core.inventory.PlayerInventoryContainer;
import net.fabricmc.fabric.mixin.dimension.EntityMixin;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.UnaryOperator;

@Mixin(value = PlayerEntity.class, priority = 6969)
public abstract class MixinPlayerEntity extends LivingEntityMixin {

    // private MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
    //     super(entityType, world);
    //     throw new UnsupportedOperationException("mixin not transformed");
    // }

    public SoulboundContainerProvider<? extends SoulboundContainer> sb$getProvider() {
        return Soulbound.PLAYER_CONTAINER_PROVIDER;
    }

    public void sb$storeToNbt(NbtCompound nbt) {
        nbt.put("main", SoulboundHooks.getFilteredItemList(this.getInventory().main, this.getRandom()));
        nbt.put("off_hand", SoulboundHooks.getFilteredItemList(this.getInventory().offHand, this.getRandom()));
        nbt.put("armor", SoulboundHooks.getFilteredItemList(this.getInventory().armor, this.getRandom()));
    }

    @Shadow
    public abstract PlayerInventory getInventory();

    public void sb$restoreFromNbt(NbtCompound nbt, UnaryOperator<ItemStack> itemProcessor) {
        SoulboundHooks.processPlayerDrops((PlayerEntity) (Object) this, this.getInventory().main, SoulboundHooks.readItemList(nbt.getList("main", NbtElement.COMPOUND_TYPE)), itemProcessor);
        SoulboundHooks.processPlayerDrops((PlayerEntity) (Object) this, this.getInventory().offHand, SoulboundHooks.readItemList(nbt.getList("off_hand", NbtElement.COMPOUND_TYPE)), itemProcessor);
        SoulboundHooks.processPlayerDrops((PlayerEntity) (Object) this, this.getInventory().armor, SoulboundHooks.readItemList(nbt.getList("armor", NbtElement.COMPOUND_TYPE)), itemProcessor);
    }
}
