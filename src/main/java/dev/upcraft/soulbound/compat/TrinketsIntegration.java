package dev.upcraft.soulbound.compat;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketEnums;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.event.TrinketDropCallback;
import dev.upcraft.soulbound.Soulbound;
import dev.upcraft.soulbound.api.SoulboundApi;
import dev.upcraft.soulbound.api.inventory.SoulboundContainer;
import dev.upcraft.soulbound.api.inventory.SoulboundContainerProvider;
import dev.upcraft.soulbound.core.SoulboundHooks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

@SuppressWarnings("unused") // called by reflection
public class TrinketsIntegration {

    public static final SoulboundContainerProvider<SoulboundContainer> DUMMY_PROVIDER = entity -> {
        TrinketComponent component = TrinketsApi.getTrinketComponent(entity).orElse(null);
        return component != null ? new SoulboundContainer() {
            @Override
            public SoulboundContainerProvider<? extends SoulboundContainer> getProvider() {
                return TrinketsIntegration.DUMMY_PROVIDER;
            }

            @Override
            public LivingEntity getEntity() {
                return component.getEntity();
            }

            @Override
            public void storeToNbt(NbtCompound nbt) {
                // NO-OP
            }

            @Override
            public void restoreFromNbt(NbtCompound nbt, UnaryOperator<ItemStack> itemProcessor) {
                // NO-OP
            }
        } : null;
    };

    private static void register() {
        TrinketDropCallback.EVENT.register((rule, stack, ref, entity) -> {
            if (EnchantmentHelper.getLevel(Soulbound.ENCHANT_SOULBOUND, stack) > 0) {
                @Nullable SoulboundContainer container = DUMMY_PROVIDER.getContainer(entity);
                if (container != null && !SoulboundHooks.createItemProcessor(container).apply(stack).isEmpty()) {
                    return TrinketEnums.DropRule.KEEP;
                }
            }
            return TrinketEnums.DropRule.DEFAULT;
        });
        Registry.register(SoulboundApi.CONTAINERS, new Identifier(Soulbound.MODID, "trinkets_integration"), DUMMY_PROVIDER);
    }

    static {
        register();
    }
}
