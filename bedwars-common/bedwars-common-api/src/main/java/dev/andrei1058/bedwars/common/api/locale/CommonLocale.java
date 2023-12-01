package dev.andrei1058.bedwars.common.api.locale;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;

public interface CommonLocale {
    /**
     * Color color translated message.
     *
     * @param message message.
     * @param player  if requesting a player message. Used to parse placeholders.
     * @return Chat color translated message at given path.
     */
    String getMsg(@Nullable Player player, CommonMessage message);

    /**
     * Color color translated message.
     *
     * @param message message.
     * @param sender  if requesting a player message. Used to parse placeholders.
     * @return Chat color translated message at given path.
     */
    String getMsg(@Nullable CommandSender sender, CommonMessage message);

    /**
     * Color color translated message.
     *
     * @param message message.
     * @param player  if requesting a player message. Used to parse placeholders.
     * @return Chat color translated message at given path.
     */
    @SuppressWarnings("unused")
    List<String> getMsgList(@Nullable Player player, CommonMessage message);

    /**
     * Color color translated message.
     *
     * @param path   message path.
     * @param player if requesting a player message. Used to parse placeholders.
     * @return Chat color translated message at given path.
     */
    default List<String> getMsgList(@Nullable Player player, CommonMessage path, @Nullable String[] replacements) {
        return getMsgList(player, path.toString(), replacements);
    }

    /**
     * Color color translated message.
     *
     * @param path   message path.
     * @param player if requesting a player message. Used to parse placeholders.
     * @return Chat color translated message at given path.
     */
    List<String> getMsgList(@Nullable Player player, String path, @Nullable String[] replacements);

    /**
     * Set a string in the language file.
     *
     * @param path  msg path.
     * @param value msg.
     */
    void setMsg(String path, String value);

    /**
     * Set a string in the language file.
     *
     * @param path  msg path.
     * @param value msg.
     */
    void setList(String path, List<String> value);

    /**
     * @param path message path.
     * @return True if the given path exists.
     */
    boolean hasPath(String path);

    /**
     * Color color translated message.
     *
     * @param path   message path.
     * @param player if requesting a player message. Used to parse placeholders.
     * @return Chat color translated message at given path.
     */
    String getMsg(@Nullable Player player, String path);

    /**
     * Color color translated message.
     *
     * @param path   message path.
     * @param player if requesting a player message. Used to parse placeholders.
     * @return Chat color translated message at given path.
     */
    List<String> getMsgList(@Nullable Player player, String path);

    /**
     * Get language iso code.
     * Languages are identified by this code.
     */
    String getIsoCode();

    /**
     * This will check if the language file is enabled via {@link CommonMessage#ENABLE}.
     */
    boolean isEnabled();

    /**
     * Get a raw message. No color translation.
     * No placeholder parsing.
     */
    String getRawMsg(String path);

    /**
     * Get a raw message list. No color translation.
     * No placeholder parsing.
     */
    List<String> getRawList(String path);

    /**
     * Format date.
     *
     * @param date date to be formatted;
     */
    String formatDate(Date date);
}
