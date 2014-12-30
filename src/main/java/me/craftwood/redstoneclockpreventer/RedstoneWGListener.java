package me.craftwood.redstoneclockpreventer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public final class RedstoneWGListener implements Listener {
	
	@EventHandler
	public void onBlockRedstoneChange(BlockRedstoneEvent er) {
		try {
			if (Main.plugin.getConfig().getStringList("Worlds").contains(er.getBlock().getWorld().getName()) && er.getBlock().getType() == Material.REDSTONE_WIRE) {

				ApplicableRegionSet set = WGBukkit.getRegionManager(er.getBlock().getWorld()).getApplicableRegions(er.getBlock().getLocation());

				if (set.size() > 0) {
					for (ProtectedRegion region : set) {
						if (!Main.plugin.getConfig().getStringList("WorldGuard.RegionWhitelist").contains(region.getId())) {
							Main.rst += 1;
							if (Main.rst > Main.plugin.getConfig().getInt("MaxSignalChanges")) {
								er.getBlock().setType(Material.getMaterial(Main.plugin.getConfig().getString("ClockBlocker")));
								if (Main.plugin.getConfig().getString("ClockBlocker").equals("SIGN_POST")) {
									Sign sign = (Sign) er.getBlock().getState();
									sign.setLine(0, Main.plugin.getConfig().getString("Sign.Line1").replaceAll("&((?i)[0-9a-fk-or])", "\u00A7$1"));
									sign.setLine(1, Main.plugin.getConfig().getString("Sign.Line2").replaceAll("&((?i)[0-9a-fk-or])", "\u00A7$1"));
									sign.setLine(2, Main.plugin.getConfig().getString("Sign.Line3").replaceAll("&((?i)[0-9a-fk-or])", "\u00A7$1"));
									sign.setLine(3, Main.plugin.getConfig().getString("Sign.Line4").replaceAll("&((?i)[0-9a-fk-or])", "\u00A7$1"));
									sign.update();
								}
								Main.rst = 0;
								if (Main.plugin.getConfig().getBoolean("NotifyAdmins")) {
									Bukkit.broadcast("[" + ChatColor.RED + "RCP" + ChatColor.WHITE + "] " + ChatColor.GRAY + "Clock detected and stopped at " + ChatColor.WHITE
											+ "x" + er.getBlock().getLocation().getBlockX() + " y" + er.getBlock().getLocation().getBlockY() + " z" + er.getBlock().getLocation().getBlockZ()
											+ ChatColor.GRAY + " in " + ChatColor.WHITE + er.getBlock().getWorld().getName()
											, "rscp.getnotified");
								}
							}
						}
					}
				} else {
					Main.rst += 1;
					if (Main.rst > Main.plugin.getConfig().getInt("MaxSignalChanges") && er.getBlock().getType() == Material.REDSTONE_WIRE) {
						er.getBlock().setType(Material.getMaterial(Main.plugin.getConfig().getString("ClockBlocker")));
						if (Main.plugin.getConfig().getString("ClockBlocker").equals("SIGN_POST")) {
							Sign sign = (Sign) er.getBlock().getState();
							sign.setLine(0, Main.plugin.getConfig().getString("Sign.Line1").replaceAll("&((?i)[0-9a-fk-or])", "\u00A7$1"));
							sign.setLine(1, Main.plugin.getConfig().getString("Sign.Line2").replaceAll("&((?i)[0-9a-fk-or])", "\u00A7$1"));
							sign.setLine(2, Main.plugin.getConfig().getString("Sign.Line3").replaceAll("&((?i)[0-9a-fk-or])", "\u00A7$1"));
							sign.setLine(3, Main.plugin.getConfig().getString("Sign.Line4").replaceAll("&((?i)[0-9a-fk-or])", "\u00A7$1"));
							sign.update();
						}
						Main.rst = 0;
						Bukkit.broadcast("[" + ChatColor.RED + "RCP" + ChatColor.WHITE + "] " + ChatColor.GRAY + "Clock detected and stopped at " + ChatColor.WHITE
								+ "x" + er.getBlock().getLocation().getBlockX() + " y" + er.getBlock().getLocation().getBlockY() + " z" + er.getBlock().getLocation().getBlockZ()
								+ ChatColor.GRAY + " in " + ChatColor.WHITE + er.getBlock().getWorld().getName()
								, "rscp.getnotified");
					}
				}
			}
		} catch (Exception ex) {
				ex.printStackTrace();
		}
	}

}
