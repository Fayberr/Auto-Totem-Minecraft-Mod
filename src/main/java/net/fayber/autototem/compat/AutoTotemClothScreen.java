package net.fayber.autototem.compat;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fayber.autototem.config.AutoTotemConfig;
import net.fayber.autototem.config.ConfigManager;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class AutoTotemClothScreen {
    private AutoTotemClothScreen() {}

    public static Screen create(Screen parent) {
        AutoTotemConfig config = ConfigManager.get();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Auto Totem Settings"))
                .setSavingRunnable(ConfigManager::save);

        ConfigEntryBuilder eb = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));
        general.addEntry(eb.startBooleanToggle(Component.literal("Master Toggle"), config.enabled)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Global enable/disable toggle."))
                .setSaveConsumer(val -> config.enabled = val)
                .build());

        ConfigCategory rules = builder.getOrCreateCategory(Component.literal("Environment Rules"));
        rules.addEntry(eb.startBooleanToggle(Component.literal("Enable in Singleplayer"), config.enableSingleplayer)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Automatically equip totems in local singleplayer worlds."))
                .setSaveConsumer(val -> config.enableSingleplayer = val)
                .build());

        rules.addEntry(eb.startBooleanToggle(Component.literal("Enable in Multiplayer"), config.enableMultiplayer)
                .setDefaultValue(false)
                .setTooltip(Component.literal("Equip totems on multiplayer servers (Disabled by default)."))
                .setSaveConsumer(val -> config.enableMultiplayer = val)
                .build());

        ConfigCategory mechanics = builder.getOrCreateCategory(Component.literal("Mechanics"));
        mechanics.addEntry(eb.startFloatField(Component.literal("Health Threshold"), config.healthThreshold)
                .setDefaultValue(20.0f)
                .setMin(1.0f).setMax(20.0f)
                .setTooltip(Component.literal("Only swap totem if player health is at or below this value (20 = always)."))
                .setSaveConsumer(val -> config.healthThreshold = val)
                .build());

        mechanics.addEntry(eb.startIntSlider(Component.literal("Swap Delay Ticks"), config.swapDelayTicks, 0, 20)
                .setDefaultValue(2)
                .setTooltip(Component.literal("Ticks between consecutive swaps to avoid rate limit flags."))
                .setSaveConsumer(val -> config.swapDelayTicks = val)
                .build());

        mechanics.addEntry(eb.startBooleanToggle(Component.literal("Hotbar First"), config.hotbarFirst)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Prioritize using totems from hotbar before main inventory."))
                .setSaveConsumer(val -> config.hotbarFirst = val)
                .build());

        return builder.build();
    }
}
