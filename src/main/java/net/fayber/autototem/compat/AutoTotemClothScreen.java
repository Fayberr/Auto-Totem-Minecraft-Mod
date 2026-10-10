package net.fayber.autototem.compat;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fayber.autototem.config.AutoTotemConfig;
import net.fayber.autototem.config.ConfigManager;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/** Cloth Config version of the settings screen. Only loaded when Cloth Config is installed. */
public final class AutoTotemClothScreen {
    private AutoTotemClothScreen() {}

    public static Screen create(Screen parent) {
        AutoTotemConfig c = ConfigManager.get();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Auto Totem Settings"))
                .setSavingRunnable(ConfigManager::save);
        ConfigEntryBuilder eb = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));
        general.addEntry(eb.startBooleanToggle(Component.literal("Enabled"), c.enabled)
                .setDefaultValue(true)
                .setSaveConsumer(v -> c.enabled = v)
                .build());
        general.addEntry(eb.startBooleanToggle(Component.literal("Singleplayer"), c.enableSingleplayer)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Equip totems in singleplayer worlds."))
                .setSaveConsumer(v -> c.enableSingleplayer = v)
                .build());
        general.addEntry(eb.startBooleanToggle(Component.literal("Multiplayer"), c.enableMultiplayer)
                .setDefaultValue(false)
                .setTooltip(Component.literal("Equip totems on servers. Check the server rules first, some forbid automation."))
                .setSaveConsumer(v -> c.enableMultiplayer = v)
                .build());

        ConfigCategory swap = builder.getOrCreateCategory(Component.literal("Swapping"));
        swap.addEntry(eb.startFloatField(Component.literal("Health Threshold"), c.healthThreshold)
                .setDefaultValue(20.0f)
                .setMin(1.0f).setMax(20.0f)
                .setTooltip(Component.literal("Only swap at or below this health. 20 swaps right away."))
                .setSaveConsumer(v -> c.healthThreshold = v)
                .build());
        swap.addEntry(eb.startIntSlider(Component.literal("Delay (ticks)"), c.swapDelayTicks, 0, 20)
                .setDefaultValue(2)
                .setTooltip(Component.literal("Ticks to wait after a swap."))
                .setSaveConsumer(v -> c.swapDelayTicks = v)
                .build());
        swap.addEntry(eb.startBooleanToggle(Component.literal("Hotbar First"), c.hotbarFirst)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Take totems from the hotbar before the rest of the inventory."))
                .setSaveConsumer(v -> c.hotbarFirst = v)
                .build());

        return builder.build();
    }
}
