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
import com.andrei1058.bedwars.arena.team.BedWarsTeam;
import com.andrei1058.bedwars.listeners.dropshandler.PlayerDrops;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.Map;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.arena.LastHit.getLastHit;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class DamageDeathMove implements Listener {

    private final double tntJumpBarycenterAlterationInY;
    private final double tntJumpStrengthReductionConstant;
    private final double tntJumpYAxisReductionConstant;

    public DamageDeathMove(){
        this.tntJumpBarycenterAlterationInY = config.getYml().getDouble(ConfigPath.GENERAL_TNT_JUMP_BARYCENTER_IN_Y);
        this.tntJumpStrengthReductionConstant = config.getYml().getDouble(ConfigPath.GENERAL_TNT_JUMP_STRENGTH_REDUCTION);
        this.tntJumpYAxisReductionConstant = config.getYml().getDouble(ConfigPath.GENERAL_TNT_JUMP_Y_REDUCTION);
    }

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

                // todo why did I set this to 1? disabled for now
                /*if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                    e.setDamage(1);
                    return;
                }*/
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
                if (a.isSpectator(p) || a.isReSpawning(p)) {
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
                            e.setDamage(1);
                            damager = (Player) tnt.getSource();
                            // tnt jump. credits to feargames.it
                            LivingEntity damaged = (LivingEntity) e.getEntity();
                            Vector distance = damaged.getLocation().subtract(0, tntJumpBarycenterAlterationInY, 0).toVector().subtract(tnt.getLocation().toVector());
                            Vector direction = distance.clone().normalize();
                            double force = (tnt.getYield() / (tntJumpStrengthReductionConstant + distance.length()));
                            Vector resultingForce = direction.clone().multiply(force);
                            resultingForce.setY(resultingForce.getY() / (distance.length() + tntJumpYAxisReductionConstant));
                            damaged.setVelocity(resultingForce);
                        } else return;
                    }
                } else if ((e.getDamager() instanceof Silverfish) || (e.getDamager() instanceof IronGolem)) {
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
        ITeam killersTeam = null;
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

            ITeam victimsTeam = a.getTeam(victim);
            if (a.getStatus() != GameState.playing) {
                victim.spigot().respawn();
                return;
            }
            if (victimsTeam == null) {
                victim.spigot().respawn();
                return;
            }
            String message = victimsTeam.isBedDestroyed() ? Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL : Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR;
            PlayerKillEvent.PlayerKillCause cause = victimsTeam.isBedDestroyed() ? PlayerKillEvent.PlayerKillCause.UNKNOWN_FINAL_KILL : PlayerKillEvent.PlayerKillCause.UNKNOWN;
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
                        message = victimsTeam.isBedDestroyed() ? Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL : Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR;
                    } else {
                        if (killer != victim) {
                            message = victimsTeam.isBedDestroyed() ? Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL : Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL;
                        } else {
                            message = victimsTeam.isBedDestroyed() ? Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL : Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR;
                        }
                    }
                    cause = victimsTeam.isBedDestroyed() ? PlayerKillEvent.PlayerKillCause.EXPLOSION_FINAL_KILL : PlayerKillEvent.PlayerKillCause.EXPLOSION;

                } else if (damageEvent.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    LastHit lh = getLastHit(victim);
                    if (lh != null) {
                        if (lh.getTime() >= System.currentTimeMillis() - 15000) {
                            if (lh.getDamager() instanceof Player) killer = (Player) lh.getDamager();
                            if (killer != null && killer.getUniqueId().equals(victim.getUniqueId())) killer = null;
                        }
                    }
                    if (killer == null) {
                        message = victimsTeam.isBedDestroyed() ? Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL : Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL;
                    } else {
                        if (killer != victim) {
                            message = victimsTeam.isBedDestroyed() ? Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL : Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL;
                        } else {
                            message = victimsTeam.isBedDestroyed() ? Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL : Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL;
                        }
                    }
                    cause = victimsTeam.isBedDestroyed() ? PlayerKillEvent.PlayerKillCause.VOID_FINAL_KILL : PlayerKillEvent.PlayerKillCause.VOID;
                } else if (damageEvent.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                    if (killer == null) {
                        LastHit lh = getLastHit(victim);
                        if (lh != null) {
                            if (lh.getTime() >= System.currentTimeMillis() - 15000) {
                                if (nms.isDespawnable(lh.getDamager())) {
                                    Despawnable d = nms.getDespawnablesList().get(lh.getDamager().getUniqueId());
                                    killersTeam = d.getTeam();
                                    message = d.getEntity().getType() == EntityType.IRON_GOLEM ? victimsTeam.isBedDestroyed() ? Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL : Messages.PLAYER_DIE_IRON_GOLEM_REGULAR : victimsTeam.isBedDestroyed() ? Messages.PLAYER_DIE_DEBUG_FINAL_KILL : Messages.PLAYER_DIE_DEBUG_REGULAR;
                                    cause = victimsTeam.isBedDestroyed() ? d.getDeathFinalCause() : d.getDeathRegularCause();
                                    killer = null;
                                }
                            }
                        }
                    } else {
                        message = victimsTeam.isBedDestroyed() ? Messages.PLAYER_DIE_PVP_FINAL_KILL : Messages.PLAYER_DIE_PVP_REGULAR_KILL;
                        cause = victimsTeam.isBedDestroyed() ? PlayerKillEvent.PlayerKillCause.PVP_FINAL_KILL : PlayerKillEvent.PlayerKillCause.PVP;
                    }
                } else if (damageEvent.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                    if (killer != null) {
                        message = victimsTeam.isBedDestroyed() ? Messages.PLAYER_DIE_SHOOT_FINAL_KILL : Messages.PLAYER_DIE_SHOOT_REGULAR;
                        cause = victimsTeam.isBedDestroyed() ? PlayerKillEvent.PlayerKillCause.PLAYER_SHOOT_FINAL_KILL : PlayerKillEvent.PlayerKillCause.PLAYER_SHOOT;
                    }
                } else if (damageEvent.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    LastHit lh = getLastHit(victim);
                    if (lh != null) {
                        // check if kicked off in the last 10 seconds
                        if (lh.getTime() >= System.currentTimeMillis() - 10000) {
                            if (lh.getDamager() instanceof Player) killer = (Player) lh.getDamager();
                            if (killer != null && killer.getUniqueId().equals(victim.getUniqueId())) killer = null;
                            if (killer != null) {
                                if (killer != victim) {
                                    message = victimsTeam.isBedDestroyed() ? Messages.PLAYER_DIE_KNOCKED_BY_FINAL_KILL : Messages.PLAYER_DIE_KNOCKED_BY_REGULAR_KILL;
                                } else {
                                    message = victimsTeam.isBedDestroyed() ? Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL : Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL;
                                }
                            }
                            cause = victimsTeam.isBedDestroyed() ? PlayerKillEvent.PlayerKillCause.PLAYER_PUSH_FINAL : PlayerKillEvent.PlayerKillCause.PLAYER_PUSH;
                        }
                    }
                }
            }

            if (killer != null) killersTeam = a.getTeam(killer);
            String finalMessage = message;
            PlayerKillEvent playerKillEvent = new PlayerKillEvent(a, victim, killer, player -> Language.getMsg(player, finalMessage), cause);
            Bukkit.getPluginManager().callEvent(playerKillEvent);
            for (Player on : a.getPlayers()) {
                Language lang = Language.getPlayerLanguage(on);
                on.sendMessage(playerKillEvent.getMessage().apply(on).
                        replace("{PlayerColor}", victimsTeam.getColor().chat().toString()).replace("{PlayerName}", victim.getDisplayName())
                        .replace("{PlayerTeamName}", victimsTeam.getDisplayName(lang))
                        .replace("{KillerColor}", killersTeam == null ? "" : killersTeam.getColor().chat().toString())
                        .replace("{KillerName}", killer == null ? "" : killer.getDisplayName())
                        .replace("{KillerTeamName}", killersTeam == null ? "" : killersTeam.getDisplayName(lang)));
            }
            for (Player on : a.getSpectators()) {
                Language lang = Language.getPlayerLanguage(on);
                on.sendMessage(playerKillEvent.getMessage().apply(on).
                        replace("{PlayerColor}", victimsTeam.getColor().chat().toString()).replace("{PlayerName}", victim.getDisplayName())
                        .replace("{KillerColor}", killersTeam == null ? "" : killersTeam.getColor().chat().toString())
                        .replace("{PlayerTeamName}", victimsTeam.getDisplayName(lang))
                        .replace("{KillerName}", killer == null ? "" : killer.getDisplayName())
                        .replace("{KillerTeamName}", killersTeam == null ? "" : killersTeam.getDisplayName(lang)));
            }

            // increase stats to killer
            if ((killer != null && !victimsTeam.equals(killersTeam)) && !victim.equals(killer)) {
                a.addPlayerKill(killer, cause.isFinalKill(), victim);
            }

            // handle drops
            if (PlayerDrops.handlePlayerDrops(a, victim, killer, victimsTeam, killersTeam, cause)) {
                e.getDrops().clear();
            }

            // send respawn packet
            Bukkit.getScheduler().runTaskLater(plugin, () -> victim.spigot().respawn(), 3L);
            a.addPlayerDeath(victim);

            // reset last damager
            LastHit lastHit = LastHit.getLastHit(victim);
            if (lastHit != null) {
                lastHit.setDamager(null);
            }
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
                if (respawnTime > 1) {
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

                // hide armor for those with invisibility potions
                if (!a.getShowTime().isEmpty()) {
                    for (Map.Entry<Player, Integer> entry : a.getShowTime().entrySet()) {
                        if (entry.getValue() > 2) {
                            BedWars.nms.hideArmor(entry.getKey(), e.getPlayer());
                        }
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
                if (a.getStatus() == GameState.playing) {
                    if (a.getYKillHeight() != -1) {
                        if (e.getPlayer().getLocation().getBlockY() <= a.getYKillHeight()) {
                            nms.voidKill(e.getPlayer());
                        }
                    }
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
                } else {
                    if (e.getPlayer().getLocation().getBlockY() <= 0) {
                        ITeam bwt = a.getTeam(e.getPlayer());
                        if (bwt != null) {
                            e.getPlayer().teleport(bwt.getSpawn());
                        } else {
                            e.getPlayer().teleport(a.getSpectatorLocation());
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

    private static void spawnUtility(String s, Location loc, ITeam t, Player p) {
        if ("silverfish".equals(s.toLowerCase())) {
            nms.spawnSilverfish(loc, t, shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_SPEED), shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_HEALTH),
                    shop.getInt(ConfigPath.SHOP_SPECIAL_SILVERFISH_DESPAWN),
                    BedWars.shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_DAMAGE));
        }
    }
}
