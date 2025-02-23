package me.softsheep6.explorersCore;

import me.softsheep6.explorersCore.tasks.ReaddHealthBoostTask;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;

public class InfinityTotem implements Listener {

    @EventHandler void onPlayerResurrect(EntityResurrectEvent event) {
        // VARIABLES !! player, totem, bla bla bla.

        // this part makes sure the entity is a player first otherwise The Errors shall invade
        Entity entity = event.getEntity();
        Player player;
        if (entity instanceof Player)
            player = (Player) entity;
        else
            return;

        ItemStack totem = ExplorersCore.getPlugin().totem;

        // if a totem with mending is used, and it isnt on cooldown, give the player a NEW MENDING TOTEM! in offhand or main hand depending on which is empty
        // (offhand is checked first cause i mean thats where u usually have a totem.) Hopefully this wont replace any items that arent totems lol
        if (player.getCooldown(totem) == 0) {

            // which hand?!?!
            if (player.getInventory().getItemInOffHand().getEnchantmentLevel(Enchantment.MENDING) == 1 && player.getInventory().getItemInOffHand().equals(new ItemStack(Material.TOTEM_OF_UNDYING))) {
                player.getInventory().setItemInOffHand(totem);
                player.setCooldown(totem, 1200); //1200
                // cool sound effect yay
                player.getWorld().playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1, -10);
            } else if (player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.MENDING) == 1 && player.getInventory().getItemInMainHand().equals(new ItemStack(Material.TOTEM_OF_UNDYING))) {
                player.getInventory().setItemInMainHand(totem);
                player.setCooldown(totem, 1200); //1200
                // cool sound effect yay
                player.getWorld().playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1, -10);
            }




        // if the Infinity Totem is still on cooldown CANCEL that event. no revival for u !
        } else if (player.getInventory().getItemInOffHand().equals(totem) ||  player.getInventory().getItemInMainHand().equals(totem) && player.getCooldown(totem) != 0) {
            event.setCancelled(true);
            player.setHealth(0.0);
        }

        // if player is wearing crown, giv them the effect back. because for WHATEVR reason totems
        // clear all effects like huh ??? who decided on that being a feature
        new ReaddHealthBoostTask(ExplorersCore.getPlugin()).runTaskLater(ExplorersCore.getPlugin(), 1L);

    }
}
