package me.softsheep6.explorersCore.miscListeners;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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

        // uses persistent data container from main class!
        // also Very short if statement:
        if (recipe.getResult().equals(maceRecipe.getResult()) && Boolean.TRUE.equals(Bukkit.getWorlds().getFirst().getPersistentDataContainer().get(new NamespacedKey(ExplorersCore.getPlugin(), "macecraftable"), PersistentDataType.BOOLEAN))) {
            event.setCancelled(true);
        }
    }
}
