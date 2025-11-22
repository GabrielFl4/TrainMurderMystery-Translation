package dev.doctor4t.trainmurdermystery.event;

import net.fabricmc.fabric.api.event.Event;
import net.minecraft.entity.Entity;

import static net.fabricmc.fabric.api.event.EventFactory.createArrayBacked;

public interface IsPlayerPunchable {

    Event<IsPlayerPunchable> EVENT = createArrayBacked(IsPlayerPunchable.class, listeners -> player -> {
        for (IsPlayerPunchable listener : listeners) {
            if (!listener.gotPunchable(player)) {
                return false;
            }
        }
        return true;
    });

    boolean gotPunchable(Entity player);
}
