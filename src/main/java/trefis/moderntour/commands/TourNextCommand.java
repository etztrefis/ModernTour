package trefis.moderntour.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import trefis.moderntour.Main;
import trefis.moderntour.Utils;
import trefis.moderntour.sql.Database;

import java.util.Iterator;
import java.util.UUID;

public class TourNextCommand {
    public static boolean executeCommand(Player player, Main plugin) {
        if (!plugin.isTourStarted) {
            Utils.sendMessage(player, "&6[ModernTour] &cTour has not started yet.");
            return true;
        }
        UUID nextPlayerUuid = Database.request.getNextQueuePlayer();
        if (nextPlayerUuid == null) {
            Utils.pluginStop(plugin);
            return true;
        }
        OfflinePlayer nextPlayer = Bukkit.getOfflinePlayer(nextPlayerUuid);
        Location nextPlayerLocation = nextPlayer.getPlayer().getLocation();
        plugin.currentPlayer = nextPlayerUuid;
        Database.request.setTourPlayerRole(nextPlayerUuid, "toured");

        Iterator<UUID> iterator = plugin.party.iterator();
        while (iterator.hasNext()) {
            UUID tourMemberUUID = iterator.next();
            if (Bukkit.getOfflinePlayer(tourMemberUUID).isOnline()) {
                Player tourMember = Bukkit.getPlayer(tourMemberUUID);
                tourMember.teleport(nextPlayerLocation);
                Utils.sendMessage(tourMember, "&6[ModernTour] &aNext player is &b" + nextPlayer.getName() + "&a. &b" + Database.request.getTourPlayers().size() + " &a players left.");
                tourMember.sendTitle("&a Next player is", nextPlayer.getName(), 20, 50, 10);
            }
        }

        if (nextPlayer.isOnline()) {
            Player onlineTourPlayer = nextPlayer.getPlayer();
            Utils.sendMessage(onlineTourPlayer, "");
            Utils.sendMessage(onlineTourPlayer, "&6[ModernTour] &aThe tour is on you!");
            Utils.sendMessage(onlineTourPlayer, "");
        }
        return true;
    }
}
