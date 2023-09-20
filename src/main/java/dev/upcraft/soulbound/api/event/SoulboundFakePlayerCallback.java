package dev.upcraft.soulbound.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Predicate;

public interface SoulboundFakePlayerCallback extends Predicate<ServerPlayer> {

    Event<SoulboundFakePlayerCallback> EVENT = EventFactory.createArrayBacked(SoulboundFakePlayerCallback.class, listeners -> player -> {
        for (SoulboundFakePlayerCallback listener : listeners) {
            if (!listener.test(player)) {
                return false;
            }
        }
        return true;
    });

    /**
     * @return whether the player is a fake player and should not apply soulbound enchantment effects
     */
    @Override
    boolean test(ServerPlayer player);
}
