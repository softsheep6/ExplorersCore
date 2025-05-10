package me.softsheep6.explorersCore.tasks;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemDetectionTask extends BukkitRunnable implements Listener {

    // i have made a series of great errors and i only hope that i can lessen the consequences of some of them.

    Logger log = Bukkit.getLogger();
    ExplorersCore plugin;
    public ItemDetectionTask(ExplorersCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        int maces = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {

            if (p.getInventory().contains(Material.ENCHANTED_GOLDEN_APPLE, 63)) {
                for (int i = 0; i < 5; i++) log.log(Level.SEVERE, "PLAYER " + p.getName() + " HAS 64 GOD APPLES IN THEIR INVENTORY AT LOCATION " + p.getLocation().getX() + ", " + p.getLocation().getY() + ", " + p.getLocation().getZ() + ", " + p.getLocation().getWorld().getEnvironment());

            }
            if (p.getInventory().contains(Material.MACE, 3)) {
                for (int i = 0; i < 5; i++) log.log(Level.SEVERE, "PLAYER " + p.getName() + " HAS 3 MACES IN THEIR INVENTORY AT LOCATION " + p.getLocation().getX() + ", " + p.getLocation().getY() + ", " + p.getLocation().getZ() + ", " + p.getLocation().getWorld().getEnvironment());

            }
            if (p.getInventory().contains(Material.MACE, 1)) {
                maces++;
            }
            if (p.getInventory().contains(Material.WARDEN_SPAWN_EGG, 1)) {
                for (int i = 0; i < 5; i++) log.log(Level.SEVERE, "PLAYER " + p.getName() + " HAS WARDEN SPAWN EGGS IN THEIR INVENTORY AT LOCATION " + p.getLocation().getX() + ", " + p.getLocation().getY() + ", " + p.getLocation().getZ() + ", " + p.getLocation().getWorld().getEnvironment());
            }

        }

        if (maces > 2) {
            for (int i = 0; i < 5; i++) log.log(Level.SEVERE, "THE COMBINED TOTAL OF PLAYERS WITH MACES IN THEIR INVENTORY IS GREATER THAN TWO");
        }


    }

    // also temporarily disable lightning sword
    // as well as disable warden spawn egging
    @EventHandler
    public void onPlayerInteract (PlayerInteractEvent event) {

        Player p = event.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item.getType().equals(Material.DIAMOND_SWORD) && item.containsEnchantment(Enchantment.CHANNELING)) {
            p.getInventory().setItemInMainHand(null);

            for (int i = 0; i < 5; i++) log.log(Level.SEVERE, "PLAYER " + p.getName() + " HAD A LIGHTNING SWORD IN THEIR INVENTORY AT LOCATION " + p.getLocation().getX() + ", " + p.getLocation().getY() + ", " + p.getLocation().getZ() + ", " + p.getLocation().getWorld().getEnvironment());
        }

        if (item.getType().equals(Material.WARDEN_SPAWN_EGG)) {
            event.setCancelled(true);
            p.getInventory().setItemInMainHand(null);
        }
    }

}