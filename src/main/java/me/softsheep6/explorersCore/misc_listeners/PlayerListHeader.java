package me.softsheep6.explorersCore.misc_listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListHeader implements Listener {

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {
        event.getPlayer().setPlayerListHeader(ChatColor.AQUA + "Welcome to Explorers SMP season three :)\n");
    }
}
