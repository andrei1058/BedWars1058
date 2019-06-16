package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.*;
import com.andrei1058.bedwars.api.events.PlayerKillEvent;
import com.andrei1058.bedwars.arena.*;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.language.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Iterator;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.arena.LastHit.getLastHit;
import static com.andrei1058.bedwars.language.Language.getMsg;

public class DamageDeathMove implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            Arena a = Arena.getArenaByPlayer(p);
            if (a != null) {
                if (a.isSpectator(p)) {
                    e.setCancelled(true);
                    return;
                }
                if (a.getStatus() != GameState.playing) {
                    e.setCancelled(true);
                    return;
                }
                if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                    e.setDamage(1);
                    return;
                }
                if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    if (a.getTeam(p) != null) {
                        if (p.getLocation().getBlock().equals(a.getTeam(p).getSpawn().getBlock())) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
        if (Main.getServerType() != ServerType.BUNGEE) {
            if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(Main.getLobbyWorld())) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity().hasMetadata("DragonTeam")) {
            Arena a = Arena.getArenaByName(e.getEntity().getWorld().getName());
            if (a != null) {
                e.setCancelled(true);
                return;
            }
        }
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            Arena a = Arena.getArenaByPlayer(p);
            if (a != null) {
                if (a.getSpectators().contains(p)) {
                    e.setCancelled(true);
                    return;
                }
                if (a.getStatus() != GameState.playing) {
                    e.setCancelled(true);
                    return;
                }
                if (a.isSpectator(p)) {
                    e.setCancelled(true);
                    return;
                }
                if (e.getDamager() instanceof Player) {
                    if (a.isSpectator((Player) e.getDamager())) {
                        e.setCancelled(true);
                        return;
                    }
                }
                if (a.getRespawn().containsKey(p)) {
                    e.setCancelled(true);
                    return;
                }
                Player damager = null;
                if (e.getDamager() instanceof Projectile) {
                    ProjectileSource shooter = ((Projectile) e.getDamager()).getShooter();
                    if (shooter instanceof Player) {
                        damager = (Player) shooter;
                    } else return;
                } else if (e.getDamager() instanceof Player) {
                    damager = (Player) e.getDamager();
                    if (a.getRespawn().containsKey(damager)) {
                        e.setCancelled(true);
                        return;
                    }
                } else if (e.getDamager() instanceof TNTPrimed) {
                    TNTPrimed tnt = (TNTPrimed) e.getDamager();
                    if (tnt.getSource() instanceof Player) {
                        damager = (Player) tnt.getSource();
                    } else return;
                }
                if (damager != null) {
                    if (a.isSpectator(damager)) return;
                    if (getLastHit().containsKey(p)) {
                        LastHit lh = getLastHit().get(p);
                        lh.setDamager(damager);
                        lh.setTime(System.currentTimeMillis());
                    } else {
                        new LastHit(p, damager, System.currentTimeMillis());
                    }

                    // #274
                    if (p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                        for (Player on : a.getPlayers()) {
                            Main.nms.showArmor(p, on);
                        }
                    }
                    //
                }
                if ((e.getDamager() instanceof Silverfish) || (e.getDamager() instanceof IronGolem)) {
                    if (getLastHit().containsKey(p)) {
                        LastHit lh = getLastHit().get(p);
                        lh.setDamager(e.getDamager());
                        lh.setTime(System.currentTimeMillis());
                    } else {
                        new LastHit(p, e.getDamager(), System.currentTimeMillis());
                    }
                }
                for (BedWarsTeam t : a.getTeams()) {
                    if (t.isMember(p) && t.isMember(damager)) {
                        if (!(e.getDamager() instanceof TNTPrimed)) {
                            e.setCancelled(true);
                        }
                        return;
                    }
                }
            }
        } else if (e.getEntity() instanceof Silverfish) {
            Player damager;
            if (e.getDamager() instanceof Player) {
                damager = (Player) e.getDamager();
            } else if (e.getDamager() instanceof Projectile) {
                Projectile proj = (Projectile) e.getDamager();
                damager = (Player) proj.getShooter();
            } else if (e.getDamager() instanceof TNTPrimed) {
                TNTPrimed tnt = (TNTPrimed) e.getDamager();
                if (tnt.getSource() instanceof Player) {
                    damager = (Player) tnt.getSource();
                } else return;
            } else return;
            Arena a = Arena.getArenaByPlayer(damager);
            if (a != null) {
                if (a.isPlayer(damager)) {
                    if (nms.isDespawnable(e.getEntity())) {
                        if (a.getTeam(damager) == nms.ownDespawnable(e.getEntity())) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        } else if (e.getEntity() instanceof IronGolem) {
            Player damager;
            if (e.getDamager() instanceof Player) {
                damager = (Player) e.getDamager();
            } else if (e.getDamager() instanceof Projectile) {
                Projectile proj = (Projectile) e.getDamager();
                damager = (Player) proj.getShooter();
            } else {
                return;
            }
            Arena a = Arena.getArenaByPlayer(damager);
            if (a != null) {
                if (a.isPlayer(damager)) {
                    if (nms.isDespawnable(e.getEntity())) {
                        if (a.getTeam(damager) == nms.ownDespawnable(e.getEntity())) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
        if (Main.getServerType() != ServerType.BUNGEE) {
            if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(Main.getLobbyWorld())) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        Player victim = e.getEntity(), killer = e.getEntity().getKiller();
        BedWarsTeam t2 = null;
        Arena a = Arena.getArenaByPlayer(victim);
        if (a != null) {
            if (a.isSpectator(victim)) {
                victim.spigot().respawn();
                return;
            }
            if (a.getStatus() != GameState.playing) {
                victim.spigot().respawn();
                return;
            }
            EntityDamageEvent damageEvent = e.getEntity().getLastDamageCause();

            ItemStack[] drops = victim.getInventory().getContents();
            if (!a.getCm().getBoolean(ConfigPath.ARENA_NORMAL_DEATH_DROPS)) {
                e.getDrops().clear();
            }

            BedWarsTeam t = a.getTeam(victim);
            if (a.getStatus() != GameState.playing) {
                victim.spigot().respawn();
                return;
            }
            if (t == null) {
                victim.spigot().respawn();
                return;
            }
            String message = t.isBedDestroyed() ? Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL : Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR;
            com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause cause = t.isBedDestroyed() ? com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause.UNKNOWN_FINAL_KILL : com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause.UNKNOWN;
            if (damageEvent.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                LastHit lh = getLastHit().get(victim);
                if (lh != null) {
                    if (lh.getTime() >= System.currentTimeMillis() - 15000) {
                        if (lh.getDamager() instanceof Player) killer = (Player) lh.getDamager();
                    }
                }
                if (killer == null) {
                    message = t.isBedDestroyed() ? Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL : Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR;
                } else {
                    if (killer != victim) {
                        message = t.isBedDestroyed() ? Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL : Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL;
                    } else {
                        message = t.isBedDestroyed() ? Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL : Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR;
                    }
                }
                cause = t.isBedDestroyed() ? com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause.EXPLOSION_FINAL_KILL : com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause.EXPLOSION;

            } else if (damageEvent.getCause() == EntityDamageEvent.DamageCause.VOID) {
                LastHit lh = getLastHit().get(victim);
                if (lh != null) {
                    if (lh.getTime() >= System.currentTimeMillis() - 15000) {
                        if (lh.getDamager() instanceof Player) killer = (Player) lh.getDamager();
                    }
                }
                if (killer == null) {
                    message = t.isBedDestroyed() ? Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL : Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL;
                } else {
                    if (killer != victim) {
                        message = t.isBedDestroyed() ? Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL : Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL;
                    } else {
                        message = t.isBedDestroyed() ? Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL : Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL;
                    }
                }
                cause = t.isBedDestroyed() ? com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause.VOID_FINAL_KILL : com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause.VOID;
            } else if (damageEvent.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                if (killer == null) {
                    LastHit lh = getLastHit().get(victim);
                    if (lh != null) {
                        if (lh.getTime() >= System.currentTimeMillis() - 15000) {
                            if ((lh.getDamager() instanceof Silverfish)) {
                                if (nms.isDespawnable(lh.getDamager())) {
                                    t2 = nms.ownDespawnable(lh.getDamager());
                                    message = t.isBedDestroyed() ? Messages.PLAYER_DIE_DEBUG_FINAL_KILL : Messages.PLAYER_DIE_DEBUG_REGULAR;
                                    cause = t.isBedDestroyed() ? com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause.SILVERFISH_FINAL_KILL : com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause.SILVERFISH;
                                }
                                killer = null;
                            } else if (lh.getDamager() instanceof IronGolem) {
                                if (nms.isDespawnable(lh.getDamager())) {
                                    t2 = nms.ownDespawnable(lh.getDamager());
                                    message = t.isBedDestroyed() ? Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL : Messages.PLAYER_DIE_IRON_GOLEM_REGULAR;
                                    cause = t.isBedDestroyed() ? com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause.IRON_GOLEM_FINAL_KILL : com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause.IRON_GOLEM;
                                }
                                killer = null;
                            }
                        }
                    }
                } else {
                    message = t.isBedDestroyed() ? Messages.PLAYER_DIE_PVP_FINAL_KILL : Messages.PLAYER_DIE_PVP_REGULAR_KILL;
                    cause = t.isBedDestroyed() ? com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause.PVP_FINAL_KILL : com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause.PVP;
                }
            } else if (damageEvent.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                if (killer != null) {
                    message = t.isBedDestroyed() ? Messages.PLAYER_DIE_SHOOT_FINAL_KILL : Messages.PLAYER_DIE_SHOOT_REGULAR;
                    cause = t.isBedDestroyed() ? com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause.PLAYER_SHOOT_FINAL_KILL : com.andrei1058.bedwars.api.events.PlayerKillEvent.PlayerKillCause.PLAYER_SHOOT;
                }
            }

            if (killer != null) t2 = a.getTeam(killer);

            for (Player on : a.getPlayers()) {
                on.sendMessage(getMsg(on, message).
                        replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName())
                        .replace("{KillerColor}", t2 == null ? "" : TeamColor.getChatColor(t2.getColor()).toString())
                        .replace("{KillerName}", killer == null ? "" : killer.getName())
                        .replace("{KillerTeamName}", t2 == null ? "" : t2.getName()));
            }
            for (Player on : a.getSpectators()) {
                on.sendMessage(getMsg(on, message).
                        replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName())
                        .replace("{KillerColor}", t2 == null ? "" : TeamColor.getChatColor(t2.getColor()).toString())
                        .replace("{KillerName}", killer == null ? "" : killer.getName())
                        .replace("{KillerTeamName}", t2 == null ? "" : t2.getName()));
            }

            /** give stats and victim's inventory */
            if (killer != null) {
                if (t.isBedDestroyed()) {
                    if (!a.getCm().getBoolean(ConfigPath.ARENA_NORMAL_DEATH_DROPS)) {
                        for (ItemStack i : drops) {
                            if (i == null) continue;
                            if (i.getType() == Material.AIR) continue;
                            if (nms.isArmor(i) || nms.isBow(i) || nms.isSword(i) || nms.isTool(i)) continue;
                            if (a.getTeam(killer) != null) {
                                killer.getWorld().dropItemNaturally(a.getTeam(killer).getIronGenerator().getLocation(), i);
                            }
                        }
                    }
                    a.addPlayerKill(killer, true, victim);
                } else {
                    if (!a.getCm().getBoolean(ConfigPath.ARENA_NORMAL_DEATH_DROPS)) {
                        if (!a.getRespawn().containsKey(killer)) {
                            for (ItemStack i : drops) {
                                if (i == null) continue;
                                if (i.getType() == Material.AIR) continue;
                                if (i.getType() == Material.DIAMOND || i.getType() == Material.EMERALD || i.getType() == Material.IRON_INGOT || i.getType() == Material.GOLD_INGOT) {
                                    killer.getInventory().addItem(i);
                                    String msg = "";
                                    int amount = i.getAmount();
                                    switch (i.getType()) {
                                        case DIAMOND:
                                            msg = getMsg(killer, Messages.PLAYER_DIE_REWARD_DIAMOND).replace("{meaning}", amount == 1 ?
                                                    getMsg(killer, Messages.MEANING_DIAMOND_SINGULAR) : getMsg(killer, Messages.MEANING_DIAMOND_PLURAL));
                                            break;
                                        case EMERALD:
                                            msg = getMsg(killer, Messages.PLAYER_DIE_REWARD_EMERALD).replace("{meaning}", amount == 1 ?
                                                    getMsg(killer, Messages.MEANING_EMERALD_SINGULAR) : getMsg(killer, Messages.MEANING_EMERALD_PLURAL));
                                            break;
                                        case IRON_INGOT:
                                            msg = getMsg(killer, Messages.PLAYER_DIE_REWARD_IRON).replace("{meaning}", amount == 1 ?
                                                    getMsg(killer, Messages.MEANING_IRON_SINGULAR) : getMsg(killer, Messages.MEANING_IRON_PLURAL));
                                            break;
                                        case GOLD_INGOT:
                                            msg = getMsg(killer, Messages.PLAYER_DIE_REWARD_GOLD).replace("{meaning}", amount == 1 ?
                                                    getMsg(killer, Messages.MEANING_GOLD_SINGULAR) : getMsg(killer, Messages.MEANING_GOLD_PLURAL));
                                            break;
                                    }
                                    killer.sendMessage(msg.replace("{amount}", String.valueOf(amount)));
                                }
                            }
                        }
                    }
                    a.addPlayerKill(killer, false, victim);
                }
                killer.playSound(killer.getLocation(), nms.playerKill(), 1f, 1f);
            }

            /** call game kill event */
            Bukkit.getPluginManager().callEvent(new PlayerKillEvent(a, victim, killer, message, cause));
            victim.spigot().respawn();
            a.addPlayerDeath(victim);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRespawn(PlayerRespawnEvent e) {
        Arena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a == null) {
            SetupSession ss = SetupSession.getSession(e.getPlayer());
            if (ss != null) {
                e.setRespawnLocation(e.getPlayer().getWorld().getSpawnLocation());
            }
        } else {
            e.setRespawnLocation(a.getCm().getArenaLoc("waiting.Loc"));
            BedWarsTeam t = a.getTeam(e.getPlayer());
            if (t.isBedDestroyed()) {
                a.addSpectator(e.getPlayer(), true, null);
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    t.getMembers().remove(e.getPlayer());
                    e.getPlayer().sendMessage(getMsg(e.getPlayer(), Messages.PLAYER_DIE_ELIMINATED_CHAT));
                    if (t.getMembers().isEmpty()) {
                        for (Player p : a.getWorld().getPlayers()) {
                            p.sendMessage(getMsg(p, Messages.TEAM_ELIMINATED_CHAT).replace("{TeamColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{TeamName}", t.getName()));
                        }
                        a.checkWinner();
                    }
                }, 40L);
            } else {
                //respawn session
                e.getPlayer().getInventory().clear();
                Bukkit.getScheduler().runTaskLater(plugin, () -> nms.hidePlayer(e.getPlayer(), a.getPlayers()), 5L);
                nms.setCollide(e.getPlayer(), a, false);
                e.getPlayer().setAllowFlight(true);
                e.getPlayer().setFlying(true);
                a.getRespawn().put(e.getPlayer(), 5);
                for (SBoard sb : SBoard.getScoreboards()) {
                    if (sb.getArena() == a) {
                        sb.giveTeamColorTag();
                    }
                }

                Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                    // #274
                    for (Player p : a.getShowTime().keySet()) {
                        Main.nms.hideArmor(p, e.getPlayer());
                    }
                    //
                }, 10L);
            }
            for (SBoard sb : SBoard.getScoreboards()) {
                if (sb.getArena() == a) {
                    sb.giveTeamColorTag();
                    sb.updateSpectators(e.getPlayer(), false);
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (Arena.isInArena(e.getPlayer())) {
            Arena a = Arena.getArenaByPlayer(e.getPlayer());
            if (e.getFrom().getChunk() != e.getTo().getChunk()) {

                /** update armorstands hidden by nms **/
                String iso = Language.getPlayerLanguage(e.getPlayer()).getIso();
                for (OreGenerator o : a.getOreGenerators()) {
                    if (o.getArena() == a) {
                        o.updateHolograms(e.getPlayer(), iso);
                    }
                }
                for (ShopHolo sh : ShopHolo.getShopHolo()) {
                    if (sh.getA() == a) {
                        sh.updateForPlayer(e.getPlayer(), iso);
                    }
                }

                /** Check if respawning */
                if (a.getRespawn().containsKey(e.getPlayer())) {
                    for (Player p : a.getPlayers()) {
                        if (p == e.getPlayer()) continue;
                        nms.hidePlayer(e.getPlayer(), p);
                    }
                } else {
                    for (Player p : a.getPlayers()) {
                        if (a.getRespawn().containsKey(p)) {
                            nms.hidePlayer(e.getPlayer(), p);
                        }
                    }
                }
            }

            if (a.isSpectator(e.getPlayer()) || a.isRespawning(e.getPlayer())) {
                if (e.getTo().getY() < 0) {
                    e.getPlayer().teleport(a.getCm().getArenaLoc("waiting.Loc"));
                }
                return;
            } else {
                if (e.getPlayer().getLocation().getY() <= 0) {
                    if (a.getStatus() == GameState.playing) {
                        if (a.getCm().getBoolean("voidKill")) {
                            nms.voidKill(e.getPlayer());
                        }
                    } else {
                        BedWarsTeam bwt = a.getTeam(e.getPlayer());
                        if (bwt != null) {
                            e.getPlayer().teleport(bwt.getSpawn());
                        } else {
                            e.getPlayer().teleport(a.getCm().getArenaLoc("waiting.Loc"));
                        }
                    }
                }

                if (a.getStatus() == GameState.playing) {
                    for (BedWarsTeam t : a.getTeams()) {
                        if (e.getPlayer().getLocation().distance(t.getBed()) < 4) {
                            if (t.isMember(e.getPlayer())) {
                                if (t.getBedHolo(e.getPlayer()) == null) continue;
                                if (!t.getBedHolo(e.getPlayer()).isHidden()) {
                                    t.getBedHolo(e.getPlayer()).hide();
                                }
                            }
                        } else {
                            if (t.isMember(e.getPlayer())) {
                                if (t.getBedHolo(e.getPlayer()) == null) continue;
                                if (t.getBedHolo(e.getPlayer()).isHidden()) {
                                    t.getBedHolo(e.getPlayer()).show();
                                }
                            }
                        }
                    }
                    if (e.getFrom() != e.getTo()) {
                        Arena.afkCheck.remove(e.getPlayer());
                        if (Main.api.isPlayerAFK(e.getPlayer())) {
                            Main.api.setPlayerAFK(e.getPlayer(), false);
                        }
                    }
                }
            }
        } else {
            if (e.getPlayer().getWorld().getName().equalsIgnoreCase(config.getLobbyWorldName()) && Main.getServerType() == ServerType.MULTIARENA) {
                if (e.getTo().getY() < 0) {
                    e.getPlayer().teleport(config.getConfigLoc("lobbyLoc"));
                }
                return;
            }
        }
    }

    @EventHandler
    public void onProjHit(ProjectileHitEvent e) {
        Projectile proj = e.getEntity();
        if (e.getEntity().getShooter() instanceof Player) {
            Arena a = Arena.getArenaByPlayer((Player) e.getEntity().getShooter());
            if (a != null) {
                if (e.getEntity() instanceof Fireball) {
                    Location l = e.getEntity().getLocation();
                    e.getEntity().getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), 3, false, true);
                    return;
                }
                String utility = "";
                if (proj instanceof Snowball) {
                    utility = "silverfish";
                }
                if (!utility.isEmpty()) {
                    spawnUtility(utility, e.getEntity().getLocation(), a.getTeam((Player) e.getEntity().getShooter()), (Player) e.getEntity().getShooter());
                }
            }
        }
    }

    @EventHandler
    public void onItemFrameDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity().getType() == EntityType.ITEM_FRAME) {
            Arena a = Arena.getArenaByName(e.getEntity().getWorld().getName());
            if (a != null) {
                e.setCancelled(true);
            }
            if (Main.getServerType() != ServerType.SHARED) {
                if (Main.getLobbyWorld().equals(e.getEntity().getWorld().getName())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (Arena.getArenaByName(e.getEntity().getLocation().getWorld().getName()) != null) {
            if (e.getEntityType() == EntityType.IRON_GOLEM || e.getEntityType() == EntityType.SILVERFISH) {
                e.getDrops().clear();
                e.setDroppedExp(0);
            }
        }
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() == nms.materialCake()) {
            if (Arena.getArenaByName(e.getPlayer().getWorld().getName()) != null) {
                e.setCancelled(true);
            }
        }
    }

    /*private static String getUtility(Material mat) {
        for (String st : Arrays.asList("silverfish", "bridge")) {
            if (shop.getYml().getBoolean(ConfigPath.SHOPSPECIALS))
            if (shop.getBoolean("utilities." + st + ".enableRotation")) {
                if (mat == Material.valueOf(shop.getYml().getString("utilities." + st + ".material"))) {
                    return st;
                }
            }
        }
        return "";
    }*/

    private static void spawnUtility(String s, Location loc, BedWarsTeam t, Player p) {
        switch (s.toLowerCase()) {
            case "silverfish":
                nms.spawnSilverfish(loc, t);
                break;
        }
    }
}
