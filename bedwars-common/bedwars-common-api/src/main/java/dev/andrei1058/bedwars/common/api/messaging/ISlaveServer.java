package dev.andrei1058.bedwars.common.api.messaging;

/**
 * Interface for message producers.
 * This is used in a form of heart beat.
 */
public interface ISlaveServer {
    /**
     * Bungee name.
     */
    String getName();

    /**
     * Update the latest pong received.
     * Using {@link DefaultChannels#PING}
     */
    void pong();

    long getLastPacket();

    /**
     * Check if dead.
     */
    @SuppressWarnings("unused")
    boolean isTimedOut();
}
