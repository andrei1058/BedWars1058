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

public class Polish extends Language{

    public Polish() {
        super(BedWars.plugin, "pl");
        YamlConfiguration yml = getYml();
        yml.options().header("Default bedwars1058 polish language by RarstManPL#0616 fixed by Endern");
        yml.addDefault(Messages.PREFIX, "");
        yml.options().copyDefaults(true);
        yml.addDefault("name", "Polski");

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

        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Poprawne uzycie: /" + mainCmd + " join §o<arena/group>");
        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Uzyj: /" + mainCmd + " join §o<arena/grupa>");
        yml.addDefault(Messages.COMMAND_NOT_ALLOWED_IN_GAME, "{prefix}&cNie mozesz tego zrobic.");
        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + mainCmd + " stats", "&2▪ &7/" + mainCmd + " join &o<arena/group>", "&2▪ &7/" + mainCmd + " leave", "&2▪ &7/" + mainCmd + " lang", "&2▪ &7/" + mainCmd + " gui", "&2▪ &7/" + mainCmd + " start &3(vip)"));
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL, "{prefix}&cTa arena jast pelna!\n&aRozwaz kupno rangi VIP+ aby odblokowac mase nowych funkcji. &7&o(kliknij)");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS, "{prefix}&cTa arena jest pelna.\n&cJako VIP mozesz wchodzic na pelne areny, lecz na tej arenie znajduje sie juz 1. lub wiecej vipow.");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "{prefix}&7{player} &eopuscil gre!");
        yml.addDefault(Messages.COMMAND_JOIN_GROUP_OR_ARENA_NOT_FOUND, "{prefix}&cNie istnieje zadna arena o nazwie: {name}");
        yml.addDefault(Messages.COMMAND_JOIN_NO_EMPTY_FOUND, "{prefix}&cObecnie nie ma zadnej dostepnej areny ;(");
        yml.addDefault(Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA, "{prefix}&cNie jestes na arenie!");
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "{prefix} &2Dostepne jezyki:");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7{iso} - &f{name}");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "{prefix}&7Uzyj: /lang &f&o<iso>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "{prefix}&cTen jezyk nie jest dostepny na serwerze!");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "{prefix}&aJezyk zostal zmieniony!");
        yml.addDefault(Messages.COMMAND_TP_PLAYER_NOT_FOUND, "{prefix}&cGracz nie zostal znaleziony!");
        yml.addDefault(Messages.COMMAND_TP_NOT_IN_ARENA, "{prefix}&cTen gracz nie jest na arenie!");
        yml.addDefault(Messages.COMMAND_TP_NOT_STARTED, "{prefix}&cArena, na ktorej znajduje sie ten gracz nie wystartowala!");
        yml.addDefault(Messages.COMMAND_TP_USAGE, "{prefix}&cPoprawne uzycie: /bw tp <username>");
        yml.addDefault(Messages.COMMAND_REJOIN_PLAYER_RECONNECTED, "{prefix}&7{player} &ewrocil do gry!");
        yml.addDefault(Messages.COMMAND_TP_PLAYER_NOT_FOUND, "{prefix}&cNie znaleziono gracza!");
        yml.addDefault(Messages.COMMAND_TP_NOT_IN_ARENA, "{prefix}&cTego gracza nie ma na arenie!");
        yml.addDefault(Messages.COMMAND_TP_NOT_STARTED, "{prefix}&cArena na której jest ten gracza, jeszcze się nie rozpoczeła!");
        yml.addDefault(Messages.COMMAND_TP_USAGE, "{prefix}&cUzycie: /bw tp <gracz>");
        yml.addDefault(Messages.COMMAND_REJOIN_PLAYER_RECONNECTED, "{prefix}&7{player} &eponownie się połączył!");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "{prefix}&cNie mozesz zmienic jezyka podczas gry.");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_PARTY_TOO_BIG, "{prefix}&cTwoje party jest zbyt duze abo dolaczyc do tej areny w pelnym skladzie :(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_NOT_PARTY_LEADER, "{prefix}&cTylko lider party moze wybrac arene.");
        yml.addDefault(Messages.COMMAND_JOIN_PLAYER_JOIN_MSG, "{prefix}&7{player} &edolaczyl do gry (&b{on}&e/&b{max}&e)!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "{prefix}&7{player} &eopuscil gre!");
        yml.addDefault(Messages.COMMAND_PARTY_HELP, Arrays.asList("&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&aParty Commands:", "&e/party help &7- &bPokazuje ta wiadomosc", "&e/party invite <player> &7- &bZaprasza gracza do twojego party",
                "&e/party leave &7- &bOpuszczasz obecne party",
                "&e/party remove <player> &7- &bWyrzuca gracza z twojego party",
                "&e/party accept <player> &7- &bAkceptuje zaproszenie do party", "&e/party disband &7- &bRozwiazuje party"));
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_USAGE, "{prefix}&cUzyj: &7/party invite <nick>");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &ejest offline!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT, "{prefix}&eZaprosiles gracza &7{player}&6.");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG, "{prefix}&b{player} &ezaprosil cie do swojego party! &o&7(Kliknijaby dolaczyc)");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF, "{prefix}&cNie mozesz zaprosic siebie samego!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE, "{prefix}&cNie masz zadnego zaproszenia do party.");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY, "{prefix}&eJuz jestes w party!");
        yml.addDefault(Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS, "{prefix}&cTylko lider party moze to zrobic!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_USAGE, "{prefix}&eUzyj: &7/party accept <nick>");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_SUCCESS, "{prefix}&7{player} &edolaczyl do party!");
        yml.addDefault(Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY, "{prefix}&cNie jestes w party!");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND, "{prefix}&cNie mozesz opuscic swojego party!\n&eSprobuj: &b/party disband");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_SUCCESS, "{prefix}&7{player} &eopuscil party!");
        yml.addDefault(Messages.COMMAND_PARTY_DISBAND_SUCCESS, "{prefix}&eParty zostalo rozwiazane!");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_USAGE, "{prefix}&7Uzyj: &e/party remove <nick>");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_SUCCESS, "{prefix}&7{player} &ezostal wyrzucony z party.");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER, "{prefix}&7{player} &enie jest w twoim party!");
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "{prefix}&cKomenda nie istnieje lub nie masz do niej uprawnien!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "§c▪ §7Nie jestes w zadnej grze!");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "§c▪ §7Odliczanie zostalo skrocone!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "{prefix}&Nie mozesz glosowac na natychmiastowy start areny.\n§7Rozwaz kupno rangi VIP+ aby odblokowac mase nowych funkcji!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_MSG, "{prefix}§6Obserwujesz teraz arene &9{arena}&6.\n{prefix}§eMozesz opuscic gre wpisujac &c/leave&e.");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &ejest offline!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG, "&cObserwatorzy nie moga ogladac tej areny!");
        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cNie mozesz tego zrobic. Zaczekaj {seconds} sekund!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG, "&cWidzowie nei mają wstępu na tę arene!");
        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cNie możesz tego zrobić! Poczekaj {seconds}!");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8Teleporter");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "{prefix}{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7Zycie: &f{health}%", "&7Jedzenie: &f{food}", "", "&7Lewy przycisk, aby obserwowac"));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lPowroc do lobby");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Kliknij PPM aby wyjsc do lobby!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&aSpectating &7{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&cSNEAK to exit");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eExiting Spectator mode");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "{prefix}§cWlasciciel party wyszedl z gry, przez co party zostalo usuniete!");
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "{prefix}&cZostales wyrzucony, poniewaz gracz z ranga VIP lub wyzsza dolaczyl na arene.\n&aRozwaz kupno rangi VIP+ aby odblokowac mase nowych funkcji. &7&o(kliknij)");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS_CHAT, "{prefix}&cBrak wystarczajacej ilosci graczy! Odliczanie zostalo przerwane!");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "{prefix}&eArena na ktorej jestes zostala zrestartowana.");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&cGra");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4Restartowanie");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&2Oczekiwanie §c{full}");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6Rozpoczynanie §c{full}");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8Dostepne areny");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l{name}");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7Status: {status}", "&7Gracze: &f{on}&7/&f{max}", "&7Typ: &a{group}", "", "&aLewy przycisk, aby dolaczyc.", "&ePrawy przycisk, aby ogladac."));
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CHAT, "{prefix}&eGra zaczyna sie za &6{time} &esekund/y!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE, "&a{second}");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-5", "&e❺");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-4", "&e❹");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-3", "&c❸");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-2", "&c❷");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-1", "&c❶");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_SUB_TITLE, "&cOczekiwanie na graczy..");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "",
                "&e&l        Chron swoje lozko i niszcz je innym.",
                "&e&l   Ulepsz wyposazenie swojej druzyny poprzez zdobywanie",
                "&e&lZelaza, Zlota, Szmaragdow, i Diamentow z generatorow ktore znajdziesz",
                "&e&l        na wyspach aby odblokowac potezne ulepszenia.", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&aSTART");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "{prefix}&cNie mozesz w tym momencie dolaczyc do areny. Kliknij prawy przycisk, aby obserwowac!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "{prefix}&cNie mozesz w tym momencie obserwowac tej areny. Uzyj lewego przycisku, aby dolaczyc!");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NO_PROXY, "&cMusisz dolaczyc na arene uzywajac BedwarsProxy. \n&eJeśli chcesz skonfigurować arenę, upewnij się, że przyznałeś sobie uprawnienia bw.setup, aby bezpośrednio dołączyć do serwera!");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&eTier &c{tier}");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&lDiamentow");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&lSzmaragdow");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&ePojawi sie za &c{seconds} &esekund/y");
        yml.addDefault(Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT, "{prefix}&eGenerator {generatorType} &ezostal ulepszony do stopnia &c{tier}");
        yml.addDefault(Messages.FORMATTING_STATS_DATE_FORMAT, "yyyy/MM/dd HH:mm");
        yml.addDefault(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH, "▮ ");
        yml.addDefault(Messages.FORMATTING_CHAT_LOBBY, "{level}{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_WAITING, "{level}{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SHOUT, "{level}{vPrefix}&6[SHOUT] {team} &7{player}&f{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_TEAM, "{level}{vPrefix}&f{team}&7 {player}{vSuffix} {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SPECTATOR, "{level}{vPrefix}&7[SPECTATOR] {player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_HEALTH, Arrays.asList("&c❤", "&aHealth"));
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
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_YOUR_TEAM, "&7 TY");
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fSledzenie: {team} &f- Dystans: {distance}m");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM, "{TeamColor}[{TeamName}]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT, "&6[KRZYK]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR, "&7[OBSERWATOR]");
        yml.addDefault(Messages.MEANING_SHOUT, "okrzyk");
        yml.addDefault(Messages.MEANING_NOBODY, "Nikt");
        yml.addDefault(Messages.MEANING_FULL, "Pelna");
        yml.addDefault(Messages.MEANING_IRON_SINGULAR, "Zelaza");
        yml.addDefault(Messages.MEANING_IRON_PLURAL, "Zelaza");
        yml.addDefault(Messages.MEANING_GOLD_SINGULAR, "Zlota");
        yml.addDefault(Messages.MEANING_GOLD_PLURAL, "Zlota");
        yml.addDefault(Messages.MEANING_EMERALD_SINGULAR, "Szmaragdow");
        yml.addDefault(Messages.MEANING_EMERALD_PLURAL, "Szmaragdy");
        yml.addDefault(Messages.MEANING_DIAMOND_SINGULAR, "Diamentow");
        yml.addDefault(Messages.MEANING_DIAMOND_PLURAL, "Diamenty");
        yml.addDefault(Messages.MEANING_VAULT_SINGULAR, "$");
        yml.addDefault(Messages.MEANING_VAULT_PLURAL, "$");
        yml.addDefault(Messages.MEANING_NEVER, "Never");
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "{prefix}&cNie mozesz tutaj budowac!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "{prefix}&c&cMozesz zniszczyc tylko bloki postawione przez graczy!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&cNie mozesz zniszczyc swojego lozka!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&lDESTRUCKJA LOZKA > {TeamColor}{TeamName} Lozko &7zostalo zniszczone przez {PlayerColor}{PlayerName}&7!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&cStraciles lozko!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&fJuz sie nie odrodzisz!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&f&lDESTRUCKJA LOZKA > {TeamColor}{TeamName} Lozko &7zostalo zniszczone przez {PlayerColor}{PlayerName}&7!");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cNie mozesz otworzyc skrzyni tej druzyny poniewaz zostala ona wyeliminowana!");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "{PlayerColor}{PlayerName} &7spadl do pustki.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "{PlayerColor}{PlayerName} &7spadl do pustki. &b&lOSTATECZNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "{PlayerColor}{PlayerName} &7zostal zepchniety do pustki przez {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal zepchniety do pustki przez {KillerColor}{KillerName}&7. &b&lFINALNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_REGULAR_KILL, "{PlayerColor}{PlayerName} &7zostal popchniety przez {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal popchniety przez {KillerColor}{KillerName}&7. &b&lFINALNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez bombe gracza {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez bombe gracza {KillerColor}{KillerName}&7. &b&lFINALNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez {KillerColor}{KillerName}&7. &b&lFINALNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_REGULAR, "{PlayerColor}{PlayerName} &7wyszedl podczas walki z {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_FINAL, "{PlayerColor}{PlayerName} &7wyszedl podczas walki z {KillerColor}{KillerName}&7. &b&lFINALNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cUMARLES");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eOdrodzisz sie za &c{time} &esekund/y!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "{prefix}&eOdrodzisz sie za &c{time} &esekund/y!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aODRODZILES SIE!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "{prefix}&cZostales wyeliminowany!");
        yml.addDefault(Messages.PLAYER_HIT_BOW, "{prefix}{TeamColor}{PlayerName} &7is on &c{amount} &7HP!");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR, "{PlayerColor}{PlayerName} &7umarl.");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL, "{PlayerColor}{PlayerName} &7umarl. &b&lFINALNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_REGULAR, "{PlayerColor}{PlayerName} &7zostal zastrzelony przez {KillerColor}{KillerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal zastrzelony przez {KillerColor}{KillerName}&7! &b&lFINALNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_REGULAR, "{PlayerColor}{PlayerName} &7zostal zabity przez {KillerColor}{KillerTeamName} &7Pluskwa!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez {KillerColor}{KillerTeamName} &7Pluskwa! &b&lFINALNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_REGULAR, "{PlayerColor}{PlayerName} &7zostal zabity przez {KillerColor}{KillerTeamName}'s &7Iron Golem!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez Iron golema {KillerColor}{KillerTeamName}! &b&lFINALNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_DIAMOND, "{prefix}&b+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_EMERALD, "{prefix}&a+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_IRON, "{prefix}&f+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_GOLD, "{prefix}&6+{amount} {meaning}");

        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR, "{PlayerColor}{PlayerName} &7zostal zabity przez bombe.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez bombe. &b&lFINALNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "&8Statystyki gracza {player}");
        yml.addDefault(Messages.GAME_END_GAME_OVER_PLAYER_TITLE, "&c&lPRZEGRANA!");
        yml.addDefault(Messages.GAME_END_VICTORY_PLAYER_TITLE, "&6&lZWYCIESTWO!");
        yml.addDefault(Messages.GAME_END_TOP_PLAYER_CHAT, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "", "{winnerFormat}", "", "",
                "&e                       &lTOP 1 Zabojca &7- {firstName} - {firstKills}",
                "&6                       &lTOP 2 Zabojca &7- {secondName} - {secondKills}",
                "&c                       &lTOP 3 Zabojca &7- {thirdName} - {thirdKills}", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.GAME_END_TEAM_WON_CHAT, "{prefix}{TeamColor}{TeamName} &awygral gre!");
        yml.addDefault(Messages.NEXT_EVENT_BEDS_DESTROY, "&dZniszczenie lozek&f:");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_II, "&bDiament &cII&f:");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_III, "&bDiament &cIII&f:");
        yml.addDefault(Messages.NEXT_EVENT_DRAGON_SPAWN, "&cNagla Smierc&f:");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_II, "&2Szmaragd &cII&f:");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_III, "&2Szmargd &cIII&f:");
        yml.addDefault(Messages.NEXT_EVENT_GAME_END, "&4Koniec gry&f:");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED, "&cLOZKO ZOSTALO ZNISZCZONE!");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED, "&fWszystkie lozka zostaly zniszczone!");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED, "&c&lWszystkie lozka zostaly zniszczone!");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH, "&cNagla Smierc");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH, "");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH, "&cNAGLA SMIERC: &6&b{TeamDragons} &cSmok: {TeamColor}{TeamName}");

        /* Lobby Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "stats"), "&eStatystyki");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fKliknij prawy przycisk aby zobaczyc swoje statystyki!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&eAreny");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Collections.singletonList("&fWybierz arene!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&eWroc do lobby");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fKliknij prawy przyciskaby wyjsc do lobby!"));
        /* Pre Game Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&eStatystyki");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fKliknij prawy przycisk, aby zobaczyc twoje statystyki!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&eWroc do lobby");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fKliknij prawy przycisk aby wyjsc do lobby!"));
        /* Spectator Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&eTeleporter");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "teleporter"), Collections.singletonList("&fKliknij prawy przycisk aby obserwowac graczy!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&eWroc do lobby");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fPPM aby wyjsc do lobby!"));

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList("&e&lBED WARS", "&7{date} &8{server}", "", "&fMapa: &a{map}", "", "&fGracze: &a{on}/{max}", "", "&fOczekiwanie...", "", "§fTryb: &a{group}", "&fWersja: &7{version}", "", "&e{server_ip}"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList("&e&lBED WARS", "&7{date} &8{server}", "", "&fMapa: &a{map}", "", "&fGracze: &a{on}/{max}", "", "&fRozpoczecie &a{time}s", "", "§fTryb: &a{group}", "&fWersja: &7{version}", "", "&e{server_ip}"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList("&f&lBED WARS", "&7{date} &8{server}", "", "&fMapa: &a{map}", "", "&fGracze: &a{on}/{max}", "", "&fOczekiwanie...", "", "§fTryb: &a{group}", "&fWersja: &7{version}", "", "&e{server_ip}"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList("&f&lBED WARS", "&7{date} &8{server}", "", "&fMapa: &a{map}", "", "&fGracze: &a{on}/{max}", "", "&fRozpoczecie &a{time}s", "", "§fTryb: &a{group}", "&fWersja: &7{version}", "", "&e{server_ip}"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{TeamRedColor}R&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}B&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}G&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}Y &f{TeamYellowName}&f: {TeamYellowStatus}",
                "{TeamAquaColor}A &f{TeamAquaName}&f: {TeamAquaStatus}", "{TeamWhiteColor}W &f{TeamWhiteName}&f: {TeamWhiteStatus}", "{TeamPinkColor}P &f{TeamPinkName}&f: {TeamPinkStatus}",
                "{TeamGrayColor}S &f{TeamGrayName}&f: {TeamGrayStatus}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.Doubles.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} za &a{time}", "", "{TeamRedColor}R&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}B&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}G&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}Y &f{TeamYellowName}&f: {TeamYellowStatus}",
                "{TeamAquaColor}A &f{TeamAquaName}&f: {TeamAquaStatus}", "{TeamWhiteColor}W &f{TeamWhiteName}&f: {TeamWhiteStatus}", "{TeamPinkColor}P &f{TeamPinkName}&f: {TeamPinkStatus}",
                "{TeamGrayColor}S &f{TeamGrayName}&f: {TeamGrayStatus}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.3v3v3v3.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} za &a{time}", "", "{TeamRedColor}R&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}B&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}G&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}Y &f{TeamYellowName}&f: {TeamYellowStatus}",
                "", "&fZabojstwa: &a{kills}", "&fFinalne zabojstwa: &a{finalKills}", "&fZniszczone lozka: &a{beds}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.4v4v4v4.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{TeamRedColor}R&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}B&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}G&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}Y &f{TeamYellowName}&f: {TeamYellowStatus}",
                "", "&fZabojstwa: &a{kills}", "&fOstateczne Zabojstwa: &a{finalKills}", "&fZniszczone Lozka: &a{beds}", "", "&e{server_ip}"));

        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&e&lBEDWARS", "", "&fPoziom: {level}", "", "&fPostep: &a{currentXp}&7/&b{requiredXp}", "{progress}", "", "&7{player}", "", "&fMonety: &a{money}"
                , "", "&fZwyciestwa: &a{wins}", "&fZabojstwa: &a{kills}", "", "&e{server_ip}"));

        yml.addDefault(Messages.XP_REWARD_PER_MINUTE, "{prefix}&6+{xp} doswiadczenia &7(Czas Gry).");
        yml.addDefault(Messages.XP_REWARD_WIN, "{prefix}&6+{xp} doswiadczenia &7(Wygrana Gra).");
        yml.addDefault(Messages.XP_REWARD_PER_TEAMMATE, "{prefix}&6+{xp} doswiadczenia. &7(Pomoc Druzynie).");
        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&6&lBedWars", "&fTwoj poziom: {level}", "", "&fProgress: &a{currentXp}&7/&b{requiredXp}", "{progress}", "", "&7{player}", "", "&fMonety: &a{money}"
                , "", "&fWygrane: &a{wins}", "&fZabojstwa: &a{kills}", "", "&e{server_ip}"));

        yml.addDefault(Messages.XP_REWARD_PER_MINUTE, "{prefix}&6+{xp} Otrzymano doswiedczenie BedWars (Czas grania).");
        yml.addDefault(Messages.XP_REWARD_WIN, "{prefix}&6+{xp} Otrzymano doswiedczenie BedWars (Wygrana gra).");
        yml.addDefault(Messages.XP_REWARD_PER_TEAMMATE, "{prefix}&6+{xp} Otrzymano doswiedczenie BedWars (Team Support).");

        yml.addDefault(Messages.MONEY_REWARD_PER_MINUTE, "{prefix}&6+{money} Monety (Czas grania).");
        yml.addDefault(Messages.MONEY_REWARD_WIN, "{prefix}&6+{money} Monety (Wygrana gra).");
        yml.addDefault(Messages.MONEY_REWARD_PER_TEAMMATE, "{prefix}&6+{money} Monety (Druzynowa wspolpraca).");
        yml.addDefault(Messages.MONEY_REWARD_BED_DESTROYED, "{prefix}&6+{money} Monety (Zniszczone lozko).");
        yml.addDefault(Messages.MONEY_REWARD_FINAL_KILL, "{prefix}&6+{money} Monety (Finalne zabojstwo).");
        yml.addDefault(Messages.MONEY_REWARD_REGULAR_KILL, "{prefix}&6+{money} Monety (Regularne zabojstwo).");

        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&lELIMINACJA > {TeamColor}{TeamName} &cdruzyna &czostala wyeliminowana!\n");
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&lChron swojego lozka!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lTwoje lozko zostalo zniszczone!");

        yml.addDefault(Messages.NPC_NAME_TEAM_UPGRADES, "&bDRUZYNOWE ULEPSZENIA,&e&lRIGHT CLICK");
        yml.addDefault(Messages.NPC_NAME_SOLO_UPGRADES, "&bSOLO ULEPSZENIA,&e&lPRAWY PRZYCISK");
        yml.addDefault(Messages.NPC_NAME_TEAM_SHOP, "&bSKLEP Z PRZEDMIOTAMI,&e&lPRAWY PRZYCISK");
        yml.addDefault(Messages.NPC_NAME_SOLO_SHOP, "&bSKLEP Z PRZEDMIOTAMI,&e&lPRAWY PRZYCISK");

        //SHOP
        yml.addDefault(Messages.SHOP_INDEX_NAME, "&8Quick Buy");
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "{prefix}&cNie masz wymaganej ilosci {currency}! Potrzebujesz {amount} wiecej!");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "{prefix}&aKupiles &6{item}");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "{prefix}&cJuz to kupiles!");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "{TeamColor}&l{TeamName} &r{TeamColor}Pluskwa");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "{TeamColor}{despawn}s &8[ {TeamColor}{health}&8]");
        yml.addDefault(Messages.SHOP_SEPARATOR_NAME, "&8⇧ Kategorie");
        yml.addDefault(Messages.SHOP_SEPARATOR_LORE, Collections.singletonList("&8⇩ Przedmioty"));
        yml.addDefault(Messages.SHOP_QUICK_BUY_NAME, "&bSzybkie kupowanie");
        yml.addDefault(Messages.SHOP_QUICK_BUY_LORE, new ArrayList<>());
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_NAME, "&cPusty slot!");
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_LORE, Arrays.asList("&7To jest wolny slot!", "&7do szybkiego kupowania,", "&7kliknij Prawym + Shift", "&7na przedmiot ktory chcesz dodac."));
        yml.addDefault(Messages.SHOP_CAN_BUY_COLOR, "&a");
        yml.addDefault(Messages.SHOP_CANT_BUY_COLOR, "&c");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CAN_BUY, "&eKliknij, aby kupic!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CANT_AFFORD, "&cNie posiadasz wystarczajacej ilosci {currency}!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_MAXED, "&aMaksymalne!");
        yml.addDefault(Messages.SHOP_LORE_QUICK_ADD, "&bShift + Lewy przycisk aby dodac do kategorii szybkiego kupowania");
        yml.addDefault(Messages.SHOP_LORE_QUICK_REMOVE, "Shift + Lewy przycisk aby usunac z kategorii szybkiego kupowania!");


        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "&8Bloki", "&aBloki", Collections.singletonList("&eKliknij aby przegladac!"));
        addContentMessages(yml, "wool", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Welna", Arrays.asList("&7Koszt: &f{cost} {currency}", "", "&7Swietne, aby przemieszczac sie", "&7do innych wrogich wysp.","", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "clay", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Utwardzona Glina", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Idealne do ochrony twojego lozka.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "glass", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Wybucho-Odporne Szklo", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Idealne do ochrony przeciwko wybuchom.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "stone", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Kamien Kresu", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Solidna ochrona twojego lozka.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "ladder", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Drabina", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Przydatne do ratowania kotów, które", "&7utknely na drzewie.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "obsidian", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Obsydian", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Najlepsza ochrona twojego lozka.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "wood", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Debowe Deski", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Solidna ochrona twojego lozka", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_MELEE, "&8Miecze", "&aMiecze", Collections.singletonList("&eKliknij aby przegladac!"));
        addContentMessages(yml, "stone-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Kamienny Miecz", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "iron-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Zelazny Miecz", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "diamond-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Diamentowy Miecz", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "stick", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Patyk (Odrzut I)", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "&8Zbroja", "&aZbroja", Collections.singletonList("&eKliknij aby przegladac!"));
        addContentMessages(yml, "chainmail", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Kolczuga", Arrays.asList("&7Koszt: {cost} {currency}",
                "", "&7Kolczugowe spodenki i buty,", "&7ktore nie nigdy znikna", "&7with.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "iron-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Zelazna zbroja", Arrays.asList("&7Koszt: {cost} {currency}",
                "", "&7Zelazne spodenki i buty,", "&7ktore nie nigdy nie znikna.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "diamond-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Diamentowa zbroja", Arrays.asList("&7Koszt: {cost} {currency}",
                "", "&7Diamentowe spodnenki i buty", "&7ktore nie nigdy nie zniszcza sie.", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "&8Narzedzia", "&aNarzedzia", Collections.singletonList("&eKliknij aby przegladac!"));
        addContentMessages(yml, "shears", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Nozyczki", Arrays.asList("&7Koszt: {cost} {currency}",
                "", "&7Idealny przedmiot to usuwania welny.", "&7Zawsze otrzymasz je po smierci.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "pickaxe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Kilof {tier}", Arrays.asList("&7Koszt: {cost} {currency}", "&7Tier: &e{tier}",
                "", "&7To jest przedmiot, ktory mozna ulepszyc.", "&7Po smierci wracasz do.", "&7poprzedniego kilofu!", "", "&7Zawsze pojawi sie", "&7po smierci", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "axe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Siekiera {tier}", Arrays.asList("&7Koszt: {cost} {currency}", "&7Tier: &e{tier}",
                "", "&7To jest przedmiot, ktory mozna ulepszyc.", "&7Po smierci wracasz do.", "&7poprzedniej siekiery!", "", "&7Zawsze pojawi sie", "&7po smierci", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_RANGED, "&8Luki", "&aLuki", Collections.singletonList("&eKliknij aby przegladac!"));
        addContentMessages(yml, "arrow", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Strzaly", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow1", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Luk", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow2", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Luk (Moc I)", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow3", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Luk (Moc I, Uderzenie I)", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "&8Mikstury", "&aMikstury", Collections.singletonList("&eKliknij aby przegladac!"));
        addContentMessages(yml, "speed-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Szybkosc II (45 sekund)", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "jump-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Zwiekszony skok V (45 sekund)", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "invisibility", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Niewidzialnosc (30 sekund)", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));


        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "&8Uzyteczne", "&aUzyteczne", Collections.singletonList("&eKliknij aby przegladac!"));
        addContentMessages(yml, "golden-apple", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Zlote Jablko", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Magiczne jablko, ktore leczy!", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bedbug", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Pluskwa", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Przywoluje silverfisha do",
                "&7twojej magicznej kuli", "&7ze sniegu. Atakuja i.", "&7znikaja po 15 sekundach", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "dream-defender", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Zelazny Golem", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Ten zelazny ochroniarz pomoże w obronie",
                "&7twojej bazy. Po 4 minutach zniknie.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "fireball", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Ognista Kula", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Kliknij prawy przycisk aby wystrzelic!",
                "&7Idealne do zrzucania wrogow,", "&7badz niszczenia mostow", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "tnt", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}TNT", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Czarna magia natychmiast",
                "&7zapali ten ladunek wybuchowy!", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "ender-pearl", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Perla Kresu", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Szybki sposob na",
                "&7przemieszczanie sie.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "water-bucket", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Wiaderko wody", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Idealne do skoku na bungee, ale",
                "&7jak nie postawisz wiadra, zginiesz.", "&7Badz antywybuchowa ochrona lozka .", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bridge-egg", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Magiczny Most", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Te magiczne jajko stworzy",
                "&7dla ciebie most.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "magic-milk", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Magiczne Mleko", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Unikaj wrogich pulapek. Wypij i",
                "&7nagle wszystkie zle efekty znikna.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "sponge", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Gombka", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Idealne do usuwania wody przeciwnika.",
                "", "{quick_buy}", "{buy_status}"));

        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "&aKliknij aby kupic!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "&cBrak odpowiedniej ilosci: &4{currency}");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cZABLOKOWANE");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "&aODBLOKOWANE");
        yml.addDefault("upgrades.Default.generators.tier1.name", "&eZelazne Ulepszenie");
        yml.addDefault("upgrades.Default.generators.tier1.lore", Arrays.asList("&7Zwieksza przyrost pojawiania sie", "&7Zelaza &7oraz &6Zlota &7o 50%.", "", "&7Koszt:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.generators.tier2.name", "&eZlote Ulepszenie");
        yml.addDefault("upgrades.Default.generators.tier2.lore", Arrays.asList("&7Zwieksza przyrost pojawiania sie", "&7Zelaza &7oraz &6Zlota &7o 100%.", "", "&7Koszt:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.generators.tier3.name", "&eSzmaragdowe Ulepszenie");
        yml.addDefault("upgrades.Default.generators.tier3.lore", Arrays.asList("&7Aktywuje pojawianie sie &2Szmaragdow", "&7w generatorze twojej druzyny.", "", "&7Koszt:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.maniacMiner.tier1.name", "&eZwariowany Gornik");
        yml.addDefault("upgrades.Default.maniacMiner.tier1.lore", Arrays.asList("&7Wszystkie osoby z twojej druzyny", "&7otrzymuja Pospiech I na czas rozgrywki", "", "&7Koszt:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.sharpSword.tier1.name", "&eOstry Miecz");
        yml.addDefault("upgrades.Default.sharpSword.tier1.lore", Arrays.asList("&7Twoja druzyna otrzymuje Ostrosc I", "&7na wszystkich mieczach!", "", "&7Koszt:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.reinforced.tier1.name", "&eWzmocniony Pancerz");
        yml.addDefault("upgrades.Default.reinforced.tier1.lore", Arrays.asList("&7Twoja druzyna otrzymuje Ochrone I", "&7na kazdej czesci pancerza!", "", "&7Koszt:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.trap.tier1.name", "&eTo jest pulapka!");
        yml.addDefault("upgrades.Default.trap.tier1.lore", Arrays.asList("&7Pierwszy przeciwnik w twojej bazie", "&7otrzymuje oslepienie i spowolnienie!", "", "&7Koszt:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.miningFatigue.tier1.name", "&eWyczerpanie gornika");
        yml.addDefault("upgrades.Default.miningFatigue.tier1.lore", Arrays.asList("&7Pierwszy przeciwnik w twojej bazie", "&7otrzyma wyczerpani na 10 sekund!", "", "&7Koszt:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.healPool.tier1.name", "&ePole Regeneracyjne");
        yml.addDefault("upgrades.Default.healPool.tier1.lore", Arrays.asList("&7Tworzy Pole Regeneracyjne", "&7wokol twojej bazy!", "", "&7Koszt:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a{player} kupil &6{upgradeName}");


        /* save default items messages for stats gui */
        addDefaultStatsMsg(yml, "wins", "&6Wygrane", "&f{wins}");
        addDefaultStatsMsg(yml, "losses", "&6Przegrane", "&f{losses}");
        addDefaultStatsMsg(yml, "kills", "&6Wygrane", "&f{kills}");
        addDefaultStatsMsg(yml, "deaths", "&6Smierci", "&f{deaths}");
        addDefaultStatsMsg(yml, "final-kills", "&6Ostateczne Zabojstwa", "&f{finalKills}");
        addDefaultStatsMsg(yml, "final-deaths", "&6Ostateczne Smierci", "&f{finalDeaths}");
        addDefaultStatsMsg(yml, "beds-destroyed", "&6Zniszczone Lozka", "&f{bedsDestroyed}");
        addDefaultStatsMsg(yml, "first-play", "&66Ostatnia gra", "&f{firstPlay}");
        addDefaultStatsMsg(yml, "last-play", "&6Ostatnia gra", "&f{lastPlay}");
        addDefaultStatsMsg(yml, "games-played", "&6Rozegrane gry", "&f{gamesPlayed}");

        yml.addDefault(Messages.REJOIN_NO_ARENA, "{prefix}&cNie masz zadnej gry, do ktorej mozesz wrocic!");
        yml.addDefault(Messages.REJOIN_DENIED, "{prefix}&cNie mozesz wrocic do tej gry. Gra sie zakonczyla lub twoje lozko zostalo zniszczone.");
        yml.addDefault(Messages.REJOIN_ALLOWED, "{prefix}&eTrwa dolaczanie do areny &a{arena}&e!");

        yml.addDefault(Messages.MEANING_NO_TRAP, "Brak pulapki!");
        yml.addDefault(Messages.FORMAT_UPGRADE_TRAP_COST, "&7Cena: {currencyColor}{cost} {currency}");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CAN_AFFORD, "&e");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CANT_AFFORD, "&c");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_UNLOCKED, "&a");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_LOCKED, "&7");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_UNLOCKED, "&a");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "{color}Kliknij, aby kupic!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "{color}Nie posiadasz wystarczajacej ilosci {currency}");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cZABLOKOWANE");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "{color}ODBLOKOWANE");
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a{player} zakupil &6{upgradeName}");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-1"), "{color}Szybszy piecyk");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "forge").replace("{tier}", "tier-1"),
                Arrays.asList("&7Zwieksza przyrost pojawiania sie", "&7surowcow na twojej wyspie.", "", "{tierColor}Tier 1: +50% surowcow, &b{cost} {currency}",
                        "&7Tier 2: +100% surowcow, &b8 diamentow",
                        "&7Tier 3: Pojawienie sie emeraldow, &b12 diamentow",
                        "&7Tier 4: +200% surowcow, &b16 diamentow", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-2"), "{color}Zlote Ulepszenie");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "forge").replace("{tier}", "tier-2"),
                Arrays.asList("&7Zwieksza przyrost pojawiania sie", "&7surowcow na twojej wyspie.", "", "&aTier 1: +50% Resources, &b{cost} {currency}",
                        "{tierColor}Tier 2: +100% Resources, &b8 diamentow",
                        "&7Tier 3: Pojawienie sie emeraldow, &b12 diamentow",
                        "&7Tier 4: +200% Resources, &b16 diamentow", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-3"), "{color}Szmaragdowe Ulepszenie");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "forge").replace("{tier}", "tier-3"),
                Arrays.asList("&7Zwieksza przyrost pojawiania sie", "&7surowcow na twojej wyspie.", "", "&aTier 1: +50% surowcow, &b{cost} {currency}",
                        "&aTier 2: +100% surowcow, &b8 diamentow",
                        "{tierColor}Tier 3: Pojawienie sie emeraldow, &b12 diamentow",
                        "&7Tier 4: +200% surowcow, &b16 diamentow", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-4"), "{color}Stopione ulepszenie");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "forge").replace("{tier}", "tier-4"),
                Arrays.asList("&7Zwieksza przyrost pojawiania sie", "&7surowcow na twojej wyspie.", "", "&aTier 1: +50% surowcow, &b{cost} {currency}",
                        "&aTier 2: +100% surowcow, &b8 diamentow",
                        "&aTier 3: Pojawienie sie emeraldow, &b12 diamentow",
                        "{tierColor}Tier 4: +200% surowcow, &b16 diamentow", ""));
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_NAME_PATH + "traps", "&eKup pulapke");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_LORE_PATH + "traps", Arrays.asList("&7Zakupione pulapki bedą", "&7znajdowaly sie w kolejce.", "", "&eKliknij, aby zobaczyc!"));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "swords").replace("{tier}", "tier-1"), "{color}Zaostrzony miecz");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "swords").replace("{tier}", "tier-1"),
                Arrays.asList("&7Twoja druzyna otrzyma enchant", "&7Sharpness I na swoich mieczach", "&7oraz posiadanych siekierach!", "", "&7Cena: &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-1"), "{color}Zbroja I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "armor").replace("{tier}", "tier-1"),
                Arrays.asList("&7Twoja druzyna otrzymuje enchant", "&7Protection I na swojej zbroji!", "", "{tierColor}Tier 1: Protection I, &b{cost} {currency}",
                        "&7Tier 2: Protection II, &b10 diamentow",
                        "&7Tier 3: Protection III, &b20 diamentow",
                        "&7Tier 4: Protection IV, &b30 diamentow", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-2"), "{color}Zbroja II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "armor").replace("{tier}", "tier-2"),
                Arrays.asList("&7Twoja druzyna otrzymuje enchant\", \"&7Protection I na swojej zbroji!", "", "&aTier 1: Protection I, &b5 Diamonds",
                        "{tierColor}Tier 2: Protection II, &b{cost} {currency}",
                        "&7Tier 3: Protection III, &b20 diamentow",
                        "&7Tier 4: Protection IV, &b30 diamentow", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-3"), "{color}Zbroja III");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "armor").replace("{tier}", "tier-3"),
                Arrays.asList("&7Twoja druzyna otrzymuje enchant\", \"&7Protection I na swojej zbroji!", "", "&aTier 1: Protection I, &b5 Diamonds",
                        "&aTier 2: Protection II, &b10 diamentow",
                        "{tierColor}Tier 3: Protection III, &b{cost} {currency}",
                        "&7Tier 4: Protection IV, &b30 diamentow", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-4"), "{color}Zbroja IV");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "armor").replace("{tier}", "tier-4"),
                Arrays.asList("&7Twoja druzyna otrzymuje enchant\", \"&7Protection I na swojej zbroji!!", "", "&aTier 1: Protection I, &b5 Diamonds",
                        "&aTier 2: Protection II, &b10 diamentow",
                        "&aTier 3: Protection III, &b20 diamentow",
                        "{tierColor}Tier 4: Protection IV, &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "miner").replace("{tier}", "tier-1"), "{color}Kopacz I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "miner").replace("{tier}", "tier-1"),
                Arrays.asList("&7Twoja druzyna otrzyma", "&7efekt szybkiego kopania.", "", "{tierColor}Tier 1: Haste I, &b{cost} {currency}",
                        "&7Tier 2: Haste II, &b6 diamentow", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "miner").replace("{tier}", "tier-2"), "{color}Kopacz II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "miner").replace("{tier}", "tier-2"),
                Arrays.asList("&7Twoja druzyna otrzyma", "&7efekt szybkiego kopania.", "", "&aTier 1: Haste I, &b4 diamentow",
                        "{tierColor}Tier 2: Haste II, &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "heal-pool").replace("{tier}", "tier-1"), "{color}Regeneracja");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "heal-pool").replace("{tier}", "tier-1"),
                Arrays.asList("&7Tworzy w twojej bazie", "&7efekt regeneracji!", "", "&7Cena: &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "dragon").replace("{tier}", "tier-1"), "{color}Dodatkowy smok");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "dragon").replace("{tier}", "tier-1"),
                Arrays.asList("&7Twoja druzyna 2 smoki zamiast", "&71 podczas deathmach!", "", "&7Cena: &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "glass", "&8⬆&7Do kupienia");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "glass", Collections.singletonList("&8⬇&7Kolejka pulapek"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "first", "{color}Pulapka #1: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "first", Arrays.asList("&7Pierwsza osoba, ktora wkroczy", "&7na twoj teren otrzyma", "&7pulapke!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "first",
                Arrays.asList("", "&7Kupujac pulapke, zostanie ona", "&7dodana tutaj w kolejce. Pulapka", "&7nastepna bedzie uruchamiac sie", "&7gdy wkroczy nastepny wrog.", "", "&7Nastepna pulapka: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "second", "{color}Pulapka #2: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "second", Arrays.asList("&7Druga osoba, ktora wkroczy", "&7na twoj teren otrzyma", "&7pulapke!!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "second",
                Arrays.asList("", "&7Kupujac pulapke, zostanie ona", "&7dodana tutaj w kolejce. Pulapka", "&7nastepna bedzie uruchamiac sie", "&7gdy wkroczy nastepny wrog.", "", "&7Nastepna pulapka: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "third", "{color}Pulapka #3: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "third", Arrays.asList("&7Trzecia osoba, ktora wkroczy", "&7na twoj teren otrzyma", "&7pulapke!!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "third",
                Arrays.asList("", "&7Kupujac pulapke, zostanie ona", "&7dodana tutaj w kolejce. Pulapka", "&7nastepna bedzie uruchamiac sie", "&7gdy wkroczy nastepny wrog.", "", "&7Nastepna pulapka: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "1", "{color}To jest pulapka!");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "1", Arrays.asList("&7Wrog otrzyma spowolnienie na 5 sekund", "&7i male pole widzenia przez 75 sekund.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "2", "{color}Maszyna szybkosci");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "2", Arrays.asList("&7Otrzymasz efekt szybkosci I, gdy", "&7ktos wkroczy na teren twojej bazy.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "3", "{color}Alarm");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "3", Arrays.asList("&7Gdy ktos wkroczy na teren twojej", "&7bazy zostaniesz poinformowany.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "4", "{color}Spowolnienie Gornika");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "4", Arrays.asList("&7Wrog otrzyma efekt wolnego", "&7kopania na 10 sekund.", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "back", "&aPowrot");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "back", Collections.singletonList("&7Do menu glownego ulepszen"));
        yml.addDefault(Messages.UPGRADES_CATEGORY_GUI_NAME_PATH + "traps", "&8Kolejka pulapek");
        yml.addDefault(Messages.UPGRADES_TRAP_QUEUE_LIMIT, "&cKolejka pulapek jest pelna!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_MSG, "&c&l{trap} zostala uruchomiona!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_TITLE, "&cPULAPKA!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_SUBTITLE, "&fPulapka {trap} zostala uruchomiona!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_MSG + "3", "&c&lAlarm wykryl wroga &7&l{player} &c&lz druzyny {color}&l{team} &c&lteam!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_TITLE + "3", "&c&lALARM");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_SUBTITLE + "3", "&fAlarm zostal uruchomiony przez {color}{team} &fdruzyne!");
        save();
        setPrefix(m(Messages.PREFIX));
    }
}
&fAlarm zostal uruchomiony przez {color}{team} &fdruzyne!");
        save();
        setPrefix(m(Messages.PREFIX));
    }
}
