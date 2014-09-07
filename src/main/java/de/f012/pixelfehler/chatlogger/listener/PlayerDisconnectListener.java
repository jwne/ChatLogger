/*
 * PlayerDisconnectListener.java
 * 
 * version 1.0
 * 
 * Copyright (c) 2014-2015 Floppy012
 * 
 * Listens to player disconnects
 */

package de.f012.pixelfehler.chatlogger.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import de.f012.pixelfehler.chatlogger.main.ChatLogger;
import de.f012.pixelfehler.chatlogger.util.FileLogger;

public class PlayerDisconnectListener implements Listener {

	
	public PlayerDisconnectListener(ChatLogger instance){
		
	}
	
	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent event){
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("H:m:s");
		FileLogger.log(event.getPlayer(), "======== CONNECTION TERMINATED AT TIME " + sdf.format(date) + " ========", true);
	}
}
