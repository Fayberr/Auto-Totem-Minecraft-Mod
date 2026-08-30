package net.fayber.autototem.logic;

import net.fayber.autototem.config.AutoTotemConfig;
import net.fayber.autototem.config.ConfigManager;
import net.fayber.autototem.storage.WorldIdResolver;

public class EnvironmentChecker {

    public static boolean isAutoTotemAllowed() {
        AutoTotemConfig config = ConfigManager.get();
        if (!config.enabled) {
            return false;
        }

        String worldId = WorldIdResolver.resolveCurrentWorldId();

        // Check explicit blacklists/whitelists
        if (config.blacklistedWorlds.contains(worldId)) {
            return false;
        }
        if (config.whitelistedWorlds.contains(worldId)) {
            return true;
        }

        // Check singleplayer vs multiplayer rules
        if (WorldIdResolver.isSingleplayer()) {
            return config.enableSingleplayer;
        }

        if (WorldIdResolver.isMultiplayer()) {
            return config.enableMultiplayer;
        }

        return false;
    }
}
