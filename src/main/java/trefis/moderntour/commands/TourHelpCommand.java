package trefis.moderntour.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import trefis.moderntour.Main;
import trefis.moderntour.Utils;

public class TourHelpCommand {
    public static boolean executeCommandWithPermission(Player player, Main plugin) {
        Utils.sendMessage(player, "&6[ModernTour]&r Available commands: &a/tour &r&ahelp&r | &ajoin&r | &aleave&r | &alist&r | &astart&r | &astop&r | &anext&r | &aadd <name>&r | &aremove <name>&r");
        if (!plugin.partyOwner.equals(player.getName())) {
            return true;
        }
        Utils.sendMessage(player, " ");
        Utils.sendMessage(player, "&a Modern tour management");
        Utils.sendMessage(player, " ");

        TextComponent message = new TextComponent(" ");

        TextComponent startButton = new TextComponent("[START]");
        startButton.addExtra(" ");
        startButton.setColor(ChatColor.GREEN);
        startButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tour start"));
        startButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.RED + "Start tour")).create()));
        message.addExtra(startButton);

        TextComponent stopButton = new TextComponent("[STOP]");
        stopButton.addExtra(" ");
        stopButton.setColor(ChatColor.RED);
        stopButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tour stop"));
        stopButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.RED + "Stop tour")).create()));
        message.addExtra(stopButton);

        TextComponent listButton = new TextComponent("[LIST]");
        listButton.addExtra(" ");
        listButton.setColor(ChatColor.YELLOW);
        listButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tour list"));
        listButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.RED + "Tour queue")).create()));
        message.addExtra(listButton);

        TextComponent nextButton = new TextComponent("[NEXT]");
        nextButton.addExtra(" ");
        nextButton.setColor(ChatColor.GOLD);
        nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tour next"));
        nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.RED + "Move on to the next player")).create()));
        message.addExtra(nextButton);

        player.spigot().sendMessage(message);
        Utils.sendMessage(player, " ");

        return true;
    }

    public static boolean executeCommandWithoutPermission(Player player, Main plugin) {
        Utils.sendMessage(player, "&6[ModernTour]&r Available commands: &a/tour &r&ahelp&r | &ajoin&r | &aleave&r | &alist&r");
        return true;
    }
}
