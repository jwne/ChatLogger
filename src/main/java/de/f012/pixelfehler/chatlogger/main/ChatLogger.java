/*
 * ChatLogger.java
 * 
 * version 1.0
 * 
 * Copyright (c) 2014-2015 Floppy012
 * 
 * Main Class of this Bungeecord Plugin
 */

package de.f012.pixelfehler.chatlogger.main;

import java.io.File;

import net.md_5.bungee.api.plugin.Plugin;
import de.f012.pixelfehler.chatlogger.listener.ChatListener;
import de.f012.pixelfehler.chatlogger.util.FileLogger;

public class ChatLogger extends Plugin {
	
	public static File pluginFolder = null;
	private static ChatLogger instance;

	public ChatLogger() {
		instance = this;
	}
	
	public static ChatLogger getInstance(){
		return instance;
	}

	@Override
	public void onEnable() {
		pluginFolder = this.getDataFolder();
		
		this.registerCommands();
		this.registerEvents();
		FileLogger.init();
	}
	
	private void registerEvents(){
		this.getProxy().getPluginManager().registerListener(this, new ChatListener(this));
	}
	
	private void registerCommands(){
		//Coming up
	}

}
