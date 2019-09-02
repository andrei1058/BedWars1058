package com.andrei1058.bedwars.upgrades;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.generator.GeneratorType;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.arena.OreGenerator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GeneratorAction extends UpgradeAction {

    private int ironAmount = 0, goldAmount = 0, emeraldAmount = 0;
    private int ironDelay = 0, goldDelay = 0, emeraldDelay = 0;
    private int ironLimit = 0, goldLimit = 0;
    private String name;

    public GeneratorAction(String name){
        this.name = name;
    }

    @Override
    public void execute(BedWarsTeam bwt, int i) {
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
        if (ironLimit > 0){
            bwt.getIronGenerator().setSpawnLimit(ironLimit);
        }
        if (goldLimit > 0){
            bwt.getGoldGenerator().setSpawnLimit(goldLimit);
        }
        if (emeraldAmount > 0 && emeraldDelay > 0 && bwt.getEmeraldGenerator() == null){
            OreGenerator g = new OreGenerator(bwt.getIronGenerator().getLocation(), bwt.getArena(), GeneratorType.IRON, bwt);
            g.setDelay(emeraldDelay);
            g.setAmount(emeraldAmount);
            g.setOre(new ItemStack(Material.EMERALD));
            g.setType(GeneratorType.EMERALD);
            bwt.setEmeraldGenerator(g);
            //bwt.getEmeraldGenerator().enableRotation();
            bwt.getArena().getOreGenerators().add(g);
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
        BedWars.debug("loading new GeneratorAction: "+getName());
    }

    public void setLimit(String generator, int amount){
        switch (generator.toLowerCase()){
            case "iron":
                ironLimit = amount;
                break;
            case "gold":
                goldLimit = amount;
                break;
        }
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
