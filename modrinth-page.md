# Auto Totem - Modrinth page copy

Source of truth for the text on https://modrinth.com/mod/fayber-auto-totem.
Update here first, then run `modrinth copy fayber-auto-totem modrinth-page.md`.

## description

Puts a Totem of Undying in your offhand automatically when it is empty. Client-side.

## body

# Auto Totem

Moves a Totem of Undying from your inventory into your offhand whenever the offhand does not hold one, so a popped totem is replaced right away.

## Features

- Swaps in a totem as soon as the offhand has none.
- Optional health threshold, so it only swaps when you are low.
- Delay between swaps and a choice between hotbar first or inventory first.
- On in singleplayer, off on servers by default. Worlds and servers can be allowed or blocked individually in the config file.
- Settings screen through Mod Menu, using Modern Config if it is installed and Cloth Config otherwise.

## Notes

- Client-side only. Nothing needs to be installed on the server.
- Some servers do not allow automation, so check the rules before enabling it in multiplayer.
- Requires Fabric API. Minecraft 26.3.
- Config file: `config/autototem.json`.
