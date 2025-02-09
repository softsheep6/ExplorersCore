package me.softsheep6.explorersCore.miscListeners;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.List;

// checks if the recipe crafted is the mace recipe, and that the mace craftable is set to false, and if so cancels the craft
public class MaceCraftable implements Listener {
    @EventHandler void onCraftItem (CraftItemEvent event) {
        Recipe recipe = event.getRecipe();
        List<Recipe> list = Bukkit.getRecipesFor(new ItemStack(Material.MACE));
        Recipe maceRecipe = list.getFirst();

        if (recipe.equals(maceRecipe) && !ExplorersCore.getPlugin().getMaceCraftable())
            event.setCancelled(true);
    }
}
