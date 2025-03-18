package me.softsheep6.explorersCore.items.craftable;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;


public class MiningHammer implements Listener {

    // if a block is broken by a player holding a mining hammer, break som blockz!
    @EventHandler void onBlockBreak(BlockBreakEvent event) {
        Location loc = event.getBlock().getLocation();
        World w = event.getPlayer().getWorld();
        Player p = event.getPlayer();

        // checks if block breaking mode is set to 1 (3x3x1)
        if (holdingHammer(event.getPlayer()) == 1) {

            // probably not very good but It Workz
            // this used to find direction based on getBlockFace buttt im not really sure how to transfer that across event handlers
            // soo instead its just based off of p.getPitch and p.getFacing which is basically the same thing
            // just a little different depending on how ur facing BUT ITS ALRIGHTTY!
            if (p.getLocation().getPitch() < -45 || p.getLocation().getPitch() > 45) {
                breakBlock(loc.getBlockX()-1, loc.getBlockY(), loc.getBlockZ(), w, p);
                breakBlock(loc.getBlockX()+1, loc.getBlockY(), loc.getBlockZ(), w, p);
                breakBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()-1, w, p);
                breakBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()+1, w, p);
                breakBlock(loc.getBlockX()-1, loc.getBlockY(), loc.getBlockZ()-1, w, p);
                breakBlock(loc.getBlockX()+1, loc.getBlockY(), loc.getBlockZ()+1, w, p);
                breakBlock(loc.getBlockX()-1, loc.getBlockY(), loc.getBlockZ()+1, w, p);
                breakBlock(loc.getBlockX()+1, loc.getBlockY(), loc.getBlockZ()-1, w, p);
            } else if (p.getFacing() == BlockFace.EAST || p.getFacing() == BlockFace.WEST) {
                breakBlock(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ(), w, p);
                breakBlock(loc.getBlockX(), loc.getBlockY()+1, loc.getBlockZ(), w, p);
                breakBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()-1, w, p);
                breakBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()+1, w, p);
                breakBlock(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()-1, w, p);
                breakBlock(loc.getBlockX(), loc.getBlockY()+1, loc.getBlockZ()+1, w, p);
                breakBlock(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()+1, w, p);
                breakBlock(loc.getBlockX(), loc.getBlockY()+1, loc.getBlockZ()-1, w, p);
            } else if (p.getFacing() == BlockFace.SOUTH || p.getFacing() == BlockFace.NORTH) {
                breakBlock(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ(), w, p);
                breakBlock(loc.getBlockX(), loc.getBlockY()+1, loc.getBlockZ(), w, p);
                breakBlock(loc.getBlockX()-1, loc.getBlockY(), loc.getBlockZ(), w, p);
                breakBlock(loc.getBlockX()+1, loc.getBlockY(), loc.getBlockZ(), w, p);
                breakBlock(loc.getBlockX()-1, loc.getBlockY()-1, loc.getBlockZ(), w, p);
                breakBlock(loc.getBlockX()+1, loc.getBlockY()+1, loc.getBlockZ(), w, p);
                breakBlock(loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ(), w, p);
                breakBlock(loc.getBlockX()-1, loc.getBlockY()+1, loc.getBlockZ(), w, p);
            }
        }
    }



    // the first if statement, not really sure WHat it does, i just copied it from somwhere cause it called the event handler twice otherwise
    // second thingy changes hammer modes
    @EventHandler void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if(event.getHand() != EquipmentSlot.HAND)
            return;

        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && holdingHammer(event.getPlayer()) != -1) {
            if (holdingHammer(event.getPlayer()) == 0) {
                item.removeEnchantment(Enchantment.POWER);
                item.addUnsafeEnchantment(Enchantment.POWER, 3);
                event.getPlayer().sendMessage("Set Mining Hammer mode to " + ChatColor.GREEN + "3x3x1");
                event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1);
            } else {
                item.removeEnchantment(Enchantment.POWER);
                item.addUnsafeEnchantment(Enchantment.POWER, 1);
                event.getPlayer().sendMessage("Set Mining Hammer mode to " + ChatColor.YELLOW + "1x1x1");
                event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 0);
            }
        }

    }


    // returns -1 if player is not holding hammer,
    // returns 0 if player is holding hammer set to 1x1x1,
    // returns 1 if player is holding hammer set to 3x3x1 . did this need its own method? maybe. its up to you. idk.
    public int holdingHammer(Player player) {
        ItemStack tool = player.getInventory().getItemInMainHand();
        if ((tool.getType().equals(Material.DIAMOND_PICKAXE) || tool.getType().equals(Material.NETHERITE_PICKAXE)) && tool.getEnchantmentLevel(Enchantment.POWER) == 1)
            return 0;
        else if ((tool.getType().equals(Material.DIAMOND_PICKAXE) || tool.getType().equals(Material.NETHERITE_PICKAXE)) && tool.getEnchantmentLevel(Enchantment.POWER) == 3)
            return 1;
        return -1;
    }


    // returns true if the block can be broken!
    // enjoy the 100 lines of block.getType!
    // itd be funny if i just put this all on one really long line lol
    // Wait this was actually completely useless why did i do this ummmmmmmm
    // i mean the first few lines sure but the rest of it Why
    public boolean isBreakable(Block block) {
        return !(block.getType().equals(Material.BEDROCK)
                || (block.getType().equals(Material.END_PORTAL))
                || (block.getType().equals(Material.END_PORTAL_FRAME))
                || (block.getType().equals(Material.END_GATEWAY))
                || (block.getType().equals(Material.NETHER_PORTAL))
                || (block.getType().equals(Material.WATER))
                || (block.getType().equals(Material.LAVA))
                || (block.getType().equals(Material.REINFORCED_DEEPSLATE))
                || (block.getType().equals(Material.OBSIDIAN))
                || (block.getType().equals(Material.CRYING_OBSIDIAN))
                || (block.getType().equals(Material.ANCIENT_DEBRIS))
                || (block.getType().equals(Material.DRAGON_EGG))
                || (block.getType().equals(Material.AZALEA))
                || (block.getType().equals(Material.BEETROOTS))
                || (block.getType().equals(Material.CARROTS))
                || (block.getType().equals(Material.CAVE_VINES))
                || (block.getType().equals(Material.TORCH))
                || (block.getType().equals(Material.SOUL_TORCH))
                || (block.getType().equals(Material.REDSTONE_TORCH))
                || (block.getType().equals(Material.BRAIN_CORAL))
                || (block.getType().equals(Material.BUBBLE_CORAL))
                || (block.getType().equals(Material.FIRE_CORAL))
                || (block.getType().equals(Material.HORN_CORAL))
                || (block.getType().equals(Material.TUBE_CORAL))
                || (block.getType().equals(Material.DEAD_BRAIN_CORAL))
                || (block.getType().equals(Material.DEAD_BUBBLE_CORAL))
                || (block.getType().equals(Material.DEAD_FIRE_CORAL))
                || (block.getType().equals(Material.DEAD_HORN_CORAL))
                || (block.getType().equals(Material.DEAD_TUBE_CORAL))
                || (block.getType().equals(Material.BRAIN_CORAL_FAN))
                || (block.getType().equals(Material.BUBBLE_CORAL_FAN))
                || (block.getType().equals(Material.FIRE_CORAL_FAN))
                || (block.getType().equals(Material.HORN_CORAL_FAN))
                || (block.getType().equals(Material.TUBE_CORAL_FAN))
                || (block.getType().equals(Material.DEAD_BRAIN_CORAL_FAN))
                || (block.getType().equals(Material.DEAD_BUBBLE_CORAL_FAN))
                || (block.getType().equals(Material.DEAD_FIRE_CORAL_FAN))
                || (block.getType().equals(Material.DEAD_HORN_CORAL_FAN))
                || (block.getType().equals(Material.DEAD_TUBE_CORAL_FAN))
                || (block.getType().equals(Material.DEAD_BUSH))
                || (block.getType().equals(Material.ALLIUM))
                || (block.getType().equals(Material.AZURE_BLUET))
                || (block.getType().equals(Material.BLUE_ORCHID))
                || (block.getType().equals(Material.CORNFLOWER))
                || (block.getType().equals(Material.DANDELION))
                || (block.getType().equals(Material.CLOSED_EYEBLOSSOM))
                || (block.getType().equals(Material.OPEN_EYEBLOSSOM))
                || (block.getType().equals(Material.LILY_OF_THE_VALLEY))
                || (block.getType().equals(Material.OXEYE_DAISY))
                || (block.getType().equals(Material.POPPY))
                || (block.getType().equals(Material.TORCHFLOWER))
                || (block.getType().equals(Material.ORANGE_TULIP))
                || (block.getType().equals(Material.PINK_TULIP))
                || (block.getType().equals(Material.RED_TULIP))
                || (block.getType().equals(Material.WHITE_TULIP))
                || (block.getType().equals(Material.WITHER_ROSE))
                || (block.getType().equals(Material.LILAC))
                || (block.getType().equals(Material.PEONY))
                || (block.getType().equals(Material.PITCHER_PLANT))
                || (block.getType().equals(Material.TORCHFLOWER_CROP))
                || (block.getType().equals(Material.PITCHER_CROP))
                || (block.getType().equals(Material.ROSE_BUSH))
                || (block.getType().equals(Material.SUNFLOWER))
                || (block.getType().equals(Material.CHORUS_FLOWER))
                || (block.getType().equals(Material.CHORUS_PLANT))
                || (block.getType().equals(Material.FLOWERING_AZALEA))
                || (block.getType().equals(Material.MANGROVE_PROPAGULE))
                || (block.getType().equals(Material.PINK_PETALS))
                || (block.getType().equals(Material.SPORE_BLOSSOM))
                || (block.getType().equals(Material.FROGSPAWN))
                || (block.getType().equals(Material.CRIMSON_FUNGUS))
                || (block.getType().equals(Material.WARPED_FUNGUS))
                || (block.getType().equals(Material.SHORT_GRASS))
                || (block.getType().equals(Material.TALL_GRASS))
                || (block.getType().equals(Material.SEAGRASS))
                || (block.getType().equals(Material.TALL_SEAGRASS))
                || (block.getType().equals(Material.HANGING_ROOTS))
                || (block.getType().equals(Material.KELP_PLANT))
                || (block.getType().equals(Material.LILY_PAD))
                || (block.getType().equals(Material.MELON_STEM))
                || (block.getType().equals(Material.BROWN_MUSHROOM))
                || (block.getType().equals(Material.RED_MUSHROOM))
                || (block.getType().equals(Material.NETHER_SPROUTS))
                || (block.getType().equals(Material.NETHER_WART))
                || (block.getType().equals(Material.PALE_HANGING_MOSS))
                || (block.getType().equals(Material.POTATOES))
                || (block.getType().equals(Material.PUMPKIN_STEM))
                || (block.getType().equals(Material.COMPARATOR))
                || (block.getType().equals(Material.REPEATER))
                || (block.getType().equals(Material.REDSTONE))
                || (block.getType().equals(Material.CRIMSON_ROOTS))
                || (block.getType().equals(Material.WARPED_ROOTS))
                || (block.getType().equals(Material.ACACIA_SAPLING))
                || (block.getType().equals(Material.BAMBOO_SAPLING))
                || (block.getType().equals(Material.BIRCH_SAPLING))
                || (block.getType().equals(Material.OAK_SAPLING))
                || (block.getType().equals(Material.CHERRY_SAPLING))
                || (block.getType().equals(Material.JUNGLE_SAPLING))
                || (block.getType().equals(Material.DARK_OAK_SAPLING))
                || (block.getType().equals(Material.PALE_OAK_SAPLING))
                || (block.getType().equals(Material.SPRUCE_SAPLING))
                || (block.getType().equals(Material.SCAFFOLDING))
                || (block.getType().equals(Material.SEA_PICKLE))
                || (block.getType().equals(Material.SMALL_DRIPLEAF))
                || (block.getType().equals(Material.SUGAR_CANE))
                || (block.getType().equals(Material.SWEET_BERRY_BUSH))
                || (block.getType().equals(Material.TRIPWIRE_HOOK))
                || (block.getType().equals(Material.TWISTING_VINES_PLANT))
                || (block.getType().equals(Material.WEEPING_VINES_PLANT))
                || (block.getType().equals(Material.WHEAT))



        );
    }

    // this method is just to make the first eventhandler slightly more readable lol
    // world parameter is needed so that it works in nether/end
    // player is so things like silk touch or fortune will get applied to the other blocks
    public void breakBlock(int x, int y, int z, World world, Player player) {
        if (isBreakable(world.getBlockAt(x, y, z)))
            world.getBlockAt(x, y, z).breakNaturally(player.getInventory().getItemInMainHand());
    }
}
