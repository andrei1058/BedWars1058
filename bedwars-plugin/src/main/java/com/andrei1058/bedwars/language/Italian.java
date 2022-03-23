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

import static com.andrei1058.bedwars.BedWars.mainCmd;
import static com.andrei1058.bedwars.api.language.Messages.*;

public class Italian extends Language {

    public Italian() {
        super(BedWars.plugin, "it");
        YamlConfiguration yml = getYml();
        yml.options().copyDefaults(true);
        yml.options().header("Traduzione in italiano di Fabian03#4583");
        yml.addDefault(Messages.PREFIX, "");
        yml.addDefault("name", "Italiano");

        // this must stay here
        // move message to new path
        if (yml.get("player-die-knocked-regular") != null && yml.get(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL) == null){
            yml.set(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, yml.getString("player-die-knocked-regular"));
            yml.set("player-die-knocked-regular", null);
        }
        if (yml.get("player-die-knocked-final") != null && yml.get(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL) == null){
            yml.set(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, yml.getString("player-die-knocked-final"));
            yml.set("player-die-knocked-final", null);
        }

        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + mainCmd + " stats", "&2▪ &7/" + mainCmd + " join &o<arena/gruppo>", "&2▪ &7/" + mainCmd + " leave", "&2▪ &7/" + mainCmd + " lang", "&2▪ &7/" + mainCmd + " gui", "&2▪ &7/" + mainCmd + " start &3(vip)"));
        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Utilizzo: /" + mainCmd + " join §o<arena/gruppo>");
        yml.addDefault(Messages.COMMAND_NOT_ALLOWED_IN_GAME, "{prefix}&cNon puoi eseguire questa azione.");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL, "{prefix}&cArena piena!\n&aPrendi in considerazione il fatto di donare per entrare anche quando le arene sono piene. &7&o(clicca qui)");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS, "{prefix}&cArena piena.\n&cSappiamo che sei un donatore ma al momento questa arena è piena di staffer o donatori.");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "{prefix}&e{player} si è disconnesso!");
        yml.addDefault(Messages.COMMAND_JOIN_GROUP_OR_ARENA_NOT_FOUND, "{prefix}&cNon esiste nessun gruppo o arena chiamato: {name}");
        yml.addDefault(Messages.COMMAND_JOIN_NO_EMPTY_FOUND, "{prefix}&cNon ci sono arene disponibili al momento ;(");
        yml.addDefault(Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA, "{prefix}&cNon sei in gioco!");
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "{prefix} &2Lingue disponibili:");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7{iso} - &f{name}");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "{prefix}&7Utilizzo: /lang &f&o<iso>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "{prefix}&cQuesta lingua non esiste!");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "{prefix}&aLingua modificata!");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "{prefix}&cNon puoi cambiare la lingua durante il gioco.");
        yml.addDefault(Messages.COMMAND_TP_PLAYER_NOT_FOUND, "{prefix}&cPlayer not found!");
        yml.addDefault(Messages.COMMAND_TP_NOT_IN_ARENA, "{prefix}&cThis player is not in a bedwars arena!");
        yml.addDefault(Messages.COMMAND_TP_NOT_STARTED, "{prefix}&cL'arena dove si trova il giocatore non è ancora inziata!");
        yml.addDefault(Messages.COMMAND_TP_USAGE, "{prefix}&cUsage: /bw tp <username>");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_PARTY_TOO_BIG, "{prefix}&cIl tuo party è troppo grande per entrare in questa arena come una squadra :(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_NOT_PARTY_LEADER, "{prefix}&cSolo il creatore del party può scegliere l'arena.");
        yml.addDefault(Messages.COMMAND_JOIN_PLAYER_JOIN_MSG, "{prefix}&7{player} &eè entrato in gioco (&b{on}&e/&b{max}&e)!");
        yml.addDefault(Messages.COMMAND_REJOIN_PLAYER_RECONNECTED, "{prefix}&7{player} &esi è riconnesso!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "{prefix}&7{player} &esi è disconnesso!");

        yml.addDefault(Messages.COMMAND_PARTY_HELP, Arrays.asList("&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&aComandi del party:", "&e/party help &7- &bStampa questa lista di comandi", "&e/party invite <player> &7- &bInvita il giocatore nel tuo party",
                "&e/party leave &7- &bLascia il party attuale",
                "&e/party remove <player> &7- &bRimuovi il giocatore dal party",
                "&e/party accept <player> &7- &bAccetta un invito al party", "&e/party disband &7- &bScioglie il tuo party"));
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_USAGE, "{prefix}&eUtilizzo: &7/party invite <player>");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &enon è online!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT, "{prefix}&eInvito inviato a &7{player}&6.");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG, "{prefix}&b{player} &eti ha invitato nel party! &o&7(Clicca per accettare)");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF, "{prefix}&cNon puoi invitare te stesso!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE, "{prefix}&cNon ci sono richieste di invito ad un party da accettare");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY, "{prefix}&eSei già in un party!");
        yml.addDefault(Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS, "{prefix}&cSolo il proprietario del party può eseguire questa azione!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_USAGE, "{prefix}&eUtilizzo: &7/party accept <player>");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_SUCCESS, "{prefix}&7{player} &eè entrato nel party!");
        yml.addDefault(Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY, "{prefix}&cNon sei in un party!");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND, "{prefix}&cNon puoi uscire dal tuo party!\n&eUtilizzo corretto: &b/party disband");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_SUCCESS, "{prefix}&7{player} &eè uscito dal party!");
        yml.addDefault(Messages.COMMAND_PARTY_DISBAND_SUCCESS, "{prefix}&eParty sciolto!");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_USAGE, "{prefix}&eUtilizzo: &e/party remove <player>");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_SUCCESS, "{prefix}&7{player} &eè stato rimosso dal party.");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER, "{prefix}&7{player} &enon è nel tuo party!");
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "{prefix}&cIl comando non è stato trovato o non hai abbastanza permessi!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "§c▪ §7Non sei in gioco!");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "§c▪ §7Conto alla rovescia diminuito!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "{prefix}&7Non puoi forzare l'inizio del gioco.\n§7Prendi in considerazione il fatto di donare per avere più cose.");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_MSG, "{prefix}§6Stai spectando l'arena §9{arena}§6.\n{prefix}§ePuoi uscire dall'arena in qualsiasi momento utilizzando §c/leave§e.");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &eè offline!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG, "&cGli spettatori non sono ammessi in questa arena!");
        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cAspetta {seconds} scondi prima di usare ancora questo comando!");
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "{prefix}&cIl comando non è stato trovato o non hai abbastanza permessi!\n&aPotresti fare una donazione per ottenere permessi vip. &7&o(click)");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS_CHAT, "{prefix}§cNon ci sono abbastanza giocatori! Conto alla rovescia fermato!");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "{prefix}&eL'arena in cui ti trovavi si sta restartando.");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&cIn gioco");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4In riavvio");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&2In attesa §c{full}");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6In avvio §c{full}");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8Arene disponibili");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l{name}");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7Stato: {status}", "&7Giocatori: &f{on}&7/&f{max}", "&7Tipo: &a{group}", "", "&aClick-Sinistro per entrare.", "&eClick-Destro per guardare."));
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_NAME, "&r{serverIp}");
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_LORE, Collections.emptyList());
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CHAT, "{prefix}&eIl gioco avrà inizio in &6{time} &esecondi!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE, "&a{second}");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-5", "&e❺");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-4", "&e❹");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-3", "&c❸");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-2", "&c❷");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-1", "&c❶");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_SUB_TITLE, "&cWaiting for more players..");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "",
                "&e&l    Proteggi il tuo letto e distruggi quello degli avversari.",
                "&e&l      Potenzia te e il tuo team collezionando",
                "&e&l   Ferro, Oro, Smeraldi, e Diamanti dai generatori",
                "&e&l             per avere accesso a forti potenziamenti.", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&aVIA");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8Teletrasporto");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "{vPrefix}{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7Vita: &f{health}%", "&7Cibo: &f{food}", "", "&7Click-Sinistro per guardare"));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lRitorna alla lobby");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Click-destro per ritornare alla lobby!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&aGuardando &7{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&cSHIFT per uscire");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eUscita Dalla Modalità Spettatore");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "{prefix}§cIl proprietario del party è uscito e il party è stato sciolto!");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "{prefix}&cCi dispiace, ma attualmente non puoi entrare in quest'arena. Usa Click-destro per guardare!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "{prefix}&cCi dispiace, ma attualmente non puoi guardare questa partita. Usa Click-sinistro per giocare!");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NO_PROXY, "&cSorry but you must join an arena using BedWarsProxy. \n&eIf you want to setup an arena make sure to give yourself the bw.setup permission so you can join the server directly!");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&eLivello &c{tier}");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&lDiamante");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&lSmeraldo");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&eDrop in &c{seconds} &esecondi");
        yml.addDefault(Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT, "{prefix}&eI generatori di {generatorType} &esono stati potenziati a livello &c{tier}");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_CHAT_LOBBY, "{level}{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_WAITING, "{level}{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SHOUT, "{level}{vPrefix}&6[SHOUT] {team} &7{player}&f{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_TEAM, "{level}{vPrefix}&f{team}&7 {player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SPECTATOR, "{level}{vPrefix}&7[SPECTATOR] {player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_HEALTH, Arrays.asList("&c❤", "&aVita"));
        yml.addDefault(Messages.FORMATTING_SPECTATOR_TEAM, "SPECT");
        yml.addDefault(Messages.FORMATTING_SPECTATOR_COLOR, "&7");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_PRESTARTING, Arrays.asList("{teamColor}&l{teamLetter} &r{teamColor}", "{team} ", "{vPrefix} {teamColor}"));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_PRESTARTING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_PLAYING, Arrays.asList("{teamColor}&l{teamLetter} &r{teamColor}", "{team} ", "{vPrefix} {teamColor}&l{teamLetter} &r{teamColor}"));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_PLAYING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_STARTING, Arrays.asList("{vPrefix} "));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_STARTING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_WAITING, Arrays.asList("{vPrefix} "));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_WAITING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_LOBBY, Arrays.asList("{vPrefix} "));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_LOBBY, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_DATE, "dd/MM/yy");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_GENERIC, "{TeamColor}{TeamLetter}&f {TeamName}: {TeamStatus}");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED, "&c&l✘");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_BED_DESTROYED, "&a{remainingPlayers}");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE, "&a&l✓");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER, "mm:ss");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_YOUR_TEAM, "&7 TU");
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fObbiettivo: {team} &f- Distanza: {distance}m");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.FORMATTING_STATS_DATE_FORMAT, "yyyy/MM/dd HH:mm");
        yml.addDefault(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH, "▮ ");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM, "{TeamColor}[{TeamName}]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT, "&6[SHOUT]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR, "&7[SPECTATOR]");
        yml.addDefault(Messages.MEANING_SHOUT, "shout");
        yml.addDefault(Messages.MEANING_NOBODY, "Nessuno");
        yml.addDefault(Messages.MEANING_FULL, "Pieno");
        yml.addDefault(Messages.MEANING_IRON_SINGULAR, "Ferro");
        yml.addDefault(Messages.MEANING_IRON_PLURAL, "Ferro");
        yml.addDefault(Messages.MEANING_GOLD_SINGULAR, "Oro");
        yml.addDefault(Messages.MEANING_GOLD_PLURAL, "Oro");
        yml.addDefault(Messages.MEANING_EMERALD_SINGULAR, "Smeraldo");
        yml.addDefault(Messages.MEANING_EMERALD_PLURAL, "Smeraldi");
        yml.addDefault(Messages.MEANING_DIAMOND_SINGULAR, "Diamante");
        yml.addDefault(Messages.MEANING_DIAMOND_PLURAL, "Diamanti");
        yml.addDefault(Messages.MEANING_VAULT_SINGULAR, "$");
        yml.addDefault(Messages.MEANING_VAULT_PLURAL, "$");
        yml.addDefault(Messages.MEANING_NEVER, "Never");
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "{prefix}&cNon puoi piazzare blocchi in questa area!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "{prefix}&cPuoi rompere solo i blocchi che sono stati piazzati dai giocatori!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&cNon puoi distruggere il tuo letto!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&lLETTO DISTRUTTO > Il letto del team {TeamColor}{TeamName} &7è stato fatto a pezzi da {PlayerColor}{PlayerName}&7!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&cLETTO DISTRUTTO!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&fNon verrai più respawnato!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&f&lLETTO DISTRUTTO > &7Il tuo letto è stato distrutto da {PlayerColor}{PlayerName}&7!");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cNon puoi aprire questa cesta, perchè il team non è stato eliminato.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "{PlayerColor}{PlayerName} &7è caduto nel vuoto.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "{PlayerColor}{PlayerName} &7è caduto nel vuoto. &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "{PlayerColor}{PlayerName} &7è caduto nel vuoto con l'aiuto di {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, "{PlayerColor}{PlayerName} &7è caduto nel vuoto con l'aiuto di {KillerColor}{KillerName}&7. &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_REGULAR_KILL, "{PlayerColor}{PlayerName} &7è stato spinto da {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_FINAL_KILL, "{PlayerColor}{PlayerName} &7è stato spinto da {KillerColor}{KillerName}&7. &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "{PlayerColor}{PlayerName} &7è saltato in aria a causa di {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7è saltato in aria a causa di {KillerColor}{KillerName}&7. &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "{PlayerColor}{PlayerName} &7è stato ucciso da {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "{PlayerColor}{PlayerName} &7è stato ucciso da {KillerColor}{KillerName}&7. &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_REGULAR, "{PlayerColor}{PlayerName} &7si è disconnesso mentre combatteva con {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_FINAL, "{PlayerColor}{PlayerName} &7si è disconnesso mentre combatteva con {KillerColor}{KillerName}&7. &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cSEI MORTO!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eVerrai respawnato in &c{time} &esecondi!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "{prefix}&eVerrai respawnato in &c{time} &esecondi!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aRESPAWNATO!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "{prefix}&cSei stato eliminato!");
        yml.addDefault(Messages.PLAYER_HIT_BOW, "{prefix}{TeamColor}{PlayerName} &7è rimasto con &c{amount} &7HP!");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR, "{PlayerColor}{PlayerName} &7è morto.");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL, "{PlayerColor}{PlayerName} &7è morto. &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_REGULAR, "{PlayerColor}{PlayerName} &7è stato sparato da {KillerColor}{KillerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_FINAL_KILL, "{PlayerColor}{PlayerName} &7è stato sparato da {KillerColor}{KillerName}&7! &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_REGULAR, "{PlayerColor}{PlayerName} &7è stato ucciso dal BedBug {KillerColor}{KillerTeamName}!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_FINAL_KILL, "{PlayerColor}{PlayerName} &7è stato ucciso dal BedBug {KillerColor}{KillerTeamName}! &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_REGULAR, "{PlayerColor}{PlayerName} &7è stato ucciso dal Dream Defender {KillerColor}{KillerTeamName}!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL, "{PlayerColor}{PlayerName} &7è stato ucciso dal Dream Defender {KillerColor}{KillerTeamName}! &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_DIAMOND, "{prefix}&b+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_EMERALD, "{prefix}&a+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_IRON, "{prefix}&f+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_GOLD, "{prefix}&6+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR, "{PlayerColor}{PlayerName} &7è stato colpito da una bomba.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7è stato colpito da una bomba. &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.GAME_END_GAME_OVER_PLAYER_TITLE, "&c&lGAME OVER!");
        yml.addDefault(Messages.GAME_END_VICTORY_PLAYER_TITLE, "&6&lVITTORIA!");
        yml.addDefault(Messages.GAME_END_TOP_PLAYER_CHAT, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "", "{winnerFormat}", "", "",
                "&e                          &l1° Uccisore &7- {firstName} - {firstKills}",
                "&6                          &l2° Uccisore &7- {secondName} - {secondKills}",
                "&c                          &l3° Uccisore &7- {thirdName} - {thirdKills}", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.GAME_END_TEAM_WON_CHAT, "{prefix}{TeamColor}{TeamName} &aha vinto il gioco!");
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&lDifendi il tuo letto!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lIl tuo letto è stato distrutto!");
        yml.addDefault(Messages.NPC_NAME_TEAM_UPGRADES, "&bTEAM UPGRADES,&e&lCLICK DESTRO");
        yml.addDefault(Messages.NPC_NAME_SOLO_UPGRADES, "&bSOLO UPGRADES,&e&lCLICK DESTRO");
        yml.addDefault(Messages.NPC_NAME_TEAM_SHOP, "&bITEM SHOP,&e&lCLICK DESTRO");
        yml.addDefault(Messages.NPC_NAME_SOLO_SHOP, "&bITEM SHOP,&e&lCLICK DESTRO");
        yml.addDefault(Messages.NEXT_EVENT_BEDS_DESTROY, "&cDistruzione letti");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_II, "&fDiamante II");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_III, "&fDiamante III");
        yml.addDefault(Messages.NEXT_EVENT_DRAGON_SPAWN, "&fMorte Brusca");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_II, "&fEmerald II");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_III, "&fEmerald III");
        yml.addDefault(Messages.NEXT_EVENT_GAME_END, "&4Fine gioco");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED, "&cLETTO DISTRUTTO!");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED, "&Tutti i letti sono stati distrutti!");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED, "{prefix}&c&lTutti i letti sono stati distrutti!");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH, "&cMorte Brusca");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH, "");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH, "&cMORTE BRUSCA: &6&b{TeamDragons} Drago {TeamColor}{TeamName}!");
        /* Lobby Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "stats"), "&eStatistiche");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Arrays.asList("&fClick-destro per vedere", "le tue statistiche!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&eSelettore Arena");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Collections.singletonList("&fClick-destro Per selezionare un'arena!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&eTorna nella Lobby");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fClick-destro per lasciare BedWars!"));
        /* Pre Game Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&eStatistiche");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Arrays.asList("&fClick-destro per vedere", "le tue statistiche!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&eBack to Lobby");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fClick-destro to leave the arena!"));
        /* Spectator Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&eTeletrasporto");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "teleporter"), Collections.singletonList("&fClick-destro to spectate a player!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&eTorna nella Lobby");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fClick-destro per lasciare BedWars!"));

        yml.addDefault(Messages.REJOIN_NO_ARENA, "{prefix}&cNon c'è un'arena su cui ritornare!");
        yml.addDefault(Messages.REJOIN_DENIED, "{prefix}&cNon puoi tornare più. Partita finita o letto distrutto.");
        yml.addDefault(Messages.REJOIN_ALLOWED, "{prefix}&eStai entrando sull'arena &a{arena}&e!");

        /* save default items messages for stats gui */
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "&8Statistiche di {player}");
        addDefaultStatsMsg(yml, "wins", "&6Vittorie", "&f{wins}");
        addDefaultStatsMsg(yml, "losses", "&6Sconfitte", "&f{losses}");
        addDefaultStatsMsg(yml, "kills", "&6Uccisioni", "&f{kills}");
        addDefaultStatsMsg(yml, "deaths", "&6Decessi", "&f{deaths}");
        addDefaultStatsMsg(yml, "final-kills", "&6Uccisioni finali", "&f{finalKills}");
        addDefaultStatsMsg(yml, "final-deaths", "&6Decessi finali", "&f{finalDeaths}");
        addDefaultStatsMsg(yml, "beds-destroyed", "&6Letti distrutti", "&f{bedsDestroyed}");
        addDefaultStatsMsg(yml, "first-play", "&6Prima partita", "&f{firstPlay}");
        addDefaultStatsMsg(yml, "last-play", "&6Ultima partita", "&f{lastPlay}");
        addDefaultStatsMsg(yml, "games-played", "&6Partite giocate", "&f{gamesPlayed}");

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList("&f&lBED WARS", "&7{date} &8{server}", "", "&fMappa: &a{map}", "", "&fGiocatori: &a{on}/{max}", "", "&fIn attesa...", "", "§fMode: &a{group}", "&fVersione: &7{version}", "", "&e{server_ip}"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList("&f&lBED WARS", "&7{date} &8{server}", "", "&fMappa: &a{map}", "", "&fGiocatori: &a{on}/{max}", "", "&fInizio in &a{time}s", "", "§fMode: &a{group}", "&fVersione: &7{version}", "", "&e{server_ip}"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.Doubles.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.3v3v3v3.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}",
                "", "&fUccisioni: &a{kills}", "&fUccisioni Finali: &a{finalKills}", "&fLetti Distrutti: &a{beds}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.4v4v4v4.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}",
                "", "&fUccisioni: &a{kills}", "&fUccisioni Finali: &a{finalKills}", "&fLetti Distrutti: &a{beds}", "", "&e{server_ip}"));


        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&6&lBedWars", "&fLivello: {level}", "", "&fProgresso: &a{currentXp}&7/&b{requiredXp}", "{progress}", "", "&7{player}", "", "&fSoldi: &a{money}"
                , "", "&fVittorie: &a{wins}", "&fUccisioni: &a{kills}", "", "&e{server_ip}"));
        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&lTEAM ELIMINATO > Il team {TeamColor}{TeamName} &cè stato eliminato\n");
        yml.addDefault(Messages.XP_REWARD_PER_MINUTE, "{prefix}&6+{xp} Esperienza BedWars ricevuta (Tempo di Gioco).");
        yml.addDefault(Messages.XP_REWARD_WIN, "{prefix}&6+{xp} Esperienza BedWars ricevuta (Vittoria).");
        yml.addDefault(Messages.XP_REWARD_PER_TEAMMATE, "{prefix}&6+{xp} Esperienza BedWars ricevuta (Supporto Team).");
        yml.addDefault(Messages.XP_REWARD_BED_DESTROY, "{prefix}&6+{xp} Esperienza BedWars ricevuta (Distruzione letto).");
        yml.addDefault(Messages.XP_REWARD_REGULAR_KILL, "{prefix}&6+{xp} Esperienza BedWars ricevuta (Uccisione).");
        yml.addDefault(Messages.XP_REWARD_FINAL_KILL, "{prefix}&6+{xp} Esperienza BedWars ricevuta (Uccisione Finale).");

        yml.addDefault(Messages.MONEY_REWARD_PER_MINUTE, "{prefix}&6+{money} Coins (Tempo di Gioco).");
        yml.addDefault(Messages.MONEY_REWARD_WIN, "{prefix}&6+{money} Coins (Vittoria).");
        yml.addDefault(Messages.MONEY_REWARD_PER_TEAMMATE, "{prefix}&6+{money} Coins (Supporto Team).");
        yml.addDefault(Messages.MONEY_REWARD_BED_DESTROYED, "{prefix}&6+{money} Coins (Distruzione letto).");
        yml.addDefault(Messages.MONEY_REWARD_FINAL_KILL, "{prefix}&6+{money} Coins (Uccisione Regolare).");
        yml.addDefault(Messages.MONEY_REWARD_REGULAR_KILL, "{prefix}&6+{money} Coins (Uccisione).");

        //shop
        yml.addDefault(Messages.SHOP_INDEX_NAME, "&8Quick Buy");
        yml.addDefault(Messages.SHOP_QUICK_ADD_NAME, "&8Aggiungendo al Quick Buy...");
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "{prefix}&cNon hai abbastanza {currency}! Te ne occorre {amount} in più!");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "{prefix}&aHai comprato &6{item}");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "{prefix}&Hai già comprato questo!");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "{TeamColor}&l{TeamName} &r{TeamColor}Silverfish");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "{TeamColor}{despawn}s &8[ {TeamColor}{health}&8]");
        yml.addDefault(SHOP_SEPARATOR_NAME, "&8⇧ Categorie");
        yml.addDefault(SHOP_SEPARATOR_LORE, Collections.singletonList("&8⇩ Items"));
        yml.addDefault(SHOP_QUICK_BUY_NAME, "&bQuick Buy");
        yml.addDefault(SHOP_QUICK_BUY_LORE, new ArrayList<>());
        yml.addDefault(SHOP_QUICK_EMPTY_NAME, "&cSlot libero!");
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_LORE, Arrays.asList("&7Questo è uno slot Quick Buy!", "&bShifta + Click &7su un item nello", "&7shop per aggungerlo qui."));
        yml.addDefault(Messages.SHOP_CAN_BUY_COLOR, "&a");
        yml.addDefault(Messages.SHOP_CANT_BUY_COLOR, "&c");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CAN_BUY, "&eClick per comprare");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CANT_AFFORD, "&cNon hai abbastanza {currency}!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_MAXED, "&aMAXED!");
        yml.addDefault(Messages.SHOP_LORE_QUICK_ADD, "&bShift Click per aggiungere al Quick Buy");
        yml.addDefault(Messages.SHOP_LORE_QUICK_REMOVE, "&bShift Click per rimuovere dal Quick Buy!");

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "&8Blocchi", "&aBlocchi", Collections.singletonList("&eClicca per sfogliare!"));
        addContentMessages(yml, "wool", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Lana", Arrays.asList("&7Costo: &f{cost} {currency}", "", "&7Buono per costruire ponti tra", "&7le isole. Prende il colore", "&7del tuo team.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "clay", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Argilla indurita", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Blocco base per proteggere  il letto.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "glass", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Vetro Anti Esplosione", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Immune alle esplosioni.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "stone", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Pietra dell'End", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Blocco solido per difendere il letto.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "ladder", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Scala", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Utile per salvare gatti bloccati", "&7sugli alberi.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "wood", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Wood", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Blocco solido per difendere il letto.", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_MELEE, "&8Spade", "&aSpade", Collections.singletonList("&eClicca per sfogliare!"));
        addContentMessages(yml, "stone-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Spada di pietra", Arrays.asList("&7Costo: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "iron-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Spada di ferro", Arrays.asList("&7Costo: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "diamond-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Spada di diamante", Arrays.asList("&7Costo: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "stick", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Bastone (Contraccolpo I)", Arrays.asList("&7Costo: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "&8Armatura", "&aArmatura", Collections.singletonList("&eClicca per sfogliare!"));
        addContentMessages(yml, "chainmail", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Armatura di Maglia Permanente", Arrays.asList("&7Costo: {cost} {currency}",
                "", "&7Chainmail leggings and boots", "&7which you will always spawn", "&7with.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "iron-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Armatura di Ferro Permanente", Arrays.asList("&7Costo: {cost} {currency}",
                "", "&7Iron leggings and boots which", "&7you will always spawn with.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "diamond-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Armatura di Diamante Permanente", Arrays.asList("&7Costo: {cost} {currency}",
                "", "&7Diamond leggings and boots which", "&7you will always crush with.", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "&8Utensili", "&aUtensili", Collections.singletonList("&eClicca per sfogliare!"));
        addContentMessages(yml, "shears", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Cesoie Permanenti", Arrays.asList("&7Costo: {cost} {currency}",
                "", "&7Utile per tagliare la lana.", "&7Respawnerai sempre con questo utensile.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "pickaxe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Piccone {tier}", Arrays.asList("&7Costo: {cost} {currency}", "&7Tier: &e{tier}",
                "", "&7Questo è un item upgradabile.", "&7Perderai un tier alla tua morte.", "", "&7Respawnerai sempre con", "&7questo item avendo almeno", "&7il tier pià basso.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "axe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Ascia {tier}", Arrays.asList("&7Costo: {cost} {currency}", "&7Tier: &e{tier}",
                "", "&7Questo è un item upgradabile.", "&7Perderai un tier alla tua morte.", "", "&7Respawnerai sempre con", "&7questo item avendo almeno", "&7il tier pià basso.", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_RANGED, "&8Ranged", "&aArchi", Collections.singletonList("&eClicca per sfogliare!"));
        addContentMessages(yml, "arrow", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Frecce", Arrays.asList("&7Costo: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow1", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Arco", Arrays.asList("&7Costo: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow2", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Arco (Potenza I)", Arrays.asList("&7Costo: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow3", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Arco (Potenza I, Contraccolpo I)", Arrays.asList("&7Costo: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "&8Pozioni", "&aPozioni", Collections.singletonList("&eClicca per sfogliare!"));

        addContentMessages(yml, "speed-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Pozione Rapidità II (45 secondi)", Arrays.asList("&7Costo: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "jump-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Pozione Salto V (45 secondi)", Arrays.asList("&7Costo: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "invisibility", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Pozione Invisibilità (30 secondi)", Arrays.asList("&7Costo: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "&8Utilità", "&aUtilità", Collections.singletonList("&eClicca per sfogliare!"));
        addContentMessages(yml, "golden-apple", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Mela D'oro", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Mela rigeneratrice di vita.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bedbug", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}BedBug", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Spawna un silverfish dove arriva", "&7la palla di neve, per distrare", "&7gli avversari. Vive 15 secondi.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "dream-defender", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Dream Defender", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Iron Golem che ti aiuta a proteggere", "&7la tua base. Vive 4 minuti.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "fireball", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Fireball", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Click-destro per lanciare! Ottima per", "&7spingere gli avversari che camminano", "&7sui ponti", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "tnt", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}TNT", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Si accende instantaneamente, utile per", "&7far esplodere cose!", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "ender-pearl", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Ender Pearl", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Il metodo pià veloce per invadere", "&7la base degli avversari.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "water-bucket", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Secchio D'acqua", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Utile per rallentare gli", "&7inamici. Può anche proteggere", "&7dalla TNT.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bridge-egg", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Bridge Egg", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Questo uovo crea un ponte in sua", "&7direzione dopo averlo lanciato.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "magic-milk", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Latte Magico", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Evita le trappole per 60 secondi", "&7dopo averlo consumato.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "sponge", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Spugna", Arrays.asList("&7Costo: {cost} {currency}", "", "&7Utile per succhiare l'acqua :)).", "", "{quick_buy}", "{buy_status}"));

        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "&aClicca per acquistare!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "&cNon hai abbastanza {currency}");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cBLOCCATO");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "&aSBLOCCATO");
        yml.addDefault("upgrades.Default.generators.tier1.name", "&eForgiatrice di ferro");
        yml.addDefault("upgrades.Default.generators.tier1.lore", Arrays.asList("&7Aumenta la velocità di spawn di ferro", "&7e oro del 50%..", "", "&7Costo:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.generators.tier2.name", "&eForgiatrice d'oro");
        yml.addDefault("upgrades.Default.generators.tier2.lore", Arrays.asList("&7Aumenta la velocità di spawn di ferro", "&7e oro del 100%..", "", "&7Costo:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.generators.tier3.name", "&eEmerald Forge");
        yml.addDefault("upgrades.Default.generators.tier3.lore", Arrays.asList("&7Attiva lo spawn di smeraldi", "&7nel generatore del tuo team.", "", "&7Costo:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.maniacMiner.tier1.name", "&eManiac Miner");
        yml.addDefault("upgrades.Default.maniacMiner.tier1.lore", Arrays.asList("&7Tutti i giocatori del tuo team", "&7riceveranno Haste I", "", "&7Costo:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.sharpSword.tier1.name", "&eSpade affilate");
        yml.addDefault("upgrades.Default.sharpSword.tier1.lore", Arrays.asList("&7Tutti i giocatori del tuo team", "&7riceveranno Affilatezza I sulle loro spade!", "", "&7Costo:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.reinforced.tier1.name", "&eArmatura rinforzata");
        yml.addDefault("upgrades.Default.reinforced.tier1.lore", Arrays.asList("&7Tutti i giocatori del tuo team", "&7riceveranno Protezione I sulle loro armature!", "", "&7Costo:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.trap.tier1.name", "&eIt's a trap!");
        yml.addDefault("upgrades.Default.trap.tier1.lore", Arrays.asList("&7Il prossimo nemico che entrerà", "&7nella tua base riceverà Blindness e", "&7Slowness!", "", "&7Costo:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.miningFatigue.tier1.name", "&eMiner Fatigue Trap");
        yml.addDefault("upgrades.Default.miningFatigue.tier1.lore", Arrays.asList("&7Il prossimo nemico che entrerà", "&7nella tua base riceverà Mining Fatigue", "&7per 10 secondi!", "", "&7Costo:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.healPool.tier1.name", "&eRigenerazione");
        yml.addDefault("upgrades.Default.healPool.tier1.lore", Arrays.asList("&7Crea un campo di rigenerazione", "&7intorno alla tua base!", "", "&7Costo:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a{player} ha comprato &6{upgradeName}");

        yml.addDefault(Messages.MEANING_NO_TRAP, "No trap!");
        yml.addDefault(Messages.FORMAT_UPGRADE_TRAP_COST, "&7Cost: {currencyColor}{cost} {currency}");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CAN_AFFORD, "&e");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CANT_AFFORD, "&c");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_UNLOCKED, "&a");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_LOCKED, "&7");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_UNLOCKED, "&a");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "{color}Click to purchase!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "{color}Non hai abbastanza {currency}");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cLOCKED");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "{color}UNLOCKED");
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a{player} ha acquisistato &6{upgradeName}");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-1"), "{color}Iron Forge");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "forge").replace("{tier}", "tier-1"),
                Arrays.asList("&7Upgrade resource spawning on", "&7your island.", "", "{tierColor}Tier 1: +50% Resources, &b{cost} {currency}",
                        "&7Tier 2: +100% Resources, &b8 Diamonds",
                        "&7Tier 3: Spawn emeralds, &b12 Diamonds",
                        "&7Tier 4: +200% Resources, &b16 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-2"), "{color}Golden Forge");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "forge").replace("{tier}", "tier-2"),
                Arrays.asList("&7Upgrade resource spawning on", "&7your island.", "", "&aTier 1: +50% Resources, &b{cost} {currency}",
                        "{tierColor}Tier 2: +100% Resources, &b8 Diamonds",
                        "&7Tier 3: Spawn emeralds, &b12 Diamonds",
                        "&7Tier 4: +200% Resources, &b16 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-3"), "{color}Emerald Forge");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "forge").replace("{tier}", "tier-3"),
                Arrays.asList("&7Upgrade resource spawning on", "&7your island.", "", "&aTier 1: +50% Resources, &b{cost} {currency}",
                        "&aTier 2: +100% Resources, &b8 Diamonds",
                        "{tierColor}Tier 3: Spawn emeralds, &b12 Diamonds",
                        "&7Tier 4: +200% Resources, &b16 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-4"), "{color}Molten Forge");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "forge").replace("{tier}", "tier-4"),
                Arrays.asList("&7Upgrade resource spawning on", "&7your island.", "", "&aTier 1: +50% Resources, &b{cost} {currency}",
                        "&aTier 2: +100% Resources, &b8 Diamonds",
                        "&aTier 3: Spawn emeralds, &b12 Diamonds",
                        "{tierColor}Tier 4: +200% Resources, &b16 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_NAME_PATH + "traps", "&eBuy a trap");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_LORE_PATH + "traps", Arrays.asList("&7Purchased traps will be", "&7queued on the right.", "", "&eClick to browse!"));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "swords").replace("{tier}", "tier-1"), "{color}Sharpened Swords");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "swords").replace("{tier}", "tier-1"),
                Arrays.asList("&7Your team permanently gains", "&7Sharpness I on all swords and", "&7axes!", "", "&7Cost: &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-1"), "{color}Reinforced Armor I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "armor").replace("{tier}", "tier-1"),
                Arrays.asList("&7Your team permanently gains", "&7Protection on all armor pieces!", "", "{tierColor}Tier 1: Protection I, &b{cost} {currency}",
                        "&7Tier 2: Protection II, &b10 Diamonds",
                        "&7Tier 3: Protection III, &b20 Diamonds",
                        "&7Tier 4: Protection IV, &b30 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-2"), "{color}Reinforced Armor II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "armor").replace("{tier}", "tier-2"),
                Arrays.asList("&7Your team permanently gains", "&7Protection on all armor pieces!", "", "&aTier 1: Protection I, &b5 Diamonds",
                        "{tierColor}Tier 2: Protection II, &b{cost} {currency}",
                        "&7Tier 3: Protection III, &b20 Diamonds",
                        "&7Tier 4: Protection IV, &b30 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-3"), "{color}Reinforced Armor III");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "armor").replace("{tier}", "tier-3"),
                Arrays.asList("&7Your team permanently gains", "&7Protection on all armor pieces!", "", "&aTier 1: Protection I, &b5 Diamonds",
                        "&aTier 2: Protection II, &b10 Diamonds",
                        "{tierColor}Tier 3: Protection III, &b{cost} {currency}",
                        "&7Tier 4: Protection IV, &b30 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-4"), "{color}Reinforced Armor IV");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "armor").replace("{tier}", "tier-4"),
                Arrays.asList("&7Your team permanently gains", "&7Protection on all armor pieces!", "", "&aTier 1: Protection I, &b5 Diamonds",
                        "&aTier 2: Protection II, &b10 Diamonds",
                        "&aTier 3: Protection III, &b20 Diamonds",
                        "{tierColor}Tier 4: Protection IV, &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "miner").replace("{tier}", "tier-1"), "{color}Maniac Miner I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "miner").replace("{tier}", "tier-1"),
                Arrays.asList("&7All players on your team", "&7permanently gain Haste.", "", "{tierColor}Tier 1: Haste I, &b{cost} {currency}",
                        "&7Tier 2: Haste II, &b6 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "miner").replace("{tier}", "tier-2"), "{color}Maniac Miner II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "miner").replace("{tier}", "tier-2"),
                Arrays.asList("&7All players on your team", "&7permanently gain Haste.", "", "&aTier 1: Haste I, &b4 Diamonds",
                        "{tierColor}Tier 2: Haste II, &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "heal-pool").replace("{tier}", "tier-1"), "{color}Heal Pool");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "heal-pool").replace("{tier}", "tier-1"),
                Arrays.asList("&7Creates a Regeneration field", "&7around yor base!", "", "&7Cost: &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "dragon").replace("{tier}", "tier-1"), "{color}Dragon Buff");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "dragon").replace("{tier}", "tier-1"),
                Arrays.asList("&7Your team will have 2 dragons", "&7instead of 1 during deathmatch!", "", "&7Cost: &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "glass", "&8⬆&7Purchasable");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "glass", Collections.singletonList("&8⬇&7Traps Queue"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "first", "{color}Trap #1: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "first", Arrays.asList("&7The first enemy to walk", "&7into your base will trigger", "&7this trap!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "first",
                Arrays.asList("", "&7Purchasing a trap will", "&7queue it here. Its cost", "&7will scale based on the", "&7number of traps queued.", "", "&7Next trap: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "second", "{color}Trap #2: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "second", Arrays.asList("&7The second enemy to walk", "&7into your base will trigger", "&7this trap!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "second",
                Arrays.asList("", "&7Purchasing a trap will", "&7queue it here. Its cost", "&7will scale based on the", "&7number of traps queued.", "", "&7Next trap: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "third", "{color}Trap #3: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "third", Arrays.asList("&7The third enemy to walk", "&7into your base will trigger", "&7this trap!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "third",
                Arrays.asList("", "&7Purchasing a trap will", "&7queue it here. Its cost", "&7will scale based on the", "&7number of traps queued.", "", "&7Next trap: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "1", "{color}It's a trap!");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "1", Arrays.asList("&7Inflicts Blindness and Slowness", "&7for 5 seconds.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "2", "{color}Counter-Offensive Trap");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "2", Arrays.asList("&7Grants Speed I for 15 seconds to", "&7allied players near your base.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "3", "{color}Alarm Trap");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "3", Arrays.asList("&7Reveales invisible players as", "&7well as their name and team.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "4", "{color}Miner Fatigue Trap");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "4", Arrays.asList("&7Inflict Mining Fatigue for 10", "&7seconds.", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "back", "&aBack");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "back", Collections.singletonList("&7To Upgrades & Traps"));
        yml.addDefault(Messages.UPGRADES_CATEGORY_GUI_NAME_PATH + "traps", "&8Queue a trap");
        yml.addDefault(Messages.UPGRADES_TRAP_QUEUE_LIMIT, "&cTrap queue full!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_MSG, "&c&l{trap} è stata attivata!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_TITLE, "&cTRAPPOLA ATTIVATA!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_SUBTITLE, "&ffLa tua trappola {trap} è stata attivata!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_MSG + "3", "&c&lAllarme attivata da &7&l{player} &c&ldel team {color}&l{team} &c&l!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_TITLE + "3", "&c&lALLARME!!!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_SUBTITLE + "3", "&fAllarme attivata dal team {color}{team}&f!");
        save();
        setPrefix(m(Messages.PREFIX));
    }
}
