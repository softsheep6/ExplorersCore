package me.softsheep6.explorersCore;

import me.softsheep6.explorersCore.blocks.HeatedBrewingStand;
import me.softsheep6.explorersCore.commands.NameColor;
import me.softsheep6.explorersCore.commands.SpawnProtection;
import me.softsheep6.explorersCore.items.craftable.*;
import me.softsheep6.explorersCore.items.event.*;
import me.softsheep6.explorersCore.misc_listeners.*;
import me.softsheep6.explorersCore.misc_listeners.death_listeners.BlazeDeath;
import me.softsheep6.explorersCore.tasks.GiveEventItemEffectsTask;
//import me.softsheep6.explorersCore.tasks.ItemDetectionTask;
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
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import com.jeff_media.armorequipevent.ArmorEquipEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public final class ExplorersCore extends JavaPlugin implements Listener {
    private static ExplorersCore plugin;
    public ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);
    public ItemStack crown = new ItemStack(Material.GOLDEN_HELMET);
    public ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
    public ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
    public ItemStack hammer = new ItemStack(Material.DIAMOND_PICKAXE);
    public ItemStack bread = new ItemStack(Material.BREAD);
    public ItemStack job = new ItemStack(Material.MAP);
    public ItemStack inferno = new ItemStack(Material.BLAZE_ROD);
    public ItemStack infernoBlock = new ItemStack(Material.MAGMA_BLOCK);
    public ItemStack combatPotion = new ItemStack(Material.SPLASH_POTION);
    public ItemStack commercePotion = new ItemStack(Material.SPLASH_POTION);
    public ItemStack certificate = new ItemStack(Material.MOJANG_BANNER_PATTERN);
    public ItemStack parachute = new ItemStack(Material.WHITE_CARPET);
    public ItemStack pistonBoots = new ItemStack(Material.IRON_BOOTS);
    public ItemStack hasteHammer = new ItemStack(Material.NETHERITE_PICKAXE);
    public ItemStack wardenBow = new ItemStack(Material.BOW);
    public ItemStack midasRod = new ItemStack(Material.FISHING_ROD);
    public ItemStack goldenString = new ItemStack(Material.STRING);
    public ItemStack lumberAxe = new ItemStack(Material.DIAMOND_AXE);
    public Player playerWithEgg = null;
    public Player playerWithAxe = null;
    @Override
    public void onEnable() {
        this.getLogger().log(Level.INFO, "Welcome to the Explorers SMP !! Explorers plugin has loaded :thumbsup:");
        plugin = this;

        // registers event handlers! and the armor event thing
        getServer().getPluginManager().registerEvents(new DragonEgg(), this);
        getServer().getPluginManager().registerEvents(new InfinityTotem(), this);
        getServer().getPluginManager().registerEvents(new Crown(), this);
        getServer().getPluginManager().registerEvents(new LightningSword(), this);
        getServer().getPluginManager().registerEvents(new ToggleMaceCraftable(), this);
        getServer().getPluginManager().registerEvents(new NetheriteArmorUnequippable(), this);
        getServer().getPluginManager().registerEvents(new PreventGrindstoningItems(), this);
        getServer().getPluginManager().registerEvents(new StringDispenser(), this);
        getServer().getPluginManager().registerEvents(new DisableCrafterCraftingMace(), this);
        getServer().getPluginManager().registerEvents(new MiningHammer(), this);
        getServer().getPluginManager().registerEvents(new EnrichedBread(), this);
        getServer().getPluginManager().registerEvents(new JobApplication(), this);
        getServer().getPluginManager().registerEvents(new AxeOfSwiftness(), this);
        getServer().getPluginManager().registerEvents(new ToggleEndPortal(), this);
        getServer().getPluginManager().registerEvents(new BlazeDeath(), this);
        getServer().getPluginManager().registerEvents(new ProtectEventItems(), this);
        getServer().getPluginManager().registerEvents(new ProtectSpawnPlatform(), this);
        getServer().getPluginManager().registerEvents(new ToggleEnderPearls(), this);
        getServer().getPluginManager().registerEvents(new HeatedBrewingStand(), this);
        getServer().getPluginManager().registerEvents(new PlayerListHeader(), this);
        //getServer().getPluginManager().registerEvents(new TintedGlassHideBeaconBeams(), this);
        getServer().getPluginManager().registerEvents(new NameColor(), this);
        getServer().getPluginManager().registerEvents(new Parachute(), this);
        getServer().getPluginManager().registerEvents(new DragonFightMessages(), this);
        //getServer().getPluginManager().registerEvents(new ItemDetectionTask(this), this);
        getServer().getPluginManager().registerEvents(new PistonBoots(), this);
        getServer().getPluginManager().registerEvents(new ToggleShulkers(), this);
        getServer().getPluginManager().registerEvents(new CatRainbowCollar(), this);
        getServer().getPluginManager().registerEvents(new GodAppleLogger(), this);
        getServer().getPluginManager().registerEvents(new HasteHammer(), this);
        getServer().getPluginManager().registerEvents(new WardenBow(), this);
        getServer().getPluginManager().registerEvents(new CustomFishingStuff(), this);
        getServer().getPluginManager().registerEvents(new LumberAxe(), this);
        getServer().getPluginManager().registerEvents(new LightningDragonFight(), this);

        ArmorEquipEvent.registerListener(this);


        // register commands
        getCommand("spawnprotection").setExecutor(new SpawnProtection());
        getCommand("namecolor").setExecutor(new NameColor());

        // checks for excessive amounts of certain items every second. im going to cry
        //new ItemDetectionTask(this).runTaskTimer(this, 0, 20);

        // checks if someones holding dragon egg every tick
        new GiveEventItemEffectsTask(this).runTaskTimer(this, 0, 1);

        // set pvp depending on /togglepvp command
        World world = Bukkit.getWorlds().getFirst();
        NamespacedKey key = new NamespacedKey(this, "pvp");
        PersistentDataContainer data = world.getPersistentDataContainer();
        if (Boolean.TRUE.equals(data.get(key, PersistentDataType.BOOLEAN)))
            world.setPVP(false);


        // the giant blocks of code below are for each item and are used in their respective classes!!!
        // putting them here is better than putting them in their own classes i think cause otherwise every time
        // their events are run they run Allll those lines again and again whereas here in the onEnable class
        // theyre only run once at server start!
        // ^ this guy is so stupid just dont put them in the eventhandler smh
        // someday i will actually move these into their own classes...........


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
        lore3.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  PASSIVE:" + ChatColor.RESET + " " + ChatColor.WHITE + "Grants immunity to lightning damage!");
        lore3.add("");
        lore3.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "EVENT ITEM");
        ItemMeta swordMeta = sword.getItemMeta();
        assert swordMeta != null;
        swordMeta.setLore(lore3);
        swordMeta.setDisplayName(ChatColor.RESET + "Lightning Sword");
        swordMeta.setRarity(ItemRarity.EPIC);
        swordMeta.addEnchant(Enchantment.CHANNELING, 1, true);
        sword.setItemMeta(swordMeta);

        // axe
        List<String> lore9 = new ArrayList<>();
        lore9.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "Run like the wind...");
        lore9.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  ABILITY:" + ChatColor.RESET + " " + ChatColor.WHITE + "Right click to dash forward!");
        lore9.add(ChatColor.RESET + " " + ChatColor.WHITE + " Dashing has a 4 second cooldown");
        lore9.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  PASSIVE:" + ChatColor.RESET + " " + ChatColor.WHITE + "Grants Speed II while holding!");
        lore9.add("");
        lore9.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "EVENT ITEM");
        ItemMeta axeMeta = axe.getItemMeta();
        assert axeMeta != null;
        axeMeta.setLore(lore9);
        axeMeta.setDisplayName(ChatColor.RESET + "Axe of Swiftness");
        axeMeta.setRarity(ItemRarity.EPIC);
        axeMeta.addEnchant(Enchantment.SWIFT_SNEAK, 1, true);
        axe.setItemMeta(axeMeta);

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
        breadMeta.setFood(breadFood);
        bread.setItemMeta(breadMeta);

        //job application
        List<String> lore6 = new ArrayList<>();
        lore6.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "Unemployment rates drop to ZERO");
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
        lore8.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Can be placed beneath a brewing stand");
        lore8.add(ChatColor.RESET + "" + ChatColor.WHITE + "  to convert it to a Heated Brewing Stand!");
        lore8.add("");
        lore8.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "CRAFTABLE ITEM");
        ItemMeta infernoBlockMeta = infernoBlock.getItemMeta();
        assert infernoBlockMeta != null;
        infernoBlockMeta.setLore(lore8);
        infernoBlockMeta.setDisplayName(ChatColor.RESET + "Inferno Block");
        infernoBlockMeta.setRarity(ItemRarity.UNCOMMON);
        infernoBlockMeta.addEnchant(Enchantment.FLAME, 1, true);
        infernoBlockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        infernoBlock.setItemMeta(infernoBlockMeta);

        // potion of combat
        List<String> lore10 = new ArrayList<>();
        lore10.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Combines the effects of multiple combat");
        lore10.add(ChatColor.RESET + "" + ChatColor.WHITE + "  related potions! Lasts for 3 minutes.");
        lore10.add("");
        lore10.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "CRAFTABLE ITEM");
        PotionMeta combatPotionMeta = (PotionMeta) combatPotion.getItemMeta();
        assert combatPotionMeta != null;
        combatPotionMeta.setLore(lore10);
        combatPotionMeta.setDisplayName(ChatColor.RESET + "Potion of Combat");
        combatPotionMeta.setRarity(ItemRarity.UNCOMMON);
        combatPotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 3600, 0), true);
        combatPotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.STRENGTH, 3600, 1), true);
        combatPotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 3600, 0), true);
        combatPotionMeta.setColor(Color.MAROON);
        combatPotion.setItemMeta(combatPotionMeta);

        // potion of commerce
        List<String> lore11 = new ArrayList<>();
        lore11.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Grants villager economy insider knowledge...");
        lore11.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Lasts for 30 minutes.");
        lore11.add("");
        lore11.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "CRAFTABLE ITEM");
        PotionMeta commercePotionMeta = (PotionMeta) commercePotion.getItemMeta();
        assert commercePotionMeta != null;
        commercePotionMeta.setLore(lore11);
        commercePotionMeta.setDisplayName(ChatColor.RESET + "Potion of Commerce");
        commercePotionMeta.setRarity(ItemRarity.COMMON);
        commercePotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 36000, 2), true);
        commercePotionMeta.setColor(Color.LIME);
        commercePotion.setItemMeta(commercePotionMeta);

        // certificate of not being a noob anymore ........
        // secret item ooooo its another gregtech reference wow
        // i hope that someone makes this!
        List<String> lore12 = new ArrayList<>();
        lore12.add(ChatColor.GRAY + "Challenge Accepted!");
        ItemMeta certificateMeta = certificate.getItemMeta();
        assert certificateMeta != null;
        certificateMeta.setLore(lore12);
        certificateMeta.setDisplayName(ChatColor.RESET + "Certificate of Not Being a Noob Anymore");
        certificateMeta.setRarity(ItemRarity.EPIC);
        certificate.setItemMeta(certificateMeta);
        ShapelessRecipe certificateRecipe = new ShapelessRecipe(new NamespacedKey(this, "certficiate"), certificate);
        certificateRecipe.addIngredient(9, Material.NETHERITE_BLOCK);
        Bukkit.addRecipe(certificateRecipe);

        // parachute
        List<String> lore13 = new ArrayList<>();
        lore13.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "basically just elytra but worse");
        lore13.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Right click to glide! Firework boosts");
        lore13.add(ChatColor.RESET + "" + ChatColor.WHITE + "  are too powerful and will damage the ");
        lore13.add(ChatColor.RESET + "" + ChatColor.WHITE + "  parachute unfortunately (2 minute cooldown) ");
        lore13.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Must also be held in main hand while gliding! ");
        lore13.add("");
        lore13.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "CRAFTABLE ITEM");
        ItemMeta parachuteMeta = parachute.getItemMeta();
        assert parachuteMeta != null;
        parachuteMeta.setLore(lore13);
        parachuteMeta.setDisplayName(ChatColor.RESET + "Parachute");
        parachuteMeta.setRarity(ItemRarity.COMMON);
        parachuteMeta.addEnchant(Enchantment.FEATHER_FALLING, 1, true);
        parachuteMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        parachute.setItemMeta(parachuteMeta);

        // piston boots
        List<String> lore14 = new ArrayList<>();
        lore14.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "Now without the annoying piston sound every time you jump!!");
        lore14.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Gives Speed II and Jump Boost II!");
        lore14.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Will also double your max step height,");
        lore14.add(ChatColor.RESET + "" + ChatColor.WHITE + "  allowing you to climb 1 block tall heights");
        lore14.add(ChatColor.RESET + "" + ChatColor.WHITE + "  without jumping!");
        lore14.add("");
        lore14.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "CRAFTABLE ITEM");
        ItemMeta pistonBootsMeta = pistonBoots.getItemMeta();
        assert pistonBootsMeta != null;
        pistonBootsMeta.setLore(lore14);
        pistonBootsMeta.setDisplayName(ChatColor.RESET + "Piston Boots");
        pistonBootsMeta.setRarity(ItemRarity.COMMON);
        pistonBootsMeta.addEnchant(Enchantment.POWER, 1, true);
        pistonBoots.setItemMeta(pistonBootsMeta);

        // haste hammer
        List<String> lore15 = new ArrayList<>();
        lore15.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "SPEEDDD");
        lore15.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Can mine a 3x3x1 area at once!");
        lore15.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Right click switches between 1x1x1 and 3x3x1");
        lore15.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Shift right click to activate Haste VIII");
        lore15.add(ChatColor.RESET + "" + ChatColor.WHITE + "  for 10 seconds! (30 second cooldown)");
        lore15.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Attack speed will not be increased, only");
        lore15.add(ChatColor.RESET + "" + ChatColor.WHITE + "  mining speed!");
        lore15.add("");
        lore15.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "EVENT ITEM");
        ItemMeta hasteHammerMeta = hasteHammer.getItemMeta();
        assert hasteHammerMeta != null;
        hasteHammerMeta.setLore(lore15);
        hasteHammerMeta.setDisplayName(ChatColor.RESET + "Haste Hammer");
        hasteHammerMeta.setRarity(ItemRarity.EPIC);
        hasteHammerMeta.addEnchant(Enchantment.POWER, 1, true);
        hasteHammerMeta.addEnchant(Enchantment.QUICK_CHARGE, 1, true);
        hasteHammer.setItemMeta(hasteHammerMeta);

        // warden bow
        List<String> lore16 = new ArrayList<>();
        lore16.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "Infused with pure sculk essence, straight");
        lore16.add(ChatColor.AQUA + "" + ChatColor.ITALIC + " from the heart of a Warden");
        lore16.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  ABILITY:" + ChatColor.RESET + " " + ChatColor.WHITE + "  Left click to fire a sonically");
        lore16.add(ChatColor.RESET + "" + ChatColor.WHITE + " charged shriek! Has a minimum range of");
        lore16.add(ChatColor.RESET + "" + ChatColor.WHITE + " 5 blocks, and a maximum range of 30");
        lore16.add(ChatColor.RESET + "" + ChatColor.WHITE + " blocks. (15 second cooldown)");
        lore16.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  PASSIVE:" + ChatColor.RESET + " " + ChatColor.WHITE + "  Arrows will inflict darkness on");
        lore16.add(ChatColor.RESET + "" + ChatColor.WHITE + " hit! Lasts for 6 seconds.");
        lore16.add("");
        lore16.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "EVENT ITEM");
        ItemMeta wardenBowMeta = wardenBow.getItemMeta();
        assert wardenBowMeta != null;
        wardenBowMeta.setLore(lore16);
        wardenBowMeta.setDisplayName(ChatColor.RESET + "Warden Bow");
        wardenBowMeta.setRarity(ItemRarity.EPIC);
        wardenBowMeta.addEnchant(Enchantment.IMPALING, 1, true);
        wardenBow.setItemMeta(wardenBowMeta);

        // midas rod
        List<String> lore17 = new ArrayList<>();
        lore17.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "Transmutate the seas to pure gold...");
        lore17.add(ChatColor.RESET + "" + ChatColor.WHITE + "Fishing with this rod has an altered, gold only");
        lore17.add(ChatColor.RESET + "" + ChatColor.WHITE + "loot table! Drops can range from gold ingots to,");
        lore17.add(ChatColor.RESET + "" + ChatColor.WHITE + "very rarely, god apples!");
        lore17.add(ChatColor.RESET + "" + ChatColor.WHITE + "The rod is also UNBREAKABLE due to its goldness!");
        lore17.add("");
        lore17.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "CRAFTABLE ITEM");
        ItemMeta midasRodMeta = midasRod.getItemMeta();
        assert midasRodMeta != null;
        midasRodMeta.setLore(lore17);
        midasRodMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + "Midas' Rod");
        midasRodMeta.addEnchant(Enchantment.FORTUNE, 1, true);
        midasRodMeta.setUnbreakable(true);
        CustomModelDataComponent midasRodCMD = midasRodMeta.getCustomModelDataComponent();
        midasRodCMD.setStrings(List.of("midasrod"));
        midasRodMeta.setCustomModelDataComponent(midasRodCMD);
        midasRod.setItemMeta(midasRodMeta);

        // golden string
        List<String> lore18 = new ArrayList<>();
        lore18.add(ChatColor.RESET + "" + ChatColor.WHITE + "Used to craft the " + ChatColor.GOLD + "Midas' Rod!");
        lore18.add("");
        lore18.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "CRAFTING INGREDIENT");
        ItemMeta goldenStringMeta = goldenString.getItemMeta();
        assert goldenStringMeta != null;
        goldenStringMeta.setLore(lore18);
        goldenStringMeta.setDisplayName(ChatColor.RESET + "Golden String");
        goldenStringMeta.setRarity(ItemRarity.UNCOMMON);
        goldenStringMeta.addEnchant(Enchantment.FLAME, 1, true);
        goldenStringMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        goldenString.setItemMeta(goldenStringMeta);

        // lumber axe
        List<String> lumberAxeLore = new ArrayList<>();
        lumberAxeLore.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "The trees' arch nemesis");
        lumberAxeLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Can chop down an entire tree at once!");
        lumberAxeLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "  Shift while mining to only mine 1 log.");
        lumberAxeLore.add("");
        lumberAxeLore.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "CRAFTABLE ITEM");
        ItemMeta lumberAxeMeta = lumberAxe.getItemMeta();
        assert lumberAxeMeta != null;
        lumberAxeMeta.setLore(lumberAxeLore);
        lumberAxeMeta.setDisplayName(ChatColor.RESET + "Lumber Axe");
        lumberAxeMeta.setRarity(ItemRarity.UNCOMMON);
        CustomModelDataComponent lumberAxeCMD = lumberAxeMeta.getCustomModelDataComponent();
        lumberAxeCMD.setStrings(List.of("lumberaxe"));
        lumberAxeMeta.setCustomModelDataComponent(lumberAxeCMD);
        lumberAxe.setItemMeta(lumberAxeMeta);



        // recipes !

        // hammer
        ShapedRecipe hammerRecipe = new ShapedRecipe(new NamespacedKey(this, "hammer"), hammer);
        hammerRecipe.shape("ABA", " C ", " C ");
        hammerRecipe.setIngredient('A', Material.DIAMOND_BLOCK);
        hammerRecipe.setIngredient('B', Material.NETHERITE_INGOT);
        hammerRecipe.setIngredient('C', Material.BREEZE_ROD);
        Bukkit.addRecipe(hammerRecipe);

        // bread
        ShapedRecipe breadRecipe = new ShapedRecipe(new NamespacedKey(this, "bread_wheat"), bread);
        breadRecipe.shape("AAA", " B ", "AAA");
        breadRecipe.setIngredient('A', Material.WHEAT);
        breadRecipe.setIngredient('B', Material.EMERALD);
        Bukkit.addRecipe(breadRecipe);

        // bread 2
        ShapedRecipe breadRecipe2 = new ShapedRecipe(new NamespacedKey(this, "bread_bread"), bread);
        breadRecipe2.shape("A", "B", "A");
        breadRecipe2.setIngredient('A', Material.BREAD);
        breadRecipe2.setIngredient('B', Material.EMERALD);
        Bukkit.addRecipe(breadRecipe2);

        //job
        ShapedRecipe jobRecipe = new ShapedRecipe(new NamespacedKey(this, "job"), job);
        jobRecipe.shape("ABC", "DEF", "GHI");
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
        ShapedRecipe infernoBlockRecipe = new ShapedRecipe(new NamespacedKey(this, "infernoBlock"), infernoBlock);
        infernoBlockRecipe.shape("II", "II");
        infernoBlockRecipe.setIngredient('I', new RecipeChoice.ExactChoice(inferno));
        Bukkit.addRecipe(infernoBlockRecipe);

        // potion of combat
        ShapedRecipe combatPotionRecipe = new ShapedRecipe(new NamespacedKey(this, "combatpotion"), combatPotion);
        combatPotionRecipe.shape("NIN", "BSM", "NWN");
        combatPotionRecipe.setIngredient('N', Material.NETHER_WART);
        combatPotionRecipe.setIngredient('I', new RecipeChoice.ExactChoice(inferno));
        combatPotionRecipe.setIngredient('B', Material.BLAZE_POWDER);
        combatPotionRecipe.setIngredient('S', Material.SUGAR);
        combatPotionRecipe.setIngredient('M', Material.MAGMA_CREAM);
        ItemStack waterbottle = new ItemStack(Material.POTION);
        PotionMeta watermeta = (PotionMeta) waterbottle.getItemMeta();
        assert watermeta != null;
        watermeta.setBasePotionType(PotionType.WATER);
        waterbottle.setItemMeta(watermeta); // why does it take a whole 5 lines to make a water bottle this is an outrage!!!!!!!!!!!!!!
        combatPotionRecipe.setIngredient('W', new RecipeChoice.ExactChoice(waterbottle));
        Bukkit.addRecipe(combatPotionRecipe);

        // potion of commerce
        ShapedRecipe commercePotionRecipe = new ShapedRecipe(new NamespacedKey(this, "commercepotion"), commercePotion);
        commercePotionRecipe.shape("EEE", "EWE", "EEE");
        commercePotionRecipe.setIngredient('E', Material.EMERALD_BLOCK);
        commercePotionRecipe.setIngredient('W', new RecipeChoice.ExactChoice(waterbottle));
        Bukkit.addRecipe(commercePotionRecipe);

        // parachute
        ShapedRecipe parachuteRecipe = new ShapedRecipe(new NamespacedKey(this, "parachute"), parachute);
        parachuteRecipe.shape("CWC", "WBW", "SSS");
        parachuteRecipe.setIngredient('C', Material.WHITE_CARPET);
        parachuteRecipe.setIngredient('W', Material.WHITE_WOOL);
        parachuteRecipe.setIngredient('B', Material.WIND_CHARGE);
        parachuteRecipe.setIngredient('S', Material.STRING);
        Bukkit.addRecipe(parachuteRecipe);

        // piston boots
        ShapedRecipe pistonBootsRecipe = new ShapedRecipe(new NamespacedKey(this, "pistonboots"), pistonBoots);
        pistonBootsRecipe.shape("LiL", "BIB", "PiP");
        pistonBootsRecipe.setIngredient('L', Material.LEATHER);
        pistonBootsRecipe.setIngredient('i', Material.IRON_INGOT);
        pistonBootsRecipe.setIngredient('B', Material.IRON_BOOTS);
        pistonBootsRecipe.setIngredient('I', Material.IRON_BLOCK);
        pistonBootsRecipe.setIngredient('P', Material.PISTON);
        Bukkit.addRecipe(pistonBootsRecipe);

        // golden string recipe
        ShapedRecipe goldenStringRecipe = new ShapedRecipe(new NamespacedKey(this, "goldenstring"), goldenString);
        goldenStringRecipe.shape("GGG", "GSG", "GGG");
        goldenStringRecipe.setIngredient('G', Material.GOLD_INGOT);
        goldenStringRecipe.setIngredient('S', Material.STRING);
        Bukkit.addRecipe(goldenStringRecipe);

        // midas rod recipe
        ShapedRecipe midasRodRecipe = new ShapedRecipe(new NamespacedKey(this, "midasrod"), midasRod);
        midasRodRecipe.shape("  G", " GS", "G S");
        midasRodRecipe.setIngredient('G', Material.GOLD_BLOCK);
        midasRodRecipe.setIngredient('S', new RecipeChoice.ExactChoice(goldenString));
        Bukkit.addRecipe(midasRodRecipe);

        // goldfish smelt recipe
        // make the itemstack first for recipe choice
        ItemStack goldfish = new ItemStack(Material.TROPICAL_FISH);
        ItemMeta goldfishMeta = goldfish.getItemMeta();
        List<String> goldfishLore = new ArrayList<>();
        goldfishLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "Can be smelted into 4 gold ingots!");
        assert goldfishMeta != null;
        goldfishMeta.setLore(goldfishLore);
        goldfishMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + "Goldfish");
        goldfish.setItemMeta(goldfishMeta);
        FurnaceRecipe goldfishSmeltRecipe = new FurnaceRecipe(new NamespacedKey(this, "goldfishsmelt"), new ItemStack(Material.GOLD_INGOT, 4), new RecipeChoice.ExactChoice(goldfish), 10f, 100);
        Bukkit.addRecipe(goldfishSmeltRecipe);
        // stupid dumb task to make fished fish and itemstack fish the same
        new BukkitRunnable() {

            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    for (int i = 0; i < p.getInventory().getContents().length; i++) {
                        // big if statement! wow!
                        if (p.getInventory().getItem(i) != null
                            && !(p.getInventory().getItem(i).getType().equals(Material.AIR))
                            && p.getInventory().getItem(i).getType().equals(Material.TROPICAL_FISH)
                            && p.getInventory().getItem(i).hasItemMeta()
                            && p.getInventory().getItem(i).getItemMeta().hasLore()
                            && !(p.getInventory().getItem(i).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Goldfish"))
                            && !(p.getInventory().getItem(i).equals(goldfish))) {
                            int amount = p.getInventory().getItem(i).getAmount();
                            p.getInventory().setItem(i, goldfish);
                            p.getInventory().getItem(i).setAmount(amount);
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0, 20);

        // phantom membrane alternate recipe
        ShapedRecipe phantomMembraneRecipe = new ShapedRecipe(new NamespacedKey(this, "phantommembrane"), new ItemStack(Material.PHANTOM_MEMBRANE));
        phantomMembraneRecipe.shape("FF", "FF");
        phantomMembraneRecipe.setIngredient('F', Material.FEATHER);
        Bukkit.addRecipe(phantomMembraneRecipe);


    }

    // commands!! all of these r operator only. names should be self explanatory
    // (toggleend disables placing eyes in frames, togglestringduper disables the string duper in StringDispenser.java)
    // also is it just me or is this whole method a little cramped like maybe i should add a little more whitespace or something

    // PLEASE PLEASE turn the give commands into a separate command with arguments this is so unnecessarily long rn Y?!!
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("givetotem")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getDisplayName().equalsIgnoreCase(args[0]))
                            p.getInventory().addItem(totem);
                        else
                            player.sendMessage(ChatColor.RED + "invalid player!");
                    }
                }
                else
                    player.getInventory().addItem(totem);
            }
        } else if (command.getName().equalsIgnoreCase("givecrown")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getDisplayName().equalsIgnoreCase(args[0]))
                            p.getInventory().addItem(crown);
                        else
                            player.sendMessage(ChatColor.RED + "invalid player!");
                    }
                }
                else
                    player.getInventory().addItem(crown);
            }
        } else if (command.getName().equalsIgnoreCase("givesword")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getDisplayName().equalsIgnoreCase(args[0]))
                            p.getInventory().addItem(sword);
                        else
                            player.sendMessage(ChatColor.RED + "invalid player!");
                    }
                }
                else
                    player.getInventory().addItem(sword);
            }
        } else if (command.getName().equalsIgnoreCase("givehammer")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getDisplayName().equalsIgnoreCase(args[0]))
                            p.getInventory().addItem(hammer);
                        else
                            player.sendMessage(ChatColor.RED + "invalid player!");
                    }
                }
                else
                    player.getInventory().addItem(hammer);
            }
        } else if (command.getName().equalsIgnoreCase("givebread")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getDisplayName().equalsIgnoreCase(args[0]))
                            p.getInventory().addItem(bread);
                        else
                            player.sendMessage(ChatColor.RED + "invalid player!");
                    }
                }
                else
                    player.getInventory().addItem(bread);
            }
        } else if (command.getName().equalsIgnoreCase("giveapplication")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getDisplayName().equalsIgnoreCase(args[0]))
                            p.getInventory().addItem(job);
                        else
                            player.sendMessage(ChatColor.RED + "invalid player!");
                    }
                }
                else
                    player.getInventory().addItem(job);
            }
        } else if (command.getName().equalsIgnoreCase("giveaxe")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getDisplayName().equalsIgnoreCase(args[0]))
                            p.getInventory().addItem(axe);
                        else
                            player.sendMessage(ChatColor.RED + "invalid player!");
                    }
                }
                else
                    player.getInventory().addItem(axe);
            }
        } else if (command.getName().equalsIgnoreCase("giverod")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getDisplayName().equalsIgnoreCase(args[0]))
                            p.getInventory().addItem(inferno);
                        else
                            player.sendMessage(ChatColor.RED + "invalid player!");
                    }
                }
                else
                    player.getInventory().addItem(inferno);
            }
        } else if (command.getName().equalsIgnoreCase("givehastehammer")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getDisplayName().equalsIgnoreCase(args[0]))
                            p.getInventory().addItem(hasteHammer);
                        else
                            player.sendMessage(ChatColor.RED + "invalid player!");
                    }
                }
                else
                    player.getInventory().addItem(hasteHammer);
            }
        } else if (command.getName().equalsIgnoreCase("givewardenbow")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getDisplayName().equalsIgnoreCase(args[0]))
                            p.getInventory().addItem(wardenBow);
                        else
                            player.sendMessage(ChatColor.RED + "invalid player!");
                    }
                }
                else
                    player.getInventory().addItem(wardenBow);
            }
        } else if (command.getName().equalsIgnoreCase("givelumberaxe")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getDisplayName().equalsIgnoreCase(args[0]))
                            p.getInventory().addItem(lumberAxe);
                        else
                            player.sendMessage(ChatColor.RED + "invalid player!");
                    }
                }
                else
                    player.getInventory().addItem(lumberAxe);
            }
        } else if (command.getName().equalsIgnoreCase("togglemacecraftable")) {
                // uses persistent data containers. i freaking LOVE these things now
                World world = Bukkit.getWorlds().getFirst();
                NamespacedKey key = new NamespacedKey(this, "macecraftable");
                PersistentDataContainer data = world.getPersistentDataContainer();
                if (Boolean.TRUE.equals(data.get(key, PersistentDataType.BOOLEAN))) {
                    data.set(key, PersistentDataType.BOOLEAN, false);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "Mace crafting " + ChatColor.GREEN + "enabled!");
                } else {
                    data.set(key, PersistentDataType.BOOLEAN, true);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "Mace crafting " + ChatColor.RED + "disabled!");
                }
        } else if (command.getName().equalsIgnoreCase("togglepvp")) {
                World world = Bukkit.getWorlds().getFirst();
                NamespacedKey key = new NamespacedKey(this, "pvp");
                PersistentDataContainer data = world.getPersistentDataContainer();
                if (Boolean.TRUE.equals(data.get(key, PersistentDataType.BOOLEAN))) {
                    data.set(key, PersistentDataType.BOOLEAN, false);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "PVP " + ChatColor.GREEN +"enabled!");
                    world.setPVP(true);
                } else {
                    data.set(key, PersistentDataType.BOOLEAN, true);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "PVP " + ChatColor.RED + "disabled!");
                    world.setPVP(false);
                }
        } else if (command.getName().equalsIgnoreCase("toggleend")) {
                World world = Bukkit.getWorlds().getFirst();
                NamespacedKey key = new NamespacedKey(this, "end");
                PersistentDataContainer data = world.getPersistentDataContainer();
                if (Boolean.TRUE.equals(data.get(key, PersistentDataType.BOOLEAN))) {
                    data.set(key, PersistentDataType.BOOLEAN, false);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "End " + ChatColor.GREEN +"enabled!");
                } else {
                    data.set(key, PersistentDataType.BOOLEAN, true);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "End " + ChatColor.RED + "disabled!");
            }
        } else if (command.getName().equalsIgnoreCase("togglestringduper")) {
                World world = Bukkit.getWorlds().getFirst();
                NamespacedKey key = new NamespacedKey(this, "stringduper");
                PersistentDataContainer data = world.getPersistentDataContainer();
                if (Boolean.TRUE.equals(data.get(key, PersistentDataType.BOOLEAN))) {
                    data.set(key, PersistentDataType.BOOLEAN, false);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "String dupers " + ChatColor.GREEN +"enabled!");
                } else {
                    data.set(key, PersistentDataType.BOOLEAN, true);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "String dupers " + ChatColor.RED + "disabled!");
                }
        } else if (command.getName().equalsIgnoreCase("toggleenderpearls")) {
                World world = Bukkit.getWorlds().getFirst();
                NamespacedKey key = new NamespacedKey(this, "enderpearls");
                PersistentDataContainer data = world.getPersistentDataContainer();
                if (Boolean.TRUE.equals(data.get(key, PersistentDataType.BOOLEAN))) {
                    data.set(key, PersistentDataType.BOOLEAN, false);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "Ender pearls " + ChatColor.GREEN +"enabled!");
                } else {
                    data.set(key, PersistentDataType.BOOLEAN, true);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "Ender pearls " + ChatColor.RED + "disabled!");
            }
        } else if (command.getName().equalsIgnoreCase("toggleshulkers")) {
                World world = Bukkit.getWorlds().getFirst();
                NamespacedKey key = new NamespacedKey(this, "shulkers");
                PersistentDataContainer data = world.getPersistentDataContainer();
                if (Boolean.TRUE.equals(data.get(key, PersistentDataType.BOOLEAN))) {
                    data.set(key, PersistentDataType.BOOLEAN, false);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "Shulker boxes " + ChatColor.GREEN +"enabled!");
                } else {
                    data.set(key, PersistentDataType.BOOLEAN, true);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "Shulker boxes " + ChatColor.RED + "disabled!");
                }
        } else if (command.getName().equalsIgnoreCase("getitem")) {
            if (sender instanceof Player p) {
                p.sendMessage(Objects.requireNonNull(p.getInventory().getItemInMainHand().getItemMeta()).getAsComponentString());
            }
        } else if (command.getName().equalsIgnoreCase("startlightningdragonfight")) {
            LightningDragonFight.startLightningDragonSpawn();
        }


        return true;
    }

    public static ExplorersCore getPlugin() {
        return plugin;
    }
}
