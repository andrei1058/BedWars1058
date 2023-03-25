/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.language;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import org.bukkit.configuration.file.YamlConfiguration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static com.andrei1058.bedwars.BedWars.mainCmd;

public class Spanish extends Language {

    public Spanish() {
        super(BedWars.plugin, "es");

        YamlConfiguration yml = getYml();
        yml.options().header("Translated by NotLew_x#9207");
        yml.addDefault(Messages.PREFIX, "");
        yml.addDefault("name", "Espanol");

        // this must stay here
        // move message to new path
        if (yml.get("player-die-knocked-regular") != null && yml.get(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL) == null) {
            yml.set(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, yml.getString("player-die-knocked-regular"));
            yml.set("player-die-knocked-regular", null);
        }
        if (yml.get("player-die-knocked-final") != null && yml.get(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL) == null) {
            yml.set(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, yml.getString("player-die-knocked-final"));
            yml.set("player-die-knocked-final", null);
        }

        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + mainCmd + " stats", "&2▪ &7/" + mainCmd + " join &o<arena/group>", "&2▪ &7/" + mainCmd + " leave", "&2▪ &7/" + mainCmd + " lang", "&2▪ &7/" + mainCmd + " gui", "&2▪ &7/" + mainCmd + " start &3(vip)"));
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "{prefix} &2Idiomas disponibles:");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7{iso} - &f{name}");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "{prefix}&7Uso: /lang &f&o<iso>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "{prefix}&cEste idioma no existe!");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "{prefix}&aIdioma cambiado!");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "{prefix}&cNo puedes cambiarte el idioma en una partida!.");
        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Uso: /" + mainCmd + " join §o<arena/grupo>");
        yml.addDefault(Messages.COMMAND_JOIN_GROUP_OR_ARENA_NOT_FOUND, "{prefix}&cNo hay ninguna partida o grupo llamado: {name}");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL, "{prefix}&cEsta partida esta llena!\n&aConsidera donar para conseguir mejoras. &7&o(click)");
        yml.addDefault(Messages.COMMAND_JOIN_NO_EMPTY_FOUND, "{prefix}&cNo hay ninguna partida disponible ahora mismo ;(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS, "{prefix}&cLo sentimos, pero esta partida esta llena.\n&cSabemos que eres un donador pero esta partida esta llena de admins y/o donadores.");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_PARTY_TOO_BIG, "{prefix}&cTu party es demasiado grande como para unirse a esta partida como un equipo :(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_NOT_PARTY_LEADER, "{prefix}&cSolo el líder puede unirse a una partida.");
        yml.addDefault(Messages.COMMAND_JOIN_PLAYER_JOIN_MSG, "{prefix}&7{player} &ese ha unido (&b{on}&e/&b{max}&e)!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_MSG, "{prefix}§6Ahora estás especteando §9{arena}§6.\n{prefix}§ePuedes salir en cualquier momento usando §c/leave§e.");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG, "&cLos espectadores no están permitidos en esta partida!");
        yml.addDefault(Messages.COMMAND_TP_PLAYER_NOT_FOUND, "{prefix}&cJugador no encontrado!");
        yml.addDefault(Messages.COMMAND_TP_NOT_IN_ARENA, "{prefix}&cEste jugador no esta en una partida de bedwars!");
        yml.addDefault(Messages.COMMAND_TP_NOT_STARTED, "{prefix}&cLa partida donde se encuentra el jugador no ha comenzado todavía!");
        yml.addDefault(Messages.COMMAND_TP_USAGE, "{prefix}&cUsage: /bw tp <usuario>");
        yml.addDefault(Messages.REJOIN_NO_ARENA, "{prefix}&cNo hay ninguna partida para reunirse!");
        yml.addDefault(Messages.REJOIN_DENIED, "{prefix}&cYa no puedes reunirte a la partida. Partida acabada o cama destruida.");
        yml.addDefault(Messages.REJOIN_ALLOWED, "{prefix}&eUniendose a &a{arena}&e!");
        yml.addDefault(Messages.COMMAND_REJOIN_PLAYER_RECONNECTED, "{prefix}&7{player} &ese ha reconectado!");
        yml.addDefault(Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA, "{prefix}&cNo estas en una partida!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "{prefix}&7{player} &eha salido!");
        yml.addDefault(Messages.COMMAND_NOT_ALLOWED_IN_GAME, "{prefix}&cNo puedes hacer esto en una partida.");
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "{prefix}&cComando no encontrado o no tienes permiso!");
        yml.addDefault(Messages.COMMAND_PARTY_HELP, Arrays.asList(
                "&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&aParty Commands:", "&e/party help &7- &bImprime este mensaje",
                "&e/party invite <jugador> &7- &bInvita al jugador a tu party",
                "&e/party leave &7- &bSal de tu party actual",
                "&e/party remove <jugador> &7- &bElimina al jugador de la party",
                "&e/party accept <jugador> &7- &bAcepta una invitación a una party",
                "&e/party disband &7- &bElimina la party"));
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_USAGE, "{prefix}&eUso: &7/party invite <jugador>");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &eno está online!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT, "{prefix}&eInvitación enviada a &7{player}&6.");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG, "{prefix}&b{player} &ete ha invitado a una party! &o&7(Click para aceptar)");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF, "{prefix}&cNo puedes invitarte a ti mismo!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &eestá offline!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE, "{prefix}&cNo hay invitaciones de party para aceptar!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY, "{prefix}&eYa estás en una party!");
        yml.addDefault(Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS, "{prefix}&cSolo en owner de la party puede hacer esto!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_USAGE, "{prefix}&eUso: &7/party accept <jugador>");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_SUCCESS, "{prefix}&7{player} &ese ha unido a la party!");
        yml.addDefault(Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY, "{prefix}&No estas en una party!");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND, "{prefix}&cNo puedes salir de tu propia party!\n&ePrueba usando: &b/party disband");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_SUCCESS, "{prefix}&7{player} &eha salido de la party!");
        yml.addDefault(Messages.COMMAND_PARTY_DISBAND_SUCCESS, "{prefix}&eParty eliminada!");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_USAGE, "{prefix}&7Uso: &e/party remove <jugador>");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_SUCCESS, "{prefix}&7{player} &eha sido eliminado de la party");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER, "{prefix}&7{player} &eno esta en tu party!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "§c▪ §7No estas jugando!");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "§c▪ §7Cuenta regresiva acortada!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "{prefix}&7No puedes forzar el inicio de una partida.\n§7Porfavor considera donar para mejoras VIP.");
        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cNo puedes hacer eso aún! Espera {seconds} segundo/s más!");
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "{prefix}&cLo siento, pero fuiste kickeado porque un donador se unió a la partida.\n&aPorfavor considera donar para más mejoras. &7&o(click)");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS_CHAT, "{prefix}§cNo hay suficientes jugadores! Cuenta regresiva detenida!");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "{prefix}&eLa partida en la que estabas esta siendo reiniciada.");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&cJugando");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4Reiniciando");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&2Esperando §c{full}");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6Comenzando §c{full}");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8Click para unirte");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l{name}");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList(
                "",
                "&7Estado: {status}",
                "&7Jugadores: &f{on}&7/&f{max}",
                "&7Tipo: &a{group}",
                "",
                "&aClick-Izquierdo para unirte.",
                "&eClick-Derecho para espectear."));
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_NAME, "&r{serverIp}");
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_LORE, Collections.emptyList());
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CHAT, "{prefix}&eEl juego comienza en &6{time} &esegundo/s!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE, "&a{second}");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-5", "&e❺");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-4", "&e❹");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-3", "&c❸");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-2", "&c❷");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-1", "&c❶");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_SUB_TITLE, "&cEsperando a más jugadores..");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&aADELANTE");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList(
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars",
                "",
                "&e&l    Proteje tu cama y destruye las camas enemigas.",
                "&e&l      Mejorate a ti mismo o a tu equipo recolectando",
                "&e&l   Hierro, Oro, Esmeralda y diamantes de generadores",
                "&e&l             para obtener acceso a mejoras.",
                "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "{prefix}&cLo siento pero no puedes unirte a esta partida en este momento. Usa Click-Derecho para espectear!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "{prefix}&cLo siento pero no puedes espectear esta partida en este momento. Usa Click-Izquierdo para unirte!");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NO_PROXY, "&cSorry but you must join an arena using BedWarsProxy. \n&eSi quieres configurar una arena asegurate de tener el permiso 'bw.setup' y así podrás unirte al servidor directamente!");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8Teletransportador");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "{vPrefix}{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList(
                "&7Vida: &f{health}%",
                "&7Comida: &f{food}",
                "",
                "&7Click-Izquierdo para espectear"));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lVolver al lobby");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Click-Derecho para volver al lobby!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&aEspecteando a &7{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&cAGACHATE para salir");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eSaliendo del modo espectador en primera persona");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "{prefix}§cEl owner de la party ha salido y la party ha sido eliminada!");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&eNivel &c{tier}");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&lDiamante");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&lEsmeralda");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&eAparece en &c{seconds} &esegundos");
        yml.addDefault(Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT, "{prefix}&eLos generadores de {generatorType} &ehan sido mejorados al Nivel &c{tier}");
        yml.addDefault(Messages.FORMATTING_CHAT_LOBBY, "{level}{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_WAITING, "{level}{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SHOUT, "{level}{vPrefix}&6[GLOBAL] {team} &7{player}&f{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_TEAM, "{level}{vPrefix}&f{team}&7 {player}{vSuffix} {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SPECTATOR, "{level}{vPrefix}&7[ESPECTADOR] {player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_HEALTH, Arrays.asList(
                "&c❤",
                "&aVida"));
        yml.addDefault(Messages.FORMATTING_SPECTATOR_TEAM, "ESPECT");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_SPECTATOR, "&7");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_SPECTATOR, "");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_RESTARTING, Arrays.asList(
                "{teamColor}&l{teamLetter} &r{teamColor}",
                "{team} ",
                "{vPrefix} {teamColor}"));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_RESTARTING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_PLAYING, Arrays.asList(
                "{teamColor}&l{teamLetter} &r{teamColor}",
                "{team} ",
                "{vPrefix} {teamColor}&l{teamLetter} &r{teamColor}"));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_PLAYING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_STARTING, List.of("{vPrefix} "));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_STARTING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_WAITING, List.of("{vPrefix} "));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_WAITING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_LOBBY, List.of("{vPrefix} "));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_LOBBY, new ArrayList<>());

        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_LOBBY, "&6{serverIp}");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_WAITING,"&a{serverIp}");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_STARTING, "&6{serverIp}");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_PLAYING, "&d{serverIp}");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_RESTARTING, "&c{serverIp}");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_SPECTATOR,"&9{serverIp}");

        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_LOBBY, "&6{serverIp}");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_WAITING, "&a{serverIp}");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_STARTING,"&6{serverIp}");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_PLAYING, "&d{serverIp}");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_RESTARTING, "&c{serverIp}");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_SPECTATOR,"&9{serverIp}");

        yml.addDefault(Messages.FORMATTING_SCOREBOARD_DATE, "dd/MM/yy");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_GENERIC, "{TeamColor}{TeamLetter}&f {TeamName}: {TeamStatus}");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED, "&c&l✘");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_BED_DESTROYED, "&a{remainingPlayers}");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE, "&a&l✓");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER, "mm:ss");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_YOUR_TEAM, "&7 TÚ");
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fSiguiendo a: {team} &f- Distancia: {distance}m");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH, "▮ ");
        yml.addDefault(Messages.FORMATTING_STATS_DATE_FORMAT, "yyyy/MM/dd HH:mm");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM, "{TeamColor}[{TeamName}]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT, "&6[GLOBAL]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR, "&7[ESPECTADOR]");
        yml.addDefault(Messages.MEANING_FULL, "Lleno");
        yml.addDefault(Messages.MEANING_SHOUT, "global");
        yml.addDefault(Messages.MEANING_NOBODY, "Nadie");
        yml.addDefault(Messages.MEANING_NEVER, "Nunca");
        yml.addDefault(Messages.MEANING_IRON_SINGULAR, "de Hierro");
        yml.addDefault(Messages.MEANING_IRON_PLURAL, "de Hierro");
        yml.addDefault(Messages.MEANING_GOLD_SINGULAR, "de Oro");
        yml.addDefault(Messages.MEANING_GOLD_PLURAL, "de Oro");
        yml.addDefault(Messages.MEANING_EMERALD_SINGULAR, "Esmeralda");
        yml.addDefault(Messages.MEANING_EMERALD_PLURAL, "Esmeraldas");
        yml.addDefault(Messages.MEANING_DIAMOND_SINGULAR, "Diamante");
        yml.addDefault(Messages.MEANING_DIAMOND_PLURAL, "Diamantes");
        yml.addDefault(Messages.MEANING_VAULT_SINGULAR, "$");
        yml.addDefault(Messages.MEANING_VAULT_PLURAL, "$");
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "{prefix}&cNo puedes poner bloques aquí!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "{prefix}&cSolo puedes romper bloques colocados por jugadores!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&cNo puedes destruir tu propia cama!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&lDESTRUCCIÓN DE CAMA > &7La cama del equipo {TeamColor}{TeamName} &7fue destruida por {PlayerColor}{PlayerName}&7!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&cCAMA DESTRUIDA!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&fNo reaparecerás más!!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&f&lDESTRUCCIÓN DE CAMA > &7Tu cama fue destruida por {PlayerColor}{PlayerName}&7!");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cNo puedes abrir este cofre porque este equipo no ha sido eliminado!");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "{PlayerColor}{PlayerName} &7se ha caído al vacío.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "{PlayerColor}{PlayerName} &7se ha caído al vacío. &b&lASESINATO FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "{PlayerColor}{PlayerName} &7fue empujado al vacío por {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, "{PlayerColor}{PlayerName} &7fue empujado al vacío por {KillerColor}{KillerName}&7. &b&lASESINATO FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_REGULAR, "{PlayerColor}{PlayerName} &7se ha desconectado mientras luchaba contra {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_FINAL, "{PlayerColor}{PlayerName} &7se ha desconectado mientras luchaba contra {KillerColor}{KillerName}&7. &b&lASESINATO FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_REGULAR_KILL, "{PlayerColor}{PlayerName} &7fue empujado por {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_FINAL_KILL, "{PlayerColor}{PlayerName} &7fue empujado por {KillerColor}{KillerName}&7. &b&lASESINATO FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "{PlayerColor}{PlayerName} &7fue empujado por una bomba de amor de {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7fue empujado por una bomba de amor de {KillerColor}{KillerName}&7. &b&lASESINATO FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR, "{PlayerColor}{PlayerName} &7fue empujado por una bomba.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7was hit off by a bomb. &b&lASESINATO FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "{PlayerColor}{PlayerName} &7fue asesinado por {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "{PlayerColor}{PlayerName} &7fue asesinado por {KillerColor}{KillerName}&7. &b&lASESINATO FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR, "{PlayerColor}{PlayerName} &7murió.");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL, "{PlayerColor}{PlayerName} &7murió. &b&lASESINATO FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_REGULAR, "{PlayerColor}{PlayerName} &7fue disparado por {KillerColor}{KillerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_FINAL_KILL, "{PlayerColor}{PlayerName} &7fue disparado por {KillerColor}{KillerName}&7! &b&lASESINATO FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_REGULAR, "{PlayerColor}{PlayerName} &7fue asesinado por el BedBug del equipo {KillerColor}{KillerTeamName}!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_FINAL_KILL, "{PlayerColor}{PlayerName} &7fue asesinado por el BedBug del equipo {KillerColor}{KillerTeamName}! &b&lASESINATO FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_REGULAR, "{PlayerColor}{PlayerName} &7fue asesinado por por el Golem de Hierro del equipo {KillerColor}{KillerTeamName}!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL, "{PlayerColor}{PlayerName} &7fue asesinado por el Golem de Hierro del equipo {KillerColor}{KillerTeamName}! &b&lASESINATO FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_DIAMOND, "{prefix}&b+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_EMERALD, "{prefix}&a+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_IRON, "{prefix}&f+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_GOLD, "{prefix}&6+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cHAS MUERTO!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eReaparecerás en &c{time} &esegundo/s!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "{prefix}&eReaparecerás en &c{time} &esegundo/s!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aHAS REAPARECIDO!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "{prefix}&cHas sido eliminado!");
        yml.addDefault(Messages.PLAYER_HIT_BOW, "{prefix}{TeamColor}{PlayerName} &7ahora tiene &c{amount} &7HP!");
        yml.addDefault(Messages.GAME_END_GAME_OVER_PLAYER_TITLE, "&c&lFIN DEL JUEGO!");
        yml.addDefault(Messages.GAME_END_VICTORY_PLAYER_TITLE, "&6&lVICTORIA!");
        yml.addDefault(Messages.GAME_END_TEAM_WON_CHAT, "{prefix}{TeamColor}{TeamName} &aha ganado el juego!");
        yml.addDefault(Messages.GAME_END_TOP_PLAYER_CHAT, Arrays.asList(
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars",
                "",
                "{winnerFormat}",
                "",
                "",
                "&e                          &l1er Asesino &7- {firstName} - {firstKills}",
                "&6                          &l2do Asesino &7- {secondName} - {secondKills}",
                "&c                          &l3er Asesino &7- {thirdName} - {thirdKills}", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&lDefiende tu cama!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lTu cama ha sido destruida!");
        yml.addDefault(Messages.NPC_NAME_TEAM_UPGRADES, "&bMEJORAS DE EQUIPO,&e&lCLICK DERECHO");
        yml.addDefault(Messages.NPC_NAME_SOLO_UPGRADES, "&bMEJORAS SOLO,&e&lCLICK DERECHO");
        yml.addDefault(Messages.NPC_NAME_TEAM_SHOP, "&bTIENDA DEL EQUIPO,&e&lCLICK DERECHO");
        yml.addDefault(Messages.NPC_NAME_SOLO_SHOP, "&bTIENDA,&e&lCLICK DERECHO");
        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&lEQUIPO ELIMINADO > &cEl equipo {TeamColor}{TeamName} &cha sido eliminado!\n");
        yml.addDefault(Messages.NEXT_EVENT_BEDS_DESTROY, "&cDestrucción de cama");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_II, "&fDiamante II");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_III, "&fDiamante III");
        yml.addDefault(Messages.NEXT_EVENT_DRAGON_SPAWN, "&fMuerte Súbita");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_II, "&fEsmeralda II");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_III, "&fEsmeralda III");
        yml.addDefault(Messages.NEXT_EVENT_GAME_END, "&4Fin del juego");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED, "&cCAMA DESTRUIDA!");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED, "&fTodas las camas han sido destruidas!");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED, "&c&lTodas las camas han sido destruidas!");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH, "&cMuerte Súbita");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH, "");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH, "&cMUERTE SÚBITA: &6&b{TeamDragons} Dragon del equipo {TeamColor}{TeamName}!");
        yml.addDefault(Messages.XP_REWARD_PER_MINUTE, "{prefix}&b+{xp} Experiencia de BedWars Recibida (Tiempo de Juego).");
        yml.addDefault(Messages.XP_REWARD_WIN, "{prefix}&b+{xp} Experiencia de BedWars Recibida (Partida Ganada).");
        yml.addDefault(Messages.XP_REWARD_PER_TEAMMATE, "{prefix}&b+{xp} Experiencia de BedWars Recibida (Ayuda de Equipo).");
        yml.addDefault(Messages.XP_REWARD_BED_DESTROY, "{prefix}&b+{xp} Experiencia de BedWars Recibida (Cama Destruida).");
        yml.addDefault(Messages.XP_REWARD_REGULAR_KILL, "{prefix}&b+{xp} Experiencia de BedWars Recibida (Asesinato Normal).");
        yml.addDefault(Messages.XP_REWARD_FINAL_KILL, "{prefix}&b+{xp} Experiencia de BedWars Recibida (Asesinato Final).");

        yml.addDefault(Messages.MONEY_REWARD_PER_MINUTE, "{prefix}&6+{money} Monedas (Tiempo de Juego).");
        yml.addDefault(Messages.MONEY_REWARD_WIN, "{prefix}&6+{money} Monedas (Partida Ganada).");
        yml.addDefault(Messages.MONEY_REWARD_PER_TEAMMATE, "{prefix}&6+{money} Monedas (Ayuda de Equipo).");
        yml.addDefault(Messages.MONEY_REWARD_BED_DESTROYED, "{prefix}&6+{money} Monedas (Cama Destruida).");
        yml.addDefault(Messages.MONEY_REWARD_FINAL_KILL, "{prefix}&6+{money} Monedas (Asesinato Final).");
        yml.addDefault(Messages.MONEY_REWARD_REGULAR_KILL, "{prefix}&6+{money} Monedas (Asesinato Normal).");

        /* Lobby Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "stats"), "&eEstadísticas");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fClick-Derecho para ver tus estadísticas!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&eSelector de partida");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Collections.singletonList("&fClick-Derecho para elegir la partida!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&eVolver al Lobby");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fClick-Derecho para salir de BedWars!"));
        /* Pre Game Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&eEstadísticas");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fClick-Derecho para ver tus estadísticas!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&eVolver al lobby");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fClick-Derecho para salir de la partida!"));
        /* Spectator Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&eTeletransportador");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&eBack to Lobby");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fRight-click to leave the arena!"));

        /* save default items messages for stats gui */
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "&8{player} Estadísticas");
        addDefaultStatsMsg(yml, "wins", "&6Ganadas", "&f{wins}");
        addDefaultStatsMsg(yml, "losses", "&6Perdidas", "&f{losses}");
        addDefaultStatsMsg(yml, "kills", "&6Asesinatos", "&f{kills}");
        addDefaultStatsMsg(yml, "deaths", "&6Muertes", "&f{deaths}");
        addDefaultStatsMsg(yml, "final-kills", "&6Asesinatos Finales", "&f{finalKills}");
        addDefaultStatsMsg(yml, "final-deaths", "&6Muertes Finales", "&f{finalDeaths}");
        addDefaultStatsMsg(yml, "beds-destroyed", "&6Camas Destruidas", "&f{bedsDestroyed}");
        addDefaultStatsMsg(yml, "first-play", "&6Primera Partida", "&f{firstPlay}");
        addDefaultStatsMsg(yml, "last-play", "&6Ultima Partida", "&f{lastPlay}");
        addDefaultStatsMsg(yml, "games-played", "&6Juegos Jugados", "&f{gamesPlayed}");

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList(
                "&f&lBED WARS",
                "&7{date} &8{server}",
                "",
                "&fMapa: &a{map}", 
                "", 
                "&fJugadores: &a{on}/{max}", 
                "", "&fEsperando...", 
                "", "§fModo: &a{group}", 
                "&fVersión: &7{version}", 
                "", 
                "&e{server_ip}"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList(
                "&f&lBED WARS", 
                "&7{date} &8{server}", 
                "",
                "&fMapa: &a{map}",
                "",
                "&fJugadores: &a{on}/{max}",
                "",
                "&fEmpezando en &a{time}s",
                "",
                "§fModo: &a{group}",
                "&fVersión: &7{version}",
                "",
                "&e{server_ip}"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList(
                "&e&lBED WARS", "&7{date}", "", "&f{nextEvent} en &a{time}", "", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.Doubles.playing", Arrays.asList(
                "&e&lBED WARS",
                "&7{date}",
                "",
                "&f{nextEvent} en &a{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "&e{server_ip}"));

        yml.addDefault("scoreboard.3v3v3v3.playing", Arrays.asList(
                "&e&lBED WARS", "&7{date}",
                "",
                "&f{nextEvent} en &a{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "&fAsesinatos: &a{kills}",
                "&fAsesinatos Finales: &a{finalKills}",
                "&fCamas Rotas: &a{beds}",
                "",
                "&e{server_ip}"));

        yml.addDefault("scoreboard.4v4v4v4.playing", Arrays.asList(
                "&e&lBED WARS",
                "&7{date}",
                "",
                "&f{nextEvent} en &a{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "&fAsesinatos: &a{kills}",
                "&fAsesinatos Finales: &a{finalKills}",
                "&fCamas Rotas: &a{beds}",
                "",
                "&e{server_ip}"));

        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&6&lBedWars,&4&lB&6edWars,&6&lB&4e&6dWars,&6&lBe&4d&6Wars,&6&lBed&4W&6ars,&6&lBedW&4a&6rs,&6&lBedWa&4r&6s,&6&lBedWar&4s,&6&lBedWars", "&fTu Nivel: {level}", "", "&fProgreso: &a{currentXp}&7/&b{requiredXp}", "{progress}", "", "&7{player}", "", "&fMonedas: &a{money}"
                , "", "&fVictorias Totales: &a{wins}", "&fAsesinatos Totales: &a{kills}", "", "&e{server_ip}"));

        //
        yml.addDefault(Messages.SHOP_INDEX_NAME, "&8Compra rápida");
        yml.addDefault(Messages.SHOP_QUICK_ADD_NAME, "&8Añadiendo a la compra rápida...");
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "{prefix}&cNo tienes suficiente/s {currency}! Necesitas {amount} more!");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "{prefix}&aHas comprado &6{item}");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "{prefix}&cYa has comprado eso!");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "{TeamColor}Silverfish &7del equipo {TeamColor}&l{TeamName}");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "{TeamColor}{despawn}s &8[ {TeamColor}{health}&8]");
        yml.addDefault(Messages.SHOP_SEPARATOR_NAME, "&8⇧ Categorías");
        yml.addDefault(Messages.SHOP_SEPARATOR_LORE, Collections.singletonList("&8⇩ Items"));
        yml.addDefault(Messages.SHOP_QUICK_BUY_NAME, "&bCompra Rápida");
        yml.addDefault(Messages.SHOP_QUICK_BUY_LORE, new ArrayList<>());
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_NAME, "&cSlot vacío!");
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_LORE, Arrays.asList(
                "&7Esto es un slot de compra rápida!",
                "&bAgachate y haz click en &7cualquier item",
                "&7en la tienda para añadirlo aquí."));
        yml.addDefault(Messages.SHOP_CAN_BUY_COLOR, "&a");
        yml.addDefault(Messages.SHOP_CANT_BUY_COLOR, "&c");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CAN_BUY, "&eClick para comprar!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CANT_AFFORD, "&cNo tienes suficiente/s {currency}!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_MAXED, "&aAL MÁXIMO!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_ARMOR, "&aEQUIPADO!");
        yml.addDefault(Messages.SHOP_LORE_QUICK_ADD, "&bAgachate y haz click en un item para añadirlo a la Compra Rápida");
        yml.addDefault(Messages.SHOP_LORE_QUICK_REMOVE, "&bAgachate y haz click en un item para removerlo de la Compra Rápida!");


        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "&8Bloques", "&aBloques", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "wool", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Lana", Arrays.asList("&7Precio: &f{cost} {currency}", "", "&7Genial para construir puentes atraves", "&7de islas. Cambia al color de",
                "&7tu equipo.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "clay", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Arcilla", Arrays.asList("&7Precio: {cost} {currency}", "", "&7Bloque básico para defender tu cama.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "glass", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Cristan Anti Explosiones", Arrays.asList("&7Precio: {cost} {currency}", "", "&7Inmune a las explosiones.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "stone", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}End Stone", Arrays.asList("&7Precio: {cost} {currency}", "", "&7Bloque sólido para defener tu cama.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "ladder", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Escaleras", Arrays.asList("&7Precio: {cost} {currency}", "", "&7Útil para salvar gatos atrapados", "&7en árboles.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "obsidian", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Obsidiana", Arrays.asList("&7Precio: {cost} {currency}", "", "&7Protección extrema para tu cama.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "wood", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Madera", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Bloque sólido para defender tu cama", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_MELEE, "&8Armas", "&aArmas", Collections.singletonList("&eClick para ver!"));

        addContentMessages(yml, "stone-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Espada de piedra", Arrays.asList("&7Precio: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "iron-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Espada de hierro", Arrays.asList("&7Precio: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "diamond-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Espada de diamante", Arrays.asList("&7Precio: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "stick", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Palo (Empuje I)", Arrays.asList("&7Precio: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "&8Armadura", "&aArmadura", Collections.singletonList("&eClick para ver!"));

        addContentMessages(yml, "chainmail", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Armadura de cota de malla permanente", Arrays.asList("&7Precio: {cost} {currency}",
                "", "&7Botas y pantalones de cota de malla", "&7con las que siempre reapacerás", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "iron-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Armadura de hierro permanente", Arrays.asList("&7Precio: {cost} {currency}",
                "", "&7Botas y pantalones de hierro", "&7con las que siempre reapacerás", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "diamond-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Armadura de diamante permanente", Arrays.asList("&7Precio: {cost} {currency}",
                "", "&7Botas y pantalones de diamante", "&7con las que siempre reaparecerás.", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "&8Herramientas", "&aHerramientas", Collections.singletonList("&eClick para ver!"));

        addContentMessages(yml, "shears", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Tijeras Permanentes", Arrays.asList("&7Precio: {cost} {currency}",
                "", "&7Geniales para deshacerte de la lana. Siempre", "&7reapareceras con estas tijeras.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "pickaxe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Pickaxe {tier}", Arrays.asList("&7Precio: {cost} {currency}", "&7Nivel: &e{tier}",
                "", "&7Esto es un item mejorable.", "&7Perderá 1 nivel cuando.", "&7mueras!", "", "&7Reaparecerás permanentemente", "&7con almenos el nivel menor.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "axe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Axe {tier}", Arrays.asList("&7Precio: {cost} {currency}", "&7Nivel: &e{tier}",
                "", "&7Esto es un item mejorable.", "&7Perderá 1 nivel cuando.", "&7mueras!", "", "&7Reaparecerás permanentemente", "&7con almenos el nivel menor.", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_RANGED, "&8A distancia", "&aA distancia", Collections.singletonList("&eClick para ver!"));

        addContentMessages(yml, "arrow", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Flechas", Arrays.asList("&7Precio: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow1", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Arco", Arrays.asList("&7Precio: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow2", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Arco (Poder I)", Arrays.asList("&7Precio: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow3", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Arco (Poder I, Golpeo I)", Arrays.asList("&7Precio: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "&8Pociones", "&aPociones", Collections.singletonList("&eClick para ver!"));

        addContentMessages(yml, "speed-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Poción de Velocidad II (45 segundos)", Arrays.asList("&7Precio: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "jump-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Poción de Salto V (45 segundos)", Arrays.asList("&7Precio: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "invisibility", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Poción de Invisibilidad (30 segundos)", Arrays.asList("&7Precio: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "&8Especiales", "&aEspeciales", Collections.singletonList("&eClick para ver!"));

        addContentMessages(yml, "golden-apple", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Manzana de Oro", Arrays.asList("&7Precio: {cost} {currency}", "", "&7Well-rounded healing.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bedbug", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}BedBug", Arrays.asList("&7Precio: {cost} {currency}", "", "&7Aparece un silverfish donde",
                "&7la bola de nieve aparezca", "&7para distraer a tus enemigos. Vive 15 segundos.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "dream-defender", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Defensor de los Sueños", Arrays.asList("&7Precio: {cost} {currency}", "", "&7Golem de Hierro para ayudarte a defender",
                "&7tu base. Vive 4 minutos.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "fireball", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Fireball", Arrays.asList("&7Precio: {cost} {currency}", "", "&7Click-Derecho para lanzar! Genial para",
                "&7empujar a enemigos caminando en", "&7puentes finos", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "tnt", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}TNT", Arrays.asList("&7Precio: {cost} {currency}", "", "&7Se activa instantaneamente, apropiado",
                "&7para explotar cosas!", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "ender-pearl", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Ender Pearl", Arrays.asList("&7Precio: {cost} {currency}", "", "&7La forma más rápida para invadir",
                "&7bases enemigas.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "water-bucket", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Cubo de Agua", Arrays.asList("&7Precio: {cost} {currency}", "", "&7Genial para ralentizar a enemigos",
                "&7acercandose. También puede proteger", "&7contra la TNT.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bridge-egg", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Puente Huevo", Arrays.asList("&7Precio: {cost} {currency}", "", "&7Este huevo crea un puente en se",
                "&7camino después de ser lanzado.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "magic-milk", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Leche Mágica", Arrays.asList("&7Precio: {cost} {currency}", "", "&7Evita cualquier trampa durante",
                "&760 segundos después de consumir.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "sponge", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Esponja", Arrays.asList("&7Precio: {cost} {currency}", "", "&7GGenial para absorber agua.",
                "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "Compact Pop-up Tower", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Torre Compacta", Arrays.asList("&7Precio: {cost} {currency}", "", "&7Pon una torre compacta", "&7como defensa!", "", "{quick_buy}", "{buy_status}"));

        //
        yml.addDefault(Messages.MEANING_NO_TRAP, "Sin trampas!");
        yml.addDefault(Messages.FORMAT_UPGRADE_TRAP_COST, "&7Precio: {currencyColor}{cost} {currency}");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CAN_AFFORD, "&e");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CANT_AFFORD, "&c");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_UNLOCKED, "&a");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_LOCKED, "&7");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_UNLOCKED, "&a");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "{color}Click para comprar!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "{color}No tienes suficiente {currency}");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cBLOQUEADO");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "{color}DESBLOQUEADO");
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a{player} compró &6{upgradeName}");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-1"), "{color}Forja de hierro");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "forge"),
                Arrays.asList(
                        "&7Incrementa la aparición de los",
                        "&7recursos de tu isla.",
                        "",
                        "{tier_1_color}Tier 1: +50% Recursos, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}Tier 2: +100% Recursos, &b{tier_2_cost} {tier_2_currency}",
                        "{tier_3_color}Tier 3: Aparecen esmeraldas, &b{tier_3_cost} {tier_3_currency}",
                        "{tier_4_color}Tier 4: +200% Recursos, &b{tier_4_cost} {tier_4_currency}",
                        ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-2"), "{color}Forja de Oro");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-3"), "{color}Forja de Esmeralda");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-4"), "{color}Forja Fundida");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_NAME_PATH + "traps", "&eCompra una trampa");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_LORE_PATH + "traps", Arrays.asList(
                "&7La trampas compradas serán",
                "&7puestas en la derecha.",
                "",
                "&eClick para ver!"));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "swords").replace("{tier}", "tier-1"), "{color}Espadas Afiladas");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "swords"),
                Arrays.asList(
                        "&7Tu equipo gana permanentemente",
                        "&7Filo I en todas las espadas y",
                        "&7hachas!",
                        "",
                        "{tier_1_color}Precio: &b{tier_1_cost} {tier_1_currency}",
                        ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-1"), "{color}Armadura Reforzada I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "armor"),
                Arrays.asList(
                        "&7Tu equipo gana permanentemente",
                        "&7Protección en todas las piezas!",
                        "&7de armadura",
                        "",
                        "{tier_1_color}Nivel 1: Protección I, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}Nivel 2: Protección II, &b{tier_2_cost} {tier_2_currency}",
                        "{tier_3_color}Nivel 3: Protección III, &b{tier_3_cost} {tier_3_currency}",
                        "{tier_4_color}Nivel 4: Protección IV, &b{tier_4_cost} {tier_4_currency}",
                        ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-2"), "{color}Armadura Reforzada II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-3"), "{color}Armadura Reforzada III");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-4"), "{color}Armadura Reforzada IV");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "miner").replace("{tier}", "tier-1"), "{color}Minero Maníaco I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "miner"),
                Arrays.asList(
                        "&7Todos los miembros de tu equipo ganarán",
                        "&7Prisa Minera permanentemente.",
                        "",
                        "{tier_1_color}Tier 1: Prisa Minera I, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}Tier 2: Prisa Minera II, &b{tier_2_cost} {tier_2_currency}",
                        ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "miner").replace("{tier}", "tier-2"), "{color}Minero Maníaco II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "heal-pool").replace("{tier}", "tier-1"), "{color}Piscina de Salud");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "heal-pool"),
                Arrays.asList(
                        "&7Crea un campo de regeneración",
                        "&7alrededor de tu base!",
                        "",
                        "{tier_1_color}Precio: &b{tier_1_cost} {tier_1_currency}",
                        ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "dragon").replace("{tier}", "tier-1"), "{color}Dragon Buff");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "dragon"),
                Arrays.asList(
                        "&7Tu equipo tendrá 2 dragones",
                        "&7en vez de uno durante la!",
                        "&7pelea final",
                        "",
                        "{tier_1_color}Precio: &b{tier_1_cost} {tier_1_currency}",
                        ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "glass", "&8⬆&7Comprable");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "glass", Collections.singletonList("&8⬇&7Trampas"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "first", "{color}Trampa #1: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "first", Arrays.asList(
                "&7El primer enemigo en entrar a tu base",
                "&7activará esta trampa"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "first",
                Arrays.asList(
                        "",
                        "&7Comprar una trampa lo pondrá",
                        "&7aquí. Su precio irá subiendo",
                        "&7basado en el número de trampas",
                        "&7listadas.",
                        "",
                        "&7Trampa Siguiente: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "second", "{color}Trampa #2: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "second", Arrays.asList(
                "&7El segundo enemigo en entrar a tu base",
                "&7activará esta trampa"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "second",
                Arrays.asList(
                        "",
                        "&7Comprar una trampa lo pondrá",
                        "&7aquí. Su precio irá subiendo",
                        "&7basado en el número de trampas",
                        "&7listadas.",
                        "",
                        "&7Trampa Siguiente: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "third", "{color}Trampa #3: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "third", Arrays.asList(
                "&7El tercer enemigo en entrar a tu base",
                "&7activará esta trampa"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "third",
                Arrays.asList(
                        "",
                        "&7Comprar una trampa lo pondrá",
                        "&7aquí. Su precio irá subiendo",
                        "&7basado en el número de trampas",
                        "&7listadas.",
                        "",
                        "&7Trampa Siguiente: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "1", "{color}Es una trampa!");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "1", Arrays.asList(
                "&7Inflinge Cegera y Lentitud",
                "&7por 5 segundos.",
                ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "2", "{color}Trampa Defensiva-Ofensiva");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "2", Arrays.asList(
                "&7Da Velocidad I durante 15 segundos",
                "&7a los jugadores aliados cerca de.",
                "&7tu base",
                ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "3", "{color}Trampa Alarma");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "3", Arrays.asList(
                "&7Revela a los jugadores invisibles",
                "&7y también su nombre y equipo.",
                ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "4", "{color}Trampa de Fatiga Minera");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "4", Arrays.asList(
                "&7Inflinje fatiga minera durante",
                "&715 segundos.",
                ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "back", "&aAtrás");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "back", Collections.singletonList("&7A las Mejoras y Trampas"));
        yml.addDefault(Messages.UPGRADES_CATEGORY_GUI_NAME_PATH + "traps", "&8Añade una trampa");
        yml.addDefault(Messages.UPGRADES_TRAP_QUEUE_LIMIT, "&cTrampas llenas!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_MSG, "&c&lLa trampa {trap} fue activada!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_TITLE, "&cTRAMPA ACTIVADA!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_SUBTITLE, "&fTu {trap} ha sido activada!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_MSG + "3", "&c&lTrampa activada por &7&l{player} &c&ldel equipo {color}&l{team}&c&l!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_TITLE + "3", "&c&lALARMA!!!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_SUBTITLE + "3", "&fTrampa activada por el equipo {color}{team} &f!");
        setPrefixStatic(m(Messages.PREFIX));
        setPrefix(m(Messages.PREFIX));
        yml.options().copyDefaults(true);
        save();
    }
}
