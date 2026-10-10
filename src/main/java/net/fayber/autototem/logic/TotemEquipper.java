package net.fayber.autototem.logic;

import net.fayber.autototem.config.AutoTotemConfig;
import net.fayber.autototem.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.Items;

public class TotemEquipper {
    private static final int OFFHAND_BUTTON = 40;
    private static int cooldown = 0;

    public static void onClientTick(Minecraft mc) {
        if (cooldown > 0) {
            cooldown--;
            return;
        }

        LocalPlayer player = mc.player;
        if (player == null || mc.gameMode == null || !player.isAlive() || player.isSpectator()) {
            return;
        }
        // The swap goes through the player inventory menu, so it only makes sense while that one is open.
        if (player.containerMenu != player.inventoryMenu) {
            return;
        }
        if (!WorldFilter.isAllowed(mc)) {
            return;
        }

        AutoTotemConfig config = ConfigManager.get();
        if (player.getHealth() > config.healthThreshold || player.getOffhandItem().is(Items.TOTEM_OF_UNDYING)) {
            return;
        }

        int slot = findTotem(player.getInventory(), config.hotbarFirst);
        if (slot == -1) {
            return;
        }

        // Hotbar slots 0-8 are menu slots 36-44, main inventory slots 9-35 map to themselves.
        int menuSlot = slot < 9 ? slot + 36 : slot;
        mc.gameMode.handleContainerInput(player.inventoryMenu.containerId, menuSlot, OFFHAND_BUTTON,
                ContainerInput.SWAP, player);
        cooldown = config.swapDelayTicks;
    }

    private static int findTotem(Inventory inv, boolean hotbarFirst) {
        int start = hotbarFirst ? 0 : 9;
        for (int i = start; i < Inventory.INVENTORY_SIZE; i++) {
            if (inv.getItem(i).is(Items.TOTEM_OF_UNDYING)) {
                return i;
            }
        }
        for (int i = 0; i < start; i++) {
            if (inv.getItem(i).is(Items.TOTEM_OF_UNDYING)) {
                return i;
            }
        }
        return -1;
    }
}
