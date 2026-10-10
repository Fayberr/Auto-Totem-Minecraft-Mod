package net.fayber.autototem.config;

import java.util.ArrayList;
import java.util.List;

public class AutoTotemConfig {
    public boolean enabled = true;
    public boolean enableSingleplayer = true;
    public boolean enableMultiplayer = false;

    // Entries are "sp_<world folder name>" or "mp_<server address>", lowercase, anything but
    // letters, digits and . _ - replaced by _. A whitelisted entry works even when its mode is off.
    public List<String> whitelistedWorlds = new ArrayList<>();
    public List<String> blacklistedWorlds = new ArrayList<>();

    public float healthThreshold = 20.0f; // swap only at or below this health, 20 means always
    public int swapDelayTicks = 2;
    public boolean hotbarFirst = true;
}
