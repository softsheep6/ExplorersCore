package me.softsheep6.explorersCore;

import me.softsheep6.explorersCore.items.craftable.EnrichedBread;
import me.softsheep6.explorersCore.items.craftable.JobApplication;
import me.softsheep6.explorersCore.items.craftable.MiningHammer;
import me.softsheep6.explorersCore.items.event.Crown;
import me.softsheep6.explorersCore.items.event.DragonEgg;
import me.softsheep6.explorersCore.items.event.InfinityTotem;
import me.softsheep6.explorersCore.items.event.LightningSword;
import me.softsheep6.explorersCore.misc_listeners.*;
//import me.softsheep6.explorersCore.misc_listeners.death_listeners.BlazeDeath;
import me.softsheep6.explorersCore.tasks.CheckForEggTask;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import com.jeff_media.armorequipevent.ArmorEquipEvent;

import java.util.ArrayList;
import java.util.List;

public final class ExplorersCore extends JavaPlugin implements Listener {
    private static ExplorersCore plugin;
    public ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);
    public ItemStack crown = new ItemStack(Material.GOLDEN_HELMET);
    public ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
    public ItemStack hammer = new ItemStack(Material.DIAMOND_PICKAXE);
    public ItemStack bread = new ItemStack(Material.BREAD);
    public ItemStack job = new ItemStack(Material.MAP);
    public ItemStack inferno = new ItemStack(Material.BLAZE_ROD);
    public ItemStack infernoblock = new ItemStack(Material.MAGMA_BLOCK);
    public Player playerWithEgg = null;
    @Override
    public void onEnable() {
        System.out.println("Welcome to the Explorers SMP !! Explorers plugin has loaded :thumbsup:");
        plugin = this;

        // registers event handlers! and the armor event thing
        getServer().getPluginManager().registerEvents(new DragonEgg(), this);
        getServer().getPluginManager().registerEvents(new InfinityTotem(), this);
        getServer().getPluginManager().registerEvents(new Crown(), this);
        getServer().getPluginManager().registerEvents(new LightningSword(), this);
        getServer().getPluginManager().registerEvents(new MaceCraftable(), this);
        getServer().getPluginManager().registerEvents(new NetheriteArmorUnequippable(), this);
        getServer().getPluginManager().registerEvents(new PreventGrindstoningItems(), this);
        getServer().getPluginManager().registerEvents(new StringDispenser(), this);
        getServer().getPluginManager().registerEvents(new DisableCrafterCraftingMace(), this);
        getServer().getPluginManager().registerEvents(new MiningHammer(), this);
        getServer().getPluginManager().registerEvents(new EnrichedBread(), this);
        getServer().getPluginManager().registerEvents(new JobApplication(), this);
//        getServer().getPluginManager().registerEvents(new BlazeDeath(), this);
        getServer().getPluginManager().registerEvents(new DisablePlacingInfernoBlock(), this);

        ArmorEquipEvent.registerListener(this);

        // checks if someones holding dragon egg every tick
        new CheckForEggTask(this).runTaskTimer(this, 0, 1);


        // the giant blocks of code below are for each item and are used in their respective classes!!!
        // putting them here is better than putting them in their own classes i think cause otherwise every time
        // their events are run they run Allll those lines again and again whereas here in the onEnable class
        // theyre only run once at server start!


        //totem
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "A totem forged from time itself...");
        lore.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  PASSIVE:" + ChatColor.RESET + " " + ChatColor.WHITE + "Totem does not get consumed after use! (one minute cooldown)");
        lore.add("");
        lore.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "EVENT ITEM");
        ItemMeta totemMeta = totem.getItemMeta();
        assert totemMeta != null;
        totemMeta.addEnchant(Enchantment.MENDING, 1, true);
        totemMeta.setLore(lore);
        totemMeta.setDisplayName(ChatColor.RESET + "Totem of Infinity");
        totemMeta.setRarity(ItemRarity.EPIC);
        totem.setItemMeta(totemMeta);

        //crown
        List<String> lore2 = new ArrayList<>();
        lore2.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "Made out of REAL gold!");
        lore2.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  PASSIVE:" + ChatColor.RESET + " " + ChatColor.WHITE + "Doubles the wearer's maximum hearts! (10 -> 20)");
        lore2.add("");
        lore2.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "EVENT ITEM");
        ItemMeta crownMeta = crown.getItemMeta();
        assert crownMeta != null;
        crownMeta.setLore(lore2);
        crownMeta.setDisplayName(ChatColor.RESET + "Explorer's Crown");
        crownMeta.setUnbreakable(true);
        AttributeModifier helmetArmor = new AttributeModifier((new NamespacedKey(ExplorersCore.getPlugin(), "helmet-armor")), 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD);
        AttributeModifier helmetArmorToughness = new AttributeModifier((new NamespacedKey(ExplorersCore.getPlugin(), "helmet-armor-toughness")), 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD);
        crownMeta.addAttributeModifier(Attribute.ARMOR, helmetArmor);
        crownMeta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS, helmetArmorToughness);
        crownMeta.setEnchantmentGlintOverride(true);
        crownMeta.setRarity(ItemRarity.EPIC);
        crownMeta.addEnchant(Enchantment.LOYALTY, 1, true);
        crownMeta.setCustomModelData(1);
        crown.setItemMeta(crownMeta);

        //sword
        List<String> lore3 = new ArrayList<>();
        lore3.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "Harness the power of the storm...");
        lore3.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  ABILITY:" + ChatColor.RESET + " " + ChatColor.WHITE + "20% chance to strike lightning when attacking!");
        lore3.add(ChatColor.RESET + " " + ChatColor.WHITE + " Lightning attacks do one heart of true damage!");
        lore3.add(ChatColor.RESET + " " + ChatColor.WHITE + " Grants immunity to lightning damage!");
        lore3.add("");
        lore3.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "EVENT ITEM");
        ItemMeta swordMeta = sword.getItemMeta();
        assert swordMeta != null;
        swordMeta.setLore(lore3);
        swordMeta.setDisplayName(ChatColor.RESET + "Lightning Sword");
        swordMeta.setRarity(ItemRarity.EPIC);
        swordMeta.addEnchant(Enchantment.CHANNELING, 1, true);
        sword.setItemMeta(swordMeta);

        // hammer
        List<String> lore4 = new ArrayList<>();
        lore4.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "Brought to you by GregoriousT");
        lore4.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Can mine a 3x3x1 area at once!");
        lore4.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Right click switches between 1x1x1 and 3x3x1");
        lore4.add("");
        lore4.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "CRAFTABLE ITEM");
        ItemMeta hammerMeta = hammer.getItemMeta();
        assert hammerMeta != null;
        hammerMeta.setLore(lore4);
        hammerMeta.setDisplayName(ChatColor.RESET + "Mining Hammer");
        hammerMeta.setRarity(ItemRarity.RARE);
        hammerMeta.addEnchant(Enchantment.POWER, 1, true);
        hammer.setItemMeta(hammerMeta);

        //bread
        List<String> lore5 = new ArrayList<>();
        lore5.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "don't mind the metallic taste that's just the nutrients!!");
        lore5.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Right clicking on a baby villager will instantly turn");
        lore5.add(ChatColor.RESET + "" + ChatColor.WHITE + "  it into an adult!");
        lore5.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Can also be eaten by players!");
        lore5.add("");
        lore5.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "CRAFTABLE ITEM");
        ItemMeta breadMeta = hammer.getItemMeta();
        assert breadMeta != null;
        breadMeta.setLore(lore5);
        breadMeta.setDisplayName(ChatColor.RESET + "Enriched Bread");
        breadMeta.setRarity(ItemRarity.COMMON);
        breadMeta.addEnchant(Enchantment.MENDING, 1, true);
        breadMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        FoodComponent breadFood = breadMeta.getFood();
        breadFood.setNutrition(8);
        System.out.println(breadFood);
        breadMeta.setFood(breadFood);
        System.out.println(breadMeta);
        bread.setItemMeta(breadMeta);

        //job application
        List<String> lore6 = new ArrayList<>();
        lore6.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "unemployment rates drop to ZERO");
        lore6.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Right clicking on an employed villager (that");
        lore6.add(ChatColor.RESET + "" + ChatColor.WHITE + "  hasn't been traded with) resets its job, giving");
        lore6.add(ChatColor.RESET + "" + ChatColor.WHITE + "  it new trades!");
        lore6.add("");
        lore6.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "CRAFTABLE ITEM");
        ItemMeta jobMeta = hammer.getItemMeta();
        assert jobMeta != null;
        jobMeta.setLore(lore6);
        jobMeta.setDisplayName(ChatColor.RESET + "Job Application");
        jobMeta.setRarity(ItemRarity.UNCOMMON);
        jobMeta.addEnchant(Enchantment.MENDING, 1, true);
        jobMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        job.setItemMeta(jobMeta);

        // inferno rod
        List<String> lore7 = new ArrayList<>();
        lore7.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Used to craft Inferno Block");
        lore7.add(ChatColor.RESET + "" + ChatColor.WHITE + "  10% chance to drop from blazes");
        lore7.add(ChatColor.RESET + "" + ChatColor.WHITE + "  (+1% per looting level)");
        lore7.add("");
        lore7.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "CRAFTING INGREDIENT");
        ItemMeta infernoMeta = inferno.getItemMeta();
        assert infernoMeta != null;
        infernoMeta.setLore(lore7);
        infernoMeta.setDisplayName(ChatColor.RESET + "Inferno Rod");
        infernoMeta.setRarity(ItemRarity.UNCOMMON);
        infernoMeta.addEnchant(Enchantment.FLAME, 1, true);
        infernoMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        inferno.setItemMeta(infernoMeta);

        // inferno block
        List<String> lore8 = new ArrayList<>();
        lore8.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Used to brew potions with both increased");
        lore8.add(ChatColor.RESET + "" + ChatColor.WHITE + "  duration and increased strength!");
        lore8.add("");
        lore8.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "CRAFTABLE ITEM");
        ItemMeta infernoblockMeta = infernoblock.getItemMeta();
        assert infernoblockMeta != null;
        infernoblockMeta.setLore(lore8);
        infernoblockMeta.setDisplayName(ChatColor.RESET + "Inferno Block");
        infernoblockMeta.setRarity(ItemRarity.UNCOMMON);
        infernoblockMeta.addEnchant(Enchantment.FLAME, 1, true);
        infernoblockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        infernoblock.setItemMeta(infernoblockMeta);




        // recipes !

        // hammer
        ShapedRecipe hammerRecipe = new ShapedRecipe(new NamespacedKey(this, "hammer"), hammer);
        hammerRecipe.shape("ABA" ," C ", " C ");
        hammerRecipe.setIngredient('A', Material.DIAMOND_BLOCK);
        hammerRecipe.setIngredient('B', Material.NETHERITE_INGOT);
        hammerRecipe.setIngredient('C', Material.BREEZE_ROD);
        Bukkit.addRecipe(hammerRecipe);

        // bread
        ShapedRecipe breadRecipe = new ShapedRecipe(new NamespacedKey(this, "bread"), bread);
        breadRecipe.shape("AAA" ," B ", "AAA");
        breadRecipe.setIngredient('A', Material.WHEAT);
        breadRecipe.setIngredient('B', Material.EMERALD);
        Bukkit.addRecipe(breadRecipe);

        //job
        ShapedRecipe jobRecipe = new ShapedRecipe(new NamespacedKey(this, "job"), job);
        jobRecipe.shape("ABC" ,"DEF", "GHI");
        jobRecipe.setIngredient('A', Material.BREWING_STAND);
        jobRecipe.setIngredient('B', Material.LECTERN);
        jobRecipe.setIngredient('C', Material.GRINDSTONE);
        jobRecipe.setIngredient('D', Material.BLAST_FURNACE);
        jobRecipe.setIngredient('E', Material.WRITABLE_BOOK);
        jobRecipe.setIngredient('F', Material.COMPOSTER);
        jobRecipe.setIngredient('G', Material.BARREL);
        jobRecipe.setIngredient('H', Material.SMITHING_TABLE);
        jobRecipe.setIngredient('I', Material.FLETCHING_TABLE);
        Bukkit.addRecipe(jobRecipe);

        // inferno block
        ShapedRecipe infernoblockRecipe = new ShapedRecipe(new NamespacedKey(this, "infernoblock"), infernoblock);
        infernoblockRecipe.shape("RIG" ,"ILI", "GIR");
        infernoblockRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        infernoblockRecipe.setIngredient('G', Material.GLOWSTONE);
        infernoblockRecipe.setIngredient('L', Material.REDSTONE_LAMP);
        infernoblockRecipe.setIngredient('I', new RecipeChoice.ExactChoice(inferno));
        Bukkit.addRecipe(infernoblockRecipe);



    }

    // commands!! all of these r operator only. the first 4 will give you the item in their names, 5th one
    // toggles whether or not the mace can be crafted
    // also is it just me or is this whole method a little cramped like maybe i should add a little more whitespace or something
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("givetotem")) {
            if (sender instanceof Player player) player.getInventory().addItem(totem);
        } else if (command.getName().equalsIgnoreCase("givecrown")) {
            if (sender instanceof Player player) player.getInventory().addItem(crown);
        } else if (command.getName().equalsIgnoreCase("givesword")) {
            if (sender instanceof Player player) player.getInventory().addItem(sword);
        } else if (command.getName().equalsIgnoreCase("givehammer")) {
            if (sender instanceof Player player) player.getInventory().addItem(hammer);
        } else if (command.getName().equalsIgnoreCase("givebread")) {
            if (sender instanceof Player player) player.getInventory().addItem(bread);
        } else if (command.getName().equalsIgnoreCase("giveapplication")) {
            if (sender instanceof Player player) player.getInventory().addItem(job);
        } else if (command.getName().equalsIgnoreCase("togglemacecraftable")) {
            if (sender instanceof Player player) {
                // uses persistent data containers. i freaking LOVE these things now
                World world = Bukkit.getWorlds().getFirst();
                NamespacedKey key = new NamespacedKey(this, "macecraftable");
                PersistentDataContainer data = world.getPersistentDataContainer();
                if (Boolean.TRUE.equals(data.get(key, PersistentDataType.BOOLEAN))) {
                    data.set(key, PersistentDataType.BOOLEAN, false);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Mace crafting " + ChatColor.GREEN + "enabled!");
                } else {
                    data.set(key, PersistentDataType.BOOLEAN, true);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Mace crafting " + ChatColor.RED + "disabled!");
                }
            }
        }

        return true;
    }

    public static ExplorersCore getPlugin() {
        return plugin;
    }





    // stupid dumb not working WHATEVER begone BANISHED TO THE DEPTHS OF EXPLORERSCORE DOT JAVA
    /* else if (command.getName().equalsIgnoreCase("togglepvp")) {
            if (sender instanceof Player player) {
                World world = Bukkit.getWorlds().getFirst();
                NamespacedKey key = new NamespacedKey(this, "pvp");
                PersistentDataContainer data = world.getPersistentDataContainer();
                if (Boolean.TRUE.equals(data.get(key, PersistentDataType.BOOLEAN))) {
                    data.set(key, PersistentDataType.BOOLEAN, false);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "PVP " + ChatColor.GREEN +"enabled!");
                    world.setPVP(true);
                } else {
                    data.set(key, PersistentDataType.BOOLEAN, true);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "PVP " + ChatColor.RED + "disabled!");
                    world.setPVP(false);
                }
            }
        }
     */
}
