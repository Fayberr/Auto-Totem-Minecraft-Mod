package net.fayber.autototem.logic;

import net.fayber.autototem.config.AutoTotemConfig;
import net.fayber.autototem.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

import java.util.Locale;

/** Decides whether the current world or server is allowed to auto-equip. */
public final class WorldFilter {
    private WorldFilter() {}

    public static boolean isAllowed(Minecraft mc) {
        AutoTotemConfig config = ConfigManager.get();
        if (!config.enabled) {
            return false;
        }

        boolean singleplayer = mc.getSingleplayerServer() != null;
        ServerData server = mc.getCurrentServer();
        if (!singleplayer && server == null) {
            return false;
        }

        String id = singleplayer
                ? "sp_" + sanitize(mc.getSingleplayerServer().getWorldData().getLevelName())
                : "mp_" + sanitize(server.ip);
        if (config.blacklistedWorlds.contains(id)) {
            return false;
        }
        if (config.whitelistedWorlds.contains(id)) {
            return true;
        }
        return singleplayer ? config.enableSingleplayer : config.enableMultiplayer;
    }

    private static String sanitize(String input) {
        if (input == null || input.isBlank()) {
            return "unknown";
        }
        return input.replaceAll("[^a-zA-Z0-9._-]", "_").toLowerCase(Locale.ROOT);
    }
}
