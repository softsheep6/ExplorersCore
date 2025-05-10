package me.softsheep6.explorersCore.misc_listeners;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ToggleShulkers implements Listener {

    // disabl shulker boxes from being opened
    @EventHandler
    public void onInventoryOpen (InventoryOpenEvent event) {

        PersistentDataContainer pdc = Bukkit.getWorlds().getFirst().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(ExplorersCore.getPlugin(), "shulkers");
        if (!pdc.has(key)) return;

        if (Boolean.TRUE.equals(pdc.get(key, PersistentDataType.BOOLEAN)) && event.getInventory().getType().equals(InventoryType.SHULKER_BOX))
            event.setCancelled(true);
    }

    // disabl shulker boxes from being damaged (cactus'd, explod'd, etc)
    @EventHandler
    public void onEntityDamage (EntityDamageEvent event) {
        if (event.getEntity().getType() != EntityType.ITEM)
            return;
        Material item = ((Item) event.getEntity()).getItemStack().getType();
        Material[] shulkers = Tag.SHULKER_BOXES.getValues().toArray(new Material[0]);


        PersistentDataContainer pdc = Bukkit.getWorlds().getFirst().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(ExplorersCore.getPlugin(), "shulkers");
        if (!pdc.has(key)) return;

        for (Material i : shulkers) {
            if (Boolean.TRUE.equals(pdc.get(key, PersistentDataType.BOOLEAN)) && item == i)
                event.setCancelled(true);
        }
    }
}
