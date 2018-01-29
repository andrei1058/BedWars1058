package com.andrei1058.bedwars.configuration;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.andrei1058.bedwars.Main.lang;
import static com.andrei1058.bedwars.Main.mainCmd;
import static com.andrei1058.bedwars.Main.plugin;

public class Language {

    private YamlConfiguration yml;
    private File config;
    private String iso = "en", prefixColor = "", langName = "English";
    private static HashMap<Player, Language> langByPlayer = new HashMap<>();
    private static List<Language> languages = new ArrayList<>();

    //Start of language file string path
    public static String
            prefix = "prefix",
            cmdMain = "cmd.main",
            youreInGame = "player.alreadyPlaying",
            vipJoinedSlot = "player.kickedByVip",
            fullArena = "player.fullArena",
            vipNoJoin = "player.vipNoJoin",
            insufficientPlayers = "player.countdownStopped",
            playerLeft = "player.quit",
            restartKick = "arena.restartKick",
            statusWaiting = "status.waiting",
            statusStarting = "status.starting",
            statusPlaying = "status.playing",
            statusRestarting = "status.restarting",
            arenaOrGroupNotFound = "player.arenaOrGroupNotFound",
            noEmptyArena = "player.noEmptyArena",
            notInArena = "player.notInArena",
            arenaGuiInvName = "arena.guiInv.Name",
            arenaGuiItemName = "arena.guiItem.name",
            arenaGuiItemLore = "arena.guiItem.lore",
            aGuiArenaName = "arena.guiArena.name",
            aGuiArenaLore = "arena.guiArena.lore",
            langListHeader = "lang.listHeader",
            langListFormat = "lang.listFormat",
            langCmdUsage = "player.langUsage",
            langNotExist = "player.langNotExist",
            langSet = "player.langSet",
            cantLangPlaying = "lang.cantChange",
            partyTooBig = "party.tooMuch",
            leaderChoose = "party.leaderChoose",
            generatorTier = "generator.tier",
    //generatorTier2 = "generator.tier2",
    //generatorTier3 = "generator.tier3",
            generatorDiamond = "generator.diamond",
            generatorEmerald = "generator.emerald",
            generatorTimer = "generator.timer",
            playerJoin = "arena.join",
            playerLeave = "arena.leave",
            arenaStartsIn = "arena.startCountdown",
            chatLobbyFormat = "chat.lobby",
            chatWaitingFormat = "chat.waiting",
            chatGlobalFormat = "chat.global",
            chatTeamFormat = "chat.team",
            chatSpectatorFormat = "chat.spectator",
            meaningShout = "meaning.shout",
            gameTutorialStrings = "arena.tutorial",
            leaveItemName = "arena.leaveItm.name",
            leaveItemLore = "arena.leaveItm.lore",
            scoreboardDefaultWaiting = "scoreboard.defaultWaiting",
            scoreboardDefaultStarting = "scoreboard.defaultStarting",
            scoreboardDefaultPlaying = "scoreboard.defaultPlaying",
            cantPlaceHere = "player.cantPlace",
            cantBreak = "player.cantBreak",
            titleStart = "arena.startTitle",
            dateFormat = "scoreboardFormat.date",
            teamEliminatedFormat = "scoreboardFormat.teamEliminated",
            bedDestroyedFormat = "scoreboardFormat.bedDestroyed",
            teamAliveFormat = "scoreboardFormat.teamAlive",
            diamondGeneratorName = "scoreboardFormat.diamondName",
            emeraldGeneratorName = "scoreboardFormat.emeraldName",
            generatorTimerFormat = "scoreboardFormat.generatorTimer",
            youScoreboardFormat = "scoreboardFormat.you",
            actionBarTracking = "action.tracking",
            bedHologram = "arena.bedHologram",
            bedHologramDestroyed = "arena.bedHologramDestroyed",
            cantDestroyOwnBed = "player.destroyOwnBed",
            teamEliminatedChat = "arena.teamEliminatedChat",
            teamBedDestroy = "arena.bedDestructionGlobal",
            bedDestroyedTitle = "arena.bedDestructionTitle",
            bedDestroyedSub = "arena.bedDestructionSub",
            teamBedDestroyTeam = "arena.bedDestructionTeam",
            playerFallInVoid = "arena.playerDie.void",
            playerFallInVoidFinalKill = "arena.playerDie.voidFinalKill",
            playerKnockedInVoid = "arena.playerDie.knockedInVoid",
            playerKnockedInVoidFinalKill = "arena.playerDie.knockedInVoidFinalKill",
            playerExplodeByBomb = "arena.playerDie.bomb",
            playerExplodeByBombFinalKill = "arena.playerDie.bombFinalKill",
            playerDieAttack = "arena.playerDie.attack",
            playerDieAttackFinalKill = "arena.playerDie.attackFinalKill",
            youDiedTitle = "arena.respawnTitle",
            youDiedSubTitle = "arena.respawnSub",
            respawnChat = "arena.respawnChat",
            respawnedTitle = "arena.respawnedTitle",
            youReEliminated = "arena.playerEliminated",
            gameOverTitle = "arena.gameOverTitle",//
            victoryTitle = "arena.victoryTitle",//
            gameEndStats = "arena.gameOverMsg",//
        //gameOverReward = "arena.gameOverReward",//todo cand o sa fie support pt reward si etc
            winnerFormatTeam = "arena.winFormat.team",//
            winnerFormatSolo = "arena.winFormat.solo",//
            teamWonChat = "arena.teamWonChat",//
            nobody = "meaning.nobody",
            tablistFormat = "format.tablist",
            tier1Format = "format.tier1",
            tier2Format = "format.tier2",
            tier3Format = "format.tier3",
            generatorUpgradeMsg = "arena.generatorUpgrade",
            teamUpgradesName = "npc.teamUpgradesName",
            soloUpgradesName = "npc.soloUpgradesName",
            shopTeamName = "npc.shopTeamName",
            shopSoloName = "npc.shopSoloName",
            shopPath = "shop.",
            insufficientMoney = "player.insuffMoney",
            youPurchased = "player.newPurchase",
            joinCmdUsage = "cmd.joinUsage",
            notAllowed = "player.permDenied",
            loreFooterInsuff = "upgrades.loreFooter.insuffMoney",
            loreFooterClick = "upgrades.loreFooter.clickBuy",
            loreFooterUnlocked = "upgrades.loreFooter.unlocked",
            loreFooterLocked = "upgrades.loreFooter.locked",
            pluralPath = "plural";
    //End of language file strings

