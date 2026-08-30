package net.fayber.autototem;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fayber.autototem.config.ConfigManager;
import net.fayber.autototem.logic.TotemEquipper;

public class AutoTotemClient implements ClientModInitializer {
    public static final String MOD_ID = "autototem";

    @Override
    public void onInitializeClient() {
        ConfigManager.load();

        ClientTickEvents.END_CLIENT_TICK.register(TotemEquipper::onClientTick);
    }
}
