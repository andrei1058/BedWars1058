package com.andrei1058.bedwars.upgrades;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class UpgradeGroup {

    private static List<UpgradeGroup> upgradeGroups = new ArrayList<>();

    private List<TeamUpgrade> teamUpgrades;
    private String name;

    public UpgradeGroup(String name, List<TeamUpgrade> teamUpgrades) {
        this.name = name;
        this.teamUpgrades = new ArrayList<>(teamUpgrades);
        BedWars.debug("loading new UpgradeGroup: " + getName());
        upgradeGroups.add(this);
    }

    public String getName() {
        return name;
    }

    public List<TeamUpgrade> getTeamUpgrades() {
        return teamUpgrades;
    }

    public static UpgradeGroup getUpgradeGroup(String search){
        UpgradeGroup def = null;
        for (UpgradeGroup ug : upgradeGroups){
            if (ug.getName().equalsIgnoreCase(search)){
                return ug;
            } else if (ug.getName().equalsIgnoreCase("Default")){
                def = ug;
            }
        }
        return def;
    }

    public void openToPlayer(Player p, IArena a){
        Inventory inv = Bukkit.createInventory(p, BedWars.upgrades.getInt("settings.invSize"), getMsg(p, "upgrades."+getName()+".name"));
        ITeam team = a.getTeam(p);

        for (TeamUpgrade tu : getTeamUpgrades()){
            inv.setItem(tu.getSlot(), tu.getTiers().get(team.getUpgradeTier().containsKey(tu.getSlot()) ? team.getUpgradeTier().get(tu.getSlot()) < tu.getTiers().size()-1 ? team.getUpgradeTier().get(tu.getSlot())+1 : team.getUpgradeTier().get(tu.getSlot()) : 0).getItemStack(p, "upgrades."+getName()+"."+tu.getName(), tu, team));
        }

        p.openInventory(inv);
    }


    public static List<UpgradeGroup> getUpgradeGroups() {
        return upgradeGroups;
    }
}
