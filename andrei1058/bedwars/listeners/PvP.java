package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.*;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.arena.LastHit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.arena.LastHit.getLastHit;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class PvP implements Listener {

    @EventHandler
    public void p(EntityDamageEvent e) {
        if (Main.npcs.containsKey(e.getEntity())) {
            e.setCancelled(true);
        }
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            Arena a = Arena.getArenaByPlayer(p);
            if (a != null) {
                if (a.isSpectator(p)) {
                    e.setCancelled(true);
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
        if (getServerType() != ServerType.SHARED) {
            if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(config.getLobbyWorldName()))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void c(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            Arena a = Arena.getArenaByPlayer(p);
            if (a != null) {
                if (a.getStatus() != GameState.playing) {
                    e.setCancelled(true);
                    return;
                }
                if (a.isSpectator(p)) {
                    e.setCancelled(true);
                    return;
                }
                if (e.getDamager() instanceof  Player) {
                    if (a.isSpectator((Player) e.getDamager())) {
                        e.setCancelled(true);
                        return;
                    }
                }
                if (a.respawn.containsKey(p)) {
                    e.setCancelled(true);
                    return;
                }
                Player damager;
                if (e.getDamager() instanceof Projectile) {
                    ProjectileSource shooter = ((Projectile) e.getDamager()).getShooter();
                    if (shooter instanceof Player) {
                        damager = (Player) shooter;
                    } else return;
                } else if (e.getDamager() instanceof Player) {
                    damager = (Player) e.getDamager();
                } else return;
                if (a.isSpectator(damager)) return;
                for (BedWarsTeam t : a.getTeams()) {
                    if (t.isMember(p) && t.isMember(damager)) {
                        e.setCancelled(true);
                        return;
                    }
                }
                if (getLastHit().containsKey(p)) {
                    LastHit lh = getLastHit().get(p);
                    lh.setDamager(damager);
                    lh.setTime(System.currentTimeMillis());
                } else {
                    new LastHit(p, damager, System.currentTimeMillis());
                }
            }
        }
        if (getServerType() != ServerType.SHARED) {
            if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(config.getLobbyWorldName()))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void ed(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player victim = (Player) e.getEntity(), killer = e.getEntity().getKiller();
            if (Arena.isInArena(victim)) {
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
                    BedWarsTeam t = a.getTeam(victim);
                    ItemStack[] drops = victim.getInventory().getContents();
                    e.getDrops().clear();
                    if (damageEvent.getCause() == EntityDamageEvent.DamageCause.VOID) {
                        killer = null;
                        LastHit lh = getLastHit().get(victim);
                        if (lh != null) {
                            if (lh.getTime() >= System.currentTimeMillis() - 15000) {
                                killer = lh.getDamager();
                            }
                        }
                        if (t.isBedDestroyed()) {
                            if (killer == null) {
                                for (Player on : a.getPlayers()) {
                                    on.sendMessage(getMsg(on, lang.playerFallInVoidFinalKill).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName()));
                                }
                                for (Player on : a.getSpectators()) {
                                    on.sendMessage(getMsg(on, lang.playerFallInVoidFinalKill).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName()));
                                }
                            } else {
                                BedWarsTeam t2 = a.getTeam(killer);
                                for (Player on : a.getPlayers()) {
                                    on.sendMessage(getMsg(on, lang.playerKnockedInVoidFinalKill).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName())
                                            .replace("{KillerColor}", TeamColor.getChatColor(t2.getColor()).toString()).replace("{KillerName}", killer.getName()));
                                }
                                for (Player on : a.getSpectators()) {
                                    on.sendMessage(getMsg(on, lang.playerKnockedInVoidFinalKill).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName())
                                            .replace("{KillerColor}", TeamColor.getChatColor(t2.getColor()).toString()).replace("{KillerName}", killer.getName()));
                                }
                            }
                        } else {
                            if (killer == null) {
                                for (Player on : a.getPlayers()) {
                                    on.sendMessage(getMsg(on, lang.playerFallInVoid).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName()));
                                }
                                for (Player on : a.getSpectators()) {
                                    on.sendMessage(getMsg(on, lang.playerFallInVoid).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName()));
                                }
                            } else {
                                BedWarsTeam t2 = a.getTeam(killer);
                                for (Player on : a.getPlayers()) {
                                    on.sendMessage(getMsg(on, lang.playerKnockedInVoid).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName())
                                            .replace("{KillerColor}", TeamColor.getChatColor(t2.getColor()).toString()).replace("{KillerName}", killer.getName()));
                                }
                                for (Player on : a.getSpectators()) {
                                    on.sendMessage(getMsg(on, lang.playerKnockedInVoid).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName())
                                            .replace("{KillerColor}", TeamColor.getChatColor(t2.getColor()).toString()).replace("{KillerName}", killer.getName()));
                                }
                            }
                        }
                    } else if (damageEvent.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || damageEvent.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                        if (t.isBedDestroyed()) {
                            if (killer == null) {
                                for (Player on : a.getPlayers()) {
                                    on.sendMessage(getMsg(on, lang.playerExplodeByBombFinalKill).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName()));
                                }
                                for (Player on : a.getSpectators()) {
                                    on.sendMessage(getMsg(on, lang.playerExplodeByBombFinalKill).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName()));
                                }
                            } else {
                                BedWarsTeam t2 = a.getTeam(killer);
                                for (Player on : a.getPlayers()) {
                                    on.sendMessage(getMsg(on, lang.playerExplodeByBombFinalKill).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName())
                                            .replace("{KillerColor}", TeamColor.getChatColor(t2.getColor()).toString()).replace("{KillerName}", killer.getName()));
                                }
                                for (Player on : a.getSpectators()) {
                                    on.sendMessage(getMsg(on, lang.playerExplodeByBombFinalKill).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName())
                                            .replace("{KillerColor}", TeamColor.getChatColor(t2.getColor()).toString()).replace("{KillerName}", killer.getName()));
                                }
                            }
                        } else {
                            if (killer == null) {
                                for (Player on : a.getPlayers()) {
                                    on.sendMessage(getMsg(on, lang.playerExplodeByBomb).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName()));
                                }
                                for (Player on : a.getSpectators()) {
                                    on.sendMessage(getMsg(on, lang.playerExplodeByBomb).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName()));
                                }
                            } else {
                                BedWarsTeam t2 = a.getTeam(killer);
                                for (Player on : a.getPlayers()) {
                                    on.sendMessage(getMsg(on, lang.playerExplodeByBomb).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName())
                                            .replace("{KillerColor}", TeamColor.getChatColor(t2.getColor()).toString()).replace("{KillerName}", killer.getName()));
                                }
                                for (Player on : a.getSpectators()) {
                                    on.sendMessage(getMsg(on, lang.playerExplodeByBomb).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName())
                                            .replace("{KillerColor}", TeamColor.getChatColor(t2.getColor()).toString()).replace("{KillerName}", killer.getName()));
                                }
                            }
                        }
                    } else if (damageEvent.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                        if (t.isBedDestroyed()) {
                            BedWarsTeam t2 = a.getTeam(killer);
                            for (Player on : a.getPlayers()) {
                                on.sendMessage(getMsg(on, lang.playerDieAttackFinalKill).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName())
                                        .replace("{KillerColor}", TeamColor.getChatColor(t2.getColor()).toString()).replace("{KillerName}", killer.getName()));
                            }
                            for (Player on : a.getSpectators()) {
                                on.sendMessage(getMsg(on, lang.playerDieAttackFinalKill).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName())
                                        .replace("{KillerColor}", TeamColor.getChatColor(t2.getColor()).toString()).replace("{KillerName}", killer.getName()));
                            }

                        } else {
                            BedWarsTeam t2 = a.getTeam(killer);
                            for (Player on : a.getPlayers()) {
                                on.sendMessage(getMsg(on, lang.playerDieAttack).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName())
                                        .replace("{KillerColor}", TeamColor.getChatColor(t2.getColor()).toString()).replace("{KillerName}", killer.getName()));
                            }
                            for (Player on : a.getSpectators()) {
                                on.sendMessage(getMsg(on, lang.playerDieAttack).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{PlayerName}", victim.getName())
                                        .replace("{KillerColor}", TeamColor.getChatColor(t2.getColor()).toString()).replace("{KillerName}", killer.getName()));
                            }
                        }
                    }
                    if (killer != null) {
                        if (t.isBedDestroyed()) {
                            for (ItemStack i : drops) {
                                if (i == null) continue;
                                if (i.getType() == Material.AIR) continue;
                                if (nms.isArmor(i) || nms.isBow(i) || nms.isSword(i) || nms.isTool(i)) continue;
                                if (a.getTeam(killer) != null) {
                                    killer.getWorld().dropItemNaturally(a.getTeam(killer).getIronGenerator().getLocation(), i);
                                }
                            }
                            a.addPlayerKill(killer, true);
                            //a.addPlayerKill(killer, false);
                            Bukkit.getPluginManager().callEvent(new FinalKillEvent(a.getWorldName(), victim, killer));
                        } else {
                            for (ItemStack i : drops) {
                                if (i == null) continue;
                                if (i.getType() == Material.AIR) continue;
                                if (i.getType() == Material.DIAMOND || i.getType() == Material.EMERALD || i.getType() == Material.IRON_INGOT || i.getType() == Material.GOLD_INGOT) {
                                    killer.getInventory().addItem(i);
                                }
                            }
                            a.addPlayerKill(killer, false);
                        }
                        killer.playSound(killer.getLocation(), nms.playerKill(), 1f, 1f);
                    }
                }
                victim.spigot().respawn();
            }
        }
    }

    @EventHandler
    public void pdd(PlayerDeathEvent e) {
        if (Arena.isInArena(e.getEntity())) {
            e.setDeathMessage(null);
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void re(PlayerRespawnEvent e) {
        Arena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a != null) {
            if (a.isSpectator(e.getPlayer())) {
                e.setRespawnLocation(a.getCm().getArenaLoc("waiting.Loc"));
                return;
            }
            if (a.getStatus() != GameState.playing) {
                e.setRespawnLocation(a.getCm().getArenaLoc("waiting.Loc"));
                return;
            }
            BedWarsTeam t = a.getTeam(e.getPlayer());
            if (t.isBedDestroyed()) {
                e.setRespawnLocation(a.getCm().getArenaLoc("waiting.Loc"));
                a.addSpectator(e.getPlayer(), true);
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    t.getMembers().remove(e.getPlayer());
                    e.getPlayer().sendMessage(getMsg(e.getPlayer(), lang.youReEliminated));
                    if (t.getMembers().isEmpty()) {
                        for (Player p : a.getWorld().getPlayers()) {
                            p.sendMessage(getMsg(p, lang.teamEliminatedChat).replace("{TeamColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{TeamName}", t.getName()));
                        }
                        a.checkWinner();
                    }
                }, 15L);
            } else {
                e.setRespawnLocation(a.getCm().getArenaLoc("waiting.Loc"));
                e.getPlayer().getInventory().clear();
                Bukkit.getScheduler().runTaskLater(plugin, ()-> nms.hidePlayer(e.getPlayer(), a.getPlayers()), 5L);
                e.getPlayer().spigot().setCollidesWithEntities(false);
                e.getPlayer().setAllowFlight(true);
                e.getPlayer().setFlying(true);
                Arena.respawn.put(e.getPlayer(), 5);
            }
        }
    }

    @EventHandler
    public void f(FoodLevelChangeEvent e) {
        if (e.getEntity().getWorld().getName().equalsIgnoreCase(config.getConfigLoc("lobbyLoc").getWorld().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void m(PlayerMoveEvent e) {
        if (e.getPlayer().getWorld().getName().equalsIgnoreCase(config.getLobbyWorldName())) {
            if (e.getTo().getY() < 0) {
                e.getPlayer().teleport(config.getConfigLoc("lobbyLoc"));
            }
            return;
        }
        if (Arena.isInArena(e.getPlayer())) {
            Arena a = Arena.getArenaByPlayer(e.getPlayer());
            if (a.isSpectator(e.getPlayer())) {
                if (e.getTo().getY() < 0) {
                    e.getPlayer().teleport(a.getCm().getArenaLoc("waiting.Loc"));
                }
                return;
            } else {
                if (a.getStatus() == GameState.playing) {
                    for (BedWarsTeam t : a.getTeams()) {
                        if (e.getPlayer().getLocation().distance(t.getBed()) < 4) {
                            if (t.isMember(e.getPlayer())) {
                                if (!t.getBedHolo(e.getPlayer()).isHidden()) {
                                    t.getBedHolo(e.getPlayer()).hide();
                                }
                            }
                        } else {
                            if (t.isMember(e.getPlayer())) {
                                if (t.getBedHolo(e.getPlayer()).isHidden()) {
                                    t.getBedHolo(e.getPlayer()).show();
                                }
                            }
                            if (t.getBed().distance(e.getTo()) < 14) {
                                //todo trap
                            }
                        }
                    }
                }
            }
        }
    }
}
