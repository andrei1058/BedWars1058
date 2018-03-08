package com.andrei1058.bedwars.commands;

import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.configuration.Language;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class LeaveCommand extends BukkitCommand {

    public LeaveCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender s, String st, String[] args) {
        if (s instanceof ConsoleCommandSender) return true;
        Player p = (Player) s;
        Arena a = Arena.getArenaByPlayer(p);
        if (a == null){
            if (getServerType() == ServerType.MULTIARENA && spigot.getBoolean("settings.bungeecord")){
                Misc.moveToLobbyOrKick(p);
            } else {
                p.sendMessage(getMsg(p, Language.notInArena));
            }
        } else {
            if (a.isPlayer(p)){
                a.removePlayer(p);
            } else if (a.isSpectator(p)){
                a.removeSpectator(p);
            }
        }
        return true;
    }
}
