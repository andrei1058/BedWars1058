package com.andrei1058.bedwars.configuration.language;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.Messages;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;
import java.util.Collections;

import static com.andrei1058.bedwars.Main.mainCmd;

public class Polish {

    public Polish(Language lbj, YamlConfiguration yml){
        yml.options().header("Polish translation by RarstManPL#0616");
        yml.addDefault(Messages.PREFIX, "");
        yml.addDefault("name", "Polski");
        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + mainCmd + " stats", "&2▪ &7/" + mainCmd + " join &o<arena/group>", "&2▪ &7/" + mainCmd + " leave", "&2▪ &7/" + mainCmd + " lang", "&2▪ &7/" + mainCmd + " gui", "&2▪ &7/" + mainCmd + " start &3(vip)"));
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "{prefix}&cZostales wyrzucony, poniewaz gracz z ranga VIP lub wyzsza dolaczyl na arene.\n&aRozwaz kupno rangi VIP+ aby odblokowac mase nowych funkcji. &7&o(kliknij)");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL, "{prefix}&cTa arena jast pelna!\n&aRozwaz kupno rangi VIP+ aby odblokowac mase nowych funkcji. &7&o(kliknij)");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS, "{prefix}&cTa arena jest pelna.\n&cJako VIP mozesz wchodzic na pelne areny, lecz na tej arenie znajduje sie juz 1. lub wiecej vipow.");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS, "{prefix}&cBrak wystarczajacej ilosci graczy! Odliczanie zostalo przerwane!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "{prefix}&7{player} &eopuscil gre!");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "{prefix}&eArena na ktorej jestes zostala zrestartowana.");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&cGra");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4Restartowanie");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&2Oczekiwanie §c{full}");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6Rozpoczynanie §c{full}");
        yml.addDefault(Messages.COMMAND_JOIN_GROUP_OR_ARENA_NOT_FOUND, "{prefix}&cNie istnieje zadna arena o nazwie: {name}");
        yml.addDefault(Messages.COMMAND_JOIN_NO_EMPTY_FOUND, "{prefix}&cObecnie nie ma zadnej dostepnej areny ;(");
        yml.addDefault(Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA, "{prefix}&cNie jestes na arenie!");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8Dostepne areny");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l{name}");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7Status: {status}", "&7Gracze: &f{on}&7/&f{max}", "&7Typ: &a{group}", "", "&aLeft-Click to join.", "&eRight-Click to spectate."));
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "{prefix} &2Dostepne jezyki:");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7{iso} - &f{name}");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "{prefix}&7Uzyj: /lang &f&o<iso>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "{prefix}&cTen jezyk nie jest dostepny na serwerze!");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "{prefix}&aJezyk zostal zmieniony!");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "{prefix}&cNie mozesz zmienic jezyka podczas gry.");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_PARTY_TOO_BIG, "{prefix}&cTwoje party jest zbyt duze abo dolaczyc do tej areny w pelnym skladzie :(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_NOT_PARTY_LEADER, "{prefix}&cTylko lider party moze wybrac arene.");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&eTier &c{tier}");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&lDiamentow");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&lSzmaragdow");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&ePojawi sie za &c{seconds} &esekund/y");
        yml.addDefault(Messages.COMMAND_JOIN_PLAYER_JOIN_MSG, "{prefix}&7{player} &edolaczyl do gry (&b{on}&e/&b{max}&e)!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "{prefix}&7{player} &eopuscil gre!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN, "{prefix}&eGra zaczyna sie za &6{time} &esekund/y!");
        yml.addDefault(Messages.FORMATTING_CHAT_LOBBY, "{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_WAITING, "{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SHOUT, "{vPrefix}&6[SHOUT] {team} &7{player}&f{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_TEAM, "{vPrefix}&f{level}{team}&7 {player}{vSuffix} {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SPECTATOR, "{vPrefix}&7[SPECTATOR] {player}{vSuffix}: {message}");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "",
                "&e&l        Chron swoje lozko i niszcz je innym.",
                "&e&l   Ulepsz wyposazenie swojej druzyny poprzez zdobywanie",
                "&e&lZelaza, Zlota, Szmaragdow, i Diamentow z generatorow ktore znajdziesz",
                "&e&l        na wyspach aby odblokowac potezne ulepszenia.", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.MEANING_SHOUT, "okrzyk");
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "{prefix}&cNie mozesz tutaj stawiac blokow!");
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList("&f&lBED WARS", "", "&fMapa: &a{map}", "&fGracze: &a{on}/{max}", "", "&ffRozpoczecie: &a{time}s", "", "§fSerwer: &a{server}", "", "&e  andrei1058.com"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList("&f&lBED WARS", "", "&fMapa: &a{map}", "&fGracze: &a{on}/{max}", "", "&fOczekiwanie...", "", "§fSerwer: &a{server}", "", "&e  andrei1058.com"));
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "{prefix}&c&cMozesz zniszczyc tylko bloki postawione przez graczy!");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&aSTART");
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent}", "&a{time}", "", "{TeamRedColor}&lR&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}&lB&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}&lG&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}&lY &f{TeamYellowName}&f: {TeamYellowStatus}", "",
                "&fZabojstwa: &a{kills}", "&fOstateczne zabojstwa:: &a{finalKills}", "&fZniszczone lozka: &a{beds}", "", "&ehttp://andrei1058.com"));
        yml.addDefault("scoreboard.SoloPlaying", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent}", "&a{time}", "", "{TeamRedColor}&lR&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}&lB&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}&lG&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}&lY &f{TeamYellowName}&f: {TeamYellowStatus}",
                "{TeamAquaColor}&lA&f {TeamAquaName}&f: {TeamAquaStatus}", "{TeamWhiteColor}&lW&f {TeamWhiteName}&f: {TeamWhiteStatus}", "{TeamPinkColor}&lP&f {TeamPinkName}&f: {TeamPinkStatus}", "{TeamGrayColor}&lG&f {TeamGrayName}&f: {TeamGrayStatus}", "", "&ehttp://andrei1058.com"));
        yml.addDefault("scoreboard.DoublesPlaying", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent}", "&a{time}", "", "{TeamRedColor}&lR&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}&lB&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}&lG&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}&lY &f{TeamYellowName}&f: {TeamYellowStatus}",
                "{TeamAquaColor}&lA&f {TeamAquaName}&f: {TeamAquaStatus}", "{TeamWhiteColor}&lW&f {TeamWhiteName}&f: {TeamWhiteStatus}", "{TeamPinkColor}&lP&f {TeamPinkName}&f: {TeamPinkStatus}", "{TeamGrayColor}&lG&f {TeamGrayName}&f: {TeamGrayStatus}", "", "&ehttp://andrei1058.com"));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_DATE, "dd/MM/yy");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED, "&c&l✘");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_BED_DESTROYED, "&a{remainingPlayers}");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE, "&a&l✓");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER, "mm:ss");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_YOUR_TEAM, "&7 (You)");
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fSledzenie: {team} &f- Dystans: {distance}m");
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&lChron swojego lozka!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&cNie mozesz zniszczyc swojego lozka!");
        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&lWYELIMINOWANA DRUZYNA > {TeamColor}{TeamName} &cdruzyna &czostala wyeliminowana!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&lDESTRUCKJA LOZKA > {TeamColor}{TeamName} Bed &7was deep fried by {PlayerColor}{PlayerName}&7!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&cBED DESTROYED!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&fYou will no longer respawn!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&f&lDESTRUCKJA LOZKA > {TeamColor}{TeamName} Lozko &7zostalo zniszczone przez {PlayerColor}{PlayerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "{PlayerColor}{PlayerName} &7spadl do pustki.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "{PlayerColor}{PlayerName} &7spadl do pustki. &b&lOSTATECZNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "{PlayerColor}{PlayerName} &7zostal zepchniety do pustki przez {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal zepchniety do pustki przez {KillerColor}{KillerName}&7. &b&lOSTATECZNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez bombe gracza {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez bombe gracza {KillerColor}{KillerName}&7. &b&lOSTATECZNE ZABOJSTWO!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "{PlayerColor}{PlayerName} &7zostal zabity przez {KillerColor}{KillerName}&7. &b&lOSTATECZNE ZABOJSTWO!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lTwoje lozko zostalo zniszczone!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cUMARLES");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eOdrodzisz sie za &c{time} &esekund/y!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "{prefix}&eOdrodzisz sie za &c{time} &esekund/y!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aODRODZILES SIE!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "{prefix}&cZostales wyeliminowany!");
        yml.addDefault(Messages.GAME_END_GAME_OVER_PLAYER_TITLE, "&c&lPRZEGRANA!");
        yml.addDefault(Messages.GAME_END_VICTORY_PLAYER_TITLE, "&6&lWYGRANA!");
        yml.addDefault(Messages.GAME_END_TOP_PLAYER_CHAT, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "", "{winnerFormat}", "", "",
                "&e                       &lTOP 1 Zabojca &7- {firstName} - {firstKills}",
                "&6                       &lTOP 2 Zabojca &7- {secondName} - {secondKills}",
                "&c                       &lTOP 3 Zabojca &7- {thirdName} - {thirdKills}", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")); //todo disponibile {firstDeaths} {firstBeds} {firstName} {firstKills}
        //yml.addDefault(gameOverReward, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
        //        "&f                                   &lReward Summary", "", "",
        //        "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.GAME_END_TEAM_WON_CHAT, "{prefix}{TeamColor}{TeamName} &awygral gre!");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 {TeamColor}{TeamName} &7- {members}");
        //yml.addDefault(tablistFormat, "{TeamColor}&l{TeamLetter}&r {TeamColor}{PlayerName} &e{PlayerHealth}");//{TeamColor}{TeamName}{TeamHealth}{PlayerName}{PlayerHealth}
        yml.addDefault(Messages.MEANING_NOBODY, "Nikt");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT, "{prefix}&eGenerator {generatorType} &ezostal ulepszony do stopnia &c{tier}");
        yml.addDefault(Messages.NPC_NAME_TEAM_UPGRADES, "&e&lDRUZYNOWE &E&LULEPSZENIA");
        yml.addDefault(Messages.NPC_NAME_SOLO_UPGRADES, "&e&lSOLO &E&LULEPSZENIA");
        yml.addDefault(Messages.NPC_NAME_TEAM_SHOP, "&e&lSKLEP Z PRZEDMIOTAMI");
        yml.addDefault(Messages.NPC_NAME_SOLO_SHOP, "&e&lSKLEP Z PRZEDMIOTAMI");
        yml.addDefault(Messages.SHOP_PATH + ".name", "&7Sklep z przedmiotami");

        lbj.saveShopStuff("invContents.armor", "&aZbroja", Arrays.asList("&8Dostepne:", "&7▪ Buty z kolczugi", "&7▪ Spodnie z kolczugi", "&7▪ Zelazne buty", "&7▪ Zelazne spodnie", "&7▪ Diamentowe buty", "&7▪ Diamentowe spodnie", "", "&eKliknij aby przegladac!"));
        lbj.saveShopStuff("invContents.melee", "&aMiecze", Arrays.asList("&8Dostepne:", "&7▪ Kamienny miecz", "&7▪ Zelazny miecz", "&7▪ Diamentowy miecz", "&7▪ Patyk (Odrzut I)", "", "&eKliknij aby przegladac!"));
        lbj.saveShopStuff("invContents.blocks", "&aBloki", Arrays.asList("&8Dostepne:", "&7▪ Welna", "&7▪ Utwardzona glina", "&7▪ Wybucho-odporne szklo", "&7▪ Kamien Kresu", "&7▪ Drabina", "&7▪ Debowe deski", "&7▪ Obsydian", "", "&eKliknij aby przegladac!"));
        lbj.saveShopStuff("invContents.ranged", "&aLuki", Arrays.asList("&8Dostepne:", "&7▪ Strzaly", "&7▪ Luk", "&7▪ Luk (Moc I)", "&7▪ Luk (Moc I, Uderzenie I)", "", "&eKliknij aby przegladac!"));
        lbj.saveShopStuff("invContents.tools", "&aNarzedzia", Arrays.asList("&8Dostepne:", "&7▪ Nozyczki", "&7▪ Drewniany kilof (Wydajnosc I)", "&7▪ Drewniania siekiera (Wydajnosc I)", "", "&eKliknij aby przegladac!"));
        lbj.saveShopStuff("invContents.potions", "&aMikstury", Arrays.asList("&8Dostepne:", "&7▪ Nozyce", "&7▪ Zwiekszony skok V", "&7▪ Invisibility Potion", "", "&eKliknij aby przegladac!"));
        lbj.saveShopStuff("invContents.utility", "&aUzyteczne", Arrays.asList("&8Dostepne:", "&7▪ Zlote jablko", "&7▪ Pluskwa", "&7▪ Golem", "&7▪ Ognista kula", "&7▪ TNT", "&7▪ Perla Kresu", "&7▪ Wiadro wody", "&7▪ Jajko-most", "", "&eKliknij aby przegladac!"));
        //lbj.saveShopStuff("invContents.favourites", "&aFavourites", Arrays.asList("&7Click to view your", "&7favourite items. You can", "&7set a favourite item by", "&7shift clicking on any shop item!"));
        //lbj.saveShopStuff("invContents.recommended", "&aRecommended Items", Arrays.asList("&7Original", "&7Click to view some", "&7recommended items for early", "&7to late game!", "", "&eKliknij aby przegladac!"));

        lbj.saveShopStuff("invContents.armor.invContents.chainmail", "&aPermanentna kolczuga", Arrays.asList("&8Przedmioty:", "&8▪ &7Buty z kolczugi", "&8▪ &7Spodnie z kolczugi", "", "&7Koszt: &f{cost} {currency}", "&8&oNie stracisz jej po smierci!"));
        lbj.saveShopStuff("invContents.armor.invContents.iron", "&aPermanentna zelazna zbroja", Arrays.asList("&8Przedmioty:", "&8▪ &7Zelazne buty", "&8▪ &7Zelazne spodnie", "", "&7Koszt: &6{cost} {currency}", "&8&oNie stracisz jej po smierci!"));
        lbj.saveShopStuff("invContents.armor.invContents.diamond", "&aPermanentna diamentowa zbroja", Arrays.asList("&8Przedmioty:", "&8▪ &7Diamentowe buty", "&8▪ &7Diamentowe spodnie", "", "&7Koszt: &2{cost} {currency}", "&8&oNie stracisz jej po smierci!"));
        lbj.saveShopStuff("invContents.armor.invContents.back", "&aPowrot", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.melee.invContents.stone", "&aKamienny miecz", Arrays.asList("&8Przedmioty:", "&8▪ &7Kamienny miecz", "", "&7Koszt: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.iron", "&aZelazny miecz", Arrays.asList("&8Przedmioty:", "&8▪ &7Zelazny miecz", "&7Koszt: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.diamond", "&aDiamentowy miecz", Arrays.asList("&8Przedmioty:", "&8▪ &7Diamentowy miecz", "", "&7Koszt: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.stick", "&aPatyk (Odrzut I)", Arrays.asList("&8Przedmioty:", "&8▪ &7Patyk (Odrzut I)", "", "&7Koszt: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.back", "&aPowrot", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.blocks.invContents.wool", "&aWelna", Arrays.asList("&8Przedmioty:", "&8▪ &7Welna", "", "&7Koszt: &f{cost} {currency}", "", "&eKliknij aby kupic!"));
        lbj.saveShopStuff("invContents.blocks.invContents.clay", "&aUtwardzona glina", Arrays.asList("&8Przedmioty:", "&8▪ &7Utwardzona glina", "", "&7Koszt: &f{cost} {currency}", "", "&eKliknij aby kupic!"));
        lbj.saveShopStuff("invContents.blocks.invContents.glass", "&aWybucho-odporne szklo", Arrays.asList("&8Przedmioty:", "&8▪ &7Wybucho-odporne szklo", "", "&7Koszt: &f{cost} {currency}", "", "&7Niewrazliwe na wybuchy", "", "&eKliknij aby kupic!"));
        lbj.saveShopStuff("invContents.blocks.invContents.stone", "&aKamien Kresu", Arrays.asList("&8Przedmioty:", "&8▪ &7Kamien Kresu", "", "&7Koszt: &f{cost} {currency}", "", "&eKliknij aby kupic!"));
        lbj.saveShopStuff("invContents.blocks.invContents.ladder", "&aDrabina", Arrays.asList("&8Przedmioty:", "&8▪ &7Drabina", "", "&7Koszt: &f{cost} {currency}", "", "&eKliknij aby kupic!"));
        lbj.saveShopStuff("invContents.blocks.invContents.wood", "&aDebowe deski", Arrays.asList("&8Przedmioty:", "&8▪ &7Debowe deski", "", "&7Koszt: &6{cost} {currency}", "", "&eKliknij aby kupic!"));
        lbj.saveShopStuff("invContents.blocks.invContents.obsidian", "&cObsydian", Arrays.asList("&8Przedmioty:", "&8▪ &7Obsydian", "", "&7Koszt: &2{cost} {currency}", "", "&eKliknij aby kupic!"));
        lbj.saveShopStuff("invContents.blocks.invContents.back", "&aPowrot", Arrays.asList(""));

        lbj.saveShopStuff("invContents.ranged.invContents.arrow", "&aStrzaly", Arrays.asList("&8Przedmioty:", "&8▪ &7Strzaly", "", "&7Koszt: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow", "&aLuk", Arrays.asList("&8Przedmioty:", "&8▪ &7Luk", "", "&7Koszt: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow2", "&aLuk (Moc I)", Arrays.asList("&8Przedmioty:", "&8▪ &7Luk (Moc I)", "", "&7Koszt: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow3", "&aLuk (Moc I, Uderzenie I)", Arrays.asList("&8Przedmioty:", "&8▪ &7Luk (Moc I, Uderzenie I)", "", "&7Koszt: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.back", "&aPowrot", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.tools.invContents.shears", "&aPermanent Nozyczki", Arrays.asList("&8Przedmioty:", "&8▪ &7Permanent Nozyczki", "", "&7Koszt: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.pick", "&aDrewniany kilof", Arrays.asList("&8Przedmioty:", "&8▪ &7Drewniany kilof", "", "&7Koszt: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.axe", "&aDrewniana siekiera", Arrays.asList("&8Przedmioty:", "&8▪ &7Drewniana siekiera", "", "&7Koszt: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.back", "&aPowrot", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.potions.invContents.potion1", "&aSzybkosc II", Arrays.asList("&8Przedmioty:", "&8▪ &7Szybkosc II", "", "&7Koszt: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.potion2", "&aZwiekszony skok V", Arrays.asList("&8Przedmioty:", "&8▪ &7Zwiekszony skok V", "", "&7Koszt: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.potion3", "&aNiewidzialnosc", Arrays.asList("&8Przedmioty:", "&8▪ &7Niewidzialnosc", "", "&7Koszt: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.back", "&aPowrot", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.utility.invContents.apple", "&aZlote jablko", Arrays.asList("&8Przedmioty:", "&8▪ &7Zlote jablko", "", "&7Koszt: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.utility.invContents.sball", "&aBedbug", Arrays.asList("&8Przedmioty:", "&8▪ &7Bedbug", "", "&7Koszt: &f{cost} {currency}", "", "&7Moderately annoying. These", "&7little critters can be thrown to", "&7distract enemies"));
        lbj.saveShopStuff("invContents.utility.invContents.monsteregg", "&aGolem", Arrays.asList("&8Przedmioty:", "&8▪ &7Golem", "", "&7Koszt: &f{cost} {currency}", "", "&7Moderately motivated.", "&7Sometimes they help defend your", "&7base"));
        lbj.saveShopStuff("invContents.utility.invContents.fireball", "&aOgnista kula", Arrays.asList("&8Przedmioty:", "&8▪ &7Ognista kula", "", "&7Koszt: &f{cost} {currency}", "", "&7Right-click to launch!"));
        lbj.saveShopStuff("invContents.utility.invContents.tnt", "&aTNT", Arrays.asList("&8Przedmioty:", "&8▪ &7TNT", "", "&7Koszt: &6{cost} {currency}", "", "&7Przy kontakcie podpala wszystko", "&7wokol, uzywaj aby wysadzac bloki!"));
        lbj.saveShopStuff("invContents.utility.invContents.enderpearl", "&aPerla Kresu", Arrays.asList("&8Przedmioty:", "", "&8Perla Kresu", "&8▪ &7Koszt: &2{cost} {currency}", "", "&7Pretty useful for invading", "&7enemies bases."));
        lbj.saveShopStuff("invContents.utility.invContents.bucket", "&aWiadro wody", Arrays.asList("&8Przedmioty:", "&8▪ &7Wiadro wody", "", "&7Koszt: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.utility.invContents.eggBridge", "&aJajko-most", Arrays.asList("&8Przedmioty:", "&8▪ &7Jajko-most", "", "&7Koszt: &2{cost} {currency}", "&7Bardzo uzyteczne jezeli chcesz", "&7szybko sie gdzies dostac."));
        lbj.saveShopStuff("invContents.utility.invContents.back", "&aPowrot", Collections.singletonList(""));
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
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "{prefix}&cNie masz wymaganej ilosci {currency}! Potrzebujesz {amount} wiecej!");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "{prefix}&aKupiles &6{item}");
        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Uzyj: /" + mainCmd + " join §o<arena/group>");
        yml.addDefault(Messages.COMMAND_NOT_ALLOWED_IN_GAME, "{prefix}&cNie mozesz tego zrobic.");
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
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "{TeamColor}&l{TeamName} &r{TeamColor}Pluskwa");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "{TeamColor}{despawn}s &8[ {TeamColor}{health}&8]");
        yml.addDefault(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH, "▮ ");
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
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "{player} Statystyki");

        /* save default items messages for stats gui */
        lbj.addDefaultStatsMsg(yml, "wins", "&6Wygrane", "&f{wins}");
        lbj.addDefaultStatsMsg(yml, "losses", "&6Przegrane", "&f{losses}");
        lbj.addDefaultStatsMsg(yml, "kills", "&6Wygrane", "&f{kills}");
        lbj.addDefaultStatsMsg(yml, "deaths", "&6Smierci", "&f{deaths}");
        lbj.addDefaultStatsMsg(yml, "final-kills", "&6Ostateczne Zabojstwa", "&f{finalKills}");
        lbj.addDefaultStatsMsg(yml, "final-deaths", "&6Ostateczne Smierci", "&f{finalDeaths}");
        lbj.addDefaultStatsMsg(yml, "beds-destroyed", "&6Zniszczone Lozka", "&f{bedsDestroyed}");
        lbj.addDefaultStatsMsg(yml, "first-play", "&66Ostatnia gra", "&f{firstPlay}");
        lbj.addDefaultStatsMsg(yml, "last-play", "&6Ostatnia gra", "&f{lastPlay}");
        lbj.addDefaultStatsMsg(yml, "games-played", "&6Rozegrane gry", "&f{gamesPlayed}");
        yml.addDefault(Messages.FORMATTING_STATS_DATE_FORMAT, "yyyy/MM/dd HH:mm");

        yml.addDefault(Messages.MEANING_NEVER, "Never");
        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&6&lBedWars", "&7{date}", "&fNick: &a{player}", "", "&fWygrane: &a{wins}", "&fPrzegrane: &a{losses}", "&fZabojstwa: &a{kills}", "&fSmierci: &a{deaths}"
                , "&fOstateczne zabojstwa: &a{fKills}", "&fZniszczone lozka: &a{beds}", "", "&fOnline: &a{on}", "&eandrei1058.com"));

        /* party commands */
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
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "{prefix}&cKomenda nie istnieje lub nie masz do niej uprawnien!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "§c▪ §7Nie jestes w zadnej grze!");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "§c▪ §7Odliczanie zostalo skrocone!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "{prefix}&Nie mozesz glosowac na natychmiastowy start areny.\n§7Rozwaz kupno rangi VIP+ aby odblokowac mase nowych funkcji!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_MSG, "{prefix}§6Obserwujesz teraz arene &9{arena}&6.\n{prefix}§eMozesz opuscic gre wpisujac &c/leave&e.");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cNie mozesz otworzyc skrzyni tej druzyny poniewaz zostala ona wyeliminowana!");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8Teleporter");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "{prefix}{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7Zycie: &f{health}%", "&7Jedzenie: &f{food}", "", "&7Left-click to spectate"));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lPowroc do lobby");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Kliknij PPM aby wyjsc do lobby!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&aSpectating &7{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&cSNEAK to exit");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eExiting Spectator mode");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "{prefix}&cJuz to kupiles!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &ejest offline!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG, "&cSpectators are not allowed in this arena!");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "{prefix}§cThe party owner has left and the party was disbanded!");

        /* Lobby Command Items */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".stats") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "stats"), "&eStatystyki");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fKliknij PPM aby zobaczyc swoje statystyki!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".arena-selector") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&eAreny");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Collections.singletonList("&fWybierz arene!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&eWroc do lobby");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fPPM aby wyjsc do lobby!"));
        }
        /* Pre Game Command Items */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".stats") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&eStatystyki");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fRight-click to see your stats!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&eWroc do lobby");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fPPM aby wyjsc do lobby!"));
        }
        /* Spectator Command Items */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH + ".teleporter") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&eTeleporter");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "teleporter"), Collections.singletonList("&fKliknij PPM aby obserwowac graczy!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH + ".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&eWroc do lobby");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fPPM aby wyjsc do lobby!"));
        }
        Language.addDefaultMessagesCommandItems(lbj);

        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cYou can't do that yet! Wait {seconds} more seconds!");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM, "{TeamColor}[{TeamName}]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT, "&6[SHOUT]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR, "&7[SPECTATOR]");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "{prefix}&cSorry but you can't join this arena at this moment. Use Right-Click to spectate!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "{prefix}&cSorry but you can't spectate this arena at this moment. Use Left-Click to join!");

        yml.addDefault(Messages.REJOIN_NO_ARENA, "{prefix}&cThere is no arena to rejoin!");
        yml.addDefault(Messages.REJOIN_DENIED, "{prefix}&cYou can't rejoin the arena anymore. Game ended or bed destroyed.");
        yml.addDefault(Messages.REJOIN_ALLOWED, "{prefix}&eJoining arena &a{arena}&e!");
    }
}