    public Language(String iso) {
        this.iso = iso;
        File d = new File("plugins/" + plugin.getDescription().getName() + "/Languages");
        if (!d.exists()) {
            d.mkdir();
        }
        config = new File(d.toPath() + "/messages_" + iso + ".yml");
        if (!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            plugin.getLogger().info("Creating " + d.toPath() + "/messages_" + iso + ".yml");
        }
        yml = YamlConfiguration.loadConfiguration(config);
        languages.add(this);
    }

    public static void setupLang(Language lbj) {
        YamlConfiguration yml = lbj.yml;
        yml.options().copyDefaults(true);
        yml.addDefault(prefix, "");
        switch (lbj.iso) {
            default:
                yml.addDefault("name", "English");
                yml.addDefault(cmdMain, Arrays.asList("", "&2▪ &7/" + mainCmd + " stats", "&2▪ &7/" + mainCmd + " join &o<arena/group>", "&2▪ &7/" + mainCmd + " leave", "&2▪ &7/" + mainCmd + " lang", "&2▪ &7/" + mainCmd + " gui"));
                yml.addDefault(youreInGame, "{prefix}&cYou're already playing!");
                yml.addDefault(vipJoinedSlot, "{prefix}&cSorry, but you were kicked out because a donor joined the arena.\n&aPlease consider donating for more features. &7&o(click)");
                yml.addDefault(fullArena, "{prefix}&cThis arena is full!\n&aPlease consider donating for more features. &7&o(click)");
                yml.addDefault(vipNoJoin, "{prefix}&cWe apologise but this arena is full.\n&cWe know you're a donor but actually this arena is full of staff or/and donors.");
                yml.addDefault(insufficientPlayers, "{prefix}§cThere aren't enough players! Countdown stopped!");
                yml.addDefault(playerLeft, "{prefix}&e{player} left the game!");
                yml.addDefault(restartKick, "{prefix}&eThe arena you were in is restarting.");
                yml.addDefault(statusPlaying, "&cPlaying");
                yml.addDefault(statusRestarting, "&4Restarting");
                yml.addDefault(statusWaiting, "&2Waiting");
                yml.addDefault(statusStarting, "&6Starting");
                yml.addDefault(arenaOrGroupNotFound, "{prefix}&cThere isn't any arena or arena group called: {name}");
                yml.addDefault(noEmptyArena, "{prefix}&cThere isn't any arena available right now ;(");
                yml.addDefault(notInArena, "{prefix}&cYou're not in arena!");
                yml.addDefault(arenaGuiInvName, "&aAvailable arenas");
                yml.addDefault(aGuiArenaName, "&7➤ {name}");
                yml.addDefault(aGuiArenaLore, Arrays.asList("", "&7Status: {status}", "&7Players: &f{on}&7/&f{max}", "&7Type: &a{group}"));
                yml.addDefault(arenaGuiItemName, "&aArenas");
                yml.addDefault(arenaGuiItemLore, Arrays.asList("&7Choose an arena!"));
                yml.addDefault(langListHeader, "{prefix} &2Available languages:");
                yml.addDefault(langListFormat, "&a▪  &7{iso} - &f{name}");
                yml.addDefault(langCmdUsage, "{prefix}&7Usage: /lang &f&o<iso>");
                yml.addDefault(langNotExist, "{prefix}&cThis language doesn't exist!");
                yml.addDefault(langSet, "{prefix}&aLanguage changed!");
                yml.addDefault(cantLangPlaying, "{prefix}&cYou can't change the language during the game.");
                yml.addDefault(partyTooBig, "{prefix}&cYour party is too big for joining this arena as a team :(");
                yml.addDefault(leaderChoose, "{prefix}&cOnly the leader can choose the arena.");
                yml.addDefault(generatorTier, "&eTier &c{tier}");
                //yml.addDefault(generatorTier2, "&eTier &cII");
                //yml.addDefault(generatorTier3, "&eTier &cIII");
                yml.addDefault(generatorDiamond, "&b&lDiamond");
                yml.addDefault(generatorEmerald, "&a&lEmerald");
                yml.addDefault(generatorTimer, "&eSpawns in &c{seconds} &eseconds");
                yml.addDefault(playerJoin, "{prefix}&7{player} &ehas joined (&b{on}&e/&b{max}&e)!");
                yml.addDefault(playerLeave, "{prefix}&7{player} &ehas quit!");
                yml.addDefault(arenaStartsIn, "{prefix}&eThe game starts in &6{time} &eseconds!");
                yml.addDefault(chatLobbyFormat, "{vPrefix}&7{player}{vSuffix}: {message}");
                yml.addDefault(chatWaitingFormat, "{vPrefix}&7{player}{vSuffix}: {message}");
                yml.addDefault(chatGlobalFormat, "{vPrefix}&6[SHOUT] {team} &7{player}&f{vSuffix}: {message}");
                yml.addDefault(chatTeamFormat, "{vPrefix}&f{level}{team}&7 {player}{vSuffix} {message}");
                yml.addDefault(chatSpectatorFormat, "{vPrefix}&7[SPECTATOR] {player}{vSuffix}: {message}");
                yml.addDefault(gameTutorialStrings, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                        "&f                                   &lBedWars", "",
                        "&e&l    Protect your bed and destroy the enemy beds.",
                        "&e&l      Upgrade yourself and your team by collecting",
                        "&e&l   Iron, Gold, Emerald, and Diamond from generators",
                        "&e&l             to access powerful upgrades.", "",
                        "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
                yml.addDefault(meaningShout, "shout");
                yml.addDefault(leaveItemName, "&c&lReturn to lobby");
                yml.addDefault(leaveItemLore, Arrays.asList("&7Right-click to leave to the lobby!"));
                yml.addDefault(cantPlaceHere, "{prefix}&cYou can't place blocks here!");
                yml.addDefault(scoreboardDefaultStarting, Arrays.asList("&f&lBED WARS", "", "&fMap: &a{map}", "&fPlayer: &a{on}/{max}", "", "&fStarting in &a{time}s", "", "§fServer: &a{server}", "", "&e  andrei1058.com"));
                yml.addDefault(scoreboardDefaultWaiting, Arrays.asList("&f&lBED WARS", "", "&fMap: &a{map}", "&fPlayer: &a{on}/{max}", "", "&fWaiting...", "", "§fServer: &a{server}", "", "&e  andrei1058.com"));
                yml.addDefault(cantBreak, "{prefix}&cYou can only break blocks placed by a player!");
                yml.addDefault(titleStart, "&aGO");
                yml.addDefault(scoreboardDefaultPlaying, Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{generatorName}", "&a{generatorTimer}", "", "{TeamRedColor}&lR&f {TeamRedName}&f: {TeamRedStatus}",
                        "{TeamBlueColor}&lB&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}&lG&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}&lY &f{TeamYellowName}&f: {TeamYellowStatus}", "",
                        "&fKills: &a{kills}", "&fFinal Kills: &a{finalKills}", "&fBeds Broken: &a{beds}", "", "&ehttp://andrei1058.com"));
                yml.addDefault("scoreboard.SoloPlaying", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{generatorName}", "&a{generatorTimer}", "", "{TeamRedColor}&lR&f {TeamRedName}&f: {TeamRedStatus}",
                        "{TeamBlueColor}&lB&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}&lG&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}&lY &f{TeamYellowName}&f: {TeamYellowStatus}",
                        "{TeamAquaColor}&lA&f {TeamAquaName}&f: {TeamAquaStatus}", "{TeamWhiteColor}&lW&f {TeamWhiteName}&f: {TeamWhiteStatus}", "{TeamPinkColor}&lP&f {TeamPinkName}&f: {TeamPinkStatus}", "{TeamGrayColor}&lG&f {TeamGrayName}&f: {TeamGrayStatus}", "", "&ehttp://andrei1058.com"));
                yml.addDefault("scoreboard.DoublesPlaying", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{generatorName}", "&a{generatorTimer}", "", "{TeamRedColor}&lR&f {TeamRedName}&f: {TeamRedStatus}",
                        "{TeamBlueColor}&lB&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}&lG&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}&lY &f{TeamYellowName}&f: {TeamYellowStatus}",
                        "{TeamAquaColor}&lA&f {TeamAquaName}&f: {TeamAquaStatus}", "{TeamWhiteColor}&lW&f {TeamWhiteName}&f: {TeamWhiteStatus}", "{TeamPinkColor}&lP&f {TeamPinkName}&f: {TeamPinkStatus}", "{TeamGrayColor}&lG&f {TeamGrayName}&f: {TeamGrayStatus}", "", "&ehttp://andrei1058.com"));
                yml.addDefault(dateFormat, "dd/MM/yy");
                yml.addDefault(teamEliminatedFormat, "&c&l✘");
                yml.addDefault(bedDestroyedFormat, "&a{remainingPlayers}");
                yml.addDefault(teamAliveFormat, "&a&l✓");
                yml.addDefault(diamondGeneratorName, "Diamond Upgrade:");
                yml.addDefault(generatorTimerFormat, "mm:ss");
                yml.addDefault(emeraldGeneratorName, "Emerald Upgrade:");
                yml.addDefault(youScoreboardFormat, "&7 (You)");
                yml.addDefault(actionBarTracking, "&fTracking: {team} &f- Distance: {distance}m");
                yml.addDefault(bedHologram, "&c&lDefend your bed!");
                yml.addDefault(cantDestroyOwnBed, "&cYou can't destroy your own bed!");
                yml.addDefault(teamEliminatedChat, "\n&f&lTEAM ELIMINATED > {TeamColor}{TeamName} Team &chas been eliminated!\n");
                yml.addDefault(teamBedDestroy, "\n&f&lBED DESTRUCTION > {TeamColor}{TeamName} Bed &7was deep fried by {PlayerColor}{PlayerName}&7!\n");
                yml.addDefault(bedDestroyedTitle, "&cBED DESTROYED!");
                yml.addDefault(bedDestroyedSub, "&fYou will no longer respawn!");
                yml.addDefault(teamBedDestroyTeam, "&f&lBED DESTRUCTION > &7Your bed was iced by {PlayerColor}{PlayerName}&7!");
                yml.addDefault(playerFallInVoid, "{PlayerColor}{PlayerName} &7fell into the void.");
                yml.addDefault(playerFallInVoidFinalKill, "{PlayerColor}{PlayerName} &7fell into the void. &b&lFINAL KILL!");
                yml.addDefault(playerKnockedInVoid, "{PlayerColor}{PlayerName} &7was knocked into the void by {KillerColor}{KillerName}&7.");
                yml.addDefault(playerKnockedInVoidFinalKill, "{PlayerColor}{PlayerName} &7was knocked into the void by {KillerColor}{KillerName}&7. &b&lFINAL KILL!");
                yml.addDefault(playerExplodeByBomb, "{PlayerColor}{PlayerName} &7was hit off by a love bomb from {KillerColor}{KillerName}&7.");
                yml.addDefault(playerExplodeByBombFinalKill, "{PlayerColor}{PlayerName} &7was hit off by a love bomb from {KillerColor}{KillerName}&7. &b&lFINAL KILL!");
                yml.addDefault(playerDieAttack, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerName}&7.");
                yml.addDefault(playerDieAttackFinalKill, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerName}&7. &b&lFINAL KILL!");
                yml.addDefault(bedHologramDestroyed, "&c&lYour bed was destroyed!");
                yml.addDefault(youDiedTitle, "&cYOU DIED!");
                yml.addDefault(youDiedSubTitle, "&eYou will respawn in &c{time} &eseconds!");
                yml.addDefault(respawnChat, "{prefix}&eYou will respawn in &c{time} &eseconds!");
                yml.addDefault(respawnedTitle, "&aRESPAWNED!");
                yml.addDefault(youReEliminated, "{prefix}&cYou have been eliminated!");
                yml.addDefault(gameOverTitle, "&c&lGAME OVER!");
                yml.addDefault(victoryTitle, "&6&lVICTORY!");
                yml.addDefault(gameEndStats, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                        "&f                                   &lBedWars", "", "{winnerFormat}", "", "",
                        "&e                          &l1st Killer &7- {firstName} - {firstKills}",
                        "&6                          &l2nd Killer &7- {secondName} - {secondKills}",
                        "&c                          &l3rd Killer &7- {thirdName} - {thirdKills}", "",
                        "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")); //todo disponibile {firstDeaths} {firstBeds} {firstName} {firstKills}
                //yml.addDefault(gameOverReward, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                //        "&f                                   &lReward Summary", "", "",
                //        "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
                yml.addDefault(teamWonChat, "{prefix}{TeamColor}{TeamName} &ahas won the game!");
                yml.addDefault(winnerFormatTeam, "      {TeamColor}{TeamName} &7- {members}");
                yml.addDefault(winnerFormatSolo, "                 {TeamColor}{TeamName} &7- {members}");
                yml.addDefault(tablistFormat, "{TeamColor}&l{TeamLetter}&r {TeamColor}{PlayerName} &e{PlayerHealth}");//{TeamColor}{TeamName}{TeamHealth}{PlayerName}{PlayerHealth}
                yml.addDefault(nobody, "Nobody");
                yml.addDefault(tier1Format, "I");
                yml.addDefault(tier2Format, "II");
                yml.addDefault(tier3Format, "III");
                yml.addDefault(generatorUpgradeMsg, "{prefix}{generatorType} Generators &ehave been upgraded to Tier &c{tier}");
                yml.addDefault(teamUpgradesName, "&e&lTEAM,&E&LUPGRADES");
                yml.addDefault(soloUpgradesName, "&e&lSOLO,&E&LUPGRADES");
                yml.addDefault(shopTeamName, "&e&lITEM SHOP");
                yml.addDefault(shopSoloName, "&e&lITEM SHOP");
                yml.addDefault(shopPath+"name", "&7Item Shop");

                lbj.saveShopStuff("invContents.armor", "&aArmor", Arrays.asList("&7Available:", "&7▪ Chainmail Boots", "&7▪ Chainmail Leggings", "&7▪ Iron Boots", "&7▪ Iron Leggings", "&7▪ Diamond Boots", "&7▪ Diamond Leggings", "", "&eClick to browse!"));
                lbj.saveShopStuff("invContents.melee", "&aMelee", Arrays.asList("&7Available:", "&7▪ Stone Sword", "&7▪ Iron Sword", "&7▪ Diamond Sword", "&7▪ Stick (Knockback I)", "", "&eClick to browse!"));
                lbj.saveShopStuff("invContents.blocks", "&aBlocks", Arrays.asList("&7Available:", "&7▪ Wool", "&7▪ Hardened Clay", "&7▪ Blast-Proof Glass", "&7▪ End Stone", "&7▪ Ladder", "&7▪ Oak Wood Planks", "&7▪ Obsidian","", "&eClick to browse!"));
                lbj.saveShopStuff("invContents.ranged", "&aRanged", Arrays.asList("&7Available:", "&7▪ Arrow", "&7▪ Bow", "&7▪ Bow (Power I)", "&7▪ Bow (Power I, Punch I)", "", "&eClick to browse!"));
                lbj.saveShopStuff("invContents.tools", "&aTools", Arrays.asList("&7Available:", "&7▪ Shears", "&7▪ Wooden Pickaxe (Effeciency I)", "&7▪ Wooden Axe (Effeciency I)",  "", "&eClick to browse!"));
                lbj.saveShopStuff("invContents.potions", "&aPotions", Arrays.asList("&7Available:", "&7▪ Shears", "&7▪ Wooden Pickaxe (Effeciency I)", "&7▪ Wooden Axe (Effeciency I)",  "", "&eClick to browse!"));
                lbj.saveShopStuff("invContents.utility", "&aUtility", Arrays.asList("&7Available:", "&7▪ Golden Apple", "&7▪ BedBug", "&7▪ Dream Defender", "&7▪ Fireball", "&7▪ TNT", "&7▪ Ender Pearl", "&7▪ Water Bucket", "&7▪ Bridge Egg",  "", "&eClick to browse!"));
                lbj.saveShopStuff("invContents.favourites", "&aFavourites", Arrays.asList("&7Click to view your", "&7favourite items. You can", "&7set a favourite item by", "&7shift clicking on any shop item!"));
                lbj.saveShopStuff("invContents.recommended", "&aRecommended Items", Arrays.asList("&7Original", "&7Click to view some", "&7recommended items for early", "&7to late game!", "", "&eClick to browse!"));

                lbj.saveShopStuff("invContents.armor.invContents.chainmail", "&aPermanent Chainmail Armor", Arrays.asList("&8Items:", "&8▪ &7Permanent Chainmail Armor", "&8▪ &7Chainmail Leggings", "", "&7Cost: &f{cost} {currency}", "&8&oYou will not lose this on death!"));
                lbj.saveShopStuff("invContents.armor.invContents.iron", "&aPermanent Iron Armor", Arrays.asList("&8Items:", "&8▪ &7Permanent Iron Armor", "&8▪ &7Iron Leggings", "", "&6Cost: &f{cost} {currency}", "&8&oYou will not lose this on death!"));
                lbj.saveShopStuff("invContents.armor.invContents.diamond", "&aPermanent Diamond Armor", Arrays.asList("&8Items:", "&8▪ &7Permanent Diamond Armor", "&8▪ &7Diamond Leggings", "", "&6Cost: &f{cost} {currency}", "&8&oYou will not lose this on death!"));
                lbj.saveShopStuff("invContents.armor.invContents.back", "&aGo Back", Arrays.asList(""));

                lbj.saveShopStuff("invContents.melee.invContents.stone", "&aStone Sword", Arrays.asList("&8Items:", "&8▪ &7Stone Sword", "", "&7Cost: &f{cost} {currency}"));
                lbj.saveShopStuff("invContents.melee.invContents.iron", "&aIron Sword", Arrays.asList("&8Items:", "&8▪ &7Iron Sword", "&7Cost: &6{cost} {currency}"));
                lbj.saveShopStuff("invContents.melee.invContents.diamond", "&aDiamond Sword", Arrays.asList("&8Items:", "&8▪ &7Diamond Sword", "&8▪ &7Cost: &2{cost} {currency}"));
                lbj.saveShopStuff("invContents.melee.invContents.stick", "&aStick (Knockback I)", Arrays.asList("&8Items:", "&8▪ &7Stick (Knockback I)", "", "&7Cost: &f{cost} {currency}"));
                lbj.saveShopStuff("invContents.melee.invContents.back", "&aGo Back", Arrays.asList(""));

                lbj.saveShopStuff("invContents.blocks.invContents.wool", "&aWool", Arrays.asList("&8Items:", "&8▪ &7Wool", "", "&7Cost: &f{cost} {currency}", "", "&eClick to purchase!"));
                lbj.saveShopStuff("invContents.blocks.invContents.clay", "&aHardened Clay", Arrays.asList("&8Items:", "&8▪ &7Hardened Clay", "&7Cost: &f{cost} {currency}", "", "&eClick to purchase!"));
                lbj.saveShopStuff("invContents.blocks.invContents.glass", "&aBlast-Proof Glass", Arrays.asList("&8Items:", "&8▪ &7Blast-Proof Glass", "&8▪ &7Cost: &f{cost} {currency}", "", "&7Immune to explosions", "", "&eClick to purchase!"));
                lbj.saveShopStuff("invContents.blocks.invContents.stone", "&aEnd Stone", Arrays.asList("&8Items:", "&8▪ &7End Stone", "", "&7Cost: &f{cost} {currency}", "", "&eClick to purchase!"));
                lbj.saveShopStuff("invContents.blocks.invContents.ladder", "&aLadder", Arrays.asList("&8Items:", "&8▪ &7Ladder", "", "&7Cost: &f{cost} {currency}", "", "&eClick to purchase!"));
                lbj.saveShopStuff("invContents.blocks.invContents.wood", "&aOak Wood Planks", Arrays.asList("&8Items:", "&8▪ &7Oak Wood Planks", "", "&7Cost: &6{cost} {currency}", "", "&eClick to purchase!"));
                lbj.saveShopStuff("invContents.blocks.invContents.obsidian", "&cObsidian", Arrays.asList("&8Items:", "&8▪ &7Obsidian", "", "&7Cost: &2{cost} {currency}", "", "&eClick to purchase!"));
                lbj.saveShopStuff("invContents.blocks.invContents.back", "&aGo Back", Arrays.asList(""));

                lbj.saveShopStuff("invContents.ranged.invContents.arrow", "&aArrow", Arrays.asList("&8Items:", "&8▪ &7Arrow", "", "&7Cost: &6{cost} {currency}"));
                lbj.saveShopStuff("invContents.ranged.invContents.bow", "&aBow", Arrays.asList("&8Items:", "&8▪ &7Bow", "&7Cost: &6{cost} {currency}"));
                lbj.saveShopStuff("invContents.ranged.invContents.bow2", "&aBow (Power I)", Arrays.asList("&8Items:", "&8▪ &7Bow (Power I)", "&8▪ &7Cost: &6{cost} {currency}"));
                lbj.saveShopStuff("invContents.ranged.invContents.bow3", "&aBow (Power I, Punch I)", Arrays.asList("&8Items:", "&8▪ &7Bow (Power I, Punch I)", "", "&7Cost: &2{cost} {currency}"));
                lbj.saveShopStuff("invContents.ranged.invContents.back", "&aGo Back", Arrays.asList(""));

                lbj.saveShopStuff("invContents.tools.invContents.shears", "&aPermanent Shears", Arrays.asList("&8Items:", "&8▪ &7Permanent Shears", "", "&7Cost: &f{cost} {currency}"));
                lbj.saveShopStuff("invContents.tools.invContents.pick", "&aWooden Pickaxe", Arrays.asList("&8Items:", "&8▪ &7Bow", "&7Cost: &f{cost} {currency}"));
                lbj.saveShopStuff("invContents.tools.invContents.axe", "&aWooden Axe", Arrays.asList("&8Items:", "&8▪ &7Wooden Axe", "&8▪ &7Cost: &f{cost} {currency}"));
                lbj.saveShopStuff("invContents.tools.invContents.back", "&aGo Back", Arrays.asList(""));

                lbj.saveShopStuff("invContents.potions.invContents.potion1", "&aSpeed II Potion", Arrays.asList("&8Items:", "&8▪ &7Speed II potion", "", "&7Cost: &2{cost} {currency}"));
                lbj.saveShopStuff("invContents.potions.invContents.potion2", "&aJump V Potion", Arrays.asList("&8Items:", "&8▪ &7Jump V Potion", "&7Cost: &2{cost} {currency}"));
                lbj.saveShopStuff("invContents.potions.invContents.potion3", "&aInvisibility Potion", Arrays.asList("&8Items:", "&8▪ &7Invisibility Potion", "&8▪ &7Cost: &w{cost} {currency}"));
                lbj.saveShopStuff("invContents.potions.invContents.back", "&aGo Back", Arrays.asList(""));

                lbj.saveShopStuff("invContents.utility.invContents.apple", "&aGolden Apple", Arrays.asList("&8Items:", "&8▪ &7Golden Apple", "", "&7Cost: &6{cost} {currency}"));
                lbj.saveShopStuff("invContents.utility.invContents.sball", "&aBedbug", Arrays.asList("&8Items:", "&8▪ &7Bedbug", "&7Cost: &f{cost} {currency}", "", "&7Moderately annoying. These", "&7little critters can be thrown to", "&7distract enemies"));
                lbj.saveShopStuff("invContents.utility.invContents.monsteregg", "&aDream Defender", Arrays.asList("&8Items:", "&8▪ &7Dream Defender", "&8▪ &7Cost: &f{cost} {currency}", "", "&7Moderately motivated.", "&7Sometimes they help defend your", "&7base"));
                lbj.saveShopStuff("invContents.utility.invContents.fireball", "&aFireball", Arrays.asList("&8Items:", "&8▪ &7Fireball", "", "&7Cost: &f{cost} {currency}", "", "&7Right-click to launch!"));
                lbj.saveShopStuff("invContents.utility.invContents.tnt", "&aTNT", Arrays.asList("&8Items:", "&8▪ &7TNT", "&7Cost: &6{cost} {currency}", "", "&7Instantly ignites, appropriate", "&7to explode things!"));
                lbj.saveShopStuff("invContents.utility.invContents.enderpearl", "&aEnder Pearl", Arrays.asList("&8Items:", "&8▪ &7Ender Pearl", "&8▪ &7Cost: &2{cost} {currency}", "", "&7Pretty useful for invading", "&7enemies bases."));
                lbj.saveShopStuff("invContents.utility.invContents.bucket", "&aWater Bucket", Arrays.asList("&8Items:", "&8▪ &7Water Bucket", "", "&7Cost: &2{cost} {currency}"));
                lbj.saveShopStuff("invContents.utility.invContents.egg", "&aBridge Egg", Arrays.asList("&8Items:", "&8▪ &7Bridge Egg", "&7Cost: &2{cost} {currency}", "&7This egg creates a bridge in", "&7its trail after being thrown."));
                lbj.saveShopStuff("invContents.utility.invContents.back", "&aGo Back", Arrays.asList(""));
                yml.addDefault("meaning.iron", "Iron");
                yml.addDefault("meaning.gold", "Gold");
                yml.addDefault("meaning.emerald", "Emerald");
                yml.addDefault("meaning.diamond", "Diamond");
                yml.addDefault("meaning.vault", "$");
                yml.addDefault(pluralPath+".iron", "Iron");
                yml.addDefault(pluralPath+".gold", "Gold");
                yml.addDefault(pluralPath+".emerald", "Emeralds");
                yml.addDefault(pluralPath+".diamond", "Diamonds");
                yml.addDefault(pluralPath+".vault", "Money");
                yml.addDefault(insufficientMoney, "{prefix}&cYou don't have enough {currency}! Need {amount} more!");
                yml.addDefault(youPurchased, "{prefix}&aYou purchased &6{item}");
                yml.addDefault(joinCmdUsage, "§a▪ §7Usage: /" + mainCmd + " join §o<arena/group>");
                yml.addDefault(notAllowed, "{prefix}&cYou can't do this.");
                yml.addDefault(loreFooterClick, "&aClick to purchase!");
                yml.addDefault(loreFooterInsuff, "&cYou don't have enough {currency}");
                yml.addDefault(loreFooterLocked, "&cLOCKED");
                yml.addDefault(loreFooterUnlocked, "&aUNLOCKED");
                yml.addDefault("upgrades.default.generators.tier1.name", "&cIron Forge");
                yml.addDefault("upgrades.default.generators.tier1.lore", Arrays.asList("&7Increases the spawn rate of Iron", "&7and Gold by 50%..", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));
                yml.addDefault("upgrades.default.generators.tier2.name", "&cGolden Forge");
                yml.addDefault("upgrades.default.generators.tier2.lore", Arrays.asList("&7Increases the spawn rate of Iron", "&7and Gold by 100%..", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));
                yml.addDefault("upgrades.default.generators.tier3.name", "&cEmerald Forge");
                yml.addDefault("upgrades.default.generators.tier3.lore", Arrays.asList("&7Activates the Emerald spawner in", "&7your team's Forge.", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));
                yml.addDefault("upgrades.default.maniacMiner.tier1.name", "&eManiac Miner");
                yml.addDefault("upgrades.default.maniacMiner.tier1.lore", Arrays.asList("&7All players on your team", "&7permanently gain Haste I", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));

                break;
        }
        lbj.save();
        lbj.langName = lbj.m("name");
        lbj.prefixColor = ChatColor.translateAlternateColorCodes('&', yml.getString(prefix));
    }

    private void saveShopStuff(String path, Object name, Object lore){
        yml.addDefault(shopPath+path+".name", name);
        yml.addDefault(shopPath+path+".lore", lore);
    }

    public static List<String> getScoreboard(Player p, String path, String alternative) {
        if (langByPlayer.containsKey(p)) {
            if (langByPlayer.get(path) != null) {
                return langByPlayer.get(p).l(path);
            }
        } else {
            if (lang.yml.get(path) != null) {
                return lang.l(path);
            }
        }
        return getList(p, alternative);
    }

    public void set(String path, Object value){
        yml.set(path, value);
        save();
    }

    public String getLangName() {
        return langName;
    }

    public void save() {
        try {
            yml.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMsg(Player p, String path) {
        if (langByPlayer.containsKey(p)) {
            return langByPlayer.get(p).m(path);
        }
        return lang.m(path);
    }

    public static Language getPlayerLanguage(Player p){
        if (langByPlayer.containsKey(p)) {
            return langByPlayer.get(p);
        }
        return lang;
    }

    public boolean exists (String path){
        if (yml.get(path) == null) return false;
        return true;
    }

    public static List<String> getList(Player p, String path) {
        if (langByPlayer.containsKey(p)) {
            return langByPlayer.get(p).l(path);
        }
        return lang.l(path);
    }

    public static void saveIfNotExists(String path, Object data){
        for (Language l : languages){
            if (l.yml.get(path) == null){
                l.set(path, data);
            }
        }
    }

    public String m(String path) {
        return ChatColor.translateAlternateColorCodes('&', yml.getString(path).replace("{prefix}", prefixColor));
    }

    public List<String> l(String path) {
        return yml.getStringList(path).stream().map(s -> s.replace("&", "§")).collect(Collectors.toList());
    }

    public static HashMap<Player, Language> getLangByPlayer() {
        return langByPlayer;
    }

    public static boolean isLanguageExist(String iso) {
        for (Language l : languages) {
            if (l.iso.equalsIgnoreCase(iso)) {
                return true;
            }
        }
        return false;
    }

    public static Language getLang(String iso) {
        for (Language l : languages) {
            if (l.iso.equalsIgnoreCase(iso)) {
                return l;
            }
        }
        return lang;
    }

    public String getIso() {
        return iso;
    }

    public static List<Language> getLanguages() {
        return languages;
    }
}
