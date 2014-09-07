/*
 * UUIDUtils.java
 * 
 * version 1.0
 * 
 * Copyright (c) 2014-2015 Floppy012 (Except for addDashesToUUID())
 * 
 * Gets username or UUID from a web based API
 */

package de.f012.pixelfehler.chatlogger.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.f012.pixelfehler.chatlogger.main.ChatLogger;

public class UUIDUtils {

	/**
	 * This method gets the username from its UUID. Still waiting for the own UUID system to be up...
	 * 
	 * @author Floppy012
	 * @param uuid The UUID as String
	 * @return The Players name
	 */
	public static String getNameFromUUID(String uuid){
		try {
			URL url = new URL("http://mc-api.net/name/" + uuid);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String jsonData = br.readLine();
			
			if(jsonData.equals("")){
				ChatLogger.getInstance().getLogger().warning("Could not get Information about Player \"" + uuid + "\" [No Data returned]");
				return null;
			}
			
			Gson gson = new Gson();
			
			Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
			HashMap<String, Object> data = gson.fromJson(jsonData, type); 
			
			if(data.size() == 0){
				ChatLogger.getInstance().getLogger().severe("Error while decoding JSON data!");
				return null;
			}
			
			String name = (String)data.get("name");
			
			return name;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * This method gets the UUID as an UUID object from the username (for cases the user is offline)
	 * @param name The username as String
	 * @return the UUID Object
	 */
	public static UUID getUUIDFromName(String name){
		try{
			URL url = new URL("http://mc-api.net/uuid/" + name);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String jsonData = br.readLine();
			
			if(jsonData.equals("")){
				ChatLogger.getInstance().getLogger().warning("Could not get Information about Player \"" + name + "\" [No Data returned]");
				return null;
			}
			
			Gson gson = new Gson();
			
			Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
			HashMap<String, Object> data = gson.fromJson(jsonData, type); 
			
			if(data.size() == 0){
				ChatLogger.getInstance().getLogger().severe("Error while decoding JSON data!");
				return null;
			}
			
			String uuid = (String)data.get("uuid");
			uuid = addDashesToUUID(uuid);
			
			try{
				UUID uuidObject = UUID.fromString(uuid);
				return uuidObject;
			}catch(Exception e){
				ChatLogger.getInstance().getLogger().severe("Error while parsing URL from String!");
				return null;
			}
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * This method adds the Dashes back to UUID since the minecraft Session Server
	 * does not return them with dashes :(
	 * 
	 * @author Quackster (https://forums.bukkit.org/threads/java-adding-dashes-back-to-minecrafts-uuids.272746/)
	 * @param undashedUUID UUID as String without dashes
	 * @return UUID as String with dashes
	 */
	public static String addDashesToUUID(String undashedUUID){
		StringBuffer sb = new StringBuffer(undashedUUID);
		sb.insert(8, "-");
		 
		sb = new StringBuffer(sb.toString());
		sb.insert(13, "-");
		 
		sb = new StringBuffer(sb.toString());
		sb.insert(18, "-");
			 
		sb = new StringBuffer(sb.toString());
		sb.insert(23, "-");
			 
		return sb.toString();
	}
	
}
