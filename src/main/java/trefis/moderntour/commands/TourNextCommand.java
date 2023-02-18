package trefis.moderntour.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import trefis.moderntour.Main;
import trefis.moderntour.Utils;
import trefis.moderntour.sql.Database;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.UUID;

public class TourNextCommand {
    public static boolean executeCommand(Player player, Main plugin) {
        if (!plugin.isTourStarted) {
            Utils.sendMessage(player, "&6[ModernTour] &cОбход еще не начался.");
            return true;
        }
        LinkedHashMap<UUID, String> nextDBPlayer = Database.request.getNextQueuePlayer();
        if (nextDBPlayer.isEmpty()) {
            Utils.pluginStop(plugin);
            return true;
        }
        UUID nextPlayerUuid = nextDBPlayer.entrySet().iterator().next().getKey();
        String nextPlayerCoords = nextDBPlayer.entrySet().iterator().next().getValue();

        OfflinePlayer nextPlayer = Bukkit.getOfflinePlayer(nextPlayerUuid);
        Bukkit.dispatchCommand(player, nextPlayerCoords);
        Utils.sendMessage(player, "&6[ModernTour] &aСледующий игрок - &b" + nextPlayer.getName() + "&a.&b " + Database.request.getTourPlayers().size() + " &a игрок(ов) остался(ось).");
        player.sendTitle("§a Следующий игрок - ", nextPlayer.getName(), 20, 50, 10);
        plugin.currentPlayer = nextPlayerUuid;
        Database.request.setTourPlayerRole(nextPlayerUuid, "toured");

        Iterator<UUID> iterator = plugin.party.iterator();
        while (iterator.hasNext()) {
            UUID tourMemberUUID = iterator.next();
            if (Bukkit.getOfflinePlayer(tourMemberUUID).isOnline()) {
                Player tourMember = Bukkit.getPlayer(tourMemberUUID);
                tourMember.teleport(player.getLocation());
                Utils.sendMessage(tourMember, "&6[ModernTour] &aСледующий игрок - &b" + nextPlayer.getName() + "&a.&b " + Database.request.getTourPlayers().size() + " &a игрок(ов) остался(ось).");
                tourMember.sendTitle("§a Следующий игрок - ", nextPlayer.getName(), 20, 50, 10);
            }
        }

        if (nextPlayer.isOnline()) {
            Player onlineTourPlayer = nextPlayer.getPlayer();
            Utils.sendMessage(onlineTourPlayer, "");
            Utils.sendMessage(onlineTourPlayer, "&6[ModernTour] &aВы в прямом эфире!");
            Utils.sendMessage(onlineTourPlayer, "");
        }
        return true;
    }
}
