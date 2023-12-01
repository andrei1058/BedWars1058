package dev.andrei1058.bedwars.common.api.party;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface PartyAdapter {

    /**
     * Party adapter name.
     *
     * @return party adapter name or plugin owner name.
     */
    String getName();

    /**
     * Check if the given player has a party.
     *
     * @param player player to be checked.
     * @return true if the player is in a party.
     */
    boolean hasParty(@Nullable UUID player);

    /**
     * Add a member to an existing party.
     *
     * @param partyOwner party owner used to find the party.
     * @param toBeAdded  player to be added.
     * @return true if added to the party.
     */
    boolean addMember(UUID partyOwner, UUID toBeAdded);

    /**
     * Create a party or add members to an existing one.
     * You have to check if the given members are already in party yourself.
     *
     * @param partyOwner party owner (eventually used to find the party).
     * @param members    party members.
     * @return true if created.
     */
    boolean createParty(UUID partyOwner, List<UUID> members);

    /**
     * Remove a player from existing party.
     * If the given use is the owner, disband.
     *
     * @param player to be removed.
     * @return true if party got disbanded.
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean removeFromParty(UUID player);

    /**
     * Get how many parties are loaded locally.
     * This is used when someone tries to replace the current party adapter and if this is greater than 0 the action is denied.
     *
     * @return amount of parties loaded locally.
     */
    int getLoadedParties();

    /**
     * Check if player is owner of a party.
     *
     * @param player player to be checked.
     * @return true if owner of a party.
     */
    boolean isOwner(UUID player);

    /**
     * Get members from a party.
     * Will return an empty list if the player has not a party or if there are no members in that party.
     * Including the party owner in that list is not a must.
     *
     * @param partyMemberOrOwner party member or owner.
     * @return party members list. Empty if the given player is not in a party.
     */
    @NotNull
    List<UUID> getMembers(UUID partyMemberOrOwner);

    /**
     * Get owner of a party.
     *
     * @param member party member.
     * @return party owner. Null if the given player is not in a party.
     */
    @Nullable
    UUID getOwner(UUID member);

    /**
     * Disband a party.
     *
     * @param player party owner or member.
     */
    void disband(UUID player);

    /**
     * Set this to false if you want to let the plugin team-up players that join from a remote lobby.
     * True if you want to handle it yourself.
     *
     * @return true if this adapter is able to team-up itself players across bungee-network.
     */
    boolean isSelfTeamUpAtRemoteJoin();

    /**
     * Transfer party ownership.
     *
     * @param partyOwner  party owner.
     * @param partyMember member to become owner.
     * @return true if transferred successfully.
     */
    boolean transferOwnership(UUID partyOwner, UUID partyMember);

    /**
     * Check if this party has reached its size limit.
     *
     * @param partyOwnerOrMember player used to retrieve the party instance. Will return false as well if party not found.
     */
    boolean isPartySizeLimitReached(UUID partyOwnerOrMember);

    /**
     * Triggered when current adapter is being replaced by another one.
     */
    void disableAdapter();
}
