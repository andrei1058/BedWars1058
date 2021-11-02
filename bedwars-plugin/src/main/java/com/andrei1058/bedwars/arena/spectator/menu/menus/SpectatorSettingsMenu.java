package com.andrei1058.bedwars.arena.spectator.menu.menus;

import java.util.Arrays;
import com.andrei1058.bedwars.arena.spectator.menu.Menu;
import com.andrei1058.bedwars.arena.spectator.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpectatorSettingsMenu extends Menu {
    public SpectatorSettingsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public String getMenuName() {
        return "&8Spectator Settings";
    }

    public int getSlots() {
        return 36;
    }

    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        int slot = e.getSlot();
        if (e.getCurrentItem() == null)
            return;
        if (slot == 11) {
            player.removePotionEffect(PotionEffectType.SPEED);
        } else if (Arrays.<Integer>asList(new Integer[] { Integer.valueOf(12), Integer.valueOf(13), Integer.valueOf(14), Integer.valueOf(15) }).contains(Integer.valueOf(slot))) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, (slot == 12) ? 0 : ((slot == 13) ? 1 : ((slot == 14) ? 2 : 3))));
        } else if (slot == 22) {
            if (this.playerMenuUtility.isNightVision()) {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                this.playerMenuUtility.setNightVision(false);
            } else {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2147483647, 1));
                this.playerMenuUtility.setNightVision(true);
            }
        }
        if (e.getCurrentItem().getType() != Material.AIR)
            player.closeInventory();
    }

    public void setMenuItems() {
        this.inventory.setItem(11, makeItem(Material.LEATHER_BOOTS, "&aNo Speed", new String[0]));
        this.inventory.setItem(12, makeItem(Material.CHAINMAIL_BOOTS, "&aSpeed I", new String[0]));
        this.inventory.setItem(13, makeItem(Material.IRON_BOOTS, "&aSpeed II", new String[0]));
        this.inventory.setItem(14, makeItem(Material.GOLDEN_BOOTS, "&aSpeed III", new String[0]));
        this.inventory.setItem(15, makeItem(Material.DIAMOND_BOOTS, "&aSpeed IV", new String[0]));
        this.inventory.setItem(22, makeItem(Material.ENDER_PEARL, this.playerMenuUtility.isNightVision() ? "&cDisable Night Vision" : "&aEnable Night Vision", new String[] { this.playerMenuUtility.isNightVision() ? "&7Click to disable night vision!" : "&7Click to enable night vision!" }));
    }
}
