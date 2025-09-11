package dev.doctor4t.trainmurdermystery.cca;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class WorldGameComponent implements AutoSyncedComponent {
    private final World world;

    private boolean running = false;
    private List<UUID> players = new ArrayList<>();
    private List<UUID> hitmen = new ArrayList<>();
    private List<UUID> hitmanTargets = new ArrayList<>();

    public WorldGameComponent(World world) {
        this.world = world;
    }

    private void sync() {
        TrainMurderMysteryComponents.TRAIN.sync(this.world);
    }

    public void setRunning(boolean running) {
        this.running = running;
        this.sync();
    }

    public boolean isRunning() {
        return running;
    }

    public void setPlayers(List<UUID> players) {
        this.players = players;
        this.sync();
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public List<UUID> getHitmen() {
        return hitmen;
    }

    public void setHitmen(List<UUID> hitmen) {
        this.hitmen = hitmen;
        this.sync();
    }

    public List<UUID> getHitmanTargets() {
        return hitmanTargets;
    }

    public void setHitmanTargets(List<UUID> hitmanTargets) {
        this.hitmanTargets = hitmanTargets;
        this.sync();
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.running = nbtCompound.getBoolean("Running");

    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("Running", running);

    }

    public void startGame() {
        if (world instanceof ServerWorld serverWorld) {
            TrainMurderMysteryComponents.TRAIN.get(serverWorld).setTrainSpeed(130);

//        List<ServerPlayerEntity> players = world.getPlayers().stream().filter(serverPlayerEntity -> !serverPlayerEntity.isInCreativeMode() && !serverPlayerEntity.isSpectator()).toList();
            List<ServerPlayerEntity> players = serverWorld.getPlayers().stream().toList();
            // select hitmen
            int hitmanCount = (int) Math.floor(players.size() * .2f);
            ArrayList<UUID> hitmen = new ArrayList<>();
            for (int i = 0; i < hitmanCount; i++) {
                hitmen.add(players.get(world.random.nextInt(hitmanCount)).getUuid());
            }
            setHitmen(hitmen);


            for (ServerPlayerEntity player : players) {
                for (UUID hitman : hitmen) {
                    player.sendMessage(Text.literal("hitmen: " + world.getPlayerByUuid(hitman)));
                }
            }
        }
    }
}
