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

    /** Load sounds configuration */
    public Sounds(){
        saveDefaultSounds();
        loadSounds();
    }

    private static ConfigManager sounds = new ConfigManager(plugin,"sounds", "plugins/" + BedWars.plugin.getName());

    @SuppressWarnings("WeakerAccess")
    public static void saveDefaultSounds() {
        YamlConfiguration yml = sounds.getYml();
        yml.addDefault(SOUND_GAME_START, nms.gameStart().toString());
        yml.addDefault("game-end", BedWars.getForCurrentVersion("AMBIENCE_THUNDER", "AMBIENCE_THUNDER", "ITEM_TRIDENT_THUNDER"));
        yml.addDefault(SOUNDS_COUNTDOWN_TICK, nms.countdownTick().toString());
        yml.addDefault(SOUNDS_BED_DESTROY, nms.bedDestroy().toString());
        yml.addDefault(SOUNDS_PLAYER_KILL, nms.playerKill().toString());
        yml.addDefault(SOUNDS_INSUFF_MONEY, nms.insufficientMoney().toString());
        yml.addDefault(SOUNDS_BOUGHT, nms.bought().toString());

        yml.addDefault(NextEvent.BEDS_DESTROY.getSoundPath(), nms.bedDestroy().toString());
        yml.addDefault(NextEvent.DIAMOND_GENERATOR_TIER_II.getSoundPath(), BedWars.getForCurrentVersion("LEVEL_UP", "LEVEL_UP", "ENTITY_PLAYER_LEVELUP"));
        yml.addDefault(NextEvent.DIAMOND_GENERATOR_TIER_III.getSoundPath(), BedWars.getForCurrentVersion("LEVEL_UP", "LEVEL_UP", "ENTITY_PLAYER_LEVELUP"));
        yml.addDefault(NextEvent.EMERALD_GENERATOR_TIER_II.getSoundPath(), BedWars.getForCurrentVersion("GHAST_MOAN", "GHAST_MOAN", "ENTITY_GHAST_WARN"));
        yml.addDefault(NextEvent.EMERALD_GENERATOR_TIER_III.getSoundPath(), BedWars.getForCurrentVersion("GHAST_MOAN", "GHAST_MOAN", "ENTITY_GHAST_WARN"));
        yml.addDefault(NextEvent.ENDER_DRAGON.getSoundPath(), BedWars.getForCurrentVersion("ENDERDRAGON_WINGS", "ENDERDRAGON_WINGS", "ENTITY_ENDER_DRAGON_FLAP"));

        yml.options().copyDefaults(true);

        // remove old paths
        yml.set("bought", null);
        yml.set("insufficient-money", null);
        yml.set("player-kill", null);
        yml.set("countdown", null);
        sounds.save();
    }

    @SuppressWarnings("WeakerAccess")
    public static void loadSounds() {
        try {
            nms.setGameStartSound(sounds.getString(SOUND_GAME_START));
            nms.setBedDestroySound(sounds.getString(SOUNDS_BED_DESTROY));
            nms.setPlayerKillsSound(sounds.getString(SOUNDS_PLAYER_KILL));
            nms.setInsuffMoneySound(sounds.getString(SOUNDS_INSUFF_MONEY));
            nms.setBoughtSound(sounds.getString(SOUNDS_BOUGHT));
            nms.setCountdownSound(sounds.getString(SOUNDS_COUNTDOWN_TICK));
        } catch (InvalidSoundException invalidSound) {
            plugin.getLogger().severe(invalidSound.getMessage());
        }
    }

    public static Sound getSound(String path){
        try {
            return Sound.valueOf(sounds.getString(path));
        } catch (Exception ex) {
            return null;
        }
    }

    public static void playSound(String path, List<Player> players){
        final Sound sound = getSound(path);
        if (sound != null) players.forEach(p -> p.playSound(p.getLocation(), sound, 1f, 1f));
    }

    public static void playSound(Sound sound, List<Player> players){
        if (sound != null) players.forEach(p -> p.playSound(p.getLocation(), sound, 1f, 1f));
    }

    public static void playSound(String path, Player player){
        final Sound sound = getSound(path);
        if (sound != null) player.playSound(player.getLocation(), sound, 1f, 1f);
    }

    public static void playSound(Sound sound, Player player){
        if (sound != null) player.playSound(player.getLocation(), sound, 1f, 1f);
    }

    public static ConfigManager getSounds() {
        return sounds;
    }
}
