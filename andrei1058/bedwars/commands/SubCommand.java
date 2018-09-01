package com.andrei1058.bedwars.commands;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    /* SubCommand name */
    private String name;
    /* Show the subCommand in the main command list*/
    private boolean show = false;
    /* SubCommand parent */
    private ParentCommand parent;
    /* Command order in main command list*/
    private int priority = 20;
    /* Display name/ info in subCommands list*/
    private TextComponent displayInfo;
    /* True if this is an arena setup SubCommand*/
    private boolean arenaSetupCommand = false;
    /* True if it is a command for ops */
    private boolean opCommand = false;

    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param name   sub-command name
     * @param parent parent command
     * @since 0.6.1 api v6
     */
    public SubCommand(ParentCommand parent, String name) {
        this.name = name;
        this.parent = parent;
        parent.addSubCommand(this);
    }

    /**
     * Add your sub-command code under this method
     *
     * @since 0.6.1 api v6
     */
    public abstract boolean execute(String args[], CommandSender s);

    /**
     * Get sub-command name
     *
     * @since 0.6.1 api v6
     */
    public String getSubCommandName() {
        return name;
    }

    /**
     * Display the sub-command under the main command
     * Don't forget to set the displayInfo
     * Ops only will see it cuz players receive a commands list from messages file
     *
     * @since 0.6.1 api v6
     */
    public void showInList(boolean value) {
        this.show = value;
    }

    /**
     * This is the command information in the subCommands list of the target parent
     *
     * @since 0.6.1 - api v6
     */
    public void setDisplayInfo(TextComponent displayInfo) {
        this.displayInfo = displayInfo;
    }

    /**
     * This is the command priority in the sub-commands list
     * You may use this method if you set showInList true
     * Commands with a minor number will be displayed first
     *
     * @since 0.6.1 api v6
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Get parent command
     *
     * @since 0.6.1 - api v6
     */
    public ParentCommand getParent() {
        return parent;
    }

    /**
     * Get show priority
     *
     * @since 0.6.1 - api v6
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Get command description for subCommands list
     *
     * @since 0.6.1 - api v6
     */
    public TextComponent getDisplayInfo() {
        return displayInfo;
    }

    /**
     * True if it is an arena setup sub-command
     *
     * @since 0.6.1 - api v6
     */
    public void setArenaSetupCommand(boolean arenaSetupCommand) {
        this.arenaSetupCommand = arenaSetupCommand;
    }

    /**
     * Check if it is an arena setup command
     *
     * @since 0.6.1 - api v6
     */
    public boolean isArenaSetupCommand() {
        return arenaSetupCommand;
    }

    /**
     * True if it is a command for ops
     *
     * @since 0.6.1 - api v6
     */
    public void setOpCommand(boolean opCommand) {
        this.opCommand = opCommand;
    }

    /**
     * True if it is a command for ops
     *
     * @since 0.6.1 - api v6
     */
    public boolean isOpCommand() {
        return opCommand;
    }

    /**Check if is displayed on the list */
    public boolean isShow() {
        return show;
    }
}
