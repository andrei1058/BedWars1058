package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.configuration.ConfigPath;
import net.citizensnpcs.api.event.NPCRemoveEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class CitizensListener implements Listener {

    @EventHandler
    public void removeNPC(NPCRemoveEvent e) {
        List<String> locations = Main.config.getYml().getStringList("npcLoc");
        boolean removed = false;
        if (Main.npcs.keySet().contains(e.getNPC().getId())) {
            Main.npcs.remove(e.getNPC().getId());
            removed = true;
        }
        for (String s : new ArrayList<>(locations)) {
            String[] data = s.split(",");
            if (data.length >= 10) {
                if (Misc.isNumber(data[9])) {
                    if (Integer.valueOf(data[9]) == e.getNPC().getId()) {
                        locations.remove(s);
                        removed = true;
                    }
                }
            }
        }
        for (Entity e2 : e.getNPC().getEntity().getNearbyEntities(0, 3, 0)) {
            if (e2.getType() == EntityType.ARMOR_STAND) {
                e2.remove();
            }
        }
        if (removed) Main.config.set(ConfigPath.GENERAL_CONFIGURATION_NPC_LOCS, locations);

    }

}
