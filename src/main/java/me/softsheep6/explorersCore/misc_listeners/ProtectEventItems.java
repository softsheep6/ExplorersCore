package me.softsheep6.explorersCore.misc_listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;

public class ProtectEventItems implements Listener {
    @EventHandler
    public void onEntityDamage (EntityDamageEvent event) {
        if (event.getEntity().getType() != EntityType.ITEM)
            return;
        ItemStack item = ((Item) event.getEntity()).getItemStack();
        Material itype = item.getType();

        if (itype == Material.DRAGON_EGG
            || (itype == Material.TOTEM_OF_UNDYING && item.containsEnchantment(Enchantment.MENDING))
            || (itype == Material.GOLDEN_HELMET && item.containsEnchantment(Enchantment.LOYALTY))
            || (itype == Material.DIAMOND_SWORD && item.containsEnchantment(Enchantment.CHANNELING))
            || (itype == Material.DIAMOND_AXE && item.containsEnchantment(Enchantment.SWIFT_SNEAK)))
            event.setCancelled(true);
    }

    @EventHandler
    public void onItemDespawn (ItemDespawnEvent event) {
        ItemStack item = event.getEntity().getItemStack();
        Material itype = item.getType();

        if (itype == Material.DRAGON_EGG
                || (itype == Material.TOTEM_OF_UNDYING && item.containsEnchantment(Enchantment.MENDING))
                || (itype == Material.GOLDEN_HELMET && item.containsEnchantment(Enchantment.LOYALTY))
                || (itype == Material.DIAMOND_SWORD && item.containsEnchantment(Enchantment.CHANNELING))
                || (itype == Material.DIAMOND_AXE && item.containsEnchantment(Enchantment.SWIFT_SNEAK)))
            event.setCancelled(true);
    }


}
