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
        yml.options().header("Polish translation by RarstManPL#0616");
        yml.addDefault(Messages.PREFIX, "");
        yml.options().copyDefaults(true);
        yml.addDefault("name", "Polski");

        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Uzyj: /" + mainCmd + " join §o<arena/group>");
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
        yml.addDefault(Messages.COMMAND_TP_PLAYER_NOT_FOUND, "{prefix}&cPlayer not found!");
        yml.addDefault(Messages.COMMAND_TP_NOT_IN_ARENA, "{prefix}&cThis player is not in a bedwars arena!");
        yml.addDefault(Messages.COMMAND_TP_NOT_STARTED, "{prefix}&cThe arena where the player is didn't start yet!");
        yml.addDefault(Messages.COMMAND_TP_USAGE, "{prefix}&cUsage: /bw tp <username>");
        yml.addDefault(Messages.COMMAND_REJOIN_PLAYER_RECONNECTED, "{prefix}&7{player} &ehas reconnected!");
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
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_USAGE, "{prefix}&eUzyj: &7/party invite <nick>");
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
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER, "{prefix}&7{player} &eis not in your party!");
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "{prefix}&cKomenda nie istnieje lub nie masz do niej uprawnien!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "§c▪ §7Nie jestes w zadnej grze!");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "§c▪ §7Odliczanie zostalo skrocone!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "{prefix}&Nie mozesz glosowac na natychmiastowy start areny.\n§7Rozwaz kupno rangi VIP+ aby odblokowac mase nowych funkcji!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_MSG, "{prefix}§6Obserwujesz teraz arene &9{arena}&6.\n{prefix}§eMozesz opuscic gre wpisujac &c/leave&e.");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &ejest offline!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG, "&cSpectators are not allowed in this arena!");
        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cYou can't do that yet! Wait {seconds} more seconds!");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8Teleporter");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "{prefix}{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7Zycie: &f{health}%", "&7Jedzenie: &f{food}", "", "&7Left-click to spectate"));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lPowroc do lobby");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Kliknij PPM aby wyjsc do lobby!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&aSpectating &7{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&cSNEAK to exit");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eExiting Spectator mode");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "{prefix}§cThe party owner has left and the party was disbanded!");
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "{prefix}&cZostales wyrzucony, poniewaz gracz z ranga VIP lub wyzsza dolaczyl na arene.\n&aRozwaz kupno rangi VIP+ aby odblokowac mase nowych funkcji. &7&o(kliknij)");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS, "{prefix}&cBrak wystarczajacej ilosci graczy! Odliczanie zostalo przerwane!");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "{prefix}&eArena na ktorej jestes zostala zrestartowana.");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&cGra");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4Restartowanie");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&2Oczekiwanie §c{full}");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6Rozpoczynanie §c{full}");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8Dostepne areny");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l{name}");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7Status: {status}", "&7Gracze: &f{on}&7/&f{max}", "&7Typ: &a{group}", "", "&aLeft-Click to join.", "&eRight-Click to spectate."));
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN, "{prefix}&eGra zaczyna sie za &6{time} &esekund/y!");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "",
                "&e&l        Chron swoje lozko i niszcz je innym.",
                "&e&l   Ulepsz wyposazenie swojej druzyny poprzez zdobywanie",
                "&e&lZelaza, Zlota, Szmaragdow, i Diamentow z generatorow ktore znajdziesz",
                "&e&l        na wyspach aby odblokowac potezne ulepszenia.", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&aSTART");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "{prefix}&cSorry but you can't join this arena at this moment. Use Right-Click to spectate!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "{prefix}&cSorry but you can't spectate this arena at this moment. Use Left-Click to join!");
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
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_DATE, "dd/MM/yy");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED, "&c&l✘");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_BED_DESTROYED, "&a{remainingPlayers}");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE, "&a&l✓");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER, "mm:ss");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_YOUR_TEAM, "&7 YOU");
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fSledzenie: {team} &f- Dystans: {distance}m");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM, "{TeamColor}[{TeamName}]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT, "&6[SHOUT]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR, "&7[SPECTATOR]");
        yml.addDefault(Messages.MEANING_SHOUT, "okrzyk");
        yml.addDefault(Messages.MEANING_NOBODY, "Nikt");
        yml.addDefault(Messages.MEANING_FULL, "Full");
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
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "{prefix}&cNie mozesz tutaj stawiac blokow!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "{prefix}&c&cMozesz zniszczyc tylko bloki postawione przez graczy!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&cNie mozesz zniszczyc swojego lozka!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&lDESTRUCKJA LOZKA > {TeamColor}{TeamName} Bed &7was deep fried by {PlayerColor}{PlayerName}&7!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&cBED DESTROYED!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&fYou will no longer respawn!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&f&lDESTRUCKJA LOZKA > {TeamColor}{TeamName} Lozko &7zostalo zniszczone przez {PlayerColor}{PlayerName}&7!");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cNie mozesz otworzyc skrzyni tej druzyny poniewaz zostala ona wyeliminowana!");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "{PlayerColor}{PlayerName} &7spadl do pustki.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "{PlayerColor}{PlayerName} &7spadl do pustki. &b&lOSTATECZNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "{PlayerColor}{PlayerName} &7zostal zepchniety do pustki przez {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal zepchniety do pustki przez {KillerColor}{KillerName}&7. &b&lOSTATECZNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez bombe gracza {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez bombe gracza {KillerColor}{KillerName}&7. &b&lOSTATECZNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez {KillerColor}{KillerName}&7. &b&lOSTATECZNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cUMARLES");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eOdrodzisz sie za &c{time} &esekund/y!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "{prefix}&eOdrodzisz sie za &c{time} &esekund/y!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aODRODZILES SIE!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "{prefix}&cZostales wyeliminowany!");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR, "{PlayerColor}{PlayerName} &7umarl.");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL, "{PlayerColor}{PlayerName} &7umarl. &b&lOSTATECZNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_REGULAR, "{PlayerColor}{PlayerName} &7was shoot by {KillerColor}{KillerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_FINAL_KILL, "{PlayerColor}{PlayerName} &7was shoot by {KillerColor}{KillerName}&7! &b&lOSTATECZNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_REGULAR, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerTeamName} &7Pluskwa!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_FINAL_KILL, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerTeamName} &7Pluskwa! &b&lOSTATECZNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_REGULAR, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerTeamName}'s &7Iron Golem!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerTeamName}'s &7Iron Golem! &b&lOSTATECZNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_DIAMOND, "{prefix}&b+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_EMERALD, "{prefix}&a+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_IRON, "{prefix}&f+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_GOLD, "{prefix}&6+{amount} {meaning}");

        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR, "{PlayerColor}{PlayerName} &7zostal zabity przez bombe.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez bombe. &b&lOSTATECZNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "&8{player} Statystyki");
        yml.addDefault(Messages.GAME_END_GAME_OVER_PLAYER_TITLE, "&c&lPRZEGRANA!");
        yml.addDefault(Messages.GAME_END_VICTORY_PLAYER_TITLE, "&6&lWYGRANA!");
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
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fKliknij PPM aby zobaczyc swoje statystyki!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&eAreny");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Collections.singletonList("&fWybierz arene!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&eWroc do lobby");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fPPM aby wyjsc do lobby!"));
        /* Pre Game Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&eStatystyki");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fRight-click to see your stats!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&eWroc do lobby");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fPPM aby wyjsc do lobby!"));
        /* Spectator Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&eTeleporter");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "teleporter"), Collections.singletonList("&fKliknij PPM aby obserwowac graczy!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&eWroc do lobby");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fPPM aby wyjsc do lobby!"));

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList("&f&lBED WARS", "&7{date} &8{server}", "", "&fMapa: &a{map}", "", "&fGracze: &a{on}/{max}", "", "&fOczekiwanie...", "", "§fMode: &a{group}", "&fVersion: &7{version}", "", "&e{server_ip}"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList("&f&lBED WARS", "&7{date} &8{server}", "", "&fMapa: &a{map}", "", "&fGracze: &a{on}/{max}", "", "&fRozpoczecie &a{time}s", "", "§fMode: &a{group}", "&fVersion: &7{version}", "", "&e{server_ip}"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{TeamRedColor}R&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}B&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}G&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}Y &f{TeamYellowName}&f: {TeamYellowStatus}",
                "{TeamAquaColor}A &f{TeamAquaName}&f: {TeamAquaStatus}", "{TeamWhiteColor}W &f{TeamWhiteName}&f: {TeamWhiteStatus}", "{TeamPinkColor}P &f{TeamPinkName}&f: {TeamPinkStatus}",
                "{TeamGrayColor}S &f{TeamGrayName}&f: {TeamGrayStatus}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.Doubles.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{TeamRedColor}R&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}B&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}G&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}Y &f{TeamYellowName}&f: {TeamYellowStatus}",
                "{TeamAquaColor}A &f{TeamAquaName}&f: {TeamAquaStatus}", "{TeamWhiteColor}W &f{TeamWhiteName}&f: {TeamWhiteStatus}", "{TeamPinkColor}P &f{TeamPinkName}&f: {TeamPinkStatus}",
                "{TeamGrayColor}S &f{TeamGrayName}&f: {TeamGrayStatus}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.3v3v3v3.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{TeamRedColor}R&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}B&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}G&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}Y &f{TeamYellowName}&f: {TeamYellowStatus}",
                "", "&fZabojstwa: &a{kills}", "&fOstateczne Zabojstwa: &a{finalKills}", "&fZniszczone Lozka: &a{beds}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.4v4v4v4.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{TeamRedColor}R&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}B&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}G&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}Y &f{TeamYellowName}&f: {TeamYellowStatus}",
                "", "&fZabojstwa: &a{kills}", "&fOstateczne Zabojstwa: &a{finalKills}", "&fZniszczone Lozka: &a{beds}", "", "&e{server_ip}"));

        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&6&lBedWars", "&fYour Level: {level}", "", "&fProgress: &a{currentXp}&7/&b{requiredXp}", "{progress}", "", "&7{player}", "", "&fCoins: &a{money}"
                , "", "&fTotal Wins: &a{wins}", "&fTotal Kills: &a{kills}", "", "&e{server_ip}"));

        yml.addDefault(Messages.XP_REWARD_PER_MINUTE, "{prefix}&6+{xp} BedWars Experience Received (Play Time).");
        yml.addDefault(Messages.XP_REWARD_WIN, "{prefix}&6+{xp} BedWars Experience Received (Game Win).");
        yml.addDefault(Messages.XP_REWARD_PER_TEAMMATE, "{prefix}&6+{xp} BedWars Experience Received (Team Support).");

        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&lWYELIMINOWANA DRUZYNA > {TeamColor}{TeamName} &cdruzyna &czostala wyeliminowana!\n");
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&lChron swojego lozka!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lTwoje lozko zostalo zniszczone!");

        yml.addDefault(Messages.NPC_NAME_TEAM_UPGRADES, "&bDRUZYNOWE ULEPSZENIA,&e&lRIGHT CLICK");
        yml.addDefault(Messages.NPC_NAME_SOLO_UPGRADES, "&bSOLO ULEPSZENIA,&e&lRIGHT CLICK");
        yml.addDefault(Messages.NPC_NAME_TEAM_SHOP, "&bSKLEP Z PRZEDMIOTAMI,&e&lRIGHT CLICK");
        yml.addDefault(Messages.NPC_NAME_SOLO_SHOP, "&bSKLEP Z PRZEDMIOTAMI,&e&lRIGHT CLICK");

        //SHOP
        yml.addDefault(Messages.SHOP_INDEX_NAME, "&8Quick Buy");
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "{prefix}&cNie masz wymaganej ilosci {currency}! Potrzebujesz {amount} wiecej!");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "{prefix}&aKupiles &6{item}");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "{prefix}&cJuz to kupiles!");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "{TeamColor}&l{TeamName} &r{TeamColor}Pluskwa");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "{TeamColor}{despawn}s &8[ {TeamColor}{health}&8]");
        yml.addDefault(Messages.SHOP_SEPARATOR_NAME, "&8⇧ Categories");
        yml.addDefault(Messages.SHOP_SEPARATOR_LORE, Collections.singletonList("&8⇩ Items"));
        yml.addDefault(Messages.SHOP_QUICK_BUY_NAME, "&bQuick Buy");
        yml.addDefault(Messages.SHOP_QUICK_BUY_LORE, new ArrayList<>());
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_NAME, "&cEmpty slot!");
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_LORE, Arrays.asList("&7This is a Quick Buy Slot!", "&bSneak Click &7any item in", "&7the shop to add it here."));
        yml.addDefault(Messages.SHOP_CAN_BUY_COLOR, "&a");
        yml.addDefault(Messages.SHOP_CANT_BUY_COLOR, "&c");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CAN_BUY, "&eClick to purchase!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CANT_AFFORD, "&cYou don't have enough {currency}!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_MAXED, "&aMAXED!");
        yml.addDefault(Messages.SHOP_LORE_QUICK_ADD, "&bSneak Click to add to Quick Buy");
        yml.addDefault(Messages.SHOP_LORE_QUICK_REMOVE, "&bSneak Click to remove from Quick Buy!");


        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "&8Bloki", "&aBloki", Collections.singletonList("&eKliknij aby przegladac!"));
        addContentMessages(yml, "wool", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Welna", Arrays.asList("&7Koszt: &f{cost} {currency}", "", "&7Great for bridging across", "&7islands. Turns into your team's", "&7color.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "clay", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Utwardzona Glina", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Basic block to defend your bed.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "glass", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Wybucho-Odporne Szklo", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Immune to explosions.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "stone", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Kamien Kresu", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Solid block to defend your bed.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "ladder", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Drabina", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Useful to save cats stuck in", "&7trees.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "obsidian", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Obsydian", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Extreme protection for your bed.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "wood", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Debowe Deski", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Solid block to defend your bed", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_MELEE, "&8Miecze", "&aMiecze", Collections.singletonList("&eKliknij aby przegladac!"));
        addContentMessages(yml, "stone-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Kamienny Miecz", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "iron-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Zelazny Miecz", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "diamond-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Diamentowy Miecz", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "stick", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Patyk (Odrzut I)", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "&8Zbroja", "&aZbroja", Collections.singletonList("&eKliknij aby przegladac!"));
        addContentMessages(yml, "chainmail", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Permanentna Klczuga", Arrays.asList("&7Koszt: {cost} {currency}",
                "", "&7Chainmail leggings and boots", "&7which you will always spawn", "&7with.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "iron-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Permanentna Zelazna Zbroja", Arrays.asList("&7Koszt: {cost} {currency}",
                "", "&7Iron leggings and boots which", "&7you will always spawn with.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "diamond-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Permanentna Diamentowa <broja", Arrays.asList("&7Koszt: {cost} {currency}",
                "", "&7Diamond leggings and boots which", "&7you will always crush with.", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "&8Narzedzia", "&aNarzedzia", Collections.singletonList("&eKliknij aby przegladac!"));
        addContentMessages(yml, "shears", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Permanent Nozyczki", Arrays.asList("&7Koszt: {cost} {currency}",
                "", "&7Great to get rid of wool. You", "&7will always spawn with these shears.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "pickaxe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Kilof {tier}", Arrays.asList("&7Koszt: {cost} {currency}", "&7Tier: &e{tier}",
                "", "&7This is an upgradable item.", "&7It will lose 1 tier upon.", "&7death!", "", "&7You will permanently", "&7respawn with at least the", "&7lowest tier.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "axe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Siekiera {tier}", Arrays.asList("&7Koszt: {cost} {currency}", "&7Tier: &e{tier}",
                "", "&7This is an upgradable item.", "&7It will lose 1 tier upon.", "&7death!", "", "&7You will permanently", "&7respawn with at least the", "&7lowest tier.", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_RANGED, "&8Luki", "&aLuki", Collections.singletonList("&eKliknij aby przegladac!"));
        addContentMessages(yml, "arrow", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Strzaly", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow1", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Luk", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow2", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Luk (Moc I)", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow3", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Luk (Moc I, Uderzenie I)", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "&8Mikstury", "&aMikstury", Collections.singletonList("&eKliknij aby przegladac!"));
        addContentMessages(yml, "speed-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Szybkosc II (45 seconds)", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "jump-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Zwiekszony skok V (45 seconds)", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "invisibility", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Niewidzialnosc (30 seconds)", Arrays.asList("&7Koszt: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));


        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "&8Uzyteczne", "&aUzyteczne", Collections.singletonList("&eKliknij aby przegladac!"));
        addContentMessages(yml, "golden-apple", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Zlote Jablko", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Well-rounded healing.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bedbug", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}BedBug", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Spawns silverfish where the",
                "&7snowball lands to distract your", "&7enemies. Lasts 15 seconds.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "dream-defender", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Dream Defender", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Iron Golem to help defend your",
                "&7base. Lasts 4 minutes.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "fireball", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Ognista Kula", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Right-click to launch! Great to",
                "&7knock back enemies walking on", "&7thin bridges", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "tnt", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}TNT", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Instantly ignites, appropriate",
                "&7to explode things!", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "ender-pearl", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Perla Kresu", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7The quickest way to invade enemy",
                "&7bases.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "water-bucket", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Water Bucket", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Great to slow down approaching",
                "&7enemies. Can also protect", "&7against TNT.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bridge-egg", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Bridge Egg", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7This egg creates a bridge in its",
                "&7trial after being thrown.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "magic-milk", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Magic Milk", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Avoid triggering traps for 60",
                "&7seconds after consuming.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "sponge", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Sponge", Arrays.asList("&7Koszt: {cost} {currency}", "", "&7Great for soaking up water.",
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
        yml.addDefault(Messages.TRAP_ENEMY_BASE_ENTER_ACTION, "&cPULAPKA ZOSTALA URUCHOMIONA!");
        yml.addDefault(Messages.TRAP_ENEMY_BASE_ENTER_CHAT, "{prefix}&cPULAPKA ZOSTALA URUCHOMIONA!");
        yml.addDefault(Messages.TRAP_ENEMY_BASE_ENTER_SUBTITLE, "&fTwoja pulapka zostala wylaczona!");
        yml.addDefault(Messages.TRAP_ENEMY_BASE_ENTER_TITLE, "&cPULAPKA ZOSTALA URUCHOMIONA!");
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

        yml.addDefault(Messages.REJOIN_NO_ARENA, "{prefix}&cThere is no arena to rejoin!");
        yml.addDefault(Messages.REJOIN_DENIED, "{prefix}&cYou can't rejoin the arena anymore. Game ended or bed destroyed.");
        yml.addDefault(Messages.REJOIN_ALLOWED, "{prefix}&eJoining arena &a{arena}&e!");

        save();
        setPrefix(m(Messages.PREFIX));
    }
}
