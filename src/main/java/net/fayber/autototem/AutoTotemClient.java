package net.fayber.autototem;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fayber.autototem.config.ConfigManager;
import net.fayber.autototem.logic.TotemEquipper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoTotemClient implements ClientModInitializer {
    public static final String MOD_ID = "autototem";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        ConfigManager.load();
        ClientTickEvents.END_CLIENT_TICK.register(TotemEquipper::onClientTick);
    }
}
