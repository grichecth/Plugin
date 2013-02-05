package com.wolvencraft.yasp;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.wolvencraft.yasp.Database.Database;
import com.wolvencraft.yasp.Database.QueryUtils;
import com.wolvencraft.yasp.Database.data.normal.Settings;
import com.wolvencraft.yasp.Database.exceptions.DatabaseConnectionException;
import com.wolvencraft.yasp.EventDataHandlers.EDHPlayer;
import com.wolvencraft.yasp.Listeners.BlockListener;
import com.wolvencraft.yasp.Listeners.EntityListener;
import com.wolvencraft.yasp.Listeners.PlayerListener;
import com.wolvencraft.yasp.Stats.PlayerData;
import com.wolvencraft.yasp.Utils.Message;
import com.wolvencraft.yasp.Utils.Configuration;

public class StatsPlugin extends JavaPlugin {
	private static StatsPlugin plugin;
	private static Configuration settings;
	
	private Database database;
	private ExecutorService executorService;
	private DataProcessor dataProcessor;
	private Timer dataProcessorTimer;
	private PlayerData playerData;
	private EDHPlayer edhPlayer;

	@Override
	public void onEnable() {
		plugin = this;
		
		this.setNaggable(false);

		getConfig().options().copyDefaults(true);
		saveConfig();
		settings = new Configuration(this);
		
		if (database == null) {
			try { database = new Database(); }
			catch (ClassNotFoundException e) {
				Message.log(Level.SEVERE, "MySQL Driver not found");
				if (settings.DEBUG) e.printStackTrace();
			} catch (DatabaseConnectionException e) {
				Message.log(Level.SEVERE, "Critical Error, could not connect to mySQL. Is the database Available? Check config file and try again. (" + e.getMessage() + ")");
				if (settings.DEBUG) e.printStackTrace();
			}
		}
		
		if (database == null) {
			StatsPlugin.plugin = null;
			this.getPluginLoader().disablePlugin(this);
			return;
		}

		Settings.refresh();
		
		QueryUtils.pluginStartup();
		this.executorService = Executors.newCachedThreadPool();
		this.edhPlayer = new EDHPlayer();
		this.playerData = new PlayerData();
		this.dataProcessor = new DataProcessor();
		this.dataProcessor.addProcessable(this.playerData);

		this.dataProcessorTimer = new Timer(true);
		this.dataProcessorTimer.scheduleAtFixedRate(this.dataProcessor, settings.PING, settings.PING);

		// Setup Listeners
		new PlayerListener(this, this.edhPlayer);
		new BlockListener(this, this.edhPlayer);
		new EntityListener(this, this.edhPlayer);

		// This could be a reload so see if people are logged in
		for (Player player : this.getServer().getOnlinePlayers()) {
			this.edhPlayer.PlayerJoin(player);
		}
	}

	@Override
	public void onDisable() {
		if (StatsPlugin.plugin == null || !StatsPlugin.plugin.equals(this)) return;

		this.dataProcessorTimer.cancel();

		if (this.edhPlayer != null) {
			for (Player player : this.getServer().getOnlinePlayers()) {
				this.edhPlayer.PlayerQuit(player);
			}
		}

		if (this.playerData != null) {
			this.playerData._processData();
		}

		if (this.database != null) {
			QueryUtils.pluginShutdown();
			this.database = null;
		}

		plugin = null;

		if (this.executorService != null) {
			this.executorService.shutdown();
		}
	}
	
	public static StatsPlugin getInstance() 		{ return plugin; }
	public static Configuration getSettings()			{ return settings; }
	public static void reloadSettings() {
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
		settings = null;
		settings = new Configuration(plugin);
	}
	public Database getDB() 						{ return this.database; }
	public ExecutorService getExecutor()			{ return this.executorService; }
	public PlayerData getPlayerData()				{ return this.playerData; }
	public boolean statExempt(Player player)		{ return !player.hasPermission("statistician.ignore"); }
}
