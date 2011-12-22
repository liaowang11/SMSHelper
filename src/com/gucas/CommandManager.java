package com.gucas;

import android.content.SharedPreferences;

//Factory Pattern to produce commands from String[]
public class CommandManager {
	public CommandManager() {
		
	}

	public static BaseCommand BuildCommand(SharedPreferences pref, String command_name, String args){
		if(command_name.equalsIgnoreCase(FetchContactCommand.mCommandName)){
			return new FetchContactCommand(args);
		}else if (command_name.equalsIgnoreCase(AuthCommand.mCommandName)) {
			return new AuthCommand(args, pref);
		}else if (command_name.equalsIgnoreCase(FetchMisscallsCommand.mCommandName)){
			return new FetchMisscallsCommand();
		}
		//TODO: more commands to be added
		return null;
	}
	public static BaseCommand[] BuildCommands(SharedPreferences pref, String[] command_names, String[] args){
		BaseCommand[] commands = new BaseCommand[command_names.length];
		for (int i = 0; i < command_names.length; i++) {
			commands[i] = BuildCommand(pref, command_names[i], args[i]);
		}
		return commands;
	}
}
