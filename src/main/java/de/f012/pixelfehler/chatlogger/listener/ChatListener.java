/*
 * ChatListener.java
 * 
 * version 1.1
 * 
 * Copyright (c) 2014-2015 Floppy012
 * 
 * Listens to the chat
 */

package de.f012.pixelfehler.chatlogger.listener;

import java.util.ArrayList;
import java.util.Collection;

import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import de.f012.pixelfehler.chatlogger.main.ChatLogger;
import de.f012.pixelfehler.chatlogger.util.FileLogger;
import de.f012.pixelfehler.chatlogger.util.PlayerUtils;

public class ChatListener implements Listener {

	private ChatLogger instance;
	
	public ChatListener(ChatLogger instance){
		this.instance = instance;
	}
	
	@EventHandler
	public void onChat(ChatEvent event){
		final Collection<ProxiedPlayer> players = new ArrayList<ProxiedPlayer>(this.instance.getProxy().getPlayers());
		final Connection con = event.getSender();
		final String message = event.getMessage();
		
		this.instance.getProxy().getScheduler().runAsync(this.instance, new Runnable(){
			@Override
			public void run(){
				ProxiedPlayer pplayer = PlayerUtils.getPlayerFromConnection(players, con);
				if(pplayer != null){
					FileLogger.log(pplayer, message, false);
				}else{
					ChatLogger.getInstance().getLogger().severe("Could not get Player from connection!");
				}
			}
		});
		
	}
}
