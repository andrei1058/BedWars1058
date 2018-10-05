package com.andrei1058.bedwars.configuration.language;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.Messages;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;
import java.util.Collections;

import static com.andrei1058.bedwars.Main.mainCmd;

public class Italian {

    public Italian(Language lbj, YamlConfiguration yml){
        yml.options().header("Traduzione in italiano di Fabian03#4583");
        yml.addDefault(Messages.PREFIX, "");
        yml.addDefault("name", "Italiano");
        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + mainCmd + " stats", "&2▪ &7/" + mainCmd + " join &o<arena/gruppo>", "&2▪ &7/" + mainCmd + " leave", "&2▪ &7/" + mainCmd + " lang", "&2▪ &7/" + mainCmd + " gui", "&2▪ &7/" + mainCmd + " start &3(vip)"));
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "{prefix}&cIl comando non è stato trovato o non hai abbastanza permessi!\n&aPotresti fare una donazione per ottenere permessi vip. &7&o(click)");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_IS_FULL, "{prefix}&cArena piena!\n&aPrendi in considerazione il fatto di donare per entrare anche quando le arene sono piene. &7&o(clicca qui)");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_IS_FULL_VIP_REQUIRED, "{prefix}&cArena piena.\n&cSappiamo che sei un donatore ma al momento questa arena è piena di staffer o donatori.");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS, "{prefix}§cNon ci sono abbastanza giocatori! Conto alla rovescia fermato!");
        yml.addDefault(Messages.ARENA_LEAVE_PLAYER_LEAVE_MSG, "{prefix}&e{player} si è disconnesso!");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "{prefix}&eL'arena in cui ti trovavi si sta restartando.");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&cIn gioco");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4In riavvio");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&2In attesa §c{full}");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6In avvio §c{full}");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_GROUP_OR_ARENA_NOT_FOUND, "{prefix}&cNon esiste nessun gruppo o arena chiamato: {name}");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NO_EMPTY_FOUND, "{prefix}&cNon ci sono arene disponibili al momento ;(");
        yml.addDefault(Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA, "{prefix}&cNon sei in gioco!");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8Arene disponibili");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l{name}");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7Stato: {status}", "&7Giocatori: &f{on}&7/&f{max}", "&7Tipo: &a{group}", "", "&eClick-Sinistro per entrare.", "&eClick-Destro per guardare."));
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "{prefix} &2Lingue disponibili:");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7{iso} - &f{name}");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "{prefix}&7Utilizzo: /lang &f&o<iso>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "{prefix}&cQuesta lingua non esiste!");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "{prefix}&aLingua modificata!");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "{prefix}&cNon puoi cambiare la lingua durante il gioco.");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_PARTY_TOO_BIG, "{prefix}&cIl tuo party è troppo grande per entrare in questa arena come una squadra :(");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NOT_PARTY_LEADER, "{prefix}&cSolo il creatore del party può scegliere l'arena.");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&eLivello &c{tier}");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&lDiamante");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&lSmeraldo");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&eDrop in &c{seconds} &esecondi");
        yml.addDefault(Messages.ARENA_JOIN_PLAYER_JOIN_MSG, "{prefix}&7{player} &eè entrato in gioco (&b{on}&e/&b{max}&e)!");
        yml.addDefault(Messages.ARENA_LEAVE_PLAYER_LEAVE_MSG, "{prefix}&7{player} &esi è disconnesso!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN, "{prefix}&eIl gioco avrà inizio in &6{time} &esecondi!");
        yml.addDefault(Messages.FORMATTING_CHAT_LOBBY, "{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_WAITING, "{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SHOUT, "{vPrefix}&6[SHOUT] {team} &7{player}&f{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_TEAM, "{vPrefix}&f{level}{team}&7 {player}{vSuffix} {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SPECTATOR, "{vPrefix}&7[SPECTATOR] {player}{vSuffix}: {message}");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "",
                "&e&l    Proteggi il tuo letto e distruggi quello degli avversari.",
                "&e&l      Potenzia te e il tuo team collezionando",
                "&e&l   Ferro, Oro, Smeraldi, e Diamanti dai generatori",
                "&e&l             per avere accesso a forti potenziamenti.", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.MEANING_SHOUT, "shout");
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "{prefix}&cNon puoi piazzare blocchi in questa area!");
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList("&f&lBED WARS", "", "&fMappa: &a{map}", "&fGiocatori: &a{on}/{max}", "", "&fInizio in &a{time}s", "", "§fServer: &a{server}", "", "&e  andrei1058.com"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList("&f&lBED WARS", "", "&fMappa: &a{map}", "&fGiocatori: &a{on}/{max}", "", "&fIn attesa...", "", "§fServer: &a{server}", "", "&e  andrei1058.com"));
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "{prefix}&cPuoi rompere solo i blocchi che sono stati piazzati dai giocatori!");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&aVIA");
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent}", "&a{time}", "", "{TeamRedColor}&lR&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}&lB&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}&lG&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}&lY &f{TeamYellowName}&f: {TeamYellowStatus}", "",
                "&fUccisioni: &a{kills}", "&fUccisioni finali: &a{finalKills}", "&fLetti distrutti: &a{beds}", "", "&ehttp://andrei1058.com"));
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
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fObbiettivo: {team} &f- Distanza: {distance}m");
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&lDifendi il tuo letto!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&cNon puoi distruggere il tuo letto!");
        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&lTEAM ELIMINATO > Il team {TeamColor}{TeamName} &cè stato eliminato\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&lLETTO DISTRUTTO > Il letto del team {TeamColor}{TeamName} &7è stato fatto a pezzi da {PlayerColor}{PlayerName}&7!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&cLETTO DISTRUTTO!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&fNon verrai più respawnato!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&f&lLETTO DISTRUTTO > &7Il tuo letto è stato distrutto da {PlayerColor}{PlayerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "{PlayerColor}{PlayerName} &7è caduto nel vuoto.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "{PlayerColor}{PlayerName} &7è caduto nel vuoto. &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "{PlayerColor}{PlayerName} &7è caduto nel vuoto con l'aiuto di {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, "{PlayerColor}{PlayerName} &7è caduto nel vuoto con l'aiuto di {KillerColor}{KillerName}&7. &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "{PlayerColor}{PlayerName} &7è saltato in aria a causa di {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7è saltato in aria a causa di {KillerColor}{KillerName}&7. &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "{PlayerColor}{PlayerName} &7è stato ucciso da {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "{PlayerColor}{PlayerName} &7è stato ucciso da {KillerColor}{KillerName}&7. &b&lUCCISIONE FINALE!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lIl tuo letto è stato distrutto!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cSEI MORTO!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eVerrai respawnato in &c{time} &esecondi!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "{prefix}&eVerrai respawnato in &c{time} &esecondi!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aRESPAWNATO!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "{prefix}&cSei stato eliminato!");
        yml.addDefault(Messages.ARENA_GAME_OVER_PLAYER_TITLE, "&c&lGAME OVER!");
        yml.addDefault(Messages.ARENA_VICTORY_PLAYER_TITLE, "&6&lVITTORIA!");
        yml.addDefault(Messages.ARENA_GAME_OVER_PLAYER_CHAT, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "", "{winnerFormat}", "", "",
                "&e                          &l1° Uccisore &7- {firstName} - {firstKills}",
                "&6                          &l2° Uccisore &7- {secondName} - {secondKills}",
                "&c                          &l3° Uccisore &7- {thirdName} - {thirdKills}", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")); //todo disponibile {firstDeaths} {firstBeds} {firstName} {firstKills}
        //yml.addDefault(gameOverReward, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
        //        "&f                                   &lReward Summary", "", "",
        //        "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.ARENA_TEAM_WON_CHAT, "{prefix}{TeamColor}{TeamName} &aha vinto il gioco!");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 {TeamColor}{TeamName} &7- {members}");
        //yml.addDefault(tablistFormat, "{TeamColor}&l{TeamLetter}&r {TeamColor}{PlayerName} &e{PlayerHealth}");//{TeamColor}{TeamName}{TeamHealth}{PlayerName}{PlayerHealth}
        yml.addDefault(Messages.MEANING_NOBODY, "Nessuno");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT, "{prefix}&eI generatori di {generatorType} &esono stati potenziati a livello &c{tier}");
        yml.addDefault(Messages.UPGRADES_TEAM_NPC_NAME, "&e&lTEAM,&E&LUPGRADES");
        yml.addDefault(Messages.UPGRADES_SOLO_NPC_NAME, "&e&lSOLO,&E&LUPGRADES");
        yml.addDefault(Messages.SHOP_TEAM_NAME, "&e&lITEM SHOP");
        yml.addDefault(Messages.SHOP_SOLO_NAME, "&e&lITEM SHOP");
        yml.addDefault(Messages.SHOP_PATH + "name", "&7Negozio di oggetti");

        lbj.saveShopStuff("invContents.armor", "&aArmatura", Arrays.asList("&7Contiene:", "&7▪ Stivali di maglia", "&7▪ Gambiera di maglia", "&7▪ Stivali di ferro", "&7▪ Gambiera di ferro", "&7▪ Stivali di diamante", "&7▪ Gambiera di diamante", "", "&eClicca per sfogliare!"));
        lbj.saveShopStuff("invContents.melee", "&aSpade", Arrays.asList("&7Contiene:", "&7▪ Spada di pietra", "&7▪ Spada di ferro", "&7▪ Spada di diamante", "&7▪ Bastone (Contraccolpo I)", "", "&eClicca per sfogliare!"));
        lbj.saveShopStuff("invContents.blocks", "&aBlocchi", Arrays.asList("&7Contiene:", "&7▪ Lana", "&7▪ Argilla indurita", "&7▪ Vetro anti esplosione", "&7▪ Pietra dell'End", "&7▪ Scala", "&7▪ Assi di quercia", "&7▪ Ossidiana", "", "&eClicca per sfogliare!"));
        lbj.saveShopStuff("invContents.ranged", "&aArchi", Arrays.asList("&7Contiene:", "&7▪ Frecce", "&7▪ Arco", "&7▪ Arco (Potenza I)", "&7▪ Arco (Potenza I, Contraccolpo I)", "", "&eClicca per sfogliare!"));
        lbj.saveShopStuff("invContents.tools", "&aUtensili", Arrays.asList("&7Contiene:", "&7▪ Cesoie", "&7▪ Piccone di legno (EfficienzaI)", "&7▪ Ascia di legno (Efficienza I)", "", "&eClicca per sfogliare!"));
        lbj.saveShopStuff("invContents.potions", "&aPozioni", Arrays.asList("&7Contiene:", "&7▪ Pozione di rapidità II", "&7▪ Pozione di salto V", "&7▪ Pozione di invisibilità", "", "&eClicca per sfogliare!"));
        lbj.saveShopStuff("invContents.utility", "&aUtilità", Arrays.asList("&7Contiene:", "&7▪ Mela d'oro", "&7▪ BedBug", "&7▪ Dream Defender", "&7▪ Fireball", "&7▪ TNT", "&7▪ Ender Pearl", "&7▪ Secchio d'acqua", "&7▪ Bridge Egg", "", "&eClicca per sfogliare!"));
        //lbj.saveShopStuff("invContents.favourites", "&aFavourites", Arrays.asList("&7Click to view your", "&7favourite items. You can", "&7set a favourite item by", "&7shift clicking on any shop item!"));
        //lbj.saveShopStuff("invContents.recommended", "&aRecommended Items", Arrays.asList("&7Original", "&7Click to view some", "&7recommended items for early", "&7to late game!", "", "&eClicca per sfogliare!"));

        lbj.saveShopStuff("invContents.armor.invContents.chainmail", "&aArmatura di maglia permanente", Arrays.asList("&8Oggetti:", "&8▪ &7Stivali di maglia", "&8▪ &7Gambiera di maglia", "", "&7Costoo: &f{cost} {currency}", "&8&oNon la perderai quando muori!"));
        lbj.saveShopStuff("invContents.armor.invContents.iron", "&aArmatura di ferro permanente", Arrays.asList("&8Oggetti:", "&8▪ &7Permanent Iron Armor", "&8▪ &7Iron Leggings", "", "&7Costoo: &6{cost} {currency}", "&8&oNon la perderai quando muori!"));
        lbj.saveShopStuff("invContents.armor.invContents.diamond", "&aPermanent Diamond Armor", Arrays.asList("&8Oggetti:", "&8▪ &7Permanent Diamond Armor", "&8▪ &7Diamond Leggings", "", "&7Costoo: &2{cost} {currency}", "&8&oNon la perderai quando muori!"));
        lbj.saveShopStuff("invContents.armor.invContents.back", "&aTorna indietro", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.melee.invContents.stone", "&aSpada di pietra", Arrays.asList("&8Oggetti:", "&8▪ &7Spada di pietra", "", "&7Costoo: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.iron", "&aSpada di ferro", Arrays.asList("&8Oggetti:", "&8▪ &7Spada di ferro", "&7Costoo: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.diamond", "&aSpada di diamante", Arrays.asList("&8Oggetti:", "&8▪ &7Spada di diamante", "", "&7Costoo: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.stick", "&aBastone (Contraccolpo I)", Arrays.asList("&8Oggetti:", "&8▪ &7Bastone (Contraccolpo I)", "", "&7Costoo: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.back", "&aTorna indietro", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.blocks.invContents.wool", "&aLana", Arrays.asList("&8Oggetti:", "&8▪ &7Lana", "", "&7Costoo: &f{cost} {currency}", "", "&eClick to purchase!"));
        lbj.saveShopStuff("invContents.blocks.invContents.clay", "&aArgilla indurita", Arrays.asList("&8Oggetti:", "&8▪ &aArgilla indurita", "", "&7Costoo: &f{cost} {currency}", "", "&eClick to purchase!"));
        lbj.saveShopStuff("invContents.blocks.invContents.glass", "&aVetro anti esplosione", Arrays.asList("&8Oggetti:", "&8▪ &7Vetro anti esplosione", "", "&7Costoo: &f{cost} {currency}", "", "&7Immune to explosions", "", "&eClick to purchase!"));
        lbj.saveShopStuff("invContents.blocks.invContents.stone", "&aPietra dell'End", Arrays.asList("&8Oggetti:", "&8▪ &7Pietra dell'End", "", "&7Costoo: &f{cost} {currency}", "", "&eClick to purchase!"));
        lbj.saveShopStuff("invContents.blocks.invContents.ladder", "&aScala", Arrays.asList("&8Oggetti:", "&8▪ &7Scala", "", "&7Costoo: &f{cost} {currency}", "", "&eClick to purchase!"));
        lbj.saveShopStuff("invContents.blocks.invContents.wood", "&aAssi di quercia", Arrays.asList("&8Oggetti:", "&8▪ &7Assi di quercia", "", "&7Costoo: &6{cost} {currency}", "", "&eClick to purchase!"));
        lbj.saveShopStuff("invContents.blocks.invContents.obsidian", "&cOssidiana", Arrays.asList("&8Oggetti:", "&8▪ &7Ossidiana", "", "&7Costoo: &2{cost} {currency}", "", "&eClick to purchase!"));
        lbj.saveShopStuff("invContents.blocks.invContents.back", "&aTorna indietro", Arrays.asList(""));

        lbj.saveShopStuff("invContents.ranged.invContents.arrow", "&aFrecce", Arrays.asList("&8Oggetti:", "&8▪ &7Frecce", "", "&7Costoo: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow", "&aArco", Arrays.asList("&8Oggetti:", "&8▪ &7Arco", "", "&7Costoo: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow2", "&aArco (Potenza I)", Arrays.asList("&8Oggetti:", "&8▪ &7Arco (Potenza I)", "", "&7Costoo: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow3", "&aArco (Potenza I, Contraccolpo I)", Arrays.asList("&8Oggetti:", "&8▪ &7Arco (Potenza I, Contraccolpo I)", "", "&7Costoo: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.back", "&aTorna indietro", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.tools.invContents.shears", "&aCesoie permanenti", Arrays.asList("&8Oggetti:", "&8▪ &7Cesoie permanenti", "", "&7Costoo: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.pick", "&aPiccone di legno", Arrays.asList("&8Oggetti:", "&8▪ &7Piccone di legno", "", "&7Costoo: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.axe", "&aAscia di legno", Arrays.asList("&8Oggetti:", "&8▪ &7Ascia di legno", "", "&7Costoo: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.back", "&aTorna indietro", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.potions.invContents.potion1", "&aPozione di rapidità II", Arrays.asList("&8Oggetti:", "&8▪ &7Pozione di rapidità II", "", "&7Costoo: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.potion2", "&aPozione di salto V", Arrays.asList("&8Oggetti:", "&8▪ &7Pozione di salto V", "", "&7Costoo: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.potion3", "&aPozione di invisibilità", Arrays.asList("&8Oggetti:", "&8▪ &7Pozione di invisibilità", "", "&7Costoo: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.back", "&aTorna indietro", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.utility.invContents.apple", "&aMela d'oro", Arrays.asList("&8Oggetti:", "&8▪ &7Mela d'oro", "", "&7Costoo: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.utility.invContents.sball", "&aBedbug", Arrays.asList("&8Oggetti:", "&8▪ &7Bedbug", "", "&7Costoo: &f{cost} {currency}", "", "&7Moderately annoying. These", "&7little critters can be thrown to", "&7distract enemies"));
        lbj.saveShopStuff("invContents.utility.invContents.monsteregg", "&aDream Defender", Arrays.asList("&8Oggetti:", "&8▪ &7Dream Defender", "", "&7Costoo: &f{cost} {currency}", "", "&7Moderately motivated.", "&7Sometimes they help defend your", "&7base"));
        lbj.saveShopStuff("invContents.utility.invContents.fireball", "&aFireball", Arrays.asList("&8Oggetti:", "&8▪ &7Fireball", "", "&7Costoo: &f{cost} {currency}", "", "&7Right-click to launch!"));
        lbj.saveShopStuff("invContents.utility.invContents.tnt", "&aTNT", Arrays.asList("&8Oggetti:", "&8▪ &7TNT", "", "&7Costoo: &6{cost} {currency}", "", "&7Instantly ignites, appropriate", "&7to explode things!"));
        lbj.saveShopStuff("invContents.utility.invContents.enderpearl", "&aEnder Pearl", Arrays.asList("&8Oggetti:", "", "&8Ender Pearl", "&8▪ &7Costo: &2{cost} {currency}", "", "&7Pretty useful for invading", "&7enemies bases."));
        lbj.saveShopStuff("invContents.utility.invContents.bucket", "&aSecchio d'acqua", Arrays.asList("&8Oggetti:", "&8▪ &7Secchio d'acqua", "", "&7Costoo: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.utility.invContents.eggBridge", "&aBridge Egg", Arrays.asList("&8Oggetti:", "&8▪ &7Bridge Egg", "", "&7Costoo: &2{cost} {currency}", "&7This egg creates a bridge in", "&7its trail after being thrown."));
        lbj.saveShopStuff("invContents.utility.invContents.back", "&aTorna indietro", Collections.singletonList(""));
        yml.addDefault("meaning.iron", "Ferro");
        yml.addDefault("meaning.gold", "Oro");
        yml.addDefault("meaning.emerald", "Smeraldo");
        yml.addDefault("meaning.diamond", "Diamante");
        yml.addDefault("meaning.vault", "$");
        yml.addDefault(Messages.PLURAL_PATH + ".iron", "Ferro");
        yml.addDefault(Messages.PLURAL_PATH + ".gold", "Oro");
        yml.addDefault(Messages.PLURAL_PATH + ".emerald", "Smeraldi");
        yml.addDefault(Messages.PLURAL_PATH + ".diamond", "Diamanti");
        yml.addDefault(Messages.PLURAL_PATH + ".vault", "Monete");
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "{prefix}&cNon hai abbastanza {currency}! Te ne occorre {amount} in più!");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "{prefix}&aHai comprato &6{item}");
        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Utilizzo: /" + mainCmd + " join §o<arena/gruppo>");
        yml.addDefault(Messages.COMMAND_NOT_ALLOWED_IN_GAME, "{prefix}&cNon puoi eseguire questa azione.");
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
        yml.addDefault("upgrades.Default.miningFatigue.tier1.lore", Arrays.asList("&7Il prossimo nemico che entrerà", "&7nella tua base riceverà Mining Fatigue", "&7for 10 seconds!", "", "&7Costo:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.healPool.tier1.name", "&eRigenerazione");
        yml.addDefault("upgrades.Default.healPool.tier1.lore", Arrays.asList("&7Crea un campo di rigenerazione", "&7intorno alla tua base!", "", "&7Costo:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault(Messages.ARENA_ENEMY_BASE_ENTER_ACTION, "&cTRAPPOLA ATTIVATA!");
        yml.addDefault(Messages.ARENA_ENEMY_BASE_ENTER_CHAT, "{prefix}&cTRAPPOLA ATTIVATA!");
        yml.addDefault(Messages.ARENA_ENEMY_BASE_ENTER_SUBTITLE, "&fLa tua trappola è stata attivata!");
        yml.addDefault(Messages.ARENA_ENEMY_BASE_ENTER_TITLE, "&cTRAPPOLA ATTIVATA!");
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a{player} ha comprato &6{upgradeName}");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "{TeamColor}&l{TeamName} &r{TeamColor}Silverfish");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "{TeamColor}{despawn}s &8[ {TeamColor}{health}&8]");
        yml.addDefault(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH, "▮ ");
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
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "Statistiche di {player}");

        /* save default items messages for stats gui */
        lbj.addDefaultStatsMsg(yml, "wins", "&6Vittorie", "&f{wins}");
        lbj.addDefaultStatsMsg(yml, "losses", "&6Sconfitte", "&f{losses}");
        lbj.addDefaultStatsMsg(yml, "kills", "&6Uccisioni", "&f{kills}");
        lbj.addDefaultStatsMsg(yml, "deaths", "&6Decessi", "&f{deaths}");
        lbj.addDefaultStatsMsg(yml, "finalKills", "&6Uccisioni finali", "&f{finalKills}");
        lbj.addDefaultStatsMsg(yml, "finalDeaths", "&6Decessi finali", "&f{finalDeaths}");
        lbj.addDefaultStatsMsg(yml, "bedsDestroyed", "&6Letti distrutti", "&f{bedsDestroyed}");
        lbj.addDefaultStatsMsg(yml, "firstPlay", "&6Prima partita", "&f{firstPlay}");
        lbj.addDefaultStatsMsg(yml, "lastPlay", "&6Ultima partita", "&f{lastPlay}");
        lbj.addDefaultStatsMsg(yml, "gamesPlayed", "&6Partite giocate", "&f{gamesPlayed}");
        yml.addDefault(Messages.FORMATTING_STATS_DATE_FORMAT, "yyyy/MM/dd HH:mm");

        yml.addDefault(Messages.MEANING_NEVER, "Never");
        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&6&lBedWars", "&7{date}", "&fNome: &a{player}", "", "&fVittorie: &a{wins}", "&fSconfitte: &a{losses}", "&fUccisioni: &a{kills}", "&fDecessi: &a{deaths}"
                , "&fUccisioni finali: &a{fKills}", "&fLetti distrutti: &a{beds}", "", "&fOnline: &a{on}", "&eandrei1058.com"));

        /* party commands */
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
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "{prefix}&cIl comando non è stato trovato o non hai abbastanza permessi!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "§c▪ §7Non sei in gioco!");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "§c▪ §7Conto alla rovescia diminuito!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "{prefix}&7Non puoi forzare l'inizio del gioco.\n§7Prendi in considerazione il fatto di donare per avere più cose.");
        yml.addDefault(Messages.ARENA_JOIN_SPECTATOR_MSG, "{prefix}§6Stai spectando l'arena §9{arena}§6.\n{prefix}§ePuoi uscire dall'arena in qualsiasi momento utilizzando §c/leave§e.");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cNon puoi aprire questa cesta, perchè il team è stato eliminato.");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8Teletrasporto");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "{prefix}{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7Vita: &f{health}%", "&7Cibo: &f{food}", "", "&7Click-Sinistro per guardare"));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lRitorna alla lobby");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Click-destro per ritornare alla lobby!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&aGuardando &7{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&cSHIFT per uscire");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eUscita Dalla Modalità Spettatore");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "{prefix}&Hai già comprato questo!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &eè offline!");
        yml.addDefault(Messages.ARENA_JOIN_SPECTATOR_DENIED_MSG, "&cGli spettatori non sono ammessi in questa arena!");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "{prefix}§cIl proprietario del party è uscito e il party è stato sciolto!");

        /* Lobby Command Items */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH+".stats") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "stats"), "&eStatistiche");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Arrays.asList("&fClick-destro per vedere", "le tue statistiche!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH+".arena-selector") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&eArena Selector");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Collections.singletonList("&fClick-destro to choose an arena!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH+".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&eTorna nella Lobby");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fClick-destro per lasciare BedWars!"));
        }
        /* Pre Game Command Items */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH+".stats") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&eStatistiche");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Arrays.asList("&fClick-destro per vedere", "le tue statistiche!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH+".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&eBack to Lobby");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fClick-destro to leave the arena!"));
        }
        /* Spectator Command Items */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH+".teleporter") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&eTeletrasporto");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "teleporter"), Collections.singletonList("&fClick-destro to spectate a player!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH+".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&eTorna nella Lobby");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fClick-destro per lasciare BedWars!"));
        }
        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cAspetta {seconds} scondi prima di usare ancora questo comando!");
        yml.addDefault(Messages.PLACEHOLDER_PLAYER_TEAM_TEAM, "{TeamColor}[{TeamName}]");
        yml.addDefault(Messages.PLACEHOLDER_PLAYER_TEAM_SHOUT, "&6[SHOUT]");
        yml.addDefault(Messages.PLACEHOLDER_PLAYER_TEAM_SPECTATOR, "&7[SPECTATOR]");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "{prefix}&cCi dispiace, ma attualmente non puoi entrare in quest'arena. Usa Click-destro per guardare!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "{prefix}&cCi dispiace, ma attualmente non puoi guardare questa parita. Usa Click-sinistro per giocare!");

        yml.addDefault(Messages.REJOIN_NO_ARENA, "{prefix}&cNon c'è un'arena su cui ritornare!");
        yml.addDefault(Messages.REJOIN_DENIED, "{prefix}&cNon puoi tornare più. Partita finita o letto distrutto.");
        yml.addDefault(Messages.REJOIN_ALLOWED, "{prefix}&eStai entrando sull'arena &a{arena}&e!");

    }
}
