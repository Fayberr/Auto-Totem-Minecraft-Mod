package net.fayber.autototem.storage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.server.IntegratedServer;

public class WorldIdResolver {

    public static String resolveCurrentWorldId() {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) {
            return "default";
        }

        IntegratedServer sp = mc.getSingleplayerServer();
        if (sp != null) {
            return "sp_" + sanitize(sp.getWorldData().getLevelName());
        }

        ServerData serverData = mc.getCurrentServer();
        if (serverData != null) {
            return "mp_" + sanitize(serverData.ip);
        }

        return "default";
    }

    public static boolean isSingleplayer() {
        Minecraft mc = Minecraft.getInstance();
        return mc != null && mc.getSingleplayerServer() != null;
    }

    public static boolean isMultiplayer() {
        Minecraft mc = Minecraft.getInstance();
        return mc != null && mc.getCurrentServer() != null && mc.getSingleplayerServer() == null;
    }

    private static String sanitize(String input) {
        if (input == null || input.isBlank()) {
            return "unknown";
        }
        return input.replaceAll("[^a-zA-Z0-9._-]", "_").toLowerCase();
    }
}
