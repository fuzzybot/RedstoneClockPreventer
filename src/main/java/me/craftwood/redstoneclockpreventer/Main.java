package me.craftwood.redstoneclockpreventer;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	boolean noWgDetected;
	public static int rst;

	public void onEnable() {
		
		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
			reloadConfig();
		}
		
		System.out.println("[" + getDescription().getName() + "] " + getDescription().getName() + " v" + getDescription().getVersion() + " by MGbeenieboy enabled");
		
		this.getCommand("rcp").setExecutor(new RcpCommand());
		Plugin worldguard = getServer().getPluginManager().getPlugin("WorldGuard");
		if(worldguard != null && worldguard.isEnabled()) {
			System.out.println("[" + getDescription().getName() + "] WorldGuard detected.");
			getServer().getPluginManager().registerEvents(new RedstoneWGListener(), this);
			noWgDetected = false;
		} else {
			System.out.println("[" + getDescription().getName() + "] WorldGuard not detected.");
			noWgDetected = true;
			getServer().getPluginManager().registerEvents(new RedstoneListener(), this);
		}
		plugin = this;
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			public void run() {
				rst = 0;
			}
		}, 0, getConfig().getLong("TimePeriod") * 20);
	}
  
	public void onDisable()
	{
		plugin = null;
		System.out.println("[" + getDescription().getName() + "] " + getDescription().getName() + " v" + getDescription().getVersion() + " by " + getDescription().getAuthors() + " disabled");
	}

}
