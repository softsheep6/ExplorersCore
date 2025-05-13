package me.softsheep6.explorersCore.items.craftable;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.jeff_media.armorequipevent.ArmorEquipEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

// i love gtnh
public class PistonBoots implements Listener {

    @EventHandler
    public void onPlayerEquip (ArmorEquipEvent event) {

        Player p = event.getPlayer();
        ItemStack newItem = event.getNewArmorPiece();
        ItemStack oldItem = event.getOldArmorPiece();
        PotionEffect speedEff = new PotionEffect(PotionEffectType.SPEED, -1, 1);
        PotionEffect jumpEff = new PotionEffect(PotionEffectType.JUMP_BOOST, -1, 1);


        if (newItem != null && newItem.getType().equals(Material.IRON_BOOTS) && newItem.containsEnchantment(Enchantment.POWER)) {
            p.addPotionEffect(speedEff);
            p.addPotionEffect(jumpEff);
            p.getAttribute(Attribute.STEP_HEIGHT).setBaseValue(p.getAttribute(Attribute.STEP_HEIGHT).getBaseValue()*2);
        }
        if (oldItem != null && oldItem.getType().equals(Material.IRON_BOOTS) && oldItem.containsEnchantment(Enchantment.POWER)) {
            p.removePotionEffect(PotionEffectType.SPEED);
            p.removePotionEffect(PotionEffectType.JUMP_BOOST);
            p.getAttribute(Attribute.STEP_HEIGHT).setBaseValue(p.getAttribute(Attribute.STEP_HEIGHT).getDefaultValue());
        }
    }

    // clears stuff if boots break
    @EventHandler
    public void onItemBreak (PlayerItemBreakEvent event) {

        Player p = event.getPlayer();
        ItemStack item = event.getBrokenItem();

        if (item.getType().equals(Material.IRON_BOOTS) && item.containsEnchantment(Enchantment.POWER)) {
            p.removePotionEffect(PotionEffectType.SPEED);
            p.removePotionEffect(PotionEffectType.JUMP_BOOST);
            p.getAttribute(Attribute.STEP_HEIGHT).setBaseValue(p.getAttribute(Attribute.STEP_HEIGHT).getDefaultValue());
        }
    }
}
