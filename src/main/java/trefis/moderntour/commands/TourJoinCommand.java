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

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class TourJoinCommand {
    public static boolean executeCommand(Player player, Main plugin) {
        if (!plugin.isTourStarted) {
            Utils.sendMessage(player, "&6[ModernTour] &cОбход еще не начался.");
            return true;
        }
        if (plugin.partyOwner.equals(player.getName())) {
            Utils.sendMessage(player, "&6[ModernTour] &cСоздатель обхода не может зайти в свой обход. :)");
            return true;
        }
        if (Database.request.getTouredPlayers().contains(player.getUniqueId())) {
            Utils.sendMessage(player, "&6[ModernTour] &cВас уже посмотрели.");
            return true;
        }
        if (Database.request.getTourPlayers().contains(player.getUniqueId())) {
            Utils.sendMessage(player, "&6[ModernTour] &cВы уже в очереди, просто подождите. :)");
            return true;
        }
        if (Database.request.getParty().contains(player.getUniqueId())) {
            Utils.sendMessage(player, "&6[ModernTour] &Участник команды не может зайтив в обход. :)");
            return true;
        }

        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);
        String environment = player.getWorld().getEnvironment().toString();
        String playerX = df.format(player.getLocation().getX());
        String playerY = df.format(player.getLocation().getY());
        String playerZ = df.format(player.getLocation().getZ());
        String playerYaw = df.format(player.getLocation().getYaw());
        String playerPitch = df.format(player.getLocation().getPitch());
        String teleport = "";
        if (player.getLocation().getWorld() != null && environment.equals("NORMAL")) {
            teleport = "execute in minecraft:overworld run tp @s " + playerX + " " + playerY + " " + playerZ + " " + playerYaw + " " + playerPitch;
        } else if (player.getLocation().getWorld() != null && environment.equals("NETHER")) {
            teleport = "execute in minecraft:the_nether run tp @s " + playerX + " " + playerY + " " + playerZ + " " + playerYaw + " " + playerPitch;
        } else if (player.getLocation().getWorld() != null && environment.equals("THE_END")) {
            teleport = "execute in minecraft:the_end run tp @s " + playerX + " " + playerY + " " + playerZ + " " + playerYaw + " " + playerPitch;
        }else {
            teleport = "execute in minecraft:" + environment + "run tp @s " + playerX + " " + playerY + " " + playerZ + " " + playerYaw + " " + playerPitch;
        }

        if(Database.request.tourAddPlayer(player.getUniqueId(), teleport)){
            Utils.sendMessage(player, "&6[ModernTour] &aВы успешно добавлены в обход.");
            TextComponent exitMessage = new TextComponent();
            exitMessage.addExtra("§сВнимание! Ваша позиция будет использована как точка для телепортации. Если вы хотите изменить ее - нажмите на кнопку в чате и выйдите из очереди, затем заново зайдите в обход на новых координатах §r");
            TextComponent message = new TextComponent();
            message.addExtra("§aПосмотреть свою позицию в очереди обхода:§r ");

            TextComponent listButton = new TextComponent("[LIST]");
            listButton.setColor(ChatColor.GOLD);
            listButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tour list"));
            listButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.RED + "Tour queue").create()));
            message.addExtra(listButton);
            TextComponent exitButton = new TextComponent("[LEAVE]");
            exitButton.setColor(ChatColor.GOLD);
            exitButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tour leave"));
            exitButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.RED + "Tour queue").create()));
            exitMessage.addExtra(exitButton);
            player.spigot().sendMessage(message);
            player.spigot().sendMessage(exitMessage);
        }else{
            Utils.sendMessage(player, "&6[ModernTour] &aПроизошла ошибка во время добавления в обход.");
        }

        return true;
    }
}
