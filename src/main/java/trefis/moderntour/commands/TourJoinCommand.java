package trefis.moderntour.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import trefis.moderntour.Main;
import trefis.moderntour.Utils;
import trefis.moderntour.sql.Database;

public class TourJoinCommand {
    public static boolean executeCommand(Player player, Main plugin) {
        if (!plugin.isTourStarted) {
            Utils.sendMessage(player, "&6[ModernTour] &cTour has not started yet.");
            return true;
        }
        if (plugin.partyOwner.equals(player.getName())) {
            Utils.sendMessage(player, "&6[ModernTour] &cAs a tour owner you can't join the tour :).");
            return true;
        }
        if (Database.request.getTouredPlayers().contains(player.getUniqueId())) {
            Utils.sendMessage(player, "&6[ModernTour] &cYou have been seen already.");
            return true;
        }
        if (Database.request.getTourPlayers().contains(player.getUniqueId())) {
            Utils.sendMessage(player, "&6[ModernTour] &cYou are in the tour queue already, just wait.");
            return true;
        }

        Database.request.tourAddPlayer(player.getUniqueId());
        Utils.sendMessage(player, "&6[ModernTour] &aYou have just been added to the tour queue.");
        TextComponent message = new TextComponent();
        message.addExtra("&aGet tour queue:&r ");

        TextComponent listButton = new TextComponent("[LIST]");
        listButton.setColor(ChatColor.GOLD);
        listButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tour list"));
        listButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.RED + "Tour queue").create()));
        message.addExtra(listButton);
        player.spigot().sendMessage(message);

        return true;
    }
}
