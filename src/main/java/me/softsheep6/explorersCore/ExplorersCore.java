package me.softsheep6.explorersCore;

import me.softsheep6.explorersCore.miscListeners.MaceCraftable;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import com.jeff_media.armorequipevent.ArmorEquipEvent;

import java.util.ArrayList;
import java.util.List;

public final class ExplorersCore extends JavaPlugin implements Listener {
    private static ExplorersCore plugin;
    public ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);
    public ItemStack crown = new ItemStack(Material.GOLDEN_HELMET);
    public ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
    public boolean maceCraftable = true;
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
        ArmorEquipEvent.registerListener(this);

        // does what the thingy says ...
        setMaceCraftable();


        // the giant blocks of code below are for each item and are used in their respective classes!!!
        // putting them here is better than putting them in their own classes i think cause otherwise every time
        // their events are run they run Allll those lines again and again whereas here in the onEnable class
        // theyre only run once at server start!


        //totem
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "A totem forged from time itself...");
        lore.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  PASSIVE:" + ChatColor.RESET + " " + ChatColor.WHITE + "Totem does not get consumed after use! (one minute cooldown)");
        lore.add("");
        lore.add("" + ChatColor.GRAY + ChatColor.ITALIC + "(warning: do not put in grindstone!)");
        ItemMeta totemMeta = totem.getItemMeta();
        assert totemMeta != null;
        totemMeta.addEnchant(Enchantment.MENDING, 1, true);
        totemMeta.setLore(lore);
//        totemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        totemMeta.setDisplayName(ChatColor.RESET + "Totem of Infinity");
        totemMeta.setRarity(ItemRarity.EPIC);
        totem.setItemMeta(totemMeta);

        //crown
        List<String> lore2 = new ArrayList<>();
        lore2.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "Made out of REAL gold!");
        lore2.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  PASSIVE:" + ChatColor.RESET + " " + ChatColor.WHITE + "Doubles the wearer's maximum hearts! (10 -> 20)");
        lore2.add("");
        lore2.add("" + ChatColor.GRAY + ChatColor.ITALIC + "(warning: do not put in grindstone!)");
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
        crown.setItemMeta(crownMeta);

        //sword
        List<String> lore3 = new ArrayList<>();
        lore3.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "Harness the power of the storm...");
        lore3.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  ABILITY:" + ChatColor.RESET + " " + ChatColor.WHITE + "20% chance to strike lightning when attacking!");
        ItemMeta swordMeta = sword.getItemMeta();
        assert swordMeta != null;
        swordMeta.setLore(lore3);
        swordMeta.setDisplayName(ChatColor.RESET + "Lightning Sword");
        swordMeta.setRarity(ItemRarity.EPIC);
        sword.addUnsafeEnchantment(Enchantment.LOYALTY, 1);
        sword.setItemMeta(swordMeta);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("givetotem")) {
            if (sender instanceof Player player) player.getInventory().addItem(totem);
        } else if (command.getName().equalsIgnoreCase("givecrown")) {
            if (sender instanceof Player player) player.getInventory().addItem(crown);
        } else if (command.getName().equalsIgnoreCase("givesword")) {
            if (sender instanceof Player player) player.getInventory().addItem(sword);
        } else if (command.getName().equalsIgnoreCase("togglemacecraftable")) {
            if (!maceCraftable) Bukkit.getWorlds().getFirst().setBlockData(0, 319, 0, Material.LIGHT.createBlockData());
            else Bukkit.getWorlds().getFirst().setBlockData(0, 319, 0, Material.AIR.createBlockData());
        }


        return true;
    }

    public boolean getMaceCraftable() {
        return maceCraftable;
    }
    public void setMaceCraftable() {
        maceCraftable = Bukkit.getWorlds().getFirst().getBlockAt(0, 319, 0).getType().equals(Material.LIGHT);
    }


    public static ExplorersCore getPlugin() {
        return plugin;
    }

}
