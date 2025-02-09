package me.softsheep6.explorersCore;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class LightningSword implements Listener {

    @EventHandler void onEntityDamageEntity (EntityDamageByEntityEvent event) {
        Player player;
        if (event.getDamager() instanceof Player)
            player = (Player) event.getDamager();
        else return;

        //ItemStack sword = ExplorersCore.getPlugin().sword;

        if (player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOYALTY) == 1 && player.getInventory().getItemInMainHand().equals(new ItemStack(Material.DIAMOND_SWORD))) {
            if ((Math.random() * 100 < 20)) {
                System.out.println("hit!");
            } else
                System.out.println("miss!");
        }
    }
}