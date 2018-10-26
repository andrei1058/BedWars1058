package com.andrei1058.bedwars.api.command;

import org.bukkit.entity.Player;

import java.util.List;

public interface ParentCommand {

    /**
     * Check if a parent command has the target sub-command
     *
     * @since api 11
     */
    boolean hasSubCommand(String name);

    /**
     * Add a subCommand
     *
     * @since api 11
     */
    void addSubCommand(SubCommand subCommand);

    /**
     * Send sub-commands list to a player
     * This includes subCommands with showInList true only
     * He can see only commands which he have permission
     *
     * @since api 11
     */
    void sendSubCommands(Player p);

    /**
     * Get available subCommands
     *
     * @since api 11
     */
    List<SubCommand> getSubCommands();

    /**
     * Get parent name
     *
     * @since api 11
     */
    String getName();
}
