package com.andrei1058.bedwars.configuration;


import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.NextEvent;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.exceptions.InvalidSoundException;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

import static com.andrei1058.bedwars.BedWars.nms;
import static com.andrei1058.bedwars.BedWars.plugin;
import static com.andrei1058.bedwars.api.configuration.ConfigPath.*;

public class Sounds {

    /**
     * Load sounds configuration
     */
    public Sounds() {
        saveDefaultSounds();
    }

    private static ConfigManager sounds = new ConfigManager(plugin, "sounds", plugin.getDataFolder().getPath());

    @SuppressWarnings("WeakerAccess")
    public static void saveDefaultSounds() {
        YamlConfiguration yml = sounds.getYml();
        yml.addDefault("game-end", BedWars.getForCurrentVersion("AMBIENCE_THUNDER", "ENTITY_LIGHTNING_THUNDER", "ITEM_TRIDENT_THUNDER"));
        yml.addDefault("rejoin-denied", BedWars.getForCurrentVersion("VILLAGER_NO", "ENTITY_VILLAGER_NO", "ENTITY_VILLAGER_NO"));
        yml.addDefault("rejoin-allowed", BedWars.getForCurrentVersion("SLIME_WALK", "ENTITY_SLIME_JUMP", "ENTITY_SLIME_JUMP"));
        yml.addDefault("spectate-denied", BedWars.getForCurrentVersion("VILLAGER_NO", "ENTITY_VILLAGER_NO", "ENTITY_VILLAGER_NO"));
        yml.addDefault("spectate-allowed", BedWars.getForCurrentVersion("SLIME_WALK", "ENTITY_SLIME_JUMP", "ENTITY_SLIME_JUMP"));
        yml.addDefault("join-denied", BedWars.getForCurrentVersion("VILLAGER_NO", "ENTITY_VILLAGER_NO", "ENTITY_VILLAGER_NO"));
        yml.addDefault("join-allowed", BedWars.getForCurrentVersion("SLIME_WALK", "ENTITY_SLIME_JUMP", "ENTITY_SLIME_JUMP"));
        yml.addDefault("spectator-gui-click", BedWars.getForCurrentVersion("SLIME_WALK", "ENTITY_SLIME_JUMP", "ENTITY_SLIME_JUMP"));
        yml.addDefault(SOUNDS_COUNTDOWN_TICK, BedWars.getForCurrentVersion("CHICKEN_EGG_POP", "ENTITY_CHICKEN_EGG", "ENTITY_CHICKEN_EGG"));
        yml.addDefault(SOUNDS_COUNTDOWN_TICK_X + "5", BedWars.getForCurrentVersion("CHICKEN_EGG_POP", "ENTITY_CHICKEN_EGG", "ENTITY_CHICKEN_EGG"));
        yml.addDefault(SOUNDS_COUNTDOWN_TICK_X + "4", BedWars.getForCurrentVersion("CHICKEN_EGG_POP", "ENTITY_CHICKEN_EGG", "ENTITY_CHICKEN_EGG"));
        yml.addDefault(SOUNDS_COUNTDOWN_TICK_X + "3", BedWars.getForCurrentVersion("CHICKEN_EGG_POP", "ENTITY_CHICKEN_EGG", "ENTITY_CHICKEN_EGG"));
        yml.addDefault(SOUNDS_COUNTDOWN_TICK_X + "2", BedWars.getForCurrentVersion("CHICKEN_EGG_POP", "ENTITY_CHICKEN_EGG", "ENTITY_CHICKEN_EGG"));
        yml.addDefault(SOUNDS_COUNTDOWN_TICK_X + "1", BedWars.getForCurrentVersion("CHICKEN_EGG_POP", "ENTITY_CHICKEN_EGG", "ENTITY_CHICKEN_EGG"));
        yml.addDefault(SOUND_GAME_START, BedWars.getForCurrentVersion("SLIME_ATTACK", "BLOCK_SLIME_FALL", "BLOCK_SLIME_BLOCK_FALL"));

        yml.addDefault(SOUNDS_BED_DESTROY, BedWars.getForCurrentVersion("ENDERDRAGON_GROWL", "ENTITY_ENDERDRAGON_GROWL", "ENTITY_ENDER_DRAGON_GROWL"));
        yml.addDefault(SOUNDS_INSUFF_MONEY, BedWars.getForCurrentVersion("VILLAGER_NO", "ENTITY_VILLAGER_NO", "ENTITY_VILLAGER_NO"));
        yml.addDefault(SOUNDS_BOUGHT, BedWars.getForCurrentVersion("VILLAGER_YES", "ENTITY_VILLAGER_YES", "ENTITY_VILLAGER_YES"));

        yml.addDefault(NextEvent.BEDS_DESTROY.getSoundPath(), BedWars.getForCurrentVersion("ENDERDRAGON_GROWL", "ENTITY_ENDERDRAGON_GROWL", "ENTITY_ENDER_DRAGON_GROWL"));
        yml.addDefault(NextEvent.DIAMOND_GENERATOR_TIER_II.getSoundPath(), BedWars.getForCurrentVersion("LEVEL_UP", "ENTITY_PLAYER_LEVELUP", "ENTITY_PLAYER_LEVELUP"));
        yml.addDefault(NextEvent.DIAMOND_GENERATOR_TIER_III.getSoundPath(), BedWars.getForCurrentVersion("LEVEL_UP", "ENTITY_PLAYER_LEVELUP", "ENTITY_PLAYER_LEVELUP"));
        yml.addDefault(NextEvent.EMERALD_GENERATOR_TIER_II.getSoundPath(), BedWars.getForCurrentVersion("GHAST_MOAN", "ENTITY_GHAST_WARN", "ENTITY_GHAST_WARN"));
        yml.addDefault(NextEvent.EMERALD_GENERATOR_TIER_III.getSoundPath(), BedWars.getForCurrentVersion("GHAST_MOAN", "ENTITY_GHAST_WARN", "ENTITY_GHAST_WARN"));
        yml.addDefault(NextEvent.ENDER_DRAGON.getSoundPath(), BedWars.getForCurrentVersion("ENDERDRAGON_WINGS", "ENTITY_ENDERDRAGON_FLAP", "ENTITY_ENDER_DRAGON_FLAP"));

        yml.addDefault("player-re-spawn", BedWars.getForCurrentVersion("SLIME_ATTACK", "BLOCK_SLIME_FALL", "BLOCK_SLIME_BLOCK_FALL"));
        yml.addDefault("arena-selector-open", BedWars.getForCurrentVersion("CHICKEN_EGG_POP", "ENTITY_CHICKEN_EGG", "ENTITY_CHICKEN_EGG"));
        yml.addDefault("stats-gui-open", BedWars.getForCurrentVersion("CHICKEN_EGG_POP", "ENTITY_CHICKEN_EGG", "ENTITY_CHICKEN_EGG"));
        yml.addDefault("trap-sound", BedWars.getForCurrentVersion("ENDERMAN_TELEPORT", "ENDERMAN_TELEPORT", "ENTITY_ENDERMAN_TELEPORT"));
        yml.options().copyDefaults(true);

        // remove old paths
        yml.set("bought", null);
        yml.set("insufficient-money", null);
        yml.set("player-kill", null);
        yml.set("countdown", null);
        sounds.save();
    }

    public static Sound getSound(String path) {
        try {
            return Sound.valueOf(sounds.getString(path));
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * @return true if sound is valid and it was played.
     */
    public static boolean playSound(String path, List<Player> players) {
        final Sound sound = getSound(path);
        if (sound != null) {
            players.forEach(p -> p.playSound(p.getLocation(), sound, 1f, 1f));
            return true;
        }
        return false;
    }

    /**
     * @return true if sound is valid and it was played.
     */
    public static boolean playSound(Sound sound, List<Player> players) {
        if (sound == null) return false;
        players.forEach(p -> p.playSound(p.getLocation(), sound, 1f, 1f));
        return true;
    }

    public static void playSound(String path, Player player) {
        final Sound sound = getSound(path);
        if (sound != null) player.playSound(player.getLocation(), sound, 1f, 1f);
    }

    public static void playSound(Sound sound, Player player) {
        if (sound != null) player.playSound(player.getLocation(), sound, 1f, 1f);
    }

    public static ConfigManager getSounds() {
        return sounds;
    }
}
