package me.softsheep6.explorersCore.misc_listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GodAppleLogger implements Listener {

    // hopefully this makes keeping track of the 5 god apple rule easier !
    @EventHandler
    public void onPlayerConsume (PlayerItemConsumeEvent event) {

        Player p = event.getPlayer();
        Logger log = Bukkit.getLogger();

        if (event.getItem().getType().equals(Material.ENCHANTED_GOLDEN_APPLE)) {
            log.log(Level.INFO, "player " + p.getName() + " has eaten a god apple!");
        }
    }
}
