package com.andrei1058.bedwars.configuration.language;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.Messages;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;
import java.util.Collections;

import static com.andrei1058.bedwars.Main.mainCmd;

public class Romanian {

    public Romanian(Language lbj, YamlConfiguration yml){
        yml.addDefault(Messages.PREFIX, "");
        yml.addDefault("name", "Romana");
        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + mainCmd + " stats", "&2▪ &7/" + mainCmd + " join &o<arena/group>", "&2▪ &7/" + mainCmd + " leave", "&2▪ &7/" + mainCmd + " lang", "&2▪ &7/" + mainCmd + " gui", "&2▪ &7/" + mainCmd + " start &3(vip)"));
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "{prefix}&cNe pare rau, dar locul tau a fost rezervat unui donator.\n&aAi putea lua in considerare donarea pentru mai multe facilitati. &7&o(click)");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_IS_FULL, "{prefix}&cArena este plina!\n&aAi putea lua in considerare donarea pentru mai multe facilitati. &7&o(click)");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_IS_FULL_VIP_REQUIRED, "{prefix}&cNe pare rau dar arena este plina.\n&cStim ca esti donator dar arena este deja plina cu persoane care au prioritate.");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS, "{prefix}§cNu sunt destui jucatori! Numaratoarea a fost oprita!");
        yml.addDefault(Messages.ARENA_LEAVE_PLAYER_LEAVE_MSG, "{prefix}&e{player} a iesit din joc!");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "{prefix}&eArena in care te aflai se reporneste.");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&cIn joc");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4Se reporneste");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&2In Asteptare §c{full}");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6Porneste §c{full}");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_GROUP_OR_ARENA_NOT_FOUND, "{prefix}&cNu exista arene sau grupuri cu numele: {name}");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NO_EMPTY_FOUND, "{prefix}&cNu sunt arene disponibile momentan ;(");
        yml.addDefault(Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA, "{prefix}&cNu esti intr-o arena!");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8Click pentru a intra");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l{name}");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7Status: {status}", "&7Jucatori: &f{on}&7/&f{max}", "&7Tip: &a{group}", "", "&eClick-Stanga pentru a intra.", "&eClick-Dreapta pentru a vizona."));
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "{prefix} &2Limbi disponibile:");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7{iso} - &f{name}");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "{prefix}&7Foloseste: /lang &f&o<iso>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "{prefix}&cAceasta limba nu exista!");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "{prefix}&aLimba a fost schimbata!");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "{prefix}&cNu poti schimba limba in timpul meciului.");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_PARTY_TOO_BIG, "{prefix}&cParty-ul este prea mare pentru a intra ca un singur team :(");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NOT_PARTY_LEADER, "{prefix}&cDoar leader-ul poate alege arena.");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&eTier &c{tier}");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&lDiamant");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&lEmerald");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&eSe spawneaza in &c{seconds} &esecunde");
        yml.addDefault(Messages.ARENA_JOIN_PLAYER_JOIN_MSG, "{prefix}&7{player} &ea intrat (&b{on}&e/&b{max}&e)!");
        yml.addDefault(Messages.ARENA_LEAVE_PLAYER_LEAVE_MSG, "{prefix}&7{player} &ea iesit!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN, "{prefix}&eJocul incepe in &6{time} &eseuncde!");
        yml.addDefault(Messages.FORMATTING_CHAT_LOBBY, "{vPretfix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_WAITING, "{vPretfix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SHOUT, "{vPretfix}&6[SHOUT] {team} &7{player}&f{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_TEAM, "{vPretfix}&f{level}{team}&7 {player}{vSuffix} {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SPECTATOR, "{vPretfix}&7[SPECTATOR] {player}{vSuffix}: {message}");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "",
                "&e&l    Protejeaza-ti patul si distruge paturile inamicilor.",
                "&e&l          Cumpara arme si upgrade-uri colectand",
                "&e&l   Fier, Aur, Emeralde si Diamonte de la generatoare." , "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.MEANING_SHOUT, "shout");
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "{prefix}&cNu poti pune blocuri aici!");
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList("&f&lBED WARS", "", "&fHarta: &a{map}", "&fJucatori: &a{on}/{max}", "", "&fIncepe in &a{time}s", "", "§fServer: &a{server}", "", "&e  andrei1058.com"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList("&f&lBED WARS", "", "&fHarta: &a{map}", "&fJucatori: &a{on}/{max}", "", "&fIn asteptare...", "", "§fServer: &a{server}", "", "&e  andrei1058.com"));
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "{prefix}&cYou can only break blocks placed by a player!");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&aGO");
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent}", "&a{time}", "", "{TeamRedColor}&lR&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}&lA&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}&lV&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}&lG &f{TeamYellowName}&f: {TeamYellowStatus}", "",
                "&fUcideri: &a{kills}", "&fUcideri Finale: &a{finalKills}", "&fPaturi distruse: &a{beds}", "", "&ehttp://andrei1058.com"));
        yml.addDefault("scoreboard.SoloPlaying", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent}", "&a{time}", "", "{TeamRedColor}&lR&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}&lA&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}&lV&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}&lG &f{TeamYellowName}&f: {TeamYellowStatus}",
                "{TeamAquaColor}&lA&f {TeamAquaName}&f: {TeamAquaStatus}", "{TeamWhiteColor}&lA&f {TeamWhiteName}&f: {TeamWhiteStatus}", "{TeamPinkColor}&lR&f {TeamPinkName}&f: {TeamPinkStatus}", "{TeamGrayColor}&lG&f {TeamGrayName}&f: {TeamGrayStatus}", "", "&ehttp://andrei1058.com"));
        yml.addDefault("scoreboard.DoublesPlaying", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent}", "&a{time}", "", "{TeamRedColor}&lR&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}&lA&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}&lV&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}&lG &f{TeamYellowName}&f: {TeamYellowStatus}",
                "{TeamAquaColor}&lA&f {TeamAquaName}&f: {TeamAquaStatus}", "{TeamWhiteColor}&lA&f {TeamWhiteName}&f: {TeamWhiteStatus}", "{TeamPinkColor}&lR&f {TeamPinkName}&f: {TeamPinkStatus}", "{TeamGrayColor}&lG&f {TeamGrayName}&f: {TeamGrayStatus}", "", "&ehttp://andrei1058.com"));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_DATE, "dd/MM/yy");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED, "&c&l✘");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_BED_DESTROYED, "&a{remainingPlayers}");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE, "&a&l✓");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER, "mm:ss");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_YOUR_TEAM, "&7 (You)");
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fUrmaresti: {team} &f- Distanta: {distance}m");
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&lApara-ti patul!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&cNu iti poti distruge propriul pat!");
        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&lTEAM ELIMINAT > {TeamColor}Echipa {TeamName} &ca fost eliminata!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&lPAT DISTRUS > {TeamColor}Patul Echipei {TeamName} &7a fost distrus de {PlayerColor}{PlayerName}&7!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&cPAT DISTRUS!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&fNu te mai poti respawna!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&f&lPAT DISTRUS > &7Patul tau a fost distrus de {PlayerColor}{PlayerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "{PlayerColor}{PlayerName} &7a cazut in void.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "{PlayerColor}{PlayerName} &7a cazut in void. &b&lUCIDERE FINALA!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "{PlayerColor}{PlayerName} &7a fost impins in void de {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, "{PlayerColor}{PlayerName} &7a fost impins in void de {KillerColor}{KillerName}&7. &b&lUCIDERE FINALA!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "{PlayerColor}{PlayerName} &7a fost lovit de tnt-ul lui {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7a fost lovit de tnt-ul lui {KillerColor}{KillerName}&7. &b&lUCIDERE FINALA!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "{PlayerColor}{PlayerName} &7a fost ucis de {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "{PlayerColor}{PlayerName} &7a fost ucis de {KillerColor}{KillerName}&7. &b&lUCIDERE FINALA!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lPatul tau a fost distrus!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cESTI BLEG!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eTe vei respawna in &c{time} &esecunde!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "{prefix}&eTe vei respawna in &c{time} &esecunde!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aRESPAWNAT!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "{prefix}&cAi fost eliminat!");
        yml.addDefault(Messages.ARENA_GAME_OVER_PLAYER_TITLE, "&c&lJOC TERMINAT!");
        yml.addDefault(Messages.ARENA_VICTORY_PLAYER_TITLE, "&6&lVICTORIE!");
        yml.addDefault(Messages.ARENA_GAME_OVER_PLAYER_CHAT, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "", "{winnerFormat}", "", "",
                "&e                      &lPrimul Ucigas &7- {firstName} - {firstKills}",
                "&6                      &lAl 2-lea Ucigas &7- {secondName} - {secondKills}",
                "&c                      &lAl 3-lea Ucigas &7- {thirdName} - {thirdKills}", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")); //todo disponibile {firstDeaths} {firstBeds} {firstName} {firstKills}
        yml.addDefault(Messages.ARENA_TEAM_WON_CHAT, "{prefix}{TeamColor}Echipa {TeamName} &aa castigat!");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.MEANING_NOBODY, "Nimeni");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT, "{prefix}&eGeneratoarele de {generatorType} &eau fost upgradate la nivelul &c{tier}");
        yml.addDefault(Messages.UPGRADES_TEAM_NPC_NAME, "&e&lTEAM,&E&LUPGRADES");
        yml.addDefault(Messages.UPGRADES_SOLO_NPC_NAME, "&e&lSOLO,&E&LUPGRADES");
        yml.addDefault(Messages.SHOP_TEAM_NAME, "&e&lMAGAZIN");
        yml.addDefault(Messages.SHOP_SOLO_NAME, "&e&lMAGAZINP");
        yml.addDefault(Messages.SHOP_PATH + "name", "&7Magazin Obiecte");

        lbj.saveShopStuff("invContents.armor", "&aArmura", Arrays.asList("&7Disponibil:", "&7▪ Cizme De Zale", "&7▪ Pantaloni De Zale", "&7▪ Cizme De Fier", "&7▪ Pantaloni De Fier", "&7▪ Cizme De Diamant", "&7▪ Pantaloni De Diamant", "", "&eClick pentru a rasfoi!"));
        lbj.saveShopStuff("invContents.melee", "&aArmament", Arrays.asList("&7Disponibil:", "&7▪ Sabie De Piatra", "&7▪ Sabie De Fier", "&7▪ Sabie De Diamant", "&7▪ Baston (Impingere I)", "", "&eClick pentru a rasfoi!"));
        lbj.saveShopStuff("invContents.blocks", "&aBlocuri", Arrays.asList("&7Disponibil:", "&7▪ Lana", "&7▪ Lut Intarit", "&7▪ Sticla Anti Explozie", "&7▪ Piatra Din End", "&7▪ Scara", "&7▪ Scandura", "&7▪ Obsidian", "", "&eClick pentru a rasfoi!"));
        lbj.saveShopStuff("invContents.ranged", "&aArcasi", Arrays.asList("&7Disponibil:", "&7▪ Sageti", "&7▪ Arc", "&7▪ Arc (Putere I)", "&7▪ Arc (Putere I, Lovitura I)", "", "&eClick pentru a rasfoi!"));
        lbj.saveShopStuff("invContents.tools", "&aUnelte", Arrays.asList("&7Disponibil:", "&7▪ Foarfece", "&7▪ Tarnacol De Lemn (Eficienta I)", "&7▪ Topor De Lemn (Eficienta I)", "", "&eClick pentru a rasfoi!"));
        lbj.saveShopStuff("invContents.potions", "&aPotiuni", Arrays.asList("&7Disponibil:", "&7▪ Potiune Viteza II", "&7▪ Potiune Saritura V", "&7▪ Potiune Invizibilitate", "", "&eClick pentru a rasfoi!"));
        lbj.saveShopStuff("invContents.utility", "&aUtilitati", Arrays.asList("&7Disponibil:", "&7▪ Mar De Aur", "&7▪ Sobolan", "&7▪ Bataus", "&7▪ Bila De Foc", "&7▪ TNT", "&7▪ Ender Pearl", "&7▪ Galeata Cu Apa", "&7▪ Oul Creator De Pod", "", "&eClick pentru a rasfoi!"));
        //lbj.saveShopStuff("invContents.favourites", "&aFavourites", Arrays.asList("&7Click to view your", "&7favourite Obiecte. You can", "&7set a favourite item by", "&7shift clicking on any shop item!"));
        //lbj.saveShopStuff("invContents.recommended", "&aRecommended Obiecte", Arrays.asList("&7Original", "&7Click to view some", "&7recommended Obiecte for early", "&7to late game!", "", "&eClick pentru a rasfoi!"));

        lbj.saveShopStuff("invContents.armor.invContents.chainmail", "&aArmura Permanenta De Zale", Arrays.asList("&8Obiecte:", "&8▪ &7Armura Permanenta De Zale", "&8▪ &7Pantaloni De Zale", "", "&8Pret: &f{cost} {currency}", "&8&oNu vei pierde asta cand vei muri!"));
        lbj.saveShopStuff("invContents.armor.invContents.iron", "&aArmura Permanenta De Fier", Arrays.asList("&8Obiecte:", "&8▪ &7Armura Permanenta De Fier", "&8▪ &7Pantaloni De Fier", "", "&8Pret: &6{cost} {currency}", "&8&oNu vei pierde asta cand vei muri!"));
        lbj.saveShopStuff("invContents.armor.invContents.diamond", "&aArmura Permanenta De Diamant", Arrays.asList("&8Obiecte:", "&8▪ &7Armura Permanenta De Diamant", "&8▪ &7Pantaloni De Diamant", "", "&8Pret: &2{cost} {currency}", "&8&oNu vei pierde asta cand vei muri!"));
        lbj.saveShopStuff("invContents.armor.invContents.back", "&aInapoi", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.melee.invContents.stone", "&aSabie De Piatra", Arrays.asList("&8Obiecte:", "&8▪ &7Sabie De Piatra", "", "&8Pret: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.iron", "&aSabie De Fier", Arrays.asList("&8Obiecte:", "&8▪ &7Sabie De Fier", "&8Pret: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.diamond", "&aSabie De Diamant", Arrays.asList("&8Obiecte:", "&8▪ &7Sabie De Diamant", "", "&8Pret: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.stick", "&aBaston (Impingere I)", Arrays.asList("&8Obiecte:", "&8▪ &7Baston (Impingere I)", "", "&8Pret: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.back", "&aInapoi", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.blocks.invContents.wool", "&aLana", Arrays.asList("&8Obiecte:", "&8▪ &7Lana", "", "&8Pret: &f{cost} {currency}", "", "&eClick pentru a cumpara!"));
        lbj.saveShopStuff("invContents.blocks.invContents.clay", "&aLut Intarit", Arrays.asList("&8Obiecte:", "&8▪ &7Lut Intarit", "", "&8Pret: &f{cost} {currency}", "", "&eClick pentru a cumpara!"));
        lbj.saveShopStuff("invContents.blocks.invContents.glass", "&aSticla Anti Explozibil", Arrays.asList("&8Obiecte:", "&8▪ &7Sticla Anti Explozibil", "", "&8Pret: &f{cost} {currency}", "", "&7Immune to explosions", "", "&eClick pentru a cumpara!"));
        lbj.saveShopStuff("invContents.blocks.invContents.stone", "&aPiatra Din End", Arrays.asList("&8Obiecte:", "&8▪ &7Piatra Din End", "", "&8Pret: &f{cost} {currency}", "", "&eClick pentru a cumpara!"));
        lbj.saveShopStuff("invContents.blocks.invContents.ladder", "&aScara", Arrays.asList("&8Obiecte:", "&8▪ &7Scara", "", "&8Pret: &f{cost} {currency}", "", "&eClick pentru a cumpara!"));
        lbj.saveShopStuff("invContents.blocks.invContents.wood", "&aScandura", Arrays.asList("&8Obiecte:", "&8▪ &7Scandura", "", "&8Pret: &6{cost} {currency}", "", "&eClick pentru a cumpara!"));
        lbj.saveShopStuff("invContents.blocks.invContents.obsidian", "&cObsidian", Arrays.asList("&8Obiecte:", "&8▪ &7Obsidian", "", "&8Pret: &2{cost} {currency}", "", "&eClick pentru a cumpara!"));
        lbj.saveShopStuff("invContents.blocks.invContents.back", "&aInapoi", Arrays.asList(""));

        lbj.saveShopStuff("invContents.ranged.invContents.arrow", "&aSageti", Arrays.asList("&8Obiecte:", "&8▪ &7Sageti", "", "&8Pret: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow", "&aArc", Arrays.asList("&8Obiecte:", "&8▪ &7Arc", "", "&8Pret: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow2", "&aArc (Putere I)", Arrays.asList("&8Obiecte:", "&8▪ &7Arc (Putere I)", "", "&8Pret: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow3", "&aArc (Putere I, Lovitura I)", Arrays.asList("&8Obiecte:", "&8▪ &7Arc (Putere I, Lovitura I)", "", "&8Pret: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.back", "&aInapoi", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.tools.invContents.shears", "&aFoarfece Permanente", Arrays.asList("&8Obiecte:", "&8▪ &7Foarfece Permanente", "", "&8Pret: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.pick", "&aTarnacop De Lemn", Arrays.asList("&8Obiecte:", "&8▪ &7Tarnacop De Lemn", "", "&8Pret: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.axe", "&aTopor De Lemn", Arrays.asList("&8Obiecte:", "&8▪ &7Topor De Lemn", "", "&8Pret: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.back", "&aInapoi", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.potions.invContents.potion1", "&aPotiune Viteza II", Arrays.asList("&8Obiecte:", "&8▪ &7Speed II potion", "", "&8Pret: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.potion2", "&aPotiune Saritura V", Arrays.asList("&8Obiecte:", "&8▪ &7Potiune Saritura V", "", "&8Pret: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.potion3", "&aPotiune Invizibilitate", Arrays.asList("&8Obiecte:", "&8▪ &7Potiune Invizibilitate", "", "&8Pret: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.back", "&aInapoi", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.utility.invContents.apple", "&aMar De Aur", Arrays.asList("&8Obiecte:", "&8▪ &7Mar De Aur", "", "&8Pret: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.utility.invContents.sball", "&aSobolan", Arrays.asList("&8Obiecte:", "&8▪ &7Sobolan", "", "&8Pret: &f{cost} {currency}", "", "&7Destul de enervant. Aceasta", "&7mica creatura poate fi folosita", "&7pentru a distrage inamicii."));
        lbj.saveShopStuff("invContents.utility.invContents.monsteregg", "&aBataus", Arrays.asList("&8Obiecte:", "&8▪ &7Bataus", "", "&8Pret: &f{cost} {currency}", "", "&7Destul de enervant.", "&7Te ajuta de obicei sa iti aperi", "&7baza."));
        lbj.saveShopStuff("invContents.utility.invContents.fireball", "&aBila De Foc", Arrays.asList("&8Obiecte:", "&8▪ &7Bila De Foc", "", "&8Pret: &f{cost} {currency}", "", "&7Click-Dreapta pentru a folosi!"));
        lbj.saveShopStuff("invContents.utility.invContents.tnt", "&aTNT", Arrays.asList("&8Obiecte:", "&8▪ &7TNT", "", "&8Pret: &6{cost} {currency}", "", "&7Se aprinde instant, util pentru", "&7a exploda chestii!"));
        lbj.saveShopStuff("invContents.utility.invContents.enderpearl", "&aEnder Pearl", Arrays.asList("&8Obiecte:", "", "&8Ender Pearl", "&8▪ &7Pret: &2{cost} {currency}", "", "&7Foarte util pentru a invada", "&7bazele inamicilor."));
        lbj.saveShopStuff("invContents.utility.invContents.bucket", "&aGaleata Cu Apa", Arrays.asList("&8Obiecte:", "&8▪ &7Galeata Cu Apa", "", "&8Pret: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.utility.invContents.eggBridge", "&aCreator De Pod", Arrays.asList("&8Obiecte:", "&8▪ &7Creator De Pod", "", "&8Pret: &2{cost} {currency}", "&7Acest ou creeaza un pod", "&7in directia in care este lansat."));
        lbj.saveShopStuff("invContents.utility.invContents.back", "&aInapoi", Collections.singletonList(""));
        yml.addDefault("meaning.iron", "Fier");
        yml.addDefault("meaning.gold", "Aur");
        yml.addDefault("meaning.emerald", "Emerald");
        yml.addDefault("meaning.diamond", "Diamant");
        yml.addDefault("meaning.vault", "$");
        yml.addDefault(Messages.PLURAL_PATH + ".iron", "Fier");
        yml.addDefault(Messages.PLURAL_PATH + ".gold", "Aur");
        yml.addDefault(Messages.PLURAL_PATH + ".emerald", "Emeralde");
        yml.addDefault(Messages.PLURAL_PATH + ".diamond", "Diamante");
        yml.addDefault(Messages.PLURAL_PATH + ".vault", "Bani");
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "{prefix}&cNu ai destul(e) {currency}! Mai ai nevoie de {amount}!");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "{prefix}&aAi cumparat &6{item}");
        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Folosire: /" + mainCmd + " join §o<arena/group>");
        yml.addDefault(Messages.COMMAND_NOT_ALLOWED_IN_GAME, "{prefix}&cNu poti face asta in timpul meciului.");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "&aClick pentru a cumpara!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "&cNu ai destul(e) {currency}");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cLBLOCAT");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "&aDEBLOCAT");
        yml.addDefault("upgrades.Default.generators.tier1.name", "&eGenerator Fier");
        yml.addDefault("upgrades.Default.generators.tier1.lore", Arrays.asList("&7Creste rata de spawnare a Fierului", "&7si a Aurului cu 50%..", "", "&7Pret:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.generators.tier2.name", "&eGenerator Aur");
        yml.addDefault("upgrades.Default.generators.tier2.lore", Arrays.asList("&7Creste rata de spawnare a Fierului", "&7si a Aurului cu 100%..", "", "&7Pret:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.generators.tier3.name", "&eGenerator Emerald");
        yml.addDefault("upgrades.Default.generators.tier3.lore", Arrays.asList("&7Activeaza generatorul de Emeralde", "&7in baza echipei tale.", "", "&7Pret:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.maniacMiner.tier1.name", "&eManiac Miner");
        yml.addDefault("upgrades.Default.maniacMiner.tier1.lore", Arrays.asList("&7Toti jucatorii din echipa ta vor primi", "&7efectul Graba I permanent.", "", "&7Pret:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.sharpSword.tier1.name", "&eSharpened Swords");
        yml.addDefault("upgrades.Default.sharpSword.tier1.lore", Arrays.asList("&7Echipa ta va primi Sharpness I", "&7pe toate sabiile!", "", "&7Pret:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.reinforced.tier1.name", "&eReinforced Armor");
        yml.addDefault("upgrades.Default.reinforced.tier1.lore", Arrays.asList("&7Echipa ta va primi Protectie I", "&7pe toata armura!", "", "&7Pret:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.trap.tier1.name", "&eEste o capcana!");
        yml.addDefault("upgrades.Default.trap.tier1.lore", Arrays.asList("&7Urmatorul inamic care va intra in", "&7baza ta va primi efectul de Orbire ", "&7si de Incetinire!", "", "&7Pret:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.miningFatigue.tier1.name", "&eMiner Fatigue Trap");
        yml.addDefault("upgrades.Default.miningFatigue.tier1.lore", Arrays.asList("&7Urmatorul inamic care va intra in", "&7baza ta va primi efectul de Oboseala", "&7pentru 10 secunde!", "", "&7Pret:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.healPool.tier1.name", "&eHeal Pool");
        yml.addDefault("upgrades.Default.healPool.tier1.lore", Arrays.asList("&7Creeaza un camp de Regenerare", "&7a vietii in baza ta!", "", "&7Pret:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault(Messages.ARENA_ENEMY_BASE_ENTER_ACTION, "&cCAPCANA ACTIVATA!");
        yml.addDefault(Messages.ARENA_ENEMY_BASE_ENTER_CHAT, "{prefix}&cCAPCANA ACTIVATA!");
        yml.addDefault(Messages.ARENA_ENEMY_BASE_ENTER_SUBTITLE, "&fCapcana dvs. a fost pornita!");
        yml.addDefault(Messages.ARENA_ENEMY_BASE_ENTER_TITLE, "&cCAPCANA ACTIVATA!");
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a{player} a cumparat &6{upgradeName}");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "{TeamColor}&lSobolan {TeamName}");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "{TeamColor}{despawn} &8[ {TeamColor}{health}&8]");
        yml.addDefault(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH, "▮ ");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR, "{PlayerColor}{PlayerName} &7a murit.");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL, "{PlayerColor}{PlayerName} &7a murit. &b&lUCIDERE FINALA!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_REGULAR, "{PlayerColor}{PlayerName} &7a fost impuscat de {KillerColor}{KillerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_FINAL_KILL, "{PlayerColor}{PlayerName} &7a fost impuscat de {KillerColor}{KillerName}&7! &b&lUCIDERE FINALA!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_REGULAR, "{PlayerColor}{PlayerName} &7a fost ucis de {KillerColor}Sobolanul Echipei {KillerTeamName}!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_FINAL_KILL, "{PlayerColor}{PlayerName} &7a fost ucis de {KillerColor}Sobolanul Echipei {KillerTeamName! &b&lUCIDERE FINALA!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_REGULAR, "{PlayerColor}{PlayerName} &7a fost ucis de {KillerColor}Gardianul Echipei {KillerTeamName}!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL, "{PlayerColor}{PlayerName} &7a fost ucis de {KillerColor}Gardianul Echipei {KillerTeamName}! &b&lUCIDERE FINALA!");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_DIAMOND, "{prefix}&b+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_EMERALD, "{prefix}&a+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_IRON, "{prefix}&f+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_GOLD, "{prefix}&6+{amount} {meaning}");

        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR, "{PlayerColor}{PlayerName} &7a fost lovit de o bomba.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &a fost lovit de o bomba. &b&lUCIDERE FINALA!");
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "&68Statisticile lui {player}");

        /* save default Obiecte messages for stats gui */
        lbj.addDefaultStatsMsg(yml, "wins", "&6Victorii", "&f{wins}");
        lbj.addDefaultStatsMsg(yml, "losses", "&6Pierderi", "&f{losses}");
        lbj.addDefaultStatsMsg(yml, "kills", "&6Ucideri", "&f{kills}");
        lbj.addDefaultStatsMsg(yml, "deaths", "&6Decese", "&f{deaths}");
        lbj.addDefaultStatsMsg(yml, "finalKills", "&6Ucideri Finale", "&f{finalKills}");
        lbj.addDefaultStatsMsg(yml, "finalDeaths", "&6Decese Finale", "&f{finalDeaths}");
        lbj.addDefaultStatsMsg(yml, "bedsDestroyed", "&6Paturi Distruse", "&f{bedsDestroyed}");
        lbj.addDefaultStatsMsg(yml, "firstPlay", "&6Primul Meci", "&f{firstPlay}");
        lbj.addDefaultStatsMsg(yml, "lastPlay", "&6Ultimul Meci", "&f{lastPlay}");
        lbj.addDefaultStatsMsg(yml, "gamesPlayed", "&6Partide Jucate", "&f{gamesPlayed}");
        yml.addDefault(Messages.FORMATTING_STATS_DATE_FORMAT, "yyyy/MM/dd HH:mm");

        yml.addDefault(Messages.MEANING_NEVER, "Niciodata");
        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&6&lBedWars", "&7{date}", "&fNume: &a{player}", "", "&fVictorii: &a{wins}", "&fPierderi: &a{losses}", "&fUcideri: &a{kills}", "&fDecese: &a{deaths}"
                , "&fDecese Finale: &a{fKills}", "&fPaturi Distruse: &a{beds}", "", "&fConectati: &a{on}", "&eandrei1058.com"));

        /* party commands */
        yml.addDefault(Messages.COMMAND_PARTY_HELP, Arrays.asList("&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&aComenzi Party:", "&e/party help &7- &bArata mesajele de ajutor", "&e/party invite <jucatori> &7- &bInvita un jucator in party",
                "&e/party leave &7- &bParaseste un party",
                "&e/party remove <player> &7- &bScoate un jucator din party",
                "&e/party accept <player> &7- &bAccepta o invitatie", "&e/party disband &7- &bSterge party-ul"));
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_USAGE, "{prefix}&eFolosire: &7/party invite <jucator>");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG, "{prefix}&7{player} &enu este online!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT, "{prefix}&eInvitatia a fost trimisa lui &7{player}&6.");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG, "{prefix}&b{player} &ete-a invitat intr-un party! &o&7(Click pentru a accepta)");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF, "{prefix}&cNu te poti invita singur!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE, "{prefix}&cNu ai cereri de acceptat.");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY, "{prefix}&Esti deja intr-un party!");
        yml.addDefault(Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS, "{prefix}&cDoar detinatorul party-ului poate face asta!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_USAGE, "{prefix}&eFolosire: &7/party accept <jucator>");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_SUCCESS, "{prefix}&7{player} &ea intrat in party!");
        yml.addDefault(Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY, "{prefix}&cNu esti intr-un party!");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND, "{prefix}&cNu poti iesi din propriul party!\n&eIncearca: &b/party disband");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_SUCCESS, "{prefix}&7{player} &ea iesit din party!");
        yml.addDefault(Messages.COMMAND_PARTY_DISBAND_SUCCESS, "{prefix}&eParty-ul a fost sters!");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_USAGE, "{prefix}&7Folosire: &e/party remove <jucator>");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_SUCCESS, "{prefix}&7{player} &ea fost scos din party.");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER, "{prefix}&7{player} &enu este in acest party!");
        yml.addDefault(Messages.NEXT_EVENT_BEDS_DESTROY, "&cDistrugerea Paturilor");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_II, "&fDiamond II");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_III, "&fDiamond III");
        yml.addDefault(Messages.NEXT_EVENT_DRAGON_SPAWN, "&fMoarte Brusca");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_II, "&fEmerald II");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_III, "&fEmerald III");
        yml.addDefault(Messages.NEXT_EVENT_GAME_END, "&4Final De Joc");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED, "&cPATUL A FOST DISTRUS!");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED, "&fToate paturile au fost distruse!");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED, "&c&lToate paturile au fost distruse!");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH, "&cMoarte Brusca");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH, "");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH, "&cMOARTE BRUSCA: &6&b{TeamDragons} {TeamColor}Dragonul {TeamName}!");
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "{prefix}&cComanda nu a fost gasita sau nu ai permisiunea!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "§c▪ §7Nu esti in joc!");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "§c▪ §7Numaratoarea a fost redusa!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "{prefix}&7Nu poti forta arena sa inceapa.\n§7Ai putea lua in considerare donarea pentru mai multe facilitati.");
        yml.addDefault(Messages.ARENA_JOIN_SPECTATOR_MSG, "{prefix}§6Acum urmaresti meciul din §9{arena}§6.\n{prefix}§ePoti parasi arena folosind §c/leave§e.");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cNu poti deschide acest cufar pentru ca echipa a fost eliminata!");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8Teleportor");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "{prefix}{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7Viata: &f{health}%", "&7Hrana: &f{food}", "", "&7Click-stanca pentru a urmari."));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lIntoarce-te in lobby");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Click-dreapta pentru a merge in lobby!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&aUrmareste-l pe &7{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&cSNEAK pentru a iesi");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eIesi din modul Spectator");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "{prefix}&cAi cumparat deja asta!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &eeste offline!");
        yml.addDefault(Messages.ARENA_JOIN_SPECTATOR_DENIED_MSG, "&cSpectatorii nu sunt permisi in aceasta arena!");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "{prefix}§cParty-ul a fost sters pentru ca detinatorul a iesit!");

        /* Lobby Command Obiecte */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH+".stats") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "stats"), "&eStatistici");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Arrays.asList("&fClick-dreapta pentru", "&fa-ti vedea statisticile!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH+".arena-selector") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&eArena Selector");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Arrays.asList("&fClick-dreapta pentru", "&fa alege o arena."));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH+".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&eInapoi in Hub");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Arrays.asList("&fClick-dreapta pentru a", "&fparasi BedWars!"));
        }
        /* Pret Game Command ITEMS */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH+".stats") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&eStatistici");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Arrays.asList("&fClick-dreapta pentru", "&fa-ti vedea statisticile!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH+".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&eInapoi in Hub");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Arrays.asList("&fClick-dreapta pentru a", "&fparasi BedWars!"));
        }
        /* Spectator Command ITEMS */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH+".teleporter") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&eTeleportor");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "teleporter"), Arrays.asList("&fClick-dreapta pentru a urmari", "&fun jucator!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH+".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&eInapoi in Lobby");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Arrays.asList("&fClick-dreapta pentru a", "&fparasi arena!"));
        }
        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cNu poti face asta inca! Mai asteapta {seconds} secunde!");
        yml.addDefault(Messages.PLACEHOLDER_PLAYER_TEAM_TEAM, "{TeamColor}[{TeamName}]");
        yml.addDefault(Messages.PLACEHOLDER_PLAYER_TEAM_SHOUT, "&6[STRIGAT]");
        yml.addDefault(Messages.PLACEHOLDER_PLAYER_TEAM_SPECTATOR, "&7[SPECTATOR]");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "{prefix}&cNe pare rau dar nu poti intra chiar acum. Foloseste Click-Dreapta pentru a urmari!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "{prefix}&cNe pare rau dar nu poti urmari arena chiar acum. Foloseste Click-Dreapta pentru a juca!");
    }
}
