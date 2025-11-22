package dev.doctor4t.trainmurdermystery.event;

import net.fabricmc.fabric.api.event.Event;
import net.minecraft.entity.player.PlayerEntity;

import static net.fabricmc.fabric.api.event.EventFactory.createArrayBacked;

public interface AllowPlayerStabDeath {

    Event<AllowPlayerStabDeath> EVENT = createArrayBacked(AllowPlayerStabDeath.class, listeners -> player -> {
        for (AllowPlayerStabDeath listener : listeners) {
            if (!listener.allowStab(player)) {
                return false;
            }
        }
        return true;
    });

    boolean allowStab(PlayerEntity player);
}