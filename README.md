# Auto Totem

Fabric mod for Minecraft **26.2**.

Moves a Totem of Undying into your offhand when the offhand does not already hold
one, so you do not have to do it by hand after a totem pops. It only runs on the
client and does nothing to the server or other players.

## Installation

Needs Fabric Loader and Fabric API. Put the jar in your `mods` folder. Nothing is
needed on the server.

## Behaviour

- Checks every tick. If your offhand holds no totem and you have one in your
  inventory, it swaps it into the offhand.
- It does nothing while another container screen is open, while you are dead or
  while you are a spectator.
- Works in singleplayer by default. Multiplayer is off by default, because some
  servers do not allow automation. Check the rules before turning it on.

## Configuration

Settings are stored in `config/autototem.json`. If Mod Menu is installed there is a
settings screen, which needs Cloth Config.

- `enableSingleplayer`, `enableMultiplayer`: where it is active.
- `healthThreshold`: only swap at or below this health. `20` swaps right away.
- `swapDelayTicks`: ticks to wait after a swap.
- `hotbarFirst`: take totems from the hotbar before the rest of the inventory.
- `whitelistedWorlds`, `blacklistedWorlds`: per world or server overrides, only
  editable in the file. Entries look like `sp_my_world` for a singleplayer world
  (the world folder name) or `mp_play.example.com` for a server (the address you
  typed in). Letters are lowercase and anything other than letters, digits, `.`,
  `_` and `-` becomes `_`. A whitelisted entry is active even when its mode is
  switched off, a blacklisted one never is.

## Building

Needs JDK 25. Run `./gradlew build`.

## License

GPL-3.0-or-later.
