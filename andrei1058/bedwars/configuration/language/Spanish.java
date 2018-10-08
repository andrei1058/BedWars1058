package com.andrei1058.bedwars.configuration.language;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.Messages;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;
import java.util.Collections;

import static com.andrei1058.bedwars.Main.mainCmd;

public class Spanish {

    public Spanish(Language lbj, YamlConfiguration yml){
        yml.options().header("Translation by JuliCarles#1783");
        yml.addDefault(Messages.PREFIX, "");
        yml.addDefault("name", "English");
        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + mainCmd + " stats", "&2▪ &7/" + mainCmd + " join &o<arena/grupo>", "&2▪ &7/" + mainCmd + " leave", "&2▪ &7/" + mainCmd + " lang", "&2▪ &7/" + mainCmd + " gui", "&2▪ &7/" + mainCmd + " start &3(vip)"));
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "{prefix}&cHas sido expulsado ya que un VIP se unio a la partida.\n&aConsidera donar a nuestro servidor. &7&o(click)");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL, "{prefix}&cThis arena is full!\n&aPlease consider donating for more features. &7&o(click)");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS, "{prefix}&cEsta arena esta llena!.\n&cConsidera donar a nuestro servidor. &7&o(click).");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS, "{prefix}§cNo hay suficientes jugadores! La cuenta regresiva ha sido frenada!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "{prefix}&e{player} ha salido!");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "{prefix}&eLa arena en la que estabas se está reiniciando.");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&cEn Juego");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4Reiniciando");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&2En Espera §c{full}");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6Comenzando §c{full}");
        yml.addDefault(Messages.COMMAND_JOIN_GROUP_OR_ARENA_NOT_FOUND, "{prefix}&No existe una arena o grupos de arena llamadas: {name}");
        yml.addDefault(Messages.COMMAND_JOIN_NO_EMPTY_FOUND, "{prefix}&cNo hay arenas disponibles por el momento ;(");
        yml.addDefault(Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA, "{prefix}&cNo estas en ninguna arena!");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8Arenas disponibles");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l{name}");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7Estado: {status}", "&7Jugadores: &f{on}&7/&f{max}", "&7Tipo: &a{group}", "", "&aClick Izquierdo para unirte!", "&eClick Derecho para espectar!"));
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "{prefix} &2Idiomas disponibles:");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7{iso} - &f{name}");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "{prefix}&7Usa: /lang &f&o<iso>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "{prefix}&cEste idioma no existe!");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "{prefix}&aIdioma cambiado!");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "{prefix}&cNo puedes cambiar tu idioma mientras estas en juego.");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_PARTY_TOO_BIG, "{prefix}&cTu party es demasiado grande para unirte a esta partida como equipo. Considera reducir el tamaño o ingresar a una arena más grande.");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_NOT_PARTY_LEADER, "{prefix}&cSolo el lider puede unirse a una partida.");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&eNivel &c{tier}");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&lDiamante");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&lEsmeralda");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&eAparece en &c{seconds} &esegundos");
        yml.addDefault(Messages.COMMAND_JOIN_PLAYER_JOIN_MSG, "{prefix}&7{player} &ese ha unido (&b{on}&e/&b{max}&e)!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "{prefix}&7{player} &eha salido!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN, "{prefix}&eEl juego comenzara en &6{time} &esegundo(s)!");
        yml.addDefault(Messages.FORMATTING_CHAT_LOBBY, "{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_WAITING, "{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SHOUT, "{vPrefix}&6[GRITA] {team} &7{player}&f{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_TEAM, "{vPrefix}&f{level}{team}&7 {player}{vSuffix} {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SPECTATOR, "{vPrefix}&7[ESPECTADOR] {player}{vSuffix}: {message}");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "",
                "&e&l    Protege tu cama y destruye camas enemigas.",
                "&e&l Consigue mejoras para ti y para tu equipo consiguiendo",
                "&e&l   Hierro, Oro, Esmeralda, y Diamantes de los generadores",
                "&e&l         para acceder a importantes mejoras.", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.MEANING_SHOUT, "grita");
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "{prefix}&cNo puedes colocar bloques aqui!");
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList("&f&lBED WARS", "", "&fMapa: &a{map}", "&fJugador: &a{on}/{max}", "", "&fComenzando en &a{time}s", "", "§fServer: &a{server}", "", "&e  andrei1058.com"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList("&f&lBED WARS", "", "&fMapa: &a{map}", "&fJugador: &a{on}/{max}", "", "&fEsperando...", "", "§fServer: &a{server}", "", "&e  andrei1058.com"));
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "{prefix}&cSolo puedes romper bloques puesto por jugadores!");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&aVAMOS");
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent}", "&a{time}", "", "{TeamRedColor}&lR&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}&lB&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}&lG&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}&lY &f{TeamYellowName}&f: {TeamYellowStatus}", "",
                "&fAsesinatos: &a{kills}", "&fAsesinatos Finales: &a{finalKills}", "&fCamas Destruidas: &a{beds}", "", "&ehttp://andrei1058.com"));
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
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_YOUR_TEAM, "&7 (Tú)");
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fBuscando: {team} &f- Distancia: {distance}m");
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&lDefiende tu cama!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&cNo puedes destruir tu propia cama!");
        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&lEQUIPO ELIMINADO > El equipo {TeamColor}{TeamName} &cha sido eliminado\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&lCAMA DESTRUIDA > La cama del equipo {TeamColor}{TeamName} &7ha sido destruida por {PlayerColor}{PlayerName}&7!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&cCAMA DESTRUÍDA!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&fYa no reaparecerás!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&f&lCAMA DESTRUIDA > &7Tu cama ha sido destruida por {PlayerColor}{PlayerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "{PlayerColor}{PlayerName} &7ha caído al vacio.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "{PlayerColor}{PlayerName} &7ha caído al vacio. &b&lMUERTE FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "{PlayerColor}{PlayerName} &7fue empujado al vacio por {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, "{PlayerColor}{PlayerName} &7fue empujado al vacio por {KillerColor}{KillerName}&7. &b&lMUERTE FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "{PlayerColor}{PlayerName} &7fue golpeado por una bomba por {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7fue golpeado por una bomba por {KillerColor}{KillerName}&7. &b&lMUERTE FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "{PlayerColor}{PlayerName} &7fue asesinado por {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "{PlayerColor}{PlayerName} &7fue asesinado por {KillerColor}{KillerName}&7. &b&lMUERTE FINAL!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lTu cama ha sido destruida!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cHAS MUERTO!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eReaparecerás en &c{time} &esegundos!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "{prefix}&eReaparecerás en &c{time} &esegundos!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aHAS REAPARECIDO!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "{prefix}&cHas sido eliminado!");
        yml.addDefault(Messages.GAME_END_GAME_OVER_PLAYER_TITLE, "&c&lJUEGO FINALIZADO!");
        yml.addDefault(Messages.GAME_END_VICTORY_PLAYER_TITLE, "&6&lVICTORIA!");
        yml.addDefault(Messages.GAME_END_TOP_PLAYER_CHAT, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "", "{winnerFormat}", "", "",
                "&e                         &l1er Asesino &7- {firstName} - {firstKills}",
                "&6                         &l2do Asesino &7- {secondName} - {secondKills}",
                "&c                         &l3er Asesino &7- {thirdName} - {thirdKills}", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")); //todo disponibile {firstDeaths} {firstBeds} {firstName} {firstKills}
        //yml.addDefault(gameOverReward, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
        //        "&f                                   &lReward Summary", "", "",
        //        "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.GAME_END_TEAM_WON_CHAT, "{prefix}{TeamColor}{TeamName} &aha ganado el juego!");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 {TeamColor}{TeamName} &7- {members}");
        //yml.addDefault(tablistFormat, "{TeamColor}&l{TeamLetter}&r {TeamColor}{PlayerName} &e{PlayerHealth}");//{TeamColor}{TeamName}{TeamHealth}{PlayerName}{PlayerHealth}
        yml.addDefault(Messages.MEANING_NOBODY, "Nadie");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT, "{prefix}{generatorType} Los generadores &ehan sido mejorados al nivel &c{tier}");
        yml.addDefault(Messages.NPC_NAME_TEAM_UPGRADES, "&e&lMEJORAS,&b&lTEAM");
        yml.addDefault(Messages.NPC_NAME_SOLO_UPGRADES, "&e&lMEJORAS,&b&lSOLO");
        yml.addDefault(Messages.NPC_NAME_TEAM_SHOP, "&b&lTIENDA,&e&lCLICK DERECHO");
        yml.addDefault(Messages.NPC_NAME_SOLO_SHOP, "&b&lTIENDA,&e&lCLICK DERECHO");
        yml.addDefault(Messages.SHOP_PATH + "name", "&7Tienda");

        lbj.saveShopStuff("invContents.armor", "&aArmadura", Arrays.asList("&7Disponible:", "&7▪ Botas de Malla", "&7▪ Pantalones de Malla", "&7▪ Botas de Hierro", "&7▪ Pantalones de Hierro", "&7▪ Botas de Diamante", "&7▪ Pantalones de Diamante", "", "&eClick para buscar!"));
        lbj.saveShopStuff("invContents.melee", "&aMelee", Arrays.asList("&7Disponible:", "&7▪ Espada de Piedra", "&7▪ Espada de Hierro", "&7▪ Espada de Diamante", "&7▪ Palo (Knockback I)", "", "&eClick para buscar!"));
        lbj.saveShopStuff("invContents.blocks", "&aBloques", Arrays.asList("&7Disponible:", "&7▪ Lana", "&7▪ Arcilla", "&7▪ Vidrio a prueba de explosiones", "&7▪ Piedra del End", "&7▪ Escalera", "&7▪ Madera", "&7▪ Obsidiana", "", "&eClick para buscar!"));
        lbj.saveShopStuff("invContents.ranged", "&aArqueria", Arrays.asList("&7Disponible:", "&7▪ Flecha", "&7▪ Arco", "&7▪ Arco (Poder I)", "&7▪ Arco (Poder I, Empuje I)", "", "&eClick para buscar!"));
        lbj.saveShopStuff("invContents.tools", "&aHerramientas", Arrays.asList("&7Disponible:", "&7▪ Tijeras permanentes", "&7▪ Pico de Madera (Effeciencia I)", "&7▪ Hacha de Madera (Eficiencia I)", "", "&eClick para buscar!"));
        lbj.saveShopStuff("invContents.potions", "&aPociones", Arrays.asList("&7Disponible:", "&7▪ Velocidad II", "&7▪ Salto V", "&7▪ Invisibilidad", "", "&eClick para buscar!"));
        lbj.saveShopStuff("invContents.utility", "&aUtilidades", Arrays.asList("&7Disponible:", "&7▪ Manzana Dorada", "&7▪ BedBug", "&7▪ Dream Defender", "&7▪ Bola de Fuego", "&7▪ TNT", "&7▪ Ender Pearl", "&7▪ Cubo de Agua", "&7▪ Bridge Egg", "", "&eClick para buscar!"));
        //lbj.saveShopStuff("invContents.favourites", "&aFavourites", Arrays.asList("&7Click to view your", "&7favourite items. You can", "&7set a favourite item by", "&7shift clicking on any shop item!"));
        //lbj.saveShopStuff("invContents.recommended", "&aRecommended Items", Arrays.asList("&7Original", "&7Click to view some", "&7recommended items for early", "&7to late game!", "", "&eClick para buscar!"));

        lbj.saveShopStuff("invContents.armor.invContents.chainmail", "&aPermanent Chainmail Armor", Arrays.asList("&8Objetos:", "&8▪ &7Botas de Malla", "&8▪ &7Pantalones de Malla", "", "&8Precio: &f{cost} {currency}", "&8&oYou will not lose this on death!"));
        lbj.saveShopStuff("invContents.armor.invContents.iron", "&aPermanent Iron Armor", Arrays.asList("&8Objetos:", "&8▪ &7Botas de Hierro", "&8▪ &7Pantalones de Hierro", "", "&8Precio: &6{cost} {currency}", "&8&oYou will not lose this on death!"));
        lbj.saveShopStuff("invContents.armor.invContents.diamond", "&aPermanent Diamond Armor", Arrays.asList("&8Objetos:", "&8▪ &7Botas de Diamante", "&8▪ &7Pantalones de Diamante", "", "&8Precio: &2{cost} {currency}", "&8&oYou will not lose this on death!"));
        lbj.saveShopStuff("invContents.armor.invContents.back", "&aIr Atrás", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.melee.invContents.stone", "&aEspada de Piedra", Arrays.asList("&8Objetos:", "&8▪ &7Espada de Piedra", "", "&8Precio: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.iron", "&aEspada de Hierro", Arrays.asList("&8Objetos:", "&8▪ &7Espada de Hierro", "&8Precio: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.diamond", "&aEspada de Diamante", Arrays.asList("&8Objetos:", "&8▪ &7Espada de Diamante", "", "&8Precio: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.stick", "&aPalo (Knockback I)", Arrays.asList("&8Objetos:", "&8▪ &7Palo (Knockback I)", "", "&8Precio: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.melee.invContents.back", "&aIr Atrás", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.blocks.invContents.wool", "&aLana", Arrays.asList("&8Objetos:", "&8▪ &7Lana", "", "&8Precio: &f{cost} {currency}", "", "&eClick para comprar!"));
        lbj.saveShopStuff("invContents.blocks.invContents.clay", "&aArcilla", Arrays.asList("&8Objetos:", "&8▪ &7Arcilla", "", "&8Precio: &f{cost} {currency}", "", "&eClick para comprar!"));
        lbj.saveShopStuff("invContents.blocks.invContents.glass", "&aVidrio a prueba de explosiones", Arrays.asList("&8Objetos:", "&8▪ &7Vidrio a prueba de explosiones", "", "&8Precio: &f{cost} {currency}", "", "&7Immune to explosions", "", "&eClick para comprar!"));
        lbj.saveShopStuff("invContents.blocks.invContents.stone", "&aPiedra del End", Arrays.asList("&8Objetos:", "&8▪ &7Piedra del End", "", "&8Precio: &f{cost} {currency}", "", "&eClick para comprar!"));
        lbj.saveShopStuff("invContents.blocks.invContents.ladder", "&aEscalera", Arrays.asList("&8Objetos:", "&8▪ &7Escalera", "", "&8Precio: &f{cost} {currency}", "", "&eClick para comprar!"));
        lbj.saveShopStuff("invContents.blocks.invContents.wood", "&aMadera", Arrays.asList("&8Objetos:", "&8▪ &7Madera", "", "&8Precio: &6{cost} {currency}", "", "&eClick para comprar!"));
        lbj.saveShopStuff("invContents.blocks.invContents.obsidian", "&cObsidiana", Arrays.asList("&8Objetos:", "&8▪ &7Obsidiana", "", "&8Precio: &2{cost} {currency}", "", "&eClick para comprar!"));
        lbj.saveShopStuff("invContents.blocks.invContents.back", "&aIr Atrás", Arrays.asList(""));

        lbj.saveShopStuff("invContents.ranged.invContents.arrow", "&aFlecha", Arrays.asList("&8Objetos:", "&8▪ &7Flecha", "", "&8Precio: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow", "&aArco", Arrays.asList("&8Objetos:", "&8▪ &7Arco", "", "&8Precio: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow2", "&aArco (Poder I)", Arrays.asList("&8Objetos:", "&8▪ &7Arco (Poder I)", "", "&8Precio: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.bow3", "&aArco (Poder I, Empuje I)", Arrays.asList("&8Objetos:", "&8▪ &7Arco (Poder I, Empuje I)", "", "&8Precio: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.ranged.invContents.back", "&aIr Atrás", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.tools.invContents.shears", "&aPermanent Tijeras permanentes", Arrays.asList("&8Objetos:", "&8▪ &7Permanent Tijeras permanentes", "", "&8Precio: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.pick", "&aWooden Pickaxe", Arrays.asList("&8Objetos:", "&8▪ &7Wooden Pickaxe", "", "&8Precio: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.axe", "&aWooden Axe", Arrays.asList("&8Objetos:", "&8▪ &7Wooden Axe", "", "&8Precio: &f{cost} {currency}"));
        lbj.saveShopStuff("invContents.tools.invContents.back", "&aIr Atrás", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.potions.invContents.potion1", "&aVelocidad II", Arrays.asList("&8Objetos:", "&8▪ &7Velocidad II", "", "&8Precio: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.potion2", "&aSalto V", Arrays.asList("&8Objetos:", "&8▪ &7Salto V", "", "&8Precio: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.potion3", "&aInvisibilidad", Arrays.asList("&8Objetos:", "&8▪ &7Invisibilidad", "", "&8Precio: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.potions.invContents.back", "&aIr Atrás", Collections.singletonList(""));

        lbj.saveShopStuff("invContents.utility.invContents.apple", "&aManzana Dorada", Arrays.asList("&8Objetos:", "&8▪ &7Manzana Dorada", "", "&8Precio: &6{cost} {currency}"));
        lbj.saveShopStuff("invContents.utility.invContents.sball", "&aBedbug", Arrays.asList("&8Objetos:", "&8▪ &7Bedbug", "", "&8Precio: &f{cost} {currency}", "", "&7Moderadamente molestos. Estos", "&7pequeños bichos pueden ser lanzados", "&7para distraer enemigos."));
        lbj.saveShopStuff("invContents.utility.invContents.monsteregg", "&aDream Defender", Arrays.asList("&8Objetos:", "&8▪ &7Dream Defender", "", "&8Precio: &f{cost} {currency}", "", "&7Moderadamente motivado.", "&7Algunas veces defendera", "&7tú base."));
        lbj.saveShopStuff("invContents.utility.invContents.fireball", "&aBola de Fuego", Arrays.asList("&8Objetos:", "&8▪ &7Bola de Fuego", "", "&8Precio: &f{cost} {currency}", "", "&7Click-Derecho para lanzar!"));
        lbj.saveShopStuff("invContents.utility.invContents.tnt", "&aTNT", Arrays.asList("&8Objetos:", "&8▪ &7TNT", "", "&8Precio: &6{cost} {currency}", "", "&7Enciende automáticamente, util", "&7para explotar cosas!"));
        lbj.saveShopStuff("invContents.utility.invContents.enderpearl", "&aEnder Pearl", Arrays.asList("&8Objetos:", "", "&8Ender Pearl", "&8▪ &8Precio: &2{cost} {currency}", "", "&7Útil para evadir o atacar", "&7bases enemigas."));
        lbj.saveShopStuff("invContents.utility.invContents.bucket", "&aCubo de Agua", Arrays.asList("&8Objetos:", "&8▪ &7Cubo de Agua", "", "&8Precio: &2{cost} {currency}"));
        lbj.saveShopStuff("invContents.utility.invContents.eggBridge", "&aBridge Egg", Arrays.asList("&8Objetos:", "&8▪ &7Bridge Egg", "", "&8Precio: &2{cost} {currency}", "&7Este huevo creara un puente", "&7en la direccion al cual lo lanzes."));
        lbj.saveShopStuff("invContents.utility.invContents.back", "&aIr Atrás", Collections.singletonList(""));

        yml.addDefault(Messages.MEANING_IRON_SINGULAR, "Hierro");
        yml.addDefault(Messages.MEANING_IRON_PLURAL, "Hierros");
        yml.addDefault(Messages.MEANING_GOLD_SINGULAR, "Oro");
        yml.addDefault(Messages.MEANING_GOLD_PLURAL, "Oros");
        yml.addDefault(Messages.MEANING_EMERALD_SINGULAR, "Esmeralda");
        yml.addDefault(Messages.MEANING_EMERALD_PLURAL, "Esmeraldas");
        yml.addDefault(Messages.MEANING_DIAMOND_SINGULAR, "Diamante");
        yml.addDefault(Messages.MEANING_DIAMOND_PLURAL, "Diamantes");
        yml.addDefault(Messages.MEANING_VAULT_SINGULAR, "$");
        yml.addDefault(Messages.MEANING_VAULT_PLURAL, "$");

        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "{prefix}&cNo tienes suficiente material de {currency}");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "{prefix}&aHas comprado &6{item}.");
        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Usa: /" + mainCmd + " join §o<arena/grupo>");
        yml.addDefault(Messages.COMMAND_NOT_ALLOWED_IN_GAME, "{prefix}&cNo tienes permisos para hacer esto.");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "&aClick para comprar!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "&cNo tienes suficiente material de {currency}");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cBLOQUEADO");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "&aDESBLOQUEADO");
        yml.addDefault("upgrades.Default.generators.tier1.name", "&eFundidor de Hierro");
        yml.addDefault("upgrades.Default.generators.tier1.lore", Arrays.asList("&7Incrementa la velocidad de aparicion de Hierro", "&7y Oro un 50%..", "", "&8Precio:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.generators.tier2.name", "&eFundidor de Oro");
        yml.addDefault("upgrades.Default.generators.tier2.lore", Arrays.asList("&7Incrementa la velocidad de aparicion de Hierro", "&7y Oro un 100%..", "", "&8Precio:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.generators.tier3.name", "&eFundidor de Esmeralda");
        yml.addDefault("upgrades.Default.generators.tier3.lore", Arrays.asList("&7Activa la aparicion de Esmeraldas del", "&7generador de tu equipo.", "", "&8Precio:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.maniacMiner.tier1.name", "&eMinero Maniaco");
        yml.addDefault("upgrades.Default.maniacMiner.tier1.lore", Arrays.asList("&7Todos los jugadores de tu quipo", "&7tendran permanentemente Apuro I", "", "&8Precio:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.sharpSword.tier1.name", "&eEspadas Afiladas");
        yml.addDefault("upgrades.Default.sharpSword.tier1.lore", Arrays.asList("&7Tu equipo consigue Filo I en", "&7todas las espadas!", "", "&8Precio:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.reinforced.tier1.name", "&eArmadura Reforzada");
        yml.addDefault("upgrades.Default.reinforced.tier1.lore", Arrays.asList("&7Tu equipo conseguira Proteccion I en", "&7en todas las armaduras!", "", "&8Precio:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.trap.tier1.name", "&eEs una trampa!");
        yml.addDefault("upgrades.Default.trap.tier1.lore", Arrays.asList("&7El proximo enemigo en entrar", "&7a tú base recibirá efecto", "&7v!", "", "&8Precio:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.miningFatigue.tier1.name", "&eTrampa de Fatiga Minera");
        yml.addDefault("upgrades.Default.miningFatigue.tier1.lore", Arrays.asList("&7El próximo enemigo en entrar a tú", "&7base recibirá Fatiga al minar", "&7por 10 segundos!", "", "&8Precio:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.healPool.tier1.name", "&ePiscina de Salud");
        yml.addDefault("upgrades.Default.healPool.tier1.lore", Arrays.asList("&7Crea una capsula de regeneracion", "&7alrededor de tu base!", "", "&8Precio:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault(Messages.TRAP_ENEMY_BASE_ENTER_ACTION, "&cTRAMPA ACTIVADA!");
        yml.addDefault(Messages.TRAP_ENEMY_BASE_ENTER_CHAT, "{prefix}&cTRAMPA ACTIVADA!");
        yml.addDefault(Messages.TRAP_ENEMY_BASE_ENTER_SUBTITLE, "&fAlguien cayó en tu trampa!");
        yml.addDefault(Messages.TRAP_ENEMY_BASE_ENTER_TITLE, "&cTRAMPA ACTIVADA!");
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a{player} ha comprado &6{upgradeName}");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "{TeamColor}&l{TeamName} &r{TeamColor}Silverfish");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "{TeamColor}{despawn}s &8[ {TeamColor}{health}&8]");
        yml.addDefault(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH, "▮ ");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR, "{PlayerColor}{PlayerName} &7murio.");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL, "{PlayerColor}{PlayerName} &7murio. &b&lMUERTE FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_REGULAR, "{PlayerColor}{PlayerName} &7ha sido disparado por {KillerColor}{KillerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_FINAL_KILL, "{PlayerColor}{PlayerName} &7ha sido disparado por {KillerColor}{KillerName}&7! &b&lMUERTE FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_REGULAR, "{PlayerColor}{PlayerName} &7&7fue asesinado por la BedBug de {KillerColor}{KillerTeamName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_FINAL_KILL, "{PlayerColor}{PlayerName} &7&7fue asesinado por la BedBug de {KillerColor}{KillerTeamName}&7. &b&lMUERTE FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_REGULAR, "{PlayerColor}{PlayerName} &7fue asesinado por el Golem de {KillerColor}{KillerTeamName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL, "{PlayerColor}{PlayerName} &7fue asesinado por el Golem de {KillerColor}{KillerTeamName}&7. &b&lMUERTE FINAL!");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_DIAMOND, "{prefix}&b+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_EMERALD, "{prefix}&a+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_IRON, "{prefix}&f+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_GOLD, "{prefix}&6+{amount} {meaning}");

        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR, "{PlayerColor}{PlayerName} &7fue golpeado por una bomba.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7fue golpeado por una bomba. &b&lMUERTE FINAL!");
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "{player} Estadistícas");

        /* save default items messages for stats gui */
        lbj.addDefaultStatsMsg(yml, "wins", "&6Victorias", "&f{wins}");
        lbj.addDefaultStatsMsg(yml, "losses", "&6Derrotas", "&f{losses}");
        lbj.addDefaultStatsMsg(yml, "kills", "&6Asesinatos", "&f{kills}");
        lbj.addDefaultStatsMsg(yml, "deaths", "&6Muertes", "&f{deaths}");
        lbj.addDefaultStatsMsg(yml, "final-kills", "&6Asesinatos Finales", "&f{finalKills}");
        lbj.addDefaultStatsMsg(yml, "final-deaths", "&6Muertes Finales", "&f{finalDeaths}");
        lbj.addDefaultStatsMsg(yml, "beds-destroyed", "&6Camas Destruidas", "&f{bedsDestroyed}");
        lbj.addDefaultStatsMsg(yml, "first-play", "&6Primera Partida", "&f{firstPlay}");
        lbj.addDefaultStatsMsg(yml, "last-play", "&6Última Partida", "&f{lastPlay}");
        lbj.addDefaultStatsMsg(yml, "games-played", "&6Juegos Jugados", "&f{gamesPlayed}");
        yml.addDefault(Messages.FORMATTING_STATS_DATE_FORMAT, "yyyy/MM/dd HH:mm");

        yml.addDefault(Messages.MEANING_NEVER, "Nunca");
        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&6&lBedWars", "&7{date}", "&fNombre: &a{player}", "", "&6Victorias: &a{wins}", "&6Derrotas: &a{losses}", "&6Asesinatos: &a{kills}", "&6Muertes: &a{deaths}"
                , "&fAsesinatos Finales: &a{fKills}", "&fCamas Destruidas: &a{beds}", "", "&fConectados: &a{on}", "&eandrei1058.com"));

        /* party commands */
        yml.addDefault(Messages.COMMAND_PARTY_HELP, Arrays.asList("&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&aComandos de Party:", "&e/party help &7- &bMuestra este mismo mensaje", "&e/party invite <player> &7- &bInvita a un jugador a tu party",
                "&e/party leave &7- &bDeja tu actual party",
                "&e/party remove <player> &7- &bExpulsa a un jugador de tu party",
                "&e/party accept <player> &7- &bAcepta la invitacion a una party", "&e/party disband &7- &bRompe una party"));
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_USAGE, "{prefix}&eUso: &7/party invite <jugador>");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &eno está conectado!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT, "{prefix}&eInvitación enviada a &7{player}&6.");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG, "{prefix}&b{player} &ete ha invitado a una party! &o&7(Click para aceptar)");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF, "{prefix}&cNo puedes invitarte a ti mismo!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE, "{prefix}&cNo hay mas solicitudes para aceptar.");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY, "{prefix}&eYa estas en una party!");
        yml.addDefault(Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS, "{prefix}&cSolo el jefe de la party puede hacer eso!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_USAGE, "{prefix}&eUso: &7/party accept <jugador>");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_SUCCESS, "{prefix}&7{player} &ese ha unido a la party!");
        yml.addDefault(Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY, "{prefix}&cTu no estas en una party!");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND, "{prefix}&cNo puedes dejar tu propia party!\n&eIntenta utilizando: &b/party disband");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_SUCCESS, "{prefix}&7{player} &eha abandonado tu party!");
        yml.addDefault(Messages.COMMAND_PARTY_DISBAND_SUCCESS, "{prefix}&eHas roto la party!");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_USAGE, "{prefix}&7Usa: &e/party remove <jugador>");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_SUCCESS, "{prefix}&7{player} &efue expulsado de tu party.");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER, "{prefix}&7{player} &eno está en tu party!");
        yml.addDefault(Messages.NEXT_EVENT_BEDS_DESTROY, "&cBOOM Camas");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_II, "&fDiamante II");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_III, "&fDiamante III");
        yml.addDefault(Messages.NEXT_EVENT_DRAGON_SPAWN, "&fMuerte Subita");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_II, "&fEsmeralda II");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_III, "&fEsmeralda III");
        yml.addDefault(Messages.NEXT_EVENT_GAME_END, "&4Fin del Juego");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED, "&cCAMAS DESTRUIDAS!");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED, "&fTodas las camas fueron destruidas!");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED, "&c&Todas las camas fueron destruidas!");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH, "&cMuerte súbita");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH, "");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH, "&cMUERTE SUBITA: &6&b{TeamDragons} {TeamColor}{TeamName} Dragon!");
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "{prefix}&cComando no encontrado o permisos insuficientes!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "§c▪ §7No estas jugando!");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "§c▪ §7Cuenta regresiva comenzada!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "{prefix}&7Tu no puedes forzar el comienzo de la arena.\n§7Esta es una funcion que solo miembros staff pueden utilizar.");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_MSG, "{prefix}§6Estas ahora espectando la arena §9{arena}§6.\n{prefix}§ePuedes dejar la arena en cualquier momento escribiendo §c/leave§e.");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cNo puedes abrir este cofre ya que el equipo ha sido totalmente eliminado!");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&lTransportador");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "{prefix}{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7Salud: &f{health}%", "&7Comida: &f{food}", "", "&7Click-Izquierdo para espectar al jugador."));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lRegresar al Lobby");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Click-Derecho para regresar al lobby!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&Modo Espectador: &7{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&cSHIFT para salir");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eSalir del Modo Espectador");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "{prefix}&cYa has comprado eso!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &eno está conectado!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG, "&cLos espectadores no están permitidos en esta arena!");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "{prefix}§cThe party owner has left and the party was disbanded!");

        /* Lobby Command Items */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".stats") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "stats"), "&lEstadistícas");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fClick-Derecho para ver tus estadisticas!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".arena-selector") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&eJugar");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Collections.singletonList("&fClick-Derecho para jugar una partida!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&eRegresar al Lobby");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fClick-Derecho para volver al lobby!"));
        }
        /* Pre Game Command Items */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".stats") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&lEstadistícas");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fClick-Derecho para ver tus estadisticas!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&eRegresar al Lobby");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fClick-Derecho para volver al lobby!"));
        }
        /* Spectator Command Items */
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH + ".teleporter") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&eTeleporter");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "teleporter"), Collections.singletonList("&fClick-Derecho para transportarte a un jugador!"));
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH + ".leave") != null) {
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&eRegresar al Lobby");
            yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fClick-Derecho para volver al lobby!"));
        }
        Language.addDefaultMessagesCommandItems(lbj);

        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cNo puedes hacer eso aún! Espera {seconds} segundos más!");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM, "{TeamColor}[{TeamName}]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT, "&6[GRITA]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR, "&7[ESPECTADOR]");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "{prefix}&cLo lamentamos, pero no puedes ingresar a esta arena por el momento. Usa Click-Derecho para entrar en modo espectador!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "{prefix}&cLo lamentamos, pero no puedes espectar esta arena en este momento. Utiliza Click-Izquierdo para jugar!");

        yml.addDefault(Messages.REJOIN_NO_ARENA, "{prefix}&cNo hay arena a la cual unirse. Recuerda que sólo puedes ingresar a una 5 minutos después del comienzo de la partida!");
        yml.addDefault(Messages.REJOIN_DENIED, "{prefix}&cNo puedes unirte a esa partida. El juego ha terminado o tu cama ha sido destruida.");
        yml.addDefault(Messages.REJOIN_ALLOWED, "{prefix}&eHas ingresado nuevamente a &a{arena}&e!");
    }
}
