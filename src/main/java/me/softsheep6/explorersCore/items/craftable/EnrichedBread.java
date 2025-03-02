package me.softsheep6.explorersCore.items.craftable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

// if player is holding Enriched Bread and right clicks a baby villager
// then set the villager to an adult, consume the bread, and spawn particles and sound !!
// thats it i think
public class EnrichedBread implements Listener {
    @EventHandler void onPlayerInteractEntity (PlayerInteractEntityEvent event) {
        ItemStack playerItem = event.getPlayer().getInventory().getItemInMainHand();
        Entity entity = event.getRightClicked();
        Location loc = entity.getLocation();
        loc.setY(loc.getY()+1);

        if (playerItem.getType().equals(Material.BREAD) && playerItem.containsEnchantment(Enchantment.MENDING) && entity.getType().equals(EntityType.VILLAGER) && !((Ageable)entity).isAdult()) {
            ((Ageable)entity).setAdult();
            playerItem.setAmount(playerItem.getAmount()-1);
            event.getPlayer().getWorld().spawnParticle(Particle.HAPPY_VILLAGER, loc, 20, .5, .5, 1);
            event.getPlayer().playSound(loc, Sound.ENTITY_GENERIC_EAT, 1, 1);
        }
    }
}
