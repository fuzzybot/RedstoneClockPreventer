package me.craftwood.redstoneclockpreventer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RcpCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 1 && args[0].equalsIgnoreCase("reload") && (sender.hasPermission("rscp.reload") || sender.isOp())) {
			Main.plugin.reloadConfig();
			sender.sendMessage("[" + ChatColor.RED + "RCP" + ChatColor.WHITE + "] " + ChatColor.GRAY + "Configuration reloaded.");
			
		} else if (args.length == 1 && args[0].equalsIgnoreCase("reload") && !sender.hasPermission("rscp.reload")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to excute this command.");
		}
		
		else if (args.length == 1 && args[0].equalsIgnoreCase("version")) {
			sender.sendMessage("[" + ChatColor.RED + "RCP" + ChatColor.WHITE + "] " + ChatColor.GRAY + "RedstoneClockPreventer Version " + Main.plugin.getDescription().getVersion() + " by MGbeeniboy.");
		}
		
		else if (args.length == 3 && args[0].equalsIgnoreCase("whitelist") && args[1].equalsIgnoreCase("add")) {
			Main.plugin.getConfig().getStringList("WorldGuard.RegionWhitelist").add(args[2]);
			Main.plugin.saveConfig();
			sender.sendMessage("ok");
			Main.plugin.saveDefaultConfig();
			Main.plugin.reloadConfig();
		}
		
		else if (args.length == 3 && args[0].equalsIgnoreCase("whitelist") && args[1].equalsIgnoreCase("remove")) {
			Main.plugin.getConfig().getStringList("WorldGuard.RegionWhitelist").remove(args[2]);
			sender.sendMessage("ok");
			Main.plugin.saveConfig();
			Main.plugin.reloadConfig();
		}
		
		return true;
	}

}
