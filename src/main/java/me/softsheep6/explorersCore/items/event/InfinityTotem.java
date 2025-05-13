package me.softsheep6.explorersCore.items.event;

import me.softsheep6.explorersCore.ExplorersCore;
import me.softsheep6.explorersCore.tasks.ReaddItemEffectsTask;
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

    private static Player totemPlayer = null; // this is so the player can be accessed from the effect task (also look its private not public im learning!!!)
    // both methods below are also for effect task
    public static Player getTotemPlayer() {return totemPlayer;}
    public static void setTotemPlayer(Player totemPlayer) {InfinityTotem.totemPlayer = totemPlayer;}

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
            if (player.getInventory().getItemInOffHand().getEnchantmentLevel(Enchantment.MENDING) == 1 && player.getInventory().getItemInOffHand().getType().equals(Material.TOTEM_OF_UNDYING)) {

                player.getInventory().setItemInOffHand(totem);
                player.setCooldown(totem, 1200); //1200
                player.getWorld().playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1, -10);
                totemPlayer = player;
            } else if (player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.MENDING) == 1 && player.getInventory().getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING)) {

                player.getInventory().setItemInMainHand(totem);
                player.setCooldown(totem, 1200); //1200
                player.getWorld().playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1, -10);
                totemPlayer = player;
            }




        // if the Infinity Totem is still on cooldown CANCEL that event. no revival for u !
        } else if (player.getInventory().getItemInOffHand().equals(totem) ||  player.getInventory().getItemInMainHand().equals(totem) && player.getCooldown(totem) != 0) {
            event.setCancelled(true);
            player.setHealth(0.0);
        }

        // if player is wearing crown, giv them the effect back. because for WHATEVR reason totems
        // clear all effects like huh ??? who decided on that being a feature
        // & then also give them better absorption if they used the infinity totem (ie totemPlayer isnt null)
        new ReaddItemEffectsTask(ExplorersCore.getPlugin()).runTaskLater(ExplorersCore.getPlugin(), 1L);

    }
}
