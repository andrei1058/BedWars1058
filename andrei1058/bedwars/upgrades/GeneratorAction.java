package com.andrei1058.bedwars.upgrades;

import com.andrei1058.bedwars.api.GeneratorType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.arena.OreGenerator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static com.andrei1058.bedwars.Main.plugin;

public class GeneratorAction extends UpgradeAction {

    private int ironAmount = 0, goldAmount = 0, emeraldAmount = 0;
    private int ironDelay = 0, goldDelay = 0, emeraldDelay = 0;
    private String name;

    public GeneratorAction(String name){
        this.name = name;
    }

    @Override
    public void execute(BedWarsTeam bwt) {
        if (ironAmount > 0){
            bwt.getIronGenerator().setAmount(ironAmount);
        }
        if (goldAmount > 0){
            bwt.getGoldGenerator().setAmount(goldAmount);
        }
        if (ironDelay > 0){
            bwt.getIronGenerator().setDelay(ironDelay);
        }
        if (goldDelay > 0){
            bwt.getGoldGenerator().setDelay(goldDelay);
        }
        if (emeraldAmount > 0 && emeraldDelay > 0 && bwt.getEmeraldGenerator() == null){
            bwt.setEmeraldGenerator(new OreGenerator(bwt.getIronGenerator().getLocation(), Arena.getArenaByPlayer(bwt.getMembers().get(0)), GeneratorType.IRON));
            bwt.getEmeraldGenerator().setDelay(emeraldDelay);
            bwt.getEmeraldGenerator().setAmount(emeraldAmount);
            bwt.getEmeraldGenerator().setOre(new ItemStack(Material.EMERALD));
        } else {
            if (emeraldDelay > 0){
                bwt.getEmeraldGenerator().setDelay(emeraldDelay);
            }
            if (emeraldAmount > 0){
                bwt.getEmeraldGenerator().setAmount(emeraldAmount);
            }
        }
    }

    public void setAmount(String generator, int amount) {
        switch (generator.toLowerCase()){
            case "iron":
                ironAmount = amount;
                break;
            case "gold":
                goldAmount = amount;
                break;
            case "emerald":
                emeraldAmount = amount;
                break;
        }
        plugin.debug("loading new GeneratorAction: "+getName());
    }

    public void setDelay(String generator, int delay) {
        switch (generator.toLowerCase()){
            case "iron":
                ironDelay = delay;
                break;
            case "gold":
                goldDelay = delay;
                break;
            case "emerald":
                emeraldDelay = delay;
                break;
        }
    }

    public boolean isSomethingLoaded(){
        for (Integer i : new int[]{ironDelay, goldDelay, emeraldDelay, ironAmount, goldAmount, emeraldAmount}){
            if (i != 0) return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }
}
