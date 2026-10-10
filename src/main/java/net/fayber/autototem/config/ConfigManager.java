package net.fayber.autototem.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fayber.autototem.AutoTotemClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("autototem.json");
    private static AutoTotemConfig config = new AutoTotemConfig();

    public static AutoTotemConfig get() {
        return config;
    }

    public static void load() {
        if (Files.exists(CONFIG_FILE)) {
            try {
                AutoTotemConfig loaded = GSON.fromJson(Files.readString(CONFIG_FILE), AutoTotemConfig.class);
                if (loaded != null) {
                    config = loaded;
                }
            } catch (Exception e) {
                AutoTotemClient.LOGGER.warn("Could not read {}, using defaults", CONFIG_FILE, e);
            }
        }
        save();
    }

    public static void save() {
        AutoTotemConfig c = config;
        c.healthThreshold = Math.max(1.0f, Math.min(20.0f, c.healthThreshold));
        c.swapDelayTicks = Math.max(0, c.swapDelayTicks);
        if (c.whitelistedWorlds == null) {
            c.whitelistedWorlds = new java.util.ArrayList<>();
        }
        if (c.blacklistedWorlds == null) {
            c.blacklistedWorlds = new java.util.ArrayList<>();
        }
        try {
            Files.createDirectories(CONFIG_FILE.getParent());
            Files.writeString(CONFIG_FILE, GSON.toJson(c));
        } catch (IOException e) {
            AutoTotemClient.LOGGER.error("Could not write {}", CONFIG_FILE, e);
        }
    }
}
