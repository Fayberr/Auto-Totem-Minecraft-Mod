package net.fayber.autototem.config;

import java.util.ArrayList;
import java.util.List;

public class AutoTotemConfig {
    public boolean enabled = true;
    public boolean enableSingleplayer = true;
    public boolean enableMultiplayer = false;

    // Per-world / server filters
    public List<String> whitelistedWorlds = new ArrayList<>();
    public List<String> blacklistedWorlds = new ArrayList<>();

    // Mechanics
    public float healthThreshold = 20.0f; // Max health to trigger swap (20 = always)
    public int swapDelayTicks = 2;        // Ticks between swaps
    public boolean hotbarFirst = true;    // Scan hotbar before main inventory
    public boolean showHudIndicator = true;
}
