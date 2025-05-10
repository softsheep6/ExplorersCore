package me.softsheep6.explorersCore.commands;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import com.jeff_media.morepersistentdatatypes.*;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Scanner;

public class NameColor implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(@NotNull CommandSender p, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length < 2 || args.length > 3) {
            p.sendMessage(ChatColor.RED + "wrong arguments do /namecolor <name> <color> [format] stupid!!!!");
            return true;
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (args[0].equalsIgnoreCase(player.getName())) {
                    // pdc shenanigans
                    PersistentDataContainer pdc = player.getPersistentDataContainer();
                    NamespacedKey key = new NamespacedKey(ExplorersCore.getPlugin(), "namecolors");
                    ArrayList<String> strArr;
                    if (pdc.has(key)) strArr = pdc.get(key, DataType.asArrayList(PersistentDataType.STRING));
                    else strArr = new ArrayList<>();
                    // iterate through chatcolors, if first argument is a valid color save it, if second exists and is a valid format save it,
                    // then set the displayname and playerlistname depending on which saved thingys arent null
                    ChatColor colorCode = null;
                    ChatColor formatCode = null;
                    for (ChatColor color : ChatColor.values()) {
                        if (args[1].equalsIgnoreCase(color.name())) {
                            colorCode = color;
                            if (!color.isColor()) {
                                p.sendMessage(ChatColor.RED + "wrong arguments do /namecolor <name> <color> [format] stupid!!!!");
                                return true;
                            }
                        }
                        if (args.length == 3 && args[2].equalsIgnoreCase(color.name())) {
                            formatCode = color;
                            if (!color.isFormat()) {
                                p.sendMessage(ChatColor.RED + "wrong arguments do /namecolor <name> <color> [format] stupid!!!!");
                                return true;
                            }
                        }
                    }
                    if (colorCode == null) {
                        p.sendMessage(ChatColor.RED + "thats not a color!!!");
                        StringBuilder colors = new StringBuilder();
                        StringBuilder formats = new StringBuilder();
                        for (ChatColor color : ChatColor.values()) {
                            if (color.isColor()) colors.append(color.name()).append(" ");
                            if (color.isFormat()) formats.append(color.name()).append(" ");
                        }
                        p.sendMessage("colors: " + colors);
                        p.sendMessage("formats: " + formats);
                        return true;
                    }
                    else if (formatCode == null) {
                        player.setDisplayName(colorCode + player.getName() + ChatColor.RESET);
                        player.setPlayerListName(colorCode + player.getName() + ChatColor.RESET);
                        p.sendMessage(ChatColor.GREEN + "successfully set name color!");
                        // update pdc
                        strArr.add(player.getName() + " " + colorCode.name());
                        pdc.set(key, DataType.asArrayList(DataType.STRING), strArr);
                        return true;
                    } else {
                        player.setDisplayName("" + colorCode + formatCode + player.getName() + ChatColor.RESET);
                        player.setPlayerListName("" + colorCode + formatCode + player.getName() + ChatColor.RESET);
                        p.sendMessage(ChatColor.GREEN + "successfully set name color and format!");
                        // update pdc
                        strArr.add(player.getName() + " " + colorCode.name() + " " + formatCode.name());
                        pdc.set(key, DataType.asArrayList(DataType.STRING), strArr);
                        return true;
                    }
                }
            }
            p.sendMessage(ChatColor.RED + "thats not a player!!!");
        }



        return true;
    }

    // why does it reset names on rejoin sigh.............pdc time again i guess
    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {

        Player player = event.getPlayer();
        PersistentDataContainer pdc = event.getPlayer().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(ExplorersCore.getPlugin(), "namecolors");

        if (!pdc.has(key)) return;

        ArrayList<String> strArr = pdc.get(key, DataType.asArrayList(DataType.STRING));
        assert strArr != null;

        // iterates through saved namecolors. formatted as "<name> <color> [format]" in a string arraylist
        for (String str : strArr) {
            Scanner sc = new Scanner(str);
            String playerName = sc.next();
            String colorName = sc.next();
            String formatName = null;
            if (sc.hasNext()) formatName = sc.next();
            ChatColor color = null;
            ChatColor format = null;

            // if the current namecolor matches with the player who joined, set the players stuff to the saved stuff yea
            if (player.getName().equalsIgnoreCase(playerName)) {
                // for loop to match the chatcolor variables with the saved strings
                for (ChatColor c : ChatColor.values()) {
                    if (c.name().equalsIgnoreCase(colorName)) {
                        color = c;
                    }
                    if (c.name().equalsIgnoreCase(formatName)) {
                        format = c;
                    }
                }

                if (formatName == null) {
                    player.setDisplayName(color + player.getName() + ChatColor.RESET);
                    player.setPlayerListName(color + player.getName() + ChatColor.RESET);
                } else {
                    player.setDisplayName("" + color + format + player.getName() + ChatColor.RESET);
                    player.setPlayerListName("" + color + format + player.getName() + ChatColor.RESET);
                }

            }

        }
    }
}
