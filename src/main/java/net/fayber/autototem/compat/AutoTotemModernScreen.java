package net.fayber.autototem.compat;

import net.fayber.autototem.config.AutoTotemConfig;
import net.fayber.autototem.config.ConfigManager;
import net.fayber.modernconfig.api.ModernConfigScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/** Modern Config version of the settings screen. Only loaded when Modern Config is installed. */
public final class AutoTotemModernScreen {
    private AutoTotemModernScreen() {}

    public static Screen create(Screen parent) {
        AutoTotemConfig c = ConfigManager.get();
        return ModernConfigScreen.builder(Component.literal("Auto Totem Settings"), parent, ConfigManager::save)

                .category("General")
                .bool("Enabled", () -> c.enabled, v -> c.enabled = v)
                .bool("Singleplayer", () -> c.enableSingleplayer, v -> c.enableSingleplayer = v)
                .tooltip("Equip totems in singleplayer worlds.")
                .bool("Multiplayer", () -> c.enableMultiplayer, v -> c.enableMultiplayer = v)
                .tooltip("Equip totems on servers. Check the server rules first, some forbid automation.")

                .category("Swapping")
                .floatSlider("Health Threshold", () -> c.healthThreshold, v -> c.healthThreshold = v, 1.0f, 20.0f, 1.0f)
                .tooltip("Only swap at or below this health. 20 swaps right away.")
                .intSlider("Delay (ticks)", () -> c.swapDelayTicks, v -> c.swapDelayTicks = v, 0, 20, 1)
                .tooltip("Ticks to wait after a swap.")
                .bool("Hotbar First", () -> c.hotbarFirst, v -> c.hotbarFirst = v)
                .tooltip("Take totems from the hotbar before the rest of the inventory.")

                .build();
    }
}
