package com.xdefcon.spigotping.commands;

import com.xdefcon.spigotping.SpigotPing;
import com.xdefcon.spigotping.utils.PingUtil;
import com.xdefcon.spigotping.utils.SoundUtil;
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

    @Override
    public boolean onCommand(CommandSender sender, Command c, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().info(ChatColor.RED + "This command is only executable as a Player.");
            return true;
        }

        final Player p = (Player) sender;
        if (plugin.getConfig().getBoolean("permission-system.enabled")) {
            if (!p.hasPermission("spigotping.ping")) {
                String noPerm = plugin.getConfig().getString("permission-system.no-perm-message");
                if (noPerm != null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', noPerm));
                } else {
                    p.sendMessage("You do not have access to this command.");
                }
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
        return true;
    }

}
