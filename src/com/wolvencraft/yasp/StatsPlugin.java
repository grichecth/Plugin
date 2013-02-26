package com.wolvencraft.yasp;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.wolvencraft.yasp.db.Database;
import com.wolvencraft.yasp.db.data.normal.Settings;
import com.wolvencraft.yasp.db.exceptions.DatabaseConnectionException;
import com.wolvencraft.yasp.listeners.*;
import com.wolvencraft.yasp.util.Message;

public class StatsPlugin extends JavaPlugin {
	private static StatsPlugin instance;
	private Database database;
	
	@Override
	public void onEnable() {
		instance = this;

		getConfig().options().copyDefaults(true);
		saveConfig();
		new Settings(this);
		
		try { database = new Database(); }
		catch (ClassNotFoundException e) {
			Message.log(Level.SEVERE, "MySQL Driver not found");
			if (Settings.getDebug()) e.printStackTrace();
			this.setEnabled(false);
			return;
		} catch (DatabaseConnectionException e) {
			Message.log(Level.SEVERE, "Could not connect to the database. Is the plugin configured correctly?");
			if (Settings.getDebug()) e.printStackTrace();
			this.setEnabled(false);
			return;
		}
		
		if (database == null) {
			instance = null;
			this.setEnabled(false);
			return;
		}
		
		Message.log("Established database connection");
		
		new DataCollector();
		
		new PlayerListener(this);
		new BlockListener(this);
		new EntityListener(this);
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, new DatabaseSync(), 0L, Settings.getPing());
	}

	@Override
	public void onDisable() {
		instance = null;
		Bukkit.getScheduler().cancelAllTasks();
		DataCollector.clear();
	}
	
	public static StatsPlugin getInstance() 		{ return instance; }
}
