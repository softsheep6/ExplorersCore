package me.softsheep6.explorersCore.items.craftable;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

// if the player is holding a job application that is off cooldown
// and the player right clicks an employed villager with 0 trading xp
// then reset the villager's trades, play sound/spawn particles, and set the cooldown to 0.5 seconds !
public class JobApplication implements Listener {
    @EventHandler
    void onPlayerInteractEntity (PlayerInteractEntityEvent event) {
        ItemStack playerItem = event.getPlayer().getInventory().getItemInMainHand();
        Entity entity = event.getRightClicked();

        if (playerItem.getType().equals(Material.MAP) && playerItem.containsEnchantment(Enchantment.MENDING) && event.getPlayer().getCooldown(Material.MAP) == 0) {

            if (entity.getType().equals(EntityType.VILLAGER) && !((Villager)entity).getProfession().equals(Villager.Profession.NONE) && ((Villager)entity).getVillagerExperience() == 0) {
                Villager villager = (Villager)entity;
                Villager.Profession job = villager.getProfession();
                villager.setProfession(Villager.Profession.NONE);
                villager.setProfession(job);

                villager.getWorld().playSound(villager, Sound.BLOCK_GRINDSTONE_USE, 1, 1);
                villager.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, villager.getLocation(), 20, .5, .5, 1);
                event.getPlayer().setCooldown(Material.MAP, 10);
            }
        }
    }


    // this just cancels the default map functionality for job applications
    // (since the item is an empty map and otherwise itd create a filled map on right click)
    @EventHandler
    void onPlayerInteract (PlayerInteractEvent event) {
        if (event.getItem() == null)
            return;

        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                && event.getItem().getType() == Material.MAP
                && event.getItem().containsEnchantment(Enchantment.MENDING)) {
            event.setCancelled(true);
        }
    }
}
