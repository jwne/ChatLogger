/*
 * FileLogger.java
 * 
 * version 1.0
 * 
 * Copyright (c) 2014-2015 Floppy012
 * 
 * Little unit to make Logging easier
 */

package de.f012.pixelfehler.chatlogger.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import de.f012.pixelfehler.chatlogger.main.ChatLogger;

public class FileLogger {
	
	private static File logFolder;
	
	/**
	 * Initializes this class
	 */
	public static void init(){
		File rootDir = ChatLogger.pluginFolder;
		File logDir = new File(rootDir.getAbsolutePath() + "/logs");
		
		if(logDir.exists() && logDir.isDirectory()){
			logFolder = logDir;
		}else{
			logDir.mkdirs();
			logFolder = logDir;
		}
	}
	
	/**
	 * Logs a String to players Logfile
	 * @param player the player thats logfile gets affected
	 * @param message the message to write
	 */
	public static void log(ProxiedPlayer player, String message){
		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat time = new SimpleDateFormat();
		time.applyPattern("H:m:s");
		
		SimpleDateFormat sdfPlayerFile = new SimpleDateFormat();
		sdfPlayerFile.applyPattern("d-M-yyyy");
		
		String logMsg = "[" + player.getServer().getInfo().getName() + "][" + time.format(d) + "]" + player.getName() + ": " + message;
		
		File playerLogFile = new File(logFolder.getAbsolutePath() + "/" + player.getName() + "/" + sdfPlayerFile.format(d) + ".log");
		
		if(!hasDir(player.getName())){
			File playerLogDir = new File(logFolder.getAbsolutePath() + "/" + player.getName());
			if(!(playerLogDir.exists() && playerLogDir.isDirectory())){
				playerLogDir.mkdirs();
			}
			
			playerLogFile = new File(playerLogDir.getAbsolutePath() + "/" + sdfPlayerFile.format(d) + ".log");
			
			try{
				playerLogFile.createNewFile();
			}catch(IOException e){
				ChatLogger.getInstance().getLogger().severe("Error while creating Player log file: " + e.getMessage());
				return;
			}
		}
		
		try {
			FileOutputStream fos = new FileOutputStream(playerLogFile, true);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter writer = new BufferedWriter(osw);
			
			writer.newLine();
			writer.append(logMsg);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			ChatLogger.getInstance().getLogger().severe("Error while writing Player log file: " + e.getMessage());
		}
	}
	
	/**
	 * Checks if the player has a Logfile or a logdirectory
	 * @param player the player of which the logdir/file should be looked
	 * @return true if the player has a Dir&file, false if one or both are missing
	 */
	private static boolean hasDir(String player){
		if(logFolder == null){
			ChatLogger.getInstance().getLogger().severe("FileLogger has not been initialized!");
			return false;
		}
		
		File playerLogDir = new File(logFolder.getAbsolutePath() + "/" + player);
		
		if(playerLogDir.exists() && playerLogDir.isDirectory()){
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern("d-M-yyyy");
			
			File playerLogFile = new File(playerLogDir.getAbsolutePath() + "/" + sdf.format(date) + ".log");
			
			if(playerLogFile.exists()){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

}
