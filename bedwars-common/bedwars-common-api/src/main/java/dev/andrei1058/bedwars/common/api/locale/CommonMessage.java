package dev.andrei1058.bedwars.common.api.locale;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

@Getter
public enum CommonMessage {
    ENABLE("enable", true),

    ;

    private final String path;
    /**
     * Get default message value.
     */
    private final Object defaultMsg;
    /**
     * Check if this message needs manual saving.
     * Returns false when it is saved by {@link #saveDefaults(YamlConfiguration)}.
     */
    private final boolean manual;

    /**
     * Create a common message used in the main mini-game and connector plugin.
     *
     * @param manual     true if this path requires manual saving to yml.
     *                   If message path has placeholders like this: my-path-{name}-lore.
     * @param path       message path.
     * @param defaultMsg default message for path.
     */
    CommonMessage(boolean manual, String path, Object defaultMsg) {
        this.path = "cm-" + path;
        this.defaultMsg = defaultMsg;
        this.manual = manual;
    }

    /**
     * Create a common message used in the main mini-game and connector plugin.
     *
     * @param path       message path.
     * @param defaultMsg default message for path.
     */
    CommonMessage(String path, Object defaultMsg) {
        this.path = "cm-" + path;
        this.defaultMsg = defaultMsg;
        this.manual = false;
    }

    @Override
    public String toString() {
        return path;
    }

    /**
     * Save this message to a yml file.
     *
     * @param yml              language file where to save.
     * @param pathReplacements placeholders to be replaced in message path.
     * @param value            message value.
     */
    public void addDefault(YamlConfiguration yml, String @NotNull [] pathReplacements, Object value) {
        String path = this.toString();
        for (int i = 0; i < pathReplacements.length; i += 2) {
            path = path.replace(pathReplacements[i], pathReplacements[i + 1]);
        }
        yml.addDefault(path, value);
    }

    /**
     * Save messages that are not {@link #isManual()} to the given yml.
     *
     * @param yml language file where to save.
     */
    public static void saveDefaults(YamlConfiguration yml) {
        for (CommonMessage message : values()) {
            if (message.isManual()) {
                continue;
            }
            yml.addDefault(message.path, message.getDefaultMsg());
        }
    }
}
