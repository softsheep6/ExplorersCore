package me.softsheep6.explorersCore.miscListeners;

import com.jeff_media.armorequipevent.ArmorEquipEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class NetheriteArmorUnequippable implements Listener {
    @EventHandler void onArmorEquip(ArmorEquipEvent event) {
        ItemStack armor = event.getNewArmorPiece();

        if (armor == null)
            return;

        // if armor equipped is a piece of netherite armor NO! NOT ALLOWED! NO ARMOR FOR U! GET CANCELLD .!!!
        if (armor.equals(new ItemStack(Material.NETHERITE_HELMET))
                || armor.equals(new ItemStack(Material.NETHERITE_LEGGINGS))
                || armor.equals(new ItemStack(Material.NETHERITE_CHESTPLATE))
                || armor.equals(new ItemStack(Material.NETHERITE_BOOTS))) {
            event.setCancelled(true);
        }
    }
}
