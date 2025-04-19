package me.softsheep6.explorersCore.misc_listeners;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.persistence.PersistentDataType;
import java.util.List;

// checks if the recipe crafted is the mace recipe, and that the mace craftable is set to false, and if so cancels the craft
public class MaceCraftable implements Listener {
    @EventHandler void onCraftItem (CraftItemEvent event) {
        Recipe recipe = event.getRecipe();
        List<Recipe> list = Bukkit.getRecipesFor(new ItemStack(Material.MACE));
        Recipe maceRecipe = list.getFirst();
        World world = Bukkit.getWorlds().getFirst();

        // uses persistent data container from main class!
        // also Very short if statement:
        if (recipe.getResult().equals(maceRecipe.getResult())) {
            if (Boolean.TRUE.equals(world.getPersistentDataContainer().get(new NamespacedKey(ExplorersCore.getPlugin(), "macecraftable"), PersistentDataType.BOOLEAN)))
                event.setCancelled(true);
            else {
                world.getPersistentDataContainer().set(new NamespacedKey(ExplorersCore.getPlugin(), "macecraftable"), PersistentDataType.BOOLEAN, Boolean.TRUE);
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "!! THE MACE HAS BEEN CRAFTED !!");
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "!! THE MACE HAS BEEN CRAFTED !!");
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "!! THE MACE HAS BEEN CRAFTED !!");
                event.getWhoClicked().sendMessage(ChatColor.DARK_GRAY + "congrats on the mace :)");
            }
        }


    }
}