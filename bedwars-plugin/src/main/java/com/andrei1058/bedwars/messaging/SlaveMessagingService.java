package com.andrei1058.bedwars.messaging;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.arena.Arena;
import dev.andrei1058.bedwars.common.api.messaging.DefaultChannels;
import dev.andrei1058.bedwars.common.api.messaging.MessagingChannel;
import dev.andrei1058.bedwars.common.api.messaging.MessagingPacket;
import dev.andrei1058.bedwars.common.api.messaging.packet.*;
import dev.andrei1058.bedwars.common.messaging.MessagingCommonManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SlaveMessagingService {

    @Getter
    private static SlaveMessagingService singleton;
    @Getter
    @Setter
    private String identity;
    @Getter
    private PostSlavePongPacket PONG_PACKET;

    private SlaveMessagingService(String identity) {
        this.identity = identity;
    }

    public static void init() {
        if (singleton != null) {
            return;
        }
        var sender = BedWars.config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_ID);
        singleton = new SlaveMessagingService(sender);
        singleton.registerChannels();
        MessagingCommonManager.getInstance().getMessagingHandler().sendPacket(
                DefaultChannels.SLAVE_WAKE_UP.getName(),
                new PostSlaveWakeUpPacket(singleton.getIdentity(), null), false
        );
        Bukkit.getScheduler().scheduleSyncDelayedTask(BedWars.plugin, () -> singleton.tellLobbiesIWokeUp(), 20L);

        singleton.PONG_PACKET = new PostSlavePongPacket(getSingleton().getIdentity(), null);
        //noinspection deprecation
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(BedWars.plugin,
                () -> MessagingCommonManager.getInstance().getMessagingHandler().sendPacket(
                        DefaultChannels.PING.getName(), getSingleton().PONG_PACKET, false
                ), 20L, 20 * 5L
        );
    }

    private void registerChannels() {
        var arenaRequest = MessagingCommonManager.getInstance().getMessagingHandler().registerIncomingPacketChannel(
                new MessagingChannel<PostGameDataPacket>() {
                    @Override
                    public void read(PostGameDataPacket message) {
                        List<MessagingPacket> packets = new LinkedList<>();
                        Arena.getArenas().forEach(arena ->
                                {
                                    //TODO
//                                    PostGameDataPacket packet = new PostGameDataPacket(getIdentity(), arena);
//                                    ItemStack displayItem = arena.getDisplayItem(null);
//                                    if (null != displayItem) {
//                                        packet.setDisplayEnchantment(displayItem.getEnchantments().size() > 0);
//                                        byte d = CommonManager.getSingleton().getItemSupport().getItemData(displayItem);
//                                        packet.setDisplayData(d);
//                                        packet.setDisplayMaterial(displayItem.getType().toString());
//                                    }
//                                    packets.add(packet);
                                }
                        );
                        MessagingCommonManager.getInstance().getMessagingHandler().sendPackets(
                                message.getReplyChannel(), packets, false
                        );
                    }

                    @Override
                    public Class<PostGameDataPacket> getType() {
                        return PostGameDataPacket.class;
                    }

                    @Override
                    public String getName() {
                        return DefaultChannels.ARENA_QUERY.getName();
                    }
                }
        );

        if (!arenaRequest) {
            BedWars.plugin.getLogger().severe("Could not register FullData channel!");
        }

        var joinPost = MessagingCommonManager.getInstance().getMessagingHandler().registerIncomingPacketChannel(
                new MessagingChannel<PostGameJoinPacket>() {
                    @Override
                    public void read(PostGameJoinPacket message) {
                        if (Objects.nonNull(message.getTarget()) && !message.getTarget().equals(identity)) {
                            return;
                        }
                        // todo
//                        ProxyUser.store(message);
                    }

                    @Override
                    public Class<PostGameJoinPacket> getType() {
                        return PostGameJoinPacket.class;
                    }

                    @Override
                    public String getName() {
                        return DefaultChannels.PLAYER_JOIN.getName();
                    }
                }
        );

        if (!joinPost) {
            BedWars.plugin.getLogger().severe("Could not register PlayerJoin channel!");
        }

        var privateTakeOver = MessagingCommonManager.getInstance().getMessagingHandler().registerIncomingPacketChannel(
                new MessagingChannel<PostGameTakeOverPacket>() {
                    @Override
                    public void read(PostGameTakeOverPacket message) {
                        if (Objects.nonNull(message.getSender()) && message.getSender().equals(getIdentity())) {
                            return;
                        }
                        // todo
//                        GameArena arena = ArenaManager.getINSTANCE().getArenaById(message.getGameId());
//                        if (null == arena) {
//                            return;
//                        }
//                        if (arena.isPrivateGame()) {
//                            arena.setHost(message.getHost());
//                        }
                    }

                    @Override
                    public Class<PostGameTakeOverPacket> getType() {
                        return PostGameTakeOverPacket.class;
                    }

                    @Override
                    public String getName() {
                        return DefaultChannels.PRIVATE_TAKE_OVER.getName();
                    }
                }
        );

        if (!privateTakeOver) {
            BedWars.plugin.getLogger().severe("Could not register PrivateTakeOver channel!");
        }

        var teleportRequest = MessagingCommonManager.getInstance().getMessagingHandler().registerIncomingPacketChannel(
                new MessagingChannel<PostQueryPlayerPacket>() {
                    @Override
                    public void read(PostQueryPlayerPacket message) {
                        if (null != message.getTarget()) {
                            if (!message.getTarget().equals(getIdentity())) {
                                return;
                            }
                        }

                        Player player = Bukkit.getPlayerExact(message.getPlayerQueried());
                        if (null == player) {
                            return;
                        }
                        IArena arena = Arena.getArenaByPlayer(player);
                        UUID gameId = null;
                        if (null != arena) {
                            gameId = arena.getGameId();
                        }
                        MessagingCommonManager.getInstance().getMessagingHandler().sendPacket(DefaultChannels.POST_TELEPORT_RESPONSE.toString(),
                                new PostQueryPlayerResponsePacket(getIdentity(), message.getSender(), message, gameId),
                                true
                        );
                    }

                    @Override
                    public Class<PostQueryPlayerPacket> getType() {
                        return PostQueryPlayerPacket.class;
                    }

                    @Override
                    public String getName() {
                        return DefaultChannels.QUERY_TELEPORT.getName();
                    }
                }
        );

        if (!teleportRequest) {
            BedWars.plugin.getLogger().severe("Could not register TeleportQuery channel!");
        }

        var playAgain = MessagingCommonManager.getInstance().getMessagingHandler().registerIncomingPacketChannel(
                new MessagingChannel<PlayAgainResponsePacket>() {
                    @Override
                    public void read(PlayAgainResponsePacket message) {
                        if (null != message.getTarget()) {
                            if (!message.getTarget().equals(getIdentity())) {
                                return;
                            }
                        }
                        // todo
//                        Bukkit.getScheduler().runTask(BedWars.plugin,
//                                () -> PlayAgainManager.getInstance().processResponse(message)
//                        );
                    }

                    @Override
                    public Class<PlayAgainResponsePacket> getType() {
                        return PlayAgainResponsePacket.class;
                    }

                    @Override
                    public String getName() {
                        return DefaultChannels.PLAY_AGAIN_RESPONSE.getName();
                    }
                }
        );

        if (!playAgain) {
            BedWars.plugin.getLogger().severe("Could not register PlayAgain channel!");
        }
    }

//    private boolean isNotTargetedHere(MessagingPacket packet) {
//        if (!(packet instanceof TargetedPacket)) {
//            return false;
//        }
//        if (null == ((TargetedPacket)packet).getTarget()) {
//            return false;
//        }
//        return !((TargetedPacket) packet).getTarget().equals(getIdentity());
//    }


    private void tellLobbiesIWokeUp() {
        List<MessagingPacket> packets = new LinkedList<>();
        Arena.getArenas().forEach(arena -> {
            PostGameDataPacket packet = new PostGameDataPacket(getIdentity(), arena);

            // todo
//            ItemStack displayItem = arena.getDisplayItem(null);
//            if (null != displayItem) {
//                packet.setDisplayEnchantment(displayItem.getEnchantments().size() > 0);
//                packet.setDisplayData(CommonManager.getSingleton().getItemSupport().getItemData(displayItem));
//                packet.setDisplayMaterial(displayItem.getType().toString());
//            }
            packets.add(packet);
        });
        MessagingCommonManager.getInstance().getMessagingHandler().sendPackets(
                DefaultChannels.ARENA_FULL_DATA.getName(),
                packets,
                false
        );
    }
}
