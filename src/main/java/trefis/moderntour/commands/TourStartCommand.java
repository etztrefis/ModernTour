package trefis.moderntour.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import trefis.moderntour.Main;
import trefis.moderntour.Utils;

import java.util.Objects;

public class TourStartCommand {
    public static boolean executeCommand(Player player, Main plugin) {
        if (plugin.isTourStarted) {
            Utils.sendMessage(player, "&6[ModernTour] &cTour has already started.");
            return true;
        }
        plugin.partyOwner = player.getName();
        plugin.isTourStarted = true;
        Utils.broadcastTitle("§6The tour has just begun", "Click the chat button to join!");

        TextComponent textComponent = new TextComponent("§6[ModernTour] §b" + plugin.partyOwner + " §6just started the tour\n");
        textComponent.addExtra("§a Click to join the tour ");

        TextComponent joinButton = new TextComponent("[JOIN]");
        joinButton.addExtra(" ");
        joinButton.setColor(ChatColor.GOLD);
        joinButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tour join"));
        joinButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.RED + "Join tour")).create()));

        textComponent.addExtra(joinButton);
        Utils.broadcast(textComponent);
        setupItems(player);
        if (plugin.autoCreativeTourOwner) {
            player.setGameMode(GameMode.CREATIVE);
        }

        return true;
    }

    public static void setupItems(Player p) {
        p.getInventory().setItem(4, getHead("MHF_ArrowRight", "§aNext"));
    }

    public static ItemStack getHead(String playerName, String displayName) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        Objects.requireNonNull(meta).setOwningPlayer(Bukkit.getOfflinePlayer(playerName));
        meta.setDisplayName(displayName);
        head.setItemMeta(meta);
        return head;
    }
}
