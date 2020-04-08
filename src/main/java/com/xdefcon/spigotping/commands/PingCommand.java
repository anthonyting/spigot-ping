package com.xdefcon.spigotping.commands;

import com.xdefcon.spigotping.SpigotPing;
import com.xdefcon.spigotping.utils.PingUtil;
import com.xdefcon.spigotping.utils.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {
    private SpigotPing plugin;

    public PingCommand(SpigotPing plugin) {
        this.plugin = plugin;
    }

    private static void sendMessageFromConfig(CommandSender p, String message, String defaultMessage) {
        if (message != null) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        } else {
            p.sendMessage(defaultMessage);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command c, String label, String[] args) {
        String defaultNoPermMessage = "You do not have access to this command.";


        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                plugin.getLogger().info(ChatColor.translateAlternateColorCodes((char) 12, "This command is only executable as a Player."));
                return true;
            }
            final Player p = (Player) sender;
            if (plugin.getConfig().getBoolean("permission-system.enabled")) {
                if (!p.hasPermission("spigotping.ping")) {
                    sendMessageFromConfig(p, plugin.getConfig().getString("permission-system.no-perm-message"), defaultNoPermMessage);
                    return true;
                }
            }
            String ping = "" + PingUtil.getPing(p);
            String customMex = plugin.getConfig().getString("ping-command.ping-message");
            if (customMex != null) {
                customMex = customMex.replaceAll("%ping%", ping);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', customMex));
                if (plugin.getConfig().getBoolean("sound-manager.enabled")) {
                    SoundUtil.playSound(p, plugin.getConfig().getString("sound-manager.sound-type"));
                }
            }
        } else {
            if (sender instanceof Player) {
                final Player p = (Player) sender;
                if (!p.hasPermission("spigotping.ping.others")) {
                    sendMessageFromConfig(p, plugin.getConfig().getString("others-ping.not-allowed-message"), defaultNoPermMessage);
                    return true;
                }
            }
            String target = args[0];
            Player targetP = Bukkit.getPlayer(target);
            if (targetP == null) {
                sendMessageFromConfig(sender, plugin.getConfig().getString("others-ping.player-not-found"), "Player not found");
                return true;
            }
            String message = plugin.getConfig().getString("ping-command.ping-target-message");
            if (message == null) {
                message = "%ping%";
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message
                    .replace("%ping%", "" + PingUtil.getPing(targetP))
                    .replace("%target%", targetP.getName())));
        }
        return true;
    }

}
