package dev.doctor4t.trainmurdermystery.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.doctor4t.trainmurdermystery.client.TrainMurderMysteryClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin {

    @Shadow
    public abstract boolean equals(KeyBinding other);

    @ModifyReturnValue(method = "wasPressed", at = @At("RETURN"))
    private boolean tmm$disableSwapHands(boolean original) {
        if (TrainMurderMysteryClient.shouldRestrictPlayerOptions()) {
            if (this.equals(MinecraftClient.getInstance().options.swapHandsKey)) return false;
        }

        return original;
    }
}
