package me.softsheep6.explorersCore;

import com.jeff_media.armorequipevent.ArmorEquipEvent;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Crown implements Listener {

    @EventHandler void onArmorEquip(ArmorEquipEvent event) {
        // the basics yk
        Player player = event.getPlayer();
        PotionEffect eff = new PotionEffect(PotionEffectType.HEALTH_BOOST, -1, 4);

        // if armor has loyalty (aka The Crown)give health boost
        // if it doesnt take that crap away
        if (event.getNewArmorPiece() != null && event.getNewArmorPiece().getEnchantmentLevel(Enchantment.LOYALTY) == 1) {
            eff.apply(player);
        }
        if (event.getOldArmorPiece() != null && event.getOldArmorPiece().getEnchantmentLevel(Enchantment.LOYALTY) == 1) {
            player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
        }
    }
}