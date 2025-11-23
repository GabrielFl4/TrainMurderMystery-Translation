package dev.doctor4t.trainmurdermystery.command;

import com.mojang.brigadier.CommandDispatcher;
import dev.doctor4t.trainmurdermystery.TMM;
import dev.doctor4t.trainmurdermystery.cca.GameWorldComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class SetRandomSpawnCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("tmm:setRandomSpawn")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("disabled")
                        .executes(context -> execute(context.getSource(), GameWorldComponent.SpawnMode.DISABLED)))
                .then(CommandManager.literal("shuffle")
                        .executes(context -> execute(context.getSource(), GameWorldComponent.SpawnMode.SHUFFLE)))
                .then(CommandManager.literal("randomPos")
                        .executes(context -> execute(context.getSource(), GameWorldComponent.SpawnMode.RANDOM_POS)))
        );
    }

    private static int execute(ServerCommandSource source, GameWorldComponent.SpawnMode mode) {
        return TMM.executeSupporterCommand(source,
                () -> {
                    GameWorldComponent gameWorldComponent = GameWorldComponent.KEY.get(source.getWorld());
                    gameWorldComponent.setSpawnMode(mode);
                    if (source.getPlayer() != null) {
                        source.getPlayer().sendMessage(Text.literal("Random spawn mode set to " + mode.asString()), false);
                    }
                }
        );
    }
}
