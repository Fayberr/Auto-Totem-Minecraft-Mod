package net.fayber.autototem.logic;

import net.fayber.autototem.config.AutoTotemConfig;
import net.fayber.autototem.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TotemEquipper {
    private static int tickCooldown = 0;

    public static void onClientTick(Minecraft mc) {
        if (tickCooldown > 0) {
            tickCooldown--;
            return;
        }

        LocalPlayer player = mc.player;
        if (player == null || mc.gameMode == null) {
            return;
        }

        if (!EnvironmentChecker.isAutoTotemAllowed()) {
            return;
        }

        AutoTotemConfig config = ConfigManager.get();

        // Check health threshold
        if (player.getHealth() > config.healthThreshold) {
            return;
        }

        // Check if offhand already has a Totem
        ItemStack offhand = player.getOffhandItem();
        if (offhand.getItem() == Items.TOTEM_OF_UNDYING) {
            return;
        }

        // Search inventory for totem
        Inventory inv = player.getInventory();
        int totemSlot = findTotemSlot(inv, config.hotbarFirst);
        if (totemSlot == -1) {
            return;
        }

        // Map inventory index to InventoryMenu container slot
        // Slots 0-8 (hotbar) map to container slots 36-44
        // Slots 9-35 (main inv) map to container slots 9-35
        int containerSlot = (totemSlot < 9) ? (totemSlot + 36) : totemSlot;

        // Perform slot swap with offhand (button 40 in ContainerInput.SWAP swaps with offhand)
        mc.gameMode.handleContainerInput(
                player.inventoryMenu.containerId,
                containerSlot,
                40,
                ContainerInput.SWAP,
                player
        );

        tickCooldown = config.swapDelayTicks;
    }

    private static int findTotemSlot(Inventory inv, boolean hotbarFirst) {
        if (hotbarFirst) {
            for (int i = 0; i < 9; i++) {
                if (inv.getItem(i).getItem() == Items.TOTEM_OF_UNDYING) {
                    return i;
                }
            }
        }

        for (int i = 0; i < inv.getContainerSize(); i++) {
            if (inv.getItem(i).getItem() == Items.TOTEM_OF_UNDYING) {
                return i;
            }
        }
        return -1;
    }
}
