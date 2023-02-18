package trefis.moderntour.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import trefis.moderntour.Main;
import trefis.moderntour.Utils;
import trefis.moderntour.sql.SQLWorker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class TourPartyCommand {
    public static boolean executeCommand(Player player, Main plugin, String[] args) {
        if (args.length < 2) {
            Utils.sendMessage(player, "&6[ModernTour] &a/tour party &r<&aadd&r | &aremove&r | &alist&r>");
        } else {
            String mode = args[1];
            switch (mode) {
                case "add":
                    if (args.length < 3) {
                        Utils.sendMessage(player, "&6[ModernTour] &a/tour party &r<&aadd&r> [&anickname&r]");
                    } else {
                        Player target = Bukkit.getPlayer(args[2]);
                        if (target == null) {
                            Utils.sendMessage(player, "&6[ModernTour] &cЭтот игрок оффлайн или не найден.");
                        } else {
                            plugin.party.add(target.getUniqueId());
                            boolean response = SQLWorker.executeUpdate("INSERT INTO mt_data SET uuid = " + "\"" + target.getUniqueId() + "\"" + ", role='party'");
                            if (response) {
                                Utils.sendMessage(player, "&6[ModernTour]&a Игрок &b" + target.getName() + "&a был добавлен в вашу команду.");
                                Utils.broadcast(" \n&aNew party member - &b" + target.getName() + "&a! \n ");
                            } else {
                                Utils.sendMessage(player, "&6[ModernTour]&r &cОшибка. Игрок &b" + target.getName() + "&c уже в вашей команде.");
                            }
                        }
                    }
                    break;
                case "remove":
                    if (args.length < 3) {
                        Utils.sendMessage(player, "&6[ModernTour] &a/tour party &r<&aremove&r> [&anickname&r]");
                    } else {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[2]);
                        if (target.getName() == null || !target.hasPlayedBefore()) {
                            target = Bukkit.getPlayer(args[2]);
                            if (target == null) {
                                Utils.sendMessage(player, "&6[ModernTour] &cPlayer not found.");
                                return true;
                            }
                        }
                        plugin.party.remove(target.getUniqueId());
                        boolean response = SQLWorker.executeUpdate("DELETE FROM mt_data WHERE uuid = " + "\"" + target.getUniqueId() + "\"" + " AND role='party'");
                        if (response) {
                            Utils.sendMessage(player, "&6[ModernTour]&a Игрок &b" + target.getName() + "&a был удален из вашей команды.");
                        }
                    }
                    break;
                case "list":
                    if(plugin.party.size() == 0){
                        Utils.sendMessage(player, "&6[ModernTour] &aТы такой одинокий :(");
                        return true;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("&6[ModernTour] &aИгроки команды: ");
                    List<String> nicknames = new ArrayList<>();
                    Iterator<UUID> iterator = plugin.party.iterator();

                    while (true) {
                        String nickname;
                        while (true) {
                            if (!iterator.hasNext()) {
                                stringBuilder.append(String.join("&r, ", nicknames));
                                Utils.sendMessage(player, stringBuilder.toString());
                                return true;
                            }
                            UUID uuid = iterator.next();
                            Player partyPlayer = Bukkit.getPlayer(uuid);
                            if (partyPlayer == null) {
                                OfflinePlayer offlinePartyPlayer = Bukkit.getOfflinePlayer(uuid);
                                if(offlinePartyPlayer.getName() == null){
                                    continue;
                                }
                                nickname = "&c" + offlinePartyPlayer.getName();
                                break;
                            }
                            nickname = "&a" + partyPlayer.getName();
                            break;
                        }
                        nicknames.add(nickname);
                    }
            }
        }
        return true;
    }
}
