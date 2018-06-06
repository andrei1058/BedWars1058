package com.andrei1058.bedwars.commands;

import org.bukkit.entity.Player;

import java.util.List;

public interface ParentCommand {

    /**
     * Check if a parent command has the target sub-command
     *
     * @since 0.6.1 - api v6
     */
    boolean hasSubCommand(SubCommand subCommand);

    /**
     * Add a subCommand
     *
     * @since 0.6.1 - api v6
     */
    void addSubCommand(SubCommand subCommand);

    /**
     * Send sub-commands list to a player
     * This includes subCommands with showInList true only
     *
     * @since 0.6.1 - api v6
     */
    void sendSubCommands(Player p);

    /**
     * Get available subCommands
     *
     * @since 0.6.1 - api v6
     */
    List<SubCommand> getSubCommands();

    /**
     * Get parent name
     *
     * @since 0.6.1 - api v6
     */
    String getName();
}
