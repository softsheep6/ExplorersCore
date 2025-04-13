package me.softsheep6.explorersCore.loot_tables;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

// commented until brewing shenanigans is done


// SO MUCH STOLEN CODE!!!! WOW!!! ALL CREDITS GO TO https://www.spigotmc.org/threads/help-with-custom-loot-tables.514477/#post-4208461 !!!!
// YOU CANNOT MAKE ME LEARN DATAPACKS PLUGINS ARE ALL I NEED
public class BlazeLoot implements LootTable {
    @NotNull
    @Override
    public Collection<ItemStack> populateLoot(Random random, @NotNull LootContext lootContext) {
        assert lootContext.getKiller() != null;
        int loot = lootContext.getKiller().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOTING);
        double lootingMod = loot * .01;

//        ItemStack inferno = new ItemStack(Material.BLAZE_ROD);
//        List<String> lore = new ArrayList<>();
//        lore.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Used to craft Inferno Block");
//        lore.add(ChatColor.RESET + "" + ChatColor.WHITE + "  10% chance to drop from blazes");
//        lore.add(ChatColor.RESET + "" + ChatColor.WHITE + "  (+1% per looting level)");
//        lore.add("");
//        lore.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "CRAFTING INGREDIENT");
//        ItemMeta infernoMeta = inferno.getItemMeta();
//        assert infernoMeta != null;
//        infernoMeta.setLore(lore);
//        infernoMeta.setDisplayName(ChatColor.RESET + "Inferno Rod");
//        infernoMeta.setRarity(ItemRarity.UNCOMMON);
//        infernoMeta.addEnchant(Enchantment.FLAME, 1, true);
//        infernoMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//        inferno.setItemMeta(infernoMeta);

        final List<ItemStack> items = new ArrayList<>();

        assert random != null;
        if (random.nextDouble() <= (0.1 + lootingMod)) { // 0.04
            items.add(ExplorersCore.getPlugin().inferno);
        }

        return items;
    }

    @Override
    public void fillInventory(@NotNull Inventory inventory, Random random, @NotNull LootContext lootContext) {
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(ExplorersCore.getPlugin(), "inferno_rod_loot");
    }
}
