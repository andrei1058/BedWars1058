package com.andrei1058.bedwars.upgrades;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.Main.plugin;

public class TeamUpgrade {

    /**
     * upgrades.yml - Documentation
     * settings:
     * invSize: 27
     * <p>
     * upgrade1:
     * slot: 1
     * whaterName:
     * DisplayItem:
     * material: DIAMOND
     * data: 0
     * amount: 1
     * enchanted: false
     * Value: emerald/ gold/ iron/ vault
     * Cost: 20
     * Receive:
     * PlayerEffect:
     * Effect1:
     * effect: INVISIBILITY
     * amplifier: 1
     * apply: members/ base/ enemyBaseEnter (base effects are on the team's island, the others are permanent)
     * ItemEnchant:
     * Enchant1:
     * enchant: SHARPNESS
     * amplifier: 1
     * apply: sword/ bow/ armor
     * TeamGenerator:
     * iron:
     * delay: 100
     * amount: 1
     * gold:
     * delay: 150
     * amount: 1
     * emerald:
     * delay: 200
     * amount: 1
     * EnemyBaseEnter:
     * announce:
     * - CHAT
     * - ACTION
     * - TITLE
     * - SUBTITLE
     * GameEnd:
     * dragonBonus: 1
     * Tier2: [] etc
     */

    private List<UpgradeTier> tiers;
    private int slot;
    private String name;

    public TeamUpgrade(String name, int slot, List<UpgradeTier> tiers) {
        this.name = name;
        this.slot = slot;
        this.tiers = new ArrayList<>(tiers);
        plugin.debug("Loading new TeamUpgrade: " + name);
    }

    public int getSlot() {
        return slot;
    }

    public List<UpgradeTier> getTiers() {
        return tiers;
    }

    public String getName() {
        return name;
    }

    public void doAction(Player p, BedWarsTeam bwt) {
        int tier = -1;
        if (bwt.getUpgradeTier().containsKey(getSlot())) {
            tier = bwt.getUpgradeTier().get(getSlot());
        }
        if (getTiers().size()-1 > tier) {
            if (getTiers().get(tier+1).buy(p, bwt)) {
                if (tier < getTiers().size()) {
                    if (bwt.getUpgradeTier().containsKey(getSlot())) {
                        bwt.getUpgradeTier().replace(getSlot(), tier + 1);
                    } else {
                        bwt.getUpgradeTier().put(getSlot(), 0);
                    }
                }
            }
        }
        //todo msg ai deja upgrade maxim
    }
}
