# Auto Totem Mod Specification & Architecture

## Overview
**Auto Totem** is a client-side Fabric mod for Minecraft 26.1.x and 26.2 that automatically keeps a Totem of Undying equipped in the player's offhand with granular per-world, singleplayer, and multiplayer toggles.

---

## 1. Core Requirements

1. **Client-Side Only (`"environment": "client"`)**:
   * Runs 100% on the client.
   * Works on singleplayer worlds and multiplayer servers.
2. **Environment & World Isolation (Key Feature)**:
   * **Singleplayer Toggle (`enableSingleplayer`, default: true)**: Activate in singleplayer / local survival worlds.
   * **Multiplayer Toggle (`enableMultiplayer`, default: false)**: Disable on multiplayer servers by default to prevent anti-cheat triggers or comply with server rules.
   * **Per-World / Per-Server Overrides**:
     * Custom whitelist/blacklist rules for specific world saves (`sp_<world_name>`) or server hostnames (`mp_<server_ip>`).
3. **Smart Inventory Swapping (`TotemEquipper`)**:
   * Checks player offhand slot during `ClientTickEvents.END_CLIENT_TICK`.
   * Trigger conditions:
     * When offhand does not hold a totem.
     * Optional health threshold (e.g. only swap when health <= X).
   * Locates `Items.TOTEM_OF_UNDYING` in inventory (searching hotbar first or main inventory).
   * Performs slot swap via `MultiPlayerGameMode.handleInventoryMouseClick` (or slot click packets) with configurable tick delay to avoid anti-cheat flag triggers.
4. **GUI & Configuration**:
   * ModMenu & Cloth Config integration.
   * In-game quick toggle keybind (default: `F7` or configurable).
   * Status HUD indicator (optional on-screen badge showing totem count and whether Auto-Totem is active in the current world).

---

## 2. Technical Architecture

### Package Structure (`net.fayber.autototem`)

```
net.fayber.autototem
├── AutoTotemClient.java              # Client entrypoint, tick listener, keybinds
├── config/
│   ├── AutoTotemConfig.java          # Configuration data model
│   └── ConfigManager.java            # JSON persistence (config/autototem.json)
├── logic/
│   ├── TotemEquipper.java            # Slot scanning and swap packet dispatcher
│   └── EnvironmentChecker.java       # Evaluates if mod should be active for current world/server
├── storage/
│   └── WorldIdResolver.java          # Resolves unique world ID (SP level name vs MP server IP)
└── compat/
    ├── AutoTotemModMenu.java         # ModMenu API integration
    └── AutoTotemClothScreen.java     # Cloth Config GUI
```

---

## 3. Data Models

### `AutoTotemConfig.java`
```java
public class AutoTotemConfig {
    public boolean enabled = true;
    public boolean enableSingleplayer = true;
    public boolean enableMultiplayer = false;
    public List<String> whitelistedWorlds = new ArrayList<>();
    public List<String> blacklistedWorlds = new ArrayList<>();

    public float healthThreshold = 20.0f; // Swap when health <= threshold
    public int swapDelayTicks = 2;        // Ticks between swaps
    public boolean hotbarFirst = true;
    public boolean showHudIndicator = true;
    public int hudX = 10;
    public int hudY = 10;
}
```

---

## 4. Build & Compatibility

* **Target MC Versions**: `26.1.x-fabric` and `26.2-fabric`
* **Java Target**: Java 25 (`options.release = 25`)
* **Loom**: `1.17.19` (unobfuscated 26.x Mojang mappings)
* **License**: GPL-3.0-or-later, Copyright 2026 Fayber.
