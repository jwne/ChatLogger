/*
 * PlayerUtils.java
 * 
 * version 1.0
 * 
 * Copyright (c) 2014-2015 Floppy012
 * 
 * Short class to get a player from a connection
 */

package de.f012.pixelfehler.chatlogger.util;

import java.util.Collection;

import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerUtils {

	
	public static ProxiedPlayer getPlayerFromConnection(Collection<ProxiedPlayer> players, Connection con){
		for(ProxiedPlayer player : players){
			if(player.getAddress().getAddress().getHostAddress().equals(con.getAddress().getAddress().getHostAddress())){
				if(player.getAddress().getPort() == con.getAddress().getPort()){
					return player;
				}
			}
		}
		return null;
	}
}
