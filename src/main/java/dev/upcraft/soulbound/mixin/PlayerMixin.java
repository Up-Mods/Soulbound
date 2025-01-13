package dev.upcraft.soulbound.mixin;

import dev.upcraft.soulbound.api.inventory.SoulboundContainer;
import dev.upcraft.soulbound.api.inventory.SoulboundContainerProvider;
import dev.upcraft.soulbound.SoulboundHooks;
import dev.upcraft.soulbound.SoulboundPersistentState;
import dev.upcraft.soulbound.inventory.PlayerInventoryContainer;
import dev.upcraft.soulbound.init.SoulboundContainerProviders;
import dev.upcraft.sparkweave.api.util.fakeplayer.FakePlayerHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.UnaryOperator;

@Mixin(value = Player.class, priority = 6969)
@Implements(@Interface(iface = PlayerInventoryContainer.class, prefix = "sb$"))
public abstract class PlayerMixin extends LivingEntity {

	private PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
		throw new UnsupportedOperationException("mixin not transformed");
	}

	@Inject(method = "dropEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;dropAll()V"))
	private void soulbound_dropInventory(CallbackInfo callbackInfo) {
		//noinspection ConstantConditions
		if ((Object) this instanceof ServerPlayer serverPlayer && !FakePlayerHelper.isFakePlayer(serverPlayer)) {
			SoulboundPersistentState persistentState = SoulboundPersistentState.get(serverPlayer);
			persistentState.storePlayer(serverPlayer);
		}
	}

	public SoulboundContainerProvider<? extends SoulboundContainer> sb$getProvider() {
		return SoulboundContainerProviders.PLAYER_INVENTORY.get();
	}

	public LivingEntity sb$getEntity() {
		return this;
	}

	public RandomSource sb$getRandom() {
		return this.random;
	}

	public void sb$storeToNbt(CompoundTag nbt) {
		nbt.put("main", SoulboundHooks.getFilteredItemList(this.getInventory().items, this.getRandom()));
		nbt.put("off_hand", SoulboundHooks.getFilteredItemList(this.getInventory().offhand, this.getRandom()));
		nbt.put("armor", SoulboundHooks.getFilteredItemList(this.getInventory().armor, this.getRandom()));
	}

	@Shadow
	public abstract Inventory getInventory();

	public void sb$restoreFromNbt(CompoundTag nbt, UnaryOperator<ItemStack> itemProcessor) {
		var self = (Player) (Object) this;
		SoulboundHooks.processPlayerDrops(self, this.getInventory().items, SoulboundHooks.readItemList(nbt.getList("main", Tag.TAG_COMPOUND)), itemProcessor);
		SoulboundHooks.processPlayerDrops(self, this.getInventory().offhand, SoulboundHooks.readItemList(nbt.getList("off_hand", Tag.TAG_COMPOUND)), itemProcessor);
		SoulboundHooks.processPlayerDrops(self, this.getInventory().armor, SoulboundHooks.readItemList(nbt.getList("armor", Tag.TAG_COMPOUND)), itemProcessor);
	}
}
