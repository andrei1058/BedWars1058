/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.IGameService;
import com.andrei1058.bedwars.api.arena.constraints.ArenaConstraintViolation;
import com.andrei1058.bedwars.api.arena.constraints.ConstraintProvider;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.server.ArenaTemplateLoadExceptionEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.constraints.DefaultConstraintProvider;
import com.andrei1058.bedwars.configuration.ArenaConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.BedWars.config;

public class ArenaManager implements IGameService {

    private final LinkedList<ConstraintProvider> constraintProviders = new LinkedList<>();

    private int gid = 0;
    private String day = "", month = "";

    public ArenaManager() {
        constraintProviders.add(new DefaultConstraintProvider());
    }

    public static void onMapLoad(World world) {
        for (IArena a : new LinkedList<>(Arena.getEnableQueue())) {
            if (a.getWorldName().equalsIgnoreCase(world.getName())) {
                a.init(world);
                break;
            }
        }
    }


    public String generateGameID() {
        SimpleDateFormat y = new SimpleDateFormat("yy"), m = new SimpleDateFormat("MM"), d = new SimpleDateFormat("dd");
        String m2 = m.format(System.currentTimeMillis()), d2 = d.format(System.currentTimeMillis());
        if (!(m2.equals(this.month) || d2.equalsIgnoreCase(this.day))) {
            this.month = m2;
            this.day = d2;
            this.gid = 0;
        }
        return "bw_temp_y" + y.format(System.currentTimeMillis()) + "m" + this.month + "d" + this.day + "g" + gid++;
    }

    /**
     * Load an arena.
     * It will check the load constraints and then will add the map to the enable queue.
     *
     * @param template  map template.
     * @param requester player requester. Eventually via command.
     */
    @ApiStatus.Experimental
    public void loadGame(String template, @Nullable Player requester) {
        LinkedList<CommandSender> recipients = new LinkedList<>();
        recipients.add(Bukkit.getConsoleSender());
        if (null != requester) {
            recipients.add(requester);
        }

        var cm = new ArenaConfig(
                BedWars.plugin,
                template,
                plugin.getDataFolder().getPath() + File.separator + ConfigPath.ARENA_DIR
        );

        var errors = this.validateTemplate(cm);

        errors.ifPresent(violations ->
                {
                    violations.forEach(
                            violation -> violation.getMessages().forEach(
                                    message -> recipients.forEach(
                                            recipient -> recipient.sendMessage(formatErr(template, message))
                                    )
                            )
                    );
                    recipients.forEach(
                            recipient -> recipient.sendMessage("Could not create new game from template: " + template)
                    );
                }
        );

        if (errors.isPresent()) {
            Bukkit.getPluginManager().callEvent(new ArenaTemplateLoadExceptionEvent(template, errors.get()));
            return;
        }

        var yml = cm.getYml();
        var group = "Default";
        if (config.getYml().get("arenaGroups") != null) {
            if (config.getYml().getStringList("arenaGroups").contains(yml.getString("group"))) {
                group = yml.getString("group");
            }
        }
        var candidate = new Arena(
                template,
                autoscale ? generateGameID() : template,
                group,
                cm.getInt("maxInTeam"),
                yml.getConfigurationSection("Team").getKeys(false).size() * cm.getInt("maxInTeam"),
                cm.getInt("minPlayers"),
                cm.getBoolean("allowSpectate"),
                cm.getInt(ConfigPath.ARENA_ISLAND_RADIUS),
                cm.getBoolean(ConfigPath.ARENA_ALLOW_MAP_BREAK),
                cm.getInt(ConfigPath.ARENA_Y_LEVEL_KILL)
        );

        // Create arena group display name msg path
        Language.saveIfNotExists(
                Messages.ARENA_DISPLAY_GROUP_PATH + group.toLowerCase(),
                String.valueOf(group.charAt(0)).toUpperCase() + group.substring(1).toLowerCase()
        );

        Arena.addToEnableQueue(candidate);
    }

    public Optional<Collection<ArenaConstraintViolation>> validateTemplate(
            @NotNull final ConfigManager templateConfig
    ) {
        LinkedList<ArenaConstraintViolation> errors = new LinkedList<>();

        constraintProviders.forEach(validator -> {
            validator.provide(templateConfig.getName(), templateConfig).ifPresent(constraints ->
                    constraints.forEach(constraint -> {
                        constraint.validate(BedWars.getAPI(), templateConfig).ifPresent(
                                strings -> errors.add(new ArenaConstraintViolation(templateConfig.getName(), constraint, strings))
                        );
                    })
            );
        });


        return Optional.of(errors);
    }

    private @NotNull String formatErr(String template, String msg) {
        return ChatColor.RED + "[" + plugin.getName() + "] Template: " + template + " -> " + msg;
    }

    public List<ConstraintProvider> getConstraintProviders() {
        return Collections.unmodifiableList(constraintProviders);
    }

    public void registerProvider(ConstraintProvider provider) {
        getConstraintProviders().forEach(existing -> {
            if (provider.getOwner().equals(existing.getOwner())) {
                throw new RuntimeException("You did already register a provider: " + existing.getClass().getPackageName());
            }
        });
        constraintProviders.add(provider);
        plugin.getLogger().warning("Registered new arena template validator: " +
                provider.getClass().getPackageName() + " by " + provider.getOwner().getName())
        ;
    }
}
