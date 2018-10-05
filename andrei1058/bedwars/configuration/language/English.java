package com.andrei1058.bedwars.configuration.language;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.Messages;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;
import java.util.Collections;

import static com.andrei1058.bedwars.Main.mainCmd;

public class English {

    public English(Language lbj, YamlConfiguration yml) {
        yml.addDefault(Messages.PREFIX, "");
        yml.addDefault("name", "English");
        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + mainCmd + " stats", "&2▪ &7/" + mainCmd + " join &o<arena/group>", "&2▪ &7/" + mainCmd + " leave", "&2▪ &7/" + mainCmd + " lang", "&2▪ &7/" + mainCmd + " gui", "&2▪ &7/" + mainCmd + " start &3(vip)"));
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "{prefix}&cSorry, but you were kicked out because a donor joined the arena.\n&aPlease consider donating for more features. &7&o(click)");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_IS_FULL, "{prefix}&cThis arena is full!\n&aPlease consider donating for more features. &7&o(click)");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_IS_FULL_VIP_REQUIRED, "{prefix}&cWe apologise but this arena is full.\n&cWe know you're a donor but actually this arena is full of staff or/and donors.");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS, "{prefix}§cThere aren't enough players! Countdown stopped!");
        yml.addDefault(Messages.ARENA_LEAVE_PLAYER_LEAVE_MSG, "{prefix}&e{player} left the game!");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "{prefix}&eThe arena you were in is restarting.");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&cPlaying");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4Restarting");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&2Waiting §c{full}");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6Starting §c{full}");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_GROUP_OR_ARENA_NOT_FOUND, "{prefix}&cThere isn't any arena or arena group called: {name}");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NO_EMPTY_FOUND, "{prefix}&cThere isn't any arena available right now ;(");
        yml.addDefault(Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA, "{prefix}&cYou're not in arena!");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8Click to join");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l{name}");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7Status: {status}", "&7Players: &f{on}&7/&f{max}", "&7Type: &a{group}", "", "&eLeft-Click to join.", "&eRight-Click to spectate."));
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "{prefix} &2Available languages:");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7{iso} - &f{name}");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "{prefix}&7Usage: /lang &f&o<iso>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "{prefix}&cThis language doesn't exist!");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "{prefix}&aLanguage changed!");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "{prefix}&cYou can't change the language during the game.");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_PARTY_TOO_BIG, "{prefix}&cYour party is too big for joining this arena as a team :(");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NOT_PARTY_LEADER, "{prefix}&cOnly the leader can choose the arena.");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&eTier &c{tier}");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&lDiamond");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&lEmerald");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&eSpawns in &c{seconds} &eseconds");
        yml.addDefault(Messages.ARENA_JOIN_PLAYER_JOIN_MSG, "{prefix}&7{player} &ehas joined (&b{on}&e/&b{max}&e)!");
        yml.addDefault(Messages.ARENA_LEAVE_PLAYER_LEAVE_MSG, "{prefix}&7{player} &ehas quit!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN, "{prefix}&eThe game starts in &6{time} &eseconds!");
        yml.addDefault(Messages.FORMATTING_CHAT_LOBBY, "{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_WAITING, "{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SHOUT, "{vPrefix}&6[SHOUT] {team} &7{player}&f{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_TEAM, "{vPrefix}&f{level}{team}&7 {player}{vSuffix} {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SPECTATOR, "{vPrefix}&7[SPECTATOR] {player}{vSuffix}: {message}");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "",
                "&e&l    Protect your bed and destroy the enemy beds.",
                "&e&l      Upgrade yourself and your team by collecting",
                "&e&l   Iron, Gold, Emerald, and Diamond from generators",
                "&e&l             to access powerful upgrades.", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.MEANING_SHOUT, "shout");
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "{prefix}&cYou can't place blocks here!");
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList("&f&lBED WARS", "", "&fMap: &a{map}", "&fPlayer: &a{on}/{max}", "", "&fStarting in &a{time}s", "", "§fServer: &a{server}", "", "&e  andrei1058.com"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList("&f&lBED WARS", "", "&fMap: &a{map}", "&fPlayer: &a{on}/{max}", "", "&fWaiting...", "", "§fServer: &a{server}", "", "&e  andrei1058.com"));
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "{prefix}&cYou can only break blocks placed by a player!");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&aGO");
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent}", "&a{time}", "", "{TeamRedColor}&lR&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}&lB&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}&lG&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}&lY &f{TeamYellowName}&f: {TeamYellowStatus}", "",
                "&fKills: &a{kills}", "&fFinal Kills: &a{finalKills}", "&fBeds Broken: &a{beds}", "", "&ehttp://andrei1058.com"));
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
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fTracking: {team} &f- Distance: {distance}m");
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&lDefend your bed!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&cYou can't destroy your own bed!");
        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&lTEAM ELIMINATED > {TeamColor}{TeamName} Team &chas been eliminated!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&lBED DESTRUCTION > {TeamColor}{TeamName} Bed &7was deep fried by {PlayerColor}{PlayerName}&7!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&cBED DESTROYED!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&fYou will no longer respawn!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&f&lBED DESTRUCTION > &7Your bed was iced by {PlayerColor}{PlayerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "{PlayerColor}{PlayerName} &7fell into the void.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "{PlayerColor}{PlayerName} &7fell into the void. &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "{PlayerColor}{PlayerName} &7was knocked into the void by {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, "{PlayerColor}{PlayerName} &7was knocked into the void by {KillerColor}{KillerName}&7. &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "{PlayerColor}{PlayerName} &7was hit off by a love bomb from {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7was hit off by a love bomb from {KillerColor}{KillerName}&7. &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerName}&7. &b&lFINAL KILL!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lYour bed was destroyed!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cYOU DIED!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eYou will respawn in &c{time} &eseconds!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "{prefix}&eYou will respawn in &c{time} &eseconds!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aRESPAWNED!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "{prefix}&cYou have been eliminated!");
        yml.addDefault(Messages.ARENA_GAME_OVER_PLAYER_TITLE, "&c&lGAME OVER!");
        yml.addDefault(Messages.ARENA_VICTORY_PLAYER_TITLE, "&6&lVICTORY!");
        yml.addDefault(Messages.ARENA_GAME_OVER_PLAYER_CHAT, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "", "{winnerFormat}", "", "",
                "&e                          &l1st Killer &7- {firstName} - {firstKills}",
                "&6                          &l2nd Killer &7- {secondName} - {secondKills}",
                "&c                          &l3rd Killer &7- {thirdName} - {thirdKills}", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")); //todo disponibile {firstDeaths} {firstBeds} {firstName} {firstKills}
        //yml.addDefault(gameOverReward, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
        //        "&f                                   &lReward Summary", "", "",
        //        "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.ARENA_TEAM_WON_CHAT, "{prefix}{TeamColor}{TeamName} &ahas won the game!");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 {TeamColor}{TeamName} &7- {members}");
        //yml.addDefault(tablistFormat, "{TeamColor}&l{TeamLetter}&r {TeamColor}{PlayerName} &e{PlayerHealth}");//{TeamColor}{TeamName}{TeamHealth}{PlayerName}{PlayerHealth}
        yml.addDefault(Messages.MEANING_NOBODY, "Nobody");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT, "{prefix}{generatorType} Generators &ehave been upgraded to Tier &c{tier}");
        yml.addDefault(Messages.UPGRADES_TEAM_NPC_NAME, "&e&lTEAM,&E&LUPGRADES");
        yml.addDefault(Messages.UPGRADES_SOLO_NPC_NAME, "&e&lSOLO,&E&LUPGRADES");
        yml.addDefault(Messages.SHOP_TEAM_NAME, "&e&lITEM SHOP");
        yml.addDefault(Messages.SHOP_SOLO_NAME, "&e&lITEM SHOP");
        yml.addDefault(Messages.SHOP_PATH + "name", "&7Item Shop");

        lbj.saveShopStuff("invContents.armor", "&aArmor", Arrays.asList("&7Available:", "&7▪ Chainmail Boots", "&7▪ Chainmail Leggings", "&7▪ Iron Boots", "&7▪ Iron Leggings", "&7▪ Diamond Boots", "&7▪ Diamond Leggings", "", "&eClick to browse!"));
        lbj.saveShopStuff("invContents.melee", "&aMelee", Arrays.asList("&7Available:", "&7▪ Stone Sword", "&7▪ Iron Sword", "&7▪ Diamond Sword", "&7▪ Stick (Knockback I)", "", "&eClick to browse!"));
        lbj.saveShopStuff("invContents.blocks", "&aBlocks", Arrays.asList("&7Available:", "&7▪ Wool", "&7▪ Hardened Clay", "&7▪ Blast-Proof Glass", "&7▪ End Stone", "&7▪ Ladder", "&7▪ Oak Wood Planks", "&7▪ Obsidian", "", "&eClick to browse!"));
        lbj.saveShopStuff("invContents.ranged", "&aRanged", Arrays.asList("&7Available:", "&7▪ Arrow", "&7▪ Bow", "&7▪ Bow (Power I)", "&7▪ Bow (Power I, Punch I)", "", "&eClick to browse!"));
        lbj.saveShopStuff("invContents.tools", "&aTools", Arrays.asList("&7Available:", "&7▪ Shears", "&7▪ Wooden Pickaxe (Effeciency I)", "&7▪ Wooden Axe (Effeciency I)", "", "&eClick to browse!"));
        lbj.saveShopStuff("invContents.potions", "&aPotions", Arrays.asList("&7Available:", "&7▪ Speed II Potion", "&7▪ Jump V Potion", "&7▪ Invisibility Potion", "", "&eClick to browse!"));
        lbj.saveShopStuff("invContents.utility", "&aUtility", Arrays.asList("&7Available:", "&7▪ Golden Apple", "&7▪ BedBug", "&7▪ Dream Defender", "&7▪ Fireball", "&7▪ TNT", "&7▪ Ender Pearl", "&7▪ Water Bucket", "&7▪ Bridge Egg", "", "&eClick to browse!"));
        //lbj.saveShopStuff("invContents.favourites", "&aFavourites", Arrays.asList("&7Click to view your", "&7favourite items. You can", "&7set a favourite item by", "&7shift clicking on any shop item!"));
        //lbj.saveShopStuff("invContents.recommended", "&aRecommended Items", Arrays.asList("&7Original", "&7Click to view some", "&7recommended items for early", "&7to late game!", "", "&eClick to browse!"));

        lbj.saveShopStuff("invContents.armor.invContents.chainmail", "&aPermanent Chainmail Armor", Arrays.asList("&8Items:", "&8▪ &7Permanent Chainmail Armor", "&8▪ &7Chainmail Leggings", "", "&8Cost: &f{cost} {currency}", "&8&oYou will not lose this on death!"));
        lbj.saveShopStuff("invContents.armor.invContents.iron", "&aPermanent Iron Armor", Arrays.asList("&8Items:", "&8▪ &7Permanent Iron Armor", "&8▪ &7Iron Leggings", "", "&8Cost: &6{cost} {currency}", "&8&oYou will not lose this on death!"));
        lbj.saveShopStuff("invContents.armor.invContents.diamond", "&aPermanent Diamond Armor", Arrays.asList("&8Items:", "&8▪ &7Permanent Diamond Armor", "&8▪ &7Diamond Leggings", "", "&8Cost: &2{cost} {currency}", "&8&oYou will not lose this on death!"));
        lbj.saveShopStuff("invContents.armor.invContents.back", "&aGo Back", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.melee.invContents.stone", "&aStone Sword", Arrays.asList("&8Items:", "&8▪ &7Stone Sword", "", "&8Cost: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.iron", "&aIron Sword", Arrays.asList("&8Items:", "&8▪ &7Iron Sword", "&8Cost: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.diamond", "&aDiamond Sword", Arrays.asList("&8Items:", "&8▪ &7Diamond Sword", "", "&8Cost: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.stick", "&aStick (Knockback I)", Arrays.asList("&8Items:", "&8▪ &7Stick (Knockback I)", "", "&8Cost: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.back", "&aGo Back", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.blocks.invContents.wool", "&aWool", Arrays.asList("&8Items:", "&8▪ &7Wool", "", "&8Cost: &f{cost} {currency}", "", "&eClick to purchase!"));
        lbj.saveShopStuff("invContents.blocks.invContents.clay", "&aHardened Clay", Arrays.asList("&8Items:", "&8▪ &7Hardened Clay", "", "&8Cost: &f{cost} {currency}", "", "&eClick to purchase!"));
        lbj.saveShopStuff("invContents.blocks.invContents.glass", "&aBlast-Proof Glass", Arrays.asList("&8Items:", "&8▪ &7Blast-Proof Glass", "", "&8Cost: &f{cost} {currency}", "", "&7Immune to explosions", "", "&eClick to purchase!"));
        lbj.saveShopStuff("invContents.blocks.invContents.stone", "&aEnd Stone", Arrays.asList("&8Items:", "&8▪ &7End Stone", "", "&8Cost: &f{cost} {currency}", "", "&eClick to purchase!"));
        lbj.saveShopStuff("invContents.blocks.invContents.ladder", "&aLadder", Arrays.asList("&8Items:", "&8▪ &7Ladder", "", "&8Cost: &f{cost} {currency}", "", "&eClick to purchase!"));
        lbj.saveShopStuff("invContents.blocks.invContents.wood", "&aOak Wood Planks", Arrays.asList("&8Items:", "&8▪ &7Oak Wood Planks", "", "&8Cost: &6{cost} {currency}", "", "&eClick to purchase!"));
        lbj.saveShopStuff("invContents.blocks.invContents.obsidian", "&cObsidian", Arrays.asList("&8Items:", "&8▪ &7Obsidian", "", "&8Cost: &2{cost} {currency}", "", "&eClick to purchase!"));
        lbj.saveShopStuff("invContents.blocks.invContents.back", "&aGo Back", Arrays.asList(""));

        lbj.saveShopStuff("invContents.ranged.invContents.arrow", "&aArrow", Arrays.asList("&8Items:", "&8▪ &7Arrow", "", "&8Cost: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow", "&aBow", Arrays.asList("&8Items:", "&8▪ &7Bow", "", "&8Cost: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow2", "&aBow (Power I)", Arrays.asList("&8Items:", "&8▪ &7Bow (Power I)", "", "&8Cost: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow3", "&aBow (Power I, Punch I)", Arrays.asList("&8Items:", "&8▪ &7Bow (Power I, Punch I)", "", "&8Cost: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.back", "&aGo Back", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.tools.invContents.shears", "&aPermanent Shears", Arrays.asList("&8Items:", "&8▪ &7Permanent Shears", "", "&8Cost: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.pick", "&aWooden Pickaxe", Arrays.asList("&8Items:", "&8▪ &7Wooden Pickaxe", "", "&8Cost: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.axe", "&aWooden Axe", Arrays.asList("&8Items:", "&8▪ &7Wooden Axe", "", "&8Cost: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.back", "&aGo Back", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.potions.invContents.potion1", "&aSpeed II Potion", Arrays.asList("&8Items:", "&8▪ &7Speed II potion", "", "&8Cost: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.potion2", "&aJump V Potion", Arrays.asList("&8Items:", "&8▪ &7Jump V Potion", "", "&8Cost: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.potion3", "&aInvisibility Potion", Arrays.asList("&8Items:", "&8▪ &7Invisibility Potion", "", "&8Cost: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.back", "&aGo Back", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.utility.invContents.apple", "&aGolden Apple", Arrays.asList("&8Items:", "&8▪ &7Golden Apple", "", "&8Cost: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.utility.invContents.sball", "&aBedbug", Arrays.asList("&8Items:", "&8▪ &7Bedbug", "", "&8Cost: &f{cost} {currency}", "", "&7Moderately annoying. These", "&7little critters can be thrown to", "&7distract enemies"));
        lbj.saveShopStuff("invContents.utility.invContents.monsteregg", "&aDream Defender", Arrays.asList("&8Items:", "&8▪ &7Dream Defender", "", "&8Cost: &f{cost} {currency}", "", "&7Moderately motivated.", "&7Sometimes they help defend your", "&7base"));
        lbj.saveShopStuff("invContents.utility.invContents.fireball", "&aFireball", Arrays.asList("&8Items:", "&8▪ &7Fireball", "", "&8Cost: &f{cost} {currency}", "", "&7Right-click to launch!"));
        lbj.saveShopStuff("invContents.utility.invContents.tnt", "&aTNT", Arrays.asList("&8Items:", "&8▪ &7TNT", "", "&8Cost: &6{cost} {currency}", "", "&7Instantly ignites, appropriate", "&7to explode things!"));
        lbj.saveShopStuff("invContents.utility.invContents.enderpearl", "&aEnder Pearl", Arrays.asList("&8Items:", "", "&8Ender Pearl", "&8▪ &7Cost: &2{cost} {currency}", "", "&7Pretty useful for invading", "&7enemies bases."));
        lbj.saveShopStuff("invContents.utility.invContents.bucket", "&aWater Bucket", Arrays.asList("&8Items:", "&8▪ &7Water Bucket", "", "&8Cost: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.utility.invContents.eggBridge", "&aBridge Egg", Arrays.asList("&8Items:", "&8▪ &7Bridge Egg", "", "&8Cost: &2{cost} {currency}", "&7This egg creates a bridge in", "&7its trail after being thrown."));
        lbj.saveShopStuff("invContents.utility.invContents.back", "&aGo Back", Collections.singletonList(""));
        yml.addDefault("meaning.iron", "Iron");
        yml.addDefault("meaning.gold", "Gold");
        yml.addDefault("meaning.emerald", "Emerald");
        yml.addDefault("meaning.diamond", "Diamond");
        yml.addDefault("meaning.vault", "$");
        yml.addDefault(Messages.PLURAL_PATH + ".iron", "Iron");
        yml.addDefault(Messages.PLURAL_PATH + ".gold", "Gold");
        yml.addDefault(Messages.PLURAL_PATH + ".emerald", "Emeralds");
        yml.addDefault(Messages.PLURAL_PATH + ".diamond", "Diamonds");
        yml.addDefault(Messages.PLURAL_PATH + ".vault", "Money");
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "{prefix}&cYou don't have enough {currency}! Need {amount} more!");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "{prefix}&aYou purchased &6{item}");
        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Usage: /" + mainCmd + " join §o<arena/group>");
        yml.addDefault(Messages.COMMAND_NOT_ALLOWED_IN_GAME, "{prefix}&cYou can't do this during the game.");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "&aClick to purchase!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "&cYou don't have enough {currency}");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cLOCKED");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "&aUNLOCKED");
        yml.addDefault("upgrades.Default.generators.tier1.name", "&eIron Forge");
        yml.addDefault("upgrades.Default.generators.tier1.lore", Arrays.asList("&7Increases the spawn rate of Iron", "&7and Gold by 50%..", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.generators.tier2.name", "&eGolden Forge");
        yml.addDefault("upgrades.Default.generators.tier2.lore", Arrays.asList("&7Increases the spawn rate of Iron", "&7and Gold by 100%..", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.generators.tier3.name", "&eEmerald Forge");
        yml.addDefault("upgrades.Default.generators.tier3.lore", Arrays.asList("&7Activates the Emerald spawner in", "&7your team's Forge.", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.maniacMiner.tier1.name", "&eManiac Miner");
        yml.addDefault("upgrades.Default.maniacMiner.tier1.lore", Arrays.asList("&7All players on your team", "&7permanently gain Haste I", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.sharpSword.tier1.name", "&eSharpened Swords");
        yml.addDefault("upgrades.Default.sharpSword.tier1.lore", Arrays.asList("&7Your team gets Sharpness I on", "&7all swords!", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.reinforced.tier1.name", "&eReinforced Armor");
        yml.addDefault("upgrades.Default.reinforced.tier1.lore", Arrays.asList("&7Your team gets Protection I on", "&7all armor pieces!", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.trap.tier1.name", "&eIt's a trap!");
        yml.addDefault("upgrades.Default.trap.tier1.lore", Arrays.asList("&7The nex enemy to enter your", "&7base will receive Blindness and", "&7Slowness!", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.miningFatigue.tier1.name", "&eMiner Fatigue Trap");
        yml.addDefault("upgrades.Default.miningFatigue.tier1.lore", Arrays.asList("&7The nex enemy to enter your", "&7base will receive Mining Fatigue", "&7for 10 seconds!", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.healPool.tier1.name", "&eHeal Pool");
        yml.addDefault("upgrades.Default.healPool.tier1.lore", Arrays.asList("&7Creates a Regeneration field", "&7around your base!", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault(Messages.ARENA_ENEMY_BASE_ENTER_ACTION, "&cTRAP TRIGGERED!");
        yml.addDefault(Messages.ARENA_ENEMY_BASE_ENTER_CHAT, "{prefix}&cTRAP TRIGGERED!");
        yml.addDefault(Messages.ARENA_ENEMY_BASE_ENTER_SUBTITLE, "&fYour trap has been set off!");
        yml.addDefault(Messages.ARENA_ENEMY_BASE_ENTER_TITLE, "&cTRAP TRIGGERED!");
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a{player} purchased &6{upgradeName}");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "{TeamColor}&l{TeamName} &r{TeamColor}Silverfish");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "{TeamColor}{despawn}s &8[ {TeamColor}{health}&8]");
        yml.addDefault(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH, "▮ ");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR, "{PlayerColor}{PlayerName} &7died.");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL, "{PlayerColor}{PlayerName} &7died. &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_REGULAR, "{PlayerColor}{PlayerName} &7was shoot by {KillerColor}{KillerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_FINAL_KILL, "{PlayerColor}{PlayerName} &7was shoot by {KillerColor}{KillerName}&7! &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_REGULAR, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerTeamName}'s &7BedBug!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_FINAL_KILL, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerTeamName}'s &7BedBug! &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_REGULAR, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerTeamName}'s &7Iron Golem!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerTeamName}'s &7Iron Golem! &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_DIAMOND, "{prefix}&b+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_EMERALD, "{prefix}&a+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_IRON, "{prefix}&f+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_GOLD, "{prefix}&6+{amount} {meaning}");

        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR, "{PlayerColor}{PlayerName} &7was hit off by a bomb.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7was hit off by a bomb. &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "{player} Stats");

        /* save default items messages for stats gui */
        lbj.addDefaultStatsMsg(yml, "wins", "&6Wins", "&f{wins}");
        lbj.addDefaultStatsMsg(yml, "losses", "&6Losses", "&f{losses}");
        lbj.addDefaultStatsMsg(yml, "kills", "&6Kills", "&f{kills}");
        lbj.addDefaultStatsMsg(yml, "deaths", "&6Deaths", "&f{deaths}");
        lbj.addDefaultStatsMsg(yml, "finalKills", "&6Final Kills", "&f{finalKills}");
        lbj.addDefaultStatsMsg(yml, "finalDeaths", "&6Final Deaths", "&f{finalDeaths}");
        lbj.addDefaultStatsMsg(yml, "bedsDestroyed", "&6Beds Destroyed", "&f{bedsDestroyed}");
        lbj.addDefaultStatsMsg(yml, "firstPlay", "&6First Play", "&f{firstPlay}");
        lbj.addDefaultStatsMsg(yml, "lastPlay", "&6Last Play", "&f{lastPlay}");
        lbj.addDefaultStatsMsg(yml, "gamesPlayed", "&6Games Played", "&f{gamesPlayed}");
        yml.addDefault(Messages.FORMATTING_STATS_DATE_FORMAT, "yyyy/MM/dd HH:mm");

        yml.addDefault(Messages.MEANING_NEVER, "Never");
        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&6&lBedWars", "&7{date}", "&fName: &a{player}", "", "&fWins: &a{wins}", "&fLosses: &a{losses}", "&fKills: &a{kills}", "&fDeaths: &a{deaths}"
                , "&fFinal Kills: &a{fKills}", "&fBeds Destroyed: &a{beds}", "", "&fOnline: &a{on}", "&eandrei1058.com"));

        /* party commands */
        yml.addDefault(Messages.COMMAND_PARTY_HELP, Arrays.asList("&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&aParty Commands:", "&e/party help &7- &bPrints this help message", "&e/party invite <player> &7- &bInvites the player to your party",
                "&e/party leave &7- &bLeaves the current party",
                "&e/party remove <player> &7- &bRemove the player from the party",
                "&e/party accept <player> &7- &bAccept a party invite", "&e/party disband &7- &bDisbands the party"));
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_USAGE, "{prefix}&eUsage: &7/party invite <player>");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &eis not online!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT, "{prefix}&eInvite sent to &7{player}&6.");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG, "{prefix}&b{player} &ehas invited you to a party! &o&7(Click to accept)");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF, "{prefix}&cYou cannot invite yourself!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE, "{prefix}&cThere's no party requests to accept");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY, "{prefix}&eYou're already in a party!");
        yml.addDefault(Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS, "{prefix}&cOnly the party owner can do this!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_USAGE, "{prefix}&eUsage: &7/party accept <player>");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_SUCCESS, "{prefix}&7{player} &ehas joined the party!");
        yml.addDefault(Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY, "{prefix}&cYou're not in a party!");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND, "{prefix}&cYou can't leave your own party!\n&eTry using: &b/party disband");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_SUCCESS, "{prefix}&7{player} &ehas leaved the party!");
        yml.addDefault(Messages.COMMAND_PARTY_DISBAND_SUCCESS, "{prefix}&eParty disbanded!");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_USAGE, "{prefix}&7Usage: &e/party remove <player>");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_SUCCESS, "{prefix}&7{player} &ewas removed from the party,");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER, "{prefix}&7{player} &eis not in your party!");
        yml.addDefault(Messages.NEXT_EVENT_BEDS_DESTROY, "&cBeds Destruction");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_II, "&fDiamond II");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_III, "&fDiamond III");
        yml.addDefault(Messages.NEXT_EVENT_DRAGON_SPAWN, "&fSudden Death");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_II, "&fEmerald II");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_III, "&fEmerald III");
        yml.addDefault(Messages.NEXT_EVENT_GAME_END, "&4Game End");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED, "&cBED DESTROYED!");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED, "&fAll beds have been destroyed!");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED, "&c&lAll beds have been destroyed!");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH, "&cSudden Death");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH, "");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH, "&cSUDDEN DEATH: &6&b{TeamDragons} {TeamColor}{TeamName} Dragon!");
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "{prefix}&cCommand not found or you don't have permission!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "§c▪ §7You're not playing!");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "§c▪ §7Countdown shortened!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "{prefix}&7You can't forcestart the arena.\n§7Please consider donating for VIP features.");
        yml.addDefault(Messages.ARENA_JOIN_SPECTATOR_MSG, "{prefix}§6You are now spectating §9{arena}§6.\n{prefix}§eYou can leave the arena at any time doing §c/leave§e.");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cYou can't open this chest because this team was eliminated!");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8Teleporter");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "{prefix}{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7Health: &f{health}%", "&7Food: &f{food}", "", "&7Left-click to spectate"));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lReturn to lobby");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Right-click to leave to the lobby!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&aSpectating &7{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&cSNEAK to exit");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eExiting Spectator mode");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "{prefix}&cYou've already bought that!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &eis offline!");
        yml.addDefault(Messages.ARENA_JOIN_SPECTATOR_DENIED_MSG, "&cSpectators are not allowed in this arena!");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "{prefix}§cThe party owner has left and the party was disbanded!");

        /* Lobby Command Items */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".stats") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "stats"), "&eStats");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fRight-click to see your stats!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".arena-selector") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&eArena Selector");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Collections.singletonList("&fRight-click to choose an arena!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&eBack to Hub");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fRight-click to leave BedWars!"));
        }
        /* Pre Game Command Items */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".stats") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&eStats");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fRight-click to see your stats!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&eBack to Lobby");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fRight-click to leave the arena!"));
        }
        /* Spectator Command Items */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH + ".teleporter") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&eTeleporter");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "teleporter"), Collections.singletonList("&fRight-click to spectate a player!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH + ".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&eBack to Lobby");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fRight-click to leave the arena!"));
        }
        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cYou can't do that yet! Wait {seconds} more seconds!");
        yml.addDefault(Messages.PLACEHOLDER_PLAYER_TEAM_TEAM, "{TeamColor}[{TeamName}]");
        yml.addDefault(Messages.PLACEHOLDER_PLAYER_TEAM_SHOUT, "&6[SHOUT]");
        yml.addDefault(Messages.PLACEHOLDER_PLAYER_TEAM_SPECTATOR, "&7[SPECTATOR]");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "{prefix}&cSorry but you can't join this arena at this moment. Use Right-Click to spectate!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "{prefix}&cSorry but you can't spectate this arena at this moment. Use Left-Click to join!");

        yml.addDefault(Messages.REJOIN_NO_ARENA, "{prefix}&cThere is no arena to rejoin!");
        yml.addDefault(Messages.REJOIN_DENIED, "{prefix}&cYou can't rejoin the arena anymore. Game ended or bed destroyed.");
        yml.addDefault(Messages.REJOIN_ALLOWED, "{prefix}&eJoining arena &a{arena}&e!");
    }
}
