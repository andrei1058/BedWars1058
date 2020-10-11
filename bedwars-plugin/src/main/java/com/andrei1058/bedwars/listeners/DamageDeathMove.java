package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.api.arena.shop.ShopHolo;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.arena.*;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.entity.Despawnable;
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
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.arena.LastHit.getLastHit;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class DamageDeathMove implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            IArena a = Arena.getArenaByPlayer(p);
            if (a != null) {
                if (a.isSpectator(p)) {
                    e.setCancelled(true);
                    return;
                }
                if (a.isReSpawning(p)) {
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
                    if (BedWarsTeam.antiFallDamageAtRespawn.containsKey(p.getUniqueId())) {
                        if (BedWarsTeam.antiFallDamageAtRespawn.get(p.getUniqueId()) > System.currentTimeMillis()) {
                            e.setCancelled(true);
                        } else BedWarsTeam.antiFallDamageAtRespawn.remove(p.getUniqueId());
                    }
                }
            }
        }
        if (BedWars.getServerType() == ServerType.MULTIARENA) {
            if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(BedWars.getLobbyWorld())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            IArena a = Arena.getArenaByPlayer(p);
            if (a != null) {
                if (a.getStatus() != GameState.playing) {
                    e.setCancelled(true);
                    return;
                }
                if (a.isSpectator(p) || a.isRespawning(p)) {
                    e.setCancelled(true);
                    return;
                }

                Player damager = null;
                if (e.getDamager() instanceof Player) {
                    damager = (Player) e.getDamager();
                } else if (e.getDamager() instanceof Projectile) {
                    ProjectileSource shooter = ((Projectile) e.getDamager()).getShooter();
                    if (shooter instanceof Player) {
                        damager = (Player) shooter;
                    } else return;
                } else if (e.getDamager() instanceof Player) {
                    damager = (Player) e.getDamager();
                    if (a.isReSpawning(damager)) {
                        e.setCancelled(true);
                        return;
                    }
                } else if (e.getDamager() instanceof TNTPrimed) {
                    TNTPrimed tnt = (TNTPrimed) e.getDamager();
                    if (tnt.getSource() != null) {
                        if (tnt.getSource() instanceof Player) {
                            damager = (Player) tnt.getSource();
                            if (tnt.getSource().equals(e.getEntity())) {
                                e.setDamage(1);
                                Vector direction = e.getEntity().getLocation().getDirection().add(new Vector(0, 0.42, 0)).multiply(2); // should be enough
                                e.getEntity().setVelocity(direction);
                            }
                        } else return;
                    }
                } else if ((e.getDamager() instanceof Silverfish) || (e.getDamager() instanceof IronGolem)) {
                    damager = null;
                    LastHit lh = LastHit.getLastHit(p);
                    if (lh != null) {
                        lh.setDamager(e.getDamager());
                        lh.setTime(System.currentTimeMillis());
                    } else {
                        new LastHit(p, e.getDamager(), System.currentTimeMillis());
                    }
                }
                if (damager != null) {
                    if (a.isSpectator(damager) || a.isReSpawning(damager.getUniqueId())) {
                        e.setCancelled(true);
                        return;
                    }

                    if (a.getTeam(p).equals(a.getTeam(damager))) {
                        if (!(e.getDamager() instanceof TNTPrimed)) {
                            e.setCancelled(true);
                        }
                        return;
                    }

                    LastHit lh = LastHit.getLastHit(p);
                    if (lh != null) {
                        lh.setDamager(damager);
                        lh.setTime(System.currentTimeMillis());
                    } else {
                        new LastHit(p, damager, System.currentTimeMillis());
                    }


                    // #274
                    if (a.getShowTime().containsKey(p)) {
                        for (Player on : a.getWorld().getPlayers()) {
                            BedWars.nms.showArmor(p, on);
                            //BedWars.nms.showPlayer(p, on);
                        }
                    }
                    //
                }
            }
        } else if (nms.isDespawnable(e.getEntity())) {
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
            IArena a = Arena.getArenaByPlayer(damager);
            if (a != null) {
                if (a.isPlayer(damager)) {
                    // do not hurt own mobs
                    if (a.getTeam(damager) == nms.getDespawnablesList().get(e.getEntity().getUniqueId()).getTeam()) {
                        e.setCancelled(true);
                    }
                } else {
                    e.setCancelled(true);
                }
            }
        } /*else if (e.getEntity() instanceof IronGolem) {
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
                        if (a.getTeam(damager) == ((OwnedByTeam) nms.getDespawnablesList().get(e.getEntity().getUniqueId())).getOwner()) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }*/
        if (BedWars.getServerType() == ServerType.MULTIARENA) {
            if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(BedWars.getLobbyWorld())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        Player victim = e.getEntity(), killer = e.getEntity().getKiller();
        ITeam t2 = null;
        IArena a = Arena.getArenaByPlayer(victim);
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
            if (!a.getConfig().getBoolean(ConfigPath.ARENA_NORMAL_DEATH_DROPS)) {
                e.getDrops().clear();
            }

            ITeam t = a.getTeam(victim);
            if (a.getStatus() != GameState.playing) {
                victim.spigot().respawn();
                return;
            }
            if (t == null) {
                victim.spigot().respawn();
                return;
            }
            String message = t.isBedDestroyed() ? Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL : Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR;
            PlayerKillEvent.PlayerKillCause cause = t.isBedDestroyed() ? PlayerKillEvent.PlayerKillCause.UNKNOWN_FINAL_KILL : PlayerKillEvent.PlayerKillCause.UNKNOWN;
            if (damageEvent != null) {
                if (damageEvent.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                    LastHit lh = getLastHit(victim);
                    if (lh != null) {
                        if (lh.getTime() >= System.currentTimeMillis() - 15000) {
                            if (lh.getDamager() instanceof Player) killer = (Player) lh.getDamager();
                            if (killer != null && killer.getUniqueId().equals(victim.getUniqueId())) killer = null;
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
                    cause = t.isBedDestroyed() ? PlayerKillEvent.PlayerKillCause.EXPLOSION_FINAL_KILL : PlayerKillEvent.PlayerKillCause.EXPLOSION;

                } else if (damageEvent.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    LastHit lh = getLastHit(victim);
                    if (lh != null) {
                        if (lh.getTime() >= System.currentTimeMillis() - 15000) {
                            if (lh.getDamager() instanceof Player) killer = (Player) lh.getDamager();
                            if (killer != null && killer.getUniqueId().equals(victim.getUniqueId())) killer = null;
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
                    cause = t.isBedDestroyed() ? PlayerKillEvent.PlayerKillCause.VOID_FINAL_KILL : PlayerKillEvent.PlayerKillCause.VOID;
                } else if (damageEvent.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                    if (killer == null) {
                        LastHit lh = getLastHit(victim);
                        if (lh != null) {
                            if (lh.getTime() >= System.currentTimeMillis() - 15000) {
                                if (nms.isDespawnable(lh.getDamager())) {
                                    Despawnable d = nms.getDespawnablesList().get(lh.getDamager().getUniqueId());
                                    t2 = d.getTeam();
                                    message = d.getEntity().getType() == EntityType.IRON_GOLEM ? t.isBedDestroyed() ? Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL : Messages.PLAYER_DIE_IRON_GOLEM_REGULAR : t.isBedDestroyed() ? Messages.PLAYER_DIE_DEBUG_FINAL_KILL : Messages.PLAYER_DIE_DEBUG_REGULAR;
                                    cause = t.isBedDestroyed() ? d.getDeathFinalCause() : d.getDeathRegularCause();
                                    killer = null;
                                }
                            }
                        }
                    } else {
                        message = t.isBedDestroyed() ? Messages.PLAYER_DIE_PVP_FINAL_KILL : Messages.PLAYER_DIE_PVP_REGULAR_KILL;
                        cause = t.isBedDestroyed() ? PlayerKillEvent.PlayerKillCause.PVP_FINAL_KILL : PlayerKillEvent.PlayerKillCause.PVP;
                    }
                } else if (damageEvent.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                    if (killer != null) {
                        message = t.isBedDestroyed() ? Messages.PLAYER_DIE_SHOOT_FINAL_KILL : Messages.PLAYER_DIE_SHOOT_REGULAR;
                        cause = t.isBedDestroyed() ? PlayerKillEvent.PlayerKillCause.PLAYER_SHOOT_FINAL_KILL : PlayerKillEvent.PlayerKillCause.PLAYER_SHOOT;
                    }
                }
            }

            if (killer != null) t2 = a.getTeam(killer);

            for (Player on : a.getPlayers()) {
                on.sendMessage(getMsg(on, message).
                        replace("{PlayerColor}", t.getColor().chat().toString()).replace("{PlayerName}", victim.getDisplayName())
                        .replace("{KillerColor}", t2 == null ? "" : t2.getColor().chat().toString())
                        .replace("{KillerName}", killer == null ? "" : killer.getDisplayName())
                        .replace("{KillerTeamName}", t2 == null ? "" : t2.getDisplayName(Language.getPlayerLanguage(on))));
            }
            for (Player on : a.getSpectators()) {
                on.sendMessage(getMsg(on, message).
                        replace("{PlayerColor}", t.getColor().chat().toString()).replace("{PlayerName}", victim.getDisplayName())
                        .replace("{KillerColor}", t2 == null ? "" : t2.getColor().chat().toString())
                        .replace("{KillerName}", killer == null ? "" : killer.getDisplayName())
                        .replace("{KillerTeamName}", t2 == null ? "" : t2.getDisplayName(Language.getPlayerLanguage(on))));
            }

            /* give stats and victim's inventory */
            if ((killer != null && !t.equals(t2)) && !victim.equals(killer)) {
                if (t.isBedDestroyed()) {
                    if (!a.getConfig().getBoolean(ConfigPath.ARENA_NORMAL_DEATH_DROPS)) {
                        for (ItemStack i : drops) {
                            if (i == null) continue;
                            if (i.getType() == Material.AIR) continue;
                            if (nms.isArmor(i) || nms.isBow(i) || nms.isSword(i) || nms.isTool(i)) continue;
                            if (!nms.getShopUpgradeIdentifier(i).trim().isEmpty()) continue;
                            if (a.getTeam(killer) != null) {
                                Vector v = t.getKillDropsLocation();
                                killer.getWorld().dropItemNaturally(new Location(a.getWorld(), v.getX(), v.getY(), v.getZ()), i);
                            }
                        }
                    }
                    a.addPlayerKill(killer, true, victim);
                } else {
                    if (!a.getConfig().getBoolean(ConfigPath.ARENA_NORMAL_DEATH_DROPS)) {
                        if (!a.isReSpawning(killer)) {
                            Map<Material, Integer> materialDrops = new HashMap<>();
                            for (ItemStack i : drops) {
                                if (i == null) continue;
                                if (i.getType() == Material.AIR) continue;
                                if (i.getType() == Material.DIAMOND || i.getType() == Material.EMERALD || i.getType() == Material.IRON_INGOT || i.getType() == Material.GOLD_INGOT) {

                                    // add to killer inventory
                                    killer.getInventory().addItem(i);

                                    // count items
                                    if (materialDrops.containsKey(i.getType())) {
                                        materialDrops.replace(i.getType(), materialDrops.get(i.getType()) + i.getAmount());
                                    } else {
                                        materialDrops.put(i.getType(), i.getAmount());
                                    }
                                }
                            }

                            for (Map.Entry<Material, Integer> entry : materialDrops.entrySet()) {
                                String msg = "";
                                int amount = entry.getValue();
                                switch (entry.getKey()) {
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
                            materialDrops.clear();
                        }
                    }
                }
                a.addPlayerKill(killer, false, victim);
            }

            /* call game kill event */
            Bukkit.getPluginManager().callEvent(new PlayerKillEvent(a, victim, killer, message, cause));
            Bukkit.getScheduler().runTaskLater(plugin, () -> victim.spigot().respawn(), 3L);
            a.addPlayerDeath(victim);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRespawn(PlayerRespawnEvent e) {
        IArena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a == null) {
            SetupSession ss = SetupSession.getSession(e.getPlayer().getUniqueId());
            if (ss != null) {
                e.setRespawnLocation(e.getPlayer().getWorld().getSpawnLocation());
            }
        } else {
            if (a.isSpectator(e.getPlayer())) {
                e.setRespawnLocation(a.getSpectatorLocation());
                String iso = Language.getPlayerLanguage(e.getPlayer()).getIso();
                for (IGenerator o : a.getOreGenerators()) {
                    o.updateHolograms(e.getPlayer(), iso);
                }
                for (ITeam t : a.getTeams()) {
                    for (IGenerator o : t.getGenerators()) {
                        o.updateHolograms(e.getPlayer(), iso);
                    }
                }
                for (ShopHolo sh : ShopHolo.getShopHolo()) {
                    if (sh.getA() == a) {
                        sh.updateForPlayer(e.getPlayer(), iso);
                    }
                }
                a.sendSpectatorCommandItems(e.getPlayer());
                return;
            }
            ITeam t = a.getTeam(e.getPlayer());
            if (t == null) {
                e.setRespawnLocation(a.getReSpawnLocation());
                plugin.getLogger().severe(e.getPlayer().getName() + " re-spawn error on " + a.getArenaName() + "[" + a.getWorldName() + "] because the team was NULL and he was not spectating!");
                plugin.getLogger().severe("This is caused by one of your plugins: remove or configure any re-spawn related plugins.");
                a.removePlayer(e.getPlayer(), false);
                a.removeSpectator(e.getPlayer(), false);
                return;
            }
            if (t.isBedDestroyed()) {
                e.setRespawnLocation(a.getSpectatorLocation());
                a.addSpectator(e.getPlayer(), true, null);
                t.getMembers().remove(e.getPlayer());
                e.getPlayer().sendMessage(getMsg(e.getPlayer(), Messages.PLAYER_DIE_ELIMINATED_CHAT));
                if (t.getMembers().isEmpty()) {
                    for (Player p : a.getWorld().getPlayers()) {
                        p.sendMessage(getMsg(p, Messages.TEAM_ELIMINATED_CHAT).replace("{TeamColor}", t.getColor().chat().toString()).replace("{TeamName}", t.getDisplayName(Language.getPlayerLanguage(p))));
                    }
                    Bukkit.getScheduler().runTaskLater(plugin, a::checkWinner, 40L);
                }
            } else {
                //respawn session
                int respawnTime = config.getInt(ConfigPath.GENERAL_CONFIGURATION_RE_SPAWN_COUNTDOWN);
                if (respawnTime > 1){
                    e.setRespawnLocation(a.getReSpawnLocation());
                    a.startReSpawnSession(e.getPlayer(), respawnTime);
                } else {
                    // instant respawn configuration
                    e.setRespawnLocation(t.getSpawn());
                    t.respawnMember(e.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (Arena.isInArena(e.getPlayer())) {
            IArena a = Arena.getArenaByPlayer(e.getPlayer());
            if (e.getFrom().getChunk() != e.getTo().getChunk()) {

                /* update armor-stands hidden by nms */
                String iso = Language.getPlayerLanguage(e.getPlayer()).getIso();
                for (IGenerator o : a.getOreGenerators()) {
                    o.updateHolograms(e.getPlayer(), iso);
                }
                for (ITeam t : a.getTeams()) {
                    for (IGenerator o : t.getGenerators()) {
                        o.updateHolograms(e.getPlayer(), iso);
                    }
                }
                for (ShopHolo sh : ShopHolo.getShopHolo()) {
                    if (sh.getA() == a) {
                        sh.updateForPlayer(e.getPlayer(), iso);
                    }
                }
            }

            if (a.isSpectator(e.getPlayer()) || a.isReSpawning(e.getPlayer())) {
                if (e.getTo().getY() < 0) {
                    e.getPlayer().teleport(a.isSpectator(e.getPlayer()) ? a.getSpectatorLocation() : a.getReSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    e.getPlayer().setAllowFlight(true);
                    e.getPlayer().setFlying(true);
                    // how to remove fall velocity?
                }
            } else {
                if (e.getPlayer().getLocation().getY() <= 0) {
                    if (a.getStatus() == GameState.playing) {
                        if (a.getConfig().getBoolean("voidKill")) {
                            nms.voidKill(e.getPlayer());
                        }
                    } else {
                        ITeam bwt = a.getTeam(e.getPlayer());
                        if (bwt != null) {
                            e.getPlayer().teleport(bwt.getSpawn());
                        } else {
                            e.getPlayer().teleport(a.getSpectatorLocation());
                        }
                    }
                }

                if (a.getStatus() == GameState.playing) {
                    for (ITeam t : a.getTeams()) {
                        if (e.getPlayer().getLocation().distance(t.getBed()) < 4) {
                            if (t.isMember(e.getPlayer()) && t instanceof BedWarsTeam) {
                                if (((BedWarsTeam) t).getBedHolo(e.getPlayer()) == null) continue;
                                if (!((BedWarsTeam) t).getBedHolo(e.getPlayer()).isHidden()) {
                                    ((BedWarsTeam) t).getBedHolo(e.getPlayer()).hide();
                                }
                            }
                        } else {
                            if (t.isMember(e.getPlayer()) && t instanceof BedWarsTeam) {
                                if (((BedWarsTeam) t).getBedHolo(e.getPlayer()) == null) continue;
                                if (((BedWarsTeam) t).getBedHolo(e.getPlayer()).isHidden()) {
                                    ((BedWarsTeam) t).getBedHolo(e.getPlayer()).show();
                                }
                            }
                        }
                    }
                    if (e.getFrom() != e.getTo()) {
                        Arena.afkCheck.remove(e.getPlayer().getUniqueId());
                        if (BedWars.getAPI().getAFKUtil().isPlayerAFK(e.getPlayer())) {
                            BedWars.getAPI().getAFKUtil().setPlayerAFK(e.getPlayer(), false);
                        }
                    }
                }
            }
        } else {
            if (e.getPlayer().getWorld().getName().equalsIgnoreCase(config.getLobbyWorldName()) && BedWars.getServerType() == ServerType.MULTIARENA) {
                if (e.getTo().getY() < 0) {
                    e.getPlayer().teleport(config.getConfigLoc("lobbyLoc"));
                }
            }
        }
    }

    @EventHandler
    public void onProjHit(ProjectileHitEvent e) {
        Projectile proj = e.getEntity();
        if (e.getEntity().getShooter() instanceof Player) {
            IArena a = Arena.getArenaByPlayer((Player) e.getEntity().getShooter());
            if (a != null) {
                if (!a.isPlayer((Player) e.getEntity().getShooter())) return;
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
            IArena a = Arena.getArenaByIdentifier(e.getEntity().getWorld().getName());
            if (a != null) {
                e.setCancelled(true);
            }
            if (BedWars.getServerType() == ServerType.MULTIARENA) {
                if (BedWars.getLobbyWorld().equals(e.getEntity().getWorld().getName())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (Arena.getArenaByIdentifier(e.getEntity().getLocation().getWorld().getName()) != null) {
            if (e.getEntityType() == EntityType.IRON_GOLEM || e.getEntityType() == EntityType.SILVERFISH) {
                e.getDrops().clear();
                e.setDroppedExp(0);
            }
        }
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() == nms.materialCake()) {
            if (Arena.getArenaByIdentifier(e.getPlayer().getWorld().getName()) != null) {
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

    private static void spawnUtility(String s, Location loc, ITeam t, Player p) {
        if ("silverfish".equals(s.toLowerCase())) {
            nms.spawnSilverfish(loc, t, shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_SPEED), shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_HEALTH),
                    shop.getInt(ConfigPath.SHOP_SPECIAL_SILVERFISH_DESPAWN),
                    BedWars.shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_DAMAGE));
        }
    }
}
