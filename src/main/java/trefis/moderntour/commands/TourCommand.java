package trefis.moderntour.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;
import trefis.moderntour.Main;
import trefis.moderntour.Utils;
import trefis.moderntour.sql.SQLWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TourCommand implements TabExecutor {
    private final Main plugin;

    public TourCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("&6[ModernTour] This command must be run on behalf of the player.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            Utils.sendMessage(player, "&6[ModernTour]&r Version " + plugin.version);
            Utils.sendMessage(player, "&6[ModernTour]&r " + plugin.description);
            Utils.sendMessage(player, "&6[ModernTour]&r Made with &c❤&r by &ntrefis");
        } else {
            switch (args[0]) {
                case "help":
                    if (player.hasPermission("tour.admin")) {
                        TourHelpCommand.executeCommandWithPermission(player, plugin);
                    } else {
                        TourHelpCommand.executeCommandWithoutPermission(player, plugin);
                    }
                    break;
                case "party":
                    if (player.hasPermission("tour.admin")) {
                        TourPartyCommand.executeCommand(player, plugin, args);
                    } else {
                        Utils.sendMessage(player, "&6[ModernTour] &cYou don't have permission to run this command.");
                    }
                    break;
                case "join":
                    TourJoinCommand.executeCommand(player, plugin);
                    break;
                case "leave":
                    TourLeaveCommand.executeCommand(player, plugin);
                    break;
                case "next":
                    if (player.hasPermission("tour.admin")) {
                        TourNextCommand.executeCommand(player, plugin);
                    } else {
                        Utils.sendMessage(player, "&6[ModernTour] &cYou don't have permission to run this command.");
                    }
                    break;
                case "list":
                    TourListCommand.executeCommand(player, plugin);
                    break;
                case "stop":
                    if (player.hasPermission("tour.admin")) {
                        TourStopCommand.executeCommand(player, plugin);
                    } else {
                        Utils.sendMessage(player, "&6[ModernTour] &cYou don't have permission to run this command.");
                    }
                default:
                    if (sender.hasPermission("sc.admin")) {
                        sender.sendMessage("/sc [show/rise/logs/players]");
                    } else {
                        sender.sendMessage("Правильное использование команды: /sc show");
                    }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> subArguments = new ArrayList<>();
        if (!commandSender.hasPermission("sc.admin")) {
            if (strings.length == 1) {
                subArguments.add("show");
            }
            return subArguments;
        } else {
            if (strings.length == 1) {
                subArguments.add("get");
                subArguments.add("show");
                subArguments.add("logs");
                subArguments.add("rise");
                return subArguments;
            }
            if (strings[0].equals("rise")) {
                if (strings.length == 2) {
                    List<String> playerNames = new ArrayList<>();
                    playerNames.add("user:");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        playerNames.add("user:" + player.getName().toLowerCase(Locale.ROOT));
                    }
                    return playerNames;
                } else if (strings.length == 3) {
                    List<String> amounts = new ArrayList<>();
                    amounts.add("amount:");
                    return amounts;
                } else if (strings.length == 4) {
                    List<String> reasons = new ArrayList<>();
                    reasons.add("reason:");
                    return reasons;
                } else {
                    return null;
                }
            } else if (strings[0].equals("show")) {
                if (strings.length == 2) {
                    List<String> playerNames = new ArrayList<>();
                    playerNames.add("user:");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        playerNames.add("user:" + player.getName().toLowerCase(Locale.ROOT));
                    }
                    return playerNames;
                } else {
                    return null;
                }
            } else if (strings[0].equals("logs")) {
                if (strings.length == 2) {
                    List<String> playerNames = new ArrayList<>();
                    playerNames.add("user:");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        playerNames.add("user:" + player.getName().toLowerCase(Locale.ROOT));
                    }
                    return playerNames;
                } else if (strings.length == 3) {
                    List<String> pages = new ArrayList<>();
                    pages.add("page:");
                    return pages;
                } else {
                    return null;
                }
            } else if (strings[0].equals("get")) {
                if (strings.length == 2) {
                    List<String> filers = new ArrayList<>();
                    filers.add("filter:<");
                    filers.add("filter:>");
                    filers.add("filter:=");
                    filers.add("filter:>=");
                    filers.add("filter:<=");
                    return filers;
                } else {
                    return null;
                }
            }
            return null;
        }
    }
}
